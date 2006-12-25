/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

public class DictionaryUpdateMuellerRus extends DictionaryUpdate {

	public String removeNonSearchParts(String expression) {
		return DictionaryUpdateLib.removeBrackets(expression);
	}

}
