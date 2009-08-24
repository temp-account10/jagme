package gui;

import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import org.jdesktop.swingx.mapviewer.GeoPosition;

public class GotoCoordinateDialog {

	private JDialog dialog;
	private JLabel latitudeLabel;
	private JFormattedTextField latitudeTextField;
	private JLabel longitudeLabel;
	private JFormattedTextField longitudeTextField;
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
		dialog.setTitle("Goto coordinate");
		dialog.setModal(true);
		dialog.setSize(300, 200);
		
		GridLayout gridLayout = new GridLayout(3,2);
		dialog.setLayout(gridLayout);
		
		latitudeLabel = new JLabel("Latitude:");
		dialog.add(latitudeLabel);
		latitudeTextField = new JFormattedTextField(new DecimalFormat("#.###"));
		dialog.add(latitudeTextField);
		
		longitudeLabel = new JLabel("Longitude:");
		dialog.add(longitudeLabel);
		longitudeTextField = new JFormattedTextField(new DecimalFormat("#.###"));
		dialog.add(longitudeTextField);
		
		okButton = new JButton("OK");
		dialog.add(okButton);
		
		cancelButton = new JButton("Cancel");
		dialog.add(cancelButton);
	}
	
	private void updateGeoPosition()
	{
		latitudeTextField.setText(new Double(geoPosition.getLatitude()).toString());
		longitudeTextField.setText(new Double(geoPosition.getLongitude()).toString());
	}
	
	public void setGeoPosition(GeoPosition geoPosition)
	{
		this.geoPosition = geoPosition;
		updateGeoPosition();
	}
	
	public void getGeoPosition(GeoPosition geoPosition)
	{
		
	}
	
	public void show()
	{
		dialog.setVisible(true);
	}
}
