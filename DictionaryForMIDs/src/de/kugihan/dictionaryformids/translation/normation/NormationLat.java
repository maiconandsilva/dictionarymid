/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.translation.normation;

public class NormationLat extends Normation {
	
	static final String valuesA = "àáâãäåæāăą";
	static final String valuesU = "ùúûüũūŭůű";
	static final String valuesO = "òóôõöøōŏ";
	static final String valuesE = "èéêëēĕėęě";
	static final String valuesI = "ìíîïĩīĭ";
	static final String valuesN = "ñň";
	static final String valuesC = "çćĉċč";
	
	static final String valuesD = "ď";
	static final String valuesL = "ĺľ";
	static final String valuesR = "ŕř";
	static final String valuesS = "šś";
	static final String valuesT = "ť";
	static final String valuesY = "ý";
	static final String valuesZ = "ž";
	
	public StringBuffer normateWord(StringBuffer nonNormatedWord,
                                    boolean      fromUserInput) {
		StringBuffer defaultNormatedWord = NormationLib.defaultNormation(nonNormatedWord, fromUserInput);
		StringBuffer normatedWord = new StringBuffer();		
		for (int charPos = 0; charPos < defaultNormatedWord.length(); ++charPos) {
			char character = defaultNormatedWord.charAt(charPos); 
			if (valuesA.indexOf(character) != -1) {
				normatedWord.append("a");
			}
			else if (valuesU.indexOf(character) != -1) {
				normatedWord.append("u");
			}
			else if (valuesO.indexOf(character) != -1) {
				normatedWord.append("o");
			}
			else if (valuesC.indexOf(character) != -1) {
				normatedWord.append("c");
			}
			else if (valuesE.indexOf(character) != -1) {
				normatedWord.append("e");
			}
			else if (valuesI.indexOf(character) != -1) {
				normatedWord.append("i");
			}
			else if (valuesN.indexOf(character) != -1) {
				normatedWord.append("n");
			}
			else if (valuesD.indexOf(character) != -1) {
				normatedWord.append("d");
			}
			else if (valuesL.indexOf(character) != -1) {
				normatedWord.append("l");
			}
			else if (valuesR.indexOf(character) != -1) {
				normatedWord.append("r");
			}
			else if (valuesS.indexOf(character) != -1) {
				normatedWord.append("s");
			}
			else if (valuesT.indexOf(character) != -1) {
				normatedWord.append("t");
			}
			else if (valuesY.indexOf(character) != -1) {
				normatedWord.append("y");
			}
			else if (valuesZ.indexOf(character) != -1) {
				normatedWord.append("z");
			}
			else {
				normatedWord.append(character);
			}
		}
		return normatedWord;
	}
	
}
