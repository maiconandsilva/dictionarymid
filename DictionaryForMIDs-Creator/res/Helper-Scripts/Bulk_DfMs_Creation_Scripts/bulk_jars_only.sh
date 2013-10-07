#!/bin/bash
#!/bin/bash
# Copyright 2013 Karim Mahamane Karimou
# GNU GPL License
# This script is part of a set of scripts
# that enable the user to batch create
# DictionaryForMIDs.jar/jad files. This
# is particularly useful when one needs
# to generate jars and jads for say 50
# dictionary files (from OmegaWiki or
# Freedict for instance).

ARGS=1				# Expected command line arguments
E_WRONGARGS=75
DIRECTORY=$1

if [ $# -ne "$ARGS" ] # Check for proper number of command-line args.
then
   echo
   echo "Usage: `basename $0` name_of_directory"
   echo "Give at leat one directory name"
   echo
   exit $E_WRONGARGS
fi

bash create_all_dfms.sh $DIRECTORY
bash copy_all_dfm_jars.sh $DIRECTORY
bash clean_all_dfms.sh $DIRECTORY
echo
echo
echo "Bulk DictionaryForMIDs creation done successfully."
exit 0

