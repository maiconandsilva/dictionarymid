/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;

import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionaryUpdateFraDef extends DictionaryUpdate {

	public String updateDictionaryExpression(String dictionaryExpression) throws DictionaryException {
		return DictionaryUpdateLib.setContentPronounciation(super.updateDictionaryExpression(dictionaryExpression), 1);
	}
	public String removeNonSearchParts(String expression) {
		return DictionaryUpdateLib.removeBrackets(expression);
	}		
}
