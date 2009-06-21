package data;

import data.coordinate.LatLon;

public class Bounds
{
	public LatLon min, max;

	public Bounds(LatLon min, LatLon max)
	{
		this.min = min;
		this.max = max;
	}

	/**
	 * Construct bounds that span the whole world.
	 */
	public Bounds()
	{
		min = new LatLon(-LatLon.MAX_LAT, -LatLon.MAX_LON);
		max = new LatLon(LatLon.MAX_LAT, LatLon.MAX_LON);
	}

	/**
	 * @return Center of the bounding box.
	 */
	public LatLon getCenter()
	{
		return new LatLon((min.getLatitude() + max.getLatitude()) / 2,
				(min.getLongitude() + max.getLongitude()) / 2);
	}

	/**
	 * Extend the bounds if necessary to include the given point.
	 */
	public void extend(LatLon coordinates)
	{
		if (coordinates.getLatitude() < min.getLatitude() || coordinates.getLongitude() < min.getLongitude())
			min = new LatLon(Math.min(coordinates.getLatitude(), min.getLatitude()), Math.min(coordinates.getLongitude(),
					min.getLongitude()));
		
		if (coordinates.getLatitude() > max.getLatitude() || coordinates.getLongitude() > max.getLongitude())
			max = new LatLon(Math.max(coordinates.getLatitude(), max.getLatitude()), Math.max(coordinates.getLongitude(),
					max.getLongitude()));
	}
}
