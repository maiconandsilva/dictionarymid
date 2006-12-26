/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension;

import javax.microedition.lcdui.TextField;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.UIDisplayTextItem;

public class DfMTextField extends TextField implements LanguageUISensitiveItem  {

	UIDisplayTextItem languageUILabel = null;
	static final String emptyLabel = "";

	public DfMTextField(UIDisplayTextItem languageUILabelParam, String text, int maxSize, int constraints)
									throws DictionaryException {
		super(emptyLabel, text, maxSize, constraints);
		setLabel(languageUILabelParam);
	}

	public void setLabel(UIDisplayTextItem languageUILabelParam) throws DictionaryException {
		languageUILabel = languageUILabelParam;
		redisplayLabels();
	}

	public void redisplayLabels() throws DictionaryException {
		if (languageUILabel != null)
			LcdUILib.setLanguageUIItemLabel(this, languageUILabel);
		else 
			super.setLabel("");
	}

}
