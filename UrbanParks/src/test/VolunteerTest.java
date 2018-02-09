package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.Volunteer;


import java.text.ParseException;
import java.util.Calendar;


import model.Job;
import model.Volunteer;

public class VolunteerTest {

	private Volunteer myVolunteer;
	
	@Before
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
		firstJob.setStartTime(7, 00);
		firstJob.setEndTime(3, 00);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 04, 2018);
		secondJob.setEndDate(01, 05, 2018);
		secondJob.setStartTime(7, 00);
		secondJob.setEndTime(3, 00);
		
		myVolunteer.signup(firstJob);
		
		assertFalse("isSameDay() should've returned false", myVolunteer.isSameDay(secondJob));
	}
	
	@Test
	public void isSameDay_OneJobAlreadyWithMyEndDayInConflictWithOtherJobStartDay_true() {
		Job firstJob = new Job();
		firstJob.setName("Park Trashing");
		firstJob.setStartDate(01, 02, 2018);
		firstJob.setEndDate(01, 03, 2018);
		firstJob.setStartTime(7, 00);
		firstJob.setEndTime(3, 00);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 03, 2018);
		secondJob.setEndDate(01, 04, 2018);
		secondJob.setStartTime(7, 00);
		secondJob.setEndTime(3, 00);
		
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
		firstJob.setStartTime(7, 00);
		firstJob.setEndTime(3, 00);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 01, 2018);
		secondJob.setEndDate(01, 02, 2018);
		secondJob.setStartTime(7, 00);
		secondJob.setEndTime(3, 00);
		
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
		firstJob.setStartTime(7, 00);
		firstJob.setEndTime(3, 00);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 02, 2018);
		secondJob.setEndDate(01, 04, 2018);
		secondJob.setStartTime(7, 00);
		secondJob.setEndTime(3, 00);
		
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
		firstJob.setStartTime(7, 00);
		firstJob.setEndTime(3, 00);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 01, 2018);
		secondJob.setEndDate(01, 03, 2018);
		secondJob.setStartTime(7, 00);
		secondJob.setEndTime(3, 00);
		
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
		firstJob.setStartTime(7, 00);
		firstJob.setEndTime(3, 00);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 02, 2018);
		secondJob.setEndDate(01, 04, 2018);
		secondJob.setStartTime(7, 00);
		secondJob.setEndTime(3, 00);
		
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
		firstJob.setStartTime(7, 00);
		firstJob.setEndTime(3, 00);
		
		Job secondJob = new Job();
		secondJob.setName("Park Cleaning");
		secondJob.setStartDate(01, 02, 2018);
		secondJob.setEndDate(01, 04, 2018);
		secondJob.setStartTime(7, 00);
		secondJob.setEndTime(3, 00);
		
		myVolunteer.signup(firstJob);
		assertTrue("My start day is during another job; should return true",
				myVolunteer.isSameDay(secondJob));
	}
	
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
