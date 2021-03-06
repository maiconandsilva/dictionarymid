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
package de.kugihan.DfMCreator.preferencesbox;

import de.kugihan.DfMCreator.DfMCreatorMain;
import edu.hws.eck.mdb.I18n;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.DefaultListModel;

public class PreferencesBox extends javax.swing.JFrame {

    // Model used with the list of installed look and feels
    DefaultListModel LookAndFeelListModel = new DefaultListModel();
    // Arraylist holding the string names of the look and feel classes
    ArrayList<String> lookAndFeelClassNames = new ArrayList<>();
    // Model used for the available languages
    DefaultListModel languagesListModel = new DefaultListModel();
    // Arraylist holding the genuine names of the locales
    ArrayList<String> languagesLocales = new ArrayList<>();

    /**
     * Creates new form PreferencesBox
     */
    public PreferencesBox() {
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayLookAndFeelNames();
        LookAndFeelList = new javax.swing.JList(LookAndFeelListModel);
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        displayAvailableLanguages();
        languagesList = new javax.swing.JList(languagesListModel);
        setLanguageBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        LookAndFeelList.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        LookAndFeelList.setMaximumSize(new java.awt.Dimension(100, 150));
        LookAndFeelList.setMinimumSize(new java.awt.Dimension(90, 140));
        jScrollPane1.setViewportView(LookAndFeelList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.ipady = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jButton1.setText(I18n.tr("selectLookAndFeel")); // NOI18N
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        jPanel1.add(jButton1, gridBagConstraints);

        jTabbedPane1.addTab("Look & Feel", jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setViewportView(languagesList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.ipady = 150;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        jPanel2.add(jScrollPane2, gridBagConstraints);

        setLanguageBT.setText(I18n.tr("set.language.bt.txt.PrefsBox")); // NOI18N
        setLanguageBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setLanguageBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        jPanel2.add(setLanguageBT, gridBagConstraints);

        jTabbedPane1.addTab("Language", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName(I18n.tr("prefsWinName.preferences")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setLookAndFeelPrefs();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void setLanguageBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setLanguageBTActionPerformed
        saveLanguageSettings();
    }//GEN-LAST:event_setLanguageBTActionPerformed

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
                //new PreferencesBox().setVisible(true);
            }
        });
    }
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static PreferencesBox getPrefsWin() {
        PreferencesBox prefs = new PreferencesBox();
        //prefs.setSize(350, 400);
        prefs.setLocation(screenSize.width / 2 - prefs.getWidth() / 2,
                screenSize.height / 2 - prefs.getHeight() / 2);
        return prefs;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList LookAndFeelList;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList languagesList;
    private javax.swing.JButton setLanguageBT;
    // End of variables declaration//GEN-END:variables

    private void displayLookAndFeelNames() {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            // add the look and feel _names_ to
            // the list that the user will see
            LookAndFeelListModel.addElement(info.getName());

            // add the actual class name to the
            // array list that will be used to
            // actually set the selected L & F
            lookAndFeelClassNames.add(info.getClassName());
        }
    }

    /**
     * In the List that displays the intalled look and feels, the values are
     * displayed as follows by this code snipet that was copied:<br><br>
     *
     * // add the look and feel names to<br>
     * // the list that the user will see
     * <pre>LookAndFeelListModel.addElement(info.getName());</pre>
     *
     * // add the actual class names to the<br>
     * // array list that will be used to<br>
     * // actually set the selected L & F
     * <pre>lookAndFeelClassNames.add(info.getClassName());</pre>
     *
     * In fact, the order of the index in the list from which the user selects
     * among the installed look and feels (info.getName()) is the same as the
     * index order of the arraylist <i>lookAndFeelClassNames</i>; consequently,
     * we use <i>info.getName</i> to get the names of the look and feels (names
     * are better than the ugly class names), and then we use the actual class
     * names that were stored in the arraylist <i>lookAndFeelClassNames</i> to
     * pass them to the subroutine <b>savePreferences()</b> in order for it to
     * save them.
     */
    private void setLookAndFeelPrefs() {
        // the selected look and feel name
        int idx = LookAndFeelList.getSelectedIndex();
        // the look and feel class name that corresponds
        // to the selected look and feel chosen by the user
        String lookAndFeelSelected = lookAndFeelClassNames.get(idx);
        DfMCreatorMain.savePreferences(lookAndFeelSelected);
    }

    private void displayAvailableLanguages() {
        Locale francais = new Locale.Builder().setLanguage("en").setRegion("US").build();
        Locale english = new Locale.Builder().setLanguage("fr").setRegion("FR").build();

        // Adding element to the list
        languagesListModel.addElement(francais.getDisplayName());
        languagesListModel.addElement(english.getDisplayName());

        // Adding element to the local ArrayList
        languagesLocales.add(francais.toLanguageTag());
        languagesLocales.add(english.toLanguageTag());
    }

    private void saveLanguageSettings() {
        // the selected language
        int idx = languagesList.getSelectedIndex();
        String lang = languagesLocales.get(idx);
        DfMCreatorMain.saveLanguagePrefs(lang);
    }
}
