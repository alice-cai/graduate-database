// Class:   QNAAdmin
// Author:  Ordencia Wu
// Date:    January 15, 2018
// School:  A.Y Jackson Secondary School
// Purpose: This class contains fields and methods that every admin should have access to 
//          that will make up an interactive Q&A menu for them. This class also inherits 
//          fields and methods from the QNAPage class, which contains everything that is
//          used by the general user

import java.util.*;
import java.io.*;

public class QNAAdmin extends QNAPage 
{
   private static final String UNANSWERED = "QNA/Active QNAs.txt";          // name for file containing unanswered Q&As, which admin has access to
   private static final String MENU = "QNA/QNA Admin Menu.txt";                 // name for file containing the admin menu
   private static int numUQs;                                           // number of unanswered questions
   private static ArrayList<QNA> unansweredQNAs = new ArrayList<QNA>(); // an array list storing all unanswered questions
   private static final String EMPTY = "";                              // empty String used to initialize answer for unanswered questions

   public static void initQNA ()                                              // method to initialize all fields and array lists for the admin   
   {
      QNAAdmin.initPage();                                                    // first initialize fields and array lists that are shared among all users
      try                                                                     // then move on to admin-specific elements
      {
         BufferedReader in = new BufferedReader (new FileReader (UNANSWERED));// read the file containing unanswered questions   
         QNA temp;                                                            // declare a temporary QNA object 
         String newLine;                                                      // String variable to detect end of file, and store id of each new question
         int id;                                                              // temporary id of each new question
         String question, answer, category;                                   // temporary question, answer, and category of each new question
         while ((newLine=in.readLine())!= null)                               // while the new line has not reached the end of file
         {
            id = Integer.parseInt(newLine);                                   // assign values to temporary id, question
            question = in.readLine();
            answer = EMPTY;                                                   // there's no answer for unanswered questions, so answer is empty
            category = in.readLine();
            temp = new QNA (id, question, answer, category);                  // create the temporary QNA object
            unansweredQNAs.add(temp);                                         // and add it to the array list of unanswered questions
         }
         numUQs = unansweredQNAs.size();                                      // initialize the number of unanswered questions
         
         in = new BufferedReader (new FileReader (MENU));                     // switch to read the menu file for admin
     
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

   } // initQNA method
   
   public static void displayMenu ()                                       // method to display an interactive menu for admin
   {
      int option = INITIAL;                                                // initialize option (useful value inputted in try strcture)
      do
      {
         System.out.println("\n--- Admin Q&A Menu ---");
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
            case 5:                          // if option = 5
               browseUnanswered();           // browse all unanswered questions, and maybe answer or delete some of them
               break; 
            case 6:                          // if option = 6
               browseCategory();             // browse unanswered questions by category, and maybe answer or delete some of them
               break;
            case 7:                          // if option = 7
               addCategory();                // add new categories to the list
               break;
         }
      } while (option != EXIT);              // menu will be displayed over and over until user decides to exit 
      answeredQNAs.clear();                  // clear all array lists before next user loads information again
      categories.clear();
      menu.clear();
      unansweredQNAs.clear();
   } // displayMenu method
   
   public static void browseCategory ()                                          // method to browse unanswered questions by category
   {
      int category = INITIAL;                                                    // initialize int representing desired category (will be given useful value in a try structure)
      ArrayList<Integer> possibleIds = new ArrayList<Integer>();                 // array list containing ids for a chosen category, will be useful when checking valid input for ids later
      do
      {
         for (int i = 0; i < numCategory; i ++)
         {
            System.out.print (i+1+". ");
            System.out.println (categories.get(i));                              // first output all categories with a category number
         }
         System.out.println ("Enter the number of a category (\"" + EXIT + "\" to return to previous menu): ");
  
         boolean valid = false;                                                  // before user enters category number, initialize valid to false
         while (!valid)
         {
            try   
            {                          
               category = sc.nextInt();                                          // if user enters an int that is either exit or within the reasonable range   
               if (category == EXIT || category >= 1 && category <= numCategory) // (from 1 to number of categories)
               {
                  valid = true;                                                  // user input is valid
               }
               else                                                              // otherwise user will be asked to input again until something valid is entered
               {
                  System.out.print ("Invalid input, enter again: ");
               }
            }  
            catch (InputMismatchException ime)                                   // catch input mismatch exceptions
            {  
               System.out.print ("Invalid input, enter again: ");
               sc.nextLine();
            }
         }
         
         System.out.println ();
         possibleIds.clear();                                                    // clear content in the possibleIds array to store new ids for a newly chosen category
         
         if (category != EXIT)                                                   // if the category number entered doesn't equal the exit number
         {
            boolean found = false;                                               // found is set to false, meaning no Q&As from the category is found
            String chosen = categories.get(category-1);                          // the same algorithm employed before to assign String value to chosen category
            
            for (int i = 0; i < numUQs; i ++)                                    // go through the list of unanswered questions
            {
               QNA temp = unansweredQNAs.get(i);                                 // temporary object is initialized
               String tempC = temp.getCategory();                                // temporary object's category is initialized
               if (chosen.equals(tempC))                                         // if the temporary category matches what admin wants
               {
                  int tempId = temp.getId();                                     // then a temporary id will be created, value is equal to the id of the temporary object
                  possibleIds.add(Integer.valueOf(tempId));                      // add this id to array list of possible ids
                  found = true;                                                  // some Q&As are found, set found to true
               }
            }
            
            if (found)                                                           // if some Q&As are found
            {
               answerPrompt(possibleIds);                                        // call the answerPrompt method, pass in the array list of possible ids
               deletePrompt(possibleIds);                                        // call the deletePrompt method, pass int eh array list of possible ids
               savingPrompt(); 
               System.out.println();                                              // call the savingsPrompt method
            }
            else                                                                 // if nothing in the category is found, output a message
            {
               System.out.println ("Currently no unanswered Q&As available from this category.");
               System.out.println ();
            }
         }
         
      } while (category != EXIT);                                                // this process if looped until admin no longer wants to browse questions by category
   } // browseCategory method
   
   public static void browseUnanswered()        // this method allows admin to browse all unanswered questions
   {
      boolean exit;                             // declare a boolean variable representing whether admin wishes to exit this page
      do
      {
         System.out.println("\n--- Browse Unanswered Questions By Category ---");
         exit = false;                          // set exit to false, assuming admin doesn't want to exit yet
         
         answerPrompt();                        // call the answerPrompt method, note that this method is overloaded and if called without parametres,
                                                // program will invoke the method that's more useful when browsing ALL unasnwered questions instead of by categories
         deletePrompt();                        // call the deletePrompt method with no parametres
         savingPrompt();                        // call the savingsPrompt method
         
         System.out.println ("\nEnter \"" + EXIT + "\" to return to previous menu, enter any other key to continue viewing questions: ");
         String choice;                         // declare variable reperesenting user's choice
         choice = sc.nextLine();                
         if (choice.equals(EXITSTR))            // if user enters the exit number here
         {
            exit = true;                        // then exit will become true
         }
      } while (!exit);                          // exit this page only when user enters the exit number
   } // browseUnanswered method
  
   public static void savingPrompt()            // this method prompts admin to save progress
   {     
      sc.nextLine();                            
      final String SAVE = "1";                  // constant to check whether user wants to save
      
      System.out.println ("\nWould you like to save changes? (Enter \""+ SAVE +"\" to save, enter any other key to dismiss)");
      String choice;                            
      choice = sc.nextLine();                   // user enters choice
      if (choice.equals(SAVE))                  // if choice is equal to the saving constant
      {
         save();                                // call the save method
      }
   } // savingPrompt method
   
   public static void save()                    // this method saves admin's progress in answering or deleting questions
   {
      try
      {
         BufferedWriter out = new BufferedWriter (new FileWriter (ANSWERED));
         QNA temp;                              // declare temporary QNA object
         
         for (int i = 0; i < numQs; i ++)       // go through all answered questions
         {
            temp = answeredQNAs.get(i);         // temporay object points to each answered question
            out.write(String.valueOf(temp.getId()));  // rewrite each element of the answered question to the file containing  
            out.newLine();                            // answered questions
            out.write(temp.getQuestion());
            out.newLine();
            out.write(temp.getAnswer());
            out.newLine();
            out.write (temp.getCategory());
            out.newLine();
         }
         out.close();
         
         out = new BufferedWriter (new FileWriter (UNANSWERED)); // switch to write to file containing unanswered questions
         for (int i = 0; i < numUQs; i ++)
         {
            temp = unansweredQNAs.get(i);                        // repeat the process of rewriting elements to the file
            out.write(String.valueOf(temp.getId()));
            out.newLine();
            out.write(temp.getQuestion());
            out.newLine();
            out.write(temp.getCategory());
            out.newLine();
         }
         
         out.close();
         System.out.println ("Progress saved.");                // ouput message indicating saving is done
      }
      catch (IOException iox)                                   // catch exception if problems are encountered when writing to file
      {
         System.out.println ("Problem writing to file.");
      }
      System.out.println ();
   } // save method
   
   public static void answerPrompt (ArrayList<Integer> possibleIds)  // this method prompts admin to answer question when browing 
   {                                                                 // parametre: an array list of ids of possible questions to be selected
      int id = INITIAL;                                              // initialize id representing user's choice of question to answer
      do
      {
         System.out.println ("\nAvailable questions to answer:");
         boolean available = false;                                  // available is initialized to false, the variable will be set to true
                                                                     // when any available questions belonging to the chosen category is found
         for (int i = 0; i < possibleIds.size(); i ++)
         {
            int possibleId = possibleIds.get(i);                     // go through each possible id, and assign temporary variable "possibleId" one value at a time
            boolean found = false;                                   // initialize found to false, meaning the id hasn't been found from the array list of unanswered questions
            int beginning = 0, ending = unansweredQNAs.size()-1;     // use binary search: beginning index of array list: 0, ending: array list size-1                               
            int middle = INITIAL;                                    // index of middle value initialized
                                                      
            while (!found && beginning <= ending)           // while not found and not completely searched (beginning <= ending)
            {
               middle = (beginning+ending)/2;               // middle value is equal to the average of beginning and ending indices
               QNA temp = unansweredQNAs.get(middle);       // temporary object is equal to middle value in the array list of unasnwered questions
               int tempId = temp.getId();                   // the id for the temporary object is obtained
               if (tempId == possibleId)                    // if the temp id is equal to the current "possible id"
               {
                  found = true;                             // found is true 
                  available = true;                         // some questions are available, so set available to true
                  System.out.println (temp);                // the available question is printed             
               }
               else if (tempId < possibleId)                // second half of the existing list will be searched next time
                  beginning = middle + 1;                   // if middle value is smaller than required number
               else
                  ending = middle - 1;                      // else search first half of the list 
            }     
         }
         if (available)                                     // if some questions are available to be answered
         {
            System.out.println ();
            System.out.println ("Enter ID to answer a question (\"" + EXIT + "\" to stop): ");
                                                            // then admin has a chance to enter the id of the question they want to answer
            boolean valid = false;                          // boolean variable valid is set to false
            while (!valid)
            {
               boolean found = false;                       // set boolean variable found to false, this variable is used to check if the id
                                                            // chosen by admin belongs to the list of possible ids
               try
               {
                  id = sc.nextInt();         
                  
                  if (id != EXIT)                           // if user doesn't enter the exit number for id then we start looking through 
                  {                                         // the list of possible ids and determine whether user entered a valid id
                     for (int i = 0; i < possibleIds.size() && !found; i ++) 
                     {
                        if (possibleIds.get(i) == id)       // if the user entered id is equal to any of the possible ids
                        found = true;                       // then set found to true
                     }
                  }
                  else
                  {
                     valid = true;                          // else if user entered the exit number, valid is true
                  }        
                     
                  if (found)                                // check if the id is found, if it is
                  {
                     valid = true;                          // then valid should be set to true
                  }
                  
                  if (!valid)                               // if input id is invalid, output a message and ask user to reenter
                  {
                     System.out.print ("Invalid input, enter again: ");
                  }
   
               }  
               catch (InputMismatchException ime)           // catch any input mismatch exception
               {  
                  System.out.print ("Invalid input, enter again: ");
                  sc.nextLine();
               }
            }
            System.out.println ();
            
            if (id != EXIT)                                       // if the valid id is not equal to exit number
            {
               if (answerQ(id))                                   // start answering the question, and if the question is answered successfully 
               {
                  possibleIds.remove(Integer.valueOf(id));        // then this id is no longer possible to be selected in the next round and will be removed
                  for (int i = 0; i < possibleIds.size(); i ++)   // go through the possible ids to re-assign them new values since one id is removed
                  {
                     int temp = possibleIds.get(i);               
                     if (temp>id)                                 // if the deleted question comes before this question in the array
                        possibleIds.set(i,Integer.valueOf(temp-1));// then we should change the id of the current question to its original value - 1
                                                                   // to fill up the spot that was orginally taken by recently deleted question           
                  }
               }
            }  
         }     
         else                                                     // else if no available question is found
         {
            System.out.println ("None");                          // print none
            id = EXIT;                                            // id is set to exit such that admin can automatically exit this page
            System.out.println ();
         }
      } while (id != EXIT);                                       // don't exit until exit number is entered
   } // answerPrompt method

   public static void answerPrompt ()                             // this method prompts admin to answer question when browing all unanswered questions
   {
      int id = INITIAL;                                           // initialize id of question (useful value given in try structure)
      do
      {
         System.out.println ("Available questions to answer:");   // print all unanswered questions
         for (int i = 0 ; i < numUQs; i ++)
         {
            System.out.println (unansweredQNAs.get(i));
         }
         if (numUQs > 0)                                          // if number of unanswered questions is greater than zero
         {                                                        // then admin starts choosing question to answer by entering id
            System.out.println ("Enter ID to answer a question (\"" + EXIT + "\" to stop): ");
            boolean valid = false;                                // boolean valid is set to false
            while (!valid)
            {
               try
               {
                  id = sc.nextInt();                              // if user enters an int that is either the exit number or within the reasonable range
                  if (id == EXIT || id>= 1 && id <= numUQs)       // (from 1 to number of unasnwered questions)
                  {
                     valid = true;                                // then valid is true
                  }
                  else
                  {                                               // otherwise admin has to re-enter until input is valid
                     System.out.print ("Invalid input, enter again: ");
                  }
               }  
               catch (InputMismatchException ime)                 // catch any input mismatch
               {  
                  System.out.print ("Invalid input, enter again: ");
                  sc.nextLine();
               }
            }
            
            System.out.println ();
            if (id != EXIT)                                       // if admin doesn't choose to exit
            {
               answerQ(id);                                       // call the answerQ method to answer chosen question
            } 
         }
         else                                                     // if there's no unanswered question found
         {
            System.out.println ("None");                          // output none
            id = EXIT;                                            // set id to equal exit number
            System.out.println ();
         }      
      } while (id != EXIT);                                       // this process is repeated until id is set to be equal to exit number
   } // answerPrompt method
      
   public static boolean answerQ (int id)                         // this method takes admin through the process of answering a chosen question
   {                                              // parametre: id of chosen question
                                                     // returns: a boolean indicating whether question is successfully answered
      for (int i = 0; i < numUQs; i ++)                           // go through the number of unanswered questions
      {
         QNA temp = unansweredQNAs.get(i);
         int tempId = temp.getId();                               // assign temporary unanswered QNA object an id value 
         if (id == tempId)                                        // if the temp value equals chosen id
         {
            String answer;                                        // then declare the answer variable
            System.out.println ("Question: " + temp.getQuestion());// print the chosen question again
            System.out.println ("Your answer (enter \"" + EXIT + "\" to not answer this question): ");
            sc.nextLine();
            answer = sc.nextLine();                                // user can choose to answer or not answer this question
            System.out.println ();
            
            if (!answer.equals(EXITSTR))                           // if user doesn't enter the exit number
            {
               temp.setAnswer (answer);                            // the answer will be set for the unanswered QNA object
               post (temp);                                        // call the post method to upload the unanswered QNA object to array list
               return true;                                        // successfully answered the question
            }
            else
               return false;                                       // return false if user chooses to not answer
         }
      }
      return false;
   } // answerQ method
   
   private static void post (QNA newQ)                            // this method uploads the newly answered QNA object to answered array list, and removes it from 
   {                                                              // the unanswered array list
                                                     // parametre: a QNA object that is going to be uploaded to the array list
      int newId;                                                  // a new id will be assigned to that QNA object since it's moving to a new array list
      numQs ++;                                                   // number of answered questions increase by 1 
      newId = numQs;                                              // the new id will just be the number of questions in the array list
      newQ.setId (newId);                                         // give new id value to the newly answered QNA object
      answeredQNAs.add(newQ);                                     // add it to array list
      if (unansweredQNAs.remove(newQ))                            // if the question is successfully removed from original array list
      {
         numUQs --;                                               // number of unanswered questions decreases by 1

         for (int i = 0; i < numUQs; i ++)                        // go through the list of unanswered questions 
         {
            QNA temp = unansweredQNAs.get(i);
            temp.setId (i+1);                                     // re-assign ids in numerical order for every one of them since a question has been removed
         }
         System.out.println ("The new Q&A has been uploaded.");   // print a sucess message
         if (numUQs == 0)                                         // flush out enter key 
         {
            System.out.println ();
            System.out.println ("*Press enter key to continue if you don't see a saving prompt:");
         }
      }
      System.out.println ();
   }
   
   private static void deletePrompt (ArrayList<Integer> possibleIds)    // this method prompts admin to delete available questions
   {                                                                    // parametre: an array list of ids of possible questions to be selected
         int id = INITIAL;                                              // initialize id of question to be deleted
         do
         {
            System.out.println ("Available questions to delete:");
            boolean available = false;                                  // available is initialized to false, the variable will be set to true
                                                                        // when any available questions belonging to the chosen category is found
            for (int i = 0; i < possibleIds.size(); i ++)
            {
               int possibleId = possibleIds.get(i);                     // go through each possible id, and assign temporary variable "possibleId" one value at a time
               boolean found = false;                                   // initialize found to false, meaning the id hasn't been found from the array list of unanswered questions
               int beginning = 0, ending = unansweredQNAs.size()-1;     // use binary search: beginning index of array list: 0, ending: array list size-1                               
               int middle = INITIAL;                                    // index of middle value initialized
                                                         
               while (!found && beginning <= ending)           // while not found and not completely searched (beginning <= ending)
               {
                  middle = (beginning+ending)/2;               // middle value is equal to the average of beginning and ending indices
                  QNA temp = unansweredQNAs.get(middle);       // temporary object is equal to middle value in the array list of unasnwered questions
                  int tempId = temp.getId();                   // the id for the temporary object is obtained
                  if (tempId == possibleId)                    // if the temp id is equal to the current "possible id"
                  {
                     found = true;                             // found is true 
                     available = true;                         // some questions are available, so set available to true
                     System.out.println (temp);                // print the available question             
                  }
                  else if (tempId < possibleId)                // second half of the existing list will be searched next time
                     beginning = middle + 1;                   // if middle value is smaller than required number
                  else
                     ending = middle - 1;                      // else search first half of the list 
               }     
            }
               
            if (available)                                     // if some questions are available for admin to delete
            {
               System.out.println ("Enter ID to delete a question (\"" + EXIT + "\" to stop): ");
            
                                                               // then admin has a chance to enter the id of the question they want to delete
               boolean valid = false;                          // boolean variable valid is set to false
               while (!valid)
               {
                  boolean found = false;                       // set boolean variable found to false, this variable is used to check if the id
                                                               // chosen by admin belongs to the list of possible ids
                  try
                  {
                     id = sc.nextInt();         
                     
                     if (id != EXIT)                           // if user doesn't enter the exit number for id then we start looking through 
                     {                                         // the list of possible ids and determine whether user entered a valid id
                        for (int i = 0; i < possibleIds.size() && !found; i ++) 
                        {
                           if (possibleIds.get(i) == id)       // if the user entered id is equal to any of the possible ids
                           found = true;                       // then set found to true
                        }
                     }
                     else
                     {
                        valid = true;                          // else if user entered the exit number, valid is true
                     }        
                        
                     if (found)                                // check if the id is found, if it is
                     {
                        valid = true;                          // then valid should be set to true
                     }
                     
                     if (!valid)                               // if input id is invalid, output a message and ask user to reenter
                     {
                        System.out.print ("Invalid input, enter again: ");
                     }
      
                  }  
                  catch (InputMismatchException ime)           // catch any input mismatch exception
                  {  
                     System.out.print ("Invalid input, enter again: ");
                     sc.nextLine();
                  }
               }
               System.out.println ();
               
               if (id != EXIT)                                       // if admin doesn't wish to exit
               {
                  if (deleteQ(id))                                   // call deleteQ method to delete question, and if done successfully
                  {
                     possibleIds.remove(Integer.valueOf(id));        // remove the id of deleted question from list of possible ids
                     for (int i = 0; i < possibleIds.size(); i ++)   
                     {
                        int temp = possibleIds.get(i);               // temporary id used to store value of current id in the array list
                        if (temp>id)                                 // if the temporary question comes after the deleted question 
                           possibleIds.set(i,Integer.valueOf(temp-1)); // move its id up by one since a question before it has been removed
                     }
                  }
               }
              
               //sc.nextLine();
            }
            else                                                     // if there are no available questions to delete
            {
               System.out.println ("None");                          // output none
               id = EXIT;                                            // set id to exit number
               System.out.println ();
            }
         } while (id != EXIT);                                       // this process is repeated until id is set to equal the exit number
   } // deletePrompt method

   private static void deletePrompt ()                               // this method prompts admin to delete questions when browsing all unanswered questions
   {
         int id = INITIAL;                                           // id of question to be deleted is initialized
         do
         {
            System.out.println ("Available questions to delete:");   
            for (int i = 0; i < numUQs; i ++)                        // print every unanswered questions
            {
               System.out.println (unansweredQNAs.get(i));
            }
            if (numUQs > 0)                                          // if there is more than zero unanswered questions
            {                                                        // then admin can enter ids to delete them
               System.out.println ("Enter ID to delete a question (\"" + EXIT + "\" to stop): ");
            
               boolean valid = false;                                // set boolean valid to false
               while (!valid)
               {
                  try
                  {
                     id = sc.nextInt();
                     if (id == EXIT || id>=1 && id<=numUQs)          // if user enters an int that either equals the exit number or is within the
                     {                                               // reasonable range (from 1 to number of unanswered questions)
                        valid = true;                                // valid is true
                     }
                     else
                     {                                               // otherwise user will be prompted to enter id again
                        System.out.print ("Invalid input, enter again: ");
                     }
                  }  
                  catch (InputMismatchException ime)                 // catch any input mismatch exception
                  {  
                     System.out.println ("Invalid input, enter again: ");
                     sc.nextLine();
                  }
               }
               System.out.println ();
               
               if (id != EXIT)                                       // if user doesn't wish to exit
               {
                  deleteQ(id);                                       // call the deleteQ method to delete chosen question
               }
            //sc.nextLine(); this seems to work when we tested the QNASystem but Information System only works without this line...
            }
            else                                                     // if no available unanswered questions found
            {
               System.out.println ("None");                          // output none
               id = EXIT;                                            // set id to exit number
               System.out.println ();
            }
         } while (id != EXIT);                                       // this process is repeated until id is set to equal exit number
   }  // deletePrompt method
   
   private static boolean deleteQ (int id)                           // this method takes admin through the process of deleting a question
   {                                                                 // parametre: the id of the question to be deleted
      boolean found = false;                                         // returns: a boolean indicating whether question is deleted successfully 
      for (int i = 0; i < numUQs && !found; i ++)                              
      {
         QNA temp = unansweredQNAs.get(i);                           // if temporary QNA object id matches what user entered
         int tempId = temp.getId();
         if (id == tempId)
         {
            found = true;
            System.out.println ("Question: " + temp.getQuestion());  // output the question and ask user to confirm whether they want to delete it
            System.out.println ("Do you want to delete this question?");
            System.out.println ("1. Yes " + "2. No");
            System.out.print ("Enter your choice: ");
            int choice = INITIAL;                                    // variable choice is initialized
            
            boolean valid = false;
            while (!valid)
            {
               try
               {
                  choice = sc.nextInt();                             // user enters choice until it's either 1 or 2
                  if (choice == 1 || choice == 2)                    // this extra step is to ensure user will not accidentally delete anything
                  {
                     valid = true;                                   // then valid becomes true
                  }
                  else
                  {                                                  // otherwise prompt user to enter a useful value
                     System.out.print ("Invalid input, enter again: ");
                  }
               }  
               catch (InputMismatchException ime)                    // catch any input mismatch exception
               {  
                  System.out.print ("Invalid input, enter again: ");
                  sc.nextLine();
               }
            }
            System.out.println ();
            
            if (choice == 1)                                         // if user chooses to delete the question
            {
               unansweredQNAs.remove(temp);                          // the QNA object (represented by temp) will be removed from array list
               numUQs --;                                            // number of questions decrease by 1
               for (int j = id-1; j < numUQs; j ++)                  // starting from one after the deleted question, go through the array list
               {
                  temp = unansweredQNAs.get(j);                      // each QNA object's id will be reset since a question before them is removed
                  temp.setId (j+1);
               }
               System.out.println ("The question has been deleted.");// output a success message
               return true;                                          // process done successfully
            }
            else
               return false;                                         // if user doesn't wish to delete question anymore, return false
         }
      }
      System.out.println ();
      return false;
   } // deleteQ method
   
   public static void addCategory ()            // this method helps admin add new categories to the array list of categories, and write new info to file            
   {
      String newCategory;                       // declare a new variable called newCategory
      sc.nextLine();
      do
      {
         System.out.println("\n--- Add Category ---");
         System.out.println ("Enter new category name (\""+ EXIT +"\" to return to previous menu): ");
         newCategory = sc.nextLine();           // admin enters the category name
         if (!newCategory.equals(EXITSTR))      // if exit number is not entered
         {
            categories.add(newCategory);        // add the new name to array list
            numCategory ++;                     // increase number of categories by 1
         }
         System.out.println ();
      } while (!newCategory.equals(EXITSTR));   // repeat until user enters exit number
      
      try
      {
         BufferedWriter out = new BufferedWriter (new FileWriter (CATEGORY)); // start writing to the category file 
         for (int i = 0; i < numCategory; i ++)                               // go through all categories      
         {
            out.write (categories.get(i));                                    // write each one to file
            out.newLine();
         }
         out.close();
      }
      catch (IOException iox)                                                 // catch any exception when writing to file
      {
         System.out.println ("Problem saving categories to file.");
      }
   } // addCategory method
   
} // QNAAdmin class
