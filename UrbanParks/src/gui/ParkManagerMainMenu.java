package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.UrbanParksSystem;
import model.User;

public class ParkManagerMainMenu extends JPanel {


	private static final long serialVersionUID = 1L;
	
	/* Create String constants here of your class names
	 * This is needed for the right panel classes
	Example: private static final String CREATE_JOB_VIEW_ = "CreateJobView";
	
	*/
	private static final String MASTER_PANEL = "MasterPanel";
	private static final String LEFT_PANEL = "LeftPanel";
	private static final String RIGHT_PANEL = "RightPanel";
	private static final String CREATE_JOB_VIEW = "CreateJobView";
	
	/*
	 * NOTE: "jordan23" is ParkManager user. Sign in with this if you want
	 * to test out the GUI.
	 */
	private UrbanParksGUI myGUI;
	private UrbanParksSystem mySystem;
	private User myUserType;
	private String myUserName;
	
	private JPanel myMasterPanel;
	private JPanel myLeftPanel;
	private JPanel myRightPanel;
	
	private CardLayout myCardLayout;
	public ParkManagerMainMenu(UrbanParksGUI theGUI, UrbanParksSystem theSystem,
			User theUserType, String theUserName) {
		
		myGUI = theGUI;
		mySystem = theSystem;
		myUserType = theUserType;
		myUserName = theUserName;
			
		myMasterPanel = new JPanel(); // flow layout by default
		myLeftPanel = new JPanel(new GridLayout());
		myRightPanel = new JPanel(new CardLayout());
		myCardLayout = new CardLayout();
		
		setLayout(new BorderLayout());
		setUpNorthLabel();
		setUpMasterPanel();
	
	}
	/* This method is used by right panel classes to switch between panels*/
	public void switchPanels(String theNewPanelName) {
		
		CardLayout cardLayout = (CardLayout) myMasterPanel.getLayout();				
		cardLayout.show(myMasterPanel, theNewPanelName);
		
	}
	
	
	private void setUpMasterPanel() {
		
		JPanel createJobView = new CreateJobView(mySystem, myUserType, myUserName);
		
		// add buttonsPanel class on myLeftPanel here 
		JButton createJobButton = new JButton("Create a Job");
		createJobButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(RIGHT_PANEL);		
			}		
		});
		
		myLeftPanel.add(createJobView);
		
		/* add the right panel classes stuff here	
		Example: myRightPanel.add(new CreateJobView(mySystem,
				myCurrentUserType, myCurrentUser),
				CREATE_JOB_PANEL);
				
		*/


		myMasterPanel.add(myLeftPanel);
		myMasterPanel.add(myRightPanel);
		add(myMasterPanel);
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
