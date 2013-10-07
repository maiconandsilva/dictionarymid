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
#
#This particular script needs to be put in the directory where
#the csv file and the DictionarForMIDs.properties files are.

for file in *
do
	if [ -d $file ]
	then
		rm -rvf $file
	fi
done

