package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UrbanParksSystem {
	
	
	private static final String USER_LIST_FILE = "./storage/userList.csv";
	private static final String PENDING_JOBS_LIST_FILE = "./storage/pendingJobsList.csv";
	private static final String VOLUNTEER_JOBS_LIST_FILE = "./storage/userJobsList.csv";
	
	private static final String SERIALIZED_USERS_FILE = "./storage/serializedUser.ser";
	private static final String SERIALIZED_JOBS_FILE = "./storage/serializedJobs.ser";
	
	private Map<String, User> myUsers;
	//private Map<String, Job> myPendingJobs;
	private Scanner inputFile;
	private Calendar myCalendar;
	
	public UrbanParksSystem() {
		
		// used to initialize storage with data
		/*
		myUsers = new HashMap<>();
		myCalendar = new Calendar();

		
		// read data from userList.csv and store it in myUsers
		readUserList();
		
		// read data from pendingJobsList.csv and create these jobs
		readPendingJobsList();
		
		// read data from userJobsList.csv and add these jobs to the user
		readVolunteerJobsList();
		
		saveUsers(myUsers);
		saveJobs(myCalendar);
		*/
		
		loadUsers();
		loadJobs();
		

	}
	
	public Calendar getCalendar() {
		return myCalendar;
	}
	
	public User signIn(String theUsername) {
		
		return myUsers.get(theUsername);		
		
	}
	
	public Map<String, User> getUsers() {
		return myUsers;
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
            myCalendar = (Calendar) in.readObject();
             
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
	
	
	public void saveJobs(Calendar thePendingJobs) {
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
	
	
	
	private void readUserList() {

		try {
			File userFile = new File(USER_LIST_FILE);
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
			File pendingJobsFile = new File(PENDING_JOBS_LIST_FILE);
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
				
				myCalendar.addJob(job);
				
				
			}
			
			
			
			inputFile.close();
		} catch (FileNotFoundException e) {
            e.printStackTrace();
		}
		
		
		
	}
	
	private void readVolunteerJobsList() {
		try {
			File userJobsFile = new File(VOLUNTEER_JOBS_LIST_FILE);
			inputFile = new Scanner(userJobsFile);

			// read past column headers
			inputFile.nextLine();
			
			
			while (inputFile.hasNextLine()) {
				String line = inputFile.nextLine();
				String[] parsedLine = line.split(",");
				User currentUser = myUsers.get(parsedLine[0]);
				
				for (int i = 1; i < parsedLine.length; i++) {
					
					
					Job currentJob = myCalendar.getSinglePendingJob(parsedLine[i]);
					try {
						((Volunteer) currentUser).signup(currentJob);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			
			inputFile.close();
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		UrbanParksSystem system = new UrbanParksSystem();	
		User user1 = system.signIn("paolo186");
		System.out.println("My jobs: " + ((Volunteer)user1).getJobsList());
		System.out.println("My info: " + user1.toString());
		System.out.println("\nEnd of Program");
		
	}
	
	
	

}
