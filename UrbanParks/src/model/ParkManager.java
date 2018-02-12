package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Represents a Park Manager.
 * 
 * @author Derick Salamanca
 * @author Bryan Santos
 */
public class ParkManager extends User {
	private static final int MAX_PENDING_JOBS = 20;
	private static final int MAX_JOB_LENGTH = 3;
	private static final int MAX_SCHEDULE_WINDOW = 75;
	private static final long serialVersionUID = 1L;
	
	private List<Job> myJobs;
	
	public ParkManager(String theUsername, String theName) {
		super(theUsername, theName, "Park Manager");
		myJobs = new ArrayList<Job>();
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
	 */
	public int createJob(UrbanParksSystem ups, String theJobName, 
			              int theStartMonth, int theStartDay, int theStartYear,
			   			  int theEndMonth,   int theEndDay,   int theEndYear) {
		
		Job theJob = new Job(theJobName, theStartMonth, theStartDay, theStartYear,
								theEndMonth, theEndDay, theEndYear);
		
		int successful = 0;
		int maxJobsConflict = 1;
		int jobTooLongConflict = 2;
		int jobTooFarConflict = 3;

		if (checkNumberOfJobsInSystem(ups)) { 
			return maxJobsConflict;
		} else if (checkJobDayLength(theJob)) {
			return jobTooLongConflict;
		} else if (checkJobEndDateMax(theJob)) { 
			return jobTooFarConflict;
		} else { 
			ups.getPendingJobs().addJob(theJob); //System job list
			myJobs.add(theJob); //local job list
		}
		
		return successful;
	
	}
	
	//Same as above, but doesn't use the UrbanParkSystem. 
	//Used for testing.
	public int createJobLocal(String theJobName, 
			              int theStartMonth, int theStartDay, int theStartYear,
			   			  int theEndMonth, int theEndDay, int theEndYear) {
		
		Job theJob = new Job(theJobName, theStartMonth, theStartDay, theStartYear,
								theEndMonth, theEndDay, theEndYear);
		
		int successful = 0;
		int maxJobsConflict = 1;
		int jobTooLongConflict = 2;
		int jobTooFarConflict = 3;
		
		boolean maxJobs = checkNumberOfJobsInSystemLocal();
		boolean jobLong = checkJobDayLength(theJob);
		boolean jobFar = checkJobEndDateMax(theJob);
		
		if (maxJobs) { 
			return maxJobsConflict;
		} else if (jobLong) {
			return jobTooLongConflict;
		} else if (jobFar) { 
			return jobTooFarConflict;
		} else { 
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
		
		PendingJobs pending = ups.getPendingJobs();
		List<Job> list = pending.getPendingJobsList();
		
		if (list.size() >= MAX_PENDING_JOBS) {
			conflict = true;
		}
		
		return conflict;
	}
	
	/* Used for testing.
	 * Same as above, but doesn't use the UrbanParkSystem. */
	public boolean checkNumberOfJobsInSystemLocal() {
		boolean conflict = false;
		
		if (myJobs.size() >= MAX_PENDING_JOBS) {
			conflict = true;
		}
		
		return conflict;
	}
	
	/**
	 * Checks a jobs start and end dates to make sure it falls within 
	 * the MAX_JOB_LENGTH range. A job cannot last longer than 
	 * the MAX_JOB_LENGTH.
	 * 
	 * Returns false if the job has a valid job length 
	 * Returns true if the job is longer than the valid job length
	 */
	public boolean checkJobDayLength(Job theJob) {
		boolean conflict = false;
		Object jobStartDay = theJob.getStartDate().clone();
		((Calendar) jobStartDay).add(Calendar.DAY_OF_YEAR, MAX_JOB_LENGTH);
	    Calendar jobEndDay = theJob.getEndDate();
		
	    if(((Calendar) jobStartDay).compareTo(jobEndDay) < 0) {
	    	conflict = true;
	    }
		return conflict;
	}
	
	/**
	 * Checks a jobs end date to make sure it falls within the
	 * MAX_JOB_DURATION range. A job cannot end after MAX_JOB_DURATION 
	 * days from the current day.
	 * 
	 * Returns false if the job is within the MAX_JOB_DURATION range
	 * Returns true if the job ends past the MAX_JOB_DURATION
	 */
	public boolean checkJobEndDateMax(Job theJob) {
		boolean conflict = false;
		Calendar jobEndDay = theJob.getEndDate(); 
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, MAX_SCHEDULE_WINDOW);
		
		if (today.compareTo(jobEndDay) < 0) {
			conflict = true;
		}
		return conflict;
	}

	/*********** Getters ***********/
	public List<Job> getJobsList() {
		return myJobs;
	}
	
	public int getMaxPendingJobs() {
		return MAX_PENDING_JOBS;
	}
	
	public int getMaxJobLength() {
		return MAX_JOB_LENGTH;
	}
	
	public int getMaxScheduleWindow() {
		return MAX_SCHEDULE_WINDOW;
	}	
}
