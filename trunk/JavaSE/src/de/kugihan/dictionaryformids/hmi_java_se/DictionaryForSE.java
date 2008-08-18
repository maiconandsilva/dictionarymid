package de.kugihan.dictionaryformids.hmi_java_se;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.jar.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import de.kugihan.dictionaryformids.dataaccess.*;
import de.kugihan.dictionaryformids.dataaccess.content.*;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.FileAccessHandler;
import de.kugihan.dictionaryformids.dataaccess.fileaccess.JarInputStreamAccess;
import de.kugihan.dictionaryformids.general.DictionaryException;
import de.kugihan.dictionaryformids.general.Util;
import de.kugihan.dictionaryformids.general.UtilWin;
import de.kugihan.dictionaryformids.hmi_common.content.*;
import de.kugihan.dictionaryformids.hmi_java_me.DictionarySettings;
import de.kugihan.dictionaryformids.translation.SingleTranslation;
import de.kugihan.dictionaryformids.translation.TextOfLanguage;
import de.kugihan.dictionaryformids.translation.TranslationExecution;
import de.kugihan.dictionaryformids.translation.TranslationExecutionCallback;
import de.kugihan.dictionaryformids.translation.TranslationParameters;
import de.kugihan.dictionaryformids.translation.TranslationResult;

/**
* J2SE Version of DictionaryForMids
* Copyright (C) 2005-2008 Stefan Martens (stefan@stefan1200.de)
* 
* GPL applies - see file COPYING for copyright statement.
*
* @author Stefan "Stefan1200" Martens
* @version 3.3.0 Beta 1 (17.08.2008)
*/
public class DictionaryForSE extends JFrame
implements ActionListener, TranslationExecutionCallback, MouseListener, DocumentListener
{
	public static String VERSION = "3.3.0 Beta 1";
	private static String CONFIG_NAME = "DictionaryForMIDs.ini";
	
	private JPanel pMainFrame = (JPanel)getContentPane();
	private JTextField tfLeft = new JTextField();
	private JTextField tfRight = new JTextField();
	private JLabel lStatus = new JLabel();
	private DefaultTableModel dtm = new DefaultTableModel()
	{
		public Class getColumnClass(int columnIndex)
		{
			switch (columnIndex)
			{
				default: return String.class;
			}
		}

		public boolean isCellEditable(int rowIndex, int columnIndex)
		{
			return false;
		}
	};
	private JTable table = new SortedTable(dtm);
	private JScrollPane spTable = new JScrollPane(table);
	private AWTFileDialog fd = new AWTFileDialog();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu leftLangSubMenu;
	private JMenu rightLangSubMenu;
	private JMenu dictHistoryMenu;
	private JCheckBoxMenuItem colourTextMenu;
	private JPopupMenu pmListL;;
	private JPopupMenu pmListR;;
	private ButtonGroup bgLeftLang;
	private ButtonGroup bgRightLang;
	
	private boolean colourText = true;
	private boolean rightSearch = false;
	private boolean configLoaded = false;
	private UtilWin utilObj;
	private Clipboard clip = getToolkit().getSystemClipboard();
	private StringSelection cont;
	private javax.swing.Timer timerStatus = new javax.swing.Timer(500, this);
	private Vector dictHistory = new Vector();
	private Vector languages = new Vector();
	private Vector languagesAll = new Vector();
	private String lastSearch = "";
	private String oldStatus = "";
	private Thread searchThread;
	private Properties prop = new Properties();
	private AppTranslation lang = new AppTranslation();
	public static JarFile jar;
	
	private JDialog ppd;
	private JButton bUse;
	private JButton bCancelPrefs;
	private JLabel lFontTest;
	private JComboBox cbFont;
	private String fontName = "SansSerif";

	public static void main(String[] args)
	{
		DictionaryForSE frame = new DictionaryForSE();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public DictionaryForSE()
	{
		lang.loadTranslation();
		initGUI();
		
		loadPrefs();
		if (jar == null)
		{
			loadDictionary();
		}
		
		this.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				if (table.getRowCount() == 0)
				{
					setTableWidth();
				}
			}
		});
		
		timerStatus.setRepeats(false);
		
		DictionarySettings.setMaxHits(1000);
		fillTableColums();
		createGUI();
		TranslationExecution.setTranslationExecutionCallback(this);
		lStatus.setText(lang.getTranslationString("Welcome", "Welcome to %1", "DictionaryForMIDs " + getAppVersion()) + " (c) by Stefan Martens & Gert Nuber");
		setTitle("DictionaryForMIDs " + getAppVersion());
	}
	
	private void setTableWidth()
	{
		int width = spTable.getViewport().getWidth();
		table.getColumnModel().getColumn(0).setPreferredWidth(width/2);
		table.getColumnModel().getColumn(1).setPreferredWidth(width/2);
	}
	
	private void setTableComparator()
	{
		if (colourText)
		{
			((SortedTable)table).setDefaultComparator(String.class, new HTMLStringComparator());
		}
		else
		{
			((SortedTable)table).setDefaultComparator(String.class, new IgnoreCaseComparator());
		}
	}
	
	private void initGUI()
	{
		pmListL = createPopupMenu("L");
		pmListR = createPopupMenu("R");
		
		dictHistoryMenu = new JMenu(lang.getTranslationString("DictionaryHistory", "Dictionary History", null));
		colourTextMenu = new JCheckBoxMenuItem(lang.getTranslationString("ColouredText", "Coloured Text", null), true);
		
		menuBar.add(createFileMenu());
		menuBar.add(createPrefsMenu());
		setJMenuBar(menuBar);
		
		tfLeft.addActionListener(this);
		tfLeft.addMouseListener(this);
		tfLeft.getDocument().addDocumentListener(this);
		tfRight.addActionListener(this);
		tfRight.addMouseListener(this);
		tfRight.getDocument().addDocumentListener(this);
		colourTextMenu.addActionListener(this);
		
		table.addMouseListener(this);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setDefaultRenderer(String.class, new MyTableCellRenderer());
		((SortedTable)table).setResortOnModelChange(false);
		JTableHeader th = table.getTableHeader();
		th.setDefaultRenderer(new MyHeaderCellRenderer());
		((SortedTable)table).setScrollPane(spTable);
	}
	
	private void createGUI()
	{
		pMainFrame.setLayout(new GridBagLayout());
		pMainFrame.add(tfLeft, getGBC(0,0,1,1,10,0, new Insets(1,1,1,5)));
		pMainFrame.add(tfRight, getGBC(1,0,1,1,10,0, new Insets(1,5,1,1)));
		pMainFrame.add(spTable, getGBC(0,1,2,1,10,10, new Insets(1,1,1,1)));
		pMainFrame.add(lStatus, getGBC(0,2,2,1,10,0, new Insets(1,1,1,1)));
	}

	private GridBagConstraints getGBC(int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, Insets inset)
	{
		return new GridBagConstraints(gridx,gridy,gridwidth,gridheight,weightx,weighty,GridBagConstraints.CENTER,GridBagConstraints.BOTH,inset,0,0);
	}
	
	private JMenu createFileMenu()
	{
        JMenu ret = new JMenu(lang.getTranslationString("File", "File", null));
        ret.setMnemonic('F');
        JMenuItem mi;
		//Open Dictionary
        mi = new JMenuItem(lang.getTranslationString("OpenDictionary", "Open Dictionary", null), 'o');
        setCtrlAccelerator(mi, 'O');
        mi.setActionCommand("openDict");
        mi.addActionListener(this);
        ret.add(mi);
		//Dictionary Information
        mi = new JMenuItem(lang.getTranslationString("DictionaryInformation", "Dictionary Information", null), 'i');
        setCtrlAccelerator(mi, 'I');
        mi.setActionCommand("dictInfo");
        mi.addActionListener(this);
        ret.add(mi);
        // Dictionary History
        ret.add(createDictHistoryMenu());
        //Separator
        ret.addSeparator();
        //About
        mi = new JMenuItem(lang.getTranslationString("About", "About", null), 'a');
        mi.setActionCommand("about");
        mi.addActionListener(this);
        ret.add(mi);
        //Separator
        ret.addSeparator();
        //Quit
        mi = new JMenuItem(lang.getTranslationString("Quit", "Quit", null), 'q');
        setCtrlAccelerator(mi, 'Q');
        mi.setActionCommand("quit");
        mi.addActionListener(this);
        ret.add(mi);
        return ret;
    }
	
	private JMenu createPrefsMenu()
	{
        JMenu ret = new JMenu(lang.getTranslationString("Preferences", "Preferences", null));
        JMenuItem mi;
        ret.setMnemonic('F');
        //Change Font
        mi = new JMenuItem(lang.getTranslationString("ChangeFont", "Change Font", null), 'f');
        setCtrlAccelerator(mi, 'F');
        mi.setActionCommand("changeFont");
        mi.addActionListener(this);
        ret.add(mi);
        //Colour Text
        ret.add(colourTextMenu);
        //Left Language
        ret.add(createLeftLangSubMenu());
        //Right Language
        ret.add(createRightLangSubMenu());
        //Separator
        ret.addSeparator();
		//Save Prefs
        mi = new JMenuItem(lang.getTranslationString("SavePreferences", "Save Preferences", null), 's');
        setCtrlAccelerator(mi, 'S');
        mi.setActionCommand("savePrefs");
        mi.addActionListener(this);
        ret.add(mi);
        return ret;
    }
	
	private JMenu createLeftLangSubMenu()
	{
		leftLangSubMenu = new JMenu(lang.getTranslationString("LeftLanguage", "Left Language", null));
        JRadioButtonMenuItem rbm;
        bgLeftLang = new ButtonGroup();
		
        for (int i=0; i<languagesAll.size(); i++)
		{
        	rbm = new JRadioButtonMenuItem(languagesAll.elementAt(i).toString());
        	rbm.addActionListener(this);
        	rbm.setActionCommand("LangL" + languagesAll.elementAt(i).toString());
        	bgLeftLang.add(rbm);
        	leftLangSubMenu.add(rbm);
		}
        
        return leftLangSubMenu;
    }
	
	private JMenu createRightLangSubMenu()
	{
		rightLangSubMenu = new JMenu(lang.getTranslationString("RightLanguage", "Right Language", null));
        JRadioButtonMenuItem rbm;
        bgRightLang = new ButtonGroup();
		
        for (int i=0; i<languagesAll.size(); i++)
		{
        	rbm = new JRadioButtonMenuItem(languagesAll.elementAt(i).toString());
        	rbm.addActionListener(this);
        	rbm.setActionCommand("LangR" + languagesAll.elementAt(i).toString());
        	bgRightLang.add(rbm);
        	rightLangSubMenu.add(rbm);
		}
        
        return rightLangSubMenu;
    }
	
	private JMenu createDictHistoryMenu()
	{
		dictHistoryMenu.removeAll();
        JMenuItem mi;
		
        if (dictHistory.size() == 0)
        {
        	mi = new JMenuItem(lang.getTranslationString("Nothing", "Nothing", null));
        	mi.setEnabled(false);
        	dictHistoryMenu.add(mi);
        }
        else
        {
            for (int i=0; i<dictHistory.size(); i++)
    		{
            	mi = new JMenuItem(getMenuFilename(dictHistory.elementAt(i).toString()));
            	mi.addActionListener(this);
            	mi.setActionCommand("HistoryOpen" + dictHistory.elementAt(i).toString());
            	dictHistoryMenu.add(mi);
    		}
        }
        
        dictHistoryMenu.addSeparator();
    	mi = new JMenuItem(lang.getTranslationString("ClearHistory", "Clear History", null), 'c');
    	mi.addActionListener(this);
    	mi.setActionCommand("clearHistory");
    	dictHistoryMenu.add(mi);
        
        dictHistoryMenu.validate();
        
        return dictHistoryMenu;
    }
	
	private JPopupMenu createPopupMenu(String ident)
	{
		JPopupMenu pm = new JPopupMenu("Popup");
		JMenuItem mi;

		// Copy
		mi = new JMenuItem(lang.getTranslationString("Copy", "Copy", null), 'c');
		mi.setActionCommand(ident + "pmCopy");
		mi.addActionListener(this);
		pm.add(mi);
		// Copy All
		mi = new JMenuItem(lang.getTranslationString("CopyAll", "Copy All", null), 'a');
		mi.setActionCommand(ident + "pmCopyAll");
		mi.addActionListener(this);
		pm.add(mi);
		// Cut
		mi = new JMenuItem(lang.getTranslationString("Cut", "Cut", null));
		mi.setActionCommand(ident + "pmCut");
		mi.addActionListener(this);
		pm.add(mi);
		// Paste
		mi = new JMenuItem(lang.getTranslationString("Paste", "Paste", null), 'p');
		mi.setActionCommand(ident + "pmPaste");
		mi.addActionListener(this);
		pm.add(mi);
		// Paste & Replace
		mi = new JMenuItem(lang.getTranslationString("PasteReplace", "Paste & Replace", null), 'p');
		mi.setActionCommand(ident + "pmPasteReplace");
		mi.addActionListener(this);
		pm.add(mi);

		return pm;
	}
	
    private void setCtrlAccelerator(JMenuItem mi, char acc)
	{
        KeyStroke ks = KeyStroke.getKeyStroke(acc, Event.CTRL_MASK);
        mi.setAccelerator(ks);
    }
	
	private void fillTableColums()
	{
		deleteTable();
		dtm.setColumnCount(0);
		
		dtm.addColumn(languages.elementAt(0));
		dtm.addColumn(languages.elementAt(1));
		
		tfLeft.setEnabled(DictionaryDataFile.supportedLanguages[languagesAll.indexOf(languages.elementAt(0))].isSearchable);
		tfRight.setEnabled(DictionaryDataFile.supportedLanguages[languagesAll.indexOf(languages.elementAt(1))].isSearchable);
		
		tfLeft.setToolTipText(lang.getTranslationString("InputSearchTooltip", "Type a search word (%1) here and press enter", languages.elementAt(0).toString()));
		tfRight.setToolTipText(lang.getTranslationString("InputSearchTooltip", "Type a search word (%1) here and press enter", languages.elementAt(1).toString()));
		
		setTableWidth();
	}
	
	private String getMenuFilename(String path)
	{
		int maxMenuTextLength = 80;
		
		if (path.length() > maxMenuTextLength)
		{
			StringBuffer sb = new StringBuffer(path.substring(path.length() - (maxMenuTextLength - 3)));
			sb.insert(0, "...");
			return sb.toString();
		}
		
		return path;
	}

	private void addTableRow(Vector newRow)
	{
		try
		{
			dtm.addRow(newRow.toArray());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void deleteTable()
	{
		dtm.setRowCount(0);
	}
	
	private String getAppVersion()
	{
		try
		{
			return Util.getUtil().getApplicationVersionString();
		}
		catch (Throwable t)
		{
			return VERSION;
		}
	}
	
	private void loadDictionary()
	{
		loadDictionary(fd.show(lang.getTranslationString("ChooseJarFile", "Choose DictionaryForMIDs jar file to open", null), "LOAD", null, "DictionaryForMIDs.jar"));
	}
	
	private void loadDictionary(String fileName)
	{
		if (fileName == null)
		{
			if (languagesAll.size() == 0)
			{
				loadLanguages();
			}
		}
		else if (loadJar(fileName))
		{
			utilObj = new UtilWin();
			Util.setUtil(utilObj);
			try
			{
				try
				{
					DictionaryDataFile.initValues(false);
				}
				catch (DictionaryException e)
				{
					showDictInitError();
				}

				loadLanguages();
				lStatus.setText(lang.getTranslationString("DictionarySuccessfullyLoaded", "Dictionary successfully loaded: %1", (new File(fileName)).getName()));
				if (configLoaded)
				{
					saveDictHistory(fileName, false);
				}
			}
			catch (Throwable t)
			{
				//t.printStackTrace();
				loadLanguages();
				showDictionaryError(t.toString());
			}
		}
		else
		{
			showErrorFileNotFound(fileName);
			if (languagesAll.size() == 0)
			{
				loadLanguages();
			}
		}
	}
	
	private void loadLanguages()
	{
		try
		{
			languagesAll.clear();
			boolean colourSupportNeeded = false;
			
			for (int language = 0; language < DictionaryDataFile.numberOfAvailableLanguages; ++language)
			{
				languagesAll.addElement(DictionaryDataFile.supportedLanguages[language].languageDisplayText);
				
				if (DictionaryDataFile.supportedLanguages[language].contentDefinitionAvailable)
				{
					colourSupportNeeded = true;
				}
			}
			
			colourTextMenu.setEnabled(colourSupportNeeded);
			
			if (colourSupportNeeded)
			{
				colourText = colourTextMenu.isSelected();
			}
			else
			{
				colourText = colourSupportNeeded;
			}
			setTableComparator();
			
			if (languagesAll.size() == 0)
			{
				languagesAll.addElement("N/A");
			}
			
			leftLangSubMenu.removeAll();
			rightLangSubMenu.removeAll();
			menuBar.remove(1);
			menuBar.add(createPrefsMenu());
			((JRadioButtonMenuItem)leftLangSubMenu.getItem(0)).setSelected(true);
			((JRadioButtonMenuItem)rightLangSubMenu.getItem(rightLangSubMenu.getItemCount()-1)).setSelected(true);
			menuBar.validate();
			
			languages.clear();
			languages.addElement(languagesAll.elementAt(0));
			languages.addElement(languagesAll.lastElement());
			
			DictionarySettings.setInputLanguage(0);
			DictionarySettings.setOutputLanguage(createBooleanArray(rightLangSubMenu.getItemCount()-1, rightLangSubMenu.getItemCount()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Method create a boolean for selecting only one output language!
	private boolean[] createBooleanArray(int sel, int max)
	{
		boolean[] retValue = new boolean[max];
		for (int i=0; i<max; i++)
		{
			if (i == sel)
			{
				retValue[i] = true;
			}
			else
			{
				retValue[i] = false;
			}
		}
		
		return retValue;
	}
	
	private String prepareCopy(String text)
	{
		if (text.startsWith("<html>"))
		{
			text = text.replaceAll("<br>", System.getProperty("line.separator", "\n"));
			text = text.replaceAll("</?\\b[^>]*>", "");
		}
		
		return text;
	}
	
	// For table right click
	private void copyToClip()
	{
		String tmp = null;
		if (table.getSelectedRow() != -1)
		{
			tmp = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
		}
		
		tmp = prepareCopy(tmp);

		if (copyToClip(tmp))
		{
			setStatus(5000, lang.getTranslationString("SelectedTextCopied", "Selected text \"%1\" copied!", tmp));
		}
	}
	
	private boolean copyToClip(String message)
	{
		if ((message != null) && (message.length() > 0))
		{
			cont = new StringSelection(message);
			clip.setContents(cont, null);
			return true;
		}
		
		return false;
	}
	
	private void setStatus(int time, String text)
	{
		if (!timerStatus.isRunning())
		{
			oldStatus = lStatus.getText();
		}
		
		lStatus.setText(text);
		timerStatus.setInitialDelay(time);
		
		if (timerStatus.isRunning())
		{
			timerStatus.stop();
		}
		
		timerStatus.start();
	}
	
	private boolean loadJar(String jarFile)
	{
		try
		{
			if (jarFile == null)
			{
				return false;
			}
			
			File check = new File(jarFile);
			if (check.exists())
			{
				jar = new JarFile(check);
				// use this jar-file for access to dictionary data files:
				FileAccessHandler.setDictionaryDataFileISAccess(new JarInputStreamAccess(jar));
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	private void showDictionaryError(String error)
	{
		JOptionPane.showMessageDialog(this,
				lang.getTranslationString("DictionaryError", "You choose an incompatible dictionary!\nPlease make sure that you use an up to date version of DictionaryForMIDs!\nError: %1", error),
				lang.getTranslationString("Error", "Error", null),
				JOptionPane.WARNING_MESSAGE);
	}
	
	private void showDictInitError()
	{
		JOptionPane.showMessageDialog(this,
				lang.getTranslationString("DictionaryErrorOwnCode", "The dictionary uses own programcode and may not work as expected.\nPlease check for an update of the 'DictionaryForMIDs on PC' version!", null),
				lang.getTranslationString("Error", "Error", null),
				JOptionPane.WARNING_MESSAGE);
	}
	
	private void showSearchInProgress()
	{
		JOptionPane.showMessageDialog(this,
				lang.getTranslationString("SearchInProgress", "Search is already in progress!", null),
				lang.getTranslationString("Information", "Information", null),
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showDictInfo()
	{
		JOptionPane.showMessageDialog(this,
				DictionaryDataFile.infoText,
				lang.getTranslationString("DictionaryInformation", "Dictionary Information", null),
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showErrorFileNotFound(String path)
	{
		JOptionPane.showMessageDialog(this,
				lang.getTranslationString("DictionaryNotFound", "Dictionary File not found!\n%1", path),
				lang.getTranslationString("Error", "Error", null),
				JOptionPane.ERROR_MESSAGE);
	}
	
	private void performSearch(String search)
	{
		lastSearch = search;
		searchThread = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					lStatus.setText(lang.getTranslationString("Searching", "Searching, please wait...", null));
					boolean [] inputLanguages = new boolean[DictionaryDataFile.numberOfAvailableLanguages];
					for (int languageCount = 0; languageCount < DictionaryDataFile.numberOfAvailableLanguages; ++languageCount)
						inputLanguages[languageCount] = false;
					inputLanguages[DictionarySettings.getInputLanguage()] = true;
					boolean [] outputLanguages = DictionarySettings.getOutputLanguage();
					TranslationParameters translationParametersObj = 
								new TranslationParameters(lastSearch,
										                  inputLanguages,
										                  outputLanguages,
										                  false,  // no background execution
										                  DictionarySettings.getMaxHits(),
										                  DictionarySettings.getDurationForCancelSearch());
					TranslationExecution.executeTranslation(translationParametersObj);
				}
				catch (Throwable t)
				{
					lStatus.setText(lang.getTranslationString("SearchError", "Error while searching!", null));
					showDictionaryError(t.toString());
				}
			}
		});
		searchThread.start();
	}
	
	private void loadPrefs()
	{
		try
		{
			prop.load(new FileInputStream(CONFIG_NAME));
			
			loadDictionary(prop.getProperty("LoadedDictionary"));
			
			fontName = prop.getProperty("FontName", fontName);
			
			colourText = new Boolean(prop.getProperty("ColourText", new Boolean(colourText).toString())).booleanValue();
			colourTextMenu.setSelected(colourText);
			setTableComparator();
			
			languages.clear();
			languages.addElement(prop.getProperty("InputLanguage"));
			languages.addElement(prop.getProperty("OutputLanguage"));
			
			((JRadioButtonMenuItem)leftLangSubMenu.getItem(languagesAll.indexOf(languages.elementAt(0)))).setSelected(true);
			((JRadioButtonMenuItem)rightLangSubMenu.getItem(languagesAll.indexOf(languages.elementAt(1)))).setSelected(true);
			
			dictHistory.clear();
			for (int i=0; prop.getProperty("DictHistory." + Integer.toString(i)) != null; i++)
			{
				if (prop.getProperty("DictHistory." + Integer.toString(i)).length() > 0)
				{
					dictHistory.addElement(prop.getProperty("DictHistory." + Integer.toString(i)));
				}
			}
			
			deleteDoubleEntries();
			createDictHistoryMenu();
			changeFonts();
			configLoaded = true;
		}
		catch (Exception e)
		{
		}
	}
	
	private void savePrefs()
	{
		try
		{
			if (jar != null)
			{
				prop.setProperty("LoadedDictionary", jar.getName());
			}
			
			prop.setProperty("FontName", fontName);
			prop.setProperty("ColourText", new Boolean(colourText).toString());
			prop.setProperty("InputLanguage", languages.elementAt(0).toString());
			prop.setProperty("OutputLanguage", languages.elementAt(1).toString());
			
			prop.store(new FileOutputStream(CONFIG_NAME, false), "DictionaryForMIDs " + getAppVersion());
		}
		catch (Exception e)
		{
			lStatus.setText(lang.getTranslationString("ErrorConfigNotSaved", "Can't save configuration file to disk! Maybe write protected?", null));
		}
	}
	
	private void deleteDoubleEntries()
	{
		Vector tmpDB = new Vector();
		
		for (int i=0; i<dictHistory.size(); i++)
		{
			tmpDB.addElement(dictHistory.elementAt(i).toString().toUpperCase());
		}

		for (int i=0; i<dictHistory.size(); i++)
		{
			int tmp = tmpDB.indexOf(tmpDB.elementAt(i),i+1);
			if (tmp != -1)
			{
//				System.out.println("Remove: " + tmpDB.elementAt(i));
				tmpDB.removeElementAt(tmp);
				dictHistory.removeElementAt(tmp);
				i--;
			}
		}
	}
	
	private void saveDictHistory(Properties propTmp)
	{
		for (int i=0; i<dictHistory.size(); i++)
		{
			propTmp.setProperty("DictHistory." + Integer.toString(i), dictHistory.elementAt(i).toString());
		}
	}
	
	private void clearDictHistory(Properties propTmp)
	{
		for (int i=0; i<dictHistory.size(); i++)
		{
			propTmp.setProperty("DictHistory." + Integer.toString(i), "");
		}
		
		dictHistory.clear();
	}

	void saveDictHistory(String path, boolean clear)
	{
		if (path != null)
		{
			dictHistory.insertElementAt(path, 0);
			deleteDoubleEntries();
			
			if (dictHistory.size() > 10)
			{
				dictHistory.setSize(10);
			}
		}

		prop.clear();
		File checkConfig = new File(CONFIG_NAME);

		if (checkConfig.exists())
		{
			try
			{
				prop.load(new FileInputStream(checkConfig));
			}
			catch (Exception e)
			{
			}
		}

		if (clear)
		{
			clearDictHistory(prop);
		}
		else
		{
			saveDictHistory(prop);
		}

		try
		{
			prop.store(new FileOutputStream(CONFIG_NAME, false), getAppVersion());
		}
		catch (Exception e)
		{
		}
		
		createDictHistoryMenu();
	}
	
	/**
	 * Open the Prefs Dialog for changing the font!
	 */
	private void openFontPrefsDialog()
	{
		// Get the Font list from OS
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		ppd = new JDialog(this, lang.getTranslationString("FontPrefsDialog", "Font Prefs Dialog", null), true);
		ppd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JPanel ppdRoot = (JPanel)ppd.getContentPane();
		lFontTest = new JLabel(lang.getTranslationString("DemoText", "Demo Text: DictionaryForMIDs", null));
		bUse = new JButton(lang.getTranslationString("Use", "Use", null));
		bUse.addActionListener(this);
		bCancelPrefs = new JButton(lang.getTranslationString("Cancel", "Cancel", null));
		bCancelPrefs.addActionListener(this);
		JToolBar tbBottom = new JToolBar();
		cbFont = new JComboBox(fontNames);
		cbFont.setSelectedItem(fontName);
		cbFont.addActionListener(this);
		cbFont.setMaximumRowCount(20);
		
		lFontTest.setFont(new Font(cbFont.getSelectedItem().toString(), Font.BOLD, 12));

		tbBottom.setLayout(new FlowLayout());
		tbBottom.setFloatable(false);
		tbBottom.add(bUse);
		tbBottom.add(bCancelPrefs);

		ppdRoot.setLayout(new BorderLayout());
		ppdRoot.add(lFontTest, BorderLayout.NORTH);
		ppdRoot.add(cbFont, BorderLayout.CENTER);
		ppdRoot.add(tbBottom, BorderLayout.SOUTH);

		ppd.setSize(300,110);
		ppd.setLocationRelativeTo(this);
		ppd.setResizable(false);
		ppd.setVisible(true);
	}
	
	/**
	 * Close the font prefs dialog
	 */
	private void closeFontPrefsDialog()
	{
		ppd.setVisible(false);
		ppd.dispose();
	}
	
	private void changeFonts()
	{
		tfLeft.setFont(new Font(fontName, Font.PLAIN, 12));
		tfRight.setFont(new Font(fontName, Font.PLAIN, 12));
		table.setFont(new Font(fontName, Font.PLAIN, 12));
	}
	
	private String getColouredHTMLText(String text, int languageIndex)
	{
		try
		{
			ContentParser cp = new ContentParser();
			StringColourItemText result = cp.determineItemsFromContent(new TextOfLanguage(text, languageIndex), false, false);
			StringBuffer returnString = new StringBuffer();
			String lastColour = null;
			
			if (colourText)
			{
				returnString.append("<html><nobr>");
			}
			
			for (int i=0; i<((StringColourItemText)result).size(); i++)
			{
				StringColourItemTextPart part = ((StringColourItemText)result).getItemTextPart(i);
								
				if (colourText)
				{	
					lastColour = part.getColour().getHexValue();
					returnString.append("<font color=\"");
					returnString.append(lastColour);
					returnString.append("\">");
					
					switch (part.getStyle().style)
					{
						case FontStyle.bold: returnString.append("<b>");
											break;
						case FontStyle.italic: returnString.append("<i>");
											break;
						case FontStyle.underlined: returnString.append("<u>");
											break;
					}
					
					returnString.append(part.getText().replaceAll("\n", "</font></nobr><br><nobr><font color=\""+lastColour+"\">"));
					
					switch (part.getStyle().style)
					{
						case FontStyle.bold: returnString.append("</b>");
											break;
						case FontStyle.italic: returnString.append("</i>");
											break;
						case FontStyle.underlined: returnString.append("</u>");
											break;
					}
					
					returnString.append("</font>");
				}
				else
				{
					returnString.append(part.getText().replaceAll("\n", " - "));
				}
			}
			
			if (colourText)
			{
				returnString.append("</nobr></html>");
			}
			
			return returnString.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return text;
		}
	}

	// Method from TranslationExecutionCallback Interface
	public void deletePreviousTranslationResult()
	{
		deleteTable();
	}
	
	public void newTranslationResult(TranslationResult resultOfTranslation)
	{
		Vector tmp = new Vector();
		// display result of new translation
		if (resultOfTranslation.translationFound())
		{
			Enumeration translationsEnum = resultOfTranslation.getAllTranslations();
			while (translationsEnum.hasMoreElements())
			{
				tmp.clear();
				SingleTranslation singleTranslation = (SingleTranslation)translationsEnum.nextElement();
				String fromTextString = ((TextOfLanguage) singleTranslation.getFromText()).getText();
				String toTextString = ((TextOfLanguage) singleTranslation.getToTexts().elementAt(0)).getText(); // only use 1st element
				// Add results to Vector
				if (rightSearch)
				{
					tmp.addElement(getColouredHTMLText(toTextString, languagesAll.indexOf(languages.elementAt(0))));
					tmp.addElement(getColouredHTMLText(fromTextString, languagesAll.indexOf(languages.elementAt(1))));
				}
				else
				{
					tmp.addElement(getColouredHTMLText(fromTextString, languagesAll.indexOf(languages.elementAt(0))));
					tmp.addElement(getColouredHTMLText(toTextString, languagesAll.indexOf(languages.elementAt(1))));
				}
				addTableRow(tmp);
			}
			
			lStatus.setText(lang.getTranslationString("OptimizeColumnWidth", "Optimize column width, please wait...", null));
			((SortedTable)table).sizeColumnsOptimal();
			String[] replace = {Long.toString(resultOfTranslation.numberOfFoundTranslations()), Double.toString(resultOfTranslation.executionTime/1000.0)};
			lStatus.setText(lang.getTranslationStringArray("SearchResult", "%1 entries found in %2 seconds!", replace));
		}
		else
		{
			// Nothing found
			lStatus.setText(lang.getTranslationString("NothingFound", "Nothing found for \"%1\"!", lastSearch));
			setTableWidth();
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == tfLeft)
		{
			DictionarySettings.setInputLanguage(languagesAll.indexOf(languages.elementAt(0)));
			DictionarySettings.setOutputLanguage(createBooleanArray(languagesAll.indexOf(languages.elementAt(1)), languagesAll.size()));
			
			if (searchThread != null)
			{
				if (searchThread.isAlive())
				{
					showSearchInProgress();
				}
				else
				{
					rightSearch = false;
					performSearch(tfLeft.getText());
				}
			}
			else
			{
				rightSearch = false;
				performSearch(tfLeft.getText());
			}
			
			tfLeft.selectAll();
		}
		else if (e.getSource() == tfRight)
		{
			DictionarySettings.setInputLanguage(languagesAll.indexOf(languages.elementAt(1)));
			DictionarySettings.setOutputLanguage(createBooleanArray(languagesAll.indexOf(languages.elementAt(0)), languagesAll.size()));

			if (searchThread != null)
			{
				if (searchThread.isAlive())
				{
					showSearchInProgress();
				}
				else
				{
					rightSearch = true;
					performSearch(tfRight.getText());
				}
			}
			else
			{
				rightSearch = true;
				performSearch(tfRight.getText());
			}
			
			tfRight.selectAll();
		}
		else if (e.getSource().equals(colourTextMenu))
		{
			colourText = colourTextMenu.isSelected();
			setTableComparator();
		}
		else if (e.getSource().equals(timerStatus))
		{
			lStatus.setText(oldStatus);
		}
		else if (e.getActionCommand().startsWith("LangL"))
		{
			DictionarySettings.setInputLanguage(languagesAll.indexOf(e.getActionCommand().substring(5)));
			languages.setElementAt(e.getActionCommand().substring(5), 0);
			fillTableColums();
		}
		else if (e.getActionCommand().startsWith("LangR"))
		{
			DictionarySettings.setOutputLanguage(createBooleanArray(languagesAll.indexOf(e.getActionCommand().substring(5)), languagesAll.size()));
			languages.setElementAt(e.getActionCommand().substring(5), 1);
			fillTableColums();
		}
		else if (e.getActionCommand().equals("openDict"))
		{
			loadDictionary();
			fillTableColums();
		}
		else if (e.getActionCommand().startsWith("HistoryOpen"))
		{
			loadDictionary(e.getActionCommand().substring(11));
			fillTableColums();
		}
		else if (e.getActionCommand().equals("dictInfo"))
		{
			showDictInfo();
		}
		else if (e.getActionCommand().equals("about"))
		{
			JOptionPane.showMessageDialog(this,
					"DictionaryForMIDs " + getAppVersion() + " (17.08.2008)\n(c) 2005-2008 by Stefan Martens & Gert Nuber\n\n" + lang.getTranslationString("Language", "Language: English", null) + "\n" + lang.getTranslationString("TransBy", "Translation by Stefan1200", null) + "\n\n" + lang.getTranslationString("VisitHomepages", "Visit our homepages:", null) + "\nhttp://dictionarymid.sourceforge.net\nhttp://www.stefan1200.de",
					lang.getTranslationString("About", "About", null),
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if (e.getActionCommand().equals("quit"))
		{
			this.setVisible(false);
			this.dispose();
			System.exit(0);
		}
		else if (e.getActionCommand().equals("savePrefs"))
		{
			savePrefs();
		}
		else if (e.getActionCommand().equals("changeFont"))
		{
			openFontPrefsDialog();
		}
		else if (e.getActionCommand().equals("clearHistory"))
		{
			saveDictHistory(null, true);
		}
		else if (e.getSource().equals(cbFont))
		{
			lFontTest.setFont(new Font(cbFont.getSelectedItem().toString(), Font.BOLD, 12));
		}
		else if (e.getSource().equals(bUse))
		{
			fontName = cbFont.getSelectedItem().toString();
			closeFontPrefsDialog();
			changeFonts();
		}
		else if (e.getSource().equals(bCancelPrefs))
		{
			closeFontPrefsDialog();
		}
		else if (e.getActionCommand().equals("LpmCopy"))
		{
			tfLeft.copy();
		}
		else if (e.getActionCommand().equals("RpmCopy"))
		{
			tfRight.copy();
		}
		else if (e.getActionCommand().equals("LpmCopyAll"))
		{
			copyToClip(tfLeft.getText());
		}
		else if (e.getActionCommand().equals("RpmCopyAll"))
		{
			copyToClip(tfRight.getText());
		}
		else if (e.getActionCommand().equals("LpmCut"))
		{
			tfLeft.cut();
		}
		else if (e.getActionCommand().equals("RpmCut"))
		{
			tfRight.cut();
		}
		else if (e.getActionCommand().equals("LpmPaste"))
		{
			tfLeft.paste();
		}
		else if (e.getActionCommand().equals("RpmPaste"))
		{
			tfRight.paste();
		}
		else if (e.getActionCommand().equals("LpmPasteReplace"))
		{
			tfLeft.selectAll();
			tfLeft.paste();
		}
		else if (e.getActionCommand().equals("RpmPasteReplace"))
		{
			tfRight.selectAll();
			tfRight.paste();
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		if (e.getSource().equals(table))
		{
			if (e.isPopupTrigger())
			{
				copyToClip();
			}
		}
		else if (e.getSource().equals(tfLeft))
		{
			if (e.isPopupTrigger())
			{
				pmListL.show(tfLeft, e.getX(), e.getY());
			}
		}
		else if (e.getSource().equals(tfRight))
		{
			if (e.isPopupTrigger())
			{
				pmListR.show(tfRight, e.getX(), e.getY());
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		if (e.getSource().equals(table))
		{
			if (e.isPopupTrigger())
			{
				copyToClip();
			}
		}
		else if (e.getSource().equals(tfLeft))
		{
			if (e.isPopupTrigger())
			{
				pmListL.show(tfLeft, e.getX(), e.getY());
			}
		}
		else if (e.getSource().equals(tfRight))
		{
			if (e.isPopupTrigger())
			{
				pmListR.show(tfRight, e.getX(), e.getY());
			}
		}
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}
	
	public void insertUpdate(DocumentEvent e)
	{
		if (tfLeft.getDocument() == e.getDocument())
		{
//			ToDo: incremental search
		}
		else if (tfRight.getDocument() == e.getDocument())
		{
//			ToDo: incremental search
		}
	}
	
	public void removeUpdate(DocumentEvent e)
	{
		if (tfLeft.getDocument() == e.getDocument())
		{
//			ToDo: incremental search
		}
		else if (tfRight.getDocument() == e.getDocument())
		{
//			ToDo: incremental search
		}
	}
	
	public void changedUpdate(DocumentEvent e)
	{
		// not used!
	}
}

class MyTableCellRenderer extends DefaultTableCellRenderer
//For more info see class DefaultTableCellRenderer
{
	public MyTableCellRenderer()
	{
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
	     boolean isSelected, boolean hasFocus, int row, int column)
	{
		if (isSelected)
		{
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		}
		else
		{
			super.setForeground(table.getForeground());
			super.setBackground(table.getBackground());
		}
		
		if (hasFocus)
		{
            Border border = null;
            if (isSelected)
            {
                border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
            }
            if (border == null)
            {
                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }
            setBorder(border);

		    if (!isSelected && table.isCellEditable(row, column))
		    {
	                Color col;
	                col = UIManager.getColor("Table.focusCellForeground");
	                if (col != null)
	                {
	                    super.setForeground(col);
	                }
	                col = UIManager.getColor("Table.focusCellBackground");
	                if (col != null)
	                {
	                    super.setBackground(col);
	                }
		    }
		}
		else
		{
		    setBorder(noFocusBorder);
		}

		setFont(table.getFont());

		setText(null);
		setIcon(null);
		
		if (value == null)
		{
			setText("null");
		}
		else if (value instanceof Icon)
		{
			setIcon((Icon)value);
		}
		else if (value instanceof Color)
		{
			Color color = (Color)value;
			setForeground(color);
			setText(color.getRed() + ", " + color.getGreen() + ", " + color.getBlue());
		}
		else if (value instanceof Boolean)
		{
			if(((Boolean)value).booleanValue())
			{
				setText("yes");
			}
			else
			{
				setText("no");
			}
		}
		else
		{
			setText(value.toString());
		}

		switch (column)
		{
			case 0: setHorizontalAlignment(LEFT); break;
			case 1: setHorizontalAlignment(LEFT); break;
			case 2: setHorizontalAlignment(LEFT); break;
			case 3: setHorizontalAlignment(LEFT); break;
			case 4: setHorizontalAlignment(LEFT); break;
			default: setHorizontalAlignment(LEFT); break;
		}
		
		if (table.getRowHeight(row) < getPreferredSize().height)
		{
			table.setRowHeight(row, getPreferredSize().height);
		}

		return this;
	}
}

class MyHeaderCellRenderer extends JLabel implements TableCellRenderer
{
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		// text
		setText(value.toString());
		// normale schriftart
		setFont(new Font(table.getFont().getName(), Font.BOLD, table.getFont().getSize()));
		// der standard rahmen für spaltenköpfe
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		// text zentriert darstellen
		setHorizontalAlignment(SwingConstants.CENTER);

		return this;
	}
}

// Using AWT FileDialog because of faster loading on slow systems!
class AWTFileDialog extends Frame
{
	private String saveLastDir = System.getProperty("user.dir","");
	
	String show(String title, String mode, String dir, String fileName)
	{
		if (dir == null)
		{
			dir = saveLastDir;
		}

		if (mode.equalsIgnoreCase("LOAD"))
		{
			FileDialog chooser = new FileDialog(this, title, FileDialog.LOAD);
			chooser.setDirectory(dir);
			chooser.setFile(fileName);
			chooser.setModal(true);
			chooser.show();
			if (chooser.getFile() == null)
			{
				return null;
			}
			else
			{
				saveLastDir = chooser.getDirectory();
				return chooser.getDirectory() + chooser.getFile();
			}
		}
		else if (mode.equalsIgnoreCase("SAVE"))
		{
			FileDialog chooser = new FileDialog(this, title, FileDialog.SAVE);
			chooser.setDirectory(dir);
			chooser.setFile(fileName);
			chooser.setModal(true);
			chooser.show();
			if (chooser.getFile() == null)
			{
				return null;
			}
			else
			{
				saveLastDir = chooser.getDirectory();
				return chooser.getDirectory() + chooser.getFile();
			}
		}
		if (mode.equalsIgnoreCase("DIR"))
		{
			FileDialog chooser = new FileDialog(this, title, FileDialog.LOAD);
			chooser.setDirectory(dir);
			chooser.setFile(fileName);
			chooser.setModal(true);
			chooser.show();
			if (chooser.getFile() == null)
			{
				return null;
			}
			else
			{
				saveLastDir = chooser.getDirectory();
				return chooser.getDirectory();
			}
		}
		else
		{
			return null;
		}
	}
}

/**
 * Class for better ignore case sorting
 */
class IgnoreCaseComparator
implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		String s1 = (String)o1;
		String s2 = (String)o2;
		return s1.toLowerCase().compareTo(s2.toLowerCase());
	}
}

/**
 * Class for HTML string and ignore case sorting
 */
class HTMLStringComparator
implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		String s1 = (String)o1;
		String s2 = (String)o2;
		return s1.replaceAll("<[\\/\\!]*?[^<>]*?>", "").toLowerCase().compareTo(s2.replaceAll("<[\\/\\!]*?[^<>]*?>", "").toLowerCase());
	}
}