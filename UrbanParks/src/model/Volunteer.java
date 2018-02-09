package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Volunteer extends User{
	private List<Job> myJobs;
	
	public Volunteer(final String theUsername, final String theName) {
		super(theUsername, theName);
		myJobs = new ArrayList<Job>();
	}
	
	/*
	 * Attemps to sign up for a job. If successful, returns true
	 * and the job is added to myJobs. And if unsuccessful, returns 
	 * false. 
	 */
	public boolean signup(Job theJob) {
		boolean successful = true;
		boolean sameDay = isSameDay(theJob);
		boolean daysUntilJob = checkDaysUntilJob(theJob);
		
		if(sameDay || daysUntilJob) {
			successful = false;
		}
		if(successful == true) {
			myJobs.add(theJob);
		}
		
		return successful;
		
	}
	
	/*
	 * Checks to see if the job being signed up for
	 * confilcts with a job the volunteer already has 
	 * signed up for in the past.
	 * Returns false if there is no conflict, and true
	 * if there is a conflict.
	 */
	public boolean isSameDay(Job theJob) { 
		boolean conflict = false;
		for(Job j : myJobs) {
			if(j.isOverlap(theJob)) {
				conflict = true;
			}
			
		}
		return conflict;
	}
	
	/*
	 * Checks the jobs startDate to make sure that the job
	 * starts at least two days in the future. You cannot sign
	 * up for a job if it starts in two days.
	 * Returns false if the job happens at least two days in advance
	 * Returns true if the job will start in less than two days.
	 */
	public boolean checkDaysUntilJob(Job theJob) {
		boolean conflict = false;
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		//Adding two days to the current date
		calendar.add(Calendar.DATE, 2);
		
		//Compare the current date +2 with the start date of the job
		//True if it is less than two days.
		if ( calendar.getTime().compareTo(theJob.getStartDate().getTime()) > 0)  {
			conflict = true;
		}
		
		return conflict;
	}

	@Override
	public String getDescription() {
		return "Volunteer";
	}
	
	public List<Job> getJobsList() {
		return myJobs;
	}
	
}