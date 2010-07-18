package gui;

import i18n.I18NHelper;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import maps.MapProvider;
import maps.Maps.Map;
import tools.ImageProvider;
import actions.AboutAction;
import actions.AddBookmarkAction;
import actions.ApplicationAction;
import actions.ChangeMapSourceAction;
import actions.ExitAction;
import actions.GoToBookmarkAction;
import actions.GoToCoordinateAction;
import actions.ManageBookmarksAction;
import actions.OpenAction;
import actions.SaveMapExtract;
import actions.OpenFullscreenWindowAction;
import data.Bookmark;
import data.BookmarkManager;

public class MainMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	
	private enum Menu
	{
		File,
		Map,
		Bookmarks,
		Help,
	}
	
	public MainMenuBar(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		
		initializeMenu();	
    }
	
	public void Update()
	{
		// currently is dynamic and has to be updated
		int index = Menu.Bookmarks.ordinal();
		remove(index);
		add(getBookMarksMenu(), index);
		updateUI();
	}
	
	private void initializeMenu()
	{
		add(getFileMenu(), Menu.File.ordinal());
		add(getMapMenu(), Menu.Map.ordinal());
		add(getBookMarksMenu(), Menu.Bookmarks.ordinal());
		add(getHelperMenu(), Menu.Help.ordinal());
	}
	
	private JMenu getFileMenu()
	{
		ApplicationAction openAction = new OpenAction(mainWindow);
		ApplicationAction saveMapExtract = new SaveMapExtract(mainWindow);
		ApplicationAction exit = new ExitAction(mainWindow.getMainController());
		
		JMenu menu = new JMenu(I18NHelper.getInstance().getString("menu.file"));
		menu.setMnemonic(I18NHelper.getInstance().getMnemonic("menu.file"));
		
		JMenuItem current;
		
		current = menu.add(openAction);
		current.setAccelerator(openAction.shortCut);
		menu.addSeparator();
		current = menu.add(saveMapExtract);
		current.setAccelerator(saveMapExtract.shortCut);
		menu.addSeparator();
		current = menu.add(exit);
		current.setAccelerator(exit.shortCut);
		
		return menu;
	}
	
	private JMenu getMapMenu()
	{
		ApplicationAction gotoCoordinateAction = new GoToCoordinateAction(mainWindow);
		ApplicationAction openFullscreenAction = new OpenFullscreenWindowAction(mainWindow);
		ApplicationAction changeMapSourceAction_osm_mapnik = new ChangeMapSourceAction(MapProvider.getMapSource(Map.OPENSTREETMAP_MAPNIK), mainWindow.getMapComponent());
		ApplicationAction changeMapSourceAction_osm_tilesAtHome = new ChangeMapSourceAction(MapProvider.getMapSource(Map.OPENSTREETMAP_TILESATHOME), mainWindow.getMapComponent());
		ApplicationAction changeMapSourceAction_osm_cycle = new ChangeMapSourceAction(MapProvider.getMapSource(Map.OPENSTREETMAP_CYCLE), mainWindow.getMapComponent());

		JMenuItem current;
		
		JMenu menu = new JMenu(I18NHelper.getInstance().getString("menu.map"));
		menu.setMnemonic(I18NHelper.getInstance().getMnemonic("menu.map"));
		
		current = menu.add(gotoCoordinateAction);
		current.setAccelerator(gotoCoordinateAction.shortCut);
		
		current = menu.add(openFullscreenAction);
		current.setAccelerator(openFullscreenAction.shortCut);
		
		menu.addSeparator();
		
		JMenu sourceMenu = new JMenu(I18NHelper.getInstance().getString("menu.map.source"));
		sourceMenu.setIcon(new ImageIcon(ImageProvider.getImage("map_source")));
		sourceMenu.setMnemonic(I18NHelper.getInstance().getMnemonic("menu.map.source"));
		menu.add(sourceMenu);
		sourceMenu.add(changeMapSourceAction_osm_mapnik);
		sourceMenu.add(changeMapSourceAction_osm_tilesAtHome);
		sourceMenu.add(changeMapSourceAction_osm_cycle);
		
		return menu;
	}
	
	private JMenu getBookMarksMenu()
	{
		ApplicationAction addBookmarkAction = new AddBookmarkAction(mainWindow);
		ApplicationAction manageBookmarksAction = new ManageBookmarksAction(mainWindow);
		
		JMenuItem current;
		
		JMenu menu = new JMenu(I18NHelper.getInstance().getString("menu.bookmarks"));
		menu.setMnemonic(I18NHelper.getInstance().getMnemonic("menu.bookmarks"));
		current = menu.add(addBookmarkAction);
		current.setAccelerator(addBookmarkAction.shortCut);
		current = menu.add(manageBookmarksAction);
		current.setAccelerator(manageBookmarksAction.shortCut);
		
		ArrayList<Bookmark> bookmarkList = BookmarkManager.getInstance().getBookmarks();
		
		if(bookmarkList.size() > 0)
		{
			menu.addSeparator();
			
			for(Bookmark bookmark : bookmarkList)
			{
				current = menu.add(new GoToBookmarkAction(bookmark, mainWindow.getMapComponent()));
			}
		}
		
		return menu;
	}
	
	private JMenu getHelperMenu()
	{
		ApplicationAction about = new AboutAction(mainWindow.getJFrame());
		
		JMenuItem current;
		
		JMenu menu = new JMenu(I18NHelper.getInstance().getString("menu.help"));
		menu.setMnemonic(I18NHelper.getInstance().getMnemonic("menu.help"));
		
		current = menu.add(about);
		current.setAccelerator(about.shortCut);
		return menu;
	}
}