/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;

import de.kugihan.dictionaryformids.hmi_java_me.mainform.MainForm;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.UIDisplayTextItem;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class DfMChoiceGroup extends ChoiceGroup implements
		LanguageUISensitiveItem {

	UIDisplayTextItem     languageUILabel = null;
	UIDisplayTextItem[]   languageUIElements = null;  // remains null if languageUIElemens are not used

	// DfMChoiceGroup with String-type elements
	public DfMChoiceGroup(UIDisplayTextItem languageUILabelParam,
            			  int 				choiceType,
            			  String[] 			StringElements,
            			  Image[]           imageElements) throws DictionaryException {
		super(languageUILabelParam.getItemDisplayText(), choiceType, StringElements, imageElements);
		languageUILabel = languageUILabelParam;
	}

	// DfMChoiceGroup with UIDisplayTextItem-type elements
	public DfMChoiceGroup(UIDisplayTextItem    languageUILabelParam,
                          int 				   choiceType,
                          UIDisplayTextItem [] languageUIElementsParam) throws DictionaryException {
		super(languageUILabelParam.getItemDisplayText(), choiceType);
		languageUILabel = languageUILabelParam;
		languageUIElements = languageUIElementsParam;
		if (languageUIElements != null ) {
			for (int elementCount = 0; elementCount < languageUIElements.length; ++elementCount) {
				append(languageUIElements[elementCount].getItemDisplayText(), getChoiceGroupIcon(languageUIElements[elementCount]));
			}
		}
	}
	
	// setting of one single element
	public void set(int elementNum, UIDisplayTextItem languageUIElement) throws DictionaryException {
		languageUIElements[elementNum] = languageUIElement; 
		super.set(elementNum, 
				  languageUIElement.getItemDisplayText(), 
				  getChoiceGroupIcon(languageUIElement));
	}
	
	// appending of one single element
	public void append(UIDisplayTextItem languageUIElement) throws DictionaryException {
		super.append(languageUIElement.getItemDisplayText(), 
				     getChoiceGroupIcon(languageUIElement));
		languageUIElements[super.size()-1] = languageUIElement; 
	}
	
	// setting of all elements
	public void setAll(UIDisplayTextItem[] languageUIElementsParam) throws DictionaryException {
		languageUIElements = new UIDisplayTextItem[languageUIElementsParam.length];
		super.deleteAll();
		for (int elementCount = 0; elementCount < languageUIElementsParam.length; ++ elementCount) {
			append(languageUIElementsParam[elementCount]);
		}
	}
	
	// new display of the labels
	public void redisplayLabels() throws DictionaryException {
		LcdUILib.setLanguageUIItemLabel(this, languageUILabel);
		if (languageUIElements != null) {
			for (int elementCount = 0; elementCount < languageUIElements.length; ++elementCount) {
				set(elementCount, languageUIElements[elementCount]);
			}
		}
	}

	// getting the icon for an element
	Image getChoiceGroupIcon(UIDisplayTextItem uiDisplayTextItem) throws DictionaryException {
		Image imagePart;
		imagePart = uiDisplayTextItem.
		               getIcon(ResourceHandler.getResourceHandlerObj().iconSizeGroupSmall,
		            		   MainForm.applicationMainForm.display.getBestImageHeight(Display.CHOICE_GROUP_ELEMENT), 
		            		   MainForm.applicationMainForm.display.getBestImageWidth(Display.CHOICE_GROUP_ELEMENT));
		return imagePart;
	}
}
