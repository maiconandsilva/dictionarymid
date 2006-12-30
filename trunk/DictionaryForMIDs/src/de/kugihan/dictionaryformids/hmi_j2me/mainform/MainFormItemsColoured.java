// Last update : August 24, 2006
package de.kugihan.dictionaryformids.hmi_j2me.mainform;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.hmi_j2me.DictionaryForMIDs;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.DfMTextField;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.StringColourItem;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.UIDisplayTextItems;

public class MainFormItemsColoured implements MainFormItems {

	MainForm applicationMainForm;
	
	public MainFormItemsColoured(MainForm applicationMainFormParam) {
		applicationMainForm = applicationMainFormParam;
	}
	
	public DfMTextField createToBeTranslatedWordTextField() throws DictionaryException {
		// temporary implementation copied from MainFormItemsSimple:
		return new DfMTextField(null, null, 60, TextField.ANY);
	}
	
	public Item createTranslationItem(StringColourItemText stringColourItemText, boolean fromItem, int screenWidth) throws DictionaryException {

		int index = applicationMainForm.indexOfLastTranslationItem+1;
		if(!fromItem) index++;
		StringColourItem translationItem = new StringColourItem(stringColourItemText,
																								applicationMainForm.stringColourItemWidth,
																								applicationMainForm.dictionarySettingFormObj.getFontSize(),
																								index,
																								applicationMainForm);
		return translationItem;
	}
	
	public void updateFonts() throws DictionaryException {
		int newFontSize = applicationMainForm.dictionarySettingFormObj.getFontSize();
		for (int currentIndexTranslationItem = applicationMainForm.indexOfFirstTranslationItem;
					 currentIndexTranslationItem <= MainForm.applicationMainForm.indexOfLastTranslationItem;
				     ++currentIndexTranslationItem) {
					Item item = applicationMainForm.get(currentIndexTranslationItem);
					if (! (item instanceof StringColourItem))
						throw new DictionaryException("String Colour Item expected");
					StringColourItem stringColourItem = (StringColourItem) item;
					if(newFontSize != stringColourItem.getFontSize()) {
						stringColourItem = new StringColourItem(stringColourItem.getColourItem(),
																						applicationMainForm.stringColourItemWidth,
																						applicationMainForm.dictionarySettingFormObj.getFontSize(),
																						currentIndexTranslationItem,
																						applicationMainForm);
						applicationMainForm.delete(currentIndexTranslationItem);
						applicationMainForm.insert(currentIndexTranslationItem, stringColourItem);
					}
			}
	}
	
}
