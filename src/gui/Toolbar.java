package gui;

import javax.swing.JToolBar;

import actions.ApplicationAction;
import actions.GoToCoordinateAction;
import actions.OpenAction;
import actions.SaveMapExtract;

public class Toolbar extends JToolBar
{
	private static final long serialVersionUID = 1L;

	public Toolbar(MainWindow mainWindow)
	{
		ApplicationAction openAction = new OpenAction(mainWindow.getMapComponent());
		ApplicationAction saveMapExtract = new SaveMapExtract(mainWindow.getMapComponent());
		ApplicationAction gotoCoordinateAction = new GoToCoordinateAction(mainWindow.getMapComponent());
		
		add(openAction);
		add(saveMapExtract);
		addSeparator();
		add(gotoCoordinateAction);
	}
}
