/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.translation;

import de.kugihan.dictionaryformids.general.*;
import de.kugihan.dictionaryformids.hmi_java_me.DictionarySettings;

public class TranslationExecution {

	protected static TranslationThread lastTranslationThread = null;
	
	protected static TranslationExecutionCallback translationResultHMIObj;
	
	public static void setTranslationExecutionCallback(TranslationExecutionCallback translationResultHMIObjParam) {
		translationResultHMIObj = translationResultHMIObjParam;
	}
	
	public synchronized static void executeTranslation(String toBeTranslatedWordTextParam, boolean executeInBackground)
	throws DictionaryException
	{
		cancelLastTranslation();
		TranslationThread newTranslationThread = new TranslationThread(toBeTranslatedWordTextParam, executeInBackground);
	    if (executeInBackground) {
	    	// start new translation thread
	    	Thread executionThread = new Thread(newTranslationThread);
	    	newTranslationThread.setOwnExecutionThread(executionThread);
	    	lastTranslationThread = newTranslationThread;
	    	executionThread.start();
	    }
	    else {
	    	newTranslationThread.runInForeground();
	    }
	}
	
	public synchronized static void cancelLastTranslation() {
		// cancel last translation thread that may still be running
		if (lastTranslationThread != null) {
			lastTranslationThread.cancelTranslation();
			lastTranslationThread = null;
		}
	}
}

class TranslationThread implements Runnable {

	protected volatile boolean translationIsCancelled = false;  // indicates whether the running translation should be aborted
	protected Translation translate = null;
	protected Thread ownExecutionThread;
	protected String toBeTranslatedWordText;
	protected boolean executeInBackground;
	
	public TranslationThread(String toBeTranslatedWordTextParam,
			                 boolean executeInBackgroundParam) {
		toBeTranslatedWordText = toBeTranslatedWordTextParam;
		executeInBackground = executeInBackgroundParam;
	}
	
	public void setOwnExecutionThread(Thread threadParam) {
		ownExecutionThread = threadParam;
	}
	
	// called from separate thread:
	public void run()
	{
		try
		{
			doTranslation();			
		}
		catch (Throwable t)
		{
			Util.getUtil().log(t);
		}
	}
	
	// called directly (not from separate thread):
	public void runInForeground()
	throws DictionaryException
	{
		doTranslation();
	}
	
	protected void doTranslation()
	throws DictionaryException
	{
		// delete previous translation result
		synchronized(this) {
			if (!translationIsCancelled)
				TranslationExecution.translationResultHMIObj.deletePreviousTranslationResult();
		}
		
		if (executeInBackground) {
		    // set a low priority for the thread
			ownExecutionThread.setPriority(Thread.NORM_PRIORITY - 2);
		}
		
		toBeTranslatedWordText = Util.removeSuperflousSearchCharacters(toBeTranslatedWordText);

		// get new translation
		if (toBeTranslatedWordText.length() > 0) {
			// check if there is the noSearchSubExpression at the beginning or the end of the
			// to be translated word
			boolean searchSubExpressionStart = true;
			boolean searchSubExpressionEnd = true;
			if (toBeTranslatedWordText.charAt(0) == Translation.noSearchSubExpressionCharacter) {
				searchSubExpressionStart = false;
				if (toBeTranslatedWordText.length() > 1)
					toBeTranslatedWordText = toBeTranslatedWordText.substring(1);
				else
					toBeTranslatedWordText = new String("");
			}
			else {
				searchSubExpressionStart = true;
			}
			if ((toBeTranslatedWordText.length() > 0) && 
			    (toBeTranslatedWordText.charAt(toBeTranslatedWordText.length() - 1) == Translation.noSearchSubExpressionCharacter)) {
				searchSubExpressionEnd = false;
				toBeTranslatedWordText = toBeTranslatedWordText.substring(0, toBeTranslatedWordText.length() - 1);
			}
			else {
				searchSubExpressionEnd = true;
			}
			translate = new Translation(searchSubExpressionStart, searchSubExpressionEnd);
			TranslationResult resultOfTranslation = null;
			
			if (!translationIsCancelled)
				resultOfTranslation = translate.getTranslationResult(toBeTranslatedWordText);
			
			// display new result
			synchronized(this) {
				if (!translationIsCancelled)
					TranslationExecution.translationResultHMIObj.newTranslationResult(resultOfTranslation);
			}
			translate = null;
		}
	}

	public synchronized void cancelTranslation() {
		// cancels a running translation
		
		// set flag for cancelled translation
		translationIsCancelled = true;
		
		// forward cancelling to translate-object
		if (translate != null)
			translate.cancelTranslation();
		
		// Send interrupt to translation thread
		if (DictionarySettings.isCldc11()) {
			ownExecutionThread.interrupt();
		}
	}
}