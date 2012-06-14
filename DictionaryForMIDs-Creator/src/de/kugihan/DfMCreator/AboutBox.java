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
import java.io.IOException;
import java.net.URL;


public class AboutBox extends javax.swing.JDialog {

    private static URL gplURL;
    
    public static AboutBox getAboutBox(){
        return new AboutBox();
    }

    /**
     * Creates new form AboutBox
     */
    public AboutBox() {
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

        okButton = new javax.swing.JButton();
        AboutTabbedPane = new javax.swing.JTabbedPane();
        infoPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoTextPane = new javax.swing.JTextPane();
        creditsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        creditsTextPane = new javax.swing.JTextPane();
        licensePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        licensePane = new javax.swing.JTextPane();
        translationPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/kugihan/I18n-L10n/Bundle"); // NOI18N
        okButton.setText(bundle.getString("ok.aboutBox")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        getContentPane().add(okButton, java.awt.BorderLayout.PAGE_END);

        infoPanel.setLayout(new javax.swing.BoxLayout(infoPanel, javax.swing.BoxLayout.LINE_AXIS));

        infoTextPane.setContentType("text/html");
        infoTextPane.setEditable(false);
        infoTextPane.setText(I18n.tr("infoText.aboutBox")); // NOI18N
        jScrollPane1.setViewportView(infoTextPane);
        infoTextPane.setCaretPosition(0);

        infoPanel.add(jScrollPane1);

        AboutTabbedPane.addTab("Information", infoPanel);

        creditsPanel.setBackground(new java.awt.Color(254, 254, 254));
        creditsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        creditsPanel.setLayout(new java.awt.BorderLayout());

        creditsTextPane.setContentType("text/html");
        creditsTextPane.setEditable(false);
        creditsTextPane.setText(I18n.tr("creditsText.aboutBox")); // NOI18N
        creditsTextPane.setToolTipText("");
        jScrollPane2.setViewportView(creditsTextPane);
        creditsTextPane.setCaretPosition(0);

        creditsPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        AboutTabbedPane.addTab("Credits", creditsPanel);

        licensePanel.setLayout(new java.awt.BorderLayout());

        licensePane.setContentType("text/html");
        licensePane.setEditable(false);
        licensePane.setText("");
        getGPLFile();
        licensePane.setAutoscrolls(true);
        jScrollPane3.setViewportView(licensePane);
        licensePane.setCaretPosition(0);

        licensePanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        AboutTabbedPane.addTab("License", licensePanel);

        translationPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        translationPanel.setLayout(new java.awt.GridBagLayout());

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText(I18n.tr("transGer.aboutBox")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel3, gridBagConstraints);

        jLabel2.setText("Karim Mahamane Karimou");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel5.add(jLabel2, gridBagConstraints);

        jLabel1.setText(I18n.tr("transFra.aboutBox")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel1, gridBagConstraints);

        jLabel4.setText("Gert Nuber ???");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel5.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        translationPanel.add(jPanel5, gridBagConstraints);

        AboutTabbedPane.addTab("Translation", translationPanel);

        getContentPane().add(AboutTabbedPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

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
                new AboutBox().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane AboutTabbedPane;
    private javax.swing.JPanel creditsPanel;
    private javax.swing.JTextPane creditsTextPane;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JTextPane infoTextPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane licensePane;
    private javax.swing.JPanel licensePanel;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel translationPanel;
    // End of variables declaration//GEN-END:variables
    
    private void getGPLFile() {
        String s = DfMCreatorHelpWin.path + I18n.tr("gplFileName.aboutBox");
        gplURL = getClass().getResource(s);
        if (gplURL == null) {
            System.err.println(I18n.tr("couldntFindHelFile") + " " + s);
        }
        displayGPLFile(gplURL);
    }
    
    private void displayGPLFile(URL url) {
        try {
            if (url != null) {
                licensePane.setPage(url);
            } else { //null url
		licensePane.setText(I18n.tr("fileNotFound"));
            }
        } catch (IOException e) {
            System.err.println(I18n.tr("badURL", new Object[] {url}));
        }
    }    
}