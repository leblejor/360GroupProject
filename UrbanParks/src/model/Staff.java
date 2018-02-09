package model;

public class Staff extends User {

	public Staff(String theUsername, String theName) {
		super(theUsername, theName);
	}

	@Override
	public String getDescription() {
		return "Staff";
	}

}
