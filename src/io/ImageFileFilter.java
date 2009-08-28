package io;

import java.io.File;

import javax.imageio.ImageIO;

import tools.FileUtilities;
import tools.ImageUtilities;

public class ImageFileFilter extends SuffixAwareFilter
{
	public boolean accept(File f)
	{
		boolean accept = super.accept(f);
		
		if (!accept)
		{
			String suffix = FileUtilities.getSuffix(f);

			if (suffix != null)
			{
				accept = super.accept(f) || ImageUtilities.isWriteSupportedFormat(suffix);
			}
		}
		return accept;
	}

	public String getDescription()
	{
		String fileFormats = "";
		int count = 0;
		
		String[] supportedFormats = ImageIO.getWriterFileSuffixes(); 
		
		for(String current : supportedFormats)
		{
			fileFormats += "*." + current;
			
			if(count < (supportedFormats.length - 1))
			{
				fileFormats += ", ";
			}
			
			count++;
		}
		
		String description = String.format("Supported Image file types (%s)", fileFormats);
		
		return description;
	}
}
