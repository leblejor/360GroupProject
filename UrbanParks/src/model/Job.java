package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Represents a Job. A Job holds a title, a start date, and an end date. 
 * 
 * @author Bryan Santos
 * @version 11 February 2018
 */
public class Job implements java.io.Serializable {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;
	
	private String myJobName;
	private Calendar myStartDate;
	private Calendar myEndDate;
	
	/** Empty constructor. */
	public Job() {
		myJobName = "";
		myStartDate = Calendar.getInstance();
		myEndDate = Calendar.getInstance();
	}
	/**
	 * Constructor.
	 * 
	 * @param theJobName Title the Job will hold.
	 * @param theStartMonth Month the Job starts in.
	 * @param theStartDay Day the Job starts on.
	 * @param theStartYear Year the Job starts in.
	 * @param theEndMonth Month the Job ends in.
	 * @param theEndDay Day the Job ends on.
	 * @param theEndYear Year the Job ends in.
	 */
	public Job(String theJobName, int theStartMonth, int theStartDay, int theStartYear,
			   					  int theEndMonth,   int theEndDay,   int theEndYear) {
		myJobName = theJobName;
		myStartDate = new GregorianCalendar(theStartYear, theStartMonth - 1, theStartDay);
		myEndDate = new GregorianCalendar(theEndYear, theEndMonth - 1, theEndDay);
		

		// months start at 0 -> January, 1 -> February, ...

  	}
	

	
	/**
	 * Checks if this Job overlaps with the other Job.
	 * 
	 * @param theOther The other Job to compare to for overlap.
	 * @return True if there is an overlap with this Job and the other Job, false otherwise.
	 */
	public boolean isOverlap(Job theOther) {
		
		boolean isOverlap = false;
		Calendar theOtherStartDate = theOther.getStartDate();
		Calendar theOtherEndDate = theOther.getEndDate();
		
		// check if otherStartDate is during myStartDate and myEndDate
		if (theOtherStartDate.after(myStartDate) && theOtherStartDate.before(myEndDate) ) {
			isOverlap = true;
		}
		
		// check if otherEndDate is during my StartDate and myEndDate
		else if (theOtherEndDate.after(myStartDate) && theOtherEndDate.before(myEndDate) ) {
			isOverlap = true;
		}
		
		// check for other potential overlapping cases
		return isOverlap ||
			   isConflictingWithMyStartDate(theOtherStartDate) ||
			   isConflictingWithMyStartDate(theOtherEndDate) ||
			   isConflictingWithMyEndDate(theOtherStartDate) ||
			   isConflictingWithMyEndDate(theOtherEndDate);
				
	}
	
	
	/**
	 * Checks that the Jobs start date occurs at least MIN_SIGNUP_DAYS days in the future. 
	 * Preconditions: theJob != null, theMinDaySignup >= 0
	 * 
	 * @param theJob the Job to check for days until it starts.
	 * @return true if Job starts within MIN_SIGNUP_DAYS days from today, false otherwise.
	 */
	public boolean checkDaysUntilJob(int theMinDaySignup) {
		boolean conflict = false;

		Calendar futureDay = Calendar.getInstance();

		//Add MIN_SIGNUP_DAYS days to the current date
		futureDay.add(Calendar.DAY_OF_YEAR, theMinDaySignup);

		//Compare the current date +MIN_SIGNUP_DAYS with the start date of the job
		//True if it is greater than the MIN_SIGNUP_DAYS
		if (futureDay.compareTo(myStartDate) > 0) {
			conflict = true;
		}	
		      
		return conflict;
	}

	/**
	 * Checks a jobs start and end dates to make sure it falls within 
	 * the MaxJobLength range. A job cannot last longer than 
	 * the MaxJobLength.
	 * 
	 * Returns false if the job has a valid job length 
	 * Returns true if the job is longer than the valid job length
	 */
	public boolean checkJobDayLength(int theMaxJobLength) {
		boolean conflict = false;
		Calendar jobStartDay = (Calendar) myStartDate.clone();
		jobStartDay.add(Calendar.DAY_OF_YEAR, theMaxJobLength);
		
	    if(((Calendar) jobStartDay).compareTo(myEndDate) < 0) {
	    	conflict = true;
	    }
		return conflict;
	}
	
	
	/**
	 * Checks a jobs end date to make sure it falls within the
	 * theMaxScheduleWindow range. A job cannot end after theMaxScheduleWindow
	 * days from the current day.
	 * 
	 * Returns false if the job is within the theMaxScheduleWindow range
	 * Returns true if the job ends past the theMaxScheduleWindow
	 */
	public boolean checkJobEndDateMax(int theMaxScheduleWindow) {
		boolean conflict = false;
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, theMaxScheduleWindow);
		
		if (today.compareTo(myEndDate) < 0) {
			conflict = true;
		}
		return conflict;
	}
	
	/**
	 * Checks a job to make sure that its start dates and 
	 * end dates do not occur in the past.
	 * 
	 * @param theJob
	 * @return true if a jobs start or end dates occur in the past, false otherwise.
	 */
	public boolean checkJobDatePast() {
		boolean conflict = false; 
		Calendar today = Calendar.getInstance();
		
		if (myStartDate.compareTo(today) < 0) {
			conflict = true;
		}
		
		if (myEndDate.compareTo(today) > 0) {
			conflict = true;
		}
		return conflict;
	}
	
	
	//private methods for isOverlap()
	private boolean isConflictingWithMyStartDate(Calendar theOther) {
		
		return myStartDate.get(Calendar.DAY_OF_YEAR) == theOther.get(Calendar.DAY_OF_YEAR) &&
				myStartDate.get(Calendar.DAY_OF_MONTH) == theOther.get(Calendar.DAY_OF_MONTH)
				&& myStartDate.get(Calendar.DAY_OF_WEEK) == theOther.get(Calendar.DAY_OF_WEEK);
		
	}
	
	private boolean isConflictingWithMyEndDate(Calendar theOther) {
		
		return myEndDate.get(Calendar.DAY_OF_YEAR) == theOther.get(Calendar.DAY_OF_YEAR) &&
				myEndDate.get(Calendar.DAY_OF_MONTH) == theOther.get(Calendar.DAY_OF_MONTH)
				&& myEndDate.get(Calendar.DAY_OF_WEEK) == theOther.get(Calendar.DAY_OF_WEEK);
		
	}
	
	
	
	/*********** Getters and Setters ***********/
	public String getJobName() {
		return myJobName;
	}
	
	public Calendar getStartDate() {
		return myStartDate;
	}
	
	public Calendar getEndDate() {
		return myEndDate;
	}

	public void setName(String theJobName) {
		myJobName = theJobName;
	}
	
	public void setStartDate(int theMonth, int theDay, int theYear) {
		myStartDate.set(theYear, theMonth, theDay);
	}
	
	public void setEndDate(int theMonth, int theDay, int theYear) {
		myEndDate.set(theYear, theMonth, theDay);
	}
	
	@Override
	public boolean equals(final Object theOther) {
	
		if (this == theOther) {
			return true;
		}
		
		if (theOther == null) {
			return false;
		}
		
		if (getClass() != theOther.getClass()) {
			return false;
		}
		
		final Job otherJob = (Job) theOther;
		return myJobName.equals(otherJob.myJobName) && myStartDate.equals(otherJob.myStartDate)
				&& myEndDate.equals(otherJob.myEndDate);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(myJobName, myStartDate, myEndDate);
	}

	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		int startYear = myStartDate.get(Calendar.YEAR);
		int startMonth = myStartDate.get(Calendar.MONTH);
		int startDay = myStartDate.get(Calendar.DATE);
		
		int endYear = myEndDate.get(Calendar.YEAR);
		int endMonth = myEndDate.get(Calendar.MONTH);
		int endDay = myEndDate.get(Calendar.DATE);
		
		str.append(myJobName + ": ");
		str.append(startMonth + "/" + startDay + "/" + startYear);
		str.append(" - ");
		str.append(endMonth + "/" + endDay + "/" + endYear);

		return str.toString();
	}
	
	

}