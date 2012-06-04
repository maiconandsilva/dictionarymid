/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (
)

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;

import de.kugihan.dictionaryformids.dictgen.IndexKeyWordEntry;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionaryUpdateEngDef extends DictionaryUpdate {

	public void updateKeyWordVector(Vector keyWordVector) 
				throws DictionaryException {
		super.updateKeyWordVector(keyWordVector);
		int elementCount = 0;
		if (keyWordVector.size() > 1) {
			do {
				String keyWord = ((IndexKeyWordEntry) keyWordVector.elementAt(elementCount)).keyWord;
				if (keyWord.equalsIgnoreCase("the") ||
					keyWord.equalsIgnoreCase("a")  ||
					keyWord.equalsIgnoreCase("to")  ||
					keyWord.equalsIgnoreCase("that")  ||
					keyWord.equalsIgnoreCase("of")  ||
					keyWord.equalsIgnoreCase("on")  ||
					keyWord.equalsIgnoreCase("or")  ||
					keyWord.equalsIgnoreCase("out")  ||
					keyWord.equalsIgnoreCase("this")  ) {
					keyWordVector.removeElementAt(elementCount);
				}
				else {
					++elementCount;
				}
			}
			while (elementCount < keyWordVector.size());
		}
	}

	public String removeNonSearchParts(String expression) {
		return DictionaryUpdateLib.removeBrackets(expression);
	}
}
