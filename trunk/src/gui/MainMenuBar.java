package gui;

import i18n.I18NHelper;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import maps.MapProvider;
import maps.Maps.Map;
import tools.ImageProvider;
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

	public MainMenuBar(MainWindow mainWindow)
	{
		// initialize actions
		ApplicationAction openAction = new OpenAction(mainWindow.getMapComponent());
		ApplicationAction saveMapExtract = new SaveMapExtract(mainWindow.getMapComponent());
		ApplicationAction exit = new ExitAction();
		
		ApplicationAction gotoCoordinateAction = new GoToCoordinateAction(mainWindow.getMapComponent());
		ApplicationAction changeMapSourceAction_osm_mapnik = new ChangeMapSourceAction(MapProvider.getMapSource(Map.OPENSTREETMAP_MAPNIK), mainWindow.getMapComponent());
		ApplicationAction changeMapSourceAction_osm_tilesAtHome = new ChangeMapSourceAction(MapProvider.getMapSource(Map.OPENSTREETMAP_TILESATHOME), mainWindow.getMapComponent());
		ApplicationAction changeMapSourceAction_osm_cycle = new ChangeMapSourceAction(MapProvider.getMapSource(Map.OPENSTREETMAP_CYCLE), mainWindow.getMapComponent());
		
		ApplicationAction about = new AboutAction();
		
		// build up menu
        JMenuItem current;
        
        // File menu
        JMenu fileMenu = new JMenu(I18NHelper.getInstance().getString("menu.file"));
		fileMenu.setMnemonic(I18NHelper.getInstance().getMnemonic("menu.file"));
		current = fileMenu.add(openAction);
		current.setAccelerator(openAction.shortCut);
		fileMenu.addSeparator();
		current = fileMenu.add(saveMapExtract);
		current.setAccelerator(saveMapExtract.shortCut);
		fileMenu.addSeparator();
		current = fileMenu.add(exit);
		current.setAccelerator(exit.shortCut);
		add(fileMenu);
		
		// Map menu
		JMenu mapMenu = new JMenu(I18NHelper.getInstance().getString("menu.map"));
		mapMenu.setMnemonic(I18NHelper.getInstance().getMnemonic("menu.map"));
		current = mapMenu.add(gotoCoordinateAction);
		current.setAccelerator(gotoCoordinateAction.shortCut);
		
		mapMenu.addSeparator();
		
		JMenu sourceMenu = new JMenu(I18NHelper.getInstance().getString("menu.map.source"));
		sourceMenu.setIcon(new ImageIcon(ImageProvider.getImage("map_source")));
		sourceMenu.setMnemonic(I18NHelper.getInstance().getMnemonic("menu.map.source"));
		mapMenu.add(sourceMenu);
		sourceMenu.add(changeMapSourceAction_osm_mapnik);
		sourceMenu.add(changeMapSourceAction_osm_tilesAtHome);
		sourceMenu.add(changeMapSourceAction_osm_cycle);
		add(mapMenu);
		
		// Help menu
		JMenu helpMenu = new JMenu(I18NHelper.getInstance().getString("menu.help"));
		helpMenu.setMnemonic(I18NHelper.getInstance().getMnemonic("menu.help"));
		current = helpMenu.add(about);
		current.setAccelerator(about.shortCut);
		add(helpMenu);
    }
}