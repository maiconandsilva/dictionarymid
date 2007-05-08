/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)and Vu Van Quynh 
(quynh2003hp@yahoo.com)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_java_me;

import java.util.Enumeration;

import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.TextField;

import de.kugihan.dictionaryformids.dataaccess.CsvFile;
import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.SettingsStore;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.DfMChoiceGroup;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.DfMCommand;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.DfMForm;
import de.kugihan.dictionaryformids.hmi_java_me.mainform.MainForm;
import de.kugihan.dictionaryformids.hmi_java_me.mainform.bitmapfont.BitmapFontCanvas;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.LanguageUI;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.UIDisplayTextItem;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.UIDisplayTextItems;

public class DictionarySettingForm 
   extends    DfMForm 
   implements CommandListener,
			  ItemCommandListener,
			  ItemStateListener {
	
	DfMCommand applyCommand;
	DfMCommand cancelCommand;
	Command    selectDictionaryPathCommand = new Command("List root devices", Command.ITEM, 4); // temporary solution for preliminaty file system access

	Form callingForm;
	Display display;

	DfMChoiceGroup inputLanguageChoiceGroup;
	DfMChoiceGroup outputLanguageChoiceGroup;
	DfMChoiceGroup searchChoiceGroup;
	DfMChoiceGroup displayChoiceGroup;
	DfMChoiceGroup fontSizeChoiceGroup;
	DfMChoiceGroup uiLanguageChoiceGroup;
	DfMChoiceGroup performanceChoiceGroup;
//	DfMChoiceGroup contentChoiceGroup;
	static TextField   dictionaryPathTextField = null;
	
	protected final int indexFontSizeCGDefault    = 0;
	protected final int indexFontSizeCGSmall      = 1;
	protected final int indexFontSizeCGMedium     = 2;
	protected final int indexFontSizeCGLarge      = 3;	
	protected final int indexFontSizeCGBitmapFont = 0;	
	protected final int indexSearchCGIncrementalSearchEnabled = 0;
	protected final int indexSearchCGFindExactMatches = 1;
	protected final int indexSearchCGEndWildcardAnySeriesOfCharacter = 2;
	protected final int indexSearchCGBeginNoSearchSubExpressionCharacter = 3;
	protected final int indexDisplayCGShowTranslationList = 0;
	protected final int indexDisplayCGColouredItems = 1;
	protected final int indexDisplayCGShowStatistic = 2;
	protected final int indexDisplayCGUseBitmapFont = 3;
	protected final int indexPerfCGBypassCharsetDecoding = 0;

	private boolean bitmapFontExists;
	private boolean lastSettingUseBitmapFont; // last setting of use bitmap fonts

	public DictionarySettingForm(Display displayParam,
			                     Form callingFormParam) 
			          throws DictionaryException {
		setTitleUIDisplayTextItem(UIDisplayTextItems.CommandSettings);

		callingForm = callingFormParam;
		display = displayParam;
		setupCommands();
		setCommandListener(this);

		checkBitmapFontAvailable();
		
		/* 
		 * input language
		 */
		UIDisplayTextItem[] inputLanguageTextItem = new UIDisplayTextItem[DictionaryDataFile.numberOfInputLanguages];
		int inputLanguage = 0;
		for (int language = 0; language < DictionaryDataFile.numberOfAvailableLanguages; ++language) {
			if (DictionaryDataFile.supportedLanguages[language].isSearchable) {
				String languageDisplayText = DictionaryDataFile.supportedLanguages[language].languageDisplayText;
				inputLanguageTextItem[inputLanguage] = LanguageUI.getUI().getUIDisplayTextItem
				                       (LanguageUI.getUI().uiDisplayTextItemPrefixLanguage + languageDisplayText, 
				                        languageDisplayText);
				++inputLanguage;
			}
		}
		if ((inputLanguage == 0) && (DictionaryDataFile.numberOfAvailableLanguages > 0)) {
			throw new DictionaryException("No searchable languages defined");
		}
		inputLanguageChoiceGroup = new DfMChoiceGroup(UIDisplayTextItems.SettingsFromLanguage,
										              Choice.EXCLUSIVE,
										              inputLanguageTextItem);
		if (DictionarySettings.isDictionaryAvailable()) {
			DictionarySettings.setInputLanguage(SettingsStore.getSettingsStore().getInputLanguage());
		}
		append(inputLanguageChoiceGroup);

		/* 
		 * output language
		 */
		UIDisplayTextItem[] outputLanguageTextItem = new UIDisplayTextItem [DictionaryDataFile.numberOfAvailableLanguages];
		for (int languageIndex = 0; 
		     languageIndex < DictionaryDataFile.numberOfAvailableLanguages;
		     ++languageIndex) {
			String languageDisplayText = DictionaryDataFile.supportedLanguages[languageIndex].languageDisplayText;
			outputLanguageTextItem[languageIndex] = LanguageUI.getUI().getUIDisplayTextItem
            								(LanguageUI.getUI().uiDisplayTextItemPrefixLanguage + languageDisplayText, 
                                             languageDisplayText);
		}
		outputLanguageChoiceGroup = new DfMChoiceGroup(UIDisplayTextItems.SettingsToLanguage,
									       			   Choice.EXCLUSIVE, // could also be Choice.MULTIPLE for multiple language output
												       outputLanguageTextItem);
		if (DictionarySettings.isDictionaryAvailable()) {
			DictionarySettings.setOutputLanguage(SettingsStore.getSettingsStore().getOutputLanguage());
		}
		append(outputLanguageChoiceGroup);

		/*
		 * search options 
		 */
		UIDisplayTextItem [] searchStrings = new UIDisplayTextItem[]  
		                                            { UIDisplayTextItems.SettingsIncrementalSearchEnabled,
				  									  UIDisplayTextItems.SettingsFindMatchWordOnly, 
													  UIDisplayTextItems.SettingsEndWildcardAnySeriesOfCharacter,
													  UIDisplayTextItems.SettingsBeginNoSearchSubExpressionCharacter};
		UIDisplayTextItems.SettingsEndWildcardAnySeriesOfCharacter.setParameterValue
											(1, String.valueOf(Util.wildcardAnySeriesOfCharacter));
		UIDisplayTextItems.SettingsBeginNoSearchSubExpressionCharacter.setParameterValue
											(1, String.valueOf(Util.noSearchSubExpressionCharacter));
		
		searchChoiceGroup = new DfMChoiceGroup(UIDisplayTextItems.SettingsSearchOptions,
				                               Choice.MULTIPLE,
				                               searchStrings);
		DictionarySettings.setIncrementalSearchEnabled
		                    (SettingsStore.getSettingsStore().getIncrementalSearchEnabled());
		DictionarySettings.setFindExactMatches
							(SettingsStore.getSettingsStore().getFindExactMatches());
		DictionarySettings.setAddAtEndWildcardAnySeriesOfCharacter
		            		(SettingsStore.getSettingsStore().getAddAtEndWildcardAnySeriesOfCharacter());
		DictionarySettings.setAddAtBeginNoSearchSubExpressionCharacter
							(SettingsStore.getSettingsStore().getAddAtBeginNoSearchSubExpressionCharacter());
		append(searchChoiceGroup);
		
		
		/*
		 * display options 
		 */
		UIDisplayTextItem[] displayStrings;
		if (bitmapFontExists)
			displayStrings = new UIDisplayTextItem[] {
					UIDisplayTextItems.SettingsShowTranslationList,
					UIDisplayTextItems.SettingsColouredItems,
					UIDisplayTextItems.SettingsShowStatistics,
					UIDisplayTextItems.SettingsUseBitmapFont };
		else
			displayStrings = new UIDisplayTextItem[] {
					UIDisplayTextItems.SettingsShowTranslationList,
					UIDisplayTextItems.SettingsColouredItems,
					UIDisplayTextItems.SettingsShowStatistics };
		displayChoiceGroup = new DfMChoiceGroup(UIDisplayTextItems.SettingsDisplayOptions,
				                               Choice.MULTIPLE,
				                               displayStrings);
		DictionarySettings.setShowTranslationList(SettingsStore.getSettingsStore().getShowTranslationList());
		displayChoiceGroup.setSelectedIndex(indexDisplayCGShowTranslationList, DictionarySettings.getShowTranslationList());
		DictionarySettings.setColouredItems(SettingsStore.getSettingsStore().getColouredItems());
		DictionarySettings.setShowStatistic(SettingsStore.getSettingsStore().getShowStatistic());
		DictionarySettings.setUseBitmapFont(SettingsStore.getSettingsStore().getUseBitmapFont());
		append(displayChoiceGroup);
		
		/* 
		 * Path to dictionary (when file system access is used)
		 */
		if (DictionarySettings.isUseFileAccessJSR75()) {
			dictionaryPathTextField = new TextField("Dictionary path", null, 300, TextField.URL); // temporary: change to UIDisplayText label
			// DictionaryDataFile.dictionaryPath was already set in the constructor of class DictionaryForMIDs
			append(dictionaryPathTextField);
			// temporary: this is a temporary solution for the partial file system dictionary support.
			dictionaryPathTextField.setDefaultCommand(selectDictionaryPathCommand);
			dictionaryPathTextField.setItemCommandListener(this);
			DictionarySettings.setDictionaryPath(SettingsStore.getSettingsStore().getDictionaryPath());
		}
		
		/* 
		 * Font size
		 */
		fontSizeChoiceGroup = new DfMChoiceGroup(UIDisplayTextItems.SettingsFontSize,
				                                 Choice.POPUP,
				                                 null);
		setFontSizeCGDisplayTextItems(DictionarySettings.getUseBitmapFont());
		DictionarySettings.setFontSize(SettingsStore.getSettingsStore().getFontSize());
		DictionarySettings.setBitmapFontSize(SettingsStore.getSettingsStore().getBitmapFontSize());
		append(fontSizeChoiceGroup);

		/* 
		 * Select user Interface Language
		 */
		String[] uiLanguageStrings = LanguageUI.getUI().getLanguageTitle();
		uiLanguageChoiceGroup = new DfMChoiceGroup(UIDisplayTextItems.SettingsUILanguage,
				                                   Choice.POPUP,
				                                   uiLanguageStrings,
				                                   null);
		DictionarySettings.setUILanguage(SettingsStore.getSettingsStore().getUILanguage());
		append(uiLanguageChoiceGroup);
		
		/* 
		 * performance
		 */
		UIDisplayTextItem [] performanceStrings = new UIDisplayTextItem[] 
		                                            { UIDisplayTextItems.SettingsBypassCharsetDecoding };
		performanceChoiceGroup = new DfMChoiceGroup(UIDisplayTextItems.SettingsPerformanceOptions,
				                                    Choice.MULTIPLE,
				                                    performanceStrings);
		CsvFile.selectedBypassCharsetDecoding = SettingsStore.getSettingsStore().getBypassCharsetDecoding();
		append(performanceChoiceGroup);
		
//		/*
//		 * display contents
//		 */
//		contentChoiceGroup = new DfMChoiceGroup(UIDisplayTextItems.SettingsContent,
//                Choice.MULTIPLE,
//                null);
//		int contentIndex = 0;
//		String[] contents = new String[10];
//		for (int i = 0; i < DictionaryDataFile.supportedLanguages.length; i++) {		
//			if (DictionaryDataFile.supportedLanguages[i].contentDefinitionAvailable) {				
//				for (int j = 1; j < DictionaryDataFile.supportedLanguages[i].contents.length; j++){
//					contents[contentIndex] = DictionaryDataFile.supportedLanguages[i].contents[j].contentDisplayText;
//					contentIndex++;
//				}				
//			}
//		}
//		if (contentIndex != 0) {
//			String[] newContents = new String[contentIndex];
//			for (int i= 0; i < contentIndex; i++){
//				newContents[i] = contents[i];			
//			}
//			DictionarySettings.loadContent(contentIndex);			
//			contentChoiceGroup.setAll(UIDisplayTextItems.createContentToggle(newContents));
////			DictionarySettings.setUILanguage(SettingsStore.getSettingsStore().getUILanguage());
//			append(contentChoiceGroup);
//		}
		
		// set the values of the created items
		setItemsToSettingValues();
		
		setItemStateListener(this);
	}
	
	protected void setItemsToSettingValues() throws DictionaryException {
		// InputLanguageChoiceGroup
		setInputLanguageChoiceGroup();

		// OutputLanguageChoiceGroup
		setOutputLanguageChoiceGroup();

		// searchChoiceGroup
		searchChoiceGroup.setSelectedIndex(indexSearchCGIncrementalSearchEnabled, 
										   DictionarySettings.isIncrementalSearchEnabled());
		searchChoiceGroup.setSelectedIndex(indexSearchCGFindExactMatches, 
						   				   DictionarySettings.isFindExactMatches());
		searchChoiceGroup.setSelectedIndex(indexSearchCGEndWildcardAnySeriesOfCharacter, 
										   DictionarySettings.isAddAtEndWildcardAnySeriesOfCharacter());
		searchChoiceGroup.setSelectedIndex(indexSearchCGBeginNoSearchSubExpressionCharacter, 
				   						   DictionarySettings.isAddAtBeginNoSearchSubExpressionCharacter());

		// displayChoiceGroup
		displayChoiceGroup.setSelectedIndex(indexDisplayCGColouredItems, DictionarySettings.isColouredItems());
		displayChoiceGroup.setSelectedIndex(indexDisplayCGShowStatistic, DictionarySettings.getShowStatistic());
		if (bitmapFontExists) {
			boolean useBitmapFont = DictionarySettings.getUseBitmapFont();
			displayChoiceGroup.setSelectedIndex(indexDisplayCGUseBitmapFont, useBitmapFont);
			lastSettingUseBitmapFont = useBitmapFont;
		}

		// dictionaryPathTextField
		if (DictionarySettings.isUseFileAccessJSR75()) {
			dictionaryPathTextField.setString(DictionarySettings.getDictionaryPath());
		}
		
		// fontSizeChoiceGroup
		setFontSizeCGSelectedIndex(DictionarySettings.getUseBitmapFont());

		// uiLanguageChoiceGroup
		uiLanguageChoiceGroup.setSelectedIndex(DictionarySettings.getUILanguage(), true);
		
		// performanceChoiceGroup
		performanceChoiceGroup.setSelectedIndex(indexPerfCGBypassCharsetDecoding, CsvFile.selectedBypassCharsetDecoding);
	}

	// sets up the commands for this form:
	public void setupCommands() throws DictionaryException {
		super.setupCommands();
		applyCommand = updateCommand(applyCommand, UIDisplayTextItems.CommandApply, Command.OK, 4);
		cancelCommand = updateCommand(cancelCommand, UIDisplayTextItems.CommandCancel, Command.CANCEL, 5);
		// temporary: this is a temporary solution for the partial file system dictionary support.
		if (dictionaryPathTextField != null)
			dictionaryPathTextField.setDefaultCommand(selectDictionaryPathCommand);
	}
	
	public void commandAction (Command c, Displayable s)  {
		try {
			if (c == applyCommand) {
				saveSettings();
			}
			else if (c == cancelCommand) {
				cancelSettings();
			}
			else {
				throw new DictionaryException("Unexpected Command");
			}
			// return to calling form
			display.setCurrent(callingForm);
		}
		catch (DictionaryException e) {
			Util.getUtil().log(e);
		}
	}

	public void commandAction(Command c, Item item) {
		try
		{
			if (c == selectDictionaryPathCommand) {
				SelectDictionaryPath selectDictionaryPath = new SelectDictionaryPath();
				selectDictionaryPath.start();
			}
		}
		catch (Throwable t)
		{
			Util.getUtil().log(t);
		}
	}

	public void itemStateChanged(Item item) {
		
		try {
			// Input language changed:
			if (item == inputLanguageChoiceGroup) {
				selectNextOutputLanguage();
			}	
	
			// UI language changed:
			if (item == uiLanguageChoiceGroup) {
				setUILanguage(false);
			}

			// Display option changed :
			if (item == displayChoiceGroup) {
				// check if bitmap font settings changed
				if (bitmapFontExists) {
					boolean [] displayCGFlags = new boolean[displayChoiceGroup.size()];
					displayChoiceGroup.getSelectedFlags(displayCGFlags); 
					boolean newUseBitmapFont = displayCGFlags[indexDisplayCGUseBitmapFont];
					if (lastSettingUseBitmapFont != newUseBitmapFont) {
						// bitmap font settings did change: update font size selection:
						updateFontSizeCGDisplayTextItems(newUseBitmapFont);
					}
					lastSettingUseBitmapFont = newUseBitmapFont;
				}
			}
		}
		catch (DictionaryException e) {
			Util.getUtil().log(e);
		}
	}

	protected void saveSettings() throws DictionaryException {
		// save settings in SettingStore
		
		// input language:
		int inputLanguageSettingsSelectedIndex = 
			inputLanguageChoiceGroupToSettings(inputLanguageChoiceGroup.getSelectedIndex());
		setInputLanguage(inputLanguageSettingsSelectedIndex);
		
		// output language:
		boolean [] outputLanguageSettingsSelectedIndexes = new boolean [DictionaryDataFile.numberOfAvailableLanguages];
		outputLanguageChoiceGroup.getSelectedFlags(outputLanguageSettingsSelectedIndexes);
		setOutputLanguage(outputLanguageSettingsSelectedIndexes);
		
		// font size:
		boolean fontSizeChanged = false;
		if (! lastSettingUseBitmapFont) {
			int newFontSize = fontSizeChoiceGroup.getSelectedIndex();
			if (newFontSize != DictionarySettings.getFontSize()) {
				fontSizeChanged = true;
				DictionarySettings.setFontSize(newFontSize);
				SettingsStore.getSettingsStore().setFontSize(DictionarySettings.getFontSize());
			}
		} else {
			int newBitmapFontSizeIndex = fontSizeChoiceGroup.getSelectedIndex();
			String newBitmapFontSize = getBitmapFontSizes()[newBitmapFontSizeIndex];
			if (! newBitmapFontSize.equals(DictionarySettings.getBitmapFontSize())) {
				fontSizeChanged = true;
				DictionarySettings.setBitmapFontSize(newBitmapFontSize);
				SettingsStore.getSettingsStore().setBitmapFontSize(DictionarySettings.getBitmapFontSize());
			}
		}	
		if (fontSizeChanged) {
			// update font size on display
			MainForm.applicationMainForm.updateFonts();
		}
		
		// user interface language:
		setUILanguage(true);
		
		// search settings
		boolean [] searchCGFlags = new boolean[searchChoiceGroup.size()];
		searchChoiceGroup.getSelectedFlags(searchCGFlags); 
		// IncrementalSearchEnabled:
		boolean oldIncrementalSearchDisabled = DictionarySettings.isIncrementalSearchEnabled(); 
		DictionarySettings.setIncrementalSearchEnabled(searchCGFlags[indexSearchCGIncrementalSearchEnabled]);
		SettingsStore.getSettingsStore().setIncrementalSearchEnabled(DictionarySettings.isIncrementalSearchEnabled());
		if (oldIncrementalSearchDisabled != DictionarySettings.isIncrementalSearchEnabled())
			MainForm.applicationMainForm.updateIncrementalSearchSetting();
		// FindExaxtMatches
		DictionarySettings.setFindExactMatches
							(searchCGFlags[indexSearchCGFindExactMatches]);
		SettingsStore.getSettingsStore().setFindExactMatches
							(DictionarySettings.isFindExactMatches());
		// EndWildcardAnySeriesOfCharacter
		DictionarySettings.setAddAtEndWildcardAnySeriesOfCharacter
		 					(searchCGFlags[indexSearchCGEndWildcardAnySeriesOfCharacter]);
		SettingsStore.getSettingsStore().setAddAtEndWildcardAnySeriesOfCharacter
							(DictionarySettings.isAddAtEndWildcardAnySeriesOfCharacter());
		// BeginNoSearchSubExpressionCharacter
		DictionarySettings.setAddAtBeginNoSearchSubExpressionCharacter
							(searchCGFlags[indexSearchCGBeginNoSearchSubExpressionCharacter]);
		SettingsStore.getSettingsStore().setAddAtBeginNoSearchSubExpressionCharacter
							(DictionarySettings.isAddAtBeginNoSearchSubExpressionCharacter());

		// display settings
		boolean [] displayCGFlags = new boolean[displayChoiceGroup.size()];
		displayChoiceGroup.getSelectedFlags(displayCGFlags); 
		// showTranslationList
		boolean oldShowTranslationList = DictionarySettings.getShowTranslationList();
		DictionarySettings.setShowTranslationList(displayCGFlags[indexDisplayCGShowTranslationList]);
		SettingsStore.getSettingsStore().setShowTranslationList(DictionarySettings.getShowTranslationList());
		if (oldShowTranslationList != DictionarySettings.getShowTranslationList())
			MainForm.applicationMainForm.refreshAllTranslationResults();
		// colouredItems
		boolean oldColouredItems = DictionarySettings.isColouredItems();
		DictionarySettings.setColouredItems(displayCGFlags[indexDisplayCGColouredItems]);
		SettingsStore.getSettingsStore().setColouredItems(DictionarySettings.isColouredItems());
		if (oldColouredItems != DictionarySettings.isColouredItems())
			MainForm.applicationMainForm.updateMainFormItemsObj();
		// statistic
		DictionarySettings.setShowStatistic(displayCGFlags[indexDisplayCGShowStatistic]); 
		SettingsStore.getSettingsStore().setShowStatistic(DictionarySettings.getShowStatistic());
		MainForm.applicationMainForm.displayStatisticItems();
		// bitmap font
		if (bitmapFontExists) {
			boolean oldUseBitmapFont = DictionarySettings.getUseBitmapFont();
			DictionarySettings.setUseBitmapFont(displayCGFlags[indexDisplayCGUseBitmapFont]);
			SettingsStore.getSettingsStore().setUseBitmapFont(DictionarySettings.getUseBitmapFont());
			if (oldUseBitmapFont != DictionarySettings.getUseBitmapFont())
				MainForm.applicationMainForm.updateMainFormItemsObj();
		}

		// path to dictionary
		if (DictionarySettings.isUseFileAccessJSR75()) {
			DictionarySettings.setDictionaryPath(dictionaryPathTextField.getString());
			SettingsStore.getSettingsStore().setDictionaryPath(DictionarySettings.getDictionaryPath());
		}
		
		// Performance settings
		boolean [] perfCGFlags = new boolean[performanceChoiceGroup.size()];
		performanceChoiceGroup.getSelectedFlags(perfCGFlags); 
		// BypassCharsetDecoding:
		CsvFile.selectedBypassCharsetDecoding = perfCGFlags[indexPerfCGBypassCharsetDecoding];
		SettingsStore.getSettingsStore().setBypassCharsetDecoding(CsvFile.selectedBypassCharsetDecoding);
	
//		//display content settings
//		boolean [] contentCGFlags = new boolean[contentChoiceGroup.size()];
//		contentChoiceGroup.getSelectedFlags(contentCGFlags);
//		DictionarySettings.setContentIsShown(contentCGFlags);
	}
	
	protected void cancelSettings() throws DictionaryException {
		// Undo setting of user interface language: this needs to be handled specifically, because
		// the DictionarySettings.uiLanguage is changed when the user selects another UI Language
		// (the UI Language is changed directly after the user changes the selection in uiLanguageChoiceGroup
		// before he choses apply). 
		int currentSelectedLanguage = uiLanguageChoiceGroup.getSelectedIndex();
		int savedSelectedLanguage = SettingsStore.getSettingsStore().getUILanguage();
		if (currentSelectedLanguage != savedSelectedLanguage) {
			uiLanguageChoiceGroup.setSelectedIndex(savedSelectedLanguage, true);
			setUILanguage(false);
		}
		
		// reset Items to stored values
		setItemsToSettingValues();
	}
	
	public int getFontSize()
					throws DictionaryException {
		int fontSize;
		if (DictionarySettings.getFontSize() == indexFontSizeCGDefault)
			fontSize = Font.getDefaultFont().getSize();
		else if (DictionarySettings.getFontSize() == indexFontSizeCGLarge)
			fontSize = Font.SIZE_LARGE;
		else if (DictionarySettings.getFontSize() == indexFontSizeCGMedium)
			fontSize = Font.SIZE_MEDIUM;
		else if (DictionarySettings.getFontSize() == indexFontSizeCGSmall)
			fontSize = Font.SIZE_SMALL;
		else
			throw new DictionaryException("Invalid font size index");
		return fontSize;
	}
	
	public static String[] getBitmapFontSizes() throws DictionaryException {		
		return BitmapFontCanvas.getBitmapFontSizes();
	}
	
	public void setUILanguage(boolean updateSettingsStore) throws DictionaryException {
		int newSelectedLanguage = uiLanguageChoiceGroup.getSelectedIndex();
		if (newSelectedLanguage != DictionarySettings.getUILanguage()) {
			DictionarySettings.setUILanguage(newSelectedLanguage);
			LanguageUI.getUI().setUILanguage(newSelectedLanguage);
			// update language on display
			MainForm.applicationMainForm.refreshAllForms();
		}
		if (updateSettingsStore && 
		    (newSelectedLanguage != SettingsStore.getSettingsStore().getUILanguage()))
			SettingsStore.getSettingsStore().setUILanguage(DictionarySettings.getUILanguage());
	}
	
	// setting of a new input language
	public void setInputLanguage(int inputLanguageIndex) 
				throws DictionaryException {
		if (DictionarySettings.isDictionaryAvailable()) {	
			int oldInputLanguage = DictionarySettings.getInputLanguage();
			if (oldInputLanguage != inputLanguageIndex) {
				DictionarySettings.setInputLanguage(inputLanguageIndex);
				SettingsStore.getSettingsStore().setInputLanguage(DictionarySettings.getInputLanguage());	
				setInputLanguageChoiceGroup();
				MainForm.applicationMainForm.updateSelectedLanguage();
			}
		}
	}
	
	// setting of a new output language
	public void setOutputLanguage(boolean [] outputLanguageSelectedIndexes) 
				throws DictionaryException {
		if (DictionarySettings.isDictionaryAvailable()) {	
			boolean outputLanguageChanged = false;
			boolean[] oldOutputLanguage = DictionarySettings.getOutputLanguage();
			for (int language = 0; language < outputLanguageSelectedIndexes.length; ++language) {
				if (oldOutputLanguage[language] != outputLanguageSelectedIndexes[language]) {
					outputLanguageChanged = true;
					break;
				}
			}
			if (outputLanguageChanged) {
				DictionarySettings.setOutputLanguage(outputLanguageSelectedIndexes);
				SettingsStore.getSettingsStore().setOutputLanguage(DictionarySettings.getOutputLanguage());				
				setOutputLanguageChoiceGroup();
				MainForm.applicationMainForm.updateSelectedLanguage();
			}
		}
	}
	
	// sets the inputLanguageChoiceGroup according to the input language setting
	protected void setInputLanguageChoiceGroup() {
		if (DictionarySettings.isDictionaryAvailable()) {
			int inputLanguageChoiceGroupSelectedIndex =  
							inputLanguageSettingsToChoiceGroup(DictionarySettings.getInputLanguage());
			inputLanguageChoiceGroup.setSelectedIndex(inputLanguageChoiceGroupSelectedIndex, true);
		}
	}
	
	// sets the outputLanguageChoiceGroup according to the output language setting
	protected void setOutputLanguageChoiceGroup() {
		if (DictionarySettings.isDictionaryAvailable()) {
			outputLanguageChoiceGroup.setSelectedFlags(DictionarySettings.getOutputLanguage());
		}
	}

	public void selectNextInputLanguage() 
			throws DictionaryException {
		int inputLanguage = DictionarySettings.getInputLanguage();
		int newInputLanguage = DictionaryDataFile.numberOfAvailableLanguages;
		for (int language = DictionaryDataFile.numberOfAvailableLanguages -1; language >= 0 ; --language) {
			if (language == inputLanguage) {
				if (newInputLanguage != DictionaryDataFile.numberOfAvailableLanguages) {
					// new input language was found before
					break;
				}
			}
			if (DictionaryDataFile.supportedLanguages[language].isSearchable) {
				newInputLanguage = language;
			}
		}
		setInputLanguage(newInputLanguage);
		
		// also update the selection of the output language:
		selectNextOutputLanguage();
		boolean [] outputLanguageSettingsSelectedIndexes = new boolean [DictionaryDataFile.numberOfAvailableLanguages];
		outputLanguageChoiceGroup.getSelectedFlags(outputLanguageSettingsSelectedIndexes);
		setOutputLanguage(outputLanguageSettingsSelectedIndexes);
	}
	
	protected void selectNextOutputLanguage() {
		int newSelectedInputLanguage = inputLanguageChoiceGroup.getSelectedIndex(); 
		boolean [] currentSelectedOutputLanguage = new boolean [DictionaryDataFile.numberOfAvailableLanguages];
		outputLanguageChoiceGroup.getSelectedFlags(currentSelectedOutputLanguage);
		if (currentSelectedOutputLanguage[newSelectedInputLanguage]) {
			currentSelectedOutputLanguage[newSelectedInputLanguage] = false;
			int newSelectedOutputLanguage = newSelectedInputLanguage + 1;
			if (newSelectedOutputLanguage == inputLanguageChoiceGroup.size())
				newSelectedOutputLanguage = 0;
			currentSelectedOutputLanguage[newSelectedOutputLanguage] = true;
			outputLanguageChoiceGroup.setSelectedFlags(currentSelectedOutputLanguage);
		}
		
	}
	
	// The index for the inputLanguageChoiceGroup is not the same as for DictionarySettings.getSelectedInputLanguage,
	// because in the inputLanguageChoiceGroup only searchable languages are shown. The mapping between the two indexes
	// is done by the methods inputLanguageSettingsToChoiceGroup and inputLanguageChoiceGroupToSettings 
	public int inputLanguageSettingsToChoiceGroup(int settingsIndex) {
		int choiceGroupIndex = -1;
		for (int language = 0; language <= settingsIndex; ++language) {
			if (DictionaryDataFile.supportedLanguages[language].isSearchable) {
				++choiceGroupIndex;
			}
		}
		return choiceGroupIndex;
	}
	
	public int inputLanguageChoiceGroupToSettings(int choiceGroupIndex) {
		int settingsIndex = -1;
		int visibleLanguageIndex = -1;
		while (visibleLanguageIndex < choiceGroupIndex) {
			++settingsIndex;
			if (DictionaryDataFile.supportedLanguages[settingsIndex].isSearchable)
				++visibleLanguageIndex;
		}
		return settingsIndex;
	}

	void updateFontSizeCGDisplayTextItems(boolean useBitmapFont) throws DictionaryException {
		setFontSizeCGDisplayTextItems(useBitmapFont);
		setFontSizeCGSelectedIndex(useBitmapFont);
	}

	void setFontSizeCGDisplayTextItems(boolean useBitmapFont) throws DictionaryException {
		UIDisplayTextItem[] fontSizeStrings;
		if (useBitmapFont) {
			String[] bitmapFontSizes = getBitmapFontSizes();
			if (bitmapFontSizes.length != 0) {				
				fontSizeStrings = new UIDisplayTextItem[bitmapFontSizes.length];
				for (int i = 0; i < bitmapFontSizes.length; ++i) {						
						fontSizeStrings[i] = LanguageUI.getUI().getUIDisplayTextItem
						                       ("SettingsFont" + bitmapFontSizes[i], bitmapFontSizes[i]);											
				}				
			} else {
				fontSizeStrings = new UIDisplayTextItem []{UIDisplayTextItems.SettingsFontBitmapFont};
			}
		}
		else {
			fontSizeStrings = new UIDisplayTextItem [] 
                                     { UIDisplayTextItems.SettingsFontDeviceDefault,
                       			       UIDisplayTextItems.SettingsFontSmall,
                       			       UIDisplayTextItems.SettingsFontMedium,
                       			       UIDisplayTextItems.SettingsFontLarge};
		}
		fontSizeChoiceGroup.setAll(fontSizeStrings);
	}

	void setFontSizeCGSelectedIndex(boolean useBitmapFont) throws DictionaryException {
		if (! useBitmapFont) {
			fontSizeChoiceGroup.setSelectedIndex(DictionarySettings.getFontSize(), true);
		}
		else {
			String[] bitmapFontSizes = getBitmapFontSizes();		
			String selectedBitmapFontSize = DictionarySettings.getBitmapFontSize();
			boolean bitmapFontSizeIndexFound = false;
			int bitmapFontSizeIndex = -1;
			for (int bitmapFontSizeCounter = 0; 
			     bitmapFontSizeCounter < bitmapFontSizes.length; 
			     ++bitmapFontSizeCounter) {
				if (bitmapFontSizes[bitmapFontSizeCounter].equals(selectedBitmapFontSize)) {
					bitmapFontSizeIndexFound = true;
					bitmapFontSizeIndex = bitmapFontSizeCounter;
				}
			}
			if (! bitmapFontSizeIndexFound)
				throw new DictionaryException("Incorrect bitmap font size setting: " + selectedBitmapFontSize);
			fontSizeChoiceGroup.setSelectedIndex(bitmapFontSizeIndex, true);
		}
	}

	/**
	 * Check to see if the bitmap font setting should be shown
	 */
	void checkBitmapFontAvailable() throws DictionaryException {
		bitmapFontExists = BitmapFontCanvas.bitmapFontExists();
//		if (bitmapFontExists) {
//			SettingsStore.getSettingsStore().setBitmapFontSize(BitmapFontCanvas.getDefaultSize());
//		} 
		if (!bitmapFontExists) {
			SettingsStore.getSettingsStore().setUseBitmapFont(false);
		}
	}
}

class SelectDictionaryPath extends Thread {
	final String urlFilePrefix = "file:///"; 
	
	public void run() {
		// currently only the rootdevices are listed
		String rootdevicesString = new String();
		Enumeration rootdevices = FileSystemRegistry.listRoots();
		while (rootdevices.hasMoreElements()) {
			rootdevicesString += urlFilePrefix + ((String) rootdevices.nextElement());
			if (rootdevices.hasMoreElements())
				rootdevicesString += " ";
		}
		DictionarySettingForm.dictionaryPathTextField.setString(rootdevicesString);
	}
}
	
