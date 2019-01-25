// Class: QNAPage
// Author: Ordencia Wu
// Date: January 15, 2018
// School: A.Y Jackson Secondary School
// Purpose: This class contains fields and methods that every user should have access to 
//          that will make up an interactive Q&A menu for them

import java.util.*;
import java.io.*;

public class QNAPage                                                          
{
	protected static int numQs;                                                // total number of answered questions
	protected static ArrayList<QNA> answeredQNAs = new ArrayList<QNA>();       // an array list storing all answered questions, their ids, answers, and categories
	protected static int numCategory;                                          // total number of question categories
	protected static ArrayList<String> categories = new ArrayList<String>();   // an array list storing categories as String
	protected static final String ANSWERED = "QNA/Inactive QNAs.txt";          // file name for all answered questions
	protected static final String CATEGORY = "QNA/Category.txt";               // file name for all categories
	protected static int numMenuOptions;                                       // number of options on menu for users
	protected static ArrayList<String> menu = new ArrayList<String>();         // an array list storing all options on menu
	protected static final int EXIT = -1;                                      // default number to indicate user's wish to exit
	protected static final String EXITSTR = "-1";                              // String value for user to exit
	protected static final int INITIAL = 0;                                    // initialize variables to this number if a try structure is used for user to input a useful value for the variable
	static Scanner sc = new Scanner (System.in);                               // Scanner


	public static void initPage ()                                             // method to initialize key variables and array lists for the menu to be functional
	{
		try
		{
			BufferedReader in = new BufferedReader (new FileReader (ANSWERED));  // declare buffered reader to read file storing answered questions
			QNA temp;                                                            // create a temporary QNA object to add to the answeredQNAs array list
			String newLine;                                                      // String variable to detect end of file, and store id of each new question 
			int id;                                                              // temporary id of each new question
			String question, answer, category;                                   // temporary question, answer, and category of each new question
			while ((newLine=in.readLine())!= null)                               // while the new line has not reached the end of file 
			{
				id = Integer.parseInt(newLine);                                  // assign the newly read information regarding id, question, answer, and category
				question = in.readLine();                                        // to the temporary QNA object
				answer = in.readLine();
				category = in.readLine();
				temp = new QNA (id, question, answer, category);                 // when object fields have value, create the object
				answeredQNAs.add(temp);                                          // and add it to the answeredQNAs array list
			}
			numQs = answeredQNAs.size();                                         // initialize the number of answered questions

			in = new BufferedReader (new FileReader (CATEGORY));                 // switch to read the category file
			while ((category=in.readLine())!= null)                              // variable category has been declared, and we use it to detect end of file
			{
				categories.add(category);                                        // if havn't reached the end of file, add the new line to categories array list
			}                                                                    // to represent a new category
			numCategory = categories.size();                                     // initialize number of categories
			in.close();
		}
		catch (IOException iox)
		{
			System.out.println ("Problem accessing file.");                      // catch exception if something is wrong with reading file
		}	  	
	}// initPage method


	public static void displayList()                                                     // method to display all answered Q&As for user
	{
		int exit = INITIAL;                                                              // declare a variable exit, initialize it since its value will be given in
                                                                                         // a try structure
		System.out.println("\n--- Q&A List ---");
		for (int i = 0; i < numQs; i ++)                                                 // print every anwered Q&As 
		{
			System.out.println (answeredQNAs.get(i));
		}
		System.out.println ("Enter \"" + EXIT + "\" to return to previous menu: ");

		boolean valid = false;                                                        // the boolean valid is created to reflect whether user's input is valid
		while (!valid)                                                                // in the while loop, if valid doesn't become true, user will keep on entering
		{                                                                             // values until the input is proven to be useful for the program
			try
			{
				exit = sc.nextInt();                                                  // if user enters an int, and if the int matches the default exit number
				if (exit == EXIT)
				{
					valid = true;                                                     // this is the only way to make valid true for this method
				}                                                                     // since user only needs to exit the current page
				else                                                                  // but if user doesn't follow instruction and doesn't enter -1
				{                                                                     // they will be asked to enter a valid number (-1)
					System.out.print ("Enter \"" + EXIT + "\" to return to previous menu, enter again: ");
				}
			}	
			catch (InputMismatchException ime)                                        // any input mismatch will be caught if user randomly enters a String
			{	
				System.out.print ("Invalid input, enter again: ");
			sc.nextLine();
			}
		}
	} // displayList method


	public static void searchKeyWord ()                                                          // method to search up questions by entering keywords
	{
		sc.nextLine();                                                                           // since we are entering a String, we should flush out any enter keys
		String word;                                                                             // declare a variable to store keywords
		
		do 
		{
			System.out.println("\n--- Search By Keyword ---");
			boolean found = false;                                                                    // boolean variable to store whether keyword is found
			System.out.println ("Enter keyword (\"" + EXIT + "\" to return to previous menu): ");
			word = sc.nextLine();
			if (!word.equals(EXITSTR))                                                                // if keyword is not the default exit number
			{
				word = word.toLowerCase();                                                            // change keyword to lower case, to make this function case-insensitive
				for (int i = 0; i < numQs; i++)                                                       // through all answered questions
				{
					QNA temp = answeredQNAs.get(i);                                                   // a temporary QNA object will store information about each answered questions
					String tempQ = temp.getQuestion().toLowerCase();                                  // new variables representing the question, answer, and category will store
					String tempA = temp.getAnswer().toLowerCase();                                    // corresponding information, all converted to lower case
					String tempC = temp.getCategory().toLowerCase();
					if (tempQ.indexOf(word)!=-1||tempA.indexOf(word)!=-1||tempC.indexOf(word)!=-1)    // if any of the components of a QNA object contains the keyword
					{
						System.out.println (temp);                                                    // program prints the temporary QNA because it's relevant 
						found = true;                                                                 // and keword is found, so boolean variable equals true
					}
				}
			}
			if (!found && !word.equals(EXITSTR))                                                      // if user doesn't intend to exit by entering -1 and keyword not found
			{
				System.out.println ("Keyword not found.");                                            // output a message saying keyword was not found
			}

		} while (!word.equals(EXITSTR));                                                              // don't exit the loop until user enters the exit number
	} // searchKeyWord method.


	public static void searchCategory ()                                                              // method to search quesitons by categories
	{
		int category = INITIAL;                                                                       // category is represented by ints and useful values will be given in try structure, we need to initialize it
		do
		{
			System.out.println("\n--- Search By Category ---");
			for (int i = 0; i < numCategory; i ++)                                                          
			{
				System.out.print (i+1+". ");                                                         // print each category and the number representing it
				System.out.println (categories.get(i));
			}
			System.out.println ("Enter the number of a category (\"" + EXIT + "\" to return to previous menu): ");
			
			boolean valid = false;                                                                   // valid is set to false
			while (!valid)
			{
				try
				{
					category = sc.nextInt();                                                         // if user input for category number is equal to exit, or if it is
					if (category == EXIT || category >= 1 && category <= numCategory)                // within the reasonable number range (1 to number of categories)
					{
						valid = true;                                                                // valid can be set to true
					}
					else
					{
						System.out.print ("Invalid input, enter again: ");                           // otherwise this process is looped and user will have to enter again
					}
				}	
				catch (InputMismatchException ime)                                                   // catch any input mismatch for user to enter a userful input
				{	
					System.out.print ("Invalid input, enter again: ");
					sc.nextLine();
				}
			}
			
			if (category != EXIT)                                                                    // if user doesn't intend to exit
			{
				boolean found = false;                                                               // set found to false, meaning that currently no Q&As from the selected category is found
				String chosen = categories.get(category-1);                                          // the String chosen gets value of the chosen category (in the array list, the chosen element
			                                                                                         // is stored at category number -1 because array list index starts from zero)
				for (int i = 0; i < numQs; i ++)                                                     // go through the list of answered questions
				{
					QNA temp = answeredQNAs.get(i);                                                  // use temporary object 
					String tempC = temp.getCategory ();                                              // to get the category of each QNA object
					if (chosen.equals(tempC))                                                        // if the category matches what user wants
					{
						System.out.println (); 
						System.out.println (temp);                                                   // print the entire Q&A to view
						found = true;                                                                // and set found to true
					}
				}

				if (!found)                                                                          // if nothing is found for the specific category
				{
					System.out.println ("Currently no Q&As available from this category.");          // output a message
					System.out.println ();
				}
			}
		} while (category != EXIT);                                                                  // loop doesn't end until user wants to stop enter category numbers
	} // searchCategory method


	public static void categoryList ()                                                  // method to output the list of available categories                                             
	{
		int exit = INITIAL;                                                             // initialize exit (value will be inputted in a try structure)
		System.out.println ("\n--- Categories ---");                                    // go through the categories array list and print each String
		for (int i = 0; i < numCategory; i ++)
		{
			System.out.println (categories.get(i));
		}
		System.out.println ("Enter \"" + EXIT + "\" to return to previous menu: ");     // user should enter the exit number to return to previous menu

		boolean valid = false;                                                          // boolean variable valid representing whether user input is valid
		while (!valid)
		{
			try
			{
				exit = sc.nextInt();                                                    // if the int user enters is equal to the preset exit number
				if (exit == EXIT)
				{
					valid = true;                                                       // valid will become true 
				}
				else                                                                    // otherwise user will have to enter again until they enter the exit number
				{
					System.out.print ("Enter \"" + EXIT + "\" to return to previous menu, enter again: ");
				}
			}	
			catch (InputMismatchException ime)                                          // catch any input mismatch 
			{	
				System.out.print ("Invalid input, enter again: ");                      // and prompt user to enter a useful value
				sc.nextLine();
			}
		}
	}  // categoryList method
}  // QNAPage class