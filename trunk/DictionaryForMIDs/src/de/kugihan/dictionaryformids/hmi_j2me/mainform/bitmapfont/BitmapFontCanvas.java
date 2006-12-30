/*
 DictionaryForMIDs - a free multi-language dictionary for mobile devices.
 Copyright (C) 2006 Sean Kernohan (webmaster@seankernohan.com)

 GPL applies - see file COPYING for copyright statement.
 */
package de.kugihan.dictionaryformids.hmi_j2me.mainform.bitmapfont;

import javax.microedition.lcdui.*;

import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;

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
