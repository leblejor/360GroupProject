package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.Staff;
import model.UrbanParksSystem;

/**
 * JUnit testing for the staff class.
 * 
 * @author Jordan LeBle
 */
public class StaffTest {

	private UrbanParksSystem mySystem;
	private Staff myUser;
	
	@Before
	public void setup() {
		mySystem = new UrbanParksSystem();
		myUser = (Staff) mySystem.signIn("emerson01");
	}
	
	@Test
	public void setMaxPendingJobs_Zero_1() {
		model.Staff myStaffMember = new model.Staff("testing", "test");
		assertEquals(1, myStaffMember.setMaxPendingJobsLocal(0));
	}

	@Test
	public void setMaxPendingJobs_Negative_2() {
		model.Staff myStaffMember = new model.Staff("testing", "test");
		assertEquals(2, myStaffMember.setMaxPendingJobsLocal(-1));
	}
	
	@Test
	public void setMaxPendingJobs_Positive_0() {
		model.Staff myStaffMember = new model.Staff("testing", "test");
		assertEquals(0, myStaffMember.setMaxPendingJobsLocal(1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void viewJobsWithinRange_jobStartIsAfterJobEnd_ThrowIllegalArgumentException() {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		startDate.add(Calendar.DATE, 1);
		myUser.viewJobsWithinRange(mySystem, startDate, endDate);
	}
	
	@Test
	public void viewJobsWithinRange_jobStartIsSameAsJobEnd_Allowed() {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		
		Set<Job> set = myUser.viewJobsWithinRange(mySystem, startDate, endDate);
		assertNotNull(set);
	}
	
	
}
