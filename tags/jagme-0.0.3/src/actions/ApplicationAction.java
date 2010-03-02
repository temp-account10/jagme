package actions;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import tools.ImageProvider;

public abstract class ApplicationAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	public KeyStroke shortCut;

	public ApplicationAction(String name, String iconName, int shortCut, int modifier)
	{
		super(name, iconName == null ? null : ImageProvider.getImageIcon(iconName));
		
		if (shortCut != 0)
		{
			this.shortCut = KeyStroke.getKeyStroke(shortCut, modifier);
		}
	}
}
