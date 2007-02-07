/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;
import java.util.Vector;

import de.kugihan.dictionaryformids.dataaccess.DictionaryUpdateIF;
import de.kugihan.dictionaryformids.dictgen.DictionaryGeneration;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;

public class DictionaryUpdate 
	implements DictionaryUpdateIF {

	protected int indexLanguage;  // index to DictionaryDataFile.supportedLanguages
	
	public String updateDictionaryExpression(String dictionaryExpression) 
				throws DictionaryException {
		// default is to do nothing
		return dictionaryExpression;
	}
	
	public String removeNonSearchParts(String expression)  
				throws DictionaryException {
		// default is to do nothing
		return expression;
	}

	public void updateKeyWordVector(Vector keyWordVector)  
				throws DictionaryException {
		// default is to do nothing
	}

	public String createKeyWordsExpression(String expression)  
				throws DictionaryException {
		// default is to use the result from updateDictionaryExpression, remove content delimiters,
		// remove field/line separator characters and remove non-search parts
		String keyWordsExpression = updateDictionaryExpression(expression);
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
			DictionaryUpdateLib.addKeyWordExpressions(keyWordsSplitUp, keyWordVector);
			// todo: create property value to alternatively use splitKeyWords as alternative method to create
			// keyWordVector
			// Vector keyWordVector = Util.splitKeyWords(keyWordsCleanedUp);
			posStartKeyWords = posEndKeyWords + 1;
		}
		while (posEndKeyWords < keyWordsExpression.length());
		return keyWordVector;
	}
	
	public void setIndexLanguage(int indexLanguageParam) {
		indexLanguage = indexLanguageParam;
	}

}