/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess.fileaccess;
import java.io.InputStream;

import de.kugihan.dictionaryformids.general.CouldNotOpenFileException;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;

public class ResourceDfMInputStreamAccess extends DfMInputStreamAccess {
	
	public InputStream getInputStream(String fileName) 
			throws DictionaryException {
		InputStream resourceInputStream = getClass().getResourceAsStream(fileName);
		if (resourceInputStream == null) {
			Util.getUtil().log("File not found:" + fileName, Util.logLevel3);
			throw new CouldNotOpenFileException("Resource file could not be opened: " + fileName);
		}
		return resourceInputStream;			
	}

	public boolean fileExists(String fileName) throws DictionaryException {
		boolean returnValue;
		InputStream resourceInputStream = getClass().getResourceAsStream(fileName);
		returnValue = (resourceInputStream != null);
		return returnValue;
	}

}
