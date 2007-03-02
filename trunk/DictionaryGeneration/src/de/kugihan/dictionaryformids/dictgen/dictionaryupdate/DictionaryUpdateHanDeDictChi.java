/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de), Erik Peterson (http://www.mandarintools.com/)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;

public class DictionaryUpdateHanDeDictChi extends DictionaryUpdate {

	/*The input looks like
	 * 學生 学生 [01xue2 sheng1]	/Schüler {{(S)}}/Student/
	 * [01 is the start content delimiter 
	 	
	Creates the keyWordVector for
	/a) the pronounciation part which is in square brackets: 
	//    - one time with tone numbers 
	//    - one time without tone numbers and without spaces
	/ b) for the Chinese expression*/
	
	public Vector createKeyWordVector(String expression, String expressionSplitString) {
		
		Vector keyWordVector = new Vector();
		
		int startBracket = expression.indexOf('[');
		int endBracket = expression.toString().indexOf(']');

		String chineseExpression;
		if ((startBracket != -1) && (endBracket > startBracket)) {
			String pronounciationExpression = expression.substring(startBracket + 3, endBracket);
			chineseExpression = expression.substring(0, startBracket);
		//creates the pinyin with tone-numbers index entry
		//	DictionaryUpdateLib.addKeyWordExpressions(pronounciationExpression, keyWordVector);
			String pronounciationWithoutNumbers = removeNumbers(pronounciationExpression);
			DictionaryUpdateLib.addKeyWordExpressions(pronounciationWithoutNumbers, keyWordVector);
		}
		else {
			chineseExpression = expression;
		}
		DictionaryUpdateLib.addKeyWordExpressions(chineseExpression, keyWordVector);

		return keyWordVector;
	}
	
	protected String removeNumbers(String expression) {
		StringBuffer output = new StringBuffer();
		for (int pos = 0; pos < expression.length(); ++pos) {
			char character = expression.charAt(pos);
			if ((! Character.isDigit(character)) && (! Character.isWhitespace(character))) {
				output.append(character);
			}
		}
		return output.toString();
	}
	
}
