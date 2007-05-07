/**
 * DictionaryForMIDs
 * 
 * FontGenerator.java - Copyright J2ME Polish
 * 
 * Modified by Sean Kernohan (webmaster@seankernohan.com)
 */
package de.kugihan.fonttoolkit;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.math.*;

import javax.imageio.*;

//import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileAccessHandler;

public class FontGenerator {
	private final Font basicFont;

	private Font derivedFont;

	private String text = "?";
	
	private final int charactersPerPng = 50;

	private float currentSize;
	
	private int clipTop;
	
	private int clipBottom;
	
	private int sizePoints;

	private Color currentColor = Color.BLACK;

	private BufferedImage externalImage;

	private int characterSpacing = 0;

	public FontGenerator(File fontFile, String chars, int size, int top, int bottom)
			throws IOException {
		super();

		currentSize = size;
		this.clipTop = top;
		this.clipBottom = bottom;
		this.sizePoints = size;

		InputStream in = new FileInputStream(fontFile);
		try {
			this.text = chars;
			this.basicFont = Font.createFont(Font.TRUETYPE_FONT, in);
			this.derivedFont = this.basicFont.deriveFont(this.currentSize);
		} catch (FontFormatException e) {
			throw new IOException("Unable to init true type font: "
					+ e.toString());
		}
	}

	public Font getFont() {
		return this.derivedFont;
	}

	public void saveBitMapFont(String fontDir) throws IOException {
		// FIXME
		// ADD CHARSPACING FIELD
		BufferedImage image = createImage(this.text);
		
		boolean status;
		
		///TODO: try catch block
		status = new File(fontDir + "\\fonts\\" + sizePoints + "\\").mkdirs();
		System.out.println(status ? "directory created" : "directory not created");
		
		File file = new File(fontDir + "\\fonts\\" + sizePoints + "\\font.bmf");
		
		FileOutputStream out = new FileOutputStream(file);
		String text = this.text;
		DataOutputStream dataOut = new DataOutputStream(out);
		int fontHeightPixels = image.getHeight();
		dataOut.writeInt(fontHeightPixels);
		dataOut.writeInt(charactersPerPng);
		int fontImagesTotalNumber = (text.length() / charactersPerPng) + 1;
		dataOut.writeInt(fontImagesTotalNumber);
		// write the character-map:
		String charMap = text;
		dataOut.writeUTF(charMap);
		
		//write the widths of each character:
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		FontRenderContext fc = g.getFontRenderContext();
		for (int k = 0; k < text.length(); k++) {
			String substring = text.substring(k, k + 1);
			Rectangle2D bounds = this.derivedFont
					.getStringBounds(substring, fc);
			dataOut
					.writeByte((int) (bounds.getWidth() + this.characterSpacing));
		}
		// write the images itself:
		int totalLength = text.length();
		String allCharacters = text;
		int i = 0;
		int j = 0;
			
		while (j < totalLength){
			text = allCharacters.substring(j, Math.min(totalLength, j + charactersPerPng));
			BufferedImage imagePart = createImage(text);
			File outFile = new File(fontDir + "\\fonts\\" + sizePoints + "\\" + String.valueOf(i)  + ".png");
			ImageIO.write(imagePart, "png", outFile);
			j = j + charactersPerPng;
			i++;
			
		}
		out.close();
	//create or extend bitmapfontsize bmf-file
		File file2 = new File(fontDir + "\\fonts\\sizes.bmf");		
		FileOutputStream out2 = new FileOutputStream(file2);		
		DataOutputStream dataOut2 = new DataOutputStream(out2);	
		
		int numberOfBitmapFontSizes = 0;
		for (int l = 8; l <= 36; l = l + 2){
			String size = Integer.toString(l);		
			boolean dirExists;
			dirExists = new File(fontDir + "\\fonts\\" + size).isDirectory();
			if (dirExists) {
				numberOfBitmapFontSizes++;
			}
		}
		dataOut2.writeInt(numberOfBitmapFontSizes);
		for (int l = 8; l <= 36; l = l + 2){
			String size = Integer.toString(l);			
			boolean dirExists;
			dirExists = new File(fontDir + "\\fonts\\" + size).isDirectory();
			if (dirExists) {
				dataOut2.writeInt(l);
			}			
		}
		dataOut2.writeInt(sizePoints);		
		out2.close();
		
	}

	public BufferedImage createImage(String text) {
		
		if (text.length() == 0) {
			return null;
		}
		// use dummy buffer for get a render context:
		BufferedImage image = new BufferedImage(1, 1,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = image.createGraphics();
		g.setFont(this.derivedFont);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		Font fontToShow = getFont();
		FontMetrics fontMetrics = g.getFontMetrics(fontToShow);

		double height = (fontMetrics.getHeight()) - this.clipBottom - this.clipTop;
		double width = fontMetrics.stringWidth(text)
				+ (text.length() * this.characterSpacing);
		image = new BufferedImage((int) width, (int) height,
				BufferedImage.TYPE_4BYTE_ABGR);
		g = image.createGraphics();
		Color transparent = new Color(1, 1, 1, 0);
		g.setBackground(transparent);
		g.clearRect(0, 0, (int) width, (int) height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setFont(this.derivedFont);
		g.setColor(this.currentColor);
		char[] characters = text.toCharArray();
		int y = (int) (fontMetrics.getMaxAscent());
		int x = 0;

		for (int i = 0; i < characters.length; i++) {
			g.drawChars(characters, i, 1, x, y - clipTop);
			x += (int) fontMetrics.stringWidth(text.substring(i, i + 1))
					+ this.characterSpacing;
		}
		return image;
	}

	public void setImage(File file) throws IOException {
		this.externalImage = ImageIO.read(file);
	}
}
