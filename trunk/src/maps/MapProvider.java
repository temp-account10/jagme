package maps;

import java.util.HashMap;
import java.util.Map;


public class MapProvider
{
	private static Map<String, MapSource> cache = new HashMap<String, MapSource>();
	
	public static MapSource getMapSource(Maps.Map map)
	{
		if(cache.containsKey(map))
		{
			return cache.get(map);
		}
		else
		{
			MapSource mapSource = null;
			switch(map)
			{
				case GOOGLE_SATELLITE:
					mapSource = new GoogleSatellite();
					break;
				case GOOGLE_STREET:
					mapSource = new GoogleStreet();
					break;
				case OPENSTREETMAP_MAPNIK:
					mapSource = new OSMMapnik();
					break;
				case OPENSTREETMAP_TILESATHOME:
					mapSource = new OSMTilesAtHome();
					break;
				case OPENSTREETMAP_CYCLE:
					mapSource = new OSMCycle();
					break;
				default:
					throw new RuntimeException("Invalid map demand!");
			}
			
			return mapSource;
		}
	}
}
