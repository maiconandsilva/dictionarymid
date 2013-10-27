/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.general;


public class UtilJs extends Util {

	public static void memCheck(String message) {
		// not implemented
    }

	protected native void outputMessage(String message) /*-{
	    UtilJs.outputMessage(message);
	}-*/;

	public String getApplicationVersionString() throws DictionaryException {
		return "todojs/development";
	}

	public native void exportStaticClasses() /*-{
		function outputMessageToConsole(message) {
			console.log(message);
		}
	
	    $wnd.UtilJs = new Object();
		var utilJs = $wnd.UtilJs;
		utilJs.setLogLevel = $entry(this.@de.kugihan.dictionaryformids.general.Util::setLogLevel(*));
		utilJs.log = $entry(this.@de.kugihan.dictionaryformids.general.Util::log(Ljava/lang/String;I));
		UtilJs.outputMessage = outputMessageToConsole;
    }-*/;
	
}
