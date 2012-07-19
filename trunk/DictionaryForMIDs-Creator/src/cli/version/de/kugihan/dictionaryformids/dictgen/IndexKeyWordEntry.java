/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package cli.version.de.kugihan.dictionaryformids.dictgen;

import de.kugihan.dictionaryformids.translation.SearchIndicator;


public class IndexKeyWordEntry {
	public String 		   keyWord;
	public SearchIndicator searchIndicator;
	
	public IndexKeyWordEntry(String          keyWordParam, 
			                 SearchIndicator searchIndicatorParam) {
		keyWord = keyWordParam;
		searchIndicator = searchIndicatorParam;
	}
}
