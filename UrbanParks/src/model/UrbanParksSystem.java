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
 *
 */

public class UrbanParksSystem {
	private static final String SERIALIZED_USERS_FILE = "./storage/serializedUser.ser";
	private static final String SERIALIZED_JOBS_FILE = "./storage/serializedJobs.ser";
	
	
	private Map<String, User> myUsers;
	//private PendingJobs myPendingJobs;
	private Map<String, Job> myPendingJobsCollection;
	
	public UrbanParksSystem() {		
		loadUsers();
		loadJobs();
	}

	@SuppressWarnings("unchecked")
	public void loadUsers() {
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
	public void loadJobs() {
		try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(SERIALIZED_JOBS_FILE);
            ObjectInputStream in = new ObjectInputStream(file);
             
            // Method for deserialization of object
            myPendingJobsCollection = (Map<String, Job>) in.readObject();
             
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
            out.writeObject(myPendingJobsCollection);
             
            out.close();
            file.close();
     
        } catch(IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        }
	}
	
	public User signIn(String theUsername) {
		return myUsers.get(theUsername);
	}
	
	public Set<Job> getPendingJobsCollection() {
		return (Set<Job>) myPendingJobsCollection.values();
		
	}
	
	public Job getSinglePendingJob(String theJobName) {
		return myPendingJobsCollection.get(theJobName);
	}
	
	public void addJobToCollection(Job theJob) {		
		myPendingJobsCollection.put(theJob.getJobName(), theJob);
	}
	
	

}

