package data;

import maps.Maps.Map;
import data.coordinate.LatLon;

public class Bookmark
{
	private String name;
	private LatLon coordinates;
	private Map map;
	private int zoomlevel;
	
	public Bookmark(String name, LatLon coordinates, Map map, int zoomlevel)
	{
		this.setName(name);
		this.setCoordinates(coordinates);
		this.setMap(map);
		this.setZoomlevel(zoomlevel);
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public LatLon getCoordinates()
	{
		return coordinates;
	}
	
	public void setCoordinates(LatLon coordinates)
	{
		this.coordinates = coordinates;
	}

	public Map getMap()
	{
		return map;
	}
	
	public void setMap(Map map)
	{
		this.map = map;
	}

	public int getZoomlevel()
	{
		return zoomlevel;
	}
	
	public void setZoomlevel(int zoomlevel)
	{
		this.zoomlevel = zoomlevel;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}
