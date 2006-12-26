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

import javax.microedition.lcdui.Image;

public final class BitMapFont {
	private static Hashtable fontsByUrl = new Hashtable();

	private String fontUrl;

	private Image fontImage;

	private boolean hasMixedCase;

	private byte[] characterWidths;

	private short[] xPositions;

	private String characterMap;

	private int fontHeight;

	private int spaceIndex;

	/**
	 * Creates a new bitmap font.
	 * 
	 * @param fontUrl
	 *            the url of the *.bmf file containing the font-specification.
	 */
	private BitMapFont(String fontUrl) {
		super();
		this.fontUrl = fontUrl;
		// #debug
		// System.out.println("Creating bitmap font " + fontUrl);
	}

	/**
	 * Creates a viewer object for the given string.
	 * 
	 * @param input
	 *            the input which should be shown.
	 * @return a viewer object which shows the font in a performant manner
	 */
	public BitMapFontViewer getViewer(String input, int maxWidthPixels) throws Exception {
		if (this.fontImage == null) {
			// try to load the *.bmf file:
			InputStream in = null;
			try {
				in = this.getClass().getResourceAsStream(this.fontUrl);
				if (in == null) {
					return null;
				}
				DataInputStream dataIn = new DataInputStream(in);
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
				this.fontUrl = null;
			} catch (IOException e) {
				// #debug error
				System.out.println("Unable to load bitmap-font ["
						+ this.fontUrl + "]" + e);
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
		// int imageWidth = this.fontImage.getWidth();
		// get the x/y-position and width for each character:
		if (!this.hasMixedCase) {
			input = input.toLowerCase();
		}
		int length = input.length();
		// short[] yPositions = new short[ length ];
		int[] indeces = new int[length];
		for (int i = length - 1; i >= 0; i--) {

			String inputCharacter = input.substring(i, i + 1);
			indeces[i] = this.characterMap.indexOf(inputCharacter);
			if (indeces[i] == -1) {
				indeces[i] = this.characterMap.indexOf("?");
				System.out.println("BitMapFont: " + inputCharacter
						+ " not found - substituting with ?");
			}
		}
		return new BitMapFontViewer(this.fontImage, indeces, this.xPositions,
				this.characterWidths, this.fontHeight, this.spaceIndex, 1,
				maxWidthPixels);
	}

	/**
	 * Gets the instance of the specified font.
	 * 
	 * @param url
	 *            the url of the font
	 * @return the corresponding bitmap font.
	 */
	public static BitMapFont getInstance(String url) {
		BitMapFont font = (BitMapFont) fontsByUrl.get(url);
		if (font == null) {
			font = new BitMapFont(url);
			fontsByUrl.put(url, font);
		}
		return font;
	}

}
