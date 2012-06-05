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
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



public class DfMPropCreate extends javax.swing.JFrame {
        
    public static DfMPropCreate getPropWin(){
        return new DfMPropCreate();
    }

    private static final DfMPropPreview ppWin = DfMPropPreview.getPropPreviewWin();
    
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
 
    private final JFileChooser fc = new JFileChooser();
        
    // Some look and feel class names
    public static final String gtk = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
    public static final String nimbus = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
    public static final String metal = "javax.swing.plaf.metal.MetalLookAndFeel";
    public static final String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

    // GTk
    public static final int GTk_height_2_Langs = 670;
    public static final int GTk_height_3_Langs = 685;
    public static final int GTk_width = 1110;

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
    public static final int Default_width = 1200;
    
    
   /*
    * Variables declaration for
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
    private static final String DictionaryUpdateClassNamePrefix = "de.kugihan.dictionaryformids.dictgen.dictionaryupdate";
    private static final String DictionaryNormationClassNamePrefix = "de.kugihan.dictionaryformids.translation.normation.Normation";
    
    // Content Declaration Variables
    public static String language1NumberOfContentDeclarations = "";
    public static String language2NumberOfContentDeclarations = "";
    public static String language3NumberOfContentDeclarations = "";    
    
    // Other Content Variables
    // Language 1
    public static String language1Content1DisplayText = "";
    public static String language1Content1FontColour = "";
    public static String language1Content1FontStyle = "";
    public static String language1Content1DisplaySelectable = "";
    public static String language1Content2DisplayText = "";
    public static String language1Content2FontColour = "";
    public static String language1Content2FontStyle = "";
    public static String language1Content2DisplaySelectable = "";
    public static String language1Content3DisplayText = "";
    public static String language1Content3FontColour = "";
    public static String language1Content3FontStyle = "";
    public static String language1Content3DisplaySelectable = "";
    public static String language1Content4DisplayText = "";
    public static String language1Content4FontColour = "";
    public static String language1Content4FontStyle = "";
    public static String language1Content4DisplaySelectable = "";
    
    /*
    public static String language1Content5DisplayText = "";
    public static String language1Content5FontColour = "";
    public static String language1Content5FontStyle = "";
    public static String language1Content5DisplaySelectable = "";
    */
    
    // Language 2
    public static String language2Content1DisplayText = "";
    public static String language2Content1FontColour = "";
    public static String language2Content1FontStyle = "";
    public static String language2Content1DisplaySelectable = "";
    public static String language2Content2DisplayText = "";
    public static String language2Content2FontColour = "";
    public static String language2Content2FontStyle = "";
    public static String language2Content2DisplaySelectable = "";
    public static String language2Content3DisplayText = "";
    public static String language2Content3FontColour = "";
    public static String language2Content3FontStyle = "";
    public static String language2Content3DisplaySelectable = "";
    public static String language2Content4DisplayText = "";
    public static String language2Content4FontColour = "";
    public static String language2Content4FontStyle = "";
    public static String language2Content4DisplaySelectable = "";
    
    /*
    public static String language2Content5DisplayText = "";
    public static String language2Content5FontColour = "";
    public static String language2Content5FontStyle = "";
    public static String language2Content5DisplaySelectable = "";
    */
    
    // Language 3
    public static String language3Content1DisplayText = "";
    public static String language3Content1FontColour = "";
    public static String language3Content1FontStyle = "";
    public static String language3Content1DisplaySelectable = "";
    public static String language3Content2DisplayText = "";
    public static String language3Content2FontColour = "";
    public static String language3Content2FontStyle = "";
    public static String language3Content2DisplaySelectable = "";
    public static String language3Content3DisplayText = "";
    public static String language3Content3FontColour = "";
    public static String language3Content3FontStyle = "";
    public static String language3Content3DisplaySelectable = "";
    public static String language3Content4DisplayText = "";
    public static String language3Content4FontColour = "";
    public static String language3Content4FontStyle = "";
    public static String language3Content4DisplaySelectable = "";
    
    /*
    public static String language3Content5DisplayText = "";
    public static String language3Content5FontColour = "";
    public static String language3Content5FontStyle = "";
    public static String language3Content5DisplaySelectable = "";
    */
    

    /**
     * Creates new form DfMPropCreate
     */
    public DfMPropCreate() {
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
        jLabel1 = new javax.swing.JLabel();
        dictGenInputCharEncodingCB = new javax.swing.JComboBox();
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
        jLabel43 = new javax.swing.JLabel();
        dictGenOmitParCB = new javax.swing.JCheckBox();
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
        jLabel35 = new javax.swing.JLabel();
        minNumEntPerIndFileSL = new javax.swing.JSlider();
        minNumEntPerDictFileSL = new javax.swing.JSlider();
        jLabel34 = new javax.swing.JLabel();
        lab1 = new javax.swing.JLabel();
        lab2 = new javax.swing.JLabel();
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
        jPanel14 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
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

        langDictUpdateCB1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "DictionaryUpdate", "DictionaryUpdateCEDICTChi", "DictionaryUpdateEDICTJpn", "DictionaryUpdateEngDef", "DictionaryUpdateEngDefPronunciation", "DictionaryUpdateEpo", "DictionaryUpdateFraDef", "DictionaryUpdateFreedictDeuEngGer", "DictionaryUpdateFreedictEngHin", "DictionaryUpdateHanDeDictChi", "DictionaryUpdateHanDeDictGer", "DictionaryUpdateIDP", "DictionaryUpdateIDPEng", "DictionaryUpdateIDPGer", "DictionaryUpdateIDPIta", "DictionaryUpdateIDPLat", "DictionaryUpdateIDPSpa", "DictionaryUpdateLib", "DictionaryUpdateMuellerRus", "DictionaryUpdatePartialIndex", "DictionaryUpdateThaiNIUEng", "DictionaryUpdateWordnetDefinition" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(langDictUpdateCB1, gridBagConstraints);

        langDictUpdateCB2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "DictionaryUpdate", "DictionaryUpdateCEDICTChi", "DictionaryUpdateEDICTJpn", "DictionaryUpdateEngDef", "DictionaryUpdateEngDefPronunciation", "DictionaryUpdateEpo", "DictionaryUpdateFraDef", "DictionaryUpdateFreedictDeuEngGer", "DictionaryUpdateFreedictEngHin", "DictionaryUpdateHanDeDictChi", "DictionaryUpdateHanDeDictGer", "DictionaryUpdateIDP", "DictionaryUpdateIDPEng", "DictionaryUpdateIDPGer", "DictionaryUpdateIDPIta", "DictionaryUpdateIDPLat", "DictionaryUpdateIDPSpa", "DictionaryUpdateLib", "DictionaryUpdateMuellerRus", "DictionaryUpdatePartialIndex", "DictionaryUpdateThaiNIUEng", "DictionaryUpdateWordnetDefinition" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(langDictUpdateCB2, gridBagConstraints);

        langDictUpdateCB3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "DictionaryUpdate", "DictionaryUpdateCEDICTChi", "DictionaryUpdateEDICTJpn", "DictionaryUpdateEngDef", "DictionaryUpdateEngDefPronunciation", "DictionaryUpdateEpo", "DictionaryUpdateFraDef", "DictionaryUpdateFreedictDeuEngGer", "DictionaryUpdateFreedictEngHin", "DictionaryUpdateHanDeDictChi", "DictionaryUpdateHanDeDictGer", "DictionaryUpdateIDP", "DictionaryUpdateIDPEng", "DictionaryUpdateIDPGer", "DictionaryUpdateIDPIta", "DictionaryUpdateIDPLat", "DictionaryUpdateIDPSpa", "DictionaryUpdateLib", "DictionaryUpdateMuellerRus", "DictionaryUpdatePartialIndex", "DictionaryUpdateThaiNIUEng", "DictionaryUpdateWordnetDefinition" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel11.add(langDictUpdateCB3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
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

        jLabel1.setText("dictGenInputCharEncoding");
        jLabel1.setToolTipText(I18n.tr("dictgenInputCharEnc.tooltipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel6.add(jLabel1, gridBagConstraints);

        dictGenInputCharEncodingCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UTF-8", "ISO-8859-1", "US-ASCII" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel6.add(dictGenInputCharEncodingCB, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
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

        langNormCB1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "NormationBul", "NormationCyr1", "NormationCyr2", "NormationEng", "NormationEng2", "NormationEpo", "NormationFil", "NormationGer", "NormationJpn", "NormationLat", "NormationLib", "NormationNor", "NormationRus", "NormationRus2", "NormationRusC", "NormationUkr", "NormationUkrC", "NormationVie" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(langNormCB1, gridBagConstraints);

        langNormCB2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "NormationBul", "NormationCyr1", "NormationCyr2", "NormationEng", "NormationEng2", "NormationEpo", "NormationFil", "NormationGer", "NormationJpn", "NormationLat", "NormationLib", "NormationNor", "NormationRus", "NormationRus2", "NormationRusC", "NormationUkr", "NormationUkrC", "NormationVie" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(langNormCB2, gridBagConstraints);

        langNormCB3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "NormationBul", "NormationCyr1", "NormationCyr2", "NormationEng", "NormationEng2", "NormationEpo", "NormationFil", "NormationGer", "NormationJpn", "NormationLat", "NormationLib", "NormationNor", "NormationRus", "NormationRus2", "NormationRusC", "NormationUkr", "NormationUkrC", "NormationVie" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(langNormCB3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
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

        jLabel43.setText("dictGenOmitParFromIndex");
        jLabel43.setToolTipText(I18n.tr("dictGenOmitPar.tooltiptext")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel5.add(jLabel43, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel5.add(dictGenOmitParCB, gridBagConstraints);

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

        langPostfixCB1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ara", "Cze", "Deu", "Eng", "Fra", "Gla", "Hin", "Hun", "Iri", "Ita", "Jpn", "Lat", "Nld", "Por", "Rom", "Rus", "Scr", "Slo", "Spa", "Swa", "Swe", "Tur", "Wel", "CHOOSE MY OWN" }));
        langPostfixCB1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                langPostfixCB1ItemStateChanged(evt);
            }
        });
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

        langPostfixCB2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ara", "Cze", "Deu", "Eng", "Fra", "Gla", "Hin", "Hun", "Iri", "Ita", "Jpn", "Lat", "Nld", "Por", "Rom", "Rus", "Scr", "Slo", "Spa", "Swa", "Swe", "Tur", "Wel", "CHOOSE MY OWN" }));
        langPostfixCB2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                langPostfixCB2ItemStateChanged(evt);
            }
        });
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

        langPostfixCB3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ara", "Cze", "Deu", "Eng", "Fra", "Gla", "Hin", "Hun", "Iri", "Ita", "Jpn", "Lat", "Nld", "Por", "Rom", "Rus", "Scr", "Slo", "Spa", "Swa", "Swe", "Tur", "Wel", "CHOOSE MY OWN" }));
        langPostfixCB3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                langPostfixCB3ItemStateChanged(evt);
            }
        });
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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel3, gridBagConstraints);

        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(242, 1, 1), 2));
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

        createPropFile.setText(I18n.tr("createThePros.dfmPropCreate")); // NOI18N
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

        editExistingBT.setText(I18n.tr("editExisting.dfmPropCreate")); // NOI18N
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
        gridBagConstraints.gridy = 1;
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

        jLabel22.setText("dictGen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel8.add(jLabel22, gridBagConstraints);

        dictGenSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tab", "Carriage Return", "Form Feed", "Comma", "Semi-Colon", "Colon" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(dictGenSepCharCB, gridBagConstraints);

        searchListSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tab", "Carriage Return", "Form Feed", "Comma", "Semi-Colon", "Colon" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(searchListSepCharCB, gridBagConstraints);

        indexFileSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tab", "Carriage Return", "Form Feed", "Comma", "Semi-Colon", "Colon" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel8.add(indexFileSepCharCB, gridBagConstraints);

        dictFileSepCharCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tab", "Carriage Return", "Form Feed", "Comma", "Semi-Colon", "Colon" }));
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

        jLabel28.setText("dictFile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel8.add(jLabel28, gridBagConstraints);

        jLabel31.setText("searchList");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel8.add(jLabel31, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel7, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        listMaxSizeSL.setMajorTickSpacing(5);
        listMaxSizeSL.setMaximum(5000);
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

        lab3.setText(I18n.tr("value")); // NOI18N
        lab3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel1.add(lab3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel2, gridBagConstraints);

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.setLayout(new java.awt.GridBagLayout());

        indexMaxSizeSL.setMajorTickSpacing(5);
        indexMaxSizeSL.setMaximum(5000);
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
        dictMaxSizeSL.setMaximum(5000);
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

        lab4.setText(I18n.tr("value")); // NOI18N
        lab4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel15.add(lab4, gridBagConstraints);

        lab5.setText(I18n.tr("value")); // NOI18N
        lab5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel15.add(lab5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
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

        jLabel35.setText("<html>dictGenMinNumber<br>OfEntriesPerIndexFile</html>");
        jLabel35.setToolTipText(I18n.tr("dictGenMinNum.toolTipText.dfmPropCreate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel16.add(jLabel35, gridBagConstraints);

        minNumEntPerIndFileSL.setMajorTickSpacing(5);
        minNumEntPerIndFileSL.setMaximum(5000);
        minNumEntPerIndFileSL.setMinorTickSpacing(1);
        minNumEntPerIndFileSL.setValue(0);
        minNumEntPerIndFileSL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                minNumEntPerIndFileSLStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel16.add(minNumEntPerIndFileSL, gridBagConstraints);

        minNumEntPerDictFileSL.setMajorTickSpacing(5);
        minNumEntPerDictFileSL.setMaximum(5000);
        minNumEntPerDictFileSL.setMinorTickSpacing(1);
        minNumEntPerDictFileSL.setValue(0);
        minNumEntPerDictFileSL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                minNumEntPerDictFileSLStateChanged(evt);
            }
        });
        minNumEntPerDictFileSL.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                minNumEntPerDictFileSLPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
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

        lab1.setText(I18n.tr("value")); // NOI18N
        lab1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel16.add(lab1, gridBagConstraints);

        lab2.setText(I18n.tr("value")); // NOI18N
        lab2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel16.add(lab2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
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
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel13, gridBagConstraints);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(new java.awt.GridBagLayout());

        infoTextTA.setColumns(20);
        infoTextTA.setRows(5);
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
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        mainContainer.add(jPanel9, gridBagConstraints);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel14.setLayout(new java.awt.GridBagLayout());

        jButton1.setText(I18n.tr("contentNNPropsEditorButton.dfmPropCreate")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel14.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainContainer.add(jPanel14, gridBagConstraints);

        jScrollPane2.setViewportView(mainContainer);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jMenu1.setText(I18n.tr("file.dfmPropCreate")); // NOI18N

        jMenuItem1.setText(I18n.tr("creatTheProps.dfmPropCreate")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText(I18n.tr("editExisting.dfmPropCreate")); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText(I18n.tr("resetSettings.dfmPropCreate")); // NOI18N
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
        viewAndOrEdit();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        editExistingPropFile();
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
        JOptionPane.showMessageDialog(ppWin, I18n.tr("aboutWinMsg.dfmPropCreate")
                , I18n.tr("aboutWinTitle.dfmPropCreate"), JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void langPostfixCB1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_langPostfixCB1ItemStateChanged

   }//GEN-LAST:event_langPostfixCB1ItemStateChanged

    private void langPostfixCB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langPostfixCB1ActionPerformed
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
    }//GEN-LAST:event_langPostfixCB1ActionPerformed

    private void langPostfixCB2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_langPostfixCB2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_langPostfixCB2ItemStateChanged

    private void langPostfixCB2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langPostfixCB2ActionPerformed
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
    }//GEN-LAST:event_langPostfixCB2ActionPerformed

    private void langPostfixCB3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_langPostfixCB3ItemStateChanged

   }//GEN-LAST:event_langPostfixCB3ItemStateChanged

    private void langPostfixCB3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langPostfixCB3ActionPerformed
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
    }//GEN-LAST:event_langPostfixCB3ActionPerformed

    private void additionalInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_additionalInfoActionPerformed
        showMoreInfo();
    }//GEN-LAST:event_additionalInfoActionPerformed

    private void samplePropFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_samplePropFileActionPerformed
        showSamplePropFile();
    }//GEN-LAST:event_samplePropFileActionPerformed

    private void createPropFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPropFileActionPerformed
        viewAndOrEdit();
    }//GEN-LAST:event_createPropFileActionPerformed

    private void resetSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSettingActionPerformed
        resetAll();
    }//GEN-LAST:event_resetSettingActionPerformed

    private void editExistingBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExistingBTActionPerformed
        editExistingPropFile();
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

    private void minNumEntPerDictFileSLPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_minNumEntPerDictFileSLPropertyChange

   }//GEN-LAST:event_minNumEntPerDictFileSLPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        showContentPropsEditor();
    }//GEN-LAST:event_jButton1ActionPerformed


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
              //  new DfMPropCreate().setVisible(true);
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
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
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
     * showInputDialog
     * Shows an input dialog and returns its content.
     * @param winTitle; the title of the window.
     * @return the value entered by the user.
     */
    private static String showInputDialog(String labelText){
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
     * filterSepChar
     * Processes the selected item of one of the four
     * separatorCharacter comboboxes and returns
     * the actual filterSepChar corresponding to
     * the selection.
     * @param combobox
     * @return separator character selected
     */
    private String filterSepChar(javax.swing.JComboBox combobox){
        int i = combobox.getSelectedIndex();
        String value;
        switch (i) {
            case 0:
                value = "'\\t'";
                break;
            case 1:
                value ="'\\r'";
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
                value = "'\\t'";
                break;
        }
        return value;
    }
    
    private void resetAll(){
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
    }
    
    private void showMoreInfo(){
        JOptionPane.showMessageDialog(ppWin, moreInfo, I18n.tr("additionalInfo.dfmPropCreate"),
                                                       JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void editExistingPropFile(){
        try {
            if (openPropFile()){
            	ppWin.editButton.setSelected(true);
            	ppWin.editButton.setText(I18n.tr("doNotEdit.dfmPropCreate"));
                ppWin.PropFileView.setVisible(true);
                ppWin.propTextPane.setCaretPosition(0);
            }
        } catch (UnsupportedOperationException ex){
            DfMCreatorMain.printAnyMsg(openPropFileErrorMsg, I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            DfMCreatorMain.printAnyMsg(NotThePropFileErrorMsg, I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);            
        }
        
    }
    
    public void showSamplePropFile(){
        JOptionPane.showMessageDialog(ppWin, samplePropertyFile, I18n.tr("samplePropsWinTitle.dfmPropCreate"),
                                                                JOptionPane.PLAIN_MESSAGE);
    }
    
    public boolean openPropFile() throws UnsupportedOperationException,
                                      IllegalArgumentException {
        boolean fileChose;
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            if (!file.exists() || !file.canRead()){
                throw new UnsupportedOperationException();
            }
            String propFileName = file.getName();
            if (!propFileName.equals(DfMPropPreview.PROPERTY_FILE_NAME)){
                throw new IllegalArgumentException();
            }
            ppWin.propTextPane.setText("");
            try {
                try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                    String l;
                    while ((l = in.readLine()) != null){
                        ppWin.appendText(l);
                        ppWin.appendText("\n");
                        ppWin.propTextPane.setEditable(true);
                        ppWin.propTextPane.setBackground(new java.awt.Color(255, 255, 190));
                    }
                }
            } catch (IOException ex){
                DfMCreatorMain.printAnyMsg(fileCantBeOpenedErrorMsg, I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
                System.out.println(ex + "\n");
            }
            fileChose = true;
        } else {
            fileChose = false;
        }
        return fileChose;
    }
    

    public void viewAndOrEdit(){        
        try {
            validateValues();
            setValuesInPropTextArea();
            ppWin.propTextPane.setCaretPosition(0);
            ppWin.editButton.setSelected(false);
            ppWin.editButton.setText(I18n.tr("edit.dfmPropCreate"));
            ppWin.PropFileView.setVisible(true);
        } catch (IllegalArgumentException e){
            DfMCreatorMain.printAnyMsg(RequiredArgsAbsentErrorMsg, I18n.tr("error.dfmPropCreate"), JOptionPane.ERROR_MESSAGE);
            System.out.println(e + "\n");
        }
    }

    private void validateValues() {
	if ("".equals(infoTextTA.getText()) || "".equals(dictAbbrevTF.getText()) ||
            "".equals(langDispTextTF1.getText()) || "".equals(langDispTextTF2.getText()) ||
	    "".equals(langPostfixCB1.getSelectedItem().toString()) || "".equals(langPostfixCB2.getSelectedItem().toString())){
            throw new IllegalArgumentException();
	}
        
	if (numOfLangCmbBox.getSelectedItem().equals("3")){
            if ("".equals(langDispTextTF3.getText())  || "".equals(langPostfixCB3.getSelectedItem().toString())){
                throw new IllegalArgumentException();
            }
	}
        
        try {
            checkInfoTextContents();
        } catch (IllegalArgumentException e){
            DfMCreatorMain.printAnyMsg(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
            
        
	
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
        

	if (dictGenOmitParCB.isSelected()){
        	dictionaryGenerationOmitParFromIndex = "true";
	} else {
		dictionaryGenerationOmitParFromIndex = "false";
	}

	if (listMaxSizeSL.getValue() != 0){
		searchListFileMaxSize = String.valueOf(listMaxSizeSL.getValue());
	}
	if (indexMaxSizeSL.getValue() != 0){
		indexFileMaxSize = String.valueOf(indexMaxSizeSL.getValue());
	}
	if (dictMaxSizeSL.getValue() != 0){
		dictionaryFileMaxSize = String.valueOf(dictMaxSizeSL.getValue());
	}
	if (minNumEntPerDictFileSL.getValue() != 0){
		dictionaryGenerationMinNumberOfEntriesPerDictionaryFile = String.valueOf(minNumEntPerDictFileSL.getValue());
	}
	if (minNumEntPerIndFileSL.getValue() != 0){
		dictionaryGenerationMinNumberOfEntriesPerIndexFile = String.valueOf(minNumEntPerIndFileSL.getValue());
	}
	
	if (langIsSearchCB1.isSelected()){
		language1IsSearchable = "true";
	} else {
		language1IsSearchable = "false";
	}
	if (langGenIndCB1.isSelected()){
		language1GenerateIndex = "true";
	} else {
		language1GenerateIndex = "false";
	}
	if (langGenIndCB1.isSelected()){
		language1HasSeparateDictionaryFile = "true";
	} else {
		language1HasSeparateDictionaryFile = "false";
	}
	
	language1DisplayText = langDispTextTF1.getText();
	//language1FilePostfix = langPostfixTF1.getText();
	dictionaryGenerationLanguage1ExpressionSplitString = langExpSplitStringTF1.getText();
	language1DictionaryUpdateClassName = DictionaryUpdateClassNamePrefix + "." + langDictUpdateCB1.getSelectedItem().toString();
        
        if (language1DictionaryUpdateClassName.endsWith(I18n.tr("none.dfmPropCreate"))){
            language1DictionaryUpdateClassName = "";
        }
        
	language1NormationClassName = DictionaryNormationClassNamePrefix + "." + langNormCB1.getSelectedItem().toString();
        
        if (language1NormationClassName.endsWith(I18n.tr("none.dfmPropCreate"))){
            language1NormationClassName = "";
        }
        
	language1IndexNumberOfSourceEntries = langIndNumSrcEntTF1.getText();
	//language1ContentNN_properties
        
	if (langIsSearchCB2.isSelected()){
		language2IsSearchable = "true";
	} else {
		language2IsSearchable = "false";
	}
	if (langGenIndCB2.isSelected()){
		language2GenerateIndex = "true";
	} else {
		language2GenerateIndex = "false";
	}
	if (langGenIndCB2.isSelected()){
		language2HasSeparateDictionaryFile = "true";
	} else {
		language2HasSeparateDictionaryFile = "false";
	}
	
	language2DisplayText = langDispTextTF2.getText();
	//language2FilePostfix = langPostfixTF2.getText();
	dictionaryGenerationLanguage2ExpressionSplitString = langExpSplitStringTF2.getText();
	language2DictionaryUpdateClassName = DictionaryUpdateClassNamePrefix + "." + langDictUpdateCB2.getSelectedItem().toString();
        
        if (language2DictionaryUpdateClassName.endsWith(I18n.tr("none.dfmPropCreate"))){
            language2DictionaryUpdateClassName = "";
        }
        
	language2NormationClassName = DictionaryNormationClassNamePrefix + "." + langNormCB2.getSelectedItem().toString();
        
        if (language2NormationClassName.endsWith(I18n.tr("none.dfmPropCreate"))){
            language2NormationClassName = "";
        }
        
	language2IndexNumberOfSourceEntries = langIndNumSrcEntTF2.getText();
	//language2ContentNN_properties

        if (numOfLangCmbBox.getSelectedItem().equals("3")) {
            if (langIsSearchCB3.isSelected()){
                    language3IsSearchable = "true";
                } else {
        	        language3IsSearchable = "false";
	        }
	    if (langGenIndCB3.isSelected()){
		    language3GenerateIndex = "true";
	    } else {
		    language3GenerateIndex = "false";
	    }
	    if (langGenIndCB3.isSelected()){
		    language3HasSeparateDictionaryFile = "true";
	    } else {
		    language3HasSeparateDictionaryFile = "false";
	    }
		
	    language3DisplayText = langDispTextTF3.getText();
	    //language3FilePostfix = langPostfixTF3.getText();
	    dictionaryGenerationLanguage3ExpressionSplitString = langExpSplitStringTF3.getText();
	    language3DictionaryUpdateClassName = DictionaryUpdateClassNamePrefix + "." + langDictUpdateCB3.getSelectedItem().toString();
            
            if (language3DictionaryUpdateClassName.endsWith(I18n.tr("none.dfmPropCreate"))){
                language3DictionaryUpdateClassName = "";
            }
            
	    language3NormationClassName = DictionaryNormationClassNamePrefix + "." + langNormCB3.getSelectedItem().toString();
            
            if (language3NormationClassName.endsWith(I18n.tr("none.dfmPropCreate"))){
                language3NormationClassName = "";
            }
            
	    language3IndexNumberOfSourceEntries = langIndNumSrcEntTF3.getText();
	    //language3ContentNN_properties
        }       
    }
    
    private void setValuesInPropTextArea(){
        // passing values to the textarea for preview before saving.
        // General values (general to all languages)
        ppWin.propTextPane.setText("");
        ppWin.appendText("infoText" + colon + infoText + newline + newline);
        ppWin.appendText("dictionaryAbbreviation" + colon + dictionaryAbbreviation + newline);
        ppWin.appendText("numberOfAvailableLanguages" + colon + numberOfAvailableLanguages + newline + newline);
        
        ppWin.appendText("indexFileSeparationCharacter" + colon + indexFileSeparationCharacter + newline);
	ppWin.appendText("searchListFileSeparationCharacter" + colon + searchListFileSeparationCharacter + newline);
	ppWin.appendText("dictionaryFileSeparationCharacter" + colon + dictionaryFileSeparationCharacter + newline);
	ppWin.appendText("dictionaryGenerationSeparatorCharacter" + colon + dictionaryGenerationSeparatorCharacter + newline + newline);       
        
        if (!"".equals(dictionaryGenerationInputCharEncoding)){
            ppWin.appendText("dictionaryGenerationInputCharEncoding" + colon + dictionaryGenerationInputCharEncoding + newline);
        }
        if (!"".equals(indexCharEncoding)){
            ppWin.appendText("indexCharEncoding" + colon + indexCharEncoding + newline);
        }
        if (!"".equals(searchListCharEncoding)){
            ppWin.appendText("searchListCharEncoding" + colon + searchListCharEncoding + newline);
        }
        if (!"".equals(dictionaryCharEncoding)){
            ppWin.appendText("dictionaryCharEncoding" + colon + dictionaryCharEncoding + newline + newline);
        }
        if (!"".equals(searchListFileMaxSize)){
            ppWin.appendText("searchListFileMaxSize" + colon + searchListFileMaxSize + newline);
        }
        if (!"".equals(indexFileMaxSize)){
            ppWin.appendText("indexFileMaxSize" + colon + indexFileMaxSize + newline);
        }
        if (!"".equals(dictionaryFileMaxSize)){
            ppWin.appendText("dictionaryFileMaxSize" + colon + dictionaryFileMaxSize + newline + newline);
        }
        if (!"".equals(dictionaryGenerationMinNumberOfEntriesPerDictionaryFile)){
            ppWin.appendText("dictionaryGenerationMinNumberOfEntriesPerDictionaryFile" + colon + 
                                                                  dictionaryGenerationMinNumberOfEntriesPerDictionaryFile + newline);
        }
        if (!"".equals(dictionaryGenerationMinNumberOfEntriesPerIndexFile)){
            ppWin.appendText("dictionaryGenerationMinNumberOfEntriesPerIndexFile" + colon + 
                                                                  dictionaryGenerationMinNumberOfEntriesPerIndexFile + newline + newline);
        }
        ppWin.appendText("dictionaryGenerationOmitParFromIndex" + colon +  dictionaryGenerationOmitParFromIndex + newline + newline);
        
        // language 1 non optional values.
        ppWin.appendText("language1DisplayText" + colon + language1DisplayText + newline);
        ppWin.appendText("language1FilePostfix" + colon + language1FilePostfix + newline + newline);
        
        // language 1 boolean values.
        ppWin.appendText("language1IsSearchable" + colon +  language1IsSearchable + newline);
        ppWin.appendText("language1GenerateIndex" + colon + language1GenerateIndex + newline);
        ppWin.appendText("language1HasSeparateDictionaryFile" + colon + language1HasSeparateDictionaryFile + newline + newline);
        
        // other values.
        if (!"".equals(dictionaryGenerationLanguage1ExpressionSplitString)){
            ppWin.appendText("dictionaryGenerationLanguage1ExpressionSplitString" + colon + 
                                                                dictionaryGenerationLanguage1ExpressionSplitString + newline);
        }
        if (!"".equals(language1IndexNumberOfSourceEntries)){
            ppWin.appendText("language1IndexNumberOfSourceEntries" + colon + language1IndexNumberOfSourceEntries + newline);
        }
        if (!"".equals(language1DictionaryUpdateClassName)){
            ppWin.appendText("language1DictionaryUpdateClassName" + colon + language1DictionaryUpdateClassName + newline);
        }
        if (!"".equals(language1NormationClassName)){
            ppWin.appendText("language1NormationClassName" + colon + language1NormationClassName + newline);
        }
        
        // language 2 non optional values.
        ppWin.appendText("language2DisplayText" + colon + language2DisplayText + newline);
        ppWin.appendText("language2FilePostfix" + colon + language2FilePostfix + newline + newline);
        
        // language 2 boolean values.
        ppWin.appendText("language2IsSearchable" + colon + language2IsSearchable + newline);
        ppWin.appendText("language2GenerateIndex" + colon + language2GenerateIndex + newline);
        ppWin.appendText("language2HasSeparateDictionaryFile" + colon + language2HasSeparateDictionaryFile + newline + newline);
        
        // other values.
        if (!"".equals(dictionaryGenerationLanguage2ExpressionSplitString)){
            ppWin.appendText("dictionaryGenerationLanguage2ExpressionSplitString" + colon + 
                                                                dictionaryGenerationLanguage2ExpressionSplitString + newline);
        }
        if (!"".equals(language2IndexNumberOfSourceEntries)){
            ppWin.appendText("language2IndexNumberOfSourceEntries" + colon + language2IndexNumberOfSourceEntries + newline);
        }
        if (!"".equals(language2DictionaryUpdateClassName)){
            ppWin.appendText("language2DictionaryUpdateClassName" + colon + language2DictionaryUpdateClassName + newline);
        }
        if (!"".equals(language2NormationClassName)){
            ppWin.appendText("language2NormationClassName" + colon + language2NormationClassName + newline);
        }
        
        // Number of content declaration variables for language 1 and 2
        if (!"".equals(language1NumberOfContentDeclarations)){
            ppWin.appendText("language1NumberOfContentDeclarations" + colon + language1NumberOfContentDeclarations + newline);
        }

        if (!"".equals(language2NumberOfContentDeclarations)){
            ppWin.appendText("language2NumberOfContentDeclarations" + colon + language2NumberOfContentDeclarations + newline);
        }

        // Langage-1 content variables
        if (!"".equals(language1Content1DisplayText)){
            ppWin.appendText("language1Content1DisplayText" + colon + language1Content1DisplayText + newline);
        }

        if (!"".equals(language1Content1FontColour)){
            ppWin.appendText("language1Content1FontColour" + colon + language1Content1FontColour + newline);
        }

        if (!"".equals(language1Content1FontStyle)){
            ppWin.appendText("language1Content1FontStyle" + colon + language1Content1FontStyle + newline);
        }

        if (!"".equals(language1Content1DisplaySelectable)){
            ppWin.appendText("language1Content1DisplaySelectable" + colon + language1Content1DisplaySelectable + newline);
        }

        if (!"".equals(language1Content2DisplayText)){
            ppWin.appendText("language1Content2DisplayText" + colon + language1Content2DisplayText + newline);
        }

        if (!"".equals(language1Content2FontColour)){
            ppWin.appendText("language1Content2FontColour" + colon + language1Content2FontColour + newline);
        }

        if (!"".equals(language1Content2FontStyle)){
            ppWin.appendText("language1Content2FontStyle" + colon + language1Content2FontStyle + newline);
        }

        if (!"".equals(language1Content2DisplaySelectable)){
            ppWin.appendText("language1Content2DisplaySelectable" + colon + language1Content2DisplaySelectable + newline);
        }

        if (!"".equals(language1Content3DisplayText)){
            ppWin.appendText("language1Content3DisplayText" + colon + language1Content3DisplayText + newline);
        }

        if (!"".equals(language1Content3FontColour)){
            ppWin.appendText("language1Content3FontColour" + colon + language1Content3FontColour + newline);
        }

        if (!"".equals(language1Content3FontStyle)){
            ppWin.appendText("language1Content3FontStyle" + colon + language1Content3FontStyle + newline);
        }

        if (!"".equals(language1Content3DisplaySelectable)){
            ppWin.appendText("language1Content3DisplaySelectable" + colon + language1Content3DisplaySelectable + newline);
        }

        if (!"".equals(language1Content4DisplayText)){
            ppWin.appendText("language1Content4DisplayText" + colon + language1Content4DisplayText + newline);
        }

        if (!"".equals(language1Content4FontColour)){
            ppWin.appendText("language1Content4FontColour" + colon + language1Content4FontColour + newline);
        }

        if (!"".equals(language1Content4FontStyle)){
            ppWin.appendText("language1Content4FontStyle" + colon + language1Content4FontStyle + newline);
        }

        if (!"".equals(language1Content4DisplaySelectable)){
            ppWin.appendText("language1Content4DisplaySelectable" + colon + language1Content4DisplaySelectable + newline + newline);
        }

        // Langage-2 content variables
        if (!"".equals(language2Content1DisplayText)){
            ppWin.appendText("language2Content1DisplayText" + colon + language2Content1DisplayText + newline);
        }

        if (!"".equals(language2Content1FontColour)){
            ppWin.appendText("language2Content1FontColour" + colon + language2Content1FontColour + newline);
        }

        if (!"".equals(language2Content1FontStyle)){
            ppWin.appendText("language2Content1FontStyle" + colon + language2Content1FontStyle + newline);
        }

        if (!"".equals(language2Content1DisplaySelectable)){
            ppWin.appendText("language2Content1DisplaySelectable" + colon + language2Content1DisplaySelectable + newline);
        }

        if (!"".equals(language2Content2DisplayText)){
            ppWin.appendText("language2Content2DisplayText" + colon + language2Content2DisplayText + newline);
        }

        if (!"".equals(language2Content2FontColour)){
            ppWin.appendText("language2Content2FontColour" + colon + language2Content2FontColour + newline);
        }

        if (!"".equals(language2Content2FontStyle)){
            ppWin.appendText("language2Content2FontStyle" + colon + language2Content2FontStyle + newline);
        }

        if (!"".equals(language2Content2DisplaySelectable)){
            ppWin.appendText("language2Content2DisplaySelectable" + colon + language2Content2DisplaySelectable + newline);
        }

        if (!"".equals(language2Content3DisplayText)){
            ppWin.appendText("language2Content3DisplayText" + colon + language2Content3DisplayText + newline);
        }

        if (!"".equals(language2Content3FontColour)){
            ppWin.appendText("language2Content3FontColour" + colon + language2Content3FontColour + newline);
        }

        if (!"".equals(language2Content3FontStyle)){
            ppWin.appendText("language2Content3FontStyle" + colon + language2Content3FontStyle + newline);
        }

        if (!"".equals(language2Content3DisplaySelectable)){
            ppWin.appendText("language2Content3DisplaySelectable" + colon + language2Content3DisplaySelectable + newline);
        }

        if (!"".equals(language2Content4DisplayText)){
            ppWin.appendText("language2Content4DisplayText" + colon + language2Content4DisplayText + newline);
        }

        if (!"".equals(language2Content4FontColour)){
            ppWin.appendText("language2Content4FontColour" + colon + language2Content4FontColour + newline);
        }

        if (!"".equals(language2Content4FontStyle)){
            ppWin.appendText("language2Content4FontStyle" + colon + language2Content4FontStyle + newline);
        }   

        if (!"".equals(language2Content4DisplaySelectable)){
            ppWin.appendText("language2Content4DisplaySelectable" + colon + language2Content4DisplaySelectable + newline + newline);
        }

        
        // language 3 non optional values.
        if (numOfLangCmbBox.getSelectedItem().equals("3")){
            ppWin.appendText("language3DisplayText" + colon + language3DisplayText + newline);
            ppWin.appendText("language3FilePostfix" + colon + language3FilePostfix + newline + newline);
            
            // language 3 boolean values.
            ppWin.appendText("language3IsSearchable" + colon + language3IsSearchable + newline);
            ppWin.appendText("language3GenerateIndex" + colon + language3GenerateIndex + newline);
            ppWin.appendText("language3HasSeparateDictionaryFile" + colon + language3HasSeparateDictionaryFile + newline + newline);
            
            // other values.
            if (!"".equals(dictionaryGenerationLanguage3ExpressionSplitString)){
                ppWin.appendText("dictionaryGenerationLanguage3ExpressionSplitString" + colon + 
                                                                  dictionaryGenerationLanguage3ExpressionSplitString + newline);
            }
            if (!"".equals(language3IndexNumberOfSourceEntries)){
                ppWin.appendText("language3IndexNumberOfSourceEntries" + colon + language3IndexNumberOfSourceEntries + newline);
            }
            if (!"".equals(language3DictionaryUpdateClassName)){
                ppWin.appendText("language3DictionaryUpdateClassName" + colon + language3DictionaryUpdateClassName + newline);
            }
            if (!"".equals(language3NormationClassName)){
                ppWin.appendText("language3NormationClassName" + colon + language3NormationClassName + newline);
            }
            
            // Langage-3 content variables
            
            // Number of content declaration variables for language 3
            if (!"".equals(language3NumberOfContentDeclarations)){
                ppWin.appendText("language3NumberOfContentDeclarations" + colon + language3NumberOfContentDeclarations + newline + newline);
            }
            if (!"".equals(language3Content1DisplayText)){
                ppWin.appendText("language3Content1DisplayText" + colon + language3Content1DisplayText + newline);
            }

            if (!"".equals(language3Content1FontColour)){
                ppWin.appendText("language3Content1FontColour" + colon + language3Content1FontColour + newline);
            }

            if (!"".equals(language3Content1FontStyle)){
                ppWin.appendText("language3Content1FontStyle" + colon + language3Content1FontStyle + newline);
            }

            if (!"".equals(language3Content1DisplaySelectable)){
                ppWin.appendText("language3Content1DisplaySelectable" + colon + language3Content1DisplaySelectable + newline);
            }

            if (!"".equals(language3Content2DisplayText)){
                ppWin.appendText("language3Content2DisplayText" + colon + language3Content2DisplayText + newline);
            }

            if (!"".equals(language3Content2FontColour)){
                ppWin.appendText("language3Content2FontColour" + colon + language3Content2FontColour + newline);
            }

            if (!"".equals(language3Content2FontStyle)){
                ppWin.appendText("language3Content2FontStyle" + colon + language3Content2FontStyle + newline);
            }

            if (!"".equals(language3Content2DisplaySelectable)){
                ppWin.appendText("language3Content2DisplaySelectable" + colon + language3Content2DisplaySelectable + newline);
            }

            if (!"".equals(language3Content3DisplayText)){
                ppWin.appendText("language3Content3DisplayText" + colon + language3Content3DisplayText + newline);
            }

            if (!"".equals(language3Content3FontColour)){
                ppWin.appendText("language3Content3FontColour" + colon + language3Content3FontColour + newline);
            }

            if (!"".equals(language3Content3FontStyle)){
                ppWin.appendText("language3Content3FontStyle" + colon + language3Content3FontStyle + newline);
            }

            if (!"".equals(language3Content3DisplaySelectable)){
                ppWin.appendText("language3Content3DisplaySelectable" + colon + language3Content3DisplaySelectable + newline);
            }

            if (!"".equals(language3Content4DisplayText)){
                ppWin.appendText("language3Content4DisplayText" + colon + language3Content4DisplayText + newline);
            }

            if (!"".equals(language3Content4FontColour)){
                ppWin.appendText("language3Content4FontColour" + colon + language3Content4FontColour + newline);
            }

            if (!"".equals(language3Content4FontStyle)){
                ppWin.appendText("language3Content4FontStyle" + colon + language3Content4FontStyle + newline);
            }

            if (!"".equals(language3Content4DisplaySelectable)){
                ppWin.appendText("language3Content4DisplaySelectable" + colon + language3Content4DisplaySelectable + newline);
            }            
        }
    }
    


    public void updateNumOfLang() {        
        if (numOfLangCmbBox.getSelectedItem().equals("2")){          
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
            if (DfMCreatorMain.LookAndFeel != null) {
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
            }
        }
        else if (numOfLangCmbBox.getSelectedItem().equals("3")){            
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
            if (DfMCreatorMain.LookAndFeel != null) {
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
            }
        }
    }
    
    private static final String RequiredArgsAbsentErrorMsg = I18n.tr("oneOrMore.dfmPropCreate");

    private static final String openPropFileErrorMsg = I18n.tr("errorOpeningPropFile.dfmPropCreate");
 
    private static final String NotThePropFileErrorMsg = I18n.tr("badPropsFileName.dfmPropCreate");
    
    private static final String fileCantBeOpenedErrorMsg = I18n.tr("fileReadError.dfmPropCreate");
    
    
    private static final String samplePropertyFile
    = "infoText: Freedict (English - German), http://www.freedict.de\n"
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

    public static String getInputOptionDialog(String label, String winTitle) {
            Object[] possibilities = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
            String s = (String)JOptionPane.showInputDialog(
                            null,
                            winTitle,
                            label + "\n",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            "1");
            return s;
    }

    private void showContentPropsEditor(){
        ContentPropsEditor CPEdit = ContentPropsEditor.getContentPropsEditor();
        CPEdit.setSize(1100, 500);
        CPEdit.setLocation(screenSize.width / 2 - CPEdit.getWidth() / 2,
                        screenSize.height / 2 - CPEdit.getHeight() / 2);
        CPEdit.updateNumOfContentDec();
        CPEdit.setModal(true);
        CPEdit.setVisible(true);
    }

    private void checkInfoTextContents() throws IllegalArgumentException {
        String s = infoTextTA.getText();
        boolean v;
        boolean w;
             
        // checking if infoText contains an email address
        // or a link that will let the users of the generated
        // dictionary to know who's the maintainer of it of
        // how the contact the original authors or something else.            
        v = DfMCreatorUtilsRegexp.validateEmail(s);            
        w = DfMCreatorUtilsRegexp.validateURL(s);
        
        if (!v && !w){
            throw new IllegalArgumentException(I18n.tr("badInfoText.dfmPropCreate"));
        }

    }
    
}
