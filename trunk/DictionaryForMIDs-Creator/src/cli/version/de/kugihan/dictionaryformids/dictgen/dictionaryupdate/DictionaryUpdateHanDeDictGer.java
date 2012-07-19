/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (
)

GPL applies - see file COPYING for copyright statement.
*/
package cli.version.de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import de.kugihan.dictionaryformids.dictgen.DictionaryGeneration;
import de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEngDef;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;

public class DictionaryUpdateHanDeDictGer extends DictionaryUpdatePartialIndex {
	
	public String updateDictionaryExpression(String dictionaryExpression) 
				throws DictionaryException {
		boolean replacementDone;
		
		//updates the DictionaryExpression by replacing the slashes by ,
		String updatedExpression;
		int startBracket = dictionaryExpression.indexOf('/');
		int endBracket = dictionaryExpression.toString().lastIndexOf('/');
		if ((startBracket != -1) && (endBracket > startBracket)) {
			String withoutBeginAndEnd = dictionaryExpression.substring(startBracket + 1, endBracket); 
			String withComas = replaceSlashes(withoutBeginAndEnd);
			updatedExpression = withComas;
			updatedExpression = DictionaryUpdateLib.setContentPronounciation(updatedExpression, 1);
		}
		else {
			updatedExpression = dictionaryExpression;
		}
		
		String returnString = super.updateDictionaryExpression(updatedExpression.toString());
		return returnString;
	}
	
	//replaces the slashes by commas
	protected String replaceSlashes(String expression) {
		StringBuffer output = new StringBuffer();
		for (int pos = 0; pos < expression.length(); ++pos) {
			char character = expression.charAt(pos);
			if (character=='/') {
				output.append(", ");
			}
			else {
				output.append(character);
			}
		}
		return output.toString();
	}
}
