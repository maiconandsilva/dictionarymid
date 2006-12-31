/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_j2me.mainform.MainForm;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.UIDisplayTextItem;

public class DfMAlert extends Alert {

	public DfMAlert(UIDisplayTextItem languageUITitle, UIDisplayTextItem languageUIAlertText, Image alertImage, AlertType alertType) 
				throws DictionaryException {
		super(languageUITitle.getItemDisplayText(),
			  languageUIAlertText.getItemDisplayText(),
			  alertImage,
			  alertType);
	}

	public DfMAlert(UIDisplayTextItem languageUITitle, UIDisplayTextItem languageUIAlertText, AlertType alertType) 
				throws DictionaryException {
		super(languageUITitle.getItemDisplayText(),
			  languageUIAlertText.getItemDisplayText(),
			  languageUIAlertText.getIcon(ResourceHandler.getResourceHandlerObj().iconSizeGroupBig,
           		   						  MainForm.applicationMainForm.display.getBestImageHeight(Display.ALERT), 
					                      MainForm.applicationMainForm.display.getBestImageWidth(Display.ALERT)),
			  alertType);
	}
}
