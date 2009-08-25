package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

public class AboutAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;

	public AboutAction()
	{
		super("About", "about", KeyEvent.VK_F1, 0);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JOptionPane.showMessageDialog(null, "Jagme 0.0.2\n\nJagme helps runners, mountain-bikers,\nhikers, geocachers and world explorers\nto plan and analyze trips.", "About Jagme", JOptionPane.INFORMATION_MESSAGE);
	}

}
