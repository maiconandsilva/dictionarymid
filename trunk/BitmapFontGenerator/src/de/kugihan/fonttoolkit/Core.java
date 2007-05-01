/**
 * DictionaryForMIDs
 * 
 * Core.java - Created by Sean Kernohan (webmaster@seankernohan.com)
 */
package de.kugihan.fonttoolkit;

import java.io.*;

public class Core extends Thread {
	private String theCharacters = "?abcdef*ghi#jklmnopqrstuvwxyzABCDEFGH" +
			"IJKLMNOPQRSTUVWXYZüäüöß.:,;()!0123456789=+-āáǎàēéěèīíǐìōóǒòūúǔùǖǘǚǜ`"; 
	// must contain question mark at the beginning

	private Callback cb;

	private File inputFile;

	private File outputFile;

	private File dictionaryDirectory;
	
	private String fontDirectory;
	
	private int size;
	
	private int clipTop;
	
	private int clipBottom;

	private Core() {
	}

	public void run() {
		yield();
		cb.start();
		try {
			findCharacters();
			process();
			cb.finish();
		} catch (IOException e) {
			cb.throwThreadedFontException();
		} catch (Exception e) {
			cb.throwThreadedDictionaryException();
		}
	}

	public Core(File inputFile, File directory, String fontDir, Callback cb, int size, int top, int bottom) {
		this.size = size;
		this.clipTop = top;
		this.clipBottom = bottom;
		this.cb = cb;
		this.inputFile = inputFile;
		this.dictionaryDirectory = directory;
		this.fontDirectory = fontDir;
	}

	// TODO: validate the file parameters

	private void findCharacters() throws Exception {
		File[] files = dictionaryDirectory.listFiles();
		boolean foundFiles = false;
		for (int i = 0; i < files.length; i++) {
			try {
				if (files[i].getName().substring(
						files[i].getName().length() - 4,
						files[i].getName().length()).equals(".csv")) {
					cb.progressing();
					foundFiles = true;
					processFile(files[i]);
				}
			} catch (StringIndexOutOfBoundsException e) {
				// ignore
			}
		}
		if (!foundFiles)
			throw new Exception();
	}

	private void processFile(File f) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					String currentChar = line.substring(i, i + 1);
					if (theCharacters.indexOf(currentChar) == -1)
						theCharacters += currentChar;
				}
			}
		} catch (Exception e) {
			System.err.println("Fatal error - " + e);
			System.exit(1);
		}
	}

	private void process() throws IOException {
		FontGenerator fg = new FontGenerator(inputFile, theCharacters, size, clipTop, clipBottom);
		fg.saveBitMapFont(fontDirectory);
	}
}
