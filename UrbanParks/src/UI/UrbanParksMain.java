package UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Job;

public class UrbanParksMain {

	public static void main(String[] args) throws FileNotFoundException {
		
		// Welcome Message, and the prompt for username
		
		System.out.println("Welcome to Urban Parks!");
		
		System.out.println("In order to proceed, please enter your username: ");

		// Stores the entered username within theUsername field
		
		Scanner theScanner = new Scanner(System.in);
		
		String theUsername = theScanner.nextLine();
		
		// Next several lines of code searches the records for username match with the database
		
		// Please put in the appropriate file path to the user list here
		
		Scanner usernameList = new Scanner(new File("/JUNIT_WORKSPACE/Test/src/storage1/userList.csv"));
		
		usernameList.useDelimiter(",|\n");
		
		// tempCounter is used to identify the current String 'type'. This is done by dividing the value by 3.
		// If remainder == 0, it's a username. If remainder == 1, it's a user type. If remainder == 2, it's a name.
		
		int tempCounter = 0;
		
		// accessType stores the type of the user. 1 for Volunteer, 2 for Park Manager, 3 for Staff. 0 for invalid.
		
		int accessLevel = 0;
		
		// realName stores the real name of the user in question. Empty initialization so Java won't complain.
		
		String realName = "";
		
		// usernameExists is basically a flag that checks whether the entered username exists or not.
		
		boolean usernameExists = false;
		
		// The while loop iterates through the database to find a match.
		
		boolean exitLoop = false;
		
		while(usernameList.hasNext() && exitLoop == false) {
			
			String currentEntry = usernameList.next();
			
	//		System.out.println("Test " + tempCounter + " " + currentEntry);
			
			if (tempCounter%3 == 0) {
				
			//	System.out.println(currentEntry);
				
				if (currentEntry.equals(theUsername)) {
					
					// System.out.println("Whee?");
					
					usernameExists = true;
					
					String tempAccessLevel = usernameList.next();
					tempCounter++;
					
					// System.out.println(tempAccessLevel);
					
					if (tempAccessLevel.equals("Volunteer")) { accessLevel = 1; }
					if (tempAccessLevel.equals("Park Manager")) { accessLevel = 2; }
					if (tempAccessLevel.equals("Staff")) { accessLevel = 3; }
					
					realName = usernameList.next();
					tempCounter++;
					exitLoop = true;
					
				}
				
			}
			
			tempCounter++;
            
        } 
		
		usernameList.close();
		
		// firstResponse is the number that the user types in at the first prompt.
		
		int firstResponse = 0;
		int secondResponse = 0;
		int thirdResponse = 0;
		
		if (usernameExists == true) {
			
			System.out.println("Your username has been identified, " + realName);
			System.out.println("What would you like to do today? Type in a number based on your preference:");
			System.out.println("1) Sign out");
			System.out.println("2) Sign up for a new job");
			System.out.println("3) View your current jobs");
			
			// Scanner theNewScanner = new Scanner(System.in);
			
			firstResponse = theScanner.nextInt();
			// theScanner.close();
			
		}
		
		// An ArrayList of an ArrayList to hold all the contents of the database
		
		ArrayList<ArrayList<String>> jobData = new ArrayList<ArrayList<String>>();
		
		if (firstResponse == 2) {
			
			System.out.println("You have chosen to sign up for a new job.");
			System.out.println("Here are the pending jobs. Type in a number to identify them:");
			System.out.println("1) Go back to main menu");
			
			// Repurposed tempCounter to help print out the next several options
			
			tempCounter = 2;
			
			Scanner pendingJobsList = new Scanner(new File("/JUNIT_WORKSPACE/Test/src/storage1/pendingJobsList.csv"));
			
			// System.out.println("Okay!");
			
			pendingJobsList.useDelimiter(",|\n");
			
			// Iterate through the headers in the csv file because we don't want them
			
			for (int i = 1; i <= 8; i++) { pendingJobsList.next(); }
			
			// System.out.println(pendingJobsList.next());
			
			// Print out the jobs in the csv file
			
			while(pendingJobsList.hasNext()) {
				
				ArrayList<String> thisJob = new ArrayList<String>();
				
				for (int k = 1; k <= 8; k++) { thisJob.add(pendingJobsList.next()); }
				jobData.add(thisJob);
				
				System.out.print(tempCounter + ") ");
				System.out.println("\"" + thisJob.get(0) + ": " + thisJob.get(2) + "/" + thisJob.get(3) + "/" + thisJob.get(4) + "\"");
				tempCounter++;
				
			}
			
			pendingJobsList.close();
			
			secondResponse = theScanner.nextInt();
			
			// Go back two indexes due to how JobData stores the information
			
			tempCounter = secondResponse - 2;
		//	System.out.println(tempCounter);
			
		}
		
		// System.out.println(jobData.get(0).get(0));
		
		String dateFormat = jobData.get(tempCounter).get(2) + "/" + jobData.get(tempCounter).get(3) + "/" + jobData.get(tempCounter).get(4);
		
		System.out.print(jobData.get(tempCounter).get(0) + " ");
		System.out.println(dateFormat);
		System.out.println("Job Descrption: " + jobData.get(tempCounter).get(1));
		System.out.println("Start Time: " + dateFormat + ", ");
		System.out.println("End Time: " + dateFormat + ", ");
		System.out.println("Location: " + "Dunnolol");
		System.out.println("Signup Status: " + "Ehh");

	}

}