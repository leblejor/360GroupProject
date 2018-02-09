package model;

import java.util.ArrayList;

public class ParkManager extends User {
	
	private ArrayList<Job> myJobs;
	
	public ParkManager(String theUsername, String theName) {
		super(theUsername, theName, "Park Manager");
		myJobs = new ArrayList<Job>();
	}

	
}
