package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import tools.CoordinateUtilities;
import tools.ImageProvider;

public class GotoCoordinateDialog
{
	public static enum Status { OK, CANCEL }
	private Status selectedValue;
	
	private JDialog dialog;
	private JTextField decLatitudeTextField;
	private JTextField decLongitudeTextField;
	private JFormattedTextField degLatitudeTextField;
	private JFormattedTextField degLongitudeTextField;
	private JButton okButton;
	private JButton cancelButton;
	
	private GeoPosition geoPosition;
	
	public GotoCoordinateDialog()
	{
		initializeComponents();
		initCloseListener();
	}
	
	private void initializeComponents()
	{
		dialog = new JDialog();
		
		// define basic settings
		dialog.setTitle("Goto coordinate");
		dialog.setModal(true);
		dialog.setSize(290, 160);
		dialog.setIconImage(ImageProvider.getImage("jagme"));
		
		// position dialog in the center of the screen
		Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension abounds = dialog.getSize(); 
        dialog.setLocation((bounds.width - abounds.width) / 2, (bounds.height - abounds.height) / 3); 

		// create main panel and define settings
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(5, 5));
        
        // tab "Lat/Lon"
        JPanel tabOnePanel = new JPanel();
		tabOnePanel.setLayout(new GridLayout(3,2));
		
		tabOnePanel.add(new JLabel("Latitude:"));
		decLatitudeTextField = new JTextField();
		decLatitudeTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				geoPosition = new GeoPosition(new Double(decLatitudeTextField.getText()), geoPosition.getLongitude());
				updateUI();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		tabOnePanel.add(decLatitudeTextField);
		
		tabOnePanel.add(new JLabel("Longitude:"));
		decLongitudeTextField = new JTextField();
		decLongitudeTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				geoPosition = new GeoPosition(geoPosition.getLatitude(), new Double(decLongitudeTextField.getText()));
				updateUI();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		tabOnePanel.add(decLongitudeTextField);
		
		// tab "Deg/Min/Sec"
		JPanel tabTwoPanel = new JPanel();
		tabTwoPanel.setLayout(new GridLayout(3,2));
		
		MaskFormatter latDegMinSecFormatter = null;
		MaskFormatter lonDegMinSecFormatter = null;
		try {
			latDegMinSecFormatter = new MaskFormatter("##'°##''##'.####'''' U");
			latDegMinSecFormatter.setValidCharacters("0123456789NS");
			
			lonDegMinSecFormatter = new MaskFormatter("##'°##''##'.####'''' U");
			lonDegMinSecFormatter.setValidCharacters("0123456789EW");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tabTwoPanel.add(new JLabel("Latitude:"));
		degLatitudeTextField = new JFormattedTextField(latDegMinSecFormatter);
		
		tabTwoPanel.add(degLatitudeTextField);
		
		tabTwoPanel.add(new JLabel("Longitude:"));
		degLongitudeTextField = new JFormattedTextField(lonDegMinSecFormatter);
		tabTwoPanel.add(degLongitudeTextField);
		
		// create TabbedPane and add tabs
		JTabbedPane tabbedPane = new JTabbedPane(); 
		tabbedPane.addTab("Lat/Lon", tabOnePanel); 
		tabbedPane.addTab("Deg/Min/Sec", tabTwoPanel);
		
		// button panel
		JPanel buttonPanel = new JPanel();
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedValue = Status.OK;
				dialog.setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedValue = Status.CANCEL;
				dialog.setVisible(false);
			}
		});
		buttonPanel.add(cancelButton);
		
		// add panels to dialog
		dialog.add(tabbedPane, BorderLayout.CENTER);
		dialog.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void updateUI()
	{
		decLatitudeTextField.setText(new Double(geoPosition.getLatitude()).toString());
		decLongitudeTextField.setText(new Double(geoPosition.getLongitude()).toString());
		
		degLatitudeTextField.setText(getFormattedCoordinateText(geoPosition.getLatitude(), true));
		degLongitudeTextField.setText(getFormattedCoordinateText(geoPosition.getLongitude(), false));
	}
	
	public void setGeoPosition(GeoPosition geoPosition)
	{
		this.geoPosition = geoPosition;
		updateUI();
	}
	
	public GeoPosition getGeoPosition()
	{
		return geoPosition;
	}
	
	public Status show()
	{
		dialog.setVisible(true);
		dialog.dispose();
		return selectedValue;
	}
	
	private void initCloseListener()
	{
		dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
		dialog.getRootPane().getActionMap().put("close", new AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
	}

	private String getFormattedCoordinateText(double coordinate, boolean isLatitudeCoordinate)
	{
		int degree = CoordinateUtilities.getDegreeOfDecimalCoordinate(Math.abs(coordinate));
		int minutes = CoordinateUtilities.getMinutesOfDecimalCoordinate(Math.abs(coordinate));
		double seconds = CoordinateUtilities.getSecondsOfDecimalCoordinate(Math.abs(coordinate));
		int intSeconds = (int)(seconds * 10000.0);
		
		String direction = "";
		
		if(isLatitudeCoordinate) {
			direction = getDirectionOfLatitudeCoordinate(coordinate);
		}
		else {
			direction = getDirectionOfLongitudeCoordinate(coordinate);
		}
		
		return String.format("%02d%02d%06d%s", degree, minutes, intSeconds, direction);		
	}
	
	private String getDirectionOfLatitudeCoordinate(double coordinate)
	{
		String direction = "";
		
		if(coordinate > 0)
		{
			direction = "N";
		}
		else if(coordinate < 0)
		{
			direction = "S";
		}
		else
		{
			// TODO not really good: should be nothing
			direction = "N";
		}
		
		return direction;
	}
	
	private String getDirectionOfLongitudeCoordinate(double coordinate)
	{
		String direction = "";
		
		if(coordinate > 0)
		{
			direction = "E";
		}
		else if(coordinate < 0)
		{
			direction = "W";
		}
		else
		{
			// TODO not really good: should be nothing
			direction = "E";
		}
		
		return direction;
	}
}