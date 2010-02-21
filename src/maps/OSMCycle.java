package maps;

import maps.Maps.Map;

import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class OSMCycle extends MapSource
{
	private final String name = "Cycle";
	private TileFactory tileFactory;
	
	public OSMCycle()
	{
		final int max = 17;
		
        TileFactoryInfo info = new TileFactoryInfo(0,max,max, 256, true, true, "http://www.thunderflames.org/tiles/cycle", "x","y","z")
        {
        	public String getTileUrl(int x, int y, int zoom)
        	{
                zoom = max-zoom;
                String url = this.baseURL +"/"+zoom+"/"+x+"/"+y+".png";
                return url;
            }
        };
        info.setDefaultZoomLevel(1);
        
        tileFactory = new DefaultTileFactory(info);
	}
	
	@Override
	public TileFactory getTileFactory()
	{
		return tileFactory;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public Map getMap()
	{
		return Map.OPENSTREETMAP_CYCLE;
	}
}
