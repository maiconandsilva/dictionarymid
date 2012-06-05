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

import de.kugihan.DfMCreator.DfMCreatorException.BadDictDirNameException;
import de.kugihan.DfMCreator.DfMCreatorException.CantCreateDestDir;
import de.kugihan.DfMCreator.DfMCreatorException.DBFolderNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.DBINIFileNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.DBNameTextFieldIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.DBPathTextFieldIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.DictionaryDirectoryNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.DirectoryDestinationNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.DirectoryDestinationTFIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.EmptyDfMDirTFIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.EmptyDfMFileNotFound;
import de.kugihan.DfMCreator.DfMCreatorException.EmptyDfMJarJadDirDoesNotExist;
import de.kugihan.DfMCreator.DfMCreatorException.InputCSVFilesTFIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.OutCSVFileCantBeWritten;
import de.kugihan.DfMCreator.DfMCreatorException.OutCSVFileTextFieldIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.OutputDirTFIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.OutputDirectoryNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.PropFileErrorException;
import de.kugihan.DfMCreator.DfMCreatorException.PropertyPathNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.PropertyPathTFIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.SourceFileNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.SourceFileTFIsEmpty;
import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dictdtodictionaryformids.DictdToDfM;
import de.kugihan.dictionaryformids.dictgen.DictionaryGeneration;
import de.kugihan.fonttoolkit.FontToolkit;
import de.kugihan.jarCreator.JarCreator;
import edu.hws.eck.mdb.I18n;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.prefs.Preferences;
import javax.swing.*;


public class DfMCreatorMain extends javax.swing.JFrame {
    // version number.
    private static final String dfm_creator_version = "0.4";
    
    // variables used with DictdToDfM
    private static String dbNameVar;
    private static String dbFolderNameVar;
    private static String outputCSVFileVar;
    private static String outputEncodingCharsetVar;
    
    private static char separatorCharacterVar;
    
    private static boolean keepTabsAndNewLinesVar;
    private static boolean switchLanguagesVar;
    private static boolean removeSquareBracketsVar;
    
    // Variables used with DictionaryGeneration
    private static String sourceFile;
    private static String directoryDestination;
    private static String propertyPath;
    
    // Variables used with JarCreator
    private static String dictionarydirectory;
    private static String emptydictionaryformids;
    private static String outputdirectory;
    
    // get the size of the screen.
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
    // System specific path separator: / in linux \ in windows...
    public static String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();
    
    // Variables used by applyPreferences() and savePreferences
    public static final String pathName = "/de/kugihan/DfMCreator";
    public static final Preferences root = Preferences.userRoot();
    public static final Preferences node = root.node(pathName);
    public static final String LookAndFeel = node.get("DfMCreator.window.LookAndFeel",null);

    private static void setTheLocale() {
        //Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        //Locale locale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
        Locale locale = Locale.getDefault();
        
        // debug
        System.out.println(locale.toString());
        I18n.setLocale(locale);
    }

    /**
     * Creates new form
     */
    public DfMCreatorMain() {
        initComponents();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        EncGroupBut = new javax.swing.ButtonGroup();
        SCGroupBut = new javax.swing.ButtonGroup();
        DFMBuilderTabbedPane = new javax.swing.JTabbedPane();
        DictdConvPanel = new javax.swing.JPanel();
        dictdPanel = new javax.swing.JPanel();
        ButtonsPanel = new javax.swing.JPanel();
        clearFieldsButton = new javax.swing.JButton();
        proceedButton = new javax.swing.JButton();
        EncodingPanel = new javax.swing.JPanel();
        EncodingLabel = new javax.swing.JLabel();
        UTFLable = new javax.swing.JLabel();
        UTFRB = new javax.swing.JRadioButton();
        UTF16Label = new javax.swing.JLabel();
        UTF16RB = new javax.swing.JRadioButton();
        UTFBELabel = new javax.swing.JLabel();
        UTFBERB = new javax.swing.JRadioButton();
        UTFLELabel = new javax.swing.JLabel();
        OWNRB = new javax.swing.JRadioButton();
        ISO1RB = new javax.swing.JRadioButton();
        ISO2RB = new javax.swing.JRadioButton();
        IBMRB = new javax.swing.JRadioButton();
        ISO1Label = new javax.swing.JLabel();
        ISO2Label = new javax.swing.JLabel();
        IBMLabel = new javax.swing.JLabel();
        ISO50Label = new javax.swing.JLabel();
        ISO50RB = new javax.swing.JRadioButton();
        UTFLERB = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        OwnEncTextField = new javax.swing.JTextField();
        OwnEncLabel = new javax.swing.JLabel();
        DBPanel = new javax.swing.JPanel();
        DBBrowseButton = new javax.swing.JButton();
        DBNameTextField = new javax.swing.JTextField();
        DBPathTextField = new javax.swing.JTextField();
        DBBrowseLabel = new javax.swing.JLabel();
        DBPathLabel = new javax.swing.JLabel();
        OutCVSFilePathLabel = new javax.swing.JLabel();
        OutCSVFileTextField = new javax.swing.JTextField();
        OutCSVFileBrowseLabel = new javax.swing.JLabel();
        OutCSVFileBrowseButton = new javax.swing.JButton();
        DBNameLabel = new javax.swing.JLabel();
        SCPanel = new javax.swing.JPanel();
        TabLabel = new javax.swing.JLabel();
        CarRetLabel = new javax.swing.JLabel();
        FormFeedLabel = new javax.swing.JLabel();
        TabRB = new javax.swing.JRadioButton();
        CRRB = new javax.swing.JRadioButton();
        FFRB = new javax.swing.JRadioButton();
        OwnSCRB = new javax.swing.JRadioButton();
        ChooseSCLabel = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        OwnLabel = new javax.swing.JLabel();
        OwnSCTextField = new javax.swing.JTextField();
        SLPanel = new javax.swing.JPanel();
        SLLabel = new javax.swing.JLabel();
        KTLabel = new javax.swing.JLabel();
        RSBLabel = new javax.swing.JLabel();
        SLCheckBox = new javax.swing.JCheckBox();
        KTCheckBox = new javax.swing.JCheckBox();
        RSBCheckBox = new javax.swing.JCheckBox();
        toolbar4 = new javax.swing.JToolBar();
        jLabel12 = new javax.swing.JLabel();
        DictGenPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        SourceFileLabel = new javax.swing.JLabel();
        SourceFileTF = new javax.swing.JTextField();
        DirectoryDestinationLabel = new javax.swing.JLabel();
        DirectoryDestinationTF = new javax.swing.JTextField();
        PropertyPathLabel = new javax.swing.JLabel();
        PropertyPathTF = new javax.swing.JTextField();
        SourceFileBrowseLB = new javax.swing.JLabel();
        DirectoryDestinationBrowseLB = new javax.swing.JLabel();
        PropertyPathBrowseLB = new javax.swing.JLabel();
        SourceFileBrowseBT = new javax.swing.JButton();
        DirectoryDestinationBrowseBT = new javax.swing.JButton();
        PropertyPathBT = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        GenDictFilesBT = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        toolbar1 = new javax.swing.JToolBar();
        jLabel9 = new javax.swing.JLabel();
        bfgPanel = new javax.swing.JPanel();
        infotarea = new javax.swing.JTextField();
        JarCreatorPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        InputCSVFilesTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        EmptyDfMDirTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        OutputDirTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        InputCSVFilesButton = new javax.swing.JButton();
        EmptyDfMDirButton = new javax.swing.JButton();
        OutputDirButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        toolbar2 = new javax.swing.JToolBar();
        jLabel10 = new javax.swing.JLabel();
        dfmBuilderMenuBar = new javax.swing.JMenuBar();
        dictdconvMenu = new javax.swing.JMenu();
        miEditSettings = new javax.swing.JMenuItem();
        miProceedConvert = new javax.swing.JMenuItem();
        dictGenMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        fontGenMenu = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jarCreatorMenu = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        miContents = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        // Grouping the radio buttons
        EncGroupBut.add(UTFRB);
        EncGroupBut.add(UTF16RB);
        EncGroupBut.add(UTFBERB);
        EncGroupBut.add(OWNRB);
        EncGroupBut.add(ISO1RB);
        EncGroupBut.add(ISO2RB);
        EncGroupBut.add(IBMRB);
        EncGroupBut.add(ISO50RB);
        EncGroupBut.add(UTFLERB);

        UTFRB.setSelected(true);

        SCGroupBut.add(TabRB);
        SCGroupBut.add(CRRB);
        SCGroupBut.add(FFRB);
        SCGroupBut.add(OwnSCRB);

        TabRB.setSelected(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DictionaryForMIDs-Creator " + dfm_creator_version);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setMinimumSize(new java.awt.Dimension(720, 450));
        setName("mainFrame");
        setResizable(false);

        DictdConvPanel.setLayout(new java.awt.GridBagLayout());

        dictdPanel.setLayout(new java.awt.GridBagLayout());

        ButtonsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ButtonsPanel.setLayout(new java.awt.GridBagLayout());

        clearFieldsButton.setText(I18n.tr("clearFields.dfmCreatorMain")); // NOI18N
        clearFieldsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFieldsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        ButtonsPanel.add(clearFieldsButton, gridBagConstraints);

        proceedButton.setText(I18n.tr("proceed.dfmCreatorMain")); // NOI18N
        proceedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proceedButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        ButtonsPanel.add(proceedButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dictdPanel.add(ButtonsPanel, gridBagConstraints);

        EncodingPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EncodingPanel.setLayout(new java.awt.GridBagLayout());

        EncodingLabel.setText(I18n.tr("encoding.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(EncodingLabel, gridBagConstraints);

        UTFLable.setText("UTF-8");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(UTFLable, gridBagConstraints);

        UTFRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UTFRBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(UTFRB, gridBagConstraints);
        UTFRB.setSelected(true);

        UTF16Label.setText("UTF-16");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(UTF16Label, gridBagConstraints);

        UTF16RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UTF16RBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(UTF16RB, gridBagConstraints);

        UTFBELabel.setText("UTF-16BE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(UTFBELabel, gridBagConstraints);

        UTFBERB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UTFBERBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(UTFBERB, gridBagConstraints);

        UTFLELabel.setText("UTF-16LE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(UTFLELabel, gridBagConstraints);

        OWNRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OWNRBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(OWNRB, gridBagConstraints);

        ISO1RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ISO1RBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(ISO1RB, gridBagConstraints);

        ISO2RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ISO2RBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(ISO2RB, gridBagConstraints);

        IBMRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IBMRBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(IBMRB, gridBagConstraints);

        ISO1Label.setText("ISO-8859-1");
        ISO1Label.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ISO1LabelAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(ISO1Label, gridBagConstraints);

        ISO2Label.setText("ISO-8859-2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(ISO2Label, gridBagConstraints);

        IBMLabel.setText("IBM-850");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(IBMLabel, gridBagConstraints);

        ISO50Label.setText("ISO-2022-50");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(ISO50Label, gridBagConstraints);

        ISO50RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ISO50RBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(ISO50RB, gridBagConstraints);

        UTFLERB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UTFLERBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        EncodingPanel.add(UTFLERB, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        OwnEncTextField.setEnabled(false);
        OwnEncTextField.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                OwnEncTextFieldInputMethodTextChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel7.add(OwnEncTextField, gridBagConstraints);

        OwnEncLabel.setText(I18n.tr("chooseYourOwn.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel7.add(OwnEncLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        EncodingPanel.add(jPanel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dictdPanel.add(EncodingPanel, gridBagConstraints);

        DBPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        DBPanel.setLayout(new java.awt.GridBagLayout());

        DBBrowseButton.setText("...");
        DBBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBBrowseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 2);
        DBPanel.add(DBBrowseButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 125;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        DBPanel.add(DBNameTextField, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        DBPanel.add(DBPathTextField, gridBagConstraints);

        DBBrowseLabel.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 2);
        DBPanel.add(DBBrowseLabel, gridBagConstraints);

        DBPathLabel.setText(I18n.tr("dbDir.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        DBPanel.add(DBPathLabel, gridBagConstraints);

        OutCVSFilePathLabel.setText(I18n.tr("outputFileDir.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        DBPanel.add(OutCVSFilePathLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        DBPanel.add(OutCSVFileTextField, gridBagConstraints);

        OutCSVFileBrowseLabel.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 2);
        DBPanel.add(OutCSVFileBrowseLabel, gridBagConstraints);

        OutCSVFileBrowseButton.setText("...");
        OutCSVFileBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OutCSVFileBrowseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 2);
        DBPanel.add(OutCSVFileBrowseButton, gridBagConstraints);

        DBNameLabel.setText(I18n.tr("dbName.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        DBPanel.add(DBNameLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dictdPanel.add(DBPanel, gridBagConstraints);

        SCPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SCPanel.setLayout(new java.awt.GridBagLayout());

        TabLabel.setText(I18n.tr("tab.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SCPanel.add(TabLabel, gridBagConstraints);

        CarRetLabel.setText(I18n.tr("carRe.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SCPanel.add(CarRetLabel, gridBagConstraints);

        FormFeedLabel.setText(I18n.tr("formFeed.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SCPanel.add(FormFeedLabel, gridBagConstraints);

        TabRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TabRBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SCPanel.add(TabRB, gridBagConstraints);

        CRRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CRRBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SCPanel.add(CRRB, gridBagConstraints);

        FFRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FFRBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SCPanel.add(FFRB, gridBagConstraints);

        OwnSCRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OwnSCRBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SCPanel.add(OwnSCRB, gridBagConstraints);

        ChooseSCLabel.setText(I18n.tr("chooseSepChar.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SCPanel.add(ChooseSCLabel, gridBagConstraints);

        jPanel8.setLayout(new java.awt.GridBagLayout());

        OwnLabel.setText(I18n.tr("chooseYourOwn.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel8.add(OwnLabel, gridBagConstraints);

        OwnSCTextField.setEnabled(false);
        OwnSCTextField.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                OwnSCTextFieldInputMethodTextChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel8.add(OwnSCTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        SCPanel.add(jPanel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.ipady = 37;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dictdPanel.add(SCPanel, gridBagConstraints);

        SLPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SLPanel.setLayout(new java.awt.GridBagLayout());

        SLLabel.setText(I18n.tr("switchLang.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SLPanel.add(SLLabel, gridBagConstraints);

        KTLabel.setText(I18n.tr("keepTab.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SLPanel.add(KTLabel, gridBagConstraints);

        RSBLabel.setText(I18n.tr("removeSquare.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        SLPanel.add(RSBLabel, gridBagConstraints);

        SLCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SLCheckBoxStateChanged(evt);
            }
        });
        SLCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SLCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        SLPanel.add(SLCheckBox, gridBagConstraints);

        KTCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KTCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        SLPanel.add(KTCheckBox, gridBagConstraints);

        RSBCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RSBCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        SLPanel.add(RSBCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dictdPanel.add(SLPanel, gridBagConstraints);

        toolbar4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        toolbar4.setFloatable(false);
        toolbar4.setRollover(true);
        toolbar4.setPreferredSize(new java.awt.Dimension(49, 15));
        toolbar4.setRequestFocusEnabled(false);

        jLabel12.setText("<html><font color=gray>DictdToDictionaryForMIDs</font>");
        toolbar4.add(jLabel12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dictdPanel.add(toolbar4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        DictdConvPanel.add(dictdPanel, gridBagConstraints);

        DFMBuilderTabbedPane.addTab("DictdToDictionaryForMIDs", DictdConvPanel);

        DictGenPanel.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        SourceFileLabel.setText(I18n.tr("dictGenLabel1.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(SourceFileLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(SourceFileTF, gridBagConstraints);

        DirectoryDestinationLabel.setText(I18n.tr("dictGenLabel3.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(DirectoryDestinationLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(DirectoryDestinationTF, gridBagConstraints);

        PropertyPathLabel.setText(I18n.tr("dictGenLabel2.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(PropertyPathLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(PropertyPathTF, gridBagConstraints);

        SourceFileBrowseLB.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 2);
        jPanel1.add(SourceFileBrowseLB, gridBagConstraints);

        DirectoryDestinationBrowseLB.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 2);
        jPanel1.add(DirectoryDestinationBrowseLB, gridBagConstraints);

        PropertyPathBrowseLB.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 2);
        jPanel1.add(PropertyPathBrowseLB, gridBagConstraints);

        SourceFileBrowseBT.setText("...");
        SourceFileBrowseBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SourceFileBrowseBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 2);
        jPanel1.add(SourceFileBrowseBT, gridBagConstraints);

        DirectoryDestinationBrowseBT.setText("...");
        DirectoryDestinationBrowseBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DirectoryDestinationBrowseBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 2);
        jPanel1.add(DirectoryDestinationBrowseBT, gridBagConstraints);

        PropertyPathBT.setText("...");
        PropertyPathBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PropertyPathBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 2);
        jPanel1.add(PropertyPathBT, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        GenDictFilesBT.setText(I18n.tr("generate.dfmCreatorMain")); // NOI18N
        GenDictFilesBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenDictFilesBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        jPanel5.add(GenDictFilesBT, gridBagConstraints);

        jButton2.setText(I18n.tr("clear.dfmCreatorMain")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        jPanel5.add(jButton2, gridBagConstraints);

        jButton5.setText(I18n.tr("checkCSVFile.dfmCreatorMain")); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        jPanel5.add(jButton5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        jPanel1.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        DictGenPanel.add(jPanel1, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jButton8.setText(I18n.tr("create.dfmCreatorMain")); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel6.add(jButton8, gridBagConstraints);

        jButton3.setText(I18n.tr("editAnExisting.dfmCreatorMain")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel6.add(jButton3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel3.add(jPanel6, gridBagConstraints);

        jLabel7.setText(I18n.tr("propsInfoLabel.dfmCreatorMain")); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel3.add(jLabel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        DictGenPanel.add(jPanel3, gridBagConstraints);

        toolbar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        toolbar1.setFloatable(false);
        toolbar1.setRollover(true);
        toolbar1.setPreferredSize(new java.awt.Dimension(49, 15));
        toolbar1.setRequestFocusEnabled(false);

        jLabel9.setText("<html><font color=gray>DictionaryGeneration</font>");
        toolbar1.add(jLabel9);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        DictGenPanel.add(toolbar1, gridBagConstraints);

        DFMBuilderTabbedPane.addTab("DictionaryGeneration", DictGenPanel);

        bfgPanel.setLayout(new java.awt.GridBagLayout());

        infotarea.setText("Don't touch this panel it will hold the FontToolkit at runtime...");
        infotarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infotareaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        bfgPanel.add(infotarea, gridBagConstraints);

        DFMBuilderTabbedPane.addTab("BitmapFontGenerator", bfgPanel);
        // Creating a toolbar
        javax.swing.JToolBar bfgToolbar = new javax.swing.JToolBar();
        bfgToolbar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bfgToolbar.setFloatable(false);
        bfgToolbar.setRollover(true);
        bfgToolbar.setPreferredSize(new java.awt.Dimension(400, 15));
        bfgToolbar.setRequestFocusEnabled(false);

        // Creating a label for the toolbar
        javax.swing.JLabel toolbarLabel = new javax.swing.JLabel();
        toolbarLabel.setText("<html><font color=gray>BitmapFontGenerator</font>");
        bfgToolbar.add(toolbarLabel);

        // Hiding the little info text area
        infotarea.setVisible(false);
        // Creating a new layout
        bfgPanel.setLayout(new java.awt.GridBagLayout());
        // Creating an instance of the FontToolkit
        FontToolkit fontTK = new FontToolkit();
        // Getting the FontToolkit panel
        JPanel fontTKPanel = fontTK.getJPanel();

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bfgPanel.add(bfgToolbar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bfgPanel.add(fontTKPanel, gridBagConstraints);

        fontTKPanel.setPreferredSize(new java.awt.Dimension(460, 300));
        fontTKPanel.setVisible(true);

        JarCreatorPanel.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(I18n.tr("jarCreatorLabel1.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jLabel1, gridBagConstraints);

        InputCSVFilesTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputCSVFilesTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(InputCSVFilesTF, gridBagConstraints);

        jLabel2.setText(I18n.tr("jarCreatorLabel2.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(EmptyDfMDirTF, gridBagConstraints);

        jLabel3.setText(I18n.tr("jarCreatorLabel3.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(OutputDirTF, gridBagConstraints);

        jLabel4.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 2);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 2);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 0, 2);
        jPanel2.add(jLabel6, gridBagConstraints);

        InputCSVFilesButton.setText("...");
        InputCSVFilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputCSVFilesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 2);
        jPanel2.add(InputCSVFilesButton, gridBagConstraints);

        EmptyDfMDirButton.setText("...");
        EmptyDfMDirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmptyDfMDirButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 2);
        jPanel2.add(EmptyDfMDirButton, gridBagConstraints);

        OutputDirButton.setText("...");
        OutputDirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OutputDirButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 2);
        jPanel2.add(OutputDirButton, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jButton4.setText(I18n.tr("packCSVFiles.dfmCreatorMain")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(jButton4, gridBagConstraints);

        jButton1.setText(I18n.tr("clear.fields.dfmCreatorMain")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel4.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        JarCreatorPanel.add(jPanel2, gridBagConstraints);

        toolbar2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        toolbar2.setFloatable(false);
        toolbar2.setRollover(true);
        toolbar2.setPreferredSize(new java.awt.Dimension(49, 15));
        toolbar2.setRequestFocusEnabled(false);

        jLabel10.setText("<html><font color=gray>JarCreator</font>");
        toolbar2.add(jLabel10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        JarCreatorPanel.add(toolbar2, gridBagConstraints);

        DFMBuilderTabbedPane.addTab("JarCreator", JarCreatorPanel);

        getContentPane().add(DFMBuilderTabbedPane, java.awt.BorderLayout.CENTER);

        dictdconvMenu.setText(I18n.tr("dictdConverter.dfmCreatorMain")); // NOI18N
        dictdconvMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dictdconvMenuMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dictdconvMenuMousePressed(evt);
            }
        });
        dictdconvMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dictdconvMenuActionPerformed(evt);
            }
        });

        miEditSettings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        miEditSettings.setText(I18n.tr("clearAllFields.dfmCreatorMain")); // NOI18N
        miEditSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditSettingsActionPerformed(evt);
            }
        });
        dictdconvMenu.add(miEditSettings);

        miProceedConvert.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        miProceedConvert.setText(I18n.tr("proceed.dfmCreatorMain")); // NOI18N
        miProceedConvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miProceedConvertActionPerformed(evt);
            }
        });
        dictdconvMenu.add(miProceedConvert);

        dfmBuilderMenuBar.add(dictdconvMenu);

        dictGenMenu.setText(I18n.tr("dictGen.dfmCreatorMain")); // NOI18N
        dictGenMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dictGenMenuMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                dictGenMenuMousePressed(evt);
            }
        });
        dictGenMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dictGenMenuActionPerformed(evt);
            }
        });

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText(I18n.tr("createThePropsFile.dfmCreatorMain")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        dictGenMenu.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText(I18n.tr("genDictFiles.dfmCreatorMain")); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        dictGenMenu.add(jMenuItem2);

        jMenuItem3.setText(I18n.tr("clear.fields.menu.item.dfmCreatorMain")); // NOI18N
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        dictGenMenu.add(jMenuItem3);

        jMenuItem9.setText(I18n.tr("editAnExisting.dfmCreatorMain")); // NOI18N
        dictGenMenu.add(jMenuItem9);

        jMenuItem10.setText(I18n.tr("checkCSVFile.dfmCreatorMain")); // NOI18N
        dictGenMenu.add(jMenuItem10);

        dfmBuilderMenuBar.add(dictGenMenu);

        fontGenMenu.setText(I18n.tr("bmf.dfmCreatorMain")); // NOI18N
        fontGenMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fontGenMenuMousePressed(evt);
            }
        });
        fontGenMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontGenMenuActionPerformed(evt);
            }
        });

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText(I18n.tr("start.dfmCreatorMain")); // NOI18N
        jMenuItem8.setEnabled(false);
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        fontGenMenu.add(jMenuItem8);

        dfmBuilderMenuBar.add(fontGenMenu);

        jarCreatorMenu.setText(I18n.tr("jarCreator.dfmCreatorMain")); // NOI18N
        jarCreatorMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jarCreatorMenuMousePressed(evt);
            }
        });
        jarCreatorMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jarCreatorMenuActionPerformed(evt);
            }
        });

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText(I18n.tr("pack.jarCreator.menu.item")); // NOI18N
        jMenuItem5.setActionCommand(I18n.tr("pack.jarCreator.menu.item")); // NOI18N
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jarCreatorMenu.add(jMenuItem5);

        jMenuItem7.setText(I18n.tr("clear.fields.menu.item.dfmCreatorMain")); // NOI18N
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jarCreatorMenu.add(jMenuItem7);

        dfmBuilderMenuBar.add(jarCreatorMenu);

        jMenu1.setText(I18n.tr("prefs.dfmCreatorMain")); // NOI18N

        jMenuItem6.setText(I18n.tr("lookAndFeelSets.dfmCreatorMain")); // NOI18N
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        dfmBuilderMenuBar.add(jMenu1);

        helpMenu.setText(I18n.tr("help.dfmCreatorMain")); // NOI18N
        helpMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuActionPerformed(evt);
            }
        });

        miContents.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        miContents.setText(I18n.tr("contents.dfmCreatorMain")); // NOI18N
        miContents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miContentsActionPerformed(evt);
            }
        });
        helpMenu.add(miContents);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText(I18n.tr("about.dfmCreatorMain")); // NOI18N
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem4);

        dfmBuilderMenuBar.add(helpMenu);

        setJMenuBar(dfmBuilderMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void helpMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuActionPerformed
        showAbout();
    }//GEN-LAST:event_helpMenuActionPerformed

    private void RSBCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RSBCheckBoxActionPerformed

   }//GEN-LAST:event_RSBCheckBoxActionPerformed

    private void KTCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KTCheckBoxActionPerformed

   }//GEN-LAST:event_KTCheckBoxActionPerformed

    private void SLCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SLCheckBoxActionPerformed

   }//GEN-LAST:event_SLCheckBoxActionPerformed

    private void SLCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SLCheckBoxStateChanged

   }//GEN-LAST:event_SLCheckBoxStateChanged

    private void OwnSCTextFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_OwnSCTextFieldInputMethodTextChanged

   }//GEN-LAST:event_OwnSCTextFieldInputMethodTextChanged

    private void OwnSCRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OwnSCRBActionPerformed
        OwnSCTextField.setEnabled(true);
    }//GEN-LAST:event_OwnSCRBActionPerformed

    private void FFRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FFRBActionPerformed
        OwnTextFieldCheck();
    }//GEN-LAST:event_FFRBActionPerformed

    private void CRRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CRRBActionPerformed
        OwnTextFieldCheck();
    }//GEN-LAST:event_CRRBActionPerformed

    private void TabRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TabRBActionPerformed
        OwnTextFieldCheck();
    }//GEN-LAST:event_TabRBActionPerformed

    private void OutCSVFileBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OutCSVFileBrowseButtonActionPerformed
        String s = getFile(true);
        if (!"".equals(s)){
            OutCSVFileTextField.setText(s);
        }
    }//GEN-LAST:event_OutCSVFileBrowseButtonActionPerformed

    private void DBBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBBrowseButtonActionPerformed
        String s = getFile(true);
        if (!"".equals(s)){
            DBPathTextField.setText(s);
            OutCSVFileTextField.setText(DBPathTextField.getText());
        }
    }//GEN-LAST:event_DBBrowseButtonActionPerformed

    private void OwnEncTextFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_OwnEncTextFieldInputMethodTextChanged

   }//GEN-LAST:event_OwnEncTextFieldInputMethodTextChanged

    private void UTFLERBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UTFLERBActionPerformed
        OwnEncTextFieldCheck();
    }//GEN-LAST:event_UTFLERBActionPerformed

    private void ISO50RBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ISO50RBActionPerformed
        OwnEncTextFieldCheck();
    }//GEN-LAST:event_ISO50RBActionPerformed

    private void ISO1LabelAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ISO1LabelAncestorAdded
        OwnEncTextFieldCheck();
    }//GEN-LAST:event_ISO1LabelAncestorAdded

    private void IBMRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IBMRBActionPerformed
        OwnEncTextFieldCheck();
    }//GEN-LAST:event_IBMRBActionPerformed

    private void ISO2RBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ISO2RBActionPerformed
        OwnEncTextFieldCheck();
    }//GEN-LAST:event_ISO2RBActionPerformed

    private void ISO1RBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ISO1RBActionPerformed
        OwnEncTextFieldCheck();
    }//GEN-LAST:event_ISO1RBActionPerformed

    private void OWNRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OWNRBActionPerformed
        OwnEncTextField.setEnabled(true);
    }//GEN-LAST:event_OWNRBActionPerformed

    private void UTFBERBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UTFBERBActionPerformed
        OwnEncTextFieldCheck();
    }//GEN-LAST:event_UTFBERBActionPerformed

    private void UTF16RBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UTF16RBActionPerformed
        OwnEncTextFieldCheck();
    }//GEN-LAST:event_UTF16RBActionPerformed

    private void UTFRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UTFRBActionPerformed
        OwnEncTextFieldCheck();
    }//GEN-LAST:event_UTFRBActionPerformed

    private void proceedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proceedButtonActionPerformed
        DictdConvProceedAll();
    }//GEN-LAST:event_proceedButtonActionPerformed

    private void clearFieldsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFieldsButtonActionPerformed
        clearFields();
    }//GEN-LAST:event_clearFieldsButtonActionPerformed

    private void InputCSVFilesTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputCSVFilesTFActionPerformed

    }//GEN-LAST:event_InputCSVFilesTFActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        checkScreenResolution();
        showPropWin();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void GenDictFilesBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenDictFilesBTActionPerformed
        DictGenProceedAll();
    }//GEN-LAST:event_GenDictFilesBTActionPerformed

    private void SourceFileBrowseBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SourceFileBrowseBTActionPerformed
        String s = getFile(false);
        if (!"".equals(s)){
            SourceFileTF.setText(s);
        }
    }//GEN-LAST:event_SourceFileBrowseBTActionPerformed

    private void DirectoryDestinationBrowseBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DirectoryDestinationBrowseBTActionPerformed
        String s = getFile(true);
        if (!"".equals(s)){
            DirectoryDestinationTF.setText(s);
        }
    }//GEN-LAST:event_DirectoryDestinationBrowseBTActionPerformed

    private void PropertyPathBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PropertyPathBTActionPerformed
        String s = getFile(true);
        if (!"".equals(s)){
            PropertyPathTF.setText(s);
        }
    }//GEN-LAST:event_PropertyPathBTActionPerformed

    private void InputCSVFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputCSVFilesButtonActionPerformed
        String s = getFile(true);
        if (!"".equals(s)){
            InputCSVFilesTF.setText(s);
        }
    }//GEN-LAST:event_InputCSVFilesButtonActionPerformed

    private void EmptyDfMDirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmptyDfMDirButtonActionPerformed
        String s = getFile(true);
        if (!"".equals(s)){
            EmptyDfMDirTF.setText(s);
        }
    }//GEN-LAST:event_EmptyDfMDirButtonActionPerformed

    private void OutputDirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OutputDirButtonActionPerformed
        String s = getFile(true);
        if (!"".equals(s)){
            OutputDirTF.setText(s);
        }
    }//GEN-LAST:event_OutputDirButtonActionPerformed

    private void dictdconvMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dictdconvMenuActionPerformed
        
   }//GEN-LAST:event_dictdconvMenuActionPerformed

    private void miProceedConvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miProceedConvertActionPerformed
        DictdConvProceedAll();
    }//GEN-LAST:event_miProceedConvertActionPerformed

    private void miEditSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditSettingsActionPerformed
        clearFields();
    }//GEN-LAST:event_miEditSettingsActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        showAbout();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JarCreatorProceedAll();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clearJCFields();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clearDGFields();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        checkScreenResolution();
        showPropWin();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        DictGenProceedAll();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        clearDGFields();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        clearJCFields();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        JarCreatorProceedAll();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        editExistingPropFile();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void infotareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infotareaActionPerformed

    }//GEN-LAST:event_infotareaActionPerformed

    private void dictGenMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dictGenMenuActionPerformed

    }//GEN-LAST:event_dictGenMenuActionPerformed

    private void jarCreatorMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jarCreatorMenuActionPerformed
        
    }//GEN-LAST:event_jarCreatorMenuActionPerformed

    private void dictdconvMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dictdconvMenuMouseClicked
        
    }//GEN-LAST:event_dictdconvMenuMouseClicked

    private void dictGenMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dictGenMenuMouseClicked
        
    }//GEN-LAST:event_dictGenMenuMouseClicked

    private void dictdconvMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dictdconvMenuMousePressed
        DFMBuilderTabbedPane.setSelectedComponent(DictdConvPanel);
    }//GEN-LAST:event_dictdconvMenuMousePressed

    private void dictGenMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dictGenMenuMousePressed
        DFMBuilderTabbedPane.setSelectedComponent(DictGenPanel);
    }//GEN-LAST:event_dictGenMenuMousePressed

    private void jarCreatorMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jarCreatorMenuMousePressed
        DFMBuilderTabbedPane.setSelectedComponent(JarCreatorPanel);
    }//GEN-LAST:event_jarCreatorMenuMousePressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        showCSVFileCheckWin();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        showPrefsWin();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void miContentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miContentsActionPerformed
        showHelpWindow();
    }//GEN-LAST:event_miContentsActionPerformed

    private void fontGenMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontGenMenuActionPerformed

   }//GEN-LAST:event_fontGenMenuActionPerformed

    private void fontGenMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fontGenMenuMousePressed
        DFMBuilderTabbedPane.setSelectedComponent(bfgPanel);
    }//GEN-LAST:event_fontGenMenuMousePressed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        /*
         * FontToolkit ft = new FontToolkit();
         * ft.startButton.addActionListener(ft); ft.startButton.doClick();
         */
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    /**
     * printCopyrightNotice() prints a copyright
     * statement and the exits.
     */
    public static void printCopyrightNotice(){
    System.out.println("\n\nDictionaryForMIDs-Creator V" + dfm_creator_version + ","
                    + "(C) 2012 Karim Mahamane Karimou\n"
                    + "DictionaryForMIDs-Creator comes with ABSOLUTELY NO WARRANTY.\n"
                    + "This is free software, and you are welcome to redistribute it\n"
                    + "under the terms and conditions of the GNU General Public License.\n\n");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // set locale
        setTheLocale();        
        // print a copyright notice
        // just for fun...
        printCopyrightNotice();
        // Apply preferences. Among other
        // preferences, look and feel settings
        applyPreferences();

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DfMCreatorMain dfmCreator = new DfMCreatorMain();
                dfmCreator.setLocation(screenSize.width / 2 - dfmCreator.getWidth() / 2,
                                     screenSize.height / 2 - dfmCreator.getHeight() / 2);
                dfmCreator.setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ButtonsPanel;
    private javax.swing.JRadioButton CRRB;
    private javax.swing.JLabel CarRetLabel;
    private javax.swing.JLabel ChooseSCLabel;
    private javax.swing.JButton DBBrowseButton;
    private javax.swing.JLabel DBBrowseLabel;
    private javax.swing.JLabel DBNameLabel;
    private javax.swing.JTextField DBNameTextField;
    private javax.swing.JPanel DBPanel;
    private javax.swing.JLabel DBPathLabel;
    private javax.swing.JTextField DBPathTextField;
    private static javax.swing.JTabbedPane DFMBuilderTabbedPane;
    private javax.swing.JPanel DictGenPanel;
    private javax.swing.JPanel DictdConvPanel;
    private javax.swing.JButton DirectoryDestinationBrowseBT;
    private javax.swing.JLabel DirectoryDestinationBrowseLB;
    private javax.swing.JLabel DirectoryDestinationLabel;
    private javax.swing.JTextField DirectoryDestinationTF;
    private javax.swing.JButton EmptyDfMDirButton;
    private javax.swing.JTextField EmptyDfMDirTF;
    public javax.swing.ButtonGroup EncGroupBut;
    private javax.swing.JLabel EncodingLabel;
    private javax.swing.JPanel EncodingPanel;
    private javax.swing.JRadioButton FFRB;
    private javax.swing.JLabel FormFeedLabel;
    private javax.swing.JButton GenDictFilesBT;
    private javax.swing.JLabel IBMLabel;
    private javax.swing.JRadioButton IBMRB;
    private javax.swing.JLabel ISO1Label;
    private javax.swing.JRadioButton ISO1RB;
    private javax.swing.JLabel ISO2Label;
    private javax.swing.JRadioButton ISO2RB;
    private javax.swing.JLabel ISO50Label;
    private javax.swing.JRadioButton ISO50RB;
    private javax.swing.JButton InputCSVFilesButton;
    private javax.swing.JTextField InputCSVFilesTF;
    private javax.swing.JPanel JarCreatorPanel;
    private javax.swing.JCheckBox KTCheckBox;
    private javax.swing.JLabel KTLabel;
    private javax.swing.JRadioButton OWNRB;
    private javax.swing.JButton OutCSVFileBrowseButton;
    private javax.swing.JLabel OutCSVFileBrowseLabel;
    private javax.swing.JTextField OutCSVFileTextField;
    private javax.swing.JLabel OutCVSFilePathLabel;
    private javax.swing.JButton OutputDirButton;
    private javax.swing.JTextField OutputDirTF;
    private javax.swing.JLabel OwnEncLabel;
    private javax.swing.JTextField OwnEncTextField;
    private javax.swing.JLabel OwnLabel;
    private javax.swing.JRadioButton OwnSCRB;
    private javax.swing.JTextField OwnSCTextField;
    private javax.swing.JButton PropertyPathBT;
    private javax.swing.JLabel PropertyPathBrowseLB;
    private javax.swing.JLabel PropertyPathLabel;
    private javax.swing.JTextField PropertyPathTF;
    private javax.swing.JCheckBox RSBCheckBox;
    private javax.swing.JLabel RSBLabel;
    private javax.swing.ButtonGroup SCGroupBut;
    private javax.swing.JPanel SCPanel;
    private javax.swing.JCheckBox SLCheckBox;
    private javax.swing.JLabel SLLabel;
    private javax.swing.JPanel SLPanel;
    private javax.swing.JButton SourceFileBrowseBT;
    private javax.swing.JLabel SourceFileBrowseLB;
    private javax.swing.JLabel SourceFileLabel;
    private javax.swing.JTextField SourceFileTF;
    private javax.swing.JLabel TabLabel;
    private javax.swing.JRadioButton TabRB;
    private javax.swing.JLabel UTF16Label;
    private javax.swing.JRadioButton UTF16RB;
    private javax.swing.JLabel UTFBELabel;
    private javax.swing.JRadioButton UTFBERB;
    private javax.swing.JLabel UTFLELabel;
    private javax.swing.JRadioButton UTFLERB;
    private javax.swing.JLabel UTFLable;
    private javax.swing.JRadioButton UTFRB;
    private javax.swing.JPanel bfgPanel;
    private javax.swing.JButton clearFieldsButton;
    private javax.swing.JMenuBar dfmBuilderMenuBar;
    private javax.swing.JMenu dictGenMenu;
    private javax.swing.JPanel dictdPanel;
    private javax.swing.JMenu dictdconvMenu;
    private javax.swing.JMenu fontGenMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JTextField infotarea;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenu jarCreatorMenu;
    private javax.swing.JMenuItem miContents;
    private javax.swing.JMenuItem miEditSettings;
    private javax.swing.JMenuItem miProceedConvert;
    private javax.swing.JButton proceedButton;
    private javax.swing.JToolBar toolbar1;
    private javax.swing.JToolBar toolbar2;
    private javax.swing.JToolBar toolbar4;
    // End of variables declaration//GEN-END:variables
    
    
    /*
     * 
     * miscellaneous subroutiness
     * 
     */
    
    
    /**
     * showPrefsWin gets the preferences window.
     */
    private void showPrefsWin(){
        DfMCreatorPreferences prefs = DfMCreatorPreferences.getPrefsWin();
        prefs.setLocation(screenSize.width / 2 - prefs.getWidth() / 2,
                        screenSize.height / 2 - prefs.getHeight() / 2);
        prefs.setVisible(true);
    }
    
    /**
     * showHelpWindow() gets the help window
     */
    private void showHelpWindow() {
        JFrame hw = DfMCreatorHelpWin.createAndShowGUI();
        hw.setLocation(screenSize.width / 2 - hw.getWidth() / 2,
                        screenSize.height / 2 - hw.getHeight() / 2);
        hw.setVisible(true);        
    }

   /**
    * savePreferences() saves the preferences
    * settings.
    * @param look the selected look and feel
    */
   public static void savePreferences(String look) {
      try {
         node.put("DfMCreator.window.LookAndFeel", look);
         printAnyMsg(I18n.tr("lookAndFeelMsg.dfmCreatorMain"),
                     I18n.tr("prefsSaved.dfmCreatorMain"), JOptionPane.INFORMATION_MESSAGE);
      }
      catch (Throwable t) {
          System.out.println(t + "\n" + t.getCause());
      }
   }
   
    /**
     * applyPreferences() applies the saved
     * preferences at program startup.
     */
    public static void applyPreferences() {
      try {
         if (! root.nodeExists(pathName)){
	    try {
                // Set the system's default look and feel
	        javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                // Save the look and feel settings
                savePreferences(javax.swing.UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException t) {
                System.out.println(t + "\n" + t.getCause());
	    }
         } else {
	     if (LookAndFeel != null) {
	        try {
                    javax.swing.UIManager.setLookAndFeel(LookAndFeel);
	        }
	        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException t) {
                    System.out.println(t.getLocalizedMessage() + "\n" + t.getCause());
	        }
	     }
	  }
      }
      catch (Throwable t) {
          System.out.println(t + "\n" + t.getCause());
      }
   }
    
    private void OwnTextFieldCheck(){
        if (OwnSCTextField.isEnabled()) {
            OwnSCTextField.setEnabled(false);
        }        
    }
    
    private void OwnEncTextFieldCheck(){
        if (OwnEncTextField.isEnabled()){
            OwnEncTextField.setEnabled(false);
        }
    }
    
    /**
     * trim() removes trailing slashes or
     * backslashes at the end of a string
     * @param s the string to be trimmed.
     * @return 
     */
    private String trim(String s){
        switch (s.substring(s.length() - 1, s.length())) {
            case "/":
                s = s.substring(0, s.length() - 1);
                break;
            case "\\":
                s = s.substring(0, s.length() - 1);
                break;
        }
        return s;
    }
    
    /**
     * prints a JOptionPane message dialog.
     * @param message the text of the message
     * @param winText the title of the windows
     * @param infType the type of the message
     */
    public static void printAnyMsg(String message, String winText, int infType){
        JOptionPane.showMessageDialog(null, message, winText, infType);
    }
    
    /**
     * getFile() shows a file chooser to let the user
     * choose a file and returns the file's path
     * @param dirsOnly whether or not let the user
     * be able to choose files or directories.
     * @return the absolute path of the file.
     */
    private String getFile(boolean dirsOnly) {
        Path returnedPath;
        String returnedPathString;
        
	JFileChooser chooser = new JFileChooser();
	if (dirsOnly) {
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	} else {
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        }
	int valueReturned = chooser.showOpenDialog(this);
	if (valueReturned == JFileChooser.APPROVE_OPTION){
            
            returnedPath = Paths.get(chooser.getSelectedFile().getAbsolutePath());
            returnedPath = returnedPath.normalize();            
            returnedPathString = returnedPath.toString();            
            
             return returnedPathString;
        }
        else {
            return "";
        }
    }
    
    /**
     * setDictdToDfMVals() checks the validity of the values entered by
     * the user and sets them up for DictdToDfM if they are valid.
     * @throws FileNotFoundException
     * @throws de.kugihan.DfMCreator.DfMCreatorException.DBNameTextFieldIsEmpty
     * @throws de.kugihan.DfMCreator.DfMCreatorException.DBPathTextFieldIsEmpty
     * @throws de.kugihan.DfMCreator.DfMCreatorException.OutCSVFileTextFieldIsEmpty
     * @throws de.kugihan.DfMCreator.DfMCreatorException.DBFolderNotAccessible
     * @throws de.kugihan.DfMCreator.DfMCreatorException.DBINIFileNotAccessible
     * @throws de.kugihan.DfMCreator.DfMCreatorException.OutCSVFileCantBeWritten 
     */
    private void setDictdToDfMVals() throws FileNotFoundException, DBNameTextFieldIsEmpty,
                                            DBPathTextFieldIsEmpty, OutCSVFileTextFieldIsEmpty,
                                            DBFolderNotAccessible, DBINIFileNotAccessible,
                                            OutCSVFileCantBeWritten {
        
        if ("".equals(DBNameTextField.getText())){
            throw new DBNameTextFieldIsEmpty(I18n.tr("dbFieldEmpty1.dfmCreatorMain")
                                           + I18n.tr("dbFieldEmpty2.dfmCreatorMain"));
        }

        if ("".equals(DBPathTextField.getText())){
            throw new DBPathTextFieldIsEmpty(I18n.tr("dbLocation.dfmCreatorMain")
                                           + I18n.tr("dbLocation2.dfmCreatorMain"));
        }
        
        if ("".equals(OutCSVFileTextField.getText())){
            throw new OutCSVFileTextFieldIsEmpty(I18n.tr("outputFileEmpty.dfmCreatorMain")
                                               + I18n.tr("outputFileEmpty2.dfmCreatorMain"));
        }
        
	String dbNameVarString = DBNameTextField.getText();
	String dbFolderNameVarString = DBPathTextField.getText();
	String outputCSVFileVarString = OutCSVFileTextField.getText();
        
        dbNameVarString = trim(dbNameVarString);
        dbFolderNameVarString = trim(dbFolderNameVarString);
        outputCSVFileVarString = trim(outputCSVFileVarString);
        
        File dirFile1 = new File(dbFolderNameVarString);
        if (!dirFile1.isDirectory() || !dirFile1.canRead()){
                throw new DBFolderNotAccessible(I18n.tr("dbNotAccessible.dfmCreatorMain"));
        }        
        
        File inFile = new File(dbFolderNameVarString +PATH_SEPARATOR+ dbNameVarString + ".ini");
	if (!inFile.canRead()){
		throw new DBINIFileNotAccessible(I18n.tr("dbINI.dfmCreatorMain"));
	}
	
	File dirFile = new File(outputCSVFileVarString);
	if (!dirFile.isDirectory() || !dirFile.canRead()){
		throw new OutCSVFileCantBeWritten(I18n.tr("outputFileAccessError.dfmCreatorMain"));
        }
        
        Path dbNameVarPath = Paths.get(dbNameVarString);
        dbNameVarPath = dbNameVarPath.normalize();
        dbNameVar = dbNameVarPath.toString();
        
        Path dbFolderNamePath = Paths.get(dbFolderNameVarString);
        dbFolderNamePath = dbFolderNamePath.normalize();
        dbFolderNameVar = dbFolderNamePath.toString();
        
        Path outputCSVFileVarPath = Paths.get(outputCSVFileVarString);
        outputCSVFileVarPath = outputCSVFileVarPath.normalize();
        outputCSVFileVar = outputCSVFileVarPath.toString() + PATH_SEPARATOR + DBNameTextField.getText();
        
        /*
         * Checking the radio buttons and setting the value of
         * outputEncodingCharsetVar accordingly.
         */
        if (UTFRB.isSelected()){
            outputEncodingCharsetVar = "UTF-8";
        }
        else
            if (UTFLERB.isSelected()){
                outputEncodingCharsetVar = "UTF-16LE";
            }
        else
            if (UTFBERB.isSelected()){
                outputEncodingCharsetVar = "UTF-16BE";
            }
        else
            if (UTF16RB.isSelected()){
                outputEncodingCharsetVar = "UTF-16";
            }
        else
            if (ISO1RB.isSelected()){
                outputEncodingCharsetVar = "ISO-8859-1";
            }
        else
            if (ISO2RB.isSelected()){
                outputEncodingCharsetVar = "ISO-8859-2";
            }
        else
            if (IBMRB.isSelected()){
                outputEncodingCharsetVar = "IBM-2020-50";
            }
        else
            if (ISO50RB.isSelected()){
                outputEncodingCharsetVar = "UTF-2020-50";
            }
        else
            if (OWNRB.isSelected()){
                if (OwnEncTextField.isEnabled()){
                    if (!"".equals(OwnEncTextField.getText())){
                        outputEncodingCharsetVar = OwnEncTextField.getText();
                    }
                    else {
                        throw new FileNotFoundException();
                    }
                }
            }
        
        /*
         * Checking the other radio buttons and setting the
         * value of separatorCharacterVar accordingly.
         */        
        if (TabRB.isSelected()){
            separatorCharacterVar = '\t';
        }
        else 
            if (CRRB.isSelected()){
                separatorCharacterVar = '\r';
            }
        else
            if (FFRB.isSelected()){
                separatorCharacterVar = '\f';
            }
        else
            if (OwnSCRB.isSelected()){
                if (OwnSCTextField.isEnabled()){
                    if (!"".equals(OwnSCTextField.getText())){
                        separatorCharacterVar = OwnSCTextField.getText().charAt(0);
                    }
                    else {
                        throw new FileNotFoundException();
                    }
                }
            }
        
        /*
         * Checking the checkboxes and setting the values of
         * keepTabsAndNewLinesVar, switchLanguagesVar
         * and removeSquareBracketsVar.
         */
        if (SLCheckBox.isSelected()){
            switchLanguagesVar = true;
        }
        else {
            switchLanguagesVar = false;
        }
        
        if (KTCheckBox.isSelected()){
            keepTabsAndNewLinesVar = true;
        }
        else {
            keepTabsAndNewLinesVar = false;
        }
        
        if (RSBCheckBox.isSelected()){
            removeSquareBracketsVar = true;
        }
        else {
            removeSquareBracketsVar = false;
        }
        
        // Setting the values for dictdconv.        
        // Setting Sting values.
        DictdToDfM.setDBName(dbNameVar);        
        DictdToDfM.setDBFolderName(dbFolderNameVar);        
        DictdToDfM.setOutputCSVFile(outputCSVFileVar);      
        DictdToDfM.setOutputEncodingCharset(outputEncodingCharsetVar);
        
        // Setting Char value.
        DictdToDfM.setSeparatorCharacter(separatorCharacterVar);
        
        // Setting boolean values.
        DictdToDfM.setKeepTabAndNewLineChars(keepTabsAndNewLinesVar);        
        DictdToDfM.setSwitchLanguages(switchLanguagesVar);        
        DictdToDfM.setRemoveSquareBrackets(removeSquareBracketsVar);
        
        // For the actual call of DictdToDfM to do
        // the actual convertion, refer to the DictdConvSummary.java class.
    }
    
    /**
     * resets the setting.
     */
    private void clearFields() {
        DBNameTextField.setText("");
        DBPathTextField.setText("");
        OutCSVFileTextField.setText("");
    }
    
    /**
     * this method gathers all the required
     * things needed to be done in order to
     * convert a dict file with DictdToDfM.
     */
    private void DictdConvProceedAll() {
        try {
            setDictdToDfMVals();
            DictdToDfM.printDictdConvSummary();            
        } catch (FileNotFoundException ex) {
            DfMCreatorException.printErrorMsg();
        } catch (DBNameTextFieldIsEmpty e){
            printAnyMsg(DfMCreatorException.DBNameTextFieldIsEmptyMsg, I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DBPathTextFieldIsEmpty e){
            printAnyMsg(DfMCreatorException.DBPathTextFieldIsEmptyMsg, I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (OutCSVFileTextFieldIsEmpty e){
            printAnyMsg(DfMCreatorException.OutCSVFileTextFieldIsEmptyMsg, I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DBFolderNotAccessible e){
            printAnyMsg(DfMCreatorException.DBFolderNotAccessibleMsg, I18n.tr("dirNotAccessible.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DBINIFileNotAccessible e){
            printAnyMsg(DfMCreatorException.DBINIFileNotAccessibleMsg, I18n.tr("fileNotAccessible.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (OutCSVFileCantBeWritten e){
            printAnyMsg(DfMCreatorException.OutCSVFileCantBeWrittenMsg, I18n.tr("dirNotAccessible.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (Throwable t){
        	printAnyMsg(I18n.tr("unknownException.dfmCreatorMain", new Object[] {t, t.getLocalizedMessage()}), I18n.tr("unknownExceptionTitle"), JOptionPane.ERROR_MESSAGE);
            System.out.println(t + "\n");
        }
    }
    

    /**
     * gets the about window
     */
    private void showAbout() {
        AboutBox aboutbox = AboutBox.getAboutBox();
        aboutbox.setSize(525, 485);
        aboutbox.setModal(true);
        aboutbox.setLocation(screenSize.width / 2 - aboutbox.getWidth() / 2,
                          screenSize.height / 2 - aboutbox.getHeight() / 2);
        aboutbox.setVisible(true);
    }
    

    /*
    * 
    * DictionaryGneration subroutines
    * 
    */
    
    /**
     * checkScreenResolution() checks the resolution
     * of the screen just before calling DfMPropCreate
     */
    public void checkScreenResolution(){
        if (screenSize.getHeight() < DfMPropCreate.Default_height_2_Langs ||
            screenSize.getWidth() < DfMPropCreate.Default_width) {
            JOptionPane.showMessageDialog(null, I18n.tr("screenResolution.dfmCreatorMain"),
                                   I18n.tr("screenResolutionTitle.dfmCreatorMain"), JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

    /**
     * showPropWin() gets the DfMPropCreate Window.
     */
    public void showPropWin(){
        DfMPropCreate propWin = DfMPropCreate.getPropWin();        
        // this subroutine will set the selected index
        // of the combo box to "2" languages and hide all
        // textfields, labels, buttons related to language-3
        // it also resizes the properties window accordingly
        propWin.updateNumOfLang();
        propWin.setLocation(screenSize.width / 2 - propWin.getWidth() / 2,
                          screenSize.height / 2 - propWin.getHeight() / 2);
        propWin.setVisible(true);
    }
    
    /**
     * editExistingPropFile() Edits and existing
     * DictionaryForMIDs.properties file.
     */
    public void editExistingPropFile(){
        DfMPropCreate propWin = DfMPropCreate.getPropWin();
        propWin.setVisible(false);
        propWin.editExistingPropFile();
    }
    
    /**
     * setDictGenVals() checks and sets the values up
     * for DictionaryGeneration
     * @throws FileNotFoundException
     * @throws de.kugihan.DfMCreator.DfMCreatorException.PropFileErrorException
     * @throws de.kugihan.DfMCreator.DfMCreatorException.SourceFileTFIsEmpty
     * @throws de.kugihan.DfMCreator.DfMCreatorException.DirectoryDestinationTFIsEmpty
     * @throws de.kugihan.DfMCreator.DfMCreatorException.PropertyPathTFIsEmpty
     * @throws de.kugihan.DfMCreator.DfMCreatorException.SourceFileNotAccessible
     * @throws de.kugihan.DfMCreator.DfMCreatorException.SourceFileNotAccessible
     * @throws de.kugihan.DfMCreator.DfMCreatorException.PropertyPathNotAccessible
     * @throws de.kugihan.DfMCreator.DfMCreatorException.DirectoryDestinationNotAccessible
     * @throws de.kugihan.DfMCreator.DfMCreatorException.CantCreateDestDir 
     */
    public void setDictGenVals() throws FileNotFoundException, PropFileErrorException,
                                     SourceFileTFIsEmpty, DirectoryDestinationTFIsEmpty,
                                     PropertyPathTFIsEmpty, SourceFileNotAccessible,
                                     SourceFileNotAccessible, PropertyPathNotAccessible,
                                     DirectoryDestinationNotAccessible, CantCreateDestDir {
        
        if ("".equals(SourceFileTF.getText())){
            throw new SourceFileTFIsEmpty(I18n.tr("srcFileField.dfmCreatorMain"));
        }
        
        if ("".equals(DirectoryDestinationTF.getText())){
            throw new DirectoryDestinationTFIsEmpty(I18n.tr("destDirField.dfmCreatorMain"));
        }
        
        if ("".equals(PropertyPathTF.getText())){
            throw new PropertyPathTFIsEmpty(I18n.tr("propFilePath.dfmCreatorMain"));
        }
        
        String sourceFileString = SourceFileTF.getText();
        String directoryDestinationString = DirectoryDestinationTF.getText();
        String propertyPathString = PropertyPathTF.getText();
        
        sourceFileString = trim(sourceFileString);
        directoryDestinationString = trim(directoryDestinationString);
        propertyPathString = trim(propertyPathString);
        
        Path sourceFilePath = Paths.get(sourceFileString);
        sourceFilePath = sourceFilePath.normalize();
        sourceFile = sourceFilePath.toString();
        
        Path directoryDestinationPath = Paths.get(directoryDestinationString);
        directoryDestinationPath = directoryDestinationPath.normalize();
        directoryDestination = directoryDestinationPath.toString();
        
        Path propertyPathPath = Paths.get(propertyPathString);
        propertyPathPath = propertyPathPath.normalize();
        propertyPath = propertyPathPath.toString();
        
        File srcFile = new File(sourceFileString);
        if (!srcFile.exists() || !srcFile.canRead()){
            throw new SourceFileNotAccessible("");
        }
        
        File destDir = new File(directoryDestinationString);
        if (!destDir.exists() || !destDir.canRead() || !destDir.isDirectory() || !destDir.canWrite()){
            throw new DirectoryDestinationNotAccessible(I18n.tr("destDirAccessError.dfmCreatorMain"));
        }
        
        File propPath = new File(propertyPathString);
        if (!propPath.exists() || !propPath.canRead() || !propPath.isDirectory()){
            throw new PropertyPathNotAccessible(I18n.tr("dictPropsFilePath.dfmCreatorMain"));
        }
        
        File propFile = new File(propertyPathString + PATH_SEPARATOR + DictionaryDataFile.propertyFileName);
        if (!propFile.exists() || !propFile.canRead()){
            throw new PropFileErrorException(I18n.tr("dictPropFileAccessError.dfmCreatorMain"));
        }
        
        File destCSVsDir = new File(directoryDestinationString + PATH_SEPARATOR + "dictionary");
        if (!destCSVsDir.exists()){
            if (!destCSVsDir.mkdirs()){
                throw new CantCreateDestDir(I18n.tr("destDirCreationError.dfmCreatorMain"));
            }
        }
        // Passing the values to dictgen
        DictionaryGeneration.setSourceFile(sourceFile);
        DictionaryGeneration.setDirectoryDestination(directoryDestination + PATH_SEPARATOR + "dictionary");
        DictionaryGeneration.setPropertyPath(propertyPath);

    }
    
    private void clearDGFields() {
        SourceFileTF.setText("");
        DirectoryDestinationTF.setText("");
        PropertyPathTF.setText("");
    }
    
    /**
     * this method will execute everything for
     * DictionaryGeneration. it's like the
     * actionPerformed subroutine for dictgen.
     */
    private void DictGenProceedAll() {
        try {
            setDictGenVals();
            DictionaryGeneration.showDictGenSummary();
        } catch (FileNotFoundException ex) {
            DfMCreatorException.printErrorMsg();
        } catch (PropFileErrorException e) {
            printAnyMsg(DfMCreatorException.PropFileErrorExceptionMsg, I18n.tr("propsFileError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (SourceFileTFIsEmpty e){
            printAnyMsg(DfMCreatorException.SourceFileTFIsEmptyMsg, I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DirectoryDestinationTFIsEmpty e){
            printAnyMsg(DfMCreatorException.DirectoryDestinationTFIsEmptyMsg, I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (PropertyPathTFIsEmpty e){
            printAnyMsg(DfMCreatorException.PropertyPathTFIsEmptyMsg, I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (SourceFileNotAccessible e){
            printAnyMsg(DfMCreatorException.SourceFileNotAccessibleMsg, I18n.tr("fileNotAccessible.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (PropertyPathNotAccessible e){
            printAnyMsg(DfMCreatorException.PropertyPathNotAccessibleMsg, I18n.tr("DictDirAccessError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DirectoryDestinationNotAccessible e){
            printAnyMsg(DfMCreatorException.DirectoryDestinationNotAccessibleMsg, I18n.tr("dictDirAccessError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (CantCreateDestDir e){
            printAnyMsg(e.getLocalizedMessage(), I18n.tr("dictCreationError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());     
        } catch (Throwable t){
        	printAnyMsg(I18n.tr("unknownError.dfmCreatorMain", new Object[] {t, t.getLocalizedMessage()}), I18n.tr("unknownErrorTitle"), JOptionPane.ERROR_MESSAGE);
            System.out.println(t + "\n");
        }
    }
    
    /**
     * showCSVFileCheckWin() gets the csvfile check window.
     */
    private void showCSVFileCheckWin(){
    	CheckCSVFileIntegrity csvWin = CheckCSVFileIntegrity.getCSVCheckWin();
        csvWin.setLocation(screenSize.width / 2 - csvWin.getWidth() / 2,
                        screenSize.height / 2 - csvWin.getHeight() / 2);
        csvWin.setVisible(true);
    }
        
    
    /*
     * 
     * JarCreator subroutines
     * 
     */
    
    /**
     * setJCVals() checks and sets the values up for JarCreator.
     * @throws FileNotFoundException
     * @throws de.kugihan.DfMCreator.DfMCreatorException.BadDictDirNameException
     * @throws de.kugihan.DfMCreator.DfMCreatorException.DictionaryDirectoryNotAccessible
     * @throws de.kugihan.DfMCreator.DfMCreatorException.PropFileErrorException
     * @throws de.kugihan.DfMCreator.DfMCreatorException.EmptyDfMJarJadDirDoesNotExist
     * @throws de.kugihan.DfMCreator.DfMCreatorException.EmptyDfMFileNotFound
     * @throws de.kugihan.DfMCreator.DfMCreatorException.OutputDirectoryNotAccessible
     * @throws de.kugihan.DfMCreator.DfMCreatorException.InputCSVFilesTFIsEmpty
     * @throws de.kugihan.DfMCreator.DfMCreatorException.EmptyDfMDirTFIsEmpty
     * @throws de.kugihan.DfMCreator.DfMCreatorException.OutputDirTFIsEmpty 
     */
    public void setJCVals() throws FileNotFoundException, BadDictDirNameException,
                                   DictionaryDirectoryNotAccessible, PropFileErrorException,
                                   EmptyDfMJarJadDirDoesNotExist, EmptyDfMFileNotFound,
                                   OutputDirectoryNotAccessible, InputCSVFilesTFIsEmpty,
                                   EmptyDfMDirTFIsEmpty, OutputDirTFIsEmpty {
        
        if ("".equals(InputCSVFilesTF.getText())){
            throw new InputCSVFilesTFIsEmpty(I18n.tr("inputFileFieldEmpty.dfmCreatorMain"));
        }
        
        if ("".equals(EmptyDfMDirTF.getText())){
            throw new EmptyDfMDirTFIsEmpty(I18n.tr("emptyDfM.dfmCreatorMain"));
        }
        
        if ("".equals(OutputDirTF.getText())){
            throw new OutputDirTFIsEmpty(I18n.tr("outputDirField.dfmCreatorMain"));
        }
        
        dictionarydirectory = InputCSVFilesTF.getText();
        emptydictionaryformids = EmptyDfMDirTF.getText();
        outputdirectory = OutputDirTF.getText();
        
        // We remove any trailling slash/backslash that
        // the user might have introduced in the path
        // we want to remothe the ones the user introduced
        // because,farther,we will add them and we dont want
        // to end up with thwo slashes/backslaches...
        dictionarydirectory = trim(dictionarydirectory);
        emptydictionaryformids = trim(emptydictionaryformids);
        outputdirectory = trim(outputdirectory);
        
        // We add a system specific path_separator.
        emptydictionaryformids = emptydictionaryformids + PATH_SEPARATOR;
        outputdirectory = outputdirectory + PATH_SEPARATOR;
        
                
        File dictdir = new File(dictionarydirectory);
        if (!dictdir.exists() || !dictdir.canRead() || !dictdir.isDirectory()){
            throw new DictionaryDirectoryNotAccessible(I18n.tr("dictDirAccessError.dfmCreatorMain"));
        }
        
        File emptydfmdir = new File(emptydictionaryformids);
        if (!emptydfmdir.exists() || !emptydfmdir.canRead() || !emptydfmdir.isDirectory()){
            throw new EmptyDfMJarJadDirDoesNotExist(I18n.tr("emptyJarJad.dfmCreatorMain"));
        }
        
        File outdir = new File(outputdirectory);
        if (!outdir.exists() || !outdir.canRead() || !outdir.isDirectory()){
            throw new OutputDirectoryNotAccessible(I18n.tr("outputDirAccessError.dfmCreatorMain"));
        }
        
	if (!dictionarydirectory.endsWith(DictionaryDataFile.pathNameDataFiles)){            
            throw new BadDictDirNameException(I18n.tr("dirNameError.dfmCreatorMain"));
	}
        
        File propfile = new File(dictionarydirectory + PATH_SEPARATOR + DictionaryDataFile.propertyFileName);
        if (!propfile.exists() || !propfile.canRead()){
            throw new PropFileErrorException(I18n.tr("dictPropsFileAccessError.dfmCreatorMain"));
        }
        
        File emptydfm = new File(emptydictionaryformids + PATH_SEPARATOR + JarCreator.FILE_EMPTY_JAR_NAME);
        if (!emptydfm.exists() || !emptydfm.canRead()){
            throw new EmptyDfMFileNotFound(I18n.tr("emptyJarNotFound.dfmCreatorMain"));
        }
        
        // Setting values for JarCreator
        JarCreator.setDictionaryDirectory(dictionarydirectory + PATH_SEPARATOR);
        JarCreator.setEmptyDictionaryForMID(emptydictionaryformids);
        JarCreator.setOutputDirectory(outputdirectory);

    }

    /**
     * clearJCFields () resets the settings for JarCreator.
     */
    private void clearJCFields() {
        InputCSVFilesTF.setText("");
        EmptyDfMDirTF.setText("");
        OutputDirTF.setText("");
    }
    
    /**
     * this method will execute everything for
     * JarCreator. it's like the actionPerformed
     * subroutine for JarCreator.
     */
    private void JarCreatorProceedAll() {
        try {
            setJCVals();
            JarCreator.showJarCreationSum();
        } catch (FileNotFoundException ex){
            DfMCreatorException.printErrorMsg();
        } catch (BadDictDirNameException e){
            printAnyMsg(DfMCreatorException.BadDictDirNameExceptionMsg, I18n.tr("badDirNameTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (PropFileErrorException e){
            printAnyMsg(DfMCreatorException.PropFileErrorExceptionMsg, I18n.tr("propsFileErrorTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (EmptyDfMFileNotFound e){
            printAnyMsg(DfMCreatorException.EmptyDfMFileNotFoundMsg, I18n.tr("emptyDfMTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (InputCSVFilesTFIsEmpty e){
            printAnyMsg(DfMCreatorException.InputCSVFilesTFIsEmptyMsg, I18n.tr("filedEmptyErrorTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (EmptyDfMDirTFIsEmpty e){
            printAnyMsg(DfMCreatorException.EmptyDfMDirTFIsEmptyMsg, I18n.tr("fieldEmptyErrorTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (OutputDirTFIsEmpty e){
            printAnyMsg(DfMCreatorException.OutputDirTFIsEmptyMsg, I18n.tr("fieldEmptyErrorTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (OutputDirectoryNotAccessible e){
            printAnyMsg(DfMCreatorException.OutputDirectoryNotAccessibleMsg, I18n.tr("dictAccessTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (EmptyDfMJarJadDirDoesNotExist e){
            printAnyMsg(DfMCreatorException.EmptyDfMJarJadDirDoesNotExistMsg, I18n.tr("emptyJarJadTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DictionaryDirectoryNotAccessible e){
            printAnyMsg(DfMCreatorException.DictionaryDirectoryNotAccessibleMsg, I18n.tr("dictAccessTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (Throwable t){
        	printAnyMsg(I18n.tr("unknownRuntimeError.dfmCreatorMain", new Object[] {t, t.getLocalizedMessage()}), I18n.tr("unknownRuntimeErrorTitle"), JOptionPane.ERROR_MESSAGE);
            System.out.println(t + "\n");
        }
    }
 
    
}