package actions;

import gui.MapComponent;

import java.awt.event.ActionEvent;

import maps.MapSource;

public class ChangeMapSourceAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MapComponent mapComponent;
	private MapSource mapSource;
	
	public ChangeMapSourceAction(MapSource mapSource, MapComponent mapComponent)
	{
		super(mapSource.getName(), null, 0, 0);
		
		this.mapSource = mapSource;
		this.mapComponent = mapComponent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		mapComponent.getControl().setMapSource(mapSource);
	}

}
