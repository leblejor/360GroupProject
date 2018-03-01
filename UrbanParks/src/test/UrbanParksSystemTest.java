package test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.ParkManager;
import model.Staff;
import model.UrbanParksSystem;


/**
 * 
 * @author Bryan Santos
 * @version 02/26/2018
 *
 */

public class UrbanParksSystemTest {

	private UrbanParksSystem mySystem;
	private ParkManager myParkManager;
	
	private int myStartMonth;
	private int myStartDay;
	private int myStartYear;
	private int myEndMonth;
	private int myEndDay;
	private int myEndYear;
	
	@Before
	public void setUp() throws Exception {
		mySystem = new UrbanParksSystem();
		myParkManager = (ParkManager) mySystem.signIn("jordan23");
		
		Calendar today = Calendar.getInstance();
		myStartMonth = today.get(Calendar.MONTH);
		myStartDay = today.get(Calendar.DATE) + 1;
		myStartYear = today.get(Calendar.YEAR) ;
		myEndMonth = myStartMonth;
		myEndDay = myStartDay;
		myEndYear = myStartYear;
	}

	@Test
	public void signIn_knownUser_ReturnUser() {
		assertNotNull(mySystem.signIn("paolo186"));

	}
	
	@Test 
	public void signIn_unknownUser_GetNullValue() {
		assertNull(mySystem.signIn("unknown"));
		
	}
	
	@Test
	public void isJobsInSystemFull_FewerThanMaximum_False() {
		Job validJob = new Job("TestJob", "", myStartMonth, myStartDay, myStartYear,
								myEndMonth, myEndDay, myEndYear);
		
		myParkManager.createJob(mySystem, validJob);
		assertFalse(mySystem.areJobsInSystemFull());
	}

	@Test
	public void isJobsInSystemFull_OneLessThanMaximum_False() {
		int size = mySystem.jobsInSystem();
		
		for (int index = 0; index < Staff.getMaxPendingJobs() - size - 1; index++) {
			String jobName = "job" + index;
			Job job = new Job(jobName, "", myStartMonth, myStartDay + index, myStartYear,
							  myEndMonth, myEndDay, myEndYear);
			
			myParkManager.createJob(mySystem, job);
		}
		
		assertFalse(mySystem.areJobsInSystemFull());
	}
	
	@Test
	public void isJobsInSystemFull_ExactlyMaxJobsInSystem_True() {

		int size = mySystem.jobsInSystem();
		
		for (int index = 0; index < Staff.getMaxPendingJobs() - size; index++) {
			String jobName = "job" + index;
			Job job = new Job(jobName, "", myStartMonth, myStartDay + index, myStartYear,
							  myEndMonth, myEndDay, myEndYear);
			
			myParkManager.createJob(mySystem, job);
		}
		
		assertTrue(mySystem.areJobsInSystemFull());
	}

}
