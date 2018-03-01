package model;

/**
 * Represents a User. User holds a username, name, and type of user.
 * 
 * @version 11 February 2018
 */
public abstract class User implements java.io.Serializable {
	/** SerialID for storage */
	private static final long serialVersionUID = 1L;
	
	private String myUsername;
	private String myName;
	private String myUserType;

	public User(final String theUsername, final String theName, final String theUserType) {
		myUsername = theUsername;
		myName = theName;
		myUserType = theUserType;
		
	}
	
	/*********** Getters ***********/
	public String getDescription() {
		return myUserType;
	}
	
	public String getUsername() {
		return myUsername;
	}
	
	public String getName() {
		return myName;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(myUsername + "-" + myUserType + "-" + myName);
		
		return str.toString();
	}
}
