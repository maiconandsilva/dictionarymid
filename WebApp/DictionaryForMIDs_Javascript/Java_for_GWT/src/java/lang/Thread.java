/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2013 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.

This class provides a standard Java class for GWT
*/
package java.lang;

import java.lang.Runnable;

public class Thread {

  public static int NORM_PRIORITY = 0;

  Runnable target;
  public Thread() {
  }
  
  public Thread(Runnable targetParam) {
  }

  public void setPriority(int priority) {
	}

  public void interrupt() {
  }
  
  public void start() {
	target.run();
  }

}