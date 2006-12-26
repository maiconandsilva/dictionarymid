/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

/*
 * How to use DictdToDictionaryForMIDs:
 * 
 * DictdToDictionaryForMIDs generates for DICT-databases an output file, which is directly used 
 * as the inputdictionaryfile by DictionaryGeneration.
 * 
 * You need to download the source of JDictd in order to use, please see 
 * http://www.informatik.uni-leipzig.de/~duc/Java/JDictd/
 * or look at 
 * http://www.sourceforge.net 
 * There is _no_ need to run JDictd, you just need to have the source code.
 * 
 * After intalling the source code of JDictd and the DICT-databases, you need to set the  
 * value of a few variables. You need to set at least dbname and outputCSVfile. For the 
 * other variables the default value may be ok.  
 * 
 * 
 * 
 * How to generate bi-directional dictionaries:
 * 
 * Typically the DICT-dictionaries are unidirectional. This means that there is only one
 * translation direction, e.g. English -> Portuguese. When you want to build a bi-directional
 * dictionary, you normally have to merge two DICT-dictionaries in one DictionaryForMIDs 
 * dictionary. This is a little bit tricky ...
 * 
 * For the first DICT-dictionary, e.g. English -> Portuguese:
 * - you set switchLanguages to false (this is the default)
 * - you generate with DictdToDictionaryForMIDs the output file
 * - for DictionaryGeneration, set languageXGenerateIndex to true for English and to false for Portuguese
 * - generate the files with DictionaryGeneration using the output from DictionaryGeneration as inputdictionaryfile  
 * 
 * For the second DICT-dictionary, e.g. Portuguese -> English:
 * - you set switchLanguages to true
 * - you generate with DictdToDictionaryForMIDs the output file
 * - for DictionaryGeneration, set languageXGenerateIndex to true for Portuguese and to true for English
 * - generate the files with DictionaryGeneration using the output from DictionaryGeneration as inputdictionaryfile.
 *   Generate the DictionaryGeneration-files into the same outputdirectory as for the first DICT-dictionary.
 * 
 * Then proceed with step 3, Putting the generated files in DictionaryForMIDs.jar 
 * 
 *  
 *  
 * If you have any trouble with DictdToDictionaryForMIDs send an email to dict@kugihan.de.
 *  
 */

package de.kugihan.dictionaryformids.dictdtodictionaryformids;

import java.io.FileOutputStream;
import java.io.*;

public class DictdToDictionaryForMIDs {

	public static String versionNumber = "2.4.0";

	/*
	 * 
	 * Variables to customize DictdToDictionaryForMIDs:
	 * 
	 */
	// dbname defined the DICT-database to be used (must be defined for JDictd). See the documentation for
	// JDictd for how to define a DICT-database for JDictd.
	// The JDictd database configuration file must have the name <dbname-value>.ini, e.g. eng-por.ini. This 
	// ini-file needs to be copied in the dictionary databases at the root of the source.
	static String dbname = "eng-deu";  // put your dbname there

	// outputCSVfile defines the path and file name where the generated file is written to.
	static String outputCSVfile = "G:/Programme/WTK22/apps/DictionaryForMIDs/Related Files/Dictionaries/freedict/"+ dbname + "/" + dbname + ".txt"; // put the name of your generated file there

	// If switchLanguages is set to true, then in the translation is put in the first column and the keyword
	// in the second. If set to false then the keyword is put in the first column and the translation in 
	// the second 
	static boolean switchLanguages = false;
//	static boolean switchLanguages = true;
	
	// keepTabAndNewlineChars is set to true when the newline and tab characters in the translation text
	// are replaced by "\n" and "\t". DictionaryForMIDs will then insert newlines/tabs when displaying the
	// result.
	// If keepTabAndNewlineChars is set to false then the newline and tab characters are replaced by blaks
	static boolean keepTabAndNewlineChars = false;
//	static boolean keepTabAndNewlineChars = true;
	
	// If removeSquareBrackets is set to true, then all text within square brackets is removed from the keyword/
	// translation. This is a very specific flag that normally is set to false and only set to true when needed
	// for a dictionary
	static boolean removeSquareBrackets = false;
	
	// separatorCharacter defines the character between the first and the second column (normally a tab-character)
	// The property dictionaryGenerationSeparatorCharacter for DictionaryGeneration needs have the same value as
	// separatorCharacter. 
	static char   separatorCharacter = '\t';

	// outputEncodingCharset defined the character encoding for the generated file. UTF-8 works with all files.
	// The property dictionaryGenerationInputCharEncoding for DictionaryGeneration needs have the same value as
	// outputEncodingCharset. 
	static String outputEncodingCharset = "UTF-8";
	
	
	/*
	 * 
	 * The main method: reads all entries from the DICT-database and writes them line by line to the output file.
	 * 
	 */
	public static void main(String[] args) {
		printCopyrightNotice();
		
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
