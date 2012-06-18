/* ////////////////////////////////////////////////////////////////
* 
*   DICTIONARYFORMIDS-CREATOR
*   
*   This file is part of DictionaryForMIDs-Creator
*   Copyright (C) 2012 Karim Mahamane Karimou
*   DictionaryForMIDs-Creator is a GUI wrapper around various
*   DictionaryForMIDs tools, among others we have
*   DictdToDictionaryForMIDs, DictionaryGeneration,
*   JarCreator and BitmapFontGenerator.
*   
*   DictionaryForMIDs-Creator is free software;
*   you can redistribute it and/or modify it under the terms
*   of the GNU General Public License as published by the
*   Free Software Foundation; either version 2 of the License, or
*   (at your option) any later version.
*   
*   DictionaryForMIDs-Creator is distributed in the hope that
*   it will be useful, but WITHOUT ANY WARRANTY; without even
*   the implied warranty of MERCHANTABILITY or
*   FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*   	
*   You should have received a copy of the GNU General Public
*   License along with DictionaryForMIDs-Creator;
*   if not, write to the Free Software Foundation, Inc.,
*   51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*   
* //////////////////////////////////////////////////////////////// */


package de.kugihan.DfMCreator;

import edu.hws.eck.mdb.I18n;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;


public class HelpContents extends JPanel
                               implements TreeSelectionListener {
    
    public static String path = "doc" + "/";
    public JEditorPane htmlPane;
    public JTree tree;
    public URL helpURL;
    
    public HelpContents() {
        super(new GridLayout(1,0));

        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode(I18n.tr("dfmCreatorHelp"));
        createNodes(top);

        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        
        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);

        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        HyperlinkListener hyperlinkListener = new ActivatedHyperlinkListener(htmlPane);
        htmlPane.addHyperlinkListener(hyperlinkListener);
        initHelp();
        JScrollPane htmlView = new JScrollPane(htmlPane);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);
        
        Dimension minimumSize = new Dimension(150, 100);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(230); 
        splitPane.setPreferredSize(new Dimension(800, 500));

        //Add the split pane to this panel.
        add(splitPane);       
    }
    
    private class BookInfo {
        public String bookName;
        public URL bookURL;

        public BookInfo(String book, String filename) {
            bookName = book;
            bookURL = getClass().getResource(filename);
            if (bookURL == null) {
                System.err.println(I18n.tr("couldntFindFile")
                                   + filename);
            }
        }

        @Override
        public String toString() {
            return bookName;
        }
    }

    private void initHelp() {
        String s = path + "Help.html";
        helpURL = getClass().getResource(s);
        if (helpURL == null) {
            System.err.println(I18n.tr("couldntFindHelFile") + " " + s);
        }
        displayURL(helpURL);
    }
    
    private void displayURL(URL url) {
        try {
            if (url != null) {
                htmlPane.setPage(url);
            } else { //null url
		htmlPane.setText(I18n.tr("fileNotFound"));
            }
        } catch (IOException e) {
            System.err.println(I18n.tr("badURL", new Object[] {url}));
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            BookInfo book = (BookInfo)nodeInfo;
            displayURL(book.bookURL);
        } else {
            displayURL(helpURL); 
        }
    }
    
    public static JFrame createAndShowGUI() {
        JFrame frame = new JFrame(I18n.tr("dfmCreatorHelp"));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Add content to the window.
        frame.add(new HelpContents());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        return frame;
    }
    
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode guiCategory = null;
        DefaultMutableTreeNode docCategory = null;
        DefaultMutableTreeNode book = null;

        guiCategory = new DefaultMutableTreeNode("DfM-Creator - Quick Guide");
        top.add(guiCategory);
        
	// GUI Documentation category
	 

        // Dictd Converter
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("dictdConverter"),
        path + "gui-DictdToDictionaryForMIDs.html"));
        guiCategory.add(book);

        // Dictionary Generator
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("dictionaryGeneration"),
        path + "gui-DictionaryGeneration.html"));
        guiCategory.add(book);

        // Bitmap Font Generator
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("bfg"),
        path + "gui-BitmapFontGenerator.html"));
        guiCategory.add(book);

        // DfM's Jar Creator
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("dfmsJarCreator"),
	path + "gui-JarCreator.html"));
        guiCategory.add(book);
        
	/*
	 * Complete Documentation category
	 */
		
        docCategory = new DefaultMutableTreeNode(I18n.tr("completeDocumentation"));
        top.add(docCategory);
		
        // Important change notes
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("importantChangesNotes"),
        path + "newdictChangeNotes.html"));
        docCategory.add(book);

        // Setting up a new dictionary
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("settingUpDict"),
        path + "newdict.html"));
        docCategory.add(book);

        // Configuring DictionaryForMIDs.properties
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("dfm.properties"),
        path + "newdictProperties.html"));
        docCategory.add(book);

        // DictionaryUpdate
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("dictUpdt"),
        path + "newdictDictionaryUpdate.html"));             
        docCategory.add(book);
        
        // DictionaryUpdate Advanced features
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("dictUpdtAdvanced"),
        path + "newdictDictionaryUpdateAdvanced.html"));
        docCategory.add(book);

        // Normation: Available classes
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("normationAvailableClasses"),
        path + "newdictNormationLang.html"));
        docCategory.add(book);

        // Normation Advanced features
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("normationAdvanced"),
        path + "newdictNormation.html"));
        docCategory.add(book);
        
        // Multiple source dictionaries
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("multiSourceDicts"),
        path + "newdictMultiple.html"));
        docCategory.add(book);

        // Content declarations
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("contentDeclarations"),
        path + "newdictContent.html"));
        docCategory.add(book);
        
        // Generation the final Dictionary file
        book = new DefaultMutableTreeNode(new BookInfo
            (I18n.tr("genTheDictFiles"),
        path + "newdictDictionaryGeneration.html"));
        docCategory.add(book);
    }
    
    
    class ActivatedHyperlinkListener implements HyperlinkListener {
    JEditorPane editorPane;

    public ActivatedHyperlinkListener(JEditorPane editorPane) {
        this.editorPane = editorPane;
    }

        @Override
    public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
        HyperlinkEvent.EventType type = hyperlinkEvent.getEventType();
        final URL url = hyperlinkEvent.getURL();
        if (type == HyperlinkEvent.EventType.ENTERED) {
        System.out.println("URL: " + url);
        } else if (type == HyperlinkEvent.EventType.ACTIVATED) {
            System.out.println("Activated");
            Runnable runner = new Runnable() {
                @Override
                public void run() {
                // Retain reference to original
                Document doc = editorPane.getDocument();
                try {
                    editorPane.setPage(url);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null,
                        "Error following link", "Invalid link",
                        JOptionPane.ERROR_MESSAGE);
                    editorPane.setDocument(doc);
                }
                }
            };
            SwingUtilities.invokeLater(runner);
            }
        }
    }
    
}
