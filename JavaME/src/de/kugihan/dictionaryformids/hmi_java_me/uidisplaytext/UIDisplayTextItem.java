/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Vu Van Quynh (quynh2003hp@yahoo.com) and Gert Nuber
(dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext;

import javax.microedition.lcdui.Image;

import de.kugihan.dictionaryformids.general.CouldNotOpenFileException;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.ResourceHandler;

// A single display text on the user interface
public class UIDisplayTextItem {
	
	final char parameterIndicator = '%';
	
	public String    id;       // ID for this UIDisplayTextItem
	protected String iconID;   // ID for the icon (by default the same as id) 
	public int       keyValue; // index to UIDisplayTextsContent
	final int keyValueNonTranslatable = -1;    // when keyValue is set to keyValueNonTranslateable then this indicates that
	                                           // for this UIDisplayTextItem there is no translation in UIDisplayTextContents.availableDisplayTexts
											   // In this case, the value of fallbackTranslation is used
	String    fallbackTranslation = null;
	UIDisplayTextItemParameter [] parameters;  // values for each of the parameters

	Image 	  icon = null; 					// icon for the UIDisplayTextItem  
	boolean   iconResourceLoadDone = false; // set to true when the icon was loaded from the resource (whether successful or not)  
	int       iconImageHeight;			    // requested height of the loaded icon
	int       iconImageWidth;                // requested width of the loaded icon
	
	UIDisplayTextItem(String idParam, int keyValueParam, int numberOfParameters) {
		// the ID is saved
		iconID = id = idParam; 
		// the index to UIDisplayTextsContent is saved
		keyValue = keyValueParam;
		// the parameterValue-array is created
		initParameters(numberOfParameters);
	}

	UIDisplayTextItem(String idParam, String fallbackTranslationParam, int numberOfParameters) {
		// the ID is saved
		iconID = id = idParam; 
		// the keyValue is set to keyValueNonTranslateable to indicate that there is no translation for this UIDisplayTextItem
		keyValue = keyValueNonTranslatable;
		// the fallback translation is saved
		fallbackTranslation = fallbackTranslationParam;
		// the parameterValue-array is created
		initParameters(numberOfParameters);
	}

	protected void initParameters(int numberOfParameters) {
		parameters = new UIDisplayTextItemParameter[numberOfParameters];
		for (int parameterCount = 0; parameterCount < parameters.length; ++ parameterCount)
			parameters[parameterCount] = new UIDisplayTextItemParameter();
	}

	public int getNumberOfParameters() {
		return parameters.length;
	}
	
	// parameter numbers are starting with 1
	public void setParameterValue(int parameterNumber, Object parameterValueParam) 
				throws DictionaryException {
		doParameterRangeCheck(parameterNumber);
		parameters[parameterNumber - 1].setValue(parameterValueParam);
	}
	
	public void setAllParameterValues(Object [] parameterValueParam) 
				throws DictionaryException {
		if (parameterValueParam.length != parameters.length)
			throw new DictionaryException("Incorrect number of parameter values");
		else {
			for (int parameterCount = 0; parameterCount < parameters.length; ++ parameterCount) {
				parameters[parameterCount].setValue(parameterValueParam[parameterCount]);
			}
		}
	}
	
	protected String replaceParameters(String itemDisplayText) 
					throws DictionaryException  {
		StringBuffer parametersReplaced = new StringBuffer();
		int fromPos = 0;
		int toPos = 0;
		boolean parameterFound;
		do {
			toPos = itemDisplayText.indexOf(parameterIndicator, fromPos);
			if (toPos == -1) {
				parameterFound = false;
				toPos = itemDisplayText.length();
			}
			else {
				parameterFound = true;
			}
			if (toPos >= 0)
				parametersReplaced.append(itemDisplayText.substring(fromPos, toPos));
			if (parameterFound) {
				if (toPos + 1 < itemDisplayText.length()) {  
					char nextCharacter = itemDisplayText.charAt(toPos + 1);
					// if the next character is also the parameterIndicator, then this indicates 
					// not a real parameter, but the parameterIndicator itself
					if (nextCharacter == parameterIndicator) {
						parametersReplaced.append(parameterIndicator);
					}
					else {
						if (! Character.isDigit(nextCharacter))
							throw new DictionaryException(parameterIndicator + " is not followed by number");
						int parameterNumber = Integer.valueOf(String.valueOf(nextCharacter)).intValue();
						doParameterRangeCheck(parameterNumber);
						parametersReplaced.append(parameters[parameterNumber-1].getStringValue());
					}
					fromPos = toPos + 2; // skip parameterIndicator and the parameterNumber
				}
				else
				{
					// single parameterIndicator at the end of the text: just add the parameterIndicator
					parameterFound = false;
					parametersReplaced.append(parameterIndicator);
				}
			}
		}
		while (parameterFound && (fromPos < itemDisplayText.length()));
		return parametersReplaced.toString();
	}
	
	protected void doParameterRangeCheck(int parameterNumber) 
				throws DictionaryException {
		if ((parameterNumber < 1) || (parameterNumber > parameters.length))
			throw new DictionaryException("Parameter number must be between 1 and " + 
					                      String.valueOf(parameters.length));
	}
	
	public String getItemDisplayText() throws DictionaryException {
		String itemDisplayText;
		if (keyValue != keyValueNonTranslatable) {
			// get translation from available translation texts
			itemDisplayText =
				UIDisplayTextContents.availableDisplayTexts[LanguageUI.getUI().getUILanguage()].languageUIItems[keyValue];
		}
		else {
			// no translation is available for this UIDisplayTextItem: use fallback translation 
			itemDisplayText = fallbackTranslation;
		}
		itemDisplayText = replaceParameters(itemDisplayText);
		return itemDisplayText;
	}
	
	/*
	 * The method getIcon(int bestImageHeight, int bestImageWidth) returns the icon for the UIDisplayTextItem
	 * or null if no icon exists.
	 */
	protected final String iconArea = "UIDisplayTextItems";
	
	public Image getIcon(String iconSizeGroup, int bestImageHeight, int bestImageWidth) 
				throws DictionaryException {
		// the icon is loaded from the resouce if
		// - the icon does not exist (icon == null) and no loading was tried before (! iconResourceLoadDone)  or
		// - the icon exists (icon != null) but the icon is now requested with a different height/width 
		if (((icon == null) && (! iconResourceLoadDone)) ||
			((icon != null) && ((iconImageHeight != bestImageHeight) || (iconImageWidth != bestImageWidth)))) {			
			// retrieve icon from resource
			try {
				iconResourceLoadDone = true;
				icon = ResourceHandler.getResourceHandlerObj().getIcon
									  (ResourceHandler.getResourceHandlerObj().iconAreaUIDisplayTextItems, 
									   iconSizeGroup,
									   iconID, 
	                                   bestImageHeight, 
	                                   bestImageWidth);
				// store the requested height and width
				iconImageHeight = bestImageHeight;
				iconImageWidth  = bestImageWidth;
			}
			catch (CouldNotOpenFileException e) {
				// icon was not found, null is returned.
			}
		}
		return icon;
	}	
	
	// normally the icon is identified by the id of the UIDisplayText; with the setIconID method 
	// a special id for the icon can be set.
	public void setIconID(String iconIDParam) {
		iconID = iconIDParam;
	}

	public String getIconID() {
		return iconID;
	}
	
}

class UIDisplayTextItemParameter {
	private Object  value;  // value can be of type String, Integer and UIDisplayTextItem
	
	UIDisplayTextItemParameter() {
		value = null;  // initialise with null as 'not yet defined'
	}

	public String getStringValue() throws DictionaryException {
		String returnValue;
		if (value == null)
			throw new DictionaryException("Parameter value not defined");
		if (value instanceof String) {
			// this parameter is of type Integer
			returnValue = (String) value;
		}
		else if (value instanceof Integer) {
			// this parameter is of type Integer
			returnValue = ((Integer) value).toString(); 
		}
		else if (value instanceof UIDisplayTextItem) {
			// this parameter refers to another UIDisplayTextItem
			returnValue = ((UIDisplayTextItem) value).getItemDisplayText();
		}
		else {
			throw new DictionaryException("Type " + value.getClass().toString() + " not supported as parameter");
		}
		return returnValue;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
