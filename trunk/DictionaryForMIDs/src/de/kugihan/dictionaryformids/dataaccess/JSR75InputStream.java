/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.dataaccess;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.CouldNotOpenFileException;

public class JSR75InputStream extends DfMInputStream {

	public InputStream getInputStream(String fileName)
			throws DictionaryException {
		InputStream streamFromJSR75File;
		try {
			String fileLocation = DictionaryDataFile.dictionaryPath + fileName;
			FileConnection file = (FileConnection) Connector.open(fileLocation, Connector.READ);
		     if (! file.exists())
		         throw new CouldNotOpenFileException("File does not exist: " + fileLocation);
		     else {
		    	 streamFromJSR75File = file.openInputStream(); 
		     }
		 }
		 catch (Exception exception) {
		 	throw new CouldNotOpenFileException(exception);
		 }
		 return streamFromJSR75File;
	}

}
