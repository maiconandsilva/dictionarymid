/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2010 Gert Nuber (dict@kugihan.de))

GPL applies - see file COPYING for copyright statement.
*/

"use strict";

function UserSettings(dictionaryID) {

	var settingInputLanguageIndex  = "inputLanguageIndex";
	var settingOutputLanguageIndex = "ouputLanguageIndex";
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
		
	this.getInputLanguageIndex = function() {
		return this.readStorage(settingInputLanguageIndex);
	}
	
	this.setInputLanguageIndex = function(indexNumber) {
		this.writeStorage(settingInputLanguageIndex, indexNumber);
	}
	
	this.getOutputLanguageIndex = function() {
		return this.readStorage(settingOutputLanguageIndex);
	}
	
	this.setOutputLanguageIndex = function(indexNumber) {
		this.writeStorage(settingOutputLanguageIndex, indexNumber);
	}
	
	this.getVersionOfSettings = function() {
		return this.readStorage(settingVersionOfSettings);
	}
	
	this.setVersionOfSettings = function(version) {
		this.writeStorage(settingVersionOfSettings, version);
	}
	
	this.buildInitialSettings = function() {
		this.setVersionOfSettings(versionOfSettings);
		this.setInputLanguageIndex(this.determineFirstInputLanguage());
		this.setOutputLanguageIndex(this.determineFirstOutputLanguage());
	}

	this.determineFirstInputLanguage = function() {
		return 0;  // for testing
	}

	this.determineFirstOutputLanguage = function() {
		return 1;  // for testing
	}
	
	// check if the version of the settings matches
	if (this.getVersionOfSettings() != versionOfSettings) {
		this.buildInitialSettings();
	}
	
	function localStorageSupported() {
		return !(localStorage == undefined);
	}	
}