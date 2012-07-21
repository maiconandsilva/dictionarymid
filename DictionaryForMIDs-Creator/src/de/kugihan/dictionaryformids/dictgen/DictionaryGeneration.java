/*
****************************************************************************
* This version of this file is part of DictionaryForMIDs-Creator
* (C) 2012 Karim Mahamane Karimou
*
* This version is a modified version. It was modified to make it compatible
* with DictionaryForMIDs-Creator. It was modified by me. See below for
* information about the original copyright holder.
*
* DictionaryForMIDs-Creator (DfM-Creator) is a GUI wrapper around various
* DictionaryForMIDs tools, among others we have DictdToDictionaryForMIDs,
* DictionaryGeneration, JarCreator and BitmapFontGenerator.
*  
* GPL applies, see file COPYING for more license information.
* 
****************************************************************************
*/


/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen;

import de.kugihan.DfMCreator.DfMCreatorMain;
import de.kugihan.DfMCreator.SumWinDictGen;
import de.kugihan.dictionaryformids.dataaccess.CsvFile;
import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dataaccess.FileCsvFile;
import de.kugihan.dictionaryformids.dataaccess.LanguageDefinition;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileAccessHandler;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileDfMInputStreamAccess;
import de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdate;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.general.UtilWin;
import de.kugihan.dictionaryformids.hmi_common.content.ContentParser;
import de.kugihan.dictionaryformids.hmi_common.content.StringColourItemText;
import de.kugihan.dictionaryformids.translation.SearchIndicator;
import de.kugihan.dictionaryformids.translation.TextOfLanguage;
import de.kugihan.dictionaryformids.translation.normation.Normation;
import edu.hws.eck.mdb.I18n;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

    public class DictionaryGeneration {

	private static String FILE_SEPARATOR = System.getProperty("file.separator");
	
	private static String sourceFile;
	private static String directoryDestination;
	private static String propertyPath;
	private static String endOfLineString = "\n";
	
	// Properties that are set in the DictionaryForMIDs property file:
	private static long searchListFileMaxSize = 0;
	private static long indexFileMaxSize = 0;
	private static long dictionaryFileMaxSize = 0;
	private static long [] IndexNumberOfSourceEntries;
	
	// content parser object for syntax checking of content:
	private static ContentParser contentParserObj;
                
        // String message shown in case
        // java.lang.OutOfMemoryError occurs.
        private static final String OutOfMemErrMsg = I18n.tr("outOfMemoryMsg.dictGenSummary");
                
        // Getter and setter methods
        public static String getSourceFile(){
            return sourceFile;
        }
        
        public static String getDirectoryDestination(){
            return directoryDestination;
        }
        
        public static String getPropertyPath(){
            return propertyPath;
        }
        
        public static void setSourceFile(String newSourceFile){
            sourceFile = newSourceFile;
        }
        
        public static void setDirectoryDestination(String newDirectoryDestination){
            directoryDestination = newDirectoryDestination;
        }
        
        public static void setPropertyPath(String newPropertyPath){
            propertyPath = newPropertyPath;
        }
        
    // Calling the showDictGenSummary window
    // that prints a summary of the preferences
    // before lauching the dictionary generation process.
    public static void showDictGenSummary(){
        SumWinDictGen dgensum = SumWinDictGen.getDictGenSummary();
        dgensum.setVisible(true);
    }
        
    public static void main(String[] args) throws DictionaryException {
	printCopyrightNotice();
        
        if (args.length != 3) {
            printArgumentError("Incorrect number of arguments");
            printUsage();
        } else {
                sourceFile = args[0];
                directoryDestination = args[1];
                propertyPath = args[2];
                
                if (! directoryDestination.endsWith(DictionaryDataFile.pathNameDataFiles)) {
                    printArgumentError("Argument 2 (outputdirectory) must end with " + DictionaryDataFile.pathNameDataFiles);  
                    printUsage();
                }
                
                // Call the dictionary generation subroutine
                generate();
        }
    }
    
	static public void printCopyrightNotice() throws DictionaryException {
            System.out.print(
                "\n\nDictionaryForMIDs - DictionaryGeneration\n" +
                "Copyright (C) 2005, 2006, 2007  Gert Nuber (dict@kugihan.de) et al\n\n" +

                "DictionaryGeneration is free software; you can redistribute it and/or modify\n" +
                "it under the terms of the GNU General Public License as published by\n" +
                "the Free Software Foundation; either version 2 of the License, or\n" +
                "(at your option) any later version.\n\n" +

                "DictionaryGeneration is distributed in the hope that it will be useful,\n" +
                "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
                "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
                "GNU General Public License for more details.\n\n" +

                "You should have received a copy of the GNU General Public License\n" +
                "along with DictionaryGeneration; if not, write to the Free Software Foundation,\n" +
                "Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA\n" +
                "For documentation and source code, see http://dictionarymid.sourceforge.net\n");
				
	}
	
	static public void printUsage() {
	System.out.print(
		"\nUsage:\n" +
		"java -jar DfM-Creator.jar -DictionaryGeneration input_dictionary_file output_directory property_directory\n\n" +
		"input_dictionary_file: file from which the directory is read (this is normally a CSV file)\n" +
		"output_directory: path to the directory where the generated dictionary files are written to.\n" +
		"This must end with \"" + DictionaryDataFile.pathNameDataFiles + "\" !)\n" + 
		"property_directory: directory where the file DictionaryForMIDs.properties is located\n\n");
        }
	
	static public void printArgumentError(String errorMessage) {
		System.out.print("\nError in command line argument:\n" + errorMessage + "\n");
	}
    

        
        public static void generate() throws DictionaryException {
        	UtilWin utilObj = new UtilWin();
		Util.setUtil(utilObj);
		boolean fileAccessError = false;

		try {
			if (!directoryDestination.endsWith(FILE_SEPARATOR))
			{
				directoryDestination = directoryDestination + FILE_SEPARATOR;
			}
			if (!propertyPath.endsWith(FILE_SEPARATOR))
			{
				propertyPath = propertyPath + FILE_SEPARATOR;
			}
			/* test if files can be accessed */
			if (! (new File(sourceFile)).canRead()) {
				System.err.println(I18n.tr("inputDictError", new Object[] {sourceFile}));
				fileAccessError = true;
			}
			if (! (new File(directoryDestination)).exists()) {
				System.err.println(I18n.tr("outputDictError", new Object[] {directoryDestination}));
				fileAccessError = true;
			}
			/* read properties */
			if (! utilObj.readProperties(propertyPath, true)) {
				System.err.println(I18n.tr("propFError", new Object[] {utilObj.buildPropertyFileName(propertyPath)}));  
				fileAccessError = true;
			}
			if (! fileAccessError) {
				/* generate the files */
				generateDictionaryFiles();
				/* copy the property file */
				copyPropertyFile(utilObj.buildPropertyFileName(propertyPath), directoryDestination);
				System.out.println(I18n.tr("complete\n"));
			}
		} catch (UnsupportedEncodingException e){
                        SumWinDictGen.done = true;
                        DfMCreatorMain.printAnyMsg(I18n.tr("encErrMsg", new Object[] {e.getLocalizedMessage()}),
                                                   I18n.tr("encErrWinTitle"), JOptionPane.ERROR_MESSAGE);
                        // print the exception in command line.
                        System.out.println(e + "\n");
                    } catch (java.lang.OutOfMemoryError e){
                        DfMCreatorMain.printAnyMsg(OutOfMemErrMsg, I18n.tr("outOfMemory.dictGenSummary")
                                                                             ,JOptionPane.ERROR_MESSAGE);
                    } catch (Throwable t){
                        DfMCreatorMain.printAnyMsg(I18n.tr("criticalErrMsg", new Object[]
                                    {t, t.getLocalizedMessage()}), I18n.tr("error"), JOptionPane.ERROR_MESSAGE);
                        System.out.println(t + "\n");
                    }
	}

        
	public static void generateDictionaryFiles() throws IOException, UnsupportedEncodingException, Throwable, DictionaryException {
		long numberOfEntriesSearchListTotal = 0;
		long numberOfEntriesIndexTotal = 0;
		long numberOfEntriesDictionaryTotal = 0;
		
		// Do a precheck first to see if all lines of the sourceFile are correctly built
		checkCsvFileFormat(sourceFile);
		
		/* 
		 * Create dictionary files
		 */
		System.out.println(I18n.tr("creatingDict"));
		FileDfMInputStreamAccess dfmInputStreamObj = new FileDfMInputStreamAccess("");
		FileAccessHandler.setDictionaryDataFileISAccess(dfmInputStreamObj);
		CsvFile source = new FileCsvFile(sourceFile,
				                         DictionaryDataFile.dictionaryGenerationSeparatorCharacter,
										 DictionaryDataFile.dictionaryGenerationInputCharEncoding);
		String postfixDictionaryFile = new String();
		IndexNumberOfSourceEntries = new long [DictionaryDataFile.numberOfAvailableLanguages];
		boolean generateSeparateDictionaryFile = false;
		HashMap indexOfNormatedWords[] = new HashMap [DictionaryDataFile.numberOfAvailableLanguages];
		contentParserObj = new ContentParser();
		for (int indexLanguage = 0;
		     indexLanguage < DictionaryDataFile.numberOfAvailableLanguages;
		     ++indexLanguage) {
			IndexNumberOfSourceEntries[indexLanguage] = 0;
			LanguageDefinition supportedLanguage = DictionaryDataFile.supportedLanguages[indexLanguage];
			if (supportedLanguage.generateIndex) {
				indexOfNormatedWords[indexLanguage] = new HashMap();
				if (supportedLanguage.hasSeparateDictionaryFile) {
					if (generateSeparateDictionaryFile) {
						String errorMessage = I18n.tr("moreThan");
						throw new DictionaryException(errorMessage);
					}
					else {
						generateSeparateDictionaryFile = true;
						postfixDictionaryFile = supportedLanguage.languageFilePostfix;
					}
				}
			}
			DictionaryUpdate DictionaryUpdateObj = (DictionaryUpdate) DictionaryDataFile.supportedLanguages[indexLanguage].dictionaryUpdateObj;
			DictionaryUpdateObj.setIndexLanguage(indexLanguage);
		}
		String indexEntryDictionary;
		String keyWordsDictionaryNonNormated = null;
		String indexString;
		long numberOfEntriesInDictionaryFile = 0;
		long dictionaryFileNumber = 0;
		long positionInDictionaryFile = 0;
		String directoryFileName = null;
		OutputStreamWriter destination = null;
		try {
			while (! source.endOfDictionaryReached) {
				String directoryLine = new String();
				if ((numberOfEntriesInDictionaryFile >= DictionaryDataFile.dictionaryGenerationMinNumberOfEntriesPerDictionaryFile)
					||  (destination == null)) {
					++dictionaryFileNumber;
					if (destination != null) {
						closeFile(destination, directoryFileName);
						numberOfEntriesInDictionaryFile = 0;
						positionInDictionaryFile = 0;
					}
					directoryFileName = new String(DictionaryDataFile.prefixDictionaryFile + 
											       postfixDictionaryFile +
							                       String.valueOf(dictionaryFileNumber) + 
							                       DictionaryDataFile.suffixDictionaryFile);
					directoryFileName = directoryDestination + directoryFileName;
					destination = new OutputStreamWriter(new FileOutputStream(directoryFileName), 
							                             DictionaryDataFile.dictionaryCharEncoding);
				}
				for (int indexLanguage = 0;
					 indexLanguage < DictionaryDataFile.numberOfAvailableLanguages;
					 ++indexLanguage) {
					DictionaryUpdate DictionaryUpdateObj = (DictionaryUpdate) DictionaryDataFile.supportedLanguages[indexLanguage].dictionaryUpdateObj;
					Normation normationObj = DictionaryDataFile.supportedLanguages[indexLanguage].normationObj;
					String sourceExpression = source.getWord().toString();
					keyWordsDictionaryNonNormated = DictionaryUpdateObj.updateDictionaryExpression(sourceExpression);
					if (DictionaryDataFile.supportedLanguages[indexLanguage].generateIndex) {
						Vector keyWordVector = DictionaryUpdateObj.createKeyWordVector(sourceExpression, 
								                                                       DictionaryDataFile.supportedLanguages[indexLanguage].expressionSplitString);
						DictionaryUpdateObj.updateKeyWordVector(keyWordVector);
						for (int indexKeyWord = 0; 
						     indexKeyWord < keyWordVector.size(); 
						     ++indexKeyWord) {
							IndexKeyWordEntry indexKeyWordEntryObj = (IndexKeyWordEntry) keyWordVector.elementAt(indexKeyWord);
							String keyWordNonNormated = indexKeyWordEntryObj.keyWord;
							SearchIndicator searchIndicatorObj = indexKeyWordEntryObj.searchIndicator;
							indexEntryDictionary = normationObj.normateWord(new StringBuffer(keyWordNonNormated), false).toString();
							if (indexEntryDictionary.length() > 0) {
								indexString = String.valueOf(dictionaryFileNumber) + 
								              DictionaryDataFile.indexFileSeparatorFileNumberToPosition + 
								              String.valueOf(positionInDictionaryFile) +
								              DictionaryDataFile.indexFileSeparatorFilePositionToSearchIndicator +
								              searchIndicatorObj.asChar();
								// check if entry already exists in map of normated words
								String existingEntry = (String) indexOfNormatedWords[indexLanguage].get(indexEntryDictionary);						
								if (existingEntry != null) {
									// entry already existed: add reference
									indexString = existingEntry + 
									              DictionaryDataFile.indexFileSeparatorIndexEntries + 
									              indexString;
								}
								indexOfNormatedWords[indexLanguage].put(indexEntryDictionary, indexString);
								if (searchIndicatorObj.isBeginOfExpression())
									++IndexNumberOfSourceEntries[indexLanguage];
							}
						}
					}
					directoryLine = directoryLine + keyWordsDictionaryNonNormated;
					if (indexLanguage < DictionaryDataFile.numberOfAvailableLanguages -1) 
						directoryLine = directoryLine + DictionaryDataFile.dictionaryFileSeparationCharacter;
				}
                                String directoryOutput = directoryLine + endOfLineString;
				 // calculate the number of used bytes
				int lengthOfDictionaryEntry = directoryOutput.getBytes(DictionaryDataFile.dictionaryCharEncoding).length;
				positionInDictionaryFile += lengthOfDictionaryEntry;
				if (positionInDictionaryFile > dictionaryFileMaxSize)
					dictionaryFileMaxSize = positionInDictionaryFile;
				// write to file and increment counters

				if ("weakCrypt".equals(DictionaryDataFile.fileEncodingFormat)) {
					directoryOutput = weakEncrypt(directoryOutput);
				}

				destination.write(directoryOutput);
				++numberOfEntriesDictionaryTotal;
				++numberOfEntriesInDictionaryFile;
			}
			closeFile(destination, directoryDestination);
			System.out.println(I18n.tr("doneGen"));
		}
		catch (DictionaryException e) {
			System.out.println(I18n.tr("errorCreatingLine", new Object[] {(numberOfEntriesDictionaryTotal+1)}));
			throw e;
		}
		
		/* 
		 * Create Indexes 
		 */
		System.out.println(I18n.tr("indexSearchlist"));
		for (int indexLanguage = 0;
		     indexLanguage < DictionaryDataFile.numberOfAvailableLanguages;
		     ++indexLanguage) {
			long numberOfEntriesInIndexFile = 0;
			long positionInIndexFile = 0;
			long indexFileNumber = 0;
			OutputStreamWriter indexFile = null;
			String indexFileName = null;

			if (! DictionaryDataFile.supportedLanguages[indexLanguage].generateIndex) {
				// do not generate an index for non-searchable parts: skip this language
				continue;					
			}
			String languageFilePostfix = DictionaryDataFile.supportedLanguages[indexLanguage].languageFilePostfix;
			String searchListFileName = directoryDestination + 
                                        DictionaryDataFile.prefixSearchListFile + 
			                            languageFilePostfix +
										DictionaryDataFile.suffixSearchListFile;
			OutputStreamWriter searchListFile = new OutputStreamWriter(new FileOutputStream(searchListFileName), 
					                                                   DictionaryDataFile.searchListCharEncoding);

			Set indexSet = (new TreeMap(indexOfNormatedWords[indexLanguage])).entrySet();
			Iterator indexIterator = indexSet.iterator();
			while (indexIterator.hasNext()) {
				Map.Entry indexEntry = (Map.Entry) indexIterator.next();
				indexEntryDictionary = (String) indexEntry.getKey();
				indexString = (String) indexEntry.getValue();
				if ((numberOfEntriesInIndexFile >= DictionaryDataFile.dictionaryGenerationMinNumberOfEntriesPerIndexFile)
					 ||  (indexFile == null)) {
					++indexFileNumber;
					if (indexFile != null) {
						closeFile(indexFile, indexFileName);
						numberOfEntriesInIndexFile = 0;
					}
					indexFileName = directoryDestination + 
					                DictionaryDataFile.prefixIndexFile + 
									languageFilePostfix + 
					                String.valueOf(indexFileNumber) + 
									DictionaryDataFile.suffixIndexFile;
			        indexFile = new OutputStreamWriter(new FileOutputStream(indexFileName), 
							                           DictionaryDataFile.indexCharEncoding);
			        positionInIndexFile = 0;
			        String searchListOutput = indexEntryDictionary + 
								              DictionaryDataFile.searchListFileSeparationCharacter + 
											  String.valueOf(indexFileNumber) +
											  endOfLineString; 
					int lengthOfSearchListEntry = searchListOutput.getBytes(DictionaryDataFile.searchListCharEncoding).length;
					searchListFileMaxSize += lengthOfSearchListEntry;
					searchListFile.write(searchListOutput);
					++numberOfEntriesSearchListTotal;
				}
				String indexOutput = indexEntryDictionary + 
		        					 DictionaryDataFile.indexFileSeparationCharacter +
		        					 indexString + 
		        					 endOfLineString; 
				int lengthOfIndexEntry = indexOutput.getBytes(DictionaryDataFile.indexCharEncoding).length;
				positionInIndexFile += lengthOfIndexEntry;
				if (positionInIndexFile > indexFileMaxSize)
					indexFileMaxSize = positionInIndexFile;
        indexFile.write(indexOutput);
				++numberOfEntriesIndexTotal;
				++numberOfEntriesInIndexFile;
			}
			closeFile(indexFile, indexFileName);
			closeFile(searchListFile, searchListFileName);
		}
		Util.getUtil().log(I18n.tr("generationDone", new Object[] {numberOfEntriesSearchListTotal, numberOfEntriesIndexTotal, numberOfEntriesDictionaryTotal}));
	}
	
	public static void checkCsvFileFormat(String csvFileName) throws DictionaryException, IOException {
		System.out.println(I18n.tr("checking", new Object[] {csvFileName}));
		InputStreamReader csvFile = new InputStreamReader
					    				(new FileInputStream(csvFileName), 
					    			     DictionaryDataFile.dictionaryGenerationInputCharEncoding);
		String csvFileLine;
		int lineCounter = 0;
		String splitCharacter = String.valueOf(DictionaryDataFile.dictionaryGenerationSeparatorCharacter);
		while ((csvFileLine = readLineFromReader(csvFile)) != null) {
			++lineCounter;
			// check if number of separator characters is correct
			int numberOfSeparatorCharacters = csvFileLine.split(splitCharacter).length - 1;
			if (numberOfSeparatorCharacters != (DictionaryDataFile.numberOfAvailableLanguages -1)) {
				DfMCreatorMain.printAnyMsg(I18n.tr("sepCharError", new Object[] {lineCounter,
				numberOfSeparatorCharacters, (DictionaryDataFile.numberOfAvailableLanguages - 1)}),
				I18n.tr("incorrectFile"), JOptionPane.ERROR_MESSAGE);
                                
                            throw new DictionaryException(I18n.tr("incorrectFile"));
			}
		}
		csvFile.close();
		System.out.println(I18n.tr("doneChecking"));
	}

	public static String removeContentDelimiters(String contentString, int indexLanguage) 
					throws DictionaryException {
		StringBuffer itemString;
		StringColourItemText stringColourItemText = 
						contentParserObj.determineItemsFromContent(new TextOfLanguage(contentString, indexLanguage), false, false);
		itemString = contentParserObj.getTextFromStringColourItemText(stringColourItemText);
		return itemString.toString();
	}

	public static String readLineFromReader(InputStreamReader reader) 
				throws IOException {
		StringBuilder line = new StringBuilder();
		int character;
		do {
			character = reader.read();
			if (character == -1)
				return null;  // end of file reached
			if (character != '\n')
				line.append((char) character);
		}
		while (character != '\n'); 
		return line.toString();
	}

	public static void copyPropertyFile(String fileNameSourcePropertyFile,
			                            String destinationDirectory) {
		String fileNameDestinationPropertyFile = destinationDirectory + UtilWin.propertyFileName;
		System.out.println(I18n.tr("creating", new Object[] {fileNameDestinationPropertyFile}));
		try {
			Properties dictionaryForMIDsProperties = new Properties();
			dictionaryForMIDsProperties.load(new FileInputStream(fileNameSourcePropertyFile));
			setFileMaxSizeProperty("searchListFileMaxSize", searchListFileMaxSize, dictionaryForMIDsProperties, fileNameSourcePropertyFile);
			setFileMaxSizeProperty("indexFileMaxSize", indexFileMaxSize, dictionaryForMIDsProperties, fileNameSourcePropertyFile);
			setFileMaxSizeProperty("dictionaryFileMaxSize", dictionaryFileMaxSize, dictionaryForMIDsProperties, fileNameSourcePropertyFile);
			for (int indexLanguage = 0;
		     	 indexLanguage < DictionaryDataFile.numberOfAvailableLanguages;
		     	 ++indexLanguage) {
				long numberOfBeginEntries = IndexNumberOfSourceEntries[indexLanguage];
				if (numberOfBeginEntries > 0) {
					setPropertyValue("language"+ String.valueOf(indexLanguage+1) + "IndexNumberOfSourceEntries",
									 String.valueOf(numberOfBeginEntries),
							         dictionaryForMIDsProperties); 
				}
			}
			FileOutputStream propertiesDestination = new FileOutputStream(fileNameDestinationPropertyFile);
			dictionaryForMIDsProperties.store(propertiesDestination, "DictionaryForMIDs property file");
		}
        catch (IOException e) {
			Util.getUtil().log(e);
        }
		System.out.println(I18n.tr("doneProperty"));
	}

	public static void setFileMaxSizeProperty(String propertyName,
			                                  long   propertyValue,
			                                  Properties propertyObj,
			                                  String     fileNamePropertyFile) {
		if (propertyObj.getProperty(propertyName) != null) {
			System.out.println(I18n.tr("propExists", new Object[] {propertyName, fileNamePropertyFile}));
			if (Integer.valueOf(propertyObj.getProperty(propertyName)).longValue() < propertyValue) {
				System.out.println(I18n.tr("warning", new Object[] {propertyName, propertyValue}));
			}
		}
		else {
			setPropertyValue(propertyName,
							 String.valueOf(propertyValue),
							 propertyObj);
		}
	}

	public static void setPropertyValue(String propertyName,
							            String propertyValue,
							            Properties propertyObj) {
		propertyObj.setProperty(propertyName, String.valueOf(propertyValue));
		System.out.println(I18n.tr("propertySetTo", new Object[] {propertyName, propertyValue}));
	}
	
	public static void closeFile(OutputStreamWriter outFileStream, String fileName) throws IOException {
		outFileStream.close();
	}

	/**
	 * Very weak encrytion/decryption mechanism.
	 * See http://dictionarymid.german-fighters.com/forum/index.php?topic=215.0
	 */
	private static String weakEncryptOld(String directoryOutput) {
		StringBuilder res = new StringBuilder(directoryOutput.length());
		for (char ch : directoryOutput.toCharArray()) {
			if (ch>=60 && ch<124) ch = (char) (((ch-60)^'+') + 60);
			res.append(ch);
		}
		return res.toString();
	}

	// intentional misspelling "Espernato" for Esperanto
	private static final char[] weakEncrypt_password = "EspernatoEstasBona".toCharArray();
	/**
	 * A little bit less weak encrytion/decryption mechanism.
	 * See http://dictionarymid.german-fighters.com/forum/index.php?topic=215.0
	 */
	private static String weakEncrypt(String word) {
		StringBuilder res = new StringBuilder(word.length());
		// Assume we start on a word boundary and restart each time we encounter a char outside range 60-124
		int n = 0;
		for (char ch : word.toCharArray()) {
			if (ch>=60 && ch<124) {
				//char ch0 = ch;
				ch = (char) ((((ch-60+weakEncrypt_password[n])%64)^'+') + 60);

				//char ch2 = (char) ((((ch-60)^'+') -weakEncrypt_password[n]+256)%64+ 60);
				//if (ch0 != ch2) throw new InternalError(word + " " + n + " " + ch + "!="+ ch2);

				n  = (n+1) % weakEncrypt_password.length;
			} else {
				n = 0; // outside range - restart
			}
			res.append(ch);
		}
		return res.toString();
	}
}
