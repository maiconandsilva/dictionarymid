/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.translation;
import java.util.Vector;
import de.kugihan.dictionaryformids.dataaccess.*;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.hmi_j2me.*;
import de.kugihan.dictionaryformids.translation.normation.Normation;

public class Translation {

	boolean searchSubExpressionStart = true;
	boolean searchSubExpressionEnd = true;
	
	public Translation(boolean searchSubExpressionStartParam, 
					   boolean searchSubExpressionEndParam) {
		searchSubExpressionStart = searchSubExpressionStartParam;
		searchSubExpressionEnd = searchSubExpressionEndParam;
	}
	
	// 'shortcuts' for the characters with a special meaning for search: 
	public static final char wildcardAnySeriesOfCharacter = Util.wildcardAnySeriesOfCharacter;
	public static final char wildcardAnySingleCharacter = Util.wildcardAnySingleCharacter;
	public static final char noSearchSubExpressionCharacter = Util.noSearchSubExpressionCharacter;
	
	// returns -1 if no wildcard character exists in word:
	static int positionFirstWildcardCharacter(String word) {
		int index;
		int indexWildcardAnySeriesOfCharacters = word.indexOf(wildcardAnySeriesOfCharacter);
		int indexWildcardAnySingleCharacter = word.indexOf(wildcardAnySingleCharacter);
		if ((indexWildcardAnySingleCharacter == -1) &&
			(indexWildcardAnySeriesOfCharacters == -1)) {
			index = -1;
		}
		else if ((indexWildcardAnySingleCharacter == -1) &&
			     (indexWildcardAnySeriesOfCharacters > -1)) {
				index = indexWildcardAnySeriesOfCharacters;
		}
		else if ((indexWildcardAnySingleCharacter > -1) &&
			     (indexWildcardAnySeriesOfCharacters == -1)) {
				index = indexWildcardAnySingleCharacter;
		}
		else if (indexWildcardAnySingleCharacter < indexWildcardAnySeriesOfCharacters) {
			index = indexWildcardAnySingleCharacter;
		}
		else {
			index = indexWildcardAnySeriesOfCharacters;			
		}
		return index;
	}
	static String expressionTillWildcard(String word) {
		return word.substring(0, positionFirstWildcardCharacter(word));
	}

	protected TranslationResult resultOfTranslation;
	
	long startTime;
	
	volatile boolean translationIsCancelled = false;
	
	public TranslationResult getTranslationResult(String toBeTranslatedWord) {

		resultOfTranslation = new TranslationResult();
		startTime = System.currentTimeMillis();
		Util.memCheck("start translation: ");
		
		try { 
			Normation normationObj = DictionaryDataFile.supportedLanguages[DictionarySettings.getInputLanguage()].normationObj;
			// determine search words from the to be translated word
			Vector searchWords = normationObj.searchWord(toBeTranslatedWord);
			// get translation for each searchWord
			for (int wordCount = 0; wordCount < searchWords.size(); ++wordCount) {
				SearchedWord searchWord = (SearchedWord) searchWords.elementAt(wordCount);
				String nonNormatedWord = searchWord.word;
				String toBeTranslatedWordNormated = normationObj.normateWord(new StringBuffer(nonNormatedWord), true).toString();
				if (toBeTranslatedWordNormated.length() > 0) {
					searchTranslationForNormatedWord(toBeTranslatedWordNormated);
				}
			}
		}
		catch (Throwable t) {
			Util.getUtil().log(t);
		}
		long endTime = System.currentTimeMillis();
		resultOfTranslation.executionTime = endTime - startTime;
		Util.memCheck("end translation: ");

		return resultOfTranslation;
	}
	
	public void searchTranslationForNormatedWord(String toBeTranslatedWordNormated) 
						throws DictionaryException  {
		String initialSearchExpression;
		boolean containsWildcard = (positionFirstWildcardCharacter(toBeTranslatedWordNormated) >= 0);
		if (containsWildcard)
			initialSearchExpression = expressionTillWildcard(toBeTranslatedWordNormated);
		else
			initialSearchExpression = toBeTranslatedWordNormated;
		int initialSearchExpressionLength = initialSearchExpression.length();
		
		/* 
		 * read searchlist file
		 */
		String languagePostfix = DictionaryDataFile.supportedLanguages[DictionarySettings.getInputLanguage()].languageFilePostfix;
		String searchListFileName = DictionaryDataFile.getPathDataFiles() + 
								    DictionaryDataFile.prefixSearchListFile + 
									languagePostfix +
									DictionaryDataFile.suffixSearchListFile; 
		CsvFile searchListFile = new CsvFile(searchListFileName,
										     DictionaryDataFile.searchListFileSeparationCharacter,
											 DictionaryDataFile.searchListCharEncoding,
											 DictionaryDataFile.searchListFileMaxSize);
		
		Util.memCheck("searchfile open: ");
		String indexFileNumber = null;
		boolean lastIndexFileSearched = false;
		searchListFile.setPositionBefore(initialSearchExpression);
		while (!searchListFile.endOfDictionaryReached) {
			String wordInIndex = searchListFile.getWord().toString();
			if (containsWildcard) {
				int endOfFirstPartWordInIndex = initialSearchExpressionLength;
				int wordInIndexLength = wordInIndex.length();
				if (wordInIndexLength < initialSearchExpressionLength)
					endOfFirstPartWordInIndex = wordInIndexLength; 
				String firstPartWordInIndex = wordInIndex.substring(0, endOfFirstPartWordInIndex);
				if (firstPartWordInIndex.startsWith(initialSearchExpression)) {
					if (! lastIndexFileSearched) {
						if (indexFileNumber != null) {
							if (searchInIndexFileBreakCondition(toBeTranslatedWordNormated, indexFileNumber))
								break;
						}
					}
					indexFileNumber = searchListFile.getWord().toString();
					if (searchInIndexFileBreakCondition(toBeTranslatedWordNormated, indexFileNumber))
						break;
					lastIndexFileSearched = true;
				}
				else if (firstPartWordInIndex.compareTo(initialSearchExpression) > 0) {
					if (! lastIndexFileSearched)
						if (indexFileNumber != null) {
							if (searchInIndexFileBreakCondition(toBeTranslatedWordNormated, indexFileNumber))
								break;
						}
					break;
				}
				else {
					indexFileNumber = searchListFile.getWord().toString();
				}
			}
			else {
				if (wordInIndex.compareTo(initialSearchExpression) >= 0) {
					// the last index file was the right one
					if (indexFileNumber != null) {
						if (searchInIndexFileBreakCondition(toBeTranslatedWordNormated, indexFileNumber))
							break;
					}
					// the search is continued 
					// when wordInIndex and initialSearchExpression are equal strings 
					// or 
					// when wordInIndex starts with initialSearchExpression and is wordInIndex
					// is followed by a blank
					boolean continueSearch = false;
					if (wordInIndex.startsWith(initialSearchExpression)) {
						if (wordInIndex.length() > initialSearchExpression.length()) {
							if (wordInIndex.charAt(initialSearchExpression.length()) == ' ') 
								continueSearch = true;
						}
						else 
							continueSearch = true;  // strings are equal
					}
					if (continueSearch) {
						indexFileNumber = searchListFile.getWord().toString();
					}
					else {
						// search is finished
						break;
					}
				}
				else {
					indexFileNumber = searchListFile.getWord().toString();
				}
			}
		}

		if (searchListFile.endOfDictionaryReached && (! lastIndexFileSearched)) {
			// search in the last index file
			searchInIndexFileBreakCondition(toBeTranslatedWordNormated, indexFileNumber);
		}
		searchListFile = null; // to allow garbage collection
	}
	
	public boolean searchInIndexFileBreakCondition(String toBeTranslatedWordNormated, String indexFileNumber) 
							throws DictionaryException  {
		if (translationBreakCondition()) {
			return true;
		}
		else {
			searchInIndexFile(toBeTranslatedWordNormated, indexFileNumber);
			return translationBreakCondition();
		}
	}
	
	public void searchInIndexFile(String toBeTranslatedWordNormated, String indexFileNumber) 
	                        throws DictionaryException  {
		Util.getUtil().logDebug("indexFileNumber " + indexFileNumber);
		String languagePostfix = DictionaryDataFile.supportedLanguages[DictionarySettings.getInputLanguage()].languageFilePostfix;
		String indexFileName = DictionaryDataFile.getPathDataFiles() + 
	                           DictionaryDataFile.prefixIndexFile +
							   languagePostfix +
							   indexFileNumber +
		                       DictionaryDataFile.suffixIndexFile;

		/* 
		 * read index file
		 */
		boolean containsWildcard = (positionFirstWildcardCharacter(toBeTranslatedWordNormated) >= 0);
		String searchExpression;
		String initialSearchExpression;
		searchExpression = toBeTranslatedWordNormated;
		if (containsWildcard) {
			initialSearchExpression = expressionTillWildcard(toBeTranslatedWordNormated);
		}
		else {
			initialSearchExpression = toBeTranslatedWordNormated;
		}
		int initialSearchExpressionLength = initialSearchExpression.length();
		String indexStringLine = null;
		Util.getUtil().logDebug("indexFileName " + indexFileName);
		
		CsvFile indexFile = new CsvFile(indexFileName,
						    DictionaryDataFile.indexFileSeparationCharacter,
							DictionaryDataFile.indexCharEncoding,
							DictionaryDataFile.indexFileMaxSize);

		Util.memCheck("indexfile open: ");
		indexFile.setPositionBefore(initialSearchExpression);
		while (! indexFile.endOfDictionaryReached) {
			String indexEntry = indexFile.getWord().toString();
			if (! containsWildcard) {
				if (indexEntry.compareTo(searchExpression) < 0) {
					// skip entry
					indexFile.skipRestOfLine();
				}
				// do an ordinary match on the entry
				else if (indexEntry.startsWith(searchExpression)) {
					if (noWildcardMatchRest(searchExpression, indexEntry)) {
						// entry found
						indexStringLine = indexFile.getWord().toString();
						getDictionaryEntry(indexStringLine);
					}
					else {
						indexFile.skipRestOfLine();
					}
				}
				else {
					break;
				}
			}
			else {
				int endOfFirstPartIndexEntry = initialSearchExpressionLength;
				int indexEntryLength = indexEntry.length();
				if (indexEntryLength < initialSearchExpressionLength)
					endOfFirstPartIndexEntry = indexEntryLength; 
				String firstPartIndexEntry = indexEntry.substring(0, endOfFirstPartIndexEntry);
				if (initialSearchExpression.compareTo(firstPartIndexEntry) < 0) {
					// no more matches possible:
					break;
				}
				// do a wildcard match on the entry
				if (wildcardMatch(indexEntry, 
						          0,
								  indexEntry.length(),
								  searchExpression, 
						          0, 
								  searchExpression.length())) {
					// Strings matched
					indexStringLine = indexFile.getWord().toString();
					getDictionaryEntry(indexStringLine);
				}
				else {
					indexFile.skipRestOfLine();
				}
			}
		}
	}
	
	// for perfomance reasons the string positions are passed in to the method wildcardMatch,
	// instead of creating new substrings for each recursion.
	// Note: this method is declared as final only for the reason of better performance
	public final boolean wildcardMatch(String toBeSearchedExpression,
									   int    positionCharacterToBeSearchedExpression,
									   int    lengthToBeSearchedExpression,
					                   String wildcardExpression,
					                   int    positionCharacterWildcardExpression,
					                   int    lengthWildcardExpression) {
		boolean expressionsMatch = false;
		if (positionCharacterWildcardExpression == lengthWildcardExpression) {
			if (positionCharacterToBeSearchedExpression == lengthToBeSearchedExpression) {
				// matched till to the last charcter
				expressionsMatch = true;
			} 
			else {
				// length is different: no match unless the next character in toBeSearchedExpression
				// is a blank character (and searchSubExpressionEnd is true)
				if ((lengthToBeSearchedExpression > positionCharacterToBeSearchedExpression) && searchSubExpressionEnd) {
					if (toBeSearchedExpression.charAt(positionCharacterToBeSearchedExpression) == ' ') {
						expressionsMatch = true;
					}
					else {
						expressionsMatch = false;
					}
				}
				else {
					expressionsMatch = false;
				}
			}
		}
		else if (positionCharacterToBeSearchedExpression == lengthToBeSearchedExpression) {
			// length is different: no match
			// but if the rest of the wilcard expression is wildcardAnySeriesOfCharacter, then
			// the expressions still match
			boolean restIsAnySeriesOfCharacter = true;
			for (int position = positionCharacterWildcardExpression;
			     position < lengthWildcardExpression;
				 ++position) {
				if (wildcardExpression.charAt(position) != wildcardAnySeriesOfCharacter) {
					restIsAnySeriesOfCharacter = false;
					break;
				}
			}
			expressionsMatch = restIsAnySeriesOfCharacter;
		}
		else {
			char characterToBeSearchedExpression = 
				              toBeSearchedExpression.charAt(positionCharacterToBeSearchedExpression);
			char characterWildcardExpression = 
				              wildcardExpression.charAt(positionCharacterWildcardExpression);

			if (characterWildcardExpression == wildcardAnySingleCharacter) {
				// matches to any character: check remaining part of the expressions
				expressionsMatch = wildcardMatch(toBeSearchedExpression,
						 						 positionCharacterToBeSearchedExpression + 1,
												 lengthToBeSearchedExpression,
												 wildcardExpression,
												 positionCharacterWildcardExpression + 1,
												 lengthWildcardExpression);
			}
			else if (characterWildcardExpression == wildcardAnySeriesOfCharacter) {
				// see if the rest of the wildcardExpression matches to any of the remaining 
				// characters of toBeSearchedExpression
				for (int position = positionCharacterToBeSearchedExpression;
				     position <= lengthToBeSearchedExpression;
					 ++position) {
					expressionsMatch = wildcardMatch(toBeSearchedExpression,
												     position,
													 lengthToBeSearchedExpression,
													 wildcardExpression,
													 positionCharacterWildcardExpression + 1,
													 lengthWildcardExpression);
					if (expressionsMatch)
						// match for expressions found, no need to continue search
						break;
				}
			}
			else if (characterWildcardExpression == characterToBeSearchedExpression) {
				// characters match: check remaining part of the expressions
				expressionsMatch = wildcardMatch(toBeSearchedExpression,
												 positionCharacterToBeSearchedExpression + 1,
												 lengthToBeSearchedExpression,
												 wildcardExpression,
												 positionCharacterWildcardExpression + 1,
												 lengthWildcardExpression);
			}
			
		}
		
		return expressionsMatch;
	}

	// checks if both strings are of equal length or if indexEntry is followed by a blank
	// character (and searchSubExpressionEnd is true)
	public boolean noWildcardMatchRest(String searchExpression, String indexEntry) {
		int searchExpressionLength = searchExpression.length();
		int indexEntryLength = indexEntry.length();
		if (searchExpressionLength == indexEntryLength) {
			return true;
		}
		if ((indexEntryLength > searchExpressionLength) && searchSubExpressionEnd) {
			if (indexEntry.charAt(searchExpressionLength) == ' ') {
				return true;
			}
		}
		// false in the other cases
		return false;
	}

	public void getDictionaryEntry(String indexStringLine) 
					throws DictionaryException {
		int posIndexFileSeparatorIndexEntries;
		int posFirstCharIndexString = 0;
		int posLastCharIndexString;
		do {
			posIndexFileSeparatorIndexEntries = 
				indexStringLine.indexOf(DictionaryDataFile.indexFileSeparatorIndexEntries, 
						                posFirstCharIndexString);
			if (posIndexFileSeparatorIndexEntries == -1)
				posLastCharIndexString = indexStringLine.length();
			else 
				posLastCharIndexString = posIndexFileSeparatorIndexEntries;
			String indexString = indexStringLine.substring(posFirstCharIndexString, 
					                                       posLastCharIndexString);
			posFirstCharIndexString = posLastCharIndexString + 1;
			int posIndexFileSeparatorFileNumberToPosition = 
			        indexString.indexOf(DictionaryDataFile.indexFileSeparatorFileNumberToPosition);
			String directoryFileNumberString = indexString.substring(0, posIndexFileSeparatorFileNumberToPosition);
			int directoryFileNumber = Integer.parseInt(directoryFileNumberString);
			int posIndexFileSeparatorFilePositionToSearchIndicator = 
				    indexString.indexOf(DictionaryDataFile.indexFileSeparatorFilePositionToSearchIndicator, 
				    		            posIndexFileSeparatorFileNumberToPosition + 1);
			if (posIndexFileSeparatorFilePositionToSearchIndicator == -1) {
				throw new DictionaryException("Indexfile has no searchindicator - use DictionaryGeneration 2.4.4 or newer");
			}
			int positionInDirectoryFile = 
				 Integer.valueOf(indexString.substring((posIndexFileSeparatorFileNumberToPosition + 1), 
						                               posIndexFileSeparatorFilePositionToSearchIndicator)).intValue();
			SearchIndicator searchIndicatorObj = new SearchIndicator(indexString.charAt(posIndexFileSeparatorFilePositionToSearchIndicator + 1));
			// if the index entry comes from a subexpression but searchSubExpressionStart is false then this entry is ignored
			if (!(searchSubExpressionStart || searchIndicatorObj.isBeginOfExpression())) {
				// just do nothing: don't get the corresponding dictionary entry
			}
			else {
				// ok, get the corresponding dictionary entry 
				String postfixDictionaryFile;
				LanguageDefinition supportedLanguage = DictionaryDataFile.supportedLanguages[DictionarySettings.getInputLanguage()]; 
				if (supportedLanguage.hasSeparateDictionaryFile) {
					// use the file postfix also for the dictionary file
					postfixDictionaryFile =
						supportedLanguage.languageFilePostfix;
				}
				else {
					// no suffix: empty string
					postfixDictionaryFile = "";
				}
				
				if (! translationBreakCondition()) {
					
					/*
					 *  read dictionary file
					 */ 
					DirectoryFileLocation directoryFileLocation = 
						             new DirectoryFileLocation(directoryFileNumber,
														       postfixDictionaryFile,
														       positionInDirectoryFile);
					getTranslation(directoryFileLocation, 
							       searchIndicatorObj.isBeginOfExpression());
				}
				else {
					break;
				}
			}
		}
		while (posIndexFileSeparatorIndexEntries > 0);
	}
	
	public void getTranslation(DirectoryFileLocation directoryFileLocation, boolean foundAtBeginOfExpression) 
			throws DictionaryException
	{
		String dictionaryFileName = DictionaryDataFile.getPathDataFiles() + 
							        DictionaryDataFile.prefixDictionaryFile +
							        directoryFileLocation.postfixDictionaryFile +
							        directoryFileLocation.directoryFileNumber +
							        DictionaryDataFile.suffixDictionaryFile;
		Util.getUtil().logDebug("dictionaryFileName " + dictionaryFileName);
		Util.getUtil().logDebug("position " + String.valueOf(directoryFileLocation.positionInDirectoryFile));
		CsvFile dictionaryFile = new CsvFile(dictionaryFileName, 
	    							         DictionaryDataFile.dictionaryFileSeparationCharacter,
											 DictionaryDataFile.dictionaryCharEncoding,
											 DictionaryDataFile.dictionaryFileMaxSize,
											 directoryFileLocation.positionInDirectoryFile);

		Util.memCheck("dictionaryfile open: ");
		StringBuffer fromText = null; 
		StringBuffer toText = null; 
		
		int indexOutputLanguage = DictionarySettings.determineOutputLanguage();
		for (int indexLanguage = 0;
  	         indexLanguage < DictionaryDataFile.numberOfAvailableLanguages;
	         ++indexLanguage) {
			StringBuffer word = dictionaryFile.getWord();
			
			if (DictionarySettings.getInputLanguage() == indexLanguage)
				fromText = word;
			if (indexOutputLanguage  == indexLanguage) { // should be extended to handle more than one language
				toText = word;
			}
		}
		addTranslation(fromText, toText, foundAtBeginOfExpression, directoryFileLocation);
		dictionaryFile = null; // to allow garbage collection
	}
	
	public void addTranslation(StringBuffer 		 fromText, 
			                   StringBuffer 		 toText,
			                   boolean               foundAtBeginOfExpression,
			                   DirectoryFileLocation directoryFileLocation) {
		if (toText == null) // one-language dictionaries don't have a toText
			toText = new StringBuffer();
		if (toText.length() > 0) {
			Util.getUtil().convertFieldAndLineSeparatorChars(fromText);
			Util.getUtil().convertFieldAndLineSeparatorChars(toText);
			SingleTranslation newSingleTranslation = 
							new SingleTranslation(fromText, 
							                      toText,
								                  foundAtBeginOfExpression,
								                  0, // primarySortNumber is currently not used
								                  directoryFileLocation);
			// sort entries and remove duplicates 
			// optimization: this needs to be implemented more efficiently; current implementation is
			//               slow if many results exist
			int indexTranslation = 0;
			int numberOfTranslations = resultOfTranslation.translations.size();
			while (indexTranslation < numberOfTranslations) {
				SingleTranslation translation = (SingleTranslation) resultOfTranslation.translations.elementAt(indexTranslation);
				int translationsCompared = newSingleTranslation.compareTo(translation);
				if (translationsCompared == 0) {
					// duplicate entry: new translation is ignored
					break;
				}
				else if (translationsCompared < 0) {
					// insert new translation at current position
					resultOfTranslation.insertTranslationAt(newSingleTranslation, indexTranslation);
					break;
				} 
				else {
					// continue to search for the right position 
				}
				++indexTranslation;
			}
			if (indexTranslation == numberOfTranslations) {
				// add new translation at end
				resultOfTranslation.addTranslation(newSingleTranslation);
			}
		}
	}

	boolean translationBreakCondition() {
		if (resultOfTranslation.numberOfFoundTranslations() >= DictionarySettings.getMaxHits()) { 
			resultOfTranslation.translationBreakOccurred = true;
			resultOfTranslation.translationBreakReason = TranslationResult.BreakReasonCancelMaxNrOfHitsReached;
			return true;
		}
		else if (System.currentTimeMillis() - startTime >= DictionarySettings.getDurationForCancelSearch()) {
			resultOfTranslation.translationBreakOccurred = true;
			resultOfTranslation.translationBreakReason = TranslationResult.BreakReasonMaxExecutionTimeReached;
			return true;
		}
		else if (translationIsCancelled) {
			resultOfTranslation.translationBreakOccurred = true;
			resultOfTranslation.translationBreakReason = TranslationResult.BreakReasonCancelReceived;
			return true;
		}
		else {
			return false;
		}
	}
	
	// called from another thread: 
	public void cancelTranslation() {
		// set flag for cancelled translation
		translationIsCancelled = true;
	}
}