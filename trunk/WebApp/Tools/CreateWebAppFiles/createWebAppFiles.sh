#
# For description see http://dictionarymid.sourceforge.net/WebApp/htdocs/WebAppCreateDictionaries.html
#
# Copyrigth 2013 by DictionaryForMIDs community
# GPLv2 applies
#
# Parameter 1 is the subdirectory in which the dictionary that shall be created is found
# e.g. "dictionary Wiktionary"
# This is a subdirectory of /home/frs/project/d/di/dictionarymid 
#

if [ $# -ne 1 ]
  then
  echo "usage: createWebAppFiles.sh <dictionary_subdirectory>"
  exit 1 
fi

dictionarySubDir=$1

projFRSBase=/home/frs/project/d/di/dictionarymid 
projWebAppBase=/home/project-web/dictionarymid/htdocs/WebApp
projWebAppDictionaries=$projWebAppBase/dictionaries

dictionaryFRSDir=$projFRSBase/$dictionarySubDir

if [ ! -d "$dictionaryFRSDir" ] 
  then
  echo "dictionary subdirectory not found: " $dictionaryFRSDir
  echo "stopped."
  exit 1
fi

newestVersionDir=`ls -1v "$dictionaryFRSDir" | grep [0123456789] | tail -n1`

dictionaryFRSSourceDir=$dictionaryFRSDir/$newestVersionDir

dictionaryWebAppDestDir=$projWebAppDictionaries/$dictionarySubDir

# function overwriteBOM: a temporary wild hack to work around problems with binary reading of UTF files
# $1 is the file including path that is checked
# $2 is the filename in the directory $scriptFiles that contains the BOM bytes (only file name, no directory)

function overwriteBOM {
  scriptFilesDir=$projWebAppDictionaries/scriptFiles
  tempDir=$scriptFilesDir/tmp
  extractFile=$tempDir/extractFile.bin
  nullFile=$tempDir/nullFile.bin
  withoutFirstByteFile=$tempDir/withoutFirstByteFile.bin

  BOMfile=$scriptFilesDir/$2

  if [ ! -f "$1" ] 
    then
    echo "overwriteBOM: file was not found: " $1
    echo "stopped."
    exit 1
  fi

  if [ ! -f "$BOMfile" ] 
    then
    echo "overwriteBOM: BOM file was not found: " $BOMfile
    echo "stopped."
    exit 1
  fi

  fileSize=`stat -c %s "$BOMfile"`

  dd ibs=1 obs=1 count=$fileSize if="$1" of="$extractFile" 2> /dev/null
  cmp "$extractFile" "$BOMfile" > /dev/null
  if [ $? -eq 0 ]
    then
    # replace first bytes with a 0 (this 'destroys' the BOM)
    dd ibs=1 obs=1 count=$fileSize if=/dev/zero of="$nullFile" 2> /dev/null
    dd ibs=1 obs=1 skip=$fileSize if="$1" of="$withoutFirstByteFile" 2> /dev/null
    cat "$nullFile" "$withoutFirstByteFile" > "$1"
    echo "Replaced BOM in" $1 "("$fileSize" bytes)"
  fi
}

mkdir "$dictionaryWebAppDestDir"

unzip "$dictionaryFRSSourceDir/"*.zip "*.jar" -d "$dictionaryWebAppDestDir"

unzip "$dictionaryWebAppDestDir"/*.jar "dictionary/*"  -x "dictionary/fonts/*" -d "$dictionaryWebAppDestDir"
unzip "$dictionaryWebAppDestDir"/*.jar "Application.properties"  -d "$dictionaryWebAppDestDir"

# remove any BOM characters at the beginning of directory1 file
directoryFile1="$dictionaryWebAppDestDir"/dictionary/directory1.csv
overwriteBOM "$directoryFile1" BOMutf8.bin
overwriteBOM "$directoryFile1" BOMutf16be.bin
overwriteBOM "$directoryFile1" BOMutf16le.bin

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


