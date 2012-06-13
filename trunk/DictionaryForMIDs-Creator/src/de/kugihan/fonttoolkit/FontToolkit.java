/*
****************************************************************************
* This version of this file is part of DictionaryForMIDs Creator
* (C) 2012 Karim Mahamane Karimou
*
* This version is a modified version. It was modified to make it compatible
* with DictionaryForMIDs Creator. It was modified by me. See below for
* information about the original copyright holder.
*
* DictionaryForMIDs Creator (DfMCreator) is a GUI wrapper around various
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

import de.kugihan.DfMCreator.BFGSummary;
import de.kugihan.DfMCreator.DfMCreatorException;
import de.kugihan.DfMCreator.DfMCreatorException.CSVDictionaryFilesNotFound;
import de.kugihan.DfMCreator.DfMCreatorException.dictionaryDirNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorException.dictionaryFieldIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.fontFieldIsEmpty;
import de.kugihan.DfMCreator.DfMCreatorException.fontNotAccessible;
import de.kugihan.DfMCreator.DfMCreatorMain;
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
    
    private static final long serialVersionUID = 1L;

    private boolean debugMode = false;

    private JPanel panel = new JPanel();

    private JFrame popup = new JFrame();

    private JTextField fontField = new JTextField(15);

    private JButton fontButton = new JButton(I18n.tr("browse"));

    private JTextField dictionaryField = new JTextField(15);

    private JButton dictionaryButton = new JButton(I18n.tr("browse"));

    private String[] fontSizes = { "8", "10", "12", "14", "16", "18", "20",
                    "22", "24", "26", "28", "30", "32", "34", "36" };

    private String[] pixelsUp = { "0", "1", "2", "3", "4"};

    private String[] pixelsBottom = { "0", "1", "2", "3", "4"};

    private JComboBox fontList = new JComboBox(fontSizes);

    private JComboBox pixelsTopBox = new JComboBox(pixelsUp);

    private JComboBox pixelsBottomBox = new JComboBox(pixelsBottom);

    // Make it public to enable DfM-Creator to access to it.
    public JButton startButton = new JButton(I18n.tr("start"));

    private JLabel fontLabel = new JLabel(I18n.tr("fontPath"));

    private JLabel pixelsLabel = new JLabel(I18n.tr("pixelsClip"));

    private JLabel dictionaryLabel = new JLabel(I18n.tr("dictionaryPath"));

    private JLabel fontSizeLabel = new JLabel(I18n.tr("fontPoints"));

    private javax.swing.JProgressBar progressBar = new javax.swing.JProgressBar();

    private JMenuItem quitItem;

    private JMenuItem aboutItem;

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private Core c;
        
        // Getter methods
    
        public String getFontFieldText() {
            return fontField.getText();
        }
        
        public String getDictionaryFieldText() {
            return dictionaryField.getText();
        }
        
        public String getFontSize() {
            String fontSize = String.valueOf(fontList.getSelectedItem().toString());
            return fontSize;
        }
        
        public String getClipTob() {
            String clipTop = String.valueOf(pixelsTopBox.getSelectedItem().toString());
            return clipTop;
        }
        
        public String getClipBottom() {
            String clipBottom = String.valueOf(pixelsBottomBox.getSelectedItem().toString());
            return clipBottom;
        }
                
	public static void main (String[] args) throws DictionaryException {
                new FontToolkit().run();
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
            this.setTitle(I18n.tr("bitmapFonGenerator")+ Util.getUtil().getApplicationVersionString());
            this.setJMenuBar(getJMenuBar());
            this.setDefaultCloseOperation(FontToolkit.EXIT_ON_CLOSE);

            this.setSize(460, 300);
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
        
        public JPanel getJPanel(){

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

            startButton.setBounds(130, 250, 210, 25);

            fontButton.addActionListener(this);
            dictionaryButton.addActionListener(this);
            startButton.addActionListener(this);

            return panel;
        }
        

        @Override
	public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == fontButton) {
                    fontField.setText(getFile(false));
            } else if (arg0.getSource() == dictionaryButton) {
                    dictionaryField.setText(getFile(true));
            } else if (arg0.getSource() == startButton) {
                
                // Validate the values entered and
                // showing the bitmap font generation
                // preferences summary which will enable
                // us to actualy generate the font files.
                validateAndShowSum();

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
    
    public void showBFGsummary() {
        BFGSummary bfgSum = BFGSummary.getBFGwindow(); 
        bfgSum.setSize(400, 400);
        bfgSum.setLocation(screenSize.width / 2 - bfgSum.getWidth() / 2,
                           screenSize.height / 2 - bfgSum.getHeight() / 2);
        
        bfgSum.setVisible(true);
    }
    
        public void validateAndShowSum() {
            try {
                validateFields();
                findCSVFiles(new File(dictionaryField.getText()));
                showBFGsummary();
            } catch (CSVDictionaryFilesNotFound ex) {
                    DfMCreatorMain.printAnyMsg(DfMCreatorException.CSVDictionaryFilesNotFoundMsg,
                                               I18n.tr("badDictDir"), JOptionPane.ERROR_MESSAGE);
            } catch (fontFieldIsEmpty e){
                    DfMCreatorMain.printAnyMsg(DfMCreatorException.fontFieldIsEmptyMsg, I18n.tr("emptyFieldError"), JOptionPane.ERROR_MESSAGE);
            } catch (dictionaryFieldIsEmpty e){
                    DfMCreatorMain.printAnyMsg(DfMCreatorException.dictionaryFielsIsEmptyMsg, I18n.tr("emptyFieldError"), JOptionPane.ERROR_MESSAGE);
            }  catch (NumberFormatException e) {
                    showSizeError(e);
            } catch (Exception e) {
                    showFatalError(e);
            }
        }
        
        public void proceed() {
            try {
                beginProcess();
            } catch (fontNotAccessible e){
                    DfMCreatorMain.printAnyMsg(DfMCreatorException.fontNotAccessibleMsg, I18n.tr("fontError"), JOptionPane.ERROR_MESSAGE);
            } catch (dictionaryDirNotAccessible e){
                     DfMCreatorMain.printAnyMsg(DfMCreatorException.dictionaryDirNotAccessibleMsg, I18n.tr("notDictFiles"), JOptionPane.ERROR_MESSAGE);                            
            }
        }

	public void validateFields() throws fontFieldIsEmpty, dictionaryFieldIsEmpty {
            
                if ("".equals(fontField.getText())){
                    throw new fontFieldIsEmpty(I18n.tr("emptyFontField"));
                }
                
                if ("".equals(dictionaryField.getText())){
                    throw new dictionaryFieldIsEmpty(I18n.tr("emptyDictField"));
                }
            
		String font = fontField.getText();
		if (font.substring(font.length() - 1, font.length()).equals("/")
				|| font.substring(font.length() - 1, font.length())
						.equals("\\"))
			font = font.substring(0, font.length() - 1);
		fontField.setText(font);

		String dictionary = dictionaryField.getText();
		if (dictionary.substring(dictionary.length() - 1, dictionary.length())
				.equals("/")
				|| dictionary.substring(dictionary.length() - 1,
						dictionary.length()).equals("\\"))
			dictionary = dictionary.substring(0, dictionary.length() - 1);
		dictionaryField.setText(dictionary);
	}

	public void beginProcess() throws fontNotAccessible, dictionaryDirNotAccessible {
            
		File inFile = new File(fontField.getText());
		if (!inFile.canRead())
			throw new fontNotAccessible(I18n.tr("notFontAccessible"));

		File dirFile = new File(dictionaryField.getText());
		if (!dirFile.isDirectory() || !dirFile.canRead())
			throw new dictionaryDirNotAccessible(I18n.tr("notDictDir"));

		int f = Integer.parseInt(fontList.getSelectedItem().toString());
		int clipTop = Integer.parseInt(pixelsTopBox.getSelectedItem().toString());
		int clipBottom = Integer.parseInt(pixelsBottomBox.getSelectedItem().toString());
		String fontDirectory = dictionaryField.getText();
		c = new Core(inFile, dirFile, fontDirectory, this, f, clipTop, clipBottom);
                c.start();
	}

	public String getFile(boolean dirsOnly) {
		JFileChooser chooser = new JFileChooser();
		if (dirsOnly) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else {
			chooser.setFileFilter(new FontFilter());
		}
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			return chooser.getSelectedFile().getAbsolutePath();
		return "";
	}

    @Override
	public void finish() {
		popup.setVisible(false);
		this.setVisible(true);
                Path outBmfPath = Paths.get(dictionaryField.getText(), I18n.tr("fonts"));
                outBmfPath = outBmfPath.normalize();
                outBmfPath = outBmfPath.toAbsolutePath();
                String outBmfString = outBmfPath.toString();
                
		JOptionPane.showMessageDialog(null, I18n.tr("successMsg", new Object[] {outBmfString}),
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
		JOptionPane
				.showMessageDialog(
						null,
						I18n.tr("appAboutMsg", new Object[] {Util.getUtil().getApplicationVersionString()}),
						I18n.tr("about"), JOptionPane.INFORMATION_MESSAGE);
	}

	public void showInvalidFieldsError(Exception e) {
		JOptionPane.showMessageDialog(null,
				I18n.tr("emptyFieldsError"), I18n.tr("error"),
				JOptionPane.ERROR_MESSAGE);
		if (debugMode)
                    e.printStackTrace();
	}

	public void showSizeError(Exception e) {
		JOptionPane.showMessageDialog(null,
				I18n.tr("invalidFontSizeError"), I18n.tr("error"),
				JOptionPane.ERROR_MESSAGE);
		if (debugMode)
                    e.printStackTrace();
	}

	public void showFatalError(Throwable t) {
		DfMCreatorMain.printAnyMsg(I18n.tr("criticalErrMsg", new Object[]
                                 {t, t.getLocalizedMessage()}), I18n.tr("error"),
                                                      JOptionPane.ERROR_MESSAGE);
                System.out.println(t + "\n");
                
		if (debugMode)
			t.printStackTrace();
	}

	public void showInvalidFileError(Exception e) {
		JOptionPane.showMessageDialog(null,
				I18n.tr("filesNotAccessible"), I18n.tr("error"),
				JOptionPane.ERROR_MESSAGE);
		if (debugMode)
			e.printStackTrace();
	}

}
    class FontFilter extends javax.swing.filechooser.FileFilter {
    @Override
	public boolean accept(File f) {
		boolean accept = f.isDirectory();

		if (!accept) {
			String suffix = getSuffix(f);

			if (suffix != null)
				accept = suffix.equals("ttf") || suffix.equals("ttc");
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

		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase();

		return suffix;
	}
}
