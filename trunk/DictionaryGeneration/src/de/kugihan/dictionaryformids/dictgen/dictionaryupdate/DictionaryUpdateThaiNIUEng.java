/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (
)

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionaryUpdateThaiNIUEng extends DictionaryUpdatePartialIndex {
	
	DictionaryUpdateEngDef englishDefaultUpdateClass = new DictionaryUpdateEngDef();
	
	// do the updateKeyWordVector from DictionaryUpdateEngDef 
	public void updateKeyWordVector(Vector keyWordVector) 
			throws DictionaryException {
		englishDefaultUpdateClass.updateKeyWordVector(keyWordVector);
	}
	
}
