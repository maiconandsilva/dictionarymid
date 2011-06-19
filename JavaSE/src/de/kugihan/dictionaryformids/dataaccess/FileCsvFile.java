/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess;

import java.io.*;

import de.kugihan.dictionaryformids.dataaccess.CsvFile;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class FileCsvFile extends CsvFile {

	public FileCsvFile(String  fileName,
			           char    separatorCharacter,
					   String  charEncoding) throws DictionaryException {
		super(fileName, separatorCharacter, charEncoding, (int) new File(fileName).length());
	}

	/*
	 * Seems the following method is not needed any more:
	 * 
	 void writeToFile(boolean crypted) throws DictionaryException {
		try {
			byte[] fileData = ((stringFileStorage) fileStorageObj).stringStorage.getBytes(charEncoding);
			FileOutputStream fileStream = new FileOutputStream(fileName);
			fileStream.write(fileData, 0, fileData.length);
			fileStream.close();
		}
		catch (IOException e) {
			throw new DictionaryException(e);
		}		
	} */

}
