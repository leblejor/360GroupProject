package UI;

import java.lang.Integer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interacts with the user. A user may be a Staff member, ParkManager, or Volunteer.
 * Users are given a different set of actions to carry out depending on the type of User.
 * A Staff member has no actions available to carry out other than sign out.
 * A ParkManager can sign out, create a Job, or view their existing Jobs.
 * A Volunteer can sign out, sign up for a Job, or view their existing Jobs.
 * 
 * @author Jordan
 * @author Emerson 
 * @version 11 February 2018
 */
public class TestMain {
	private static boolean signedIn;
	private static model.UrbanParksSystem ups;
	private static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		signedIn = false;
		ups = new model.UrbanParksSystem();
		
		System.out.println("Welcome to Urban Parks!");
		model.User user = login(ups);
		
		/*
		for(Map.Entry<String, model.User> aUser : ups.getUsers().entrySet()) {
			System.out.println(aUser.getValue().getDescription() + " " + aUser.getKey());
		}
		*/
		
		
		while(signedIn) {
			if(user.getDescription().equals("Volunteer")) {
				volunteerAction((model.Volunteer) user);
			} else if(user.getDescription().equals("Park Manager")) {
				parkManagerAction((model.ParkManager) user);
			} else if(user.getDescription().equals("Staff")) {
				staffAction();
			}
			
			if(!signedIn) {
				user = login(ups);
			}
		}
		
		ups.saveJobs(ups.getPendingJobs());
		ups.saveUsers(ups.getUsers());
		
		input.close();
	}
	
	private static void staffAction() {
		System.out.println("What would you like to do today?");
		System.out.println("1) Sign out");
		System.out.print("Enter a number to decide what action you wish to take: ");
		
		input.nextLine();
		System.out.println("");
		
		signedIn = false;
	}
	
	private static model.User login(model.UrbanParksSystem ups) {
		System.out.print("Please enter your username (enter '1' to exit): ");
		
		model.User user;
		String userName = input.nextLine();
		System.out.println("");

		if(!userName.equals("1")) {
			user = ups.signIn(userName);
			System.out.println("Welcome, " + user.getDescription() + " " + user.getName() + ".");
			signedIn = true;
		} else {
			signedIn = false;
			user = null;
		}

		return user;
	}
	

	private static void volunteerAction(model.Volunteer theVolunteer) {
		System.out.println("What would you like to do today?");
		System.out.println("1) Sign out");
		System.out.println("2) Sign up for a new job");
		System.out.println("3) View your current jobs");
		System.out.print("Enter a number to decide what action you wish to take: ");

		int theSelectedOption = input.nextInt();
		input.nextLine(); // To pick up the '\n' character in scanner
		System.out.println("");

		 if (theSelectedOption == 2) {
			 volunteerSignupJob(theVolunteer);
		}else if (theSelectedOption == 3) {
			volunteerViewCurrentJobs(theVolunteer);
		} else {
			signedIn = false;
		}
	}

	// Method acts as the screen that volunteer users will see when they sign up for a job.
	private static void volunteerSignupJob(model.Volunteer theVolunteer) {
		System.out.println("Here are the pending jobs.");
		System.out.println("1) Go back to main menu");
		
		int selectionOffset = 2;
		List<model.Job> jobListCopy = new ArrayList<model.Job>(ups.getPendingJobs().getPendingJobsList());
		List<model.Job> volunteerJobList = theVolunteer.getJobsList();
		
		// Remove all Jobs in the list that the user has already signed up for
		for(model.Job signedUp : volunteerJobList) {
			if(jobListCopy.contains(signedUp)) {
				jobListCopy.remove(signedUp);
			}
		}
		
		// Display all Jobs that Volunteer hasn't already signed up for
		int count = selectionOffset;
		for(model.Job aJob  : jobListCopy) {
			System.out.println(count + ") " + aJob.toString());
			count++;
		}
		
		System.out.print("Enter a number to make your selection: ");
		int theSelectedOption = input.nextInt() - selectionOffset;
		input.nextLine(); // To pick up the '\n' character in scanner

		if(theSelectedOption > 0) { // input of 1 is selected, exit
			int success = theVolunteer.signup(jobListCopy.get(theSelectedOption));
			
			if(success == 0) {
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
		System.out.println("");
	}
	
	// Method acts as the screen volunteers will see when they choose to view their current jobs.
	private static void volunteerViewCurrentJobs(model.Volunteer theVolunteer) {
		System.out.println("Here are your current jobs: ");
				
		int count = 1;
		for(model.Job aJob : theVolunteer.getJobsList()) {
			System.out.println(count + ") " + aJob.toString());
			count++;
		}
		System.out.println("");
	}
	
	// Method acts as the screen that park managers will see when they log in.
	private static void parkManagerAction(model.ParkManager theParkManager) {
		System.out.println("What would you like to do today?");
		System.out.println("1) Sign out");
		System.out.println("2) Create a new job");
		System.out.println("3) View your current jobs");
		System.out.print("Enter a number to decide what action you wish to take: ");

		//List<model.Job> jobs = theParkManager.getJobsList();
		//jobs.clear();// = new ArrayList<model.Job>();
		
		int theSelectedOption = input.nextInt();
		input.nextLine(); // To pick up the '\n' character in scanner
		System.out.println("");
		
		if (theSelectedOption == 2) {
			parkManagerCreateJob(theParkManager);
		}else if (theSelectedOption == 3) {
			parkManagerViewCurrentJobs(theParkManager);
		} else {
			signedIn = false;
		}
	}
	
	// Method acts as the screen park managers will see when they choose to create new jobs.
	private static void parkManagerCreateJob(model.ParkManager theParkManager) {
		System.out.println("Please enter the following required Job Information: ");
		
		System.out.print("Job Title: ");
		String theJobTitle = input.nextLine();
		
		System.out.print("Start Date (mm/dd/yyyy format): ");
		String theStartDate = input.nextLine();
		
		System.out.print("End Date (mm/dd/yyyy format): ");
		String theEndDate = input.nextLine();
		
		int startMonth = Integer.parseInt(theStartDate.substring(0, 2));
		int startDay = Integer.parseInt(theStartDate.substring(3, 5));
		int startYear = Integer.parseInt(theStartDate.substring(6, 10));
		
		int endMonth = Integer.parseInt(theEndDate.substring(0, 2));
		int endDay = Integer.parseInt(theEndDate.substring(3, 5));
		int endYear = Integer.parseInt(theEndDate.substring(6, 10));
		
		int success = theParkManager.createJob(ups, theJobTitle, startMonth, startDay, startYear, endMonth, endDay, endYear);

		if(success == 0) {
			System.out.println("Job successfully created!");
		} else if(success == 1) {
			System.out.println("Error - Could not create job.");
			System.out.println("There are already the maximum (" + 
						theParkManager.getMaxPendingJobs() + ") number of jobs in the system.");
		} else if(success == 2) {
			System.out.println("Error - Could not create job.");
			System.out.println("The duration of the job is greater than the maximum (" + 
						theParkManager.getMaxJobLength() + " days) allowed.");
		} else if(success == 3) {
			System.out.println("Error - Could not create job.");
			System.out.println("The job would be scheduled too far (" +  
						theParkManager.getMaxJobDuration() +  " days) in advance");
		}
		
		System.out.println("");
	}
	
	private static void parkManagerViewCurrentJobs(model.ParkManager theParkManager) {
		System.out.println("Here are your current jobs: ");
		
		int count = 1;
		for(model.Job aJob : theParkManager.getJobsList()) {
			System.out.println(count + ") " + aJob.toString());
			count++;
		}
		System.out.println("");
	}
}