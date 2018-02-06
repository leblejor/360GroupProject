package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Job;
import model.Volunteer;

class VolunteerUnitTest {

	@Test
	final void checkDaysUntilJob_MuchMoreDays_False() {
		Volunteer volunteerNoJobs = new Volunteer("testUsername", "testName");
		Job jobInFarFuture = new Job();
		Calendar futureDate = jobInFarFuture.getStartDate();
		futureDate.add(Calendar.DATE, 14); //Adds 14 days in the future from current date
		int day = futureDate.get(DAY_OF_MONTH);
		int month = futureDate.get(MONTH);
		int year = futureDate.get(YEAR);
		
		//Sets start date to 14 days in the future
		jobInFarFuture.setStartDate(month, day, year);
		
		assertFalse(volunteerNoJobs.checkDaysUntilJob(jobInFarFuture));
	}

	@Test
	final void checkDaysUntilJob_JobExactlyTwoDaysLater_False() {
		Volunteer volunteerNoJobs = new Volunteer("testUsername", "testName");
		Job jobTwoDaysLater = new Job();
		Calendar futureDate = jobInFarFuture.getStartDate();
		futureDate.add(Calendar.DATE, 2); //Adds 2 days in the future from current date
		int day = futureDate.get(DAY_OF_MONTH);
		int month = futureDate.get(MONTH);
		int year = futureDate.get(YEAR);
		
		//Sets start date to 2 days in the future
		jobInFarFuture.setStartDate(month, day, year);
		
		assertFalse(volunteerNoJobs.checkDaysUntilJob(jobTwoDaysLater));
	}
	
	@Test
	final void checkDaysUntilJob_JobLessThanTwoDays_True() {
		Volunteer volunteerNoJobs = new Volunteer("testUsername", "testName");
		Job jobLessThanTwoDays = new Job();
		Calendar futureDate = jobInFarFuture.getStartDate();
		futureDate.add(Calendar.DATE, 1); //Adds 1 day in the future from current date
		int day = futureDate.get(DAY_OF_MONTH);
		int month = futureDate.get(MONTH);
		int year = futureDate.get(YEAR);
		
		//Sets start date to 1 day in the future
		jobInFarFuture.setStartDate(month, day, year);
		
		assertTrue(volunteerNoJobs.checkDaysUntilJob(jobLessThanTwoDays));
	}--

}
