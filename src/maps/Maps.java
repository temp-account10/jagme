package maps;

public class Maps
{
	public static enum Map
	{
		GOOGLE_SATELLITE,
		GOOGLE_STREET,
		OPENSTREETMAP_MAPNIK,
		OPENSTREETMAP_TILESATHOME,
		OPENSTREETMAP_CYCLE
	}
	
	public MapSource getMapSource(Map map)
	{
		return null;
	}
}
