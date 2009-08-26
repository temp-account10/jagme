package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.mapviewer.GeoPosition;

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
	}
	
	private void initializeComponents()
	{
		dialog = new JDialog();
		
		// define basic settings
		dialog.setTitle("Goto coordinate");
		dialog.setModal(true);
		dialog.setSize(290, 160);
		
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
	
	private String getFormattedCoordinateText(double coordinate, boolean isLatitudeCoordinate)
	{
		int degree = getDegreeOfDecimalCoordinate(Math.abs(coordinate));
		int minutes = getMinutesOfDecimalCoordinate(Math.abs(coordinate));
		double seconds = getSecondsOfDecimalCoordinate(Math.abs(coordinate));
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
	
	private int getDegreeOfDecimalCoordinate(double coordinate)
	{
		return getWhole(coordinate);
	}
	
	private int getMinutesOfDecimalCoordinate(double coordinate)
	{
		return getWhole(getFraction(coordinate) * 60);
	}
	
	private double getSecondsOfDecimalCoordinate(double coordinate)
	{
		return getFraction(getFraction(coordinate) * 60) * 60;
	}
	
	private int getWhole(double value)
	{
		int result = 0;
		
		if(value >= 0)
		{
			result = (int)(Math.floor(value));
		}
		else if(value < 0)
		{
			result = (int)(Math.ceil(value));
		}
		
		return result;
	}
	
	private double getFraction(double value)
	{
		double result = 0.0;
		
		if(value >= 0)
		{
			result = value - Math.floor(value);
		}
		else if(value < 0)
		{
			result = value - Math.ceil(value);
		}
		
		return result;	
	}
}