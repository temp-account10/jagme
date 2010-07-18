package actions;

import gui.FullscreenWindow;
import gui.MainWindow;
import i18n.I18NHelper;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class OpenFullscreenWindowAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	
	public OpenFullscreenWindowAction(MainWindow mainWindow)
	{
		super(I18NHelper.getInstance().getString("action.fullscreen"), "fullscreen", KeyEvent.VK_F11, 0);
		
		this.mainWindow = mainWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		FullscreenWindow fullscreenWindow = new FullscreenWindow(mainWindow);
		fullscreenWindow.show();
	}

}
