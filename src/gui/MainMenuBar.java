package gui;
// License: GPL. See LICENSE file for details.

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import maps.MapProvider;
import maps.Maps.Map;
import actions.AboutAction;
import actions.ApplicationAction;
import actions.BackgroundAction;
import actions.ExitAction;
import actions.OpenAction;

public class MainMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;

	/* File menu */
	public final JMenu fileMenu = new JMenu("File");
	public final ApplicationAction open;
	public final ApplicationAction exit = new ExitAction();
    
	/* Background menu */
	public final JMenu backgroundMenu = new JMenu("Background");
	public final JMenu googleMenu = new JMenu("Google");
	public final ApplicationAction backgroundAction_google_satellite;
	public final ApplicationAction backgroundAction_google_street;
	public final JMenu openstreetmapMenu = new JMenu("OpenStreetMap");
	public final ApplicationAction backgroundAction_osm_mapnik;
	public final ApplicationAction backgroundAction_osm_tilesAtHome;
	public final ApplicationAction backgroundAction_osm_cycle;
	
	/* Help menu */
	public final JMenu helpMenu = new JMenu("Help");
	public final ApplicationAction about = new AboutAction();
	
        

	public MainMenuBar(MainWindow mainWindow)
	{
		open = new OpenAction(mainWindow.getMapComponent());
		
		backgroundAction_google_satellite = new BackgroundAction(MapProvider.getMapSource(Map.GOOGLE_SATELLITE), mainWindow.getMapComponent());
		backgroundAction_google_street = new BackgroundAction(MapProvider.getMapSource(Map.GOOGLE_STREET), mainWindow.getMapComponent());
		backgroundAction_osm_mapnik = new BackgroundAction(MapProvider.getMapSource(Map.OPENSTREETMAP_MAPNIK), mainWindow.getMapComponent());
		backgroundAction_osm_tilesAtHome = new BackgroundAction(MapProvider.getMapSource(Map.OPENSTREETMAP_TILESATHOME), mainWindow.getMapComponent());
		backgroundAction_osm_cycle = new BackgroundAction(MapProvider.getMapSource(Map.OPENSTREETMAP_CYCLE), mainWindow.getMapComponent());
		
        JMenuItem current;
        
		fileMenu.setMnemonic('F');
		current = fileMenu.add(open);
		current.setAccelerator(open.shortCut);
		fileMenu.addSeparator();
		current = fileMenu.add(exit);
		current.setAccelerator(exit.shortCut);
		add(fileMenu);
		
		backgroundMenu.setMnemonic('B');
		backgroundMenu.add(googleMenu);
		googleMenu.add(backgroundAction_google_satellite);
		googleMenu.add(backgroundAction_google_street);
		backgroundMenu.add(openstreetmapMenu);
		openstreetmapMenu.add(backgroundAction_osm_mapnik);
		openstreetmapMenu.add(backgroundAction_osm_tilesAtHome);
		openstreetmapMenu.add(backgroundAction_osm_cycle);
		add(backgroundMenu);
		
		helpMenu.setMnemonic('H');
		current = helpMenu.add(about);
		current.setAccelerator(about.shortCut);
		add(helpMenu);
    }
}