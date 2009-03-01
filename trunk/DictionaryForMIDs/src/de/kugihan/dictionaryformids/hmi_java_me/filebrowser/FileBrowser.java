/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2007 Zz85
Modified by Gert Nuber

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.hmi_java_me.filebrowser;
import java.io.IOException;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;


/**
 * Modified from
 * Demonstration MIDlet for File Connection API. This MIDlet implements simple
 * file browser for the filesystem available to the J2ME applications.
 *
 */
public class FileBrowser extends List implements CommandListener {
	/**	Indicates that the user cancelled the selection process. */
	public final static int CANCELLED = 0;

	/**	Indicates that the user selected a directory. The user's
	 *	selection can be retrieved with <code>getFileConnection</code>.
	 *	@see getFileConnection() */
	public final static int ACCEPTED = 1;
	
	/* special string denotes upper directory */
	private static final String CURRENT_DIRECTORY = ".";
	private static final String UP_DIRECTORY = "..";

    /* special string that denotes upper directory accessible by this browser.
     * this virtual directory contains all roots.
     */
    private static final String MEGA_ROOT = "/";

    /* separator string as defined by FC specification */
    private static final String SEP_STR = "/";

    /* separator character as defined by FC specification */
    private static final char SEP = '/';
    private String currDirName;
    
    // TO change to DFM Commands - Zz85
    private Command view = new Command("Select", Command.OK, 1);
    private Command back = new Command("Back",   Command.BACK, 2);
    private Command exit = new Command("Cancel", Command.EXIT, 3);

    // TO change to DFM Icons?
    private Image dirIcon;
    private Image fileIcon;
    
    DirectoryChooserListener dcl;
	Display display;
	
    /**	Creates and initializes an instance of this class. */
    public FileBrowser(DirectoryChooserListener dcl, Display display)
	{
		super(null, IMPLICIT); 
		this.dcl = dcl;
		this.display = display;
		
		currDirName = MEGA_ROOT;

        try {
            dirIcon = Image.createImage("/icons/Browser/dir.png");
        } catch (IOException e) {
            dirIcon = null;
        }

        try {
            fileIcon = Image.createImage("/icons/Browser/file.png");
        } catch (IOException e) {
            fileIcon = null;
        }
        
        addCommand(view);
        addCommand(back);
        addCommand(exit);
        setCommandListener(this);
        
        startBrowser();
	}
	
    public void startBrowser() {

        try {
            new Thread(new Runnable() { // To avoid deadlock
                public void run() {
                	showCurrDir();
                }
            }).start();
        } catch (SecurityException e) {
        	// Display Warning or Throw Exception ? - Zz85

        	
        	Alert alert =
                new Alert("Error", "You do not permissions access files with the Dictionary Selector", null,
                    AlertType.ERROR);
            alert.setTimeout(Alert.FOREVER);
            display.setCurrent(alert, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void commandAction(Command c, Displayable d) {
        if (c == view) { // Either Traverse or Select
            final String currFile = getString(getSelectedIndex());
            new Thread(new Runnable() { // To avoid deadlock
                    public void run() {
                        if (currFile.endsWith(SEP_STR) || currFile.equals(UP_DIRECTORY)) {
                            traverseDirectory(currFile);
                        } else {
                            // Show file contents
                        	selectFile(currFile);
                        }
                    }
                }).start();
        } else if (c == back) {
            showCurrDir();
        } else if (c == exit) {
        	dcl.dirSelected(ACCEPTED, null);
        }
    }

    /**
     * Show file list in the current directory .
     */
    void showCurrDir() {
        Enumeration e;
        FileConnection currDir = null;

        try {
            setTitle(currDirName);
            deleteAll();
            if (MEGA_ROOT.equals(currDirName)) {
                e = FileSystemRegistry.listRoots();
            } else {
                currDir = (FileConnection)Connector.open("file://localhost/" + currDirName, Connector.READ);
                e = currDir.list();
                // not root - draw UP_DIRECTORY
                append(UP_DIRECTORY, dirIcon);
                append(CURRENT_DIRECTORY, dirIcon); 
            }

            while (e.hasMoreElements()) {
                String fileName = (String)e.nextElement();

                if (fileName.charAt(fileName.length() - 1) == SEP) {
                    // This is directory
                    append(fileName, dirIcon);
                } else {
                    // this is regular file
                    append(fileName, fileIcon);
                }
            }

            if (currDir != null) {
                currDir.close();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void traverseDirectory(String fileName) {
        /* In case of directory just change the current directory
         * and show it
         */
        if (currDirName.equals(MEGA_ROOT)) {
            if (fileName.equals(UP_DIRECTORY)) {
                // can not go up from MEGA_ROOT
                return;
            }

            currDirName = fileName;
        } else if (fileName.equals(UP_DIRECTORY)) {
            // Go up one directory
            int i = currDirName.lastIndexOf(SEP, currDirName.length() - 2);

            if (i != -1) {
                currDirName = currDirName.substring(0, i + 1);
            } else {
                currDirName = MEGA_ROOT;
            }
        } else {
            currDirName = currDirName + fileName;
        }

        showCurrDir();
    }

    void selectFile(String fileName) {
    	if (fileName.endsWith(CURRENT_DIRECTORY)) {
    		dcl.dirSelected(ACCEPTED, "file:///" + currDirName.substring(0,currDirName.length()-1));
    	}
    	else {
    		dcl.dirSelected(ACCEPTED, "file:///" + currDirName + fileName);
    	}
    
    }
}
