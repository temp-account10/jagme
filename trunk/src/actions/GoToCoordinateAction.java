package actions;

import gui.GotoCoordinateDialog;
import gui.MapComponent;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GoToCoordinateAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MapComponent mapComponent;
	
	public GoToCoordinateAction(MapComponent mapComponent)
	{
		super("Goto coordinate...", null, KeyEvent.VK_G, 0);
		
		this.mapComponent = mapComponent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		GotoCoordinateDialog dialog = new GotoCoordinateDialog();
		dialog.setGeoPosition(mapComponent.getCenterPosition());
		if(dialog.show() == GotoCoordinateDialog.Status.OK)
		{
			mapComponent.setCenterPosition(dialog.getGeoPosition());
		}
	}

}
