package UI;

//import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.lang.Integer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import model.Job;
import model.ParkManager;
import model.User;
import model.Volunteer;


public class TestMain {
	private static boolean signedIn;
	private static model.UrbanParksSystem ups;
	
	public static void main(String[] args) throws FileNotFoundException, ParseException {
		signedIn = false;
		ups = new model.UrbanParksSystem();
		
		System.out.println("Welcome to Urban Parks!");
		model.User user = login(ups);
		
		while(signedIn) { // How to exit?
			if(user.getDescription().equals("Volunteer")) {
				volunteerAction((Volunteer) user);
			} else if(user.getDescription().equals("Park Manager")) {
				parkManagerAction((ParkManager) user);
			}
			
			if(!signedIn) {
				login(ups);
			}
		}
		
		ups.saveJobs(ups.getPendingJobs());
		ups.saveUsers(ups.getUsers());

	}
	
	private static model.User login(model.UrbanParksSystem ups) {
		System.out.println("Please enter your username (enter '1' to exit): ");
		
		Scanner sc = new Scanner(System.in);
		
		model.User user;
		String userName = sc.nextLine();
		user = ups.signIn(userName);
		
		if(!userName.equals("1")) {
			signedIn = true;
		} else {
			user = null;
		}
		
		sc.close();
		
		return user;
	}
	

	private static void volunteerAction(model.Volunteer theVolunteer) throws ParseException {
		System.out.println("Welcome, Volunteer " + theVolunteer.getName() + ".");
		System.out.println("What would you like to do today? Enter a number to decide what action you wish to take.");
		System.out.println("1) Sign out");
		System.out.println("2) Sign up for a new job");
		System.out.println("3) View your current jobs");

		Scanner theScanner = new Scanner(System.in);
		int theSelectedOption = theScanner.nextInt();
		theScanner.close();

		 if (theSelectedOption == 2) {
			 volunteerSignupJob(theVolunteer);
		}else if (theSelectedOption == 3) {
			volunteerViewCurrentJobs(theVolunteer);
		} else {
			signedIn = false;
		}
	}

	// Method acts as the screen that volunteer users will see when they sign up for a job.
	private static void volunteerSignupJob(model.Volunteer theVolunteer) throws ParseException {
		int selectionOffset = 2;

		System.out.println("Here are the pending jobs. Enter a number to identify them:");
		System.out.println("1) Go back to main menu");
		
		
		List<Job> jobListCopy = new ArrayList<Job>(ups.getPendingJobs().getPendingJobsList());
		List<Job> volunteerJobList = theVolunteer.getJobsList();
		
		// Remove all Jobs in the list that the user has already signed up for
		for(Job signedUp : volunteerJobList) {
			if(jobListCopy.contains(signedUp)) {
				jobListCopy.remove(signedUp);
			}
		}
		
		// Display all Jobs that Volunteer hasn't already signed up for
		int count = selectionOffset;
		for(Job aJob  : jobListCopy) {
			System.out.println(count + ") " + aJob.toString());
			count++;
		}
		
		Scanner theScanner = new Scanner(System.in);
		int theSelectedOption = theScanner.nextInt() - selectionOffset;
		theScanner.close();
		
		int success = theVolunteer.signup(jobListCopy.get(theSelectedOption));
		if(success == 0) { // Successful sign up
			System.out.println("You've successfully signed up for a job!");
		} else if(success == 1) {
			System.out.println("Unable to sign up for job.");
			System.out.println("You cannot sign up for two jobs that are on the same day.");
		} else if(success == 2) {
			System.out.println("Unable to sign up for job.");
			System.out.println("You can only sign up for jobs that begin " + 
								theVolunteer.getMinSignupDays() + " or more days from today.");
		}
	}
	
	// Method acts as the screen volunteers will see when they choose to view their current jobs.
	private static void volunteerViewCurrentJobs(model.Volunteer theVolunteer) throws ParseException {
		System.out.println("Here are your current jobs: ");
				
		int count = 1;
		for(Job aJob : theVolunteer.getJobsList()) {
			System.out.println(count + ") " + aJob.toString());
			count++;
		}
	}
	
	// Method acts as the screen that park managers will see when they log in.
	private static void parkManagerAction(model.ParkManager theParkManager) throws ParseException {
		System.out.println("Welcome, Park Manager " + theParkManager.getName() + ".");
		System.out.println("What would you like to do today? Enter in a number to decide what action you wish to take.");
		System.out.println("1) Sign out");
		System.out.println("2) Create a new job");
		System.out.println("3) View your current jobs");

		Scanner theScanner = new Scanner(System.in);
		int theSelectedOption = theScanner.nextInt();
		theScanner.close();

		if (theSelectedOption == 2) {
			parkManagerCreateJob(theParkManager);
		}else if (theSelectedOption == 3) {
			parkManagerViewCurrentJobs(theParkManager);
		} else {
			signedIn = false;
		}
	}
	
	// Method acts as the screen park managers will see when they choose to create new jobs.
	private static void parkManagerCreateJob(model.ParkManager theParkManager) throws ParseException {
		// The user will be prompted to enter information necessary for the creation of a new Job object.
		System.out.println("You have chosen to create a new job.");
		System.out.println("Please enter the following required Job Information: ");
		
		Scanner theScanner = new Scanner(System.in);
		
		System.out.print("Job Title: ");
		String theJobTitle = theScanner.nextLine();
		//System.out.println();
		
		System.out.print("Start Date (mm/dd/yyyy format): ");
		String theStartDate = theScanner.nextLine();
		//System.out.println();
		
		System.out.print("End Date (mm/dd/yyyy format): ");
		String theEndDate = theScanner.nextLine();
		//System.out.println();
		
		theScanner.close();
		
		int startMonth = Integer.parseInt(theStartDate.substring(0, 2));
		int startDay = Integer.parseInt(theStartDate.substring(3, 5));
		int startYear = Integer.parseInt(theStartDate.substring(6, 10));
		
		int endMonth = Integer.parseInt(theEndDate.substring(0, 2));
		int endDay = Integer.parseInt(theEndDate.substring(3, 5));
		int endYear = Integer.parseInt(theEndDate.substring(6, 10));
		
		theParkManager.createJob(ups, theJobTitle, startMonth, startDay, startYear, endMonth, endDay, endYear);
		
		//TODO Error handling with business rules
	}
	
	private static void parkManagerViewCurrentJobs(model.ParkManager theParkManager) throws ParseException {
		System.out.println("Here are your current jobs: ");
		
		int count = 1;
		for(Job aJob : theParkManager.getJobsList()) {
			System.out.println(count + ") " + aJob.toString());
			count++;
		}
	}
}