#! /usr/bin/env bash
# this script was taken from Stack Overflow
# and was modified to fit with my needs.
# This script is part of a set of scripts
# that enable the user to batch create
# DictionaryForMIDs.jar/jad files. This
# is particularly useful when one needs
# to generate jars and jads for say 50
# dictionary files (from OmegaWiki or
# Freedict for instance).
#
# This one walks through a directory recursively in order to carry
# the requested action which are generatlly: generating the dictionary
# files, creating bitmap fonts, creating the DictionarForMIDs.jar/jad
# files, copying the jars and/or jads into a given directory, copying
# whole directories containing the jars/jads into another directory etc.
# Note that many scripts are used for mentioned actions. There is no such
# thing as a single script that does that.

SCRIPT_FILE_ONE=`readlink --no-newline --quiet --silent -f create.sh`
SCRIPT_FILE_TWO=`readlink --no-newline --quiet --silent -f clean.sh`
SCRIPT_ONE_PERMLINK=$SCRIPT_FILE_ONE
SCRIPT_TWO_PERMLINK=$SCRIPT_FILE_TWO

ARGS=3				# Expected command line arguments
E_WRONGARGS=75

if [ $# -ne "$ARGS" ] # Check for proper number of command-line args.
then
   echo
   echo "Usage: `basename $0` name_of_directory"
   echo "Give at leat one directory name"
   echo
   exit $E_WRONGARGS
fi

copy_script_files() {
	cp -fv $SCRIPT_ONE_PERMLINK $PWD
	cp -fv $SCRIPT_TWO_PERMLINK $PWD
}

walk_tree() {
	ls "$1" | while IFS= read i; do
		if [ -d "$1/$i" ]; then
			echo "$i/"
			cd "$1/$i" && copy_script_files && cd ../../
			walk_tree "$1/$i" "$2" | sed -r 's/^/\t/'
		else
			echo "$i" | grep -E "$2"
		fi
	done
}

walk_tree "$1" "\.sh$"

