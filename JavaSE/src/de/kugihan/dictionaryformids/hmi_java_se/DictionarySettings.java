/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006   Vu Van Quynh (quynh2003hp@yahoo.com), Gert Nuber
(dict@kugihan.de) and Stefan Martens (stefan (a) stefan1200.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_java_se;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionarySettings
{
	private static int inputLanguage;
	private static boolean[] outputLanguage;
	private static boolean showStatistic;
	private static boolean useBitmapFont;
	private static String bitmapFontSize;
	private static boolean showTranslationList;
	private static boolean incrementalSearchEnabled;
	private static boolean colouredItems;
	private static boolean findExactMatches;
	private static boolean addAtBeginNoSearchSubExpressionCharacter;
	private static boolean addAtEndWildcardAnySeriesOfCharacter;
	private static int maxHits = 30;   // currently not set from this dialog
	private static int durationForCancelSearch = 60*1000;  // in milliseconds; currently hard set value
	private static int fontSize;
	private static int uiLanguage;
	private static boolean useFileAccessJSR75 = false; 
	private static boolean dictionaryAvailable;
	private static String dictionaryPath;
//	private static boolean[] contentIsShown;
	
	public static int getDurationForCancelSearch()
	{
		return durationForCancelSearch;
	}
	public static void setDurationForCancelSearch(int durationForCancelSearch)
	{
		DictionarySettings.durationForCancelSearch = durationForCancelSearch;
	}
	public static boolean isIncrementalSearchEnabled()
	{
		return incrementalSearchEnabled;
	}
	public static void setIncrementalSearchEnabled(boolean incrementalSearchEnabled)
	{
		DictionarySettings.incrementalSearchEnabled = incrementalSearchEnabled;
	}
	public static int getMaxHits()
	{
		return maxHits;
	}
	public static void setMaxHits(int maxHits)
	{
		DictionarySettings.maxHits = maxHits;
	}
	public static int getFontSize()
	{
		return fontSize;
	}
	public static void setFontSize(int fontSize)
	{
		DictionarySettings.fontSize = fontSize;
	}
	public static String getBitmapFontSize()
	{
		return bitmapFontSize;
	}
	public static void setBitmapFontSize(String bitmapSize)
	{
		DictionarySettings.bitmapFontSize = bitmapSize;
	}
	public static final int inputLanguageAll = -1; // search over all languages
	public static int getInputLanguage()
	{
		return inputLanguage;
	}
	public static void setInputLanguage(int inputLanguage)
	{
		DictionarySettings.inputLanguage = inputLanguage;
	}
	public static int numberOfSearchableInputLanguages() {
		int number = 0;
		for (int language = 0; language < getloadedDictionary().numberOfAvailableLanguages; ++language) {
			if (getloadedDictionary().supportedLanguages[language].isSearchable) {
				number++;
			}
		}
		return number;
	}
	public static boolean[] getOutputLanguage()
	{
		return outputLanguage;
	}
	public static boolean getOutputLanguage(int value)
	{
		return outputLanguage[value];
	}
	public static void setOutputLanguage(boolean[] outputLanguage)
	{
		DictionarySettings.outputLanguage = outputLanguage;
	}
	
	/*
	 * The outputLanguage data structure allows to define more than one output language,
	 * the idea is that in the future a word can be translated to more than one language. 
	 * However currently only one outputLanguage is allowed. The method determineOutputLanguage
	 * returns the index of this one outputLanguage. 
	 */
	public static int determineOutputLanguage() throws DictionaryException {
		int indexOutputLanguage = -1;
		for (int indexLanguage = 0;
	         indexLanguage < getloadedDictionary().numberOfAvailableLanguages;
	         ++indexLanguage) {
			if (DictionarySettings.getOutputLanguage(indexLanguage)) {
				indexOutputLanguage = indexLanguage; 
				break;
			}
		}
		if (indexOutputLanguage == -1)
			throw new DictionaryException("Output language could not be determined");
		return indexOutputLanguage;
	}	
	
	public static boolean getShowStatistic()
	{
		return showStatistic;
	}
	public static void setShowStatistic(boolean showStatistic)
	{
		DictionarySettings.showStatistic = showStatistic;
	}
	public static boolean getUseBitmapFont()
	{
		return useBitmapFont;
	}
	public static void setUseBitmapFont(boolean useBitmapFonts)
	{
		DictionarySettings.useBitmapFont = useBitmapFonts;
	}
	public static boolean getShowTranslationList()
	{
		return showTranslationList;
	}
	public static void setShowTranslationList(boolean showTranslationList)
	{
		DictionarySettings.showTranslationList = showTranslationList;
	}
	public static int getUILanguage()
	{
		return uiLanguage;
	}
	public static void setUILanguage(int uiLanguage)
	{
		DictionarySettings.uiLanguage = uiLanguage;
	}
	public static boolean isColouredItems() {
		return colouredItems;
	}
	public static void setColouredItems(boolean colouredItems) {
		DictionarySettings.colouredItems = colouredItems;
	}
	public static boolean isFindExactMatches() {
		return findExactMatches;
	}
	public static void setFindExactMatches(boolean findExactMatches) {
		DictionarySettings.findExactMatches = findExactMatches;
	}
	public static boolean isAddAtBeginNoSearchSubExpressionCharacter() {
		return addAtBeginNoSearchSubExpressionCharacter;
	}
	public static void setAddAtBeginNoSearchSubExpressionCharacter(
			boolean addAtBeginNoSearchSubExpressionCharacter) {
		DictionarySettings.addAtBeginNoSearchSubExpressionCharacter = addAtBeginNoSearchSubExpressionCharacter;
	}
	public static boolean isAddAtEndWildcardAnySeriesOfCharacter() {
		return addAtEndWildcardAnySeriesOfCharacter;
	}
	public static void setAddAtEndWildcardAnySeriesOfCharacter(
			boolean addAtEndWildcardAnySeriesOfCharacter) {
		DictionarySettings.addAtEndWildcardAnySeriesOfCharacter = addAtEndWildcardAnySeriesOfCharacter;
	}
	public static boolean isUseFileAccessJSR75() {
		return useFileAccessJSR75;
	}
	public static void setUseFileAccessJSR75(boolean useFileAccessJSR75) {
		DictionarySettings.useFileAccessJSR75 = useFileAccessJSR75;
	}
	public static boolean isDictionaryAvailable() {
		return dictionaryAvailable;
	}
	public static void setDictionaryAvailable(boolean dictionaryAvailable) {
		DictionarySettings.dictionaryAvailable = dictionaryAvailable;
	}
	public static String getDictionaryPath() {
		return dictionaryPath;
	}
	public static void setDictionaryPath(String dictionaryPath) {
		DictionarySettings.dictionaryPath = dictionaryPath;
	}
	
	public static DictionaryDataFile getloadedDictionary() {
		return DictionaryForSE.DictionaryForSEObj.getloadedDictionary();
	}
//	public static void setContentIsShown(boolean[] contentShown) {
//		if (contentIsShown == null){
//			contentIsShown = new boolean[contentShown.length];		
//		}
//		contentIsShown = contentShown;
//	}
//	public static boolean getContentIsShown(int lang, int contentNum) {
//		if (contentNum == -1) 
//			return true;
//		for (int i = 0; i < lang; i++){
//			contentNum += DictionaryDataFile.supportedLanguages[i].contents.length;
//		}		
//		return contentIsShown[contentNum];		
//	}
//	public static void loadContent(int contentIndex){
//		if (contentIsShown == null){
//			contentIsShown = new boolean[contentIndex];
//			for (int i = 0; i < contentIsShown.length; i++)contentIsShown[i] = true;
//		}
//	}
}
