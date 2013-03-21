/* ////////////////////////////////////////////////////////////////
*
*   In the Name of Allah
*
*   DICTIONARYFORMIDS-CREATOR
*
*   This file is part of DictionaryForMIDs-Creator
*   Copyright (C) 2012, 2013 Karim Mahamane Karimou
*   DictionaryForMIDs-Creator is a GUI wrapper around various
*   DictionaryForMIDs tools, among others we have
*   DictdToDictionaryForMIDs, DictionaryGeneration,
*   JarCreator and BitmapFontGenerator.
*
*   DictionaryForMIDs-Creator is free software;
*   you can redistribute it and/or modify it under the terms
*   of the GNU General Public License as published by the
*   Free Software Foundation; either version 2 of the License, or
*   (at your option) any later version.
*
*   DictionaryForMIDs-Creator is distributed in the hope that
*   it will be useful, but WITHOUT ANY WARRANTY; without even
*   the implied warranty of MERCHANTABILITY or
*   FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public
*   License along with DictionaryForMIDs-Creator;
*   if not, write to the Free Software Foundation, Inc.,
*   51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*
* //////////////////////////////////////////////////////////////// */


package de.kugihan.DfMCreator;

import edu.hws.eck.mdb.I18n;
import javax.swing.JOptionPane;


public class DfMCreatorException {

    /*
     *
     * Exceptions
     *
     */
    public static class CantCreateDestDir extends Exception {

        public CantCreateDestDir(String message) {
            super(message);
        }
    }

    public static class CantCreatOutputJarJadDirectory extends Exception {

        public CantCreatOutputJarJadDirectory(String message) {
            super(message);
        }
    }

    public static class BadDictDirNameException extends Exception {

        public BadDictDirNameException(String message) {
            super(message);
        }
    }

    public static class PropFileErrorException extends Exception {

        public PropFileErrorException(String message) {
            super(message);
        }
    }

    public static class EmptyDfMFileNotFound extends Exception {
        public EmptyDfMFileNotFound(String message) {
            super(message);
        }
    }

    public static class DBFolderNotAccessible extends Exception {

        public DBFolderNotAccessible(String message) {
            super(message);
        }
    }

    public static class DBINIFileNotAccessible extends Exception {

        public DBINIFileNotAccessible(String message) {
            super(message);
        }
    }

    public static class DBNameTextFieldIsEmpty extends Exception {

        public DBNameTextFieldIsEmpty(String message) {
            super(message);
        }
    }

    public static class DBPathTextFieldIsEmpty extends Exception {

        public DBPathTextFieldIsEmpty(String message) {
            super(message);
        }
    }

    public static class OutCSVFileCantBeWritten extends Exception {

        public OutCSVFileCantBeWritten(String message) {
            super(message);
        }
    }

    public static class OutCSVFileTextFieldIsEmpty extends Exception {

        public OutCSVFileTextFieldIsEmpty(String message) {
            super(message);
        }
    }

    public static class DirectoryDestinationTFIsEmpty extends Exception {

        public DirectoryDestinationTFIsEmpty(String message) {
            super(message);
        }
    }

    public static class PropertyPathNotAccessible extends Exception {

        public PropertyPathNotAccessible(String message) {
            super(message);
        }
    }

    public static class PropertyPathTFIsEmpty extends Exception {

        public PropertyPathTFIsEmpty(String message) {
            super(message);
        }
    }

    public static class SourceFileNotAccessible extends Exception {

        public SourceFileNotAccessible(String message) {
            super(message);
        }
    }

    public static class SourceFileTFIsEmpty extends Exception {

        public SourceFileTFIsEmpty(String message) {
            super(message);
        }
    }

    public static class DirectoryDestinationNotAccessible extends Exception {

        public DirectoryDestinationNotAccessible(String message) {
            super(message);
        }
    }

    public static class DictionaryDirectoryNotAccessible extends Exception {

        public DictionaryDirectoryNotAccessible(String message) {
            super(message);
        }
    }

    public static class EmptyDfMDirTFIsEmpty extends Exception {

        public EmptyDfMDirTFIsEmpty(String message){
            super(message);
        }
    }

    public static class EmptyDfMJarJadDirDoesNotExist extends Exception {

        public EmptyDfMJarJadDirDoesNotExist(String message) {
            super(message);
        }
    }

    public static class InputCSVFilesTFIsEmpty extends Exception {

        public InputCSVFilesTFIsEmpty(String message) {
            super(message);
        }
    }

    public static class OutputDirTFIsEmpty extends Exception {

        public OutputDirTFIsEmpty(String message) {
            super(message);
        }
    }

    public static class OutputDirectoryNotAccessible extends Exception {

        public OutputDirectoryNotAccessible(String message) {
            super(message);
        }
    }

        public static class dictionaryDirNotAccessible extends Exception {

            public dictionaryDirNotAccessible(String message) {
                super(message);
            }
        }

        public static class dictionaryFieldIsEmpty extends Exception {

            public dictionaryFieldIsEmpty(String message) {
                super(message);
            }
        }

        public static class fontFieldIsEmpty extends Exception {

            public fontFieldIsEmpty(String message) {
                super(message);
            }
        }

        public static class fontNotAccessible extends Exception {

            public fontNotAccessible(String message) {
                super(message);
            }
        }

        public static class CSVDictionaryFilesNotFound extends Exception {

            public CSVDictionaryFilesNotFound(String message) {
                super(message);
            }
        }

        public static class NoDictFileFound extends Exception {

            public NoDictFileFound(String message) {
                 super(message);
            }
        }

        public static class NoIndexFileFound extends Exception {

            public NoIndexFileFound(String message) {
                 super(message);
            }
        }


    /*
     * Error messages used by the exceptions.
     *
     */

    // DictdToDfM error messages.
    public static final String DBFolderNotAccessibleMsg = I18n.tr("databaseDir.exception");
    public static final String DBINIFileNotAccessibleMsg = I18n.tr("databaseIni.exception");
    public static final String DBNameTextFieldIsEmptyMsg = I18n.tr("databaseName.exception");
    public static final String DBPathTextFieldIsEmptyMsg = I18n.tr("databasePath.exception");
    public static final String OutCSVFileTextFieldIsEmptyMsg = I18n.tr("outputFile.exception");
    public static final String OutCSVFileCantBeWrittenMsg = I18n.tr("cantWrite.exception");

    // DictionaryGeneration error messages.
    public static final String SourceFileTFIsEmptyMsg = I18n.tr("inputFile.exception");
    public static final String DirectoryDestinationTFIsEmptyMsg = I18n.tr("destinationDir.exception");
    public static final String PropertyPathTFIsEmptyMsg = I18n.tr("propertyFilePath.exception");
    public static final String SourceFileNotAccessibleMsg = I18n.tr("inputFileExistence.exception");
    public static final String PropertyPathNotAccessibleMsg = I18n.tr("propertiesFile.exception");
    public static final String DirectoryDestinationNotAccessibleMsg = I18n.tr("destinationDirectory.exception");

    // JarCreator error messages.
    public static final String InputCSVFilesTFIsEmptyMsg = I18n.tr("inputFileField.exception");
    public static final String EmptyDfMDirTFIsEmptyMsg = I18n.tr("emptyDfM.exception");
    public static final String OutputDirTFIsEmptyMsg = I18n.tr("outputDirectoryField.exception");
    public static final String OutputDirectoryNotAccessibleMsg = I18n.tr("directoryOutputAccess.exception");
    public static final String DictionaryDirectoryNotAccessibleMsg = I18n.tr("csv.exception");

    public static final String EmptyDfMJarJadDirDoesNotExistMsg = I18n.tr("emptyJarJad.exception");
    public static final String EmptyDfMFileNotFoundMsg = I18n.tr("emptyJarJadPath");
    public static final String BadDictDirNameExceptionMsg = I18n.tr("outputDirectoryName.exception");
    public static final String PropFileErrorExceptionMsg = I18n.tr("propertiesFileReadability.exception");
    public static final String CantCreateDestDirMsg = I18n.tr("cant.create.dest.dir.Exc.Msg");

    // Bitmap Font Generaton Error messages
    public static final String fontFieldIsEmptyMsg = I18n.tr("font.exception");
    public static final String fontNotAccessibleMsg = I18n.tr("selectedFont.exception");
    public static final String dictionaryFielsIsEmptyMsg = I18n.tr("dictDir.exception");

    public static final String dictionaryDirNotAccessibleMsg = I18n.tr("selectedDictDir.exception");

    public static final String CSVDictionaryFilesNotFoundMsg = I18n.tr("csvsExistence.exception");    
    public static final String NoDictFileFoundMsg = I18n.tr("no.dict.file.found.Ex.Msg");
    public static final String NoIndexFileFoundMsg = I18n.tr("no.index.file.found.Ex.Msg");
    
    
    /*
     * Subroutines that show the messages.
     */
    public static void printErrorMsg(){
        JOptionPane.showMessageDialog(null, I18n.tr("oneOrMoreFields.exception"),
               I18n.tr("error"), JOptionPane.ERROR_MESSAGE);
    }
}
