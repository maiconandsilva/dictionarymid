/*
****************************************************************************
* This version of this file is part of DictionaryForMIDs-Creator
* (C) 2012 Karim Mahamane Karimou
*
* This version is a modified version. It was modified to make it compatible
* with DictionaryForMIDs-Creator. It was modified by me. See below for
* information about the original copyright holder.
*
* DictionaryForMIDs-Creator (DfM-Creator) is a GUI wrapper around various
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
import de.kugihan.DfMCreator.SumWinDictdToDfM;
import edu.hws.eck.mdb.I18n;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JOptionPane;

    public class DictdToDfM {

	public static String versionNumber = "2.5.0";
        
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
        SumWinDictdToDfM summary = SumWinDictdToDfM.getInstance();
        summary.setVisible(true);
    }
        
	public static void main(String[] args) {            
				
            // Starting the interaction with the user.
            printDBSetUpInfo();
            dbName = TextIO.getlnWord();

            printDBFolderNamePrompt();
            dbFolderName = TextIO.getlnWord();

            printOutputCSVfileNamePrompt();
            outputCSVFile = TextIO.getlnWord();

            printSwitchLanguagesPrompt();
            switchLanguages = TextIO.getlnBoolean();

            printKeepTabAndNewlineCharsPrompt();
            keepTabAndNewLineChars = TextIO.getlnBoolean();

            printRemoveSquareBracketsPrompt();
            removeSquareBrackets = TextIO.getlnBoolean();

            printOutputEncodingCharsetPrompt();
            outputEncodingCharset = TextIO.getlnWord();

            printSeparatorCharacterPrompt();
            separatorCharacter = TextIO.getAnyChar();

            // Call the conversion subroutine
            convert();
        }   
	 
	/*
	 * The convertion subroutine: reads all the entries from the DICT-database
	 * and writes them line by line to the output file.
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
                            SumWinDictdToDfM.done = true;
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
        
	
	static public void printCopyrightNotice() {
            System.out.print(
                "\n\nDictionaryForMIDs - DictdToDictionaryForMIDs Version " + versionNumber + "\n" +
                "Copyright (C) 2005 Gert Nuber, 2012 Karim Mahamane Karimou\n\n" +

                "DictdToDictionaryForMIDs is free software; you can redistribute it and/or modify\n" +
                "it under the terms of the GNU General Public License as published by\n" +
                "the Free Software Foundation; either version 2 of the License, or\n" +
                "(at your option) any later version.\n\n" +

                "DictdToDictionaryForMIDs is distributed in the hope that it will be useful,\n" +
                "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
                "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
                "GNU General Public License for more details.\n\n" +

                "You should have received a copy of the GNU General Public License\n" +
                "along with DictdToDictionaryForMIDs; if not, write to the Free Software Foundation,\n" +
                "Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA\n" +
                "For documentation and source code, see http://dictionarymid.sourceforge.net\n\n");
	}

	/*
	 * Interactive messages subroutines
         */
	
	
	static public void printDBSetUpInfo() {
		System.out.print(
				"\nDB_NAME\n" +
				"Enter the name of the database:\n" +
				"The name of the database defines the DICT-database to be used. It must be\n" +
				"defined for JDictd. See the documentation for JDictd to see how to set up\n" +
				"a DICT-database for JDictd. The JDictd database configuration file must\n" +
				"have the name <dbname-value>.ini. e.g. eng-por.ini. This ini-file\n" +
				"needs to be copied in the folder where the dictionary files are.\n");
	}
	

	static public void printDBFolderNamePrompt() {
		System.out.print(
				"\nDB_PATH_AND_FOLDER_NAME\n" +
				"Enter the path and the name of the folder where the ini file\n" +
				"is located:\n" +
				"e.g: /home/user/Dicts/Gcide -- on a Linux/Unix machine.\n" +
                "Or C:/Dicts/Gcide -- on a windows machine.\n" +
				"PS: don't add the last '/' to the folder name\n" +
				"this is added automatically by me ;-) what I mean is\n" +
				"instead of C:/Dicts/Gcide/ write C:/Dicts/Gcide\n");
	}
				
				

	static public void printOutputCSVfileNamePrompt() {
		System.out.print(
				"\nOUTPUT_CSV_FILE_PATH_AND_NAME\n" +
				"Enter the name and the path to the output CSV-file:\n" +
				"This is where the converted dictionary will be written to.\n" +
				"This file need not be present even if it were,\n" +
				"it will be overwritten anyway ;-)\n" +
				"e.g: C:/Generated_Dict/gcide.txt\n");
	}
				
				

	static public void printSwitchLanguagesPrompt() {
		System.out.print(
				"\nSWITCH_LANGUAGES\n" +
				"Enter: true, false, t, f, yes, no, y, n, 0, or 1\n" +
				"You can put upper or lowercase letters.\n" +
				"This will toggle the SWITCH_LANGUAGES value On/Off.\n" +
				"If SWITCH_LANGUAGES is set to true then\n" +
				"the translation is put in the first column and\n" +
				"the keyword in the second.\n" +
				"If it is set to false, the keyword is put in the\n" +
				"first column and the translation in the second\n");				
	}


	static public void printKeepTabAndNewlineCharsPrompt() {
		System.out.print(
				"\nKEEP_TAB_AND_NEW_LINE_CHARS\n" +
				"When KEEP_TAB_AND_NEW_LINE_CHARS is set to true then\n" +
				"the newline and tab characters in the translation text\n" +
				"are replaced by '\\n' and '\\t'. DictionaryForMIDs will then\n" +
				"insert newlines/tabs when displaying the result.\n" +
				"If KEEP_TAB_AND_NEW_LINE_CHARS is set to false then the newline\n" +
				"and tab characters are replaced by blanks;\n" +
				"Enter: true, false, t, f, yes, no, y, n, 0, or 1\n" +
				"You can put upper or lowercase letters.\n");
	}
	

	static public void printRemoveSquareBracketsPrompt() {
		System.out.print(
				"\nREMOVE_SQUARE_BRACKETS\n" +
				"If REMOVE_SQUARE_BRACKETS is set to true,\n" +
				"then any text within square brackets is removed from the\n" +
				"keyword/translation. However, bear in mind that this is a\n" +
				"very special (dangerous) flag that normally is set to false.\n" +
				"It is only set to true when really needed\n" +
				"Enter: true, false, t, f, yes, no, y, n, 0, or 1\n" +
				"You can put upper or lowercase letters.\n");
	}
	
	
	static public void printSeparatorCharacterPrompt() {
		System.out.print(
				"\nSEPARATOR_CHARACTER\n" +
				"It defines the character between the first\n" +
				"and the second column (normally a tab-character).\n" +
				"The property dictionaryGenerationSeparatorCharacter\n" +
				"for DictionaryGeneration has to have the same value\n" +
				"as SEPARATOR_CHARACTER\n" +
				"PS: if you want to put a tab character, type it literally,\n" +
				"That is, HIT the TAB KEY on your keyboard ;-)\n");
	}
	
	
	static public void printOutputEncodingCharsetPrompt() {
		System.out.print(
				"\nOUTPUT_ENCODING_CHARSET\n" +
				"It defines the character encoding for\n" +
				"the generated file. UTF-8 works with all files. The property\n" +
				"dictionaryGenerationInputCharEncoding for DictionaryGeneration\n" +
				"has to have the same value as OUTPUT_ENCODING_CHARSET.\n" +
				"PS: if UTF-8 is what you want to choose, type it as follows:\n" +
				"UTF-8\n");
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
