package actions;

import gui.MapComponent;
import io.GPXReader;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import painters.GPXPainter;

import data.Bounds;
import data.coordinate.LatLon;
import data.gpx.GPXData;
import exceptions.ReadException;

public class OpenAction extends ApplicationAction
{
	private static final long serialVersionUID = 1L;
	private MapComponent mapComponent;
	
	public OpenAction(MapComponent mapComponent)
	{
		super("Open...", "open", KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		
		this.mapComponent = mapComponent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser fc = new JFileChooser();
		
		fc.setFileFilter(new FileFilter()
		{
			@Override
			public boolean accept(File f)
			{
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".gpx");
			}

			@Override
			public String getDescription()
			{
				return "GPX Files (*.gpx)";
			}
		});

		int state = fc.showOpenDialog(null);

		if (state == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			
			GPXReader reader;
			try
			{
				reader = new GPXReader(file);
				GPXData data = reader.getGPXDataObject();
				
				mapComponent.addOverlayPainter(new GPXPainter(data).getPainter());
				// TODO: Remove debug information
				System.out.println(data.toString());
				
				Bounds bounds = data.getBounds();
				LatLon center = bounds.getCenter();
				mapComponent.setCenterPosition(center.getGeoPosition());
				
				Set<GeoPosition> boundSet = new HashSet<GeoPosition>(2);
				boundSet.add(bounds.max.getGeoPosition());
				boundSet.add(bounds.min.getGeoPosition());
				mapComponent.setZoom(mapComponent.getTileFactory().getInfo().getMinimumZoomLevel());
				mapComponent.calculateZoomFrom(boundSet);
			}
			catch (ReadException exception)
			{
				JOptionPane.showMessageDialog(null, exception.getMessage(), "Read error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

