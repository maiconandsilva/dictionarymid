package de.kugihan.dictionaryformids.hmi_j2me.mainform;

import javax.microedition.lcdui.Item;

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.DfMTextField;

public interface MainFormItems {

	/*
	 * Factory methods for creating user interface items
	 */
	
	/*
	 * createToBeTranslatedWordTextField
	 * 
	 * Creates the text field where the user can input the word that is translated
	 */ 
	DfMTextField createToBeTranslatedWordTextField() throws DictionaryException;
	
	
	/*
	 * createTranslationItem
	 * 
	 * Creates an item that is used for displaying the translation result. 
	 * 
	 * The parameter stringColourItemText contains the translation result including colour and style information.
	 * 
	 * The parameter fromItem is true when the text belongs to the inputLanguage and false when it belongs to 
	 * the outputLanguage.
	 */ 
	Item createTranslationItem(StringColourItemText stringColourItemText, boolean fromItem, int screenWidth) throws DictionaryException;
	
	
	public void updateFonts() throws DictionaryException;
	
}
