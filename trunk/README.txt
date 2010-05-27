

== How to compile the sources ==


Check out the whole source tree from SVN

Edit Build/build.xml and change 
	<property name="wtk.home"  location="D:\WTK25"/>
to the corrrect path

$ cd Build
$ ant

now Build directory will contain the compiled result of all the sources.


Full instructions can be found at http://dictionarymid.sourceforge.net/development.html#Source



== Editing translations ==

If you have changed the J2ME version language file 
( JavaME/src/de/kugihan/dictionaryformids/hmi_java_me/uidisplaytext/DictionaryForMIDs.languages ), run

$ cd Build
$ java -jar LanguageUIGeneration/LanguageUIGeneration.jar ../JavaME/src/de/kugihan/dictionaryformids/hmi_java_me/uidisplaytext/  ../JavaME/src/de/kugihan/dictionaryformids/hmi_java_me/uidisplaytext/

This will change the actual Java class that contains the translations (JavaME/src/de/kugihan/dictionaryformids/hmi_java_me/uidisplaytext/UIDisplayTextContents.java - don't edit this directly, use the tool).

Next you have to recompile:

$ ant


Full instructions can be found at http://dictionarymid.sourceforge.net/LanguageUIGeneration.html

