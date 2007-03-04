/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.general;

import java.io.File;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileAccessHandler;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileDfMInputStreamAccess;

public class UtilWin extends Util {

	protected void outputMessage(String message) {
		System.out.println(message);
	}

	public static final String propertyFileName = "DictionaryForMIDs.properties";
	
	public String buildPropertyFileName(String propertyPath) {
		return propertyPath + "/" + propertyFileName;
	}
	
	public boolean readProperties(String propertyPath, 
			                      boolean initDictionaryGenerationValues) 
			throws DictionaryException {
		boolean propertyFileAccessible = new File(buildPropertyFileName(propertyPath)).canRead();
		if (propertyFileAccessible) {
			FileDfMInputStreamAccess dfmInputStreamObj = new FileDfMInputStreamAccess(propertyPath);
			FileAccessHandler.setDictionaryDataFileISAccess(dfmInputStreamObj);
			DictionaryDataFile.useStandardPath = false;
			DictionaryDataFile.initValues(initDictionaryGenerationValues);
		}
		return propertyFileAccessible;
	}
	
}
