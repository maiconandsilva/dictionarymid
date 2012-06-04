/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005 Gert Nuber (Gert.Nuber@gmx.net)
GPL applies - see file COPYING for copyright statement.

Copyright (C) 2012 Karim Mahamane Karimou (karimoune87@gmail.com)
This is version 2.4.0. It brings many improvements  regarding
The setting up of the values. Remember, one needed to set up the values
directly in this source code and compile it each time one needed to convert
a new dictionary. Now, values are set up directly through the command
line. Therefore, the first "universal" jar of DictdToDictionaryForMIDs
about to be distributed will be of version 2.4.0.

This version uses the TextIO.java class from David J. Eck,
to retrieve the parameters -- LGPL compatible Licence, See TextIO_Licence.txt
Since TextIO.java and the source code for jDictd are all (L)GPL compatible
free softwares, this version of DictdToDictionaryForMIDs includes a
distribution of these sources and therefore one does not need to go fetch for
these other sources. Thanks to the jDictd author, TextIO author ;-)
*/

import java.io.FileOutputStream;
import java.io.*;
import java.util.Scanner;

public class DictdToDictionaryForMIDs {

	public static String versionNumber = "2.5.0";

	static String dbname;	//The name of the database to be used.
	static String dbFoldername;	//The name and path to the folder/directory.
	static String outputCSVfile; // The name and path to the output file.
	static boolean switchLanguages;
	static boolean keepTabAndNewlineChars;
	static boolean removeSquareBrackets;
	static char separatorCharacter;
	static String outputEncodingCharset;

	
/************************************************************************
	Following lines are the former way of setting up the values directly in the
	source code for DictdToDictionaryForMIDs.
	
	static Sting dbname = "gcide";
	static String outputCSVfile = "C:/Generated_Dict/" + dbname + ".txt";
	static boolean switchLanguages = false;
	static boolean keepTabAndNewlineChars = true;
	static boolean removeSquareBrackets = false;
	static char separatorCharacter = '\t';
	static String outputEncodingCharset = "UTF-8";
*************************************************************************/
	
	
	
	/*
	 * 
	 * The main method: reads all entries from the DICT-database and writes them line by line to the output file.
	 * 
	 */
	public static void main(String[] args) {
		printCopyrightNotice();
				
	// Starting the interaction with the user.
		printDBSetUpInfo();
		dbname = TextIO.getlnWord();
	
		printDBFolderNamePrompt();
		dbFoldername = TextIO.getlnWord();
	
		printOutputCSVfileNamePrompt();
		outputCSVfile = TextIO.getlnWord();
	
		printSwitchLanguagesPrompt();
		switchLanguages = TextIO.getlnBoolean();
	
		printKeepTabAndNewlineCharsPrompt();
		keepTabAndNewlineChars = TextIO.getlnBoolean();
	
		printRemoveSquareBracketsPrompt();
		removeSquareBrackets = TextIO.getlnBoolean();
		
		printOutputEncodingCharsetPrompt();
		outputEncodingCharset = TextIO.getlnWord();
	
		printSeparatorCharacterPrompt();
		separatorCharacter = TextIO.getAnyChar();
	// End of the interaction.
		
		try {
			// Open output file for writing
			OutputStreamWriter outputWriter = new OutputStreamWriter(new FileOutputStream(outputCSVfile), outputEncodingCharset);
			// Open DICT-database
			org.dict.kernel.IDictEngine e =
			  org.dict.server.DatabaseFactory.getEngine("databases/" + dbname + ".ini");  // the ini-file must be in the databases-directory
			
			String definition;
			long writtenLines = 0; long badones = 0;
			/* In a loop all entries of the datebase are retrieved */
//			for (int pos = 0; pos < e.getDatabases()[0].getSize(); ++ pos) {
			for (int pos = 1; pos < e.getDatabases()[0].getSize(); ++ pos) {
//				for (int pos = 1; pos < 100; ++ pos) {  // this line is for tests only
				// generate the string to query the entry at pos
				String posString = "db=" + dbname + "&pos=" + pos;
				// retrieve the definition at pos
				org.dict.kernel.IRequest req =
						new org.dict.kernel.SimpleRequest("", posString);
				org.dict.kernel.IAnswer[] arr = e.lookup(req);
				// parse the result and write to the output file
				for (int i = 0; i < arr.length; ++i) {
					definition = arr[i].getDefinition();
					String key = arr[i].getKey();
					if (! definition.startsWith("00")) {  // skip the entries starting with "00" - these contain
						                                  // general information about the dictionary
						if (removeSquareBrackets)
							definition = removeSquareBracketContent(definition);
						// get rid of the separatorCharacter in the definition
						definition = replaceCharacterInString(definition, separatorCharacter, ' ');
						String definitionConverted = definition;
						// search where the keyword ends and the translation starts
						int posTranslationSeparator = definitionConverted.indexOf('\n');
//						int posTranslationSeparator = definitionConverted.indexOf('\\');
						if (posTranslationSeparator == -1) { System.out.println("def: " + i + " " + definition); ++badones; continue; }
						int posEndKeyWord = posTranslationSeparator - 1;
						int posStartTranslation = posTranslationSeparator + 1;
//						int posStartTranslation = posTranslationSeparator;
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
						++writtenLines;
					}
					else {
						// print to stdout the "00" entries (for information)
						System.out.println(definition);
					}
				}
			}
			outputWriter.close();
			System.out.println("written lines: " + writtenLines + "/bad lines: " + badones);
			System.out.println("Done");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	static public void printCopyrightNotice() {
		System.out.print(
				"\n\nDictionaryForMIDs/DictdToDictionaryForMIDs, Copyright (C) 2005 Gert Nuber (dict@kugihan.de)\n" +
				"Version : " + versionNumber + "\n\n" +
				"This program comes with ABSOLUTELY NO WARRANTY\n\n" +
				"This program is free software under the terms and conditions of the GPL " + 
				"(GNU \nGeneral Public License) version 2. See file COPYING. " +
				"If you did not receive the\nGNU General Public License along with this program " +
                "(file COPYING), write\nto the Free Software Foundation, Inc., " +
				"59 Temple Place, Suite 330, Boston,\nMA  02111-1307  USA\n\n" +
				"Source code is availble from http://sourceforge.net/projects/dictionarymid\n\n\n");
	}

	/*
	 * 
	 * Interactive messages print:
	 * 
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
		StringBuffer returnExpression = new StringBuffer(expression);
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
		if (outputEncodingCharset == "ISO_8859-1") {
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

		if (! keepTabAndNewlineChars) {
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
