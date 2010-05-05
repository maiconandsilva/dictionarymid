/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dictgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

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

public class DictionaryGeneration {

	static String FILE_SEPARATOR = System.getProperty("file.separator");
	
	static String sourceFile;
	static String directoryDestination;
	static String propertyPath;
	static String endOfLineString = new String("\n");
	
	// Properties that are set in the DictionaryForMIDs property file:
	static long searchListFileMaxSize = 0;
	static long indexFileMaxSize = 0;
	static long dictionaryFileMaxSize = 0;
	static long [] IndexNumberOfSourceEntries;
	
	// content parser object for syntax checking of content:
	static ContentParser contentParserObj;
	
	public static void main(String[] args) throws DictionaryException {
		UtilWin utilObj = new UtilWin();
		Util.setUtil(utilObj);
		boolean fileAccessError = false;
		printCopyrightNotice();
		if (args.length != 3) {
			printArgumentError("Incorrect number of arguments");
			printUsage();
		}
		else {
			sourceFile = args[0];
			directoryDestination = args[1];
			propertyPath = args[2];
			if (! directoryDestination.endsWith(DictionaryDataFile.pathNameDataFiles)) {
				printArgumentError("Argument 2 (outputdirectory) must end with " + DictionaryDataFile.pathNameDataFiles);  
				printUsage();
			}
			else {
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
						printArgumentError("Inputdictionaryfile cannot be accessed: " + sourceFile);
						fileAccessError = true;
					}
					if (! (new File(directoryDestination)).exists()) {
						printArgumentError("Outputdirectory cannot be accessed: " + directoryDestination);
						fileAccessError = true;
					}
					/* read properties */
					if (! utilObj.readProperties(propertyPath, true)) {
						System.err.println("Property-file cannot be accessed: " + utilObj.buildPropertyFileName(propertyPath));  
						fileAccessError = true;
					}
					if (! fileAccessError) {
						/* generate the files */
						generateDictionaryFiles();
						/* copy the property file */
						copyPropertyFile(utilObj.buildPropertyFileName(propertyPath), directoryDestination);
						System.out.println("Complete\n");
					}
				}
				catch (Throwable t) {
					Util.getUtil().log(t);
				}
			}
		}
	}

	static public void printCopyrightNotice() throws DictionaryException {
		System.out.print(
				"\n\nDictionaryForMIDs/DictionaryGeneration, Copyright (C) 2005, 2006, 2007  Gert Nuber (dict@kugihan.de) et al\n" +
				"Version : " + Util.getUtil().getApplicationVersionString() + "\n\n" +
				"This program comes with ABSOLUTELY NO WARRANTY\n\n" +
				"This program is free software under the terms and conditions of the GPL " + 
				"(GNU \nGeneral Public License) version 2. See file COPYING. " +
				"If you did not receive the\nGNU General Public License along with this program " +
                "(file COPYING), write\nto the Free Software Foundation, Inc., " +
				"59 Temple Place, Suite 330, Boston,\nMA  02111-1307  USA\n\n" +
				"Documentation and source code is available from http://dictionarymid.sourceforge.net\n\n\n");
	}
	
	static public void printUsage() {
		System.out.print(
				"\n\nUsage:\n" +
				"java -jar DictionaryGeneration.jar inputdictionaryfile outputdirectory propertydirectory\n\n" +
				"inputdictionaryfile: file from which the directory is read\n" +
				"outputdirectory: pathname where the generated directory files are written to " +
				"(must end with \"" + DictionaryDataFile.pathNameDataFiles + "\" !)\n" + 
				"propertydirectory: directory where the file DictionaryForMIDs.properties is located\n\n\n");
	}
	
	static public void printArgumentError(String errorMessage) {
		System.out.print(
				"\n\nError in command line argument:\n" + errorMessage);
	}
	
	
	static void generateDictionaryFiles() 
			throws IOException, DictionaryException {
		long numberOfEntriesSearchListTotal = 0;
		long numberOfEntriesIndexTotal = 0;
		long numberOfEntriesDictionaryTotal = 0;
		
		// Do a precheck first to see if all lines of the sourceFile are correctly built
		checkCsvFileFormat(sourceFile);
		
		/* 
		 * Create dictionary files
		 */
		System.out.println("Creating: dictionary files");
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
						String errorMessage = "More than 1 language with hasSeparateDictionaryFile and generateIndex true";
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
			System.out.println("Done: dictionary files\n");
		}
		catch (DictionaryException e) {
			System.out.println("\n\nError creating line nr. " + (numberOfEntriesDictionaryTotal+1) + "\n");
			throw e;
		}
		
		/* 
		 * Create Indexes 
		 */
		System.out.println("Creating: index and searchlist files");
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
		Util.getUtil().log("Done: index and searchlist files\n\n" + 
				           "Total number of searchlist entries : " + numberOfEntriesSearchListTotal + "\n" +
				           "Total number of index entries :      " + numberOfEntriesIndexTotal      + "\n" +
				           "Total number of dictionary entries : " + numberOfEntriesDictionaryTotal + "\n");
 	}
	
	public static void 	checkCsvFileFormat(String csvFileName) 
					throws DictionaryException, IOException {
		System.out.println("Checking: " + csvFileName);
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
				System.out.println("\nNumber of separator-characters is not correct in line " + lineCounter);
				System.out.println("\nNumber of found separator-characters: " + numberOfSeparatorCharacters + 
						           " / expected: " + (DictionaryDataFile.numberOfAvailableLanguages -1) + "\n\n");
				throw new DictionaryException("File format incorrect");
			}
		}
		csvFile.close();
		System.out.println("Done: checking\n");
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
		StringBuffer line = new StringBuffer();
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
		System.out.println("Creating: " + fileNameDestinationPropertyFile);
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
		System.out.println("Done: property file\n");
	}

	public static void setFileMaxSizeProperty(String propertyName,
			                                  long   propertyValue,
			                                  Properties propertyObj,
			                                  String     fileNamePropertyFile) {
		if (propertyObj.getProperty(propertyName) != null) {
			System.out.println("Property " + propertyName + " exists in " + fileNamePropertyFile + ": not automatically set");
			if (Integer.valueOf(propertyObj.getProperty(propertyName)).longValue() < propertyValue) {
				System.out.println("Warning Property " + propertyName + " should be " + propertyValue);
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
		System.out.println("Property " + propertyName + " set to " + propertyValue);
	}
	
	public static void closeFile(OutputStreamWriter outFileStream, String fileName) throws IOException {
		outFileStream.close();
	}

  /**
   * Very weak encrytion/decryption mechanism.
   * See http://dictionarymid.german-fighters.com/forum/index.php?topic=215.0
   */
  private static String weakEncrypt(String directoryOutput) {
    StringBuilder res = new StringBuilder(directoryOutput.length());
    for (char ch : directoryOutput.toCharArray()) {
        if (ch>=60 && ch<124) ch = (char) (((ch-60)^'+') + 60);
        res.append(ch);
    }
    return res.toString();
  }

}
