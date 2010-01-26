package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Configuration
{
	private static final Configuration instance = new Configuration();
	private HashMap<String, String> configTable = new HashMap<String, String>(); 
	private File configFile;
	
	private Configuration()
	{
		determineConfigFile();
	}
	
	public static Configuration getInstance()
	{
		return instance;
	}
	
	public boolean hasProperty(String key)
	{
		return configTable.keySet().contains(key);
	}
	
	public void setProperty(String key, String value)
	{
		configTable.put(key, value);
	}
	
	public void setProperty(String key, Object value)
	{
		String s = value.toString();
		setProperty(key, s);
	}
	
	public void setProperty(String key, int value)
	{
		String s = new Integer(value).toString();
		setProperty(key, s);
	}
	
	public void setProperty(String key, double value)
	{
		String s = new Double(value).toString();
		setProperty(key, s);
	}
	
	public String getProperty(String key)
	{
		return configTable.get(key);
	}
	
	public Integer getIntegerProperty(String key)
	{
		return new Integer(configTable.get(key));
	}
	
	public Double getDoubleProperty(String key)
	{
		return new Double(configTable.get(key));
	}
	
	private void determineConfigFile()
	{
        if (configFile != null)
        {
            return;
        } 
        
        String path = System.getenv("APPDATA");
        if (path != null)
        {
        	configFile = new File(path, "jagme");
        }
        else
        {
        	configFile = new File(System.getProperty("user.home"), ".jagme");
        }
    }
	
	public void load()
	{
		Properties properties = new Properties();
		
		FileReader reader = null;
		
		try
		{
			reader = new FileReader(configFile);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			properties.load(reader);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Object o : properties.keySet())
		{
			String key = o.toString();
			String value = properties.getProperty(key);
			
			configTable.put(key, value);
		}
	}
	
	public void store()
	{
		// prepare properties
		Properties properties = new Properties();
		
		for(String key : configTable.keySet())
		{
			properties.setProperty(key, configTable.get(key));
		}
		
		// write properties to file
		try
		{
			FileWriter writer = new FileWriter(configFile);
			properties.store(writer, "");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}