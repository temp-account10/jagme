package data;

import maps.MapSource;

public class MapComponentModel
{
	private boolean coordinatesValid;
	private double currentLatitude;
	private double currentLongitude;
	private MapSource mapSource;
	
	public boolean areCoordinatesValid()
	{
		return coordinatesValid;
	}
	
	public void setCoordinatesValid(boolean coordinatesValid)
	{
		this.coordinatesValid = coordinatesValid;
	}
	
	public double getCurrentLatitude()
	{
		return currentLatitude;
	}
	
	public void setCurrentLatitude(double currentLatitude)
	{
		this.currentLatitude = currentLatitude;
	}
	
	public double getCurrentLongitude()
	{
		return currentLongitude;
	}

	public void setCurrentLongitude(double currentLongitude)
	{
		this.currentLongitude = currentLongitude;
	}

	public void setMapSource(MapSource mapSource)
	{
		this.mapSource = mapSource;
	}

	public MapSource getMapSource()
	{
		return mapSource;
	}
}
