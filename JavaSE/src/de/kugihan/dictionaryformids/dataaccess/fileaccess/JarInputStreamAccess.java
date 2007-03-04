package de.kugihan.dictionaryformids.dataaccess.fileaccess;

import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

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
			if (fileName.charAt(0) == '/') {
				fileName = fileName.substring(1);
			}
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
}
