package de.kugihan.dictionaryformids.client;

import com.google.gwt.core.client.*;

import de.kugihan.dictionaryformids.general.*;
import de.kugihan.dictionaryformids.dataaccess.*;
import de.kugihan.dictionaryformids.translation.*;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.*;

import java.util.*;

import de.kugihan.dictionaryformids.hmi_common.content.*;
import de.kugihan.dictionaryformids.dataaccess.content.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TranslationLayerGWT implements EntryPoint, TranslationExecutionCallback {

	public static ContentParser contentParserObj;
	public DictionaryDataFile dictionary;

	/**
	  * This is the entry point method.
    */
	public void onModuleLoad() {
		UtilJs utilObj = new UtilJs();
		Util.setUtil(utilObj);
		utilObj.exportStaticClasses();
		contentParserObj = new ContentParser();
		exportStaticClasses();
		  try {
			HTRInputStream.setBaseDirectory(getBaseDirectory());
			CsvFile.fileStorageReader = new HTRFileStorageReader();
			dictionary = TranslationExecution.loadDictionary(new HTRInputStreamAccess());
			TranslationExecution.setTranslationExecutionCallback(this);
			exportLoadedDictionary(dictionary);
		}
		catch (Exception e) { 
			Util.getUtil().log(e); 
		}
	}

	public void deletePreviousTranslationResult() {
		callDeletePreviousTranslationResultJs();
	}
	public void newTranslationResult(TranslationResult resultOfTranslation) {
		callNewTranslationResultJs(resultOfTranslation);
	}
	

	// newTranslationParameters creates an object of class TranslationParameters from Javascript parameters 
	public static TranslationParameters newTranslationParameters(DictionaryDataFile dictionary,
													             String  		    toBeTranslatedWordTextInputParam,
																 JsArrayBoolean     inputLanguagesParam,
																 JsArrayBoolean     outputLanguagesParam,
																 boolean 		    executeInBackgroundParam,
																 int                maxHitsParam,
																 int   		        durationForCancelSearchParam) 
                throws DictionaryException {
		
		boolean[]	inputLanguages =  new boolean[inputLanguagesParam.length()];
		boolean[]	outputLanguages = new boolean[outputLanguagesParam.length()];
		
		for (int i=0; i < inputLanguagesParam.length(); ++i) {
			inputLanguages[i] = inputLanguagesParam.get(i);
		}
		
		for (int i=0; i < outputLanguagesParam.length(); ++i) {
			outputLanguages[i] = outputLanguagesParam.get(i);
		}

		TranslationParameters translationParametersObj = 
						new TranslationParameters(dictionary,
								                  toBeTranslatedWordTextInputParam,
												  inputLanguages,
												  outputLanguages,
												  executeInBackgroundParam,
												  maxHitsParam,
												  durationForCancelSearchParam);
		return translationParametersObj;
	}
	

	// newTranslationParametersBatch creates an object of class TranslationParametersBatchs 
	public static TranslationParametersBatch newTranslationParametersBatch() {
		TranslationParametersBatch translationParametersBatchObj = new TranslationParametersBatch(); 
		return translationParametersBatchObj;
	}

	// execute translation in the Translation Layer
	public static void executeTranslation(TranslationParameters translationParametersObj) 
				throws DictionaryException {
		TranslationExecution.executeTranslation(translationParametersObj);
	}
	  
	// execute batch translation in the Translation Layer
	public static void executeTranslationBatch(TranslationParametersBatch translationParametersBatchObj) 
					  throws DictionaryException {
		TranslationExecution.executeTranslationBatch(translationParametersBatchObj);
	}
	
	public static StringColourItemText determineItemsFromContent(TextOfLanguage contentText, 
			                                                     boolean changeInputAndOutputContent,
			                                                     boolean isInput) 
		              throws DictionaryException {
		StringColourItemText stringColourItemText = null;
		stringColourItemText = contentParserObj.determineItemsFromContent(contentText, changeInputAndOutputContent, isInput);
		return stringColourItemText;
	}

	protected static native void callDeletePreviousTranslationResultJs() /*-{
		TranslationExecution.deletePreviousTranslationResultCallback();
    }-*/;

	protected static native void callNewTranslationResultJs(TranslationResult resultOfTranslation) /*-{
		// 
		// create JavaScript methods for accessing Java methods
		//
		// TranslationResult
   		resultOfTranslation.numberOfFoundTranslations 		= $entry(resultOfTranslation.@de.kugihan.dictionaryformids.translation.TranslationResult::numberOfFoundTranslations());
   		resultOfTranslation.translationFound 				= $entry(resultOfTranslation.@de.kugihan.dictionaryformids.translation.TranslationResult::translationFound());
   		resultOfTranslation.getTranslationAt 				= $entry(resultOfTranslation.@de.kugihan.dictionaryformids.translation.TranslationResult::getTranslationAt(*));

		// each SingleTranslation within TranslationResult
   		for (var i = 0; i < resultOfTranslation.numberOfFoundTranslations(); i++) {
   			singleTranslation = resultOfTranslation.getTranslationAt(i);
   			singleTranslation.getFromText 					= $entry(singleTranslation.@de.kugihan.dictionaryformids.translation.SingleTranslation::getFromText());
   			singleTranslation.getNumberOfToTexts 			= $entry(singleTranslation.@de.kugihan.dictionaryformids.translation.SingleTranslation::getNumberOfToTexts());
   			singleTranslation.getToTextAt 					= $entry(singleTranslation.@de.kugihan.dictionaryformids.translation.SingleTranslation::getToTextAt(*));  			
   			// fromText within singleTranslation
   			setJsMethodsForJavaTextOfLanguage(singleTranslation.getFromText);
   			// each toText within singleTranslation
   			for (var j = 0; j < singleTranslation.getNumberOfToTexts(); j++) {
   				setJsMethodsForJavaTextOfLanguage(singleTranslation.getToTextAt(j));
   			}
   		}

   		function setJsMethodsForJavaTextOfLanguage(textOfLanguage)  {
   			textOfLanguage.getLanguageIndex					= $entry(textOfLanguage.@de.kugihan.dictionaryformids.translation.TextOfLanguage::getLanguageIndex());
   			textOfLanguage.getText 							= $entry(textOfLanguage.@de.kugihan.dictionaryformids.translation.TextOfLanguage::getText());
   		}
   		
		TranslationExecution.newTranslationResultCallback(resultOfTranslation);
    }-*/;

	protected static native void exportStaticClasses() /*-{

		// Constructor for Javascript objects of type TranslationParameters
		function newTranslationParametersJs(dictionary,
									        toBeTranslatedWordTextInputParam,
									        inputLanguagesParam,
									        outputLanguagesParam,
									        executeInBackgroundParam,
									        maxHitsParam,
									        durationForCancelSearchParam) {
			var newTranslationParametersFunction = $entry(@de.kugihan.dictionaryformids.client.TranslationLayerGWT::newTranslationParameters(*));
			translationParametersObj = newTranslationParametersFunction
	                                      (dictionary,
										   toBeTranslatedWordTextInputParam,
										   inputLanguagesParam,
										   outputLanguagesParam,
										   executeInBackgroundParam,
										   maxHitsParam,
										   durationForCancelSearchParam);
            // some member functions are added:
            translationParametersObj.getInputLanguages = 			$entry(translationParametersObj.@de.kugihan.dictionaryformids.translation.TranslationParameters::getInputLanguages(*));
            translationParametersObj.getOutputLanguages = 			$entry(translationParametersObj.@de.kugihan.dictionaryformids.translation.TranslationParameters::getOutputLanguages(*));
            translationParametersObj.getDictionary = 				$entry(translationParametersObj.@de.kugihan.dictionaryformids.translation.TranslationParameters::getDictionary(*));
            translationParametersObj.getToBeTranslatedWordText = 	$entry(translationParametersObj.@de.kugihan.dictionaryformids.translation.TranslationParameters::getToBeTranslatedWordText(*));
            translationParametersObj.getDurationForCancelSearch = 	$entry(translationParametersObj.@de.kugihan.dictionaryformids.translation.TranslationParameters::getDurationForCancelSearch(*));
            translationParametersObj.getMaxHits = 					$entry(translationParametersObj.@de.kugihan.dictionaryformids.translation.TranslationParameters::getMaxHits(*));
            return translationParametersObj;
		}

		// Constructor for Javascript objects of type TranslationParametersBatch
		function newTranslationParametersBatchJs() {
			var newTranslationParametersBatchFunction = $entry(@de.kugihan.dictionaryformids.client.TranslationLayerGWT::newTranslationParametersBatch(*));
			translationParametersBatchJavaObj = newTranslationParametersBatchFunction();
            
            // some member functions are added:
			translationParametersBatchJavaObj.getTranslationParametersAt = 		$entry(translationParametersBatchJavaObj.@de.kugihan.dictionaryformids.translation.TranslationParametersBatch::getTranslationParametersAt(*));
			translationParametersBatchJavaObj.getAllTranslationParameters = 	$entry(translationParametersBatchJavaObj.@de.kugihan.dictionaryformids.translation.TranslationParametersBatch::getAllTranslationParameters(*));
			translationParametersBatchJavaObj.numberOfTranslationParameters = 	$entry(translationParametersBatchJavaObj.@de.kugihan.dictionaryformids.translation.TranslationParametersBatch::numberOfTranslationParameters(*));
			translationParametersBatchJavaObj.addTranslationParameters = 		$entry(translationParametersBatchJavaObj.@de.kugihan.dictionaryformids.translation.TranslationParametersBatch::addTranslationParameters(*));
			translationParametersBatchJavaObj.insertTranslationParametersAt= 	$entry(translationParametersBatchJavaObj.@de.kugihan.dictionaryformids.translation.TranslationParametersBatch::insertTranslationParametersAt(*));
			translationParametersBatchJavaObj.removeTranslationParametersAt= 	$entry(translationParametersBatchJavaObj.@de.kugihan.dictionaryformids.translation.TranslationParametersBatch::removeTranslationParametersAt(*));
			translationParametersBatchJavaObj.removeAllTranslationParameters = 	$entry(translationParametersBatchJavaObj.@de.kugihan.dictionaryformids.translation.TranslationParametersBatch::removeAllTranslationParameters(*));
			return translationParametersBatchJavaObj;
		}

		// setting callback functions for translation result
		function setTranslationResultCallbackJs(deletePreviousTranslationResultCallbackParam,
		                                        newTranslationResultCallbackParam) {
			TranslationExecution.deletePreviousTranslationResultCallback = deletePreviousTranslationResultCallbackParam;
			TranslationExecution.newTranslationResultCallback = newTranslationResultCallbackParam;
	    }
	    
		//
		// TranslationExecution
		//
		function executeTranslationJs(translationParametersObj,
		                              deletePreviousTranslationResultCallback,
		                              newTranslationResultCallback) {
		    setTranslationResultCallbackJs(deletePreviousTranslationResultCallback,
		                                   newTranslationResultCallback);
			var executeTranslationFunction = $entry(@de.kugihan.dictionaryformids.client.TranslationLayerGWT::executeTranslation(*));
			executeTranslationFunction(translationParametersObj);
		}
		function executeTranslationBatchJs(translationParametersBatchObj,
		                              deletePreviousTranslationResultCallback,
		                              newTranslationResultCallback) {
		    setTranslationResultCallbackJs(deletePreviousTranslationResultCallback,
		                                   newTranslationResultCallback);
			var executeTranslationBatchFunction = $entry(@de.kugihan.dictionaryformids.client.TranslationLayerGWT::executeTranslationBatch(*));
			executeTranslationBatchFunction(translationParametersBatchObj);
		}
		
		function checkForBrowserCompatibilityJs() {
			// Check whether Application Cache is supported
			var applicationCacheSupported = true;
			try {
				if (applicationCache == undefined) applicationCacheSupported = false;
			}
			catch (e) {applicationCacheSupported = false;}
			if (! applicationCacheSupported) {
				alert("Your Browser does not support HTML5 Application Cache - please upgrade your browser to a newer version !");
			}
		
			// Check whether Local Storage is supported
			var localStorageSupported = true;
			try {
				if (localStorage == undefined) localStorageSupported = false;
			}
			catch (e) {localStorageSupported = false;}
			if (! localStorageSupported) {
				alert("Your Browser does not support HTML5 Local Storage - please upgrade your browser to a newer version !");
			}
		
			// Check whether XMLHttpRequest.overrideMimeType is supported
			var htrOverrideMimeTypeSupported = true;
			var htr = new XMLHttpRequest();
			try {
				if (htr.overrideMimeType == undefined) htrOverrideMimeTypeSupported = false;
			}
			catch (e) {htrOverrideMimeTypeSupported = false;}		
			if (! htrOverrideMimeTypeSupported) {
				alert("Your Browser does not support HTML5 overrideMimeType - please upgrade your browser to a newer version !");
			}
		}
		checkForBrowserCompatibilityJs();

		$wnd.TranslationExecution = new Object();
		var TranslationExecution = $wnd.TranslationExecution;
		TranslationExecution.executeTranslation			  = executeTranslationJs;
		TranslationExecution.executeTranslationBatch	  = executeTranslationBatchJs;
		TranslationExecution.newTranslationParameters     = newTranslationParametersJs;

		//
		// ContentParser
		// Note: the Javascript object is called ContentParser, not contentParserObj as in Java
		//
		function determineItemsFromContent(contentText, 
										   changeInputAndOutputContent,
										   isInput) {
			var stringColourItemText =                           
				@de.kugihan.dictionaryformids.client.TranslationLayerGWT::determineItemsFromContent(*)
										  (contentText, 
										   changeInputAndOutputContent,
										   isInput);
			// 
			// create JavaScript methods for accessing Java methods
			//
			// StringColourItemText
			stringColourItemText.getItemTextPart				= $entry(stringColourItemText.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText::getItemTextPart(*));
			stringColourItemText.size							= $entry(stringColourItemText.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText::size());
			// each stringColourItemTextPart within stringColourItemText
			for (var i = 0; i < stringColourItemText.size(); i++) {
				stringColourItemTextPart = stringColourItemText.getItemTextPart(i);
				stringColourItemTextPart.getText				= $entry(stringColourItemTextPart.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemTextPart::getText());
				stringColourItemTextPart.getColour				= $entry(stringColourItemTextPart.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemTextPart::getColour());
				rgbColour = stringColourItemTextPart.getColour();
				rgbColour.getHexValue							= $entry(rgbColour.@de.kugihan.dictionaryformids.dataaccess.content.RGBColour::getHexValue());
				stringColourItemTextPart.getStyle				= $entry(stringColourItemTextPart.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemTextPart::getStyle());
				style = stringColourItemTextPart.getStyle();
				style.style 									= style.@de.kugihan.dictionaryformids.dataaccess.content.FontStyle::style; 
			}
			return stringColourItemText;
		}
		$wnd.ContentParser = new Object();
		var ContentParser = $wnd.ContentParser;
		ContentParser.determineItemsFromContent	= determineItemsFromContent;

		//
		// PredefinedContent
		//
		// to be done: fields/methods of PredefinedContent should be made directly available in Javascript. 
		$wnd.PredefinedContent = new Object();
		var predefinedContent = $wnd.PredefinedContent;
		predefinedContent.getPredefinedContent	= $entry(@de.kugihan.dictionaryformids.dataaccess.content.PredefinedContent::getPredefinedContent(*));
		 
    }-*/;

	protected static native void exportLoadedDictionary(DictionaryDataFile dictionary) /*-{
		function setJsFieldsForLanguageDefinition(supportedLanguageObj) {
			supportedLanguageObj.languageDisplayText = 			supportedLanguageObj.@de.kugihan.dictionaryformids.dataaccess.LanguageDefinition::languageDisplayText;
			supportedLanguageObj.languageFilePostfix = 			supportedLanguageObj.@de.kugihan.dictionaryformids.dataaccess.LanguageDefinition::languageFilePostfix;
			supportedLanguageObj.normationClassName = 			supportedLanguageObj.@de.kugihan.dictionaryformids.dataaccess.LanguageDefinition::normationClassName;
			supportedLanguageObj.isSearchable = 				supportedLanguageObj.@de.kugihan.dictionaryformids.dataaccess.LanguageDefinition::isSearchable;
			supportedLanguageObj.normationObj = 				supportedLanguageObj.@de.kugihan.dictionaryformids.dataaccess.LanguageDefinition::normationObj;
			supportedLanguageObj.indexNumberOfSourceEntries = 	supportedLanguageObj.@de.kugihan.dictionaryformids.dataaccess.LanguageDefinition::indexNumberOfSourceEntries;
			supportedLanguageObj.contentDefinitionAvailable = 	supportedLanguageObj.@de.kugihan.dictionaryformids.dataaccess.LanguageDefinition::contentDefinitionAvailable;
			supportedLanguageObj.languageIcon = 				supportedLanguageObj.@de.kugihan.dictionaryformids.dataaccess.LanguageDefinition::languageIcon;
			supportedLanguageObj.contents = 					supportedLanguageObj.@de.kugihan.dictionaryformids.dataaccess.LanguageDefinition::contents;
			// for each of the content, set the Javascript fields
			for (var i=0; i < supportedLanguageObj.contents.length; ++i) {
				setJsFieldsForContentDefinition(supportedLanguageObj.contents[i]);
			}
		}

		function setJsFieldsForContentDefinition(contentObj) {
			contentObj.contentDisplayText = 	contentObj.@de.kugihan.dictionaryformids.dataaccess.content.ContentDefinition::contentDisplayText;
			contentObj.fontColour = 			contentObj.@de.kugihan.dictionaryformids.dataaccess.content.ContentDefinition::fontColour;
			contentObj.fontStyle = 				contentObj.@de.kugihan.dictionaryformids.dataaccess.content.ContentDefinition::fontStyle;
			contentObj.selectionMode = 			contentObj.@de.kugihan.dictionaryformids.dataaccess.content.ContentDefinition::selectionMode;
			contentObj.displaySelectable = 		contentObj.@de.kugihan.dictionaryformids.dataaccess.content.ContentDefinition::displaySelectable;
		}

		dictionary.numberOfAvailableLanguages = 	dictionary.@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::numberOfAvailableLanguages;
		dictionary.numberOfInputLanguages = 		dictionary.@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::numberOfInputLanguages;
		dictionary.infoText = 						dictionary.@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::infoText;
		dictionary.dictionaryAbbreviation = 		dictionary.@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::dictionaryAbbreviation;
		dictionary.applicationFileNamePrefix = 		           @de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::applicationFileNamePrefix;  // static field
		dictionary.supportedLanguages = 			dictionary.@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::supportedLanguages;
		var supportedLanguages = dictionary.supportedLanguages;
		// for each of the supportedLanguage, set the Javascript fields
		for (var i=0; i < dictionary.numberOfAvailableLanguages; ++i) {
			setJsFieldsForLanguageDefinition(supportedLanguages[i]);
		}
		$wnd.dictionary = dictionary;
    }-*/;
	 			



	protected static native String getCurrentURLJs() /*-{
		return $wnd.location.href;
    }-*/;

	protected String getBaseDirectory() 
		throws DictionaryException {
		final char pathSeparator = '/';
		String url = getCurrentURLJs();
		// strip off the name of the html file:
		int pos = url.lastIndexOf(pathSeparator);
		if (pos < 0) throw new DictionaryException("URL could not be parsed");
		return url.substring(0, pos);
	}	
}
