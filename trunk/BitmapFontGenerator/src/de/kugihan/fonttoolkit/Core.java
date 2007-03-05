/**
 * DictionaryForMIDs
 * 
 * Core.java - Created by Sean Kernohan (webmaster@seankernohan.com)
 */
package de.kugihan.fonttoolkit;

import java.io.*;

public class Core extends Thread {
	private String theCharacters = "?"; // must contain question mark

	private Callback cb;

	private File inputFile;

	private File outputFile;

	private File dictionaryDirectory;
	
	private int size;

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

	public Core(File inputFile, File outputFile, File directory, Callback cb, int size) {
		this.size = size;
		this.cb = cb;
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.dictionaryDirectory = directory;
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
		FontGenerator fg = new FontGenerator(inputFile, theCharacters, size);
		fg.saveBitMapFont(outputFile);
	}
}
