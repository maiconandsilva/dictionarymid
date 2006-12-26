/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Vu Van Quynh (quynh2003hp@yahoo.com) and Gert Nuber
(dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.languagegen;

import java.io.*;

public class LanguageUIGeneration {

	static final String applicationName = "LanguageUIGeneration";
	static final String versionNumber = "3.0.1";
	
	static String FILE_SEPARATOR = System.getProperty("file.separator");
	
	static final String languageUICharEncoding = "UTF-8";
	
	static final String languageUIInputFileName = "DictionaryForMIDs.languages";
	static final String uiDisplayTextContentsOutputFileName = "UIDisplayTextContents.java";
	static final String uiDisplayTextItemsOutputFileName = "UIDisplayTextItems.java";
	
	static String languageUIInputFileLocation;
	static String uiDisplayTextContentsOutputLocation;
	static String uiDisplayTextItemsOutputLocation;
	
	static final String fallbackLanguage = "English";  // for items that are not available, the items from this language are used
	static final String noTranslationForThisItem = "[no translation]";  // for this item there is no translation, the fallbackLanguage is used instead   
	static final String uiDisplayTextItemsIdentifier = "UIDisplayTextItems";  // special "language" that contains the keys for the items
	static final String uiDisplayTextItemCommentSeparator = ";";  // separator for comment for display text items
	static final String uiDisplayTextItemParameterSeparator = ",";  // separtor for parameters for display text items
	static final String languageCodeSeparator = ",";  // for #-lines: separator between language title and ISO 639 language codes
	static final String languageSeparatorStartsWith = "==============";
	static final String uiDisplayTextPackage = "de.kugihan.dictionaryformids.hmi_j2me.uidisplaytext";
	
	public static void main(String[] args) {
		boolean fileAccessError = false;
		printCopyrightNotice();
		if (args.length != 2) {
			printArgumentError("Incorrect number of arguments");
			printUsage();
		}
		else {
			String inputDirectory = args[0];
			inputDirectory  = appendFileSeparator(inputDirectory);
			languageUIInputFileLocation = inputDirectory + languageUIInputFileName;
			String outputDirectory = args[1];
			outputDirectory  = appendFileSeparator(outputDirectory);
			uiDisplayTextContentsOutputLocation = outputDirectory + uiDisplayTextContentsOutputFileName;
			uiDisplayTextItemsOutputLocation = outputDirectory + uiDisplayTextItemsOutputFileName;
			try {
				/* test if file can be accessed */
				if (! (new File(languageUIInputFileLocation)).canRead()) {
					printArgumentError("File cannot be accessed: " + languageUIInputFileLocation);
					fileAccessError = true;
				}
				if (! (new File(outputDirectory)).exists()) {
					printArgumentError("Outputdirectory cannot be accessed: " + outputDirectory);
					fileAccessError = true;
				}
				if (! fileAccessError) {
					/* generate the files */
					generateLanguageUIFiles();
					log("\nComplete\n");
				}
			}
			catch (Throwable t) {
				log(t);
			}
		}
	}

	static public void printCopyrightNotice() {
		System.out.print(
				"\n\nDictionaryForMIDs/LanguageUIGeneration, Copyright (C) 2005, 2006  Vu Van Quynh \n" + 
				"(quynh2003hp (a) yahoo.com) and Gert Nuber (dict@kugihan.de)\n" +
				"Version : " + versionNumber + "\n\n" +
				"This program comes with ABSOLUTELY NO WARRANTY\n\n" +
				"This program is free software under the terms and conditions of the GPL " + 
				"(GNU \nGeneral Public License) version 2. See file COPYING. " +
				"If you did not receive the\nGNU General Public License along with this program " +
                "(file COPYING), write\nto the Free Software Foundation, Inc., " +
				"59 Temple Place, Suite 330, Boston,\nMA  02111-1307  USA\n\n" +
				"Source code is available from http://sourceforge.net/projects/dictionarymid\n\n\n");
	}
	
	static public void printUsage() {
		System.out.print(
				"\n\nUsage:\n" +
				"java -jar LanguageUIGeneration.jar inputdirectory outputdirectory\n\n" +
				"inputdirectory: file from which the file " + languageUIInputFileName + " is read\n" +
				"outputdirectory: pathname where the generated files " + uiDisplayTextContentsOutputFileName + " and\n" +  
				uiDisplayTextItemsOutputFileName + " are written to.\n\n\n");
	}
	
	static public void printArgumentError(String errorMessage) {
		System.out.print(
				"\n\nError in command line argument:\n" + errorMessage);
	}
	
	
	static void generateLanguageUIFiles() {
		try {
			/* 
			 * Create output files
			 */
			log("Creating files " + uiDisplayTextContentsOutputLocation + " and " + uiDisplayTextItemsOutputLocation + "\n");
			OutputStreamWriter uiDisplayTextContentsOutputFile = 
				            new OutputStreamWriter(new FileOutputStream(uiDisplayTextContentsOutputLocation), 
                    							   languageUICharEncoding);
			OutputStreamWriter uiDisplayTextItemsOutputFile = 
	             			new OutputStreamWriter(new FileOutputStream(uiDisplayTextItemsOutputLocation), 
       											   languageUICharEncoding);
			writeHeaderLines(uiDisplayTextItemsOutputFile);
			writeLine(uiDisplayTextItemsOutputFile,
					  "public class UIDisplayTextItems {");
			writeHeaderLines(uiDisplayTextContentsOutputFile);
			writeLine(uiDisplayTextContentsOutputFile,
			  	      "public class UIDisplayTextContents {\n" +
			  	      "\tpublic static final LanguageDisplayText[] availableDisplayTexts = {\n");
			InputStream languageStream = new FileInputStream(languageUIInputFileLocation);
			if (languageStream != null) {
				InputStreamReader rd = new InputStreamReader(languageStream, "UTF-8");
				boolean endOfFile = false;
				String strLine;
				boolean beforeFirstLanguage = true;
				boolean languageSeparatorBefore = true;
				boolean inUIDisplayTextItems = false; // true, when the keys for the items are read
				boolean inFallbackLanguage = false; // true, when the items for the fallback language are read
				String [] fallbackItems = null; // items from fallback language
				boolean languageWrittenToDisplayTexts = false;
				int numberOfUILanguages = 0;
				int numberItemInLanguage = 0;
				int totalNumberItemsInLanguage = 0;
				int numberOfLine = 0;
				String language = null;
				String lastLanguage = null;
				while(! endOfFile){
					strLine = readLine(rd);
					++numberOfLine;
					endOfFile = (strLine == null);
					if (! endOfFile) {
						if ((strLine.length() != 0) && (strLine.charAt(0) == '#')){
							beforeFirstLanguage = false;
							if (! languageSeparatorBefore)
								incorrectFileFormat("#-line must follow " + languageSeparatorStartsWith + "-line", 
										            numberOfLine);
							languageSeparatorBefore = false;
							lastLanguage = language;
							int languageEndPos = strLine.indexOf(languageCodeSeparator);
							if (languageEndPos == -1)
								incorrectFileFormat("No language code provided for " + strLine, numberOfLine);
							language = strLine.substring(1, languageEndPos).trim();
							String languageCode;
							if (languageEndPos + 1 < strLine.length())
								languageCode = strLine.substring(languageEndPos + 1).trim().toLowerCase();
							else
								languageCode = new String();
							if (language.compareTo(uiDisplayTextItemsIdentifier) == 0) {
								inUIDisplayTextItems = true;
							}
							else {
								if (inUIDisplayTextItems)  // last was uiDisplayTextItems
									log("Number of items per language: " + totalNumberItemsInLanguage + "\n");
								inUIDisplayTextItems = false;
								++numberOfUILanguages;
								copyFallbackItems(numberItemInLanguage,
					                      	      totalNumberItemsInLanguage,
					                      	      lastLanguage,
					                      	      uiDisplayTextContentsOutputFile,
					                      	      fallbackItems);
								if (languageWrittenToDisplayTexts) {
									writeLine(uiDisplayTextContentsOutputFile,
										      "\t\t}),\n");
								}
								writeLine(uiDisplayTextContentsOutputFile,
										  "\t\tnew LanguageDisplayText(\"" + language + "\" ,\n" +
										  "\t\t\t\t\t\t\t\t\"" + languageCode + "\",\n" +
										  "\t\t\t\t\t\t\t\tnew String[] {");
								languageWrittenToDisplayTexts = true;
							}
							if (language.compareTo(fallbackLanguage) == 0) {
								inFallbackLanguage = true;
								if (totalNumberItemsInLanguage == 0)
									incorrectFileFormat("Language " + uiDisplayTextItemsIdentifier + " not found at start of file", 
									                    numberOfLine);
								fallbackItems = new String[totalNumberItemsInLanguage];
							}
							else {
								inFallbackLanguage = false;
							}
							numberItemInLanguage = 0;
						}
						else if (beforeFirstLanguage) {
							languageSeparatorBefore = true;
							continue;
						}
						else if (strLine.toLowerCase().startsWith(languageSeparatorStartsWith.toLowerCase())){
							beforeFirstLanguage = false;
							languageSeparatorBefore = true;
						}
						else {
							languageSeparatorBefore = false;
							++numberItemInLanguage;
							if (inUIDisplayTextItems) {
								// special part for display text items
								int uiDisplayTextItemPartEndPos = strLine.indexOf(uiDisplayTextItemCommentSeparator);
								if (uiDisplayTextItemPartEndPos == -1)
									uiDisplayTextItemPartEndPos = strLine.length();
								String uiDisplayTextItemPart = (strLine.substring(0, uiDisplayTextItemPartEndPos)).trim();
								int uiDisplayTextItemNameEndPos = uiDisplayTextItemPart.indexOf(uiDisplayTextItemParameterSeparator);
								int numberOfParameters = 0;
								if (uiDisplayTextItemNameEndPos == -1) {
									uiDisplayTextItemNameEndPos = uiDisplayTextItemPart.length();
								}
								else {
									if (uiDisplayTextItemPart.length() > uiDisplayTextItemNameEndPos+1) {
										String numberOfParametersString = 
													uiDisplayTextItemPart.substring(uiDisplayTextItemNameEndPos+1, 
													 							    uiDisplayTextItemPart.length());
										numberOfParameters = Integer.valueOf(numberOfParametersString.trim()).intValue(); 
									}
								}
								String uiDisplayTextItemName = (uiDisplayTextItemPart.substring(0, uiDisplayTextItemNameEndPos)).trim();
								
								writeUIDisplayTextItemLine(uiDisplayTextItemsOutputFile,
														   uiDisplayTextItemName,
										                   numberItemInLanguage-1,
										                   numberOfParameters);
								totalNumberItemsInLanguage = numberItemInLanguage;
							}
							else {
								// normal language
								if (numberItemInLanguage > totalNumberItemsInLanguage)
									incorrectFileFormat("Too many items for language",
						                                numberOfLine);
								String displayText;
								if (strLine.startsWith(noTranslationForThisItem)) {
									if (inFallbackLanguage)
										incorrectFileFormat(noTranslationForThisItem + " not allowed for fallback language",
				                                            numberOfLine);
									displayText = fallbackItems[numberItemInLanguage-1];
								}
								else {
									displayText = strLine;
								}
								writeUIDisplayTextContentLine(uiDisplayTextContentsOutputFile,
															  displayText);
								if (inFallbackLanguage) {
									fallbackItems[numberItemInLanguage-1] = displayText;									
								}
							}
						}
					}
				}
				copyFallbackItems(numberItemInLanguage,
                	      		  totalNumberItemsInLanguage,
                	      		  language,
                	      		  uiDisplayTextContentsOutputFile,
                	      		  fallbackItems);
				writeLine(uiDisplayTextItemsOutputFile, "}");
				writeLine(uiDisplayTextContentsOutputFile, 
						"\t\t})\n" +
						"\t};\n\n" +
						"\tpublic static int totalNumberItemsInLanguage = "+ totalNumberItemsInLanguage +";\n\n"+ 
						"}");
				languageStream.close();
				uiDisplayTextItemsOutputFile.close();
				uiDisplayTextContentsOutputFile.close();
			}
		}
        catch (FileNotFoundException e) {
			log(e);
        }
        catch (incorrectFileFormatException e) {
			log(e);
        }
        catch (IOException e) {
			log(e);
        }
 	}

	
	public static void log(String logMessage) {
		System.out.println(logMessage);
	}
	
	public static void log(Throwable thrown) {
		log("\n\nThrown " + thrown.toString() + " /\n" + thrown.getMessage() + "\n");
		if (! (thrown instanceof incorrectFileFormatException))
			thrown.printStackTrace();
	}
	
	public static void incorrectFileFormat(String message, int numberOfLine) throws incorrectFileFormatException {
		throw new incorrectFileFormatException(message + "/ Line number " + numberOfLine);
	}
	
	public static void writeHeaderLines(OutputStreamWriter osr) throws IOException {
		writeLine(osr,
				"/*\n" +
				"DictionaryForMIDs - a free multi-language dictionary for mobile devices.\n" +
				"Copyright (C) 2005, 2006  Vu Van Quynh (quynh2003hp@yahoo.com) and Gert Nuber\n" +
				"(dict@kugihan.de)\n\n" +
				"GPL applies - see file COPYING for copyright statement.\n" +
				"*/\n\n\n" +
				"/*\n\n" +
				"This file was automatically generated by " + applicationName + " version " + versionNumber + "\n\n" +
				"*/\n\n" +
				"package " + uiDisplayTextPackage + ";\n\n");
	}
	
	public static void writeUIDisplayTextItemLine(OutputStreamWriter osr, 
                                                  String             uiDisplayTextItemName,
                                                  int                displayTextItemKeyValue,
                                                  int				 numberOfParameters) 
	                    throws IOException {
		writeLine(osr,
		          "\tpublic static final UIDisplayTextItem " + uiDisplayTextItemName + 
		                  " = LanguageUI.getUI().createNewUIDisplayTextItem(" + 
				                  					 "\"" + uiDisplayTextItemName + "\"" + ", " +
		                                             String.valueOf(displayTextItemKeyValue) + ", " +
		                                             String.valueOf(numberOfParameters) + 
                                                                       ");");
	}
	
	public static void writeUIDisplayTextContentLine(OutputStreamWriter osr, 
            								         String             displayText)
						throws IOException {
		writeLine(osr,
		          "\t\t\t\t\t\t\t\t\t\"" + displayText + "\",");
	}
	
	
	public static void copyFallbackItems(int 				numberItemInLanguage,
			                             int 				totalNumberItemsInLanguage,
			                             String    		 	lastLanguage,
			                             OutputStreamWriter uiDisplayTextContentsOutputFile,
			                             String [] 		 	fallbackItems) 
							throws IOException {
		for(int itemCount = numberItemInLanguage; 
		    itemCount < totalNumberItemsInLanguage;
		    ++itemCount) {
			String fallbackItem = fallbackItems[itemCount];
			log("Warning: item #" + String.valueOf(itemCount+1) + " for language " + 
				lastLanguage + " not found; taken from " + 
				fallbackLanguage + ": " + fallbackItem);
			writeUIDisplayTextContentLine(uiDisplayTextContentsOutputFile,
								          fallbackItem);
		}
	}
	
	public static String appendFileSeparator(String directoryString) {
		String newDirectoryString = directoryString;
		if (! newDirectoryString.endsWith(FILE_SEPARATOR))
		{
			newDirectoryString = newDirectoryString + FILE_SEPARATOR;
		}
		return newDirectoryString;
	}
	
	private static String readLine(InputStreamReader isr) throws IOException {
		  StringBuffer strLine = new StringBuffer();
			int ch;
			boolean endOfLineReached = false;
			do {
					ch = isr.read();
					if ((ch != '\n') && (ch != -1)) {
						if (ch != '\r')  // ignore carriage return characters
							strLine.append((char) ch);
					}
					else {
						endOfLineReached = true;
					}
			} 
			while (! endOfLineReached);
			if((ch == -1) && (strLine.length() == 0)) return null;
		  else return strLine.toString();
	  }
	
	private static void writeLine(OutputStreamWriter osr, String line) throws IOException {
		osr.write(line + "\n");
	}
}

class incorrectFileFormatException extends Exception {
	incorrectFileFormatException(String message) {
		super(message);
	}
}