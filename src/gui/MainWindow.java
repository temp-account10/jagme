package gui;

import i18n.I18NHelper;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import tools.ImageProvider;

public class MainWindow
{
	private JFrame frame;
	private MapComponent mapComponent = new MapComponent();
	private SearchComponent searchComponent = new SearchComponent(mapComponent);
	
	public MainWindow()
	{
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		frame = new JFrame(I18NHelper.getInstance().getString("generic.appname"));
		// TODO ev. exit action?
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    frame.setSize(800, 600);
	    frame.setIconImage(ImageProvider.getImage("jagme"));
	    frame.setLayout(new BorderLayout());
	    
	    MainMenuBar mainMenuBar = new MainMenuBar(this);
	    frame.setJMenuBar(mainMenuBar);
	    
	    Toolbar toolbar = new Toolbar(this);
	    frame.add(toolbar, BorderLayout.NORTH);
	    
	    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, searchComponent, mapComponent);
	    splitPane.setOneTouchExpandable(true);
	    splitPane.setDividerLocation(200);
	    frame.add(splitPane, BorderLayout.CENTER);
	}
	
	public MapComponent getMapComponent()
	{
		return mapComponent;
	}
	
	public void show()
	{
		frame.setVisible(true);
	}
}
