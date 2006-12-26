/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.translation;
import java.util.*;

public class TranslationResult {

	public boolean translationFound; // true if a valid translation was found in the dictionary
	public long    numberOfHits;  // indicates how many times a given search expression was found in the dictionary
	public long    executionTime; // in ms
	
	public boolean translationBreakOccurred = false; // true if the translation was aborted
	public int     translationBreakReason; // reason why translation was aborted
	
	public Vector  translations = new Vector();  // Vector of SingleTranslation
	
	public final static int BreakReasonCancelReceived = 1; 
	public final static int BreakReasonMaxExecutionTimeReached = 2; 
	public final static int BreakReasonCancelMaxNrOfHitsReached = 3; 
}

