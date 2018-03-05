package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Job;
import model.UrbanParksSystem;
import model.User;
import model.ParkManager;


public class ParkManagerViewJobs extends JPanel {
	/** SerialID */
	private static final long serialVersionUID = 1L;

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
	/** cancellationConfirmation contains a message that confirms the success status of a job cancellation. */
	private JLabel cancellationConfirmation;
	
	/** selectedJob contains the currently selected job by the user. */
	private Job selectedJob;
	
	private UrbanParksSystem myUPS;
	private ParkManager myUser;
	
	public ParkManagerViewJobs(UrbanParksSystem theSystem, User theUser) {
		myUPS = theSystem;
		myUser = (ParkManager) theUser;
		
		myMasterPanel = new JPanel();
		myMasterPanel.setLayout (new BoxLayout (myMasterPanel, BoxLayout.Y_AXIS));  
		
		/* myInformationPanel contains a field of text displaying information about a 
		 * specific job of the user's, and will change depending on which job is currently 
		 * selected (selectedJob). */
		myInformationPanel = new JPanel(new GridLayout(4,1));
		
		/* Fields that will be displayed in myInformationPanel. */
		jobNameLabel = new JLabel();
		jobDescriptionLabel = new JLabel();
		cancellationConfirmation = new JLabel();
		
		/* Message at the top of the screen. */
		volunteerMenuLabel = new JLabel();
		
		setupPanel(); 
		
	}
	
	/**
	 *  Method for setting up the overall VolunteerViewCurrentJobsPanel panel.
	 */
	private void setupPanel() {
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
		} else {
			volunteerMenuLabel.setText("<html><br>Unfortunately, you do not have any jobs associated with your account.</html>");
		}
		
		volunteerMenuLabel.setHorizontalAlignment(JLabel.CENTER);
		volunteerMenuLabel.setVisible(true);
		add(volunteerMenuLabel, BorderLayout.NORTH);
	}
	
	/**
	  *	 Sets up the myMasterPanel and myInformationPanel panels.
	  */
	private void setupViewCurrentJobsPanel() {
		if (!myUser.getJobsList().isEmpty()) {
			for (model.Job availableJob : myUser.getJobsList()) {
				JButton jobButton = new JButton(availableJob.getJobName());
				
				jobButton.addActionListener(new ActionListener(){  
				    public void actionPerformed(ActionEvent e){     
				    	selectedJob = availableJob;
				    	setupJobInformationLabel(availableJob);
				    	cancellationConfirmation.setVisible(false);
				    }  
				});
				
				// Button is added to myMasterPanel
				JPanel myButtonLayout = new JPanel(new FlowLayout());
				myButtonLayout.add(jobButton);		
				myMasterPanel.add(myButtonLayout);
			}
			
			setupCancelJobButton();
		} else {
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
		
		myInformationPanel.setVisible(true);	
		add(myInformationPanel, BorderLayout.SOUTH);
	}
	
	/**
	  *	 Creates the Cancel Job button.
	  */
	private void setupCancelJobButton() {
		JButton cancelJobButton = new JButton("Cancel Selected Job");
		
		// If selectedJob is pointing to something, clicking "Cancel Selected Job" will remove 
		// the job from the user's personal list. selectedJob then points at null so that user 
		// can't attempt to remove an already removed job.
		cancelJobButton.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){       
			    if (selectedJob != null) {
				   	String selectedJobName = selectedJob.getJobName();
				   	
				    int removalStatus = myUser.removeJob(myUPS, selectedJob);
				    if (removalStatus == 0) { 
				    	cancellationConfirmation.setText(selectedJobName 
				    			+ " was successfully cancelled!");
				    } else if (removalStatus == 1) { 
				    	cancellationConfirmation.setText(selectedJobName + " was not found! "
				    			+ "Could not be cancelled.");
				    } else {
				   		cancellationConfirmation.setText(selectedJobName 
				   				+ " could not be cancelled as it's too close to the job date!");
				    }
				    	
				    cancellationConfirmation.setVisible(true);
					myInformationPanel.add(cancellationConfirmation);
			   	}
			    	
			    selectedJob = null;
			    	
			    // Remove and refresh remaining jobs on screen
			    myMasterPanel.removeAll();
			    setupViewCurrentJobsPanel();
				
			    // ???????????????/
				if (myUser.getJobsList().isEmpty()) {
					myInformationPanel.removeAll();
					setupVolunteerViewCurrentJobsWelcomeLabel(false);
					myMasterPanel.removeAll();
				}
			    	
		    }
		});		
	}
}