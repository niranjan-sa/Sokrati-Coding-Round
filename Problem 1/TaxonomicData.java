/*
 * Author Niranjan Agnihotri
 * Store the taxonomic data
 * Test cases in file data.txt
 * Output is the number of occurrences of particular items
 * Data structure used
 * Modified trie, using HashMap
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

//utility class each item is an object of Node class
class Node {
	//count variable to note the count the occurrences 
	private int count;	
	//stores the links to the objects in the next level
	private HashMap<String, Node> data;
	
	//constructor
	public Node() {
		super();
		this.count = 1;
		data = new HashMap<String, Node>();
	}
	
	//getters setters
	public HashMap<String, Node> getData(){
		return data;
	}
	
	public int getCount() {
		return count;
	}
	
	public void increment() {
		this.count++;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void addNew(String token, Node node) {
		this.data.put(token, node);
	}
}


//main driver class
public class TaxonomicData {
	
	//to store the top level items
	private static HashMap<String, Node> data = 
				new HashMap<String, Node>();
	
	
	public static void main(String[] args) throws IOException {
		//reads the data and construct the data structure
		readData();
		//utility function to query the data structure
		queryResults();
	}

	private static void queryResults() {
		//repeat till user wants to exit
		while(true) {
			int opt=0;
			System.out.println("Enter your options :- ");
			System.out.println("1. Query an Item 2. Exit");
			Scanner sc = new Scanner(System.in);
			opt = sc.nextInt();
			switch(opt) {
				default :
					System.out.println("Thank you");
					return;
				case 1:
					//take query as input
					System.out.println("Enter the item to query :- ");
					sc.nextLine();
					String token = sc.nextLine();
					String []tokens = token.split(" > ");
					
					//access the top level hashmap
					HashMap<String, Node> tmp = data;
					Node node = null;
					
					//For all the tokens traversing down the trie
					for(String temp : tokens) {
						temp = temp.trim();
						if(tmp.keySet().contains(temp)) {
							node = tmp.get(temp);
							tmp = node.getData();
						} else {
							System.out.println("Wrong Key");
							return;
						}
					}
					//finally print the count of the output
					System.out.println(token + " = "+node.getCount());
					break;
				case 2:
					return;
			}
		}
	}

	//Reads the file and builds the data structure
	private static void readData() throws IOException {
			//Open the file 
			File log = new File("data.txt");
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
				String []tokens = thisLine.split(" > ");
				
				//trimming the keys
				for(int i=0; i<tokens.length; i++) {
					tokens[i] = tokens[i].trim();
				}
				
				HashMap<String, Node> temp = data;
				
				//for all the tokens in the line perform insert operation
				for(String token : tokens) {
					
					if(temp.keySet().contains(token)) {
						//if already seen the key increment the occurrence count
						((Node)temp.get(token)).increment();
						//get it's list of links to traverse further down
						temp = ((Node)temp.get(token)).getData();
					} else {
						//if the key is not present than just insert new one
						//create a new node object and put it in the HashMap for the proper key
						Node node = new Node();
						temp.put(token, node);
						temp = node.getData();
					}
				}	
			}	
			
			//for debugging
			//System.out.println(data);
			//parseData(data);
			
			//close the resourse
			br.close();
		
	}
	
	//function created to view the entire data structure
	//debugging purpose
	private static void parseData(HashMap<String, Node> data) {
		if(!data.isEmpty()) {
			Set<String> keys = data.keySet();
			for(String key : keys) {
				System.out.println(key + " - " +((Node)data.get(key)).getCount());
				parseData(((Node)data.get(key)).getData());
			}
		}
	}
}
