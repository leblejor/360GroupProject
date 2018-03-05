package test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.Staff;
import model.Volunteer;

public class JobTest {

	private final int MAX_JOB_LENGTH = 4;
	private final int MAX_SCHEDULE_WINDOW = 60;
	
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
	public void isOverlap_NoCurrentJobsYet_false() {
		mySecondJob.getStartDate().add(Calendar.DATE, 3);
		mySecondJob.getEndDate().add(Calendar.DATE, 5);
		assertFalse("no prior jobs yet; return false", myFirstJob.isOverlap(mySecondJob));
		
	}
	
	@Test
	public void isOverlap_OneJobAlreadyWithoutConflict_false() {		
		myVolunteer.signup(myFirstJob);		
		assertFalse("isOverlap() should've returned false", myFirstJob.isOverlap(new Job()));
	}

	
	@Test
	public void isOverlap_OneJobAlreadyWithMyEndDayInConflictWithOtherJobStartDay_true() {		
		mySecondJob.getStartDate().add(Calendar.DATE, 2);
		mySecondJob.getEndDate().add(Calendar.DATE, 2);
		myVolunteer.signup(myFirstJob);
		assertTrue("My End day is in conflict with other start day; should return true",
				myFirstJob.isOverlap(mySecondJob));
	}
	
	@Test
	public void isOverlap_OneJobAlreadyWithMyStartDayInConflictWithOtherJobEndDay_true() {
		
		myFirstJob.getStartDate().add(Calendar.DATE, 2);
		myFirstJob.getEndDate().add(Calendar.DATE, 2);

		myVolunteer.signup(myFirstJob);
		assertTrue("My start day is in conflict with other end day; should return true",
				myFirstJob.isOverlap(mySecondJob));
	}
	
	@Test
	public void isOverlap_OneJobAlreadyWithOtherStartIsDuringMyJob_true() {
		
		mySecondJob.getStartDate().add(Calendar.DATE, 1);
		
		myVolunteer.signup(myFirstJob);
		assertTrue("My start day is during another job; should return true",
				myFirstJob.isOverlap(mySecondJob));
	}
	
	@Test
	public void isOverlap_OneJobAlreadyWithOtherEndIsDuringMyJob_true() {
		mySecondJob.getEndDate().add(Calendar.DATE, 1);
		
		myVolunteer.signup(myFirstJob);
		assertTrue("My start day is during another job; should return true",
				myFirstJob.isOverlap(mySecondJob));
	}
	@Test
	public void isBeforeMinTimeSpan_MoreThanMinDays_False() {
		Job jobMoreThanMinDays = new Job();
		Calendar futureDate = jobMoreThanMinDays.getStartDate();	
		futureDate.add(Calendar.DAY_OF_YEAR, model.Staff.getMinTimespan() + 1);

		assertFalse(jobMoreThanMinDays.isBeforeMinTimespan());
	}

	@Test
	public void isBeforeMinTimeSpan_JobExactlyMinDaysLater_False() {
		Job jobMinDaysLater = new Job();
		Calendar futureDate = jobMinDaysLater.getStartDate();
		futureDate.add(Calendar.DATE, model.Staff.getMinTimespan());

		assertFalse(jobMinDaysLater.isBeforeMinTimespan());
	}
	
	@Test
	public void isBeforeMinTimeSpan_JobLessThanMinDays_True() {
		Job jobLessThanMinDays = new Job();
		Calendar futureDate = jobLessThanMinDays.getStartDate();
		futureDate.add(Calendar.DATE, model.Staff.getMinTimespan() - 1);

		assertTrue(jobLessThanMinDays.isBeforeMinTimespan());
	}
	@Test (expected = IllegalArgumentException.class)
	public void checkJobDayLength_NegativeInteger_IllegalArgumentException() {
		myFirstJob.checkJobDayLength(-1);
	}
	
	@Test
	public void checkJobDayLength_beforeMaxJobLength_False() {
		Job beforeJob = new Job();
		beforeJob.getStartDate().add(Calendar.DATE, MAX_JOB_LENGTH - 1);
		beforeJob.getEndDate().add(Calendar.DATE, MAX_JOB_LENGTH - 1);
		assertFalse(beforeJob.checkJobDayLength(MAX_JOB_LENGTH));
	}
	
	@Test
	public void checkJobDayLength_exactlyOnMaxJobLength_False() {
		Job exactJob = new Job();
		exactJob.getStartDate().add(Calendar.DATE, MAX_JOB_LENGTH);
		exactJob.getEndDate().add(Calendar.DATE, MAX_JOB_LENGTH);
		assertFalse(exactJob.checkJobDayLength(MAX_JOB_LENGTH));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void checkJobEndDateMax_NegativeInteger_IllegalArgumentException() {
		myFirstJob.checkJobDayLength(-1);
	}
	
	@Test
	public void checkJobEndDateMax_beforeMaxWindow_False() {
		Job beforeJob = new Job();
		beforeJob.getStartDate().add(Calendar.DATE, MAX_SCHEDULE_WINDOW - 1);
		beforeJob.getEndDate().add(Calendar.DATE, MAX_SCHEDULE_WINDOW - 1);
		assertFalse(beforeJob.checkJobDayLength(MAX_SCHEDULE_WINDOW));
	}
	
	@Test
	public void checkJobEndDateMax_exactlyOnMaxWindow_False() {
		Job exactJob = new Job();
		exactJob.getStartDate().add(Calendar.DATE, MAX_SCHEDULE_WINDOW);
		exactJob.getEndDate().add(Calendar.DATE, MAX_SCHEDULE_WINDOW);
		assertFalse(exactJob.checkJobEndDateMax(MAX_SCHEDULE_WINDOW));
	}
	
	@Test
	public void checkJobEndDateMax_afterMaxWindow_True() {
		Job afterJob = new Job();
		afterJob.getStartDate().add(Calendar.DATE, MAX_SCHEDULE_WINDOW + 1);
		afterJob.getEndDate().add(Calendar.DATE, MAX_SCHEDULE_WINDOW + 1);
		assertTrue(afterJob.checkJobEndDateMax(MAX_SCHEDULE_WINDOW));
	}
	
	@Test
	public void checkJobDatePast_pastStartDate_True() {
		Job pastJobStart = new Job();
		pastJobStart.getStartDate().add(Calendar.DATE, -1);
		assertTrue(pastJobStart.checkJobDatePast());
	}
	
	@Test
	public void checkJobDatePast_pastEndDate_True() {
		Job pastJobEnd = new Job();
		pastJobEnd.getEndDate().add(Calendar.DATE, -1);
		assertTrue(pastJobEnd.checkJobDatePast());
	}
	
	@Test
	public void checkJobDatePast_today_False() {
		Job today = new Job();
		
		assertFalse(today.checkJobDatePast());
	}
	
	@Test
	public void isStartDateBeforeEndDate_JobStartAfterJobEnd_True() {
		Job pastJobStart = new Job();
		pastJobStart.getStartDate().add(Calendar.DATE, 1);
		assertTrue(pastJobStart.isStartDateBeforeEndDate());
	}
	
	@Test
	public void isStartDateBeforeEndDate_sameDay_False() {
		Job sameDayJob = new Job();
		assertFalse(sameDayJob.isStartDateBeforeEndDate());
	}
	
	@Test
	public void isStartDateBeforeEndDate_jobEndAfterJobStart_False() {
		Job pastEnd = new Job();
		pastEnd.getEndDate().add(Calendar.DATE, +1);
		assertFalse(pastEnd.isStartDateBeforeEndDate());
	}


}
