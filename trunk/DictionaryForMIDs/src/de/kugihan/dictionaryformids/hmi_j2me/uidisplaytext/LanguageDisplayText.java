/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Vu Van Quynh (quynh2003hp@yahoo.com) and Gert Nuber
(Gert.Nuber@gmx.net)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext;

public class LanguageDisplayText {
	public String 	  languageIdentifier; // title of the language
	public String     languageCode;       // ISO 639 code for this language
	public String []  languageUIItems;    // display items for this language
	
	LanguageDisplayText(String    languageIdentifierParam,
					    String    languageCodeParam,
			            String [] languageUIItemsParam) {
		languageIdentifier = languageIdentifierParam;
		languageCode = languageCodeParam;
		languageUIItems = languageUIItemsParam;
	}
}
