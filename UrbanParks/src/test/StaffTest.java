package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit testing for the staff class.
 * 
 * @author Jordan LeBle
 */
public class StaffTest {

	@Before
	public void setup() {
		//model.Staff myStaffMember = new model.Staff("testing", "test");
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
}
