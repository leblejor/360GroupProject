package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
	
	

}
