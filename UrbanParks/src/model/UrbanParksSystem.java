package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;


/**
 * Represents the Urban Parks System that the model will run on. 
 * Handles systematic functions that the UI needs such as signing in, loading and saving files
 * for persistent storage.
 * 
 * @author Bryan Santos
 * @author Jordan LeBle
 */
public class UrbanParksSystem {
	private static final String SERIALIZED_USERS_FILE = "./storage/serializedUser.ser";
	private static final String SERIALIZED_JOBS_FILE = "./storage/serializedJobs.ser";
	
	private Map<String, User> myUsers;
	private Map<String, Job> myPendingJobs; // TODO: Make this a HashSet.
	
	/** 
	 * Constructor for UrbanParksSystem. 
	 * Loads the set of Users and Jobs from previous save states. 
	 */
	public UrbanParksSystem() {		
		loadUsers();
		loadJobs();
	}
	
	/**
	 * Signs the user into the system. The username determines if the user is a 
	 * Volunteer, ParkManager, or Staff.
	 * 
	 * @param theUsername the username to sign in.
	 * @return the Volunteer/ParkManager/Staff user object based on their username
	 */
	public User signIn(String theUsername) {
		return myUsers.get(theUsername);
	}

	/**
	 * Adds theJob into the the serialized object myPendingJobs.
	 * 
	 * @param theJob the job to be added in the system.
	 */
	public void addJob(Job theJob) { // Should the check for MAX_PENDING_JOBS go here?
		myPendingJobs.put(theJob.getJobName(), theJob);
	}
	
	/**
	 * Removes theJob  from the serialized object myPendingJobs.
	 * 
	 * @param theJob the job to be removed from the system.
	 */
	public void removeJob(Job theJob) { // Make any checks here?
		myPendingJobs.remove(theJob.getJobName()); // Would rather remove from a set, not by name
	}
	
	/**
	 * Checks to see if the list of pending jobs is full or not, determined 
	 * by the Staff.getMaxPendingJobs(). The list of jobs cannot exceed the maximum number.
	 * 
	 * @return true if the job cannot be added to the system, false otherwise.
	 */
	public boolean areJobsInSystemFull() {
		return jobsInSystem() >= Staff.getMaxPendingJobs();
	}

	/**
	 * Returns the number of jobs in the system.
	 * 
	 * @return the number of jobs in the system.
	 */
	public int jobsInSystem() {
		return myPendingJobs.size();			
	}
	
	/**
	 * Returns a single Job found from a given Job's name.
	 * 
	 * @param string Name of Job to be found.
	 * @return Job with the given name as it's title. 
	 */
	private Job getSinglePendingJob(String string) {	
		return myPendingJobs.get(string);
	}
	
	/**
	 * Will return a set of jobs in the system within the given range. 
	 * The lower bound is exclusive, the upper bound is inclusive.
	 * 
	 * @param from exclusive lower bound to range.
	 * @param to inclusive upper bound to range.
	 * @return Set of all jobs in system within a range (exclusive-inclusive).
	 */
	public Set<Job> jobsWithinRange(Calendar from, Calendar to) {
		Set<Job> jobSet = new HashSet<Job>();
		
		// Handles special cases of equal dates since
		// before() and after() are both exclusive checks on dates
		from.add(Calendar.DATE, -1);
		to.add(Calendar.DATE, 1);
		
		for (Job myJob : myPendingJobs.values()) {
			Calendar myJobStart = myJob.getStartDate();
			Calendar myJobEnd = myJob.getEndDate();
			
			if (myJobStart.after(from) && myJobStart.before(to) &&
				myJobEnd.after(from) && myJobEnd.before(to)) {
				jobSet.add(myJob);
			}
		}

		return jobSet;
	}
	
	/**
	 * Compares the jobs in the system and theVolunteer's jobs and returns a set 
	 * of jobs that the volunteer can sign up for. Checks jobs that are already in
	 * the Volunteer job list and jobs that start too 
	 * 
	 * @param theVolunteer the volunteer to check job list.
	 * @return the set of valid jobs the volunteer can sign up for.
	 */
	public Set<Job> getVolunteerValidJobs(Volunteer theVolunteer) {
		Set<Job> validJobs = new HashSet<Job>();
		for(Job j : myPendingJobs.values()) { 
			if (j.isBeforeMinTimespan() || !theVolunteer.isConflict(j)) { 
				validJobs.add(j);
			} 
		}		
		return validJobs;
	}
	
	/*********** Getters & Setters ***********/
	
	public Set<Job> getPendingJobs() {
		Set<Job> jobSet = new HashSet<Job>(myPendingJobs.values());
		return jobSet;
	}

	/*********** Load & Save ***********/
	
	@SuppressWarnings("unchecked")
	private void loadUsers() {
		try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(SERIALIZED_USERS_FILE);
            ObjectInputStream in = new ObjectInputStream(file);
             
            // Method for deserialization of object
            myUsers = (Map<String, User>) in.readObject();
            
            in.close();
            file.close();
            
        } catch(IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
            
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadJobs() {
		try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(SERIALIZED_JOBS_FILE);
            ObjectInputStream in = new ObjectInputStream(file);
             
            // Method for deserialization of object
            myPendingJobs = (Map<String, Job>) in.readObject();
             
            in.close();
            file.close();
     
        } catch(IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
            
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void saveUsers() {
		try {
            // Reading the object from a file
            FileOutputStream file = new FileOutputStream(SERIALIZED_USERS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(file);
             
            // Method for serialization of object
            out.writeObject(myUsers);
             
            out.close();
            file.close(); 
     
        } catch(IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        } 
	}

	public void saveJobs() {
		try {
            // Reading the object from a file
            FileOutputStream file = new FileOutputStream(SERIALIZED_JOBS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(file);
             
            // Method for serialization of object
            out.writeObject(myPendingJobs);
             
            out.close();
            file.close();
     
        } catch(IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        }
	}
	
	
	
	/*****Initializing new Database from csv files *****/
	/* Should only be used once ever */
	
	public static void main(String[] args) {
		UrbanParksSystem system = new UrbanParksSystem(true);
		
		//System.out.println("users: " + system.myUsers);
		//System.out.println("jobs: " + system.myPendingJobs);
	}
	
	private Scanner inputFile;
	
	private static final String userListFile = "./storage/userList.csv";
	private static final String pendingJobsListFile = "./storage/pendingJobsList.csv";
	private static final String userJobsListFile = "./storage/userJobsList.csv";
	
	public UrbanParksSystem(boolean k) {
		myUsers = new HashMap<String, User>();
		myPendingJobs = new HashMap<String, Job>();
		//myCalendar = new Calendar();
		
		// read data from userList.csv and store it in myUsers
		readUserList();
		
		// read data from pendingJobsList.csv and create these jobs
		readPendingJobsList();
		
		// read data from userJobsList.csv and add these jobs to the user
		readUserJobsList();
		
		saveJobs();
		saveUsers();
	}
	
	private void readUserList() {
		try {
			File userFile = new File(userListFile);
			inputFile = new Scanner(userFile);

			// read past column headers
			inputFile.nextLine();
			
			while (inputFile.hasNextLine()) {
				String line = inputFile.nextLine();
				String[] parsedLine = line.split(",");
				User user = parseUserType(parsedLine[0], parsedLine[1], parsedLine[2]);
				myUsers.put(parsedLine[0], user);
			}
			inputFile.close();
		} catch (FileNotFoundException e) {
            e.printStackTrace();
		}
	}

	private User parseUserType(String theUserName, String theUserType, String theName) {
		switch (theUserType.toLowerCase()) {
			case "volunteer" : 
				return new Volunteer(theUserName, theName);
			case "park manager" :
				return new ParkManager(theUserName, theName);
			case "staff" :
				return new Staff(theUserName, theName);
			default:
				return null;
		}
	}
	
	private void readPendingJobsList() {
		try {
			File pendingJobsFile = new File(pendingJobsListFile);
			inputFile = new Scanner(pendingJobsFile);

			// read past column headers
			inputFile.nextLine();
			
			while (inputFile.hasNextLine()) {
				String line = inputFile.nextLine();
				String[] parsedLine = line.split(",");
				
				String jobName = parsedLine[0];
				String jobDescription = parsedLine[1];
			
				// Java's Calendar Months start at index 0 (0 -> January...)
				int startMonth = Integer.parseInt(parsedLine[2]);
				int startDay = Integer.parseInt(parsedLine[3]);
				int startYear = Integer.parseInt(parsedLine[4]);
				int endMonth = Integer.parseInt(parsedLine[5]);
				int endDay = Integer.parseInt(parsedLine[6]);
				int endYear = Integer.parseInt(parsedLine[7]);
				
				Job job = new Job(jobName, jobDescription, 
						          startMonth, startDay, startYear,
						          endMonth, endDay, endYear);
				
				Calendar myStartDate = job.getStartDate();
				Calendar myEndDate = job.getEndDate();

				addJob(job);
			}
			inputFile.close();
		} catch (FileNotFoundException e) {
            e.printStackTrace();
		}
	}
	
	private void readUserJobsList() {
		try {
			File userJobsFile = new File(userJobsListFile);
			inputFile = new Scanner(userJobsFile);

			// read past column headers
			inputFile.nextLine();
			
			while (inputFile.hasNextLine()) {
				String line = inputFile.nextLine();
				String[] parsedLine = line.split(",");
				User currentUser = myUsers.get(parsedLine[0]);
				
				for (int i = 1; i < parsedLine.length; i++) {
					Job currentJob = getSinglePendingJob(parsedLine[i]);
					((Volunteer) currentUser).signup(currentJob);
				}
			}
			inputFile.close();
		} catch (FileNotFoundException e) {
            e.printStackTrace();
		}
	}
}

