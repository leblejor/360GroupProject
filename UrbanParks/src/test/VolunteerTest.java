package test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import model.Job;
import model.Volunteer;

/**
 * JUnit testing for the volunteer class.
 * 
 * @author Derick Salamanca
 * @author Bryan Santos
 * @author Jordan LeBle
 */
public class VolunteerTest {

	private Volunteer myVolunteer;
	
	private Calendar yesterday;
	private Calendar today;
	private Calendar tomorrow;
	
	private Calendar lessThanMinDaysAway;
	private Calendar minDaysAway;
	private Calendar moreThanMinDaysAway;
	private Calendar muchMoreThanMinDaysAway;
	
	@Before
	public void setup() throws Exception {
		myVolunteer = new Volunteer("Paolo186", "Bryan");
		
		yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_YEAR, -1);
		
		today = Calendar.getInstance();
		
		tomorrow = Calendar.getInstance(); 
		tomorrow.add(Calendar.DAY_OF_YEAR, 1);
		
		lessThanMinDaysAway = Calendar.getInstance();
		lessThanMinDaysAway.add(Calendar.DAY_OF_YEAR, model.UrbanParksSystem.getMinTimespan() - 1);
		
		minDaysAway = Calendar.getInstance();
		minDaysAway.add(Calendar.DAY_OF_YEAR, model.UrbanParksSystem.getMinTimespan());
		
		moreThanMinDaysAway = Calendar.getInstance();
		moreThanMinDaysAway.add(Calendar.DAY_OF_YEAR, model.UrbanParksSystem.getMinTimespan() + 1);
		
		muchMoreThanMinDaysAway = Calendar.getInstance();
		muchMoreThanMinDaysAway.add(Calendar.DAY_OF_YEAR, model.UrbanParksSystem.getMinTimespan() + 2);
	}
	
	@Test
	public void signup_JobBeginsLessThanMinDaysAway_2() {		
		// Maybe change this? what if MIN_TIMESPAN = 1?

		Job jobBeginsLessThanMinDaysAway = new Job("", "", lessThanMinDaysAway, lessThanMinDaysAway);
		
		assertEquals(2, myVolunteer.signup(jobBeginsLessThanMinDaysAway));
	}
	
	@Test
	public void signup_JobStartsMoreThanMinDaysAway_0() {
		Job jobStartsMoreThanMinDaysAway = new Job("", "", moreThanMinDaysAway, moreThanMinDaysAway);
		
		assertEquals(0, myVolunteer.signup(jobStartsMoreThanMinDaysAway));
	}

	@Test
	public void signup_JobStartsExactlyMinDaysAway_0() {
		Job jobStartsMinDaysAway = new Job("", "", minDaysAway, minDaysAway);

		assertEquals(0, myVolunteer.signup(jobStartsMinDaysAway));
	}

	@Test
	public void signup_AStartConflictsOtherEnd_1() {
		Job aJob = new Job("", "", moreThanMinDaysAway, muchMoreThanMinDaysAway);
		
		Job otherJob = new Job("", "", minDaysAway, moreThanMinDaysAway);
		
		myVolunteer.signup(aJob);
		
		assertEquals(1, myVolunteer.signup(otherJob));
	}
	
	@Test
	public void signup_AnEndConflictsOtherStart_1() {
		Job aJob = new Job("", "", minDaysAway, moreThanMinDaysAway);
		
		Job otherJob = new Job("", "", moreThanMinDaysAway, muchMoreThanMinDaysAway);
		
		myVolunteer.signup(aJob);
		
		assertEquals(1, myVolunteer.signup(otherJob));
	}
	
	
	@Test
	public void removeJob_JobStartsToday_2() {
		Job aJob = new Job("", "", today, today);
		
		myVolunteer.signupLocal(aJob);
		
		assertEquals(2, myVolunteer.removeJob(aJob));
	}
	
	@Test
	public void removeJob_MultiDayJobStartsBeforeToday_2() {
		Job aJob = new Job("", "", yesterday, tomorrow);

		myVolunteer.signupLocal(aJob);
		
		assertEquals(2, myVolunteer.removeJob(aJob));
	}
	
	@Test
	public void removeJob_JobStartsMoreThanMinDaysAway_0() {
		Job aJob = new Job("", "", moreThanMinDaysAway, moreThanMinDaysAway);

		myVolunteer.signup(aJob);
		
		assertEquals(0, myVolunteer.removeJob(aJob));
	}

	@Test
	public void removeJob_JobStartsExactlyMinDaysAway_0() {
		Job aJob = new Job("", "", minDaysAway, minDaysAway);

		myVolunteer.signup(aJob);
		
		assertEquals(0, myVolunteer.removeJob(aJob));
	}
}