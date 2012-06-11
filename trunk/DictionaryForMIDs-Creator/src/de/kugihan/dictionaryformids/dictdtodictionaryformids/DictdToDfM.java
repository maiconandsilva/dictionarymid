/*
****************************************************************************
* This version of this file is part of DictionaryForMIDs Creator
* (C) 2012 Karim Mahamane Karimou
*
* This version is a modified version. It was modified to make it compatible
* with DictionaryForMIDs Creator. It was modified by me. See below for
* information about the original copyright holder.
*
* DictionaryForMIDs Creator (DfMCreator) is a GUI wrapper around various
* DictionaryForMIDs tools, among others we have DictdToDictionaryForMIDs,
* DictionaryGeneration, JarCreator and BitmapFontGenerator.
*  
* GPL applies, see file COPYING for more license information.
* 
*****************************************************************************/


/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005 Gert Nuber (Gert.Nuber@gmx.net)
GPL applies - see file COPYING for copyright statement.


This is the dictionary convertion tool for DictionaryForMIDs.
It takes a dictd file, using jDictd, and converts it to a CSV-file.
*/

package de.kugihan.dictionaryformids.dictdtodictionaryformids;

import de.kugihan.DfMCreator.DfMCreatorMain;
import de.kugihan.DfMCreator.DictdConvSummary;
import edu.hws.eck.mdb.I18n;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JOptionPane;

public class DictdToDfM {

	//public String versionNumber = "2.4.0";

	private static String dbName;
	private static String dbFolderName;
	private static String outputCSVFile;
	private static String outputEncodingCharset;
	private static char separatorCharacter;
	private static boolean switchLanguages;
	private static boolean keepTabAndNewLineChars;
	private static boolean removeSquareBrackets;
        
        // We will use the following variable and its getter
        // method to retrieve the 00-header of dictd files.
        private static String header;
        
        private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        /*
         * Getter methods for DictdToDfM class.
         */

        public static String getDBName(){
            return dbName;
        }
        
        public static String getDBFolderName(){
            return dbFolderName;
        }
        
        public static String getOutputCSVFile(){
            return outputCSVFile;
        }
        
        public static String getOutputEncodingCharset(){
            return outputEncodingCharset;
        }
        
        public static char getSeparatorCharacter(){
            return separatorCharacter;
        }
        
        public static boolean getSwitchLanguages(){
            return switchLanguages;
        }
        
        public static boolean getKeepTabAndNewLineChars(){
            return keepTabAndNewLineChars;
        }
        
        public static boolean getRemoveSquareBrackets(){
            return removeSquareBrackets;
        }
        
        // Getter method for the header variable.
        public static String getHeader(){
            return header;
        }
        
        /*
         * Setter methods.
         */
        
        public static void setDBName(String newDBName){
            dbName = newDBName;
        }
        
        public static void setDBFolderName(String newDBFolderName){
            dbFolderName = newDBFolderName;
        }
        
        public static void setOutputCSVFile(String newOutputCSVFile){
            outputCSVFile = newOutputCSVFile;
        }
        
        public static void setOutputEncodingCharset(String newOutputEncodingCharset){
            outputEncodingCharset = newOutputEncodingCharset;
        }
        
        public static void setSeparatorCharacter(char newSeparatorCharacter){
            separatorCharacter = newSeparatorCharacter;
        }
        
        public static void setSwitchLanguages(boolean newSwitchLanguages){
            switchLanguages = newSwitchLanguages;
        }
        
        public static void setKeepTabAndNewLineChars(boolean newKeepTabAndNewLineChars){
            keepTabAndNewLineChars = newKeepTabAndNewLineChars;
        }
        
        public static void setRemoveSquareBrackets(boolean newRemoveSquareBrackets){
            removeSquareBrackets = newRemoveSquareBrackets;
        }
        
        
    // subroutine to be called to retrieve the summary
    // of the conversion preferences before the actual
    // conversion.
    public static void printDictdConvSummary() {
        DictdConvSummary summary = DictdConvSummary.getInstance();
        summary.setSize(425, 500);
        summary.setModal(true);
        summary.setLocation(screenSize.width / 2 - summary.getWidth() / 2,
                          screenSize.height / 2 - summary.getHeight() / 2);
        summary.setVisible(true);
    }
        
	 
	/*
	 * 
	 * The convertion subroutine: reads all the entries from the DICT-database
	 * and writes them line by line to the output file.
	 * 
	 */
	 
	 public static void convert() {

		try {
               try (OutputStreamWriter outputWriter = new OutputStreamWriter(new FileOutputStream(outputCSVFile),
                    outputEncodingCharset)) {
                        org.dict.kernel.IDictEngine e =
                        org.dict.server.DatabaseFactory.getEngine(dbFolderName + "/" + dbName + ".ini");

                        String definition;
                        /* In a loop all entries of the datebase are retrieved */
                        for (int pos = 0; pos < e.getDatabases()[0].getSize(); ++ pos) {
                        //for (int pos = 0; pos < 1000; ++ pos) {  // this line is for tests only
                                // generate the string to query the entry at pos
                                String posString = "db=" + dbName + "&pos=" + pos;
                                // retrieve the definition at pos
                                org.dict.kernel.IRequest req =
                                                new org.dict.kernel.SimpleRequest("", posString);
                                org.dict.kernel.IAnswer[] arr = e.lookup(req);
                                // parse the result and write to the output file
                                for (int i = 0; i < arr.length; ++i) {
                                        definition = arr[i].getDefinition();
                                        if (! definition.startsWith("00")) {  // skip the entries starting with "00" - these contain
                                                                                // general information about the dictionary
                                                if (removeSquareBrackets)
                                                        definition = removeSquareBracketContent(definition);
                                                // get rid of the separatorCharacter in the definition
                                                definition = replaceCharacterInString(definition, separatorCharacter, ' ');
                                                String definitionConverted = definition;
                                                // search where the keyword ends and the translation starts
                                                int posTranslationSeparator = definitionConverted.indexOf('\n');
                                                int posEndKeyWord = posTranslationSeparator - 1;
                                                int posStartTranslation = posTranslationSeparator + 1;
                                                // separate keyword and translation
                                                String keyWord = definitionConverted.substring(0, posEndKeyWord + 1).trim();
                                                String translation = definitionConverted.substring(posStartTranslation);
                                                translation = removeNewlinesAtBeginAndEnd(translation); // useful e.g. for freedict English-Turkish Dictionary
                                                translation = replaceFieldAndLineSeparationChars(translation);  // note: separatorCharacter was already removed before
                                                // if required, perform adaptation to output character encoding
                                                translation = adaptToCharset(translation);
                                                keyWord = adaptToCharset(keyWord);
                                                // if there should be any repeated whitespaces in the translation, trim the superflous whitespaces
                                                translation = filterSuperflousWhitespaces(translation);
                                                // write line to the output file
                                                String outputLine = new String();
                                                if (switchLanguages) {
                                                        outputLine = translation + separatorCharacter + keyWord + "\n";
                                                }
                                                else {
                                                        outputLine = keyWord + separatorCharacter + translation + "\n";
                                                }
                                                outputWriter.write(outputLine);
                                        }
                                        else {
                                                // print to stdout the "00" entries (for information)

                                                System.out.println(definition);
                                                header = "\n\n" + definition + "\n\n";

                                        }
                                }
                        }
                    }
			System.out.println(I18n.tr("done"));
		} catch (UnsupportedEncodingException e){
                            DictdConvSummary.done = true;
                            DfMCreatorMain.printAnyMsg(I18n.tr("encErrMsg", new Object[] {e.getLocalizedMessage()}),
                                                       I18n.tr("encErrWinTitle"), JOptionPane.ERROR_MESSAGE);
                            // print the exception in command line.
                            System.out.println(e + "\n");
                        } catch (Throwable t){
                            DfMCreatorMain.printAnyMsg(I18n.tr("criticalErrMsg", new Object[]
                                        {t, t.getLocalizedMessage()}), I18n.tr("error"), JOptionPane.ERROR_MESSAGE);
                            System.out.println(t + "\n");
                        } 
	}
        
		
				
	/*
	 * 
	 * A few utility methods:
	 * 
	 */
	 
	public static String removeSquareBracketContent(String expression) {
		// remove everything in square brackets
		int startBracket = expression.indexOf('[');
		int endBracket = expression.indexOf(']');
		StringBuilder returnExpression = new StringBuilder(expression);
		if ((startBracket >= 0) && (endBracket > startBracket)) {
			returnExpression.delete(startBracket, endBracket+1);
		}
		return returnExpression.toString();
	}

	public static String replaceCharacterInString(String input, char oldCharacter, char newCharacter) {
		String output = new String();
		for (int pos = 0; pos < input.length(); ++pos) {
			if (input.charAt(pos) != oldCharacter) {
				output = output + input.charAt(pos); 
			}
			else {
				output = output + newCharacter;
			}
		}
		return output;
	}
	
	public static String adaptToCharset(String input)
				throws IOException {
		String output;
		if ("ISO_8859-1".equals(outputEncodingCharset)) {
			// Replace characters that do not exist in ISO-8859-1 with blanks.
			String inputCleaned = new String();
			for (int posInput = 0; posInput < input.length(); ++posInput) {
				char characterFromInput = input.charAt(posInput);
				int charType = Character.getType(characterFromInput);
				if ((charType == Character.COMBINING_SPACING_MARK) ||
					(charType == Character.DASH_PUNCTUATION ) ||
					(charType == Character.FORMAT ) ||
					(charType == Character.SPACE_SEPARATOR ) ||
					(charType == Character.UNASSIGNED )) {
					// replace that character by a blank
					inputCleaned = inputCleaned + ' '; 
				}
				else {
					inputCleaned = inputCleaned + characterFromInput; 
				}
			}
			output = new String();
			// get rid of characters that are converted into ?
			for (int posInput = 0; posInput < inputCleaned.length(); ++posInput) {
				String characterFromInput = inputCleaned.substring(posInput, posInput + 1);
				byte [] bytesForString = characterFromInput.getBytes(outputEncodingCharset); 
				if ((bytesForString.length == 1) && (bytesForString[0] == (int) '?')) {
					// replace that character by a blank
					output = output + ' '; 
				}
				else {
					output = output + characterFromInput; 
				}
			}
		}
		else {
			// keep input
			output = input;
		}
		return output;
	}

	public static String replaceFieldAndLineSeparationChars(String input) {
		String output;

		if (! keepTabAndNewLineChars) {
			output = replaceCharacterInString(input, '\n', ' ');
			output = replaceCharacterInString(output, separatorCharacter, ' ');
		}
		else {
			output = new String();
			for (int pos = 0; pos < input.length(); ++pos) {
				char currentCharacter = input.charAt(pos);
				if (currentCharacter == '\n') {
					output = output + "\\n";
				}
				else if (currentCharacter == '\t') {
					output = output + "\\t";
				}
				else if (currentCharacter == '\\') {
					output = output + "\\\\";
				}
				else {
					output = output + currentCharacter;
				}
			}
		}
		return output;
	}

	// duplicated for convenience from de.kugihan.dictionaryformids.general.Util: 
	public static String filterSuperflousWhitespaces(String expression) {
		String resultExpression = new String();
		boolean lastCharWasWhitespace = false;
		for (int charPos = 0; charPos < expression.length(); ++ charPos) {
			char character = expression.charAt(charPos);
			if ( (character == '\t') || (character == ' ')) {
			    if (resultExpression.length() == 0) {
					// ignore whitspaces at the beginning of the expression
				}
				else if (charPos == expression.length() -1) {
					// ignore whitspaces at the end of the expression
				}
				else if (lastCharWasWhitespace)  {
					// ignore multiple whitespaces in sequence
				}

				else {
					resultExpression = resultExpression + character;
				}
				lastCharWasWhitespace = true;
			}
			else {
				resultExpression = resultExpression + character;
				lastCharWasWhitespace = false;
			}
		}
		return resultExpression;
	}

	public static String removeNewlinesAtBeginAndEnd(String input) {
		String output;
		int pos = 0;
		while ((pos < input.length()) && (input.charAt(pos) == '\n'))
			++pos;
		if (pos < input.length())
			output = input.substring(pos, input.length());
		else 
			output = "";
		pos = output.length() -1;
		while ((pos >= 0) && (output.charAt(pos) == '\n'))
			--pos;
		if (pos >= 0)
			output = output.substring(0, pos + 1);
		else 
			output = "";
		return output;
	}
}
