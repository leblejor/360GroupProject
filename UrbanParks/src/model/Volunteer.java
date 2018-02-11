package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Represents a volunteer.
 * 
 * @author Derick Salamanca
 */
public class Volunteer extends User {
	

	private static final long serialVersionUID = 1L;

	//Volunteer cannot sign up for job that begins in MIN_SIGNUP_DAYS
	private static final int MIN_SIGNUP_DAYS = 2;
	
	private List<Job> myJobs;
	
	public Volunteer(final String theUsername, final String theName) {
		super(theUsername, theName, "Volunteer");
		myJobs = new ArrayList<Job>();
	}
	
	/**
	 * Attempts to sign up for a job. 
	 * Returns 0 if the signup is successful
	 * Returns 1 if the volunteer already has a job on that day
	 * Returns 2 if the job starts less than MIN_SIGNUP_DAYS away
	 */
	public int signup(Job theJob) {
		int successful = 0;
		int sameDayConflict = 1;
		int minDaysConflict = 2;
		boolean sameDay = isSameDay(theJob);
		boolean daysUntilJob = checkDaysUntilJob(theJob);
		
		if (sameDay) {
			return sameDayConflict;
		} else if(daysUntilJob) {
			return minDaysConflict;
		} else {
			myJobs.add(theJob);
		}
		
		return successful;		
	}
	
	/**
	 * Checks to see if the job being signed up for
	 * conflicts with a job the volunteer already has 
	 * signed up for in the past.
	 * Returns false if there is no conflict, and true
	 * if there is a conflict.
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
	 * Checks the jobs startDate to make sure that the job
	 * starts at least MIN_SIGNUP_DAYS days in the future. You cannot sign
	 * up for a job if it starts in MIN_SIGNUP_DAYS days.
	 * Returns false if the job happens at least MIN_SIGNUP_DAYS days in advance
	 * Returns true if the job will start in less than MIN_SIGNUP_DAYS days.
	 */
	public boolean checkDaysUntilJob(Job theJob) {
		boolean conflict = false;

		Calendar futureDay = Calendar.getInstance();

		//Adding MIN_SIGNUP_DAYS days to the current date
		futureDay.add(Calendar.DAY_OF_YEAR, MIN_SIGNUP_DAYS);

		//Compare the current date +MIN_SIGNUP_DAYS with the start date of the job
		//True if it is less than the MIN_SIGNUP_DAYS
		if (futureDay.compareTo(theJob.getStartDate()) < 0) {
			conflict = true;
		}
		
		return conflict;
	}

	
	public List<Job> getJobsList() {
		return myJobs;
	}
	

	public int getMinSignupDays() {
		return MIN_SIGNUP_DAYS;
	}
}