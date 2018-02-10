package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkManager extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Job> myJobs;
	
	public ParkManager(String theUsername, String theName) {
		super(theUsername, theName, "Park Manager");
		myJobs = new ArrayList<Job>();
	}
	
	
	/**
	 * 
	 * @param theJob
	 * @return 0 -> success, 
	 */
	public int createJob(Job theJob, PendingJobs thePendingJobs) {
		
		int code = 0;
		Date startDate = theJob.getStartDate().getTime();
		Date endDate = theJob.getEndDate().getTime();
		
		myJobs.add(theJob);
		
		return code;
		
		
		
		
	}

	public void createJob(UrbanParksSystem ups, String theJobName, 
			              int theStartMonth, int theStartDay, int theStartYear,
			   			  int theEndMonth, int theEndDay, int theEndYear) {
		
		Job theJob = new Job(theJobName, "", theStartMonth, theStartDay, theStartYear,
								theEndMonth, theEndDay, theEndYear);
		
		myJobs.add(theJob); // Do we need this?
		// Yes, we need this statement. 
		// This saves the job list for park manager object
		
		// this line adds the job to the overall Pending Jobs list
		ups.getPendingJobs().addJob(theJob);
	}
	
	public List<Job> getJobsList() {
		return myJobs;
	}
	
	public static void main(String[] args) {
		ParkManager mgr = new ParkManager("paolo186", "Bryan");
		Job job1 = new Job("Park Cleaning", "", 4, 3, 2018, 4, 7, 2018);
		int code = mgr.createJob(job1, new PendingJobs());
		System.out.println("Code: " + code);
	}
}
