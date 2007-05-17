/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de))

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_java_me.mainform;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dataaccess.LanguageDefinition;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.SettingsStore;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.hmi_common.content.ContentParser;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.hmi_java_me.DictionaryForMIDs;
import de.kugihan.dictionaryformids.hmi_java_me.DictionarySettingForm;
import de.kugihan.dictionaryformids.hmi_java_me.DictionarySettings;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.DfMAlert;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.DfMCommand;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.DfMForm;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.DfMStringItem;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.DfMTextField;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.ResourceHandler;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.StringColourItem;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.LanguageUI;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.UIDisplayTextItem;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.UIDisplayTextItems;
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
	DfMCommand backToTranslationList = null;
	
	public Display display;
	DictionaryForMIDs dictionaryForMIDsMidlet;
	DictionarySettingForm dictionarySettingFormObj = null;
	ContentParser contentParserObj;
	WordHistory wordHistoryObj;
	
	// contains the result from the last translation:
	TranslationResult lastResultOfTranslation = null ;  

	// used when DictionarySettings.getShowTranslationList() is true:
	// - when translationListDisplayList is true then the translation list is currently shown
	// - when translationListDisplayList is false then a single translation is currently shown
	boolean translationListDisplayList;
	
	// when a translation for an item in the translation list is requested, then the index of this
	// item is saved in translationListFocusedItem;
	int		translationListFocusedItemIndex;
	
	
	public MainForm(DictionaryForMIDs DictionaryForMIDsMidletParam) {
		/*
		 * Initialisation of required objects
		 */
		applicationMainForm = this; 
		dictionaryForMIDsMidlet = DictionaryForMIDsMidletParam;
		display = Display.getDisplay(dictionaryForMIDsMidlet);
		contentParserObj = new ContentParser();
		wordHistoryObj = new WordHistory();
		systemBackgroundColour = display.getColor(0);

		// special workaround for SE OutOfMemoryError
		checkForSonyEricssonWorkaround();
	}

	public void initialiseForm() 
	             throws DictionaryException {
		// load the settings
		DictionarySettingForm.loadSettings();

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

		readDisplayTextProperties();
		
		showStartupDisplay();
	}
	
	public void startForm() {
		setCommandListener(this);
		updateIncrementalSearchSetting();
		display.setCurrent(this);
		TranslationExecution.setTranslationExecutionCallback(this);
	}
	
	/*
	 *  sets up the commands for this form:
	 */
	public void setupCommands() throws DictionaryException {
		super.setupCommands();
		settingsCommand = updateCommand(settingsCommand, UIDisplayTextItems.CommandSettings, Command.SCREEN, 6);
		helpCommand = updateCommand(helpCommand, UIDisplayTextItems.CommandHelp, Command.SCREEN, 8);
		infoCommand = updateCommand(infoCommand, UIDisplayTextItems.CommandInfo, Command.SCREEN, 9);
		newWordCommand = updateCommand(newWordCommand, UIDisplayTextItems.CommandNewWord, Command.SCREEN, 3);
		forwardWordCommand = updateCommand(forwardWordCommand, UIDisplayTextItems.CommandForwardWord, Command.SCREEN, 5);
		backWordCommand = updateCommand(backWordCommand, UIDisplayTextItems.CommandBackWord, Command.SCREEN, 4);
		changeInputLanguageCommand = updateCommand(changeInputLanguageCommand, UIDisplayTextItems.CommandChangeInputLanguage, Command.SCREEN, 2);
		// for the exitCommand Command.EXIT is not used, because typically the devices reserve a high-priority 
		// location for this and several users requested to put this command at a lower-priority location (usually
		// somewhere in the option menu):
		exitCommand = updateCommand(exitCommand, UIDisplayTextItems.CommandExit, Command.SCREEN, 7);   
		
		removeTranslationCommandScreen(); // remove translation command screen (if there was one)

		// set up commands for the form's items
		setupItemCommands();

		addTranslationCommandScreen();    // add translation command screen (if there is one required)		
	}

	// adds the translation command to the form
	void addTranslationCommandScreen() throws DictionaryException {
		if (! DictionarySettings.getShowTranslationList()) {
			translateCommand = updateCommand(translateCommand, UIDisplayTextItems.CommandTranslate, Command.SCREEN, 1);
		}
	}

	// removes the translation command from the form
	void removeTranslationCommandScreen()  throws DictionaryException {
		removeCommand(translateCommand);
	}

	/*
	 *  sets up the item commands for this form:
	 */
	public void setupItemCommands() throws DictionaryException {
		// remove old commands (if there are any)
		removeToBeTranslatedWordTextFieldCommand();
		for (int itemCount = indexOfFirstTranslationItem; itemCount <= indexOfLastTranslationItem; ++itemCount) {
			removeTranslationItemCommand(get(itemCount));
		}
		if (DictionarySettings.getShowTranslationList()) {
			// create new commands
			translateCommand = new DfMCommand(UIDisplayTextItems.CommandTranslate, Command.ITEM, 1);
			backToTranslationList = new DfMCommand(UIDisplayTextItems.CommandBack, Command.ITEM, 1);
			// set new commands
			setToBeTranslatedWordTextFieldCommand();
			// Set the translationListItemCommand for all translationList items that are displayed
			for (int itemCount = indexOfFirstTranslationItem; itemCount <= indexOfLastTranslationItem; ++itemCount) {
				setTranslationItemCommand(get(itemCount));
			}
		}
	}
	
	void removeToBeTranslatedWordTextFieldCommand()  {
		toBeTranslatedWordTextField.removeCommand(translateCommand);
	}

	void setToBeTranslatedWordTextFieldCommand() 
			throws DictionaryException {
		if (DictionarySettings.getShowTranslationList()) {
			updateItemCommand(toBeTranslatedWordTextField, null, translateCommand);
		}
	}

	void removeTranslationItemCommand(Item item) 
			throws DictionaryException {
		if (translationListDisplayList) {
			item.removeCommand(translateCommand);
		}
		else {
			item.removeCommand(backToTranslationList);
		}
	}
	
	void setTranslationItemCommand(Item item) 
			throws DictionaryException {
		if (DictionarySettings.getShowTranslationList()) {
			if (translationListDisplayList) {
				updateItemCommand(item, null, translateCommand);
			}
			else {
				updateItemCommand(item, null, backToTranslationList);
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
				if (item == toBeTranslatedWordTextField) { 
					translateToBeTranslatedWordTextField(false);
				}
				else {
					displayTranslationForListItem(item);
				}
			}
			else if (c == backToTranslationList) {
				displayItemsInTranslationList();
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
		removeStartupDisplay();
		translateWord(toBeTranslatedWordTextField.getString(),
					  executeInBackground);
	}
	
	public void translateWord(String word,
			  				  boolean executeInBackground)  
			throws DictionaryException {
		if (DictionarySettings.isDictionaryAvailable()) {
			// for JSR75 support this needs to be done in a separate thread
			if (DictionarySettings.isUseFileAccessJSR75())
				executeInBackground = true;
			TranslationExecution.executeTranslation(addSearchCharacters(word),  
												    executeInBackground);
			setFocusToBeTranslatedWordTextField(); 	
			translatedWord = word;
			indexOfSelectedItem = -1;
		}
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
		removeStartupDisplay();
		if (sonyEricssonWorkaroundRequired) {
			deletePreviousTranslationResult();
		}
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
		UIDisplayTextItem infoText = UIDisplayTextItems.InfoTextContent;
		// collect information about dictionary
		Object dictionaryInformation;
		Vector dictionaryInfoItems = new Vector();
		if (DictionarySettings.isDictionaryAvailable()) {
			for (int displayTextCounter = 0; displayTextCounter < displayTextProperties.length; ++displayTextCounter) {
				UIDisplayTextItem dictionaryDisplayTextItem = 
						LanguageUI.getUI().existingUIDisplayTextItem(displayTextProperties[displayTextCounter], true);
				if (dictionaryDisplayTextItem != null) {
					if (dictionaryInfoItems.size() > 0) {
					// todo	dictionaryInfoItems.addElement(LanguageUI.getUI().uiDisplayTextItemNewline);
					}
					dictionaryInfoItems.addElement(dictionaryDisplayTextItem);
				}
			}
			dictionaryInfoItems.removeAllElements() ; // todo: currently deactivated
			if (dictionaryInfoItems.size() != 0) {
				dictionaryInformation = dictionaryInfoItems;
			}
			else {
				// for support of legacy configuration files: use infoText when no dictionary information is there.
				dictionaryInformation = DictionaryDataFile.infoText;
			}
		}
		else {
			// no dictionary loaded
			dictionaryInformation = UIDisplayTextItems.MessageNoDictionaryLoaded;
		}
		if (dictionaryInformation == null) {
			throw new DictionaryException("No dictionary display texts configured");
		}
		infoText.setAllParameterValues( new Object [] { 
												dictionaryInformation,
												UIDisplayTextItems.DictionaryForMIDsApplicationName,
												"Gert Nuber (dict@kugihan.de)",
												"http://dictionarymid.sourceforge.net",
												Util.getUtil().getApplicationVersionString()
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
		setToBeTranslatedWordTextFieldCommand();
		toBeTranslatedWordTextField.setItemCommandListener(this);
		insert(indexOfToBeTranslatedWordTextField, toBeTranslatedWordTextField);
		indexOfFirstTranslationItem = indexOfToBeTranslatedWordTextField + 1; 
		
		// update the display of the selected language
		updateSelectedLanguage();

		// redisplay translation results
		refreshAllTranslationResults();
		
		//Causes an error on SonyEricsson devices when called before the midlet is started
		if (! sonyEricssonWorkaroundRequired) {
			display.setCurrentItem(toBeTranslatedWordTextField); 
		}
	}

	// update the fonts of the translated texts:
	public void updateFonts() 
				throws DictionaryException {
		if (!DictionarySettings.getUseBitmapFont())
		mainFormItemsObj.updateFonts();
	}
	
	public void updateSelectedLanguage() 
				throws DictionaryException {
		if (! DictionarySettings.isDictionaryAvailable()) {
			// no dictionary loaded
			setTitleUIDisplayTextItem(UIDisplayTextItems.MessageNoDictionaryLoaded);
		}
		else if (DictionarySettings.numberOfSearchableInputLanguages() == 1) {  
			// only one input language: no need to indicate translation direction; use application name instead
			setTitleUIDisplayTextItem(UIDisplayTextItems.DictionaryForMIDsApplicationName);
		}
		else  {
			if ((DictionarySettings.isDictionaryAvailable()) && 
				(dictionarySettingFormObj != null)) { // in order to set from/to language the settings must be available 
				                                      // more than one input language: indicate translation direction
				UIDisplayTextItem inputLanguageUIDisplayTextItem = 
					LanguageUI.getUI().getLanguageDisplayTextItem(DictionaryDataFile.supportedLanguages[DictionarySettings.getInputLanguage()].languageDisplayText);
				UIDisplayTextItem outputLanguageUIDisplayTextItem = 
					LanguageUI.getUI().getLanguageDisplayTextItem(DictionaryDataFile.supportedLanguages[DictionarySettings.determineOutputLanguage()].languageDisplayText);
				UIDisplayTextItems.FromLanguageToLanguage.setAllParameterValues(
														new Object [] { inputLanguageUIDisplayTextItem, 
																        outputLanguageUIDisplayTextItem });
				setTitleUIDisplayTextItem(UIDisplayTextItems.FromLanguageToLanguage);
			}
		}
	}
	
	// switchTranslationListDisplay is called when the settings for showTranslationList is changed
	public void switchTranslationListDisplay() throws DictionaryException {
		if (DictionarySettings.getShowTranslationList()) {
			// display of translation list is enabled
			translationListDisplayList = true;
		}
		else {
			// display of translation list is disabled
			translationListDisplayList = false;
		}
		// refresh command settings
		setupCommands();
		// update all items of the translation result:
		refreshAllTranslationResults();
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
		// remember index of focused item
		translationListFocusedItemIndex = indexItemForTranslation;
		int indexSingleTranslation = indexItemForTranslation - indexOfFirstTranslationItem;
		SingleTranslation singleTranslation = 
					lastResultOfTranslation.getTranslationAt(indexSingleTranslation);
		deletePreviousTranslationResult();
		translationListDisplayList = false;
		displaySingleTranslation(singleTranslation);
		// set the focus on the newly created translation item
		if (indexOfLastTranslationItem >= indexOfFirstTranslationItem) {
			display.setCurrentItem(get(indexOfFirstTranslationItem)); 
		}
		translationResultStatus.setLabel(UIDisplayTextItems.EmptyText);
		
                // specific handling for SonyEricsson devices
		applySonyEricssonWorkaround();

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
			translationListDisplayList = true; // if translationList is activated, then display result in a list
			indexOfLastTranslationItem = indexOfFirstTranslationItem - 1;
			stringColourItemWidth = this.getWidth();
			UIDisplayTextItem translationResultSatus = UIDisplayTextItems.EmptyText;
			if (resultOfTranslation.translationFound()) {
				Enumeration translationsEnum = resultOfTranslation.getAllTranslations();
				while (translationsEnum.hasMoreElements()) {
					SingleTranslation singleTranslation = (SingleTranslation) translationsEnum.nextElement();
					displaySingleTranslation(singleTranslation);
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
				String hitsText = String.valueOf(resultOfTranslation.numberOfFoundTranslations());
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
					(SingleTranslation singleTranslation)  

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
		if (DictionarySettings.getShowTranslationList()) {
			setTranslationItemCommand(translationFromItem);
			translationFromItem.setItemCommandListener(this);
		}
		if ((! DictionarySettings.getShowTranslationList()) || (! translationListDisplayList)) {
			addTranslationItem(translationToItem);
			if (DictionarySettings.getShowTranslationList()) {
				setTranslationItemCommand(translationToItem);
				translationToItem.setItemCommandListener(this);
			}
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
			int len = lastResultOfTranslation.numberOfFoundTranslations();
			for (int i = 0; i < len; i++) {
				SingleTranslation st = (SingleTranslation) lastResultOfTranslation.getTranslationAt(i);
				displaySingleTranslation(st);
			}
		}
	}

	void displayItemsInTranslationList() 
			throws DictionaryException {
		translationListDisplayList = true;
		refreshAllTranslationResults();
		// restore focus on last focused item
		display.setCurrentItem(get(translationListFocusedItemIndex));
	}

	/*
	 * Initialisation of UIDisplayTextItems from the property file
	 */
	public static String dictionaryTitleDisplayText   		= "dictionaryTitleDisplayText";
	public static String dictionaryMaintainerDisplayText   	= "dictionaryMaintainerDisplayText";
	public static String dictionarySetupByDisplayText		= "dictionarySetupByDisplayText";

	static String [] displayTextProperties = { 
										dictionaryTitleDisplayText,
										dictionaryMaintainerDisplayText,
										dictionarySetupByDisplayText
									         };
			
	protected void readDisplayTextProperties() 
				throws DictionaryException {
		for (int displayTextCounter = 0; displayTextCounter < displayTextProperties.length; ++displayTextCounter) {
			generateUIDisplayTextItem(displayTextProperties[displayTextCounter]);
		}
	}

	protected void generateUIDisplayTextItem(String propertyName)
			throws DictionaryException {
		String displayText = DictionaryDataFile.getDisplayText(propertyName);
		if (displayText != null) {
			LanguageUI.getUI().getUIDisplayTextItem(propertyName, displayText);
		}
	}

	
	/*
	 * Startup display
	 */
	boolean startupDisplayIsShown;  // set to true, when after startup the start screen is shown
	int     indexOfFirstItemStartupDisplay;
	int     indexOfLastItemStartupDisplay;
	
	// showing of the start display
	void showStartupDisplay() 
			throws DictionaryException {
		indexOfFirstItemStartupDisplay = indexOfFirstTranslationItem;
		indexOfLastItemStartupDisplay = indexOfFirstItemStartupDisplay - 1;
		
		// use bold font
		Font startupDisplayFont = Font.getFont(Font.getDefaultFont().getFace(), 
				                               Font.STYLE_BOLD, 
				                               Font.getDefaultFont().getSize());
		// display application name
		DfMStringItem applicationNameItem = new DfMStringItem(UIDisplayTextItems.DictionaryForMIDsApplicationName);
		applicationNameItem.setLayout(Item.LAYOUT_2 | Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_NEWLINE_AFTER | Item.LAYOUT_CENTER);
		appendStartupDisplayItem(applicationNameItem);
		// display application logo
		int bestApplicationIconSize = getWidth()/2;  // size should be half of the display width
		Image applicationIcon = UIDisplayTextItems.DictionaryForMIDsApplicationName.getIcon
						                      (ResourceHandler.getResourceHandlerObj().iconSizeGroupLarge, 
						                       bestApplicationIconSize, 
						                       bestApplicationIconSize);
		ImageItem applicationIconItem = new ImageItem(null, applicationIcon, Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_CENTER | Item.LAYOUT_VCENTER | Item.LAYOUT_2, null);
		if (applicationIconItem != null) {
			appendStartupDisplayItem(applicationIconItem);
		}

		// leave some space
		Spacer spaceBeforeDictionary = new Spacer(0, bestApplicationIconSize/10);
		spaceBeforeDictionary.setLayout(Item.LAYOUT_2 | Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_NEWLINE_AFTER);
		appendStartupDisplayItem(spaceBeforeDictionary);
		
		// display the maintainer and the title of the dictionary
		UIDisplayTextItem dictionaryMaintainer = LanguageUI.getUI().existingUIDisplayTextItem(dictionaryMaintainerDisplayText, true);
		UIDisplayTextItem dictionaryTitle = LanguageUI.getUI().existingUIDisplayTextItem(dictionaryTitleDisplayText, true);
		UIDisplayTextItem firstUIItem = null;
		UIDisplayTextItem secondUIItem  = null;
		if (dictionaryMaintainer != null) {
			firstUIItem = dictionaryMaintainer;
			secondUIItem = dictionaryTitle;
		}
		else {
			firstUIItem = dictionaryTitle;
		}
		if ((firstUIItem != null) && (dictionaryTitle != null)) {
			DfMStringItem firstItem = new DfMStringItem(firstUIItem);
			firstItem.setLayout(Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_2);
			firstItem.setFont(startupDisplayFont);
			appendStartupDisplayItem(firstItem);
			if (secondUIItem != null) {
				Spacer spaceBeforeSecondItem = new Spacer(8, 0);
				appendStartupDisplayItem(spaceBeforeSecondItem);
				DfMStringItem secondItem = new DfMStringItem(secondUIItem);
				secondItem.setLayout(Item.LAYOUT_NEWLINE_AFTER | Item.LAYOUT_2);
				secondItem.setFont(startupDisplayFont);
				appendStartupDisplayItem(secondItem);
			}
		}

		// display the number of entries for each input language
		for (int languageCounter = 0; languageCounter < DictionaryDataFile.numberOfAvailableLanguages; ++languageCounter) {
			LanguageDefinition language = DictionaryDataFile.supportedLanguages[languageCounter]; 
			if (language.isSearchable && (language.indexNumberOfSourceEntries > 0)) {
				// prepare required UIDisplayTextItems
				String languageDisplayText = DictionaryDataFile.supportedLanguages[languageCounter].languageDisplayText;
				UIDisplayTextItem inputLanguageTextItem = 
					LanguageUI.getUI().getUIDisplayTextItem
				                   (LanguageUI.getUI().uiDisplayTextItemPrefixLanguage + languageDisplayText, 
				                    languageDisplayText);
				String entriesUIDisplayTextItemTemplate = "StartDisplayLanguageNumberOfEntries";
				UIDisplayTextItem entriesUIDisplayTextItem =  
					LanguageUI.getUI().createUIDisplayTextItemFromTemplate
				                   (entriesUIDisplayTextItemTemplate + language,
				                    entriesUIDisplayTextItemTemplate);
				entriesUIDisplayTextItem.setIconID(inputLanguageTextItem.getIconID());  // use the icon for the language
				entriesUIDisplayTextItem.setParameterValue(1, inputLanguageTextItem);
				entriesUIDisplayTextItem.setParameterValue(2, new Integer(language.indexNumberOfSourceEntries));
				// first create icon
				int iconSizeHeight = startupDisplayFont.getBaselinePosition();
				int iconSizeWidth = (startupDisplayFont.getBaselinePosition()*4)/3; // flag icons have a bigger width than height
				Image icon = entriesUIDisplayTextItem.getIcon(ResourceHandler.getResourceHandlerObj().iconSizeGroupSmall, 
						                                      iconSizeHeight, 
						                                      iconSizeWidth);
				if (icon != null) {
					ImageItem iconItem = new ImageItem(null, icon, Item.LAYOUT_NEWLINE_BEFORE | Item.LAYOUT_CENTER | Item.LAYOUT_VCENTER | Item.LAYOUT_2, null);
					appendStartupDisplayItem(iconItem);
				}
				// second create display text
				DfMStringItem numberOfEntriesForLanguageItem = new DfMStringItem(entriesUIDisplayTextItem);
				numberOfEntriesForLanguageItem.setLayout(Item.LAYOUT_2);
				numberOfEntriesForLanguageItem.setFont(startupDisplayFont);
				appendStartupDisplayItem(numberOfEntriesForLanguageItem);
			}
		}
		startupDisplayIsShown = true;
	}
	
	// removal of the start display if it is shown
	void removeStartupDisplay() {
		if (startupDisplayIsShown) {
			for (int currentIndexItemStartupDisplay = indexOfFirstItemStartupDisplay;
				 currentIndexItemStartupDisplay <= indexOfLastItemStartupDisplay;
				 ++currentIndexItemStartupDisplay) {
				delete(indexOfFirstItemStartupDisplay);
			}
			startupDisplayIsShown = false;
		}
	}
	
	void appendStartupDisplayItem(Item item) {
		++indexOfLastItemStartupDisplay;
		insert(indexOfLastItemStartupDisplay, item);
	}

	// SonyEricsson-workaround for the problem that after a translation the display is not correctly refreshed.
	// The workaround is to display an empty form after the translation result has been displayed
	public static boolean sonyEricssonWorkaroundRequired = false;
	void checkForSonyEricssonWorkaround() {
		String platform = System.getProperty("microedition.platform");
		if (platform != null) {
			if (platform.startsWith("SonyEricsson")) {
				if (!platform.startsWith("SonyEricssonP")) { // P-Series does not have the OutOfMemoryError
					sonyEricssonWorkaroundRequired = true;
				}
			}
		}
//		sonyEricssonWorkaroundRequired = false; // uncomment for SE bug testing
	}
	void applySonyEricssonWorkaround() {
		if (sonyEricssonWorkaroundRequired) {
			showSonyEricssonWorkaroundForm();
			Display.getDisplay(dictionaryForMIDsMidlet).callSerially(new SEWorkaroundTask());
		}
	}
	class SEWorkaroundTask extends Thread {
		public void run() {
			removeSonyEricsssonWorkaroundForm();
		}
	}
	void showSonyEricssonWorkaroundForm() {
		Form seWorkaroundForm;
		seWorkaroundForm = new Form("SE workaround");
		Display.getDisplay(dictionaryForMIDsMidlet).setCurrent(seWorkaroundForm);
	}
	void removeSonyEricsssonWorkaroundForm() {
		Display.getDisplay(dictionaryForMIDsMidlet).setCurrent(this);
	}
	
}