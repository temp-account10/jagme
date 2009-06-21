package actions;

import gui.MapComponent;

import java.awt.event.ActionEvent;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import maps.MapSource;

public class BackgroundAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MapComponent mapComponent;
	private MapSource mapSource;
	
	public BackgroundAction(MapSource mapSource, MapComponent mapComponent)
	{
		super(mapSource.getName(), null, 0, 0);
		
		this.mapSource = mapSource;
		this.mapComponent = mapComponent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		int zoom = mapComponent.getZoom();
		GeoPosition geoPosition = mapComponent.getCenterPosition();
		mapComponent.setTileFactory(mapSource.getTileFactory());
		mapComponent.setCenterPosition(geoPosition);
		mapComponent.setZoom(zoom);
	}

}
