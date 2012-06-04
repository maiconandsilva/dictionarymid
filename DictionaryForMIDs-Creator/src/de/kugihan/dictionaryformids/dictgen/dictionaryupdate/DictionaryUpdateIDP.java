/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;

import de.kugihan.dictionaryformids.dictgen.IndexKeyWordEntry;
import de.kugihan.dictionaryformids.general.DictionaryException;


public class DictionaryUpdateIDP extends DictionaryUpdate {

	boolean idpIndicatorCharacterDetected;
	
	public String updateDictionaryExpression(String idpString) 
				throws DictionaryException {
		String idpIndicationCharacters = "/\\~.^,";
		idpIndicatorCharacterDetected = false;
		StringBuffer convertedIDPString = new StringBuffer();
		if (idpString.length() > 1) {
			char currentCharacter = ' ';
			char nextCharacter = ' ';
			for (int charPos = 0; charPos < idpString.length() -1; ++charPos) {
				boolean currentCharacterIsUpperCase = Character.isUpperCase(idpString.charAt(charPos)); 
				currentCharacter = Character.toLowerCase(idpString.charAt(charPos)); 
				nextCharacter = idpString.charAt(charPos+1); 
				if (nextCharacter == '/') {
					if (currentCharacter == 'a')
						currentCharacter = replaceCharacter('á');
					else if (currentCharacter == 'e')
						currentCharacter = replaceCharacter('é');
					else if (currentCharacter == 'i')
						currentCharacter = replaceCharacter('í');
					else if (currentCharacter == 'o')
						currentCharacter = replaceCharacter('ó');
					else if (currentCharacter == 'u')
						currentCharacter = replaceCharacter('ú');
				}
				else if (nextCharacter == '\\') {
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
				else if (nextCharacter == '^') {
					if (currentCharacter == 'a')
						currentCharacter = replaceCharacter('â');
					else if (currentCharacter == 'e')
						currentCharacter = replaceCharacter('ê');
					else if (currentCharacter == 'i')
						currentCharacter = replaceCharacter('î');
					else if (currentCharacter == 'o')
						currentCharacter = replaceCharacter('ô');
					else if (currentCharacter == 'u')
						currentCharacter = replaceCharacter('û');
				}
				else if (nextCharacter == '~') {
					if (currentCharacter == 'a')
						currentCharacter = replaceCharacter('ã');
					else if (currentCharacter == 'o')
						currentCharacter = replaceCharacter('õ');
					else if (currentCharacter == 'u')
						currentCharacter = replaceCharacter('ũ');
					else if (currentCharacter == 'n')
						currentCharacter = replaceCharacter('ñ');
				}
				else if (nextCharacter == '.') {
					if (currentCharacter == 'a')
						currentCharacter = replaceCharacter('ä');
					else if (currentCharacter == 'o')
						currentCharacter = replaceCharacter('ö');
					else if (currentCharacter == 'u')
						currentCharacter = replaceCharacter('ü');
				}
				else if (nextCharacter == ',') {
					if (currentCharacter == 'c')
						currentCharacter = replaceCharacter('ç');
				}
				if ((idpIndicationCharacters.indexOf(currentCharacter) != -1) && 
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
			if (idpIndicationCharacters.indexOf(nextCharacter) == -1)
				convertedIDPString.append(nextCharacter);
		}
		else {
			convertedIDPString = new StringBuffer(idpString);
		}
		return convertedIDPString.toString();
	}

	protected char replaceCharacter(char newChar)  {
		idpIndicatorCharacterDetected = true;
		return newChar;
	}

	
	public String removeNonSearchParts(String expression) {
		return DictionaryUpdateLib.removeBrackets(expression);
	}
		
	public void updateKeyWordVector(Vector keyWordVector) 
				throws DictionaryException {
		int elementCount = 0;
		if (keyWordVector.size() > 1) {
			do {
				String keyWord = ((IndexKeyWordEntry) keyWordVector.elementAt(elementCount)).keyWord;
				// remove words that in general appear in the IDP dictionaries but are not useful for searches
				if (((keyWord.length() < 2) && (keyWord.compareTo("a") != 0))||
					(keyWord.equalsIgnoreCase("el")) ||
					(keyWord.equalsIgnoreCase("la"))) {
					keyWordVector.removeElementAt(elementCount);
				}
				else {
					++elementCount;
				}
			}
			while (elementCount < keyWordVector.size());
		}
	}
}
