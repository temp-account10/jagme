package data;

import java.util.ArrayList;

import tools.Configuration;

public class BookmarkManager
{
	private ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
	
	public BookmarkManager()
	{
		loadBookmarks();
	}

	public void addBookmark(Bookmark bookmark)
	{
		bookmarks.add(bookmark);
	}
	
	public boolean deleteBookmark(Bookmark bookmark)
	{
		return bookmarks.remove(bookmark);
	}
	
	// load bookmarks from configuration
	private void loadBookmarks()
	{
		Configuration config = Configuration.getInstance();
		
		if (config.hasProperty("bookmarks.totalnumber"))
		{
			int numOfBookmarks = config.getIntegerProperty("bookmarks.totalnumber");			
			// TODO
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
