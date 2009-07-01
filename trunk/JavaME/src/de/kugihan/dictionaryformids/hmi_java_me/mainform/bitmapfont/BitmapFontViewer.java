package de.kugihan.dictionaryformids.hmi_java_me.mainform.bitmapfont;

/*
 * Copyright (c) 2004-2005 Robert Virkus / Enough Software
 *
 * Modified by Sean Kernohan (webmaster@seankernohan.com)
 * Last Modified by Sebastian Loziczky (bastil@gmx.at)
 */

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import de.kugihan.dictionaryformids.dataaccess.content.RGBColour;
//import de.kugihan.dictionaryformids.hmi_java_me.DictionarySettings;

public class BitmapFontViewer {

	protected final static byte ABSOLUTE_LINE_BREAK = -2;

	protected final static byte ARTIFICAL_LINE_BREAK = -3;
	
	private Image[] characterImage;

	private int linesPainted;

	private final int[] xPositions;

	private final byte[] usedCharactersWidths;

	private final int fontHeight;

	private int verticalPadding;

	private final int spaceIndex;

	private int height;

	private int width;

	private int numberOfLines;

	private short[] lineWidths;

	private final int[] indeces;
	
// 	private RGBColour backgroundColour;
// 	
// 	private boolean isUseBackgroundColour;

	private final RGBColour[] colours;

	private final byte[] actualCharacterWidths;

	private final short[] originalLineIndeces;

	private int orientation;

	private int maxWidthPixels;

	private boolean colouredMode;

	/**
	 * Views a specific input string with a specific bitmap font.
	 * 
	 * @param charsImage
	 *            the basic character-images
	 * @param indeces
	 *            array of the x-positions of the to-be-displayed characters
	 * @param xPositions
	 *            array of the x-positions of the to-be-displayed characters
	 * @param characterWidths
	 *            array of the widths of the to-be-displayed characters
	 * @param fontHeight
	 *            the height of the font
	 * @param spaceIndex
	 *            the index of the space character
	 * @param verticalPadding
	 *            the padding between two lines
	 * 
	 */
	public BitmapFontViewer(Image[] charsImage, int[] indeces, RGBColour[] colours,
			int[] xPositions, byte[] characterWidths, int fontHeight,
			int spaceIndex, int verticalPadding, int maxWidthPixels,
			boolean colouredMode) {

		this.characterImage = charsImage;
		this.colouredMode = colouredMode;
		this.maxWidthPixels = maxWidthPixels;
		this.indeces = indeces;
		this.colours = colours;
		this.actualCharacterWidths = characterWidths;
		this.spaceIndex = spaceIndex;
		this.lineWidths = new short[20];
		this.originalLineIndeces = new short[20];
		this.verticalPadding = verticalPadding;
		this.xPositions = new int[indeces.length];
		this.usedCharactersWidths = new byte[indeces.length];
		short currentLineWidth = 0;
		short maxLineWidth = 0;
		short linesIndex = 0;
		for (int i = 0; i < indeces.length; i++) {
			int index = indeces[i];
			if (index == ABSOLUTE_LINE_BREAK) {
				// line is too long - add line break
				if (currentLineWidth > maxLineWidth) {
					maxLineWidth = currentLineWidth;
				}
				this.originalLineIndeces[linesIndex] = (short) i;
				this.lineWidths[linesIndex] = currentLineWidth;
				currentLineWidth = 0;
				// mark the character as absolute line-break:
				this.usedCharactersWidths[i] = ABSOLUTE_LINE_BREAK;
				linesIndex++;
			} else {
				// this is a normal character
				this.xPositions[i] = xPositions[index];
				byte characterWidth = characterWidths[index];
				this.usedCharactersWidths[i] = characterWidth;
				currentLineWidth += characterWidth;
			}
		}
		// store the width of the last-line:
		this.lineWidths[linesIndex] = currentLineWidth;
		if (currentLineWidth > maxLineWidth) {
			maxLineWidth = currentLineWidth;
		}
		this.numberOfLines = linesIndex + 1;
		this.height = this.numberOfLines * (fontHeight + verticalPadding)
				- verticalPadding;
		this.width = maxLineWidth;
		this.fontHeight = fontHeight;
		calculateLines(0);
	}

	public int getLinesPainted() {
		return linesPainted;
	}

	public void calculateLines(int x) {
		int startX = x;
		linesPainted = 1;

		for (int i = 0; i < this.xPositions.length; i++) {
			byte characterWidth = this.usedCharactersWidths[i];
			if ((x + characterWidth) > maxWidthPixels) {
				linesPainted++;
				x = startX;
				i--;
				continue;
			}
			x += characterWidth;
		}
	}

	/**
	 * Paints this viewer on the screen.
	 * 
	 * @param x
	 *            the x-position for the text. When the orientation is LEFT, x
	 *            defines the left-border; when the orientation is RIGHT, x
	 *            defines the rigth border; when the orientation is HCENTER, x
	 *            defines the middle between left and right border.
	 * @param y
	 *            the top y-position of the first line.
	 * @param g
	 *            the graphics object
	 */
	public void paint(int x, int y, Graphics g) {
		int clipX = g.getClipX();
		int clipY = g.getClipY();
		int clipWidth = g.getClipWidth();
		int clipHeight = g.getClipHeight();
		int clipXEnd = clipX + clipWidth;
		int startX = x;

		boolean isLayoutRight = (this.orientation == Graphics.RIGHT);
		boolean isLayoutCenter = (this.orientation == Graphics.HCENTER);
		if (isLayoutCenter) {
			x = startX - (this.lineWidths[0] / 2);
		} else if (isLayoutRight) {
			x = startX - this.lineWidths[0];
		}
		int lineIndex = 0;
		for (int i = 0; i < this.xPositions.length; i++) {
			byte characterWidth = this.usedCharactersWidths[i];
			if (characterWidth == 0) {
				continue;
			} else if ((x + characterWidth) > maxWidthPixels) {
				// this is a line-break:
				lineIndex++;
				if (isLayoutCenter) {
					x = startX - (this.lineWidths[lineIndex] / 2);
				} else if (isLayoutRight) {
					x = startX - this.lineWidths[lineIndex];
				} else {
					x = startX;
				}
				y += this.fontHeight + this.verticalPadding;
				i--;
				continue;
			} else if (x >= clipXEnd) {
				// this character is outside of the clipping area:
				continue;
			}
			g.clipRect(x, y, characterWidth, this.fontHeight);
		
			Image theCharacter = characterImage[indeces[i]];
						
			if (colouredMode) {
				RGBColour colour = colours[i];
				int w = theCharacter.getWidth();
				int h = theCharacter.getHeight();
				int[] rgbValues = new int[w * h];
				theCharacter.getRGB(rgbValues, 0, w, 0, 0, w, h);
				for (int k = 0; k < rgbValues.length; k++) {
					int argb = rgbValues[k];
					int red = (argb >> 16) & 0xff;
					int green = (argb >> 8) & 0xff;
					int blue = argb & 0xff;
					int alpha = (argb >> 24) & 0xff;				
					if (rgbValues[k] < 0) {
						// set foreground to coloured
						try {
							// try to set colours...
							red = colour.red;
							blue = colour.blue;
							green = colour.green;
						} catch (NullPointerException e) {
							// if colours[i] is null, just set it to black :(
							// can somebody find out why this exception happens?
							red = 0;
							blue = 0;
							green = 0;
						}
						alpha = 255;
					} else {
						//TODO: use the devices background colour
//						isUseBackgroundColour = DictionarySettings.isUseBackgroundColour();
//					  	if(isUseBackgroundColour) backgroundColour = DictionarySettings.getBackgroundColour();
//					  	red = backgroundColour.red;
//					  	blue = backgroundColour.blue;
//					  	green = backgroundColour.green;
//						alpha = 255;
						
						// set background to white
						red = 255;
						blue = 255;
						green = 255;
						alpha = 255;
					}
					argb = (alpha << 24) | (red << 16) | (green << 8) | blue;
					rgbValues[k] = argb;
				}
				theCharacter = Image.createRGBImage(rgbValues, w, h, false);
			}
			
			g.drawImage(theCharacter, x, y, Graphics.TOP | Graphics.LEFT);
			x += characterWidth;
			// reset clip:
			g.setClip(clipX, clipY, clipWidth, clipHeight);
		}
	}

	/**
	 * Retrieves the width needed for this viewer.
	 * 
	 * @return the width in pixels
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Retrieves the height needed for this viewer.
	 * 
	 * @return the height in pixels
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Layouts this text-viewer.
	 * 
	 * @param firstLineWidth
	 *            the available width for the first line
	 * @param lineWidth
	 *            the available width for the following lines
	 * @param paddingVertical
	 *            the space between lines
	 * @param orientationSetting
	 *            the orientation of this viewer, either Grapics.LEFT,
	 *            Graphics.RIGHT or Graphics.HCENTER
	 */
	public void layout(int firstLineWidth, int lineWidth, int paddingVertical,
			int orientationSetting) {
		this.orientation = orientationSetting;
		this.verticalPadding = paddingVertical;
		// remove all existing artificial line-breaks first.
		int lineIndex = 0;
		short currentLineWidth = 0;
		short maxLineWidth = 0;
		int lastSpaceIndex = -1;
		int lastSpaceWidth = 0;
		int lineStartIndex = 0;
		for (int i = 0; i < this.usedCharactersWidths.length; i++) {
			byte characterWidth = this.usedCharactersWidths[i];
			if (characterWidth == ARTIFICAL_LINE_BREAK) {
				// restore original character width:
				characterWidth = this.actualCharacterWidths[this.indeces[i]];
				this.usedCharactersWidths[i] = characterWidth;
			} else if (characterWidth == ABSOLUTE_LINE_BREAK) {
				// this is an absolute linebreak (\n)
				this.lineWidths[lineIndex] = currentLineWidth;
				if (currentLineWidth > maxLineWidth) {
					maxLineWidth = currentLineWidth;
				}
				lineStartIndex = (i + 1);
				lineIndex++;
				currentLineWidth = 0;
				continue;
			}

			// this is a normal character
			int index = this.indeces[i];
			if (index == this.spaceIndex) {
				lastSpaceIndex = i;
				lastSpaceWidth = currentLineWidth;
			}
			currentLineWidth += characterWidth;
			if (currentLineWidth > firstLineWidth) {
				// we need to include an artificial line-break:
				if (lastSpaceIndex > lineStartIndex) {
					this.usedCharactersWidths[lastSpaceIndex] = ARTIFICAL_LINE_BREAK;
					lineStartIndex = lastSpaceIndex + 1;
					this.lineWidths[lineIndex] = (short) lastSpaceWidth;
					if (lastSpaceWidth > maxLineWidth) {
						maxLineWidth = (short) lastSpaceWidth;
					}
					currentLineWidth -= lastSpaceWidth;
					lineIndex++;
					// } else {
					// System.out.println("Unable to break line without any
					// prior space");
				}
			}
			firstLineWidth = lineWidth;
		}
		// store the width of the last-line:
		this.lineWidths[lineIndex] = currentLineWidth;
		if (currentLineWidth > maxLineWidth) {
			maxLineWidth = currentLineWidth;
		}
		this.numberOfLines = lineIndex + 1;
		this.width = maxLineWidth;
		this.height = this.numberOfLines * (this.fontHeight + paddingVertical)
				- paddingVertical;
	}

	/**
	 * Returns the height of used font.
	 * 
	 * @return the height of the bitmap-font in pixels.
	 */
	public int getFontHeigth() {
		return this.fontHeight;
	}

	/**
	 * Retrieves the number of lines which are used to display the embedded
	 * text.
	 * 
	 * @return the number of lines.
	 */
	public int getNumberOfLines() {
		return this.numberOfLines;
	}

}
