package de.kugihan.dictionaryformids.hmi_java_se;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * A simple class for loading and returning translations for programs.<br>
 * <br>
 * If you want to try to load a translation file of the system language,
 * simply call loadTranslation() one time.<br>
 * <br>
 * On a german os, this class try by default to open the translation file in
 * <b>Language/german.lng</b><br>
 * <br>
 * The directory, file prefix and suffix can be changed.
 * 
 * @author Stefan1200
 * @version 1.0 (17.08.2008)
 */
public class AppTranslation
{
	private String languageDirectory = "Languages";
	private String languageFilePrefix = null;
	private String languageFileSuffix = ".lng";
	private String fileSeparator = System.getProperty("file.separator","\\");
	private Properties language = new Properties();
	
	/**
	 * Returns a translation String from an already loaded Properties file.
	 * 
	 * @param key The Properties key in the translation file.
	 * @param defaultString The default String to be returned, if key does not exists.
	 * @param replace An optional replace String.
	 *        %1 in the translation string will be replaced with this String,
	 *        If replace is null, nothing will be replaced.
	 * @return The translated String or the defaultString, if no translation was found.  
	 */
	public String getTranslationString(String key, String defaultString, String replace)
	{
		if (replace == null)
		{
			return getTranslationStringArray(key, defaultString, null);
		}
		else
		{
			String[] replaceArray = {replace};
			return getTranslationStringArray(key, defaultString, replaceArray);
		}
	}
	
	/**
	 * Returns a translation String from an already loaded Properties file.
	 * 
	 * @param key The Properties key in the translation file.
	 * @param defaultString The default String to be returned, if key does not exists.
	 * @param replace An optional String Array with replace strings.
	 *        %1 in the translation string will be replaced with the first array entry,
	 *        %2 with the second, and so on.
	 *        If replace is null, nothing will be replaced.
	 * @return The translated String or the defaultString, if no translation was found.  
	 */
	public String getTranslationStringArray(String key, String defaultString, String[] replace)
	{
		StringBuffer sb;
		if (language != null)
		{
			sb = new StringBuffer(language.getProperty(key, defaultString));
		}
		else
		{
			sb = new StringBuffer(defaultString);
		}
		
		if (replace == null)
		{
			return sb.toString();
		}
		
		int pos = -1;
		for (int i=0; i<replace.length; i++)
		{
			pos = sb.indexOf("%" + Integer.toString(i+1));
			if (pos == -1)
			{
				continue;
			}
			
			sb.replace(pos, pos+2, replace[i]);
		}
		
		return sb.toString();
	}
	
	/**
	 * Loads a translation file of the default system language.
	 * 
	 * @return true if loading translation file was successful, false if was not.
	 */
	public boolean loadTranslation()
	{
		return loadTranslation(Locale.getDefault().getDisplayLanguage(Locale.ENGLISH).toLowerCase());
	}
	
	/**
	 * Loads a translation file of the specified language.
	 * 
	 * @param languageName The name of the language
	 * @return true if loading translation file was successful, false if was not.
	 */
	public boolean loadTranslation(String languageName)
    {
    	boolean retValue = false;
    	
    	if (language == null)
    	{
    		language = new Properties();
    	}
    	
    	language.clear();

    	try
		{
			File checkLang = new File(languageDirectory + fileSeparator + ((languageFilePrefix == null) ? "" : languageFilePrefix) + languageName + ((languageFileSuffix == null) ? "" : languageFileSuffix));

			if (checkLang.exists())
			{
				language.load(new FileInputStream(checkLang));
				retValue = true;
			}
		}
		catch (IOException e)
		{
		}
		
		return retValue;
	}

    /**
     * Returns the directory of the language files. Default is Languages.
     * 
     * @return Directory as String
     */
	public String getLanguageDirectory()
	{
		return languageDirectory;
	}

	/**
	 * Sets a new directory for the language files.
	 * 
	 * @param languageDirectory The directory as String
	 */
	public void setLanguageDirectory(String languageDirectory)
	{
		this.languageDirectory = languageDirectory;
	}

	/**
	 * Returns the filename prefix of the language file.<br>
	 * Default is <i>null</i> for no prefix.
	 * 
	 * @return Filename prefix as String or <i>null</i> if no prefix is set.
	 */
	public String getLanguageFilePrefix()
	{
		return languageFilePrefix;
	}

	/**
	 * Sets a new filename prefix of the language file.
	 * 
	 * @param languageFilePrefix The filename prefix or <i>null</i> for no prefix
	 */
	public void setLanguageFilePrefix(String languageFilePrefix)
	{
		this.languageFilePrefix = languageFilePrefix;
	}

	/**
	 * Returns the filename suffix of the language file.<br>
	 * Default is .lng
	 * 
	 * @return Filename suffix as String or <i>null</i> if no suffix is set.
	 */
	public String getLanguageFileSuffix()
	{
		return languageFileSuffix;
	}

	/**
	 * Sets a new filename suffix of the language file.
	 * 
	 * @param languageFileSuffix The filename suffix or <i>null</i> for no suffix
	 */
	public void setLanguageFileSuffix(String languageFileSuffix)
	{
		this.languageFileSuffix = languageFileSuffix;
	}
}
