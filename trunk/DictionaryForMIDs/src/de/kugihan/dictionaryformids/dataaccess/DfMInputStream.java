/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess;

import java.io.InputStream;

import de.kugihan.dictionaryformids.general.DictionaryException;

public abstract class DfMInputStream {

	private static DfMInputStream dfmInputStreamObj; 

	public static DfMInputStream getDfMInputStream() {
		return dfmInputStreamObj;
	}

	public static void setDfMInputStream(DfMInputStream newDfMInputStreamObj) {
		dfmInputStreamObj = newDfMInputStreamObj;
	}

	public abstract InputStream getInputStream(String fileName) throws DictionaryException;

}
