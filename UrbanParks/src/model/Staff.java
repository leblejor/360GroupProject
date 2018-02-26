package model;

/**
 * Represents a Staff member. 
 * 
 * @author Jordan LeBle
 * @version 11 February 2018
 */
public class Staff extends User {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;
	
	public Staff(String theUsername, String theName) {
		super(theUsername, theName, "Staff");
	}
	
	public int setMaxPendingJobs(UrbanParksSystem ups, int theMaxJobs) {
		int success = 0;
		int zeroInvalid = 1;
		int negativeInvalid = 2;
		
		if(theMaxJobs == 0) {
			return zeroInvalid;
		} else if(theMaxJobs < 0) {
			return negativeInvalid;
		} else {
			ups.setMaxPendingJobs(theMaxJobs);
			return success;
		}
		
	}
	
	public int setMaxPendingJobsLocal(int theMaxJobs) {
		int success = 0;
		int zeroInvalid = 1;
		int negativeInvalid = 2;
		
		if(theMaxJobs == 0) {
			return zeroInvalid;
		} else if(theMaxJobs < 0) {
			return negativeInvalid;
		} else {
			return success;
		}
		
	}

}
