/*
 DictionaryForMIDs - a free multi-language dictionary for mobile devices.
 Copyright (C) 2006 Sean Kernohan (webmaster@seankernohan.com)

 GPL applies - see file COPYING for copyright statement.
 */
package de.kugihan.dictionaryformids.hmi_java_me.mainform.bitmapfont;

import javax.microedition.lcdui.*;

import de.kugihan.dictionaryformids.dataaccess.content.FontStyle;
import de.kugihan.dictionaryformids.dataaccess.content.RGBColour;
import de.kugihan.dictionaryformids.dataaccess.content.SelectionMode;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemTextPart;

public class BitmapFontCanvas extends CustomItem {

	private static BitmapFont font;

	private BitmapFontViewer viewer;

	private StringColourItemText stringItem;

	private int lineHeightPixels;

	private int totalHeightPixels;

	private int maxWidthPixels;
	
	public BitmapFontCanvas(StringColourItemText input, int maxWidthPixels, boolean colouredMode) {
		super(null);
		this.maxWidthPixels = maxWidthPixels;
		stringItem = input;
		try {
			font = BitmapFont.getInstance();
			viewer = font.getViewer(stringItem, maxWidthPixels, colouredMode);
			lineHeightPixels = font.getLineHeightPixels();
			totalHeightPixels = viewer.getLinesPainted() * lineHeightPixels;
		} catch (Exception e) {
		}
	}

	public boolean fontExists() {
		if (viewer == null)
			return false;
		return true;
	}

	public static boolean fontExistsStatic() {
		StringColourItemTextPart part = new StringColourItemTextPart("test",
				new RGBColour(0, 0, 0), new FontStyle(FontStyle.plain),
				new SelectionMode(SelectionMode.none));
		StringColourItemText text = new StringColourItemText();
		text.addItemTextPart(part);		
		return new BitmapFontCanvas(text, 50, false).fontExists();
	}
	
	protected int getMinContentHeight() {
		return totalHeightPixels;
	}

	protected int getMinContentWidth() {
		return maxWidthPixels;
	}

	protected int getPrefContentHeight(int arg0) {
		return totalHeightPixels;
	}

	protected int getPrefContentWidth(int arg0) {
		return maxWidthPixels;
	}

	protected void paint(Graphics arg0, int arg1, int arg2) {
		viewer.paint(0, 0, arg0);
	}
}
