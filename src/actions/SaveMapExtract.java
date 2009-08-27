package actions;

import gui.MapComponent;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SaveMapExtract extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MapComponent mapComponent;

	public SaveMapExtract(MapComponent mapComponent)
	{
		super("Save map extract...", "save_map_extract", KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
		
		this.mapComponent = mapComponent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        Robot robot = null;
		try
		{
			robot = new Robot();
		}
		catch (AWTException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(-1);
		}
        
        Point componentLocation = mapComponent.getLocationOnScreen();
        Dimension dimension = new Dimension(mapComponent.getWidth(), mapComponent.getHeight());
        Rectangle rectangle = new Rectangle(componentLocation, dimension);
        BufferedImage image = robot.createScreenCapture(rectangle);

        File outputfile = new File("saved.png");
        try
        {
			ImageIO.write(image, "png", outputfile);
		}
        catch (IOException e1)
        {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
