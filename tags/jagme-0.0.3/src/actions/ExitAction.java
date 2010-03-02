package actions;

import i18n.I18NHelper;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import control.MainController;

public class ExitAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MainController mainController;
	
	public ExitAction(MainController mainController)
	{
		super(I18NHelper.getInstance().getString("action.exit"), "exit", KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK);
		
		this.mainController = mainController;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		mainController.exitApplication();
	}

}
