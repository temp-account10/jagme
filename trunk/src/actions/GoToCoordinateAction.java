package actions;

import gui.GotoCoordinateDialog;
import gui.MapComponent;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.jdesktop.swingx.mapviewer.GeoPosition;

public class GoToCoordinateAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MapComponent mapComponent;
	
	public GoToCoordinateAction(MapComponent mapComponent)
	{
		super("Goto coordinate...", null, KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK);
		
		this.mapComponent = mapComponent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		GotoCoordinateDialog dialog = new GotoCoordinateDialog();
		dialog.setGeoPosition(mapComponent.getCenterPosition());
		dialog.show();
		
		mapComponent.setCenterPosition(new GeoPosition(47.22521, 8.98941));
	}

}
