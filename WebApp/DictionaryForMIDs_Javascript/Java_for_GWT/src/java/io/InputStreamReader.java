/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.

This class provides a standard Java class for GWT
*/

/*
	Note: the methods of this class contain just some test code.
	The code of class InputStreamReader is never called by the htmlApp.
	But the class interface needs to be there in order to compile the
	sources.
*/

package java.io;

import de.kugihan.dictionaryformids.general.Util;

public class InputStreamReader 
{

	protected int maxSizeOfFile = 1000000;
	protected String inputStreamContentAsString; 
	protected int pos = 0;

	public InputStreamReader(InputStream in, String encoding_name)
			throws UnsupportedEncodingException, IOException
	{
		byte[] fileData = new byte[maxSizeOfFile];
		int sizeOfFile = 0;

		int character;
		do {
			character = in.read();
			if (character != -1) {
				fileData[sizeOfFile] = (byte) character;
				++sizeOfFile;
			}
		}
		while (character != -1);
		inputStreamContentAsString = new String(fileData , 0, sizeOfFile, encoding_name);
	}

	public int read() throws IOException
	{	
		int character;
		if (pos < inputStreamContentAsString.length()) {
			character = inputStreamContentAsString.charAt(pos);
			++pos;
		}
		else {
			character = -1;
		}
		return character;
	}

}
