package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.geonames.Toponym;
import org.jdesktop.swingx.mapviewer.GeoPosition;

import tools.ImageProvider;
import actions.SearchAction;

public class SearchComponent extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static enum Status { READY, SEARCHING }
	
	private MapComponent mapComponent;
	
	private JTextField textField;
	private JButton searchButton;
	private JList list;
	private DefaultListModel listModel;
	
	public SearchComponent(MapComponent mapComponent)
	{
		this.mapComponent = mapComponent;
		
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		SearchAction searchAction = new SearchAction(this);
		
		textField = new JTextField(14);
		textField.addActionListener(searchAction);
		ImageIcon searchIcon = new ImageIcon(ImageProvider.getImage("search"));
		searchButton = new JButton(searchIcon);
		searchButton.setMargin(new Insets(0, 0, 0, 0));
		// TODO i18n
		searchButton.setToolTipText("Initiate search for the given string");
		searchButton.addActionListener(searchAction);
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new ToponymListCellRenderer());
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// check for a real selection (user selected an entry
				// and released the mouse button)
				if(e.getValueIsAdjusting())
				{
					return;
				}
				
				Object selectedObject = list.getSelectedValue();
				if(selectedObject instanceof Toponym)
				{
					Toponym selectedToponym = (Toponym)selectedObject;
					GeoPosition geoPosition = new GeoPosition(selectedToponym.getLatitude(), selectedToponym.getLongitude());
					mapComponent.setCenterPosition(geoPosition);
					
					// zoom in
					int minZoomeLevel = mapComponent.getTileFactory().getInfo().getMinimumZoomLevel();
					mapComponent.setZoom(minZoomeLevel);
				}
			}
		});
		
		JPanel searchPanel = new JPanel(new FlowLayout());
		searchPanel.add(textField);
		searchPanel.add(searchButton);
		
		this.setLayout(new BorderLayout());
		this.add(searchPanel, BorderLayout.NORTH);
		this.add(new JScrollPane(list), BorderLayout.CENTER);
	}
	
	public String getSearchString()
	{
		return textField.getText();
	}
	
	public void setStatus(Status status)
	{
		switch(status)
		{
			case READY:
				textField.setEnabled(true);
				searchButton.setEnabled(true);
				list.setEnabled(true);
				break;
			case SEARCHING:
				textField.setEnabled(false);
				searchButton.setEnabled(false);
				list.setEnabled(false);
				break;
			default:
				throw new RuntimeException("Unsupported search status!");
		}
	}
	
	public void setListData(List<Toponym> list)
	{
		listModel.removeAllElements();
		
		for (Toponym toponym : list)
		{
			listModel.addElement(toponym);
		}
	}
}
