package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import maps.MapSource;
import maps.OSMMapnik;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.Painter;

import tools.ImageProvider;

public class MapComponent extends JXMapViewer
{
	public final int STATUS_BAR_HEIGHT = 25;
	
	private static final long serialVersionUID = 1L;
	private double currentLatitude;
	private double currentLongitude;
	private CompoundPainter<Painter<JXMapViewer>> compoundPainter = new CompoundPainter<Painter<JXMapViewer>>();
	private ArrayList<Painter<JXMapViewer>> overlayPainters = new ArrayList<Painter<JXMapViewer>>();
	
	public MapComponent()
	{
		super();
		
		// set default map source
		MapSource defaultMapSource = new OSMMapnik();
		setTileFactory(defaultMapSource.getTileFactory());
		
		setZoom(defaultMapSource.getTileFactory().getInfo().getTotalMapZoom()-2);
		setCenterPosition(new GeoPosition(0.0, 0.0));
		
        setLoadingImage(ImageProvider.getImage("clock"));
        
        Painter<JXMapViewer> statusTextOverlay = new Painter<JXMapViewer>()
		{
		    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
		        g.setPaint(new Color(0,0,0,180));
		        g.fillRect(0, getSize().height-STATUS_BAR_HEIGHT, getSize().width, STATUS_BAR_HEIGHT);
		        g.setPaint(Color.WHITE);
		        g.drawString(String.format("Latitude: %f  Longitude: %f", currentLatitude, currentLongitude), 10, getSize().height-8);
		    }
		};
		
		addOverlayPainter(statusTextOverlay);
		
		
		this.addMouseMotionListener(new MouseMotionListener()
        {
			@Override
			public void mouseDragged(MouseEvent e)
			{ }

			@Override
			public void mouseMoved(MouseEvent e)
			{
				GeoPosition geoPosition = convertPointToGeoPosition(e.getPoint());
				currentLatitude = geoPosition.getLatitude();
			    currentLongitude = geoPosition.getLongitude();
			    repaint();
			}
        });
	}
	
	public void addOverlayPainter(Painter<JXMapViewer> painter)
	{
		overlayPainters.add(painter);
		
		compoundPainter.setPainters(overlayPainters.toArray(new Painter[overlayPainters.size()]));
		compoundPainter.setCacheable(false);
		setOverlayPainter(compoundPainter);
	}
	
	public void removeOverlayPainter(Painter<JXMapViewer> painter)
	{
		overlayPainters.remove(painter);
		
		compoundPainter.setPainters(overlayPainters.toArray(new Painter[overlayPainters.size()]));
		compoundPainter.setCacheable(false);
		setOverlayPainter(compoundPainter);
	}
}
