/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de))

GPL applies - see file COPYING for copyright statement.
*/

"use strict";

function UserSettings(dictionaryID) {

	var settingInputLanguage  = "inputLanguage";
	var settingOutputLanguage = "ouputLanguage";
	var settingVersionOfSettings   = "versionOfSettings";
	var versionOfSettings = 0;
	
	// use a unique key-prefix for each dictionary to distinguish between the 
	// user settings for the different dictionaries
	var keyPrefix = dictionaryID;
	
	if (localStorageSupported()) {
		this.storageObj = localStorage;
	}
	else {
		this.storageObj = new Object();
	}
	
	this.writeStorage = function(key, value) {
		this.storageObj[keyPrefix + key] = value;
	}
	
	this.readStorage = function(key) {
		return this.storageObj[keyPrefix + key];
	}
		
	this.getInputLanguage = function() {
		return this.readStorage(settingInputLanguage);
	}
	
	this.setInputLanguage = function(indexNumber) {
		this.writeStorage(settingInputLanguage, indexNumber);
	}
	
	this.getOutputLanguage = function() {
		return this.readStorage(settingOutputLanguage);
	}
	
	this.setOutputLanguage = function(indexNumber) {
		this.writeStorage(settingOutputLanguage, indexNumber);
	}
	
	this.getVersionOfSettings = function() {
		return this.readStorage(settingVersionOfSettings);
	}
	
	this.setVersionOfSettings = function(version) {
		this.writeStorage(settingVersionOfSettings, version);
	}
	
	this.buildInitialSettings = function() {
		this.setVersionOfSettings(versionOfSettings);
		this.setInputAndOutputLanguage();
	}

	this.setInputAndOutputLanguage = function () {
		// inputLanguage is the first searchable language
		// outputLanguage is the first language after inputLanguage
		var inputLanguageInitalValue = -1;
		var outputLanguageInitalValue = -1;
		for (var languageCounter = 0; 
			 languageCounter < dictionary.numberOfAvailableLanguages; 
			 ++languageCounter) {
			if (inputLanguageInitalValue != -1) {
				outputLanguageInitalValue = languageCounter;
				break; // both inputLanguageInitalValue and outputLanguageInitalValue are set
			}
			if (dictionary.supportedLanguages[languageCounter].isSearchable) {
				if (inputLanguageInitalValue == -1)
					inputLanguageInitalValue = languageCounter;
			}
		}
		if (inputLanguageInitalValue == -1) {
			throw "No searchable languages defined";
		}
		if (outputLanguageInitalValue == -1) {
			// inputLanguageInitialValue was last language in list: use first language
			outputLanguageInitalValue = 0;
		}
		this.setInputLanguage(inputLanguageInitalValue);
		this.setOutputLanguage(outputLanguageInitalValue);
	}

	// check if the version of the settings matches
	if (this.getVersionOfSettings() != versionOfSettings) {
		this.buildInitialSettings();
	}
	
	function localStorageSupported() {
		return !(localStorage == undefined);
	}	
}