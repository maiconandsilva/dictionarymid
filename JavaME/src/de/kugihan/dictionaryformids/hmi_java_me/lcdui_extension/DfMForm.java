/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.Command;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.UIDisplayTextItem;

public class DfMForm extends Form implements
			LanguageUISensitiveItem  {
	
	UIDisplayTextItem title = null;

	// Workaround for http://code.google.com/p/microemu/issues/detail?id=44.
	// The method can be deleted when this bug is released in MicroEmu, probably end of 2010:

	public void removeCommand(Command cmd)
	{
		if (cmd != null) super.removeCommand(cmd);
	}

	public DfMForm()  {
		super(null);
	}
	
	public void setTitle(String titleParam) {
		super.setTitle(titleParam);
		title = null; 
	}
	
	public void setTitleUIDisplayTextItem(UIDisplayTextItem titleParam) throws DictionaryException {
		super.setTitle(titleParam.getItemDisplayText());
		title = titleParam; 
	}
	
	public void redisplayLabels() throws DictionaryException {
		// redisplay the title
		if (title != null) {
			super.setTitle(title.getItemDisplayText());
		}
		
		// Loops over all items of the form and redisplay their labels.
		for (int currentIndexItem = 0;
		 	 currentIndexItem <= size()-1;
		 	 ++currentIndexItem) {
			Item item = get(currentIndexItem);
			if (item instanceof LanguageUISensitiveItem) {
				LanguageUISensitiveItem languageUISensitiveItem = (LanguageUISensitiveItem) item;
				languageUISensitiveItem.redisplayLabels();
			}
		}
		// Redisplay the commands
		setupCommands();
	}

	// Note that commands cannot change their label (seems to be a hole in MIDP 2.0), 
	// so these need to be manually recreated in the form's inherited redisplayCommands method.
	public void setupCommands() throws DictionaryException {
		// Default behaviour is to do nothing.
		// Forms with commands need to call updateCommand (see below) to get their commands
		// redisplayed
	}
	
	// Update a command for a form by removing, re-creating and appending the command for 
	// the form:
	public DfMCommand updateCommand(DfMCommand oldCommand,
								    UIDisplayTextItem languageUILabel, 
						            int commandType, 
						            int priority) throws DictionaryException {
		removeCommand(oldCommand);
		DfMCommand newCommand = new DfMCommand(languageUILabel, commandType, priority);
		addCommand(newCommand);
		return newCommand;
	}

	public DfMCommand updateItemCommand(Item item,
										DfMCommand oldCommand,
										UIDisplayTextItem languageUILabel, 
		    							int commandType, 
		    							int priority) throws DictionaryException {
		if (oldCommand != null)
			item.removeCommand(oldCommand);
		DfMCommand newCommand = new DfMCommand(languageUILabel, commandType, priority);
		item.setDefaultCommand(newCommand);
		return newCommand;
	}

	public DfMCommand updateItemCommand(Item item,
										DfMCommand oldCommand,
										DfMCommand newCommand) throws DictionaryException {
		if (oldCommand != null)
			item.removeCommand(oldCommand);
		item.setDefaultCommand(newCommand);
		return newCommand;
	}

}
