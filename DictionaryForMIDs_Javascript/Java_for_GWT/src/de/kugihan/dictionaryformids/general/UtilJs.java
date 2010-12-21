/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.general;


public class UtilJs extends Util {

	public static void memCheck(String message) {

}

	protected native void outputMessage(String message) /*-{
  $wnd.outputMessage(message);
}-*/;


	public String getApplicationVersionString() throws DictionaryException {
		return "todojs/development";
	}
	
	
}
