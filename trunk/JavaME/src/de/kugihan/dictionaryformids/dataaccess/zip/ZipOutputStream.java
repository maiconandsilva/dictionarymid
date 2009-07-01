/* net.sf.jazzlib.ZipOutputStream
   Copyright (C) 2001 Free Software Foundation, Inc.

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */


package de.kugihan.dictionaryformids.dataaccess.zip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

/**
 * This is a FilterOutputStream that writes the files into a zip
 * archive one after another.  It has a special method to start a new
 * zip entry.  The zip entries contains information about the file name
 * size, compressed size, CRC, etc.
 *
 * It includes support for STORED and DEFLATED entries.
 *
 * This class is not thread safe.
 *
 * @author Jochen Hoenicke 
 */
public class ZipOutputStream extends DeflaterOutputStream implements ZipConstants
{
  private Vector entries = new Vector();
  private CRC32 crc = new CRC32();
  private ZipEntry curEntry = null;

  private int curMethod;
  private int size;
  private int offset = 0;

  private byte[] zipComment = new byte[0];
  private int defaultMethod = DEFLATED;

  /**
   * Our Zip version is hard coded to 1.0 resp. 2.0
   */
  private final static int ZIP_STORED_VERSION   = 10;
  private final static int ZIP_DEFLATED_VERSION = 20;

  /**
   * Compression method.  This method doesn't compress at all.
   */
  public final static int STORED      =  0;
  /**
   * Compression method.  This method uses the Deflater.
   */
  public final static int DEFLATED    =  8;

  /**
   * Creates a new Zip output stream, writing a zip archive.
   * @param out the output stream to which the zip archive is written.
   */
  public ZipOutputStream(OutputStream out)
  {
    super(out, new Deflater(Deflater.DEFAULT_COMPRESSION, true));
  }

  /**
   * Set the zip file comment.
   * @param comment the comment.
   * @exception IllegalArgumentException if encoding of comment is
   * longer than 0xffff bytes.
   */
  public void setComment(String comment)
  {
    byte[] commentBytes;
    commentBytes = comment.getBytes();
    if (commentBytes.length > 0xffff)
      throw new IllegalArgumentException("Comment too long.");
    zipComment = commentBytes;
  }
  
  /**
   * Sets default compression method.  If the Zip entry specifies
   * another method its method takes precedence.
   * @param method the method.
   * @exception IllegalArgumentException if method is not supported.
   * @see #STORED
   * @see #DEFLATED
   */
  public void setMethod(int method)
  {
    if (method != STORED && method != DEFLATED)
      throw new IllegalArgumentException("Method not supported.");
    defaultMethod = method;
  }

  /**
   * Sets default compression level.  The new level will be activated
   * immediately.  
   * @exception IllegalArgumentException if level is not supported.
   * @see Deflater
   */
  public void setLevel(int level)
  {
    def.setLevel(level);
  }
  
  /**
   * Write an unsigned short in little endian byte order.
   */
  private final void writeLeShort(int value) throws IOException 
  {
    out.write(value & 0xff);
    out.write((value >> 8) & 0xff);
  }

  /**
   * Write an int in little endian byte order.
   */
  private final void writeLeInt(int value) throws IOException 
  {
    writeLeShort(value);
    writeLeShort(value >> 16);
  }

  /**
   * Writes the given buffer to the current entry.
   * @exception IOException if an I/O error occured.
   * @exception ZipException if no entry is active.
   */
  public void write(byte[] b, int off, int len) throws IOException
  {
    if (curEntry == null)
      throw new ZipException("No open entry.");

    switch (curMethod)
      {
      case DEFLATED:
	super.write(b, off, len);
	break;
	
      case STORED:
	out.write(b, off, len);
	break;
      }

    crc.update(b, off, len);
    size += len;
  }
}
