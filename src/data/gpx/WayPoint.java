package data.gpx;

import java.util.Date;

import data.coordinate.LatLon;

public class WayPoint
{
	private final LatLon latlon;
	private double elevation;
	private String name;
	private Date dateTime;
	
	public WayPoint(LatLon latlon)
	{
		this.latlon = latlon;
	}
	
	public LatLon getCoordinates()
	{
		return latlon;
	}
	
	public Date getDateTime()
	{
		return dateTime;
	}
	
	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}
	
	public double getElevation()
	{
		return elevation;
	}
	
	public void setElevation(double elevation)
	{
		this.elevation = elevation;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return String.format("Waypoint (latitude: %f, longitude: %f, name: %s, elevation: %f, time: %s)\n", latlon.getLatitude(), latlon.getLongitude(), getName(), getElevation(), dateTime);
	}
}
