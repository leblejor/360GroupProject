package model;

import java.util.ArrayList;

public class ParkManager extends User {
	
	private ArrayList<Job> myJobs;
	
	public ParkManager(String theUsername, String theName) {
		super(theUsername, theName);
		myJobs = new ArrayList<Job>();
	}

	
}
