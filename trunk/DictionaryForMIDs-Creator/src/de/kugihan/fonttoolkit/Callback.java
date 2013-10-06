/**
 * DictionaryForMIDs
 *
 * Interface.java - Created by Sean Kernohan (webmaster@seankernohan.com)
 */
package de.kugihan.fonttoolkit;

public interface Callback {

    public void finish();

    public void start();

    public void progressing();

    public void throwThreadedFontException();

    public void throwThreadedDictionaryException();
}
