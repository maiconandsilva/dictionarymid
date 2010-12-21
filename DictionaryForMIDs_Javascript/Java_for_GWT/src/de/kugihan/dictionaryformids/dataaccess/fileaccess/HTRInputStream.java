package de.kugihan.dictionaryformids.dataaccess.fileaccess;

import java.io.*;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class HTRInputStream extends InputStream {

	public final String charsetBinary = "x-user-defined"; // x-user-defined is used for binary data
	
	protected String fileContent;
	protected int currentPosition = 0;
	// public static String baseDirectory = "http://www.kugihan.de/dict/htmlapp/ChiEng_CEDICT";
	public static String baseDirectory = "file:///C:/Projects/DictionaryForMIDs/DictionaryForMIDs_Javascript/Java_for_GWT/war/ChiEng_CEDICT";
	// public static String baseDirectory = "http://dictionarymid.sourceforge.net/htmlApp/ChiEng_CEDICT/ChiEng_CEDICT";


	public HTRInputStream(String fileName) 
				throws DictionaryException {
		fileContent = readFile(fileName, 
							   charsetBinary);
	}

	public static String readFile(String fileName,
	                              String charset) 
				throws DictionaryException {
		String url = buildURLFromFileName(fileName);
		String readFileContent = readFileJS(url, charset);
		if (readFileContent == null) {
			throw new DictionaryException("File data could not be read via XMLHttpRequest");
		}
		return readFileContent;
	}

	protected static native String readFileJS(String url,
	                                       String charset) /*-{
		var req = new XMLHttpRequest();  
		req.open('GET', url, false);  
		var mimeType = 'text/plain; charset=' + charset;
		req.overrideMimeType(mimeType);  
		try {
			req.send(null);  
		}
		catch (e) {
			alert("Exception bei XMLHttpRequest.send: "+e);
		}
		//if (req.status != 200) return null; // bei http  
		if (req.status != 0) return null;  // bei lokalem Filezugriff
		return req.responseText; 
		}-*/;

	public int read() throws IOException {
		if (currentPosition == fileContent.length())
			return -1;
		int character = fileContent.charAt(currentPosition) & 0xFF;
		++currentPosition;
		return character;
	}
	
	public long skip(long n) {
		long actualSkipped;
		if (n < 0) {
			actualSkipped = 0;
		}
		else if (currentPosition + n >= fileContent.length()) {
			actualSkipped = fileContent.length() - currentPosition;
		} 
		else {
			actualSkipped = n;
		}
		currentPosition += actualSkipped;
		return actualSkipped;
	}

	public static boolean fileExists(String fileName) {
		String url = buildURLFromFileName(fileName);
		return fileExistsJS(url);
	}

	public static native boolean fileExistsJS(String url)	/*-{
		var req = new XMLHttpRequest();  
		req.open('GET', url, false);  
		req.send(null);  
		if (req.status != 200) 
			return true;  
		return false; 
		}-*/;

	protected static String buildURLFromFileName(String fileName) {
		return baseDirectory + fileName;
	}
}