package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Job {
	
	private String myJobName;
	private Calendar myStartDate;
	private Calendar myEndDate;
	
	
	public Job() {
		myJobName = "";
		myStartDate = Calendar.getInstance();
		myEndDate = Calendar.getInstance();

	}
	
	
	// getters
	public String getJobName() {
		return myJobName;
	}
	
	public Calendar getStartDate() {
		return myStartDate;
	}
	
	public Calendar getEndDate() {
		return myEndDate;
	}
	
	public Date getStartTime() {
		return myStartDate.getTime();
	}
	
	public Date getEndTime() {
		return myEndDate.getTime();
	}
	
	
	
	
	
	
	// setters
	public void setName(String theJobName) {
		myJobName = theJobName;
	}
	
	public void setStartDate(int theMonth, int theDay, int theYear) {
		myStartDate.set(theYear, theMonth, theDay);
	}
	
	public void setEndDate(int theMonth, int theDay, int theYear) {
		myEndDate.set(theYear, theMonth, theDay);
	}
	
	public void setStartTime(int theHour, int theMinute) {
		if (myStartDate == null) {
			throw new NullPointerException();
		}
		
		if ( (theHour > 12 && theHour < 0)  || (theMinute > 59 && theMinute < 0) ){
			throw new IllegalArgumentException();
		}
		
		// uses 12-hour clock; not 24-hour clock
		myStartDate.set(Calendar.HOUR, theHour);
		myStartDate.set(Calendar.MINUTE, theMinute);
	}
	
	public void setEndTime(int theHour, int theMinute) {
		if (myStartDate == null) {
			throw new NullPointerException();
		}
		
		if ( (theHour > 12 && theHour < 0)  || (theMinute > 59 && theMinute < 0) ){
			throw new IllegalArgumentException();
		}
		
		// uses 12-hour clock; not 24-hour clock
		myEndDate.set(Calendar.HOUR, theHour);
		myEndDate.set(Calendar.MINUTE, theMinute);
	}
	
	

	
	
	// other methods
	public boolean isOverlap(Job theOther) {
		
		return isSameStartDateConflicting(theOther) ||
				isSameEndDateConflicting(theOther) ||
				isMyStartOtherEndDateConflicting(theOther) ||
				isMyEndOtherStartDateConflicting(theOther);
	}
	
	//private methods for isOverlap()
	
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


}