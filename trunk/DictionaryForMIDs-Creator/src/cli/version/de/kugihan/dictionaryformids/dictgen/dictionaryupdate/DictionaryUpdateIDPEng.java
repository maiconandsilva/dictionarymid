/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package cli.version.de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;

import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionaryUpdateIDPEng extends DictionaryUpdateIDP {
	DictionaryUpdateEngDef dictionaryUpdateEngDefObj;
	
	public DictionaryUpdateIDPEng() {
		super();
		dictionaryUpdateEngDefObj = new DictionaryUpdateEngDef();
	}

	public void updateKeyWordVector(Vector keyWordVector) 
				throws DictionaryException {
		super.updateKeyWordVector(keyWordVector);
		dictionaryUpdateEngDefObj.updateKeyWordVector(keyWordVector);
	}
}
