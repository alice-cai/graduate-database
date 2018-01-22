// Class: 	QNAGuest
// Author: 	Ordencia Wu
// Date: 	January 15, 2018
// School: 	A.Y Jackson Secondary School
// Purpose: This class contains fields and methods that every guest should have access to 
//          that will make up an interactive Q&A menu for them. This class also inherits 
//			fields and methods from the QNAPage class, which contains everything that is
//			used by the general user

import java.util.*;
import java.io.*;

public class QNAGuest extends QNAPage
{
	private static final String MENU = "QNA/QNA Guest Menu.txt";  // file name for guest menu options

	public static void initQNA ()                         // this method initializes all fields and array lists for guests
	{
		QNAGuest.initPage();                               // initialize fields and array lists for general users
		try                                                // then start storing menu content for guests
		{    
			BufferedReader in = new BufferedReader (new FileReader (MENU));   // start reading in the menu file
			String newLine;                                                   // declare a new String to detect end of file

			while ((newLine = in.readLine())!= null)                             // until reaching end of file
			{
				menu.add(newLine);                                                // add new menu option to the menu array list
			}
			numMenuOptions = menu.size();                                        // then initialize number of menu options

			in.close();
			}
		catch (IOException iox)                                                 // catch exception if needed
		{
			System.out.println ("Problem accessing file.");
		}

	}  // initQNA method

	public static void displayMenu ()               // this method displays an interactive menu for guests
	{	
		int option = INITIAL;                                                // initialize option (useful value inputted in try strcture)
		do
		{
			System.out.println("\n--- Guest Q&A Menu ---");
			for (int i = 0; i < numMenuOptions; i ++)                
			{
				System.out.println (menu.get(i));                              // first print all menu options
			}
			System.out.println ("Enter option number (\"" + EXIT + "\" to return to previous menu): ");

			boolean valid = false;                                            // before user enters option number, set boolean variable valid to false   
			while (!valid)                                                    // this represents whether user's input is valid
			{
				try
				{
					option = sc.nextInt();                                         // if user enters an int that is either the exit number or a number within the 
					if (option == EXIT || option>= 1 && option <= numMenuOptions)  // reasonable range (1 to number of menu options)
					{
						valid = true;                                               // then set valid to true
					}
					else                                                           // otherwise user have to enter an option again
					{
						System.out.print ("Invalid input, enter again: ");
					}
				}	
				catch (InputMismatchException ime)                                // catch any input mismatch if user enters a String
				{	
					System.out.print ("Invalid input, enter again: ");             // and ask user to enter again
					sc.nextLine();
				}
			} 

			switch (option)                     // once option number is valid, enter the switch structure
			{
				case 1:                          // if option = 1
					displayList();                 // display list of Q&As
					break;
				case 2:                          // if option = 2
					searchKeyWord();              // search questions by keyword
					break;
				case 3:                          // if option = 3
					categoryList();               // display list of categories
					break;
				case 4:                          // if option = 4
					searchCategory();             // search questions by category
					break;
			}
		} while (option != EXIT);              // repeat until user enters exit number
      answeredQNAs.clear();                  // clear all array lists before next user loads information again
      categories.clear();
      menu.clear();
	}  // displayMenu method
} // QNAGuest class