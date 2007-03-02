/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.kugihan.dictionaryformids.dataaccess.DfMInputStream;
import de.kugihan.dictionaryformids.general.CouldNotOpenFileException;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class FileDfMInputStream extends DfMInputStream {

	public InputStream getInputStream(String fileName) throws DictionaryException {
		try {
			return new FileInputStream(fileName);
		}
		catch (FileNotFoundException e) {
			throw new CouldNotOpenFileException(e);
		}
	}
}
