package maps;

import maps.Maps.Map;

import org.jdesktop.swingx.mapviewer.TileFactory;

abstract public class MapSource
{	
	public abstract TileFactory getTileFactory();
	
	public abstract String getName();
	
	public abstract Map getMap();
}
