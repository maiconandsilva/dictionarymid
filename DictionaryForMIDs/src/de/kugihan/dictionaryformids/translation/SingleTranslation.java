/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.translation;

public class SingleTranslation {
	public    StringBuffer fromText;
	public    StringBuffer toText;
	protected boolean      foundAtBeginOfExpression;
	protected DirectoryFileLocation directoryFileLocation;
	
	public SingleTranslation(StringBuffer          fromTextParam, 
			                 StringBuffer          toTextParam,
			                 boolean      		   foundAtBeginOfExpressionParam,
			                 DirectoryFileLocation directoryFileLocationParam) {
		fromText = fromTextParam;
		toText = toTextParam;
		foundAtBeginOfExpression = foundAtBeginOfExpressionParam;
		directoryFileLocation = directoryFileLocationParam;
	}
}
