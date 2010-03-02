package gui;

import i18n.I18NHelper;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import tools.ImageProvider;
import control.MainController;
import control.MainWindowController;

public class MainWindow
{
	private JFrame frame;
	private MainController mainController;
	private MainMenuBar mainMenuBar;
	private MapComponent mapComponent = new MapComponent();
	private SearchComponent searchComponent = new SearchComponent(mapComponent);
	private MainWindowController control = new MainWindowController(this);
	
	public MainWindow(MainController mainController)
	{
		this.mainController = mainController;
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		frame = new JFrame(I18NHelper.getInstance().getString("generic.appname"));
		
	    frame.setSize(800, 600);
	    frame.setIconImage(ImageProvider.getImage("jagme"));
	    frame.setLayout(new BorderLayout());
	    
	    mainMenuBar = new MainMenuBar(this);
	    frame.setJMenuBar(mainMenuBar);
	    
	    Toolbar toolbar = new Toolbar(this);
	    frame.add(toolbar, BorderLayout.NORTH);
	    
	    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, searchComponent, mapComponent);
	    splitPane.setOneTouchExpandable(true);
	    splitPane.setDividerLocation(200);
	    frame.add(splitPane, BorderLayout.CENTER);
	    
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) { }
			
			@Override
			public void windowIconified(WindowEvent e) { }
			
			@Override
			public void windowDeiconified(WindowEvent e) { }
			
			@Override
			public void windowDeactivated(WindowEvent e) { }
			
			@Override
			public void windowClosing(WindowEvent e) { }
			
			@Override
			public void windowClosed(WindowEvent e) {
				mainController.exitApplication();
			}
			
			@Override
			public void windowActivated(WindowEvent e) { }
		});
	}
	
	public MapComponent getMapComponent()
	{
		return mapComponent;
	}
	
	public MainMenuBar getMainMenuBar()
	{
		return mainMenuBar;
	}
	
	public MainController getMainController()
	{
		return mainController;
	}
	
	public JFrame getJFrame()
	{
		return frame;
	}
	
	public void show()
	{
		frame.setVisible(true);
	}
}
