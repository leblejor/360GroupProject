package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Job;
import model.Volunteer;

class VolunteerUnitTest {

	@Test
	final void checkDaysUntilJob_MuchMoreDays_False() {
		Volunteer volunteerNoJobs = new Volunteer("testUsername", "testName");
		Job jobInFarFuture = new Job();
		jobInFarFuture.setStartDate(4, 27, 2018);
		
		assertFalse(volunteerNoJobs.checkDaysUntilJob(jobInFarFuture));
	}

	@Test
	final void checkDaysUntilJob_JobExactlyTwoDaysLater_False() {
		Volunteer volunteerNoJobs = new Volunteer("testUsername", "testName");
		Job jobTwoDaysLater = new Job();
		jobTwoDaysLater.setStartDate(2, 7, 2018);
		
		assertFalse(volunteerNoJobs.checkDaysUntilJob(jobTwoDaysLater));
	}
	
	@Test
	final void checkDaysUntilJob_JobLessThanTwoDays_True() {
		Volunteer volunteerNoJobs = new Volunteer("testUsername", "testName");
		Job jobLessThanTwoDays = new Job();
		jobLessThanTwoDays.setStartDate(2, 1, 2018);
		
		assertTrue(volunteerNoJobs.checkDaysUntilJob(jobLessThanTwoDays));
	}

}
