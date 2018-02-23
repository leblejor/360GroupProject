package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Park Manager.
 * 
 * @author Derick Salamanca
 * @author Bryan Santos
 */
public class ParkManager extends User {
	
	private static final long serialVersionUID = 1L;
	
	private Set<Job> myJobs;
	
	public ParkManager(String theUsername, String theName) {
		super(theUsername, theName, "Park Manager");
		myJobs = new HashSet<Job>();
	}
	
	/**
	 * Creates a job and puts it into the UrbanParksSystem.
	 * Performs various checks to make sure the job is valid. 
	 * A job is valid and is added to the system if:
	 * 1) There are less than the maximum jobs already in the system
	 * 2) The start date - end date of a job is less than MAX_JOB_LENGTH
	 * 3) the end date of the job is within the MAX_JOB_DURATION range from today
	 * 
	 * Returns 0 if the job is valid, the job is added to the system
	 * Returns 1 there are already the maximum number of jobs in the system
	 * Returns 2 if the job start date - end date is longer than MAX_JOB_LENGTH
	 * Returns 3 if the job end date is past the MAX_JOB_DURATION range from today
	 * Returns 4 if the job has a date that is in the past
	 */
	public int createJob(UrbanParksSystem ups, Job theJob) {
		
		int successful = 0;
		int maxJobsConflict = 1;
		int jobTooLongConflict = 2;
		int jobTooFarConflict = 3;
		int jobInThePastConflict = 4;

		if (checkNumberOfJobsInSystem(ups)) { 
			return maxJobsConflict;
		} else if (theJob.checkJobDayLength(Staff.getMaxJobLength())) {
			return jobTooLongConflict;
		} else if (theJob.checkJobEndDateMax(Staff.getMaxScheduleWindow())) { 
			return jobTooFarConflict;
		} else if (theJob.checkJobDatePast()) { 
			return jobInThePastConflict;
		} else { //successful 
			ups.addJobToCollection(theJob); //System job list
			myJobs.add(theJob); //local job list
		}
		
		return successful;
	
	}
	
	/**
	 * Removes a Job from this ParkManager's list of myJobs. Will return 1 if theJob was not
	 * found in the list of ParkMangare's job, otherwise will return 0 if successful. 
	 * 
	 * @param theJob Job to remove from the list.
	 * @return 0 if removing the Job was successful, 1 if theJob was not found in the list.
	 */
	public int removeJob(Job theJob) {
		// Did this similar to how createJob() works for error handling
		int success = 0;
		int jobDNE = 1;
		
		if(myJobs.contains(theJob)) {
			myJobs.remove(theJob);
			return success;
		} else {
			return jobDNE;
		}
	}
	
	
	
	//Same as above, but doesn't use the UrbanParkSystem. 
	//Used for testing.
	public int createJobLocal(Job theJob) {
		
		int successful = 0;
		int maxJobsConflict = 1; // never used???
		int jobTooLongConflict = 2;
		int jobTooFarConflict = 3;
		int jobInThePastConflict = 4;

		if (theJob.checkJobDayLength(Staff.getMaxJobLength())) {
			return jobTooLongConflict;
		} else if (theJob.checkJobEndDateMax(Staff.getMaxScheduleWindow())) { 
			return jobTooFarConflict;
		} else if (theJob.checkJobDatePast()) { 
			return jobInThePastConflict;
		} else { //successful 
			myJobs.add(theJob); //local job list
		}
		
		return successful;
	
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
		
		
		Set<Job> set = ups.getPendingJobsCollection();
		
		if (set.size() >= Staff.getMaxPendingJobs()) {
			conflict = true;
		}
		
		return conflict;
	}
	
	/* Used for testing.
	 * Same as above, but doesn't use the UrbanParkSystem. */
	public boolean checkNumberOfJobsInSystemLocal() {
		boolean conflict = false;
		
		if (myJobs.size() >= Staff.getMaxPendingJobs()) {
			conflict = true;
		}
		
		return conflict;
	}
	

	/*********** Getters ***********/
	public Set<Job> getJobsList() {
		return myJobs;
	}
	
	
}
