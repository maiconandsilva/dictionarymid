/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.general;

import java.io.*;
import javax.microedition.lcdui.*;

import de.kugihan.dictionaryformids.dataaccess.DfMInputStream;
import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.hmi_j2me.DictionaryForMIDs;
import de.kugihan.dictionaryformids.hmi_j2me.DictionarySettings;


public class UtilMid extends Util {

	private static Form logForm;

	private Properties dictionaryForMIDsProperties;
	
	private static boolean oldStyleEncoding;
	public  static final String  oldStyleISO88591 = "ISO8859_1";
		
	public void openProperties() throws DictionaryException {
		dictionaryForMIDsProperties = new Properties();
		String propertyFileName = DictionaryDataFile.getPathDataFiles() + DictionaryDataFile.propertyFileName;
		InputStream propertyStream;
		try {
			propertyStream = DfMInputStream.getDfMInputStream().getInputStream(propertyFileName);
		}
		catch (DictionaryException exception) {
			throw new CouldNotOpenPropertyFileException();
		}
		try {
			dictionaryForMIDsProperties.load(propertyStream);
		}
		catch (IOException exception) {
			throw new DictionaryException("Property file could not be read " + DictionaryDataFile.propertyFileName);
		}
	}

	public void closeProperties() throws DictionaryException {
		dictionaryForMIDsProperties = null;
	}

	public String getDictionaryProperty(String propertyName) {
		return dictionaryForMIDsProperties.getProperty(propertyName);
	}	
	
	public static void setLogForm(Form logFormParam) {
		logForm = logFormParam;
	}

	protected void outputMessage(String message) {
		StringItem outputMessageItem = new StringItem(null, message);
		outputMessageItem.setLayout(Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_2);
		logForm.append(outputMessageItem);
	}

	// old CLDC 1.0 phones sometimes use the string ISO8859_1 instead of ISO-8859-1
	public static void determineCharEncoding() throws DictionaryException {
		oldStyleEncoding = false;
		if (! DictionarySettings.isCldc11()) {
			String supportedEncoding = System.getProperty("microedition.encoding");
			if (supportedEncoding == null) {
				throw new DictionaryException("System property microedition.encoding could not be read");
			}
			if (supportedEncoding.equals(oldStyleISO88591)) {
				oldStyleEncoding = true;
			}
		}
	}
	
	public static void setDeviceCharEncoding(String charEncoding) {
		if (charEncoding.equals("ISO-8859-1")) {
			if (oldStyleEncoding) {
				charEncoding = oldStyleISO88591;
			}
		}
	}
}
