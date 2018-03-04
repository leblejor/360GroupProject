package gui;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.UrbanParksSystem;

public class VolunteerPanel extends JPanel {
	
	public static final String VOLUNTEER_SIGN_UP_PANEL = "VolunteerSignUpPanel";
	public static final String VOLUNTEER_VIEW_CURRENT_JOBS_PANEL = "VolunteerViewCurrentJobsPanel";
	public static final String VOLUNTEER_PANEL = "VolunteerPanel";
	
	// VolunteerPanel will contain myMasterPanel.
	// myMasterPanel will contain myLeftPanel and myRightPanel.
	
	/** myMasterPanel contains myLeftPanel and myRightPanel, and nothing else.  */
	
	private JPanel myMasterPanel;
	
	/** myLeftPanel shows the main Volunteer screen. */
	
	private JPanel myLeftPanel;
	
	/** myMRightPanel shows either VolunteerSignUpPanel or VolunteerViewCurrentJobs. */
	
	private JPanel myRightPanel;
	
	/** Various fields that need to be pass in as an argument. */
	
	private UrbanParksGUI myGUI;
	private JPanel myGUIMasterPanel;
	private String myMainMenu;	
	private model.Volunteer myUser;
	private model.UrbanParksSystem mySystem;
	
	/** Instances of the other two panels are declared early on to work with CardLayout. */
	
	VolunteerSignUpPanel mySignUpPanel;
	VolunteerViewCurrentJobsPanel myViewCurrentJobsPanel;
	CardLayout cardLayoutPanel;
	
	public VolunteerPanel(UrbanParksGUI theGUI, JPanel theGUIMasterPanel, String theMainMenu, model.Volunteer theUser, model.UrbanParksSystem theSystem) {
		
		/** Various fields from UrbanParksGUI that need to be used later on. */
		
		myGUI = theGUI;
		myGUIMasterPanel = theGUIMasterPanel;
		myMainMenu = theMainMenu;
		myUser = theUser;
		mySystem = theSystem;

		/** Set the name of VolunteerPanel. */
		
		setName(VOLUNTEER_PANEL);
		
		/** VolunteerSignUp and VolunteerViewCurrentJobs are initialized here; they'll be used with myRightPanel later on.*/
		
		mySignUpPanel = new VolunteerSignUpPanel(theGUI, myGUIMasterPanel, myUser, mySystem);
		myViewCurrentJobsPanel = new VolunteerViewCurrentJobsPanel(theGUI, myGUIMasterPanel, myUser);
			
		/** myMasterPanel will hold everything except for the JLabel welcome message at the top.*/
		
		myMasterPanel = new JPanel(new GridLayout(1,2));
		
		/** myLeftPanel will use BoxLayout. */
		
		myLeftPanel = new JPanel();
		myLeftPanel.setLayout (new BoxLayout (myLeftPanel, BoxLayout.Y_AXIS));  
		
		/** myRightPanel will use CardLayout. */
		
		cardLayoutPanel = new CardLayout();  
		myRightPanel = new JPanel(cardLayoutPanel);
		
		/** method for setting the main framework.. */
		
		setupPanel(); 
		
	}
	
	/**
	 *  Method for setting up the overall VolunteerPanel panel.
	 */
	
	 public void setupPanel() {
		
		// Sets the layout for the overall VolunteerPanel.
		 // BorderLayout.NORTH will contain the welcoming message.
		 // BorderLayout.CENTER will hold myMasterPanel (and in extension myLeftPanel/myRightPanel).
		
		setLayout(new BorderLayout());
		
		// Creates the welcoming message at the top of the screen.
		
		setupVolunteerMenuWelcomeLabel();
		
		// Add the initial Volunteer buttons to myLeftPanel.
		
		setupVolunteerMenuOptionsPanel();
		
		// Make everything visible to the screen.
		
		myLeftPanel.setVisible(true);
		myRightPanel.setVisible(true);
		myMasterPanel.add(myLeftPanel);
		myMasterPanel.add(myRightPanel);
		add(myMasterPanel);
		
	}
	 
	 /**
		 *  Method for creating the welcome message at the top of the screen.
		 */
	
	public void setupVolunteerMenuWelcomeLabel() {
		
		JLabel volunteerMenuLabel = new JLabel();
		
		volunteerMenuLabel.setText("What would you like to do today, Volunteer " + myUser.getName() + "?");
		volunteerMenuLabel.setHorizontalAlignment(JLabel.CENTER);
		volunteerMenuLabel.setVisible(true);
		add(volunteerMenuLabel, BorderLayout.NORTH);
		
	}
	
	/**
	 *  Method for creating the three main buttons of the Volunteer Panel.
	 */
	
	public void setupVolunteerMenuOptionsPanel() {
		
		// Create empty buttons for "Sign Out", "Sign up for a new job" and "View your current jobs" options.
		
		Button signOutButton = new Button("Sign out");
		Button signUpForNewJobButton = new Button("Sign up for a new job");
		Button viewCurrentJobsButton = new Button("View your current jobs");
		
		// ActionListener for the "Sign Out" button. 
		// Entire panel is brought back to the main menu.
		
		signOutButton.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		    		myGUI.switchPanels(myMainMenu);
		    		myGUI.setSize(getPreferredSize());
		    		myGUI.setMinimumSize(getPreferredSize());
		    	}
		    });  
		
		// ActionListener for the "Sign Up For a New Job" button. 
		// The right panel is changed to a brand new panel.
		
		signUpForNewJobButton.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		    	
		    	myRightPanel.add(mySignUpPanel, VOLUNTEER_SIGN_UP_PANEL);    	
		    	cardLayoutPanel.show(myRightPanel, VOLUNTEER_SIGN_UP_PANEL);
		    	
		    	myGUI.setSize(new Dimension(800,500));
		    	
		    	}  
		    });  
		
		// ActionListener for the "View Current Jobs" button. 
		// The right panel is changed to a brand new panel.
		
		viewCurrentJobsButton.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
	    	
		    	myRightPanel.add(myViewCurrentJobsPanel, VOLUNTEER_VIEW_CURRENT_JOBS_PANEL);	    	
		    	cardLayoutPanel.show(myRightPanel, VOLUNTEER_VIEW_CURRENT_JOBS_PANEL);
		    	
		    	myGUI.setSize(new Dimension(800,500));
		    	
		    	}
		    });  
		
		JPanel mySignedOutLayout = new JPanel(new FlowLayout());
		JPanel mySignUpForNewJobLayout = new JPanel(new FlowLayout());
		JPanel myViewCurrentJobsLayout = new JPanel(new FlowLayout());
		
		mySignedOutLayout.add(signOutButton);
		mySignUpForNewJobLayout.add(signUpForNewJobButton);
		myViewCurrentJobsLayout.add(viewCurrentJobsButton);
		
		myLeftPanel.add(mySignedOutLayout);
		myLeftPanel.add(mySignUpForNewJobLayout);
		myLeftPanel.add(myViewCurrentJobsLayout);
	
	} 

}