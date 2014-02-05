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
	var stringColourItemText = ContentParser.determineItemsFromContent(textOfLanguage, true, isInput);
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
	var TranslatedWordTextInput = document.getElementById('userInput').value;
	var toBeTranslatedWordTextInput = TranslatedWordTextInput + '*';
	var inputLanguages =  buildBooleanArray(dictionary.numberOfAvailableLanguages,
											userSettingsObj.getInputLanguage());
	var outputLanguages = buildBooleanArray(dictionary.numberOfAvailableLanguages,
											userSettingsObj.getOutputLanguage());
	var executeInBackground = false;
	var maxHits = 100;
	var durationForCancelSearch = 5000;  // 5 seconds
	
	var translationParameters = 
		TranslationExecution.newTranslationParameters
		                                 (dictionary,                    // Type DictionaryDataFile: dictionary for translation
									      toBeTranslatedWordTextInput,   // Type string: word/expression that shall be translated
									      inputLanguages,                // Type Array of boolean, size window.dictionary.numberOfAvailableLanguages
									      outputLanguages,               // Type Array of boolean, size window.dictionary.numberOfAvailableLanguages
									      executeInBackground,           // Type boolean: this parameter is currently ignored
									      maxHits,                       // Type integer: maximum number of translation results 
									      durationForCancelSearch)       // Type integer: maximum duration of search in milliseconds
		TranslationExecution.executeTranslation(translationParameters,
		                                        deletePreviousTranslationResult,
												newTranslationResult);
}

function outputMessageInAlert(message) {
	alert(message);
}

function applicationCacheEventHandler(event) {
	if (optionWindow != null) {
		if (! optionWindow.closed) {
			if (optionWindowInitializationComplete) {
				if (optionWindow.displayApplicationEvent != undefined) {
					optionWindow.displayApplicationEvent(event.type,
														 applicationCache.status);
				}
			}
		}
	}
}

function openOptionWindow() { 
	optionWindowInitializationComplete = false;
	optionWindow = open(pathToHTML + "OptionWindow.html", 
	"Options", 
	"location=no,height=300,width=200,left=100,top=100,toolbar=no,scrollbars=yes,resizable=yes");
	optionWindow.focus();
}

function initializeApplication() {
	UtilJs.outputMessage = outputMessageInAlert;
	applicationCache.onchecking    	= applicationCacheEventHandler;
	applicationCache.onerror       	= applicationCacheEventHandler;
	applicationCache.onnoupdate    	= applicationCacheEventHandler;
	applicationCache.ondownloading 	= applicationCacheEventHandler;
	applicationCache.onprogress 	= applicationCacheEventHandler;
	applicationCache.oncached 		= applicationCacheEventHandler;
	applicationCache.onobsolete 	= applicationCacheEventHandler;
	this.translationResultsTable 	= document.getElementById("translationResultsTable");
	this.userSettingsObj 			= new UserSettings(location.href);
	this.optionWindowInitializationComplete = false;
	var optionWindowObj = window.frames["optionStyle"];
	optionWindowObj.initializeOptionWindow();
}

var optionWindow = null;