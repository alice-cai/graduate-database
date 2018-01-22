import java.io.*;
import java.util.*;

public class InformationSystem {
	private static String MENU_FILE = "user_menus/Main_Menu.txt";
	private static String EXIT = "-1";
	private UserDatabase users;
	private GraduateDatabase graduateDatabase;
	private User currentUser;
	private String[] menuOptions;
	private int numMenuOptions;
	private Scanner sc;
	private boolean endProgram;

	public InformationSystem () {
		graduateDatabase = new GraduateDatabase();
		users = new UserDatabase(graduateDatabase);
		ProgramDatabase.initialize();
		sc = new Scanner(System.in);
		endProgram = false;
		loadMenu();
		displayMenu();
	}

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

	public void displayMenu () {
		do {
			System.out.println("\n--- Information System ---");
			for (int i = 0; i < numMenuOptions; i++) {
				System.out.println(menuOptions[i]);
			}
			processMenuChoice();
		} while (!endProgram);
		users.saveAdminList();
		users.saveStudentList();
	}

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

	public void logInStudent () {
		String username, password;

		System.out.println("\n--- Student Login ---");

		System.out.print("Username: ");
		username = sc.nextLine();
		System.out.print("Password: ");
		password = sc.nextLine();

		while (users.searchStudentByLoginInfo(username, password) == null) {
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

		currentUser = users.searchStudentByLoginInfo(username, password);
	}


	public void logInAdmin () {
		String username, password;

		System.out.println("\n--- Admin Login ---");

		System.out.print("Username: ");
		username = sc.nextLine();
		System.out.print("Password: ");
		password = sc.nextLine();

		while (users.searchAdminByLoginInfo(username, password) == null) {
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

		currentUser = users.searchAdminByLoginInfo(username, password);
	}

	public void createStudentAccount () {
		final int MIN_USERNAME_LENGTH = 4;
		final int MIN_PASSWORD_LENGTH = 6;
		String username, password, firstName, lastName, oen;
		int numCourses = 0;

		System.out.println("\n--- Student Registration ---");

		System.out.print("Username: ");
		username = sc.nextLine();
		while (username.length() < MIN_USERNAME_LENGTH) {
			System.out.println("Username must be at least " + MIN_USERNAME_LENGTH + " characters long.");
			System.out.print("Username: ");
			username = sc.nextLine();
		}
		while (!users.studentUsernameAvailable(username)) {
			System.out.println("Username unavailable. Please enter a new one.");
			System.out.print("Username: ");
			username = sc.nextLine();
		}
		System.out.print("Password: ");
		password = sc.nextLine();
		while (password.length() < MIN_PASSWORD_LENGTH) {
			System.out.println("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
			System.out.print("Password: ");
			password = sc.nextLine();
		}
		System.out.print("First Name: ");
		firstName = sc.nextLine();
		System.out.print("Last Name: ");
		lastName = sc.nextLine();
		System.out.print("OEN: ");
		oen = sc.nextLine();
		while (!checkValidOEN(oen)) {
			System.out.println("Invalid OEN number. OEN must be 9 characters long and contain only digits.");
			System.out.print("OEN: ");
			oen = sc.nextLine();
		}
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

		Student student = new Student(username, password, firstName, lastName, oen, new CourseTracker(courseList), false);
		users.addStudent(student);

		System.out.println("Registration successful.");
	}

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

	public static void main (String[] args) {
		InformationSystem is = new InformationSystem();
	}
}
