/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.dataaccess.fileaccess;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.CouldNotOpenFileException;

public class JSR75InputStreamAccess extends DfMInputStreamAccess {

	protected String baseDirectory;
	
	public JSR75InputStreamAccess(String baseDirectoryParam) {
		baseDirectory = baseDirectoryParam; 
	}
	
	public InputStream getInputStream(String fileName)
			throws DictionaryException {
		InputStream streamFromJSR75File;
		try {
			String fileLocation = getFileLocation(fileName); 
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

	public boolean fileExists(String fileName) throws DictionaryException {
		boolean returnValue;
		try {
			String fileLocation = getFileLocation(fileName); 
			FileConnection file = (FileConnection) Connector.open(fileLocation, Connector.READ);
			returnValue = file.exists();
			file.close();
		}
		catch (Exception exception) {
		 	throw new CouldNotOpenFileException(exception);
		}
		return returnValue;
	}

	// the following method is simply used to determine whether the fileLocation refers to 
	// a true file or a directory or is not referring to anything accessible at all.
	public static int FileTypeFILE = 1;
	public static int FileTypeDIRECTORY = 2;
	public static int FileTypeNOT_ACCESSIBLE = 3;
	public static int determineFileType(String fileLocation) throws DictionaryException {
		int returnValue;
		if (fileLocation.length() == 0) {
				returnValue = FileTypeNOT_ACCESSIBLE;
		}
		else {
			try {
				FileConnection file = (FileConnection) Connector.open(fileLocation, Connector.READ);
				if (file.exists()) {
					if (file.isDirectory()){
						returnValue = FileTypeDIRECTORY;
					}
					else {
						returnValue = FileTypeFILE;				
					}
				}
				else {
					returnValue = FileTypeNOT_ACCESSIBLE;
				}
				file.close();
			}
			catch (Exception exception) {
				returnValue = FileTypeNOT_ACCESSIBLE;
			}
		}
		return returnValue;
	}

	protected String getFileLocation(String fileName) {
		return baseDirectory + fileName;
	}
}
