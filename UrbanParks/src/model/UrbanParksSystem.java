package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;


/**
 * Handles systematic functions that the UI needs 
 * such as signing in, loading and saving files
 * for persistent storage.
 * 
 * @author Bryan Santos
 * @author Jordan LeBle
 */
public class UrbanParksSystem {
	private static final String SERIALIZED_USERS_FILE = "./storage/serializedUser.ser";
	private static final String SERIALIZED_JOBS_FILE = "./storage/serializedJobs.ser";

	
	private Map<String, User> myUsers;
	private Map<String, Job> myPendingJobs; // TODO: Make this a HashSet
	
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
	

	public int jobsInSystem() {
		return myPendingJobs.size();			
	}
	
	
	private Job getSinglePendingJob(String string) {	
		return myPendingJobs.get(string);
	}
	
	/*********** Getters & Setters ***********/
	
	public Set<Job> getPendingJobs() {
		return (Set<Job>) myPendingJobs.values();
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
	
	
	
	/*
	 * Initializing new Database from csv files
	 */
	
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
				
				int startMonth = Integer.parseInt(parsedLine[2]);
				int startDay = Integer.parseInt(parsedLine[3]);
				int startYear = Integer.parseInt(parsedLine[4]);
				int endMonth = Integer.parseInt(parsedLine[5]);
				int endDay = Integer.parseInt(parsedLine[6]);
				int endYear = Integer.parseInt(parsedLine[7]);
				
				Job job = new Job(jobName, jobDescription, 
						          startMonth, startDay, startYear,
						          endMonth, endDay, endYear);
				
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
	
	public static void main(String[] args) {
		UrbanParksSystem system = new UrbanParksSystem(true);
		User user = system.signIn("derickZ");
		
		System.out.println("Users: " + system.myUsers);
		System.out.println("Jobs: " + system.myPendingJobs);
		
	}
	
	
}

