/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.general;

import java.io.IOException;
import java.io.InputStream;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileAccessHandler;

public abstract class Util {

	private static Util utilObj; 
		
	public static Util getUtil() {
		return utilObj;
	}
	
	public static void setUtil(Util newUtilObj) {
		utilObj = newUtilObj;
	}
	
	protected abstract void outputMessage(String message); 
 
	public static final int    logLevel0 = 0;
	public static final int    logLevel1 = 1;
	public static final int    logLevel2 = 2;
	public static final int    logLevel3 = 3;
	public static final int    logLevelMin = logLevel0;
	public static final int    logLevelMax = logLevel3;
	protected int              logLevel = logLevelMin;

	public void log(String logMessage, int logLevelOutput) {
		if (logLevel >= logLevelOutput)
			outputMessage(logMessage + "\n");
	}
	
	public void log(String logMessage) {
		log(logMessage, logLevelMin);
	}
	
	public void logDebug(String logMessage) {
		log(logMessage, logLevel1);
	}
	
	public void log(Throwable thrown) {
		// log exception, unless it is a DictionaryInterruptedException (which can be raised during
		// normal conditions)
		if (! (thrown instanceof DictionaryInterruptedException)) {
			outputMessage("Thrown " + thrown.toString() + " / " + thrown.getMessage() + "\n");
			// if (logLevel >= logLevel1)
				thrown.printStackTrace();
		}
	}
	
	public void logTime(String message, long startTime) {
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		log("Time for " + message + ": " + executionTime, logLevel2);
	}
	
	public void setLogLevel(int logLevelParam) {
		logLevel = logLevelParam;
	}

	public static void memCheck(String message) {
		if (getUtil().logLevel > logLevel1) {
			System.gc();
			getUtil().log(message + ": " + String.valueOf(Runtime.getRuntime().freeMemory()));
		}
	}
	
	public static boolean isSeparatorCharacter(char character) {
		return charIsLineWhitespace(character) ||
	    	   charIsPunctuation(character);
	}
		
	// replaces punctuation characters with blanks
	public static StringBuffer removePunctuation(StringBuffer string,
			                                     boolean      exceptSearchSpecialCharacters) {
		StringBuffer noPunctuationWord = new StringBuffer();
		for (int charPos = 0; charPos < string.length(); ++charPos) {
			char charFromString = string.charAt(charPos);
			if ((!charIsPunctuation(charFromString)) || 
				(charIsSearchSpecialCharacter(charFromString) && exceptSearchSpecialCharacters)) {
				noPunctuationWord.append(charFromString);
			}
			else {
				noPunctuationWord.append(' ');
			}
		}
		return noPunctuationWord;
	}

	public static StringBuffer convertToLowerCase(StringBuffer string) {
		StringBuffer lowerCaseWord = new StringBuffer();
		for (int charPos = 0; charPos < string.length(); ++charPos) {
			lowerCaseWord.append(Character.toLowerCase(string.charAt(charPos)));
		}
		return lowerCaseWord;
	}
	
	public static void firstWord(StringBuffer words) {
		for (int charPos = 0; charPos < words.length(); ++charPos) {
			if (charIsLineWhitespace(words.charAt(charPos)) ||
			    charIsPunctuation(words.charAt(charPos))) {
				words.setLength(charPos);
				break;
			}
		}
	}
	
	public static boolean charIsLineWhitespace(char character) {
		boolean returnValue;
		if ( (character == '\t') ||
			 (character == ' ')) {
			returnValue = true;			
		}
		else {
			returnValue = false;
		}
		return returnValue;
	}

	// behaviour similar to J2SE Character.isWhitespace:
	public static boolean isWhitespace(char character) {
		boolean returnValue;
		if ( (character == '\t') ||
			 (character == '\n') || 
			 (character == ' ') || 
			 (character == '\u000C') || 
			 (character == '\u001C') || 
			 (character == '\u001D') || 
			 (character == '\u001E') || 
			 (character == '\u001F')) 
			 {
			returnValue = true;			
		}
		else {
			returnValue = false;
		}
		return returnValue;
	}

	public static StringBuffer filterSuperflousWhitespaces(StringBuffer expression) {
		int expressionLength = expression.length();
		StringBuffer resultExpression = new StringBuffer(expressionLength);
		boolean lastCharWasWhitespace = false;
		for (int charPos = 0; charPos < expressionLength; ++ charPos) {
			char character = expression.charAt(charPos);
			if (charIsLineWhitespace(character)) {
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
					resultExpression.append(character);
				}
				lastCharWasWhitespace = true;
			}
			else {
				resultExpression.append(character);
				lastCharWasWhitespace = false;
			}
		}
		return resultExpression;
	}
	
	public void convertFieldAndLineSeparatorChars(StringBuffer text) {
		int pos = 0;
		int textlength = text.length();
		while (pos < textlength) {
			char currentCharacter = text.charAt(pos);
			if (currentCharacter == '\\') {
				++pos;
				if (pos < textlength) {
					currentCharacter = text.charAt(pos);
					if (currentCharacter == '\\') {
						text.deleteCharAt(pos);
					}
					else if (currentCharacter == 'n') {
						text.setCharAt(pos-1, '\n');
						text.deleteCharAt(pos);
					}
					else if (currentCharacter == 't') {
						text.setCharAt(pos-1, '\t');
						text.deleteCharAt(pos);
					}
				}
				textlength = text.length(); // determine new length in case of deleteCharAt
			}
			else {
				++pos;
			}
		}
	}

	public void replaceFieldAndLineSeparatorChars(StringBuffer text) {
		int pos = 0;
		int textlength = text.length();
		while (pos < textlength) {
			char currentCharacter = text.charAt(pos);
			if (currentCharacter == '\\') {
				++pos;
				if (pos < textlength) {
					currentCharacter = text.charAt(pos);
					if (currentCharacter == '\\') {
						text.deleteCharAt(pos);
					}
					else if (currentCharacter == 'n') {
						text.setCharAt(pos-1, ' ');  // replace by blank
						text.deleteCharAt(pos);
					}
					else if (currentCharacter == 't') {
						text.setCharAt(pos-1, ' '); // replace by blank
						text.deleteCharAt(pos);
					}
				}
				textlength = text.length(); // determine new length in case of deleteCharAt
			}
			else {
				++pos;
			}
		}
	}

	// returns true if character is a search character with a special meaning	
	public static final char wildcardAnySeriesOfCharacter = '*';
	public static final char wildcardAnySingleCharacter = '?';
	public static final char noSearchSubExpressionCharacter = '/';
	static boolean charIsSearchSpecialCharacter(char character) {
		return (character == wildcardAnySeriesOfCharacter) || 
		       (character == wildcardAnySingleCharacter);
	}
	
	public static String removeSuperflousSearchCharacters(String toBeTranslatedWord) {
		StringBuffer result = new StringBuffer(toBeTranslatedWord);
		// remove duplicate noSearchSubExpressionCharacter at begin and end
		while (result.length() >= 2) {
			if ((result.charAt(0) == noSearchSubExpressionCharacter) &&
				(result.charAt(1) == noSearchSubExpressionCharacter)) {
				result.deleteCharAt(0);
			}
			else if ((result.charAt(result.length()-1) == noSearchSubExpressionCharacter) &&
					 (result.charAt(result.length()-2) == noSearchSubExpressionCharacter)) {
				result.deleteCharAt(result.length()-1);
			}
			else {
				break;
			}
		}
		// a single noSearchSubExpressionCharacter is deleted
		if ((result.length() == 1) && (result.charAt(0) == noSearchSubExpressionCharacter))
			result.deleteCharAt(0);

		// to improve response time, consecutive *-wildcards, such as in "un**ly" are removed
		// by one *-wildcard
		int charPos = 0;
		while (result.length() > charPos + 1) {
			if ((result.charAt(charPos) == wildcardAnySeriesOfCharacter) &&
			    (result.charAt(charPos + 1) == wildcardAnySeriesOfCharacter)) {
				result.deleteCharAt(charPos);
			}
			else {
				++charPos;
			}
		}
		return result.toString();
	}


	static boolean charIsPunctuation(char character) {
		String punctuations = "!\"$§$%&/()=?´`\\{}[]^°+*~#'-_.:,;<>|@"; 		
		return punctuations.indexOf(character) != -1;
	}
	
	// this method is needed becaus CLDC 1.0 does not support String.equalIgnoreCase:
	public static boolean stringEqualIgnoreCase(String string1, String string2) {
		boolean stringsAreEqual = false;
		if (string1.length() == string2.length()) {
			int stringLength = string1.length();
			int pos;
			for (pos = 0; pos < stringLength; ++pos) {
				if (Character.toUpperCase(string1.charAt(pos)) != Character.toUpperCase(string2.charAt(pos))) {
					break;
				}
			}
			if (pos == stringLength) {
				stringsAreEqual = true;
			}
		}
		return stringsAreEqual;
	}
	
	public static String[] stringSplit(String stringToSplit, char separatorChar) {
		int numberOfElements = 1;
		int stringToSplitLength = stringToSplit.length();
		// first loop: count the occurences of separatorChar
		for (int charCount = 0; charCount < stringToSplitLength; ++charCount) {
			if (stringToSplit.charAt(charCount) == separatorChar) {
				++numberOfElements;
			}
		}

		// allocate String array
		String[] stringElements = new String[numberOfElements];
		
		// copy string elements to array
		int elementCount = 0;
		int charLastPos = 0;
		for (int charCount = 0; charCount <= stringToSplitLength; ++charCount) {
			if ((charCount == stringToSplitLength) ||
				(stringToSplit.charAt(charCount) == separatorChar)) {
				if (charLastPos < stringToSplitLength) {
					stringElements[elementCount] = stringToSplit.substring(charLastPos, charCount); 
					charLastPos = charCount + 1;
				}
				else {
					stringElements[elementCount] = new String(""); 
				}
				++elementCount;
			}
		}
		return stringElements;
	}
	
	/*
	 * Prperty handling methods
	 */
	
	private Properties dictionaryForMIDsProperties;
	
	public void openProperties(String propertyPath) throws DictionaryException {
		dictionaryForMIDsProperties = new Properties();
		String propertyFileName = propertyPath + DictionaryDataFile.propertyFileName;
		InputStream propertyStream;
		try {
			propertyStream = FileAccessHandler.getDictionaryDataFileISAccess().getInputStream(propertyFileName);
		}
		catch (DictionaryException exception) {
			throw new CouldNotOpenPropertyFileException();
		}
		try {
			dictionaryForMIDsProperties.load(propertyStream);
		}
		catch (IOException exception) {
			throw new DictionaryException("Property file could not be read " + DictionaryDataFile.propertyFileName);
		}
	}

	public void closeProperties() throws DictionaryException {
		dictionaryForMIDsProperties = null;
	}

	public String getDictionaryProperty(String propertyName) {
		return dictionaryForMIDsProperties.getProperty(propertyName);
	}	
		
	public String getDictionaryPropertyString(String propertyName, boolean optional) throws DictionaryException {
		String propertyValue = getDictionaryProperty(propertyName);
		if ((propertyValue == null) && (! optional)) {
			propertyNotFound(propertyName);
		}
		return propertyValue;
	}

	public void propertyNotFound(String propertyName) throws DictionaryException {
		String errorText = "Property " + propertyName + " not found";
		throw new DictionaryException(errorText);
	}
	
	public String getDictionaryPropertyString(String propertyName) throws DictionaryException {
		return getDictionaryPropertyString(propertyName, false);
	}
	
	public int getDictionaryPropertyInt(String propertyName) throws DictionaryException {
		String propertyValue = getDictionaryPropertyString(propertyName);
		int propertyValueInt = Integer.valueOf(propertyValue).intValue();
		return propertyValueInt;
	}

	public char getDictionaryPropertyChar(String propertyName) throws DictionaryException {
		String propertyValue = getDictionaryPropertyString(propertyName);
		char propertyValueChar = getCharValueFromProperty(propertyName, propertyValue);
		return propertyValueChar;
	}

	public char getCharValueFromProperty(String propertyName, String propertyValue)  
	                   throws DictionaryException  {
		char propertyValueChar = '\t';
		if ((! propertyValue.startsWith("\'")) ||
			(! propertyValue.endsWith("\'"))) {
				String errorText = "Property "  + propertyName + " must start with \' and end with \' ";
				log(errorText);
				throw new DictionaryException(errorText);
		}
		String extractedValue = propertyValue.substring(1,propertyValue.length() - 1);
		boolean charPropertyNotWellFormed = false;
		if (extractedValue.startsWith("\\")) {
			if (extractedValue.length() != 2)
				charPropertyNotWellFormed = true;
			else if (extractedValue.endsWith("t"))
				propertyValueChar = '\t';
			else 
				charPropertyNotWellFormed = true;
		}
		else {
			if (extractedValue.length() != 1)
				charPropertyNotWellFormed = true;
			else
				propertyValueChar = extractedValue.charAt(0);
		}
		if (charPropertyNotWellFormed) {
			String errorText = "Property "  + propertyName + " must contain one character or \\t";
			log(errorText);
			throw new DictionaryException(errorText);
		}
		return propertyValueChar;
	}

	public String getDictionaryPropertyStringDefault(String propertyName, String defaultValue) 
				throws DictionaryException {
		String returnValueString;
		String propertyValue = getDictionaryPropertyString(propertyName, true);
		if (propertyValue != null) {
			returnValueString = propertyValue;
		}
		else {
			// property was not set
			returnValueString = defaultValue;
		}
		return returnValueString;
	}

	public char getDictionaryPropertyCharDefault(String propertyName, char defaultValue) 
			throws DictionaryException {
		char returnValueChar;
		String propertyValue = getDictionaryPropertyString(propertyName, true);
		if (propertyValue != null) {
			returnValueChar = getCharValueFromProperty(propertyName, propertyValue);
		}
		else {
			// property was not set
			returnValueChar = defaultValue;
		}
		return returnValueChar;
	}

	public int getDictionaryPropertyIntDefault(String propertyName, int defaultValue) 
    			throws DictionaryException {
		int returnValueInt;
		String propertyValue = getDictionaryPropertyString(propertyName, true);
		if (propertyValue != null) {
			returnValueInt = Integer.valueOf(propertyValue).intValue();
		}
		else {
			// property was not set
			returnValueInt = defaultValue;
		}
		return returnValueInt;
	}
		
	public boolean getDictionaryPropertyBooleanDefault(String propertyName, boolean defaultValue) 
	                         throws DictionaryException {
		boolean returnValueBoolean;
		String propertyValue = getDictionaryPropertyString(propertyName, true);
		if (propertyValue != null) {
			returnValueBoolean = getBooleanFromProperty(propertyName, propertyValue);
		}
		else {
			// property was not set
			returnValueBoolean = defaultValue;
		}
		return returnValueBoolean;
	}
	
	public boolean getBooleanFromProperty(String propertyName, String propertyValue) 
				throws DictionaryException {
		boolean returnValueBoolean;
		if (stringEqualIgnoreCase(propertyValue, "true")) {
			returnValueBoolean = true;
		}
		else if (stringEqualIgnoreCase(propertyValue, "false")) {
			returnValueBoolean = false;
		}
		else {
			String errorText = "Property "  + propertyName + " must be true or false";
			log(errorText);
			throw new DictionaryException(errorText);
		}
		return returnValueBoolean;
	}
	
	// For some devices the charset encoding that is configurated for DictionaryForMIDs
	// needs to be replaced with a device specific string. This is done with a call to
	// getDeviceCharEncoding. At initialisation determineCharEncoding needs to be called
	public void determineCharEncoding() throws DictionaryException {
		// default is to do nothing
	}
	
	public String getDeviceCharEncoding(String charEncoding) {
		// default is to return the charEncoding unchanged
		return charEncoding;
	}
	
}
