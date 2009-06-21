package data.gpx;

import java.util.Collection;
import java.util.LinkedList;

public class GPXTrack
{
	private Collection<WayPoint> trackPoints;  
	private String name;
	
	public GPXTrack()
	{
		trackPoints = new LinkedList<WayPoint>();
	}
	
	public void add(WayPoint wayPoint)
	{
		trackPoints.add(wayPoint);
	}
	
	public Collection<WayPoint> getTrackPoints()
	{
		return trackPoints;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		String value = String.format("Track name: %s\n", getName());
		
		for(WayPoint wayPoint : trackPoints)
		{
			value += wayPoint.toString();
		}
		
		return value;
	}
}
