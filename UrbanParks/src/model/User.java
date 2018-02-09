package model;


import java.util.List;


public class User implements java.io.Serializable {

	private String myUsername;
	private String myName;
	
	public User(final String theUsername, final String theName) {
		myUsername = theUsername;
		myName = theName;
	}
	
	//public abstract String getDescription();
	
	public String getUsername() {
		return myUsername;
	}
	
	public String getName() {
		return myName;
	}

}
