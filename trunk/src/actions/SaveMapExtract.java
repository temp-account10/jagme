package actions;

import gui.MapComponent;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
		System.out.println("hello");
//		BufferedImage image;
//        Robot robot = null;
//        try {
//            robot = new Robot();
//        } catch (AWTException ex) {
//            ex.printStackTrace();
//        }
//        Rectangle viewRect = jScrollPane1.getViewport().getViewRect();
//        Point componentLocation =
//                jScrollPane1.getViewport().getView().getLocationOnScreen();
//        viewRect.translate(componentLocation.x, componentLocation.y);
//        image = robot.createScreenCapture(viewRect);

	}

}
