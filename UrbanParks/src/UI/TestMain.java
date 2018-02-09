package UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Job;

public class UrbanParksMain {

	public static void main(String[] args) throws FileNotFoundException {
		model.UrbanParksSystem ups = new model.UrbanParksSystem();
		
		model.User user = login(ups);
		
		if(user.getDescription().equals("Volunteer")) {
			// Don't print jobs that the user has already signed up for
		} else if(user.getDescription().equals("Park Manager")) {
			
		}
		
		
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
	
	private static model.User login(model.UrbanParksSystem ups) {
		System.out.println("Welcome to Urban Parks!");
		System.out.println("In order to proceed, please enter your username: ");
		
		Scanner sc = new Scanner(System.in);
		
		ups.signIn(sc.nextLine());
		
		sc.close();
	}

}