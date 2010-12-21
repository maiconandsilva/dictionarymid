/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005 - 2011 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/

package de.kugihan.dictionaryformids.dataaccess;

import de.kugihan.dictionaryformids.dataaccess.fileaccess.HTRInputStream;
import de.kugihan.dictionaryformids.general.DictionaryException;
import java.io.IOException;
import java.io.InputStream;

public class HTRFileStorageReader 
		extends DefaultFileStorageReader {
		
	GetCharacterIF getUTF8CharacterObj 		= new GetUTF8Character();
	GetCharacterIF getISO88591CharacterObj	= new GetISO88591Character();
		
	public FileStorage readFileToFileStorage(String  fileName,
										     String  charEncoding,
				       	                     int     maxSizeOfFileData) 
	 						throws DictionaryException {
		String fileDataString = HTRInputStream.readFile(fileName,
														charEncoding);
		FileStorage fileStorageObj = new StringFileStorage(fileDataString);
		return fileStorageObj;
	}
	
	public FileStorage readCsvFileLine(String fileName,
									   String charEncoding,
									   int startPosition) 
						throws DictionaryException {
		FileStorage fileStorageObj;
		if (charEncoding.equalsIgnoreCase("UTF-8")) {
			fileStorageObj = readHTRFileLine(fileName,
			                                 startPosition,
										     getUTF8CharacterObj);
		}
		else if (charEncoding.equalsIgnoreCase("ISO-8859-1")) {
			fileStorageObj = readHTRFileLine(fileName,
			                                 startPosition,
										     getISO88591CharacterObj);
		}
		else {
			fileStorageObj = super.readCsvFileLine(fileName,
								                   charEncoding,
								                   startPosition);
		}
		return fileStorageObj;
	}

	protected FileStorage readHTRFileLine(String 			fileName,
			                              int 				startPosition,
										  GetCharacterIF    getCharacterObj) 
						throws DictionaryException {
		StringBuffer htrLineStringBuffer = new StringBuffer();
		try {
			InputStream htrInputStream = new HTRInputStream(fileName);
			htrInputStream.skip(startPosition);
			int charFromHTRStream;
			boolean endOfLineReached = false;
			do {
				charFromHTRStream = getCharacterObj.getCharacter(htrInputStream);
				if ((charFromHTRStream != '\n') && (charFromHTRStream != -1)) {
					htrLineStringBuffer.append((char) charFromHTRStream);
				}
				else {
					endOfLineReached = true;
				}
			} 
			while (! endOfLineReached);
		}
		catch (IOException e) {
			throw new DictionaryException(e);
		}
		FileStorage fileStorageObj = new StringFileStorage(htrLineStringBuffer);
		return fileStorageObj;
	}
}

interface GetCharacterIF {
	public int getCharacter(InputStream inputStream) 
			throws IOException ;
}


class GetISO88591Character 
			implements GetCharacterIF {
	public int getCharacter(InputStream inputStream) 
			throws IOException {
		int charFromInputStream = inputStream.read();
		return charFromInputStream;
	}
}

class GetUTF8Character
			implements GetCharacterIF {

	public int getCharacter(InputStream inputStream) 
			throws IOException {
		int count = 0;		
		int ch = inputStream.read();
		if (ch != -1) {
			if ((ch & 0x80) == 0) {
				count = 1;
				ch &= 127;
			} else if ((ch & 0xE0) == 0xC0) {
				count = 2;
				ch &= 31;
			} else if ((ch & 0xF0) == 0xE0) {
				count = 3;
				ch &= 15;
			} else if ((ch & 0xF8) == 0xF0) {
				count = 4;
				ch &= 7;
			} else if ((ch & 0xFC) == 0xF8) {
				count = 5;
				ch &= 3;
			}
			while (--count > 0) {
				int extCh = inputStream.read();
				if (ch == -1) {
					throw new IllegalArgumentException("Invalid UTF8 sequence (EOF reached)");
				}
				byte b = (byte) extCh;
				if ((b & 0xC0) != 0x80) {
					throw new IllegalArgumentException("Invalid UTF8 sequence ");
				}
				ch = (ch << 6) | (b & 63);
			}
		}
		return ch;
	}
}

/*
temporary for lookup: String code from GWT:

	private static String utf8ToString(byte[] bytes, int ofs, int len) {
    // TODO(jat): consider using decodeURIComponent(escape(bytes)) instead
    int charCount = 0;
    for (int i = 0; i < len; ) {
      ++charCount;
      byte ch = bytes[ofs + i];
      if ((ch & 0xC0) == 0x80) {
        throw new IllegalArgumentException("Invalid UTF8 sequence");
      } else if ((ch & 0x80) == 0) {
        ++i;
      } else if ((ch & 0xE0) == 0xC0) {
        i += 2;
      } else if ((ch & 0xF0) == 0xE0) {
        i += 3;
      } else if ((ch & 0xF8) == 0xF0) {
        i += 4;
      } else {
        // no 5+ byte sequences since max codepoint is less than 2^21
        throw new IllegalArgumentException("Invalid UTF8 sequence");
      }
      if (i > len) {
        throw new IndexOutOfBoundsException("Invalid UTF8 sequence");
      }
    }
    char[] chars = new char[charCount];
    int outIdx = 0;
    int count = 0;
    for (int i = 0; i < len; ) {
      int ch = bytes[ofs + i++];
      if ((ch & 0x80) == 0) {
        count = 1;
        ch &= 127;
      } else if ((ch & 0xE0) == 0xC0) {
        count = 2;
        ch &= 31;
      } else if ((ch & 0xF0) == 0xE0) {
        count = 3;
        ch &= 15;
      } else if ((ch & 0xF8) == 0xF0) {
        count = 4;
        ch &= 7;
      } else if ((ch & 0xFC) == 0xF8) {
        count = 5;
        ch &= 3;
      }
      while (--count > 0) {
        byte b = bytes[ofs + i++];
        if ((b & 0xC0) != 0x80) {
          throw new IllegalArgumentException("Invalid UTF8 sequence at "
              + (ofs + i - 1) + ", byte=" + Integer.toHexString(b));
        }
        ch = (ch << 6) | (b & 63);
      }
      outIdx += Character.toChars(ch, chars, outIdx);
    }
    return valueOf(chars);
*/