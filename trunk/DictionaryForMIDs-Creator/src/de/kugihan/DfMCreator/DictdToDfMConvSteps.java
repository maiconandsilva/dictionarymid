/* ////////////////////////////////////////////////////////////////
*   
*   In the Name of Allah
*   
*   DICTIONARYFORMIDS-CREATOR
*   
*   This file is part of DictionaryForMIDs-Creator
*   Copyright (C) 2012 Karim Mahamane Karimou
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



public interface DictdToDfMConvSteps {
    
    public void start();
    
    public void stop();
    
    public void stopOnQuit();    
}
