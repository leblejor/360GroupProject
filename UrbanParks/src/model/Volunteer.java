package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Represents a volunteer.
 * 
 * @author Derick Salamanca
 * @version 11 February 2018
 */
public class Volunteer extends User {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;

	//Volunteer cannot sign up for job that begins in MIN_SIGNUP_DAYS
	private static final int MIN_SIGNUP_DAYS = 3;
	
	private List<Job> myJobs;
	
	public Volunteer(String theUsername, String theName) {
		super(theUsername, theName, "Volunteer");
		myJobs = new ArrayList<Job>();
	}
	
	/**
	 * This Volunteer attempts to sign up for a Job. Will return an int based on the 
	 * outcome of attempt. Will return 0 if successful, 1 if this Volunteer already has a 
	 * job that day, 2 if the Job starts less than MIN_SIGNUP_DAYS away.
	 * 
	 * @param theJob Job this Volunteer will attempt to sign up for.
	 * @return 0 if successful, 1 if this Volunteer already has a job that day, 
	 * 2 if the Job starts less than MIN_SIGNUP_DAYS away.
	 */
	public int signup(Job theJob) {
		int successful = 0;
		int sameDayConflict = 1;
		int minDaysConflict = 2;
		
		for (Job j : myJobs) {
			if (j.isOverlap(theJob)) {
				return sameDayConflict;
			}
		} 
		
		if (theJob.checkDaysUntilJob(MIN_SIGNUP_DAYS)) {
			return minDaysConflict;
		}
		
		myJobs.add(theJob);
		
		return successful;		
	}
	
	public int removeJob(Job theJob) {
				
		if (theJob.checkDaysUntilJob(MIN_SIGNUP_DAYS)) { 
			return 1;
		}
				
		if (!myJobs.contains(theJob)) { //shouldn't ever happen?
			return 2; 
		}
		
		for (int i = 0; i < myJobs.size(); i++) {
			if (myJobs.get(i).equals(theJob)) {
				myJobs.remove(i);
			}
		}
		
		return 0;
	}
	


	/*********** Getters ***********/
	public List<Job> getJobsList() {
		return myJobs;
	}

	public int getMinSignupDays() {
		return MIN_SIGNUP_DAYS;
	}
	
}