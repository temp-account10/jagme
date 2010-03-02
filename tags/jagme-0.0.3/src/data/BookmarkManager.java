package data;

import java.util.ArrayList;

import maps.Maps.Map;
import tools.Configuration;
import data.coordinate.LatLon;

public class BookmarkManager
{
	private ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
	private static final BookmarkManager instance = new BookmarkManager();
	
	private BookmarkManager()
	{
		loadBookmarks();
	}

	public static BookmarkManager getInstance()
	{
		return instance;
	}
	
	public void addBookmark(Bookmark bookmark)
	{
		bookmarks.add(bookmark);
	}
	
	public boolean deleteBookmark(Bookmark bookmark)
	{
		return bookmarks.remove(bookmark);
	}
	
	public ArrayList<Bookmark> getBookmarks()
	{
		return bookmarks;
	}
	
	// load bookmarks from configuration
	private void loadBookmarks()
	{
		Configuration config = Configuration.getInstance();
		
		if (config.hasProperty("bookmarks.totalnumber"))
		{
			int numOfBookmarks = config.getIntegerProperty("bookmarks.totalnumber");			
			
			for(int i = 0; i < numOfBookmarks; i++)
			{
				String prefix = String.format("bookmarks.bookmark%d.", i);
				
				String name = config.getProperty(prefix + "name");
				double latitude = config.getDoubleProperty(prefix + "latitude");
				double longitude = config.getDoubleProperty(prefix + "longitude");
				String mapValue = config.getProperty(prefix + "map");
				Map map = Enum.valueOf(Map.class, mapValue);
				int zoomlevel = config.getIntegerProperty(prefix + "zoomlevel");
				
				LatLon coordinates = new LatLon(latitude, longitude);
				
				Bookmark bookmark = new Bookmark(name, coordinates, map, zoomlevel);
				addBookmark(bookmark);
			}
		}		
	}
	
	public void saveBookmarks()
	{
		Configuration config = Configuration.getInstance();
		
		int numOfBookmarks = bookmarks.size();
		config.setProperty("bookmarks.totalnumber", numOfBookmarks);
				
		for(int i = 0; i < numOfBookmarks; i++)
		{
			Bookmark bookmark = bookmarks.get(i);
			
			String prefix = String.format("bookmarks.bookmark%d.", i);
			
			config.setProperty(prefix + "name",      bookmark.getName());
			config.setProperty(prefix + "latitude",  bookmark.getCoordinates().getLatitude());
			config.setProperty(prefix + "longitude", bookmark.getCoordinates().getLongitude());
			config.setProperty(prefix + "map",       bookmark.getMap());
			config.setProperty(prefix + "zoomlevel", bookmark.getZoomlevel());
		}
	}
}
