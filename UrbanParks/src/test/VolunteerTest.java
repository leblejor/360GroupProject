package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Job;
import model.Volunteer;

/**
 * JUnit testing for the volunteer class.
 * 
 * @author Derick Salamanca
 * @author Bryan Santos
 */
public class VolunteerTest {

	private Volunteer myVolunteer;
	
	@BeforeEach
	public void setUp() throws Exception {
		myVolunteer = new Volunteer("Paolo186", "Bryan");
	}

	@Test
	public void isSameDay_NoCurrentJobsYet_false() {
		Job noJob = new Job();
		assertFalse("isSameDay() should've returned false", myVolunteer.isSameDay(noJob));
		
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithoutConflict_false() {
		Job firstJob = new Job();
		firstJob.setName("Park Trashing");
		firstJob.setStartDate(01, 02, 2018);
		firstJob.setEndDate(01, 03, 2018);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 04, 2018);
		secondJob.setEndDate(01, 05, 2018);

		myVolunteer.signup(firstJob);
		
		assertFalse("isSameDay() should've returned false", myVolunteer.isSameDay(secondJob));
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithMyEndDayInConflictWithOtherJobStartDay_true() {
		Job firstJob = new Job();
		firstJob.setName("Park Trashing");
		firstJob.setStartDate(01, 02, 2018);
		firstJob.setEndDate(01, 03, 2018);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 03, 2018);
		secondJob.setEndDate(01, 04, 2018);
		
		myVolunteer.signup(firstJob);
		assertTrue("My End day is in conflict with other start day; should return true",
				myVolunteer.isSameDay(secondJob));
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithMyStartDayInConflictWithOtherJobEndDay_true() {
		Job firstJob = new Job();
		firstJob.setName("Park Trashing");
		firstJob.setStartDate(01, 02, 2018);
		firstJob.setEndDate(01, 03, 2018);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 01, 2018);
		secondJob.setEndDate(01, 02, 2018);

		myVolunteer.signup(firstJob);
		assertTrue("My start day is in conflict with other end day; should return true",
				myVolunteer.isSameDay(secondJob));
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithMyStartDayInConflictWithOtherJobStartDay_true() {
		Job firstJob = new Job();
		firstJob.setName("Park Trashing");
		firstJob.setStartDate(01, 02, 2018);
		firstJob.setEndDate(01, 03, 2018);

		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 02, 2018);
		secondJob.setEndDate(01, 04, 2018);

		myVolunteer.signup(firstJob);
		assertTrue("My start day is in conflict with start end day; should return true",
				myVolunteer.isSameDay(secondJob));
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithMyEndDayInConflictWithOtherJobEndDay_true() {
		Job firstJob = new Job();
		firstJob.setName("Park Trashing");
		firstJob.setStartDate(01, 02, 2018);
		firstJob.setEndDate(01, 03, 2018);

		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 01, 2018);
		secondJob.setEndDate(01, 03, 2018);

		myVolunteer.signup(firstJob);
		assertTrue("My end day is in conflict with other end day; should return true",
				myVolunteer.isSameDay(secondJob));
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithMyStartDayIsDuringOtherJob_true() {
		Job firstJob = new Job();
		firstJob.setName("Park Trashing");
		firstJob.setStartDate(01, 03, 2018);
		firstJob.setEndDate(01, 05, 2018);

		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 02, 2018);
		secondJob.setEndDate(01, 04, 2018);
		
		myVolunteer.signup(firstJob);
		assertTrue("My start day is during another job; should return true",
				myVolunteer.isSameDay(secondJob));
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithMyEndDayIsDuringOtherJob_true() {
		Job firstJob = new Job();
		firstJob.setName("Park Trashing");
		firstJob.setStartDate(01, 01, 2018);
		firstJob.setEndDate(01, 03, 2018);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 02, 2018);
		secondJob.setEndDate(01, 04, 2018);
		myVolunteer.signup(firstJob);
		assertTrue("My start day is during another job; should return true",
				myVolunteer.isSameDay(secondJob));
	}
	
	@Test
	final void checkDaysUntilJob_MoreThanMinDays_False() {
		Job jobMoreThanMinDays = new Job();
		Calendar futureDate = jobMoreThanMinDays.getStartDate();	
		futureDate.add(Calendar.DAY_OF_YEAR, myVolunteer.getMinSignupDays() - 1);

		assertFalse(myVolunteer.checkDaysUntilJob(jobMoreThanMinDays));
	}

	@Test
	final void checkDaysUntilJob_JobExactlyMinDaysLater_False() {
		Job jobMinDaysLater = new Job();
		Calendar futureDate = jobMinDaysLater.getStartDate();
		futureDate.add(Calendar.DATE, myVolunteer.getMinSignupDays());

		assertFalse(myVolunteer.checkDaysUntilJob(jobMinDaysLater));
	}
	
	@Test
	final void checkDaysUntilJob_JobLessThanMinDays_True() {
		Job jobLessThanMinDays = new Job();
		Calendar futureDate = jobLessThanMinDays.getStartDate();
		futureDate.add(Calendar.DATE, myVolunteer.getMinSignupDays() + 1);

		assertTrue(myVolunteer.checkDaysUntilJob(jobLessThanMinDays));
	}

}
