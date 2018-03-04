package gui;

import java.util.Set;

import javax.swing.JScrollPane;

import model.Job;
import model.UrbanParksSystem;

public class JobDetailsView extends JScrollPane {
	
	UrbanParksSystem mySystem;
	Set<Job> myJobs;
	
	public JobDetailsView(UrbanParksSystem theSystem, Set<Job> theJobs) {
		mySystem = theSystem;
		myJobs = theJobs;
	}
}
