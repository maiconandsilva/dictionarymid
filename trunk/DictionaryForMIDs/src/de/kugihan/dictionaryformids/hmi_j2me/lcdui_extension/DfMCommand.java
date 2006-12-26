package de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension;

import javax.microedition.lcdui.Command;
import de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext.UIDisplayTextItem;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class DfMCommand extends Command  {

	UIDisplayTextItem languageUILabel = null;
	
	DfMCommand(UIDisplayTextItem languageUILabelParam, int commandType, int priority) 
	 						throws DictionaryException {
		super(languageUILabelParam.getItemDisplayText(), commandType, priority);
		languageUILabel = languageUILabelParam;
	}
	
	/*public void redisplayLabels() {
		// unfortunately it is not possible to change the label of a command in MIDP 2.0 !!
	} */

}
