package actions;

import i18n.I18NHelper;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ExitAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;

	public ExitAction()
	{
		super(I18NHelper.getInstance().getString("action.exit"), "exit", KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.exit(0);
	}

}
