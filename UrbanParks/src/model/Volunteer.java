package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a volunteer. A Volunteer has it's list of jobs it has signed up for. 
 * A Volunteer may add or remove from this list of jobs. 
 * 
 * @author Derick Salamanca
 * @author Jordan LeBle
 * @version 11 February 2018
 */
public class Volunteer extends User {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;
	
	private Set<Job> myJobs;
	
	/**
	 * Constructor for Volunteer. 
	 * 
	 * @param theUsername User's user name.
	 * @param theName User's actual name.
	 */
	public Volunteer(String theUsername, String theName) {
		super(theUsername, theName, "Volunteer");
		myJobs = new HashSet<Job>();
	}
	
	/**
	 * This Volunteer attempts to sign up for a Job. Will return an int based on the 
	 * outcome of attempt. Will return 0 if successful, 1 if this Volunteer already has a 
	 * job that day, 2 if the Job starts less than MIN_TIMESPAN away.
	 * 
	 * @param theJob Job this Volunteer will attempt to sign up for.
	 * @return 0 if successful, 1 if this Volunteer already has a job that day, 
	 * 2 if the Job starts less than MIN_TIMESPAN away.
	 */
	public int signup(Job theJob) {
		int successful = 0;
		int sameDayConflict = 1;
		int minDaysConflict = 2;
		
		if (isConflict(theJob)) {
			return sameDayConflict;
		} else if (theJob.isBeforeMinTimespan()) {
			return minDaysConflict;
		}
		
		myJobs.add(theJob);
		setChanged();
		notifyObservers("Volunteer");
		
		return successful;		
	}
	
	/**
	 * This Volunteer attempts to unvolunteer up for a Job. Will return an int based on the 
	 * outcome of attempt. Will return 0 if successful, 1 if this Volunteer is not signed up for 
	 * the Job, 2 if the Job starts less than MIN_TIMESPAN away.
	 * 
	 * @param theJob Job this Volunteer will attempt to unvolunteer for.
	 * @return 0 if successful, 1 if this Volunteer is not signed up for the Job, 
	 * 2 if the Job starts less than MIN_TIMESPAN away.
	 */
	public int removeJob(Job theJob) {
		int successful = 0;
		int jobDNE = 1;
		int minDaysConflict = 2;
		
		if (!myJobs.contains(theJob)) { //shouldn't ever happen?
			return jobDNE; 
		} else if (theJob.isBeforeMinTimespan()) {
			return minDaysConflict;
		} else {
			myJobs.remove(theJob);
			setChanged();
			notifyObservers("Volunteer");
			return successful;
		}
	}
	
	/**
	 * Checks if theJob is already in the volunteers job list.
	 * 
	 * @param theJob the job to be checked.
	 * @return true if the job is already in the job list, false otherwise.
	 */
	public boolean isConflict(Job theJob) {
		for (Job j : myJobs) {
			if (j.isOverlap(theJob)) {
				return true;
			}
		} 
		return false;
	}

	/*********** Getters ***********/
	
	public Set<Job> getJobsList() {
		return myJobs;
	}
	
	/***** Test Functions *****/
	
	/**
	 * SHOULD ONLY BE USED FOR TESTING
	 * Adds theJob to this Volunteer's myJobs without any checks. 
	 * 
	 * @param theJob Job this Volunteer will sign up for.
	 */
	public void signupLocal(Job theJob) {
		myJobs.add(theJob);		
	}
}