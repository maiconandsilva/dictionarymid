/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de))

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_j2me.mainform;

import java.util.Enumeration;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.ItemStateListener;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.SettingsStore;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.hmi_common.content.ContentParser;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.hmi_j2me.DictionaryForMIDs;
import de.kugihan.dictionaryformids.hmi_j2me.DictionarySettingForm;
import de.kugihan.dictionaryformids.hmi_j2me.DictionarySettings;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.DfMAlert;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.DfMCommand;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.DfMForm;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.DfMStringItem;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.DfMTextField;
import de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension.StringColourItem;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.LanguageUI;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.UIDisplayTextItem;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.UIDisplayTextItems;
import de.kugihan.dictionaryformids.translation.SingleTranslation;
import de.kugihan.dictionaryformids.translation.Translation;
import de.kugihan.dictionaryformids.translation.TranslationExecution;
import de.kugihan.dictionaryformids.translation.TranslationExecutionCallback;
import de.kugihan.dictionaryformids.translation.TranslationResult;

public class MainForm 
            extends    DfMForm
			implements CommandListener,
	                   ItemStateListener,
	                   ItemCommandListener,
	                   TranslationExecutionCallback {
	
	public static MainForm applicationMainForm;
	MainFormItems mainFormItemsObj;

	protected final int  indexOfToBeTranslatedWordTextField = 0;
	protected int  indexOfFirstTranslationItem = 0; 
	protected int  indexOfLastTranslationItem = -1;
	// for selction word
	public    int  indexOfSelectedItem = -1;
	private String translatedWord = null;
	public int systemBackgroundColour;
	public int stringColourItemWidth;
	
	DfMStringItem translationResultStatus;
	DfMStringItem responseTimeItem;
	DfMStringItem freeMemoryItem;
	DfMStringItem statisticItem;
	DfMTextField  toBeTranslatedWordTextField;

	DfMCommand exitCommand = null;
	DfMCommand settingsCommand = null;
	DfMCommand helpCommand = null;
	DfMCommand infoCommand = null;
	DfMCommand translateCommand = null;
	DfMCommand newWordCommand = null;
	DfMCommand backWordCommand = null;
	DfMCommand forwardWordCommand = null;
	DfMCommand changeInputLanguageCommand = null;
	DfMCommand backToTranslationList = null; // todo: to be implemented
	
	public Display display;
	DictionaryForMIDs dictionaryForMIDsMidlet;
	DictionarySettingForm dictionarySettingFormObj = null;
	ContentParser contentParserObj;
	WordHistory wordHistoryObj;
	
	TranslationResult lastResultOfTranslation = null ;  // contains the result from 
														// the last translation
	boolean translationListIsShown = false; // is set to true when the 
										    // translation list is currently shown
	
	public MainForm(DictionaryForMIDs DictionaryForMIDsMidletParam) {
		super(DictionaryForMIDs.applicationName);
		/*
		 * Initialisation of required objects
		 */
		applicationMainForm = this; 
		dictionaryForMIDsMidlet = DictionaryForMIDsMidletParam;
		display = Display.getDisplay(dictionaryForMIDsMidlet);
		contentParserObj = new ContentParser();
		wordHistoryObj = new WordHistory();
		systemBackgroundColour = display.getColor(0);
	}

	public void initialiseForm() 
	             throws DictionaryException {
		/*
		 *  create display items
		 */
		updateMainFormItemsObj();

		translationResultStatus = new DfMStringItem();
		translationResultStatus.setLayout(Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_2);
		append(translationResultStatus);
		
		responseTimeItem = new DfMStringItem();
		responseTimeItem.setLayout(Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_2);
		append(responseTimeItem);

		statisticItem = new DfMStringItem();
		statisticItem.setLayout(Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_2);
		append(statisticItem);
		
		freeMemoryItem = new DfMStringItem();
		freeMemoryItem.setLayout(Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_2);
		append(freeMemoryItem);

		// create the command objects:
		setupCommands();
		
		Util.getUtil().log("Display elements created", Util.logLevelMax);

		// create settings form and get settings
		dictionarySettingFormObj = new DictionarySettingForm(display, this);
		Util.getUtil().log("Settingsform created", Util.logLevelMax);

		// settings are available now: update display
		updateSelectedLanguage();
		
		updateFonts();

		displayStatisticItems();
		
		// special workaround for SE OutOfMemoryError
		checkForSonyEricssonWorkaround();
	}
	
	public void startForm() {
		setCommandListener(this);
		updateIncrementalSearchSetting();
		display.setCurrent(this);
		TranslationExecution.setTranslationExecutionCallback(this);
	}
	
	// sets up the commands for this form:
	public void setupCommands() throws DictionaryException {
		super.setupCommands();
		settingsCommand = updateCommand(settingsCommand, UIDisplayTextItems.CommandSettings, Command.SCREEN, 6);
		helpCommand = updateCommand(helpCommand, UIDisplayTextItems.CommandHelp, Command.SCREEN, 8);
		infoCommand = updateCommand(infoCommand, UIDisplayTextItems.CommandInfo, Command.SCREEN, 9);
		translateCommand = updateCommand(translateCommand, UIDisplayTextItems.CommandTranslate, Command.ITEM, 1);
		newWordCommand = updateCommand(newWordCommand, UIDisplayTextItems.CommandNewWord, Command.SCREEN, 3);
		forwardWordCommand = updateCommand(forwardWordCommand, UIDisplayTextItems.CommandForwardWord, Command.SCREEN, 5);
		backWordCommand = updateCommand(backWordCommand, UIDisplayTextItems.CommandBackWord, Command.SCREEN, 4);
		changeInputLanguageCommand = updateCommand(changeInputLanguageCommand, UIDisplayTextItems.CommandChangeInputLanguage, Command.SCREEN, 2);
		
		// for the exitCommand Command.EXIT is not used, because typically the devices reserve a high-priority 
		// location for this and several users requested to put this command at a lower-priority location (usually
		// somewhere in the option menu):
		exitCommand = updateCommand(exitCommand, UIDisplayTextItems.CommandExit, Command.SCREEN, 7);   
		
		// Set the translationListItemCommand for all translationList items that are displayed
		if (translationListIsShown) {
			for (int itemCount = indexOfFirstTranslationItem; itemCount <= indexOfLastTranslationItem; ++itemCount) {
				get(itemCount).setDefaultCommand(translateCommand);
			}
		}
	}

	public void commandAction(Command c, Displayable s) {
		try {
			if (c == exitCommand)
				dictionaryForMIDsMidlet.notifyDestroyed();
			else if (c == translateCommand) 
				translateToBeTranslatedWordTextField(false);
			else if (c == settingsCommand)
				dictionarySetting();
			else if (c == newWordCommand)
				newWord();
			else if (c == forwardWordCommand)
				wordHistoryObj.doForwardWord();
			else if (c == backWordCommand)
				wordHistoryObj.doBackWord();
			else if (c == changeInputLanguageCommand)
				changeInputLanguage();
			else if (c == helpCommand)
				showHelp();
			else if (c == infoCommand)
				showInfo();
		}
		catch (Throwable t) {
			Util.getUtil().log(t);
		}
	}

	public void commandAction(Command c, Item item) {
		try
		{
			if (c == translateCommand) {
				displayTranslationForListItem(item);
			}
		}
		catch (Throwable t)
		{
			Util.getUtil().log(t);
		}
	}
	
	public void itemStateChanged(Item item) {
		try
		{
			if (item == toBeTranslatedWordTextField) {
				// start background translation ("incremental translation")
				translateToBeTranslatedWordTextField(true);
			}
		}
		catch (Throwable t)
		{
			Util.getUtil().log(t);
		}
	}
	
	public void translateToBeTranslatedWordTextField(boolean executeInBackground) 
			throws DictionaryException {
		translateWord(toBeTranslatedWordTextField.getString(),
					  executeInBackground);
	}
	
	public void translateWord(String word,
			  				  boolean executeInBackground)  
			throws DictionaryException {
		// for JSR75 support this needs to be done in a separate thread
		if (DictionarySettings.isUseFileAccessJSR75())
			executeInBackground = true;
		TranslationExecution.executeTranslation(addSearchCharacters(word),  
											    executeInBackground);
		setFocusToBeTranslatedWordTextField(); 	
		translatedWord = word;
		indexOfSelectedItem = -1;
	}

	//used for selectionWord
	public void translateSelectedWord(String word)  
			throws DictionaryException {
		toBeTranslatedWordTextField.setString(word);
		translateWord(word, false);
	}
	
	public void setCurrentSelectedItem(int index, String selectedWord, boolean isDisplayCurrentItem){
		if(index != indexOfSelectedItem){
			if(indexOfSelectedItem != -1){
				StringColourItem stringColourItem = (StringColourItem) this.get(indexOfSelectedItem);
				stringColourItem.clearSelectedWord();
			}
			indexOfSelectedItem = index;
		}
		if(	selectedWord == null) 	toBeTranslatedWordTextField.setString(translatedWord);
		else	toBeTranslatedWordTextField.setString(selectedWord);
		if(isDisplayCurrentItem) display.setCurrentItem(this.get(index)); 
	}
	
	public void setSelectedWordNextItem(int index){
		for (int currentIndexTranslationItem = index +1;
		 			currentIndexTranslationItem <= indexOfLastTranslationItem;  ++currentIndexTranslationItem) {
			StringColourItem stringColourItem = (StringColourItem) this.get(currentIndexTranslationItem);
			if(stringColourItem.isSelectableWord()){
				stringColourItem.selectNextWord();
				break;
			}		    					
		}	
	}
	
	public void setSelectedWordPrevItem(int index){
		for (int currentIndexTranslationItem = index-1; currentIndexTranslationItem > 0;  currentIndexTranslationItem --) {
			StringColourItem stringColourItem = (StringColourItem) this.get(currentIndexTranslationItem);
			if(stringColourItem.isSelectableWord()){
				stringColourItem.selectPrevWord();
				break;
			}
		}	
	}

	public void updateStringColourItemWidth(int index, int width, int fontSize){
		StringColourItemText itemText = ((StringColourItem) this.get(index)).getColourItem();
		this.delete(index);
		this.insert(index, new StringColourItem(itemText, width, fontSize, index, this));
		stringColourItemWidth = width;
	}
	
	public void newWord() {
		// empties the to be translated word and sets the focus on that field 
		toBeTranslatedWordTextField.setString("");
		setFocusToBeTranslatedWordTextField();
	}

	public void setFocusToBeTranslatedWordTextField() {
		display.setCurrentItem(toBeTranslatedWordTextField); 
	}

	public void changeInputLanguage() 
			throws DictionaryException {
		// chooses the next input language
		dictionarySettingFormObj.selectNextInputLanguage();
	}
	
	public void dictionarySetting() {
		Display.getDisplay(dictionaryForMIDsMidlet).setCurrent(dictionarySettingFormObj);	
	}
	
	public void showHelp() throws DictionaryException {
		UIDisplayTextItem helpText = UIDisplayTextItems.HelpTextContent;
		helpText.setAllParameterValues( new String [] { 
		                                       String.valueOf(Translation.wildcardAnySeriesOfCharacter),
		                                       String.valueOf(Translation.wildcardAnySingleCharacter),
		                                       String.valueOf(Translation.noSearchSubExpressionCharacter),
		                                       String.valueOf(Translation.noSearchSubExpressionCharacter) 
		                                     } );
		DfMAlert helpPage = new DfMAlert(UIDisplayTextItems.CommandHelp, helpText, AlertType.INFO);
		helpPage.setTimeout(Alert.FOREVER);
		Display.getDisplay(dictionaryForMIDsMidlet).setCurrent(helpPage, this);	
	}
	
	public void showInfo() throws DictionaryException {
		String versionStatusString = dictionaryForMIDsMidlet.versionStatus;
		if (versionStatusString == null)
			versionStatusString = new String("");
		else
			versionStatusString = "/ " + versionStatusString;
		UIDisplayTextItem infoText = UIDisplayTextItems.InfoTextContent;
		infoText.setAllParameterValues( new String [] { 
												DictionaryDataFile.infoText,
												dictionaryForMIDsMidlet.applicationName,
												"Gert Nuber (dict@kugihan.de)",
												"http://dictionarymid.sourceforge.net",
												dictionaryForMIDsMidlet.versionNumber + versionStatusString
								              } );		
		DfMAlert infoPage = new DfMAlert(UIDisplayTextItems.CommandInfo, infoText, AlertType.INFO);
		infoPage.setTimeout(Alert.FOREVER);
		Display.getDisplay(dictionaryForMIDsMidlet).setCurrent(infoPage, this);
	}

	public void displayStatisticItems() throws DictionaryException {
		if (DictionarySettings.getShowStatistic()) {
			responseTimeItem.setLabel(UIDisplayTextItems.StatisticResponseTimeInMS);
			statisticItem.setLabel(UIDisplayTextItems.StatisticHits);
			freeMemoryItem.setLabel(UIDisplayTextItems.StatisticFreeMemory);
			freeMemoryItem.setText(String.valueOf(Runtime.getRuntime().freeMemory()));
		} else {
			statisticItem.setLabel(UIDisplayTextItems.EmptyText);
			statisticItem.setText(null);
			responseTimeItem.setLabel(UIDisplayTextItems.EmptyText);
			responseTimeItem.setText(null);
			freeMemoryItem.setLabel(UIDisplayTextItems.EmptyText);
			freeMemoryItem.setText(null);			
		}	
	}

	public void updateMainFormItemsObj() throws DictionaryException {
		// update old translation results
		deletePreviousTranslationResult();
		
		// remove old toBeTranslatedWordTextField
		if (toBeTranslatedWordTextField != null)
			delete(indexOfToBeTranslatedWordTextField);
		
		// determine colouredItems from the settings store (not DictionarySettings) because DictionarySettingsForm
		// may be initialised yet
		boolean isColouredItems;
		isColouredItems = SettingsStore.getSettingsStore().getColouredItems();
		boolean useBitmapFont;
		useBitmapFont = SettingsStore.getSettingsStore().getUseBitmapFont();
		
		// create new mainFormItemsObj
		if (useBitmapFont && isColouredItems) {
			mainFormItemsObj = new MainFormItemsBitmap(this, true);
		} else if (useBitmapFont) {
			mainFormItemsObj = new MainFormItemsBitmap(this, false);
		} else if (isColouredItems) {
			mainFormItemsObj = new MainFormItemsColoured(this);
		} else {
			mainFormItemsObj = new MainFormItemsSimple(this);
		}
		
		// create new toBeTranslatedWordTextField 
		toBeTranslatedWordTextField = mainFormItemsObj.createToBeTranslatedWordTextField(); 
		updateSelectedLanguage();
		insert(indexOfToBeTranslatedWordTextField, toBeTranslatedWordTextField);
		indexOfFirstTranslationItem = indexOfToBeTranslatedWordTextField + 1; 
		
		// redisplay translation results
		refreshAllTranslationResults();
		
		display.setCurrentItem(toBeTranslatedWordTextField); 
	}

	// update the fonts of the translated texts:
	public void updateFonts() 
				throws DictionaryException {
		if (!DictionarySettings.getUseBitmapFont()) // todo: font size changes must be possible with activated bitmap fonts
		mainFormItemsObj.updateFonts();
	}
	
	public void updateSelectedLanguage() 
				throws DictionaryException {
		if (DictionarySettings.numberOfSearchableInputLanguages() == 1) {  
			// only one input language: no need to indicate translation direction
			toBeTranslatedWordTextField.setLabel(UIDisplayTextItems.WordForTranslation);
		}
		else  {
			if ((DictionarySettings.isDictionaryAvailable()) && 
				(dictionarySettingFormObj != null)) { // in order to set from/to language the settings must be available 
				// more than one input language: indicate translation direction
				String inputLanguageText  = 
							LanguageUI.getUI().uiDisplayTextItemReference + 
							LanguageUI.getUI().uiDisplayTextItemPrefixLanguage + 
				 			DictionaryDataFile.supportedLanguages[DictionarySettings.getInputLanguage()].languageDisplayText;
				String outputLanguageText = 
							LanguageUI.getUI().uiDisplayTextItemReference + 
							LanguageUI.getUI().uiDisplayTextItemPrefixLanguage + 
				 			DictionaryDataFile.supportedLanguages[DictionarySettings.determineOutputLanguage()].languageDisplayText;
				UIDisplayTextItems.FromLanguageToLanguage.setAllParameterValues(
														new String [] { inputLanguageText, 
																		outputLanguageText });
				toBeTranslatedWordTextField.setLabel(UIDisplayTextItems.FromLanguageToLanguage);
			}
		}
	}
	
	// redisplay the labels of all forms
	public void refreshAllForms() throws DictionaryException  {
		// redisplay labels of this form:
		redisplayLabels();
		// redisplay labels of DictionarySettingsForm:
		dictionarySettingFormObj.redisplayLabels();
	}
	
	public void updateIncrementalSearchSetting() {
		if (! DictionarySettings.isIncrementalSearchEnabled()) {
			setItemStateListener(null);	
		}
		else {
			setItemStateListener(this);	
		}
	}
	
	protected String addSearchCharacters(String toBeTranslatedWord) {
		StringBuffer result = new StringBuffer(toBeTranslatedWord);
		if (result.length() > 0) {
			if (DictionarySettings.isAddAtEndWildcardAnySeriesOfCharacter()) {
				if (result.charAt(result.length()-1) != Util.wildcardAnySeriesOfCharacter)
					result.append(Util.wildcardAnySeriesOfCharacter);
			}
			if (DictionarySettings.isFindExactMatches()) {
				if (result.charAt(0) != Util.noSearchSubExpressionCharacter)
					result.insert(0, Util.noSearchSubExpressionCharacter);	
				if (result.charAt(result.length()-1) != Util.noSearchSubExpressionCharacter)
					result.append(Util.noSearchSubExpressionCharacter);
			}
			if (DictionarySettings.isAddAtBeginNoSearchSubExpressionCharacter()) {
				if (result.charAt(0) != Util.noSearchSubExpressionCharacter)
					result.insert(0, Util.noSearchSubExpressionCharacter);
			}
		}
		return result.toString();
	}
	
	public void displayTranslationForListItem(Item listItem) 
							throws DictionaryException {
		TranslationExecution.cancelLastTranslation();
		// search the item for which the translation result shall be displayed
		int indexItemForTranslation = -1;
		for (int itemCount = indexOfFirstTranslationItem; itemCount <= indexOfLastTranslationItem; ++itemCount) {
			if (get(itemCount) == listItem) {
				indexItemForTranslation = itemCount;
				break;
			}
		}
		if (indexItemForTranslation == -1)
			throw new DictionaryException("Translation list item not found");
		int indexSingleTranslation = indexItemForTranslation - indexOfFirstTranslationItem;
		SingleTranslation singleTranslation = 
					(SingleTranslation) lastResultOfTranslation.translations.elementAt(indexSingleTranslation);
		deletePreviousTranslationResult();
		displaySingleTranslation(singleTranslation, true);
		translationResultStatus.setLabel(UIDisplayTextItems.EmptyText);
		setFocusToBeTranslatedWordTextField();
	}
	
	/*
	 * Display of translation results
	 */
	
	public void deletePreviousTranslationResult() {
		// delete result of previous translation
		for (int currentIndexTranslationItem = indexOfFirstTranslationItem;
			 currentIndexTranslationItem <= indexOfLastTranslationItem;
			 ++currentIndexTranslationItem) {
			delete(indexOfFirstTranslationItem);
		}
		indexOfLastTranslationItem = indexOfFirstTranslationItem - 1;
	}

	public void newTranslationResult(TranslationResult resultOfTranslation) {
		try {
			// display result of new translation
			indexOfLastTranslationItem = indexOfFirstTranslationItem - 1;
			stringColourItemWidth = this.getWidth();
			UIDisplayTextItem translationResultSatus = UIDisplayTextItems.EmptyText;
			if (resultOfTranslation.translationFound) {
				Enumeration translationsEnum = resultOfTranslation.translations.elements();
				while (translationsEnum.hasMoreElements()) {
					SingleTranslation singleTranslation = (SingleTranslation) translationsEnum.nextElement();
					displaySingleTranslation(singleTranslation, false);
				}
				wordHistoryObj.saveHistoryWord();
			}
			else {
				translationResultSatus = UIDisplayTextItems.TranslationMessageNotFound;
			}
			
			if (resultOfTranslation.translationBreakOccurred) {
				switch (resultOfTranslation.translationBreakReason) {
					case TranslationResult.BreakReasonMaxExecutionTimeReached:
						translationResultSatus = UIDisplayTextItems.TranslationMessageMaxExecutionTimeReached; 
					case TranslationResult.BreakReasonCancelMaxNrOfHitsReached:
						translationResultSatus = UIDisplayTextItems.TranslationMessageMaxNrOfHitsReached;
				}
			}
			translationResultStatus.setLabel(translationResultSatus);
			
			Util.memCheck("results displayed: ");
			if (DictionarySettings.getShowStatistic()) {
				String hitsText = String.valueOf(resultOfTranslation.numberOfHits);
				statisticItem.setText(hitsText);
				responseTimeItem.setText(String.valueOf(resultOfTranslation.executionTime));
				System.gc();
				freeMemoryItem.setText(String.valueOf(Runtime.getRuntime().freeMemory()));
			}
			
			// store the translation result for future use
			lastResultOfTranslation = resultOfTranslation;
			
			// specific handling for SonyEricsson devices
			applySonyEricssonWorkaround();
		}
		catch (Throwable t) {
			Util.getUtil().log(t);
		}
	}

	void displaySingleTranslation
					(SingleTranslation singleTranslation,
			         boolean           displayTranslationListContent)  // true when the result for one translation 
																	   // list item shall be displayed
				throws DictionaryException {
		String translationFromString = singleTranslation.fromText.toString();
		String translationToString   = singleTranslation.toText.toString();
		Item translationFromItem;
		Item translationToItem;

		boolean useBitmapFont = DictionarySettings.getUseBitmapFont();

		StringColourItemText translationFromItemText = 
			contentParserObj.determineItemsFromContent(translationFromString, 
													   DictionarySettings.getInputLanguage(),
													   true);
		StringColourItemText translationToItemText   = 
			contentParserObj.determineItemsFromContent(translationToString, 
													   DictionarySettings.determineOutputLanguage(),
													   true); 
		int width = this.getWidth();
		translationFromItem = mainFormItemsObj.createTranslationItem(translationFromItemText, true, width); 
		translationToItem   = mainFormItemsObj.createTranslationItem(translationToItemText, false, width); 

		addTranslationItem(translationFromItem);
		if (DictionarySettings.getShowTranslationList() && (! displayTranslationListContent)) {
			translationListIsShown = true;
			translationFromItem.setDefaultCommand(translateCommand);
			translationFromItem.setItemCommandListener(this);
		}
		else {
			translationListIsShown = false;
			addTranslationItem(translationToItem);
		}
	}
	
	void addTranslationItem(Item item) {
		++indexOfLastTranslationItem;
		insert(indexOfLastTranslationItem, item);
	}
	
	public void refreshAllTranslationResults() 
			throws DictionaryException {
		deletePreviousTranslationResult();
		if (lastResultOfTranslation != null) {
			int len = lastResultOfTranslation.translations.size();
			for (int i = 0; i < len; i++) {
				SingleTranslation st = (SingleTranslation) lastResultOfTranslation.translations.elementAt(i);
				displaySingleTranslation(st, false);
			}
		}
	}


	// Hope someone will solve this SonyEricsson problem, so that the following workaround will
	// not be necessary any more !
	//
	// Workaround for SonyEricsson OutOfMemoryError that occurs when deleting old translation
	// items at the beginning of executeTranslation.
	// The workaround is to display an info-dialog after the translation result has been displayed
	// It seems that then no OutOfMemoryError is thrown.
	boolean sonyEricssonWorkaroundRequired = false;
	void checkForSonyEricssonWorkaround() {
		String platform = System.getProperty("microedition.platform");
		if (platform != null) {
			if (platform.startsWith("SonyEricsson")) {
				if (!platform.startsWith("SonyEricssonP")) { // P-Series does not have the OutOfMemoryError
					sonyEricssonWorkaroundRequired = true;
				}
			}
		}
	}
	void applySonyEricssonWorkaround() {
		if (sonyEricssonWorkaroundRequired) {
			showSonyEricssonWorkaroundInfo();
		}
	}
	public void showSonyEricssonWorkaroundInfo() {
		String workaroundText = new String("SE workaround");
		Alert workaroundPage = new Alert("workaround", workaroundText, null, AlertType.INFO);
		workaroundPage.setTimeout(10);  // display with duration of 10 ms
		Display.getDisplay(dictionaryForMIDsMidlet).setCurrent(workaroundPage, this);
	}

}