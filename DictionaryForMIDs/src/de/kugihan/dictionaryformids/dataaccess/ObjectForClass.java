/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de) 

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess;

import de.kugihan.dictionaryformids.translation.normation.*;
import java.util.Hashtable;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.DictionaryClassNotLoadedException;

public class ObjectForClass {

	final String normationPackageString = "de.kugihan.dictionaryformids.translation.normation.";	

	protected Hashtable  normationClassTable = null;
	
	protected void initValues() {
		normationClassTable = new Hashtable();
		normationClassTable.put("Normation", 		new Normation()		);
		normationClassTable.put("NormationBul", 	new NormationBul() 	);
		normationClassTable.put("NormationCyr1", 	new NormationCyr1() );
		normationClassTable.put("NormationCyr2", 	new NormationCyr2() );
		normationClassTable.put("NormationEng", 	new NormationEng() 	);
		normationClassTable.put("NormationEng2", 	new NormationEng2() );
		normationClassTable.put("NormationEpo", 	new NormationEpo() 	);
		normationClassTable.put("NormationFil", 	new NormationFil() 	);
		normationClassTable.put("NormationGer", 	new NormationGer() 	);
		normationClassTable.put("NormationJpn", 	new NormationJpn() 	);
		normationClassTable.put("NormationLat", 	new NormationLat() 	);
		normationClassTable.put("NormationNor", 	new NormationNor() 	);
		normationClassTable.put("NormationRus", 	new NormationRus() 	);
		normationClassTable.put("NormationRus2", 	new NormationRus2() );
		normationClassTable.put("NormationRusC", 	new NormationRusC() );
		normationClassTable.put("NormationUkr", 	new NormationUkr() 	);
		normationClassTable.put("NormationUkrC", 	new NormationUkrC() );
		normationClassTable.put("NormationVie", 	new NormationVie() 	);
	}
	
	public Object createObjectForClass(String className) {
		Object classObj = null;
		if (normationClassTable == null) {
			initValues();
		}
		if (className.indexOf(normationPackageString) == 0) {
			String classNameWithoutPackagename = className.substring(normationPackageString.length());
			classObj = normationClassTable.get(classNameWithoutPackagename);
		}
		return classObj;
	}
}
