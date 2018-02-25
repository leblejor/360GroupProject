package test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.Staff;
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
		myFirstJob.getStartDate().add(Calendar.DATE, model.UrbanParksSystem.getMinTimespan() + 1);
		myFirstJob.getEndDate().add(Calendar.DATE, model.UrbanParksSystem.getMinTimespan() + 2);
		
		mySecondJob = new Job();
		mySecondJob.getStartDate().add(Calendar.DATE, model.UrbanParksSystem.getMinTimespan() + 1);
		mySecondJob.getEndDate().add(Calendar.DATE, model.UrbanParksSystem.getMinTimespan() + 2);
		
	}
	
	@Test
	public void signup_SuccessfulSignup_0() {
		int successful = myVolunteer.signup(myFirstJob);

		assertEquals(0, successful);
	}

	@Test
	public void signup_SameDayConflict_1() {
		myVolunteer.signup(myFirstJob);
		int sameDayConflict = myVolunteer.signup(mySecondJob);

		assertEquals(1, sameDayConflict);
	}
	
	@Test
	public void signup_MinDaysConflict_2() {
		Job jobTomorrow = new Job();
		jobTomorrow.getStartDate().add(Calendar.DATE, 1);
		
		int minDayConflict = myVolunteer.signup(jobTomorrow);
		
		assertEquals(2, minDayConflict);
	}
	
	@Test
	public void removeJob_JobStartsOnSameDay_1() {
		myVolunteer.signup(myFirstJob);
		myFirstJob.getStartDate().add(Calendar.DATE, - (model.UrbanParksSystem.getMinTimespan() + 1));
		myFirstJob.getEndDate().add(Calendar.DATE, - (model.UrbanParksSystem.getMinTimespan() + 2));
		
		int sameDayConflict = myVolunteer.removeJob(myFirstJob);
		
		assertEquals(1, sameDayConflict);
	}
	
	@Test
	public void removeJob_MultiDayJobStartsBeforeToday_1() {
		Job multiDayJob = new Job();
		multiDayJob.getStartDate().add(Calendar.DATE, model.UrbanParksSystem.getMinTimespan());
		multiDayJob.getEndDate().add(Calendar.DATE, model.UrbanParksSystem.getMinTimespan() + 2);
		
		myVolunteer.signup(multiDayJob);
		
		multiDayJob.getStartDate().add(Calendar.DATE, - (model.UrbanParksSystem.getMinTimespan() + 1));
		multiDayJob.getEndDate().add(Calendar.DATE, - (model.UrbanParksSystem.getMinTimespan() + 2));
		
		int jobStartsBeforeToday = myVolunteer.removeJob(multiDayJob);
		
		assertEquals(1, jobStartsBeforeToday);
	}
	
	@Test
	public void removeJob_JobStartsMoreThanMinDaysAway_0() {
		myVolunteer.signup(myFirstJob);
		int jobInFuture = myVolunteer.removeJob(myFirstJob);
		
		assertEquals(0, jobInFuture);
	}

	@Test
	public void removeJob_JobStartsExactlyMinDaysAway_0() {
		Job jobMinDaysAway = new Job();
		jobMinDaysAway.getStartDate().add(Calendar.DATE, model.UrbanParksSystem.getMinTimespan());
		jobMinDaysAway.getEndDate().add(Calendar.DATE, model.UrbanParksSystem.getMinTimespan() + 1);
		
		myVolunteer.signup(jobMinDaysAway);
		
		int jobStartsExactlyMinDaysAway = myVolunteer.removeJob(jobMinDaysAway);
		
		assertEquals(0, jobStartsExactlyMinDaysAway);
		
	}

}
