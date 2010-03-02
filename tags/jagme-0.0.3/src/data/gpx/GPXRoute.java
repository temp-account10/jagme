package data.gpx;

import java.util.Collection;
import java.util.LinkedList;

public class GPXRoute
{
	private Collection<WayPoint> routePoints;  
	
	public GPXRoute()
	{
		routePoints = new LinkedList<WayPoint>();
	}
	
	public void add(WayPoint wayPoint)
	{
		routePoints.add(wayPoint);
	}
	
	public Collection<WayPoint> getRoutePoints()
	{
		return routePoints;
	}
}
