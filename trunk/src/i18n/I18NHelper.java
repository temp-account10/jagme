package i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18NHelper
{
	private static final String MNEMONIC_SUFFIX = "mnemonic";
	
	private static final I18NHelper instance = new I18NHelper();
	private static ResourceBundle bundle;

	private I18NHelper()
	{
		bundle = ResourceBundle.getBundle("i18n.ApplicationResources", Locale.getDefault());
	}
	
	public static I18NHelper getInstance()
	{
		return instance;
	}
	
	public String getString(String key)
	{
		return bundle.getString(key);
	}
	
	public char getMnemonic(String key)
	{
		key = String.format("%s.%s", key, MNEMONIC_SUFFIX);
		String s = getString(key);
		
		if(s.length() == 1)
		{
			return s.charAt(0);
		}
		else
		{
			return 0;
		}
	}
}
