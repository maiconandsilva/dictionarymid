/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005 Gert Nuber (dict@kugihan.de) and  2005 Vu Van Quynh 
(quynh2003hp@yahoo.com)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess;

import de.kugihan.dictionaryformids.dataaccess.content.ContentDefinition;
import de.kugihan.dictionaryformids.dataaccess.content.FontStyle;
import de.kugihan.dictionaryformids.dataaccess.content.PredefinedContent;
import de.kugihan.dictionaryformids.dataaccess.content.RGBColour;
import de.kugihan.dictionaryformids.dataaccess.content.SelectionMode;
import de.kugihan.dictionaryformids.general.DictionaryClassNotLoadedException;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.hmi_j2me.DictionarySettings;
import de.kugihan.dictionaryformids.translation.normation.Normation;

public class DictionaryDataFile  {

	/*
	 * describes the structure of the dictionary data files
	 */ 

	public static int numberOfAvailableLanguages;	
	public static int numberOfInputLanguages;
	
	public static LanguageDefinition [] supportedLanguages;

	public static String infoText;
	public static String dictionaryAbbreviation;
	
	public static String pathNameFonts = "fonts";

	public static String pathNameDataFiles = "dictionary";
	public static String prefixSearchListFile = "searchlist";
	public static String suffixSearchListFile = ".csv";
	public static String searchListCharEncoding;
	public static char   searchListFileSeparationCharacter;
	public static int    searchListFileMaxSize;
	public static String prefixIndexFile = "index";
	public static String suffixIndexFile = ".csv";
	public static String indexCharEncoding;
	public static char   indexFileSeparationCharacter;
	public static char   indexFileSeparatorFileNumberToPosition = '-';
	public static char   indexFileSeparatorFilePositionToSearchIndicator = '-';
	public static char   indexFileSeparatorIndexEntries = ',';
	public static int    indexFileMaxSize;
	public static String prefixDictionaryFile = "directory";
	public static String suffixDictionaryFile = ".csv";
	public static String dictionaryCharEncoding;
	public static char   dictionaryFileSeparationCharacter;
	public static int    dictionaryFileMaxSize;
	public static String applicationFileNamePrefix = "DictionaryForMIDs";
	public static String propertyFileName = applicationFileNamePrefix + ".properties";
	public static char   contentFontColourSeparationCharacter = ',';

	// properties used by DictionaryGeneration only
	public static String dictionaryGenerationInputCharEncoding;
	public static char   dictionaryGenerationSeparatorCharacter;
	public static long   dictionaryGenerationMinNumberOfEntriesPerDictionaryFile;
	public static long   dictionaryGenerationMinNumberOfEntriesPerIndexFile;
	
	public static String fileEncodingFormat; // for "special use" - currently ignored
	
	public static String dictionaryPath; // used for dictionaries that are accessed via the file system
	

	public static void initValues(boolean initDictionaryGenerationValues)
				throws DictionaryException
	{
		/* 
		 * values from resource file 
		 * */
		Util utilObj = Util.getUtil();
		utilObj.openProperties();
		infoText = utilObj.getDictionaryPropertyString("infoText");
		dictionaryAbbreviation = utilObj.getDictionaryPropertyString("dictionaryAbbreviation", true);
		checkForEmptyProperty(dictionaryAbbreviation);
		numberOfAvailableLanguages = utilObj.getDictionaryPropertyInt("numberOfAvailableLanguages");
		numberOfInputLanguages = 0; // initialised with 0
		supportedLanguages = new LanguageDefinition[numberOfAvailableLanguages];
		for (int indexLanguage = 0;
	         indexLanguage < numberOfAvailableLanguages;
	         ++indexLanguage) {
			String indexLanguageString = String.valueOf(indexLanguage + 1);
			String languagePropertyPrefix = "language" + indexLanguageString;
			String languageDisplayText = utilObj.getDictionaryPropertyString(languagePropertyPrefix + "DisplayText");
			String languageFilePostfix = utilObj.getDictionaryPropertyString(languagePropertyPrefix + "FilePostfix");
			boolean isSearchable = utilObj.getDictionaryPropertyBooleanDefault(languagePropertyPrefix + "IsSearchable", true);
			if (isSearchable)
				++numberOfInputLanguages;
			boolean hasSeparateDictionaryFile = utilObj.getDictionaryPropertyBooleanDefault(languagePropertyPrefix + "HasSeparateDictionaryFile", false);
			boolean generateIndex = utilObj.getDictionaryPropertyBooleanDefault(languagePropertyPrefix + "GenerateIndex", true);
			String normationClassName = utilObj.getDictionaryPropertyString(languagePropertyPrefix + "NormationClassName", true);
			checkForEmptyProperty(normationClassName);
			String dictionaryUpdateClassName = utilObj.getDictionaryPropertyString(languagePropertyPrefix + "DictionaryUpdateClassName", true);
			checkForEmptyProperty(dictionaryUpdateClassName);
			int indexNumberOfSourceEntries = utilObj.getDictionaryPropertyIntDefault(languagePropertyPrefix + "IndexNumberOfSourceEntries", -1);
			String expressionSplitString = null;
			if (initDictionaryGenerationValues) {
				expressionSplitString = utilObj.getDictionaryPropertyString("dictionaryGenerationLanguage" + indexLanguageString + "ExpressionSplitString", true);
				checkForEmptyProperty(expressionSplitString);
			}
			/* 
			 * Content definitions for this language 
			 */
			boolean contentDefinitionAvailable = false;
			ContentDefinition content[] = null;
			int numberOfContentDeclarations = 1; // there is at least the default content definiton at array index 0
			String numberOfContentDeclarationsString = utilObj.getDictionaryPropertyString(languagePropertyPrefix + "NumberOfContentDeclarations", true);
			if (numberOfContentDeclarationsString != null) {
				numberOfContentDeclarations = Integer.valueOf(numberOfContentDeclarationsString).intValue() + 1;
				contentDefinitionAvailable = true;
			}
			content = new ContentDefinition[numberOfContentDeclarations];
			// default content is at array index 0
			content[0] = PredefinedContent.getContentNoDefinitionProvided();
			// get the declarations for the remaining contents from the property file:
			for (int indexContent = 0;
	         	 indexContent < numberOfContentDeclarations;
	             ++indexContent) {
				String indexContentString = String.valueOf(indexContent);
				if (indexContentString.length() == 1)
					indexContentString = '0' + indexContentString; // always use 2 digits
				else if (indexContentString.length() > 2)
					throw new DictionaryException("Number of contents too big");
				String contentPropertyPrefix = languagePropertyPrefix + "Content" + indexContentString;

				ContentDefinition contentFromPropertyFile;
				// contentDisplayText
				String contentDisplayTextProperty = contentPropertyPrefix + "DisplayText";
				String contentDisplayText = utilObj.getDictionaryPropertyString(contentDisplayTextProperty, true);
				if (contentDisplayText == null) {
					if (indexContent == 0) {
						// ok for content 00 (= default content): this is optional
						continue;
					}
					else {
						utilObj.propertyNotFound(contentDisplayTextProperty);
					}
				}
				if (contentDisplayText.startsWith(PredefinedContent.predefinedContentNamePrefix)) {
					// content is a predefined content
					contentFromPropertyFile = PredefinedContent.getPredefinedContent(contentDisplayText);
				}
				else {
					contentFromPropertyFile = new ContentDefinition(contentDisplayText);
				}
				// read the other content properties from the property file:
				// fontColour
				String fontColourString = utilObj.getDictionaryPropertyString(contentPropertyPrefix + "FontColour", true);
				RGBColour fontColour;
				if (fontColourString != null) {
					fontColour = determineRGBColourFromProperty(fontColourString, contentPropertyPrefix);
					contentFromPropertyFile.setFontColour(fontColour);
				}

				// fontStyle
				String fontStyleString = utilObj.getDictionaryPropertyString(contentPropertyPrefix + "FontStyle", true);
				if (fontStyleString != null) {
					FontStyle fontStyle = null;
					if      (Util.stringEqualIgnoreCase(fontStyleString, FontStyle.plainString)) fontStyle = PredefinedContent.fontStylePlain;
					else if (Util.stringEqualIgnoreCase(fontStyleString, FontStyle.underlinedString)) fontStyle = PredefinedContent.fontStyleUnderlined;  
					else if (Util.stringEqualIgnoreCase(fontStyleString, FontStyle.boldString)) fontStyle = PredefinedContent.fontStyleBold; 
					else if (Util.stringEqualIgnoreCase(fontStyleString, FontStyle.italicString)) fontStyle = PredefinedContent.fontStyleItalic; 
					else throwContentException("Incorrect font style", contentPropertyPrefix);
					contentFromPropertyFile.setFontStyle(fontStyle);
				}

				// displaySelectable
				String displaySelectablePropertyName = new String(contentPropertyPrefix + "DisplaySelectable");
				String displaySelectableString = utilObj.getDictionaryPropertyString(displaySelectablePropertyName, true);
				if (displaySelectableString != null) {
					boolean displaySelectable = utilObj.getBooleanFromProperty(displaySelectablePropertyName, displaySelectableString);
					contentFromPropertyFile.setDisplaySelectable(displaySelectable);
				}
				
				// selectionMode
				String selectionModeString = utilObj.getDictionaryPropertyString(contentPropertyPrefix + "SelectionMode", true);
				if (selectionModeString != null) {
					SelectionMode selectionMode = null;
					if (Util.stringEqualIgnoreCase(selectionModeString, SelectionMode.noneString)) selectionMode = PredefinedContent.selectionModeNone;
					else if (Util.stringEqualIgnoreCase(selectionModeString, SelectionMode.singleString)) selectionMode = PredefinedContent.selectionModeSingle;  
					else if (Util.stringEqualIgnoreCase(selectionModeString, SelectionMode.allString)) selectionMode = PredefinedContent.selectionModeAll; 
					else throwContentException("Incorrect selection mode", contentPropertyPrefix);
					contentFromPropertyFile.setSelectionMode(selectionMode);
				}
					
				content[indexContent] = contentFromPropertyFile;
			}
			supportedLanguages[indexLanguage] = new LanguageDefinition(languageDisplayText, 
					                                                   languageFilePostfix,
																	   isSearchable,
																	   hasSeparateDictionaryFile,
					                                                   normationClassName,
					                                                   indexNumberOfSourceEntries,
					                                                   contentDefinitionAvailable,
					                                                   content,
					                                                   dictionaryUpdateClassName,
					                                                   generateIndex,
					                                                   expressionSplitString);
	    }
		searchListCharEncoding = utilObj.getDictionaryPropertyString("searchListCharEncoding");
		Util.setDeviceCharEncoding(searchListCharEncoding);
		searchListFileSeparationCharacter = utilObj.getDictionaryPropertyChar("searchListFileSeparationCharacter");
		searchListFileMaxSize = utilObj.getDictionaryPropertyIntDefault("searchListFileMaxSize", 10000);
		indexCharEncoding = utilObj.getDictionaryPropertyString("indexCharEncoding");
		Util.setDeviceCharEncoding(indexCharEncoding);
		indexFileSeparationCharacter = utilObj.getDictionaryPropertyChar("indexFileSeparationCharacter");
		indexFileMaxSize = utilObj.getDictionaryPropertyIntDefault("indexFileMaxSize", 10000);
		dictionaryCharEncoding = utilObj.getDictionaryPropertyString("dictionaryCharEncoding");
		Util.setDeviceCharEncoding(dictionaryCharEncoding);
		dictionaryFileSeparationCharacter = utilObj.getDictionaryPropertyChar("dictionaryFileSeparationCharacter");			
		dictionaryFileMaxSize = utilObj.getDictionaryPropertyIntDefault("dictionaryFileMaxSize", 10000);
		String backgroundColourProperty = "backgroundColour";
		String backgroundColourString = utilObj.getDictionaryPropertyString(backgroundColourProperty, true);
		if (backgroundColourString == null) {
			DictionarySettings.setUseBackgroundColour(false);
			DictionarySettings.setBackgroundColour(null);
		}
		else {
			DictionarySettings.setUseBackgroundColour(true);
			if (Util.stringEqualIgnoreCase(backgroundColourString, PredefinedContent.backgroundColourDefaultName)) {
				DictionarySettings.setBackgroundColour(PredefinedContent.backgroundColourDefault);
			}
			else { 
				DictionarySettings.setBackgroundColour(determineRGBColourFromProperty(backgroundColourString, backgroundColourProperty));
			}
		}

		if (initDictionaryGenerationValues) {
			dictionaryGenerationInputCharEncoding = utilObj.getDictionaryPropertyStringDefault("dictionaryGenerationInputCharEncoding",  "ISO-8859-1");
			dictionaryGenerationSeparatorCharacter = utilObj.getDictionaryPropertyCharDefault("dictionaryGenerationSeparatorCharacter", '\t');
			dictionaryGenerationMinNumberOfEntriesPerDictionaryFile = utilObj.getDictionaryPropertyIntDefault("dictionaryGenerationMinNumberOfEntriesPerDictionaryFile", 200);
			dictionaryGenerationMinNumberOfEntriesPerIndexFile = utilObj.getDictionaryPropertyIntDefault("dictionaryGenerationMinNumberOfEntriesPerIndexFile", 500);
		}

		fileEncodingFormat = utilObj.getDictionaryPropertyString("fileEncodingFormat", true);
		if (fileEncodingFormat == null) {
			fileEncodingFormat = new String("plain_format1");
		}
		utilObj.closeProperties();

		/* 
		 * load normation classes and create normation objects; same for dictionaryUpdate classes/objects
		 */
		for (int indexLanguage = 0;
	         indexLanguage < numberOfAvailableLanguages;
			 ++indexLanguage) {

			String normationClassName = supportedLanguages[indexLanguage].normationClassName;
			Normation normationObj = 
					(Normation) getObjectForClass(normationClassName, 
							                      Normation.class.getName(),
							                      "de.kugihan.dictionaryformids.translation.normation",
							                      "de.kugihan.dictionaryformids.translation");
			supportedLanguages[indexLanguage].normationObj = normationObj;  
			
			if (initDictionaryGenerationValues) {
				String dictionaryUpdateClassName = supportedLanguages[indexLanguage].dictionaryUpdateClassName;
				DictionaryUpdateIF dictionaryUpdateObj =
						(DictionaryUpdateIF) getObjectForClass(dictionaryUpdateClassName, 
															   "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdate",
											                   "de.kugihan.dictionaryformids.dictgen.dictionaryupdate",
															   "de.kugihan.dictionaryformids.dictgen");
				supportedLanguages[indexLanguage].dictionaryUpdateObj = dictionaryUpdateObj;				 
			}
		}
	}

	protected static Object getObjectForClass(String className, 
			                                  String fallbackClassName,
			                                  String newPackageName,  // in support of pre 3.0 Jar-files for the PC-Version
			                                  String oldPackageName)  // in support of pre 3.0 Jar-files for the PC-Version
				throws DictionaryException {
		if (className == null) {
			// use fallbackClassName instead
			className = fallbackClassName;
		}
		Class classToLoad;
		Object classObj;
		try
		{
			classToLoad = Class.forName(className);
			classObj = classToLoad.newInstance(); 
		}
		catch (Exception e)
		{
			// try old package name
			try {
				StringBuffer classNameNewPackage = new StringBuffer(className);
				// delete old package name:
				classNameNewPackage.delete(0, oldPackageName.length());
				// prepend new package name:
				String classNameNewPackageStr = newPackageName + classNameNewPackage.toString();
				classToLoad = Class.forName(classNameNewPackageStr);
				classObj = classToLoad.newInstance();
			}
			catch (Exception e2) {
				// did not work either:
				throw new DictionaryClassNotLoadedException("Class could not be loaded: " + className);
			}
		}
		return classObj;
	}
	
	public static int determineColourComponent(String rbgString, String propertyName) 
					throws DictionaryException {
		String rbgStringTrimmed = rbgString.trim();
		int rgbValue = 0;
		try {
			rgbValue = Integer.parseInt(rbgStringTrimmed);
		}
		catch (NumberFormatException e) {
			throwContentException("RGB value is incorrect: " + e.getMessage(), propertyName); 
		}
		if (rgbValue > RGBColour.maxRBGColourValue)
			throwContentException("RGB value is bigger than " + RGBColour.maxRBGColourValue, propertyName); 
		return rgbValue;
	}
	
	protected static RGBColour determineRGBColourFromProperty(String fontColourString, String propertyName)  
				throws DictionaryException {
		String[] fontColourStringElements = Util.stringSplit(fontColourString, contentFontColourSeparationCharacter);
		if (fontColourStringElements.length != 3) {
			throwContentException("3 components reqired for font colour (red, green, blue)", propertyName);
		}
		int red = determineColourComponent(fontColourStringElements[0], propertyName);
		int green = determineColourComponent(fontColourStringElements[1], propertyName);
		int blue = determineColourComponent(fontColourStringElements[2], propertyName);
		return new RGBColour(red, green, blue);
	}

	protected static void throwContentException(String message, String propertyName) 
					throws DictionaryException {
		throw new DictionaryException(propertyName + ": " + message);
	}
	
	// if property is provided but empty, then this is handled as if no property was provided:
	static void checkForEmptyProperty(String propertyName) {
		if (propertyName != null)
			if (propertyName.length() == 0)
				propertyName = null;
	}
	
	// sets the values when no dictionary with a property file is available
	public static void setDictionaryNotAvailable() {
		numberOfAvailableLanguages = 0;
	}
	
	public static String getPathDataFiles()
	{
		return "/" + pathNameDataFiles + "/";
	}
}
