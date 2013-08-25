#!/bin/bash

OUTPUT_DIR=INSTALLERS

if [ ! -d $OUTPUT_DIR ]
then
  mkdir -v --parents $OUTPUT_DIR
fi

VERSION=0.6-beta
makeself-2.1.5/makeself.sh --bzip2 \
  DfM-Creator-$VERSION-Linux-x86-portable \
  $OUTPUT_DIR/dfm-creator-$VERSION-linux-x86-with-jre.run \
  "DictionaryForMIDs-Creator-$VERSION" ./dfm-creator_x86_install.sh

exit 0;
