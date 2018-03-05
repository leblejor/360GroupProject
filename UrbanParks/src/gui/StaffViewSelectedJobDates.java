package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Job;
import model.Staff;
import model.UrbanParksSystem;
import model.User;


/**
 * 
 * @author Bryan Santos
 *
 */
public class StaffViewSelectedJobDates extends JPanel {

	
	private static final long serialVersionUID = 1L;

	private UrbanParksSystem mySystem;
	private Staff myUser;
	private JTextField myStartDayField;
	private JTextField myEndDayField;
	private Box myDisplayJobsBox;
	
	
	
	public StaffViewSelectedJobDates (UrbanParksSystem theSystem, User theUser) {
		mySystem = theSystem;
		myUser = (Staff) theUser;
		myDisplayJobsBox = Box.createVerticalBox();
		setLayout(new BorderLayout());
		myStartDayField = new JTextField(10);
		myEndDayField = new JTextField(10);
		setUpNorthPanel();
		
		
	}
	
	private void setUpNorthPanel() {
		JPanel northPanel = new JPanel(new BorderLayout());
		JLabel viewDatesLabel = new JLabel("<html><b><font color=blue>View Dates</font></b></html>");
		viewDatesLabel.setHorizontalAlignment(JLabel.CENTER);
		viewDatesLabel.setBackground(Color.WHITE);
		viewDatesLabel.setOpaque(true);
		
		northPanel.add(viewDatesLabel, BorderLayout.NORTH);
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(5);
		
		JPanel searchPanel = new JPanel(flowLayout);
		JLabel fromLabel = new JLabel("From: ");
		JLabel toLabel = new JLabel("To: ");
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getDatesFromUser();
				
			}
			
		});
		
		searchPanel.add(fromLabel);
		searchPanel.add(myStartDayField);
		searchPanel.add(toLabel);
		searchPanel.add(myEndDayField);
		searchPanel.add(searchButton);
		
		northPanel.add(searchPanel);
		add(northPanel, BorderLayout.NORTH);		
		add(myDisplayJobsBox);
		
		
		
		
	}
	
	private void getDatesFromUser() {
		int[] startDate = getDate(myStartDayField.getText());
		int[] endDate = getDate(myEndDayField.getText());
		
		Set<Job> jobsSet = myUser.viewJobsWithinRange(mySystem, startDate[0], startDate[1], startDate[2], endDate[0], endDate[1], endDate[2]);
		displaySelectedJobs(jobsSet);
		
	}
	
	private void displaySelectedJobs(Set<Job> jobsSet) {
		
		myDisplayJobsBox.removeAll();
			
		for (final Job job : jobsSet) { 
			
			JButton button = new JButton("<html><b><font color=green>" + job.toString() + "</font></b></html>");
			button.setBackground(Color.BLACK);
			
			button.setVisible(true);
			button.setOpaque(true);

			myDisplayJobsBox.add(button);
			myDisplayJobsBox.add(Box.createVerticalStrut(3));
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String title = "Job Full Details";
					JOptionPane.showMessageDialog(null, job.toStringWithDescription(), title, JOptionPane.INFORMATION_MESSAGE);
					
				}
				
			});
			
			
		}
		myDisplayJobsBox.setVisible(true);
		myDisplayJobsBox.repaint();
		myDisplayJobsBox.revalidate();		
	}
	
	
	private int[] getDate(String theDate) {
		int[] date = new int[3];
		String[] tokens = theDate.split("/");
		for(int i = 0; i < tokens.length; i++) {
			int x = Integer.parseInt(tokens[i]);
			date[i] = x;
		}
		
		return date;
				
	}
	
	
	
	
	
	
	
}
