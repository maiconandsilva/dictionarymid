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

import javax.imageio.*;

public class FontGenerator {
	private final Font basicFont;

	private Font derivedFont;

	private String text = "?";

	private float currentSize;

	private Color currentColor = Color.BLACK;

	private BufferedImage externalImage;

	private int characterSpacing = 0;

	public FontGenerator(File fontFile, String chars, int size)
			throws IOException {
		super();

		currentSize = size;

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

	public void saveBitMapFont(File file) throws IOException {
		// FIXME
		// ADD CHARSPACING FIELD
		BufferedImage image = createImage();
		FileOutputStream out = new FileOutputStream(file);
		String text = this.text;
		DataOutputStream dataOut = new DataOutputStream(out);
		// write the line height plus 2 pixels padding
		int size = (int)currentSize + 2;
		dataOut.writeInt(size);
		// write whether there are mixed case characters included:
		boolean hasMixedCase = true;
		dataOut.writeBoolean(hasMixedCase);
		// write the character-map:
		String charMap = text;
		dataOut.writeUTF(charMap);
		// write the widths of each character:
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		FontRenderContext fc = g.getFontRenderContext();
		for (int i = 0; i < text.length(); i++) {
			String substring = text.substring(i, i + 1);
			Rectangle2D bounds = this.derivedFont
					.getStringBounds(substring, fc);
			dataOut
					.writeByte((int) (bounds.getWidth() + this.characterSpacing));
		}
		// write the image itself:
		ImageIO.write(image, "png", out);
		out.close();
	}

	public BufferedImage createImage() {
		if (this.externalImage != null) {
			return this.externalImage;
		}
		String text = this.text;
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

		double height = (fontMetrics.getHeight()); // +
		// fontMetrics.getMaxDescent()
		// );
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
		int y = (int) (fontMetrics.getHeight() - fontMetrics.getMaxDescent());
		int x = 0;

		for (int i = 0; i < characters.length; i++) {
			g.drawChars(characters, i, 1, x, y);
			x += (int) fontMetrics.stringWidth(text.substring(i, i + 1))
					+ this.characterSpacing;
		}
		return image;
	}

	public void setImage(File file) throws IOException {
		this.externalImage = ImageIO.read(file);
	}
}
