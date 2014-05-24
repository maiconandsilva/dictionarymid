/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/
"use strict";
	
<!-- This is the Script for the Visual effects and jQuery functions: -->
$(window).load(function() {
	$("#headerFrame").hide().delay(700).fadeIn('slow');
	$("#searchFrame").hide().delay(700).fadeIn('slow');
	$("#footerFrame").hide().delay(700).fadeIn('slow');
	$("#optionFrame").hide();
	$("#footerText2").hide();
	$("#recentActivity1").hide();
	$("#recentActivity2").hide();
	$("#recentActivity3").hide();
	$("#recentActivity4").hide();
	$("#recentActivity5").hide();
	$("#footerText1").hide().delay(1000).slideToggle('slow');
	$("#footerText1").delay(2000).slideToggle('slow');
	$("#footerText2").delay(3000).slideToggle('slow');
	   if ($("#recentActivity1").length) {
		$("#footerText2").delay(7000).slideToggle('fast');
		$("#recentActivity1").delay(8000).slideToggle('fast');
		$("#recentActivity1").delay(10500).slideToggle('fast');
		$("#recentActivity2").delay(11500).slideToggle('fast');
		$("#recentActivity2").delay(13000).slideToggle('fast');
		$("#recentActivity3").delay(14000).slideToggle('fast');
		$("#recentActivity3").delay(16500).slideToggle('fast');
		$("#recentActivity4").delay(17500).slideToggle('fast');
		$("#recentActivity4").delay(20000).slideToggle('fast');
		$("#recentActivity5").delay(21000).slideToggle('fast');
		$("#recentActivity5").delay(23500).slideToggle('fast');
		$("#footerText2").delay(34000).slideToggle('slow');
	   }
});

<!-- This is the Script for feeding recent activities to Information Bar: -->
$(document).ready(function () {
   if (navigator.onLine){
	$.getScript( '../../Apps/mini_hmi/zrssfeed.js', function() {
	   $('#recentFeeds').rssfeed('http://sourceforge.net/p/dictionarymid/activity/feed', {
		limit: 5,
		dateformat: 'dd MMMM yyyy'
	   });
	});
   }
});

$(function(){
	$("td.optionbuttonStyle").click(function(){
		$("#optionFrame").slideToggle();
	});
});

<!-- This is the Script for focusing on input box and set action for enter key: -->

for (var start = 1; start < 10; start++)
	setTimeout(function Focus(){
		document.getElementById("userInput").focus();  //Focus Input box
	}, 350 * start);  //Timer to handle smooth focusing
			
function visibilityOnload(){
	document.getElementById("headerFrame").style.visibility = "visible";  //Change Frame visibility status
	document.getElementById("optionFrame").style.visibility = "visible";  //Change Frame visibility status
	document.getElementById("searchFrame").style.visibility = "visible";  //Change Frame visibility status
	document.getElementById("footerFrame").style.visibility = "visible";  //Change Frame visibility status
}
			
function enterKeyCommand(){
	if (event.keyCode == 13){
		startTranslation(); //Enter key press action	
	}
}

<!-- This is the Script for toggle between up/down option symbols: -->
var open = '&#9660; Options'  //The String 'options' with down arrow	
var close = '&#9650; Options'  //The String 'options' with up arrow

var currentState = 0;

function toggleoptionString() {
	if (currentState == 0) {
		document.getElementById("optionString").innerHTML = close;
		currentState = 1;
	} else {
		document.getElementById("optionString").innerHTML = open;
		currentState = 0;
	}
}


<!-- Initialization -->
window.addEventListener("load", initializeApplication);


function newTranslationResult(resultOfTranslation) {
	for (var currentTranslation = 0; 
		 currentTranslation < resultOfTranslation.numberOfFoundTranslations(); 
		 currentTranslation++) {
		var trElement = translationResultsTable.insertRow(currentTranslation);
		var singleTranslation = resultOfTranslation.getTranslationAt(currentTranslation);
		var fromText = singleTranslation.getFromText();
		var tdElement = document.createElement('td');
		appendHtmlElementsFromContent(tdElement,
		                              fromText, 
									  true);
		trElement.appendChild(tdElement);
		for (var currentToText = 0; 
			 currentToText < singleTranslation.getNumberOfToTexts(); 
			 currentToText++) {
				var toText = singleTranslation.getToTextAt(currentToText);
				var tdElement = document.createElement('td');
				appendHtmlElementsFromContent(tdElement,
											  toText, 
											  false);
				trElement.appendChild(tdElement);
		}
	}
}

function appendHtmlElementsFromContent(parentElement,
                                       textOfLanguage, 
						               isInput) {
	var stringColourItemText = ContentParser.determineItemsFromContent(textOfLanguage, true, isInput);
	for (var currentStringColourItemTextPart = 0; 
		 currentStringColourItemTextPart < stringColourItemText.size(); 
		 currentStringColourItemTextPart++) {
		var stringColourItemTextPart = stringColourItemText.getItemTextPart(currentStringColourItemTextPart);
		var text = stringColourItemTextPart.getText();
		var colourHexValue = stringColourItemTextPart.getColour().getHexValue();
		var spanElement = document.createElement('span');
		spanElement.setAttribute("style", "color:#" + colourHexValue);
		spanElement.textContent = text;
		parentElement.appendChild(spanElement);
	}
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

function searchHits() {
	var optionFrame = window.frames[0];
	var radioButtonCount = optionFrame.document.getElementById('searchHitsRadioButton').value;
	var comboBoxCount = optionFrame.document.getElementById('searchHitsComboBox').value;
	var resultsCount = "50";
		if(numberOfSearchableInputLanguages < 2){
			resultsCount = "50";
			return resultsCount
		}
	else if ((numberOfSearchableInputLanguages == 2) && (numberOfAvailableLanguages == 2)) {
			resultsCount = radioButtonCount;
			return resultsCount
		}
	else {
			resultsCount = comboBoxCount;
			return resultsCount
		}
}

function startTranslation() {
	var textInput = document.getElementById('userInput').value;
	var textInputWithAstrik = textInput.replace(' ','* ');
	var toBeTranslatedWordTextInput = textInputWithAstrik + '*';
	var inputLanguages =  buildBooleanArray(dictionary.numberOfAvailableLanguages,
											userSettingsObj.getInputLanguage());
	var outputLanguages = buildBooleanArray(dictionary.numberOfAvailableLanguages,
											userSettingsObj.getOutputLanguage());
	var executeInBackground = false;
	var maxHits = searchHits();
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
		optionWindowObj.displayApplicationEvent(event, parent.applicationCache.status);
}


function initializeApplication() {
	optionWindowObj = window.frames[0];
	UtilJs.outputMessage = outputMessageInAlert;
	applicationCache.onchecking    	= applicationCacheEventHandler;
	applicationCache.onerror       	= applicationCacheEventHandler;
	applicationCache.onnoupdate    	= applicationCacheEventHandler;
	applicationCache.ondownloading 	= applicationCacheEventHandler;
	applicationCache.onprogress 		= applicationCacheEventHandler;
	applicationCache.oncached 		= applicationCacheEventHandler;
	applicationCache.onobsolete 		= applicationCacheEventHandler;
	this.translationResultsTable 		= document.getElementById("translationResultsTable");
	this.userSettingsObj 			= new UserSettings(location.href);
	this.optionWindowInitializationComplete = false;
	optionWindowObj.initializeOptionWindow();
	optionWindowObj.focus();
}

var optionWindowObj = null;