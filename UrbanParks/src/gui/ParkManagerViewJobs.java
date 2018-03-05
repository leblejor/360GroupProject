package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Job;
import model.UrbanParksSystem;
import model.User;
import model.ParkManager;


public class ParkManagerViewJobs extends JPanel implements Observer {
	/** SerialID */
	private static final long serialVersionUID = 1L;

	private UrbanParksSystem mySystem;
	private ParkManager myUser;
	private Box myDisplayJobsBox;
	
	public ParkManagerViewJobs(UrbanParksSystem theSystem, User theUser) {
		mySystem = theSystem;
		myUser = (ParkManager) theUser;
		myUser.addObserver(this);
		
		myDisplayJobsBox = Box.createVerticalBox();
		myDisplayJobsBox.add(Box.createVerticalStrut(3));
		setLayout(new BorderLayout());
		setUpNorthLabel();
		add(myDisplayJobsBox);
		
	}
	
	private void setUpNorthLabel() {
		JLabel myJobsLabel = new JLabel("<html><b><font color=blue>View My Jobs</font></b></html>");
		myJobsLabel.setHorizontalAlignment(JLabel.CENTER);
		myJobsLabel.setBackground(Color.WHITE);
		myJobsLabel.setOpaque(true);		
		add(myJobsLabel, BorderLayout.NORTH);
	
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
					Object[] options = {"Ok", "Cancel", "Remove Job"};
					int result = JOptionPane.showOptionDialog(null, job.toStringWithDescription(), title, 
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE, 
							null, options, options[0]);
					
					// User selects removeJob
					if (result == 2) {
						String removeTitle = "Removing a Job";
						String question = "Are you sure you want to remove this job?: \n" + job.toString();
						
						int confirmRemoval = JOptionPane.showConfirmDialog(null, question, removeTitle,
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						
						if (confirmRemoval == JOptionPane.YES_OPTION) {
							myUser.removeJob(mySystem, job);
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
		
		if (observable.equals("Park Manager")) {
			displaySelectedJobs(myUser.getJobsList());
		}
	
	}
	
	
	
}