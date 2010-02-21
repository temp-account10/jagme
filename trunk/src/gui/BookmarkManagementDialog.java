package gui;

import i18n.I18NHelper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.Bookmark;
import data.BookmarkManager;

public class BookmarkManagementDialog
{
	private JDialog dialog;
	private JList bookmarkList;
	private MainWindow mainWindow;
	private JButton deleteButton;
	private JButton renameButton;
	
	public BookmarkManagementDialog(MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		
		initializeComponents();
		initCloseListener();
		
		UpdateUI();
	}

	private void initializeComponents()
	{
		dialog = new JDialog();
		dialog.setModal(true);
		dialog.setSize(300, 250);
		dialog.setLayout(new BorderLayout());		
		dialog.setTitle(I18NHelper.getInstance().getString("dialog.bookmarkmanagement.title"));
		dialog.setLocationRelativeTo(mainWindow.getJFrame());
		
		dialog.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) { }
			
			@Override
			public void windowIconified(WindowEvent e) { }
			
			@Override
			public void windowDeiconified(WindowEvent e) { }
			
			@Override
			public void windowDeactivated(WindowEvent e) { }
			
			@Override
			public void windowClosing(WindowEvent e) { }
			
			@Override
			public void windowClosed(WindowEvent e) {
				closeWindowClicked();
			}
			
			@Override
			public void windowActivated(WindowEvent e) { }
		});
		
		JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		ArrayList<Bookmark> bookmarks = BookmarkManager.getInstance().getBookmarks();
		DefaultListModel lm = new DefaultListModel();
		
		for(Bookmark bookmark : bookmarks)
		{
			lm.addElement(bookmark);
		}
		
		bookmarkList = new JList(lm);
		bookmarkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookmarkList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				UpdateUI();
			}
		});
		
		mainPanel.add(new JScrollPane(bookmarkList), BorderLayout.CENTER);
		
		deleteButton = new JButton(I18NHelper.getInstance().getString("dialog.bookmarkmanagement.deletebutton.label"));
		deleteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				deleteButtonClicked();
			}
		});
		buttonPanel.add(deleteButton);
		
		buttonPanel.add(Box.createVerticalStrut(5));
		
		renameButton = new JButton(I18NHelper.getInstance().getString("dialog.bookmarkmanagement.renamebutton.label"));
		renameButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				renameButtonClicked();
			}
		});
		buttonPanel.add(renameButton);
		
		mainPanel.add(buttonPanel, BorderLayout.EAST);
		
		JPanel closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton closeButton = new JButton(I18NHelper.getInstance().getString("dialog.bookmarkmanagement.closebutton.label"));
		closeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				closeWindowClicked();
			}
		});
		closeButtonPanel.add(closeButton);
		
		dialog.add(mainPanel, BorderLayout.CENTER);
		dialog.add(closeButtonPanel, BorderLayout.SOUTH);
	}
	
	public void show()
	{
		dialog.setVisible(true);
	}
	
	private void UpdateUI()
	{
		if(bookmarkList.getSelectedIndex() != -1)
		{
			renameButton.setEnabled(true);
			deleteButton.setEnabled(true);
		}
		else
		{
			renameButton.setEnabled(false);
			deleteButton.setEnabled(false);
		}
	}
	
	private void deleteButtonClicked()
	{
		Bookmark selectedBookmark = (Bookmark)bookmarkList.getSelectedValue();
		((DefaultListModel)bookmarkList.getModel()).removeElement(selectedBookmark);
		BookmarkManager.getInstance().deleteBookmark(selectedBookmark);
	}

	private void renameButtonClicked()
	{
		Bookmark bookmark = (Bookmark)bookmarkList.getSelectedValue();
		
		Object newName = JOptionPane.showInputDialog(dialog, I18NHelper.getInstance().getString("dialog.renamebookmark.renamedialog.msg"), I18NHelper.getInstance().getString("dialog.renamebookmark.renamedialog.title"), JOptionPane.QUESTION_MESSAGE, null, null, bookmark.getName());
		
		if(newName != null) // user pressed 'ok'
		{
			if(newName.toString().length() > 0)
			{
				bookmark.setName(newName.toString());
				bookmarkList.updateUI();
			}
			else
			{
				JOptionPane.showMessageDialog(dialog, I18NHelper.getInstance().getString("dialog.renamebookmark.invalidname.msg"), I18NHelper.getInstance().getString("dialog.renamebookmark.invalidname.title"), JOptionPane.WARNING_MESSAGE);
				renameButtonClicked();
			}
		}
	}
	
	private void closeWindowClicked()
	{
		dialog.setVisible(false);
		mainWindow.getMainMenuBar().Update();
	}
	
	private void initCloseListener()
	{
		dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
		dialog.getRootPane().getActionMap().put("close", new AbstractAction()
		{
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				closeWindowClicked();
			}
		});
	}
}
