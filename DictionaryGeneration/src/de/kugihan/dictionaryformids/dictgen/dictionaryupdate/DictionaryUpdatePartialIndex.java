/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (
)

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import de.kugihan.dictionaryformids.dictgen.DictionaryGeneration;
import de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEngDef;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;

public class DictionaryUpdatePartialIndex extends DictionaryUpdate {
	
	final String delimiterStart = "{{";
	final String delimiterEnd = "}}";

	public String createKeyWordsExpression(String expression)  
		throws DictionaryException {
		String keyWordsExpressionWithoutContentDelimiters = 
     		DictionaryGeneration.removeContentDelimiters(expression, indexLanguage);
		StringBuffer keyWordsExpressionWithoutSeparatorChars = new StringBuffer(keyWordsExpressionWithoutContentDelimiters); 
		Util.getUtil().replaceFieldAndLineSeparatorChars(keyWordsExpressionWithoutSeparatorChars);
		String keyWordsCleanedUp = removeNonSearchParts(keyWordsExpressionWithoutSeparatorChars.toString());
		return keyWordsCleanedUp;
	}
	
	public String updateDictionaryExpression(String dictionaryExpression) 
				throws DictionaryException {
		boolean replacementDone;
		StringBuffer expressionUpdated = new StringBuffer(dictionaryExpression);
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
		String returnString = super.updateDictionaryExpression(expressionUpdated.toString());
		return returnString;
	}
		
	public String removeNonSearchParts(String expression) 
			throws DictionaryException {
		boolean replacementDone;
		StringBuffer expressionUpdated = new StringBuffer(expression);
		do {
			replacementDone = false;
			// remove everything between delimiterStart and delimiterEnd
			int posDelimiterStart = expressionUpdated.toString().indexOf(delimiterStart);
			int posDelimiterEnd = expressionUpdated.toString().indexOf(delimiterEnd);
			if ((posDelimiterStart >= 0) && (posDelimiterEnd > posDelimiterStart)) {
				expressionUpdated.delete(posDelimiterStart, posDelimiterEnd + delimiterEnd.length());
				replacementDone = true;
			}
		}
		while (replacementDone);
		String returnString = super.removeNonSearchParts(expressionUpdated.toString());
		return returnString;
	}
}
