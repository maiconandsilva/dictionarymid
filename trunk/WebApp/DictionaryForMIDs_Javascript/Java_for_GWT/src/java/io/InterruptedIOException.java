/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.

This class provides a standard Java class for GWT
*/
package java.io;

public class InterruptedIOException extends IOException
{
  private static final long serialVersionUID = 4020568460727500567L;

  public InterruptedIOException()
  {
  }

  public InterruptedIOException(String message)
  {
    super(message);
  }

} 
