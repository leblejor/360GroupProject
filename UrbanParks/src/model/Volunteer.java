package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a volunteer.
 * 
 * @author Derick Salamanca
 * @version 11 February 2018
 */
public class Volunteer extends User {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;
	
	private Set<Job> myJobs;
	
	public Volunteer(String theUsername, String theName) {
		super(theUsername, theName, "Volunteer");
		myJobs = new HashSet<Job>();
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
		
		if (theJob.checkDaysUntilJob(Staff.getMinSignUpDays())) {
			return minDaysConflict;
		}
		
		myJobs.add(theJob);
		
		return successful;		
	}
	
	public int removeJob(Job theJob) {
				
		if (theJob.checkDaysUntilJob(Staff.getMinSignUpDays())) { 
			return 1;
		}
				
		if (!myJobs.contains(theJob)) { //shouldn't ever happen?
			return 2; 
		}

		myJobs.remove(theJob);
		
		return 0;
	}
	


	/*********** Getters ***********/
	
	
	public Set<Job> getJobsList() {
		return myJobs;
	}
	
}