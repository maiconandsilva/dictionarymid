package de.kugihan.dictionaryformids.hmi_j2me.mainform.bitmapfont;

/*
 * Copyright (c) 2004-2005 Robert Virkus / Enough Software
 *
 * Modified by Sean Kernohan (webmaster@seankernohan.com)
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Image;

import de.kugihan.dictionaryformids.dataaccess.content.RGBColour;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemTextPart;

public final class BitmapFont {
	private static Hashtable fontsByUrl = new Hashtable();

	private static final String bitmapFontFilename = "/fonts/font.bmf";

	private Image fontImage;

	private boolean hasMixedCase;

	private byte[] characterWidths;

	private short[] xPositions;

	private String characterMap;

	private int fontHeight;

	private int spaceIndex;

	private int lineHeightPixels;

	/**
	 * Creates a new bitmap font.
	 * 
	 * @param fontUrl
	 *            the url of the *.bmf file containing the font-specification.
	 */
	private BitmapFont() {
		super();
	}

	public int getLineHeightPixels() {
		return lineHeightPixels;
	}

	/**
	 * Creates a viewer object for the given string.
	 * 
	 * @param input
	 *            the input which should be shown.
	 * @return a viewer object which shows the font in a performant manner
	 */
	public BitmapFontViewer getViewer(StringColourItemText stringItem,
			int maxWidthPixels, boolean colouredMode) throws Exception {
		if (this.fontImage == null) {
			// try to load the *.bmf file:
			InputStream in = null;
			try {
				in = this.getClass().getResourceAsStream(
						this.bitmapFontFilename);
				if (in == null) {
					return null;
				}
				DataInputStream dataIn = new DataInputStream(in);
				this.lineHeightPixels = dataIn.readInt();
				this.hasMixedCase = dataIn.readBoolean();
				String map = dataIn.readUTF();
				this.characterMap = map;
				this.spaceIndex = map.indexOf(' ');
				int length = map.length();
				this.characterWidths = new byte[length];
				this.xPositions = new short[length];
				short xPos = 0;
				for (int i = 0; i < length; i++) {
					byte width = dataIn.readByte();
					this.characterWidths[i] = width;
					this.xPositions[i] = xPos;
					xPos += width;
				}
				// #ifdef polish.midp2
				this.fontImage = Image.createImage(in);
				// #endif
				this.fontHeight = this.fontImage.getHeight();
			} catch (IOException e) {
				// #debug error
				System.out.println("Unable to load bitmap-font ["
						+ this.bitmapFontFilename + "]" + e);
				return null;
				// #ifndef polish.Bugs.ImageIOStreamAutoClose
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// #debug error
					System.out
							.println("Unable to close bitmap-font stream" + e);
				}
				// #endif
			}
		}

		// we need to know how many characters long the current item is...
		// get number of stringcolouritemtextparts
		int size = stringItem.size();
		Vector theParts = new Vector();
		// totallength = number of characters in the entire stringcolouritemtext
		int totalLength = 0;
		for (int i = 0; i < size; i++) {
			StringColourItemTextPart textPart = stringItem.getItemTextPart(i);
			totalLength += textPart.getText().length();
			// add stringcolouritemtextpart to the vector for future use
			theParts.addElement(textPart);
		}

		// initialise index and colour arrays
		int[] indeces = new int[totalLength];
		RGBColour[] colours = new RGBColour[totalLength];

		// finally, process the data
		for (int j = 0; j < size; j++) {
			StringColourItemTextPart currentPart = (StringColourItemTextPart) theParts
					.elementAt(j);
			int length = currentPart.getText().length();
			for (int i = length - 1; i >= 0; i--) {
				char inputCharacter = currentPart.charAt(i);
				indeces[i] = this.characterMap.indexOf(inputCharacter);
				colours[i] = currentPart.getColour();
				if (indeces[i] == -1) {
					indeces[i] = this.characterMap.indexOf("?");
					System.out.println("BitmapFont: " + inputCharacter
							+ " not found - substituting with ?");
				}
			}
		}
		return new BitmapFontViewer(this.fontImage, indeces, colours,
				this.xPositions, this.characterWidths, this.fontHeight,
				this.spaceIndex, 1, maxWidthPixels, colouredMode);
	}

	/**
	 * Gets the instance of the specified font.
	 * 
	 * @param url
	 *            the url of the font
	 * @return the corresponding bitmap font.
	 */
	public static BitmapFont getInstance() {
		BitmapFont font = (BitmapFont) fontsByUrl.get(bitmapFontFilename);
		if (font == null) {
			font = new BitmapFont();
			fontsByUrl.put(bitmapFontFilename, font);
		}
		return font;
	}
}