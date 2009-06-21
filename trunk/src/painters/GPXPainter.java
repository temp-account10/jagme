package painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;

import data.gpx.GPXData;
import data.gpx.GPXTrack;
import data.gpx.WayPoint;

public class GPXPainter extends OverlayPainter
{
	private Painter<JXMapViewer> painter;
	private final GPXData data;
	
	public GPXPainter(GPXData gpxData)
	{
		data = gpxData;
		
		painter = new Painter<JXMapViewer>()
		{
			@Override
			public void paint(Graphics2D g, JXMapViewer map, int w, int h)
			{
				g = (Graphics2D) g.create();
		        //convert from viewport to world bitmap
		        Rectangle rect = map.getViewportBounds();
		        g.translate(-rect.x, -rect.y);
		        
				// paint tracks
				for(GPXTrack track: data.getTracks())
				{
					for(WayPoint wayPoint : track.getTrackPoints())
					{
						g.setColor(Color.RED);
						GeoPosition geoPosition = new GeoPosition(wayPoint.getCoordinates().getLatitude(), wayPoint.getCoordinates().getLongitude());
						Point2D point = map.getTileFactory().geoToPixel(geoPosition, map.getZoom());
		                g.fillRect((int)point.getX(),(int)point.getY(),3,3);
					}
				}
			}
		};
	}

	@Override
	public Painter<JXMapViewer> getPainter()
	{
		return painter;
	}	
}
