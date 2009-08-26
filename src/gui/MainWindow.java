package gui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class MainWindow
{
	private JFrame frame;
	private MapComponent mapComponent = new MapComponent();
	private SearchComponent searchComponent = new SearchComponent();
	
	public MainWindow()
	{
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		frame = new JFrame("Jagme"); 
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    frame.setSize(800, 600);
	    
	    MainMenuBar mainMenuBar = new MainMenuBar(this);
	    frame.setJMenuBar(mainMenuBar);
	    
	    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, searchComponent, mapComponent);
	    splitPane.setOneTouchExpandable(true);
	    splitPane.setDividerLocation(200);
	    frame.add(splitPane);
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
