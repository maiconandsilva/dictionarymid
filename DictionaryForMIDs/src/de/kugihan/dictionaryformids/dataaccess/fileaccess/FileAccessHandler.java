package de.kugihan.dictionaryformids.dataaccess.fileaccess;

public class FileAccessHandler {
	protected static DfMInputStreamAccess dictionaryDataFileISAccess;

	public static DfMInputStreamAccess getDictionaryDataFileISAccess() {
		return dictionaryDataFileISAccess;
	}

	public static void setDictionaryDataFileISAccess(
			DfMInputStreamAccess dictionaryDataFileISAccessParam) {
		dictionaryDataFileISAccess = dictionaryDataFileISAccessParam;
	}
}
