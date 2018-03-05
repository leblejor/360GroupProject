package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Job;
import model.ParkManager;
import model.UrbanParksSystem;
import model.User;
import model.Volunteer;


/**
 * 
 * @author Bryan Santos
 *
 */
public class VolunteerViewPendingJobs extends JPanel implements Observer {

	/** SerialID */
	private static final long serialVersionUID = 1L;

	private UrbanParksSystem mySystem;
	private Volunteer myUser;
	private Box myDisplayJobsBox;
	
	public VolunteerViewPendingJobs(UrbanParksSystem theSystem, User theUser) {
		mySystem = theSystem;
		myUser = (Volunteer) theUser;
		myUser.addObserver(this);
		
		myDisplayJobsBox = Box.createVerticalBox();
		myDisplayJobsBox.add(Box.createVerticalStrut(5));
		setLayout(new BorderLayout());
		setUpNorthLabel();
		add(myDisplayJobsBox);
		displaySelectedJobs(mySystem.getVolunteerValidJobs(myUser));
		
	}
	
	private void setUpNorthLabel() {
		JLabel pendingJobsLabel = new JLabel("<html><b><font color=blue>View Pending Jobs</font></b></html>");
		pendingJobsLabel.setHorizontalAlignment(JLabel.CENTER);
		pendingJobsLabel.setBackground(Color.WHITE);
		pendingJobsLabel.setOpaque(true);		
		add(pendingJobsLabel, BorderLayout.NORTH);
	
	}
	
	private void displaySelectedJobs(Set<Job> jobsSet) {
		
		myDisplayJobsBox.removeAll();
		
		for (final Job job : jobsSet) { 
			
			JButton button = new JButton("<html><b><font color=green>" + job.toString() + "</font></b></html>");
			button.setBackground(Color.BLACK);
			
			button.setVisible(true);
			button.setOpaque(true);

			myDisplayJobsBox.add(button);
			myDisplayJobsBox.add(Box.createVerticalStrut(8));
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String title = "Job Full Details";
					Object[] options = {"Sign Up", "Cancel"};
					int result = JOptionPane.showOptionDialog(null, job.toStringWithDescription(), title, 
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE, 
							null, options, options[0]);
					
					// User selects to sign Up for this Job
					if (result == 0) {
						String removeTitle = "Signing Up for a Job";
						String question = "Are you sure you want to sign up for this job?: \n" + job.toString();
						
						int confirmSignUp = JOptionPane.showConfirmDialog(null, question, removeTitle,
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						
						if (confirmSignUp == JOptionPane.YES_OPTION) {
							myUser.signup(job);
						}
						
						
					}

				}
				
			});
			
			
		}
		myDisplayJobsBox.setVisible(true);
		myDisplayJobsBox.repaint();
		myDisplayJobsBox.revalidate();		
	}

	@Override
	public void update(Observable o, Object arg) {
		String observable = (String) arg;
		
		if (observable.equals("Volunteer")) {
			displaySelectedJobs(mySystem.getVolunteerValidJobs(myUser));
		}
		
	
	}
}
