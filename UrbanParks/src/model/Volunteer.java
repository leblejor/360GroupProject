package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Volunteer extends User{
	private ArrayList<Job> myJobs;
	
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
	private boolean isSameDay(Job theJob) { 
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
	private boolean checkDaysUntilJob(Job theJob) {
		boolean conflict = false;
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		
		//todo
		
	}
	
}