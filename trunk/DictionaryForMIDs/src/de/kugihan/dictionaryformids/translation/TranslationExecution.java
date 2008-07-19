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
	
	public synchronized static void executeTranslation(TranslationParameters translationParametersObj)
	throws DictionaryException
	{
		cancelLastTranslation();
		TranslationThread newTranslationThread = new TranslationThread(translationParametersObj);
	    if (translationParametersObj.isExecuteInBackground()) {
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
	protected TranslationParameters translationParametersObj;
	
	public TranslationThread(TranslationParameters translationParametersObjParam) {
		translationParametersObj = translationParametersObjParam;
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
		
		if (translationParametersObj.isExecuteInBackground()) {
		    // set a low priority for the thread
			ownExecutionThread.setPriority(Thread.NORM_PRIORITY - 2);
		}
		
		// get new translation
		if (! translationParametersObj.toBeTranslatedWordTextIsEmpty()) {
			translate = new Translation(translationParametersObj);
			TranslationResult resultOfTranslation = null;
			
			if (!translationIsCancelled)
				resultOfTranslation = translate.getTranslationResult();
			
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
		if (translate != null) {
			translate.cancelTranslation();
		
			// Send interrupt to translation thread
			if (DictionarySettings.isCldc11()) {
				ownExecutionThread.interrupt();
			}
		}
	}
}