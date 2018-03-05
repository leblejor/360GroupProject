package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Staff;
import model.UrbanParksSystem;
import model.User;

public class StaffViewChangeMaxJobs extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private UrbanParksSystem mySystem;
	private Staff myUser;
	private JTextField myTextField;
	
	public StaffViewChangeMaxJobs(UrbanParksSystem theSystem, User theUser) {
		mySystem = theSystem;
		myUser = (Staff) theUser;
		
		setLayout(new BorderLayout());
		setUpNorthLabel();
		setUpTextField();
		
	}

	private void setUpNorthLabel() {
		JPanel northPanel = new JPanel(new BorderLayout());
		JLabel changeMaxJobsLabel = new JLabel("<html><b><font color=blue>Change Max Jobs</font></b></html>");
		changeMaxJobsLabel.setHorizontalAlignment(JLabel.CENTER);
		changeMaxJobsLabel.setBackground(Color.WHITE);
		changeMaxJobsLabel.setOpaque(true);
		
		northPanel.add(changeMaxJobsLabel, BorderLayout.NORTH);
		
		add(northPanel, BorderLayout.NORTH);
		
	}
	
	private void setUpTextField() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(3);
		JPanel changePanel = new JPanel(flowLayout);
		JLabel changeLabel = new JLabel("Change To: ");
		JTextField textField = new JTextField(10);
		JButton changeButton = new JButton("Change");
		changeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String newMaxString = textField.getText();
				int newMax = Integer.parseInt(newMaxString);
				if (newMax != Staff.getMaxPendingJobs()) {
					String title = "Change Max Pending Jobs";
					String question = "Are you sure you want to change Max Pending Jobs ";
					StringBuilder message = new StringBuilder(100);
					message.append("from: ");
					message.append(Staff.getMaxPendingJobs());
					message.append(" to ");
					message.append(newMaxString);
					
					question += message.toString() + '?';
					
					int result = JOptionPane.showConfirmDialog(null, question, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						myUser.setMaxPendingJobs(newMax);
						String successMessage = "You've successfully changed the Max Pending Jobs ";
						successMessage += message.toString() + '!';
						JOptionPane.showMessageDialog(null, successMessage);
					}
				} else {
					String noChangeMessage = "This is already the Max Pending Jobs amount";
					JOptionPane.showMessageDialog(null, noChangeMessage);
				}
			}
			
		});
		
		changePanel.add(changeLabel);
		changePanel.add(textField);
		changePanel.add(changeButton);
		
		add(changePanel);
		
		
	}
	
	
	

}
