package gui;

import javax.swing.JToolBar;

import actions.ApplicationAction;
import actions.GoToCoordinateAction;
import actions.OpenAction;
import actions.SaveMapExtract;
import actions.OpenFullscreenWindowAction;

public class Toolbar extends JToolBar
{
	private static final long serialVersionUID = 1L;

	public Toolbar(MainWindow mainWindow)
	{
		ApplicationAction openAction = new OpenAction(mainWindow);
		ApplicationAction saveMapExtract = new SaveMapExtract(mainWindow);
		ApplicationAction gotoCoordinateAction = new GoToCoordinateAction(mainWindow);
		ApplicationAction openFullscreenAction = new OpenFullscreenWindowAction(mainWindow);
		
		add(openAction);
		add(saveMapExtract);
		addSeparator();
		add(gotoCoordinateAction);
		add(openFullscreenAction);
	}
}
