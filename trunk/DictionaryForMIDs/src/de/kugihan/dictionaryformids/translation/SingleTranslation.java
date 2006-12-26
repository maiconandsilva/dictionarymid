/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.translation;

public class SingleTranslation {
	public StringBuffer fromText;
	public StringBuffer toText;
	
	public SingleTranslation(StringBuffer fromTextParam, StringBuffer toTextParam) {
		fromText = fromTextParam;
		toText = toTextParam;
	}
}
