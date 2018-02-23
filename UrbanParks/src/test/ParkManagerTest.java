package test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.ParkManager;
import model.Staff;

/**
 * Junit testing for the ParkManger class.
 * 
 * @author Derick Salamanca
 */
public class ParkManagerTest {	
	private ParkManager myParkManager;
	
	@Before
	public void setUp() throws Exception {
		myParkManager = new ParkManager("derickzs", "Derick");		
	}
	
	@Test
	public void removeJob_JobExists_Equals() {
		Calendar today = Calendar.getInstance();
		
		String title = "test";
		
		int theStartMonth = today.get(Calendar.MONTH) + 1;
		int theStartDay = today.get(Calendar.DATE);
		int theStartYear = today.get(Calendar.YEAR) ;

		int theEndMonth = theStartMonth;
		int theEndDay = theStartDay;
		int theEndYear = theStartYear;
		
		Job theJob = new Job(title, "", theStartMonth, theStartDay, theStartYear,
				theEndMonth, theEndDay, theEndYear);
		
		myParkManager.createJobLocal(theJob);

		assertEquals(0, myParkManager.removeJob(theJob));
	}
	
	@Test
	public void removeJob_JobDNE_Equals() {
		Calendar today = Calendar.getInstance();
		
		String title = "test";
		
		int theStartMonth = today.get(Calendar.MONTH) + 1;
		int theStartDay = today.get(Calendar.DATE);
		int theStartYear = today.get(Calendar.YEAR) ;

		int theEndMonth = theStartMonth;
		int theEndDay = theStartDay;
		int theEndYear = theStartYear;
		
		Job theJob = new Job(title, "", theStartMonth, theStartDay, theStartYear,
				theEndMonth, theEndDay, theEndYear);
		
		assertEquals(1, myParkManager.removeJob(theJob));
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
		for (int i = 0; i < Staff.getMaxPendingJobs() - 1; i++) {
			myParkManager.createJobLocal(validJob);
		}
		
		assertFalse(myParkManager.checkNumberOfJobsInSystemLocal());
	}
	
	@Test
	public void checkNumberOfJobsInSystem_ExactlyMaxJobsInSystem_True() {
		Job validJob = new Job("TestJob", "", 3, 14, 2018, 3, 15, 2018);
		for (int i = 0; i < Staff.getMaxPendingJobs(); i++) {
			myParkManager.createJobLocal(validJob);
		}
		
		assertTrue(myParkManager.checkNumberOfJobsInSystemLocal());
	}
	
	@Test
	public void checkJobDayLength_OneLessThanMaximumDays_False() {
		Job validLengthJob = new Job();
		Calendar jobEndDate = validLengthJob.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, Staff.getMaxJobLength() - 1);
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validLengthJob.setEndDate(month, day, year);
		
		assertFalse(validLengthJob.checkJobDayLength(Staff.getMaxJobLength()));		
	}
	
	@Test 
	public void checkJobDayLength_ExactlyMaximumDays_False() {
		Job validLengthJob = new Job();
		Calendar jobEndDate = validLengthJob.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, Staff.getMaxJobLength());
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validLengthJob.setEndDate(month, day, year);
		
		assertFalse(validLengthJob.checkJobDayLength(Staff.getMaxJobLength()));	
	}
	
	@Test
	public void checkJobDayLength_OneMoreThanMaximum_True() {
		Job invalidLengthJob = new Job();
		invalidLengthJob.setStartDate(2, 14, 2018);
		invalidLengthJob.setEndDate(2, 14 + Staff.getMaxJobLength() + 1, 2018);
		
		assertTrue(invalidLengthJob.checkJobDayLength(Staff.getMaxJobLength()));	
	}
	
	@Test
	public void checkJobEndDateMax_OneLessThanMaximum_False() {
		Job validJobDuration = new Job();
		Calendar jobEndDate = validJobDuration.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, Staff.getMaxScheduleWindow() - 1);
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validJobDuration.setEndDate(month, day, year);
		
		assertFalse(validJobDuration.checkJobEndDateMax(Staff.getMaxScheduleWindow()));
	}
	
	@Test
	public void checkJobEndDateMax_ExactlyMaximumDays_False() {
		Job validJobDuration = new Job();
		Calendar jobEndDate = validJobDuration.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, Staff.getMaxScheduleWindow());
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		validJobDuration.setEndDate(month, day, year);
		
		assertFalse(validJobDuration.checkJobEndDateMax(Staff.getMaxScheduleWindow()));
	}
	
	@Test
	public void checkJobEndDateMax_OneMoreThanMaximum_True() {
		Job invalidJobDuration = new Job();
		Calendar jobEndDate = invalidJobDuration.getEndDate();
		jobEndDate.add(Calendar.DAY_OF_YEAR, Staff.getMaxScheduleWindow() + 1);
		
		int month = jobEndDate.get(Calendar.MONTH); 
		int day = jobEndDate.get(Calendar.DAY_OF_MONTH);
		int year = jobEndDate.get(Calendar.YEAR);
		
		invalidJobDuration.setEndDate(month, day, year);
		
		assertTrue(invalidJobDuration.checkJobEndDateMax(Staff.getMaxScheduleWindow()));		
	}

}
