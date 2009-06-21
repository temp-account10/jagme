package gui;

import javax.swing.JFrame;

public class MainWindow
{
	private JFrame frame;
	private MapComponent mapComponent = new MapComponent();;
	
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
	    
	    frame.add(mapComponent);
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
