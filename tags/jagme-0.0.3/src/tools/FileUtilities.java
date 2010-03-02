package tools;

import java.io.File;

public class FileUtilities
{
	public static String getSuffix(File f)
	{
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase();

		return suffix;
	}
}
