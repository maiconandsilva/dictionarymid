#!/bin/bash
# build.sh
# Copyright (C) 2012, 2013 Karim Mahamane Karimou
# License: Under the GPL.

# Current version of DfM-Creator
VERSION=0.6-beta
DFM_CREATOR=DfM-Creator-$VERSION.jar

# The location of the build.xml file
DFM_CREATOR_PATH=/media/X-SOFTS/DictionaryForMIDs/code/trunk/Build

# The location of the jar file that will be created by ant
DFM_CREATOR_JAR=/media/X-SOFTS/DictionaryForMIDs/code/trunk/Build/DictionaryForMIDs-Creator/$DFM_CREATOR

# Where the created jar file will be copied to (backup)
OUTPUT_LOCATION=/media/Khorasan/DfM-Creator/DfM-Creator-$VERSION-Windows-portable/dist

# Older jar file that will have to be deleted and replaced with the new one
DFM_CREATOR_OLDER_JAR=$OUTPUT_LOCATION/$DFM_CREATOR

# These are for svn export
# Absolute path to the DfM-Creator source directory
DFM_CREATOR_SOURCES_SOURCE=/media/X-SOFTS/DictionaryForMIDs/code/trunk/DictionaryForMIDs-Creator

# Location where the sources will be copied to
DFM_CREATOR_SOURCES_DESTINATION=$OUTPUT_LOCATION

# Path to the copied DfM-Creator sources.
# This will also act as the path to the older
# sources' directory between builds...
DFM_CREATOR_SOURCES_DESTINATION_DIR=$DFM_CREATOR_SOURCES_DESTINATION/DictionaryForMIDs-Creator

# Directory where the web documentation files will be moved to
# in order to provide the user with complete documentation
DFM_CREATOR_DESTINATION_DOC_DIR=$OUTPUT_LOCATION/../doc

# This directory will be deleted because it contains jars and sources for DfM-Creator
DFM_WEB_DOC_JARS_AND_SRC_DIR=$DFM_CREATOR_DESTINATION_DOC_DIR/files

# This varible will be used to delete the 'files' directory located
# in the web-documentation folder that contains the distributed
# sources and binaries of DfM-Creator
WEB_DOCUMENTATION_DIRECTORY=/media/X-SOFTS/DictionaryForMIDs/code/trunk/Documentation/DfM-Creator_Web_Documentation


# These are for 7z
DFM_SOURCES_7Z_ARCHIVE=$DFM_CREATOR_SOURCES_DESTINATION_DIR-$VERSION-src.7z
DFM_BIN_7Z_ARCHIVE=$OUTPUT_LOCATION-$VERSION.7z

# Resetting the terminal screen
tput reset

# Create the destination dist directory if it does not already exist
if [ ! -d $OUTPUT_LOCATION ]
then
	mkdir -v --parents $OUTPUT_LOCATION
else
	rm -rfv $OUTPUT_LOCATION
	mkdir -v --parents $OUTPUT_LOCATION
fi

# delete the destination doc directory if it does already exist
if [ -d $DFM_CREATOR_DESTINATION_DOC_DIR ]
then
	rm -rfv $DFM_CREATOR_DESTINATION_DOC_DIR
fi

# Entering the build directory to build DfM-Creator
cd $DFM_CREATOR_PATH || {
echo
echo
echo "Couldn't change to:"
echo "$DFM_CREATOR_PATH"
}

# Building DfM-Creator
ant build_dfm_creator || { echo
echo
echo "An error occured! Couldn't execute \"ant build_dfm_creator\""
echo "Check if \"wkt.home\" points to an existing directory."
}

echo
echo "============================================"
echo

# Rendering DfM-Creator executable
chmod 777 $DFM_CREATOR_JAR && echo "$DFM_CREATOR_JAR is now executable!"

# Removing the older built jar and jad files
# Removing older DfM-Creator.jar file
if [ -f $DFM_CREATOR_OLDER_JAR ]
then
	rm -fv $DFM_CREATOR_OLDER_JAR
fi
echo

# Copying the newly built files in the output directories
# Copying DfM-Creator
cp $DFM_CREATOR_JAR $OUTPUT_LOCATION && echo "$DFM_CREATOR_JAR copied at $OUTPUT_LOCATION"

# Removing the older sources' directory
if [ -d $DFM_CREATOR_SOURCES_DESTINATION_DIR ]
then
	rm -Rf $DFM_CREATOR_SOURCES_DESTINATION_DIR && echo "Older DfM-Creator sources successfully deleted"
fi
echo

# Copying the DfM-Creator sources in the output directory
svn export $DFM_CREATOR_SOURCES_SOURCE $DFM_CREATOR_SOURCES_DESTINATION_DIR && echo "Newer DfM-Creator sources successfully copied in the selected location."
echo

# Copying the Web-Documentation directory
svn export $WEB_DOCUMENTATION_DIRECTORY $DFM_CREATOR_DESTINATION_DOC_DIR && \
rm -rfv $DFM_WEB_DOC_JARS_AND_SRC_DIR && \
echo "Latest Web-Documentation files successfully copied in the selected location."
echo

# Removing the older archives; bin and sources
if [ -f $DFM_SOURCES_7Z_ARCHIVE ]
then
    rm -fv $DFM_SOURCES_7Z_ARCHIVE
fi

if [ -f $DFM_BIN_7Z_ARCHIVE ]
then
    rm -fv $DFM_BIN_7Z_ARCHIVE
fi

# Creating a 7zip archives and cleaning up
7za a -t7z -m0=lzma -mx=9 -mfb=64 -md=32m -ms=on $DFM_SOURCES_7Z_ARCHIVE $DFM_CREATOR_SOURCES_DESTINATION_DIR && {
rm -Rf $DFM_CREATOR_SOURCES_DESTINATION_DIR
echo "7zip archive created and some housekeeping performed."
}

# Executing the built jar file
java -jar DictionaryForMIDs-Creator/$DFM_CREATOR
#tput reset

ant clean_all || {
echo
echo
echo "An error occured! Couldn't execute \"ant clean_all\""
}

echo
echo
# Echoing the path
echo $DFM_CREATOR_PATH
echo
echo

exit 0;

