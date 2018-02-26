package model;

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
	
	private static int MAX_PENDING_JOBS = 10;
	private static final int MAX_JOB_DURATION = 4;
	private static final int MAX_TIMESPAN = 60;
	private static final int MIN_TIMESPAN = 3;
	
	private Map<String, User> myUsers;
	private Map<String, Job> myPendingJobs; // TODO: Make this a HashSet
	
	public UrbanParksSystem() {		
		loadUsers();
		loadJobs();
	}
	
	public User signIn(String theUsername) {
		return myUsers.get(theUsername);
	}

	public void addJob(Job theJob) { // Should the check for MAX_PENDING_JOBS go here?
		myPendingJobs.put(theJob.getJobName(), theJob);
	}
	
	public void removeJob(Job theJob) { // Make any checks here?
		myPendingJobs.remove(theJob.getJobName()); // Would rather remove from a set, not by name
	}
	
	/*********** Getters & Setters ***********/
	
	public Set<Job> getPendingJobs() {
		return (Set<Job>) myPendingJobs.values();
	}

	public static int getMaxPendingJobs() {
		return MAX_PENDING_JOBS;
	}
	
	public static int getMaxJobDuration() {
		return MAX_JOB_DURATION;
	}
	
	public static int getMaxTimespan() {
		return MAX_TIMESPAN;
	}
	
	public static int getMinTimespan() {
		return MIN_TIMESPAN;
	}
	
	public void setMaxPendingJobs(int theMaxJobs) {
		MAX_PENDING_JOBS = theMaxJobs;
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
}

