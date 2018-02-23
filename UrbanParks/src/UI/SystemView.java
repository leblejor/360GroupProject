package UI;

import java.lang.Integer;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import model.Job;
import model.Staff;

/**
 * Interacts with the user. A user may be a Staff member, ParkManager, or Volunteer.
 * Users are given a different set of actions to carry out depending on the type of User.
 * A Staff member has no actions available to carry out other than sign out.
 * A ParkManager can sign out, create a Job, or view their existing Jobs.
 * A Volunteer can sign out, sign up for a Job, or view their existing Jobs.
 * 
 * @author Jordan LeBle
 * @author Emerson Millard
 * @version 11 February 2018
 */
public class SystemView {
	private static boolean signedIn;
	private static model.UrbanParksSystem ups;
	private static Scanner input = new Scanner(System.in);
	
	/** Main runs program to completion. */
	public static void main(String[] args) {
		signedIn = false;
		ups = new model.UrbanParksSystem();
		
		System.out.println("Welcome to Urban Parks!");
		model.User user = login(ups);
		
		while(signedIn) {
			if (user == null) {
				System.out.println("We cannot find the username you've entered");
				System.out.println("Please try again");
				System.out.println("----------------------------------------------------\n");
				user = login(ups);
			
			} else if(user.getDescription().equals("Volunteer")) {
				volunteerAction((model.Volunteer) user);
			} else if(user.getDescription().equals("Park Manager")) {
				parkManagerAction((model.ParkManager) user);
			} else if(user.getDescription().equals("Staff")) {
				staffAction();
			}
			
			if(!signedIn) {
				System.out.println("See you next time!");
				System.out.println("----------------------------------------------------\n");
				System.out.println("Welcome to Urban Parks!");
				user = login(ups);
			}
			
		}
		System.out.println("Program terminated.");
		
		ups.saveJobs();
		ups.saveUsers();
		
		input.close();
	}
	
	/**
	 * Displays the actions available to the Volunteer. User is prompted to select an option.
	 * A Staff member has no actions available to carry out other than sign out.
	 */
	private static void staffAction() {
		System.out.println("----------------------------------------------------");
		System.out.println("STAFF MENU\n");
		System.out.println("What would you like to do today?");
		System.out.println("1) Sign out");
		System.out.print("Enter a number to decide what action you wish to take: ");
		
		input.nextLine();
		System.out.println("");
		
		signedIn = false;
	}
	
	/**
	 * Prompts the user for a user name and attempts to sign in with that user name. 
	 * When the user is signed in successfully, a welcome message is displayed. 
	 * Inputing '1' will exit.
	 * 
	 * @param ups the model to run and store variables.
	 * @return the User that was logged in.
	 */
	private static model.User login(model.UrbanParksSystem ups) {
		System.out.print("Please enter your username (enter '1' to exit): ");
		
		model.User user;
		String userName = input.nextLine();
		System.out.println("");
		

		if(!userName.equals("1")) {
			user = ups.signIn(userName);
			
			// username not found
			if (user == null) {
				signedIn = true;
				return null;
			}
			
			System.out.println("Welcome, " + user.getDescription() + " " + user.getName() + ".");
			signedIn = true;
		} else {
			signedIn = false;
			user = null;
		}

		return user;
	}
	
	/**
	 * Displays the actions available to the Volunteer. User is prompted to select an option.
	 * A Volunteer can sign out, sign up for a Job, or view their existing Jobs.
	 * 
	 * @param theVolunteer the Volunteer making an action.
	 */
	private static void volunteerAction(model.Volunteer theVolunteer) {
		System.out.println("----------------------------------------------------");
		System.out.println("VOLUNTEER MENU\n");
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

	/**
	 * Prompts the Volunteer to select a Job to sign up for. The Job will either
	 * be signed up for successfully or an error message is displayed based on the selection. 
	 * 
	 * @param theVolunteer Volunteer signing up for a Job.
	 */
	private static void volunteerSignupJob(model.Volunteer theVolunteer) {
		System.out.println("----------------------------------------------------");
		System.out.println("LIST OF AVAILABLE PENDING JOBS\n");
		System.out.println("Here are the pending jobs.");
		System.out.println("1) Go back to main menu");
		
		int selectionOffset = 2;
		Set<model.Job> jobListCopy = ups.getPendingJobsCollection();
		Set<model.Job> volunteerJobList = theVolunteer.getJobsList();
		
		// Remove all Jobs in the list that the user has already signed up for
		for(model.Job signedUp : volunteerJobList) {
				jobListCopy.remove(signedUp);	
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

		if(theSelectedOption > 0) { // on input of 1, exit
			int success = theVolunteer.signup(jobListCopy.get(theSelectedOption));
			
			if(success == 0) {
				System.out.println("You've successfully signed up for a job!");
			} else if(success == 1) {
				System.out.println("Unable to sign up for job.");
				System.out.println("You cannot sign up for two jobs that are on the same day.");
			} else if(success == 2) {
				System.out.println("Unable to sign up for job.");
				System.out.println("You can only sign up for jobs that begin " + 
									Staff.getMinSignUpDays() + " or more days from today.");
			}
		}
		
		
	}
	
	/**
	 * Displays the Volunteer's pending Jobs to the console in a numbered list.
	 * 
	 * @param theVolunteer Volunteer to display their pending Jobs for.
	 */
	private static void volunteerViewCurrentJobs(model.Volunteer theVolunteer) {
		
		System.out.println("----------------------------------------------------");
		System.out.println("VOLUNTEER LIST OF CURRENT JOBS\n");
		System.out.println("Here are your current jobs: ");
				
		int count = 1;
		for(model.Job aJob : theVolunteer.getJobsList()) {
			System.out.println(count + ") " + aJob.toString());
			count++;
		}
		System.out.println("");
	}
	
	/**
	 * Displays the actions available to the ParkManager. User is prompted to select an option.
	 * A ParkManager can sign out, create a Job, or view their existing Jobs.
	 * 
	 * @param theParkManager the ParkManager making an action.
	 */
	private static void parkManagerAction(model.ParkManager theParkManager) {
		System.out.println("----------------------------------------------------");
		System.out.println("PARK MANAGER MENU\n");
		System.out.println("What would you like to do today?");
		System.out.println("1) Sign out");
		System.out.println("2) Create a new job");
		System.out.println("3) View your current jobs");
		System.out.print("Enter a number to decide what action you wish to take: ");
		
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
	
	/**
	 * Prompts the ParkManager to input information to create a Job. The Job will either
	 * be created or display an error message based on the validity of these inputs. 
	 * 
	 * @param theParkManager the ParkManager creating the Job.
	 */
	private static void parkManagerCreateJob(model.ParkManager theParkManager) {
		System.out.println("----------------------------------------------------");
		System.out.println("PARK MANAGER CREATE A JOB\n");
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
		Job theJob = new Job("", theJobTitle, startMonth, startDay, startYear, endMonth, endDay, endYear);
		int success = theParkManager.createJob(ups, theJob);

		if(success == 0) {
			System.out.println("Job successfully created!");
		} else if(success == 1) {
			System.out.println("Error - Could not create job.");
			System.out.println("There are already the maximum (" + 
						Staff.getMaxPendingJobs() + ") number of jobs in the system.");
		} else if(success == 2) {
			System.out.println("Error - Could not create job.");
			System.out.println("The duration of the job is greater than the maximum (" + 
						Staff.getMaxJobLength() + " days) allowed.");
		} else if(success == 3) {
			System.out.println("Error - Could not create job.");
			System.out.println("The job would be scheduled too far (" +  
						Staff.getMaxScheduleWindow() +  " days) in advance");
		}
		
		System.out.println("");
	}
	
	/**
	 * Displays the ParkManager's Jobs to the console in a numbered list.
	 * 
	 * @param theParkManager ParkManager to display their Jobs for.
	 */
	private static void parkManagerViewCurrentJobs(model.ParkManager theParkManager) {
		System.out.println("----------------------------------------------------");
		System.out.println("PARK MANAGER LIST OF CURRENT JOBS\n");
		System.out.println("Here are your current jobs: ");
		
		int count = 1;
		for(model.Job aJob : theParkManager.getJobsList()) {
			System.out.println(count + ") " + aJob.toString());
			count++;
		}
		System.out.println("");
	}
}