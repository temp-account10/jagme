package gui;

import i18n.I18NHelper;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import tools.ImageProvider;

public class FullscreenWindow
{
	private JFrame frame;
	private MainWindow mainWindow;
	private MapComponent mapComponent;
	
	public FullscreenWindow(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		
		initializeComponents();
		initCloseListener();
	}
	
	private void initializeComponents()
	{
		frame = new JFrame(I18NHelper.getInstance().getString("generic.appname"));
		
		frame.setIconImage(ImageProvider.getImage("jagme"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mapComponent = new MapComponent(mainWindow.getMapComponent());
		
		frame.add(mapComponent);
		frame.setUndecorated(true);
	}
	
	private void initCloseListener()
	{
		InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW); 
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
		
		frame.getRootPane().getActionMap().put("close", new AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				performCloseAction();
			}
		});
	}
	
	private void performCloseAction()
	{
		mainWindow.getMapComponent().getControl().adoptConfiguration(mapComponent);
		frame.setVisible(false);
	}
	
	public void show()
	{
		frame.setVisible(true);
		frame.getGraphicsConfiguration().getDevice().setFullScreenWindow(frame);
	}
}
