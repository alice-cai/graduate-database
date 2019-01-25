// Class: 	QNAStudent
// Author: 	Ordencia Wu
// Date: 	January 15, 2018
// School: 	A.Y Jackson Secondary School
// Purpose: This class contains fields and methods that every student should have access to 
//          that will make up an interactive Q&A menu for them. This class also inherits 
//			fields and methods from the QNAPage class, which contains everything that is
//			used by the general user

import java.util.*;
import java.io.*;

public class QNAStudent extends QNAPage
{
	private static final String UNANSWERED = "QNA/Active QNAs.txt";          	// file name for unanswered questions list, used by student class to upload a question
	private static int numUQs;                                           		// number of unanswered questions
	private static ArrayList<QNA> unansweredQNAs = new ArrayList<QNA>(); 		// array list storing unanswered questions
	private static final String MENU = "QNA/QNA Student Menu.txt";               // file name for student menu options
	private static final String EMPTY = "";                              		// empty String used to initialize answer for unanswered questions

	public static void initQNA ()                                        		// this method initializes all useful fields and array lists for student
	{
		QNAStudent.initPage();                                            		// first initialize fields and array lists for general users
		try                                                               		// then move on to student-specific elements
		{
			BufferedReader in = new BufferedReader (new FileReader (UNANSWERED));	// read the file containing unanswered questions   
			QNA temp;                                                            	// declare a temporary QNA object 
			String newLine;                                                      	// String variable to detect end of file, and store id of each new question
			int id;                                                              	// temporary id of each new question
			String question, answer, category;                                   	// temporary question, answer, and category of each new question
			while ((newLine=in.readLine())!= null)                               	// while the new line has not reached the end of file
			{
				id = Integer.parseInt(newLine);                                   	// assign values to temporary id, question
				question = in.readLine();
				answer = EMPTY;                                                   	// there's no answer for unanswered questions, so answer is empty
				category = in.readLine();
				temp = new QNA (id, question, answer, category);                  	// create the temporary QNA object and add it to the array list of unanswered questions
				unansweredQNAs.add(temp);
			}
			numUQs = unansweredQNAs.size();                                      		// initialize the number of unanswered questions

			in = new BufferedReader (new FileReader (MENU));                     		// switch to read the menu file for student

			while ((newLine = in.readLine())!= null)                             		// until reaching end of file
			{
				menu.add(newLine);                                                		// add new menu option to the menu array list
			}
			numMenuOptions = menu.size();                                        		// then initialize number of menu options

			in.close();
		}
		catch (IOException iox)                                                 		// catch exception if needed
		{
			System.out.println ("Problem accessing file.");
		}

   } // initQNA method


	public static void displayMenu ()                  								// this method displays an interactive menu for student
	{
		int option = INITIAL;                                                			// initialize option (useful value inputted in try strcture)
		do
		{
			System.out.println("\n--- Student Q&A Menu ---");
			for (int i = 0; i < numMenuOptions; i ++)                
			{
				System.out.println (menu.get(i));                              			// first print all menu options
			}
			System.out.println ("Enter option number (\"" + EXIT + "\" to return to previous menu): ");

			boolean valid = false;                                            		// before user enters option number, set boolean variable valid to false   
			while (!valid)                                                    		// this represents whether user's input is valid
			{
				try
				{
					option = sc.nextInt();                                         	// if user enters an int that is either the exit number or a number within the 
					if (option == EXIT || option>= 1 && option <= numMenuOptions)  	// reasonable range (1 to number of menu options)
					{
						valid = true;                                               // then set valid to true
					}
					else                                                           	// otherwise user have to enter an option again
					{
						System.out.print ("Invalid input, enter again: ");
					}
				}	
				catch (InputMismatchException ime)                                	// catch any input mismatch if user enters a String
				{	
					System.out.print ("Invalid input, enter again: ");             	// and ask user to enter again
					sc.nextLine();
				}
			}

			switch (option)                     	// once option number is valid, enter the switch structure
			{
				case 1:                          	// if option = 1
					displayList();               	// display list of Q&As
					break;
				case 2:                         	// if option = 2
					searchKeyWord();            	// search questions by keyword
					break;
				case 3:                         	// if option = 3
					categoryList();             	// display list of categories
					break;
				case 4:                         	// if option = 4
					searchCategory();           	// search questions by category
					break;
				case 5:                         	// if option = 5   
					askQ();                      	// ask and upload questions
					break; 
			}
		} while (option != EXIT);              // repeat until user enters exit number
		answeredQNAs.clear();                  // clear all array lists before next user loads information again
		categories.clear();
		menu.clear();
		unansweredQNAs.clear();
	} // displayMenu method


	private static void askQ ()               		// this method prompts student to ask a question
	{
		String newQuestion;                    		// declare a String representing new question
		int category = INITIAL;                		// decalre and initialize category number, which will be entered later to better define the new question
		do
		{
			sc.nextLine();
			System.out.println ("Enter your question (\"" + EXIT + "\" to return to previous menu):");		
			newQuestion = sc.nextLine();                                	// user enters their question
			if (!newQuestion.equals(EXITSTR))                           	// if student doesn't wish to exit
			{
				System.out.println ();
				for (int i = 0; i < numCategory; i ++)						// go through list of categories and print each of them
				{
					System.out.println (i+1 + ". " + categories.get(i));
				}
				System.out.println ("Enter the number of the category your question belongs to: ");
				boolean valid = false;                                  	// boolean variable valid is set to false
				while (!valid)
				{
					try
					{
						category = sc.nextInt();                           	// user enters the category their question should belong to
						if (category>= 1 && category <= numCategory)       	// if the category number is within a reasonable range 
						{                                                  	// (from 1 to number of categories)
							valid = true;                                  	// valid is true
						}
						else
						{                                                  	// otherwise prompt user to enter a useful value
							System.out.print ("Invalid input, enter again: ");
						}
					}	
					catch (InputMismatchException ime)                     	// catch any input mismatch exceptions
					{	
						System.out.print ("Invalid input, enter again: ");
						sc.nextLine();
					}
				}
				System.out.println ();
				String newCategory = categories.get(category-1);        	// the new category in given a String value (take the category at position "chosen category number - 1" in array list)
				addQ(newQuestion, newCategory);                         	// add the question to array list of unanswered questions
			}
		} while (!newQuestion.equals(EXITSTR));                        		// repeat until user enters exit number for new question
		System.out.println ();

		try
		{                                                              		// after all new questions have been added to array list
			BufferedWriter out = new BufferedWriter (new FileWriter (UNANSWERED));// start writing to file
			QNA temp;                                                   	// use a temporary QNA object pointer
			for (int i = 0; i < numUQs; i ++)                           	// go through every question in the unanswered questions array list
			{
				temp = unansweredQNAs.get(i);                            	// assign each QNA to the temp variable
				out.write(String.valueOf(temp.getId()));                 	// then write information to file
				out.newLine();
				out.write(temp.getQuestion());
				out.newLine();
				out.write(temp.getCategory());
				out.newLine();
			}

			out.close();
		}
		catch (IOException iox)                                        		// catch any exception if file cannot be accessed
		{
			System.out.println ("Problem uploading questions.");
		}
	} // askQ method


	private static void addQ (String newQuestion, String newCategory) 			// this method adds a new question to the array list of unanswered questions
	{																 			// parameters: newQuestion is the question to be added to array list
																				// newCategory is the category of the question to be added to array list
		QNA newQ;                                                      			// declare new object newQ
		int newId = numUQs+1;                                          			// its id equals current number of unanswered questions plus 1
		numUQs ++;                                                     			// number of unanswered questions increases by 1
		newQ = new QNA (newId, newQuestion, EMPTY, newCategory);       			// create the new QNA object

		unansweredQNAs.add(newQ);                                      			// add the object to the unanswered questionts array list and output a success message
		System.out.println ("Your question has been uploaded to be reviewed by the administration.");
		System.out.println ();  
	} // addQ method
} // QNAStudent class