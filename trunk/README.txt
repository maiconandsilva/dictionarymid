

== How to compile the sources ==

Check out the whole source tree from SVN

Edit Build/build.xml and change 
	<property name="wtk.home"  location="D:\WTK25"/>
to the corrrect path

$ cd Build
$ ant

now Build directory will contain the compiled result.



== Editing languages ==

If you have changed the J2ME version language file 
( JavaME/src/de/kugihan/dictionaryformids/hmi_java_me/uidisplaytext/DictionaryForMIDs.languages ), run

$ cd Build
$ java -jar LanguageUIGeneration/LanguageUIGeneration.jar ../JavaME/src/de/kugihan/dictionaryformids/hmi_java_me/uidisplaytext/  ../JavaME/src/de/kugihan/dictionaryformids/hmi_java_me/uidisplaytext/

and recompile:

$ ant



