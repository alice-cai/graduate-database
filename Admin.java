/**
* Admin.java
* Stores all information about a single Admin object. Responisble for storing
* and displaying menu options for Admin.
*/

import java.util.*;
import java.io.*;

public class Admin extends User {
	// constants that store the names of the files that contain the menu options
	private final String MAIN_MENU = "user_menus/Admin_Menu.txt";
	private final String UPDATE_STUDENT_INFO_MENU = "user_menus/Admin_Menu_UpdateStudentInfo.txt";

	// stores information about the main Admin menu
	private int numMenuOptions;
	private String[] menuOptions;

	// stores information about the "Update Student Info" Admin menu
	private int numUpdateStudentMenuOptions;
	private String[] updateStudentMenuOptions;

	private String adminNumber;
	private ArrayList<Student> studentList;
	private Scanner sc;

	public Admin(String username, String password, String adminNumber, ArrayList<Student> studentList){
		super(username, password);
		this.adminNumber = adminNumber;
		this.studentList = studentList;
		sc = new Scanner(System.in);
		loadMainMenu();
		loadUpdateCourseMenu();
	}

	public String getUsername(){
		return username;
	}

	public String getPassword(){
		return password;
	}

	public String getAdminNumber(){
		return adminNumber;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public void setAdminNumber(String num){
		adminNumber = num;
	}

	/**
	* Loads all main menu options from the MAIN_MENU text file. Stores number of menu options 
	* in numMenuOptions and stores options in menuOptions array.
	*/
	public void loadMainMenu () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(MAIN_MENU));
			numMenuOptions = Integer.parseInt(in.readLine());
			in.readLine();

			menuOptions = new String[numMenuOptions];
			for (int i = 0; i < numMenuOptions; i++) {
				menuOptions[i] = in.readLine();
			}

			in.close();
		} catch(IOException iox){
			System.out.println("Error reading admin menu from file.");
		}
	}

	/**
	* Loads all main menu options from the UPDATE_STUDENT_INFO_MENU text file. Stores
	* number of menu options in numUpdateStudentMenuOptions and stores options in
	* updateStudentMenuOptions array.
	*/
	public void loadUpdateCourseMenu () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(UPDATE_STUDENT_INFO_MENU));
			numUpdateStudentMenuOptions = Integer.parseInt(in.readLine());
			in.readLine();

			updateStudentMenuOptions = new String[numUpdateStudentMenuOptions];
			for (int i = 0; i < numUpdateStudentMenuOptions; i++) {
				updateStudentMenuOptions[i] = in.readLine();
			}

			in.close();
		} catch(IOException iox){
			System.out.println("Error reading admin's \"update student course menu\" from file.");
		}
	}

	/**
	* Displays the main menu of options for Admin users and prompts the user to
	* select an option. Calls the method corresponding to the option selected by
	* the user.
	*/
	public void displayMainMenu () {
		int choice = 0;
		boolean loggedIn = true;

		// while the user is logged in, keep displaying the user's main menu
		while(loggedIn){
			System.out.println("\n--- Admin Menu (Logged In As " + username + ") ---");
			for (int i = 0; i < numMenuOptions; i++) {
				System.out.println(menuOptions[i]);
			}

			// prompts the user to input a choice until they input a valid one
			while (choice == 0) {
				try {
					System.out.print("Enter your choice: ");
					choice = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException ime) {
					sc.nextLine();
				}

				if (!(choice > 0 && choice <= numMenuOptions)) {
					System.out.println("Invalid input!");
					choice = 0;
				}
			}

			// calls the method that corresponds to the option chosen by the user
			switch (choice){
				case 1:
					QNAAdmin.initQNA();
					QNAAdmin.displayMenu();
					break;
				case 2:
					InformationSystem.getProgramDatabase().displayMenu();
					break;
				case 3:
					InformationSystem.getProgramDatabase().addProgram();
					break;
				case 4: 
					InformationSystem.getProgramDatabase().deleteProgram();
					break;
				case 5:
					displayStudentList();
					break;
				case 6:
					displayGraduateList();
					break;
				case 7:
					loggedIn = false;
					break;
			}
			choice = 0;
		}
	}

	/**
	* Displays a numbered list of all the students in the database. Prompts the user to select
	* a student by inputting the corresponding number. Once the user has inputted a valid number,
	* this method calls displayUpdateStudentInfoMenu(Student student), passing the selected
	* student as the argument.
	*/
	private void displayStudentList () {
		final int EXIT = -1;
		int choice = 0;

		// keeps displaying the list of students until the user chooses to return to the main menu
		while (true) {
			// output list of students
			System.out.println("\n--- Student Database ---");
			for (int i = 0; i < studentList.size(); i++) {
				Student student = studentList.get(i);
				System.out.printf("%2d - %s %s (%s)%n", i+1, student.getFirstName(), student.getLastName(), student.getOEN());
			}

			// prompt user for input until they enter a valid number
			System.out.println("Enter a student's index to update their information. Enter " + EXIT + " to return to the previous menu.");
			do {
				try {
					System.out.print("Enter your choice: ");
					choice = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException ime) {
					sc.nextLine();
				}

				if (choice == EXIT) {
					return;
				} else if (!(choice > 0 && choice <= studentList.size() + 1)) {
					System.out.println("Invalid input!");
					choice = 0;
				}
			} while (choice == 0);


			// displays the "Update Student Info" menu for the selected student
			Student student = studentList.get(choice-1);
			displayUpdateStudentInfoMenu(student);
		}
	}

	/**
	* Displays the list of options for updating the argument student's information.
	* Prompts the user to select an option and calls the method corresponding to
	* the option selected.
	*
	* @param student - the student whose information is being updated
	*/
	private void displayUpdateStudentInfoMenu (Student student) {
		final int EXIT = -1;
		int choice = 0;
		boolean displayStudentInfoMenu = true;

		while (true) {
			// display menu
			System.out.printf("%n--- Update %s %s's Information ---%n", student.getFirstName(), student.getLastName());
			for (int i = 0; i < updateStudentMenuOptions.length; i++) {
				System.out.println(updateStudentMenuOptions[i]);
			}

			// prompts the user to select an option from the menu until they enter a valid option
			while (choice == 0) {
				try {
					System.out.print("Enter your choice (or enter " + EXIT + " to cancel): ");
					choice = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException ime) {
					sc.nextLine();
				}

				if (choice == EXIT) {
					return;
				} else if (!(choice > 0 && choice <= numUpdateStudentMenuOptions)) {
					System.out.println("Invalid input!");
					choice = 0;
				}
			}

			// calls the method corresponding to the selection option
			switch (choice){
				case 1:
					student.viewCourses();
					break;
				case 2:
					student.addCourse();
					break;
				case 3:
					student.deleteCourse();
					break;
				case 4: 
					student.updateCourseMark();
					break;
				case 5:
					if (moveToGraduateDatabase(student)) {
						return;
					}
					break;
				case 6:
					if (confirmStudentDeletion()) {
						studentList.remove(student);
						System.out.println("Student removed. Enter any key to go back to the previous menu.");
						sc.nextLine();
						return;
					}
					break;
			}
			choice = 0;
		}
	}

	/**
	* Displays a numbered list of all the graduates in the database. Prompts the user to select
	* a graduate by inputting the corresponding number. Once the user has inputted a valid number,
	* this method calls displays all the information stored about the selected graduate.
	*/
	private void displayGraduateList () {
		final int EXIT = -1;
		ArrayList<Graduate> graduateList = InformationSystem.getGraduateDatabase().getGraduateList();
		int choice = 0;

		while (true) {
			System.out.println("\n--- Graduate Database ---");
			for (int i = 0; i < graduateList.size(); i++) {
				Graduate graduate = graduateList.get(i);
				System.out.printf("%2d - %s %s (%s)%n", i+1, graduate.getFirstName(), graduate.getLastName(), graduate.getOEN());
			}

			System.out.println("Enter a graduate's index to view their information. Enter " + EXIT + " to return to the previous menu.");
			do {
				try {
					System.out.print("Enter your choice: ");
					choice = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException ime) {
					sc.nextLine();
				}

				if (choice == EXIT) {
					return;
				} else if (!(choice > 0 && choice <= InformationSystem.getGraduateDatabase().getNumGraduates() + 1)) {
					System.out.println("Invalid input!");
					choice = 0;
				}
			} while (choice == 0);

			System.out.println(graduateList.get(choice-1));
			System.out.println("Enter any key to return to the previous menu: ");
			sc.nextLine();
			choice = 0;
		}
	}

	/**
	* This method first checks if the argument student has enough credits to graduate. If
	* so, the method outputs a message describing what will happen once the student is moved
	* to the GraduateDatabase. It then asks the user the confirm their decision to move the
	* student. If they do not confirm their choice, they will be taken back to the previous
	* menu. If they do confirm their choice, they will be asked to select the six courses
	* that the student used for their top six and the program to which the student got
	* accepted. It then outputs a confirmation message telling the user that the student
	* has successfully been moved to the GraduateDatabase.
	*
	* @param student - the student that is to be moved to the graduate databse
	* @return boolean indicating whether the student has been moved
	*/
	private boolean moveToGraduateDatabase (Student student) {
		final String CONFIRMATION = "CONFIRM";

		System.out.println("\n--- Move Student to Graudate Database ---");

		// if the student does not have enough credits to graduate, output an error message
		// and take the user back to the previous menu
		if (student.getNumPassingCourses() < GraduateDatabase.NUM_COURSES) {
			System.out.println("This student does not have enough credits to graduate! Enter any");
			System.out.println("key to return to the previous menu.");
			sc.nextLine();
			return false;
		}

		// output a message describing to the user what wil happen once the student is moved to
		// the graduate database and asks the user to confirm their choice
		System.out.println("Are you sure you want to move this student to the graduate database? You will no");
		System.out.println("longer be able to modify this student's information and their account will be closed.");
		System.out.println("Enter \"" + CONFIRMATION + "\" to confirm. Enter anything else to cancel.");
		String input = sc.nextLine();

		// if the user chooses not to proceed, take the user back to the previous menu
		if (!input.equalsIgnoreCase(CONFIRMATION)) {
			return false;
		}

		ArrayList<ActiveCourse> currentCourses = student.getCourseTracker().getCourseList();
		ArrayList<Integer> selectedCourses = new ArrayList<>();
		int choice = 0;

		// display a numbered list of the student's courses and ask the user to select the six courses
		// that the student used to apply to their program
		student.displayCourseList();
		System.out.println("\nEnter the indicies of the courses that the student used for their top six:");
		for (int i = 0; i < GraduateDatabase.NUM_COURSES; i++) {
			while (choice == 0) {
				try {
					System.out.print("Course #" + (i+1) + ": ");
					choice = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException ime) {
					sc.nextLine();
				}

				if (!(choice > 0 && choice <= currentCourses.size()) || selectedCourses.contains(choice-1)) {
					System.out.println("Invalid input!");
					choice = 0;
				}
			}
			selectedCourses.add(choice-1);
			choice = 0;
		}

		// convert all ActiveCourse objects to Course objects and store them in an array
		Course[] topSixCourses = new Course[GraduateDatabase.NUM_COURSES];
		int markSum = 0;
		for (int i = 0; i < selectedCourses.size(); i++) {
			int courseIndex = selectedCourses.get(i);
			ActiveCourse selectedCourse = currentCourses.get(courseIndex);
			topSixCourses[i] = new Course(selectedCourse.getCourseCode(), selectedCourse.getMark());
			markSum += selectedCourse.getMark();
		}

		// calculate the student's top six average based on their top six courses
		double topSixAverage = (double) markSum / GraduateDatabase.NUM_COURSES;

		// prompts user to select a program from a given list
		System.out.println("\nTo which program did this student get accepted?");
		String[] programList = GraduateDatabase.PROGRAMS;
		for (int i = 0; i < programList.length; i++) {
			System.out.printf("%2d - %s%n", i + 1, programList[i]);
		}
		while (choice == 0) {
			try {
				System.out.print("Program: ");
				choice = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException ime) {
				sc.nextLine();
			}

			if (!(choice > 0 && choice <= GraduateDatabase.PROGRAMS.length)) {
				System.out.println("Invalid input!");
				choice = 0;
			}
		}

		int programID = choice - 1;
		String university = programList[programID];

		// sends all the student's information to the GraduateDatabase, where it will be archived
		InformationSystem.getGraduateDatabase().addGraduate(student.getOEN(), student.getFirstName(), student.getLastName(), topSixCourses, topSixAverage, university, programID);
		studentList.remove(student);  // remove student account
		System.out.println("Student moved to graduate file."); // print confirmation message
		return true;
	}

	/**
	* This method outputs a message describing what will happen after a student account
	* is removed from the database and asks the user to confirm their decision to remove
	* the student. If they confirm, the method returns true. Otherwise, the method returns
	* false.
	*
	* @return boolean indicating whether the user has confirmed the deletion
	*/
	private boolean confirmStudentDeletion () {
		final String CONFIRMATION = "CONFIRM DELETION";

		System.out.println("\n--- Remove Student Account ---");
		System.out.println("Are you sure you want to remove this student from the database? This action");
		System.out.println("cannot be undone. Enter \"" + CONFIRMATION + "\" to delete the student. Enter");
		System.out.println("anything else to return to the previous menu.");
		String input = sc.nextLine();
		if (input.equalsIgnoreCase(CONFIRMATION)) {
			return true;
		}
		return false;
	}
}
