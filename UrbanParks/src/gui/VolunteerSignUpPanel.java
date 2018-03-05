package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton; 
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Job;

public class VolunteerSignUpPanel extends JPanel {
	
	// VolunteerSignUpPanel -> BorderLayout
	// Welcome Message -> BorderLayout.NORTH
	// myMasterPanel -> BorderLayout.CENTER
	// myInformationPanel -> BorderLayout.SOUTH
	
	/** myMasterPanel contains all the buttons representing the jobs. */
	
	private JPanel myMasterPanel;
	
	/** myInformationPanel contains the text describing the currently selected job. */
	
	private JPanel myInformationPanel;
	
	/** jobNameLabel contains the name of the currently selected job. */
	
	private JLabel jobNameLabel;
	
	/** jobNameLabel contains the description of the currently selected job. */
	
	private JLabel jobDescriptionLabel;
	
	/** jobStartDateLabel contains the start date of the job. */
	
	private JLabel jobStartDateLabel;
	
	/** jobEndDateLabel contains the end date of the job. */
	
	private JLabel jobEndDateLabel;
	
	/** signUpConfirmation contains a message that confirms the success status of a job signup. */
	
	private JLabel signUpConfirmation;
	
	/** Message at the top of the screen */
	
	private JLabel volunteerMenuLabel;
	
	/** selectedJob contains the currently selected job by the user. */
	
	private model.Job selectedJob;
	
	private UrbanParksGUI myGUI;
	private model.Volunteer myUser;
	private model.UrbanParksSystem mySystem;
	
	public VolunteerSignUpPanel(UrbanParksGUI theGUI, JPanel theGUIMasterPanel, model.Volunteer theUser, model.UrbanParksSystem theSystem) {
		
		/** Store all relevant arguments as fields. */
		
		myGUI = theGUI;
		myUser = theUser;
		mySystem = theSystem;

		/** myMasterPanel will contain all the currently available jobs for the user in the form of buttons. */
		
		myMasterPanel = new JPanel();
		myMasterPanel.setLayout (new BoxLayout (myMasterPanel, BoxLayout.Y_AXIS));  
		
		/** myInformationPanel contains a field of text displaying information about a specific job selected by the user, 
		 *  and will change depending on which job is currently selected (selectedJob). */
		
		myInformationPanel = new JPanel(new GridLayout(5,1));
		
		/** Fields that will be displayed in myInformationPanel. */
		
		jobNameLabel = new JLabel();
		jobDescriptionLabel = new JLabel();
		jobStartDateLabel = new JLabel();
		jobEndDateLabel = new JLabel();
		signUpConfirmation = new JLabel();
		
		/** Message at the top of the screen. */
		
		volunteerMenuLabel = new JLabel();
		
		/** Method for setting up the myMasterPanel and myInformationPanel. */
		
		setupPanel(); 
		
	}
	
	/**
	 *  Method for setting up the overall VolunteerViewCurrentJobsPanel panel.
	 */
	
	private void setupPanel() {
		
		 /** Sets the layout of VolunteerViewCurrentJobsPanel as a BorderLayout. */
		
		setLayout(new BorderLayout(10,10));
		
		/** Creates a welcome message at this.BorderLayout.NORTH */
		
		setupVolunteerViewCurrentJobsWelcomeLabel(true);
		
		/** Sets up the myMasterPanel and myInformationPanel panels. */
		
		setupViewCurrentJobsPanel();
		
		myMasterPanel.setVisible(true);
		add(myMasterPanel, BorderLayout.CENTER);
		
	}
	 
	 /**
	  *	 Creates a basic message at the top of the screen, explaining to the user that the buttons 
	  *  in myMasterPanel represents their current list of jobs.
	  */
	
	 private void setupVolunteerViewCurrentJobsWelcomeLabel(boolean jobsExist) {
		
		if (jobsExist) {
		
		volunteerMenuLabel.setText("<html><br>Here is the current list of available jobs: </html>");
		volunteerMenuLabel.setHorizontalAlignment(JLabel.CENTER);
		volunteerMenuLabel.setVisible(true);
		add(volunteerMenuLabel, BorderLayout.NORTH);
		
		}
		
		if (!jobsExist) {
			
			volunteerMenuLabel.setText("<html><br>Unfortunately, there are no jobs you can sign up for at this time.</html>");
			volunteerMenuLabel.setHorizontalAlignment(JLabel.CENTER);
			volunteerMenuLabel.setVisible(true);
			add(volunteerMenuLabel, BorderLayout.NORTH);
			
			}
		
	}
	
	/**
	  *	 Sets up the myMasterPanel and myInformationPanel panels.
	  */
	
	private void setupViewCurrentJobsPanel() {
		
		if (!mySystem.getVolunteerValidJobs(myUser).isEmpty()) {
			
			// refreshPanel will add all available valid jobs to the list that the user may sign up for.
			
			refreshPanel(true);
			
			// After all other job buttons are added, "Sign Up For Job" button is added last.
			
			setupSignUpJobButton();
			
		}
		
		if (mySystem.getVolunteerValidJobs(myUser).isEmpty()) {
			
			setupVolunteerViewCurrentJobsWelcomeLabel(false);
			
		}
		
	}
	
	/**
	  *	 Gives information about the currently selected job.
	  */
	
	private void setupJobInformationLabel(Job theJob) {

		jobNameLabel.setText("Job Title: " + theJob.getJobName());
		jobNameLabel.setVisible(true);
		myInformationPanel.add(jobNameLabel);
			
		jobDescriptionLabel.setText("Job Description: " + theJob.getJobDescription());
		jobDescriptionLabel.setVisible(true);
		myInformationPanel.add(jobDescriptionLabel);
		
		jobStartDateLabel.setText("Job Start Date: " + theJob.getStartDate().MONTH + "/" + theJob.getStartDate().DAY_OF_MONTH + "/" + theJob.getStartDate().YEAR);
		jobStartDateLabel.setVisible(true);
		myInformationPanel.add(jobStartDateLabel);
		
		jobEndDateLabel.setText("Job End Date: " + theJob.getEndDate().MONTH + "/" + theJob.getEndDate().DAY_OF_MONTH + "/" + theJob.getEndDate().YEAR);
		jobEndDateLabel.setVisible(true);
		myInformationPanel.add(jobEndDateLabel);
		
		myInformationPanel.setVisible(true);	
		add(myInformationPanel, BorderLayout.SOUTH);
		
		// I HAVE NO IDEA HOW TO DEAL WITH RESIZING
		
		myGUI.setSize(getPreferredSize());
    	myGUI.setSize(new Dimension(800,500));
		
	}
	
	/**
	  *	 Creates the "Sign Up For Selected Job" button.
	  */
	
	
	private void setupSignUpJobButton() {

		// After all other job buttons are added, "Sign Up For Job" button is added last.
		
		JPanel myButtonLayout = new JPanel(new FlowLayout());
		JButton signUpJobButton = new JButton("Sign Up for Selected Job");
		
		// If selectedJob is pointing to something, clicking "Sign Up for Job" will add the job from the user's personal list.
		// selectedJob then points at null so that user can't attempt to add an already added job.
				
			signUpJobButton.addActionListener(new ActionListener(){
			    public void actionPerformed(ActionEvent e){  
				              
				    if (selectedJob != null) {
	    	
				    	myUser.signup(selectedJob);
				    	signUpConfirmation.setText(selectedJob.getJobName() + " was successfully signed up for!");
				    	
				   	 	signUpConfirmation.setVisible(true);
				   	 	myInformationPanel.add(signUpConfirmation);
				    	myGUI.setSize(getPreferredSize());
				    	myGUI.setSize(new Dimension(800,500));
				    
				    	selectedJob = null;
				    	
				    	// All the buttons in myMasterPanel are removed and re-added in order to 'refresh' the screen without the added job.
					
						if (!mySystem.getVolunteerValidJobs(myUser).isEmpty()) {
						
							refreshPanel(true);
						
							// After all other job buttons are added, "Sign Up For Job" button is added last.
						
							myButtonLayout.add(signUpJobButton);		
							myMasterPanel.add(myButtonLayout);
						
						}
					
						if (mySystem.getVolunteerValidJobs(myUser).isEmpty()) {
						
							setupVolunteerViewCurrentJobsWelcomeLabel(false);
							myInformationPanel.removeAll();
							myMasterPanel.removeAll();
						
						}
					
				    }
				    	
			    }
			});
				
				myButtonLayout.add(signUpJobButton);		
				myMasterPanel.add(myButtonLayout);
		
	}

	public void refreshPanel(boolean fromInsideCurrentClass) {

		// Remove all buttons currently on myMasterPanel.
		
	    myMasterPanel.removeAll();
	    
	    // Create an Iterator containing all valid Pending Jobs for the current user.
	    
	    Iterator<model.Job> listOfAvailableJobs = mySystem.getVolunteerValidJobs(myUser).iterator();

	    // For each job, create an additional button for myMasterPanel.
	    
		while (listOfAvailableJobs.hasNext()) {
				
			model.Job availableJob = listOfAvailableJobs.next();
			JButton jobButton = new JButton(availableJob.getJobName());
				
			// When button is clicked, selectedJob will point to the associated job object.
			// setupJobInformationLabel will make it so that text describing the selected job is printed on the GUI.
			
			jobButton.addActionListener(new ActionListener(){  
				public void actionPerformed(ActionEvent e){  
				              
					selectedJob = availableJob;
				    setupJobInformationLabel(availableJob);
				    signUpConfirmation.setVisible(false);
				    	
				    }  
				});
			
				// Button is added to myMasterPanel, and FlowLayout is used here only for design aesthetics.
				
				JPanel myButtonLayout = new JPanel(new FlowLayout());
				myButtonLayout.add(jobButton);		
				myMasterPanel.add(myButtonLayout);
				
			}
		
	}

}
