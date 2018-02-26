package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Park Manager.
 * 
 * @author Derick Salamanca
 * @author Bryan Santos
 * @author Jordan LeBle
 */
public class ParkManager extends User {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;
	
	private Set<Job> myJobs;
	
	public ParkManager(String theUsername, String theName) {
		super(theUsername, theName, "Park Manager");
		myJobs = new HashSet<Job>();
	}
	
	/**
	 * Attempts to create a valid job and put it into the UrbanParksSystem.
	 * 
	 * A job is valid and is added to the system iff:
	 *    There are less than the MAX_PENDING_JOBS already in the system
	 * && The Job's duration (end date - start date)  is less than MAX_JOB_DURATION
	 * && The end date of the job is within MAX_TIMESPAN days from today
	 * && The Job does not occur in the past
	 * 
	 * @param ups UrbanParksSystem running the program
	 * @param theJob The Job to create in UrbanParksSystem
	 * @return 0 if the job is valid and is added to the system,
	 * 1 if there are already the maximum number of jobs in the system,
	 * 2 if the job duration is longer than the maximum job duration,
	 * 3 if the job occurs past the maximum timespan from today
	 * 4 if the job has a start or end date that is in the past
	 */
	public int createJob(UrbanParksSystem ups, Job theJob) {
		int successful = 0;
		int maxJobsConflict = 1;
		int jobTooLongConflict = 2;
		int jobTooFarConflict = 3;
		int jobInThePastConflict = 4;

		if (checkNumberOfJobsInSystem(ups)) { 
			return maxJobsConflict;
		} else if (theJob.checkJobDayLength(UrbanParksSystem.getMaxJobDuration())) {
			return jobTooLongConflict;
		} else if (theJob.checkJobEndDateMax(UrbanParksSystem.getMaxTimespan())) { 
			return jobTooFarConflict;
		} else if (theJob.checkJobDatePast()) { 
			return jobInThePastConflict;
		} else { //successful 
			ups.addJob(theJob); //System job list
			myJobs.add(theJob); //local job list
			return successful;
		}
	}
	
	/**
	 * Removes a Job from this ParkManager's list of myJobs. Will return 1 if theJob was not
	 * found in the list of ParkMangare's jobs, otherwise will return 0 if successful. 
	 * 
	 * @param theJob Job to remove from the list.
	 * @return 0 if removing the Job was successful, 1 if theJob was not found in the list.
	 */
	public int removeJob(UrbanParksSystem ups, Job theJob) {
		int success = 0;
		int jobDNE = 1;
		int minDaysConflict = 2;
		
		if (!myJobs.contains(theJob)) { //shouldn't ever happen?
			return jobDNE; 
		} else if (theJob.isBeforeMinTimespan()) {
			return minDaysConflict;
		} else {
			ups.removeJob(theJob);
			myJobs.remove(theJob);
			return success;
		}
	}
	
	/**
	 * SHOULD ONLY BE USED FOR TESTING.
	 * Adds theJob to this ParkManager's myJobs without any checks. 
	 * Does not use UrbanParksSystem.
	 * 
	 * @param theJob Job to add to this ParkManager's myJobs
	 */
	public void createJobLocal(Job theJob) {
			myJobs.add(theJob);	
	}
	
	/**
	 * SHOULD ONLY BE USED FOR TESTING.
	 * Removes theJob from this ParkManager's myJobs without updates to UrbanParksSystem. 
	 * Does not use UrbanParksSystem.
	 * 
	 * @param theJob Job to remove from this ParkManager's myJobs
	 */
	public int removeJobLocal(Job theJob) {
		int success = 0;
		int jobDNE = 1;
		int minDaysConflict = 2;
		
		if (!myJobs.contains(theJob)) { //shouldn't ever happen?
			return jobDNE; 
		} else if (theJob.isBeforeMinTimespan()) {
			return minDaysConflict;
		} else {
			myJobs.remove(theJob);
			return success;
		}
	}
	/**
	 * Takes the UrbanParksSystem, which contains the serialized object
	 * for the list of current pending jobs. There cannot be more than
	 * MAX_PENDING_JOBS in the system.
	 * 
	 * Returns false if the job can be added to the system
	 * Returns true if the system already has MAX_PENDING_JOBS
	 */
	public boolean checkNumberOfJobsInSystem(UrbanParksSystem ups) {
		boolean conflict = false;
		
		Set<Job> set = ups.getPendingJobs();
		
		if (set.size() >= UrbanParksSystem.getMaxPendingJobs()) {
			conflict = true;
		}
		
		return conflict;
	}
	
	/* Used for testing.
	 * Same as above, but doesn't use the UrbanParkSystem. */
	public boolean checkNumberOfJobsInSystemLocal() {
		boolean conflict = false;
		
		if (myJobs.size() >= UrbanParksSystem.getMaxPendingJobs()) {
			conflict = true;
		}
		
		return conflict;
	}
	

	/*********** Getters ***********/
	public Set<Job> getJobsList() {
		return myJobs;
	}
	
	
}
