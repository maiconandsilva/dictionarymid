/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension;

import javax.microedition.lcdui.Item;

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.*;

public class LcdUILib {
	
	public static void setLanguageUIItemLabel(Item lcdUIItem,
											  UIDisplayTextItem languageUIItem) throws DictionaryException {
		lcdUIItem.setLabel(languageUIItem.getItemDisplayText());
	}

}
