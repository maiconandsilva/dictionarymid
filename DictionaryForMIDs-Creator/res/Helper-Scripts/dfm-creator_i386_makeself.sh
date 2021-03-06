#!/bin/bash

OUTPUT_DIR=INSTALLERS

if [ ! -d $OUTPUT_DIR ]
then
  mkdir -v --parents $OUTPUT_DIR
fi

VERSION=0.7-final
makeself-2.1.5/makeself.sh --bzip2 \
  DfM-Creator-$VERSION-Linux-x86-portable \
  $OUTPUT_DIR/dfm-creator-$VERSION-linux-i386-with-jre.run \
  "DictionaryForMIDs-Creator-$VERSION" ./dfm-creator_i386_install.sh

exit 0;
