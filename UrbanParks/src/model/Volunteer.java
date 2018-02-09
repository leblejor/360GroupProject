package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Volunteer extends User{
	
	//Volunteer cannot sign up for job that begins in MIN_SIGNUP_DAYS
	private static final int MIN_SIGNUP_DAYS = 2;
	
	private List<Job> myJobs;
	
	public Volunteer(final String theUsername, final String theName) {
		super(theUsername, theName, "Volunteer");
		myJobs = new ArrayList<Job>();
	}
	
	/**
	 * Attempts to sign up for a job. 
	 * Returns 0 if the signup is successful
	 * Returns 1 if the volunteer already has a job on that day
	 * Returns 2 if the job starts less than MIN_SIGNUP_DAYS away
	 */
	public int signup(Job theJob) throws ParseException {
		int successful = 0;
		int sameDayConflict = 1;
		int minDaysConflict = 2;
		boolean sameDay = isSameDay(theJob);
		boolean daysUntilJob = checkDaysUntilJob(theJob);
		
		if (sameDay) {
			return sameDayConflict;
		} else if(daysUntilJob) {
			return minDaysConflict;
		} else {
			myJobs.add(theJob);
		}
		
		return successful;		
	}
	
	/**
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
	
	/**
	 * Checks the jobs startDate to make sure that the job
	 * starts at least MIN_SIGNUP_DAYS days in the future. You cannot sign
	 * up for a job if it starts in MIN_SIGNUP_DAYS days.
	 * Returns false if the job happens at least MIN_SIGNUP_DAYS days in advance
	 * Returns true if the job will start in less than MIN_SIGNUP_DAYS days.
	 */
	public boolean checkDaysUntilJob(Job theJob) throws ParseException {
		boolean conflict = false;

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		//Adding MIN_SIGNUP_DAYS days to the current date
		calendar.add(Calendar.DAY_OF_YEAR, MIN_SIGNUP_DAYS);

		//Compare the current date +2 with the start date of the job
		//True if it is less than two days.
		if ( calendar.getTime().compareTo(theJob.getStartDate().getTime()) > 0)  {
			conflict = true;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		int day = theJob.getStartDate().get(Calendar.DAY_OF_MONTH);
		int month = theJob.getStartDate().get(Calendar.MONTH);
		int year = theJob.getStartDate().get(Calendar.YEAR);
		
		int day1 = calendar.get(Calendar.DAY_OF_MONTH);
		int month1 = calendar.get(Calendar.MONTH) + 1;
		int year1 = calendar.get(Calendar.YEAR);
		
		Date date1 = sdf.parse(day + "-" + month + "-" + year);
		Date date2 = sdf.parse(day1 + "-" + month1 + "-" + year1);
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		
		//Compare the current date + MIN_SIGNUP_DAYS with the start date of the job
		//True if it is less than MIN_SIGNUP_DAYS days.
		if (cal1.compareTo(cal2) < 0) {
			conflict = true;
		}
		
		return conflict;
	}

	
	public List<Job> getJobsList() {
		return myJobs;
	}
	
}