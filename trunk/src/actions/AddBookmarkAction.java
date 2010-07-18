package actions;

import gui.MainWindow;
import gui.MapComponent;
import i18n.I18NHelper;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import maps.Maps.Map;
import data.Bookmark;
import data.BookmarkManager;
import data.coordinate.LatLon;

public class AddBookmarkAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MainWindow mainWindow;
	
	public AddBookmarkAction(MainWindow mainWindow)
	{
		super(I18NHelper.getInstance().getString("action.addbookmark"), "bookmark-new", KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK);
		
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		performAction();
	}
	
	private void performAction()
	{
		MapComponent mapComponent = mainWindow.getMapComponent();
		
		Object name = JOptionPane.showInputDialog(mapComponent, I18NHelper.getInstance().getString("action.addbookmark.adddialog.msg"), I18NHelper.getInstance().getString("action.addbookmark.adddialog.title"), JOptionPane.QUESTION_MESSAGE, null, null, "");
		
		if(name != null) // user pressed 'ok'
		{
			if(name.toString().length() > 0)
			{
				LatLon coordinates = new LatLon(mapComponent.getCenterPosition().getLatitude(), mapComponent.getCenterPosition().getLongitude());
				Map map = mapComponent.getModel().getMapSource().getMap();
				int zoomlevel = mapComponent.getZoom();
				
				Bookmark bookmark = new Bookmark(name.toString(), coordinates, map, zoomlevel);
				BookmarkManager.getInstance().addBookmark(bookmark);
			}
			else
			{
				JOptionPane.showMessageDialog(mapComponent, I18NHelper.getInstance().getString("action.addbookmark.invalidname.msg"), I18NHelper.getInstance().getString("action.addbookmark.invalidname.title"), JOptionPane.WARNING_MESSAGE);
				performAction();
			}
		}
		
		mainWindow.getMainMenuBar().Update();
	}

}
