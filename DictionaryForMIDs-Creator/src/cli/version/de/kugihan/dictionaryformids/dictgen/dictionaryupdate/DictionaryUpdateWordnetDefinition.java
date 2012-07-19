package cli.version.de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import de.kugihan.dictionaryformids.dataaccess.content.ContentLib;
import de.kugihan.dictionaryformids.general.DictionaryException;

public class DictionaryUpdateWordnetDefinition extends DictionaryUpdate {

	public String updateDictionaryExpression(String dictionaryExpression) 
			throws DictionaryException {
		StringBuffer updatedExpression = new StringBuffer(dictionaryExpression);
		int startPos = 0;
		boolean endReached = false;
		int endPos;
		do {
			if (updatedExpression.charAt(startPos) == ' ')
				updatedExpression.delete(startPos, startPos+1);  // remove blank at beginning 
			endPos = updatedExpression.indexOf(";");
			if (endPos != -1) {
				// replace semicolon with newline
				updatedExpression.replace(endPos, endPos + 1, "\\n");
				--endPos;
			}
			else {
				endPos = updatedExpression.length()-1;
				endReached = true;
			}
			int startApostrophs = updatedExpression.indexOf("\"", startPos);
			if ((startApostrophs == -1) || (startApostrophs > endPos)) {
				// content 01 (definition)
				ContentLib.addContentFormat(updatedExpression,
											1, 
										    startPos,  
										    endPos);
			} 
			else {
				// content 02 (example)
				ContentLib.addContentFormat(updatedExpression,
											2, 
										    startPos,  
										    endPos);
			}
			startPos = endPos+3+3+1;
			if (startPos >= updatedExpression.length())
				endReached = true;
		}
		while (! endReached);
		return updatedExpression.toString();
	}
	
}
