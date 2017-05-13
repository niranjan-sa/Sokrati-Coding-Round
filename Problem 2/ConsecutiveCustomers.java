/*
 * Author Niranjan Agnihotri
 * Program to find the names of users that visited the web site for 4 consecutive days
 * Test cases in file log.txt
 * Output customer ids of customers that visisted for 4 consecutive days
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;


//Class to store the customer visits information
class Customer {
	private ArrayList<Date> visits;
	private boolean visited=false;
	
	public Customer( Date visitDate) {
		visits = new ArrayList<Date>();
		this.visits.add(visitDate);
	}
	
	
	//getters setters
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public void addVisitDate(Date date) {
		this.visits.add(date);
	}
	
	public ArrayList<Date> getVisits() {
		return visits;
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		s += visits.toString();
		return s;
	}
}


//Main driver class
public class ConsecutiveCustomers {
	public static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	//Hash Map to map customers to their visit dates
	private static HashMap<Long, Customer> customers = new HashMap<Long, Customer>();
	
	//Driver function
	public static void main(String[] args) throws IOException, ParseException {
		//Reading the input
		readInput();
		//View the data
		System.out.println("The following customers visited the site for 4 consecutive days :- \n" + process());
	}

	//Reader function 
	private static void readInput() throws IOException, ParseException {
		//Open the file 
		File log = new File("log.txt");
		
		//Checking the file
		if(!log.exists()) {
			System.out.println("No File");
			return;
		}
		
		
		//Open connection to the file
		BufferedReader br = 
				new BufferedReader(new FileReader(log));
		
		String thisLine = null;
	
		//Reading the output line by line and building the data structure
		while((thisLine = br.readLine()) != null) {
			//Split by tab delimiter
			String []tokens = thisLine.split("\t");
			//parse the date
			Date visitDate = df.parse(tokens[0]);
			long custId = Long.parseLong(tokens[1]);
			
			//Build the data structure
			if(customers.containsKey(custId)) {
				//if the customer has already visited earlier
				((Customer)(customers.get(custId))).addVisitDate(visitDate);
			} else {
				//customer visiting for first time
				customers.put(custId, new Customer(visitDate));
			}
		}	
		br.close();
	}

	//Function to process the 
	private static ArrayList<Long> process() {
		ArrayList<Long> regularVisitors = new ArrayList<Long>();
		
		//For each customer check if he visited for 4 consecutive days.
		for(Long keys : customers.keySet()) {
			Customer temp = ((Customer)customers.get(keys));
			temp.setVisited(visitsRegularly(temp.getVisits()));
			//If yes than included his customer in the list
			if(temp.isVisited()) {
				regularVisitors.add(keys);
			}
		}
		
		//Return the list
		return regularVisitors;
	}
	
	
	private static boolean visitsRegularly(ArrayList<Date> visits) {
		int cnt = 1;
		boolean isRegular = false;
		//Sorting the dates
		//Sorting dates is not necessary can also implement without sorting
		//Sorting is not used assuming that the log contains the entries in sorted order
		//Normal scenario
		//If this is not the case, we can uncomment the following line of code to solve the problem
		
		//Collections.sort(visits);
		//
		Date tmp0 = (Date)visits.get(0);
		long length = visits.size();
		
		for(int i=1; i<length; i++) {
			
			Date tmp1 = (Date)visits.get(i);
			
			if(getDifferenceDays(tmp0, tmp1) == 1) {
				//if consecutive days are there increment the count this 
				//snippet also takes into account if the user visits on the same date
				//an easy modification in the comparison operator in the above if condition
				//can change the behavior.
				cnt++;
				if(cnt == 4) {
					break;
				}
			} else if(getDifferenceDays(tmp0, tmp1) > 1) {
				//else reset the count
				cnt = 0;
			}
			
			//shuffle
			tmp0 = tmp1;
			
		}
		
		//
		if(cnt == 4) {
			isRegular = true;
		}
 		return isRegular;
	}

	public static long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(Math.abs(diff), TimeUnit.MILLISECONDS);
	}
	
}
