package model;

/**
 * Represents a Staff member. 
 * 
 * @version 11 February 2018
 */
public class Staff extends User {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;
	
	
	private static int myMaxPendingJobs = 10;
	private static int myMaxJobLength = 4;
	private static int myMaxScheduleWindow = 60;
	private static int myMinSignUpDays = 3;

	public Staff(String theUsername, String theName) {
		super(theUsername, theName, "Staff");
	}
	
	public static int getMaxPendingJobs() {
		return myMaxPendingJobs;
	}
	
	public static int getMaxJobLength() {
		return myMaxJobLength;
	}
	
	public static int getMaxScheduleWindow() {
		return myMaxScheduleWindow;
	}
	
	public static int getMinSignUpDays() {
		return myMinSignUpDays;
	}
	
	public void setMaxPendingJobs(int theMaxJobs) {
		myMaxPendingJobs = theMaxJobs;
	}
	
	public void setMaxScheduleWindow(int theMaxWindow) {
		myMaxScheduleWindow = theMaxWindow;
	}

}
