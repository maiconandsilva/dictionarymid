#
# For description see http://dictionarymid.sourceforge.net/WebApp/htdocs/WebAppCreateDictionaries.html
#
# Copyrigth 2013 by DictionaryForMIDs community
# GPLv2 applies
#

projFRSBase=/home/frs/project/d/di/dictionarymid
projWebAppBase=/home/project-web/dictionarymid/htdocs/WebApp
projWebAppDictionaries=$projWebAppBase/dictionaries
scriptFilesDir=$projWebAppDictionaries/scriptFiles
tempDir=$scriptFilesDir/tmp
listOfDictionariesFile=$tempDir/listOfDictionaries

(cd "$projFRSBase"; ls -d1 dictionary\ * > "$listOfDictionariesFile")
while read dictionarySubdirectory
do
  ./createWebAppFiles.sh "$dictionarySubdirectory"
done <"$listOfDictionariesFile"
