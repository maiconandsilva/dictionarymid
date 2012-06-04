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

public class DictionaryUpdateEpo extends DictionaryUpdate {

	public void updateKeyWordVector(Vector keyWordVector) 
				throws DictionaryException {

		//System.err.println("keyWordVector = " + keyWordVector);
		super.updateKeyWordVector(keyWordVector);
		int elementCount = 0;
		if (keyWordVector.size() > 1) {
			do {
				String keyWord = ((IndexKeyWordEntry) keyWordVector.elementAt(elementCount)).keyWord;
				String orgKeyWord  = keyWord;
				System.err.println("keyWord = " + keyWord);
				//if (keyWord.contains(" ")) keyWord=keyWord.substring(0, keyWord.indexOf(' '));
				if (keyWord.contains(" ")  ||
/*					keyWord.equalsIgnoreCase("o") ||
					keyWord.equalsIgnoreCase("oj")  ||
					keyWord.equalsIgnoreCase("a")  ||
					keyWord.equalsIgnoreCase("i")  ||
					keyWord.equalsIgnoreCase("de")  ||
					keyWord.equalsIgnoreCase("ad")  ||
					keyWord.equalsIgnoreCase("aĉ")  ||
					keyWord.equalsIgnoreCase("ac")  ||
					//keyWord.equalsIgnoreCase("ek")  ||
					keyWord.equalsIgnoreCase("la")  ||
					keyWord.equalsIgnoreCase("al")  ||
					keyWord.equalsIgnoreCase("aĵ")  ||
					keyWord.equalsIgnoreCase("tiu")  ||
					keyWord.equalsIgnoreCase("tio")  ||
					keyWord.equalsIgnoreCase("kaj")  ||
					keyWord.equalsIgnoreCase("aŭ")  ||
					keyWord.equalsIgnoreCase("mi")  ||
					keyWord.equalsIgnoreCase("min")  ||
					keyWord.equalsIgnoreCase("li")  ||
					keyWord.equalsIgnoreCase("lin")  ||
					keyWord.equalsIgnoreCase("ne")  ||
					keyWord.equalsIgnoreCase("ĝi")  ||
					keyWord.equalsIgnoreCase("ĝin")  ||
					keyWord.equalsIgnoreCase("ĉi")   || */
					false) {
					keyWordVector.removeElementAt(elementCount);

//				System.err.println("orgKeyWord  = " + orgKeyWord );
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
