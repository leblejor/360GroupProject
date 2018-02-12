package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.UrbanParksSystem;
import model.User;


/**
 * JUnit testing for the UrbanParksSystme user sign in.
 * 
 * @author Derick Salamanca
 */
public class UserTest {

	@Test
	public void signIn_SignInAsVolunteer_Equals() {
		UrbanParksSystem ups = new UrbanParksSystem();
		User volunteer = ups.signIn("paolo186");
		
		assertEquals("Volunteer", volunteer.getDescription());
	}
	
	@Test 
	public void signIn_SignInAsParkManager_Equals() {
		UrbanParksSystem ups = new UrbanParksSystem();
		User volunteer = ups.signIn("jordan23");
		
		assertEquals("Park Manager", volunteer.getDescription());
	}
	
	@Test
	public void signIn_SignInAsStaff_Equals() { 
		UrbanParksSystem ups = new UrbanParksSystem();
		User volunteer = ups.signIn("emerson01");
		
		assertEquals("Staff", volunteer.getDescription());
	}


}
