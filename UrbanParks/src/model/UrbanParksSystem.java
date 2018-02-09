package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UrbanParksSystem {
	
	private static final String userListFile = "./storage/userList.csv";
	private static final String pendingJobsListFile = "./storage/pendingJobsList.csv";
	private static final String userJobsListFile = "./storage/userJobsList.csv";
	
	private Map<String, User> myUsers;
	private Scanner inputFile;
	private static Calendar myCalendar;
	
	public UrbanParksSystem() {
		
		myUsers = new HashMap<String, User>();
		myCalendar = new Calendar();
		
		// read data from userList.csv and store it in myUsers
		readUserList();
		
		// read data from pendingJobsList.csv and create these jobs
		readPendingJobsList();
		
		// read data from userJobsList.csv and add these jobs to the user
		readUserJobsList();

	}
	
	public User signIn(String theUsername) {
		
		return myUsers.get(theUsername);		
		
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
				
				myCalendar.addJob(job);
				
				
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
					
					
					Job currentJob = myCalendar.getSinglePendingJob(parsedLine[i]);
					
					((Volunteer) currentUser).signup(currentJob);
					
				}
				
			}
			
			
			inputFile.close();
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
		}
		
		
	}
	
	
	
	public static void main(String[] args) {
		UrbanParksSystem system = new UrbanParksSystem();
		User user = system.signIn("derickZ");
		
		List<Job> calendarJobs = myCalendar.getPendingJobsList();
		//System.out.println("Calendar List: " + calendarJobs);
		
		System.out.println("User: " + user.getName());
		System.out.println("User Job List: " + ((Volunteer) user).getJobsList());
		
	}
	
	

}
