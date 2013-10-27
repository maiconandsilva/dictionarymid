/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.

This class provides a standard Java class for GWT
*/
package java.lang;

// this is just a stub for GWT, so that the Java code compiles
public class Runtime {
	static Runtime runtimeSingleton = new Runtime();
	
	static public Runtime getRuntime() {
		return runtimeSingleton;
	}
	
	public long freeMemory() {
		return 0; // not implemented
	}
}
