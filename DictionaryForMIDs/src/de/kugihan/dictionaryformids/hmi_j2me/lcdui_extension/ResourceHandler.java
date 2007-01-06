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
	
	public final static String pathSeparator = "/";
	/*
	 * The method getIcon(int bestImageHeight, int bestImageWidth) should return an icon with the
	 * size of bestImageHeight and bestImageWidth (or as closely as possible).
	 */
	protected final String pathIcons = "icons";

	protected final String fileExtensionIcons = ".png";

	public final String iconAreaUIDisplayTextItems = "UIDisplayTextItems";
	public final String iconSizeGroupSmall = "small";
	public final String iconSizeGroupBig   = "big";

	public class IconSize { 
		IconSize(String iconAreaParam, 
				 String iconSizeGroupParam, 
				 int[] sizesInPixelParam) {
			iconArea = iconAreaParam;
			iconSizeGroup = iconSizeGroupParam;
			sizesInPixel = sizesInPixelParam;
		}
		public String iconArea;
		public String iconSizeGroup;
		public int[]  sizesInPixel;  // size is used both for height and width
		                      		 // sizes must be sorted ascendingly
	}
	// iconSizes defines the available sizes of the icons for a certain iconArea / iconSizeGroup
	public IconSize[] iconSizes = { 
					new IconSize(iconAreaUIDisplayTextItems, iconSizeGroupSmall, new int[] {12 , 16, 20, 24, 32} ), 
					new IconSize(iconAreaUIDisplayTextItems, iconSizeGroupBig,   new int[] {20, 32, 48} ), 
			};

	public Image getIcon(String        iconArea,       // specifies the subdirectory where the icons are located 
			             String        iconSizeGroup,  // selects the size group for the icons; can be null if n/a for iconArea
			             String 	   iconName, 
			             int 		   bestImageHeight, 
			             int 		   bestImageWidth) 
			throws DictionaryException {
		// determine the best fitting icon size for iconArea / iconSizeGroup
		int maxSizeInPixel;
		if (bestImageHeight > bestImageWidth) 
			maxSizeInPixel = bestImageHeight;
		else 
			maxSizeInPixel = bestImageWidth;
		++maxSizeInPixel; // allow one pixel as additional size
		int foundIconSizeInPixel = -1;
		int iconSizeCount = 0;
		while (iconSizeCount < iconSizes.length) {
			IconSize iconSize = iconSizes[iconSizeCount]; 
			if ((iconSize.iconArea.compareTo(iconArea) == 0) &&
				(iconSize.iconSizeGroup.compareTo(iconSizeGroup) == 0)) {
					for (int sizesInPixelCount = 0; 
					     sizesInPixelCount < iconSize.sizesInPixel.length;
					     ++sizesInPixelCount) {
						int iconSizeInPixel = iconSize.sizesInPixel[sizesInPixelCount];
						if (sizesInPixelCount == 0) {
							// always use the smallest size as a starting point
							foundIconSizeInPixel = iconSizeInPixel; 
						}
						if (iconSizeInPixel > maxSizeInPixel)  {
							// best fitting size was found
							break;
						}
						else {
							foundIconSizeInPixel = iconSizeInPixel; 
						}
					}
				break;
			}
			++iconSizeCount;
		}
		if (foundIconSizeInPixel < 0) {
			throw new DictionaryException("Size for icon could not be determined: " +  iconArea + "/" + iconSizeGroup);
		}
		
		return getImage(buildIconPathName(iconArea, iconSizeGroup, foundIconSizeInPixel),  
		        		buildIconFileName(iconName));
	}
	
	public String buildIconPathName(String  iconArea, 
						            String  iconSizeGroup,
						            int     foundIconSizeInPixel) {
		String iconPathName =   pathIcons + pathSeparator + 
						        iconArea + pathSeparator + 
						        iconSizeGroup + pathSeparator + 
						        foundIconSizeInPixel +  "px";
		return iconPathName;
	}

	public String buildIconFileName(String  iconName) {
		String iconFileName = iconName + fileExtensionIcons;
		return iconFileName;
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
	
	public String getResourceLocation(String path,  // path is without leading or trailing /
			                             String resourceName)
	{
		return pathSeparator + path + pathSeparator + resourceName;
	}

}
