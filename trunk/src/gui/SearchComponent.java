package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tools.ImageProvider;
import actions.SearchAction;

public class SearchComponent extends JPanel
{
	public static enum Status { READY, SEARCHING }
	
	private static final long serialVersionUID = 1L;
	JTextField textField;
	JButton searchButton;

	public SearchComponent()
	{
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
		searchButton.setToolTipText("Initiate search for the given string");
		searchButton.addActionListener(searchAction);
		
		String[] listData = { "Shinguz", "Glapum'tianer", "Suffus", "Zypanon", "Tschung" }; 
		JList list = new JList(listData);
		
		JPanel searchPanel = new JPanel(new FlowLayout());
		searchPanel.add(textField);
		searchPanel.add(searchButton);
		
		this.setLayout(new BorderLayout());
		this.add(searchPanel, BorderLayout.NORTH);
		this.add(list, BorderLayout.CENTER);
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
				break;
			case SEARCHING:
				textField.setEnabled(false);
				searchButton.setEnabled(false);
				break;
			default:
				throw new RuntimeException("Unsupported search status!");
		}
	}
}
