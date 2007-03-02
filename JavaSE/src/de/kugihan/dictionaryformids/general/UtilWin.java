/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.general;

import java.util.*;
import java.io.*;

import de.kugihan.dictionaryformids.general.Util;

public class UtilWin extends Util {

	protected void outputMessage(String message) {
		System.out.println(message);
	}

	protected String propertyPath;
	
	public static final String propertyFileName = "DictionaryForMIDs.properties";
	
	public void setPropertyPath(String propertyPathParam) {
		propertyPath = propertyPathParam;
	}

	public String buildPropertyFileName() {
		return propertyPath + "/" + propertyFileName;
	}
	
	public String getDictionaryProperty(String propertyName) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(buildPropertyFileName()));
		}
		catch (Exception e) {
			log(e);
		} 
		String propertyValue = prop.getProperty(propertyName);
	    return propertyValue;
	}
}
