/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de), Erik Peterson (http://www.mandarintools.com/)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dictgen.DictionaryGeneration;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;

public class DictionaryUpdateCEDICTChi extends DictionaryUpdate {

	/*
	 * The input looks like
	 * 安康 安康 [01an1 kang1],good health
	 * [01 is the start content delimiter 
	 */
	
	final String startDelimiter = "[01";
	final String endDelimiter = "]";
	
	// replaces the pronounciation part which is pinyin with tone numbers in the source
	// with accented pinyin, by using Erik Peterson's conversion routines
	public String updateDictionaryExpression(String dictionaryExpressionParam) throws DictionaryException {
		String dictionaryExpression = super.updateDictionaryExpression(dictionaryExpressionParam);
		String updatedExpression;
		int posStartDelimiter = dictionaryExpression.indexOf(startDelimiter);
		int posEndDelimiter = -1;
		if (posStartDelimiter != -1)
			posEndDelimiter = dictionaryExpression.toString().indexOf(endDelimiter, posStartDelimiter);
		if ((posStartDelimiter != -1) && (posEndDelimiter > posStartDelimiter)) {
			String pronounciationToneNumbers = dictionaryExpression.substring(posStartDelimiter + startDelimiter.length(), posEndDelimiter); 
			String pronounciationAccented = addTones(pronounciationToneNumbers);
			updatedExpression = dictionaryExpression.substring(0, posStartDelimiter) +
							    DictionaryUpdateLib.setContentPronounciation("[" + pronounciationAccented + "]",
							    										     1);
		}
		else {
			updatedExpression = dictionaryExpression;
		}
		return updatedExpression;
	}
	
	// Creates the keyWordVector for
	// a) the pronounciation part which is in square brackets: 
	//    - one time with tone numbers 
	//    - one time without tone numbers
	//    - one time in the accented version using Erik's conversion routines  
	// b) for the Chinese expression
	public void addKeyWordsSplitUpToKeyWordVector(String expression, 
												  Vector keyWordVector) 
				throws DictionaryException {
		int posStartDelimiter = expression.indexOf(startDelimiter);
		int posEndDelimiter = -1; 
		if (posStartDelimiter != -1)
			posEndDelimiter = expression.toString().indexOf(endDelimiter, posStartDelimiter);
		String chineseExpression;
		if ((posStartDelimiter != -1) && (posEndDelimiter > posStartDelimiter)) {
			String pronounciationExpression = expression.substring(posStartDelimiter + startDelimiter.length(), posEndDelimiter);
			chineseExpression = expression.substring(0, posStartDelimiter);
			if (pronounciationExpression.length() > 0) {
				DictionaryUpdateLib.addKeyWordExpressions(pronounciationExpression, keyWordVector);
				String pronounciationWithoutNumbers = removeNumbers(pronounciationExpression);
				DictionaryUpdateLib.addKeyWordExpressions(pronounciationWithoutNumbers, keyWordVector);
				String pronounciationAccented = addTones(pronounciationExpression);
				DictionaryUpdateLib.addKeyWordExpressions(pronounciationAccented, keyWordVector);
			}
		}
		else {
			chineseExpression = expression;
		}
		StringBuffer chineseExpressionWithoutContentDelimiters = 
					new StringBuffer(DictionaryGeneration.removeContentDelimiters(chineseExpression, indexLanguage));
		Util.filterSuperflousWhitespaces(chineseExpressionWithoutContentDelimiters);
		if (chineseExpressionWithoutContentDelimiters.length() > 0) {
			DictionaryUpdateLib.addKeyWordExpressions(chineseExpressionWithoutContentDelimiters.toString(), keyWordVector);
		}
	}
	
	// compared to DictionaryUpdate.createKeyWordsExpression no call to DictionaryGeneration.removeContentDelimiters
	// is done. This may have to be changed in the future in order to support more than one contents.
	public String createKeyWordsExpression(String expression)  
				throws DictionaryException {
		String keyWordsExpression;
		if (DictionaryDataFile.dictionaryGenerationOmitParFromIndex) {
			keyWordsExpression = expression;
		}
		else {
			keyWordsExpression = updateDictionaryExpression(expression);
		}
		String keyWordsExpressionWithoutContentDelimiters = keyWordsExpression;
		StringBuffer keyWordsExpressionWithoutSeparatorChars = new StringBuffer(keyWordsExpressionWithoutContentDelimiters); 
		Util.getUtil().replaceFieldAndLineSeparatorChars(keyWordsExpressionWithoutSeparatorChars);
		String keyWordsCleanedUp = removeNonSearchParts(keyWordsExpressionWithoutSeparatorChars.toString());
		return keyWordsCleanedUp;
	}

	protected String removeNumbers(String expression) {
		StringBuffer output = new StringBuffer();
		for (int pos = 0; pos < expression.length(); ++pos) {
			char character = expression.charAt(pos);
			if (! Character.isDigit(character)) {
				output.append(character);
			}
		}
		return output.toString();
	}

	
	/*
	 * The code below comes from Erik Peterson (http://www.mandarintools.com/)
	 */
    public static String addTones(String withnumbers) {
    	StringBuffer scratch = new StringBuffer(withnumbers);
    	int index, oldindex;
    	String source, target;
    	String oldtail[];
    	String newtail[];
    	String vowelnums[];
    	String voweltones[];
    	oldtail = new String[] {"ng1", "ng2", "ng3", "ng4", "ng5",
    				"n1", "n2", "n3", "n4", "n5",
    				"r1", "r2", "r3", "r4", "r5",
    				"ao1", "ao2", "ao3", "ao4", "ao5",
    				"ai1", "ai2", "ai3", "ai4", "ao5",
    				"ei1", "ei2", "ei3", "ei4", "ei5",
    				"ou1", "ou2", "ou3", "ou4", "ou5"};

    	newtail = new String[] {"1ng", "2ng", "3ng", "4ng", "5ng",
    				"1n", "2n", "3n", "4n", "5n",
    				"1r", "2r", "3r", "4r", "5r",
    				"a1o", "a2o", "a3o", "a4o", "a5o",
    				"a1i", "a2i", "a3i", "a4i", "a5i",
    				"e1i", "e2i", "e3i", "e4i", "e5i",
    				"o1u", "o2u", "o3u", "o4u", "o5u"};

    	vowelnums = new String[] {"a1", "a2", "a3", "a4", "a5", "e1", "e2", 
    "e3", "e4", "e5",
    				  "i1", "i2", "i3", "i4", "i5", "o1", "o2", "o3", "o4", "o5",
    				  "u1", "u2", "u3", "u4", "u5",
    				  "u:1", "u:2", "u:3", "u:4", "u:5", "u:",
    				  "v1", "v2", "v3", "v4", "v5", "v"};
    	voweltones = new String[]  {"\u0101", "\u00e1", "\u01ce", "\u00e0", "a",
    				    "\u0113", "\u00e9", "\u011b", "\u00e8", "e",
    				    "\u012b", "\u00ed", "\u01d0", "\u00ec", "i",
    				    "\u014d", "\u00f3", "\u01d2", "\u00f2", "o",
    				    "\u016b", "\u00fa", "\u01d4", "\u00f9", "u",
    				    "\u01d6", "\u01d8", "\u01da", "\u01dc", "\u00fc", "\u00fc",
    				    "\u01d6", "\u01d8", "\u01da", "\u01dc", "\u00fc", "\u00fc"};

    	// Move to lower case
    	withnumbers = withnumbers.toLowerCase();

    	// Switch tone number from end of syllable to next to appropriate vowel
    	source = withnumbers;
    	target = withnumbers;  // Have to set it here to satisfy compiler
    	for (int i=0; i < oldtail.length; i++) {
    	    oldindex = index = 0;
    	    target = "";
    	    index = source.indexOf(oldtail[i], oldindex);
    	    while (index >= 0) {
    		target = target + source.substring(oldindex, index);
    		target = target + newtail[i];
    		oldindex = index + oldtail[i].length();
    		index = source.indexOf(oldtail[i], oldindex);
    	    }
    	    target = target + source.substring(oldindex, source.length());
    	    source = target;
    	}

    	// Replace vowel+tone number with vowel with a tone diacritic
    	boolean foundvowel = false;
    	for (int i=0; i < vowelnums.length; i++) {
    	    oldindex = index = 0;
    	    target = "";
    	    index = source.indexOf(vowelnums[i], oldindex);
    	    while (index >= 0) {
    		target = target + source.substring(oldindex, index);
    		target = target + voweltones[i];
    		oldindex = index + vowelnums[i].length();
    		index = source.indexOf(vowelnums[i], oldindex);
    		foundvowel = true;
    	    }
    	    target = target + source.substring(oldindex, source.length());
    	    source = target;
    	}

    	if (!foundvowel) {
    	    target = withnumbers;
    	}
    	return target;
	}
}
