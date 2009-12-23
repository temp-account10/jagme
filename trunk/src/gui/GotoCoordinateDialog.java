package gui;

import i18n.I18NHelper;

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
import javax.swing.InputVerifier;
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
	private GeoPosition geoPosition;
	
	private JDialog dialog;
	private JTextField decLatitudeTextField;
	private JTextField decLongitudeTextField;
	private JFormattedTextField degLatitudeTextField;
	private JFormattedTextField degLongitudeTextField;
	private JButton okButton;
	private JButton cancelButton;
	
	public GotoCoordinateDialog()
	{
		initializeComponents();
		initCloseListener();
	}
	
	private void initializeComponents()
	{
		dialog = new JDialog();
		
		// define basic dialog settings
		dialog.setTitle(I18NHelper.getInstance().getString("dialog.goto.title"));
		dialog.setModal(true);
		dialog.setSize(300, 160);
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
		
		tabOnePanel.add(new JLabel(String.format("%s:", I18NHelper.getInstance().getString("dialog.goto.latitude"))));
		decLatitudeTextField = new JTextField();
		decLatitudeTextField.setInputVerifier(decimalCoordinateInputVerifier);
		decLatitudeTextField.addFocusListener(focusListener);
		tabOnePanel.add(decLatitudeTextField);
		
		tabOnePanel.add(new JLabel(String.format("%s:", I18NHelper.getInstance().getString("dialog.goto.longitude"))));
		decLongitudeTextField = new JTextField();
		decLongitudeTextField.setInputVerifier(decimalCoordinateInputVerifier);
		decLongitudeTextField.addFocusListener(focusListener);
		tabOnePanel.add(decLongitudeTextField);
		
		// tab "Deg/Min/Sec"
		JPanel tabTwoPanel = new JPanel();
		tabTwoPanel.setLayout(new GridLayout(3,2));
		
		MaskFormatter latDegMinSecFormatter = null;
		MaskFormatter lonDegMinSecFormatter = null;
		try
		{
			latDegMinSecFormatter = new MaskFormatter("##'°##''##'.####'''' U");
			latDegMinSecFormatter.setValidCharacters("0123456789NS");
			
			lonDegMinSecFormatter = new MaskFormatter("##'°##''##'.####'''' U");
			lonDegMinSecFormatter.setValidCharacters("0123456789EW");
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		tabTwoPanel.add(new JLabel(String.format("%s:", I18NHelper.getInstance().getString("dialog.goto.latitude"))));
		degLatitudeTextField = new JFormattedTextField(latDegMinSecFormatter);
		degLatitudeTextField.addFocusListener(focusListener);
		tabTwoPanel.add(degLatitudeTextField);
		
		tabTwoPanel.add(new JLabel(String.format("%s:", I18NHelper.getInstance().getString("dialog.goto.longitude"))));
		degLongitudeTextField = new JFormattedTextField(lonDegMinSecFormatter);
		degLongitudeTextField.addFocusListener(focusListener);
		tabTwoPanel.add(degLongitudeTextField);
		
		// create TabbedPane and add tabs
		JTabbedPane tabbedPane = new JTabbedPane(); 
		tabbedPane.addTab(I18NHelper.getInstance().getString("dialog.goto.latlon"), tabOnePanel);
		tabbedPane.addTab(I18NHelper.getInstance().getString("dialog.goto.degminsec"), tabTwoPanel);
		
		// button panel
		JPanel buttonPanel = new JPanel();
		
		okButton = new JButton(I18NHelper.getInstance().getString("generic.ok"));
		okButton.addActionListener(buttonActionListener);
		buttonPanel.add(okButton);
		
		cancelButton = new JButton(I18NHelper.getInstance().getString("generic.cancel"));
		cancelButton.addActionListener(buttonActionListener);
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

			public void actionPerformed(ActionEvent e)
			{
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
		
		if(isLatitudeCoordinate)
		{
			direction = CoordinateUtilities.getDirectionOfLatitudeCoordinate(coordinate);
		}
		else
		{
			direction = CoordinateUtilities.getDirectionOfLongitudeCoordinate(coordinate);
		}
		
		return String.format("%02d%02d%06d%s", degree, minutes, intSeconds, direction);		
	}
	
	private InputVerifier decimalCoordinateInputVerifier = new InputVerifier()
	{
		@Override
		public boolean verify(JComponent input)
		{
			String text;

			if(input instanceof JTextField)
			{
				text = ((JTextField) input).getText();
			}
			else
			{
				// TODO
				throw new RuntimeException();
			}
			
			// ensure value is in double format
			Double value;
			
			try
			{
				value = new Double(text);
			}
			catch(NumberFormatException nfe)
			{
				return false;
			}
			
			if(input == decLatitudeTextField)
			{
				if( (value < -90.0) || (value > 90.0) )
				{
					return false;
				}
			}
			else if (input == decLongitudeTextField)
			{
				if( (value < -180.0) || (value > 180.0) )
				{
					return false;
				}
			}
			else
			{
				// TODO
				throw new RuntimeException();
			}

			return true;
		}
		
		@Override
		public boolean shouldYieldFocus(JComponent input)
		{
			// if user input is invalid, insert old value into the field
			if (!verify(input))
			{
				if(input == decLatitudeTextField)
				{
					decLatitudeTextField.setText(new Double(geoPosition.getLatitude()).toString());
				}
				else if (input == decLongitudeTextField)
				{
					decLongitudeTextField.setText(new Double(geoPosition.getLongitude()).toString());
				}
			}
			
			return true;
	    }

	};
	
	private FocusListener focusListener = new FocusListener()
	{
		@Override
		public void focusLost(FocusEvent e) {
			Object source = e.getSource();

			double latitude = geoPosition.getLatitude();
			double longitude = geoPosition.getLongitude();
			
			if(source == decLatitudeTextField)
			{
				latitude = new Double(decLatitudeTextField.getText());
			}
			else if(source == decLongitudeTextField)
			{
				longitude = new Double(decLongitudeTextField.getText());
			}
			else if(source == degLatitudeTextField)
			{
				latitude = CoordinateUtilities.getDecimalCoordinateOfDegMinSec(degLatitudeTextField.getText());
			}
			else if(source == degLongitudeTextField)
			{
				longitude = CoordinateUtilities.getDecimalCoordinateOfDegMinSec(degLongitudeTextField.getText());
			}
			else
			{
				// TODO
				throw new RuntimeException();
			}
			
			geoPosition = new GeoPosition(latitude, longitude);
			updateUI();
		}
		
		@Override
		public void focusGained(FocusEvent e) { }
	};
	
	private ActionListener buttonActionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			if(source == okButton)
			{
				selectedValue = Status.OK;
			}
			else if(source == cancelButton)
			{
				selectedValue = Status.CANCEL;
			}
			else
			{
				// TODO
				throw new RuntimeException();
			}
			
			dialog.setVisible(false);
		}
	};
}