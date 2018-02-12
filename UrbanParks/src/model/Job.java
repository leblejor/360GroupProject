package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Represents a Job. A Job holds a title, a start date, and an end date. 
 * 
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
		myStartDate = new GregorianCalendar(theStartYear, theStartMonth, theStartDay);
		myEndDate = new GregorianCalendar(theEndYear, theEndMonth, theEndDay);
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
		return isOverlap || isSameStartDateConflicting(theOther) || isSameEndDateConflicting(theOther)
				|| isMyStartOtherEndDateConflicting(theOther) || isMyEndOtherStartDateConflicting(theOther);

	}
	
	private boolean isSameStartDateConflicting(Job theOther) {
		
		Calendar theOtherStartDate = theOther.getStartDate();
		
		return getStartDate().get(Calendar.DAY_OF_YEAR) == theOtherStartDate.get(Calendar.DAY_OF_YEAR) &&
				getStartDate().get(Calendar.DAY_OF_MONTH) == theOtherStartDate.get(Calendar.DAY_OF_MONTH)
				&& getStartDate().get(Calendar.DAY_OF_WEEK) == theOtherStartDate.get(Calendar.DAY_OF_WEEK);
	}
	
	private boolean isSameEndDateConflicting(Job theOther) {
		
		Calendar theOtherEndDate = theOther.getEndDate();
		
		return getEndDate().get(Calendar.DAY_OF_YEAR) == theOtherEndDate.get(Calendar.DAY_OF_YEAR) &&
				getEndDate().get(Calendar.DAY_OF_MONTH) == theOtherEndDate.get(Calendar.DAY_OF_MONTH)
				&& getEndDate().get(Calendar.DAY_OF_WEEK) == theOtherEndDate.get(Calendar.DAY_OF_WEEK);
	}
	
	private boolean isMyStartOtherEndDateConflicting(Job theOther) {
		
		Calendar theOtherEndDate = theOther.getEndDate();
		
		return getStartDate().get(Calendar.DAY_OF_YEAR) == theOtherEndDate.get(Calendar.DAY_OF_YEAR) &&
				getStartDate().get(Calendar.DAY_OF_MONTH) == theOtherEndDate.get(Calendar.DAY_OF_MONTH)
				&& getStartDate().get(Calendar.DAY_OF_WEEK) == theOtherEndDate.get(Calendar.DAY_OF_WEEK);	
	}
	
	private boolean isMyEndOtherStartDateConflicting(Job theOther) {
		
		Calendar theOtherStartDate = theOther.getStartDate();
		
		return getEndDate().get(Calendar.DAY_OF_YEAR) == theOtherStartDate.get(Calendar.DAY_OF_YEAR) &&
				getEndDate().get(Calendar.DAY_OF_MONTH) == theOtherStartDate.get(Calendar.DAY_OF_MONTH)
				&& getEndDate().get(Calendar.DAY_OF_WEEK) == theOtherStartDate.get(Calendar.DAY_OF_WEEK);
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