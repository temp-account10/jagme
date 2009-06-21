
import gui.MainWindow;
import maps.Maps;


public class Main
{	 
	private static Maps maps;
	
	public static void main(String[] args)
	{
		MainWindow window = new MainWindow();
		window.show();
	}
	
	public static Maps getMaps()
	{
		return maps;
	}
}
