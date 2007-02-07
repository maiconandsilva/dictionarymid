/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.translation;
import java.util.*;

public class TranslationResult {

	public long    executionTime; 			 // in ms
	public boolean translationBreakOccurred = false; // true if the translation was aborted
	public int     translationBreakReason; // reason why translation was aborted
	
	protected Vector  translations = new Vector();  // Vector of SingleTranslation
	
	public final static int BreakReasonCancelReceived = 1; 
	public final static int BreakReasonMaxExecutionTimeReached = 2; 
	public final static int BreakReasonCancelMaxNrOfHitsReached = 3; 
	
	public SingleTranslation getTranslationAt(int index) {
		return (SingleTranslation) translations.elementAt(index);
	}

	public Enumeration getAllTranslations() {
		return translations.elements();
	}

	public int numberOfFoundTranslations() {
		return translations.size();
	}

	public boolean translationFound() {
		return numberOfFoundTranslations() >0;
	}

	public void addTranslation(SingleTranslation newSingleTranslation) {
		translations.addElement(newSingleTranslation);
	}

	public void insertTranslationAt(SingleTranslation newSingleTranslation, int index) {
		translations.insertElementAt(newSingleTranslation, index);
	}

}

