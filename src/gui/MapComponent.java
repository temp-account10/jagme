package gui;

import i18n.I18NHelper;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.Painter;

import tools.ImageProvider;
import control.MapComponentController;
import data.MapComponentModel;

public class MapComponent extends JXMapViewer
{
	public final int STATUS_BAR_HEIGHT = 25;
	
	private static final long serialVersionUID = 1L;
	private MapComponentModel model = new MapComponentModel();
	private MapComponentController control = new MapComponentController(this, model);

	public MapComponent()
	{
		super();
		
        setLoadingImage(ImageProvider.getImage("clock"));
        
        Painter<JXMapViewer> statusTextOverlay = new Painter<JXMapViewer>()
		{
		    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
		        g.setPaint(new Color(0,0,0,180));
		        g.fillRect(0, getSize().height-STATUS_BAR_HEIGHT, getSize().width, STATUS_BAR_HEIGHT);
		        g.setPaint(Color.WHITE);
		        
		        String latitude = "-";
		        String longitude = "-";
		        if(model.areCoordinatesValid())
		        {
		        	latitude = String.format("%f", model.getCurrentLatitude());
		        	longitude = String.format("%f", model.getCurrentLongitude());
		        }
		        
		        g.drawString(String.format("%s: %s  %s: %s", I18NHelper.getInstance().getString("mapcomponent.latitude"), latitude, I18NHelper.getInstance().getString("mapcomponent.longitude"), longitude), 10, getSize().height-8);
		    }
		};
		
		control.addOverlayPainter(statusTextOverlay);
		
		this.addMouseMotionListener(new MouseMotionListener()
        {
			@Override
			public void mouseDragged(MouseEvent e)
			{ }

			@Override
			public void mouseMoved(MouseEvent e)
			{
				control.mouseMoved(e);
			}
        });
		
		this.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{ }
			
			@Override
			public void mousePressed(MouseEvent e)
			{ }
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				control.mouseExited(e);
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				control.mouseEntered(e);
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{ }
		});
	}	

	public MapComponent(MapComponent other)
	{
		this();
		
		this.getControl().adoptConfiguration(other);
	}
	
	public MapComponentController getControl()
	{
		return control;
	}
	
	public MapComponentModel getModel()
	{
		return model;
	}
}
