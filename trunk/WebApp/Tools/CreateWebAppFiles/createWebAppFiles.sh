# Parameter 1 is the subdirectory in which the dictionary that shall be created is found
# e.g. "dictionary Wiktionary"
# This is a subdirectory of /home/frs/project/d/di/dictionarymid 

dictionarySubDir=$1

projFRSBase=/home/frs/project/d/di/dictionarymid 
projWebAppBase=/home/project-web/dictionarymid/htdocs/WebApp
projWebAppDictionaries=$projWebAppBase/dictionaries

dictionaryFRSDir=$projFRSBase/$dictionarySubDir

newestVersionDir=`ls "$dictionaryFRSDir" | grep [0123456789] | sort | tail -n1`

dictionaryFRSSourceDir=$dictionaryFRSDir/$newestVersionDir

dictionaryWebAppDestDir=$projWebAppDictionaries/$dictionarySubDir

mkdir "$dictionaryWebAppDestDir"

unzip "$dictionaryFRSSourceDir/"*.zip "*.jar" -d "$dictionaryWebAppDestDir"

unzip "$dictionaryWebAppDestDir"/*.jar "dictionary/*"  -x "dictionary/fonts/*" -d "$dictionaryWebAppDestDir"
unzip "$dictionaryWebAppDestDir"/*.jar "Application.properties"  -d "$dictionaryWebAppDestDir"

# remove any BOM characters at the beginning of directory files
directoryFilesList=dictionary/directory*.csv
for directoryFile in $directoryFilesList
do
  overwriteBOM $directoryFile BOMutf8.bin
  overwriteBOM $directoryFile BOMutf16be.bin
  overwriteBOM $directoryFile BOMutf16le.bin
done

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


# function overwriteBOM: a temporary wild hack to work around problems with binary reading of UTF files
# $1 is the file including path that is checked
# $2 is the filename in the directory $scriptFiles that contains the BOM bytes (only file name, no directory)

function overwriteBOM {
  projWebAppDictionaries=/home/project-web/dictionarymid/htdocs/WebApp/dictionaries  ### todo
  scriptFilesDir=$projWebAppDictionaries/scriptFiles
  tempDir=$scriptFilesDir/tmp
  extractFile=$tempDir/extractFile.bin
  nullFile=$tempDir/nullFile.bin
  withoutFirstByteFile=$tempDir/withoutFirstByteFile.bin

  BOMfile=$scriptFilesDir/$2

  fileSize=`stat -c %s $BOMfile`

  dd ibs=1 obs=1 count=$fileSize if=$1 of=$extractFile 2> /dev/null
  cmp $extractFile $BOMfile > /dev/null
  if [ $? -eq 0 ]
    then
    # replace first bytes with a 0 (this 'destroys' the BOM)
    dd ibs=1 obs=1 count=$fileSize if=/dev/zero of=$nullFile 2> /dev/null
    dd ibs=1 obs=1 skip=$fileSize if=$1 of=$withoutFirstByteFile 2> /dev/null
    cat $nullFile $withoutFirstByteFile > $1
    echo "Replaced BOM in" $1 "("$fileSize" bytes)"
  fi
}

