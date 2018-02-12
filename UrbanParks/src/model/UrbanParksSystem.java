package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class UrbanParksSystem {
	private static final String SERIALIZED_USERS_FILE = "./storage/serializedUser.ser";
	private static final String SERIALIZED_JOBS_FILE = "./storage/serializedJobs.ser";
	
	private Map<String, User> myUsers;
	private PendingJobs myPendingJobs;
	
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
	
	
	//@SuppressWarnings("unchecked") // Unneeded?
	public void loadJobs() {
		try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(SERIALIZED_JOBS_FILE);
            ObjectInputStream in = new ObjectInputStream(file);
             
            // Method for deserialization of object
            myPendingJobs = (PendingJobs) in.readObject();
             
            in.close();
            file.close();
     
        } catch(IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
            
        } catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public void saveUsers(Map<String, User> theUsers) {
		try {
            // Reading the object from a file
            FileOutputStream file = new FileOutputStream(SERIALIZED_USERS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(file);
             
            // Method for deserialization of object
            out.writeObject(theUsers);
             
            out.close();
            file.close(); 
     
        } catch(IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        } 
	}

	public void saveJobs(PendingJobs thePendingJobs) {
		try {
            // Reading the object from a file
            FileOutputStream file = new FileOutputStream(SERIALIZED_JOBS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(file);
             
            // Method for deserialization of object
            out.writeObject(thePendingJobs);
             
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
	
	public PendingJobs getPendingJobs() {
		return myPendingJobs;
	}
	
	public Map<String, User> getUsers() {
		return myUsers;
	}
}