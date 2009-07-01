package de.kugihan.dictionaryformids.hmi_java_me.filebrowser;
// Copyright (C) yyyy  name of author

// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

/**	This interface receives directory selection events from instances of the
 *	<code>DirectoryChooser</code> class. Classes wishing to use the chooser
 *	must implement this interface and register it with a chooser using its
 *	<code>setDirectoryChooserListener</code> method.
 *
 *	When the user selects a directory using a <code>DirectoryChooser</code>
 *	instance, the registered listener's <code>dirSelected</code> method is
 *	invoked.
 *
 *  @see DirectoryChooser
 *	@version 1.0 - 03/2007
 *  @author Steven J. Castellucci
 */
public interface DirectoryChooserListener
{
	/**	Handles directory selection events.
	 *	
	 *	@param type an <code>int</code> indicating the type of event
	 *	@param fc the <code>FileConnection</code> associated with this event
	 *	@see DirectoryChooser
	 */
	void dirSelected(int type, String selected);
}
