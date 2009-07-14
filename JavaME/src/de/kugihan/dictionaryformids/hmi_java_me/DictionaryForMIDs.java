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
import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileAccessHandler;
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
 
public class DictionaryForMIDs 
    extends MIDlet  {

		public static String 		      applicationName = "DictionaryForMIDs";
		public static byte   		      versionRMSStructure = 21;
		public static DictionaryForMIDs   dictionaryForMIDsMidlet;
	
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
			if (supportedMidpProfile.indexOf("MIDP-2.") == -1) {
				// if MIDP 2.0 is not supported, then the application will not run
				utilObj.log("MIDP 2.0 not supported by the device.\n" +
                            applicationName + " will not run correctly !");
				Thread.sleep(3000);
			}

			// initialize SettingsStore
			SettingsStore.getSettingsStore().initValues();

			// check for file access via JSR 75
			DictionarySettings.setUseFileAccessJSR75(determineFileAccessJSR75());
			
			// Create object for reading InputStreams
			DfMInputStreamAccess dfmInputStreamObj;
			if (DictionarySettings.isUseFileAccessJSR75()) {
				String dictionaryStore = SettingsStore.getSettingsStore().getDictionaryPath();
				if (Util.convertToLowerCase(new StringBuffer(dictionaryStore)).toString().endsWith(".jar")) {
					// access files from a zipped dictionary - Zz85
					dfmInputStreamObj = new ZipInputStreamAccess(dictionaryStore);
				} else {
					// access files from file system
					dfmInputStreamObj = new JSR75InputStreamAccess(dictionaryStore);
				}
			}
			else {
				// access files from JAR-file
				dfmInputStreamObj = new ResourceDfMInputStreamAccess();
			}
			FileAccessHandler.setDictionaryDataFileISAccess(dfmInputStreamObj);
			
			utilObj.determineCharEncoding();

			try {
				DictionaryDataFile.initValues(false);
				utilObj.log("Initialized values", Util.logLevelMax);
				DictionarySettings.setDictionaryAvailable(true);
			}
			catch (CouldNotOpenPropertyFileException exception) {
				// properties for DictionaryDataFiles could not be initialized
				DictionarySettings.setDictionaryAvailable(false);
				utilObj.log("DictionaryDataFiles could not be initialized", Util.logLevel1);
				DictionaryDataFile.setDictionaryNotAvailable();
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

	// returns true when the dictionary files are accessed in the file system via the JSR 75 API 
	boolean determineFileAccessJSR75() {
		boolean useFileAccessJSR75;
		String fileConnectionVersion = System.getProperty("microedition.io.file.FileConnection.version");
		if (fileConnectionVersion != null) {
			// JSR 75 API is provided by the device
			// next test is to check whether file DictionaryForMIDs.properties is found in the dictionary
			// directory of the JAR-file
			String propertyFileName = DictionaryDataFile.getPathDataFiles() + DictionaryDataFile.propertyFileName;
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
}