package gui;

import javax.swing.JPanel;

import model.Staff;
import model.UrbanParksSystem;
import model.User;

public class StaffViewSelectedJobDates extends JPanel {

	
	private static final long serialVersionUID = 1L;

	private UrbanParksSystem mySystem;
	private Staff myUser;
	
	public StaffViewSelectedJobDates (UrbanParksSystem theSystem, User theUser) {
		mySystem = theSystem;
		myUser = (Staff) theUser;
	}
	
	
}
