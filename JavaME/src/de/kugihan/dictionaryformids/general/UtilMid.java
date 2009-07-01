/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.general;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

import de.kugihan.dictionaryformids.hmi_java_me.DictionarySettings;


public class UtilMid extends Util {

	private static Form logForm;

	private static boolean oldStyleEncoding;
	public  static final String  oldStyleISO88591 = "ISO8859_1";
		
	public static void setLogForm(Form logFormParam) {
		logForm = logFormParam;
	}

	protected void outputMessage(String message) {
		StringItem outputMessageItem = new StringItem(null, message);
		outputMessageItem.setLayout(Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_2);
		logForm.append(outputMessageItem);
	}

	// old CLDC 1.0 phones sometimes use the string ISO8859_1 instead of ISO-8859-1
	public void determineCharEncoding() throws DictionaryException {
		oldStyleEncoding = false;
		String supportedEncoding = System.getProperty("microedition.encoding");
		if (supportedEncoding == null) {
			throw new DictionaryException("System property microedition.encoding could not be read");
		}
		if (supportedEncoding.equals(oldStyleISO88591)) {
			oldStyleEncoding = true;
		}
	}
	
	public String getDeviceCharEncoding(String charEncoding) {
		if (charEncoding.equals("ISO-8859-1")) {
			if (oldStyleEncoding) {
				charEncoding = oldStyleISO88591;
			}
		}
		return charEncoding;
	}
}
