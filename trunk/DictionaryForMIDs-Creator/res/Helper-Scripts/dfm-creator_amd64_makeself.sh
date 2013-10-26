#!/bin/bash

OUTPUT_DIR=INSTALLERS

if [ ! -d $OUTPUT_DIR ]
then
  mkdir -v --parents $OUTPUT_DIR
fi

VERSION=0.7-final
makeself-2.1.5/makeself.sh --bzip2 \
  DfM-Creator-$VERSION-Linux-amd64-portable \
  $OUTPUT_DIR/dfm-creator-$VERSION-linux-amd64-with-jre.run \
  "DictionaryForMIDs-Creator-$VERSION" ./dfm-creator_amd64_install.sh

exit 0;
