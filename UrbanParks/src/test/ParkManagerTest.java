package test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.ParkManager;

/**
 * Junit testing for the ParkManger class.
 * 
 * @author Derick Salamanca
 * @author Jordan LeBle
 */
public class ParkManagerTest {	
	private ParkManager myParkManager;
	
	@Before
	public void setUp() throws Exception {
		myParkManager = new ParkManager("derickzs", "Derick");		
	}
	
	@Test
	public void removeJob_JobStartsToday_2() {
		// Create Job that starts and ends today
		Job theJob = new Job();
		myParkManager.createJobLocal(theJob);

		assertEquals(2, myParkManager.removeJobLocal(theJob));
	}
	
	@Test
	public void removeJob_MultiDayJobStartsBeforeToday_2() {
		// Create Job that starts the day before today, and ends the day after today
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_YEAR, -1);
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_YEAR, 1);
		
		Job aJob = new Job("", "", yesterday.get(Calendar.MONTH), 
				yesterday.get(Calendar.DAY_OF_MONTH),yesterday.get(Calendar.YEAR), 
				tomorrow.get(Calendar.MONTH), tomorrow.get(Calendar.DAY_OF_MONTH) + 1, 
				tomorrow.get(Calendar.YEAR));

		myParkManager.createJobLocal(aJob);
		
		int jobStartsBeforeToday = myParkManager.removeJobLocal(aJob);
		
		assertEquals(2, jobStartsBeforeToday);
	}
	
	@Test
	public void removeJob_JobStartsMoreThanMinDaysAway_0() {
		Job aJob = new Job();
		aJob.getStartDate().add(Calendar.DATE, model.Staff.getMinTimespan() + 1);
		aJob.getEndDate().add(Calendar.DATE, model.Staff.getMinTimespan() + 2);
		
		myParkManager.createJobLocal(aJob);
		int jobInFuture = myParkManager.removeJobLocal(aJob);
		
		assertEquals(0, jobInFuture);
	}

	@Test
	public void removeJob_JobStartsExactlyMinDaysAway_0() {
		Job jobMinDaysAway = new Job();
		jobMinDaysAway.getStartDate().add(Calendar.DAY_OF_YEAR, model.Staff.getMinTimespan());
		jobMinDaysAway.getEndDate().add(Calendar.DAY_OF_YEAR, model.Staff.getMinTimespan());
		
		myParkManager.createJobLocal(jobMinDaysAway);
		
		int jobStartsExactlyMinDaysAway = myParkManager.removeJobLocal(jobMinDaysAway);
		
		assertEquals(0, jobStartsExactlyMinDaysAway);
		
	}
	
	@Test
	public void checkNumberOfJobsInSystem_FewerThanMaximum_False() {
		Job validJob = new Job("TestJob", "", 3, 14, 2018, 3, 15, 2018);
		myParkManager.createJobLocal(validJob);

		assertFalse(myParkManager.checkNumberOfJobsInSystemLocal());
	}

	@Test
	public void checkNumberOfJobsInSystem_OneLessThanMaximum_False() {
		Job validJob = new Job("TestJob", "", 3, 14, 2018, 3, 15, 2018);
		for (int i = 0; i < model.Staff.getMaxPendingJobs() - 1; i++) {
			myParkManager.createJobLocal(validJob);
		}
		
		assertFalse(myParkManager.checkNumberOfJobsInSystemLocal());
	}
	
	@Test
	public void checkNumberOfJobsInSystem_ExactlyMaxJobsInSystem_True() {
		Job validJob = new Job("TestJob", "", 3, 14, 2018, 3, 15, 2018);
		for (int i = 0; i < model.Staff.getMaxPendingJobs(); i++) {
			myParkManager.createJobLocal(validJob);
		}
		
		assertTrue(myParkManager.checkNumberOfJobsInSystemLocal());
	}
	
	@Test
	public void checkJobDayLength_OneLessThanMaximumDays_False() {
		Job validLengthJob = new Job();
		Calendar jobEndDate = validLengthJob.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, model.Staff.getMaxJobDuration() - 1);
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validLengthJob.setEndDate(month, day, year);
		
		assertFalse(validLengthJob.checkJobDayLength(model.Staff.getMaxJobDuration()));		
	}
	
	@Test 
	public void checkJobDayLength_ExactlyMaximumDays_False() {
		Job validLengthJob = new Job();
		Calendar jobEndDate = validLengthJob.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, model.Staff.getMaxJobDuration());
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validLengthJob.setEndDate(month, day, year);
		
		assertFalse(validLengthJob.checkJobDayLength(model.Staff.getMaxJobDuration()));	
	}
	
	@Test
	public void checkJobDayLength_OneMoreThanMaximum_True() {
		Job invalidLengthJob = new Job();
		invalidLengthJob.setStartDate(2, 14, 2018);
		invalidLengthJob.setEndDate(2, 14 + model.Staff.getMaxJobDuration() + 1, 2018);
		
		assertTrue(invalidLengthJob.checkJobDayLength(model.Staff.getMaxJobDuration()));	
	}
	
	@Test
	public void checkJobEndDateMax_OneLessThanMaximum_False() {
		Job validJobDuration = new Job();
		Calendar jobEndDate = validJobDuration.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, model.Staff.getMaxTimespan() - 1);
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validJobDuration.setEndDate(month, day, year);
		
		assertFalse(validJobDuration.checkJobEndDateMax(model.Staff.getMaxTimespan()));
	}
	
	@Test
	public void checkJobEndDateMax_ExactlyMaximumDays_False() {
		Job validJobDuration = new Job();
		Calendar jobEndDate = validJobDuration.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, model.Staff.getMaxTimespan());
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validJobDuration.setEndDate(month, day, year);
		
		assertFalse(validJobDuration.checkJobEndDateMax(model.Staff.getMaxTimespan()));
	}
	
	@Test
	public void checkJobEndDateMax_OneMoreThanMaximum_True() {
		Job invalidJobDuration = new Job();
		Calendar jobEndDate = invalidJobDuration.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, model.Staff.getMaxTimespan() + 1);
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		invalidJobDuration.setEndDate(month, day, year);
		
		assertTrue(invalidJobDuration.checkJobEndDateMax(model.Staff.getMaxTimespan()));		
	}

}
