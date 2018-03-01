package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.UrbanParksSystem;
import model.User;

public class MainMenuPanel extends JPanel {
	
	private UrbanParksGUI myGUI;
	private UrbanParksSystem mySystem;
	private User myUserType;
	private String myUserName;
	
	
	public MainMenuPanel(UrbanParksGUI theGUI, UrbanParksSystem theSystem,
			User theUserType, String theUserName) {
		
		myGUI = theGUI;
		mySystem = theSystem;
		myUserType = theUserType;
		myUserName = theUserName;
		setLayout(new BorderLayout());
		setUp();

		
	}
	
	public void setUp() {
		GridLayout northLayout = new GridLayout(2, 1);
		northLayout.setVgap(10);
		JPanel northPanel = new JPanel(northLayout);
		
		JLabel menuLabel = new JLabel("Main Menu");
		JLabel userLabel = new JLabel();
		
		menuLabel.setHorizontalAlignment(JLabel.CENTER);
		menuLabel.setBackground(Color.BLACK);
		menuLabel.setForeground(Color.WHITE);
		menuLabel.setOpaque(true);
		
		userLabel.setHorizontalAlignment(JLabel.CENTER);
		userLabel.setText("Welcome, " + myUserType.getDescription() + " " + myUserType.getName());
		
		
		northPanel.add(menuLabel);
		northPanel.add(userLabel);
		
		add(northPanel, BorderLayout.NORTH);
		
		
	}

}
