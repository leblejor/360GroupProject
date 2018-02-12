package model;

/**
 * Represents a Staff member. 
 * 
 * @version 11 February 2018
 */
public class Staff extends User {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;

	public Staff(String theUsername, String theName) {
		super(theUsername, theName, "Staff");
	}

}
