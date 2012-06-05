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

import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.jarCreator.JarCreator;
import edu.hws.eck.mdb.I18n;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author leko
 */
public class JarCreationSummary extends javax.swing.JDialog implements PropertyChangeListener {
    
    public static JarCreationSummary getCJPWin(){
        return new JarCreationSummary();
    }
    
    private Task task;
    public static boolean done;

    /**
     * Creates new form JarCreationSummary
     */
    public JarCreationSummary() {
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        outTA = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        StartBT = new javax.swing.JButton();
        CancelBT = new javax.swing.JButton();
        ProgBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        outTA.setColumns(20);
        outTA.setEditable(false);
        outTA.setRows(5);
        jScrollPane2.setViewportView(outTA);
        outTA.setText("");

        outTA.append(I18n.tr("headLine.jarCreationSummary") + "\n");

        outTA.append(I18n.tr("dictDir.jarCreationSummary"));
        outTA.append("\n" + JarCreator.getDictionaryDirectory() + "\n\n");

        outTA.append(I18n.tr("emptyDfMDir.jarCreationSummary"));
        outTA.append("\n" + JarCreator.getEmptyDictionaryForMID() + "\n\n");

        outTA.append(I18n.tr("outputDir.jarCreationSummary"));
        outTA.append("\n" + JarCreator.getOutputDirectory() + "\n\n");

        outTA.append(I18n.tr("hitStart.jarCreationSummary"));
        outTA.append("\n");

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));
        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        StartBT.setText(I18n.tr("start.jarCreationSummary")); // NOI18N
        StartBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        jPanel2.add(StartBT, gridBagConstraints);

        CancelBT.setText(I18n.tr("cancel.jarCreationSummary")); // NOI18N
        CancelBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel2.add(CancelBT, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 70;
        jPanel2.add(ProgBar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void StartBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartBTActionPerformed
        progActionPerf();
    }//GEN-LAST:event_StartBTActionPerformed

    private void CancelBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelBTActionPerformed
        confirmCancelConvert();
    }//GEN-LAST:event_CancelBTActionPerformed

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
              //  new JarCreationSummary().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelBT;
    private javax.swing.JProgressBar ProgBar;
    private javax.swing.JButton StartBT;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea outTA;
    // End of variables declaration//GEN-END:variables

    private static final String newline = "\n";

    class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            try {
                JarCreator.createJar();
            } catch (IOException | DictionaryException e) {
                done = true;
                DfMCreatorMain.printAnyMsg(e.getMessage() ,"Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());             
            } catch (DfMCreatorException.CantCreatOutputJarJadDirectory e){
                done = true;
                DfMCreatorMain.printAnyMsg(e.getMessage() ,"Directory Creation Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }
            return null;
            }
        
        @Override
        public void done() {
            //Tell progress listener to stop updating progress bar.
            done = true;
            StartBT.setEnabled(true);
            CancelBT.setEnabled(false);
            Toolkit.getDefaultToolkit().beep();
            setCursor(null); //turn off the wait cursor
            ProgBar.setIndeterminate(false);
            ProgBar.setValue(ProgBar.getMinimum());
            outTA.append(I18n.tr("done.jarCreationSummary"));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!done) {
            int progress = task.getProgress();
            if (progress == 0) {
                ProgBar.setStringPainted(false);
                ProgBar.setIndeterminate(true);
                outTA.append(I18n.tr("pleaseWait.jarCreationSummary"));
                StartBT.setEnabled(false);
                CancelBT.setEnabled(true);
            }
            else {
                ProgBar.setIndeterminate(false); 
                ProgBar.setString(null);
                ProgBar.setValue(progress);        
                outTA.append(String.format(newline + newline +
                I18n.tr("completed.jarCreationSummary"), task.getProgress()));
            }            
        }
    }
    
    public void progActionPerf(){
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        done = false;
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
        ProgBar.setValue(task.getProgress());
      }

    public void confirmCancelConvert() {
        int n = JOptionPane.showConfirmDialog(null, I18n.tr("sureToCancelConversion.jarCreationSummary"),
                                      I18n.tr("cancelConversion.jarCreationSummary"), JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION){
            if (!task.isDone()){
                task.cancel(true);
                deleteIncompleteDictFile();                
            }
        }
    }

    // deletes the incomplete jar and jad files
    // if the user aborts the operation
    public void deleteIncompleteDictFile(){
        File incompleteJar = JarCreator.getJarFile();
        if (incompleteJar.exists()) {
            incompleteJar.delete();
        }
        
        File incompleteJad = JarCreator.getJadFile();
        if (incompleteJad.exists()) {
            incompleteJad.delete();
        }
    }

}
