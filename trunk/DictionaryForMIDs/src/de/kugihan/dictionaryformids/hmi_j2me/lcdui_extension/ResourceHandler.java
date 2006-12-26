/*
DictionaryForMIDs - a free multi-language dictionary for mobile devices.
Copyright (C) 2005, 2006  Gert Nuber (dict@kugihan.de)

GPL applies - see file COPYING for copyright statement.
*/
package de.kugihan.dictionaryformids.hmi_j2me.lcdui_extension;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Image;

import de.kugihan.dictionaryformids.dataaccess.ResourceDfMInputStream;
import de.kugihan.dictionaryformids.general.DictionaryException;

/*
 * The class ResourceHandler provides access to Icons, Images, Animations, etc.
 */
public class ResourceHandler {

	private static ResourceHandler resourceHandlerObj = null; 
	
	public static ResourceHandler getResourceHandlerObj() {
		if (resourceHandlerObj == null)
			resourceHandlerObj = new ResourceHandler(); 
		return resourceHandlerObj;
	}

	ResourceHandler() {
		resourceDfMInputStream = new ResourceDfMInputStream();
	}
	
	protected ResourceDfMInputStream resourceDfMInputStream;  // resources are all read from the JAR-file
	
	/*
	 * The method getIcon(int bestImageHeight, int bestImageWidth) should return an icon with the
	 * size of bestImageHeight and bestImageWidth (or as closely as possible).
	 */
	protected final String pathIcons = "icons";
	
	public Image getIcon(String iconArea,    // specifies the subdirectory where the icons are located 
			             String iconName, 
			             int bestImageHeight, 
			             int bestImageWidth) 
			throws DictionaryException {
		// not yet implemented:
		// some processing should be done here, either 1 or 2
		// 1: get an icon with any size and stretch it with a clever algotithm to bestImageHeight/bestImageWidth
		// 2: for each icon there should be images with several sizes and the one with the size best usable for
		//    bestImageHeight/bestImageWidth is used.
		return getImage(pathIcons + '/' + iconArea, iconName);
	}
	
	/*
	 * The method getImage() returns an image which is read as a resource 
	 */ 
	public Image getImage(String path,    
			              String imageName) 
			throws DictionaryException {
		String resourceLocation = getResourceLocation(path, imageName); 
		InputStream imageInputStream = resourceDfMInputStream.getInputStream(resourceLocation);
		Image imageFromResource;
		try {
			imageFromResource = Image.createImage(imageInputStream);
			imageInputStream.close();
		}
		catch (IOException e) {
			throw new DictionaryException("Image file could not be read:" + resourceLocation);
		}
		return imageFromResource;
	}
	
	protected String getResourceLocation(String path,  // path is without leading or trailing /
			                             String resourceName)
	{
		return "/" + path + "/" + resourceName;
	}

}
