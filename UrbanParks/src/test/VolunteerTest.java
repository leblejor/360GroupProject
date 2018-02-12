package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

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
	private Job myFirstJob;
	private Job mySecondJob;
	
	@Before
	public void setUp() throws Exception {
		myVolunteer = new Volunteer("Paolo186", "Bryan");
		myFirstJob = new Job();
		myFirstJob.getStartDate().add(Calendar.DATE, 3);
		myFirstJob.getEndDate().add(Calendar.DATE, 5);
		
		mySecondJob = new Job();
		mySecondJob.getStartDate().add(Calendar.DATE, 3);
		mySecondJob.getEndDate().add(Calendar.DATE, 5);
		
	}

	@Test
	public void isSameDay_NoCurrentJobsYet_false() {
		assertFalse("no prior jobs yet; return false", myVolunteer.isSameDay(myFirstJob));
		
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithoutConflict_false() {
		
		myVolunteer.signup(myFirstJob);		
		assertFalse("isSameDay() should've returned false", myVolunteer.isSameDay(new Job()));
	}

	
	@Test
	public void isSameDay_OneJobAlreadyWithMyEndDayInConflictWithOtherJobStartDay_true() {
		
		
		
		mySecondJob.getStartDate().add(Calendar.DATE, 2);
		mySecondJob.getEndDate().add(Calendar.DATE, 2);
		myVolunteer.signup(myFirstJob);
		assertTrue("My End day is in conflict with other start day; should return true",
				myVolunteer.isSameDay(mySecondJob));
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithMyStartDayInConflictWithOtherJobEndDay_true() {
		
		myFirstJob.getStartDate().add(Calendar.DATE, 2);
		myFirstJob.getEndDate().add(Calendar.DATE, 2);

		myVolunteer.signup(myFirstJob);
		assertTrue("My start day is in conflict with other end day; should return true",
				myVolunteer.isSameDay(mySecondJob));
	}
	

	
	@Test
	public void isSameDay_OneJobAlreadyWithOtherStartIsDuringMyJob_true() {
		
		mySecondJob.getStartDate().add(Calendar.DATE, 1);
		
		myVolunteer.signup(myFirstJob);
		assertTrue("My start day is during another job; should return true",
				myVolunteer.isSameDay(mySecondJob));
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithOtherEndIsDuringMyJob_true() {
		mySecondJob.getEndDate().add(Calendar.DATE, 1);
		
		myVolunteer.signup(myFirstJob);
		assertTrue("My start day is during another job; should return true",
				myVolunteer.isSameDay(mySecondJob));
	}
	
	@Test
	public void checkDaysUntilJob_MoreThanMinDays_False() {
		Job jobMoreThanMinDays = new Job();
		Calendar futureDate = jobMoreThanMinDays.getStartDate();	
		futureDate.add(Calendar.DAY_OF_YEAR, myVolunteer.getMinSignupDays() + 1);

		assertFalse(myVolunteer.checkDaysUntilJob(jobMoreThanMinDays));
	}

	@Test
	public void checkDaysUntilJob_JobExactlyMinDaysLater_False() {
		Job jobMinDaysLater = new Job();
		Calendar futureDate = jobMinDaysLater.getStartDate();
		futureDate.add(Calendar.DATE, myVolunteer.getMinSignupDays());

		assertFalse(myVolunteer.checkDaysUntilJob(jobMinDaysLater));
	}
	
	@Test
	public void checkDaysUntilJob_JobLessThanMinDays_True() {
		Job jobLessThanMinDays = new Job();
		Calendar futureDate = jobLessThanMinDays.getStartDate();
		futureDate.add(Calendar.DATE, myVolunteer.getMinSignupDays() - 1);

		assertTrue(myVolunteer.checkDaysUntilJob(jobLessThanMinDays));
	}

}
