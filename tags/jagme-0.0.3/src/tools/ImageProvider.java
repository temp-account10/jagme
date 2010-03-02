package tools;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import control.ErrorHandler;

public class ImageProvider
{
	/**
	 * The icon cache
	 */
	private static Map<String, Image> cache = new HashMap<String, Image>();
    
	public static Image getImage(String name)
	{
		return getImage("", name);
	}
	
	/**
	 * Shortcut for get("", name);
	 */
	public static ImageIcon getImageIcon(String name)
	{
		return getImageIcon("", name);
	}
	
	/**
	 * Return an image from the specified location.
	 *
	 * @param subdir	The position of the directory, e.g. "layer"
	 * @param name		The icons name (without the ending of ".png")
	 * @return The requested Image.
	 */
	public static ImageIcon getImageIcon(String subdir, String name)
	{
		Image img = getIfAvailable(subdir, name);
		
		if (img == null)
		{
			String ext = name.indexOf('.') != -1 ? "" : ".png";
			throw new NullPointerException("/images/"+subdir+name+ext+" not found");
		}
		else
		{
			return new ImageIcon(img);
		}
	}
	
	public static Image getImage(String subdir, String name)
	{
		Image img = getIfAvailable(subdir, name);
		
		if (img == null)
		{
			String ext = name.indexOf('.') != -1 ? "" : ".png";
			throw new NullPointerException("/images/"+subdir+name+ext+" not found");
		}
		else
		{
			return img;
		}
	}

	/**
	 * Like {@link #getImageIcon(String)}, but does not throw an exception
	 * in case of nothing is found. Use this, if the image to retrieve is optional.
	 */
	public static Image getIfAvailable(String subdir, String name)
	{
		if (name == null)
		{
			return null;
		}
		
		if (subdir == null)
		{
			subdir = "";
		}
		else if (!subdir.equals(""))
		{
			subdir += "/";
		}
		
		String ext = name.indexOf('.') != -1 ? "" : ".png";
		String full_name = subdir+name+ext;

		Image img = cache.get(full_name);
		if (img == null)
		{
			URL path = getImageUrl(full_name);
			if (path == null)
			{
				return null;
			}
			img = Toolkit.getDefaultToolkit().createImage(path);
			cache.put(full_name, img);
		}
	
		return img;
	}

	private static URL getImageUrl(String path, String name)
    {
        if(path.startsWith("resource://"))
        {
            String p = path.substring("resource://".length());
            
            URL res;
            if ((res = ClassLoader.getSystemClassLoader().getResource(p+name)) != null)
                return res;
        }
        else
        {
            try {
                File f = new File(path, name);
                if(f.exists())
                    return f.toURI().toURL();
            } catch (MalformedURLException e) {}
        }
        return null;
    }
	
	private static URL getImageUrl(String imageName)
	{
		URL url;
		
	    // Try user-preference directory first
   		String path = "images/"+imageName;
   		if (new File(path).exists())
   		{
   			try
   			{
    			url = new URL("file", "", path);
    			if(url != null) return url;
    		}
   			catch (MalformedURLException e)
   			{
   				ErrorHandler.handleFatalError(e);
   			}
    	}
   		
   		url = getImageUrl("resource://images/", imageName);
        if(url != null) return url;

	    return null;
    }

}
