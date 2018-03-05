package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Represents a Staff member. Staff member hold the system variables as they are the only
 * User allowed to modify them. A Staff member may change MAX_PENDING_JOBS, or view jobs within
 * a range of dates.
 * 
 * @author Jordan LeBle
 * @author Bryan Santos
 * @version 11 February 2018
 */
public class Staff extends User {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;
	
	private static int MAX_PENDING_JOBS = 10;
	private static final int MAX_JOB_DURATION = 4;
	private static final int MAX_TIMESPAN = 60;
	private static final int MIN_TIMESPAN = 3;
	
	/**
	 * Constructor for Staff.
	 *  
	 * @param theUsername the User's user name.
	 * @param theName the User's actual name.
	 */
	public Staff(String theUsername, String theName) {
		super(theUsername, theName, "Staff");
	}
	
	/**
	 * Sets the maximum number of pending jobs in the system. theMaxJobs must be > 0.
	 *
	 * @param ups contains the pending jobs list for the maximum jobs to be changed.
	 * @param theMaxJobs the maximum number of jobs in the system.
	 * @return 0 if the process was successful, 1 if theMaxJobs is zero, 
	 * 2 if theMaxJobs is negative.
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
		Calendar startDate = new GregorianCalendar(theStartYear, theStartMonth - 1, theStartDay);
		Calendar endDate = new GregorianCalendar(theEndYear, theEndMonth - 1, theEndDay);
		
		if (startDate.compareTo(endDate) > 0) {
			throw new IllegalArgumentException("Invalid dates, start date is after end date");
		}
		
		return ups.jobsWithinRange(startDate, endDate);
		

	}
	
	/** 
	 * Will return a set of jobs in the system within the given range. 
	 * The lower bound is exclusive, the upper bound is inclusive. 
	 * 
	 * @param theSystem System to find jobs in.
	 * @param theStartDate exclusive lower bound to range.
	 * @param theEndDate inclusive upper bound to range.
	 * @return Set of all jobs in system within a range (exclusive-inclusive).
	 */
	public Set<Job> viewJobsWithinRange(UrbanParksSystem theSystem, Calendar theStartDate, 
			Calendar theEndDate) {
		int startYear = theStartDate.get(Calendar.YEAR);	
		int startMonth = theStartDate.get(Calendar.MONTH);		
		int startDay = theStartDate.get(Calendar.DATE);		
		int endYear = theEndDate.get(Calendar.YEAR);
		int endMonth = theEndDate.get(Calendar.MONTH);	
		int endDay = theEndDate.get(Calendar.DATE);
		
		return viewJobsWithinRange(theSystem, startMonth, startDay, startYear, endMonth, endDay, endYear);
	}
	
	/*********** Getters and Setters ***********/
	
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
	
	/***** Test Functions *****/
	
	/**
	 * SHOULD ONLY BE USED FOR TESTING.
	 * Returns true if jobs in system is full, false otherwise.
	 * Does not use UrbanParksSystem.
	 * 
	 * @return true if number of jobs in system is full, false otherwise.
	 */
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
}
