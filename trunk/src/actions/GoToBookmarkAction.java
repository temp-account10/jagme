package actions;

import gui.MapComponent;

import java.awt.event.ActionEvent;

import maps.MapProvider;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import data.Bookmark;
import data.coordinate.LatLon;

public class GoToBookmarkAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private Bookmark bookmark;
	private MapComponent mapComponent;
	
	public GoToBookmarkAction(Bookmark bookmark, MapComponent mapComponent)
	{
		super(bookmark.getName(), null, 0, 0);
		
		this.bookmark = bookmark;
		this.mapComponent = mapComponent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		mapComponent.getControl().setMapSource(MapProvider.getMapSource(bookmark.getMap()));
		LatLon centerCoordinates = bookmark.getCoordinates();
		GeoPosition centerGeoPos = new GeoPosition(centerCoordinates.getLatitude(), centerCoordinates.getLongitude());
		mapComponent.setCenterPosition(centerGeoPos);
		mapComponent.setZoom(bookmark.getZoomlevel());
	}
}
