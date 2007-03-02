package de.kugihan.dictionaryformids.hmi_java_me.mainform;

import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.DfMTextField;
import de.kugihan.dictionaryformids.hmi_java_me.mainform.bitmapfont.BitmapFontCanvas;

public class MainFormItemsBitmap implements MainFormItems {

	private MainForm applicationMainForm;
	
	private boolean colouredMode;

	public MainFormItemsBitmap(MainForm applicationMainFormParam, boolean colouredMode)
			throws DictionaryException {
		this.colouredMode = colouredMode;
		applicationMainForm = applicationMainFormParam;
		updateFonts();
	}

	public DfMTextField createToBeTranslatedWordTextField()
			throws DictionaryException {
		return new DfMTextField(null, null, 100, TextField.ANY);
	}

	public Item createTranslationItem(
			StringColourItemText stringColourItemText, boolean fromItem, int screenWidth) {
		Item translationItem = new BitmapFontCanvas(stringColourItemText, screenWidth, colouredMode);
		return translationItem;
	}

	// update the fonts of the translated texts:
	public void updateFonts() throws DictionaryException {
		// do nothing - fonts are not handled in this class
	}
}
