package de.kugihan.dictionaryformids.hmi_j2me.mainform;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.hmi_j2me.DictionaryForMIDs;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.DfMTextField;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.UIDisplayTextItems;

public class MainFormItemsSimple implements MainFormItems {
	
	private Font translationFromFont = null;
	private Font translationToFont = null;

	private MainForm applicationMainForm;
	
	public MainFormItemsSimple(MainForm applicationMainFormParam) throws DictionaryException {
		applicationMainForm = applicationMainFormParam;
		updateFonts();
	}
	
	public DfMTextField createToBeTranslatedWordTextField()
			throws DictionaryException {
		return new DfMTextField(null, null, 100, TextField.ANY);
	}
	
	public Item createTranslationItem(StringColourItemText stringColourItemText, boolean fromItem) {
		StringBuffer itemText = applicationMainForm.contentParserObj.getTextFromStringColourItemText(stringColourItemText);
		StringItem translationItem = new StringItem(null, itemText.toString());
		if (DictionaryForMIDs.useMIDP20) {
			Font font;
			if (fromItem)
				font = translationFromFont;
			else
				font = translationToFont;
			translationItem.setFont(font);
			translationItem.setLayout(Item.LAYOUT_NEWLINE_BEFORE  | Item.LAYOUT_2);
		}
		else {
			String lineBreak = "\n";
			translationItem.setText(translationItem.getText() + lineBreak);
		}
		return translationItem;
	}

//	 update the fonts of the translated texts:
	public void updateFonts() 
				throws DictionaryException {
		if (DictionaryForMIDs.useMIDP20) { // only supported starting with MIDP 2.0
			if (applicationMainForm.dictionarySettingFormObj != null) {
				Font newTranslationFromFont = Font.getFont(Font.getDefaultFont().getFace(), 
						                                   Font.STYLE_UNDERLINED,
						                                   applicationMainForm.dictionarySettingFormObj.getFontSize());
				Font newTranslationToFont = Font.getFont(Font.getDefaultFont().getFace(), 
		                                                 Font.STYLE_BOLD,
						                                 applicationMainForm.dictionarySettingFormObj.getFontSize());
				if ((translationFromFont == null) && (translationToFont == null)) {
					// first time initialisation
					translationFromFont = newTranslationFromFont;
					translationToFont = newTranslationToFont;
				}
				for (int currentIndexTranslationItem = applicationMainForm.indexOfFirstTranslationItem;
					 currentIndexTranslationItem <= MainForm.applicationMainForm.indexOfLastTranslationItem;
				     ++currentIndexTranslationItem) {
					Item item = applicationMainForm.get(currentIndexTranslationItem);
					if (! (item instanceof StringItem))
						throw new DictionaryException("StringItem expected");
					StringItem stringItem = (StringItem) item;
					Font lastFont = stringItem.getFont();
					if (lastFont == translationFromFont) {
						stringItem.setFont(newTranslationFromFont);
					}
					else if (lastFont == translationToFont) {
						stringItem.setFont(newTranslationToFont);
					}
					else {
						throw new DictionaryException("Unexpected Font found");
					}			
				}
				translationFromFont = newTranslationFromFont;
				translationToFont = newTranslationToFont;
			}
		}
	}
}
