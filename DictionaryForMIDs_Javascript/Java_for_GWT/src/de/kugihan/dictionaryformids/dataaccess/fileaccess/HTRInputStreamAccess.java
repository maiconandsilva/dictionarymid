/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess.fileaccess;

import java.io.*;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class HTRInputStreamAccess extends DfMInputStreamAccess  {

	public InputStream getInputStream(String fileName) throws DictionaryException {
	return new HTRInputStream(fileName);
}

	public boolean fileExists(String fileName) throws DictionaryException {
	return HTRInputStream.fileExists(fileName);
}

}
