import gui.MainWindow;
import tools.Configuration;
import data.BookmarkManager;

public class Main
{	 
	public static void main(String[] args)
	{
		MainWindow window = new MainWindow();
		window.show();
		
		/*Configuration.getInstance().load();
		BookmarkManager mgr = new BookmarkManager();
		//mgr.addBookmark(new Bookmark("test", new LatLon(10.01, 10.02), Map.OPENSTREETMAP_MAPNIK, 8));
		mgr.saveBookmarks();
		
		Configuration.getInstance().store();*/
	}
}
