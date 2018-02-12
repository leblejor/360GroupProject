package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

import java.util.Calendar;

import model.Job;
import model.ParkManager;

/**
 * Junit testing for the ParkManger class.
 * 
 * @author Derick Salamanca
 */
public class ParkManagerTest {	
	private ParkManager myParkManager;
	
	@BeforeEach
	public void setUp() throws Exception {
		myParkManager = new ParkManager("derickzs", "Derick");		
	}
	
	@Test
	public void checkNumberOfJobsInSystem_FewerThanMaximum_False() {
		myParkManager.createJobLocal("TestJob", 2, 14, 2018, 2, 15, 2018);

		assertFalse(myParkManager.checkNumberOfJobsInSystemLocal());
	}

	@Test
	public void checkNumberOfJobsInSystem_OneLessThanMaximum_False() {
		for (int i = 0; i < myParkManager.getMaxPendingJobs() - 1; i++) {
			myParkManager.createJobLocal("TestJob", 2, 14, 2018, 2, 15, 2018);
		}
		
		assertFalse(myParkManager.checkNumberOfJobsInSystemLocal());
	}
	
	@Test
	public void checkNumberOfJobsInSystem_ExactlyMaxJobsInSystem_True() {
		for (int i = 0; i < myParkManager.getMaxPendingJobs(); i++) {
			myParkManager.createJobLocal("TestJob", 2, 14, 2018, 2, 15, 2018);
		}
		
		assertTrue(myParkManager.checkNumberOfJobsInSystemLocal());
	}
	
	@Test
	public void checkJobDayLength_OneLessThanMaximumDays_False() {
		Job validLengthJob = new Job();
		Calendar jobEndDate = validLengthJob.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, myParkManager.getMaxJobLength() - 1);
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validLengthJob.setEndDate(month, day, year);
		
		assertFalse(myParkManager.checkJobDayLength(validLengthJob));		
	}
	
	@Test 
	public void checkJobDayLength_ExactlyMaximumDays_False() {
		Job validLengthJob = new Job();
		Calendar jobEndDate = validLengthJob.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, myParkManager.getMaxJobLength());
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validLengthJob.setEndDate(month, day, year);
		
		assertFalse(myParkManager.checkJobDayLength(validLengthJob));	
	}
	
	@Test
	public void checkJobDayLength_OneMoreThanMaximum_True() {
		Job invalidLengthJob = new Job();
		invalidLengthJob.setStartDate(2, 14, 2018);
		invalidLengthJob.setEndDate(2, 14 + myParkManager.getMaxJobLength() + 1, 2018);
		
		assertTrue(myParkManager.checkJobDayLength(invalidLengthJob));	
	}
	
	@Test
	public void checkJobEndDateMax_OneLessThanMaximum_False() {
		Job validJobDuration = new Job();
		Calendar jobEndDate = validJobDuration.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, myParkManager.getMaxScheduleWindow() - 1);
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validJobDuration.setEndDate(month, day, year);
		
		assertFalse(myParkManager.checkJobEndDateMax(validJobDuration));
	}
	
	@Test
	public void checkJobEndDateMax_ExactlyMaximumDays_False() {
		Job validJobDuration = new Job();
		Calendar jobEndDate = validJobDuration.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, myParkManager.getMaxScheduleWindow());
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validJobDuration.setEndDate(month, day, year);
		
		assertFalse(myParkManager.checkJobEndDateMax(validJobDuration));
	}
	
	@Test
	public void checkJobEndDateMax_OneMoreThanMaximum_True() {
		Job invalidJobDuration = new Job();
		Calendar jobEndDate = invalidJobDuration.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, myParkManager.getMaxScheduleWindow() + 1);
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		invalidJobDuration.setEndDate(month, day, year);
		
		assertTrue(myParkManager.checkJobEndDateMax(invalidJobDuration));		
	}

}
