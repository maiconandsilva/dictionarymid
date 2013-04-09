/* ////////////////////////////////////////////////////////////////
*
*   In the Name of Allah
*
*   DICTIONARYFORMIDS-CREATOR
*
*   This file is part of DictionaryForMIDs-Creator
*   Copyright (C) 2012, 2013 Karim Mahamane Karimou
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
import de.kugihan.DfMCreator.DfMCreatorException.NoDictFileFound;
import de.kugihan.DfMCreator.DfMCreatorException.NoIndexFileFound;
import de.kugihan.DfMCreator.DfMCreatorException.OutCSVFileCantBeWritten;
import de.kugihan.DfMCreator.DfMCreatorException.OutCSVFileTextFieldIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.OutputDirTFIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.OutputDirectoryNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.PropFileErrorException;
import de.kugihan.DfMCreator.DfMCreatorException.PropertyPathNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.PropertyPathTFIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.SourceFileNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.SourceFileTFIsEmpty;
import de.kugihan.DfMCreator.preferencesbox.PreferencesBox;
import de.kugihan.DfMCreator.propertieseditor.PropertiesEditor;
import de.kugihan.DfMCreator.utils.CheckCSVFileIntegrity;
import de.kugihan.dictionaryformids.dataaccess.DictionaryDataFile;
import de.kugihan.dictionaryformids.dictdtodictionaryformids.DictdToDfM;
import de.kugihan.dictionaryformids.dictgen.DictionaryGeneration;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.fonttoolkit.Callback;
import de.kugihan.fonttoolkit.FontToolkit;
import de.kugihan.jarCreator.JarCreator;
import edu.hws.eck.mdb.I18n;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.prefs.Preferences;
import javax.swing.*;


public class DfMCreatorMain extends javax.swing.JFrame {
    // version number.
    public static final String dfm_creator_version = "0.6-Beta";

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

    // System specific path separator for file systems: / in linux \ in windows...
    public static String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    // path separator for JAR files (accessed via Class.getResourceAsStream();
	public static String JAR_PATH_SEPARATOR = "/";
	
    // Variables used by applyPreferences() and savePreferences()
    // Variables used by applyPreferences() and savePreferences()
    public static final String pathName = "/de/kugihan/DfMCreator";
    public static final Preferences root = Preferences.userRoot();
    public static final Preferences node = root.node(pathName);
    public static final String LookAndFeel = node.get("DfMCreator.window.LookAndFeel", null);
    public static final String selectedLang = node.get("DfMCreator.language.Settings", null);

    // Creating an instance of the FontToolkit
    public FontToolkit fontTK = new FontToolkit();

    // A public DfMCreatorMain instance
    public static DfMCreatorMain dfmCreator;

    // A public PropertiesEditor instance
    public static PropertiesEditor propWin;

    // These are the queue variables that will be used to enqueue
    // conversion/generation/creation settings before the actual processings.
    // For DictdToDfM
    public Queue<DTDFMValsToEnqueue> dictConvQueue;
    // For DictionaryGeneration
    public Queue<DictGenValsToEnqueue> dictgenQueue;
    // For JarCreator
    public Queue<JarCreatorValsToEnqueue> jarCreatorQueue;
    // For BitmapFontGenerator
    public Queue<BitmapFontGeneratorToEnqueue> fontGenerationQueue;

    // These are for checking if the queues have not already been created
    // in order to prevent overwritring them if they already exist.
    // For DictdToDfM
    private static boolean dtdfmQueueCreationFlag = false;
    // For DictionaryGeneration
    private static boolean dictgenQueueCreationFlag = false;
    // For JarCreator
    private static boolean jarCreatorQueueCreationtorFlag = false;
    // For BitmapFontGenerator
    private static boolean fontGenerationQueueCreationFlag = false;

    // These are the ArrayList variables that will be used to hold
    // the names of the items being processed in order to use them
    // in TextAreas as visual representations for the queues being
    // processed so as to be references for the user.

    // For DictdToDfM
    public ArrayList<String> dictConvArray;
    // For DictionaryGeneration
    public ArrayList<String> dictGenArray;
    // For JarCreator
    public ArrayList<String> jarCreatorArray;
    // For BitmapFontGenerator
    public ArrayList<String> fontGenerationArray;

    // Tells us wether the user wants to use and external
    // empty DictionaryForMIDs or the internal bundled one.
    public static boolean externalEmptyDfMFlag = false;




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
        OwnEncLabel = new javax.swing.JLabel();
        OwnEncTextField = new javax.swing.JTextField();
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
        iniInfoLabelText = new javax.swing.JLabel();
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
        clearFieldsBT = new javax.swing.JButton();
        checkCSVFileIntegrityBT = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        createPropsFile = new javax.swing.JButton();
        editPropsFile = new javax.swing.JButton();
        toolbar1 = new javax.swing.JToolBar();
        jLabel9 = new javax.swing.JLabel();
        bfgPanel = new javax.swing.JPanel();
        infotarea = new javax.swing.JTextField();
        JarCreatorPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        InputCSVFilesTF = new javax.swing.JTextField();
        EmptyDfMLabel = new javax.swing.JLabel();
        EmptyDfMDirTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        OutputDirTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        EmptyDfMBrowseLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        InputCSVFilesButton = new javax.swing.JButton();
        EmptyDfMDirBrowseButton = new javax.swing.JButton();
        OutputDirButton = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        chooseCustomJarJadCBox = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        toolbar2 = new javax.swing.JToolBar();
        jLabel10 = new javax.swing.JLabel();
        dfmBuilderMenuBar = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
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

        DictdConvPanel.setLayout(new java.awt.BorderLayout());

        ButtonsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ButtonsPanel.setLayout(null);

        clearFieldsButton.setText(I18n.tr("clear.fields.dfmCreatorMain")); // NOI18N
        clearFieldsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFieldsButtonActionPerformed(evt);
            }
        });
        ButtonsPanel.add(clearFieldsButton);
        clearFieldsButton.setBounds(60, 18, 207, 30);

        proceedButton.setText(I18n.tr("proceed.dfmCreatorMain")); // NOI18N
        proceedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proceedButtonActionPerformed(evt);
            }
        });
        ButtonsPanel.add(proceedButton);
        proceedButton.setBounds(60, 52, 207, 30);

        EncodingPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        EncodingPanel.setLayout(null);

        EncodingLabel.setText(I18n.tr("encoding.dfmCreatorMain")); // NOI18N
        EncodingPanel.add(EncodingLabel);
        EncodingLabel.setBounds(5, 5, 221, 20);

        UTFLable.setText("UTF-8");
        EncodingPanel.add(UTFLable);
        UTFLable.setBounds(30, 35, 140, 20);

        UTFRB.setPreferredSize(new java.awt.Dimension(22, 22));
        UTFRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UTFRBActionPerformed(evt);
            }
        });
        EncodingPanel.add(UTFRB);
        UTFRB.setBounds(5, 35, 20, 20);
        UTFRB.setSelected(true);

        UTF16Label.setText("UTF-16");
        EncodingPanel.add(UTF16Label);
        UTF16Label.setBounds(30, 65, 140, 20);

        UTF16RB.setPreferredSize(new java.awt.Dimension(22, 22));
        UTF16RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UTF16RBActionPerformed(evt);
            }
        });
        EncodingPanel.add(UTF16RB);
        UTF16RB.setBounds(5, 65, 20, 20);

        UTFBELabel.setText("UTF-16BE");
        EncodingPanel.add(UTFBELabel);
        UTFBELabel.setBounds(30, 95, 140, 20);

        UTFBERB.setPreferredSize(new java.awt.Dimension(22, 22));
        UTFBERB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UTFBERBActionPerformed(evt);
            }
        });
        EncodingPanel.add(UTFBERB);
        UTFBERB.setBounds(5, 95, 20, 20);

        UTFLELabel.setText("UTF-16LE");
        EncodingPanel.add(UTFLELabel);
        UTFLELabel.setBounds(30, 125, 140, 20);

        OWNRB.setPreferredSize(new java.awt.Dimension(22, 22));
        OWNRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OWNRBActionPerformed(evt);
            }
        });
        EncodingPanel.add(OWNRB);
        OWNRB.setBounds(5, 155, 20, 20);

        ISO1RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ISO1RBActionPerformed(evt);
            }
        });
        EncodingPanel.add(ISO1RB);
        ISO1RB.setBounds(175, 35, 20, 20);

        ISO2RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ISO2RBActionPerformed(evt);
            }
        });
        EncodingPanel.add(ISO2RB);
        ISO2RB.setBounds(175, 65, 20, 20);

        IBMRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IBMRBActionPerformed(evt);
            }
        });
        EncodingPanel.add(IBMRB);
        IBMRB.setBounds(175, 95, 20, 20);

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
        EncodingPanel.add(ISO1Label);
        ISO1Label.setBounds(200, 35, 140, 20);

        ISO2Label.setText("ISO-8859-2");
        EncodingPanel.add(ISO2Label);
        ISO2Label.setBounds(200, 65, 140, 20);

        IBMLabel.setText("IBM-850");
        EncodingPanel.add(IBMLabel);
        IBMLabel.setBounds(200, 95, 140, 20);

        ISO50Label.setText("ISO-2022-50");
        EncodingPanel.add(ISO50Label);
        ISO50Label.setBounds(200, 125, 140, 20);

        ISO50RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ISO50RBActionPerformed(evt);
            }
        });
        EncodingPanel.add(ISO50RB);
        ISO50RB.setBounds(175, 125, 20, 20);

        UTFLERB.setPreferredSize(new java.awt.Dimension(22, 22));
        UTFLERB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UTFLERBActionPerformed(evt);
            }
        });
        EncodingPanel.add(UTFLERB);
        UTFLERB.setBounds(5, 125, 20, 20);

        OwnEncLabel.setText(I18n.tr("chooseYourOwn.dfmCreatorMain")); // NOI18N
        OwnEncLabel.setMaximumSize(new java.awt.Dimension(140, 20));
        OwnEncLabel.setPreferredSize(new java.awt.Dimension(140, 20));
        EncodingPanel.add(OwnEncLabel);
        OwnEncLabel.setBounds(30, 155, 155, 20);

        OwnEncTextField.setEnabled(false);
        EncodingPanel.add(OwnEncTextField);
        OwnEncTextField.setBounds(190, 155, 30, 25);

        DBPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        DBPanel.setLayout(null);

        DBBrowseButton.setText("...");
        DBBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBBrowseButtonActionPerformed(evt);
            }
        });
        DBPanel.add(DBBrowseButton);
        DBBrowseButton.setBounds(550, 70, 36, 29);

        DBNameTextField.setToolTipText(I18n.tr("dbname.tooltiptext")); // NOI18N
        DBPanel.add(DBNameTextField);
        DBNameTextField.setBounds(270, 20, 130, 28);
        DBPanel.add(DBPathTextField);
        DBPathTextField.setBounds(270, 70, 275, 27);

        DBBrowseLabel.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        DBPanel.add(DBBrowseLabel);
        DBBrowseLabel.setBounds(590, 75, 100, 20);

        DBPathLabel.setText(I18n.tr("dbDir.dfmCreatorMain")); // NOI18N
        DBPanel.add(DBPathLabel);
        DBPathLabel.setBounds(5, 70, 250, 20);

        OutCVSFilePathLabel.setText(I18n.tr("outputFileDir.dfmCreatorMain")); // NOI18N
        DBPanel.add(OutCVSFilePathLabel);
        OutCVSFilePathLabel.setBounds(5, 105, 250, 20);
        DBPanel.add(OutCSVFileTextField);
        OutCSVFileTextField.setBounds(270, 105, 275, 27);

        OutCSVFileBrowseLabel.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        DBPanel.add(OutCSVFileBrowseLabel);
        OutCSVFileBrowseLabel.setBounds(590, 110, 100, 20);

        OutCSVFileBrowseButton.setText("...");
        OutCSVFileBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OutCSVFileBrowseButtonActionPerformed(evt);
            }
        });
        DBPanel.add(OutCSVFileBrowseButton);
        OutCSVFileBrowseButton.setBounds(550, 105, 36, 29);

        DBNameLabel.setText(I18n.tr("dbName.dfmCreatorMain")); // NOI18N
        DBNameLabel.setToolTipText(I18n.tr("dbname.tooltiptext")); // NOI18N
        DBPanel.add(DBNameLabel);
        DBNameLabel.setBounds(5, 20, 250, 20);

        iniInfoLabelText.setText(I18n.tr("ini.info.label.text")); // NOI18N
        iniInfoLabelText.setToolTipText(I18n.tr("dbname.label.tooltiptext")); // NOI18N
        DBPanel.add(iniInfoLabelText);
        iniInfoLabelText.setBounds(405, 6, 290, 60);

        SCPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SCPanel.setLayout(null);

        TabLabel.setText(I18n.tr("tab.dfmCreatorMain")); // NOI18N
        SCPanel.add(TabLabel);
        TabLabel.setBounds(35, 35, 140, 20);

        CarRetLabel.setText(I18n.tr("carRe.dfmCreatorMain")); // NOI18N
        SCPanel.add(CarRetLabel);
        CarRetLabel.setBounds(35, 65, 140, 20);

        FormFeedLabel.setText(I18n.tr("formFeed.dfmCreatorMain")); // NOI18N
        SCPanel.add(FormFeedLabel);
        FormFeedLabel.setBounds(35, 95, 140, 20);

        TabRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TabRBActionPerformed(evt);
            }
        });
        SCPanel.add(TabRB);
        TabRB.setBounds(12, 35, 20, 20);

        CRRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CRRBActionPerformed(evt);
            }
        });
        SCPanel.add(CRRB);
        CRRB.setBounds(12, 65, 20, 20);

        FFRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FFRBActionPerformed(evt);
            }
        });
        SCPanel.add(FFRB);
        FFRB.setBounds(12, 95, 20, 20);

        OwnSCRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OwnSCRBActionPerformed(evt);
            }
        });
        SCPanel.add(OwnSCRB);
        OwnSCRB.setBounds(12, 125, 20, 20);

        ChooseSCLabel.setText(I18n.tr("chooseSepChar.dfmCreatorMain")); // NOI18N
        SCPanel.add(ChooseSCLabel);
        ChooseSCLabel.setBounds(5, 5, 300, 20);

        jPanel8.setLayout(new java.awt.GridBagLayout());
        SCPanel.add(jPanel8);
        jPanel8.setBounds(188, 143, 0, 0);

        OwnLabel.setText(I18n.tr("chooseYourOwn.dfmCreatorMain")); // NOI18N
        SCPanel.add(OwnLabel);
        OwnLabel.setBounds(35, 125, 155, 20);

        OwnSCTextField.setEnabled(false);
        SCPanel.add(OwnSCTextField);
        OwnSCTextField.setBounds(195, 125, 30, 25);

        SLPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SLPanel.setLayout(null);

        SLLabel.setText(I18n.tr("switchLang.dfmCreatorMain")); // NOI18N
        SLPanel.add(SLLabel);
        SLLabel.setBounds(30, 5, 325, 25);

        KTLabel.setText(I18n.tr("keepTab.dfmCreatorMain")); // NOI18N
        KTLabel.setMaximumSize(new java.awt.Dimension(250, 50));
        KTLabel.setMinimumSize(new java.awt.Dimension(250, 20));
        KTLabel.setPreferredSize(new java.awt.Dimension(250, 20));
        SLPanel.add(KTLabel);
        KTLabel.setBounds(30, 30, 325, 35);

        RSBLabel.setText(I18n.tr("removeSquare.dfmCreatorMain")); // NOI18N
        SLPanel.add(RSBLabel);
        RSBLabel.setBounds(30, 70, 325, 30);
        SLPanel.add(SLCheckBox);
        SLCheckBox.setBounds(5, 5, 20, 20);
        SLPanel.add(KTCheckBox);
        KTCheckBox.setBounds(5, 35, 20, 20);
        SLPanel.add(RSBCheckBox);
        RSBCheckBox.setBounds(5, 70, 20, 20);

        toolbar4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        toolbar4.setFloatable(false);
        toolbar4.setRollover(true);
        toolbar4.setPreferredSize(new java.awt.Dimension(49, 15));
        toolbar4.setRequestFocusEnabled(false);

        jLabel12.setText("<html><font color=gray>DictdToDictionaryForMIDs</font>");
        toolbar4.add(jLabel12);

        javax.swing.GroupLayout dictdPanelLayout = new javax.swing.GroupLayout(dictdPanel);
        dictdPanel.setLayout(dictdPanelLayout);
        dictdPanelLayout.setHorizontalGroup(
            dictdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dictdPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(dictdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dictdPanelLayout.createSequentialGroup()
                        .addGroup(dictdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(EncodingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SLPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dictdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SCPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(DBPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toolbar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        dictdPanelLayout.setVerticalGroup(
            dictdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dictdPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolbar4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DBPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dictdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(SCPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EncodingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dictdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SLPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        DictdConvPanel.add(dictdPanel, java.awt.BorderLayout.CENTER);

        DFMBuilderTabbedPane.addTab("DictdToDictionaryForMIDs", DictdConvPanel);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        SourceFileLabel.setText(I18n.tr("dictGenLabel1.dfmCreatorMain")); // NOI18N
        jPanel1.add(SourceFileLabel);
        SourceFileLabel.setBounds(5, 10, 240, 35);
        jPanel1.add(SourceFileTF);
        SourceFileTF.setBounds(250, 10, 300, 27);

        DirectoryDestinationLabel.setText(I18n.tr("dictGenLabel3.dfmCreatorMain")); // NOI18N
        jPanel1.add(DirectoryDestinationLabel);
        DirectoryDestinationLabel.setBounds(5, 120, 240, 35);
        jPanel1.add(DirectoryDestinationTF);
        DirectoryDestinationTF.setBounds(250, 120, 300, 27);

        PropertyPathLabel.setText(I18n.tr("dictGenLabel2.dfmCreatorMain")); // NOI18N
        jPanel1.add(PropertyPathLabel);
        PropertyPathLabel.setBounds(5, 65, 240, 35);
        jPanel1.add(PropertyPathTF);
        PropertyPathTF.setBounds(250, 65, 300, 27);

        SourceFileBrowseLB.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        jPanel1.add(SourceFileBrowseLB);
        SourceFileBrowseLB.setBounds(600, 15, 130, 20);

        DirectoryDestinationBrowseLB.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        jPanel1.add(DirectoryDestinationBrowseLB);
        DirectoryDestinationBrowseLB.setBounds(600, 125, 130, 20);

        PropertyPathBrowseLB.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        jPanel1.add(PropertyPathBrowseLB);
        PropertyPathBrowseLB.setBounds(600, 70, 130, 20);

        SourceFileBrowseBT.setText("...");
        SourceFileBrowseBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SourceFileBrowseBTActionPerformed(evt);
            }
        });
        jPanel1.add(SourceFileBrowseBT);
        SourceFileBrowseBT.setBounds(560, 10, 36, 29);

        DirectoryDestinationBrowseBT.setText("...");
        DirectoryDestinationBrowseBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DirectoryDestinationBrowseBTActionPerformed(evt);
            }
        });
        jPanel1.add(DirectoryDestinationBrowseBT);
        DirectoryDestinationBrowseBT.setBounds(560, 120, 36, 29);

        PropertyPathBT.setText("...");
        PropertyPathBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PropertyPathBTActionPerformed(evt);
            }
        });
        jPanel1.add(PropertyPathBT);
        PropertyPathBT.setBounds(560, 65, 36, 29);

        jPanel5.setLayout(new java.awt.GridBagLayout());
        jPanel1.add(jPanel5);
        jPanel5.setBounds(141, 131, 439, 0);

        GenDictFilesBT.setText(I18n.tr("generate.dfmCreatorMain")); // NOI18N
        GenDictFilesBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenDictFilesBTActionPerformed(evt);
            }
        });
        jPanel1.add(GenDictFilesBT);
        GenDictFilesBT.setBounds(120, 165, 200, 60);

        clearFieldsBT.setText(I18n.tr("clear.fields.html.dfmCreatorMain")); // NOI18N
        clearFieldsBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFieldsBTActionPerformed(evt);
            }
        });
        jPanel1.add(clearFieldsBT);
        clearFieldsBT.setBounds(330, 165, 70, 60);

        checkCSVFileIntegrityBT.setText(I18n.tr("checkCSVFile.dfmCreatorMain")); // NOI18N
        checkCSVFileIntegrityBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCSVFileIntegrityBTActionPerformed(evt);
            }
        });
        jPanel1.add(checkCSVFileIntegrityBT);
        checkCSVFileIntegrityBT.setBounds(410, 165, 200, 60);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(null);

        jLabel7.setText(I18n.tr("propsInfoLabel.dfmCreatorMain")); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel3.add(jLabel7);
        jLabel7.setBounds(60, 20, 500, 140);

        createPropsFile.setText(I18n.tr("create.dfmCreatorMain")); // NOI18N
        createPropsFile.setPreferredSize(new java.awt.Dimension(70, 40));
        createPropsFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPropsFileActionPerformed(evt);
            }
        });
        jPanel3.add(createPropsFile);
        createPropsFile.setBounds(570, 35, 160, 40);

        editPropsFile.setText(I18n.tr("editAnExisting.dfmCreatorMain")); // NOI18N
        editPropsFile.setMinimumSize(new java.awt.Dimension(100, 60));
        editPropsFile.setName("");
        editPropsFile.setPreferredSize(new java.awt.Dimension(150, 60));
        editPropsFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPropsFileActionPerformed(evt);
            }
        });
        jPanel3.add(editPropsFile);
        editPropsFile.setBounds(570, 85, 160, 60);

        toolbar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        toolbar1.setFloatable(false);
        toolbar1.setRollover(true);
        toolbar1.setPreferredSize(new java.awt.Dimension(49, 15));
        toolbar1.setRequestFocusEnabled(false);

        jLabel9.setText("<html><font color=gray>DictionaryGeneration</font>");
        toolbar1.add(jLabel9);

        javax.swing.GroupLayout DictGenPanelLayout = new javax.swing.GroupLayout(DictGenPanel);
        DictGenPanel.setLayout(DictGenPanelLayout);
        DictGenPanelLayout.setHorizontalGroup(
            DictGenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DictGenPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DictGenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toolbar1, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        DictGenPanelLayout.setVerticalGroup(
            DictGenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DictGenPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolbar1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        DFMBuilderTabbedPane.addTab("DictionaryGeneration", DictGenPanel);

        bfgPanel.setLayout(new java.awt.GridBagLayout());

        infotarea.setText("Don't touch this panel it will hold the FontToolkit at runtime...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        bfgPanel.add(infotarea, gridBagConstraints);

        DFMBuilderTabbedPane.addTab("BitmapFontGenerator", bfgPanel);
        // Getting the font_size generator panel and displaying it.
        addFontTKToDfMCreatorTabbedPane();

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jLabel1.setText(I18n.tr("jarCreatorLabel1.dfmCreatorMain")); // NOI18N
        jPanel2.add(jLabel1);
        jLabel1.setBounds(5, 5, 310, 35);
        jPanel2.add(InputCSVFilesTF);
        InputCSVFilesTF.setBounds(5, 50, 345, 27);

        EmptyDfMLabel.setText(I18n.tr("jarCreatorLabel2.dfmCreatorMain")); // NOI18N
        EmptyDfMLabel.setEnabled(false);
        jPanel2.add(EmptyDfMLabel);
        EmptyDfMLabel.setBounds(5, 150, 310, 35);

        EmptyDfMDirTF.setEnabled(false);
        jPanel2.add(EmptyDfMDirTF);
        EmptyDfMDirTF.setBounds(5, 190, 345, 27);

        jLabel3.setText(I18n.tr("jarCreatorLabel3.dfmCreatorMain")); // NOI18N
        jPanel2.add(jLabel3);
        jLabel3.setBounds(5, 270, 310, 25);
        jPanel2.add(OutputDirTF);
        OutputDirTF.setBounds(5, 300, 345, 27);

        jLabel4.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        jPanel2.add(jLabel4);
        jLabel4.setBounds(415, 300, 100, 20);

        EmptyDfMBrowseLabel.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        EmptyDfMBrowseLabel.setEnabled(false);
        jPanel2.add(EmptyDfMBrowseLabel);
        EmptyDfMBrowseLabel.setBounds(415, 190, 100, 20);

        jLabel6.setText(I18n.tr("browse.dfmCreatorMain")); // NOI18N
        jPanel2.add(jLabel6);
        jLabel6.setBounds(415, 50, 100, 20);

        InputCSVFilesButton.setText("...");
        InputCSVFilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputCSVFilesButtonActionPerformed(evt);
            }
        });
        jPanel2.add(InputCSVFilesButton);
        InputCSVFilesButton.setBounds(365, 50, 47, 25);

        EmptyDfMDirBrowseButton.setText("...");
        EmptyDfMDirBrowseButton.setEnabled(false);
        EmptyDfMDirBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmptyDfMDirBrowseButtonActionPerformed(evt);
            }
        });
        jPanel2.add(EmptyDfMDirBrowseButton);
        EmptyDfMDirBrowseButton.setBounds(365, 190, 47, 25);

        OutputDirButton.setText("...");
        OutputDirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OutputDirButtonActionPerformed(evt);
            }
        });
        jPanel2.add(OutputDirButton);
        OutputDirButton.setBounds(365, 300, 47, 25);

        jButton4.setText(I18n.tr("packCSVFiles.dfmCreatorMain")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4);
        jButton4.setBounds(50, 350, 220, 30);

        jButton1.setText(I18n.tr("clear.fields.dfmCreatorMain")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(275, 350, 150, 30);

        chooseCustomJarJadCBox.setText(I18n.tr("custom.empty.dfm.jar.jad.JarCreator")); // NOI18N
        chooseCustomJarJadCBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chooseCustomJarJadCBoxStateChanged(evt);
            }
        });
        jPanel2.add(chooseCustomJarJadCBox);
        chooseCustomJarJadCBox.setBounds(5, 110, 500, 25);

        jSeparator1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jSeparator1);
        jSeparator1.setBounds(0, 145, 520, 100);

        toolbar2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        toolbar2.setFloatable(false);
        toolbar2.setRollover(true);
        toolbar2.setPreferredSize(new java.awt.Dimension(49, 15));
        toolbar2.setRequestFocusEnabled(false);

        jLabel10.setText("<html><font color=gray>JarCreator</font>");
        toolbar2.add(jLabel10);

        javax.swing.GroupLayout JarCreatorPanelLayout = new javax.swing.GroupLayout(JarCreatorPanel);
        JarCreatorPanel.setLayout(JarCreatorPanelLayout);
        JarCreatorPanelLayout.setHorizontalGroup(
            JarCreatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JarCreatorPanelLayout.createSequentialGroup()
                .addContainerGap(138, Short.MAX_VALUE)
                .addGroup(JarCreatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                    .addComponent(toolbar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(134, 134, 134))
        );
        JarCreatorPanelLayout.setVerticalGroup(
            JarCreatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JarCreatorPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(toolbar2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        DFMBuilderTabbedPane.addTab("JarCreator", JarCreatorPanel);

        getContentPane().add(DFMBuilderTabbedPane, java.awt.BorderLayout.CENTER);

        jMenu2.setText(I18n.tr("file.dfmcreatormain")); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText(I18n.tr("quit.dfmcreatormain")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        dfmBuilderMenuBar.add(jMenu2);

        jMenu1.setText(I18n.tr("prefs.dfmCreatorMain")); // NOI18N

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText(I18n.tr("settings.dfmCreatorMain")); // NOI18N
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

    private void OwnSCRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OwnSCRBActionPerformed
        ownSCTextFieldSetEnabled();
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
        outCSVFileBrowseGetFile();
    }//GEN-LAST:event_OutCSVFileBrowseButtonActionPerformed

    private void DBBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBBrowseButtonActionPerformed
        dbBrowseButtonGetFile();
    }//GEN-LAST:event_DBBrowseButtonActionPerformed

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
        ownEncTextFieldSetEnabled();
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
        DictdConvDoAll();
    }//GEN-LAST:event_proceedButtonActionPerformed

    private void clearFieldsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFieldsButtonActionPerformed
        clearFields();
    }//GEN-LAST:event_clearFieldsButtonActionPerformed

    private void createPropsFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPropsFileActionPerformed
    //        checkScreenResolution();
        showPropWin();
    }//GEN-LAST:event_createPropsFileActionPerformed

    private void GenDictFilesBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenDictFilesBTActionPerformed
        DictGenDoAll();
    }//GEN-LAST:event_GenDictFilesBTActionPerformed

    private void SourceFileBrowseBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SourceFileBrowseBTActionPerformed
        sourceFileBrowseBTGetFile();
    }//GEN-LAST:event_SourceFileBrowseBTActionPerformed

    private void DirectoryDestinationBrowseBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DirectoryDestinationBrowseBTActionPerformed
        directoryDestinationBrowseBTGetFile();
    }//GEN-LAST:event_DirectoryDestinationBrowseBTActionPerformed

    private void PropertyPathBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PropertyPathBTActionPerformed
        propertyPathBTGetFile();
    }//GEN-LAST:event_PropertyPathBTActionPerformed

    private void InputCSVFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputCSVFilesButtonActionPerformed
        inputCSVFilesButtonGetFile();
    }//GEN-LAST:event_InputCSVFilesButtonActionPerformed

    private void EmptyDfMDirBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmptyDfMDirBrowseButtonActionPerformed
        emptyDfMDirButtonGetFile();
    }//GEN-LAST:event_EmptyDfMDirBrowseButtonActionPerformed

    private void OutputDirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OutputDirButtonActionPerformed
        outputDirButtonGetFile();
    }//GEN-LAST:event_OutputDirButtonActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        showAbout();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JarCreatorDoAll();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clearJarCreatorFields();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void clearFieldsBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFieldsBTActionPerformed
        clearDictGenFields();
    }//GEN-LAST:event_clearFieldsBTActionPerformed

    private void editPropsFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPropsFileActionPerformed
        try {
            editExistingPropsFile();
        } catch (Throwable t){
            System.out.println(t.getLocalizedMessage());
        }
    }//GEN-LAST:event_editPropsFileActionPerformed

    private void checkCSVFileIntegrityBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCSVFileIntegrityBTActionPerformed
        showCSVFileCheckWin();
    }//GEN-LAST:event_checkCSVFileIntegrityBTActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        showPrefsWin();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void miContentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miContentsActionPerformed
        showHelpWindow();
    }//GEN-LAST:event_miContentsActionPerformed

    private void chooseCustomJarJadCBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chooseCustomJarJadCBoxStateChanged
        monitorEmptyDfMCheckBoxState();
    }//GEN-LAST:event_chooseCustomJarJadCBoxStateChanged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        // Set Nimbus as the default Look And Feel if running for
        // the first time or if the user has not chosen any.
        setTheNimbusLookAndFeel();

        if (args.length != 0) {
            switch (args[0]) {

           /************************************
            * DictdToDictionaryForMIDs         *
            ************************************/
                case "--DictdToDictionaryForMIDs":
                case "-dc":
                    // Tell the user she invoked DictdToDictionaryForMIDs
                    System.out.println("\n\nYou invoked the commad line version of DictdToDictionaryForMIDs");

                    // Printing the DictdToDictionaryForMIDs copyright notice
                    de.kugihan.dictionaryformids.dictdtodictionaryformids.DictdToDfM.printCopyrightNotice();

                    // Set flag to true to tell the app that the
                    // DictdToDictionaryForMIDs is being called from the command line
                    de.kugihan.dictionaryformids.dictdtodictionaryformids.DictdToDfM.flag = true;

                    // Call the DictdToDictionaryForMIDs main class
                    de.kugihan.dictionaryformids.dictdtodictionaryformids.DictdToDfM.main(args);
                    break;

           /************************************
            * DictionaryGeneration             *
            ************************************/
                case "--DictionaryGeneration":
                case "-dg":
                    // Tell the user she invoked DictionaryGeneration
                    System.out.println("\n\nYou invoked the commad line version of DictionaryGeneration");

                    // Printing the DictionaryGeneration copyright notice
                    de.kugihan.dictionaryformids.dictgen.DictionaryGeneration.printCopyrightNotice();

                    // Check the number of command line arguments
                    if (args.length!=4){
                        de.kugihan.dictionaryformids.dictgen.DictionaryGeneration.printUsage();
                    }

                    // Call the DictionaryGeneration main class
                    try {
                        // Creating the directory "dictionary" that will hold the csv
                        // dictionary files that are generated by DictionaryGeneration
                        File destCSVsDir = new File(args[2] + PATH_SEPARATOR + "dictionary");
                        if (!destCSVsDir.exists()){
                            if (!destCSVsDir.mkdirs()){
                                try {
                                    throw new DfMCreatorException.CantCreateDestDir(I18n.tr("destDirCreationError.dfmCreatorMain"));
                                } catch (CantCreateDestDir ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }

                        // Passing the values to dictgen
                        // we will start with args[1] because args[0] is already used.
                        // Here it is the one that holds the argument -DictionaryGeneration
                        DictionaryGeneration.setSourceFile(args[1]);
                        DictionaryGeneration.setDirectoryDestination(args[2] + PATH_SEPARATOR + "dictionary");
                        DictionaryGeneration.setPropertyPath(args[3]);

                        // calling the actual main class
                        de.kugihan.dictionaryformids.dictgen.DictionaryGeneration.main(args);
                    } catch (DictionaryException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;

           /************************************
            * BitmapFontGenerator              *
            ************************************/
                case "--FontGenerator":
                case "-fg":
                    // Tell the user she invoked FontGenerator
                    System.out.println("\n\nYou invoked the older standalone GUI version of FontGenerator");

                    // Printing the FontGenerator copyright notice
                    de.kugihan.fonttoolkit.FontToolkit.printCopyrightNotice();

                    // Set flag to true to tell the app that the fontToolkit is
                    // being called from the command line in order to prevent it
                    // from calling the font_size generation preferences summary window.
                    de.kugihan.fonttoolkit.FontToolkit.flag = true;
                    // Call the FontGenerator main class
                    try {
                        de.kugihan.fonttoolkit.FontToolkit.main(args);
                    } catch (DictionaryException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;

           /************************************
            * JarCreator              *
            ************************************/
                case "--JarCreator":
                case "-jc":
                    // Tell the user she invoked JarCreator
                    System.out.println("\n\nYou invoked the commad line version of JarCreator");

                    // Printing the JarCreator copyright notice
                    de.kugihan.jarCreator.JarCreator.printCopyrightNotice();

                    // Check the number of command line arguments
                    if (args.length!=4){
                        de.kugihan.jarCreator.JarCreator.printUsage();
                    }

                    externalEmptyDfMFlag = true;
                    try {
                        // Setting up the values for JarCreator
                        // we will start with args[1] because args[0] is already used.
                        // Here, it is the one that holds the argument -JarCreator
                        JarCreator.setDictionaryDirectory(args[1] + PATH_SEPARATOR);
                        JarCreator.setEmptyDictionaryForMID(args[2]);
                        JarCreator.setOutputDirectory(args[3]);

                        // Calling the actual main class
                        de.kugihan.jarCreator.JarCreator.main(args);
                    } catch (FileNotFoundException ex) {
                        System.out.println(ex.getMessage());
                    } catch (IOException | DictionaryException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;

                default:
                    // Print a general information/error message in case the user
                    // entered a bad argument.
                    System.out.println(I18n.tr("cli.invoke.msg.dfmcreatormain"));

                    // If we reach here, it means the user passed no argument
                    // to DfM-Creator through command line, so, launch the
                    // DfM-Creator window.
                    break;
                }
        }
        else {
            // Apply preferences. Among other
            // preferences, look and feel settings
            applyPreferences();

            // print a copyright notice
            printCopyrightNotice();

            // Creates the DictionaryForMIDs-Creator
            // form and displays it
            CreateAndDisplayTheForm();
        }

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
    private javax.swing.JLabel EmptyDfMBrowseLabel;
    private javax.swing.JButton EmptyDfMDirBrowseButton;
    private javax.swing.JTextField EmptyDfMDirTF;
    private javax.swing.JLabel EmptyDfMLabel;
    private javax.swing.ButtonGroup EncGroupBut;
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
    private javax.swing.JButton checkCSVFileIntegrityBT;
    private javax.swing.JCheckBox chooseCustomJarJadCBox;
    private javax.swing.JButton clearFieldsBT;
    private javax.swing.JButton clearFieldsButton;
    private javax.swing.JButton createPropsFile;
    private javax.swing.JMenuBar dfmBuilderMenuBar;
    private javax.swing.JPanel dictdPanel;
    private javax.swing.JButton editPropsFile;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JTextField infotarea;
    private javax.swing.JLabel iniInfoLabelText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuItem miContents;
    private javax.swing.JButton proceedButton;
    private javax.swing.JToolBar toolbar1;
    private javax.swing.JToolBar toolbar2;
    private javax.swing.JToolBar toolbar4;
    // End of variables declaration//GEN-END:variables


   /*
    *
    * DictdToDictionaryForMIDs subroutines
    *
    */

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
                                            OutCSVFileCantBeWritten, NoDictFileFound,
                                            NoIndexFileFound {

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

        dbFolderNameVarString = trim(dbFolderNameVarString);
        outputCSVFileVarString = trim(outputCSVFileVarString);

        File dirFile1 = new File(dbFolderNameVarString);
        if (!dirFile1.isDirectory() || !dirFile1.canRead()){
                throw new DBFolderNotAccessible(I18n.tr("dbNotAccessible.dfmCreatorMain"));
        }

        File iniFile = new File(dbFolderNameVarString +PATH_SEPARATOR+ dbNameVarString + ".ini");
        if (!iniFile.canRead()){

            // Default values: Database Name + .dict.dz | Database Name + .index
            String compressedDict = dbNameVarString + ".dict.dz";
            String uncompressedDict = dbNameVarString + ".dict";
            String dictIndex = dbNameVarString + ".index";

            // Extensions of the dict and index files
            String compressedDictExtension = ".dict.dz";
            String UncompressedDictExtension = ".dict";
            String dictIndexExtension = ".index";

            // Checking if the dict and index files exist.
            File compressedDictFile = new File(dbFolderNameVarString +PATH_SEPARATOR+ compressedDict);
            File uncompressedDictFile = new File (dbFolderNameVarString +PATH_SEPARATOR+ uncompressedDict);
            File dictIndexFile = new File (dbFolderNameVarString +PATH_SEPARATOR+ dictIndex);

            String dictNameToBeWrittenToINI = "";
            if (!compressedDictFile.exists() && (!uncompressedDictFile.exists())){
                throw new NoDictFileFound(I18n.tr("no.dict.file.found"));
            } else
                if (compressedDictFile.exists()){
                    dictNameToBeWrittenToINI = dbNameVarString + compressedDictExtension;
                } else
                    if (uncompressedDictFile.exists()){
                        dictNameToBeWrittenToINI = dbNameVarString + UncompressedDictExtension;
                    }

            if (!dictIndexFile.exists()){
                throw new NoIndexFileFound(I18n.tr("no.index.file.found"));
            }

            try {
                // Attempting to create the .ini file
                iniFile.createNewFile();
                FileWriter outputStream = null;

                // Attempting to write the required infos
                try {
                    outputStream = new FileWriter(iniFile);
                    outputStream.write("# The " + dbNameVarString + " database" + "\n");
                    outputStream.write(dbNameVarString + ".data=" + dictNameToBeWrittenToINI + "\n");
                    outputStream.write(dbNameVarString + ".index=" + dbNameVarString + dictIndexExtension + "\n");
                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                printAnyMsg(I18n.tr("cant.create.iniFile"), I18n.tr("cant.create.iniFile.winTitle"),
                                                            JOptionPane.ERROR_MESSAGE);
                throw new DBINIFileNotAccessible(I18n.tr("dbINI.dfmCreatorMain"));
            }
        }

        File dirFile = new File(outputCSVFileVarString);
        if (!dirFile.isDirectory() || !dirFile.canRead()){
                throw new OutCSVFileCantBeWritten(I18n.tr("outputFileAccessError.dfmCreatorMain"));
        }

        dbNameVar = dbNameVarString;

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

        // Creating a new item to be put in the queue.
        DTDFMValsToEnqueue dtdfmRecord = new DTDFMValsToEnqueue();

        // This checks if the dictConvQueue hasn't already been created
        // otherwise creates it but if it already exists, avoids
        // overwriting it so that other conversion settings may
        // be enqueued.
        if (!dtdfmQueueCreationFlag){
            dictConvQueue = new LinkedList<>();
            // Initializing our ArrayList
            dictConvArray = new ArrayList<>();
            dtdfmQueueCreationFlag = true;
        }

        // Passing values to the variables of this item
        dtdfmRecord.db = dbNameVar;
        dtdfmRecord.dbFolder = dbFolderNameVar;
        dtdfmRecord.outputCSV = outputCSVFileVar;
        dtdfmRecord.outputEncoding = outputEncodingCharsetVar;
        dtdfmRecord.separatorChar = separatorCharacterVar;
        dtdfmRecord.removeSquareBracks = removeSquareBracketsVar;
        dtdfmRecord.keepTabAndNewLines = keepTabsAndNewLinesVar;
        dtdfmRecord.switchLangs = switchLanguagesVar;

        // Actually adding the element to the queue
        dictConvQueue.add(dtdfmRecord);

        // Adding the dbName variable to the ArrayList
        // This will be used to display a representation of the queue.
        dictConvArray.add(dtdfmRecord.db);

        // As a test, try passing values to DictdToDfM
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

    }

    // When the user selects the check box
    // then he wants to choose his own empty
    // DfM jar-jad and so we let him do so by
    // enabling the buttons, textfields...Otherwise
    // we deactive them since he won't need them.
    private void monitorEmptyDfMCheckBoxState() {
        if (chooseCustomJarJadCBox.isSelected()){
            EmptyDfMBrowseLabel.setEnabled(true);
            EmptyDfMDirBrowseButton.setEnabled(true);
            EmptyDfMDirTF.setEnabled(true);
            EmptyDfMLabel.setEnabled(true);
        } else {
            EmptyDfMBrowseLabel.setEnabled(false);
            EmptyDfMDirBrowseButton.setEnabled(false);
            EmptyDfMDirTF.setEnabled(false);
            EmptyDfMLabel.setEnabled(false);
        }
    }


    public class DTDFMValsToEnqueue {
        String db;
        String dbFolder;
        String outputCSV;
        String outputEncoding;
        char separatorChar;
        boolean switchLangs;
        boolean keepTabAndNewLines;
        boolean removeSquareBracks;
    }

    /**
     * this method gathers all the required
     * things needed to be done in order to
     * convert a dict file with DictdToDfM.
     */
    private void DictdConvDoAll() {
        try {
            setDictdToDfMVals();
            DictdToDfM.printDictdConvSummary();
        } catch (FileNotFoundException ex) {
            DfMCreatorException.printErrorMsg();
        } catch (DBNameTextFieldIsEmpty e){
            printAnyMsg(DfMCreatorException.DBNameTextFieldIsEmptyMsg,
                    I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DBPathTextFieldIsEmpty e){
            printAnyMsg(DfMCreatorException.DBPathTextFieldIsEmptyMsg,
                    I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (OutCSVFileTextFieldIsEmpty e){
            printAnyMsg(DfMCreatorException.OutCSVFileTextFieldIsEmptyMsg,
                    I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DBFolderNotAccessible e){
            printAnyMsg(DfMCreatorException.DBFolderNotAccessibleMsg,
                    I18n.tr("dirNotAccessible.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DBINIFileNotAccessible e){
            printAnyMsg(DfMCreatorException.DBINIFileNotAccessibleMsg,
                    I18n.tr("fileNotAccessible.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (OutCSVFileCantBeWritten e){
            printAnyMsg(DfMCreatorException.OutCSVFileCantBeWrittenMsg,
                    I18n.tr("dirNotAccessible.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (NoDictFileFound e){
            printAnyMsg(DfMCreatorException.NoDictFileFoundMsg,
                    I18n.tr("no.index.file.found.WinTitle"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (NoIndexFileFound e){
            printAnyMsg(DfMCreatorException.NoIndexFileFoundMsg,
                    I18n.tr("no.index.file.found.WinTitle"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        }

        catch (Throwable t){
                printAnyMsg(I18n.tr("unknownException.dfmCreatorMain",
                        new Object[] {t, t.getLocalizedMessage()}),
                        I18n.tr("unknownExceptionTitle"), JOptionPane.ERROR_MESSAGE);
            System.out.println(t + "\n");
        }
    }

    /**
    * resets the setting for the
    * DictdToDictionaryForMIDs tab.
    */
    private void clearFields() {
        DBNameTextField.setText("");
        DBPathTextField.setText("");
        OutCSVFileTextField.setText("");
    }

    // Clears the contents of the TextFields of the
    // DictdToDfM panel from DfMCreatorMain window
    // The reason why i chose to not use the method above
    // is that it might one day be different from it,
    // though today they both do the same thing.
    public void clearDictdToDfMTFs() {
        DBNameTextField.setText("");
        DBPathTextField.setText("");
        OutCSVFileTextField.setText("");
    }


   /*
    *
    * DictionaryGneration subroutines
    *
    */


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

        // Creating a new item to be put in the queue.
        DictGenValsToEnqueue dictgenRecord = new DictGenValsToEnqueue();


        // This checks if the dictgenQueue hasn't already been created
        // otherwise creates it but if it already exists, prevents
        // overwriting it so that other dictionary generation items
        // may be enqueued.
        if (!dictgenQueueCreationFlag){
            dictgenQueue = new LinkedList<>();
            // Initializing our DictGen ArrayList
            dictGenArray = new ArrayList<>();
            dictgenQueueCreationFlag = true;
        }

        // Passing values to the variables of this item
        dictgenRecord.srcFile = sourceFile;
        dictgenRecord.destDir = directoryDestination + PATH_SEPARATOR + "dictionary";
        dictgenRecord.propPath = propertyPath;

        // Actually adding the element to the queue
        dictgenQueue.add(dictgenRecord);

        // Adding the sourceFile path to the ArrayList
        // This will be used to display a representation of the queue.
        dictGenArray.add(dictgenRecord.srcFile);

         // As a test, try passing values to DictGen
        DictionaryGeneration.setSourceFile(sourceFile);
        DictionaryGeneration.setDirectoryDestination(directoryDestination + PATH_SEPARATOR + "dictionary");
        DictionaryGeneration.setPropertyPath(propertyPath);

    }

    public class DictGenValsToEnqueue {
        String srcFile;
        String destDir;
        String propPath;
    }

    /**
     * this method will execute everything for
     * DictionaryGeneration. it's like the
     * actionPerformed subroutine for DictGen.
     */
    private void DictGenDoAll() {
        try {
            setDictGenVals();
            DictionaryGeneration.showDictGenSummary();
        } catch (FileNotFoundException ex) {
            DfMCreatorException.printErrorMsg();
        } catch (PropFileErrorException e) {
            printAnyMsg(DfMCreatorException.PropFileErrorExceptionMsg,
                    I18n.tr("propsFileError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (SourceFileTFIsEmpty e){
            printAnyMsg(DfMCreatorException.SourceFileTFIsEmptyMsg,
                    I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DirectoryDestinationTFIsEmpty e){
            printAnyMsg(DfMCreatorException.DirectoryDestinationTFIsEmptyMsg,
                    I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (PropertyPathTFIsEmpty e){
            printAnyMsg(DfMCreatorException.PropertyPathTFIsEmptyMsg,
                    I18n.tr("emptyFieldError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (SourceFileNotAccessible e){
            printAnyMsg(DfMCreatorException.SourceFileNotAccessibleMsg,
                    I18n.tr("fileNotAccessible.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (PropertyPathNotAccessible e){
            printAnyMsg(DfMCreatorException.PropertyPathNotAccessibleMsg,
                    I18n.tr("DictDirAccessError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DirectoryDestinationNotAccessible e){
            printAnyMsg(DfMCreatorException.DirectoryDestinationNotAccessibleMsg,
                    I18n.tr("dictDirAccessError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (CantCreateDestDir e){
            printAnyMsg(DfMCreatorException.CantCreateDestDirMsg,
                    I18n.tr("dictCreationError.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (Throwable t){
                printAnyMsg(I18n.tr("unknownError.dfmCreatorMain",
                        new Object[] {t, t.getLocalizedMessage()}),
                        I18n.tr("unknownErrorTitle"), JOptionPane.ERROR_MESSAGE);
            System.out.println(t + "\n");
        }
    }


    /**
     * resets the setting for the
     * DictionaryGeneration tab.
     */
    private void clearDictGenFields() {
        SourceFileTF.setText("");
        DirectoryDestinationTF.setText("");
        PropertyPathTF.setText("");
    }

    // Clears the contents of the TextFields of the
    // DictGen panel from DfMCreatorMain window.
    // The reason why i chose to not use the method above
    // is that it might one day be different from it,
    // though today they both do the same thing.
    public void clearDictGenTFs() {
        SourceFileTF.setText("");
        PropertyPathTF.setText("");
        DirectoryDestinationTF.setText("");
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
    public void setJarCreatorVals() throws FileNotFoundException, BadDictDirNameException,
                                   DictionaryDirectoryNotAccessible, PropFileErrorException,
                                   EmptyDfMJarJadDirDoesNotExist, EmptyDfMFileNotFound,
                                   OutputDirectoryNotAccessible, InputCSVFilesTFIsEmpty,
                                   EmptyDfMDirTFIsEmpty, OutputDirTFIsEmpty, IllegalArgumentException {

        if ("".equals(InputCSVFilesTF.getText())){
            throw new InputCSVFilesTFIsEmpty(I18n.tr("inputFileFieldEmpty.dfmCreatorMain"));
        }

        if ("".equals(OutputDirTF.getText())){
            throw new OutputDirTFIsEmpty(I18n.tr("outputDirField.dfmCreatorMain"));
        }
        dictionarydirectory = InputCSVFilesTF.getText();
        outputdirectory = OutputDirTF.getText();

        // We remove any trailling slash/backslash that
        // the user might have introduced in the path
        // we want to remothe the ones the user introduced
        // because,farther,we will add them and we dont want
        // to end up with thwo slashes/backslaches...
        dictionarydirectory = trim(dictionarydirectory);
        outputdirectory = trim(outputdirectory);
        // We add a system specific path separator.
        outputdirectory = outputdirectory + PATH_SEPARATOR;

        File dictdir = new File(dictionarydirectory);
        if (!dictdir.exists() || !dictdir.canRead() || !dictdir.isDirectory()){
            throw new DictionaryDirectoryNotAccessible(I18n.tr("dictDirAccessError.dfmCreatorMain"));
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
        // Activate the processing for the empty DfM jar-jad
        // files when the check box is selected.
        if (chooseCustomJarJadCBox.isSelected()){
            if ("".equals(EmptyDfMDirTF.getText())){
                throw new EmptyDfMDirTFIsEmpty(I18n.tr("emptyDfM.dfmCreatorMain"));
            }

            emptydictionaryformids = EmptyDfMDirTF.getText();
            emptydictionaryformids = trim(emptydictionaryformids);
            emptydictionaryformids = emptydictionaryformids + PATH_SEPARATOR;

            File emptydfmdir = new File(emptydictionaryformids);
            if (!emptydfmdir.exists() || !emptydfmdir.canRead() || !emptydfmdir.isDirectory()){
                throw new EmptyDfMJarJadDirDoesNotExist(I18n.tr("emptyJarJad.dfmCreatorMain"));
            }

            File emptydfm = new File(emptydictionaryformids + PATH_SEPARATOR + JarCreator.FILE_EMPTY_JAR_NAME);
            if (!emptydfm.exists() || !emptydfm.canRead()){
                throw new EmptyDfMFileNotFound(I18n.tr("emptyJarNotFound.dfmCreatorMain"));
            }
            externalEmptyDfMFlag = true;
        }

        // Creating a new item to be put in the queue.
        JarCreatorValsToEnqueue jarCreatorRecord = new JarCreatorValsToEnqueue();
        // This checks if the jarCreatorQueue hasn't already been created
        // otherwise creates it but if it already exists, prevents
        // overwriting it so that other dictionary generation items
        // may be enqueued.
        if (!jarCreatorQueueCreationtorFlag){
            jarCreatorQueue = new LinkedList<>();
            // Initializing our JarCreator ArrayList
            jarCreatorArray = new ArrayList<>();
            jarCreatorQueueCreationtorFlag = true;
        }

        // Passing values to the variables of this item
        jarCreatorRecord.dictDir = dictionarydirectory + PATH_SEPARATOR;
        jarCreatorRecord.outputDir = outputdirectory;

        if (chooseCustomJarJadCBox.isSelected()){
            jarCreatorRecord.emptyDfM = emptydictionaryformids;
        } else {
            //throw new IllegalArgumentException();
            String dfmPath = JAR_PATH_SEPARATOR + "de" + JAR_PATH_SEPARATOR + "kugihan"
                           + JAR_PATH_SEPARATOR + "DfMCreator" + JAR_PATH_SEPARATOR
                           + "Empty_DfM_JavaME" + JAR_PATH_SEPARATOR;
            jarCreatorRecord.emptyDfM = dfmPath;
        }

        // Actually adding the element to the queue
        jarCreatorQueue.add(jarCreatorRecord);

        // Adding the dictionaryDirectory path to the ArrayList
        // This will be used to display a representation of the queue.
        jarCreatorArray.add(jarCreatorRecord.dictDir);

        // As a test, try passing values to JarCreator
        JarCreator.setDictionaryDirectory(dictionarydirectory + PATH_SEPARATOR);
        JarCreator.setEmptyDictionaryForMID(jarCreatorRecord.emptyDfM);
        JarCreator.setOutputDirectory(outputdirectory);
    }

    public class JarCreatorValsToEnqueue {
        String dictDir;
        String emptyDfM;
        String outputDir;
    }

    /**
     * this method will execute everything for
     * JarCreator. it's like the actionPerformed
     * subroutine for JarCreator.
     */
    private void JarCreatorDoAll() {
        try {
            setJarCreatorVals();
            JarCreator.showJarCreationSum();
        } catch (FileNotFoundException ex){
            DfMCreatorException.printErrorMsg();
        } catch (BadDictDirNameException e){
            printAnyMsg(DfMCreatorException.BadDictDirNameExceptionMsg,
                    I18n.tr("badDirNameTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (PropFileErrorException e){
            printAnyMsg(DfMCreatorException.PropFileErrorExceptionMsg,
                    I18n.tr("propsFileErrorTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (EmptyDfMFileNotFound e){
            printAnyMsg(DfMCreatorException.EmptyDfMFileNotFoundMsg,
                    I18n.tr("emptyDfMTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (InputCSVFilesTFIsEmpty e){
            printAnyMsg(DfMCreatorException.InputCSVFilesTFIsEmptyMsg,
                    I18n.tr("filedEmptyErrorTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (EmptyDfMDirTFIsEmpty e){
            printAnyMsg(DfMCreatorException.EmptyDfMDirTFIsEmptyMsg,
                    I18n.tr("fieldEmptyErrorTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (OutputDirTFIsEmpty e){
            printAnyMsg(DfMCreatorException.OutputDirTFIsEmptyMsg,
                    I18n.tr("fieldEmptyErrorTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (OutputDirectoryNotAccessible e){
            printAnyMsg(DfMCreatorException.OutputDirectoryNotAccessibleMsg,
                    I18n.tr("dictAccessTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (EmptyDfMJarJadDirDoesNotExist e){
            printAnyMsg(DfMCreatorException.EmptyDfMJarJadDirDoesNotExistMsg,
                    I18n.tr("emptyJarJadTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (DictionaryDirectoryNotAccessible e){
            printAnyMsg(DfMCreatorException.DictionaryDirectoryNotAccessibleMsg,
                    I18n.tr("dictAccessTitle.dfmCreatorMain"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getLocalizedMessage());
        } catch (IllegalArgumentException e){
            // Before i find a way to fix this.
            printAnyMsg("This functionality is broken for now.\n"
                      + "Please go and select the checkbox so as\n"
                      + "to provide an external empty DictionaryForMIDs.\n"
                      + "This problem shall be fixed in a newer version.",
                        "Broken Bundled DfM", JOptionPane.ERROR_MESSAGE);
        } catch (Throwable t){
            printAnyMsg(I18n.tr("unknownRuntimeError.dfmCreatorMain",
                    new Object[] {t, t.getLocalizedMessage()}),
                    I18n.tr("unknownRuntimeErrorTitle"), JOptionPane.ERROR_MESSAGE);
            System.out.println(t + "\n");
        }
    }

    /**
     * resets the setting for the
     * JarCreator tab.
     */
    private void clearJarCreatorFields() {
        InputCSVFilesTF.setText("");
        EmptyDfMDirTF.setText("");
        OutputDirTF.setText("");
    }

    // Clears the contents of the TextFields of the
    // JarCreator panel from DfMCreatorMain window
    // but preserves the contents of the empty DfM TF
    public void clearJarCreatorTFsExceptEmptyDfM() {
        InputCSVFilesTF.setText("");
        OutputDirTF.setText("");
    }

    /**
     * BitmapFontGenerator subroutines
     */

    /**
     * addFontTKToDfMCreatorTabbedPane()
     * The font_size generator tab holds the FontToolkit created by Sean Kernohan.
     * When i started creating the DfM-Creator, i did not want to have to
     * write a new GUI from scratch for the BitmapFontGenerator. So, i decided
     * to modify the FontToolkit and integrate it in the DfM-Creator.
     * The code below does what's necessary to get the FontToolkit to
     * work with the DictionaryForMIDs-Creator.
     */
    private void addFontTKToDfMCreatorTabbedPane() {
        // Creating a toolbar
        javax.swing.JToolBar bfgToolbar = new javax.swing.JToolBar();
        bfgToolbar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        bfgToolbar.setFloatable(false);
        bfgToolbar.setRollover(true);
        bfgToolbar.setPreferredSize(new java.awt.Dimension(400, 15));
        bfgToolbar.setRequestFocusEnabled(false);

        // Creating a label for the toolbar
        javax.swing.JLabel toolbarLabel = new javax.swing.JLabel();
        toolbarLabel.setText("<html><font color=gray>BitmapFontGenerator</font>");
        bfgToolbar.add(toolbarLabel);

        // Hiding the little info textarea that is visible
        // during design and souldn't during runtime.
        infotarea.setVisible(false);

        // Creating a new layout to override
        // the one of the little info textarea.
        bfgPanel.setLayout(new java.awt.GridBagLayout());

        // Getting the FontToolkit panel
        JPanel fontTKPanel = fontTK.getJPanel();

        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
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
    }

    public void createQueueForBFG(){
        // Creating a new item to be put in the queue.
        BitmapFontGeneratorToEnqueue bfgenRecord = new BitmapFontGeneratorToEnqueue();

        // This checks if the fontGenerationQueue hasn't already been created
        // otherwise creates it but if it already exists, avoids
        // overwriting it so that other conversion settings may
        // be enqueued.
        if (!fontGenerationQueueCreationFlag){
            fontGenerationQueue = new LinkedList<>();
            // Initializing our ArrayList
            fontGenerationArray = new ArrayList<>();
            fontGenerationQueueCreationFlag = true;
        }

        // Passing values to the variables of this item
        bfgenRecord.in_file = fontTK.getInputFontFile();
        bfgenRecord.dict_dir = fontTK.getDirFile();
        bfgenRecord.font_directory = fontTK.getFontDirectory();
        bfgenRecord.cback = fontTK.getCallback();
        bfgenRecord.font_size = fontTK.getFontSize();
        bfgenRecord.clip_top = fontTK.getClipTop();
        bfgenRecord.clip_bottom = fontTK.getClipBottom();

        // Actually adding the element to the queue
        fontGenerationQueue.add(bfgenRecord);

        // Adding the dbName variable to the ArrayList
        // This will be used to display a representation of the queue.
        fontGenerationArray.add(String.valueOf(bfgenRecord.font_size));
    }

    public class BitmapFontGeneratorToEnqueue {
        File in_file;
        File dict_dir;
        String font_directory;
        Callback cback;
        int font_size;
        int clip_top;
        int clip_bottom;
    }


    /*
     *
     * miscellaneous subroutiness
     *
     */

    /**
     * CreateAndDisplayTheForm() creates and displays
     * The DfM-Creator form.
     */
    private static void CreateAndDisplayTheForm() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                dfmCreator = new DfMCreatorMain();
                dfmCreator.setLocation(screenSize.width / 2 - dfmCreator.getWidth() / 2,
                                     screenSize.height / 2 - dfmCreator.getHeight() / 2);
                dfmCreator.setVisible(true);
            }
        });
    }

    /**
     * printCopyrightNotice() prints a copyright
     * statement and then exits.
     */
    public static void printCopyrightNotice(){
    System.out.println("\n\nDictionaryForMIDs-Creator V" + dfm_creator_version + ","
                    + "(C) 2012 Karim Mahamane Karimou\n"
                    + "DictionaryForMIDs-Creator comes with ABSOLUTELY NO WARRANTY.\n"
                    + "This is free software, and you are welcome to redistribute it\n"
                    + "under the terms and conditions of the GNU General Public License.\n\n");
    }


    /**
     * setTheLocale() gets and sets the default
     * locale. It can also test other locales
     * during debug sessions for example.

    private static void setTheLocale() {
        //Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        //Locale locale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
        Locale locale = Locale.getDefault();

        // debug
        System.out.println("Locale: " + locale.toString());
        I18n.setLocale(locale);
    }*/

    /**
     * showAbout() gets the about window
     */
    private void showAbout() {
        AboutBox aboutbox = AboutBox.getAboutBox();
        aboutbox.setSize(525, 485);
        aboutbox.setModal(true);
        aboutbox.setLocation(screenSize.width / 2 - aboutbox.getWidth() / 2,
                          screenSize.height / 2 - aboutbox.getHeight() / 2);
        aboutbox.setVisible(true);
    }

    /**
     * showPrefsWin() gets the preferences window.
     */
    private void showPrefsWin(){
        PreferencesBox prefs = PreferencesBox.getPrefsWin();
        prefs.setVisible(true);
    }

    /**
     * showHelpWindow() gets the help window
     */
    private void showHelpWindow() {
        JFrame hw = HelpContents.createAndShowGUI();
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
    * saveLanguagePrefs() saves the preferences
    * settings relative to the language of the UI
    * @param look the selected look and feel
    */
   public static void saveLanguagePrefs(String lang) {
      try {
         node.put("DfMCreator.language.Settings", lang);
         printAnyMsg(I18n.tr("lang.prefs.saved.DfMCreatorMain"),
                     I18n.tr("lang.prefs.saved.WinTitle.DfMCreatorMain"), JOptionPane.INFORMATION_MESSAGE);
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

             //TODO: Becomes a placeholder now for writing new code :-)

         } else {
             if (LookAndFeel != null) {
                try {
                    javax.swing.UIManager.setLookAndFeel(LookAndFeel);
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException t) {
                    System.out.println(t.getLocalizedMessage() + "\n" + t.getCause());
                }
             }
             if (selectedLang != null){
                Locale selectedLocale = Locale.forLanguageTag(selectedLang);
                I18n.setLocale(selectedLocale);
                System.out.println("Locale: " + selectedLocale.toString());
             } else {
                //Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
                //Locale locale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
                Locale defaultLocale = Locale.getDefault();
                I18n.setLocale(defaultLocale);
                System.out.println("Locale: " + defaultLocale.toString());
             }
          }
      }
      catch (Throwable t) {
          System.out.println(t + "\n" + t.getCause());
      }
   }

    /**
     * OwnTextFieldCheck() checks if the "Choose Your Own"
     * radio button is selected in order to enable/disable
     * the little text area that lets the user provide
     * her own choice instead of the ones given by default.
     */
    private void OwnTextFieldCheck(){
        if (OwnSCTextField.isEnabled()) {
            OwnSCTextField.setEnabled(false);
        }
    }

    /**
     * OwnEncTextFieldCheck() checks if the "Choose Your Own"
     * radio button is selected in order to enable/disable
     * the little text area that enables the user to provide
     * her own choice instead of the ones given by default.
     */
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
    public String getFile(boolean dirsOnly) {
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
     * checkScreenResolution() checks the resolution
     * of the screen just before calling PropertiesEditor
     */
/*    public void checkScreenResolution(){
        if (screenSize.getHeight() < PropertiesEditor.Default_height_2_Langs ||
            screenSize.getWidth() < PropertiesEditor.Default_width) {
            JOptionPane.showMessageDialog(null, I18n.tr("screenResolution.dfmCreatorMain"),
                                   I18n.tr("screenResolutionTitle.dfmCreatorMain"), JOptionPane.INFORMATION_MESSAGE);
        }
    } */


    /**
     * showPropWin() gets the PropertiesEditor Window.
     */
    public void showPropWin(){
        propWin = PropertiesEditor.getPropWin();
        // this subroutine will set the selected index
        // of the combo box to "2" languages and hide all
        // textfields, labels, buttons related to language-3
        // it also resizes the properties window accordingly
        propWin.updateNumOfLang();
        propWin.setLocation(screenSize.width / 2 - propWin.getWidth() / 2,
                          screenSize.height / 2 - propWin.getHeight() / 2);
        propWin.setVisible(true);
    }


    private void editExistingPropsFile(){
        //Custom button text
        Object[] options = {I18n.tr("in.a.text.editor.PropsFileEditing"),
                            I18n.tr("in.props.editor.PropsFileEditing"),
                            I18n.tr("dismiss.PropsFileCreation")};
        int n = JOptionPane.showOptionDialog(this,
            I18n.tr("choose.editing.mode.PropsFileEditing"),
            I18n.tr("editing.mode.win.title.PropsFileEditing"),
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]);

        if (n == JOptionPane.YES_OPTION){
            editExistingPropsFileInTextEditor();
        } else if (n == JOptionPane.NO_OPTION){
            editExistingPropsFileInPropsEditorWin();
        } else if (n == JOptionPane.CANCEL_OPTION || n == JOptionPane.CLOSED_OPTION){
            // Do nothing for now.
        }
    }

    /**
     * getPropertiesFile() Edits an existing
     * DictionaryForMIDs.properties file in the PropertiesEditor.
     */
    private void editExistingPropsFileInPropsEditorWin(){
        PropertiesEditor propWindow = PropertiesEditor.getPropWin();
        try {
            propWindow.getPropertiesFile();
        } catch (UnsupportedOperationException ex){
            DfMCreatorMain.printAnyMsg(PropertiesEditor.openPropFileErrorMsg,
                    I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            DfMCreatorMain.printAnyMsg(PropertiesEditor.NotThePropFileErrorMsg,
                    I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
        if (PropertiesEditor.openPropsFileForGUIEditFlag){
            propWindow.updatePropsEditLabel();
            propWindow.updateNumOfLang();
            propWindow.setLocation(screenSize.width / 2 - propWindow.getWidth() / 2,
                                screenSize.height / 2 - propWindow.getHeight() / 2);
            propWindow.setVisible(true);
            propWindow.expandPropsInPropsEditor();
        }
    }


    /**
     * editExistingPropsFileInTextEditor() Edits an existing
     * DictionaryForMIDs.properties file in the Preview window.
     */
    private void editExistingPropsFileInTextEditor(){
        PropertiesEditor propWindow = PropertiesEditor.getPropWin();
        propWindow.setVisible(false);
        propWindow.editExistingPropsFileInTextEditor();
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


    public static void setTheNimbusLookAndFeel() {
        /*
         * Copied from a NetBeans generated code from
         * another project. Seems to be the easiest way
         * to set nimbus as the default look and feel.
         */

        // If Nimbus (introduced in Java SE 6) is not available,
        // stay with the default look and feel. For details see
        // http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void ownSCTextFieldSetEnabled() {
        OwnSCTextField.setEnabled(true);
    }

    private void outCSVFileBrowseGetFile() {
        String s = getFile(true);
        if (!"".equals(s)){
            OutCSVFileTextField.setText(s);
        }
    }

    private void dbBrowseButtonGetFile() {
        String s = getFile(true);
        if (!"".equals(s)){
            DBPathTextField.setText(s);
            OutCSVFileTextField.setText(DBPathTextField.getText());
        }
    }

    private void ownEncTextFieldSetEnabled() {
        OwnEncTextField.setEnabled(true);
    }

    private void sourceFileBrowseBTGetFile() {
        String s = getFile(false);
        if (!"".equals(s)){
            SourceFileTF.setText(s);
        }
    }

    private void directoryDestinationBrowseBTGetFile() {
        String s = getFile(true);
        if (!"".equals(s)){
            DirectoryDestinationTF.setText(s);
        }
    }

    private void propertyPathBTGetFile() {
        String s = getFile(true);
        if (!"".equals(s)){
            PropertyPathTF.setText(s);
            DirectoryDestinationTF.setText(PropertyPathTF.getText());
        }
    }

    private void inputCSVFilesButtonGetFile() {
        String s = getFile(true);
        if (!"".equals(s)){
            InputCSVFilesTF.setText(s);
        }
    }

    private void emptyDfMDirButtonGetFile() {
        String s = getFile(true);
        if (!"".equals(s)){
            EmptyDfMDirTF.setText(s);
        }
    }

    private void outputDirButtonGetFile() {
        String s = getFile(true);
        if (!"".equals(s)){
            OutputDirTF.setText(s);
        }
    }

}
