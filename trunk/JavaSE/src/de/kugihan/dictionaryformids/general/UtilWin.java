/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.general;

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.DfMInputStreamAccess;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileDfMInputStreamAccess;
import de.kugihan.dictionaryformids.translation.TranslationExecution;

import java.io.File;

public class UtilWin extends Util {

        @Override
        protected void outputMessage(String message) {
                System.out.println(message);
        }

        public static final String propertyFileName = "DictionaryForMIDs.properties";

        public String buildPropertyFileName(String propertyPath) {
                return propertyPath + "/" + propertyFileName;
        }

        public DictionaryDataFile readProperties(String propertyPath, 
                                                 boolean initDictionaryGenerationValues)
                        throws DictionaryException {
        		DictionaryDataFile dictionary = null; 
        		boolean propertyFileAccessible = new File(buildPropertyFileName(propertyPath)).canRead();
                if (propertyFileAccessible) {
                        FileDfMInputStreamAccess dfmInputStreamObj = new FileDfMInputStreamAccess(propertyPath);
                        DictionaryDataFile.useStandardPath = false;
                        if (initDictionaryGenerationValues) {
                        	dictionary = TranslationExecution.loadDictionaryWithDictionaryGenerationValues(dfmInputStreamObj);
                        }
                        else {
                        	dictionary = TranslationExecution.loadDictionary(dfmInputStreamObj);
                        }
                }
                return dictionary;
        }

}
