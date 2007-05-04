/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.general;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.hmi_java_me.DictionaryForMIDs;
import de.kugihan.dictionaryformids.hmi_java_me.DictionarySettingForm;
import de.kugihan.dictionaryformids.hmi_java_me.DictionarySettings;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.LanguageUI;

public class SettingsStore {

	private RecordStore mobileDictionaryStore;
	private static SettingsStore instanceSettingsStore = null;

	private final int rms_index_defaultValuesSet = 1;
	private final int rms_index_structureVersion = rms_index_defaultValuesSet + 1;
	private final int rms_index_inputLanguage = rms_index_structureVersion + 1;
	private final int rms_index_outputLanguage = rms_index_inputLanguage + 1;
	private final int rms_index_showStatistic = rms_index_outputLanguage + 1;
	private final int rms_index_bypassCharsetDecoding = rms_index_showStatistic + 1;
	private final int rms_index_incrementalSearchEnabled = rms_index_bypassCharsetDecoding + 1;
	private final int rms_index_maxHits = rms_index_incrementalSearchEnabled + 1;
	private final int rms_index_fontSize = rms_index_maxHits + 1;
	private final int rms_index_bitmapFontSize = rms_index_fontSize + 1;
	private final int rms_index_uiLanguage = rms_index_bitmapFontSize + 1;
	private final int rms_index_showTranslationList = rms_index_uiLanguage + 1;
	private final int rms_index_findExactMatches = rms_index_showTranslationList + 1;
	private final int rms_index_addAtBeginNoSearchSubExpressionCharacter = rms_index_findExactMatches + 1;
	private final int rms_index_addAtEndWildcardAnySeriesOfCharacter = rms_index_addAtBeginNoSearchSubExpressionCharacter + 1;
	private final int rms_index_colouredItems = rms_index_addAtEndWildcardAnySeriesOfCharacter + 1;
	private final int rms_index_useBitmapFont = rms_index_colouredItems + 1;
	private final int rms_index_dictionaryPath = rms_index_useBitmapFont + 1;
	private final int rms_index_contentIsShown = rms_index_dictionaryPath + 1;
	private final int rms_max_index = rms_index_contentIsShown;

	private final String rmsStoreName = "DictionaryForMIDs";
	
	public static SettingsStore getSettingsStore() throws DictionaryException {
		if (instanceSettingsStore == null)
			instanceSettingsStore = new SettingsStore();
		return instanceSettingsStore;
	}
		
	public SettingsStore() throws DictionaryException {
		// open RMS store
		try {
			mobileDictionaryStore = RecordStore.openRecordStore(rmsStoreName, true);
			boolean buildNewRMSStore = false;
			if (mobileDictionaryStore.getNumRecords() < rms_max_index) {
				buildNewRMSStore = true;
			}
			else {
				int structureVersion = getIntValue(rms_index_structureVersion);
				if (structureVersion != DictionaryForMIDs.versionRMSStructure)
					buildNewRMSStore = true;
			}
			if (buildNewRMSStore) {
				// Re-Initialize Store: delete it and create from scratch
				mobileDictionaryStore.closeRecordStore();
				RecordStore.deleteRecordStore(rmsStoreName);
				mobileDictionaryStore = RecordStore.openRecordStore(rmsStoreName, true);
				// 
				// set up intital field values
				//
				
				// default values: had not been set yet
				addBooleanValue(false);
				
				// store structure version
				addIntValue(DictionaryForMIDs.versionRMSStructure);
				
				// inputLanguage:
				addIntValue(-1); // will be set later in setDefaultValues

				// outputLanguage
				addBooleanArrayValue(new boolean[0]); // will be set later in setDefaultValues

				// statistics settings: switched off 
				addBooleanValue(false);
				
				// BypassCharsetDecoding settings: switched off 
				addBooleanValue(false);
				
				// incremental search enabled settings: switched off
				addBooleanValue(false);
				
				// maxHits settings: 30
				addIntValue(30);
				
				// fontSize: device default settings (= value 0)
				addIntValue(0);
				
				// bitmapFontSize:
				addStringValue(""); // will be set later in setDefaultValues
				
				// Current User Interface Language
				addIntValue(0);  // will be set later in setDefaultValues  // todo: will not be saved when no dictionary is available
				
				// showTranslationList: switched off
				addBooleanValue(false);				

				// findExactMatches: switched off
				addBooleanValue(false);
				
				// addAtBeginNoSearchSubExpressionCharacter: switched off
				addBooleanValue(false);
				
				// addAtEndWildcardAnySeriesOfCharacter: switched off
				addBooleanValue(false);
				
				// colouredItems: switched on by default
				addBooleanValue(true);				

				// bitmap font: switched off
				addBooleanValue(false);
				
				// dictionaryPath: empty string
				addStringValue("");
				
				// contentIsShown
				addBooleanArrayValue(new boolean[0]); // will be set later in setDefaultValues

			}
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}

	// The setting of default values is not done in the constuctor but in setDefaultValues,
	// because setDefaultValues is called later when all default values are available
	public void setDefaultValues()
			throws DictionaryException {
		// inputLanguage is the first searchable language
		// outputLanguage is the first language after inputLanguage
		byte inputLanguageInitalValue = -1;
		byte outputLanguageInitalValue = -1;
		for (byte languageCounter = 0; 
		 	 languageCounter < DictionaryDataFile.numberOfAvailableLanguages; 
		 	 ++languageCounter) {
			if (inputLanguageInitalValue != -1) {
				outputLanguageInitalValue = languageCounter;
				break; // both inputLanguageInitalValue and outputLanguageInitalValue are set
			}
			if (DictionaryDataFile.supportedLanguages[languageCounter].isSearchable) {
				if (inputLanguageInitalValue == -1)
					inputLanguageInitalValue = languageCounter;
			}
		}
		if (inputLanguageInitalValue == -1) {
			throw new DictionaryException("No searchable languages defined");
		}
		if (outputLanguageInitalValue == -1) {
			// inputLanguageInitialValue was last language in list: use first language
			outputLanguageInitalValue = 0;
		}
		
		// inputLanguage:
		setInputLanguage(inputLanguageInitalValue);
		
		// outputLanguages:
		boolean [] outputLanguage = new boolean[DictionaryDataFile.numberOfAvailableLanguages];
		for (int languageCounter = 0; 
		 	 languageCounter < DictionaryDataFile.numberOfAvailableLanguages; 
		 	 ++languageCounter) {
			boolean languageSetting = languageCounter == outputLanguageInitalValue;
			outputLanguage[languageCounter] = languageSetting;
		}
		setOutputLanguage(outputLanguage);
		
		// Current User Interface Lenguage: try to get the settings from the device
	 	// via microedition.locale
		byte uiLanguage = 0;
		String locale = System.getProperty("microedition.locale");
		if (locale != null) {
			int endPosLanguageCode = locale.indexOf('-');
			if (endPosLanguageCode == -1)
				endPosLanguageCode = locale.length();
			String languageCode = locale.substring(0, endPosLanguageCode);
			String [] availableLanguageCode = LanguageUI.getUI().getLanguageCode();
			for (byte languageCount = 0;
			     languageCount < availableLanguageCode.length;
			     ++languageCount) {
				if (languageCode.equals(availableLanguageCode[languageCount])) {
					uiLanguage = languageCount;
					break;
				}
			}
		}
		setUILanguage(uiLanguage);
		
		// bitmap font size
		String [] bitmapFontSizes = DictionarySettingForm.getBitmapFontSizes(); // todo sebastian: DictionarySettingForm muss durch eine BitmapFont-Klasse ersetzt werden
		if (bitmapFontSizes.length > 0) {
			setBitmapFontSize(bitmapFontSizes[0]); // set the first font size of the available font sizes
		}

		// todo: contentIsShown
		
		// default values had been set
		setDefaultValuesSet(true);
	}
	
	public void initValues() {
		// values already had been initialized in the constructor.
	}

	public void closeSettingsStore() throws DictionaryException {
		try {
			mobileDictionaryStore.closeRecordStore();
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}
	
	public boolean getDefaultValuesSet() throws DictionaryException  {
		return getBooleanValue(rms_index_defaultValuesSet);
	}

	public void setDefaultValuesSet(boolean defaultValuesSet) throws DictionaryException  {
		setBooleanValue(rms_index_defaultValuesSet,
						defaultValuesSet);
	}

	public int getInputLanguage() throws DictionaryException  {
		return getIntValue(rms_index_inputLanguage);
	}
	
	public void setInputLanguage(int inputLanguage) throws DictionaryException  {
		setIntValue(rms_index_inputLanguage,
				    inputLanguage);
	}
	
	public boolean [] getOutputLanguage() throws DictionaryException  {
		return getBooleanArrayValue(rms_index_outputLanguage, 
                					DictionaryDataFile.numberOfAvailableLanguages);
	}
	
	public void setOutputLanguage(boolean [] outputLanguage) throws DictionaryException  {
		setBooleanArrayValue(rms_index_outputLanguage,
				             outputLanguage);
	}

	
	public boolean getShowStatistic() throws DictionaryException  {
		return getBooleanValue(rms_index_showStatistic);
	}
	
	public void setShowStatistic(boolean showStatistic) throws DictionaryException  {
		setBooleanValue(rms_index_showStatistic,
						showStatistic);
	}

	public boolean getBypassCharsetDecoding() throws DictionaryException  {
		return getBooleanValue(rms_index_bypassCharsetDecoding);
	}
	
	public void setBypassCharsetDecoding(boolean bypassCharsetDecoding) throws DictionaryException  {
		setBooleanValue(rms_index_bypassCharsetDecoding,
						bypassCharsetDecoding);
	}

	public boolean getIncrementalSearchEnabled() throws DictionaryException  {
		return getBooleanValue(rms_index_incrementalSearchEnabled);
	}
	
	public void setIncrementalSearchEnabled(boolean incrementalSearchEnabled) throws DictionaryException  {
		setBooleanValue(rms_index_incrementalSearchEnabled,
					    incrementalSearchEnabled);
	}

	public int getMaxHits() throws DictionaryException  {
		return getIntValue(rms_index_maxHits);
	}
	
	public void setMaxHits(int maxHits) throws DictionaryException  {
		setIntValue(rms_index_maxHits,
				    maxHits);
	}
	
	public int getFontSize() throws DictionaryException  {
		return getIntValue(rms_index_fontSize);
	}
	
	public void setFontSize(int fontSize) throws DictionaryException  {
		setIntValue(rms_index_fontSize,
				    fontSize);
	}
	
	public String getBitmapFontSize() throws DictionaryException  {
		return getStringValue(rms_index_bitmapFontSize);
	}
	
	public void setBitmapFontSize(String bitmapFontSize) throws DictionaryException  {
		setStringValue(rms_index_bitmapFontSize,
				       bitmapFontSize);
	}
	
	public int getUILanguage() throws DictionaryException  {
		return getIntValue(rms_index_uiLanguage);
	}
	
	public void setUILanguage(int uiLanguage) throws DictionaryException  {
		setIntValue(rms_index_uiLanguage,
					uiLanguage);
	}

	public boolean getShowTranslationList() throws DictionaryException  {
		return getBooleanValue(rms_index_showTranslationList);
	}
	
	public void setShowTranslationList(boolean showTranslationList) throws DictionaryException  {
		setBooleanValue(rms_index_showTranslationList,
						showTranslationList);
	}

	public boolean getFindExactMatches() throws DictionaryException  {
		return getBooleanValue(rms_index_findExactMatches);
	}
	
	public void setFindExactMatches(boolean findExactMatches) throws DictionaryException  {
		setBooleanValue(rms_index_findExactMatches,
						findExactMatches);
	}

	public boolean getAddAtBeginNoSearchSubExpressionCharacter() throws DictionaryException  {
		return getBooleanValue(rms_index_addAtBeginNoSearchSubExpressionCharacter);
	}
	
	public void setAddAtBeginNoSearchSubExpressionCharacter(boolean addAtBeginNoSearchSubExpressionCharacter) throws DictionaryException  {
		setBooleanValue(rms_index_addAtBeginNoSearchSubExpressionCharacter,
						addAtBeginNoSearchSubExpressionCharacter);
	}

	public boolean getAddAtEndWildcardAnySeriesOfCharacter() throws DictionaryException  {
		return getBooleanValue(rms_index_addAtEndWildcardAnySeriesOfCharacter);
	}
	
	public void setAddAtEndWildcardAnySeriesOfCharacter(boolean addAtEndWildcardAnySeriesOfCharacter) throws DictionaryException  {
		setBooleanValue(rms_index_addAtEndWildcardAnySeriesOfCharacter,
				        addAtEndWildcardAnySeriesOfCharacter);
	}

	public boolean getColouredItems() throws DictionaryException  {
		return getBooleanValue(rms_index_colouredItems);
	}
	
	public void setColouredItems(boolean colouredItems) throws DictionaryException  {
		setBooleanValue(rms_index_colouredItems,
						colouredItems);
	}

	public boolean getUseBitmapFont() throws DictionaryException {
		return getBooleanValue(rms_index_useBitmapFont);
	}

	public void setDictionaryPath(String dictionaryPath)
			throws DictionaryException {
		setStringValue(rms_index_dictionaryPath, dictionaryPath);
	}

	public String getDictionaryPath() throws DictionaryException {
		return getStringValue(rms_index_dictionaryPath);
	}

	public void setUseBitmapFont(boolean useBitmapFont)
			throws DictionaryException {
		setBooleanValue(rms_index_useBitmapFont, useBitmapFont);
	}

	protected void addIntValue(int intValue) throws DictionaryException {
		try {
			checkByteValueRange(intValue);
			mobileDictionaryStore.addRecord(new byte [] { (byte) intValue }, 0, 1);
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}
	
	protected int getIntValue(int rmsIndex) throws DictionaryException {
		int  intValue;
		try {
			 byte [] intValueRecord = mobileDictionaryStore.getRecord(rmsIndex);
			 intValue = intValueRecord[0];
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
		return intValue;
	}
	
	protected void setIntValue(int rmsIndex, int intValue) throws DictionaryException {
		try {
			checkByteValueRange(intValue);
			mobileDictionaryStore.setRecord(rmsIndex, 
											new byte [] { (byte) intValue },
							                0,
							                1);
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}

	void checkByteValueRange(int intValue) throws DictionaryException {
		if (intValue > Byte.MAX_VALUE)
			throw new DictionaryException("SettingStore maximum value is " + Byte.MAX_VALUE);
		else if (intValue < Byte.MIN_VALUE)
			throw new DictionaryException("SettingStore minimum value is " + Byte.MIN_VALUE);
	}
	
	protected void addBooleanValue(boolean booleanValue) throws DictionaryException {
		try {
			mobileDictionaryStore.addRecord(new byte [] { convertBooleanToByte(booleanValue) }, 0, 1);
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}
	
	protected boolean getBooleanValue(int rmsIndex) throws DictionaryException {
		boolean  booleanValue;
		try {
			 byte [] booleanValueRecord = mobileDictionaryStore.getRecord(rmsIndex);
			 booleanValue = convertByteToBoolean(booleanValueRecord[0]);
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
		return booleanValue;
	}
	
	protected void setBooleanValue(int rmsIndex, boolean booleanValue) throws DictionaryException {
		try {
			mobileDictionaryStore.setRecord(rmsIndex, 
											new byte [] { convertBooleanToByte(booleanValue) },
							                0,
							                1);
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}

	protected void addBooleanArrayValue(boolean[] booleanArrayValue) throws DictionaryException {
		try {
			byte [] outputLanguageRecord = getBooleanArrayRecord(booleanArrayValue);
			mobileDictionaryStore.addRecord(outputLanguageRecord,
					                        0,
					                        outputLanguageRecord.length);
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}

	protected boolean[] getBooleanArrayValue(int rmsIndex,
			                                 int numberOfElements) throws DictionaryException {
		boolean [] booleanArrayValue = new boolean[numberOfElements];
		try {
			byte [] booleanArrayValueRecord = mobileDictionaryStore.getRecord(rmsIndex);
			ByteArrayInputStream byteInStream = new ByteArrayInputStream(booleanArrayValueRecord); 
			DataInputStream dataInStream = new DataInputStream(byteInStream);
			for (int languageCounter = 0; 
			     languageCounter < numberOfElements; 
				 ++languageCounter) {
				boolean languageSetting = dataInStream.readBoolean();
				booleanArrayValue[languageCounter] = languageSetting;
			}
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
		catch (IOException e) {
			throw new DictionaryException(e);
		}
		return booleanArrayValue;
	}
	
	protected void setBooleanArrayValue(int rmsIndex, boolean[] booleanArrayValue) throws DictionaryException {
		try {
			byte [] booleanArrayValueRecord = getBooleanArrayRecord(booleanArrayValue);
			mobileDictionaryStore.setRecord(rmsIndex, 
											booleanArrayValueRecord,
					                        0,
					                        booleanArrayValueRecord.length);
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}

	protected byte [] getBooleanArrayRecord(boolean [] booleanArray) throws DictionaryException {
		try {
			ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream(); 
			DataOutputStream dataOutStream = new DataOutputStream(byteOutStream);
			for (int languageCounter = 0; 
		         languageCounter < booleanArray.length; 
			     ++languageCounter) {
				dataOutStream.writeBoolean(booleanArray[languageCounter]);
			}
			dataOutStream.flush();
			return byteOutStream.toByteArray();
		}
		catch (IOException e) {
			throw new DictionaryException(e);
		}
	}
	
	protected boolean convertByteToBoolean(byte byteValue) throws DictionaryException {
		boolean booleanValue;
		
		if (byteValue == 0)
			booleanValue = false;
		else if (byteValue == 1)
			booleanValue = true;
		else
			throw new DictionaryException("Invalid byte-value in SettingsStore");
		return booleanValue;
	}
	
	protected byte convertBooleanToByte(boolean booleanValue) throws DictionaryException {
		byte byteValue;
		
		if (booleanValue)
			byteValue = 1;
		else
			byteValue = 0;
		return byteValue;
	}
	
	protected void addStringValue(String stringValue) throws DictionaryException {
		try {
			byte [] stringValueInBytes = stringValue.getBytes();
			mobileDictionaryStore.addRecord(stringValueInBytes, 0, stringValueInBytes.length);
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}
	
	protected String getStringValue(int rmsIndex) throws DictionaryException {
		String stringValue;
		try {
			 byte [] stringValueRecord = mobileDictionaryStore.getRecord(rmsIndex);
			 if (stringValueRecord != null)
				 stringValue = new String(stringValueRecord);
			 else {
				 stringValue = new String("");
			 }
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
		return stringValue;
	}
	
	protected void setStringValue(int rmsIndex, String stringValue) throws DictionaryException {
		try {
			byte [] stringValueInBytes = stringValue.getBytes();
			mobileDictionaryStore.setRecord(rmsIndex, 
											stringValueInBytes,
							                0,
							                stringValueInBytes.length);
		}
		catch (RecordStoreException e) {
			throw new DictionaryException(e);
		}
	}	
}
