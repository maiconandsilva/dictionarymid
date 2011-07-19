/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de))

GPL applies - see file COPYING for copyright statement.
*/
"use strict";

function newTranslationResult(resultOfTranslation) {
	for (var currentTranslation = 0; 
		 currentTranslation < resultOfTranslation.numberOfFoundTranslations(); 
		 currentTranslation++) {
		var tr = translationResultsTable.insertRow(currentTranslation);
		var singleTranslation = resultOfTranslation.getTranslationAt(currentTranslation);
		var innerHTMLText;
		var fromText = singleTranslation.getFromText();
		innerHTMLText = createInnerHTML(fromText, true);
		var tdElement;
		tdElement = document.createElement('td');
		tdElement.innerHTML = innerHTMLText;
		tr.appendChild(tdElement);
		for (var currentToText = 0; 
			 currentToText < singleTranslation.getNumberOfToTexts(); 
			 currentToText++) {
				var toText = singleTranslation.getToTextAt(currentToText);
				innerHTMLText = createInnerHTML(toText, false);
				var tdElement = document.createElement('td');
				tdElement.innerHTML = innerHTMLText;
				tr.appendChild(tdElement);
			
		}
	}
}

function createInnerHTML(textOfLanguage, 
						 isInput) {
	var innerHTMLText = '';
	var stringColourItemText = determineItemsFromContent(textOfLanguage, true, isInput);
	for (var currentStringColourItemTextPart = 0; 
		 currentStringColourItemTextPart < stringColourItemText.size(); 
		 currentStringColourItemTextPart++) {
		var stringColourItemTextPart = stringColourItemText.getItemTextPart(currentStringColourItemTextPart);
		var rgbString = stringColourItemTextPart.getColour().getHexValue();
		innerHTMLText += '<font color=\"#' + rgbString + '\">' + stringColourItemTextPart.getText();
	}
	return innerHTMLText;
}

function deletePreviousTranslationResult() {
	while (translationResultsTable.rows.length > 0) {
		translationResultsTable.deleteRow(0);
	}
}

// Create an boolean Array with numberOfElements elements where all elements
// are false except the element at the index indexTrueElement.
function buildBooleanArray(numberOfElements, indexTrueElement) {
	var booleanArray = new Array(numberOfElements);
	for (var booleanArrayIndex = 0; booleanArrayIndex < numberOfElements; ++booleanArrayIndex) {
		if (booleanArrayIndex == indexTrueElement)
			booleanArray[booleanArrayIndex] = true;
		else
			booleanArray[booleanArrayIndex] = false;
	}
	return booleanArray;
}

function startTranslation() {
	var text = document.getElementById('userInput').value;
	var inputLanguages =  buildBooleanArray(DictionaryDataFile.numberOfAvailableLanguages,
											userSettingsObj.getInputLanguage());
	var outputLanguages = buildBooleanArray(DictionaryDataFile.numberOfAvailableLanguages,
											userSettingsObj.getOutputLanguage());
	executeTranslation(text,
					   inputLanguages,
					   outputLanguages,
					   false,
					   50,
					   20000);
}

function outputMessage(message) {
	alert(message);
}

function checkForBrowserCompatibility() {
	// Check whether Application Cache is supported
	var applicationCacheSupported = true;
	try {
		if (applicationCache == undefined) applicationCacheSupported = false;
	}
	catch (e) {applicationCacheSupported = false;}

	// Check whether Local Storage is supported
	var localStorageSupported = true;
	try {
		if (localStorage == undefined) localStorageSupported = false;
	}
	catch (e) {localStorageSupported = false;}

	// Check whether XMLHttpRequest.overrideMimeType is supported
	var htrOverrideMimeTypeSupported = true;
	var htr = new XMLHttpRequest();
	try {
		if (htr.overrideMimeType == undefined) htrOverrideMimeTypeSupported = false;
	}
	catch (e) {htrOverrideMimeTypeSupported = false;}		
	// Check whether FileReader is supported
	var fileReaderSupported = true;
	try {
		if (FileReader == undefined) fileReaderSupported = false;
	}
	catch (e) {fileReaderSupported = false;}
	if (!applicationCacheSupported) {
		alert("Application Cache not supported by browser; download of dictionary not possible !");
	}
	if (!localStorageSupported) {
		alert("Local Storage not supported by browser; you cannot save user settings");
	}
	if (!htrOverrideMimeTypeSupported) {
		alert("XMLHttpRequest.overrideMimeType not supported by browser; DictionaryForMIDs will probably not run !");
	}
	if (!fileReaderSupported) {
		alert("FileReader not supported by browser; translations can produce errors !");
	}
}

function applicationCacheEventHandler(event) {
	if (optionWindow != null) {
		if (! optionWindow.closed) {
			if (optionWindow.displayApplicationEvent != undefined) {
				optionWindow.displayApplicationEvent(event.type,
													 applicationCache.status);
			}
		}
	}
}

function openOptionWindow() { 
//			optionWindow = open("../htmlApp/mini_hmi/OptionWindow.html", 
		optionWindow = open(pathToHTML + "OptionWindow.html", 
						"Options", 
						"location=no,height=300,width=200,left=100,top=100,toolbar=no,scrollbars=yes,resizable=yes");
}

function initializeApplication() {
	applicationCache.onchecking    	= applicationCacheEventHandler;
	applicationCache.onerror       	= applicationCacheEventHandler;
	applicationCache.onnoupdate    	= applicationCacheEventHandler;
	applicationCache.ondownloading 	= applicationCacheEventHandler;
	applicationCache.onprogress 	= applicationCacheEventHandler;
	applicationCache.oncached 		= applicationCacheEventHandler;
	applicationCache.onobsolete 	= applicationCacheEventHandler;
	this.translationResultsTable 	= document.getElementById("translationResultsTable");
	this.userSettingsObj 			= new UserSettings(location.href);
}

var optionWindow = null;


