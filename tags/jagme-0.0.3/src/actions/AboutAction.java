package actions;

import i18n.I18NHelper;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tools.ImageProvider;

public class AboutAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private JFrame owner;
	
	public AboutAction(JFrame owner)
	{
		super(I18NHelper.getInstance().getString("action.about"), "about", KeyEvent.VK_F1, 0);
		
		this.owner = owner;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JOptionPane.showMessageDialog(owner,
				String.format("%s 0.0.3\n\n%s", I18NHelper.getInstance().getString("generic.appname"), I18NHelper.getInstance().getString("action.about.appdesc")),
				I18NHelper.getInstance().getString("action.about.title"),
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(ImageProvider.getImage("jagme")));
	}

}
