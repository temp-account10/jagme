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
		super("Goto coordinate...", "goto", KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK);
		
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
