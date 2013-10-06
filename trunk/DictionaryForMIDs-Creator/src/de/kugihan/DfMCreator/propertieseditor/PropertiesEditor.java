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
package de.kugihan.DfMCreator.propertieseditor;

import de.kugihan.DfMCreator.DfMCreatorMain;
import de.kugihan.DfMCreator.utils.InputDialog;
import de.kugihan.DfMCreator.utils.RegexpUtils;
import edu.hws.eck.mdb.I18n;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class PropertiesEditor extends javax.swing.JFrame {

    public static PropertiesEditor getPropWin() {
        return new PropertiesEditor();
    }
    public static final PropertiesPreview proPrevWin = PropertiesPreview.getPropPreviewWin();
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final JFileChooser fc = new JFileChooser();
    // Some look and feel class names
    public static final String gtk = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
    public static final String nimbus = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
    public static final String metal = "javax.swing.plaf.metal.MetalLookAndFeel";
    public static final String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    // The size that the PropertiesEditor window should
    // have depending on the look and feel selected.
    // GTk
/*    public static final int GTk_height_2_Langs = 670;
     public static final int GTk_height_3_Langs = 685;
     public static final int GTk_width = 1377;

     // Nimbus
     public static final int Nimbus_height_2_Langs = 560;
     public static final int Nimbus_height_3_Langs = 590;
     public static final int Nimbus_width = 1075;

     // Metal
     public static final int Metal_height_2_Langs = 520;
     public static final int Metal_height_3_Langs = 550;
     public static final int Metal_width = 1135;

     // Motif
     public static final int Motif_height_2_Langs = 610;
     public static final int Motif_height_3_Langs = 640;
     public static final int Motif_width = 1010;

     // Default
     public static final int Default_height_2_Langs = 738;
     public static final int Default_height_3_Langs = 768;
     public static final int Default_width = 1200; */
    /*
     * Variable declarations for
     * the property file editor
     */
    // variables unique to all
    // languages in the dict file
    private static String infoText = "";
    private static String dictionaryAbbreviation = "";
    private static String numberOfAvailableLanguages = "";
    private static String dictionaryGenerationSeparatorCharacter = "'\\t'";
    private static String indexFileSeparationCharacter = "'\\t'";
    private static String searchListFileSeparationCharacter = "'\\t'";
    private static String dictionaryFileSeparationCharacter = "'\\t'";
    private static String dictionaryGenerationInputCharEncoding = "";
    private static String indexCharEncoding = "";
    private static String searchListCharEncoding = "";
    private static String dictionaryCharEncoding = "";
    private static String searchListFileMaxSize = "";
    private static String indexFileMaxSize = "";
    private static String dictionaryFileMaxSize = "";
    private static String dictionaryGenerationMinNumberOfEntriesPerDictionaryFile = "";
    private static String dictionaryGenerationMinNumberOfEntriesPerIndexFile = "";
    private static String dictionaryGenerationOmitParFromIndex = "";
    // language specific variables.
    // language 1
    private static String language1DisplayText = "";
    private static String language1FilePostfix = "";
    private static String language1IsSearchable = "";
    private static String language1GenerateIndex = "";
    private static String language1HasSeparateDictionaryFile = "";
    private static String dictionaryGenerationLanguage1ExpressionSplitString = "";
    private static String language1DictionaryUpdateClassName = "";
    private static String language1NormationClassName = "";
    private static String language1IndexNumberOfSourceEntries = "";
    // language 2
    private static String language2DisplayText = "";
    private static String language2FilePostfix = "";
    private static String language2IsSearchable = "";
    private static String language2GenerateIndex = "";
    private static String language2HasSeparateDictionaryFile = "";
    private static String dictionaryGenerationLanguage2ExpressionSplitString = "";
    private static String language2DictionaryUpdateClassName = "";
    private static String language2NormationClassName = "";
    private static String language2IndexNumberOfSourceEntries = "";
    // language 3
    private static String language3DisplayText = "";
    private static String language3FilePostfix = "";
    private static String language3IsSearchable = "";
    private static String language3GenerateIndex = "";
    private static String language3HasSeparateDictionaryFile = "";
    private static String dictionaryGenerationLanguage3ExpressionSplitString = "";
    private static String language3DictionaryUpdateClassName = "";
    private static String language3NormationClassName = "";
    private static String language3IndexNumberOfSourceEntries = "";
    // other variables
    private static String colon = ":";
    private static final String newline = "\n";
    // Content Declaration Variables
    public static String language1NumberOfContentDeclarations = "";
    public static String language2NumberOfContentDeclarations = "";
    public static String language3NumberOfContentDeclarations = "";
    // Other ContentNN properties Variables
    // Language 1
    public static String language1Content01DisplayText = "";
    public static String language1Content01FontColour = "";
    public static String language1Content01FontStyle = "";
    public static String language1Content01DisplaySelectable = "";
    public static String language1Content02DisplayText = "";
    public static String language1Content02FontColour = "";
    public static String language1Content02FontStyle = "";
    public static String language1Content02DisplaySelectable = "";
    public static String language1Content03DisplayText = "";
    public static String language1Content03FontColour = "";
    public static String language1Content03FontStyle = "";
    public static String language1Content03DisplaySelectable = "";
    public static String language1Content04DisplayText = "";
    public static String language1Content04FontColour = "";
    public static String language1Content04FontStyle = "";
    public static String language1Content04DisplaySelectable = "";

    /*
     public static String language1Content05DisplayText = "";
     public static String language1Content05FontColour = "";
     public static String language1Content05FontStyle = "";
     public static String language1Content05DisplaySelectable = "";
     */
    // Language 2
    public static String language2Content01DisplayText = "";
    public static String language2Content01FontColour = "";
    public static String language2Content01FontStyle = "";
    public static String language2Content01DisplaySelectable = "";
    public static String language2Content02DisplayText = "";
    public static String language2Content02FontColour = "";
    public static String language2Content02FontStyle = "";
    public static String language2Content02DisplaySelectable = "";
    public static String language2Content03DisplayText = "";
    public static String language2Content03FontColour = "";
    public static String language2Content03FontStyle = "";
    public static String language2Content03DisplaySelectable = "";
    public static String language2Content04DisplayText = "";
    public static String language2Content04FontColour = "";
    public static String language2Content04FontStyle = "";
    public static String language2Content04DisplaySelectable = "";

    /*
     public static String language2Content05DisplayText = "";
     public static String language2Content05FontColour = "";
     public static String language2Content05FontStyle = "";
     public static String language2Content05DisplaySelectable = "";
     */
    // Language 3
    public static String language3Content01DisplayText = "";
    public static String language3Content01FontColour = "";
    public static String language3Content01FontStyle = "";
    public static String language3Content01DisplaySelectable = "";
    public static String language3Content02DisplayText = "";
    public static String language3Content02FontColour = "";
    public static String language3Content02FontStyle = "";
    public static String language3Content02DisplaySelectable = "";
    public static String language3Content03DisplayText = "";
    public static String language3Content03FontColour = "";
    public static String language3Content03FontStyle = "";
    public static String language3Content03DisplaySelectable = "";
    public static String language3Content04DisplayText = "";
    public static String language3Content04FontColour = "";
    public static String language3Content04FontStyle = "";
    public static String language3Content04DisplaySelectable = "";

    /*
     public static String language3Content05DisplayText = "";
     public static String language3Content05FontColour = "";
     public static String language3Content05FontStyle = "";
     public static String language3Content05DisplaySelectable = "";
     */
    // The properties file to be edited.
    private File propsFileChosen;
    // Tells us wether the user chose a file or not
    public static boolean openPropsFileForGUIEditFlag = false;
    // Directory of the properties file to be edited
    public static String propertiesFile;
    // The properties file itself
    File propsFile;
    private ArrayList<String> loadedProperties;
    private ArrayList<String> nonLoadedProperties;
    public static Properties dfMProps;

    /**
     * Creates new form PropertiesEditor
     */
    public PropertiesEditor() {
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

        jScrollPane2 = new javax.swing.JScrollPane();
        mainContainer = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        indexCharEncodingCB = new javax.swing.JComboBox();
        searchListCharEncodingCB = new javax.swing.JComboBox();
        dictionaryCharEncodingCB = new javax.swing.JComboBox();
        jPanel11 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        langDictUpdateLB3 = new javax.swing.JLabel();
        langDictUpdateCB1 = new javax.swing.JComboBox();
        langDictUpdateCB2 = new javax.swing.JComboBox();
        langDictUpdateCB3 = new javax.swing.JComboBox();
        jPanel17 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        langExpSplitStringTF1 = new javax.swing.JTextField();
        langExpSplitStringTF2 = new javax.swing.JTextField();
        langExpSplitStringLB3 = new javax.swing.JLabel();
        langExpSplitStringTF3 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        langIsSearchCB1 = new javax.swing.JCheckBox();
        langIsSearchCB2 = new javax.swing.JCheckBox();
        langIsSearchCB3 = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        langNormLB3 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        langNormCB1 = new javax.swing.JComboBox();
        langNormCB2 = new javax.swing.JComboBox();
        langNormCB3 = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        langFilePostfixLB3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        langPostfixCB1 = new javax.swing.JComboBox();
        langPostfixCB2 = new javax.swing.JComboBox();
        langPostfixCB3 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        langHasSepDictCB3 = new javax.swing.JCheckBox();
        langHasSepDictCB1 = new javax.swing.JCheckBox();
        langHasSepDictCB2 = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        additionalInfo = new javax.swing.JButton();
        samplePropFile = new javax.swing.JButton();
        createPropFile = new javax.swing.JButton();
        resetSetting = new javax.swing.JButton();
        editExistingBT = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        dictGenSepCharCB = new javax.swing.JComboBox();
        searchListSepCharCB = new javax.swing.JComboBox();
        indexFileSepCharCB = new javax.swing.JComboBox();
        dictFileSepCharCB = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dictAbbrevTF = new javax.swing.JTextField();
        numOfLangCmbBox = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        listMaxSizeSL = new javax.swing.JSlider();
        jLabel37 = new javax.swing.JLabel();
        lab3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        langGenIndCB1 = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        langGenIndCB2 = new javax.swing.JCheckBox();
        langGenIndCB3 = new javax.swing.JCheckBox();
        jPanel15 = new javax.swing.JPanel();
        indexMaxSizeSL = new javax.swing.JSlider();
        dictMaxSizeSL = new javax.swing.JSlider();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lab4 = new javax.swing.JLabel();
        lab5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        langDispTextTF3 = new javax.swing.JTextField();
        langDispTextLB3 = new javax.swing.JLabel();
        langDispTextTF2 = new javax.swing.JTextField();
        langDispTextTF1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        minNumEntPerDictFileSL = new javax.swing.JSlider();
        jLabel34 = new javax.swing.JLabel();
        lab1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        langIndNumSrcEntLB3 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        langIndNumSrcEntTF1 = new javax.swing.JTextField();
        langIndNumSrcEntTF2 = new javax.swing.JTextField();
        langIndNumSrcEntTF3 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoTextTA = new javax.swing.JTextArea();
        infoTextLabel = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        minNumEntPerIndFileSL = new javax.swing.JSlider();
        lab2 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        dictGenOmitParCB = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        dictGenInputCharEncodingCB = new javax.swing.JComboBox();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        editingTaskInfo = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(I18n.tr("winTitle.dfmPropCreate")); // NOI18N
        setMinimumSize(new java.awt.Dimension(800, 600));

        mainContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainContainer.setLayout(new java.awt.GridBagLayout());

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setLayout(new java.awt.GridBagLayout());

        jLabel18.setText("indexCharEncoding");
        jLabel18.setToolTipText(I18n.tr("indexCharEncoding.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel10.add(jLabel18, gridBagConstraints);

        jLabel7.setText("searchListCharEncoding");
        jLabel7.setToolTipText(I18n.tr("searchList.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel10.add(jLabel7, gridBagConstraints);

        jLabel11.setText("dictionaryCharEncoding");
        jLabel11.setToolTipText(I18n.tr("dictionary.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel10.add(jLabel11, gridBagConstraints);

        indexCharEncodingCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UTF-8", "ISO-8859-1", "US-ASCII" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel10.add(indexCharEncodingCB, gridBagConstraints);

        searchListCharEncodingCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UTF-8", "ISO-8859-1", "US-ASCII" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel10.add(searchListCharEncodingCB, gridBagConstraints);

        dictionaryCharEncodingCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UTF-8", "ISO-8859-1", "US-ASCII" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel10.add(dictionaryCharEncodingCB, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel10, gridBagConstraints);

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel19.setText("languageXDictUpdateClass");
        jLabel19.setToolTipText(I18n.tr("dictUpdate.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(jLabel19, gridBagConstraints);

        jLabel20.setText(I18n.tr("language-1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(jLabel20, gridBagConstraints);

        jLabel21.setText(I18n.tr("language-2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(jLabel21, gridBagConstraints);

        langDictUpdateLB3.setText(I18n.tr("language-3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(langDictUpdateLB3, gridBagConstraints);

        langDictUpdateCB1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdate", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateCEDICTChi", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEDICTJpn", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEngDef", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEngDefPronunciation", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEpo", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateFraDef", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateFreedictDeuEngGer", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateFreedictEngHin", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateHanDeDictChi", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateHanDeDictGer", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDP", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPEng", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPGer", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPIta", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPLat", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPSpa", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateLib", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateMuellerRus", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdatePartialIndex", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateThaiNIUEng", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateWordnetDefinition" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(langDictUpdateCB1, gridBagConstraints);

        langDictUpdateCB2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdate", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateCEDICTChi", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEDICTJpn", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEngDef", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEngDefPronunciation", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEpo", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateFraDef", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateFreedictDeuEngGer", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateFreedictEngHin", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateHanDeDictChi", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateHanDeDictGer", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDP", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPEng", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPGer", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPIta", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPLat", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPSpa", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateLib", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateMuellerRus", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdatePartialIndex", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateThaiNIUEng", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateWordnetDefinition" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(langDictUpdateCB2, gridBagConstraints);

        langDictUpdateCB3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdate", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateCEDICTChi", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEDICTJpn", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEngDef", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEngDefPronunciation", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateEpo", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateFraDef", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateFreedictDeuEngGer", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateFreedictEngHin", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateHanDeDictChi", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateHanDeDictGer", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDP", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPEng", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPGer", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPIta", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPLat", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateIDPSpa", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateLib", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateMuellerRus", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdatePartialIndex", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateThaiNIUEng", "de.kugihan.dictionaryformids.dictgen.dictionaryupdate.DictionaryUpdateWordnetDefinition" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(langDictUpdateCB3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel11, gridBagConstraints);

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel17.setLayout(new java.awt.GridBagLayout());

        jLabel41.setText("<html>dictGenLanguageX<br>ExpressionSplitString</html>");
        jLabel41.setToolTipText(I18n.tr("expressionSplitString.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(jLabel41, gridBagConstraints);

        jPanel21.setLayout(new java.awt.GridBagLayout());

        jLabel30.setText(I18n.tr("language-2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel21.add(jLabel30, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel21.add(langExpSplitStringTF1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel21.add(langExpSplitStringTF2, gridBagConstraints);

        langExpSplitStringLB3.setText(I18n.tr("language-3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel21.add(langExpSplitStringLB3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel21.add(langExpSplitStringTF3, gridBagConstraints);

        jLabel29.setText(I18n.tr("language-1")); // NOI18N
        jLabel29.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel21.add(jLabel29, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel17.add(jPanel21, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel17, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel12.setText("languageXIsSearchable");
        jLabel12.setToolTipText(I18n.tr("langIsSearchable.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel6.add(jLabel12, gridBagConstraints);

        langIsSearchCB1.setText(I18n.tr("language-1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel6.add(langIsSearchCB1, gridBagConstraints);

        langIsSearchCB2.setText(I18n.tr("language-2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel6.add(langIsSearchCB2, gridBagConstraints);

        langIsSearchCB3.setText(I18n.tr("language-3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel6.add(langIsSearchCB3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel6, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setLayout(new java.awt.GridBagLayout());

        langNormLB3.setText(I18n.tr("language-3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(langNormLB3, gridBagConstraints);

        jLabel24.setText(I18n.tr("language-1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(jLabel24, gridBagConstraints);

        jLabel23.setText("languageXNormationClass");
        jLabel23.setToolTipText(I18n.tr("dictNorm.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(jLabel23, gridBagConstraints);

        jLabel25.setText(I18n.tr("language-2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(jLabel25, gridBagConstraints);

        langNormCB1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationBul", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationCyr1", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationCyr2", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationEng", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationEng2", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationEpo", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationFil", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationGer", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationJpn", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationLat", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationLib", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationNor", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationRus", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationRus2", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationRusC", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationUkr", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationUkrC", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationVie" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(langNormCB1, gridBagConstraints);

        langNormCB2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationBul", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationCyr1", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationCyr2", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationEng", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationEng2", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationEpo", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationFil", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationGer", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationJpn", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationLat", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationLib", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationNor", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationRus", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationRus2", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationRusC", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationUkr", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationUkrC", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationVie" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(langNormCB2, gridBagConstraints);

        langNormCB3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationBul", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationCyr1", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationCyr2", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationEng", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationEng2", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationEpo", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationFil", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationGer", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationJpn", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationLat", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationLib", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationNor", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationRus", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationRus2", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationRusC", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationUkr", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationUkrC", "de.kugihan.dictionaryformids.translation.normation.Normation.NormationVie" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(langNormCB3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel12, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel8.setText("* languageXFilePostfix");
        jLabel8.setToolTipText(I18n.tr("langFilePostfix.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel5.add(jLabel8, gridBagConstraints);

        jPanel20.setLayout(new java.awt.GridBagLayout());

        jLabel9.setText(I18n.tr("language-1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel20.add(jLabel9, gridBagConstraints);

        langFilePostfixLB3.setText(I18n.tr("language-3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel20.add(langFilePostfixLB3, gridBagConstraints);

        jLabel10.setText(I18n.tr("language-2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel20.add(jLabel10, gridBagConstraints);

        langPostfixCB1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "﻿aar", "abk", "ace", "ach", "ada", "ady", "afa", "afh", "afr", "ain", "aka", "akk", "alb", "ale", "alg", "alt", "amh", "ang", "anp", "apa", "ara", "arc", "arg", "arm", "arn", "arp", "art", "arw", "asm", "ast", "ath", "aus", "ava", "ave", "awa", "aym", "aze", "bad", "bai", "bak", "bal", "bam", "ban", "baq", "bas", "bat", "bej", "bel", "bem", "ben", "ber", "bho", "bih", "bik", "bin", "bis", "bla", "bnt", "bos", "bra", "bre", "btk", "bua", "bug", "bul", "bur", "byn", "cad", "cai", "car", "cat", "cau", "ceb", "cel", "cha", "chb", "che", "chg", "chi", "chk", "chm", "chn", "cho", "chp", "chr", "chu", "chv", "chy", "cmc", "cop", "cor", "cos", "cpe", "cpf", "cpp", "cre", "crh", "crp", "csb", "cus", "cze", "dak", "dan", "dar", "day", "del", "den", "dgr", "din", "div", "doi", "dra", "dsb", "dua", "dum", "dut", "dyu", "dzo", "efi", "egy", "eka", "elx", "eng", "enm", "epo", "est", "ewe", "ewo", "fan", "fao", "fat", "fij", "fil", "fin", "fiu", "fon", "fre", "frm", "fro", "frr", "frs", "fry", "ful", "fur", "gaa", "gay", "gba", "gem", "geo", "ger", "gez", "gil", "gla", "gle", "glg", "glv", "gmh", "goh", "gon", "gor", "got", "grb", "grc", "gre", "grn", "gsw", "guj", "gwi", "hai", "hat", "hau", "haw", "heb", "her", "hil", "him", "hin", "hit", "hmn", "hmo", "hrv", "hsb", "hun", "hup", "iba", "ibo", "ice", "ido", "iii", "ijo", "iku", "ile", "ilo", "ina", "inc", "ind", "ine", "inh", "ipk", "ira", "iro", "ita", "jav", "jbo", "jpn", "jpr", "jrb", "kaa", "kab", "kac", "kal", "kam", "kan", "kar", "kas", "kau", "kaw", "kaz", "kbd", "kha", "khi", "khm", "kho", "kik", "kin", "kir", "kmb", "kok", "kom", "kon", "kor", "kos", "kpe", "krc", "krl", "kro", "kru", "kua", "kum", "kur", "kut", "lad", "lah", "lam", "lao", "lat", "lav", "lez", "lim", "lin", "lit", "lol", "loz", "ltz", "lua", "lub", "lug", "lui", "lun", "luo", "lus", "mac", "mad", "mag", "mah", "mai", "mak", "mal", "man", "mao", "map", "mar", "mas", "may", "mdf", "mdr", "men", "mga", "mic", "min", "mis", "mkh", "mlg", "mlt", "mnc", "mni", "mno", "moh", "mon", "mos", "mul", "mun", "mus", "mwl", "mwr", "myn", "myv", "nah", "nai", "nap", "nau", "nav", "nbl", "nde", "ndo", "nds", "nep", "new", "nia", "nic", "niu", "nno", "nob", "nog", "non", "nor", "nqo", "nso", "nub", "nwc", "nya", "nym", "nyn", "nyo", "nzi", "oci", "oji", "ori", "orm", "osa", "oss", "ota", "oto", "paa", "pag", "pal", "pam", "pan", "pap", "pau", "peo", "per", "phi", "phn", "pli", "pol", "pon", "por", "pra", "pro", "pus", "qaa-qtz", "que", "raj", "rap", "rar", "roa", "roh", "rom", "rum", "run", "rup", "rus", "sad", "sag", "sah", "sai", "sal", "sam", "san", "sas", "sat", "scn", "sco", "sel", "sem", "sga", "sgn", "shn", "sid", "sin", "sio", "sit", "sla", "slo", "slv", "sma", "sme", "smi", "smj", "smn", "smo", "sms", "sna", "snd", "snk", "sog", "som", "son", "sot", "spa", "srd", "srn", "srp", "srr", "ssa", "ssw", "suk", "sun", "sus", "sux", "swa", "swe", "syc", "syr", "tah", "tai", "tam", "tat", "tel", "tem", "ter", "tet", "tgk", "tgl", "tha", "tib", "tig", "tir", "tiv", "tkl", "tlh", "tli", "tmh", "tog", "ton", "tpi", "tsi", "tsn", "tso", "tuk", "tum", "tup", "tur", "tut", "tvl", "twi", "tyv", "udm", "uga", "uig", "ukr", "umb", "und", "urd", "uzb", "vai", "ven", "vie", "vol", "vot", "wak", "wal", "war", "was", "wel", "wen", "wln", "wol", "xal", "xho", "yao", "yap", "yid", "yor", "ypk", "zap", "zbl", "zen", "zgh", "zha", "znd", "zul", "zun", "zxx", "zza", "CHOOSE MY OWN" }));
        langPostfixCB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                langPostfixCB1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel20.add(langPostfixCB1, gridBagConstraints);

        langPostfixCB2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "﻿aar", "abk", "ace", "ach", "ada", "ady", "afa", "afh", "afr", "ain", "aka", "akk", "alb", "ale", "alg", "alt", "amh", "ang", "anp", "apa", "ara", "arc", "arg", "arm", "arn", "arp", "art", "arw", "asm", "ast", "ath", "aus", "ava", "ave", "awa", "aym", "aze", "bad", "bai", "bak", "bal", "bam", "ban", "baq", "bas", "bat", "bej", "bel", "bem", "ben", "ber", "bho", "bih", "bik", "bin", "bis", "bla", "bnt", "bos", "bra", "bre", "btk", "bua", "bug", "bul", "bur", "byn", "cad", "cai", "car", "cat", "cau", "ceb", "cel", "cha", "chb", "che", "chg", "chi", "chk", "chm", "chn", "cho", "chp", "chr", "chu", "chv", "chy", "cmc", "cop", "cor", "cos", "cpe", "cpf", "cpp", "cre", "crh", "crp", "csb", "cus", "cze", "dak", "dan", "dar", "day", "del", "den", "dgr", "din", "div", "doi", "dra", "dsb", "dua", "dum", "dut", "dyu", "dzo", "efi", "egy", "eka", "elx", "eng", "enm", "epo", "est", "ewe", "ewo", "fan", "fao", "fat", "fij", "fil", "fin", "fiu", "fon", "fre", "frm", "fro", "frr", "frs", "fry", "ful", "fur", "gaa", "gay", "gba", "gem", "geo", "ger", "gez", "gil", "gla", "gle", "glg", "glv", "gmh", "goh", "gon", "gor", "got", "grb", "grc", "gre", "grn", "gsw", "guj", "gwi", "hai", "hat", "hau", "haw", "heb", "her", "hil", "him", "hin", "hit", "hmn", "hmo", "hrv", "hsb", "hun", "hup", "iba", "ibo", "ice", "ido", "iii", "ijo", "iku", "ile", "ilo", "ina", "inc", "ind", "ine", "inh", "ipk", "ira", "iro", "ita", "jav", "jbo", "jpn", "jpr", "jrb", "kaa", "kab", "kac", "kal", "kam", "kan", "kar", "kas", "kau", "kaw", "kaz", "kbd", "kha", "khi", "khm", "kho", "kik", "kin", "kir", "kmb", "kok", "kom", "kon", "kor", "kos", "kpe", "krc", "krl", "kro", "kru", "kua", "kum", "kur", "kut", "lad", "lah", "lam", "lao", "lat", "lav", "lez", "lim", "lin", "lit", "lol", "loz", "ltz", "lua", "lub", "lug", "lui", "lun", "luo", "lus", "mac", "mad", "mag", "mah", "mai", "mak", "mal", "man", "mao", "map", "mar", "mas", "may", "mdf", "mdr", "men", "mga", "mic", "min", "mis", "mkh", "mlg", "mlt", "mnc", "mni", "mno", "moh", "mon", "mos", "mul", "mun", "mus", "mwl", "mwr", "myn", "myv", "nah", "nai", "nap", "nau", "nav", "nbl", "nde", "ndo", "nds", "nep", "new", "nia", "nic", "niu", "nno", "nob", "nog", "non", "nor", "nqo", "nso", "nub", "nwc", "nya", "nym", "nyn", "nyo", "nzi", "oci", "oji", "ori", "orm", "osa", "oss", "ota", "oto", "paa", "pag", "pal", "pam", "pan", "pap", "pau", "peo", "per", "phi", "phn", "pli", "pol", "pon", "por", "pra", "pro", "pus", "qaa-qtz", "que", "raj", "rap", "rar", "roa", "roh", "rom", "rum", "run", "rup", "rus", "sad", "sag", "sah", "sai", "sal", "sam", "san", "sas", "sat", "scn", "sco", "sel", "sem", "sga", "sgn", "shn", "sid", "sin", "sio", "sit", "sla", "slo", "slv", "sma", "sme", "smi", "smj", "smn", "smo", "sms", "sna", "snd", "snk", "sog", "som", "son", "sot", "spa", "srd", "srn", "srp", "srr", "ssa", "ssw", "suk", "sun", "sus", "sux", "swa", "swe", "syc", "syr", "tah", "tai", "tam", "tat", "tel", "tem", "ter", "tet", "tgk", "tgl", "tha", "tib", "tig", "tir", "tiv", "tkl", "tlh", "tli", "tmh", "tog", "ton", "tpi", "tsi", "tsn", "tso", "tuk", "tum", "tup", "tur", "tut", "tvl", "twi", "tyv", "udm", "uga", "uig", "ukr", "umb", "und", "urd", "uzb", "vai", "ven", "vie", "vol", "vot", "wak", "wal", "war", "was", "wel", "wen", "wln", "wol", "xal", "xho", "yao", "yap", "yid", "yor", "ypk", "zap", "zbl", "zen", "zgh", "zha", "znd", "zul", "zun", "zxx", "zza", "CHOOSE MY OWN" }));
        langPostfixCB2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                langPostfixCB2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel20.add(langPostfixCB2, gridBagConstraints);

        langPostfixCB3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "﻿aar", "abk", "ace", "ach", "ada", "ady", "afa", "afh", "afr", "ain", "aka", "akk", "alb", "ale", "alg", "alt", "amh", "ang", "anp", "apa", "ara", "arc", "arg", "arm", "arn", "arp", "art", "arw", "asm", "ast", "ath", "aus", "ava", "ave", "awa", "aym", "aze", "bad", "bai", "bak", "bal", "bam", "ban", "baq", "bas", "bat", "bej", "bel", "bem", "ben", "ber", "bho", "bih", "bik", "bin", "bis", "bla", "bnt", "bos", "bra", "bre", "btk", "bua", "bug", "bul", "bur", "byn", "cad", "cai", "car", "cat", "cau", "ceb", "cel", "cha", "chb", "che", "chg", "chi", "chk", "chm", "chn", "cho", "chp", "chr", "chu", "chv", "chy", "cmc", "cop", "cor", "cos", "cpe", "cpf", "cpp", "cre", "crh", "crp", "csb", "cus", "cze", "dak", "dan", "dar", "day", "del", "den", "dgr", "din", "div", "doi", "dra", "dsb", "dua", "dum", "dut", "dyu", "dzo", "efi", "egy", "eka", "elx", "eng", "enm", "epo", "est", "ewe", "ewo", "fan", "fao", "fat", "fij", "fil", "fin", "fiu", "fon", "fre", "frm", "fro", "frr", "frs", "fry", "ful", "fur", "gaa", "gay", "gba", "gem", "geo", "ger", "gez", "gil", "gla", "gle", "glg", "glv", "gmh", "goh", "gon", "gor", "got", "grb", "grc", "gre", "grn", "gsw", "guj", "gwi", "hai", "hat", "hau", "haw", "heb", "her", "hil", "him", "hin", "hit", "hmn", "hmo", "hrv", "hsb", "hun", "hup", "iba", "ibo", "ice", "ido", "iii", "ijo", "iku", "ile", "ilo", "ina", "inc", "ind", "ine", "inh", "ipk", "ira", "iro", "ita", "jav", "jbo", "jpn", "jpr", "jrb", "kaa", "kab", "kac", "kal", "kam", "kan", "kar", "kas", "kau", "kaw", "kaz", "kbd", "kha", "khi", "khm", "kho", "kik", "kin", "kir", "kmb", "kok", "kom", "kon", "kor", "kos", "kpe", "krc", "krl", "kro", "kru", "kua", "kum", "kur", "kut", "lad", "lah", "lam", "lao", "lat", "lav", "lez", "lim", "lin", "lit", "lol", "loz", "ltz", "lua", "lub", "lug", "lui", "lun", "luo", "lus", "mac", "mad", "mag", "mah", "mai", "mak", "mal", "man", "mao", "map", "mar", "mas", "may", "mdf", "mdr", "men", "mga", "mic", "min", "mis", "mkh", "mlg", "mlt", "mnc", "mni", "mno", "moh", "mon", "mos", "mul", "mun", "mus", "mwl", "mwr", "myn", "myv", "nah", "nai", "nap", "nau", "nav", "nbl", "nde", "ndo", "nds", "nep", "new", "nia", "nic", "niu", "nno", "nob", "nog", "non", "nor", "nqo", "nso", "nub", "nwc", "nya", "nym", "nyn", "nyo", "nzi", "oci", "oji", "ori", "orm", "osa", "oss", "ota", "oto", "paa", "pag", "pal", "pam", "pan", "pap", "pau", "peo", "per", "phi", "phn", "pli", "pol", "pon", "por", "pra", "pro", "pus", "qaa-qtz", "que", "raj", "rap", "rar", "roa", "roh", "rom", "rum", "run", "rup", "rus", "sad", "sag", "sah", "sai", "sal", "sam", "san", "sas", "sat", "scn", "sco", "sel", "sem", "sga", "sgn", "shn", "sid", "sin", "sio", "sit", "sla", "slo", "slv", "sma", "sme", "smi", "smj", "smn", "smo", "sms", "sna", "snd", "snk", "sog", "som", "son", "sot", "spa", "srd", "srn", "srp", "srr", "ssa", "ssw", "suk", "sun", "sus", "sux", "swa", "swe", "syc", "syr", "tah", "tai", "tam", "tat", "tel", "tem", "ter", "tet", "tgk", "tgl", "tha", "tib", "tig", "tir", "tiv", "tkl", "tlh", "tli", "tmh", "tog", "ton", "tpi", "tsi", "tsn", "tso", "tuk", "tum", "tup", "tur", "tut", "tvl", "twi", "tyv", "udm", "uga", "uig", "ukr", "umb", "und", "urd", "uzb", "vai", "ven", "vie", "vol", "vot", "wak", "wal", "war", "was", "wel", "wen", "wln", "wol", "xal", "xho", "yao", "yap", "yid", "yor", "ypk", "zap", "zbl", "zen", "zgh", "zha", "znd", "zul", "zun", "zxx", "zza", "CHOOSE MY OWN" }));
        langPostfixCB3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                langPostfixCB3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel20.add(langPostfixCB3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel5.add(jPanel20, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel5, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.GridBagLayout());

        langHasSepDictCB3.setText(I18n.tr("language-3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel3.add(langHasSepDictCB3, gridBagConstraints);

        langHasSepDictCB1.setText(I18n.tr("language-1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel3.add(langHasSepDictCB1, gridBagConstraints);

        langHasSepDictCB2.setText(I18n.tr("language-2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel3.add(langHasSepDictCB2, gridBagConstraints);

        jLabel14.setText("<html>languageXHas<br>SeparateDictionaryFile</html>");
        jLabel14.setToolTipText(I18n.tr("sepDictFile.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel3.add(jLabel14, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel3, gridBagConstraints);

        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(177, 0, 0), 2));
        jPanel19.setLayout(new java.awt.GridBagLayout());

        additionalInfo.setText(I18n.tr("extraInfo.dfmPropCreate")); // NOI18N
        additionalInfo.setToolTipText(I18n.tr("extraButton.toolTipText.dfmPropCreate")); // NOI18N
        additionalInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                additionalInfoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel19.add(additionalInfo, gridBagConstraints);

        samplePropFile.setText(I18n.tr("samplePros.dfmPropCreate")); // NOI18N
        samplePropFile.setToolTipText(I18n.tr("sampleButton.toolTipText.dfmPropCreate")); // NOI18N
        samplePropFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                samplePropFileActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel19.add(samplePropFile, gridBagConstraints);

        createPropFile.setText(I18n.tr("save.Props.File.dfmPropCreate")); // NOI18N
        createPropFile.setToolTipText(I18n.tr("createButton.toolTipText.dfmPropCreate")); // NOI18N
        createPropFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPropFileActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel19.add(createPropFile, gridBagConstraints);

        resetSetting.setText(I18n.tr("resetSettings.dfmPropCreate")); // NOI18N
        resetSetting.setToolTipText(I18n.tr("resetButton.toolTipText.dfmPropCreate")); // NOI18N
        resetSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSettingActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel19.add(resetSetting, gridBagConstraints);

        editExistingBT.setText(I18n.tr("save.props.file.under.dfmPropCreate")); // NOI18N
        editExistingBT.setToolTipText(I18n.tr("editButton.toolTipText.dfmPropCreate")); // NOI18N
        editExistingBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExistingBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel19.add(editExistingBT, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel19, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel16.setText(I18n.tr("sepChar.dfmPropCreate")); // NOI18N
        jLabel16.setToolTipText(I18n.tr("sepChars.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 15, 2);
        jPanel8.add(jLabel16, gridBagConstraints);

        jLabel22.setText("dictionaryGeneration");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel8.add(jLabel22, gridBagConstraints);

        dictGenSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tab ( \\t )", "Carriage Return ( \\r )", "Form Feed ( \\f )", "Comma ( , )", "Semi-Colon ( ; )", "Colon ( : )" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(dictGenSepCharCB, gridBagConstraints);

        searchListSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tab ( \\t )", "Carriage Return ( \\r )", "Form Feed ( \\f )", "Comma ( , )", "Semi-Colon ( ; )", "Colon ( : )" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(searchListSepCharCB, gridBagConstraints);

        indexFileSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tab ( \\t )", "Carriage Return ( \\r )", "Form Feed ( \\f )", "Comma ( , )", "Semi-Colon ( ; )", "Colon ( : )" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(indexFileSepCharCB, gridBagConstraints);

        dictFileSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tab ( \\t )", "Carriage Return ( \\r )", "Form Feed ( \\f )", "Comma ( , )", "Semi-Colon ( ; )", "Colon ( : )" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(dictFileSepCharCB, gridBagConstraints);

        jLabel26.setText("indexFile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel8.add(jLabel26, gridBagConstraints);

        jLabel28.setText("dictionaryFile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel8.add(jLabel28, gridBagConstraints);

        jLabel31.setText("searchListFile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel8.add(jLabel31, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel8, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("* numberOfAvailableLanguages");
        jLabel3.setToolTipText(I18n.tr("numOfAvailableLanguages.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel7.add(jLabel3, gridBagConstraints);

        jLabel2.setText("* dictionaryAbbreviation");
        jLabel2.setToolTipText(I18n.tr("dictAbbrev.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel7.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel7.add(dictAbbrevTF, gridBagConstraints);

        numOfLangCmbBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2", "3" }));
        numOfLangCmbBox.setSelectedIndex(0);
        numOfLangCmbBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                numOfLangCmbBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel7.add(numOfLangCmbBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel7, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        listMaxSizeSL.setMajorTickSpacing(5);
        listMaxSizeSL.setMaximum(10000);
        listMaxSizeSL.setMinorTickSpacing(1);
        listMaxSizeSL.setValue(0);
        listMaxSizeSL.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        listMaxSizeSL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                listMaxSizeSLStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel1.add(listMaxSizeSL, gridBagConstraints);

        jLabel37.setText("searchListFileMaxSize");
        jLabel37.setToolTipText(I18n.tr("searchListMaxSize.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel1.add(jLabel37, gridBagConstraints);

        lab3.setText(I18n.tr("slider.value")); // NOI18N
        lab3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel1.add(lab3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.GridBagLayout());

        langGenIndCB1.setText(I18n.tr("language-1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel2.add(langGenIndCB1, gridBagConstraints);

        jLabel13.setText("languageXgenerateIndex");
        jLabel13.setToolTipText(I18n.tr("generateIndex.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel2.add(jLabel13, gridBagConstraints);

        langGenIndCB2.setText(I18n.tr("language-2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel2.add(langGenIndCB2, gridBagConstraints);

        langGenIndCB3.setText(I18n.tr("language-3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel2.add(langGenIndCB3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel2, gridBagConstraints);

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.setLayout(new java.awt.GridBagLayout());

        indexMaxSizeSL.setMajorTickSpacing(5);
        indexMaxSizeSL.setMaximum(200000);
        indexMaxSizeSL.setMinorTickSpacing(1);
        indexMaxSizeSL.setValue(0);
        indexMaxSizeSL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                indexMaxSizeSLStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel15.add(indexMaxSizeSL, gridBagConstraints);

        dictMaxSizeSL.setMajorTickSpacing(5);
        dictMaxSizeSL.setMaximum(200000);
        dictMaxSizeSL.setMinorTickSpacing(1);
        dictMaxSizeSL.setValue(0);
        dictMaxSizeSL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                dictMaxSizeSLStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel15.add(dictMaxSizeSL, gridBagConstraints);

        jLabel36.setText("dictionaryFileMaxSize");
        jLabel36.setToolTipText(I18n.tr("dictionaryFileMaxSize.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel15.add(jLabel36, gridBagConstraints);

        jLabel38.setText("indexFileMaxSize");
        jLabel38.setToolTipText(I18n.tr("indexFileMaxSize.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel15.add(jLabel38, gridBagConstraints);

        lab4.setText(I18n.tr("slider.value")); // NOI18N
        lab4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel15.add(lab4, gridBagConstraints);

        lab5.setText(I18n.tr("slider.value")); // NOI18N
        lab5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel15.add(lab5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel15, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("* languageXDisplayText");
        jLabel4.setToolTipText(I18n.tr("langDiaplayText.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel4.add(jLabel4, gridBagConstraints);

        jPanel18.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel18.add(langDispTextTF3, gridBagConstraints);

        langDispTextLB3.setText(I18n.tr("language-3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel18.add(langDispTextLB3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel18.add(langDispTextTF2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel18.add(langDispTextTF1, gridBagConstraints);

        jLabel5.setText(I18n.tr("language-1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel18.add(jLabel5, gridBagConstraints);

        jLabel6.setText(I18n.tr("language-2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel18.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel4.add(jPanel18, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel4, gridBagConstraints);

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.setLayout(new java.awt.GridBagLayout());

        minNumEntPerDictFileSL.setMajorTickSpacing(5);
        minNumEntPerDictFileSL.setMaximum(10000);
        minNumEntPerDictFileSL.setMinorTickSpacing(1);
        minNumEntPerDictFileSL.setValue(0);
        minNumEntPerDictFileSL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                minNumEntPerDictFileSLStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel16.add(minNumEntPerDictFileSL, gridBagConstraints);

        jLabel34.setText("<html>dictGenMinNumber<br>OfEntriesPerDictionaryFile</html>");
        jLabel34.setToolTipText(I18n.tr("dictGenMinNumOf.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel16.add(jLabel34, gridBagConstraints);

        lab1.setText(I18n.tr("slider.value")); // NOI18N
        lab1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel16.add(lab1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel16, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setLayout(new java.awt.GridBagLayout());

        langIndNumSrcEntLB3.setText(I18n.tr("language-3")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel13.add(langIndNumSrcEntLB3, gridBagConstraints);

        jLabel44.setText(I18n.tr("language-2")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel13.add(jLabel44, gridBagConstraints);

        jLabel45.setText(I18n.tr("language-1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel13.add(jLabel45, gridBagConstraints);

        jLabel46.setText("<html>languageXIndexNumber<br>OfSourceEntries</html>");
        jLabel46.setToolTipText(I18n.tr("indexNumOfSourceEntries.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel13.add(jLabel46, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        jPanel13.add(langIndNumSrcEntTF1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel13.add(langIndNumSrcEntTF2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel13.add(langIndNumSrcEntTF3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel13, gridBagConstraints);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(new java.awt.GridBagLayout());

        infoTextTA.setColumns(20);
        infoTextTA.setLineWrap(true);
        infoTextTA.setRows(5);
        infoTextTA.setWrapStyleWord(true);
        jScrollPane1.setViewportView(infoTextTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel9.add(jScrollPane1, gridBagConstraints);

        infoTextLabel.setText("* infoText");
        infoTextLabel.setToolTipText(I18n.tr("infoTextLabel.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel9.add(infoTextLabel, gridBagConstraints);

        jLabel42.setText(I18n.tr("compusoryPropsInfo.dfmPropCreate")); // NOI18N
        jLabel42.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel9.add(jLabel42, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel9, gridBagConstraints);

        jPanel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel22.setLayout(new java.awt.GridBagLayout());

        minNumEntPerIndFileSL.setMajorTickSpacing(5);
        minNumEntPerIndFileSL.setMaximum(10000);
        minNumEntPerIndFileSL.setMinorTickSpacing(1);
        minNumEntPerIndFileSL.setValue(0);
        minNumEntPerIndFileSL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                minNumEntPerIndFileSLStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel22.add(minNumEntPerIndFileSL, gridBagConstraints);

        lab2.setText(I18n.tr("slider.value")); // NOI18N
        lab2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel22.add(lab2, gridBagConstraints);

        jLabel35.setText("<html>dictGenMinNumber<br>OfEntriesPerIndexFile</html>");
        jLabel35.setToolTipText(I18n.tr("dictGenMinNum.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel22.add(jLabel35, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel22, gridBagConstraints);

        jPanel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel23.setLayout(new java.awt.GridBagLayout());

        jLabel43.setText("<html>dictionaryGeneration<br>OmitParFromIndex");
        jLabel43.setToolTipText(I18n.tr("dictGenOmitPar.tooltiptext")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel23.add(jLabel43, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel23.add(dictGenOmitParCB, gridBagConstraints);

        jLabel1.setText("dictGenInputCharEncoding");
        jLabel1.setToolTipText(I18n.tr("dictgenInputCharEnc.tooltipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel23.add(jLabel1, gridBagConstraints);

        dictGenInputCharEncodingCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UTF-8", "ISO-8859-1", "US-ASCII" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel23.add(dictGenInputCharEncodingCB, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel23, gridBagConstraints);

        jPanel24.setBorder(null);
        jPanel24.setLayout(new java.awt.GridBagLayout());

        editingTaskInfo.setBackground(new java.awt.Color(228, 228, 228));
        editingTaskInfo.setColumns(20);
        editingTaskInfo.setEditable(false);
        editingTaskInfo.setLineWrap(true);
        editingTaskInfo.setRows(5);
        editingTaskInfo.setText(I18n.tr("creating.a.props.file")); // NOI18N
        editingTaskInfo.setWrapStyleWord(true);
        jScrollPane3.setViewportView(editingTaskInfo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 600;
        gridBagConstraints.ipady = -30;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel24.add(jScrollPane3, gridBagConstraints);

        jButton1.setText(I18n.tr("contentNNPropsEditorButton.dfmPropCreate")); // NOI18N
        jButton1.setToolTipText(I18n.tr("content.nn.props.creator.win.launcher.button.tooltiptext")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel24.add(jButton1, gridBagConstraints);

        jLabel15.setText(I18n.tr("create.a.set.of.content.Props")); // NOI18N
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel24.add(jLabel15, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel24, gridBagConstraints);

        jScrollPane2.setViewportView(mainContainer);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jMenu1.setText(I18n.tr("file.dfmPropCreate")); // NOI18N

        jMenuItem1.setText(I18n.tr("save.Props.File.dfmPropCreate")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText(I18n.tr("save.props.file.under.dfmPropCreate")); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText(I18n.tr("resetSettings.dfmPropCreate")); // NOI18N
        jMenuItem3.setToolTipText("");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText(I18n.tr("samplePros.dfmPropCreate")); // NOI18N
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText(I18n.tr("extraInfos.dfmPropCreate")); // NOI18N
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem7.setText(I18n.tr("quit.dfmPropCreate")); // NOI18N
        jMenuItem7.setToolTipText("");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(I18n.tr("about.dfmPropCreate")); // NOI18N

        jMenuItem6.setText(I18n.tr("aboutPropsEditor.dfmPropCreate")); // NOI18N
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        viewAndOrEditAndSave();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        savePropsFileUnder();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        resetAll();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        showSamplePropFile();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        showMoreInfo();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        JOptionPane.showMessageDialog(proPrevWin, I18n.tr("aboutWinMsg.dfmPropCreate"), I18n.tr("aboutWinTitle.dfmPropCreate"), JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void langPostfixCB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langPostfixCB1ActionPerformed
        processLangPostfixCB1();
    }//GEN-LAST:event_langPostfixCB1ActionPerformed

    private void langPostfixCB2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langPostfixCB2ActionPerformed
        processLangPostfixCB2();
    }//GEN-LAST:event_langPostfixCB2ActionPerformed

    private void langPostfixCB3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langPostfixCB3ActionPerformed
        processLangPostfixCB3();
    }//GEN-LAST:event_langPostfixCB3ActionPerformed

    private void additionalInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_additionalInfoActionPerformed
        showMoreInfo();
    }//GEN-LAST:event_additionalInfoActionPerformed

    private void samplePropFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_samplePropFileActionPerformed
        showSamplePropFile();
    }//GEN-LAST:event_samplePropFileActionPerformed

    private void createPropFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPropFileActionPerformed
        viewAndOrEditAndSave();
    }//GEN-LAST:event_createPropFileActionPerformed

    private void resetSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSettingActionPerformed
        resetAll();
    }//GEN-LAST:event_resetSettingActionPerformed

    private void editExistingBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExistingBTActionPerformed
        savePropsFileUnder();
    }//GEN-LAST:event_editExistingBTActionPerformed

    private void numOfLangCmbBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_numOfLangCmbBoxItemStateChanged
        updateNumOfLang();
    }//GEN-LAST:event_numOfLangCmbBoxItemStateChanged

    private void listMaxSizeSLStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_listMaxSizeSLStateChanged
        lab3.setText(String.valueOf(listMaxSizeSL.getValue()));
    }//GEN-LAST:event_listMaxSizeSLStateChanged

    private void indexMaxSizeSLStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_indexMaxSizeSLStateChanged
        lab4.setText(String.valueOf(indexMaxSizeSL.getValue()));
    }//GEN-LAST:event_indexMaxSizeSLStateChanged

    private void dictMaxSizeSLStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_dictMaxSizeSLStateChanged
        lab5.setText(String.valueOf(dictMaxSizeSL.getValue()));
    }//GEN-LAST:event_dictMaxSizeSLStateChanged

    private void minNumEntPerIndFileSLStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minNumEntPerIndFileSLStateChanged
        lab2.setText(String.valueOf(minNumEntPerIndFileSL.getValue()));
    }//GEN-LAST:event_minNumEntPerIndFileSLStateChanged

    private void minNumEntPerDictFileSLStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minNumEntPerDictFileSLStateChanged
        lab1.setText(String.valueOf(minNumEntPerDictFileSL.getValue()));
    }//GEN-LAST:event_minNumEntPerDictFileSLStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        showContentPropsCreator();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void processLangPostfixCB1() {
        if (langPostfixCB1.getSelectedItem().toString().equals(I18n.tr("chooseOwn.dfmPropCreate"))) {
            language1FilePostfix = showInputDialog(I18n.tr("inputDialogLabelTitle.dfmPropCreate"));
        } else {
            language1FilePostfix = langPostfixCB1.getSelectedItem().toString();
        }
        // if we reach here and the user didn't enter anything
        // in the input dialog, reselect the first element
        if ("".equals(language1FilePostfix)) {
            langPostfixCB1.setSelectedIndex(0);
            language1FilePostfix = langPostfixCB1.getSelectedItem().toString();
        }
    }

    private void processLangPostfixCB2() {
        if (langPostfixCB2.getSelectedItem().toString().equals(I18n.tr("chooseOwn.dfmPropCreate"))) {
            language2FilePostfix = showInputDialog(I18n.tr("inputDialogLabelTitle.dfmPropCreate"));
        } else {
            language2FilePostfix = langPostfixCB2.getSelectedItem().toString();
        }
        // if we reach here and the user didn't enter anything
        // in the input dialog, reselect the first element
        if ("".equals(language2FilePostfix)) {
            langPostfixCB2.setSelectedIndex(0);
            language2FilePostfix = langPostfixCB2.getSelectedItem().toString();
        }
    }

    private void processLangPostfixCB3() {
        if (langPostfixCB3.getSelectedItem().toString().equals(I18n.tr("chooseOwn.dfmPropCreate"))) {
            language3FilePostfix = showInputDialog(I18n.tr("inputDialogLabelTitle.dfmPropCreate"));
        } else {
            language3FilePostfix = langPostfixCB3.getSelectedItem().toString();
        }
        // if we reach here and the user didn't enter anything
        // in the input dialog, reselect the first element
        if ("".equals(language3FilePostfix)) {
            langPostfixCB3.setSelectedIndex(0);
            language3FilePostfix = langPostfixCB3.getSelectedItem().toString();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /*
         * Set the default look and feel
         */

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DfMCreatorMain.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //  new PropertiesEditor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton additionalInfo;
    private javax.swing.JButton createPropFile;
    private javax.swing.JTextField dictAbbrevTF;
    private javax.swing.JComboBox dictFileSepCharCB;
    private javax.swing.JComboBox dictGenInputCharEncodingCB;
    private javax.swing.JCheckBox dictGenOmitParCB;
    private javax.swing.JComboBox dictGenSepCharCB;
    private javax.swing.JSlider dictMaxSizeSL;
    private javax.swing.JComboBox dictionaryCharEncodingCB;
    private javax.swing.JButton editExistingBT;
    private javax.swing.JTextArea editingTaskInfo;
    private javax.swing.JComboBox indexCharEncodingCB;
    private javax.swing.JComboBox indexFileSepCharCB;
    private javax.swing.JSlider indexMaxSizeSL;
    private javax.swing.JLabel infoTextLabel;
    private javax.swing.JTextArea infoTextTA;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lab1;
    private javax.swing.JLabel lab2;
    private javax.swing.JLabel lab3;
    private javax.swing.JLabel lab4;
    private javax.swing.JLabel lab5;
    private javax.swing.JComboBox langDictUpdateCB1;
    private javax.swing.JComboBox langDictUpdateCB2;
    private javax.swing.JComboBox langDictUpdateCB3;
    private javax.swing.JLabel langDictUpdateLB3;
    private javax.swing.JLabel langDispTextLB3;
    private javax.swing.JTextField langDispTextTF1;
    private javax.swing.JTextField langDispTextTF2;
    private javax.swing.JTextField langDispTextTF3;
    private javax.swing.JLabel langExpSplitStringLB3;
    private javax.swing.JTextField langExpSplitStringTF1;
    private javax.swing.JTextField langExpSplitStringTF2;
    private javax.swing.JTextField langExpSplitStringTF3;
    private javax.swing.JLabel langFilePostfixLB3;
    private javax.swing.JCheckBox langGenIndCB1;
    private javax.swing.JCheckBox langGenIndCB2;
    private javax.swing.JCheckBox langGenIndCB3;
    private javax.swing.JCheckBox langHasSepDictCB1;
    private javax.swing.JCheckBox langHasSepDictCB2;
    private javax.swing.JCheckBox langHasSepDictCB3;
    private javax.swing.JLabel langIndNumSrcEntLB3;
    private javax.swing.JTextField langIndNumSrcEntTF1;
    private javax.swing.JTextField langIndNumSrcEntTF2;
    private javax.swing.JTextField langIndNumSrcEntTF3;
    private javax.swing.JCheckBox langIsSearchCB1;
    private javax.swing.JCheckBox langIsSearchCB2;
    private javax.swing.JCheckBox langIsSearchCB3;
    private javax.swing.JComboBox langNormCB1;
    private javax.swing.JComboBox langNormCB2;
    private javax.swing.JComboBox langNormCB3;
    private javax.swing.JLabel langNormLB3;
    private javax.swing.JComboBox langPostfixCB1;
    private javax.swing.JComboBox langPostfixCB2;
    private javax.swing.JComboBox langPostfixCB3;
    private javax.swing.JSlider listMaxSizeSL;
    private javax.swing.JPanel mainContainer;
    private javax.swing.JSlider minNumEntPerDictFileSL;
    private javax.swing.JSlider minNumEntPerIndFileSL;
    private javax.swing.JComboBox numOfLangCmbBox;
    private javax.swing.JButton resetSetting;
    private javax.swing.JButton samplePropFile;
    private javax.swing.JComboBox searchListCharEncodingCB;
    private javax.swing.JComboBox searchListSepCharCB;
    // End of variables declaration//GEN-END:variables
    private static final String moreInfo = I18n.tr("logLevelMsg.dfmPropCreate");

    /**
     * showInputDialog Shows an input dialog and returns its content.
     *
     * @param winTitle; the title of the window.
     * @return the value entered by the user.
     */
    private static String showInputDialog(String labelText) {
        InputDialog id = InputDialog.getInputDialog();
        InputDialog.infoLabel.setText(labelText);
        //id.setSize(300, 200);
        id.setLocation(screenSize.width / 2 - id.getWidth() / 2,
                screenSize.height / 2 - id.getHeight() / 2);
        id.setModal(true);
        id.setVisible(true);
        return InputDialog.inputTextArea.getText();
    }

    /**
     * filterSepChar Processes the selected item of one of the four
     * separatorCharacter comboboxes and returns the actual filterSepChar
     * corresponding to the selection.
     *
     * @param combobox
     * @return separator character selected
     */
    private String filterSepChar(javax.swing.JComboBox combobox) {
        int i = combobox.getSelectedIndex();
        String value;
        switch (i) {
            case 0:
                value = "'\\t'";
                break;
            case 1:
                value = "'\\r'";
                break;
            case 2:
                value = "'\\f'";
                break;
            case 3:
                value = "','";
                break;
            case 4:
                value = "';'";
                break;
            case 5:
                value = "':'";
                break;
            default:
                value = combobox.getSelectedItem().toString();
                break;
        }
        return value;
    }

    /**
     * revertFilterSepChar this does the contrary of what revertFilterSepChar
     * does
     */
    private int revertFilterSepChar(String value) {
        int i;
        switch (value) {
            case "'\t'":
                i = 0;
                break;
            case "'\r'":
                i = 1;
                break;
            case "'\f'":
                i = 2;
                break;
            case "','":
                i = 3;
                break;
            case "';'":
                i = 4;
                break;
            case "':'":
                i = 5;
                break;
            default:
                // This indicates that the value provided
                // is none of the the above ones.
                i = 1000;
                break;
        }
        return i;
    }

    private void resetAll() {
        editingTaskInfo.setText("");
        editingTaskInfo.setText(I18n.tr("creating.a.props.file"));
        infoTextTA.setText("");
        dictAbbrevTF.setText("");

        dictGenInputCharEncodingCB.setSelectedIndex(0);
        indexCharEncodingCB.setSelectedIndex(0);
        searchListCharEncodingCB.setSelectedIndex(0);
        dictionaryCharEncodingCB.setSelectedIndex(0);

        langDispTextTF1.setText("");
        langDispTextTF2.setText("");
        langDispTextTF3.setText("");

        langPostfixCB1.setSelectedIndex(0);
        langPostfixCB2.setSelectedIndex(0);
        langPostfixCB3.setSelectedIndex(0);

        langExpSplitStringTF1.setText("");
        langExpSplitStringTF2.setText("");
        langExpSplitStringTF3.setText("");

        langDictUpdateCB1.setSelectedIndex(0);
        langDictUpdateCB2.setSelectedIndex(0);
        langDictUpdateCB3.setSelectedIndex(0);

        langNormCB1.setSelectedIndex(0);
        langNormCB2.setSelectedIndex(0);
        langNormCB3.setSelectedIndex(0);

        langIndNumSrcEntTF1.setText("");
        langIndNumSrcEntTF2.setText("");
        langIndNumSrcEntTF3.setText("");

        dictGenSepCharCB.setSelectedIndex(0);
        indexFileSepCharCB.setSelectedIndex(0);
        searchListSepCharCB.setSelectedIndex(0);
        dictFileSepCharCB.setSelectedIndex(0);

        numOfLangCmbBox.setSelectedIndex(0);

        dictMaxSizeSL.setValue(0);
        indexMaxSizeSL.setValue(0);
        listMaxSizeSL.setValue(0);
        minNumEntPerDictFileSL.setValue(0);
        minNumEntPerIndFileSL.setValue(0);

        lab1.setText(I18n.tr("value.dfmPropCreate"));
        lab2.setText(I18n.tr("value.dfmPropCreate"));
        lab3.setText(I18n.tr("value.dfmPropCreate"));
        lab4.setText(I18n.tr("value.dfmPropCreate"));
        lab5.setText(I18n.tr("value.dfmPropCreate"));

        langGenIndCB1.setSelected(false);
        langGenIndCB2.setSelected(false);
        langGenIndCB3.setSelected(false);
        langHasSepDictCB1.setSelected(false);
        langHasSepDictCB2.setSelected(false);
        langHasSepDictCB3.setSelected(false);
        langIsSearchCB1.setSelected(false);
        langIsSearchCB2.setSelected(false);
        langIsSearchCB3.setSelected(false);
        dictGenOmitParCB.setSelected(false);

        langPostfixCB1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"﻿aar", "abk", "ace", "ach", "ada", "ady", "afa", "afh", "afr", "ain", "aka", "akk", "alb",
            "ale", "alg", "alt", "amh", "ang", "anp", "apa", "ara", "arc", "arg", "arm", "arn", "arp", "art",
            "arw", "asm", "ast", "ath", "aus", "ava", "ave", "awa", "aym", "aze", "bad", "bai", "bak", "bal",
            "bam", "ban", "baq", "bas", "bat", "bej", "bel", "bem", "ben", "ber", "bho", "bih", "bik", "bin",
            "bis", "bla", "bnt", "bos", "bra", "bre", "btk", "bua", "bug", "bul", "bur", "byn", "cad", "cai",
            "car", "cat", "cau", "ceb", "cel", "cha", "chb", "che", "chg", "chi", "chk", "chm", "chn", "cho",
            "chp", "chr", "chu", "chv", "chy", "cmc", "cop", "cor", "cos", "cpe", "cpf", "cpp", "cre", "crh",
            "crp", "csb", "cus", "cze", "dak", "dan", "dar", "day", "del", "den", "dgr", "din", "div", "doi",
            "dra", "dsb", "dua", "dum", "dut", "dyu", "dzo", "efi", "egy", "eka", "elx", "eng", "enm", "epo",
            "est", "ewe", "ewo", "fan", "fao", "fat", "fij", "fil", "fin", "fiu", "fon", "fre", "frm", "fro",
            "frr", "frs", "fry", "ful", "fur", "gaa", "gay", "gba", "gem", "geo", "ger", "gez", "gil", "gla",
            "gle", "glg", "glv", "gmh", "goh", "gon", "gor", "got", "grb", "grc", "gre", "grn", "gsw", "guj",
            "gwi", "hai", "hat", "hau", "haw", "heb", "her", "hil", "him", "hin", "hit", "hmn", "hmo", "hrv",
            "hsb", "hun", "hup", "iba", "ibo", "ice", "ido", "iii", "ijo", "iku", "ile", "ilo", "ina", "inc",
            "ind", "ine", "inh", "ipk", "ira", "iro", "ita", "jav", "jbo", "jpn", "jpr", "jrb", "kaa", "kab",
            "kac", "kal", "kam", "kan", "kar", "kas", "kau", "kaw", "kaz", "kbd", "kha", "khi", "khm", "kho",
            "kik", "kin", "kir", "kmb", "kok", "kom", "kon", "kor", "kos", "kpe", "krc", "krl", "kro", "kru",
            "kua", "kum", "kur", "kut", "lad", "lah", "lam", "lao", "lat", "lav", "lez", "lim", "lin", "lit",
            "lol", "loz", "ltz", "lua", "lub", "lug", "lui", "lun", "luo", "lus", "mac", "mad", "mag", "mah",
            "mai", "mak", "mal", "man", "mao", "map", "mar", "mas", "may", "mdf", "mdr", "men", "mga", "mic",
            "min", "mis", "mkh", "mlg", "mlt", "mnc", "mni", "mno", "moh", "mon", "mos", "mul", "mun", "mus",
            "mwl", "mwr", "myn", "myv", "nah", "nai", "nap", "nau", "nav", "nbl", "nde", "ndo", "nds", "nep",
            "new", "nia", "nic", "niu", "nno", "nob", "nog", "non", "nor", "nqo", "nso", "nub", "nwc", "nya",
            "nym", "nyn", "nyo", "nzi", "oci", "oji", "ori", "orm", "osa", "oss", "ota", "oto", "paa", "pag",
            "pal", "pam", "pan", "pap", "pau", "peo", "per", "phi", "phn", "pli", "pol", "pon", "por", "pra",
            "pro", "pus", "qaa-qtz", "que", "raj", "rap", "rar", "roa", "roh", "rom", "rum", "run", "rup",
            "rus", "sad", "sag", "sah", "sai", "sal", "sam", "san", "sas", "sat", "scn", "sco", "sel", "sem",
            "sga", "sgn", "shn", "sid", "sin", "sio", "sit", "sla", "slo", "slv", "sma", "sme", "smi", "smj",
            "smn", "smo", "sms", "sna", "snd", "snk", "sog", "som", "son", "sot", "spa", "srd", "srn", "srp",
            "srr", "ssa", "ssw", "suk", "sun", "sus", "sux", "swa", "swe", "syc", "syr", "tah", "tai", "tam",
            "tat", "tel", "tem", "ter", "tet", "tgk", "tgl", "tha", "tib", "tig", "tir", "tiv", "tkl", "tlh",
            "tli", "tmh", "tog", "ton", "tpi", "tsi", "tsn", "tso", "tuk", "tum", "tup", "tur", "tut", "tvl",
            "twi", "tyv", "udm", "uga", "uig", "ukr", "umb", "und", "urd", "uzb", "vai", "ven", "vie", "vol",
            "vot", "wak", "wal", "war", "was", "wel", "wen", "wln", "wol", "xal", "xho", "yao", "yap", "yid",
            "yor", "ypk", "zap", "zbl", "zen", "zgh", "zha", "znd", "zul", "zun", "zxx", "zza", "CHOOSE MY OWN"}));

        langPostfixCB2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"﻿aar", "abk", "ace", "ach", "ada", "ady", "afa", "afh", "afr", "ain", "aka", "akk", "alb",
            "ale", "alg", "alt", "amh", "ang", "anp", "apa", "ara", "arc", "arg", "arm", "arn", "arp", "art",
            "arw", "asm", "ast", "ath", "aus", "ava", "ave", "awa", "aym", "aze", "bad", "bai", "bak", "bal",
            "bam", "ban", "baq", "bas", "bat", "bej", "bel", "bem", "ben", "ber", "bho", "bih", "bik", "bin",
            "bis", "bla", "bnt", "bos", "bra", "bre", "btk", "bua", "bug", "bul", "bur", "byn", "cad", "cai",
            "car", "cat", "cau", "ceb", "cel", "cha", "chb", "che", "chg", "chi", "chk", "chm", "chn", "cho",
            "chp", "chr", "chu", "chv", "chy", "cmc", "cop", "cor", "cos", "cpe", "cpf", "cpp", "cre", "crh",
            "crp", "csb", "cus", "cze", "dak", "dan", "dar", "day", "del", "den", "dgr", "din", "div", "doi",
            "dra", "dsb", "dua", "dum", "dut", "dyu", "dzo", "efi", "egy", "eka", "elx", "eng", "enm", "epo",
            "est", "ewe", "ewo", "fan", "fao", "fat", "fij", "fil", "fin", "fiu", "fon", "fre", "frm", "fro",
            "frr", "frs", "fry", "ful", "fur", "gaa", "gay", "gba", "gem", "geo", "ger", "gez", "gil", "gla",
            "gle", "glg", "glv", "gmh", "goh", "gon", "gor", "got", "grb", "grc", "gre", "grn", "gsw", "guj",
            "gwi", "hai", "hat", "hau", "haw", "heb", "her", "hil", "him", "hin", "hit", "hmn", "hmo", "hrv",
            "hsb", "hun", "hup", "iba", "ibo", "ice", "ido", "iii", "ijo", "iku", "ile", "ilo", "ina", "inc",
            "ind", "ine", "inh", "ipk", "ira", "iro", "ita", "jav", "jbo", "jpn", "jpr", "jrb", "kaa", "kab",
            "kac", "kal", "kam", "kan", "kar", "kas", "kau", "kaw", "kaz", "kbd", "kha", "khi", "khm", "kho",
            "kik", "kin", "kir", "kmb", "kok", "kom", "kon", "kor", "kos", "kpe", "krc", "krl", "kro", "kru",
            "kua", "kum", "kur", "kut", "lad", "lah", "lam", "lao", "lat", "lav", "lez", "lim", "lin", "lit",
            "lol", "loz", "ltz", "lua", "lub", "lug", "lui", "lun", "luo", "lus", "mac", "mad", "mag", "mah",
            "mai", "mak", "mal", "man", "mao", "map", "mar", "mas", "may", "mdf", "mdr", "men", "mga", "mic",
            "min", "mis", "mkh", "mlg", "mlt", "mnc", "mni", "mno", "moh", "mon", "mos", "mul", "mun", "mus",
            "mwl", "mwr", "myn", "myv", "nah", "nai", "nap", "nau", "nav", "nbl", "nde", "ndo", "nds", "nep",
            "new", "nia", "nic", "niu", "nno", "nob", "nog", "non", "nor", "nqo", "nso", "nub", "nwc", "nya",
            "nym", "nyn", "nyo", "nzi", "oci", "oji", "ori", "orm", "osa", "oss", "ota", "oto", "paa", "pag",
            "pal", "pam", "pan", "pap", "pau", "peo", "per", "phi", "phn", "pli", "pol", "pon", "por", "pra",
            "pro", "pus", "qaa-qtz", "que", "raj", "rap", "rar", "roa", "roh", "rom", "rum", "run", "rup",
            "rus", "sad", "sag", "sah", "sai", "sal", "sam", "san", "sas", "sat", "scn", "sco", "sel", "sem",
            "sga", "sgn", "shn", "sid", "sin", "sio", "sit", "sla", "slo", "slv", "sma", "sme", "smi", "smj",
            "smn", "smo", "sms", "sna", "snd", "snk", "sog", "som", "son", "sot", "spa", "srd", "srn", "srp",
            "srr", "ssa", "ssw", "suk", "sun", "sus", "sux", "swa", "swe", "syc", "syr", "tah", "tai", "tam",
            "tat", "tel", "tem", "ter", "tet", "tgk", "tgl", "tha", "tib", "tig", "tir", "tiv", "tkl", "tlh",
            "tli", "tmh", "tog", "ton", "tpi", "tsi", "tsn", "tso", "tuk", "tum", "tup", "tur", "tut", "tvl",
            "twi", "tyv", "udm", "uga", "uig", "ukr", "umb", "und", "urd", "uzb", "vai", "ven", "vie", "vol",
            "vot", "wak", "wal", "war", "was", "wel", "wen", "wln", "wol", "xal", "xho", "yao", "yap", "yid",
            "yor", "ypk", "zap", "zbl", "zen", "zgh", "zha", "znd", "zul", "zun", "zxx", "zza", "CHOOSE MY OWN"}));

        langPostfixCB3.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"﻿aar", "abk", "ace", "ach", "ada", "ady", "afa", "afh", "afr", "ain", "aka", "akk", "alb",
            "ale", "alg", "alt", "amh", "ang", "anp", "apa", "ara", "arc", "arg", "arm", "arn", "arp", "art",
            "arw", "asm", "ast", "ath", "aus", "ava", "ave", "awa", "aym", "aze", "bad", "bai", "bak", "bal",
            "bam", "ban", "baq", "bas", "bat", "bej", "bel", "bem", "ben", "ber", "bho", "bih", "bik", "bin",
            "bis", "bla", "bnt", "bos", "bra", "bre", "btk", "bua", "bug", "bul", "bur", "byn", "cad", "cai",
            "car", "cat", "cau", "ceb", "cel", "cha", "chb", "che", "chg", "chi", "chk", "chm", "chn", "cho",
            "chp", "chr", "chu", "chv", "chy", "cmc", "cop", "cor", "cos", "cpe", "cpf", "cpp", "cre", "crh",
            "crp", "csb", "cus", "cze", "dak", "dan", "dar", "day", "del", "den", "dgr", "din", "div", "doi",
            "dra", "dsb", "dua", "dum", "dut", "dyu", "dzo", "efi", "egy", "eka", "elx", "eng", "enm", "epo",
            "est", "ewe", "ewo", "fan", "fao", "fat", "fij", "fil", "fin", "fiu", "fon", "fre", "frm", "fro",
            "frr", "frs", "fry", "ful", "fur", "gaa", "gay", "gba", "gem", "geo", "ger", "gez", "gil", "gla",
            "gle", "glg", "glv", "gmh", "goh", "gon", "gor", "got", "grb", "grc", "gre", "grn", "gsw", "guj",
            "gwi", "hai", "hat", "hau", "haw", "heb", "her", "hil", "him", "hin", "hit", "hmn", "hmo", "hrv",
            "hsb", "hun", "hup", "iba", "ibo", "ice", "ido", "iii", "ijo", "iku", "ile", "ilo", "ina", "inc",
            "ind", "ine", "inh", "ipk", "ira", "iro", "ita", "jav", "jbo", "jpn", "jpr", "jrb", "kaa", "kab",
            "kac", "kal", "kam", "kan", "kar", "kas", "kau", "kaw", "kaz", "kbd", "kha", "khi", "khm", "kho",
            "kik", "kin", "kir", "kmb", "kok", "kom", "kon", "kor", "kos", "kpe", "krc", "krl", "kro", "kru",
            "kua", "kum", "kur", "kut", "lad", "lah", "lam", "lao", "lat", "lav", "lez", "lim", "lin", "lit",
            "lol", "loz", "ltz", "lua", "lub", "lug", "lui", "lun", "luo", "lus", "mac", "mad", "mag", "mah",
            "mai", "mak", "mal", "man", "mao", "map", "mar", "mas", "may", "mdf", "mdr", "men", "mga", "mic",
            "min", "mis", "mkh", "mlg", "mlt", "mnc", "mni", "mno", "moh", "mon", "mos", "mul", "mun", "mus",
            "mwl", "mwr", "myn", "myv", "nah", "nai", "nap", "nau", "nav", "nbl", "nde", "ndo", "nds", "nep",
            "new", "nia", "nic", "niu", "nno", "nob", "nog", "non", "nor", "nqo", "nso", "nub", "nwc", "nya",
            "nym", "nyn", "nyo", "nzi", "oci", "oji", "ori", "orm", "osa", "oss", "ota", "oto", "paa", "pag",
            "pal", "pam", "pan", "pap", "pau", "peo", "per", "phi", "phn", "pli", "pol", "pon", "por", "pra",
            "pro", "pus", "qaa-qtz", "que", "raj", "rap", "rar", "roa", "roh", "rom", "rum", "run", "rup",
            "rus", "sad", "sag", "sah", "sai", "sal", "sam", "san", "sas", "sat", "scn", "sco", "sel", "sem",
            "sga", "sgn", "shn", "sid", "sin", "sio", "sit", "sla", "slo", "slv", "sma", "sme", "smi", "smj",
            "smn", "smo", "sms", "sna", "snd", "snk", "sog", "som", "son", "sot", "spa", "srd", "srn", "srp",
            "srr", "ssa", "ssw", "suk", "sun", "sus", "sux", "swa", "swe", "syc", "syr", "tah", "tai", "tam",
            "tat", "tel", "tem", "ter", "tet", "tgk", "tgl", "tha", "tib", "tig", "tir", "tiv", "tkl", "tlh",
            "tli", "tmh", "tog", "ton", "tpi", "tsi", "tsn", "tso", "tuk", "tum", "tup", "tur", "tut", "tvl",
            "twi", "tyv", "udm", "uga", "uig", "ukr", "umb", "und", "urd", "uzb", "vai", "ven", "vie", "vol",
            "vot", "wak", "wal", "war", "was", "wel", "wen", "wln", "wol", "xal", "xho", "yao", "yap", "yid",
            "yor", "ypk", "zap", "zbl", "zen", "zgh", "zha", "znd", "zul", "zun", "zxx", "zza", "CHOOSE MY OWN"}));

        dictGenInputCharEncodingCB.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"UTF-8", "ISO-8859-1", "US-ASCII"}));

        indexCharEncodingCB.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"UTF-8", "ISO-8859-1", "US-ASCII"}));

        searchListCharEncodingCB.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"UTF-8", "ISO-8859-1", "US-ASCII"}));

        dictionaryCharEncodingCB.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"UTF-8", "ISO-8859-1", "US-ASCII"}));

        dictGenSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tab ( \\t )", "Carriage Return ( \\r )", "Form Feed ( \\f )", "Comma ( , )",
            "Semi-Colon ( ; )", "Colon ( : )"}));

        indexFileSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tab ( \\t )", "Carriage Return ( \\r )", "Form Feed ( \\f )", "Comma ( , )",
            "Semi-Colon ( ; )", "Colon ( : )"}));

        searchListSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tab ( \\t )", "Carriage Return ( \\r )", "Form Feed ( \\f )", "Comma ( , )",
            "Semi-Colon ( ; )", "Colon ( : )"}));

        dictFileSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Tab ( \\t )", "Carriage Return ( \\r )", "Form Feed ( \\f )", "Comma ( , )",
            "Semi-Colon ( ; )", "Colon ( : )"}));
    }

    /**
     * showMoreInfo() shows some extra information about the properties.
     */
    private void showMoreInfo() {
        JOptionPane.showMessageDialog(proPrevWin, moreInfo, I18n.tr("additionalInfo.dfmPropCreate"),
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Duplicated from DfMCreatorMain because of the fact that
    // the editExistingPropsFileInPropsEditorWin() method defined
    // there and the one defined here are not the same.
    private void editExistingPropsFile() {
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

        if (n == JOptionPane.YES_OPTION) {
            editExistingPropsFileInTextEditor();
        } else if (n == JOptionPane.NO_OPTION) {
            editExistingPropsFileInPropsEditorWin();
        } else if (n == JOptionPane.CANCEL_OPTION || n == JOptionPane.CLOSED_OPTION) {
            // Do nothing for now.
        }
    }

    /**
     * editExistingPropsFileInTextEditor() launches a file open dialog in order
     * to let the user choose a DictionaryForMIDs.properties file that the user
     * wishes to edit and then launches the PropertiesPreview window. The user
     * can then edit the file manually and then save it.
     */
    public void editExistingPropsFileInTextEditor() {
        try {
            if (openPropFile()) {
                proPrevWin.editButton.setSelected(true);
                proPrevWin.editButton.setText(I18n.tr("doNotEdit.dfmPropCreate"));
                proPrevWin.PropFileView.setVisible(true);
                proPrevWin.propsText.setCaretPosition(0);
            }
        } catch (UnsupportedOperationException ex) {
            DfMCreatorMain.printAnyMsg(openPropFileErrorMsg, I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            DfMCreatorMain.printAnyMsg(NotThePropFileErrorMsg, I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * getPropertiesFile() Edits an existing DictionaryForMIDs.properties file
     * in the PropertiesEditor.
     */
    private void editExistingPropsFileInPropsEditorWin() {
        try {
            getPropertiesFile();
        } catch (UnsupportedOperationException ex) {
            DfMCreatorMain.printAnyMsg(openPropFileErrorMsg,
                    I18n.tr("error.dfmPropCreate"),
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            DfMCreatorMain.printAnyMsg(NotThePropFileErrorMsg,
                    I18n.tr("error.dfmPropCreate"),
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
        if (openPropsFileForGUIEditFlag) {
            updatePropsEditLabel();
            expandPropsInPropsEditor();
            updateNumOfLang();
        }
    }

    /**
     * getPropertiesFile() launches a file open dialog in order to let the user
     * choose a DictionaryForMIDs.properties file that he wishes to edit and
     * then launches the PropertiesEditor window. He user can then edit the file
     * with the powerful features of the PropertiesEditor window and then save
     * the file.
     */
    public void getPropertiesFile() throws UnsupportedOperationException {
        propertiesFile = DfMCreatorMain.dfmCreator.getFile(false);
        //String propFileString = propertiesFile + DfMCreatorMain.PATH_SEPARATOR + PropertiesPreview.PROPERTY_FILE_NAME;
        propsFile = new File(propertiesFile);
        if ("".equals(propertiesFile)) {
            openPropsFileForGUIEditFlag = false; // this is not really needed since its already set to false.
        } else if (!propsFile.exists() || !propsFile.canRead()) {
            openPropsFileForGUIEditFlag = false;
            throw new UnsupportedOperationException();
        } else if (!propsFile.getName().equals(PropertiesPreview.PROPERTY_FILE_NAME)) {
            openPropsFileForGUIEditFlag = false;
            throw new IllegalArgumentException();
        } else {
            openPropsFileForGUIEditFlag = true;
        }
    }

    public void updatePropsEditLabel() {
        editingTaskInfo.setText("");
        editingTaskInfo.setText(I18n.tr("props.file.creation.task.info.prefix")
                + propertiesFile + DfMCreatorMain.PATH_SEPARATOR
                + PropertiesPreview.PROPERTY_FILE_NAME + I18n.tr("props.file.creation.task.info.suffix"));
    }

    // This returns an object made of string that
    // that can be added as an item to a combobox.
    public static Object makeObj(final String item) {
        return new Object() {
            @Override
            public String toString() {
                return item;
            }
        };
    }

    /**
     * This subroutine reads a DictionaryForMIDs.properties file and then
     * applies its settings to the PropertiesEditor so as to be able to edit the
     * file within it.
     */
    public void expandPropsInPropsEditor() {
        dfMProps = new Properties();

        loadedProperties = new ArrayList<>();
        nonLoadedProperties = new ArrayList<>();

        try {
            // Loading the properties file
            dfMProps.load(new FileInputStream(propsFile));

            // Passing the values to the different components of the PropertiesEditor
            String infoTx = dfMProps.getProperty("infoText");
            if (infoTx != null && !"".equals(infoTx)) {
                infoTextTA.setText(infoTx);
                loadedProperties.add("infoText");
            } else {
                nonLoadedProperties.add("infoText");
            }

            String dictAbb = dfMProps.getProperty("dictionaryAbbreviation");
            if (dictAbb != null && !"".equals(dictAbb)) {
                dictAbbrevTF.setText(dictAbb);
                loadedProperties.add("dictionaryAbbreviation");
            } else {
                nonLoadedProperties.add("dictionaryAbbreviation");
            }

            String numOfLang = dfMProps.getProperty("numberOfAvailableLanguages");
            if (numOfLang != null && !"".equals(numOfLang)) {
                numOfLangCmbBox.setSelectedItem(numOfLang);
                loadedProperties.add("numberOfAvailableLanguages");
            } else {
                // in case the property numberOfAvailableLanguages
                // has not been found defaut it to 2 languages.
                numOfLangCmbBox.setSelectedItem("2");
                loadedProperties.add("numberOfAvailableLanguages");
            }

            String inputCharEnc = dfMProps.getProperty("dictionaryGenerationInputCharEncoding");
            if (inputCharEnc != null && !"".equals(inputCharEnc)) {
                Object inputCharEncObj = makeObj(inputCharEnc);
                dictGenInputCharEncodingCB.addItem(inputCharEncObj);
                dictGenInputCharEncodingCB.setSelectedItem(inputCharEncObj);
                loadedProperties.add("dictionaryGenerationInputCharEncoding");
            } else {
                nonLoadedProperties.add("dictionaryGenerationInputCharEncoding");
            }

            String idxCharEnc = dfMProps.getProperty("indexCharEncoding");
            if (idxCharEnc != null && !"".equals(idxCharEnc)) {
                Object idxCharEncObj = makeObj(idxCharEnc);
                indexCharEncodingCB.addItem(idxCharEncObj);
                indexCharEncodingCB.setSelectedItem(idxCharEncObj);
                loadedProperties.add("indexCharEncoding");
            } else {
                nonLoadedProperties.add("indexCharEncoding");
            }

            String searchListCharEnc = dfMProps.getProperty("searchListCharEncoding");
            if (searchListCharEnc != null && !"".equals(searchListCharEnc)) {
                Object searchListCharEncObj = makeObj(searchListCharEnc);
                searchListCharEncodingCB.addItem(searchListCharEncObj);
                searchListCharEncodingCB.setSelectedItem(searchListCharEncObj);
                loadedProperties.add("searchListCharEncoding");
            } else {
                nonLoadedProperties.add("searchListCharEncoding");
            }

            String dictCharEnc = dfMProps.getProperty("dictionaryCharEncoding");
            if (dictCharEnc != null && !"".equals(dictCharEnc)) {
                Object dictCharEncObj = makeObj(dictCharEnc);
                dictionaryCharEncodingCB.addItem(dictCharEncObj);
                dictionaryCharEncodingCB.setSelectedItem(dictCharEncObj);
                loadedProperties.add("dictionaryCharEncoding");
            } else {
                nonLoadedProperties.add("dictionaryCharEncoding");
            }

            String dictGenSepChar = dfMProps.getProperty("dictionaryGenerationSeparatorCharacter");
            if (dictGenSepChar != null && !"".equals(dictGenSepChar)) {
                int i = revertFilterSepChar(dictGenSepChar);
                if (i <= 5) {
                    dictGenSepCharCB.setSelectedIndex(i);
                } else if (i == 1000) {
                    Object dictGenSepCharObj = makeObj(dictGenSepChar);
                    dictGenSepCharCB.addItem(dictGenSepCharObj);
                    dictGenSepCharCB.setSelectedItem(dictGenSepCharObj);
                }
                loadedProperties.add("dictionaryGenerationSeparatorCharacter");
            } else {
                nonLoadedProperties.add("dictionaryGenerationSeparatorCharacter");
            }

            String indFileSepChar = dfMProps.getProperty("indexFileSeparationCharacter");
            if (indFileSepChar != null && !"".equals(indFileSepChar)) {
                int i = revertFilterSepChar(indFileSepChar);
                if (i <= 5) {
                    indexFileSepCharCB.setSelectedIndex(i);
                } else if (i == 1000) {
                    Object indFileSepCharObj = makeObj(indFileSepChar);
                    indexFileSepCharCB.addItem(indFileSepCharObj);
                    indexFileSepCharCB.setSelectedItem(indFileSepCharObj);
                }
                loadedProperties.add("indexFileSeparationCharacter");
            } else {
                nonLoadedProperties.add("indexFileSeparationCharacter");
            }

            String searchListSepChar = dfMProps.getProperty("searchListFileSeparationCharacter");
            if (searchListSepChar != null && !"".equals(searchListSepChar)) {
                int i = revertFilterSepChar(searchListSepChar);
                if (i <= 5) {
                    searchListSepCharCB.setSelectedIndex(i);
                } else if (i == 1000) {
                    Object searchListSepCharObj = makeObj(searchListSepChar);
                    searchListSepCharCB.addItem(searchListSepCharObj);
                    searchListSepCharCB.setSelectedItem(searchListSepCharObj);
                }
                loadedProperties.add("searchListFileSeparationCharacter");
            } else {
                nonLoadedProperties.add("searchListFileSeparationCharacter");
            }

            String dictFileSepChar = dfMProps.getProperty("dictionaryFileSeparationCharacter");
            if (dictFileSepChar != null && !"".equals(dictFileSepChar)) {
                int i = revertFilterSepChar(dictFileSepChar);
                if (i <= 5) {
                    dictFileSepCharCB.setSelectedIndex(i);
                } else if (i == 1000) {
                    Object dictFileSepCharObj = makeObj(dictFileSepChar);
                    dictFileSepCharCB.addItem(dictFileSepCharObj);
                    dictFileSepCharCB.setSelectedItem(dictFileSepCharObj);
                }
                loadedProperties.add("dictionaryFileSeparationCharacter");
            } else {
                nonLoadedProperties.add("dictionaryFileSeparationCharacter");
            }

            String lang1PostFix = dfMProps.getProperty("language1FilePostfix");
            if (lang1PostFix != null && !"".equals(lang1PostFix)) {
                Object lang1PostFixObj = makeObj(lang1PostFix);
                langPostfixCB1.addItem(lang1PostFixObj);
                langPostfixCB1.setSelectedItem(lang1PostFixObj);
                loadedProperties.add("language1FilePostfix");
            } else {
                nonLoadedProperties.add("language1FilePostfix");
            }

            String lang2PostFix = dfMProps.getProperty("language2FilePostfix");
            if (lang2PostFix != null && !"".equals(lang2PostFix)) {
                Object lang2PostFixObj = makeObj(lang2PostFix);
                langPostfixCB2.addItem(lang2PostFixObj);
                langPostfixCB2.setSelectedItem(lang2PostFixObj);
                loadedProperties.add("language2FilePostfix");
            } else {
                nonLoadedProperties.add("language2FilePostfix");
            }

            String dictGenOmitPar = dfMProps.getProperty("dictionaryGenerationOmitParFromIndex");
            if (dictGenOmitPar != null && !"".equals(dictGenOmitPar)) {
                switch (dictGenOmitPar) {
                    case "true":
                        dictGenOmitParCB.setSelected(true);
                        break;
                    case "false":
                        dictGenOmitParCB.setSelected(false);
                        break;
                }
                loadedProperties.add("dictionaryGenerationOmitParFromIndex");
            } else {
                nonLoadedProperties.add("dictionaryGenerationOmitParFromIndex");
            }

            String lang1IsSearch = dfMProps.getProperty("language1IsSearchable");
            if (lang1IsSearch != null && !"".equals(lang1IsSearch)) {
                switch (lang1IsSearch) {
                    case "true":
                        langIsSearchCB1.setSelected(true);
                        break;
                    case "false":
                        langIsSearchCB1.setSelected(false);
                        break;
                }
                loadedProperties.add("language1IsSearchable");
            } else {
                nonLoadedProperties.add("language1IsSearchable");
            }

            String lang2IsSearch = dfMProps.getProperty("language2IsSearchable");
            if (lang2IsSearch != null && !"".equals(lang2IsSearch)) {
                switch (lang2IsSearch) {
                    case "true":
                        langIsSearchCB2.setSelected(true);
                        break;
                    case "false":
                        langIsSearchCB2.setSelected(false);
                        break;
                }
                loadedProperties.add("language2IsSearchable");
            } else {
                nonLoadedProperties.add("language2IsSearchable");
            }

            String lang1GenInd = dfMProps.getProperty("language1GenerateIndex");
            if (lang1GenInd != null && !"".equals(lang1GenInd)) {
                switch (lang1GenInd) {
                    case "true":
                        langGenIndCB1.setSelected(true);
                        break;
                    case "false":
                        langGenIndCB1.setSelected(false);
                        break;
                }
                loadedProperties.add("language1GenerateIndex");
            } else {
                nonLoadedProperties.add("language1GenerateIndex");
            }

            String lang2GenInd = dfMProps.getProperty("language2GenerateIndex");
            if (lang2GenInd != null && !"".equals(lang2GenInd)) {
                switch (lang2GenInd) {
                    case "true":
                        langGenIndCB2.setSelected(true);
                        break;
                    case "false":
                        langGenIndCB2.setSelected(false);
                        break;
                }
                loadedProperties.add("language2GenerateIndex");
            } else {
                nonLoadedProperties.add("language2GenerateIndex");
            }

            String lang1HasSepDict = dfMProps.getProperty("language1HasSeparateDictionaryFile");
            if (lang1HasSepDict != null && !"".equals(lang1HasSepDict)) {
                switch (lang1HasSepDict) {
                    case "true":
                        langHasSepDictCB1.setSelected(true);
                        break;
                    case "false":
                        langHasSepDictCB1.setSelected(false);
                        break;
                }
                loadedProperties.add("language1HasSeparateDictionaryFile");
            } else {
                nonLoadedProperties.add("language1HasSeparateDictionaryFile");
            }

            String lang2HasSepDict = dfMProps.getProperty("language2HasSeparateDictionaryFile");
            if (lang2HasSepDict != null && !"".equals(lang2HasSepDict)) {
                switch (lang2HasSepDict) {
                    case "true":
                        langHasSepDictCB2.setSelected(true);
                        break;
                    case "false":
                        langHasSepDictCB2.setSelected(false);
                        break;
                }
                loadedProperties.add("language2HasSeparateDictionaryFile");
            } else {
                nonLoadedProperties.add("language2HasSeparateDictionaryFile");
            }

            String SearchFileMaxSize = dfMProps.getProperty("searchListFileMaxSize");
            if (SearchFileMaxSize != null && !"".equals(SearchFileMaxSize) && (Integer.parseInt(SearchFileMaxSize) != 0)) {
                listMaxSizeSL.setValue(Integer.parseInt(SearchFileMaxSize));
                loadedProperties.add("searchListFileMaxSize");
            } else {
                nonLoadedProperties.add("searchListFileMaxSize");
            }

            String IndFileMaxSize = dfMProps.getProperty("indexFileMaxSize");
            if (IndFileMaxSize != null && !"".equals(IndFileMaxSize) && (Integer.parseInt(IndFileMaxSize) != 0)) {
                indexMaxSizeSL.setValue(Integer.parseInt(IndFileMaxSize));
                loadedProperties.add("indexFileMaxSize");
            } else {
                nonLoadedProperties.add("indexFileMaxSize");
            }

            String DictFileMaxSize = dfMProps.getProperty("dictionaryFileMaxSize");
            if (DictFileMaxSize != null && !"".equals(DictFileMaxSize) && (Integer.parseInt(DictFileMaxSize) != 0)) {
                dictMaxSizeSL.setValue(Integer.parseInt(DictFileMaxSize));
                loadedProperties.add("dictionaryFileMaxSize");
            } else {
                nonLoadedProperties.add("dictionaryFileMaxSize");
            }

            String MinNumberOfEntPerDict = dfMProps.getProperty("dictionaryGenerationMinNumberOfEntriesPerDictionaryFile");
            if (MinNumberOfEntPerDict != null && !"".equals(MinNumberOfEntPerDict) && (Integer.parseInt(MinNumberOfEntPerDict) != 0)) {
                minNumEntPerDictFileSL.setValue(Integer.parseInt(MinNumberOfEntPerDict));
                loadedProperties.add("dictionaryGenerationMinNumberOfEntriesPerDictionaryFile");
            } else {
                nonLoadedProperties.add("dictionaryGenerationMinNumberOfEntriesPerDictionaryFile");
            }

            String MinNumberOfEntriesPerInd = dfMProps.getProperty("dictionaryGenerationMinNumberOfEntriesPerIndexFile");
            if (MinNumberOfEntriesPerInd != null && !"".equals(MinNumberOfEntriesPerInd) && (Integer.parseInt(MinNumberOfEntriesPerInd) != 0)) {
                minNumEntPerIndFileSL.setValue(Integer.parseInt(MinNumberOfEntriesPerInd));
                loadedProperties.add("dictionaryGenerationMinNumberOfEntriesPerIndexFile");
            } else {
                nonLoadedProperties.add("dictionaryGenerationMinNumberOfEntriesPerIndexFile");
            }

            String lang1DispTx = dfMProps.getProperty("language1DisplayText");
            if (lang1DispTx != null && !"".equals(lang1DispTx)) {
                langDispTextTF1.setText(lang1DispTx);
                loadedProperties.add("language1DisplayText");
            } else {
                nonLoadedProperties.add("language1DisplayText");
            }

            String lang1ExpSplit = dfMProps.getProperty("dictionaryGenerationLanguage1ExpressionSplitString");
            if (lang1ExpSplit != null && !"".equals(lang1ExpSplit)) {
                langExpSplitStringTF1.setText(lang1ExpSplit);
                loadedProperties.add("dictionaryGenerationLanguage1ExpressionSplitString");
            } else {
                nonLoadedProperties.add("dictionaryGenerationLanguage1ExpressionSplitString");
            }

            String lang1Norm = dfMProps.getProperty("language1NormationClassName");
            if (lang1Norm == null || "".equals(lang1Norm)) {
                langNormCB1.setSelectedItem("NONE");
                nonLoadedProperties.add("language1NormationClassName");
            } else {
                Object lang1NormObj = makeObj(lang1Norm);
                langNormCB1.addItem(lang1NormObj);
                langNormCB1.setSelectedItem(lang1NormObj);
                loadedProperties.add("language1NormationClassName");
            }

            String lang1DictUpd = dfMProps.getProperty("language1DictionaryUpdateClassName");
            if (lang1DictUpd == null || "".equals(lang1DictUpd)) {
                langDictUpdateCB1.setSelectedItem("NONE");
                nonLoadedProperties.add("language1DictionaryUpdateClassName");
            } else {
                Object lang1DictUpdObj = makeObj(lang1DictUpd);
                langDictUpdateCB1.addItem(lang1DictUpdObj);
                langDictUpdateCB1.setSelectedItem(lang1DictUpdObj);
                loadedProperties.add("language1DictionaryUpdateClassName");
            }

            String lang1IndNumSrcEnt = dfMProps.getProperty("language1IndexNumberOfSourceEntries");
            if (lang1IndNumSrcEnt != null && !"".equals(lang1IndNumSrcEnt)) {
                langIndNumSrcEntTF1.setText(lang1IndNumSrcEnt);
                loadedProperties.add("language1IndexNumberOfSourceEntries");
            } else {
                nonLoadedProperties.add("language1IndexNumberOfSourceEntries");
            }

            String lang2DispTx = dfMProps.getProperty("language2DisplayText");
            if (lang2DispTx != null && !"".equals(lang2DispTx)) {
                langDispTextTF2.setText(lang2DispTx);
                loadedProperties.add("language2DisplayText");
            } else {
                nonLoadedProperties.add("language2DisplayText");
            }

            String lang2ExpSplit = dfMProps.getProperty("dictionaryGenerationLanguage2ExpressionSplitString");
            if (lang2ExpSplit != null && !"".equals(lang2ExpSplit)) {
                langExpSplitStringTF2.setText(lang2ExpSplit);
                loadedProperties.add("dictionaryGenerationLanguage2ExpressionSplitString");
            } else {
                nonLoadedProperties.add("dictionaryGenerationLanguage2ExpressionSplitString");
            }

            String lang2DictUpd = dfMProps.getProperty("language2DictionaryUpdateClassName");
            if (lang2DictUpd == null || "".equals(lang2DictUpd)) {
                langDictUpdateCB2.setSelectedItem("NONE");
                nonLoadedProperties.add("language2DictionaryUpdateClassName");
            } else {
                Object lang2DictUpdObj = makeObj(lang2DictUpd);
                langDictUpdateCB2.addItem(lang2DictUpdObj);
                langDictUpdateCB2.setSelectedItem(lang2DictUpdObj);
                loadedProperties.add("language2DictionaryUpdateClassName");
            }

            String lang2Norm = dfMProps.getProperty("language2NormationClassName");
            if (lang2Norm == null || "".equals(lang2Norm)) {
                langNormCB2.setSelectedItem("NONE");
                nonLoadedProperties.add("language2NormationClassName");
            } else {
                Object lang2NormObj = makeObj(lang2Norm);
                langNormCB2.addItem(lang2NormObj);
                langNormCB2.setSelectedItem(lang2NormObj);
                loadedProperties.add("language2NormationClassName");
            }

            String lang2InsNumSrcEnt = dfMProps.getProperty("language2IndexNumberOfSourceEntries");
            if (lang2InsNumSrcEnt != null && !"".equals(lang2InsNumSrcEnt)) {
                langIndNumSrcEntTF2.setText(lang2InsNumSrcEnt);
                loadedProperties.add("language2IndexNumberOfSourceEntries");
            } else {
                nonLoadedProperties.add("language2IndexNumberOfSourceEntries");
            }

            // These are for language 3 if there is one of course
            String langNumb = dfMProps.getProperty("numberOfAvailableLanguages");
            if (langNumb != null && !"".equals(langNumb) && "3".equals(langNumb)) {
                String lang3IsSearch = dfMProps.getProperty("language3IsSearchable");
                if (lang3IsSearch != null && !"".equals(lang3IsSearch)) {
                    switch (lang3IsSearch) {
                        case "true":
                            langIsSearchCB3.setSelected(true);
                            break;
                        case "false":
                            langIsSearchCB3.setSelected(false);
                            break;
                    }
                    loadedProperties.add("language3IsSearchable");
                } else {
                    nonLoadedProperties.add("language3IsSearchable");
                }

                String lang3GenInd = dfMProps.getProperty("language3GenerateIndex");
                if (lang3GenInd != null && !"".equals(lang3GenInd)) {
                    switch (lang3GenInd) {
                        case "true":
                            langGenIndCB3.setSelected(true);
                            break;
                        case "false":
                            langGenIndCB3.setSelected(false);
                            break;
                    }
                    loadedProperties.add("language3GenerateIndex");
                } else {
                    nonLoadedProperties.add("language3GenerateIndex");
                }

                String lang3HasSepDict = dfMProps.getProperty("language3HasSeparateDictionaryFile");
                if (lang3HasSepDict != null && !"".equals(lang3HasSepDict)) {
                    switch (lang3HasSepDict) {
                        case "true":
                            langHasSepDictCB3.setSelected(true);
                            break;
                        case "false":
                            langHasSepDictCB3.setSelected(false);
                            break;
                    }
                    loadedProperties.add("language3HasSeparateDictionaryFile");
                } else {
                    nonLoadedProperties.add("language3HasSeparateDictionaryFile");
                }

                String lang3PostFix = dfMProps.getProperty("language3FilePostfix");
                if (lang3PostFix != null && !"".equals(lang3PostFix)) {
                    Object lang3PostFixObj = makeObj(lang3PostFix);
                    langPostfixCB3.addItem(lang3PostFixObj);
                    langPostfixCB3.setSelectedItem(lang3PostFixObj);
                    loadedProperties.add("language3FilePostfix");
                } else {
                    nonLoadedProperties.add("language3FilePostfix");
                }

                String lang3DispTx = dfMProps.getProperty("language3DisplayText");
                if (lang3DispTx != null && !"".equals(lang3DispTx)) {
                    langDispTextTF3.setText(lang3DispTx);
                    loadedProperties.add("language3DisplayText");
                } else {
                    nonLoadedProperties.add("language3DisplayText");
                }

                String lang3ExpSplit = dfMProps.getProperty("dictionaryGenerationLanguage3ExpressionSplitString");
                if (lang3ExpSplit != null && !"".equals(lang3ExpSplit)) {
                    langExpSplitStringTF3.setText(lang3ExpSplit);
                    loadedProperties.add("dictionaryGenerationLanguage3ExpressionSplitString");
                } else {
                    nonLoadedProperties.add("dictionaryGenerationLanguage3ExpressionSplitString");
                }

                String lang3DictUpd = dfMProps.getProperty("language3DictionaryUpdateClassName");
                if (lang3DictUpd == null || "".equals(lang3DictUpd)) {
                    langDictUpdateCB3.setSelectedItem("NONE");
                    nonLoadedProperties.add("language3DictionaryUpdateClassName");
                } else {
                    Object lang3DictUpdObj = makeObj(lang3DictUpd);
                    langDictUpdateCB3.addItem(lang3DictUpdObj);
                    langDictUpdateCB3.setSelectedItem(lang3DictUpdObj);
                    loadedProperties.add("language3DictionaryUpdateClassName");
                }

                String lang3Norm = dfMProps.getProperty("language3NormationClassName");
                if (lang3Norm == null || "".equals(lang3Norm)) {
                    langNormCB3.setSelectedItem("NONE");
                    nonLoadedProperties.add("language3NormationClassName");
                } else {
                    Object lang3NormObj = makeObj(lang3Norm);
                    langNormCB3.addItem(lang3NormObj);
                    langNormCB3.setSelectedItem(lang3NormObj);
                    loadedProperties.add("language3NormationClassName");
                }

                String lang3IndNumSrcEnt = dfMProps.getProperty("language3IndexNumberOfSourceEntries");
                if (lang3IndNumSrcEnt != null && !"".equals(lang3IndNumSrcEnt)) {
                    langIndNumSrcEntTF3.setText(lang3IndNumSrcEnt);
                    loadedProperties.add("language3IndexNumberOfSourceEntries");
                } else {
                    nonLoadedProperties.add("language3IndexNumberOfSourceEntries");
                }
            }
            // Printing the loaded properties.
            System.out.println(I18n.tr("successfully.loaded.props"));
            String str = I18n.tr("successfully.loaded.props");
            String curProp;
            for (int i = 0; i < loadedProperties.size(); i++) {
                curProp = loadedProperties.remove(i).toString();
                System.out.println(curProp);
                str = str + "\n" + curProp;
            }
            //DfMCreatorMain.printAnyMsg(str, I18n.tr("successfully.loaded.props.WinTitle"),
            //                                             JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * showSamplePropFile() shows a sample DictionaryForMIDs.properties file.
     */
    public void showSamplePropFile() {
        JOptionPane.showMessageDialog(proPrevWin, samplePropertyFile, I18n.tr("samplePropsWinTitle.dfmPropCreate"),
                JOptionPane.PLAIN_MESSAGE);
    }

    public boolean openPropFile() throws UnsupportedOperationException,
            IllegalArgumentException {
        boolean fileChosen;
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            propsFileChosen = fc.getSelectedFile();
            if (!propsFileChosen.exists() || !propsFileChosen.canRead()) {
                throw new UnsupportedOperationException();
            }
            String propFileName = propsFileChosen.getName();
            if (!propFileName.equals(PropertiesPreview.PROPERTY_FILE_NAME)) {
                throw new IllegalArgumentException();
            }
            proPrevWin.propsText.setText("");
            try {
                try (BufferedReader in = new BufferedReader(new FileReader(propsFileChosen))) {
                    String l;
                    while ((l = in.readLine()) != null) {
                        proPrevWin.appendText(l);
                        proPrevWin.appendText("\n");
                        proPrevWin.propsText.setEditable(true);
                        proPrevWin.propsText.setBackground(new java.awt.Color(255, 255, 190));
                    }
                }
            } catch (IOException ex) {
                DfMCreatorMain.printAnyMsg(fileCantBeOpenedErrorMsg, I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
                System.out.println(ex + "\n");
            }
            fileChosen = true;
        } else {
            fileChosen = false;
        }
        return fileChosen;
    }

    /**
     * viewAndOrEditAndSave() launches the PropertiesPreview window with the
     * values selected/entered by the user in the PropertiesEditor. Here also
     * the user can edit the file manually before saving it. Note that the name
     * of the file is automatically set by the PropertiesPreview and is
     * DictionaryForMIDs.properties.
     */
    public void viewAndOrEditAndSave() {
        try {
            validateValues();
            setValuesInThePropsPreviewWindow();
            proPrevWin.propsText.setCaretPosition(0);
            proPrevWin.editButton.setSelected(false);
            proPrevWin.editButton.setText(I18n.tr("edit.dfmPropCreate"));
            if (!PropertiesPreview.hasPropsFileAlreadyBeenSaved) {
                proPrevWin.PropFileView.setVisible(true);
            } else {
                try {
                    proPrevWin.PropFileView.setVisible(false);
                    proPrevWin.savePropFileWithoutPrompt();
                } catch (UnsupportedOperationException ex) {
                    DfMCreatorMain.printAnyMsg(PropertiesPreview.canWriteFileMsg, I18n.tr("dirNotAccessible"), JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    DfMCreatorMain.printAnyMsg(PropertiesPreview.internalErrorMsg, I18n.tr("error"), JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (IllegalArgumentException e) {
            DfMCreatorMain.printAnyMsg(RequiredArgsAbsentErrorMsg, I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e + "\n");
        }
    }

    /**
     * validateValues() validates the values entered by the user in the
     * PropertiesEditor. Checks if the required values are all provided, if some
     * TextFields are empty or not and so forth.
     */
    public void validateValues() {
        if ("".equals(infoTextTA.getText()) || "".equals(dictAbbrevTF.getText())
                || "".equals(langDispTextTF1.getText()) || "".equals(langDispTextTF2.getText())
                || "".equals(langPostfixCB1.getSelectedItem().toString()) || "".equals(langPostfixCB2.getSelectedItem().toString())) {
            throw new IllegalArgumentException();
        }

        if (numOfLangCmbBox.getSelectedItem().equals("3")) {
            if ("".equals(langDispTextTF3.getText()) || "".equals(langPostfixCB3.getSelectedItem().toString())) {
                throw new IllegalArgumentException();
            }
        }

        /* Broken. Disabled till fixed!
         try {
         checkInfoTextContents();
         } catch (IllegalArgumentException e){
         DfMCreatorMain.printAnyMsg(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
         */

        infoText = infoTextTA.getText();
        dictionaryAbbreviation = dictAbbrevTF.getText();
        numberOfAvailableLanguages = numOfLangCmbBox.getSelectedItem().toString();
        dictionaryGenerationInputCharEncoding = dictGenInputCharEncodingCB.getSelectedItem().toString();
        indexCharEncoding = indexCharEncodingCB.getSelectedItem().toString();
        searchListCharEncoding = searchListCharEncodingCB.getSelectedItem().toString();
        dictionaryCharEncoding = dictionaryCharEncodingCB.getSelectedItem().toString();

        // separator characters
        dictionaryGenerationSeparatorCharacter = filterSepChar(dictGenSepCharCB);
        indexFileSeparationCharacter = filterSepChar(indexFileSepCharCB);
        searchListFileSeparationCharacter = filterSepChar(searchListSepCharCB);
        dictionaryFileSeparationCharacter = filterSepChar(dictFileSepCharCB);


        if (dictGenOmitParCB.isSelected()) {
            dictionaryGenerationOmitParFromIndex = "true";
        } else {
            dictionaryGenerationOmitParFromIndex = "false";
        }

        if (listMaxSizeSL.getValue() != 0) {
            searchListFileMaxSize = String.valueOf(listMaxSizeSL.getValue());
        }
        if (indexMaxSizeSL.getValue() != 0) {
            indexFileMaxSize = String.valueOf(indexMaxSizeSL.getValue());
        }
        if (dictMaxSizeSL.getValue() != 0) {
            dictionaryFileMaxSize = String.valueOf(dictMaxSizeSL.getValue());
        }
        if (minNumEntPerDictFileSL.getValue() != 0) {
            dictionaryGenerationMinNumberOfEntriesPerDictionaryFile = String.valueOf(minNumEntPerDictFileSL.getValue());
        }
        if (minNumEntPerIndFileSL.getValue() != 0) {
            dictionaryGenerationMinNumberOfEntriesPerIndexFile = String.valueOf(minNumEntPerIndFileSL.getValue());
        }

        if (langIsSearchCB1.isSelected()) {
            language1IsSearchable = "true";
        } else {
            language1IsSearchable = "false";
        }
        if (langGenIndCB1.isSelected()) {
            language1GenerateIndex = "true";
        } else {
            language1GenerateIndex = "false";
        }
        if (langGenIndCB1.isSelected()) {
            language1HasSeparateDictionaryFile = "true";
        } else {
            language1HasSeparateDictionaryFile = "false";
        }

        language1DisplayText = langDispTextTF1.getText();
        //language1FilePostfix = langPostfixCB1.getSelectedItem().toString();
        dictionaryGenerationLanguage1ExpressionSplitString = langExpSplitStringTF1.getText();

        if (langDictUpdateCB1.getSelectedItem().toString().equals("NONE")) {
            language1DictionaryUpdateClassName = "";
        } else {
            language1DictionaryUpdateClassName = langDictUpdateCB1.getSelectedItem().toString();
        }

        if (langNormCB1.getSelectedItem().toString().equals("NONE")) {
            language1NormationClassName = "";
        } else {
            language1NormationClassName = langNormCB1.getSelectedItem().toString();
        }

        language1IndexNumberOfSourceEntries = langIndNumSrcEntTF1.getText();

        if (langIsSearchCB2.isSelected()) {
            language2IsSearchable = "true";
        } else {
            language2IsSearchable = "false";
        }
        if (langGenIndCB2.isSelected()) {
            language2GenerateIndex = "true";
        } else {
            language2GenerateIndex = "false";
        }
        if (langHasSepDictCB2.isSelected()) {
            language2HasSeparateDictionaryFile = "true";
        } else {
            language2HasSeparateDictionaryFile = "false";
        }

        language2DisplayText = langDispTextTF2.getText();
        //language2FilePostfix = langPostfixCB2.getSelectedItem().toString();
        dictionaryGenerationLanguage2ExpressionSplitString = langExpSplitStringTF2.getText();

        if (langDictUpdateCB2.getSelectedItem().toString().equals("NONE")) {
            language2DictionaryUpdateClassName = "";
        } else {
            language2DictionaryUpdateClassName = langDictUpdateCB2.getSelectedItem().toString();
        }

        if (langNormCB2.getSelectedItem().toString().equals("NONE")) {
            language2NormationClassName = "";
        } else {
            language2NormationClassName = langNormCB2.getSelectedItem().toString();
        }

        language2IndexNumberOfSourceEntries = langIndNumSrcEntTF2.getText();


        if (numOfLangCmbBox.getSelectedItem().equals("3")) {
            if (langIsSearchCB3.isSelected()) {
                language3IsSearchable = "true";
            } else {
                language3IsSearchable = "false";
            }
            if (langGenIndCB3.isSelected()) {
                language3GenerateIndex = "true";
            } else {
                language3GenerateIndex = "false";
            }
            if (langHasSepDictCB3.isSelected()) {
                language3HasSeparateDictionaryFile = "true";
            } else {
                language3HasSeparateDictionaryFile = "false";
            }

            language3DisplayText = langDispTextTF3.getText();
            //language3FilePostfix = langPostfixCB3.getSelectedItem().toString();
            dictionaryGenerationLanguage3ExpressionSplitString = langExpSplitStringTF3.getText();

            if (langDictUpdateCB3.getSelectedItem().toString().equals("NONE")) {
                language3DictionaryUpdateClassName = "";
            } else {
                language3DictionaryUpdateClassName = langDictUpdateCB3.getSelectedItem().toString();
            }

            if (langNormCB3.getSelectedItem().toString().equals("NONE")) {
                language3NormationClassName = "";
            } else {
                language3NormationClassName = langNormCB3.getSelectedItem().toString();
            }

            language3IndexNumberOfSourceEntries = langIndNumSrcEntTF3.getText();

        }
    }

    /**
     * setValuesInThePropsPreviewWindow() sets the values entered/selected by
     * the user in the PropertiesPreview window.
     */
    public void setValuesInThePropsPreviewWindow() {
        // passing values to the textarea for preview before saving.
        // General values (general to all languages)
        //ppWin.propsText.setText("");
        proPrevWin.propsText.setText("infoText" + colon + infoText + newline + newline);
        proPrevWin.appendText("dictionaryAbbreviation" + colon + dictionaryAbbreviation + newline);
        proPrevWin.appendText("numberOfAvailableLanguages" + colon + numberOfAvailableLanguages + newline + newline);

        proPrevWin.appendText("indexFileSeparationCharacter" + colon + indexFileSeparationCharacter + newline);
        proPrevWin.appendText("searchListFileSeparationCharacter" + colon + searchListFileSeparationCharacter + newline);
        proPrevWin.appendText("dictionaryFileSeparationCharacter" + colon + dictionaryFileSeparationCharacter + newline);
        proPrevWin.appendText("dictionaryGenerationSeparatorCharacter" + colon + dictionaryGenerationSeparatorCharacter + newline + newline);

        if (!"".equals(dictionaryGenerationInputCharEncoding)) {
            proPrevWin.appendText("dictionaryGenerationInputCharEncoding" + colon + dictionaryGenerationInputCharEncoding + newline);
        }
        if (!"".equals(indexCharEncoding)) {
            proPrevWin.appendText("indexCharEncoding" + colon + indexCharEncoding + newline);
        }
        if (!"".equals(searchListCharEncoding)) {
            proPrevWin.appendText("searchListCharEncoding" + colon + searchListCharEncoding + newline);
        }
        if (!"".equals(dictionaryCharEncoding)) {
            proPrevWin.appendText("dictionaryCharEncoding" + colon + dictionaryCharEncoding + newline + newline);
        }
        if (!"".equals(searchListFileMaxSize)) {
            proPrevWin.appendText("searchListFileMaxSize" + colon + searchListFileMaxSize + newline);
        }
        if (!"".equals(indexFileMaxSize)) {
            proPrevWin.appendText("indexFileMaxSize" + colon + indexFileMaxSize + newline);
        }
        if (!"".equals(dictionaryFileMaxSize)) {
            proPrevWin.appendText("dictionaryFileMaxSize" + colon + dictionaryFileMaxSize + newline + newline);
        }
        if (!"".equals(dictionaryGenerationMinNumberOfEntriesPerDictionaryFile)) {
            proPrevWin.appendText("dictionaryGenerationMinNumberOfEntriesPerDictionaryFile" + colon
                    + dictionaryGenerationMinNumberOfEntriesPerDictionaryFile + newline);
        }
        if (!"".equals(dictionaryGenerationMinNumberOfEntriesPerIndexFile)) {
            proPrevWin.appendText("dictionaryGenerationMinNumberOfEntriesPerIndexFile" + colon
                    + dictionaryGenerationMinNumberOfEntriesPerIndexFile + newline + newline);
        }
        proPrevWin.appendText("dictionaryGenerationOmitParFromIndex" + colon + dictionaryGenerationOmitParFromIndex + newline + newline);

        // language 1 non optional values.
        proPrevWin.appendText("language1DisplayText" + colon + language1DisplayText + newline);
        proPrevWin.appendText("language1FilePostfix" + colon + language1FilePostfix + newline + newline);

        // language 1 boolean values.
        proPrevWin.appendText("language1IsSearchable" + colon + language1IsSearchable + newline);
        proPrevWin.appendText("language1GenerateIndex" + colon + language1GenerateIndex + newline);
        proPrevWin.appendText("language1HasSeparateDictionaryFile" + colon + language1HasSeparateDictionaryFile + newline + newline);

        // other values.
        if (!"".equals(dictionaryGenerationLanguage1ExpressionSplitString)) {
            proPrevWin.appendText("dictionaryGenerationLanguage1ExpressionSplitString" + colon
                    + dictionaryGenerationLanguage1ExpressionSplitString + newline);
        }
        if (!"".equals(language1IndexNumberOfSourceEntries)) {
            proPrevWin.appendText("language1IndexNumberOfSourceEntries" + colon + language1IndexNumberOfSourceEntries + newline);
        }
        if (!"".equals(language1DictionaryUpdateClassName)) {
            proPrevWin.appendText("language1DictionaryUpdateClassName" + colon + language1DictionaryUpdateClassName + newline);
        }
        if (!"".equals(language1NormationClassName)) {
            proPrevWin.appendText("language1NormationClassName" + colon + language1NormationClassName + newline);
        }

        // language 2 non optional values.
        proPrevWin.appendText("language2DisplayText" + colon + language2DisplayText + newline);
        proPrevWin.appendText("language2FilePostfix" + colon + language2FilePostfix + newline + newline);

        // language 2 boolean values.
        proPrevWin.appendText("language2IsSearchable" + colon + language2IsSearchable + newline);
        proPrevWin.appendText("language2GenerateIndex" + colon + language2GenerateIndex + newline);
        proPrevWin.appendText("language2HasSeparateDictionaryFile" + colon + language2HasSeparateDictionaryFile + newline + newline);

        // other values.
        if (!"".equals(dictionaryGenerationLanguage2ExpressionSplitString)) {
            proPrevWin.appendText("dictionaryGenerationLanguage2ExpressionSplitString" + colon
                    + dictionaryGenerationLanguage2ExpressionSplitString + newline);
        }
        if (!"".equals(language2IndexNumberOfSourceEntries)) {
            proPrevWin.appendText("language2IndexNumberOfSourceEntries" + colon + language2IndexNumberOfSourceEntries + newline);
        }
        if (!"".equals(language2DictionaryUpdateClassName)) {
            proPrevWin.appendText("language2DictionaryUpdateClassName" + colon + language2DictionaryUpdateClassName + newline);
        }
        if (!"".equals(language2NormationClassName)) {
            proPrevWin.appendText("language2NormationClassName" + colon + language2NormationClassName + newline);
        }

        // Number of content declaration variables for language 1 and 2
        if (!"".equals(language1NumberOfContentDeclarations)) {
            proPrevWin.appendText("language1NumberOfContentDeclarations" + colon + language1NumberOfContentDeclarations + newline);
        }

        if (!"".equals(language2NumberOfContentDeclarations)) {
            proPrevWin.appendText("language2NumberOfContentDeclarations" + colon + language2NumberOfContentDeclarations + newline);
        }

        // Langage-1 content variables
        if (!"".equals(language1Content01DisplayText)) {
            proPrevWin.appendText("language1Content01DisplayText" + colon + language1Content01DisplayText + newline);
        }

        if (!"".equals(language1Content01FontColour)) {
            proPrevWin.appendText("language1Content01FontColour" + colon + language1Content01FontColour + newline);
        }

        if (!"".equals(language1Content01FontStyle)) {
            proPrevWin.appendText("language1Content01FontStyle" + colon + language1Content01FontStyle + newline);
        }

        if (!"".equals(language1Content01DisplaySelectable)) {
            proPrevWin.appendText("language1Content01DisplaySelectable" + colon + language1Content01DisplaySelectable + newline);
        }

        if (!"".equals(language1Content02DisplayText)) {
            proPrevWin.appendText("language1Content02DisplayText" + colon + language1Content02DisplayText + newline);
        }

        if (!"".equals(language1Content02FontColour)) {
            proPrevWin.appendText("language1Content02FontColour" + colon + language1Content02FontColour + newline);
        }

        if (!"".equals(language1Content02FontStyle)) {
            proPrevWin.appendText("language1Content02FontStyle" + colon + language1Content02FontStyle + newline);
        }

        if (!"".equals(language1Content02DisplaySelectable)) {
            proPrevWin.appendText("language1Content02DisplaySelectable" + colon + language1Content02DisplaySelectable + newline);
        }

        if (!"".equals(language1Content03DisplayText)) {
            proPrevWin.appendText("language1Content03DisplayText" + colon + language1Content03DisplayText + newline);
        }

        if (!"".equals(language1Content03FontColour)) {
            proPrevWin.appendText("language1Content03FontColour" + colon + language1Content03FontColour + newline);
        }

        if (!"".equals(language1Content03FontStyle)) {
            proPrevWin.appendText("language1Content03FontStyle" + colon + language1Content03FontStyle + newline);
        }

        if (!"".equals(language1Content03DisplaySelectable)) {
            proPrevWin.appendText("language1Content03DisplaySelectable" + colon + language1Content03DisplaySelectable + newline);
        }

        if (!"".equals(language1Content04DisplayText)) {
            proPrevWin.appendText("language1Content04DisplayText" + colon + language1Content04DisplayText + newline);
        }

        if (!"".equals(language1Content04FontColour)) {
            proPrevWin.appendText("language1Content04FontColour" + colon + language1Content04FontColour + newline);
        }

        if (!"".equals(language1Content04FontStyle)) {
            proPrevWin.appendText("language1Content04FontStyle" + colon + language1Content04FontStyle + newline);
        }

        if (!"".equals(language1Content04DisplaySelectable)) {
            proPrevWin.appendText("language1Content04DisplaySelectable" + colon + language1Content04DisplaySelectable + newline + newline);
        }

        // Langage-2 content variables
        if (!"".equals(language2Content01DisplayText)) {
            proPrevWin.appendText("language2Content01DisplayText" + colon + language2Content01DisplayText + newline);
        }

        if (!"".equals(language2Content01FontColour)) {
            proPrevWin.appendText("language2Content01FontColour" + colon + language2Content01FontColour + newline);
        }

        if (!"".equals(language2Content01FontStyle)) {
            proPrevWin.appendText("language2Content01FontStyle" + colon + language2Content01FontStyle + newline);
        }

        if (!"".equals(language2Content01DisplaySelectable)) {
            proPrevWin.appendText("language2Content01DisplaySelectable" + colon + language2Content01DisplaySelectable + newline);
        }

        if (!"".equals(language2Content02DisplayText)) {
            proPrevWin.appendText("language2Content02DisplayText" + colon + language2Content02DisplayText + newline);
        }

        if (!"".equals(language2Content02FontColour)) {
            proPrevWin.appendText("language2Content02FontColour" + colon + language2Content02FontColour + newline);
        }

        if (!"".equals(language2Content02FontStyle)) {
            proPrevWin.appendText("language2Content02FontStyle" + colon + language2Content02FontStyle + newline);
        }

        if (!"".equals(language2Content02DisplaySelectable)) {
            proPrevWin.appendText("language2Content02DisplaySelectable" + colon + language2Content02DisplaySelectable + newline);
        }

        if (!"".equals(language2Content03DisplayText)) {
            proPrevWin.appendText("language2Content03DisplayText" + colon + language2Content03DisplayText + newline);
        }

        if (!"".equals(language2Content03FontColour)) {
            proPrevWin.appendText("language2Content03FontColour" + colon + language2Content03FontColour + newline);
        }

        if (!"".equals(language2Content03FontStyle)) {
            proPrevWin.appendText("language2Content03FontStyle" + colon + language2Content03FontStyle + newline);
        }

        if (!"".equals(language2Content03DisplaySelectable)) {
            proPrevWin.appendText("language2Content03DisplaySelectable" + colon + language2Content03DisplaySelectable + newline);
        }

        if (!"".equals(language2Content04DisplayText)) {
            proPrevWin.appendText("language2Content04DisplayText" + colon + language2Content04DisplayText + newline);
        }

        if (!"".equals(language2Content04FontColour)) {
            proPrevWin.appendText("language2Content04FontColour" + colon + language2Content04FontColour + newline);
        }

        if (!"".equals(language2Content04FontStyle)) {
            proPrevWin.appendText("language2Content04FontStyle" + colon + language2Content04FontStyle + newline);
        }

        if (!"".equals(language2Content04DisplaySelectable)) {
            proPrevWin.appendText("language2Content04DisplaySelectable" + colon + language2Content04DisplaySelectable + newline + newline);
        }


        // language 3 non optional values.
        if (numOfLangCmbBox.getSelectedItem().equals("3")) {
            proPrevWin.appendText("language3DisplayText" + colon + language3DisplayText + newline);
            proPrevWin.appendText("language3FilePostfix" + colon + language3FilePostfix + newline + newline);

            // language 3 boolean values.
            proPrevWin.appendText("language3IsSearchable" + colon + language3IsSearchable + newline);
            proPrevWin.appendText("language3GenerateIndex" + colon + language3GenerateIndex + newline);
            proPrevWin.appendText("language3HasSeparateDictionaryFile" + colon + language3HasSeparateDictionaryFile + newline + newline);

            // other values.
            if (!"".equals(dictionaryGenerationLanguage3ExpressionSplitString)) {
                proPrevWin.appendText("dictionaryGenerationLanguage3ExpressionSplitString" + colon
                        + dictionaryGenerationLanguage3ExpressionSplitString + newline);
            }
            if (!"".equals(language3IndexNumberOfSourceEntries)) {
                proPrevWin.appendText("language3IndexNumberOfSourceEntries" + colon + language3IndexNumberOfSourceEntries + newline);
            }
            if (!"".equals(language3DictionaryUpdateClassName)) {
                proPrevWin.appendText("language3DictionaryUpdateClassName" + colon + language3DictionaryUpdateClassName + newline);
            }
            if (!"".equals(language3NormationClassName)) {
                proPrevWin.appendText("language3NormationClassName" + colon + language3NormationClassName + newline);
            }

            // Langage-3 content variables

            // Number of content declaration variables for language 3
            if (!"".equals(language3NumberOfContentDeclarations)) {
                proPrevWin.appendText("language3NumberOfContentDeclarations" + colon + language3NumberOfContentDeclarations + newline + newline);
            }
            if (!"".equals(language3Content01DisplayText)) {
                proPrevWin.appendText("language3Content01DisplayText" + colon + language3Content01DisplayText + newline);
            }

            if (!"".equals(language3Content01FontColour)) {
                proPrevWin.appendText("language3Content01FontColour" + colon + language3Content01FontColour + newline);
            }

            if (!"".equals(language3Content01FontStyle)) {
                proPrevWin.appendText("language3Content01FontStyle" + colon + language3Content01FontStyle + newline);
            }

            if (!"".equals(language3Content01DisplaySelectable)) {
                proPrevWin.appendText("language3Content01DisplaySelectable" + colon + language3Content01DisplaySelectable + newline);
            }

            if (!"".equals(language3Content02DisplayText)) {
                proPrevWin.appendText("language3Content02DisplayText" + colon + language3Content02DisplayText + newline);
            }

            if (!"".equals(language3Content02FontColour)) {
                proPrevWin.appendText("language3Content02FontColour" + colon + language3Content02FontColour + newline);
            }

            if (!"".equals(language3Content02FontStyle)) {
                proPrevWin.appendText("language3Content02FontStyle" + colon + language3Content02FontStyle + newline);
            }

            if (!"".equals(language3Content02DisplaySelectable)) {
                proPrevWin.appendText("language3Content02DisplaySelectable" + colon + language3Content02DisplaySelectable + newline);
            }

            if (!"".equals(language3Content03DisplayText)) {
                proPrevWin.appendText("language3Content03DisplayText" + colon + language3Content03DisplayText + newline);
            }

            if (!"".equals(language3Content03FontColour)) {
                proPrevWin.appendText("language3Content03FontColour" + colon + language3Content03FontColour + newline);
            }

            if (!"".equals(language3Content03FontStyle)) {
                proPrevWin.appendText("language3Content03FontStyle" + colon + language3Content03FontStyle + newline);
            }

            if (!"".equals(language3Content03DisplaySelectable)) {
                proPrevWin.appendText("language3Content03DisplaySelectable" + colon + language3Content03DisplaySelectable + newline);
            }

            if (!"".equals(language3Content04DisplayText)) {
                proPrevWin.appendText("language3Content04DisplayText" + colon + language3Content04DisplayText + newline);
            }

            if (!"".equals(language3Content04FontColour)) {
                proPrevWin.appendText("language3Content04FontColour" + colon + language3Content04FontColour + newline);
            }

            if (!"".equals(language3Content04FontStyle)) {
                proPrevWin.appendText("language3Content04FontStyle" + colon + language3Content04FontStyle + newline);
            }

            if (!"".equals(language3Content04DisplaySelectable)) {
                proPrevWin.appendText("language3Content04DisplaySelectable" + colon + language3Content04DisplaySelectable + newline);
            }
        }
    }

    /**
     * updateNumOfLang() updates/shows/hides some values/TextFields/CheckBoxes
     * etc. according to the number of languages selected in the
     * <b>numOfLangCmbBox</b> ComboBox.
     */
    public void updateNumOfLang() {
        if (numOfLangCmbBox.getSelectedItem().equals("2")) {
            // textareas combo boxes...
            langDispTextTF3.setVisible(false);
            langPostfixCB3.setVisible(false);
            langIsSearchCB3.setVisible(false);
            langGenIndCB3.setVisible(false);
            langHasSepDictCB3.setVisible(false);
            langExpSplitStringTF3.setVisible(false);
            langNormCB3.setVisible(false);
            langDictUpdateCB3.setVisible(false);
            langIndNumSrcEntTF3.setVisible(false);

            // forcing the comboboxes to assign the
            // default selected value to the variable
            language1FilePostfix = langPostfixCB1.getSelectedItem().toString();
            language2FilePostfix = langPostfixCB2.getSelectedItem().toString();

            // labels
            langDispTextLB3.setVisible(false);
            langFilePostfixLB3.setVisible(false);
            langExpSplitStringLB3.setVisible(false);
            langIndNumSrcEntLB3.setVisible(false);
            langDictUpdateLB3.setVisible(false);
            langNormLB3.setVisible(false);

            // adaptiong the size of the screen.
/*            if (DfMCreatorMain.LookAndFeel != null) {
             switch (DfMCreatorMain.LookAndFeel) {
             case (gtk):
             this.setSize(GTk_width, GTk_height_2_Langs);
             break;
             case (nimbus):
             this.setSize(Nimbus_width, Nimbus_height_2_Langs);
             break;
             case (metal):
             this.setSize(Metal_width, Metal_height_2_Langs);
             break;
             case (motif):
             this.setSize(Motif_width, Motif_height_2_Langs);
             break;
             default:
             this.setSize(Default_width, Default_height_2_Langs);
             break;
             }
             } */
        } else if (numOfLangCmbBox.getSelectedItem().equals("3")) {
            // textareas combo boxes...
            langDispTextTF3.setVisible(true);
            langPostfixCB3.setVisible(true);
            langIsSearchCB3.setVisible(true);
            langGenIndCB3.setVisible(true);
            langHasSepDictCB3.setVisible(true);
            langExpSplitStringTF3.setVisible(true);
            langNormCB3.setVisible(true);
            langDictUpdateCB3.setVisible(true);
            langIndNumSrcEntTF3.setVisible(true);

            // forcing the comboboxes to assign the
            // default selected value to the variable
            language3FilePostfix = langPostfixCB3.getSelectedItem().toString();

            // labels
            langDispTextLB3.setVisible(true);
            langFilePostfixLB3.setVisible(true);
            langExpSplitStringLB3.setVisible(true);
            langIndNumSrcEntLB3.setVisible(true);
            langDictUpdateLB3.setVisible(true);
            langNormLB3.setVisible(true);

            // adaptiong the size of the screen.
/*            if (DfMCreatorMain.LookAndFeel != null) {
             switch (DfMCreatorMain.LookAndFeel) {
             case (gtk):
             this.setSize(GTk_width, GTk_height_3_Langs);
             break;
             case (nimbus):
             this.setSize(Nimbus_width, Nimbus_height_3_Langs);
             break;
             case (metal):
             this.setSize(Metal_width, Metal_height_3_Langs);
             break;
             case (motif):
             this.setSize(Motif_width, Motif_height_3_Langs);
             break;
             default:
             this.setSize(Default_width, Default_height_3_Langs);
             break;
             }
             } */
        }
    }
    public static final String RequiredArgsAbsentErrorMsg = I18n.tr("oneOrMore.dfmPropCreate");
    public static final String openPropFileErrorMsg = I18n.tr("errorOpeningPropFile.dfmPropCreate");
    public static final String NotThePropFileErrorMsg = I18n.tr("badPropsFileName.dfmPropCreate");
    public static final String fileCantBeOpenedErrorMsg = I18n.tr("fileReadError.dfmPropCreate");
    private static final String samplePropertyFile = "infoText: Freedict (English - German), http://www.freedict.de\n"
            + "dictionaryAbbreviation: Freedict(Eng-Ger)\n"
            + "numberOfAvailableLanguages: 2\n"
            + "\n"
            + "language1DisplayText: English\n"
            + "language2DisplayText: German\n"
            + "language1FilePostfix: Eng\n"
            + "language2FilePostfix: Ger\n"
            + "\n"
            + "language1IsSearchable: true\n"
            + "language2IsSearchable: true\n"
            + "language1GenerateIndex: true\n"
            + "language2GenerateIndex: true\n"
            + "\n"
            + "language1HasSeparateDictionaryFile: false\n"
            + "language2HasSeparateDictionaryFile: false\n"
            + "\n"
            + "dictionaryGenerationSeparatorCharacter: '\\t'\n"
            + "indexFileSeparationCharacter: '\\t'\n"
            + "searchListFileSeparationCharacter: '\\t'\n"
            + "dictionaryFileSeparationCharacter: '\\t'\n"
            + "\n"
            + "dictionaryGenerationOmitParFromIndex: true\n"
            + "dictionaryGenerationInputCharEncoding: UTF-8\n"
            + "indexCharEncoding: UTF-8\n"
            + "searchListCharEncoding: UTF-8\n"
            + "dictionaryCharEncoding: UTF-8\n"
            + "\n"
            + "language1DictionaryUpdateClassName: de.kugihan.dictionaryformids.dictgen.\ndictionaryupdate.DictionaryUpdateFreedictDeuEngGer\n"
            + "language1NormationClassName: de.kugihan.dictionaryformids.translation.\nnormation.Normation.NormationEng\n";

    /**
     * getInputOptionDialog() shows the InputDialog window.
     *
     * @param label
     * @param winTitle
     * @return String <i>S</i>
     */
    public static String getInputOptionDialog(String label, String winTitle) {
        Object[] possibilities = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String s = (String) JOptionPane.showInputDialog(
                null,
                winTitle,
                label + "\n",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "1");
        return s;
    }

    /**
     * showContentPropsCreator() shows the ContentProperties-Creator window.
     */
    private void showContentPropsCreator() {
        ContentPropertiesCreator cpc = ContentPropertiesCreator.getContentPropsCreator();
        cpc.setSize(1020, 600);
        cpc.setLocation(screenSize.width / 2 - cpc.getWidth() / 2,
                screenSize.height / 2 - cpc.getHeight() / 2);

        cpc.updateNumOfContentDec();
        if (openPropsFileForGUIEditFlag) {
            cpc.loadAndEditExistingContentNNProperties();
        }
        cpc.updateNumOfContentDec();
        cpc.setModal(true);
        cpc.setVisible(true);
    }

    /**
     * checkInfoTextContents() checks the content of the InfoText TextField to
     * match it against the regular expression patterns present in the
     * RegexpUtils class in order to make sure the user provided a URL or an
     * email address for the maintainer(s) of the dictionary file that is about
     * to be set up in the empty DictionaryForMIDs.jar/jad files.
     *
     * @throws IllegalArgumentException
     */
    private void checkInfoTextContents() throws IllegalArgumentException {
        String s = infoTextTA.getText();
        boolean v;
        boolean w;

        // checking if infoText contains an email address
        // or a link that will let the users of the generated
        // dictionary to know who's the maintainer of it of
        // how the contact the original authors or something else.
        v = RegexpUtils.validateEmail(s);
        w = RegexpUtils.validateURL(s);

        if (!v && !w) {
            throw new IllegalArgumentException(I18n.tr("badInfoText.dfmPropCreate"));
        }

    }

    private void savePropsFileUnder() {
        try {
            validateValues();
            setValuesInThePropsPreviewWindow();
            proPrevWin.PropFileView.setVisible(false);
            proPrevWin.savePropFile(true);
        } catch (IllegalArgumentException e) {
            DfMCreatorMain.printAnyMsg(RequiredArgsAbsentErrorMsg, I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e + "\n");
        } catch (UnsupportedOperationException ex) {
            DfMCreatorMain.printAnyMsg(PropertiesPreview.canWriteFileMsg, I18n.tr("dirNotAccessible"), JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            DfMCreatorMain.printAnyMsg(PropertiesPreview.internalErrorMsg, I18n.tr("error"), JOptionPane.ERROR_MESSAGE);
        }

    }
}
