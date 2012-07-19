/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/
package cli.version.de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;

import de.kugihan.dictionaryformids.dictgen.IndexKeyWordEntry;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionaryUpdateFreedictEngHin extends DictionaryUpdateEngDef {
	
	public String removeNonSearchParts(String expression) {
		return DictionaryUpdateLib.removeBrackets(expression);
	}
	
	public void updateKeyWordVector(Vector keyWordVector) 
				throws DictionaryException {
		super.updateKeyWordVector(keyWordVector);
		int elementCount = 0;
		if (keyWordVector.size() > 1) {
			do {
				String keyWord = ((IndexKeyWordEntry) keyWordVector.elementAt(elementCount)).keyWord;
				if (keyWord.equalsIgnoreCase("n") ||
					keyWord.equalsIgnoreCase("abbr")  ||
					keyWord.equalsIgnoreCase("v")  ||
					keyWord.equalsIgnoreCase("vi")  ||
					keyWord.equalsIgnoreCase("vp")  ||
					keyWord.equalsIgnoreCase("vt")  ||
					keyWord.equalsIgnoreCase("vti")  ) {
					keyWordVector.removeElementAt(elementCount);
				}
				else {
					++elementCount;
				}
			}
			while (elementCount < keyWordVector.size());
		}
	}

}
