#!/bin/bash

VERSION=0.6-beta
DFM_CREATOR_DIR=DfM-Creator-$VERSION-Linux-x86-portable
LAUNCHER_NAME=DfM-Creator_x86.desktop

echo
echo "Debug:"
echo "Working directory:"
echo $PWD
echo

echo "Installing DfM-Creator in $HOME/bin"
echo "Creating directory $HOME/bin if it does not already exist..."

if [ ! -d $HOME/bin/$DFM_CREATOR_DIR ]
then
  mkdir -v --parents $HOME/bin/$DFM_CREATOR_DIR
fi

# Rendering the files executable
chmod u+x dist/DfM-Creator-$VERSION.jar
chmod u+x DfM-Creator.sh

#chmod u+x $HOME/bin/$DFM_CREATOR_DIR/DfM-Creator.sh
#chmod u+x $HOME/bin/$DFM_CREATOR_DIR/dist/DfM-Creator-$VERSION.jar

echo "Copying the files..."

cp -rf * $HOME/bin/$DFM_CREATOR_DIR

echo
echo "Debug:"
echo "Working directory:"
echo $PWD
echo

# Creating a launcher for the application
touch $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME

# Defining entries to be put in the file
line1='[Desktop Entry]'
line2='Encoding=UTF-8'
line3='Name=DfM-Creator'
line4='Comment=The DictioanryForMIDs Dictioanry Creation Tool'
line5='Exec='"$HOME/bin/$DFM_CREATOR_DIR/DfM-Creator.sh"
line6='Icon='"$HOME/bin/$DFM_CREATOR_DIR/dfm-creator.png"
line7='Categories=Application;Dictionary'
line8='Version=1.0'
line9='Type=Application'
line10='Terminal=0'
line11='Name[en_US]=DfM-Creator'

# Writing entries into the file
echo $line1 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line2 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line3 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line4 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line5 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line6 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line7 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line8 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line9 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line10 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
echo $line11 >> $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME

# Rendering the launcher executable
chmod u+x $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME
# Copying the launcher on the desktop
cp -f $HOME/bin/$DFM_CREATOR_DIR/$LAUNCHER_NAME $HOME/Desktop

echo
echo "Done."

exit 0;
