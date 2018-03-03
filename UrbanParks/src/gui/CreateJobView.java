package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Job;
import model.ParkManager;
import model.UrbanParksSystem;
import model.User;

public class CreateJobView extends JPanel {
	
	private UrbanParksSystem mySystem;
	private ParkManager myUser;
	private String myUsername;
	
	private JPanel myMainPanel;

	public CreateJobView(UrbanParksSystem theSystem, User theUser, String theUsername) {
		mySystem = theSystem;
		myUser = (ParkManager) theUser;
		myUsername = theUsername;
		myMainPanel = new JPanel(new GridLayout(10, 1));
		setupPanel();
	}
	
	public void setupPanel() {
		JButton createJobButton = new JButton("Create Job");
		JButton goBackButton = new JButton("Go Back");
		
		JLabel jobTitleLabel = new JLabel("Job Title");
		JLabel jobDescriptionLabel = new JLabel("Job Description");
		JLabel jobStartDayLabel = new JLabel("Job Start Date (mm/dd/yyyy)");
		JLabel jobEndDayLabel = new JLabel("Job End Date (mm/dd/yyyy)");
		
		JTextField jobTitleTextField = new JTextField(10);
		JTextField jobDescriptionTextField = new JTextField(10);
		JTextField jobStartDayTextField = new JTextField(10);
		JTextField jobEndDayTextField = new JTextField(10);
		
		myMainPanel.add(jobTitleLabel);
		myMainPanel.add(jobTitleTextField);
		
		myMainPanel.add(jobDescriptionLabel);
		myMainPanel.add(jobDescriptionTextField);
		
		myMainPanel.add(jobStartDayLabel);
		myMainPanel.add(jobStartDayTextField);
		
		myMainPanel.add(jobEndDayLabel);
		myMainPanel.add(jobEndDayTextField);
		
		createJobButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String jobTitle = jobTitleTextField.getText();
				String jobDescription = jobDescriptionTextField.getText();
				String jobStartDay = jobStartDayTextField.getText();
				String jobEndDay = jobEndDayTextField.getText();
				
				int[] startDay = getDate(jobStartDay);
				int[] endDay = getDate(jobEndDay);
				
				Job j = new Job(jobTitle, jobDescription, startDay[0], startDay[1], startDay[2],
						endDay[0], endDay[1], endDay[2]);
				
				int errorCode = myUser.createJob(mySystem, j);
				
				if (errorCode == 0) {
					int dialog = JOptionPane.showConfirmDialog(myMainPanel,
							"Would you like to create this job?\n" + 
						    j.toString(), "Job Confirmation", JOptionPane.YES_NO_OPTION);
					if (dialog == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(myMainPanel, "Job created successfuly!");
						mySystem.addJob(j);
						
						//Update view jobs list here?
					} 
					//If no is clicked, then the dialog box closes.
					
				} else if (errorCode == 1) {
					JOptionPane.showMessageDialog(myMainPanel, "Error, pending jobs is currently full.",
							"Error", JOptionPane.WARNING_MESSAGE);
				} else if (errorCode == 2) {
					JOptionPane.showMessageDialog(myMainPanel, "Error, job legnth is too long", "Error", 
							JOptionPane.WARNING_MESSAGE);
				} else if (errorCode == 3) {
					JOptionPane.showMessageDialog(myMainPanel, "Error, job is scheduled too far in the future", 
							"Error", JOptionPane.WARNING_MESSAGE);
				} else if (errorCode == 4) {
					JOptionPane.showMessageDialog(myMainPanel, "Error, job date is in the past", "Error", 
							JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
		});
		
		add(createJobButton);
		add(myMainPanel);
	}
	

	public int[] getDate(String theDate) {
		int[] date = new int[3];
		String[] tokens = theDate.split("/");
		for(int i = 0; i < tokens.length; i++) {
			int x = Integer.parseInt(tokens[i]);
			date[i] = x;
		}
		
		return date;
				
	}
	
	

}
