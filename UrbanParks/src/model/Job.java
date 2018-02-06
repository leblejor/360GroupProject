package model;

import java.lang.IllegalArgumentException;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Job {
	private static final int MAX_JOB_LENGTH = 3;
	private static final int MILLIS_IN_DAY = 86400000;

    private Calendar myStartDate;
	private Calendar myEndDate;
	
	
	public Job(int theStartMonth, int theStartDay, int theStartYear,
				int theEndMonth, int theEndDay, int theEndYear) {
		
		Calendar myStartDate = new GregorianCalendar(theStartYear, theStartMonth, theStartDay);
		Calendar myEndDate = new GregorianCalendar(theEndYear, theEndMonth, theEndDay);
		
		if((myEndDate.getTimeInMillis() - myStartDate.getTimeInMillis()) > MAX_JOB_LENGTH * MILLIS_IN_DAY) {
			throw new IllegalArgumentException();
		}
	}
}