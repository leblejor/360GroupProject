package model;

public class User implements java.io.Serializable {
	private String myUsername;
	private String myName;
	
	public User(final String theUsername, final String theName) {
		myUsername = theUsername;
		myName = theName;
	}

}
