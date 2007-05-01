package de.kugihan.dictionaryformids.hmi_java_me.mainform.bitmapfont;

/*
 * Copyright (c) 2004-2005 Robert Virkus / Enough Software
 *
 * Modified by Sean Kernohan (webmaster@seankernohan.com)
 * Last Modified by Sebastian Loziczky (bastil@gmx.at)
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import de.kugihan.dictionaryformids.dataaccess.content.RGBColour;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemTextPart;

public final class BitmapFont {
	private static Hashtable fontsByUrl = new Hashtable();

	private String bitmapFontDir;
	
	private String fontUrl;
	
	private boolean charsLoaded = false;
	
	private Image[] characterImage;

	private int fontImagesTotalNumber;
	
	private int totalNumberOfCharacters;

	private byte[] characterWidths;

	private int[] xPositions;

	private String characterMap;
	
	private int fontHeightPixels;
	
	private int charactersPerPng;

	private int spaceIndex;

	/**
	 * Creates a new bitmap font.
	 * 
	 * @param fontUrl
	 *            the url of the *.bmf file containing the font-specification.
	 */
	private BitmapFont(String fontDirectory, String fontUrl) {
		super();
		this.fontUrl = fontUrl;
		this.bitmapFontDir = fontDirectory;
	}

	public int getLineHeightPixels() {
		 return fontHeightPixels;
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
		int thePartsTotalLength = 0;
		
		for (int j = 0; j < size; j++) {
			StringColourItemTextPart currentPart = (StringColourItemTextPart) theParts
					.elementAt(j);
			int length = currentPart.getText().length();
			for (int i = length - 1; i >= 0; i--) {
				char inputCharacter = currentPart.charAt(i);
				indeces[i + thePartsTotalLength] = this.characterMap.indexOf(inputCharacter);
				colours[i + thePartsTotalLength] = currentPart.getColour();
				if (indeces[i + thePartsTotalLength] == -1) {
					indeces[i + thePartsTotalLength] = this.characterMap.indexOf("?");
					System.out.println("BitmapFont: " + inputCharacter
							+ " not found - substituting with ?");
				}
			}
			thePartsTotalLength += length;
		}
		
		//load the remaining char images for this stringItem 
		if (characterImage == null) this.characterImage = new Image[totalNumberOfCharacters];
		                                                      
		for (int i = 0; i < totalLength; i++){
			int l = indeces[i] / charactersPerPng;
			try {
				if (characterImage[indeces[i]] == null) {
					InputStream pngIn = this.getClass().getResourceAsStream(
							bitmapFontDir + String.valueOf(l) + ".png");
					Image image = Image.createImage(pngIn);
					if (this.characterWidths[indeces[i]] != 0) {
						this.characterImage[indeces[i]] = Image.createImage(image, this.xPositions[indeces[i]],
							0, this.characterWidths[indeces[i]], this.fontHeightPixels, Sprite.TRANS_NONE);
					}
					try {
						pngIn.close();
					}catch (IOException e) {
						System.out.println("Unable to close bitmap-font stream: " +
									bitmapFontDir + String.valueOf(l) + ".png " + e);
					}
				}
			} catch (IOException e) {
				System.out.println("Unable to open bitmap-font stream: " +
						bitmapFontDir + String.valueOf(l) + ".png " + e);
			}
		}
		return new BitmapFontViewer(this.characterImage, indeces, colours,
				this.xPositions, this.characterWidths, this.fontHeightPixels,
				this.spaceIndex, 0, maxWidthPixels, colouredMode);
	}
	/**
	 * Loads the the index-file and the font properties
	 * */
	public boolean loadFont() throws Exception {
		if (this.xPositions == null) {
			// try to load the *.bmf file:
			InputStream in = null;
			try {
				in = this.getClass().getResourceAsStream(
						this.fontUrl);
				if (in == null) {
					return false;
				}			
				DataInputStream dataIn = new DataInputStream(in);
				this.fontHeightPixels = dataIn.readInt();
				this.charactersPerPng = dataIn.readInt();
				this.fontImagesTotalNumber = dataIn.readInt();
				String map = dataIn.readUTF();		
				this.characterMap = map;
				this.spaceIndex = map.indexOf(' ');
				this.totalNumberOfCharacters = map.length();
				this.characterWidths = new byte[totalNumberOfCharacters];
				this.xPositions = new int[totalNumberOfCharacters];
				if (characterImage == null) {
					this.characterImage = new Image[totalNumberOfCharacters];
				}
				
				for (int l = 0; l < fontImagesTotalNumber; l++){
					int xPos = 0;
					for (int i = l * charactersPerPng; (i < ((l + 1) * charactersPerPng)) 
													&& (i < totalNumberOfCharacters) ; i++) {
						byte width = dataIn.readByte();
						this.characterWidths[i] = width;
						this.xPositions[i] = xPos;
						xPos += width;
					}
				}				
			} catch (IOException e) {
				System.out.println("Unable to load bitmap-font ["
						+ this.fontUrl + "]" + e);
				return false;
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					System.out
							.println("Unable to close bitmap-font stream: " + e);
				}
			}
		}
		return true;
	}
	
	/**
	 * Loads the first 120 chars of the font into the cache.
	 * */
	public boolean loadChars() throws Exception {
		if (this.charsLoaded == true)
			return false;
		
		if (characterImage == null) {
			this.characterImage = new Image[totalNumberOfCharacters];
		}
		Image image = null;		
		int currentImage = 5;
		boolean currentImageChanged;
		for (int i =0; i < totalNumberOfCharacters && i < 120; i++){
			int newCurrentImage = i / this.charactersPerPng;
			if (newCurrentImage == currentImage) currentImageChanged = false;
			else currentImageChanged = true;
			currentImage = newCurrentImage;
			
			if (currentImageChanged){
				InputStream pngIn = this.getClass().getResourceAsStream(
						bitmapFontDir + String.valueOf(currentImage) + ".png");
				image = Image.createImage(pngIn);
				try {
					pngIn.close();
				}catch (IOException e) {
					System.out.println("Unable to close bitmap-font stream: " +
							bitmapFontDir + String.valueOf(currentImage) + ".png " + e);
				}
			}
			//TODO: try catch block?
			if (this.characterWidths[i] != 0){
				this.characterImage[i] = Image.createImage(image, this.xPositions[i],
						0, this.characterWidths[i], this.fontHeightPixels, Sprite.TRANS_NONE);
			}
		}
		this.charsLoaded = true;
		return true;
	}	
	
	/**
	 * Gets the instance of the specified font.
	 * 
	 * @param url
	 *            the url of the font	 * 			
	 * @return the corresponding bitmap font.
	 */
	public static BitmapFont getInstance(String fontDirectory, String url) {
		BitmapFont font = (BitmapFont) fontsByUrl.get(url);
		if (font == null) {
			font = new BitmapFont(fontDirectory, url);
			fontsByUrl.put(url, font);
		}
		return font;
	}
	
	/**
	 * Removes the instance of the specified font from the internal cache.
	 * 
	 * @param url the url of the font
	 */
	public static void removeInstance(String url) {
		fontsByUrl.remove( url );
	}
}