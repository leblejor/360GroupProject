package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ParkManager;
import model.UrbanParksSystem;
import model.User;
import model.Volunteer;

public class MainMenu extends JPanel {
	
	/* Create String constants here of your class names
	 * This is needed for the right panel classes
	Example: private static final String CREATE_JOB_VIEW_ = "CreateJobView";
	
	*/
	
	/*
	 * NOTE: "jordan23" is ParkManager user. Sign in with this if you want
	 * to test out the GUI.
	 * 
	 * "paolo186" is Volunteer user.
	 * "emerson01" is Staff User.
	 */
	private UrbanParksGUI myGUI;
	private UrbanParksSystem mySystem;
	private User myUserType;
	private String myUserName;
	
	private JPanel myMasterPanel;
	private JPanel myLeftPanel;
	private JPanel myRightPanel;
	
	
	public MainMenu(UrbanParksGUI theGUI, UrbanParksSystem theSystem,
			User theUserType, String theUserName) {
		
		myGUI = theGUI;
		mySystem = theSystem;
		myUserType = theUserType;
		myUserName = theUserName;
			
		myMasterPanel = new JPanel(); // flow layout by default
		myLeftPanel = new JPanel(new GridLayout());
		myRightPanel = new JPanel(new CardLayout());
		
		setLayout(new BorderLayout());
		setUpNorthLabel();
		setUpMasterPanel();
	
	}
	/* This method is used by right panel classes to switch between panels*/
	public void switchPanels(String theNewPanelName) {
		
		CardLayout cardLayout = (CardLayout) myRightPanel.getLayout();				
		cardLayout.show(myRightPanel, theNewPanelName);
		
	}
	
	
	private void setUpMasterPanel() {
		
		if (myUserType instanceof Volunteer) {
			setUpVolunteerMenu();
		} 
		else if (myUserType instanceof ParkManager) {
			setUpParkManagerMenu();
		} 
		else {
			setUpStaffMenu();
		}
		
		
		myMasterPanel.add(myLeftPanel);
		myMasterPanel.add(myRightPanel);
		add(myMasterPanel);
	}
	
	private void setUpVolunteerMenu() {
		
	}
	
	private void setUpParkManagerMenu() {
		/* add buttonsPanel class on myLeftPanel here 
		
		*/
		
		/* add the right panel classes stuff here	
		Example: myRightPanel.add(new CreateJobView(mySystem,
				myCurrentUserType, myCurrentUser),
				CREATE_JOB_VIEW);
				
		*/
	}
	
	private void setUpStaffMenu() {
		
	}
	
	/**
	 * Displays the welcome label on the top.
	 */
	private void setUpNorthLabel() {
		
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
