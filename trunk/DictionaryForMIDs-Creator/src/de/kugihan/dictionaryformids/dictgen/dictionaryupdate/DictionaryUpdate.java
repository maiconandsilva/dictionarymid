/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dataaccess.DictionaryUpdateIF;
import de.kugihan.dictionaryformids.dictgen.DictionaryGeneration;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;
import java.util.Vector;

public class DictionaryUpdate 
	implements DictionaryUpdateIF {

	protected DictionaryDataFile dictionary;
	public DictionaryDataFile getDictionary() {
		return dictionary;
	}
	public void setDictionary(DictionaryDataFile dictionaryParam) {
		dictionary = dictionaryParam;
	}

	protected int indexLanguage;  // index to DictionaryDataFile.supportedLanguages

	final String delimiterStart = "{{";
	final String delimiterEnd = "}}";
	
	public String updateDictionaryExpression(String dictionaryExpression) 
				throws DictionaryException {
		String returnString;
		if (dictionary.dictionaryGenerationOmitParFromIndex) {
			boolean replacementDone;
			StringBuilder expressionUpdated = new StringBuilder(dictionaryExpression);
			do {
				replacementDone = false;
				// remove all delimiterStart and delimiterEnd
				int posDelimiterStart = expressionUpdated.toString().indexOf(delimiterStart);
				if (posDelimiterStart >= 0) {
					expressionUpdated.delete(posDelimiterStart, posDelimiterStart + delimiterStart.length());
					replacementDone = true;
				}
				else {
					int posDelimiterEnd = expressionUpdated.toString().indexOf(delimiterEnd);
					if (posDelimiterEnd >= 0) {
						expressionUpdated.delete(posDelimiterEnd, posDelimiterEnd + delimiterEnd.length());
						replacementDone = true;					
					}
				}
			}
			while (replacementDone);
			returnString = expressionUpdated.toString();
		}
		else {
			// otherwise do nothing
			returnString = dictionaryExpression;
		}
		return returnString;
	}
	
	public String removeNonSearchParts(String expression)  
				throws DictionaryException {
		String returnString;
		if (dictionary.dictionaryGenerationOmitParFromIndex) {
			int nestingLevel = 0;
			int posDelimiterStartAtNestingLevel0 = 0;
			int posBehindLastDelimiter = 0;
			int posDelimiterStart;
			int posDelimiterEnd;
			StringBuilder expressionUpdated = new StringBuilder(expression);
			do {
				if ((posBehindLastDelimiter >= expressionUpdated.length()) && (nestingLevel > 0)) {
						throw new DictionaryException("Number of " + delimiterStart + " does not match " +  delimiterEnd + " at end of expression");
				}
				posDelimiterEnd = expressionUpdated.toString().indexOf(delimiterEnd, posBehindLastDelimiter);
				posDelimiterStart = expressionUpdated.toString().indexOf(delimiterStart, posBehindLastDelimiter);
				if ((posDelimiterStart == -1) && (posDelimiterEnd == -1)) {
					if (nestingLevel > 0) {
						throw new DictionaryException("Number of " + delimiterStart + " does not match " +  delimiterEnd + " at end of expression");
					}
				}
				else if (posDelimiterEnd == -1) {
						throw new DictionaryException(delimiterEnd + " without previous " +  delimiterStart);					
				}
				else if ((posDelimiterEnd < posDelimiterStart) || (posDelimiterStart == -1)) {
					--nestingLevel;
					posBehindLastDelimiter = posDelimiterEnd + delimiterEnd.length();
					if (nestingLevel == 0) {
						// remove everything between delimiterStart and delimiterEnd
						expressionUpdated.delete(posDelimiterStartAtNestingLevel0, posBehindLastDelimiter);
						// search with updated string
						posDelimiterStartAtNestingLevel0 = 0;
						posBehindLastDelimiter = 0;
					}
					else if (nestingLevel < 0) {
						throw new DictionaryException(delimiterEnd + " without previous " +  delimiterStart);
					}
				}
				else  { // (posDelimiterStart != -1) && (posDelimiterEnd > posDelimiterStart)
					if (nestingLevel == 0) {
						posDelimiterStartAtNestingLevel0 = posDelimiterStart;
					}
					++nestingLevel;
					posBehindLastDelimiter = posDelimiterStart + delimiterStart.length();
				}
			}
			while (! ((posDelimiterStart == -1) && (posDelimiterEnd == -1)));
			returnString = expressionUpdated.toString();
		}
		else {
			// otherwise do nothing
			returnString = expression;
		}
		return returnString;
	}

	public void updateKeyWordVector(Vector keyWordVector)  
				throws DictionaryException {
		// default is to do nothing
	}

	public String createKeyWordsExpression(String expression)  
				throws DictionaryException {
		// default is to use the result from updateDictionaryExpression, remove content delimiters,
		// remove field/line separator characters and remove non-search parts
		String keyWordsExpression;
		if (dictionary.dictionaryGenerationOmitParFromIndex) {
			keyWordsExpression = expression;
		}
		else {
			keyWordsExpression = updateDictionaryExpression(expression);
		}
		String keyWordsExpressionWithoutContentDelimiters = 
     		DictionaryGeneration.removeContentDelimiters(keyWordsExpression, indexLanguage);
		StringBuffer keyWordsExpressionWithoutSeparatorChars = new StringBuffer(keyWordsExpressionWithoutContentDelimiters); 
		Util.getUtil().replaceFieldAndLineSeparatorChars(keyWordsExpressionWithoutSeparatorChars);
		String keyWordsCleanedUp = removeNonSearchParts(keyWordsExpressionWithoutSeparatorChars.toString());
		return keyWordsCleanedUp;
	}

	// Creates a Vector of IndexKeyWordEntry from the expression.
	// expressionSplitString is the value from the corresponding property in DictionaryForMIDs.properties
	public Vector createKeyWordVector(String expression, String expressionSplitString)  
				throws DictionaryException {
		String keyWordsExpression = createKeyWordsExpression(expression);
		int posStartKeyWords = 0;
		int posEndKeyWords;
		Vector keyWordVector = new Vector();
		do {
			if (expressionSplitString != null) {
				posEndKeyWords  = keyWordsExpression.indexOf(expressionSplitString, posStartKeyWords);
				if (posEndKeyWords == -1)
					posEndKeyWords = keyWordsExpression.length();
			}
			else {
				posEndKeyWords = keyWordsExpression.length();
			}
			String keyWordsSplitUp = keyWordsExpression.substring(posStartKeyWords, posEndKeyWords); 
			addKeyWordsSplitUpToKeyWordVector(keyWordsSplitUp, keyWordVector);
			// future enhancement: create property value to alternatively use splitKeyWords as alternative
			// method to create keyWordVector
			// Vector keyWordVector = Util.splitKeyWords(keyWordsCleanedUp);
			posStartKeyWords = posEndKeyWords + 1;
		}
		while (posEndKeyWords < keyWordsExpression.length());
		return keyWordVector;
	}

	public void addKeyWordsSplitUpToKeyWordVector(String keyWordsSplitUp,
			                                      Vector keyWordVector)
			throws DictionaryException {
			DictionaryUpdateLib.addKeyWordExpressions(keyWordsSplitUp, keyWordVector);
	}
	
	public void setIndexLanguage(int indexLanguageParam) {
		indexLanguage = indexLanguageParam;
	}

}
