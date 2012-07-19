/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/
package cli.version.de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionaryUpdateEngDefPronunciation extends DictionaryUpdateEngDef {

	public String updateDictionaryExpression(String dictionaryExpression) throws DictionaryException {
		return DictionaryUpdateLib.setContentPronounciation(super.updateDictionaryExpression(dictionaryExpression), 1);
	}

}
