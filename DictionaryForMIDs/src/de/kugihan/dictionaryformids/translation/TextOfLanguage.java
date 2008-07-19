/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2008 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.translation;

public class TextOfLanguage {
	String text;
	int	   languageIndex;  // index of the language where the text comes from
	
	public int getLanguageIndex() {
		return languageIndex;
	}

	public String getText() {
		return text;
	}

	public TextOfLanguage(String text, int languageIndex) {
		super();
		this.text = text;
		this.languageIndex = languageIndex;
	}

}
