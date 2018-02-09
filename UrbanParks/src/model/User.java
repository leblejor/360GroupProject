package model;


import java.util.List;


public abstract class User implements java.io.Serializable {

	private String myUsername;
	private String myName;
	private String myUserType;
	
	public User(final String theUsername, final String theName, final String theUserType) {
		myUsername = theUsername;
		myName = theName;
		myUserType = theUserType;
	}
	
	public String getDescription() {
		return myUserType;
	}
	
	public String getUsername() {
		return myUsername;
	}
	
	public String getName() {
		return myName;
	}

}
