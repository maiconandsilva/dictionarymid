#!/bin/bash
# Example of uploading using rsync
# See 'man rsync' on a Linux box for more details

rsync -v -e ssh \
dfm-creator-0.6-beta-linux-amd64-with-jre.run \
dfm-creator-0.6-beta-linux-x86-with-jre.run \
dfm-creator-0.6-beta-x86-installer.exe \
dfm-creator-0.6-beta-x86_64-installer.exe \
karimoune,dictionarymid@frs.sourceforge.net:/home/project-web/dictionarymid/htdocs/DfM-Creator/files/

