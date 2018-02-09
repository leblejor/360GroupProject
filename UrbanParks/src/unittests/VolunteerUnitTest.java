package unittests;
import static org.junit.jupiter.api.Assertions.*;
import java.text.ParseException;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import model.Job;
import model.Volunteer;

class VolunteerUnitTest {

	@Test
	final void checkDaysUntilJob_MoreThanMinDays_False() throws ParseException {
		Volunteer volunteerNoJobs = new Volunteer("testUsername", "testName");
		Job jobMoreThanMinDays = new Job();
		Calendar futureDate = jobMoreThanMinDays.getStartDate();
		futureDate.add(Calendar.DATE, 14);
		int day = futureDate.get(Calendar.DAY_OF_MONTH);
		int month = futureDate.get(Calendar.MONTH) + 1;
		int year = futureDate.get(Calendar.YEAR);
		
		jobMoreThanMinDays.setStartDate(month, day, year);
		
		assertFalse(volunteerNoJobs.checkDaysUntilJob(jobMoreThanMinDays));
	}

	@Test
	final void checkDaysUntilJob_JobExactlyMinDaysLater_False() throws ParseException {
		Volunteer volunteerNoJobs = new Volunteer("testUsername", "testName");
		Job jobMinDaysLater = new Job();
		Calendar futureDate = jobMinDaysLater.getStartDate();
		futureDate.add(Calendar.DATE, 2);
		int day = futureDate.get(Calendar.DAY_OF_MONTH);
		int month = futureDate.get(Calendar.MONTH) + 1;
		int year = futureDate.get(Calendar.YEAR);
		
		//Sets start date to 2 days in the future
		jobMinDaysLater.setStartDate(month, day, year);
		
		assertFalse(volunteerNoJobs.checkDaysUntilJob(jobMinDaysLater));
	}
	
	@Test
	final void checkDaysUntilJob_JobLessThanMinDays_True() throws ParseException {
		Volunteer volunteerNoJobs = new Volunteer("testUsername", "testName");
		Job jobLessThanMinDays = new Job();
		Calendar futureDate = jobLessThanMinDays.getStartDate();
		futureDate.add(Calendar.DATE, 1); //Adds 1 day in the future from current date
		int day = futureDate.get(Calendar.DAY_OF_MONTH);
		int month = futureDate.get(Calendar.MONTH) + 1;
		int year = futureDate.get(Calendar.YEAR);
		
		//Sets start date to 1 day in the future
		jobLessThanMinDays.setStartDate(month, day, year);
		
		assertTrue(volunteerNoJobs.checkDaysUntilJob(jobLessThanMinDays));
	}

}
