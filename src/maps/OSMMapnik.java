package maps;

import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class OSMMapnik extends MapSource
{
	private final String name = "Mapnik";
	private DefaultTileFactory tileFactory;
	
	public OSMMapnik()
	{
		final int max = 17;
		
        TileFactoryInfo info = new TileFactoryInfo(0,max,max, 256, true, true, "http://tile.openstreetmap.org", "x","y","z")
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
       
        tileFactory.setTileCache(CustomTileCache.getInstance());

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
}
