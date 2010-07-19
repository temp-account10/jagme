package control;

import gui.MapComponent;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import maps.MapProvider;
import maps.MapSource;
import maps.Maps.Map;
import maps.OSMMapnik;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.Painter;

import tools.Configuration;
import data.MapComponentModel;

public class MapComponentController
{
	private MapComponent view;
	private MapComponentModel model;
	private CompoundPainter<Painter<JXMapViewer>> compoundPainter = new CompoundPainter<Painter<JXMapViewer>>();
	private ArrayList<Painter<JXMapViewer>> overlayPainters = new ArrayList<Painter<JXMapViewer>>();
	
	public MapComponentController(MapComponent view, MapComponentModel model)
	{
		this.view = view;
		this.model = model;
	}
	
	public void addOverlayPainter(Painter<JXMapViewer> painter)
	{
		overlayPainters.add(painter);
		
		compoundPainter.setPainters(overlayPainters.toArray(new Painter[overlayPainters.size()]));
		compoundPainter.setCacheable(false);
		view.setOverlayPainter(compoundPainter);
	}
	
	public void removeOverlayPainter(Painter<JXMapViewer> painter)
	{
		overlayPainters.remove(painter);
		
		compoundPainter.setPainters(overlayPainters.toArray(new Painter[overlayPainters.size()]));
		compoundPainter.setCacheable(false);
		view.setOverlayPainter(compoundPainter);
	}
	
	public void mouseEntered(MouseEvent e)
	{
		model.setCoordinatesValid(true);
	    view.repaint();
	}
	
	public void mouseExited(MouseEvent e)
	{
		model.setCoordinatesValid(false);
	    view.repaint();
	}
	
	public void mouseMoved(MouseEvent e)
	{
		GeoPosition geoPosition = view.convertPointToGeoPosition(e.getPoint());
		model.setCurrentLatitude(geoPosition.getLatitude());
		model.setCurrentLongitude(geoPosition.getLongitude());
	    view.repaint();
	}
	
	public void adoptConfiguration(MapComponent other)
	{
		model.setMapSource(other.getModel().getMapSource());
		view.setTileFactory(other.getModel().getMapSource().getTileFactory());
		
		GeoPosition geoPosition = new GeoPosition(other.getModel().getCurrentLatitude(), other.getModel().getCurrentLongitude());
		model.setCurrentLatitude(geoPosition.getLatitude());
		model.setCurrentLongitude(geoPosition.getLongitude());
		view.setCenterPosition(geoPosition);
		
		view.setZoom(other.getZoom());
	}
	
	public void setMapSource(MapSource mapSource)
	{
		model.setMapSource(mapSource);
		
		int zoom = view.getZoom();
		GeoPosition geoPosition = view.getCenterPosition();
		view.setTileFactory(mapSource.getTileFactory());
		view.setCenterPosition(geoPosition);
		view.setZoom(zoom);
	}
	
	// restore the position if possible, otherwise set the default map
	public void restorePosition()
	{
		Configuration config = Configuration.getInstance();
		
		if (config.hasProperty("mapcomponent.lastlatitude") &&
				config.hasProperty("mapcomponent.lastlongitude") &&
				config.hasProperty("mapcomponent.lastmap") &&
				config.hasProperty("mapcomponent.lastzoomlevel"))
		{
			double latitude = config.getDoubleProperty("mapcomponent.lastlatitude");
			double longitude = config.getDoubleProperty("mapcomponent.lastlongitude");
			String mapValue = config.getProperty("mapcomponent.lastmap");
			Map map = Enum.valueOf(Map.class, mapValue);
			int zoomlevel = config.getIntegerProperty("mapcomponent.lastzoomlevel");

			setMapSource(MapProvider.getMapSource(map));
			
			model.setCurrentLatitude(latitude);
			model.setCurrentLongitude(longitude);
			
			view.setCenterPosition(new GeoPosition(latitude, longitude));
			view.setZoom(zoomlevel);
		}	
		else
		{
			// set default map source
			MapSource defaultMapSource = new OSMMapnik();
			setMapSource(defaultMapSource);
			
			view.setZoom(defaultMapSource.getTileFactory().getInfo().getTotalMapZoom()-2);
			view.setCenterPosition(new GeoPosition(0.0, 0.0));
		}
	}
	
	public void savePosition()
	{
		Configuration config = Configuration.getInstance();
		
		config.setProperty("mapcomponent.lastlatitude", view.getCenterPosition().getLatitude());
		config.setProperty("mapcomponent.lastlongitude", view.getCenterPosition().getLongitude());
		config.setProperty("mapcomponent.lastmap", model.getMapSource().getMap());
		config.setProperty("mapcomponent.lastzoomlevel", view.getZoom());
	}
}
