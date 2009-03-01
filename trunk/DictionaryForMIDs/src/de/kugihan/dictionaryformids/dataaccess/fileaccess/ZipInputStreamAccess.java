/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2007 Zz85
Modified by Gert Nuber

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.dataaccess.fileaccess;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import de.kugihan.dictionaryformids.dataaccess.zip.ZipEntry;
import de.kugihan.dictionaryformids.dataaccess.zip.ZipInputStream;
import de.kugihan.dictionaryformids.general.CouldNotOpenFileException;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;

/*
 * Note: this class needs to be optimized with the usage of the ZipFile class.
 * However the ZipFile class is until now not available for Java ME, because
 * it uses the File class which does not exist in Java ME. However it could
 * easily be adapted to Java ME, see als web page on 'loadable dictionaries'.
 */
public class ZipInputStreamAccess extends DfMInputStreamAccess {

	FileConnection fileConnector = null;
	protected String zipfile = null;
	
	public ZipInputStreamAccess(String zipfile) {
		this.zipfile = zipfile;
	}
	
	private InputStream openFileConnectionStream() throws IOException {
		fileConnector  = (FileConnection) Connector.open(zipfile, Connector.READ);
	    InputStream fcStream = fileConnector.openInputStream();
		fileConnector.close();  
		return fcStream;
	}
	
	private ZipInputStream openZipStream() throws IOException {
		 return new ZipInputStream(openFileConnectionStream());
	}
	
	public InputStream getInputStreamInternal(String fileName)
			throws DictionaryException {
		ZipInputStream zipStream;
    	ZipEntry zippedFile;
		try {
	    	zipStream = openZipStream(); 
	    	while((zippedFile = zipStream.getNextEntry()) != null) {
	    		if (fileName.equals("/"+zippedFile.getName())) {
	    			break;
	    		} 
	    	}
		} catch (IOException ioe) {
			 throw new CouldNotOpenFileException(ioe);
		}
		if (zippedFile == null)
			return null;
		else
			return zipStream;
	}

	public InputStream getInputStream(String fileName)
			throws DictionaryException {
		InputStream zipStream = getInputStreamInternal(fileName);
		if (zipStream == null) {
			Util.getUtil().log("File not found:" + fileName, Util.logLevel3);
			throw new CouldNotOpenFileException("Resource file could not be opened: " + fileName);
		}		
		return zipStream;
	}
	
	public boolean fileExists(String fileName) 
			throws DictionaryException {
    	return (getInputStreamInternal(fileName) != null);
	}

}
