/*
****************************************************************************
* This version of this file is part of DictionaryForMIDs Creator
* (C) 2012 Karim Mahamane Karimou
*
* This version is a modified version. It was modified to make it compatible
* with DictionaryForMIDs Creator. It was modified by me. See below for
* information about the original copyright holder.
*
* DictionaryForMIDs Creator (DfMCreator) is a GUI wrapper around various
* DictionaryForMIDs tools, among others we have DictdToDictionaryForMIDs,
* DictionaryGeneration, JarCreator and BitmapFontGenerator.
*  
* GPL applies, see file COPYING for more license information.
* 
****************************************************************************
*/


/*
JARCreator for DictionaryForMIDs 
Copyright (C) 2005 Mathis Karmann

Some modifications by Gert Nuber

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.jarCreator;

import de.kugihan.DfMCreator.DfMCreatorException;
import de.kugihan.DfMCreator.DfMCreatorMain;
import de.kugihan.DfMCreator.JarCreationSummary;
import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.general.UtilWin;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.ResourceHandler;
import de.kugihan.dictionaryformids.hmi_java_me.lcdui_extension.ResourceHandler.IconSize;
import de.kugihan.dictionaryformids.hmi_java_me.uidisplaytext.LanguageUI;
import edu.hws.eck.mdb.I18n;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.util.jar.*;
import java.util.zip.ZipEntry;

public class JarCreator {
    
public static final String EXTENSION_JAR = ".jar";
public static final String EXTENSION_JAD = ".jad";
public static final String FILE_EMPTY_JAR_NAME = DictionaryDataFile.applicationFileNamePrefix + EXTENSION_JAR;
public static final String FILE_EMPTY_JAD_NAME = DictionaryDataFile.applicationFileNamePrefix + EXTENSION_JAD;

private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

private static String dictionarydirectory; 
private static String emptydictionaryformids;
private static String outputdirectory;

private static File JARFILE;
public static File getJarFile(){
    return JARFILE;
}

private static File JADFILE;
public static File getJadFile(){
    return JADFILE;
}


// Setter and Getter Methods.
public static void setDictionaryDirectory(String newDictionaryDirectory){
    dictionarydirectory = newDictionaryDirectory;
}

public static void setEmptyDictionaryForMID(String newEmptyDictionaryForMIDs){
    emptydictionaryformids = newEmptyDictionaryForMIDs;
}

public static void setOutputDirectory(String newOutputDirectory){
    outputdirectory = newOutputDirectory;
}

public static String getDictionaryDirectory(){
    return dictionarydirectory;
}

public static String getEmptyDictionaryForMID(){
    return emptydictionaryformids;
}

public static String getOutputDirectory(){
    return outputdirectory;
}

    // window showing jar creation preferencies
    // summarry before the actual jar creation.
    public static void showJarCreationSum(){
        JarCreationSummary cjpw = JarCreationSummary.getCJPWin();
        //cjpw.setSize(298, 328);
        cjpw.setLocation(screenSize.width / 2 - cjpw.getWidth() / 2,
                          screenSize.height / 2 - cjpw.getHeight() / 2);
        cjpw.setModal(true);
        cjpw.setVisible(true);
    }

	public static void createJar() throws FileNotFoundException,
                                              DfMCreatorException.CantCreatOutputJarJadDirectory,
                                              IOException, DictionaryException {

            	UtilWin utilObj = new UtilWin();
		Util.setUtil(utilObj);

		String applicationUniqueIdentifier = buildApplicationUniqueIdentifier(dictionarydirectory);
		String midletName = "DfM" + applicationUniqueIdentifier; 
		String midletNameShort = "DfM" + applicationUniqueIdentifier; 
		// restrict midletName to a maximum of 32 characters, because Motorola phones cannot handle more
		int maxMidletNameLength = 64;
		if (midletName.length() > maxMidletNameLength) 
			midletName = midletName.substring(0, maxMidletNameLength); 
		String fileNameOutputJar = outputdirectory + midletName + DfMCreatorMain.PATH_SEPARATOR + midletName + EXTENSION_JAR;
		String fileNameOutputJad = outputdirectory + midletName + DfMCreatorMain.PATH_SEPARATOR + midletName + EXTENSION_JAD;
                // Creating a unique directory that will hold the output jar and jad
                File jarAndJadDir = new File (outputdirectory + midletName);
                if (!jarAndJadDir.exists()){
                    if (!jarAndJadDir.mkdirs()){
                        throw new DfMCreatorException.CantCreatOutputJarJadDirectory(I18n.tr("outputDirCreationError", new Object[] {midletName}));
                    }
                }

		// open property file 
		String propertyPath = dictionarydirectory;
		/* read properties */
		if (! utilObj.readProperties(propertyPath, false)) {
			System.err.println(I18n.tr("propFileAccessError", new Object[] {utilObj.buildPropertyFileName(propertyPath)}));  
			System.exit(1);
		}

		File dictDir = new File(dictionarydirectory);
		String fileNameEmptyJar = emptydictionaryformids + FILE_EMPTY_JAR_NAME;
		JarInputStream emptyJar = new JarInputStream(new FileInputStream(new File(fileNameEmptyJar))); //would be better: in resources of JAR file of JARCreator -- new JarInputStream(getClass().getResourceAsStream(emptyJAR));	 
		File jarOutputFile = new File(fileNameOutputJar);
		long jarSize=writeJAR(midletName, midletNameShort, dictDir, emptyJar, jarOutputFile);
		System.out.println(I18n.tr("writtenJar", new Object[] {fileNameOutputJar}));
		
		File jadInputFile=new File(emptydictionaryformids+FILE_EMPTY_JAD_NAME);
		File jadOutputFile=new File(fileNameOutputJad);
		writeJAD(jarSize, midletNameShort, midletName, jadInputFile, jadOutputFile);
		System.out.println(I18n.tr("writtenJad", new Object[] {fileNameOutputJar}));

		System.out.println(I18n.tr("finalMsg"));
	}
	
	static void writeJAD(long jarSize, String midletNameShort,String midletName, File jadInputFile, File jadOutputFile) throws IOException {
	  BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(jadInputFile)));
	  BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jadOutputFile)));

	  boolean sizeSuccessful=false;
	  boolean midletNameSuccessful=false;
	  boolean midlet1Successful=false;
	  String line = null;
	  while((line = in.readLine()) != null){
        	boolean writeNewLine = true;
		  if (line.startsWith("MIDlet-Jar-Size")){
			  out.write("MIDlet-Jar-Size: "+jarSize);
			  sizeSuccessful=true;
		  }
		  else if (line.startsWith("MIDlet-Name")){
			  out.write("MIDlet-Name: "+midletNameShort);
			  midletNameSuccessful=true;
		  }
		  else if (line.startsWith("MIDlet-1")){
			  out.write("MIDlet-1: " + buildMidlet1Name(midletName));
			  midlet1Successful=true;
		  }
		  else if (line.startsWith("MIDlet-Jar-URL")){
			  out.write("MIDlet-Jar-URL: "+midletName + EXTENSION_JAR);  // complete URL is not provided
		  }
        	else if (line.indexOf("javax.microedition.io.Connector.file.read") != -1){
        		// just do nothing: the permission for reading from connectors via JSR-75 is not needed for
        		// a JAR-file that is created by JarCreator
        		writeNewLine = false;
        	}
		  else{
			  out.write(line);
		  }
        	if (writeNewLine)
        		out.newLine();
	  }
	  in.close();
	  out.close();
          // this will be used to retrieve the filename
          // of the jad file in case we want to delete it
          JADFILE = jadOutputFile;
		  
		  if (!sizeSuccessful){
			  throw new RuntimeException(I18n.tr("runtimeException3"));
		  }
		  else if (!midletNameSuccessful){
			  throw new RuntimeException(I18n.tr("runtimeException2"));
		  } 
		  else if (!midlet1Successful){
			  throw new RuntimeException(I18n.tr("runtimeException1"));
		  } 
	}
	
	static long writeJAR(String midletName, String midletNameShort, File dictDir, JarInputStream in, File jarOutputFile) throws IOException{//returns the file size of the JAR file
		Manifest manifest = new Manifest(in.getManifest());
		Attributes manifestAttributes = manifest.getMainAttributes();
		manifestAttributes.putValue("MIDlet-Name", midletNameShort);
		manifestAttributes.putValue("MIDlet-1", buildMidlet1Name(midletName));
		
		JarOutputStream out=new JarOutputStream(new FileOutputStream(jarOutputFile), manifest);
		
		byte[] b = new byte[3000];
		int readBytes;
		JarEntry nextOne;
		while((nextOne=in.getNextJarEntry())!=null){
			boolean includeEntry = true;
			/* do not include entries that are on the exclude list */
			String[] excludeEntries = { 
										"de/kugihan/dictionaryformids/dataaccess/zip"  // zip library for decompression of dictionaries in the file system
									  }; 
			for (int i = 0; i < excludeEntries.length; ++i) {
				if (nextOne.getName().startsWith(excludeEntries[i])) {
					includeEntry = false;
					break;
				}
			}
			/* do not include language icons that are not needed (would be waste of space) */
			if (isLanguageIconFileNotNeeded(nextOne.getName()))
				includeEntry = false;
			if (includeEntry) {
				out.putNextEntry(nextOne);
				while(( readBytes= in.read(b,0,3000)) != -1) 
		        { 
		            out.write(b, 0, readBytes); 
		        }
			}
		}
		in.close();
                
                // this will be used to retrieve the filename
                // of the jar file in case we want to delete it
                JARFILE = jarOutputFile;

		addDirectory(out, dictDir, "");
		
		out.close();
                
		return jarOutputFile.length();
	}
	
	static void addDirectory(JarOutputStream out, File dictDir, String subDir) throws IOException {
		byte[] b = new byte[3000];
		int readBytes;
		File[] dictFiles= dictDir.listFiles();
		for(int i=0; i<dictFiles.length; i++) 
		{ 
			if (!dictFiles[i].isDirectory()){
			      FileInputStream fis = new FileInputStream(dictFiles[i]); 						
							out.putNextEntry(new ZipEntry(DictionaryDataFile.pathNameDataFiles + "/"+subDir +dictFiles[i].getName())); 
							while((readBytes = fis.read(b)) != -1) { 
									out.write(b, 0, readBytes); 
							}						
			      fis.close(); 
			}
			else {
				File subDirFile = dictFiles[i];
				String newSubDir = subDir + subDirFile.getName() + '/' ;
				addDirectory(out, subDirFile, newSubDir);
			}
		}
	}
	
	// adds a directory separator character to pathName if the pathName does have one at the end 
	static String getPathName(String pathName) {
		String completePathName = pathName;
		if (! completePathName.endsWith(File.separator)) {
			completePathName = completePathName + File.separator; 
		}
		return completePathName;
	}

	// create suffix for unique application name from the language postfixes plus dictionary abbreviation
	static String buildApplicationUniqueIdentifier(String propertyPath) throws DictionaryException {
		UtilWin utilObj = new UtilWin();
		Util.setUtil(utilObj);
		String applicationUniqueIdentifier = new String("_");
		if (! utilObj.readProperties(propertyPath, false)) {
			System.err.println(I18n.tr("propertyFileNotAccessible", new Object[] {utilObj.buildPropertyFileName(propertyPath)}));
			System.exit(1);
		}
		for (int indexLanguage = 0;
		     indexLanguage < DictionaryDataFile.numberOfAvailableLanguages;
		     ++indexLanguage) {
			applicationUniqueIdentifier = applicationUniqueIdentifier + 
										  DictionaryDataFile.supportedLanguages[indexLanguage].languageFilePostfix;
		}
		if (DictionaryDataFile.dictionaryAbbreviation != null) {
			applicationUniqueIdentifier = applicationUniqueIdentifier + "_" + 
										  DictionaryDataFile.dictionaryAbbreviation;
		}
		else {
			System.err.println(I18n.tr("dictAbbrevError"));  
		}
		return applicationUniqueIdentifier;
	}
	
	static String buildMidlet1Name(String midletName) {
		return midletName + ", /icons/Application/DictionaryForMIDs.png, de.kugihan.dictionaryformids.hmi_java_me.DictionaryForMIDs";		
	}
	
	static boolean isLanguageIconFileNotNeeded(String fileName) {
		// is the file name belonging to a language icon file ?
		boolean fileIsLanguageIconFile = false;
		boolean fileIsNeeded = true;
		// loop over all icon sizes
		int iconSizeCount = 0;
		ResourceHandler resourceHandlerObj = ResourceHandler.getResourceHandlerObj();
		IconSize[] iconSizes = resourceHandlerObj.iconSizes;
		boolean searchDone = false;
		while ((iconSizeCount < iconSizes.length) && (! searchDone)) {
			IconSize iconSize = iconSizes[iconSizeCount]; 
			for (int sizesInPixelCount = 0; 
			     sizesInPixelCount < iconSize.sizesInPixel.length;
			     ++sizesInPixelCount) {
				int iconSizeInPixel = iconSize.sizesInPixel[sizesInPixelCount];
				String languagePrefix = resourceHandlerObj.buildIconPathName(iconSize.iconArea,
					                                                         iconSize.iconSizeGroup,
					                                                         iconSizeInPixel) +
	                                    ResourceHandler.pathSeparator + LanguageUI.getUI().uiDisplayTextItemPrefixLanguage;
				if (fileName.startsWith(languagePrefix)) {
					fileIsLanguageIconFile = true;
					fileIsNeeded = false;
				}
				if (fileIsLanguageIconFile) {
					for (int language = 0; language < DictionaryDataFile.numberOfAvailableLanguages; ++language) {
						String languageDisplayText = DictionaryDataFile.supportedLanguages[language].languageDisplayText;
						String languageIconLocation = 
							resourceHandlerObj.getResourceLocation(
									resourceHandlerObj.buildIconPathName(iconSize.iconArea,
																		 iconSize.iconSizeGroup,
																		 iconSizeInPixel),
						          	resourceHandlerObj.buildIconFileName(LanguageUI.getUI().uiDisplayTextItemPrefixLanguage + 
						          			                             languageDisplayText));
						languageIconLocation = languageIconLocation.substring(1); // remove starting / -character
						if (languageIconLocation.equalsIgnoreCase(fileName)) {
							fileIsNeeded = true;
							searchDone = true;
							break;
						}
					}
					if (searchDone)
						break;
				}
			}
			++iconSizeCount;
		}
		return ! fileIsNeeded;
	}

}
