#!/bin/sh

# This file is inspired of Zekr.sh (from the Zekr Quran Application Open-source project)
# This portable version of DfM-Creator is bundled with Oracle JRE 1.7.05

VERSION=0.6-beta
ORIG_DIR_NAME=`cd`
FILE=`readlink -f $0`
DIR=`dirname $FILE`

JAVA_CMD=$DIR/jre/bin/java
MAIN_CLASS=de.kugihan.DfMCreator.DfMCreatorMain

CLASS_PATH=dist/DfM-Creator-$VERSION.jar
VM_ARGS="-Xms64m -Xmx512m $ZEKR_BROWSER"

cd $DIR
"$JAVA_CMD" $VM_ARGS -cp "$CLASS_PATH" $MAIN_CLASS
cd $ORIG_DIR_NAME

