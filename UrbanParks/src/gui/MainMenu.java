package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import action.ButtonAction;
import model.ParkManager;
import model.UrbanParksSystem;
import model.User;
import model.Volunteer;

public class MainMenu extends JPanel {
	
	/*
	 * NOTE: "jordan23" is ParkManager user. Sign in with this if you want
	 * to test out the GUI.
	 * 
	 * "paolo186" is Volunteer user.
	 * "emerson01" is Staff User.
	 */
	

	private static final long serialVersionUID = 1L;
	private UrbanParksSystem mySystem;
	private User myUserType;
	private String myUserName;
	
	private Box myLeftButtons;
	private JPanel myRightPanel;
	
	
	public MainMenu(UrbanParksSystem theSystem,
			User theUserType, String theUserName) {
		
		mySystem = theSystem;
		myUserType = theUserType;
		myUserName = theUserName;
			
		myLeftButtons = Box.createVerticalBox();
		myRightPanel = new JPanel(new CardLayout());
		
		setLayout(new BorderLayout());
		setUpNorthLabel();
		setUpMainPanel();
	
	}
	/* This method is used by right panel classes to switch between panels*/
	public void switchPanels(String theNewPanelName) {
		CardLayout cardLayout = (CardLayout) myRightPanel.getLayout();				
		cardLayout.show(myRightPanel, theNewPanelName);
		myRightPanel.setVisible(true);
		
	}
	
	
	private void setUpMainPanel() {
		
		if (myUserType instanceof Volunteer) {
			setUpVolunteerMenu();
		} 
		else if (myUserType instanceof ParkManager) {
			setUpParkManagerMenu();
		} 
		else {
			setUpStaffMenu();
		}
		
		
		
		myRightPanel.setVisible(false);
		add(myRightPanel);
		
	}
	
	private void setUpVolunteerMenu() {
		String jobSignUpString = "Sign Up for a Job";
		String viewMyJobsString = "View My Jobs";
		
		AbstractAction[] buttonActions = {new ButtonAction(this, jobSignUpString), new ButtonAction(this, viewMyJobsString)};		
		String[] leftButtonNames = {jobSignUpString, viewMyJobsString};
		
		createLeftButtons(leftButtonNames, buttonActions);
		
		add(myLeftButtons, BorderLayout.WEST);
		myRightPanel.add(new VolunteerSignUpPanel(mySystem, myUserType), jobSignUpString);
		myRightPanel.add(new VolunteerViewCurrentJobsPanel(mySystem, myUserType), viewMyJobsString);
		
	}
	
	private void setUpParkManagerMenu() {
		String createJobViewString = "Create a Job";
		String viewMyJobsString = "View My Jobs";
		AbstractAction[] buttonActions = {new ButtonAction(this, createJobViewString), new ButtonAction(this, viewMyJobsString)};		
		String[] leftButtonNames = {createJobViewString, viewMyJobsString};
		
		createLeftButtons(leftButtonNames, buttonActions);
		
		add(myLeftButtons, BorderLayout.WEST);
		myRightPanel.add(new CreateJobView(mySystem, myUserType, myUserName), createJobViewString);
	
	}
	
	private void setUpStaffMenu() {
		String viewJobsString = "View Jobs On These Dates";
		String changeMaxPendingJobsString = "Change Max Pending Jobs";
		AbstractAction[] buttonActions = {new ButtonAction(this, viewJobsString),
										  new ButtonAction(this, changeMaxPendingJobsString)};		
		
		String[] leftButtonNames = {viewJobsString, changeMaxPendingJobsString};
		createLeftButtons(leftButtonNames, buttonActions);
		

		add(myLeftButtons, BorderLayout.WEST);
		myRightPanel.add(new StaffViewSelectedJobDates(mySystem, myUserType));
		
	}
	
	private void createLeftButtons(String[] leftButtonNames, AbstractAction[] buttonActions) {
		int i = 0;
		for (String name : leftButtonNames) {
			JButton button = new JButton(name);
			button.addActionListener(buttonActions[i]);
			i++;
			myLeftButtons.add(button);
			myLeftButtons.add(Box.createVerticalStrut(10));
		
		}
	}
	

	
	/**
	 * Displays the welcome label on the top.
	 */
	private void setUpNorthLabel() {
		
		JPanel userPanel = new JPanel();
		JPanel northPanel = new JPanel(new BorderLayout());
		
		JLabel menuLabel = new JLabel("Main Menu");
		JLabel userLabel = new JLabel();
		
		menuLabel.setHorizontalAlignment(JLabel.CENTER);
		menuLabel.setBackground(Color.BLACK);
		menuLabel.setForeground(Color.WHITE);
		menuLabel.setOpaque(true);
		
		userLabel.setHorizontalAlignment(JLabel.CENTER);
		userLabel.setText("Welcome, " + myUserType.getDescription() + " " + myUserType.getName());
		
		JButton signOutButton = new JButton("Sign Out");
		signOutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "Are you sure you want to sign out?";
				String title = "Signing out";
				int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					clearPanel();
					firePropertyChange("clearGUI", null, null);
				}
			}
			
		});
		
		userPanel.add(userLabel);
		userPanel.add(signOutButton);
		
		northPanel.add(menuLabel, BorderLayout.NORTH);
		northPanel.add(userPanel);
		
		
		add(northPanel, BorderLayout.NORTH);
		
	}
	
	private void clearPanel() {
		myLeftButtons.removeAll();
		myRightPanel.removeAll();
		mySystem = null;
		myUserType = null;
		myUserName = null;
	}
}
