/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;

import de.kugihan.dictionaryformids.hmi_j2me.mainform.MainForm;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.UIDisplayTextItem;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class DfMChoiceGroup extends ChoiceGroup implements
		LanguageUISensitiveItem {

	UIDisplayTextItem     languageUILabel = null;
	UIDisplayTextItem[]   languageUIElements = null;  // remains null if languageUIElemens are not used
	
	public DfMChoiceGroup(UIDisplayTextItem languageUILabelParam,
            			  int 				choiceType,
            			  String[] 			StringElements,
            			  Image[]           imageElements) throws DictionaryException {
		super(languageUILabelParam.getItemDisplayText(), choiceType, StringElements, imageElements);
		languageUILabel = languageUILabelParam;
	}

	public DfMChoiceGroup(UIDisplayTextItem    languageUILabelParam,
                          int 				   choiceType,
                          UIDisplayTextItem [] languageUIElementsParam) throws DictionaryException {
		super(languageUILabelParam.getItemDisplayText(), choiceType);
		languageUILabel = languageUILabelParam;
		languageUIElements = languageUIElementsParam;
		for (int elementCount = 0; elementCount < languageUIElements.length; ++elementCount) {
			Image imagePart;
				imagePart = languageUIElements[elementCount].
				               getIcon(MainForm.applicationMainForm.display.getBestImageHeight(Display.CHOICE_GROUP_ELEMENT), 
				            		   MainForm.applicationMainForm.display.getBestImageWidth(Display.CHOICE_GROUP_ELEMENT)); 
			append(languageUIElements[elementCount].getItemDisplayText(), imagePart);
		}
	}

	public void set(int elementNum, UIDisplayTextItem languageUIElement) throws DictionaryException {
		languageUIElements[elementNum] = languageUIElement; 
		Image imagePart = languageUIElement.
							getIcon(MainForm.applicationMainForm.display.getBestImageHeight(Display.CHOICE_GROUP_ELEMENT), 
									MainForm.applicationMainForm.display.getBestImageWidth(Display.CHOICE_GROUP_ELEMENT)); 
		super.set(elementNum, 
				  languageUIElement.getItemDisplayText(), 
				  imagePart);
	}
	
	public void redisplayLabels() throws DictionaryException {
		LcdUILib.setLanguageUIItemLabel(this, languageUILabel);
		if (languageUIElements != null) {
			for (int elementCount = 0; elementCount < languageUIElements.length; ++elementCount) {
				set(elementCount, languageUIElements[elementCount]);
			}
		}
	}

}
