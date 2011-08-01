/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2011 Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.

This class provides a standard Java class for GWT
*/
package java.io;
import de.kugihan.dictionaryformids.general.Util;

public abstract class InputStream 
{

	public void close() throws IOException
	{
		// Do nothing
	}

	public abstract int read() throws IOException;

	public int read(byte[] b) throws IOException
	{
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, int len) throws IOException
	{
		if (off < 0 || len < 0 || b.length - off < len)
			throw new IndexOutOfBoundsException();

		int i, ch;

		for (i = 0; i < len; ++i)
		  try
			{
			  if ((ch = read()) < 0)
				return i == 0 ? -1 : i;             // EOF
			  b[off + i] = (byte) ch;
			}
		  catch (IOException ex)
			{
			  // Only reading the first byte should cause an IOException.
			  if (i == 0)
				throw ex;
			  return i;
			}

		return i;
	}

	public void reset() throws IOException {
		Util.getUtil().log("js reset not supported");
		throw new IOException("js reset not supported");
	}

	public long skip(long n) throws IOException
	{
		final int buflen = n > 2048 ? 2048 : (int) n;
		byte[] tmpbuf = new byte[buflen];
		final long origN = n;

		while (n > 0L)
		  {
			int numread = read(tmpbuf, 0, n > buflen ? buflen : (int) n);
			if (numread <= 0)
			  break;
			n -= numread;
		  }

		return origN - n;
	}
}
