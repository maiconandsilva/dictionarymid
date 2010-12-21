/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.

This class provides a standard Java class for GWT
*/
package java.io;

public class IOException extends Exception
{
  private static final long serialVersionUID = 7818375828146090155L;

  /**
   * Create an exception without a descriptive error message.
   */
  public IOException()
  {
  }


  public IOException(String message)
  {
    super(message);
  }
} 
