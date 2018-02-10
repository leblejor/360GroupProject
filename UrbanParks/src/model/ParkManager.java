package model;

import java.util.ArrayList;
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
	
	public void createJob(Job theJob) {
		
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
		ups.getCalendar().addJob(theJob);
	}
	
	public List<Job> getJobsList() {
		return myJobs;
	}
}
