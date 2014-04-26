/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de))

GPL applies - see file COPYING for copyright statement.
*/

"use strict";

function inputCombo() {

	for (var inLang = 0; inLang < dictionary.numberOfAvailableLanguages; inLang++) {
		var inputText = dictionary.supportedLanguages[inLang].languageDisplayText;
		var inCombo = document.getElementById("inputLanguageComboBox");
		var inOptions = document.createElement("option");
			inOptions.text = inputText;
			inOptions.value = inLang;
			inCombo.add(inOptions);
	}
}

function outputCombo() {
	
	document.getElementById('outputLanguageComboBox').options.length = 0;
	
	for (var outLang = 0; outLang < dictionary.numberOfAvailableLanguages; outLang++) {
	var inputSelectedindex = document.getElementById("inputLanguageComboBox").selectedIndex;
		 if (outLang==inputSelectedindex){
			continue;
		 }
		var outputText = dictionary.supportedLanguages[outLang].languageDisplayText;
		var outCombo = document.getElementById("outputLanguageComboBox");
		var outOptions = document.createElement("option");
			outOptions.text = outputText;
			outOptions.value = outLang;
			outCombo.add(outOptions);
	}
}

function displayApplicationEventFunction(eventText, applicationCacheStatusValue) {
	displayApplicationCacheStatus(applicationCacheStatusValue);
	document.getElementById("receivedApplicationCacheEvents").innerHTML =  eventText.type;
}

function displayApplicationCacheStatus(applicationCacheStatusValue) {
	var currentCacheStatus = getAppCacheStatusString(applicationCacheStatusValue);
	var applicationCacheStatus =	document.getElementById("applicationCacheStatus");
	applicationCacheStatus.innerHTML = currentCacheStatus;
}

function getAppCacheStatusString(applicationCacheStatusValue) {
	var appCache = parent.applicationCache;
	switch (applicationCacheStatusValue) {
	  case appCache.UNCACHED: // UNCACHED == 0
		return 'UNCACHED';
		break;
	  case appCache.IDLE: // IDLE == 1
		return 'IDLE';
		break;
	  case appCache.CHECKING: // CHECKING == 2
		return 'CHECKING';
		break;
	  case appCache.DOWNLOADING: // DOWNLOADING == 3
		return 'DOWNLOADING';
		break;
	  case appCache.UPDATEREADY:  // UPDATEREADY == 4
		return 'UPDATEREADY';
		break;
	  case appCache.OBSOLETE: // OBSOLETE == 5
		return 'OBSOLETE';
		break;
	  default:
		return 'UKNOWN CACHE STATUS';
		break;
	};
}

function numberOfSearchableInputLanguages() {
	var number = 0;
	for (var language = 0; language < dictionary.numberOfAvailableLanguages; ++language) {
		if (dictionary.supportedLanguages[language].isSearchable) {
			number++;
		}
	}
	return number;
}

function initializeOptionWindow() {
	this.dictionary = parent.dictionary;
	this.numberOfAvailableLanguages = dictionary.numberOfAvailableLanguages;
	this.numberOfSearchableInputLanguages = numberOfSearchableInputLanguages();
	this.inputLanguage = parent.userSettingsObj.getInputLanguage();
	this.outputLanguage = parent.userSettingsObj.getOutputLanguage();
	this.radioButtons = document.getElementsByName('languageSelection');
	this.languageSelection1RadioButton = radioButtons[0];
	this.languageSelection2RadioButton = radioButtons[1];
	this.languageSelection1Text = languageSelection1RadioButton.nextSibling;
	this.languageSelection2Text = languageSelection2RadioButton.nextSibling;
	
	if (parent.applicationCache.status == parent.applicationCache.UPDATEREADY) {
		parent.applicationCache.swapCache(); // The fetch was successful
		parent.location.reload(); // Reload the entire webApp in the cache.
	}
	
	displayApplicationCacheStatus(parent.applicationCache.status);
	createItems();
	parent.optionWindowInitializationComplete = true;
}

var languageSelectionTypeEnum = { none : 0, radioButtons : 1,  comboBoxes : 2 };
var languageSelectionType;

function createItems() {
	if (numberOfSearchableInputLanguages < 2) {
		languageSelectionType = languageSelectionTypeEnum.none;
		hideLanguageSelectionRadioButtons();
		hideLanguageSelectionComboBoxes();
	}
	else if ((numberOfSearchableInputLanguages == 2) && (numberOfAvailableLanguages == 2)) {
		languageSelectionType = languageSelectionTypeEnum.radioButtons;
		var language0DisplayText = dictionary.supportedLanguages[0].languageDisplayText;
		var language1DisplayText = dictionary.supportedLanguages[1].languageDisplayText;
		languageSelection1Text.nodeValue = language0DisplayText + " > " + language1DisplayText;
		languageSelection2Text.nodeValue = language1DisplayText + " > " + language0DisplayText;
		hideLanguageSelectionComboBoxes();
	}
	else {
		languageSelectionType = languageSelectionTypeEnum.comboBoxes;
		inputCombo();
		outputCombo();
		hideLanguageSelectionRadioButtons();
	}
	setItemsToSettingValues();
	saveSettings();
}

function hideLanguageSelectionRadioButtons() {
	document.getElementById("languageSelectionRadioButtons").style.display = "none";
}

function hideLanguageSelectionComboBoxes() {
	document.getElementById("languageSelectionComboBoxes").style.display = "none";
}

function setItemsToSettingValues() {
	if (languageSelectionType == languageSelectionTypeEnum.radioButtons) {
		if (inputLanguage == 0) {
			languageSelection1RadioButton.checked = true;
			languageSelection2RadioButton.checked = false;
		} 
		else {
			languageSelection1RadioButton.checked = false;
			languageSelection2RadioButton.checked = true;
		}
	}
	else if (languageSelectionType == languageSelectionTypeEnum.comboBoxes){
		document.getElementById("inputLanguageComboBox").value=0;
		document.getElementById("outputLanguageComboBox").value=1;
	}
}

function saveSettings() {
	if (languageSelectionType == languageSelectionTypeEnum.radioButtons) {
		if (languageSelection1RadioButton.checked) {
			inputLanguage  = 0;
			outputLanguage = 1;
		}
		else {
			inputLanguage  = 1;
			outputLanguage = 0;
		}
	}
	else if (languageSelectionType == languageSelectionTypeEnum.comboBoxes){
		inputLanguage  = document.getElementById("inputLanguageComboBox").value;
		outputLanguage = document.getElementById("outputLanguageComboBox").value;
	}
	if (languageSelectionType != languageSelectionTypeEnum.none) {
		parent.userSettingsObj.setInputLanguage(inputLanguage);
		parent.userSettingsObj.setOutputLanguage(outputLanguage);
	}
}

function onUnloadOptionWindow() {
	parent.optionWindow = null;
}

this.displayApplicationEvent = displayApplicationEventFunction;
window.onunload = onUnloadOptionWindow;