package control;

import i18n.I18NHelper;

public class ErrorHandler
{
	public static void handleFatalError(Exception e)
	{		
		System.out.println(I18NHelper.getInstance().getString("application.fatalabort"));
		System.out.println();
		System.out.println(e.getMessage());
		System.out.println();
		e.printStackTrace();
		System.exit(-1);
	}
}
