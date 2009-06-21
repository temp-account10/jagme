package maps;

import org.jdesktop.swingx.mapviewer.TileFactory;

abstract public class MapSource
{	
	public abstract TileFactory getTileFactory();
	
	public abstract String getName();
}
