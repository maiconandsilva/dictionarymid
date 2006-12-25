/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;

public class DictionaryUpdateEDICTJpn extends DictionaryUpdate {

	// Creates the keyWordVector:
	// - if there is a square bracket [ then add the content between square brackets to the index (hiragana) plus add the content
	//   before the square bracket (kanji)
	// - if there is no square bracket add the content directly (kanji or hiragana or katakana)
	//
	// Note: expressionSplitString is ignored
	public Vector createKeyWordVector(String expression, String expressionSplitString) {
		
		Vector keyWordVector = new Vector();
		
		int startBracket = expression.indexOf('[');
		int endBracket = expression.toString().indexOf(']');

		String indexExpression;
		if ((startBracket != -1) && (endBracket > startBracket)) {
			indexExpression = expression.substring(startBracket + 1, endBracket);
			DictionaryUpdateLib.addKeyWordExpressions(indexExpression, keyWordVector);
			indexExpression = expression.substring(0, startBracket);
		}
		else {
			indexExpression = expression;
		}
		DictionaryUpdateLib.addKeyWordExpressions(indexExpression, keyWordVector);

		return keyWordVector;
	}	
	
}
