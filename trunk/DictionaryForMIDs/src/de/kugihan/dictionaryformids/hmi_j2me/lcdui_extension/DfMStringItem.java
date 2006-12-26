/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension;

import javax.microedition.lcdui.StringItem;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.UIDisplayTextItem;

public class DfMStringItem extends StringItem implements LanguageUISensitiveItem {

	UIDisplayTextItem languageUILabel = null;

	public DfMStringItem() {
		super(null, null);
	}
	
	public DfMStringItem(String text) {
		super(null, text);
	}
	
	public DfMStringItem(UIDisplayTextItem languageUILabelParam) throws DictionaryException {
		super(languageUILabelParam.getItemDisplayText(), null);
		languageUILabel = languageUILabelParam;
	}
	
	public DfMStringItem(UIDisplayTextItem languageUILabelParam, String text) throws DictionaryException {
		super(languageUILabelParam.getItemDisplayText(), text);
		languageUILabel = languageUILabelParam;
	}

	public void setLabel(UIDisplayTextItem languageUILabelParam) throws DictionaryException {
		languageUILabel = languageUILabelParam;
		super.setLabel(languageUILabel.getItemDisplayText());
	}
	
	public void redisplayLabels() throws DictionaryException {
		if (languageUILabel != null) {
			LcdUILib.setLanguageUIItemLabel(this, languageUILabel);
		}
	}

}
