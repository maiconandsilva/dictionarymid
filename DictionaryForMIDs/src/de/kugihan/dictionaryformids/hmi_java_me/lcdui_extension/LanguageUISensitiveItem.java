/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension;

import de.kugihan.dictionaryformids.general.DictionaryException;

// Items that implement this interface display their labels with
// LanguageUI. The label content is stored as UIItemKeys value.
public interface LanguageUISensitiveItem {	
	// the method redisplayLabels is called when the labels must
	// be displayed again (by use of LanguageUI)
	void redisplayLabels() throws DictionaryException;
}
