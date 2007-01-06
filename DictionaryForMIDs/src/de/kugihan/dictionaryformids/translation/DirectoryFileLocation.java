package de.kugihan.dictionaryformids.translation;

public class DirectoryFileLocation {

	public int    directoryFileNumber;
	public String postfixDictionaryFile;
	public int    positionInDirectoryFile;
	
	DirectoryFileLocation(int    directoryFileNumberParam,
						  String postfixDictionaryFileParam,
						  int    positionInDirectoryFileParam) {
		directoryFileNumber = directoryFileNumberParam; 
		postfixDictionaryFile = postfixDictionaryFileParam;
		positionInDirectoryFile = positionInDirectoryFileParam;
	}
	
	int compareTo(DirectoryFileLocation otherDirectoryFileLocation) {
		if ((directoryFileNumber == otherDirectoryFileLocation.directoryFileNumber) &&
			(positionInDirectoryFile == otherDirectoryFileLocation.positionInDirectoryFile) &&
			(postfixDictionaryFile.compareTo(otherDirectoryFileLocation.postfixDictionaryFile) == 0))
			return 0;
		else
			return 1;
		// optimization: also handle sorting to allow for more efficient filtering
	}
}
