#!/bin/bash
# Example of uploading using rsync
# See 'man rsync' on a Linux box for more details

rsync --inplace --partial --append-verify -v -e ssh \
dfm-creator-0.7-final-linux-amd64-with-jre.run \
dfm-creator-0.7-final-linux-i386-with-jre.run \
karimoune,dictionarymid@frs.sourceforge.net:/home/project-web/dictionarymid/htdocs/DfM-Creator/files/Linux/

rsync --inplace --partial --append-verify -v -e ssh \
dfm-creator-0.7-final-x86_64-installer.exe \
dfm-creator-0.7-final-x86-installer.exe \
karimoune,dictionarymid@frs.sourceforge.net:/home/project-web/dictionarymid/htdocs/DfM-Creator/files/Windows/

