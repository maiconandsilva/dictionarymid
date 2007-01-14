/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006   Vu Van Quynh (quynh2003hp@yahoo.com) and Gert Nuber
(dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/


package de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext;

import java.util.Hashtable;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_j2me.DictionarySettings;

public class LanguageUI {
	
	static private LanguageUI instanceLanguageUI = null;

	private String[] languageTitle;
	private String[] languageCode;
	private int uiLanguage;
			
	public static LanguageUI getUI() {
		if (instanceLanguageUI == null)
			instanceLanguageUI = new LanguageUI();
		return instanceLanguageUI;
	}
	
	public void initValue() {
		// fill in the language titles and language codes
		languageTitle = new String[getMaxLanguage()];
		languageCode  = new String[getMaxLanguage()];
		for (int languageCount = 0;
		     languageCount < getMaxLanguage();
		     ++languageCount) {
			languageTitle[languageCount] = 
						UIDisplayTextContents.availableDisplayTexts[languageCount].languageIdentifier;
			languageCode[languageCount] = 
				UIDisplayTextContents.availableDisplayTexts[languageCount].languageCode;
		}
	}
	
	public int getMaxLanguage(){
		return UIDisplayTextContents.availableDisplayTexts.length;
	}
	
	public String[] getLanguageTitle(){
		return languageTitle;
	}
	
	public String[] getLanguageCode(){
		return languageCode;
	}
	
	public void setUILanguage(int languageNumber) {
		// sets the selected language
		uiLanguage = languageNumber;
	}
	
	public int getUILanguage() {
		// sets the selected language
		return uiLanguage;
	}
	
	
	/*
	 * General constants  
	 */
	public final String uiDisplayTextItemReference = "UIDisplayTextItems."; // Parameters of a UIDisplayTextItem with this  
    // prefix refer to another display text item 
	public final String uiDisplayTextItemPrefixLanguage = "Language";


	/*
	 * Creation of UIDisplayTextItem objects and searching of UIDisplayTextItem  
	 */
	private Hashtable uiDisplayTextItemTable = new Hashtable(UIDisplayTextContents.totalNumberItemsInLanguage);
	
	// Creates a new translatable UIDisplayTextItem object as a clone from an template    
	public UIDisplayTextItem createUIDisplayTextItemFromTemplate(String idNewUIDisplayTextItem, String idTemplate)
			throws DictionaryException {
		UIDisplayTextItem template = existingUIDisplayTextItem(idTemplate, true);
		UIDisplayTextItem newUIDisplayTextItem = createNewUIDisplayTextItem(idNewUIDisplayTextItem, 
				                                                            template.keyValue,
				                                                            template.getNumberOfParameters());
		return newUIDisplayTextItem;
	}

	// Creates a new translatable UIDisplayTextItem object  
	UIDisplayTextItem createNewUIDisplayTextItem(String id, int keyValue, int numberOfParameters) {
		UIDisplayTextItem newUIDisplayTextItem = new UIDisplayTextItem(id, keyValue, numberOfParameters);
		addNewUIDisplayTextItem(id, newUIDisplayTextItem);
		return newUIDisplayTextItem;
	}
	
	// Creates a new non-translatable UIDisplayTextItem object  
	UIDisplayTextItem createNewUIDisplayTextItem(String id, String fallbackTranslationText) {
		UIDisplayTextItem newUIDisplayTextItem = new UIDisplayTextItem(id, fallbackTranslationText, 0);
		addNewUIDisplayTextItem(id, newUIDisplayTextItem);
		return newUIDisplayTextItem;
	}

	protected void addNewUIDisplayTextItem(String id, UIDisplayTextItem newUIDisplayTextItem) {
		uiDisplayTextItemTable.put(id, newUIDisplayTextItem);
	}
	
	// Checks if a UIDisplayTextItem object with uiDisplayTextItemID exists. 
	// - If yes, this object is returned.
	// - If no, a new object is created and this newly created object is returned. 
	public UIDisplayTextItem getUIDisplayTextItem(String uiDisplayTextItemID, String fallbackTranslationText)
						   throws DictionaryException {
		UIDisplayTextItem uiDisplayTextItem = existingUIDisplayTextItem(uiDisplayTextItemID, true);
		if (uiDisplayTextItem == null) {
			// create a new UIDisplayTextItem 
			uiDisplayTextItem = createNewUIDisplayTextItem(uiDisplayTextItemID, fallbackTranslationText);
		}
		return uiDisplayTextItem;
	}
	
	// Returns an existing UIDisplayTextItem object  
	public UIDisplayTextItem existingUIDisplayTextItem(String uiDisplayTextItemID, boolean testExist) 
							throws DictionaryException {
		UIDisplayTextItem foundUIDisplayTextItem;
		foundUIDisplayTextItem = (UIDisplayTextItem) uiDisplayTextItemTable.get(uiDisplayTextItemID);
		if ((! testExist) && (foundUIDisplayTextItem == null)) {
			throw new DictionaryException("No UIDisplayTextItem for " + uiDisplayTextItemID);
		}
		return foundUIDisplayTextItem;
	}
	
	/*
	 * Getting specific UIDisplayTextItems
	 */
	// Language UIDisplayTextItems
	public UIDisplayTextItem getLanguageUIDisplayTextItem(String language) 
			throws DictionaryException {
		String languageID  = uiDisplayTextItemReference + 
							 uiDisplayTextItemPrefixLanguage + 
							 language;
		UIDisplayTextItem languageUIDisplayTextItem = LanguageUI.getUI().getUIDisplayTextItem(languageID, language);
		return languageUIDisplayTextItem;
	}
	
}