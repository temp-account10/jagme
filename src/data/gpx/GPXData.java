package data.gpx;

import java.util.Collection;
import java.util.LinkedList;

import data.Bounds;

public class GPXData
{
	private Collection<WayPoint> wayPoints = new LinkedList<WayPoint>();
	private Collection<GPXRoute> routes = new LinkedList<GPXRoute>();
	private Collection<GPXTrack> tracks = new LinkedList<GPXTrack>();
	private Bounds bounds;
	
	public GPXData()
	{
		
	}
	
	public void addWayPoint(WayPoint wayPoint)
	{
		wayPoints.add(wayPoint);
	}
	
	public Collection<GPXRoute> getRoutes()
	{
		return routes;
	}
	
	public Collection<GPXTrack> getTracks()
	{
		return tracks;
	}
	
	public void calculateBounds()
	{
		for(WayPoint wayPoint : wayPoints)
		{
			if(bounds == null)
			{
				bounds = new Bounds(wayPoint.getCoordinates(), wayPoint.getCoordinates());
			}
			else
			{
				bounds.extend(wayPoint.getCoordinates());
			}
		}
		
		for(GPXRoute route : routes)
		{
			Collection<WayPoint> wayPointCollection = route.getRoutePoints();
			for(WayPoint wayPoint : wayPointCollection)
			{
				if(bounds == null)
				{
					bounds = new Bounds(wayPoint.getCoordinates(), wayPoint.getCoordinates());
				}
				else
				{
					bounds.extend(wayPoint.getCoordinates());
				}
			}
		}
		
		for(GPXTrack track : tracks)
		{
			Collection<WayPoint> wayPointCollection = track.getTrackPoints();
			for(WayPoint wayPoint : wayPointCollection)
			{
				if(bounds == null)
				{
					bounds = new Bounds(wayPoint.getCoordinates(), wayPoint.getCoordinates());
				}
				else
				{
					bounds.extend(wayPoint.getCoordinates());
				}
			}
		}
		
		if(bounds == null)
		{
			bounds = new Bounds();
		}
	}
	
	public Bounds getBounds()
	{
		return bounds;
	}
	
	@Override
	public String toString()
	{
		String value = "Waypoints:\n";
		for(WayPoint wayPoint : wayPoints)
		{
			value += wayPoint.toString();
		}
		
		value += "Tracks:\n";
		for(GPXTrack track : tracks)
		{
			value += track.toString();
		}
		return value;
	}
}
