/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess;

import java.io.*;

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.DictionaryInterruptedException;
import de.kugihan.dictionaryformids.general.Util;

public class CsvFile {

	public static boolean selectedBypassCharsetDecoding = false;
	
	public boolean endOfDictionaryReached = false;	
	protected fileStorage fileStorageObj;
	protected int position = 0;
	protected int columnNumber = 0;
	protected char separatorCharacter;	
	protected String fileName;
	protected int maxSizeOfFileData;
	protected String  charEncoding;
	
	static CsvFileCache fileCache = new CsvFileCache();
	
	public CsvFile(String  fileNameParam,
	       	       char    separatorCharacterParam,
		       	   String  charEncodingParam,
		       	   int     maxSizeOfFileDataParam) throws DictionaryException {
		setParams(fileNameParam, separatorCharacterParam, charEncodingParam, maxSizeOfFileDataParam);
		readCsvFileComplete();
	}

	public CsvFile(String  fileNameParam,
    	           char    separatorCharacterParam,
		       	   String  charEncodingParam,
		       	   int     maxSizeOfFileDataParam,
		       	   int 	   startPositionParam) throws DictionaryException {
		setParams(fileNameParam, separatorCharacterParam, charEncodingParam, maxSizeOfFileDataParam);
		readCsvFileLine(startPositionParam);
	}

	private void setParams(String  fileNameParam,
				           char    separatorCharacterParam,
				           String  charEncodingParam,
				       	   int     maxSizeOfFileDataParam) {
		separatorCharacter = separatorCharacterParam;
		charEncoding       = charEncodingParam;
		maxSizeOfFileData  = maxSizeOfFileDataParam;
		fileName = fileNameParam;
	}
	
	public void readCsvFileComplete() 
	 						throws DictionaryException {
		byte[] fileData = new byte[maxSizeOfFileData];

		try {
			long startTime = System.currentTimeMillis();
			InputStream csvStream =	DfMInputStream.getDfMInputStream().getInputStream(fileName);  // done in derived class
			Util.getUtil().logTime("open file", startTime);
			startTime = System.currentTimeMillis();
			if (csvStream != null) {
				int sizeOfFile = 0;
				int bytesRead;
				do {
					bytesRead = csvStream.read(fileData, sizeOfFile, fileData.length - sizeOfFile);
					if (bytesRead != -1)
						sizeOfFile += bytesRead;
					if (sizeOfFile == fileData.length) {
						// buffer is full: see if there is still more data to be read
						int character = csvStream.read();
						if (character != -1) {
							// there is more data that had not been read
							Util.getUtil().log("Warning: buffer size too small for file " + fileName);
						}
						break;
					}
				}
				while (bytesRead != -1);
				csvStream.close();
				Util.getUtil().logTime("read file", startTime);
				startTime = System.currentTimeMillis();
				if (selectedBypassCharsetDecoding) {
					fileStorageObj = new byteFileStorage(fileData,
							                             sizeOfFile);					
				}
				else {
					fileStorageObj = new stringFileStorage(fileData,
							                               sizeOfFile,
														   charEncoding);
				}
				Util.getUtil().logTime("parse file", startTime);
			}
			else
			{
				throw new DictionaryException("Could not open file " + fileName);
			}
		}
		catch (InterruptedIOException e)
		{
			// Thread was interupted during IO operation
			throw new DictionaryInterruptedException(e);
		}
		catch (IOException e)
		{
			throw new DictionaryException(e);
		}
	}

	// special method for reading a CSV file starting at byte position 
	// startPosition and reading only one single line
	public void readCsvFileLine(int startPosition)
	 						throws DictionaryException {
		if (!selectedBypassCharsetDecoding) {
			try {
				InputStream csvStream = fileCache.getCsvFile(fileName, startPosition, this);
				long startTime = System.currentTimeMillis();
				int sizeOfFile = 0;
				StringBuffer csvLineString = new StringBuffer();
				InputStreamReader csvStreamReader = new InputStreamReader(csvStream, charEncoding);
				int character;
				boolean endOfLineReached = false;
				// read character by character till eiter newline or EOF is received:
				do {
					character = csvStreamReader.read();
					if ((character != '\n') && (character != -1)) {
						csvLineString.append((char) character);
						++sizeOfFile;
					}
					else {
						endOfLineReached = true;
					}
				} 
				while (! endOfLineReached);
				Util.getUtil().logTime("read/parse file-line", startTime);
				fileStorageObj = new stringFileStorage(csvLineString);
			}
			catch (InterruptedIOException e)
			{
				// Thread was interupted during IO operation
				throw new DictionaryInterruptedException(e);
			}
			catch (IOException e)
			{
				throw new DictionaryException(e);
			}			
		}
		else {
			// if BypassCharsetDecoding is selected, then it is faster to take the 'complete' CSV-Reader: 
			readCsvFileComplete();
			setPosition(startPosition);
		}
	}

	public void setPosition(int newPosition) {
		position = newPosition;
	}
	
	// Note: this method is declared as final only for the reason of better performance
	public final void skipRestOfLine() {
		int charactersInFile = fileStorageObj.getCharactersInFile();
		boolean endOfLineFound = false;

		if (position < charactersInFile) {
			do {
				char currentCharacter = fileStorageObj.readCharacterAt(position);
				if (position == charactersInFile -1) {
					endOfLineFound = true;
					endOfDictionaryReached = true;
				}
				else if (currentCharacter == '\n') {
					columnNumber = 0;
					endOfLineFound = true;
					++position;
				} else {
					++position;
				}
			}
			while (!endOfLineFound);
		}
	}
	
	// Note: this method is declared as final only for the reason of better performance
	public final StringBuffer getWord() {
		int charactersInFile = fileStorageObj.getCharactersInFile();
		StringBuffer word = new StringBuffer();  // check performance impact of creating a new StringBuffer-object 
												 // for each call to getWord 
		boolean endOfWordFound = false;
		word.setLength(0); 
		if (position < charactersInFile) {
			do {
				char currentCharacter = fileStorageObj.readCharacterAt(position);
				if (currentCharacter == '\n') {
					columnNumber = 0;
					endOfWordFound = true;
					++position;
				} else if (currentCharacter == separatorCharacter) {
					++columnNumber;
					endOfWordFound = true;
					++position;
				} else {
					// add character if not carriage return
					if (currentCharacter != '\r') {
						word.append(currentCharacter);
					}
					++position;
				}
				if (position == charactersInFile) {
					endOfWordFound = true;
					endOfDictionaryReached = true;
				}
			}
			while (! endOfWordFound);
		}
		return word;
	}

	// Searches for a position before the occurence of searchCriteria (searchCriteria is 
	// searched in in the first column).
	// The position may be exactly at the first occurence of searchCriteria or before
	// The values in the first column must be sorted and unique (no diplicates allowed) 
	public void setPositionBefore(String searchCriteria) {
		int charactersInFile = fileStorageObj.getCharactersInFile();
		int lastPositionBefore = 0;  // beginning of file
		int currentPosition = charactersInFile / 2;  // divide by 2: middle of file
		int lastPositionAfter = charactersInFile - 1 ;  // end of file
		
		int numberOfLoops = 0;
		do {
			++numberOfLoops;

			// search for beginning of next line
			while ((currentPosition < charactersInFile) &&
				   (fileStorageObj.readCharacterAt(currentPosition) != '\n')) {
				++currentPosition;
			}
			++currentPosition;
			if (currentPosition >= charactersInFile) {
				// end of file reached, take last position
				break;
			}
			// read next word
			position = currentPosition;
			StringBuffer word = getWord();
			int comparison = searchCriteria.compareTo(word.toString());
			if (comparison < 0) {
				if (currentPosition == lastPositionAfter) {
					// same hit as before
					break;
				}
				else {
					// search before
					lastPositionAfter = currentPosition;
					currentPosition = currentPosition - ((currentPosition - lastPositionBefore) / 2);
				}
			}
			else if (comparison > 0) {
				if (currentPosition == lastPositionAfter) {
					// same hit as before
					break;
				}
				else {
					// search after
					lastPositionBefore = currentPosition;
					currentPosition = currentPosition + ((lastPositionAfter - currentPosition) / 2);
				}
			}
			else if (comparison == 0) {
				// direct hit 
				lastPositionBefore = currentPosition;
				break;
			}
		}
		while (true);
		position = lastPositionBefore;
		Util.getUtil().log("pos before: " + position, Util.logLevelMax);
	}

	// to be analyzed: readCharacterAt is called very frequently; calling this method through
	// and interface may impact performance significantly; to improve performance possibly the
	// byteFileStorage should be treated separately.
	public interface fileStorage {
		int  getCharactersInFile();
		char readCharacterAt(int pos);
	}
	
	public class byteFileStorage
	 	implements fileStorage {

		int charactersInFile;
		byte[] byteStorage;

		public byteFileStorage(byte[] content,
				               int sizeOfFile) {
			byteStorage = content;
			charactersInFile = sizeOfFile;
		}
		
		public int getCharactersInFile() {
			return charactersInFile;
		}
		
		public char readCharacterAt(int pos) {
			return (char) byteStorage[pos];
		}
	}

	public class stringFileStorage
		implements fileStorage {

		int charactersInFile;
		public String stringStorage;

		public  stringFileStorage(StringBuffer content)
					throws IOException {
			stringStorage = content.toString();
			charactersInFile = stringStorage.length(); 
		}
		
		public  stringFileStorage(byte[] content,
                	              int sizeOfFile,
                	              String charEncoding)
					throws IOException {
			stringStorage = new String(content, 0, sizeOfFile, charEncoding);
			charactersInFile = stringStorage.length(); 
		}
	
		public int  getCharactersInFile() {
			return charactersInFile;
		}
		
		public char readCharacterAt(int pos) {
			return stringStorage.charAt(pos);
		}
	} 
}


class CsvFileCache {
	// This is a sketch for simple caching where only the last read file is kept 
	// in the cache. This is good enough for optimizing access to the directorynnn.csv-files.
	// Should be extended for further caching, specifically for the searchfiles.
	// This implementation is not yet complete: currently the file is re-opened
	// each time 
	// This cache is not yet active !
	protected InputStream cachedFile = null;
	protected String      fileName = null;
	protected int         lastPositionInStream;

	synchronized InputStream getCsvFile(String  fileNameParam,
            					        int 	startPosition,
            					        CsvFile csvFileObj) // last parameter obsolete when reading of files is separated 
				throws IOException, DictionaryException {
		long startTime = System.currentTimeMillis();
		InputStream csvStream = null;
		// check if file is in the cache
		if ((cachedFile != null) && fileName.equals(fileNameParam)) {
			System.out.println("cache hit " + fileNameParam);  // to be removed for final implementation
			// skip additional bytes
			int numberOfBytesToBeSkipped = startPosition - lastPositionInStream;
			if (numberOfBytesToBeSkipped < 0) {
				// stream needs to be reopened
				DfMInputStream.getDfMInputStream().getInputStream(fileNameParam);
				numberOfBytesToBeSkipped = startPosition;
			}
			else {
				// reset to the old file position
				cachedFile.reset();
			}
			long skippedBytes = cachedFile.skip(numberOfBytesToBeSkipped);
			if (skippedBytes != numberOfBytesToBeSkipped) {
				throw new DictionaryException("CSV file: skipped only " + skippedBytes + " bytes");
			}
			lastPositionInStream = startPosition;
			csvStream = cachedFile;
		}
		else {
			// remember the file name and position into the cache
			fileName = fileNameParam;
			lastPositionInStream = startPosition;
			// close the previously cached file
			if (cachedFile != null)
				cachedFile.close();
			// open new file 
			csvStream =	DfMInputStream.getDfMInputStream().getInputStream(fileName);  // to be done in specific class
			Util.getUtil().logTime("open file", startTime);
			startTime = System.currentTimeMillis();
			if (csvStream != null) {
				long skippedBytes = csvStream.skip(startPosition);
				Util.getUtil().logTime("position file", startTime);
				if (skippedBytes != startPosition) {
					throw new DictionaryException("CSV file: skipped only " + skippedBytes + " bytes");
				}
				// put file into cache, but only when the stream supports marks
				// currently: don't put into cache (= cache deactivated) 
				// if (csvStream.markSupported()) {
				//	csvStream.mark(20000);  // for the moment just assume 20000 bytes to remember
				//  cachedFile = csvStream;
				//}
			}
			else {
				throw new DictionaryException("Could not open file " + fileName);
			}
		}
		return csvStream;
	}
}
