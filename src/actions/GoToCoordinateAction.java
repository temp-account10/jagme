package actions;

import gui.GotoCoordinateDialog;
import gui.MainWindow;
import gui.MapComponent;

import i18n.I18NHelper;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GoToCoordinateAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	
	public GoToCoordinateAction(MainWindow mainWindow)
	{
		super(I18NHelper.getInstance().getString("action.goto"), "goto", KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK);
		
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		GotoCoordinateDialog dialog = new GotoCoordinateDialog(mainWindow.getJFrame());
		
		MapComponent mapComponent = mainWindow.getMapComponent();
		
		// set current position as the base for the user input
		dialog.setGeoPosition(mapComponent.getCenterPosition());
		
		// if the user pressed ok, jump to the given position
		if(dialog.show() == GotoCoordinateDialog.Status.OK)
		{
			mapComponent.setCenterPosition(dialog.getGeoPosition());
		}
	}

}
