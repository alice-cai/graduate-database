/**
* InformationSystem.java
* This is the main class used for running the Information System program. It is
* resposible for loading and displaying the main proram menu, loggin users in,
* and registering new users.
*/

import java.io.*;
import java.util.*;

public class InformationSystem {
	private static final String MENU_FILE = "user_menus/Main_Menu.txt";
	private static final String EXIT = "-1";

	private static GraduateDatabase graduateDatabase = new GraduateDatabase();
	private static ProgramDatabase programDatabase = new ProgramDatabase();
	private UserDatabase userDatabase;

	private String[] menuOptions;
	private int numMenuOptions;
	private User currentUser;
	private boolean endProgram;
	private Scanner sc;

	public InformationSystem () {
		userDatabase = new UserDatabase();
		sc = new Scanner(System.in);
		endProgram = false;
		loadMenu();
		displayMenu();
	}

	public static GraduateDatabase getGraduateDatabase () {
		return graduateDatabase;
	}

	public static ProgramDatabase getProgramDatabase () {
		return programDatabase;
	}

	/**
	* Loads the main menu options from the text file.
	*/
	public void loadMenu () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(MENU_FILE));
			numMenuOptions = Integer.parseInt(in.readLine());
			in.readLine();
			menuOptions = new String[numMenuOptions];
			for (int i = 0; i < numMenuOptions; i++) {
				menuOptions[i] = in.readLine();
			}
			in.close();
		} catch (IOException iox) {
			System.out.println("Error reading main menu from file.");
		}
	}

	/**
	* Displays the main program menu until the user chooses to end the program. Saves
	* the user data before terminating the program.
	*/
	public void displayMenu () {
		do {
			System.out.println("\n--- Information System ---");
			for (int i = 0; i < numMenuOptions; i++) {
				System.out.println(menuOptions[i]);
			}
			processMenuChoice();
		} while (!endProgram);
		userDatabase.saveAdminList();
		userDatabase.saveStudentList();
	}

	/**
	* Repeatedly prompts the user to choose an option from the main menu until they enter
	* a valid choice. Calls the method corresponding to the option selected by the user.
	*/
	public void processMenuChoice () {
		int choice = -1;

		do {
			try {
				System.out.print("Enter your choice: ");
				choice = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException ime) {
				sc.nextLine();
				System.out.print("Invalid input. ");
			}
		} while (!(choice > 0 && choice <= numMenuOptions));

		switch (choice) {
			case 1:
				logInStudent();
				break;
			case 2:
				logInAdmin();
				break;
			case 3:
				currentUser = UserDatabase.GUEST;
				break;
			case 4:
				createStudentAccount();
				break;
			case 5:
				endProgram = true;
				break;
		}

		if (currentUser != null) {
			currentUser.displayMainMenu();
			currentUser = null;
		}

		choice = -1;
	}

	/**
	* Allows the user to log in as a Student. Prompts the user to input their username and
	* password. If their username and password match those of an existing Student, the
	* currentUser is set as that Student object. Otherwise, an error message is displayed
	* and the user is given the option to try logging in again or return to the previous menu.
	*/
	public void logInStudent () {
		String username, password;

		System.out.println("\n--- Student Login ---");

		System.out.print("Username: ");
		username = sc.nextLine();
		System.out.print("Password: ");
		password = sc.nextLine();

		while (userDatabase.searchStudentByLoginInfo(username, password) == null) {
			System.out.println("Invalid username or password! Enter anything to try");
			System.out.println("again. Enter -1 to return to the main menu.");
			String choice = sc.nextLine();
			if (choice.equals(EXIT)) {
				return;
			}

			System.out.println("\n--- Student Login ---");
			System.out.print("Username: ");
			username = sc.nextLine();
			System.out.print("Password: ");
			password = sc.nextLine();
		}

		currentUser = userDatabase.searchStudentByLoginInfo(username, password);
	}

	/**
	* Allows the user to log in as an Admin. Prompts the user to input their username and
	* password. If their username and password match those of an existing Admin, the
	* currentUser is set as that Admin object. Otherwise, an error message is displayed
	* and the user is given the option to try logging in again or return to the previous 
	* menu.
	*/
	public void logInAdmin () {
		String username, password;

		System.out.println("\n--- Admin Login ---");

		System.out.print("Username: ");
		username = sc.nextLine();
		System.out.print("Password: ");
		password = sc.nextLine();

		while (userDatabase.searchAdminByLoginInfo(username, password) == null) {
			System.out.println("Invalid username or password! Enter anything to try");
			System.out.println("again. Enter -1 to return to the main menu.");
			String choice = sc.nextLine();
			if (choice.equals(EXIT)) {
				return;
			}

			System.out.println("\n--- Admin Login ---");
			System.out.print("Username: ");
			username = sc.nextLine();
			System.out.print("Password: ");
			password = sc.nextLine();
		}

		currentUser = userDatabase.searchAdminByLoginInfo(username, password);
	}

	/**
	* Allows the user to register as a Student. Prompts the user for a username, a password,
	* their first name, their last name, their Ontario Education Number, and all their
	* courses. Creates a Student object for the user and adds them to the UserDatabase.
	*/
	public void createStudentAccount () {
		String username, password, confirmPassword, firstName, lastName, oen;
		int numCourses = 0;

		System.out.println("\n--- Student Registration ---");

		// prompts user for username (if their username is too short or is already taken
		// an error message is displayed and the user is prompted again)
		System.out.print("Username: ");
		username = sc.nextLine();
		while (username.length() < Student.MIN_USERNAME_LENGTH) {
			System.out.println("Username must be at least " + Student.MIN_USERNAME_LENGTH + " characters long.");
			System.out.print("Username: ");
			username = sc.nextLine();
		}
		while (!userDatabase.studentUsernameAvailable(username)) {
			System.out.println("Username unavailable. Please enter a new one.");
			System.out.print("Username: ");
			username = sc.nextLine();
		}

		// prompts user for password (if their password is too short an error message is
		// displayed and the user is prompted again)
		System.out.print("Password: ");
		password = sc.nextLine();
		while (password.length() < Student.MIN_PASSWORD_LENGTH) {
			System.out.println("Password must be at least " + Student.MIN_PASSWORD_LENGTH + " characters long.");
			System.out.print("Password: ");
			password = sc.nextLine();
		}

		// prompts user to confirm their password (if their password is too short an error
		// message is displayed and the user is prompted again)
		System.out.print("Confirm Password: ");
		confirmPassword = sc.nextLine();
		while (!confirmPassword.equals(password)) {
			System.out.println("Passwords don't match.");
			System.out.print("Confirm Password: ");
			password = sc.nextLine();
		}

		// prompts user for their name
		System.out.print("First Name: ");
		firstName = sc.nextLine();
		System.out.print("Last Name: ");
		lastName = sc.nextLine();

		// prompts user for their Ontario Education Number until they enter one that is valid
		System.out.print("OEN: ");
		oen = sc.nextLine();
		while (!checkValidOEN(oen)) {
			System.out.println("Invalid OEN. OEN must be 9 characters long and contain only digits.");
			System.out.print("OEN: ");
			oen = sc.nextLine();
		}
		while (userDatabase.searchStudentByOEN(oen) != null) {
			System.out.println("That OEN is taken! OENs should be unique to each student.");
			System.out.print("OEN: ");
			oen = sc.nextLine();
		}

		// prompts user for the number of courses that they are taking
		do {
			try {
				System.out.print("How many courses are you taking? ");
				numCourses = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException ime) {
				sc.nextLine();
			}
			if (!(numCourses > 0 && numCourses <= CourseTracker.MAX_COURSES)) {
				System.out.println("Invalid input. Please enter a number between 1 and 8.");
				numCourses = 0;
			}
		} while (numCourses == 0);

		ArrayList<ActiveCourse> courseList = new ArrayList<>();

		// prompts user to enter all their course information (outputs an error message
		// if the user enters a course that is not offered at AY Jackson)
		for (int i = 0; i < numCourses; i++) {
			String courseCode;
			int mark = -1;

			System.out.print("Course: ");
			courseCode = sc.nextLine();
			while (!CourseTracker.isValidCourse(courseCode)) {
				System.out.println("That course is not offered at AY Jackson.");
				System.out.print("Enter a new course: ");
				courseCode = sc.nextLine();
			}

			do {
				try {
					System.out.print("Mark: ");
					mark = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException ime) {
					sc.nextLine();
				}
				if (!(mark >= ActiveCourse.MIN_MARK && mark <= ActiveCourse.MAX_MARK)) {
					System.out.println("Invalid input.");
					mark = -1;
				}
			} while (mark == -1);

			courseList.add(new ActiveCourse(courseCode, mark));
		}


		// constructs a Student object and adds it to the UserDatabase
		Student student = new Student(username, password, firstName, lastName, oen, new CourseTracker(courseList));
		userDatabase.addStudent(student);

		// output confirmation message
		System.out.println("Registration successful.");
	}

	/**
	* Takes in an oen number and checks to see if it is valid. An OEN is
	* valid if it is 9 characters long and contains only digits.
	*/
	public boolean checkValidOEN (String oen) {
		int oenLength = oen.length();

		if (oenLength < Student.OEN_LENGTH) {
			return false;
		}

		for (int i = 0; i < oenLength; i++) {
			char curChar = oen.charAt(i);
			if (curChar >= '0' && curChar <= '9') {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
}