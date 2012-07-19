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

import de.kugihan.dictionaryformids.dictgen.DictionaryGeneration;
import edu.hws.eck.mdb.I18n;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


public class SumWinDictGen extends javax.swing.JDialog implements PropertyChangeListener {
    
    private static Task task;
    public static boolean done;
    
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       
    public static SumWinDictGen getDictGenSummary(){
        SumWinDictGen dgensum = new SumWinDictGen();
        dgensum.setSize(380, 450);
        dgensum.setModal(true);
        dgensum.setLocation(screenSize.width / 2 - dgensum.getWidth() / 2,
                          screenSize.height / 2 - dgensum.getHeight() / 2);
        return dgensum;
    }

    /**
     * Creates new form SumWinDictGen
     */
    public SumWinDictGen() {
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
        jPanel2 = new javax.swing.JPanel();
        CancelDictGenBT = new javax.swing.JButton();
        DictGenBT = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        DictGenTA = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        CancelDictGenBT.setText(I18n.tr("cancel.dictdConvSummary")); // NOI18N
        CancelDictGenBT.setEnabled(false);
        CancelDictGenBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelDictGenBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(CancelDictGenBT, gridBagConstraints);

        DictGenBT.setText(I18n.tr("generate.dictdConvSummary")); // NOI18N
        DictGenBT.setActionCommand(I18n.tr("generate.dictdConvSummary")); // NOI18N
        DictGenBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DictGenBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel2.add(DictGenBT, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(jPanel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(progressBar, gridBagConstraints);

        DictGenTA.setBackground(new java.awt.Color(252, 254, 222));
        DictGenTA.setColumns(20);
        DictGenTA.setEditable(false);
        DictGenTA.setRows(5);
        DictGenTA.setTabSize(4);
        jScrollPane2.setViewportView(DictGenTA);
        fillDictGenSummaryTextArea();

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 125;
        gridBagConstraints.ipady = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(jScrollPane2, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DictGenBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DictGenBTActionPerformed
        generatorActPerf();
    }//GEN-LAST:event_DictGenBTActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // cancelling the dictionary generation
        // process if the user closes the window.
        GenCancelledOnQuit();
    }//GEN-LAST:event_formWindowClosing

    private void CancelDictGenBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelDictGenBTActionPerformed
        confirmCancelGen();
    }//GEN-LAST:event_CancelDictGenBTActionPerformed

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
              //  new SumWinDictGen().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelDictGenBT;
    private javax.swing.JButton DictGenBT;
    private javax.swing.JTextArea DictGenTA;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables

    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!done) {
            int progress = task.getProgress();
            if (progress == 0) {
                progressBar.setStringPainted(false);
                progressBar.setIndeterminate(true);                    
                DictGenTA.append(I18n.tr("wait.dictGenSummary"));
            }
            else {
                progressBar.setIndeterminate(false); 
                progressBar.setString(null);
                progressBar.setValue(progress);        
                DictGenTA.append(String.format("\n\n" +
                I18n.tr("completed.dictGenSummary"), task.getProgress()));
            }            
        }
    }

    /**
     * <pre>class Task extends SwingWorker<Void, Void></pre>
     * SwinWorker class named <i>Task</i> that does the same
     * the is for DictionaryGeneration, what the above mentioned
     * one is for DictdToDictionaryForMIDs.
     */
    class Task extends SwingWorker<Void, Void> {
        
        @Override
        protected Void doInBackground() throws Exception {            
            // launching the dict generation.
            try {
                DictionaryGeneration.generate();
            } catch (Exception e){
                // Ignored.
            }
            return null;
        }

        @Override
        public void done() {
            //Tell progress listener to stop updating progress bar.
            done = true;
            Toolkit.getDefaultToolkit().beep();
            DictGenBT.setEnabled(true);
            CancelDictGenBT.setEnabled(false);
            setCursor(null); //turn off the wait cursor
            progressBar.setIndeterminate(false);
            progressBar.setValue(progressBar.getMinimum());
            DictGenTA.append(I18n.tr("done.dictGenSummary"));
        }

    }
    
    /*
     * This method will set some values up
     * and call the generation task.
     */
    public void generatorActPerf(){
        DictGenBT.setEnabled(false);
        CancelDictGenBT.setEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        done = false;
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
        progressBar.setValue(task.getProgress());
      }
    
    
    public void confirmCancelGen() {
        int n = JOptionPane.showConfirmDialog(null, I18n.tr("sureToCancel.dictGenSummary"),
                                                    I18n.tr("cancelConversion.dictGenSummary"), JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION){
            if (!task.isDone()){
                task.cancel(true);
                deleteIncompleteDictFiles();                
            }
        }
    }
    
    public void GenCancelledOnQuit() {
        boolean n = deleteIncompleteDictFiles();
        if (n == true){
            if (!task.isDone()){
                task.cancel(true);
            }
        }        
        this.dispose();
    }
    
    // This method will be used to delete the
    // incomplete outputcsvfile if the conversion
    // process is aborted.
    public boolean deleteIncompleteDictFiles(){
        boolean status;
        File csvFile = new File ("DictionaryGeneration");
        if (csvFile.exists()) {
            csvFile.delete();
            status = true;
        }
        else {
            status = false;
        }
        return status;
    }
    
    /**
     * fillDictGenSummaryTextArea() gets all the needed information
     * and formats them correctly so as to have a clear and neat
     * summary of the DictionaryGeneration preferences entered
     * by the user.
     */
    private void fillDictGenSummaryTextArea() {
        
        DictGenTA.append(I18n.tr("headline.dictGenSummary") + "\n\n");

        DictGenTA.append(I18n.tr("inputFile.dictGenSummary") + "\n");

        DictGenTA.append(DictionaryGeneration.getSourceFile() + "\n\n");

        DictGenTA.append(I18n.tr("outputDir.dictGenSummary") + "\n");

        DictGenTA.append(DictionaryGeneration.getDirectoryDestination() + "\n\n");

        DictGenTA.append(I18n.tr("dfmPropsFile.dictGenSummary") + "\n");

        DictGenTA.append(DictionaryGeneration.getPropertyPath() + "\n\n");

        DictGenTA.append(I18n.tr("hitEnterToProceed.dictGenSummary") + "\n");
    }
}
