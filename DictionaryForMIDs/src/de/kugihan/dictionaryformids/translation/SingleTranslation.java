/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.translation;

import de.kugihan.dictionaryformids.general.Util;

public class SingleTranslation {
	public    StringBuffer fromText;
	public    StringBuffer toText;
	protected boolean      foundAtBeginOfExpression;
	protected DirectoryFileLocation directoryFileLocation;
	protected int primarySortNumber;
	protected int internalSortNumber;
	
	public SingleTranslation(StringBuffer          fromTextParam, 
			                 StringBuffer          toTextParam,
			                 boolean      		   foundAtBeginOfExpressionParam,
			                 int 				   primarySortNumberParam,
			                 DirectoryFileLocation directoryFileLocationParam) {
		fromText = fromTextParam;
		toText = toTextParam;
		foundAtBeginOfExpression = foundAtBeginOfExpressionParam;
		primarySortNumber = primarySortNumberParam;
		directoryFileLocation = directoryFileLocationParam;
		determineInternalSortNumber();
	}

	protected void determineInternalSortNumber() {
		// count the number of words in fromText
		int numberOfWords = 1;
		boolean inSeparator = false;
		for (int charCount = 0; charCount < fromText.length(); ++charCount) {
			boolean isSeparatorCharacter = Util.isSeparatorCharacter(fromText.charAt(charCount));
			if (! inSeparator) {
				if (isSeparatorCharacter)
					++numberOfWords;
			}
			inSeparator = isSeparatorCharacter;
		}
		internalSortNumber = numberOfWords;
	}

	int compareTo(SingleTranslation otherSingleTranslation) {
		int compareResult;
		int directoryFileLocationCompared = directoryFileLocation.compareTo(otherSingleTranslation.directoryFileLocation);
		if (directoryFileLocationCompared == 0) {
			// translations are identical
			compareResult = directoryFileLocationCompared;
		}
		else {
			// compare primarySortNumbers
			int primarySortNumberCompared = primarySortNumber - otherSingleTranslation.primarySortNumber;
			if (primarySortNumberCompared == 0) {
				// primarySortNumbers are identical: priorize translations where the expression was found at the beginning
				if (foundAtBeginOfExpression == otherSingleTranslation.foundAtBeginOfExpression) { 
					// no difference in foundAtBeginOfExpression: compare the internalSortNumbers
					int internalSortNumberCompared = internalSortNumber - otherSingleTranslation.internalSortNumber;
					if (internalSortNumberCompared == 0) {
						// cannot determine sorting from sortNumber return sorting from directoryFileLocation
						compareResult = directoryFileLocationCompared;
					}
					else {
						compareResult = internalSortNumberCompared;
					}
				}
				else {
					if (foundAtBeginOfExpression) {
						// expression of this translation was found at beginning: priorize this translation 
						compareResult = -1;
					}
					else {
						// expression of the other translation was found at beginning: priorize other translation 
						compareResult = 1;
					}
				}
			}
			else {
				compareResult = primarySortNumberCompared;
			}
		}
		return compareResult;
	}

}
