/*
 DictionaryForMIDs - a free multi-language dictionary for mobile devices.
 Copyright (C) 2006 Sean Kernohan (webmaster@seankernohan.com)

 GPL applies - see file COPYING for copyright statement.
 */
package de.kugihan.dictionaryformids.hmi_j2me.mainform.bitmapfont;

import javax.microedition.lcdui.*;

public class BitMapFontCanvas extends CustomItem {

	public static String bitmapFontFilename;

	private static BitMapFont font;

	private BitMapFontViewer viewer;

	private String displayedString;

	// TODO: variable line height?
	private final int lineHeightPixels = 14;

	private int totalHeightPixels = lineHeightPixels;

	private int maxWidthPixels;

	public BitMapFontCanvas(String input, int maxWidthPixels) {
		super(null);
		this.maxWidthPixels = maxWidthPixels;
		displayedString = input;
		try {
			font = BitMapFont.getInstance("/fonts/font.bmf");
			viewer = font.getViewer(input, maxWidthPixels);
			totalHeightPixels = viewer.getLinesPainted() * lineHeightPixels;
		} catch (Exception e) {
		}
	}

	public boolean fontExists() {
		if (viewer == null)
			return false;
		return true;
	}

	public String getString() {
		return displayedString;
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
