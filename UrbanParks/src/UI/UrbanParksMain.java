package UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Job;
import model.ParkManager;
import model.Volunteer;

public class UrbanParksMain {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		
		while (true) {
			
			// This variable is set to false when the user logs out later. 
			
			boolean stillLoggedIn = true;
		
			// The UrbanParkSystem class holds the list of user objects.
			// The logIn method will prompt the user for a username, and then return the user object if any.

			model.UrbanParksSystem theSystem = new model.UrbanParksSystem(); 
			model.User theUser = logIn(theSystem);
		
			// If the user is identified to be a volunteer, the volunteer method is referred to.

			if (theUser.getDescription().equals("Volunteer")) {
				
				// The volunteerInitialScreen will return true unless the user chooses to sign out, which breaks this loop.
			
				while (stillLoggedIn) { stillLoggedIn = volunteerInitialScreen((Volunteer) theUser); }

			}
			
			// If the user is identified to be a park manager, the park manager method is referred to.

			if (theUser.getDescription().equals("Park Manager")) {
							
				// The parkManagerInitialScreen will return true unless the user chooses to sign out, which breaks this loop.
						
				while (stillLoggedIn) { stillLoggedIn = parkManagerInitialScreen((ParkManager) theUser); }

			}

		}

	}

	// Method acts as the initial screen that users will come across when using the system.

	private static model.User logIn(model.UrbanParksSystem theSystem) {

		// The initial prompt message for the username.

		System.out.println("Welcome to Urban Parks!");
		System.out.println("In order to proceed, please enter your username: ");

		// The user will then type in a username, and the system will record it.

		Scanner theScanner = new Scanner(System.in);
		String theUsername = theScanner.nextLine();
		theScanner.close();

		// The UrbanParkSystem class will then take the String, and return the corresponding User object.

		return theSystem.signIn(theUsername);

	}

	// Method acts as the screen that volunteer users will see when they log in.

	private static boolean volunteerInitialScreen(model.Volunteer theVolunteer) throws ParseException {

		// The initial list of choices that volunteers will see.

		System.out.println("Welcome, Volunteer " + theVolunteer.getName() + ".");
		System.out.println("What would you like to do today? Type in a number to decide what action you wish to take.");
		System.out.println("1) Sign out");
		System.out.println("2) Sign up for a new job");
		System.out.println("3) View your current jobs");

		// The user will then type in a number, and the system will respond appropriately.

		Scanner theScanner = new Scanner(System.in);
		int theSelectedOption = theScanner.nextInt();
		theScanner.close();

		// If user chose to sign out, then the method will return a false, and the main method will deal with the rest.

		if (theSelectedOption == 1) {

			return false;

		}

		// If user chose to sign up for a new job, then volunteerSignupScreen will be called to deal with it.

		if (theSelectedOption == 2) {

			volunteerSignupScreenOne(theVolunteer);

		}
		
		// If user chose to view current jobs, then volunteerViewCurrentJobs will be called to deal with it.
		
		if (theSelectedOption == 3) {

			volunteerViewCurrentJobs(theVolunteer);
			return false;

		}

		return true;

	}

	// Method acts as the screen that volunteer users will see when they sign up for a job.

	private static void volunteerSignupScreenOne(model.Volunteer theVolunteer) throws ParseException {

		boolean stayOnThisScreen = true;

		while (stayOnThisScreen) {

			// The list of pending jobs that users can sign up for.
			
			System.out.println("You have chosen to sign up for a new job.");
			System.out.println("Here are the pending jobs. Type in a number to identify them:");
			System.out.println("1) Go back to main menu");

			// IMAGINE THAT AN ARRAY ITERATES THROUGH AN URBANPARKSYSTEMS METHOD AND PRINTS OUT ALL THE PENDING JOBS HERE

			Scanner theScanner = new Scanner(System.in);
			int theSelectedOption = theScanner.nextInt();
			theScanner.close();

			// IMAGINE THAT THE INT VALUE IS THROWN INTO AN URBANPARKSYSTEMS METHOD AND IT RETURNS THE SPECIFIC JOB

			model.Job placeholderJob = new Job();
			
			// This method will bring the user to the next screen, and ask them for confirmation.
			// If they confirm the sign up, the method will return true. Otherwise, it will be false.
			// If the return is true, then this method will be broken out of. 

			if (volunteerSignupScreenTwo(theVolunteer, placeholderJob)) {

				stayOnThisScreen = false;

			}

		}

	}

	// Method acts as the screen that volunteer users will see when they selected a job to sign up for.

	private static boolean volunteerSignupScreenTwo(model.Volunteer theVolunteer, model.Job theSelectedJob)
			throws ParseException {

		// The relevant details of the job.
		
		System.out.println("Job Title:" + theSelectedJob.getJobName());
		System.out.println("Start Date: " + theSelectedJob.getStartDate());
		// System.out.println("Job Description: " + theSelectedJob.getDescription()); NOT YET IMPLEMENTED
		// MAYBE HAVE A METHOD THAT RETURNS TODAY'S DATE IN THE FORM A STRING

		// System will ask the user for one final confirmation before proceeding. 
		
		System.out.println("Are you sure you wish to sign up for this job?");
		System.out.println("Type in a number to decide what action you wish to take.");
		System.out.println("1) Go back to the Job Screen");
		System.out.println("2) Confirm sign up for this job");

		// The user will then type in a number, and the system will respond appropriately.

		Scanner theScanner = new Scanner(System.in);
		int theSelectedOption = theScanner.nextInt();
		theScanner.close();

		// If going back to the Job Screen is selected, method returns a false so that the previous method is repeated.

		if (theSelectedOption == 1) {

			return false;

		}

		// If the user confirms the signing up of the job, then use the sign up method through the Volunteers class.
		// This will lead to the return being true, which brings the user back to the initial Volunteer screen.

		if (theSelectedOption == 2) {

			theVolunteer.signup(theSelectedJob);
			System.out.println("You've successfully signed up for a job!");

		}

		return true;

	}
	
	// Method acts as the screen volunteers will see when they choose to view their current jobs.
	// CANCELLING JOBS NOT PART OF DELIVERABLE 2 REQUIREMENTS, SO I GLOSSED OVER THAT.
	
	private static void volunteerViewCurrentJobs(model.Volunteer theVolunteer) throws ParseException {

		System.out.println("Here are your current jobs: ");
		
		// This temporary field will help to iterate through the Volunteer object's personal Job List.
		
		int iterateThroughVolunteerCurrentJobs = 0;
		
		// All jobs associated with the Volunteer are then printed out.
		
		while (theVolunteer.getJobsList().get(iterateThroughVolunteerCurrentJobs) != null) {
			
			model.Job theJob = new model.Job();
			theJob = theVolunteer.getJobsList().get(iterateThroughVolunteerCurrentJobs);
			System.out.println(iterateThroughVolunteerCurrentJobs + 1 + ") " + theJob.getJobName());
			
		}

	}
	
	// Method acts as the screen that park managers will see when they log in.

		private static boolean parkManagerInitialScreen(model.ParkManager theParkManager) throws ParseException {

			// The initial list of choices that park managers will see.

			System.out.println("Welcome, Park Manager " + theParkManager.getName() + ".");
			System.out.println("What would you like to do today? Type in a number to decide what action you wish to take.");
			System.out.println("1) Sign out");
			System.out.println("2) Create a new job");
			System.out.println("3) View your current jobs");

			// The user will then type in a number, and the system will respond appropriately.

			Scanner theScanner = new Scanner(System.in);
			int theSelectedOption = theScanner.nextInt();
			theScanner.close();

			// If user chose to sign out, then the method will return a false, and the main method will deal with the rest.

			if (theSelectedOption == 1) {

				return false;

			}

			// If user chose to create a new job, then parkManagerCreateJobScreen will be called to deal with it.

			if (theSelectedOption == 2) {

				parkManagerCreateJobScreen(theParkManager);

			}
			
			// If user chose to view current jobs, then parkManagerViewCurrentJobs will be called to deal with it.
			
			if (theSelectedOption == 3) {

				parkManagerViewCurrentJobs(theParkManager);
				return false;

			}

			return true;

		}
		
		// Method acts as the screen park managers will see when they choose to create new jobs.
		
		private static void parkManagerCreateJobScreen(model.ParkManager theParkManager) throws ParseException {
			
			// The user will be prompted to enter information necessary for the creation of a new Job object.
			
			System.out.println("You have chosen to create a new job.");
			System.out.println("Please enter the following required Job Information: ");
			
			Scanner theScanner = new Scanner(System.in);
			
			System.out.print("Job Title: ");
			String theJobTitle = theScanner.nextLine();
			System.out.println();
			
			System.out.print("Job Description: ");
			String theJobDescription = theScanner.nextLine();
			System.out.println();
			
			System.out.print("Start Date (mm/dd/yyyy format): ");
			String theStartDate = theScanner.nextLine();
			System.out.println();
			
			System.out.print("End Date (mm/dd/yyyy format): ");
			String theEndDate = theScanner.nextLine();
			System.out.println();
			
			// The system takes the Start/End Date string apart so that the appropriate int values
			// can be passed into the job constructor later.
			
			int startMonth = Character.getNumericValue(theStartDate.charAt(0)) * 10 + 
					Character.getNumericValue(theStartDate.charAt(1));
			
			int startDay = Character.getNumericValue(theStartDate.charAt(3)) * 10 + 
					Character.getNumericValue(theStartDate.charAt(4));
			
			int startYear = Character.getNumericValue(theStartDate.charAt(6)) * 1000 + 
					Character.getNumericValue(theStartDate.charAt(7)) * 100 +
							Character.getNumericValue(theStartDate.charAt(8)) * 10 +
								Character.getNumericValue(theStartDate.charAt(9));
			
			int endMonth = Character.getNumericValue(theEndDate.charAt(0)) * 10 + 
					Character.getNumericValue(theEndDate.charAt(1));
			
			int endDay = Character.getNumericValue(theEndDate.charAt(3)) * 10 + 
					Character.getNumericValue(theEndDate.charAt(4));
			
			int endYear = Character.getNumericValue(theEndDate.charAt(6)) * 1000 + 
					Character.getNumericValue(theEndDate.charAt(7)) * 100 +
							Character.getNumericValue(theEndDate.charAt(8)) * 10 +
								Character.getNumericValue(theEndDate.charAt(9));
			
			// The system asks for one last confirmation about whether they want to make any changes.
			
			System.out.println("Do you want to edit any of these fields? (Y/N)");
			
			String confirmCreation = theScanner.nextLine();
			
			// NOT ENTIRELY SURE WHAT TO DO HERE. ADD TO CALENDAR? IN THAT CASE, DOES UI CREATE
			// A CALENDAR OBJECT EARLY ON? I'LL LEAVE IT HERE FOR NOW.
			
			if (confirmCreation.equals("N")) {
				
				Job newJob = new Job(theJobTitle, theJobDescription, 
						   startMonth, startDay, startYear,
						   endMonth, endDay, endYear);
				
				System.out.println("A job has been successfully created.");
				
			}
			
			// The system repeatedly asks for changes until the user is satisfied here.
			
			if (confirmCreation.equals("Y")) {

				boolean stayOnThisScreen = true;
				
				while (stayOnThisScreen) {
				
				System.out.println("What do you wish to change?");
				System.out.println("Type in a number to decide what change you wish to make.");
				System.out.println("1) Job Title");
				System.out.println("2) Job Description: ");
				System.out.println("3) Start Month (mm)");
				System.out.println("4) Start Day (dd)");
				System.out.println("5) Start Year (yyyy)");
				System.out.println("6) End Month (mm)");
				System.out.println("7) End Day (dd)");
				System.out.println("8) End Year (yyyy)");
				
				int valueToChange = theScanner.nextInt();
				
				System.out.println("Enter the new value for this information.");
				
				if (valueToChange == 1) { theJobTitle = theScanner.nextLine();; }
				if (valueToChange == 2) { theJobDescription = theScanner.nextLine();; }
				if (valueToChange == 3) { startMonth = theScanner.nextInt(); }
				if (valueToChange == 4) { startDay = theScanner.nextInt(); }
				if (valueToChange == 5) { startYear = theScanner.nextInt(); }
				if (valueToChange == 6) { endMonth = theScanner.nextInt(); }
				if (valueToChange == 7) { endDay = theScanner.nextInt(); }
				if (valueToChange == 8) { endYear = theScanner.nextInt(); }
				
				System.out.println("Change has been done successfully. Make any further changes? (Y/N)");
				
				String repeatMethod = theScanner.nextLine();
				if (repeatMethod.equals("N")) { 
					
					stayOnThisScreen = false;
					Job newJob = new Job(theJobTitle, theJobDescription, 
							   startMonth, startDay, startYear,
							   endMonth, endDay, endYear);
					System.out.println("A job has been successfully created.");
					
					}
				
				}
				
			}
			
			theScanner.close();
			
		}
		
		private static void parkManagerViewCurrentJobs(model.ParkManager theParkManager) throws ParseException {

			// PRETEND THAT THERE IS CODE HERE THAT USES SOME METHOD FROM THE PARKMANAGER CLASS
			// TO ITERATE THROUGH THEIR PERSONAL JOB LIST.

		}

}