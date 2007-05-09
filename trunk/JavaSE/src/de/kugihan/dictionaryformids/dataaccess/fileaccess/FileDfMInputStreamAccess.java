/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess.fileaccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.kugihan.dictionaryformids.dataaccess.fileaccess.DfMInputStreamAccess;
import de.kugihan.dictionaryformids.general.CouldNotOpenFileException;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class FileDfMInputStreamAccess extends DfMInputStreamAccess {

	protected String baseDirectory;
	
	public FileDfMInputStreamAccess() {
		baseDirectory = ""; 
	}
	public FileDfMInputStreamAccess(String baseDirectoryParam) {
		baseDirectory = baseDirectoryParam; 
	}
	
	public InputStream getInputStream(String fileName) throws DictionaryException {
		try {
			String fileLocation = getFileLocation(fileName); 
			return new FileInputStream(fileLocation);
		}
		catch (FileNotFoundException e) {
			throw new CouldNotOpenFileException(e);
		}
	}

	public boolean fileExists(String fileName) {
		boolean returnValue = true;
		try {
			String fileLocation = getFileLocation(fileName); 
			new FileInputStream(fileLocation);
		}
		catch (FileNotFoundException e) {
			returnValue = false;
		}
		return returnValue;
	}

	protected String getFileLocation(String fileName) {
		return baseDirectory + fileName;
	}

}
