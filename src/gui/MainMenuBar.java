package gui;
// License: GPL. See LICENSE file for details.

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import tools.ImageProvider;

import maps.MapProvider;
import maps.Maps.Map;
import actions.AboutAction;
import actions.ApplicationAction;
import actions.ChangeMapSourceAction;
import actions.ExitAction;
import actions.GoToCoordinateAction;
import actions.OpenAction;
import actions.SaveMapExtract;

public class MainMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;

	/* File menu */
	public final JMenu fileMenu = new JMenu("File");
	public final ApplicationAction open;
	public final ApplicationAction saveMapExtract;
	public final ApplicationAction exit = new ExitAction();
    
	/* Map menu */
	public final JMenu mapMenu = new JMenu("Map");
//	TODO: cleanup
//	public final JMenu googleMenu = new JMenu("Google");
//	public final ApplicationAction backgroundAction_google_satellite;
//	public final ApplicationAction backgroundAction_google_street;
	public final JMenu sourceMenu = new JMenu("Source");
	public final ApplicationAction gotoCoordinateAction; 
	public final ApplicationAction changeMapSourceAction_osm_mapnik;
	public final ApplicationAction changeMapSourceAction_osm_tilesAtHome;
	public final ApplicationAction changeMapSourceAction_osm_cycle;
	
	/* Help menu */
	public final JMenu helpMenu = new JMenu("Help");
	public final ApplicationAction about = new AboutAction();
	
        

	public MainMenuBar(MainWindow mainWindow)
	{
		open = new OpenAction(mainWindow.getMapComponent());
		saveMapExtract = new SaveMapExtract(mainWindow.getMapComponent());
		
//		TODO: cleanup
//		backgroundAction_google_satellite = new BackgroundAction(MapProvider.getMapSource(Map.GOOGLE_SATELLITE), mainWindow.getMapComponent());
//		backgroundAction_google_street = new BackgroundAction(MapProvider.getMapSource(Map.GOOGLE_STREET), mainWindow.getMapComponent());
		gotoCoordinateAction = new GoToCoordinateAction(mainWindow.getMapComponent());
		changeMapSourceAction_osm_mapnik = new ChangeMapSourceAction(MapProvider.getMapSource(Map.OPENSTREETMAP_MAPNIK), mainWindow.getMapComponent());
		changeMapSourceAction_osm_tilesAtHome = new ChangeMapSourceAction(MapProvider.getMapSource(Map.OPENSTREETMAP_TILESATHOME), mainWindow.getMapComponent());
		changeMapSourceAction_osm_cycle = new ChangeMapSourceAction(MapProvider.getMapSource(Map.OPENSTREETMAP_CYCLE), mainWindow.getMapComponent());
		
        JMenuItem current;
        
		fileMenu.setMnemonic('F');
		current = fileMenu.add(open);
		current.setAccelerator(open.shortCut);
		fileMenu.addSeparator();
		current = fileMenu.add(saveMapExtract);
		current.setAccelerator(saveMapExtract.shortCut);
		fileMenu.addSeparator();
		current = fileMenu.add(exit);
		current.setAccelerator(exit.shortCut);
		add(fileMenu);
		
		mapMenu.setMnemonic('M');
//		TODO: cleanup
//		mapMenu.add(googleMenu);
//		googleMenu.add(backgroundAction_google_satellite);
//		googleMenu.add(backgroundAction_google_street);
		current = mapMenu.add(gotoCoordinateAction);
		current.setAccelerator(gotoCoordinateAction.shortCut);
		
		mapMenu.addSeparator();
		sourceMenu.setIcon(new ImageIcon(ImageProvider.getImage("map_source")));
		mapMenu.add(sourceMenu);
		sourceMenu.add(changeMapSourceAction_osm_mapnik);
		sourceMenu.add(changeMapSourceAction_osm_tilesAtHome);
		sourceMenu.add(changeMapSourceAction_osm_cycle);
		add(mapMenu);
		
		helpMenu.setMnemonic('H');
		current = helpMenu.add(about);
		current.setAccelerator(about.shortCut);
		add(helpMenu);
    }
}