package gui;

import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class GotoCoordinateDialog {

	private JDialog dialog;
	private JLabel latitudeLabel;
	private JFormattedTextField latitudeTextField;
	private JLabel longitudeLabel;
	private JFormattedTextField longitudeTextField;
	private JButton okButton;
	private JButton cancelButton;
	
	public GotoCoordinateDialog()
	{
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		dialog = new JDialog();
		dialog.setModal(true);
		dialog.setSize(300, 200);
		
		GridLayout gridLayout = new GridLayout(2,2);
		dialog.setLayout(gridLayout);
		
		latitudeLabel = new JLabel("Latitude:");
		dialog.add(latitudeLabel);
		latitudeTextField = new JFormattedTextField(new DecimalFormat("#.###"));
		dialog.add(latitudeTextField);
		
		longitudeLabel = new JLabel("Longitude:");
		dialog.add(longitudeLabel);
		longitudeTextField = new JFormattedTextField(new DecimalFormat("#.###"));
		dialog.add(longitudeTextField);
	}
	
	public void show()
	{
		dialog.setVisible(true);
	}
}
