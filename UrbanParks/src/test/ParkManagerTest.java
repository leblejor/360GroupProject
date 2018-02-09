package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParkManagerTest {	
	
	@Test
	public void Job_DurationFewerThanMax_Success() {
		// Attempt to create a job such that the job length is less than the max job length
		model.Job x = new model.Job();
		int maxJobLength = x.getMaxJobLength();
		new model.Job("Short Job", "", 1, 1, 2018, 
							1, 1 + maxJobLength - 1, 2018);
	}

	@Test
	public void Job_DurationEqualToMax_Success() {
		// Attempt to create a job such that the job length is equal to the max job length
		model.Job x = new model.Job();
		int maxJobLength = x.getMaxJobLength();
		new model.Job("Max Job", "", 1, 1, 2018, 
							1, 1 + maxJobLength, 2018);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Job_DurationGreaterThanMax_IllegalArgumentException() {
		// Attempt to create a job such that the job length is more than the max job length
		model.Job x = new model.Job();
		int maxJobLength = x.getMaxJobLength();
		new model.Job("Long Job", "", 1, 1, 2018, 
							1, 1 + maxJobLength + 1, 2018);
	}

}
