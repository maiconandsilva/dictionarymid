/*
 ****************************************************************************
 * This version of this file is part of DictionaryForMIDs-Creator
 * Copyright (C) 2012, 2013 Karim Mahamane Karimou
 *
 * This version is a modified version. It was modified to make it compatible
 * with DictionaryForMIDs-Creator. It was modified by me. See below for
 * information about the original copyright holder.
 *
 * DictionaryForMIDs-Creator (DfM-Creator) is a GUI wrapper around various
 * DictionaryForMIDs tools, among others we have DictdToDictionaryForMIDs,
 * DictionaryGeneration, JarCreator and BitmapFontGenerator.
 *
 * GPL applies, see file COPYING for more license information.
 *
 ****************************************************************************
 */
/**
 * DictionaryForMIDs
 *
 * FontToolkit.java - Created by Sean Kernohan (webmaster@seankernohan.com)
 */
package de.kugihan.fonttoolkit;

import de.kugihan.DfMCreator.DfMCreatorException;
import de.kugihan.DfMCreator.DfMCreatorException.CSVDictionaryFilesNotFound;
import de.kugihan.DfMCreator.DfMCreatorException.dictionaryDirNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.dictionaryFieldIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.fontFieldIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.fontNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorMain;
import de.kugihan.DfMCreator.SumWinBFG;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.general.UtilWin;
import edu.hws.eck.mdb.I18n;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;

public class FontToolkit extends JFrame implements ActionListener, Callback {

    // Flag that will tell us wether the FontToolkit
    // is being called from DfM-Creator or from the
    // command line (the user might have called the
    // older standalone version of the FontToolkit)
    // set to true if it is called from DfM-Creator
    // and to false otherwise.
    public static boolean flag = false;
    // For the new upcoming CLI version of FontGenerator
    public static boolean flag_cli = false;
    // These variables will be used in the
    // setValuesForQueue subroutine
    private File inFile;
    private File dirFile;
    private int fontSize;
    private int clipTop;
    private int clipBottom;
    private String fontDirectory;
    // Getter and setter methods for
    // the above variables.
    Callback call_back;

    public Callback getCallback() {
        return call_back;
    }

    public File getInputFontFile() {
        return inFile;
    }

    public File getDirFile() {
        return dirFile;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getClipTop() {
        return clipTop;
    }

    public int getClipBottom() {
        return clipBottom;
    }

    public String getFontDirectory() {
        return fontDirectory;
    }

    public void setInputFontFile(File newInFile) {
        inFile = newInFile;
    }

    public void setDirFile(File newDirFile) {
        dirFile = newDirFile;
    }

    public void setFontSize(int newFontSize) {
        fontSize = newFontSize;
    }

    public void setClipTop(int newClipTop) {
        clipTop = newClipTop;
    }

    public void setClipBottom(int newClipBottom) {
        clipBottom = newClipBottom;
    }

    public void setFontDirectory(String newFontDirectory) {
        fontDirectory = newFontDirectory;
    }
    private static final long serialVersionUID = 1L;
    private boolean debugMode = false;
    private JPanel panel = new JPanel();
    private JFrame popup = new JFrame();
    private JTextField fontField = new JTextField(15);
    private JButton fontButton = new JButton(I18n.tr("browse"));
    private JTextField dictionaryField = new JTextField(15);
    private JButton dictionaryButton = new JButton(I18n.tr("browse"));
    private String[] fontSizes = {"8", "10", "12", "14", "16", "18", "20",
        "22", "24", "26", "28", "30", "32", "34", "36"};
    private String[] pixelsUp = {"0", "1", "2", "3", "4"};
    private String[] pixelsBottom = {"0", "1", "2", "3", "4"};
    private JComboBox fontList = new JComboBox(fontSizes);
    private JComboBox pixelsTopBox = new JComboBox(pixelsUp);
    private JComboBox pixelsBottomBox = new JComboBox(pixelsBottom);
    private JButton startButton = new JButton(I18n.tr("start"));
    private JButton clearButton = new JButton(I18n.tr("clear.fields.dfmCreatorMain"));
    private JLabel fontLabel = new JLabel(I18n.tr("fontPath"));
    private JLabel pixelsLabel = new JLabel(I18n.tr("pixelsClip"));
    private JLabel dictionaryLabel = new JLabel(I18n.tr("dictionaryPath"));
    private JLabel fontSizeLabel = new JLabel(I18n.tr("fontPoints"));
    private javax.swing.JProgressBar progressBar = new javax.swing.JProgressBar();
    private JMenuItem quitItem;
    private JMenuItem aboutItem;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Core c;

    public void executeFontGenerationTaskCLI(File inputFontFile,
            File dictDirectory_FILE, String dictDirectory_STRING,
            int fontSize, int clip_Top, int clip_Bottom) throws
            fontNotAccessible, dictionaryDirNotAccessible {


        if (!inputFontFile.canRead()) {
            throw new fontNotAccessible(I18n.tr("notFontAccessible"));
        }

        if (!dictDirectory_FILE.isDirectory() || !dictDirectory_FILE.canRead()) {
            throw new dictionaryDirNotAccessible(I18n.tr("notDictDir"));
        }

        // DEBUG:
        System.out.println("Debug Information:");
        System.out.print("Input Font File: ");
        System.out.println(String.valueOf(inputFontFile));
        System.out.print("Input Dictionary Directory (FILE): ");
        System.out.println(String.valueOf(dictDirectory_FILE));
        System.out.print("Input Dictionary Directory (STRING): ");
        System.out.println(dictDirectory_STRING);
        //System.out.println(String.valueOf(this)); // Callback value
        System.out.print("Font Size: ");
        System.out.println(String.valueOf(fontSize));
        System.out.print("Clip Top Value: ");
        System.out.println(String.valueOf(clip_Top));
        System.out.print("Clip Bottom Value: ");
        System.out.println(String.valueOf(clip_Bottom));
        System.out.println();
        if (!flag_cli) {
            c = new Core(inputFontFile, dictDirectory_FILE, dictDirectory_STRING, this, fontSize, clip_Top, clip_Bottom);
            c.generateFonts();
        } else { // Generate font for all the sizes: i.e from 8 to 36!

            for (int i = 8; i <= 36; i += 2) {
                c = new Core(inputFontFile, dictDirectory_FILE, dictDirectory_STRING, this, i, clip_Top, clip_Bottom);
                c.generateFonts();
            }
        }
    }

//#################################################################################
    public static void main(String[] args) throws DictionaryException {
        DfMCreatorMain.setTheNimbusLookAndFeel();
        new FontToolkit().run();
    }

    public static void printCopyrightNotice() {
        System.out.print(
                "\n\nDictionaryForMIDs - FontGenerator Copyright (C) 2005 J2ME Polish\n"
                + "FontToolkit (GUI for FontGenerator) Copyright (C) Sean Kernohan\n\n"
                + "This program is free software; you can redistribute it and/or modify\n"
                + "it under the terms of the GNU General Public License as published by\n"
                + "the Free Software Foundation; either version 2 of the License, or\n"
                + "(at your option) any later version.\n\n"
                + "This program is distributed in the hope that it will be useful,\n"
                + "but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
                + "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n"
                + "GNU General Public License for more details.\n\n"
                + "You should have received a copy of the GNU General Public License\n"
                + "along with This program; if not, write to the Free Software Foundation,\n"
                + "Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA\n"
                + "For documentation and source code, see http://dictionarymid.sourceforge.net\n\n");
    }

    public static void printUsage() {
        System.out.println("Not Yet Written. I mean, the Documentation :-)");
    }

    // We will use this subroutine to test the existence of ".csv" files in the
    // selected dictionary directory and throw and exception if they dont exist.
    private void findCSVFiles(File dictionaryDirectory) throws CSVDictionaryFilesNotFound {
        File[] files = dictionaryDirectory.listFiles();
        boolean foundFiles = false;
        for (int i = 0; i < 2; i++) {
            if (files[i].getName().substring(files[i].getName().length() - 4,
                    files[i].getName().length()).equals(".csv")) {
                foundFiles = true;
            }
        }
        if (!foundFiles) {
            throw new CSVDictionaryFilesNotFound(I18n.tr("invalidDictDir"));
        }
    }

    public void run() throws DictionaryException {
        UtilWin utilObj = new UtilWin();
        Util.setUtil(utilObj);
        this.setTitle(I18n.tr("bitmapFonGenerator"));
        this.setJMenuBar(getJMenuBar());
        this.setDefaultCloseOperation(FontToolkit.EXIT_ON_CLOSE);

        this.setSize(460, 350);
        this.setLocation(screenSize.width / 2 - this.getWidth() / 2,
                screenSize.height / 2 - this.getHeight() / 2);

        this.setVisible(true);
        this.add(getJPanel());
        this.setResizable(false);

        this.validate();
    }

    @Override
    public JMenuBar getJMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(I18n.tr("fime.menuItem"));
        JMenu helpMenu = new JMenu(I18n.tr("help.menuItem"));

        quitItem = new JMenuItem(I18n.tr("quit.menuItem"));
        aboutItem = new JMenuItem(I18n.tr("about.menuItem"));

        menuBar.add(fileMenu);
        fileMenu.add(quitItem);
        menuBar.add(helpMenu);
        helpMenu.add(aboutItem);

        quitItem.addActionListener(this);
        aboutItem.addActionListener(this);

        return menuBar;
    }

    public JPanel getJPanel() {

        fontList.setSelectedIndex(2);
        pixelsTopBox.setSelectedIndex(0);
        pixelsBottomBox.setSelectedIndex(0);

        panel.setLayout(null);
        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        panel.add(fontField);
        panel.add(fontButton);
        panel.add(dictionaryField);
        panel.add(dictionaryButton);
        panel.add(fontList);
        panel.add(pixelsTopBox);
        panel.add(pixelsBottomBox);
        panel.add(startButton);
        panel.add(clearButton);
        panel.add(fontLabel);
        panel.add(pixelsLabel);
        panel.add(dictionaryLabel);
        panel.add(fontSizeLabel);

        fontField.setBounds(50, 40, 250, 25);
        fontLabel.setBounds(50, 20, 250, 25);
        fontButton.setBounds(310, 40, 100, 25);

        dictionaryField.setBounds(50, 95, 250, 25);
        dictionaryLabel.setBounds(50, 75, 250, 25);
        dictionaryButton.setBounds(310, 95, 100, 25);

        fontSizeLabel.setBounds(50, 126, 250, 25);
        fontList.setBounds(50, 146, 250, 25);

        pixelsLabel.setBounds(50, 185, 350, 25);
        pixelsTopBox.setBounds(50, 205, 50, 25);
        pixelsBottomBox.setBounds(250, 205, 50, 25);

        startButton.setBounds(45, 250, 210, 25);
        clearButton.setBounds(265, 250, 150, 25);

        fontButton.addActionListener(this);
        dictionaryButton.addActionListener(this);
        startButton.addActionListener(this);
        clearButton.addActionListener(this);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == fontButton) {
            String s = getFile(false);
            if (!"".equals(s)) {
                fontField.setText(s);
            }
        } else if (arg0.getSource() == dictionaryButton) {
            String s = getFile(true);
            if (!"".equals(s)) {
                dictionaryField.setText(s);
            }
        } else if (arg0.getSource() == startButton) {
            // check if flag is set to true, meaning that the fontToolkit
            // is being called from the command line. In such case we don't
            // call the bitmap font generation preferences summary window
            // since that window is related to DfM-Creator but here it has
            // not even been loaded, so, we'll directly generate the font
            // files without calling the preferences summary window.
            if (flag) {
                proceed();
            } else {
                try {
                    // if we reach here flag remains set to false. In such case we can call the
                    // bitmap font generation preferences summary window since DfM-Creator is loaded.
                    // We Validate the values entered and then we show the bitmap font generation
                    // preferences summary window. Here, It enables us to actualy generate the font files.
                    setValuesForQueue();
                } catch (fontNotAccessible | dictionaryDirNotAccessible ex) {
                    System.out.println(ex.getMessage());
                }
                DfMCreatorMain.dfmCreator.createQueueForBFG(getInputFontFile(), getDirFile(),
                        getFontDirectory(), getFontSize(), getClipTop(), getClipBottom());
                validateAndShowSummaryWin();
            }

        } else if (arg0.getSource() == clearButton) {
            clearFields();
        } else if (arg0.getSource() == quitItem) {
            System.exit(0);
        } else if (arg0.getSource() == aboutItem) {
            try {
                showAbout();
            } catch (Exception e) {
                showFatalError(e);
            }
        }
    }

    public void clearFields() {
        fontField.setText("");
        dictionaryField.setText("");
    }

    public void clearDictionaryField() {
        dictionaryField.setText("");
    }

    public void showBFGsummary() {
        SumWinBFG bfgSum = SumWinBFG.getBFGwindow();
        bfgSum.setVisible(true);
    }

    public void validateAndShowSummaryWin() {
        try {
            validateFields();
            findCSVFiles(new File(dictionaryField.getText()));
            showBFGsummary();
        } catch (CSVDictionaryFilesNotFound ex) {
            DfMCreatorMain.printAnyMsg(DfMCreatorException.CSVDictionaryFilesNotFoundMsg,
                    I18n.tr("badDictDir"), JOptionPane.ERROR_MESSAGE);
        } catch (fontFieldIsEmpty e) {
            DfMCreatorMain.printAnyMsg(DfMCreatorException.fontFieldIsEmptyMsg,
                    I18n.tr("emptyFieldError"), JOptionPane.ERROR_MESSAGE);
        } catch (dictionaryFieldIsEmpty e) {
            DfMCreatorMain.printAnyMsg(DfMCreatorException.dictionaryFielsIsEmptyMsg,
                    I18n.tr("emptyFieldError"), JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            showSizeError(e);
        } catch (Exception e) {
            showFatalError(e);
        }
    }

    public void proceed() {
        try {
            beginProcess();
        } catch (fontNotAccessible e) {
            DfMCreatorMain.printAnyMsg(DfMCreatorException.fontNotAccessibleMsg,
                    I18n.tr("fontError"), JOptionPane.ERROR_MESSAGE);
        } catch (dictionaryDirNotAccessible e) {
            DfMCreatorMain.printAnyMsg(DfMCreatorException.dictionaryDirNotAccessibleMsg,
                    I18n.tr("notDictFiles"), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void validateFields() throws fontFieldIsEmpty, dictionaryFieldIsEmpty {

        if ("".equals(fontField.getText())) {
            throw new fontFieldIsEmpty(I18n.tr("emptyFontField"));
        }

        if ("".equals(dictionaryField.getText())) {
            throw new dictionaryFieldIsEmpty(I18n.tr("emptyDictField"));
        }

        String font = fontField.getText();
        if (font.substring(font.length() - 1, font.length()).equals("/")
                || font.substring(font.length() - 1, font.length())
                .equals("\\")) {
            font = font.substring(0, font.length() - 1);
        }
        fontField.setText(font);

        String dictionary = dictionaryField.getText();
        if (dictionary.substring(dictionary.length() - 1, dictionary.length())
                .equals("/")
                || dictionary.substring(dictionary.length() - 1,
                dictionary.length()).equals("\\")) {
            dictionary = dictionary.substring(0, dictionary.length() - 1);
        }
        dictionaryField.setText(dictionary);
    }

    // This is the subroutine that will be called to generate the bitmap fonts
    // when the FontGenerator is called from the GUI of DfM-Creator.
    public void setValuesForQueue() throws fontNotAccessible, dictionaryDirNotAccessible {

        inFile = new File(fontField.getText());
        if (!inFile.canRead()) {
            throw new fontNotAccessible(I18n.tr("notFontAccessible"));
        }

        dirFile = new File(dictionaryField.getText());
        if (!dirFile.isDirectory() || !dirFile.canRead()) {
            throw new dictionaryDirNotAccessible(I18n.tr("notDictDir"));
        }

        fontSize = Integer.parseInt(fontList.getSelectedItem().toString());
        clipTop = Integer.parseInt(pixelsTopBox.getSelectedItem().toString());
        clipBottom = Integer.parseInt(pixelsBottomBox.getSelectedItem().toString());
        fontDirectory = dictionaryField.getText();
    }

    // This is the subroutine that will be called to generate the bitmap fonts
    // when the FontGenerator is called in it's stand alone version from the CLI.
    public void beginProcess() throws fontNotAccessible, dictionaryDirNotAccessible {

        File inputFile = new File(fontField.getText());
        if (!inputFile.canRead()) {
            throw new fontNotAccessible(I18n.tr("notFontAccessible"));
        }

        File dictionaryDirectory = new File(dictionaryField.getText());
        if (!dictionaryDirectory.isDirectory() || !dictionaryDirectory.canRead()) {
            throw new dictionaryDirNotAccessible(I18n.tr("notDictDir"));
        }

        int f = Integer.parseInt(fontList.getSelectedItem().toString());
        int clip_Top = Integer.parseInt(pixelsTopBox.getSelectedItem().toString());
        int clip_Bottom = Integer.parseInt(pixelsBottomBox.getSelectedItem().toString());
        String font_Directory = dictionaryField.getText();

        c = new Core(inputFile, dictionaryDirectory, font_Directory, this, f, clip_Top, clip_Bottom);
        c.start();
    }

    public void incrementFontSizeAndRevalidateValues() {
        try {
            validateFields();
        } catch (fontFieldIsEmpty | dictionaryFieldIsEmpty ex) {
            System.out.println(ex.getMessage());
        }
        try {
            findCSVFiles(new File(dictionaryField.getText()));
        } catch (CSVDictionaryFilesNotFound ex) {
            System.out.println(ex.getMessage());
        }
        int i = fontList.getSelectedIndex();
        if (i < 14) {
            i++;
            fontList.setSelectedIndex(i);
        } else {
            // We restart
            fontList.setSelectedIndex(0);
        }
    }

    public String getFile(boolean dirsOnly) {
        JFileChooser chooser = new JFileChooser();
        if (dirsOnly) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            chooser.setFileFilter(new FontFilter());
        }
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    @Override
    public void finish() {
        popup.setVisible(false);
        this.setVisible(true);
        Path outBmfPath = Paths.get(dictionaryField.getText(), "fonts");
        outBmfPath = outBmfPath.normalize();
        outBmfPath = outBmfPath.toAbsolutePath();
        String outBmfString = outBmfPath.toString();

        JOptionPane.showMessageDialog(null, I18n.tr("successMsg", new Object[]{outBmfString}),
                I18n.tr("DONE"), JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void start() {
        this.setVisible(false);
        popup.setAlwaysOnTop(true);
        popup.add(progressBar);
        popup.setTitle(I18n.tr("wait"));
        progressBar.setStringPainted(true);
        progressBar.setString(I18n.tr("processDurationMsg"));
        popup.setSize(500, 100);
        popup.setLocation(screenSize.width / 2 - popup.getWidth() / 2,
                screenSize.height / 2 - popup.getHeight() / 2);

        popup.setVisible(true);
        popup.setResizable(false);
    }

    @Override
    public void progressing() {
        progressBar.setIndeterminate(true);
    }

    private void halt() {
        c = null;
        popup.setVisible(false);
        this.setVisible(true);
    }

    @Override
    public void throwThreadedFontException() {
        halt();
        JOptionPane.showMessageDialog(null,
                I18n.tr("criticalError.invalidFont"), I18n.tr("error"),
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void throwThreadedDictionaryException() {
        halt();
        JOptionPane.showMessageDialog(null,
                I18n.tr("criticalError.invalidDict"), I18n.tr("error"),
                JOptionPane.ERROR_MESSAGE);
    }

    public void showAbout() throws DictionaryException {
        JOptionPane.showMessageDialog(null,
                I18n.tr("appAboutMsg"), I18n.tr("about"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void showInvalidFieldsError(Exception e) {
        JOptionPane.showMessageDialog(null,
                I18n.tr("emptyFieldsError"), I18n.tr("error"),
                JOptionPane.ERROR_MESSAGE);
        if (debugMode) {
            e.printStackTrace();
        }
    }

    public void showSizeError(Exception e) {
        JOptionPane.showMessageDialog(null,
                I18n.tr("invalidFontSizeError"), I18n.tr("error"),
                JOptionPane.ERROR_MESSAGE);
        if (debugMode) {
            e.printStackTrace();
        }
    }

    public void showFatalError(Throwable t) {
        DfMCreatorMain.printAnyMsg(I18n.tr("criticalErrMsg", new Object[]{t, t.getLocalizedMessage()}), I18n.tr("error"),
                JOptionPane.ERROR_MESSAGE);
        System.out.println(t + "\n");

        if (debugMode) {
            t.printStackTrace();
        }
    }

    public void showInvalidFileError(Exception e) {
        JOptionPane.showMessageDialog(null,
                I18n.tr("filesNotAccessible"), I18n.tr("error"),
                JOptionPane.ERROR_MESSAGE);
        if (debugMode) {
            e.printStackTrace();
        }
    }
}

class FontFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File f) {
        boolean accept = f.isDirectory();

        if (!accept) {
            String suffix = getSuffix(f);

            if (suffix != null) {
                accept = suffix.equals("ttf") || suffix.equals("ttc");
            }
        }
        return accept;
    }

    @Override
    public String getDescription() {
        return I18n.tr("fontFilesFilter");
    }

    private String getSuffix(File f) {
        String s = f.getPath(), suffix = null;
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            suffix = s.substring(i + 1).toLowerCase();
        }

        return suffix;
    }
}
