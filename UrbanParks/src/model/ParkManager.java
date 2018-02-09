package model;

import java.util.ArrayList;

public class ParkManager extends User {
	
	private ArrayList<Job> myJobs;
	
	public ParkManager(String theUsername, String theName) {
		super(theUsername, theName, "Park Manager");
		myJobs = new ArrayList<Job>();
	}

	public void createJob(UrbanParksSystem ups, String theJobName, int theStartMonth, int theStartDay, int theStartYear,
			   				int theEndMonth, int theEndDay, int theEndYear) {
		Job theJob = new Job(theJobName, "", theStartMonth, theStartDay, theStartYear,
								theEndMonth, theEndDay, theEndYear);
		
		myJobs.add(theJob); // Do we need this?
		
		ups.getCalendar().addJob(theJob);
	}
}
