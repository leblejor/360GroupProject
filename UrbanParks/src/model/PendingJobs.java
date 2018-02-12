package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingJobs implements java.io.Serializable {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;

	Map<String, Job> myPendingJobs;
	
	public PendingJobs() {
		myPendingJobs = new HashMap<String, Job>();
	}
	
	public void addJob(Job theJob) {
		myPendingJobs.put(theJob.getJobName(), theJob);
	}
	
	public List<Job> getPendingJobsList() {
		List<Job> list = new ArrayList<Job>(myPendingJobs.values());
		
		return list;
	}
	
	public Job getSinglePendingJob(String theJobName) {
		return myPendingJobs.get(theJobName);
	}
}
