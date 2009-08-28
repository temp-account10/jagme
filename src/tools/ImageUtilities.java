package tools;

import javax.imageio.ImageIO;

public class ImageUtilities
{
	public static boolean isWriteSupportedFormat(String formatSuffix)
	{
		String[] supportedFormats = ImageIO.getWriterFileSuffixes();
		
		for(String current : supportedFormats)
		{
			if(current.equals(formatSuffix))
			{
				return true;
			}
		}
		
		return false;
	}
}
