package model;

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
