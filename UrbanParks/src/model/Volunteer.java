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
	private static final int MIN_SIGNUP_DAYS = 2;
	
	private List<Job> myJobs;
	
	public Volunteer(final String theUsername, final String theName) {
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
		
		if (isSameDay(theJob)) {
			return sameDayConflict;
		} else if(checkDaysUntilJob(theJob)) {
			return minDaysConflict;
		} else {
			myJobs.add(theJob);
		}
		
		return successful;		
	}
	
	/**
	 * Checks to see if the Job being signed up for conflicts with a Job in this Volunteer's
	 * job list. Conflicts occur when there is overlap in the start and/or end days of two Jobs.
	 * Returns true if there is a conflict, and false otherwise.
	 * 
	 * @param theJob the Job to check conflicts with.
	 * @return true if there is a conflict, and false otherwise.
	 */
	public boolean isSameDay(Job theJob) { 
		boolean conflict = false;
		for(Job j : myJobs) {
			if(j.isOverlap(theJob)) {
				conflict = true;
			}
			
		}
		return conflict;
	}
	
	/**
	 * Checks that the Jobs start date occurs at least MIN_SIGNUP_DAYS days in the future. 
	 * Returns true if Job starts within MIN_SIGNUP_DAYS days from today, false otherwise.
	 * 
	 * @param theJob the Job to check for days until it starts.
	 * @return true if Job starts within MIN_SIGNUP_DAYS days from today, false otherwise.
	 */
	public boolean checkDaysUntilJob(Job theJob) {
		boolean conflict = false;

		Calendar futureDay = Calendar.getInstance();

		//Add MIN_SIGNUP_DAYS days to the current date
		futureDay.add(Calendar.DAY_OF_YEAR, MIN_SIGNUP_DAYS);

		//Compare the current date + MIN_SIGNUP_DAYS with the start date of the job
		if (futureDay.compareTo(theJob.getStartDate()) > 0) {
			conflict = true;
		}
		
		return conflict;
	}

	/*********** Getters ***********/
	public List<Job> getJobsList() {
		return myJobs;
	}

	public int getMinSignupDays() {
		return MIN_SIGNUP_DAYS;
	}
}