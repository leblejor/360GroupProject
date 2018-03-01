package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Staff member. 
 * 
 * @author Jordan LeBle
 * @version 11 February 2018
 */
public class Staff extends User {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;
	
	// TODO: Put all variables in terms of staff's variables!
	// include setter.
	private static int MAX_PENDING_JOBS = 10;
	private static final int MAX_JOB_DURATION = 4;
	private static final int MAX_TIMESPAN = 60;
	private static final int MIN_TIMESPAN = 3;
	
	public Staff(String theUsername, String theName) {
		super(theUsername, theName, "Staff");
	}
	
	/**
	 * Sets the maximum number of pending jobs in the system. theMaxJobs must be > 0.
	 *
	 * @param ups contains the pending jobs list for the maximum jobs to be changed.
	 * @param theMaxJobs the maximum number of jobs in the system.
	 * @return 0 if the process was successful, 1 if theMaxJobs is zero, 2 if theMaxJobs is negative.
	 */
	public int setMaxPendingJobs(UrbanParksSystem ups, int theMaxJobs) {
		int success = 0;
		int zeroInvalid = 1;
		int negativeInvalid = 2;
		
		if(theMaxJobs == 0) {
			return zeroInvalid;
		} else if(theMaxJobs < 0) {
			return negativeInvalid;
		} else {
			setMaxPendingJobs(theMaxJobs);
			return success;
		}
		
	}
	
	/**
	 * Finds jobs that are within the given start and end dates and returns 
	 * a list of them. The integer values must be valid calendar dates, and 
	 * the start date cannot be in the future of the end date.
	 * 
	 * @param ups The system containing all of the pending jobs
	 * @return A list of jobs within the date range
	 */
	public Set<Job> viewJobsWithinRange(UrbanParksSystem ups, int theStartMonth, int theStartDay, 
			int theStartYear, int theEndMonth, int theEndDay, int theEndYear) {
		Set<Job> jobs = new HashSet<Job>();
		Calendar startDate = new GregorianCalendar(theStartYear, theStartMonth, theStartDay);
		Calendar endDate = new GregorianCalendar(theEndYear, theEndMonth, theEndDay);
		
		if (startDate.compareTo(endDate) > 0) {
			throw new IllegalArgumentException("Invalid dates, start date is after end date");
		}
		
		for(Job j : ups.getPendingJobs()) {
			//Start date and end date is within the range
			if (startDate.compareTo(j.getStartDate()) * j.getStartDate().compareTo(endDate) > 0 &&
					startDate.compareTo(j.getEndDate()) * j.getEndDate().compareTo(endDate) > 0) {
				jobs.add(j);			
			}
		}
		
		
		return jobs;
	}
	
	// TODO: Remove this
	public int setMaxPendingJobsLocal(int theMaxJobs) {
		int success = 0;
		int zeroInvalid = 1;
		int negativeInvalid = 2;
		
		if(theMaxJobs == 0) {
			return zeroInvalid;
		} else if(theMaxJobs < 0) {
			return negativeInvalid;
		} else {
			return success;
		}
		
	}
	
	

	public static int getMaxPendingJobs() {
		return MAX_PENDING_JOBS;
	}
	
	public static int getMaxJobDuration() {
		return MAX_JOB_DURATION;
	}
	
	public static int getMaxTimespan() {
		return MAX_TIMESPAN;
	}
	
	public static int getMinTimespan() {
		return MIN_TIMESPAN;
	}
	
	public void setMaxPendingJobs(int theMaxJobs) {
		MAX_PENDING_JOBS = theMaxJobs;
	}


}
