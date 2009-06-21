package maps;

import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class GoogleStreet extends MapSource
{
	private final String name = "Street";
	private TileFactory tileFactory;
	
	public GoogleStreet()
	{
		final int max = 17;
		
        TileFactoryInfo info = new TileFactoryInfo(0,max,max, 256, true, true, "http://mt.google.com/mt?w=2.43", "x","y","z")
        {
        	public String getTileUrl(int x, int y, int zoom)
        	{
        		String url = baseURL +
                "&x=" + x +
                "&y=" + y +
                "&zoom=" + zoom;
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
}
