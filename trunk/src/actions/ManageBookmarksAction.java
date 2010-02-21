package actions;

import gui.BookmarkManagementDialog;
import gui.MainWindow;
import i18n.I18NHelper;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ManageBookmarksAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	
	public ManageBookmarksAction(MainWindow mainWindow)
	{
		super(I18NHelper.getInstance().getString("action.managebookmarks"), null, KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK);
		
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		new BookmarkManagementDialog(mainWindow).show();
	}

}
