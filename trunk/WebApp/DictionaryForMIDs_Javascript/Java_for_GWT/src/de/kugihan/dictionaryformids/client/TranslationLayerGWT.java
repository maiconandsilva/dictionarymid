package de.kugihan.dictionaryformids.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
public class TranslationLayerGWT implements EntryPoint , TranslationExecutionCallback{

	public static ContentParser contentParserObj;

	/**
	  * This is the entry point method.
    */
	public void onModuleLoad() {
		UtilJs utilObj = new UtilJs();
		Util.setUtil(utilObj);
		exportStaticMethods();
		exportPredefinedContent();
		contentParserObj = new ContentParser();
		  try {
			HTRInputStream.setBaseDirectory(getBaseDirectory());
			FileAccessHandler.setDictionaryDataFileISAccess(new HTRInputStreamAccess());
			CsvFile.fileStorageReader = new HTRFileStorageReader();
			DictionaryDataFile.initValues(false);
			exportDictionaryDataFileDataStructures();
			TranslationExecution.setTranslationExecutionCallback(this);
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
	
	public static void executeTranslation(String  		 toBeTranslatedWordTextInputParam,
										  JsArrayBoolean inputLanguagesParam,
										  JsArrayBoolean outputLanguagesParam,
										  boolean 		 executeInBackgroundParam,
										  int            maxHitsParam,
										  int   		 durationForCancelSearchParam) 
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
						new TranslationParameters(toBeTranslatedWordTextInputParam,
												  inputLanguages,
												  outputLanguages,
												  executeInBackgroundParam,
												  maxHitsParam,
												  durationForCancelSearchParam);
		// execute translation in the Translation Layer
		TranslationExecution.executeTranslation(translationParametersObj);
	}
	  
	public static StringColourItemText determineItemsFromContent(TextOfLanguage contentText, 
			                                              boolean changeInputAndOutputContent,
			                                              boolean isInput) {
		StringColourItemText stringColourItemText = null;
		try {
			stringColourItemText = contentParserObj.determineItemsFromContent(contentText, changeInputAndOutputContent, isInput);
		}
		catch (Exception e) { Util.getUtil().log(e); }
		return stringColourItemText;
	}

	protected static native void callDeletePreviousTranslationResultJs() /*-{
		$wnd.deletePreviousTranslationResult();
    }-*/;

	protected static native void callNewTranslationResultJs(TranslationResult resultOfTranslation) /*-{
		// 
		// create JavaScript methods for accessing Java methods
		//
		// TranslationResult
   		resultOfTranslation.numberOfFoundTranslations 		= resultOfTranslation.@de.kugihan.dictionaryformids.translation.TranslationResult::numberOfFoundTranslations();
   		resultOfTranslation.translationFound 				= resultOfTranslation.@de.kugihan.dictionaryformids.translation.TranslationResult::translationFound();
   		resultOfTranslation.getTranslationAt 				= resultOfTranslation.@de.kugihan.dictionaryformids.translation.TranslationResult::getTranslationAt(*);

		// each SingleTranslation within TranslationResult
   		for (var i = 0; i < resultOfTranslation.numberOfFoundTranslations(); i++) {
   			singleTranslation = resultOfTranslation.getTranslationAt(i);
   			singleTranslation.getFromText 					= singleTranslation.@de.kugihan.dictionaryformids.translation.SingleTranslation::getFromText();
   			singleTranslation.getNumberOfToTexts 			= singleTranslation.@de.kugihan.dictionaryformids.translation.SingleTranslation::getNumberOfToTexts();
   			singleTranslation.getToTextAt 					= singleTranslation.@de.kugihan.dictionaryformids.translation.SingleTranslation::getToTextAt(*);  			
   			// fromText within singleTranslation
   			setJsMethodsForJavaTextOfLanguage(singleTranslation.getFromText);
   			// each toText within singleTranslation
   			for (var j = 0; j < singleTranslation.getNumberOfToTexts(); j++) {
   				setJsMethodsForJavaTextOfLanguage(singleTranslation.getToTextAt(j));
   			}
   		}

   		function setJsMethodsForJavaTextOfLanguage(textOfLanguage)  {
   			textOfLanguage.getLanguageIndex					= textOfLanguage.@de.kugihan.dictionaryformids.translation.TextOfLanguage::getLanguageIndex();
   			textOfLanguage.getText 							= textOfLanguage.@de.kugihan.dictionaryformids.translation.TextOfLanguage::getText();
   		}
   		
		$wnd.newTranslationResult(resultOfTranslation);
    }-*/;
	 
	protected static native void exportStaticMethods() /*-{
		function executeTranslationJs(toBeTranslatedWordTextInputParam,
									 inputLanguagesParam,
									 outputLanguagesParam,
									 executeInBackgroundParam,
									 maxHitsParam,
									 durationForCancelSearchPara) {
			var executeTranslationFunction = @de.kugihan.dictionaryformids.client.TranslationLayerGWT::executeTranslation(*);
			executeTranslationFunction(toBeTranslatedWordTextInputParam,
									   inputLanguagesParam,
									   outputLanguagesParam,
									   executeInBackgroundParam,
									   maxHitsParam,
									   durationForCancelSearchPara);
		}
	   
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
			stringColourItemText.getItemTextPart				= stringColourItemText.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText::getItemTextPart(*);
			stringColourItemText.size							= stringColourItemText.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText::size();
			// each stringColourItemTextPart within stringColourItemText
			for (var i = 0; i < stringColourItemText.size(); i++) {
				stringColourItemTextPart = stringColourItemText.getItemTextPart(i);
				stringColourItemTextPart.getText				= stringColourItemTextPart.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemTextPart::getText();
				stringColourItemTextPart.getColour				= stringColourItemTextPart.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemTextPart::getColour();
				rgbColour = stringColourItemTextPart.getColour();
				rgbColour.getHexValue							= rgbColour.@de.kugihan.dictionaryformids.dataaccess.content.RGBColour::getHexValue();
				stringColourItemTextPart.getStyle				= stringColourItemTextPart.@de.kugihan.dictionaryformids.hmi_common.content.StringColourItemTextPart::getStyle();
				style = stringColourItemTextPart.getStyle();
				style.style 									= style.@de.kugihan.dictionaryformids.dataaccess.content.FontStyle::style; 
			}
			return stringColourItemText;
		}
		
		$wnd.executeTranslation = executeTranslationJs;
		$wnd.determineItemsFromContent = determineItemsFromContent;
    }-*/;

	protected static native void exportDictionaryDataFileDataStructures() /*-{
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

		$wnd.setJsFieldsForContentDefinition = setJsFieldsForContentDefinition;  // this method may be needed by the HMI
		$wnd.DictionaryDataFile = new Object();
		var dictionaryDataFile = $wnd.DictionaryDataFile;
		dictionaryDataFile.numberOfAvailableLanguages = 	@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::numberOfAvailableLanguages;
		dictionaryDataFile.numberOfInputLanguages = 		@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::numberOfInputLanguages;
		dictionaryDataFile.infoText = 						@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::infoText;
		dictionaryDataFile.dictionaryAbbreviation = 		@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::dictionaryAbbreviation;
		dictionaryDataFile.applicationFileNamePrefix = 		@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::applicationFileNamePrefix;
		dictionaryDataFile.supportedLanguages = 			@de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile::supportedLanguages;
		var supportedLanguages = dictionaryDataFile.supportedLanguages;
		// for each of the supportedLanguage, set the Javascript fields
		for (var i=0; i < dictionaryDataFile.numberOfAvailableLanguages; ++i) {
			setJsFieldsForLanguageDefinition(supportedLanguages[i]);
		}
    }-*/;
	 			
	protected static native void exportPredefinedContent() /*-{
		$wnd.PredefinedContent = new Object();
		var predefinedContent = $wnd.PredefinedContent;
		predefinedContent.getPredefinedContent	= @de.kugihan.dictionaryformids.dataaccess.content.PredefinedContent::getPredefinedContent(*);
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
