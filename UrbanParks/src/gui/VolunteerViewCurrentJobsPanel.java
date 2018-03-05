package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton; 
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Job;

public class VolunteerViewCurrentJobsPanel extends JPanel {
	
	// VolunteerViewCurrentJobsPanel -> BorderLayout
	// Welcome Message -> BorderLayout.NORTH
	// myMasterPanel -> BorderLayout.CENTER
	// myInformationPanel -> BorderLayout.SOUTH
	
	/** myMasterPanel contains all the buttons representing the jobs. */
	
	private JPanel myMasterPanel;
	
	/** myInformationPanel contains the text describing the current job. */
	
	private JPanel myInformationPanel;
	
	/** volunteerMenuLabel contains the message at the top of the panel. */
	
	private JLabel volunteerMenuLabel;
	
	/** jobNameLabel contains the name of the currently selected job. */
	
	private JLabel jobNameLabel;
	
	/** jobNameLabel contains the description of the currently selected job. */
	
	private JLabel jobDescriptionLabel;
	
	/** jobStartDateLabel contains the start date of the job. */
	
	private JLabel jobStartDateLabel;
	
	/** jobEndDateLabel contains the end date of the job. */
	
	private JLabel jobEndDateLabel;
	
	/** cancellationConfirmation contains a message that confirms the success status of a job cancellation. */
	
	private JLabel cancellationConfirmation;
	
	/** selectedJob contains the currently selected job by the user. */
	
	private model.Job selectedJob;
	
	private UrbanParksGUI myGUI;
	private model.Volunteer myUser;
	
	public VolunteerViewCurrentJobsPanel(UrbanParksGUI theGUI, JPanel theGUIMasterPanel, model.Volunteer theUser) {
		
		/** Store all relevant arguments as fields. */
		
		myGUI = theGUI;
		myUser = theUser;

		/** myMasterPanel will contain all the currently available jobs of the user in the form of buttons. */
		
		myMasterPanel = new JPanel();
		myMasterPanel.setLayout (new BoxLayout (myMasterPanel, BoxLayout.Y_AXIS));  
		
		/** myInformationPanel contains a field of text displaying information about a specific job of the user's, 
		 *  and will change depending on which job is currently selected (selectedJob). */
		
		myInformationPanel = new JPanel(new GridLayout(5,1));
		
		/** Fields that will be displayed in myInformationPanel. */
		
		jobNameLabel = new JLabel();
		jobDescriptionLabel = new JLabel();
		jobStartDateLabel = new JLabel();
		jobEndDateLabel = new JLabel();
		cancellationConfirmation = new JLabel();
		
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
		
		volunteerMenuLabel.setText("<html><br>Here are your current list of jobs: </html>");
		volunteerMenuLabel.setHorizontalAlignment(JLabel.CENTER);
		volunteerMenuLabel.setVisible(true);
		add(volunteerMenuLabel, BorderLayout.NORTH);
		
		}
		
		if (!jobsExist) {
			
			volunteerMenuLabel.setText("<html><br>Unfortunately, you do not have any jobs associated with you.</html>");
			volunteerMenuLabel.setHorizontalAlignment(JLabel.CENTER);
			volunteerMenuLabel.setVisible(true);
			add(volunteerMenuLabel, BorderLayout.NORTH);
			
			}
		
	}
	
	/**
	  *	 Sets up the myMasterPanel and myInformationPanel panels.
	  */
	
	private void setupViewCurrentJobsPanel() {
		
		if (!myUser.getJobsList().isEmpty()) {

			// Add buttons to myMaster Panel with refreshPanel() for every job that the user has.
			
			refreshPanel(true);
		
			// After all other job buttons are added, "Cancel Selected Job" button is added last.
		
			setupCancelJobButton();
		
		}
		
		if (myUser.getJobsList().isEmpty()) {
			
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
	  *	 Creates the Cancel Job button.
	  */
	
	
	private void setupCancelJobButton() {

		// After all other job buttons are added, "Cancel Selected Job" button is added last.
		
		JPanel myButtonLayout = new JPanel(new FlowLayout());
		JButton cancelJobButton = new JButton("Cancel Selected Job");
		
		// If selectedJob is pointing to something, clicking "Cancel Selected Job" will remove the job from the user's personal list.
		// selectedJob then points at null so that user can't attempt to remove an already removed job.
				
			cancelJobButton.addActionListener(new ActionListener(){
			    public void actionPerformed(ActionEvent e){  
				              
				    if (selectedJob != null) {
				    	
				    	String selectedJobName = selectedJob.getJobName();
				    		
				    	int removalStatus = myUser.removeJob(selectedJob);
				    	
				    if (removalStatus == 0) { 
				    	cancellationConfirmation.setText(selectedJobName + " was successfully cancelled!");
				    }
				    		
				    if (removalStatus != 0) { 
				   		cancellationConfirmation.setText(selectedJobName + " could not be cancelled as it's too close to the job date!");
				    }
				    	
				    	cancellationConfirmation.setVisible(true);
				    	myInformationPanel.add(cancellationConfirmation);
				    	myGUI.setSize(getPreferredSize());
				    	myGUI.setSize(new Dimension(800,500));
				    		
				    	selectedJob = null;
				    	
				    // All the buttons in myMasterPanel are removed and re-added in order to 'refresh' the screen without the removed job.
				  
				    if (removalStatus == 0) {
				    	
				    	refreshPanel(true);
				    
				    	myButtonLayout.add(cancelJobButton);		
						myMasterPanel.add(myButtonLayout);
					
						if (myUser.getJobsList().isEmpty()) {
						
							myInformationPanel.removeAll();
							setupVolunteerViewCurrentJobsWelcomeLabel(false);
							myMasterPanel.removeAll();
						
						}
					
				    }
					
				}
				    	
			    }
			});
				
				myButtonLayout.add(cancelJobButton);		
				myMasterPanel.add(myButtonLayout);
		
	}
	
	/** Removes and re-adds all the job buttons in myMasterPanel. Does not add back "Cancel Job" button.  */
	
	public void refreshPanel(boolean fromInsideCurrentClass) {

		// Remove all buttons currently on myMasterPanel.
		
	    myMasterPanel.removeAll();
	    
	    // Iterates through all jobs in the user's personal job list and adds a button for each one.
	    	
	    for (model.Job availableJob : myUser.getJobsList()) {
	    		
	    	// When button is clicked, selectedJob will point to the associated job object.
	    	// setupJobInformationLabel will make it so that text describing the selected job is printed on the GUI.
	    	
	    	JButton jobButton = new JButton(availableJob.getJobName());
	    		
	    	jobButton.addActionListener(new ActionListener(){ 
	    		public void actionPerformed(ActionEvent e){
				              
	    			selectedJob = availableJob;
	    			setupJobInformationLabel(availableJob);
	    			cancellationConfirmation.setVisible(false);
				    
	    		}
	    	});
	    		
	    		JPanel myButtonLayout = new JPanel(new FlowLayout());
	    		myButtonLayout.add(jobButton);		
	    		myMasterPanel.add(myButtonLayout); 
	    		
	    }
	    
	    if (!fromInsideCurrentClass) {
	    	
	    	setupCancelJobButton();
	    	
	    }
		
	}

}
