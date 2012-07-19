/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package cli.version.de.kugihan.dictionaryformids.dictgen.dictionaryupdate;

import java.util.Vector;

import de.kugihan.dictionaryformids.dataaccess.content.ContentLib;
import de.kugihan.dictionaryformids.dictgen.IndexKeyWordEntry;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.translation.SearchIndicator;


/*
 * Library methods for DictionaryUpdate-classes
 */
public class DictionaryUpdateLib {

	public static String removeBrackets(String expression) {
		boolean replacementDone;
		StringBuffer expressionCleanedUp = new StringBuffer(expression);
		do {
			replacementDone = false;
			// remove everything in square brackets
			int startBracket = expressionCleanedUp.toString().indexOf('[');
			int endBracket = expressionCleanedUp.toString().indexOf(']');
			if ((startBracket >= 0) && (endBracket > startBracket)) {
				expressionCleanedUp.delete(startBracket, endBracket+1);
				replacementDone = true;				
			}
			// remove everything in normal brackets
			startBracket = expressionCleanedUp.toString().indexOf('(');
			endBracket = expressionCleanedUp.toString().indexOf(')');
			if ((startBracket >= 0) && (endBracket > startBracket)) {
				expressionCleanedUp.delete(startBracket, endBracket+1);
				replacementDone = true;
			}
			// remove everything in curly brackets
			startBracket = expressionCleanedUp.toString().indexOf('{');
			endBracket = expressionCleanedUp.toString().indexOf('}');
			if ((startBracket >= 0) && (endBracket > startBracket)) {
				expressionCleanedUp.delete(startBracket, endBracket+1);
				replacementDone = true;
			}
			// remove everything in < > -brackets
			startBracket = expressionCleanedUp.toString().indexOf('<');
			endBracket = expressionCleanedUp.toString().indexOf('>');
			if ((startBracket >= 0) && (endBracket > startBracket)) {
				expressionCleanedUp.delete(startBracket, endBracket+1);
				replacementDone = true;
			}
		}
		while (replacementDone);
		return expressionCleanedUp.toString();
	}


	// set 
	public static String setContentPronounciation(String contentExpressionString,
            									  int    contentNumber) 
				throws DictionaryException {
		StringBuffer contentExpression = new StringBuffer(contentExpressionString);
		ContentLib.addContentPrependChar(contentExpression);
		setContentPronunciationForSquareBrackets(contentExpression, contentNumber);
		return contentExpression.toString();
	}
	
	// searches for text in square brackets and sets the content format contentPronunciation
	// for this part
	protected static void setContentPronunciationForSquareBrackets(StringBuffer expressionContent,
			                                                       int    contentNumber) 
					throws DictionaryException {
		// check for square brackets
		int startBracket = expressionContent.toString().indexOf('[');
		int endBracket = expressionContent.toString().indexOf(']');
		if ((startBracket >= 0) && (endBracket > startBracket)) {
			ContentLib.addContentFormat(expressionContent,
					                    contentNumber, 
					                    startBracket-1,  // -1 because there is a \-character before the [-character  
					                    endBracket);
		}
	}

	// splits the keywords of an expression in the elements of an Vectors
	// Example: the expression "go back home" creates a Vector with three
	// elements ("go", "back", "home")
	public static Vector splitKeyWords(String keyWords) {
		Vector keyWordVector = new Vector();
		int startOfWord = 0;
		boolean inSeparatorText = true;
		for (int charPos = 0; charPos < keyWords.length(); ++charPos) {
			if (! Util.isSeparatorCharacter(keyWords.charAt(charPos))) {
				inSeparatorText = false;
			}
			if (Util.isSeparatorCharacter(keyWords.charAt(charPos)) ||
				(charPos == keyWords.length()-1)) {
				if (! inSeparatorText) {
					inSeparatorText = true;
					String keyword;
					if ((charPos == keyWords.length()-1) && (! Util.isSeparatorCharacter(keyWords.charAt(charPos)))) {
						keyword = keyWords.substring(startOfWord, charPos+1);
					}
					else {
						keyword = keyWords.substring(startOfWord, charPos);
					}
					startOfWord = charPos+1;
					keyWordVector.addElement(keyword);
				}
				else {
					startOfWord = charPos+1;
				}
			} 
		}
		return keyWordVector;
	}

	// addKeyWordExpressions compared to splitKeyWords:
	// - Vector needs to be passed and KeyWordExpressions are added (splitKeyWords) creates this Vector
	// - the expressions from addKeyWordExpressions include the rest of the expressions
	//   Example: the expression "go back home" adds the following three
	//   elements ("go back home", "back home", "home")
	public static void addKeyWordExpressions(String expression, Vector keyWordVector) {
		int startOfWord = 0;
		boolean firstWordEntry = true;
		boolean inSeparatorText = true;
		for (int charPos = 0; charPos < expression.length(); ++charPos) {
			if (! Util.isSeparatorCharacter(expression.charAt(charPos))) {
				inSeparatorText = false;
			}
			if (Util.isSeparatorCharacter(expression.charAt(charPos)) ||
				(charPos == expression.length()-1)) {
				if (! inSeparatorText) {
					inSeparatorText = true;
					String keyword = expression.substring(startOfWord);
					IndexKeyWordEntry indexKeyWordEntryObj = new IndexKeyWordEntry(Util.filterSuperflousWhitespaces(new StringBuffer(keyword)).toString(),
							                                                       new SearchIndicator(firstWordEntry)); 
					keyWordVector.addElement(indexKeyWordEntryObj);
					startOfWord = charPos+1;
					firstWordEntry = false;
				}
				else {
					startOfWord = charPos+1;
				}
			} 
		}
	}	

}
