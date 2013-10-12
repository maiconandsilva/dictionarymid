/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_java_me;

import java.io.InputStream;

import javax.microedition.midlet.MIDlet;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.DfMInputStreamAccess;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.JSR75InputStreamAccess;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.ResourceDfMInputStreamAccess;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.ZipInputStreamAccess;
import de.kugihan.dictionaryformids.general.CouldNotOpenPropertyFileException;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_java_me.SettingsStore;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.general.UtilMid;
import de.kugihan.dictionaryformids.hmi_java_me.mainform.MainForm;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.LanguageUI;
import de.kugihan.dictionaryformids.translation.TranslationExecution;
 
public class DictionaryForMIDs 
    extends MIDlet  {

		public static String 		      applicationName = "DictionaryForMIDs";
		public static byte   		      versionRMSStructure = 23;
		public static DictionaryForMIDs   dictionaryForMIDsMidlet;
		protected static boolean		  dictionaryCanBeAccessed = false;
	
		protected DictionaryDataFile dictionary;
		public  DictionaryDataFile getloadedDictionary() {
			return dictionary;
		}
		public void setDictionaryNotAvailable() {
			dictionary = null;
		}


	public DictionaryForMIDs() throws DictionaryException {
		/*
		 *  initialise the application
		 */
		super();
		dictionaryForMIDsMidlet = this;
		
		MainForm applicationMainForm = new MainForm(this);			

		UtilMid utilObj = new UtilMid();
		utilObj.setLogForm(applicationMainForm);
		Util.setUtil(utilObj);
		int logLevel = 0;
		String logLevelString = getAppProperty("logLevel");
		if (logLevelString != null) {
			logLevel = Integer.valueOf(logLevelString).intValue();
		}
		utilObj.setLogLevel(logLevel);

		try {
			utilObj.log("Initialized logging", Util.logLevelMax);
			
			// check for supported MIDP version
			String supportedMidpProfile = System.getProperty("microedition.profiles");
			// Workaround for http://code.google.com/p/microemu/issues/detail?id=45.
			// "supportedMidpProfile != null && " code can be deleted when this bug is fixed and released in MicroEmu, probably 2011:
			if (supportedMidpProfile != null && supportedMidpProfile.indexOf("MIDP-2.") == -1) {
				// if MIDP 2.0 is not supported, then the application will not run
				utilObj.log("MIDP 2.0 not supported by the device.\n" +
                            applicationName + " will not run correctly !");
				Thread.sleep(3000);
			}

			// initialize SettingsStore
			SettingsStore.getSettingsStore().initValues();

			// determine the character encoding scheme that is used by the device
			utilObj.determineCharEncoding();

			// check for file access via JSR 75
			DictionarySettings.setUseFileAccessJSR75(determineFileAccessJSR75());
			
			// Create object for reading InputStreams read the file DictionaryForMIDs.properties
			try {
				doSetDictionaryDataFileISAccessAndReadDictionaryProperties();
				
				utilObj.log("Initialized values", Util.logLevelMax);
				DictionarySettings.setDictionaryAvailable(true);
			}
			catch (CouldNotOpenPropertyFileException exception) {
				// properties for DictionaryDataFiles could not be initialized
				dictionaryCanBeAccessed = false;
			}
			if (dictionaryCanBeAccessed != true) {
				DictionarySettings.setDictionaryAvailable(false);
				utilObj.log("DictionaryDataFiles could not be initialized", Util.logLevel1);
				setDictionaryNotAvailable(); 
			}

		 	LanguageUI.getUI().initValue();

		 	// if the values in the SettingsStore were newly initialized, then set some
			// default values
			if (DictionarySettings.isDictionaryAvailable() && 
				(! SettingsStore.getSettingsStore().getDefaultValuesSet()))
				SettingsStore.getSettingsStore().setDefaultValues();
			
			// set the language to be used for the user interface:
		 	LanguageUI.getUI().setUILanguage(SettingsStore.getSettingsStore().getUILanguage());
			
			// initialise the main form:
			applicationMainForm.initialiseForm();
			
			utilObj.log("construct application complete", Util.logLevelMax);
			Util.memCheck("End of DictionaryForMIDs constructor");
		}
		catch (Throwable t) {
			utilObj.log(t);
		}
	}


	public void startApp() {
		try {
			MainForm.applicationMainForm.startForm();
		}
		catch (Throwable t) {
			Util.getUtil().log(t);
		}
	}

	public void pauseApp () {}

	public void destroyApp(boolean unconditional) {
		try {
			SettingsStore.getSettingsStore().closeSettingsStore();
		}
		catch (DictionaryException e) {
			// do nothing
		}
	}	
	
	// returns true when the dictionary files are accessed in the file system via the JSR 75 API 
	boolean determineFileAccessJSR75() {
		boolean useFileAccessJSR75;
		String fileConnectionVersion = System.getProperty("microedition.io.file.FileConnection.version");
		if (fileConnectionVersion != null) {
			// JSR 75 API is provided by the device
			// next test is to check whether file DictionaryForMIDs.properties is found in the dictionary
			// directory of the JAR-file
			String propertyFileName = DictionaryDataFile.getDfMPropertyFileLocation("");
			InputStream propertyStream = getClass().getResourceAsStream(propertyFileName);
			if (propertyStream == null) {
				// DictionaryForMIDs.properties not available: use file access via JSR75
				useFileAccessJSR75 = true;
			}
			else {
				useFileAccessJSR75 = false;
			}
		}
		else {
			useFileAccessJSR75 = false;
		}
		return useFileAccessJSR75;
	}
	
	// sets the DfMInputStramAccess that is used for accessing the dictionary data files
	// also reads the file DictionaryForMIDs.properties
	protected void doSetDictionaryDataFileISAccessAndReadDictionaryProperties() 
				throws DictionaryException {
		DfMInputStreamAccess dfmInputStreamObj;
		if (DictionarySettings.isUseFileAccessJSR75()) {
			String dictionaryStore = SettingsStore.getSettingsStore().getDictionaryPath();
			int fileType = JSR75InputStreamAccess.determineFileType(dictionaryStore);
			if (fileType == JSR75InputStreamAccess.FileTypeFILE) {
				// access files from a zipped dictionary - Zz85
				dfmInputStreamObj = new ZipInputStreamAccess(dictionaryStore);
				dictionaryCanBeAccessed = true;
			} else if (fileType == JSR75InputStreamAccess.FileTypeDIRECTORY) {
				// access files from file system
				StringBuffer dictionaryDirectoryLocation = new StringBuffer(dictionaryStore);
				determineJSR75DirectoryLocation(dictionaryDirectoryLocation); 
				dfmInputStreamObj = new JSR75InputStreamAccess(dictionaryDirectoryLocation.toString());
				dictionaryCanBeAccessed = true;
			} else {
				// file cannot be accessed; still use file system access 
				dfmInputStreamObj = new JSR75InputStreamAccess(dictionaryStore);
				dictionaryCanBeAccessed = false;
			}
		}
		else {
			// access files from JAR-file
			dfmInputStreamObj = new ResourceDfMInputStreamAccess();
			dictionaryCanBeAccessed = true;
		}
		dictionary = TranslationExecution.loadDictionary(dfmInputStreamObj);
	}
	
	
	//
	// The following code is a temporary workaround.
	//
	// The following method determineJSR75DirectoryLocation is a temporary workaround till there is 
	// a final implementation that will automatically download the dictionaries to the correct directory.
	// Currently the users manually select the dictionary path; there some users incorrectly select  
	// the 'dictionary'-directory (one level above the 'dictionary'-directory must be selected);
	// The method determineJSR75DirectoryLocation will remove the 'dictionary' from the path in
	// case this exits.
	protected void determineJSR75DirectoryLocation(StringBuffer dictionaryDirectoryLocation) 
			throws DictionaryException {
		// remove trailing slash if there is any
		Util.getUtil().removeTrailingSlashFromPath(dictionaryDirectoryLocation);
		// check whether file DictionaryForMIDs.properties can be accessed
		String DfMPropertyFileLocation = dictionary.getDfMPropertyFileLocation(dictionaryDirectoryLocation.toString());
		if (JSR75InputStreamAccess.determineFileType(DfMPropertyFileLocation) == JSR75InputStreamAccess.FileTypeFILE) {
			dictionaryCanBeAccessed = true;
		}
		else if (dictionaryDirectoryLocation.toString().endsWith(dictionary.pathNameDataFiles)) {
			// 'dictionary'-directory is incorrectly included: remove this part
			dictionaryDirectoryLocation.setLength(dictionaryDirectoryLocation.length() - dictionary.pathNameDataFiles.length());
			Util.getUtil().removeTrailingSlashFromPath(dictionaryDirectoryLocation);
			DfMPropertyFileLocation = dictionary.getDfMPropertyFileLocation(dictionaryDirectoryLocation.toString());
			// check whether file DictionaryForMIDs.properties can be accessed after removal of the incorrect 'dictionary'-part
			if (JSR75InputStreamAccess.determineFileType(DfMPropertyFileLocation) == JSR75InputStreamAccess.FileTypeFILE) {
				dictionaryCanBeAccessed = true;
			}
			else {
				dictionaryCanBeAccessed = false;
			}
		}
		else {
			dictionaryCanBeAccessed = false;
		}
	}
	

}