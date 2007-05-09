package de.kugihan.dictionaryformids.dataaccess.fileaccess;

import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import de.kugihan.dictionaryformids.general.DictionaryException;

/**
  * DictionaryForMIDs - a free multi-language dictionary for mobile devices.
  * Copyright (C) 2005 Stefan Martens (stefan@stefan1200.de)
  * 
  * GPL applies - see file COPYING for copyright statement.
*/

public class JarInputStreamAccess extends DfMInputStreamAccess
{		
	
	protected JarFile jar;
	
	public  JarInputStreamAccess (JarFile jarParam) {
		jar = jarParam; 
	}
	
	public InputStream getInputStream(String fileName)
	{
		try
		{
			fileName = buildFileName(fileName);
			ZipEntry tmp = jar.getEntry(fileName);
			if (tmp != null)
			{
				return jar.getInputStream(tmp);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean fileExists(String fileName) 
	{
		try
		{
			fileName = buildFileName(fileName);
			ZipEntry tmp = jar.getEntry(fileName);
			if (tmp != null)
			{
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	protected String buildFileName(String fileName) {
		if (fileName.charAt(0) == '/') {
			fileName = fileName.substring(1);
		}
		return fileName;
	}
	
}
