/*
 DictionaryForMIDs - a free multi-language dictionary for mobile devices.
 Copyright (C) 2006 Sean Kernohan (webmaster@seankernohan.com)

 GPL applies - see file COPYING for copyright statement.
 */
package de.kugihan.dictionaryformids.hmi_java_me.mainform.bitmapfont;

import javax.microedition.lcdui.*;

import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileAccessHandler;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import java.io.InputStream;
import java.io.DataInputStream;


public class BitmapFontCanvas extends CustomItem {

	private BitmapFont font;

	private BitmapFontViewer viewer;

	private StringColourItemText stringItem;
	
	private String bitmapFontSize;
	
	private static String bitmapFontSizesFile = "/dictionary/fonts/sizes.bmf";
	
	private static String[] availableSizes;
	
	private static String defaultSize;

	private int lineHeightPixels;

	private int totalHeightPixels;

	private int maxWidthPixels;
	
	public BitmapFontCanvas(StringColourItemText input, String fontSize, int maxWidthPixels, boolean colouredMode) {
		super(null);
		this.maxWidthPixels = maxWidthPixels;
		this.stringItem = input;
		this.bitmapFontSize = fontSize;
		try {
			String fontDir;
			fontDir = "/dictionary/fonts/" + bitmapFontSize + "/";
			font = BitmapFont.getInstance(fontDir, fontDir + "font.bmf");
			font.loadFont();
			font.loadChars();
			viewer = font.getViewer(stringItem, maxWidthPixels, colouredMode);
			lineHeightPixels = font.getLineHeightPixels();
			totalHeightPixels = viewer.getLinesPainted() * lineHeightPixels;
		} catch (Exception e) {
		}
	}
	
	public static boolean bitmapFontExists() {
		boolean fileExists = false;	
		try {
			fileExists = FileAccessHandler.getDictionaryDataFileISAccess().fileExists(bitmapFontSizesFile);
		} catch (Exception e){
			return false;
		}		
		return fileExists;			
	}
	
	public static String[] getBitmapFontSizes() {
		if (availableSizes == null) loadSizes();
		return availableSizes;
	}
	
	public static String getDefaultSize() {
		if (defaultSize == null) loadSizes();
		return defaultSize;
	}
	
	private static void loadSizes(){
		InputStream in = null;
		try{
			in = FileAccessHandler.getDictionaryDataFileISAccess().getInputStream(bitmapFontSizesFile);			
			DataInputStream dataIn = new DataInputStream(in);
			int bitmapFontSizesTotalNumber = dataIn.readInt();
			availableSizes = new String[bitmapFontSizesTotalNumber];			
			for (int i = 0; i < bitmapFontSizesTotalNumber; i++){				
				availableSizes[i] = Integer.toString(dataIn.readInt());
			}
			defaultSize = Integer.toString(dataIn.readInt());
			dataIn.close();
		}catch (Exception e) {
			System.out.println("Unable to load bitmap-font-sizes-file ["
					+ bitmapFontSizesFile + "]" + e);
		}
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
