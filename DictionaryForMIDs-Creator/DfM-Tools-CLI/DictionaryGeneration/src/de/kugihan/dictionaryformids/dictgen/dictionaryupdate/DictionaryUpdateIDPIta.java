/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionaryUpdateIDPIta extends DictionaryUpdateIDP {
	
	public String updateDictionaryExpression(String idpString) 
				throws DictionaryException {
		String updatedIDPString = super.updateDictionaryExpression(idpString);
		idpIndicatorCharacterDetected = false;
		StringBuffer convertedIDPString = new StringBuffer();
		if (updatedIDPString.length() > 1) {
			char currentCharacter = ' ';
			char nextCharacter = ' ';
			for (int charPos = 0; charPos < updatedIDPString.length() -1; ++charPos) {
				boolean currentCharacterIsUpperCase = Character.isUpperCase(updatedIDPString.charAt(charPos)); 
				currentCharacter = Character.toLowerCase(updatedIDPString.charAt(charPos)); 
				nextCharacter = updatedIDPString.charAt(charPos+1); 
				if (nextCharacter == '\'') {
					if (currentCharacter == 'a')
						currentCharacter = replaceCharacter('à');
					else if (currentCharacter == 'e')
						currentCharacter = replaceCharacter('è');
					else if (currentCharacter == 'i')
						currentCharacter = replaceCharacter('ì');
					else if (currentCharacter == 'o')
						currentCharacter = replaceCharacter('ò');
					else if (currentCharacter == 'u')
						currentCharacter = replaceCharacter('ù');
				}
				if ((currentCharacter == '\'') && 
					 idpIndicatorCharacterDetected) {
					// do nothing
				}
				else {
					// add character
					if (currentCharacterIsUpperCase)
						currentCharacter = Character.toUpperCase(currentCharacter);
					convertedIDPString.append(currentCharacter);
				}
			}
			if ((currentCharacter != '\''))
				convertedIDPString.append(nextCharacter);
		}
		else {
			convertedIDPString = new StringBuffer(updatedIDPString);
		}
		return convertedIDPString.toString();
	}
}
