package maps;

import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class GoogleSatellite extends MapSource
{
	private final String name = "Satellite";
	private TileFactory tileFactory;
	
	public GoogleSatellite()
	{
		final int max = 17;

        TileFactoryInfo info = new TileFactoryInfo(0,max,max, 256, true, true, "http://kh.google.com/kh?v=3", "x","y","z")
        {
        	public String getTileUrl(int x, int y, int zoom)
        	{
        		String url = baseURL +
                "&t=" + quadtree(x, y, zoom);
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
	
	private String quadtree(int x, int y, int zoom)
	{	
		char[][] quadnames = {{'q', 't'}, { 'r',  's'}};
		String quadtreePath = "";

		for(int i=1; i <= (17-zoom); i++)
		{
			int rx = x%2;
			int ry = y%2;
			
			quadtreePath = quadnames[rx][ry] + quadtreePath;

			x = x/2;
			y = y/2;
		}
		System.out.println("t" + quadtreePath);
		return "t" + quadtreePath;
	}
}
