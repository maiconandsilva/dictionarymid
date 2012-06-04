/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionaryUpdateIDPLat extends DictionaryUpdateIDP {
	
	public String updateDictionaryExpression(String idpString) 
			throws DictionaryException {
		String updatedString = super.updateDictionaryExpression(idpString);
		// remove the dot at the and if there is any
		if (updatedString.endsWith("."))
			updatedString = updatedString.substring(0,updatedString.length() -1);
		return updatedString;
	}
}
