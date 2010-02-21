package control;

import data.BookmarkManager;
import gui.MainWindow;
import tools.Configuration;

public class MainController
{
	private MainWindow window;
	
	public MainController()
	{
		initialize();
	}
	
	private void initialize()
	{
		Configuration.getInstance().load();
	}
	
	public void startGUI()
	{
		window = new MainWindow(this);
		window.show();
		window.getMapComponent().getControl().restorePosition();
	}
	
	public void exitApplication()
	{
		window.getMapComponent().getControl().savePosition();
		BookmarkManager.getInstance().saveBookmarks();
		Configuration.getInstance().store();
		System.exit(0);
	}
}
