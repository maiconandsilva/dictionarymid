/**
 * DictionaryForMIDs
 * 
 * GUI.java - Created by Sean Kernohan (webmaster@seankernohan.com)
 */
package de.kugihan.fonttoolkit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FontToolkit extends JFrame implements ActionListener, Callback {
	static final String versionNumber = "3.1.1";
	
	private static final long serialVersionUID = 1L;

	private boolean debugMode = false;

	private JPanel panel = new JPanel();

	private JFrame popup = new JFrame();

	private JTextField fontField = new JTextField(15);

	private JButton fontButton = new JButton("Browse");

	private JTextField dictionaryField = new JTextField(15);

	private JButton dictionaryButton = new JButton("Browse");

	private String[] fontSizes = { "8", "10", "12", "14", "16", "18", "20",
			"22", "24", "26", "28", "30", "32", "34", "36" };
	
	private String[] pixelsUp = { "0", "1", "2", "3", "4"};
	
	private String[] pixelsBottom = { "0", "1", "2", "3", "4"};

	private JComboBox fontList = new JComboBox(fontSizes);
	
	private JComboBox pixelsTopBox = new JComboBox(pixelsUp);
	
	private JComboBox pixelsBottomBox = new JComboBox(pixelsBottom);

	private JButton startButton = new JButton("Start");

	private JLabel fontLabel = new JLabel("Path to font file");
	
	private JLabel pixelsLabel = new JLabel("Clip Pixels from Top / Bottom (default = 0)");

	private JLabel dictionaryLabel = new JLabel("Path to dictionary");

	private JLabel fontSizeLabel = new JLabel("Font size (points)");

	private JTextArea progressBar = new JTextArea();

	private JMenuItem quitItem;

	private JMenuItem aboutItem;

	private static Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	private Core c;

	public static void main(String[] args) {
		new FontToolkit().run();
	}

	public void run() {
		this.setTitle("Bitmap font generator "+ versionNumber);
		this.setJMenuBar(getJMenuBar());
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		this.setSize(460, 300);
		this.setLocation(screenSize.width / 2 - this.getWidth() / 2,
				screenSize.height / 2 - this.getHeight() / 2);

		fontList.setSelectedIndex(2);
		pixelsTopBox.setSelectedIndex(0);
		pixelsBottomBox.setSelectedIndex(0);

		this.setVisible(true);
		this.add(panel);
		this.setResizable(false);
		panel.setLayout(null);

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

		fontField.setBounds(50, 40, 250, 20);
		fontLabel.setBounds(50, 20, 250, 20);
		pixelsLabel.setBounds(50, 150, 250, 20);
		fontButton.setBounds(310, 40, 100, 20);
		dictionaryField.setBounds(50, 80, 250, 20);
		dictionaryLabel.setBounds(50, 60, 250, 20);
		dictionaryButton.setBounds(310, 80, 100, 20);
		fontSizeLabel.setBounds(50, 100, 250, 20);
		fontList.setBounds(50, 120, 250, 20);
		
		pixelsTopBox.setBounds(50, 170, 50, 20);
		pixelsBottomBox.setBounds(200, 170, 50, 20);
		startButton.setBounds(310, 200, 100, 20);

		progressBar.setFont(new Font("monospaced", Font.PLAIN, 12));
		progressBar.setLineWrap(true);
		progressBar.setEditable(false);

		fontButton.addActionListener(this);
		dictionaryButton.addActionListener(this);
		startButton.addActionListener(this);

		this.validate();
	}

	public JMenuBar getJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		quitItem = new JMenuItem("Quit");
		aboutItem = new JMenuItem("About");
		menuBar.add(fileMenu);
		fileMenu.add(quitItem);
		menuBar.add(helpMenu);
		helpMenu.add(aboutItem);

		quitItem.addActionListener(this);
		aboutItem.addActionListener(this);

		return menuBar;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == fontButton) {
			fontField.setText(getFile(false));
		} else if (arg0.getSource() == dictionaryButton) {
			dictionaryField.setText(getFile(true));
		} else if (arg0.getSource() == startButton) {
			try {
				validateFields();
				beginProcess();
			} catch (StringIndexOutOfBoundsException e) {
				showInvalidFieldsError(e);
			} catch (FileNotFoundException e) {
				showInvalidFileError(e);
			} catch (NumberFormatException e) {
				showSizeError(e);
			} catch (Exception e) {
				showFatalError(e);
			}
		} else if (arg0.getSource() == quitItem) {
			System.exit(0);
		} else if (arg0.getSource() == aboutItem) {
			showAbout();
		}
	}

	public void validateFields() throws StringIndexOutOfBoundsException {
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

	public void beginProcess() throws FileNotFoundException {
		File inFile = new File(fontField.getText());
		if (!inFile.canRead())
			throw new FileNotFoundException();

		File dirFile = new File(dictionaryField.getText());
		if (!dirFile.isDirectory() || !dirFile.canRead())
			throw new FileNotFoundException();

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

	public void finish() {
		popup.setVisible(false);
		this.setVisible(true);
		JOptionPane.showMessageDialog(null, "Success!\n\nWrote "
				+ dictionaryField.getText() + "\\font.bmf", "Done",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void start() {
		this.setVisible(false);
		popup.add(progressBar);
		popup.setTitle("Please wait...");
		progressBar
				.setText("This process may take several minutes to complete");
		popup.setSize(500, 250);
		popup.setLocation(screenSize.width / 2 - popup.getWidth() / 2,
				screenSize.height / 2 - popup.getHeight() / 2);

		popup.setVisible(true);
		popup.setResizable(false);
	}

	public void progressing() {
		progressBar.append(".");
	}

	private void halt() {
		c = null;
		popup.setVisible(false);
		this.setVisible(true);
	}

	public void throwThreadedFontException() {
		halt();
		JOptionPane.showMessageDialog(null,
				"Critical error: Invalid font entered.", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public void throwThreadedDictionaryException() {
		halt();
		JOptionPane.showMessageDialog(null,
				"Critical error: Invalid dictionary entered.", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public void showAbout() {
		JOptionPane
				.showMessageDialog(
						null,
						"DictionaryForMIDs\nBitmap font generator " + versionNumber +"\n\nBy Sean Kernohan (webmaster@seankernohan.com)",
						"About", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showInvalidFieldsError(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Error: One or more fields are not filled in.", "Error",
				JOptionPane.ERROR_MESSAGE);
		if (debugMode)
			e.printStackTrace();
	}

	public void showSizeError(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Error: Invalid font size entered.", "Error",
				JOptionPane.ERROR_MESSAGE);
		if (debugMode)
			e.printStackTrace();
	}

	public void showFatalError(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Fatal error: Unknown error. Quitting.", "Error",
				JOptionPane.ERROR_MESSAGE);
		if (debugMode)
			e.printStackTrace();
		System.exit(1);
	}

	public void showInvalidFileError(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Error: Unable to access to the selected file(s).", "Error",
				JOptionPane.ERROR_MESSAGE);
		if (debugMode)
			e.printStackTrace();
	}
}

class FontFilter extends javax.swing.filechooser.FileFilter {
	public boolean accept(File f) {
		boolean accept = f.isDirectory();

		if (!accept) {
			String suffix = getSuffix(f);

			if (suffix != null)
				accept = suffix.equals("ttf") || suffix.equals("ttc");
		}
		return accept;
	}

	public String getDescription() {
		return "Font Files(*.ttf, *.ttc)";
	}

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase();

		return suffix;
	}
}