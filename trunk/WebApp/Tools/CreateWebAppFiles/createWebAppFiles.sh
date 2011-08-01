# Parameter 1 is the subdirectory in which the dictionary that shall be created is found
# e.g. "dictionary Wiktionary"
# This is a subdirectory of /home/frs/project/d/di/dictionarymid 

dictionarySubDir=$1

projFRSBase=/home/frs/project/d/di/dictionarymid 
projWebAppBase=/home/project-web/dictionarymid/htdocs/WebApp
projWebAppDictionaries=$projWebAppBase/dictionaries

dictionaryFRSDir=$projFRSBase/$dictionarySubDir

newestVersionDir=`ls "$dictionaryFRSDir" | sort | tail -n1`

dictionaryFRSSourceDir=$dictionaryFRSDir/$newestVersionDir

dictionaryWebAppDestDir=$projWebAppDictionaries/$dictionarySubDir

mkdir "$dictionaryWebAppDestDir"

unzip "$dictionaryFRSSourceDir/"*.zip "*.jar" -d "$dictionaryWebAppDestDir"

unzip "$dictionaryWebAppDestDir"/*.jar "dictionary/*"  -x "dictionary/fonts/*" -d "$dictionaryWebAppDestDir"
unzip "$dictionaryWebAppDestDir"/*.jar "Application.properties"  -d "$dictionaryWebAppDestDir"

# remove the jar file in the destination directory (the jar file is not needed any more)
rm "$dictionaryWebAppDestDir"/*.jar

# now create the cache manifest

cacheFile=$dictionaryWebAppDestDir/cache_dictionary.manifest

echo "CACHE MANIFEST" > "$cacheFile"
echo "# cache manifest automatically generaed by script" >> "$cacheFile"
echo "Application.properties" >> "$cacheFile"

pushd .
cd "$dictionaryWebAppDestDir"
ls -1 "dictionary/"* >> "$cacheFile"


# create the links
ln -s ../../Apps/dictionary_common/.htaccess .
ln -s ../../Apps/dictionary_common/cache_mini_hmi.manifest .
ln -s ../../Apps/dictionary_common/mini_hmi.php .
ln -s ../../Apps/dictionary_common/index.php .

popd


echo Done
