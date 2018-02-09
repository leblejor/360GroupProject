package UI;

import java.text.ParseException;
import java.util.Scanner;

import model.Job;
import model.User;
import model.Volunteer;

public class UrbanParksMain {

	public static void main(String[] args) throws ParseException {
		
		Volunteer test = new Volunteer("shark12", "Derick");
		Job testJob = new Job();
		testJob.setName("Test Job");
		testJob.setStartDate(2, 10, 2018);
		testJob.setEndDate(2, 10, 2018);
		testJob.setStartTime(6, 00);
		testJob.setEndTime(9, 00);
		
		Job testJob2 = new Job();
		testJob2.setName("Test Job");
		testJob2.setStartDate(3, 9, 2018);
		testJob2.setEndDate(3, 9, 2018);
		testJob2.setStartTime(6, 00);
		testJob2.setEndTime(9, 00);
		
		boolean t = test.signup(testJob);
		boolean t2 = test.signup(testJob2);
		System.out.println(t);
		System.out.println(t2);
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to Urban Parks");
		System.out.print("Enter your username: ");
		
		
	}
	
	public String identifyUser(String theUsername) {
		return "hi";
	}

}
