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

import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import edu.hws.eck.mdb.I18n;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class PropertiesPreview extends JDialog implements ActionListener {

    //undo helpers
    protected UndoAction undoAction;
    protected RedoAction redoAction;
    protected UndoManager undo = new UndoManager();
    
    HashMap<Object, Action> actions;
    
    public JDialog PropFileView = new JDialog();
    
    public JPanel panel = new JPanel();
    
    public JTextPane propTextPane = new JTextPane();
    
    public JMenuBar menuBar = new JMenuBar();
    
    public StyledDocument styledDoc = propTextPane.getStyledDocument();
    
    public AbstractDocument doc;
    
    public JScrollPane scrollpane = new JScrollPane(propTextPane);
    
    public JButton saveButton = new JButton(I18n.tr("save"));
    
    public JToggleButton editButton = new JToggleButton(I18n.tr("edit"));
    
    public JButton clearButton = new JButton(I18n.tr("clearText"));
    
    public JButton closeButton = new JButton(I18n.tr("close"));

    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // property file name
    public static final String PROPERTY_FILE_NAME = DictionaryDataFile.propertyFileName;
    
    public JFileChooser fc = new JFileChooser();
    
    /**
     * Creates new form PropertiesPreview
     */
    public PropertiesPreview() {
        initComponents();
    }
        
    private void initComponents() {
        PropFileView.setLayout(null);
        PropFileView.add(panel);
        
        panel.setBounds(0, 0, 500, 600);
        panel.setLayout(null);
        panel.add(scrollpane);
        panel.add(saveButton);
        panel.add(editButton);
        panel.add(clearButton);
        panel.add(closeButton);
        
        if (styledDoc instanceof AbstractDocument) {
            doc = (AbstractDocument)styledDoc;
            //doc.setDocumentFilter(new DocumentSizeFilter(MAX_CHARACTERS));
        } else {
            System.err.println(I18n.tr("textPaneDoc"));
            System.exit(-1);
        }
        
        //Set up the menu bar.
        actions=createActionTable(propTextPane);
        JMenu editMenu = createEditMenu();
        menuBar.add(editMenu);
        PropFileView.setJMenuBar(menuBar);
        
        scrollpane.setBounds(2, 2, 490, 535);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        saveButton.setBounds(40, 540, 100, 30);
        editButton.setBounds(143, 540, 100, 30);
        clearButton.setBounds(246, 540, 100, 30);
        closeButton.setBounds(349, 540, 100, 30);
        
        saveButton.addActionListener(this);
        editButton.addActionListener(this);        
        clearButton.addActionListener(this);        
        closeButton.addActionListener(this);
        
        doc.addUndoableEditListener(new MyUndoableEditListener());
        
        propTextPane.setEditable(false);
        propTextPane.setBackground(new java.awt.Color(225, 255, 190));
        PropFileView.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        PropFileView.setPreferredSize(new Dimension(500, 650));
        PropFileView.pack();
        PropFileView.setLocation(screenSize.width / 2 - PropFileView.getWidth() / 2,
                               screenSize.height / 2 - PropFileView.getHeight() / 2);
        PropFileView.setModal(true);
        PropFileView.setResizable(false);
    }
    
    public static PropertiesPreview getPropPreviewWin() {
    	return new PropertiesPreview();
    }
    
    public void appendText(String s) {
        try {
            styledDoc.insertString(styledDoc.getLength(), s, null);
        } catch(BadLocationException exc) {
            System.out.println(exc + I18n.tr("execMsg", new Object[] {exc.getMessage()}));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
        if (e.getSource() == saveButton) {
            try {
                savePropFile();
            } catch (UnsupportedOperationException ex){
                DfMCreatorMain.printAnyMsg(canWriteFileMsg, I18n.tr("dirNotAccessible"), JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex){
                DfMCreatorMain.printAnyMsg(internalErrorMsg, I18n.tr("error"), JOptionPane.ERROR_MESSAGE);                
            }
        } else if (e.getSource() == editButton) {
            editPropFile();
        } else if (e.getSource() == clearButton) {
            clearProps();
        } else if (e.getSource() == closeButton) {
            closePropViewWin();
        }
    }
    
    private void savePropFile() throws UnsupportedOperationException,
                                       IOException, NullPointerException {
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showSaveDialog(PropFileView);
        int rval = -1;
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            file.mkdirs(); // if the user entered a path that doesn't exist yet, we create it.
            if (!file.canWrite()){
                throw new UnsupportedOperationException();
            }
            String s = file.toString();
            s = s + FileSystems.getDefault().getSeparator() + PROPERTY_FILE_NAME;
            File file2 = new File(s);
            if (file2.exists()){
                rval = JOptionPane.showConfirmDialog(PropFileView, I18n.tr("fileExistsString"),
                                                              I18n.tr("fileExistsTitle"), JOptionPane.YES_NO_OPTION);
            }
            if (rval == JOptionPane.YES_OPTION || !file2.exists()){
                try {
                    try (BufferedWriter out = new BufferedWriter(new FileWriter((file2)))) {
                        String text = propTextPane.getText();
                        // writing content to file
                        out.write(text);
                        out.flush();
                    }
                } catch (IOException e){
                    DfMCreatorMain.printAnyMsg(IOEMsg, I18n.tr("runtimeErrorTitle"), JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException e){
                    DfMCreatorMain.printAnyMsg(NPEMsg, I18n.tr("error"), JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // do nothing, for now...
            }
        }
    }

    private void editPropFile() {
        
        if (editButton.isSelected()){
            propTextPane.setEditable(true);
            propTextPane.setBackground(new java.awt.Color(255, 255, 255));
            editButton.setText(I18n.tr("doNotEdit"));
            undoAction.setEnabled(true);
            redoAction.setEnabled(true);
            propTextPane.setCaretPosition(styledDoc.getLength() - 1);
        } else {
            propTextPane.setEditable(false);
            propTextPane.setBackground(new java.awt.Color(225, 255, 190));
            editButton.setText(I18n.tr("edit"));
            undoAction.setEnabled(false);
            redoAction.setEnabled(false);
            propTextPane.setCaretPosition(0);
        }
    }

    private void clearProps() {
        int returnVal = JOptionPane.showConfirmDialog(null, I18n.tr("sureToClear"),
                                                    I18n.tr("confirmDeletion"), JOptionPane.YES_NO_OPTION);
        if (returnVal == JOptionPane.YES_OPTION){
            propTextPane.setText("");
        }
    }

    private void closePropViewWin() {
        int returnVal = JOptionPane.showConfirmDialog(PropFileView, I18n.tr("sureToClose"),
                                                     I18n.tr("confirmation"), JOptionPane.YES_NO_OPTION);
        if (returnVal == JOptionPane.YES_OPTION){
            PropFileView.dispose();
            
        }
    }
    
    private final String internalErrorMsg = I18n.tr("internalError.operationAborted");
    private final String IOEMsg = I18n.tr("internalError.operationAborted");                           
    private final String NPEMsg = I18n.tr("textAreaEmptyError");    
    private final String canWriteFileMsg = I18n.tr("propFileCantBeWritten");
                                         
    /*
     *
     * Undo and Redo Subroutines
     *
     */
     
    //This one listens for edits that can be undone.
    protected class MyUndoableEditListener
                    implements UndoableEditListener {
        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            //RememenuBarer the edit and update the menus.
            undo.addEdit(e.getEdit());
            undoAction.updateUndoState();
            redoAction.updateRedoState();
        }
    }
    
    //Create the edit menu.
    protected JMenu createEditMenu() {
        JMenu menu = new JMenu(I18n.tr("edit"));

        //Undo and redo are actions of our own creation.
        undoAction = new UndoAction();
        menu.add(undoAction);

        redoAction = new RedoAction();
        menu.add(redoAction);

        menu.addSeparator();

        //These actions come from the default editor kit.
        //Get the ones we want and stick them in the menu.
        menu.add(getActionByName(DefaultEditorKit.cutAction));
        menu.add(getActionByName(DefaultEditorKit.copyAction));
        menu.add(getActionByName(DefaultEditorKit.pasteAction));

        menu.addSeparator();

        menu.add(getActionByName(DefaultEditorKit.selectAllAction));
        return menu;
    }
    
    private Action getActionByName(String name) {
        return actions.get(name);
    }
    
    //The following two methods allow us to find an
    //action provided by the editor kit by its name.
    private HashMap<Object, Action> createActionTable(JTextComponent textComponent) {
        HashMap<Object, Action> actions_ = new HashMap<>();
        Action[] actionsArray = textComponent.getActions();
        for (int i = 0; i < actionsArray.length; i++) {
            Action a = actionsArray[i];
            actions_.put(a.getValue(Action.NAME), a);
        }
	return actions_;
    }
    
    class UndoAction extends AbstractAction {
        public UndoAction() {
            super(I18n.tr("undo"));
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                System.out.println(I18n.tr("notUndoable", new Object[] {ex}));
            }
            updateUndoState();
            redoAction.updateRedoState();
        }

        protected void updateUndoState() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, I18n.tr("undo"));
            }
        }
    }

    class RedoAction extends AbstractAction {
        public RedoAction() {
            super(I18n.tr("redo"));
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                System.out.println(I18n.tr("notRedoable", new Object[] {ex}));
            }
            updateRedoState();
            undoAction.updateUndoState();
        }

        protected void updateRedoState() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, I18n.tr("redo"));
            }
        }
    }
                                         
    
}
