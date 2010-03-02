package painters;

import gui.MapComponent;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.Painter;

public class InformativeTextPainter extends OverlayPainter
{
	private Painter<JXMapViewer> painter;
	
	public InformativeTextPainter(final MapComponent mapComponent, final String infoText)
	{
		painter = new Painter<JXMapViewer>()
		{
		    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
		    	FontMetrics fm = g.getFontMetrics(); 
		    	int stringWidth = fm.stringWidth(infoText);
		    	int stringHeight = fm.getAscent();
		    	int borderWidth = 12;
		    	
		        g.setPaint(new Color(0,0,0,180));
		        g.fillRoundRect((mapComponent.getWidth()/2)-(stringWidth/2)-borderWidth, (mapComponent.getHeight()/2)-(stringHeight/2)-borderWidth, stringWidth+(2*borderWidth), stringHeight+(2*borderWidth), borderWidth, borderWidth);
		        g.setPaint(Color.WHITE);
		        g.drawString(infoText, (mapComponent.getWidth()/2)-(stringWidth/2), (mapComponent.getHeight()/2)+(stringHeight/2));
		    }
		};
	}
	
	@Override
	public Painter<JXMapViewer> getPainter()
	{
		return painter;
	}

}
