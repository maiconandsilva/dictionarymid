package de.kugihan.dictionaryformids.hmi_java_se;

import java.io.*;
import java.util.zip.*;

import de.kugihan.dictionaryformids.general.*;
import de.kugihan.dictionaryformids.dataaccess.*;

/**
  * DictionaryForMIDs - a free multi-language dictionary for mobile devices.
  * Copyright (C) 2005 Stefan Martens (stefan@stefan1200.de)
  * 
  * GPL applies - see file COPYING for copyright statement.
*/

public class JarCsvFile extends DfMInputStream
{	
/*	public JarCsvFile(String  fileName,
				   char    separatorCharacter,
				   String  charEncoding,
				   int     maxSizeOfFileData) throws DictionaryException
	{
		super(fileName, separatorCharacter, charEncoding, maxSizeOfFileData);
	}
	
	public JarCsvFile(String  fileName,
				   char    separatorCharacter,
				   String  charEncoding,
				   int     maxSizeOfFileData,
				   int     startPosition) throws DictionaryException
	{
		super(fileName, separatorCharacter, charEncoding, maxSizeOfFileData, startPosition);
	}*/
	
	public InputStream getInputStream(String fileName)
	{
		try
		{
			if(DictionaryForSE.jar != null)
			{
				fileName = fileName.substring(1);
				ZipEntry tmp = DictionaryForSE.jar.getEntry(fileName);
				if (tmp != null)
				{
					return DictionaryForSE.jar.getInputStream(tmp);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
