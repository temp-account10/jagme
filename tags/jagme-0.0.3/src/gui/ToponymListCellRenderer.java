package gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.geonames.Toponym;

public class ToponymListCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;
	    
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
	    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	    Toponym toponym = (Toponym)value;
	    setText(toponym.getName() +" "+ toponym.getCountryName());
	    return this;
	  }

}
