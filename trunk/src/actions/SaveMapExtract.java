package actions;

import gui.MapComponent;
import io.ImageFileFilter;

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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import tools.FileUtilities;
import tools.ImageUtilities;

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
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle("Save map extract...");
		fileChooser.setFileFilter(new ImageFileFilter());
		
		int returnValue = fileChooser.showSaveDialog(null);
		
		switch(returnValue)
		{
			case JFileChooser.APPROVE_OPTION:
				saveImage(fileChooser.getSelectedFile());
				break;
			case JFileChooser.CANCEL_OPTION:
				break;
			case JFileChooser.ERROR_OPTION:
				// TODO
				break;
		}
	}
	
	private void saveImage(File outputFile)
	{
		String fileSuffix = FileUtilities.getSuffix(outputFile);
		
		if(ImageUtilities.isWriteSupportedFormat(fileSuffix))
		{
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
			Dimension dimension = new Dimension(mapComponent.getWidth(), mapComponent.getHeight() - mapComponent.STATUS_BAR_HEIGHT);
			Rectangle rectangle = new Rectangle(componentLocation, dimension);
			BufferedImage image = robot.createScreenCapture(rectangle);
			
			try
			{
				ImageIO.write(image, fileSuffix, outputFile);
			}
	        catch (IOException e1)
	        {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		else // user chose an unsupported file type
		{
			JOptionPane.showMessageDialog(null, "You chose an unsupported file type.\nPlease choose a different one.", "Unsupported file type", JOptionPane.ERROR_MESSAGE);
		}
	}
}
