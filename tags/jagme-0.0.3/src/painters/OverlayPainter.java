package painters;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.Painter;

abstract public class OverlayPainter
{	
	public abstract Painter<JXMapViewer> getPainter();
}
