import java.util.*;
import java.io.*;

public class Student extends User {
	private Scanner sc = new Scanner(System.in);

	private static final String MENU = "user_menus/Student_Menu.txt";
	public static final int OEN_LENGTH = 9;
	public static final int PASSING_MARK = 50;
	private String firstName;
	private String lastName;
	private String oen;
	private CourseTracker courseTracker;
	private boolean acceptedToUni;
	private int numMenuOptions;
	private String[] menuOptions;

	public Student(String username, String password, String firstName, String lastName, String oen, CourseTracker courseTracker, boolean accepted){
		super(username, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.oen = oen;
		this.courseTracker = courseTracker;
		acceptedToUni = accepted;
		loadMenu();
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getOEN(){
		return oen;
	}

	public CourseTracker getCourseTracker(){
		return courseTracker;
	}

	public int getNumCourses () {
		return courseTracker.getNumCourses();
	}

	public int getNumPassingCourses () {
		ArrayList<ActiveCourse> courseList = courseTracker.getCourseList();
		int numPassingCourses = 0;

		for (ActiveCourse course : courseList) {
			if (course.getMark() >= PASSING_MARK) {
				numPassingCourses++;
			}
		}
		return numPassingCourses;
	}

	public boolean getAcceptedToUni(){
		return acceptedToUni;
	}

	//mutators
	public void setFirstName(String s){
		firstName = s;
	}

	public void setLastName(String s){
		lastName = s;
	}

	public void setOEN(String s){
		oen = s;
	}

	public void setCourseTracker(CourseTracker c){
		courseTracker = c;
	}

	public void setAcceptedToUni(boolean b){
		acceptedToUni = b;
	}

	public boolean setPassword (String oldPass, String newPass) {
		if (oldPass.equals(password)) {
			password = newPass;
			return true;
		}
		return false;
	}

	public boolean setUsername (String oldPass, String newUsername) {
		if (oldPass.equals(password)) {
			username = newUsername;
			return true;
		}
		return false;
	}

	public void loadMenu () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(MENU));
			numMenuOptions = Integer.parseInt(in.readLine());
			in.readLine();

			menuOptions = new String[numMenuOptions];
			for (int i = 0; i < numMenuOptions; i++) {
				menuOptions[i] = in.readLine();
			}

			in.close();
		}
		catch(IOException iox){
			System.out.println("Error reading student menu from file.");
		}
	}

	//displays the menu for admin users
	public void displayMainMenu() {
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		boolean keepOnGoing = true;

		while (keepOnGoing) {

			System.out.println("\n--- Student Menu (Logged In As " + username + ") ---");
			for (int i = 0; i < numMenuOptions; i++) {
				System.out.println(menuOptions[i]);
			}

			while (!(choice > 0 && choice <= numMenuOptions)) {
				try {
						System.out.print("Enter an option: ");
						choice = sc.nextInt();
						sc.nextLine();
					} catch (InputMismatchException ime) {
						sc.nextLine();
						System.out.print("Invalid input. ");
				}
			}

			//executes the choice number
			switch (choice){
				case 1:
				   	QNAStudent.initQNA();
					QNAStudent.displayMenu();
				 	break;
				case 2:
					ProgramDatabase.displayMenu();
					break;
				case 3:
					viewCourses();
					break;
				case 4:
					addCourse();
					break;
				case 5:
					deleteCourse();
					break;
				case 6:
					updateCourseMark();
					break;
				case 7:
					changeUsername();
					break;
				case 8:
					changePassword();
					break;
				case 9:
					keepOnGoing = false;
					break;
			}
			choice = 0;
		}
	}

	public void displayCourseList () {
		ArrayList<ActiveCourse> courseList = courseTracker.getCourseList();

		System.out.printf("%n--- %s %s's Courses ---%n", firstName, lastName);
		for (int i = 0; i < courseList.size(); i++) {
			ActiveCourse course = courseList.get(i);
			System.out.printf("%d - %s (%.1f)%n", i+1, course.getCourseCode(), course.getMark());
		}
	}

	public void viewCourses () {
		displayCourseList();
		System.out.println("Enter anything to return to the previous menu: ");
		sc.nextLine();
	}

	public void addCourse () {
		final String EXIT = "-1";
		String courseCode;
		int mark = -1;

		System.out.println("\n--- Add Course ---");

		if (courseTracker.getNumCourses() >= CourseTracker.MAX_COURSES) {
			System.out.println("A student can only take 8 courses at a time! Please drop an existing course");
			System.out.println("and try again. Enter anything to return to the previous menu.");
			sc.nextLine();
			return;
		} else {
			System.out.print("Course Code: ");
			courseCode = sc.nextLine();
			while (!CourseTracker.isValidCourse(courseCode)) {
				System.out.println("That course is not offered at AY Jackson.");
				System.out.print("Enter a new course (enter " + EXIT + " to cancel): ");
				courseCode = sc.nextLine();
				if (courseCode.equals(EXIT)) {
					return;
				}
			}
			while (courseTracker.courseExists(courseCode)) {
				System.out.println("Already taking this course!");
				System.out.print("Enter a new course (enter " + EXIT + " to cancel): ");
				courseCode = sc.nextLine();
				if (courseCode.equals(EXIT)) {
					return;
				}
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

			ActiveCourse course = new ActiveCourse(courseCode, mark);
			courseTracker.addCourse(course);
			System.out.println("Course added successfully!");
		}
	}

	public void deleteCourse () {
		final int EXIT = -1;
		int choice = 0;
		ArrayList<ActiveCourse> courseList = courseTracker.getCourseList();
		displayCourseList();

		System.out.println("Enter the number associated with the course you wish to delete (or enter " + EXIT + " to cancel):");
		while (choice == 0) {
			try {
				choice = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException ime) {
				sc.nextLine();
			}
			
			if (choice == EXIT) {
				return;
			} else if (!(choice >= 1 && choice <= courseList.size())) {
				System.out.print("Invalid input! Please try again: ");
				choice = 0;
			}
		}

		ActiveCourse course = courseList.get(choice - 1);
		courseTracker.dropCourse(course);
		System.out.println("Course successfully droppped.");
	}

	//student update mark
	public void updateCourseMark () {
		final int EXIT = -1;
		int choice = 0;
		double mark = Double.NaN;
		ArrayList<ActiveCourse> courseList = courseTracker.getCourseList();
		displayCourseList();

		System.out.println("Enter the number associated with the course you wish to update (or enter " + EXIT + " to cancel):");
		while (choice == 0) {
			try {
				choice = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException ime) {
				sc.nextLine();
			}
			
			if (choice == EXIT) {
				return;
			} else if (!(choice >= 1 && choice <= courseList.size())) {
				System.out.print("Invalid input! Please try again: ");
				choice = 0;
			}
		}

		ActiveCourse course = courseList.get(choice - 1);

		System.out.printf("Enter a new mark for %s (or enter %d to cancel): ", course.getCourseCode(), EXIT);
		do {
			try {
				mark = sc.nextDouble();
				sc.nextLine();
			} catch (InputMismatchException ime) {
				sc.nextLine();
				System.out.println("Invalid input. Please try again: ");
			}
			if (mark == EXIT) {
				return;
			} else if (!course.updateMark(mark)) {
				mark = Double.NaN;
				System.out.println("Mark must be between " + ActiveCourse.MIN_MARK + " and " + ActiveCourse.MAX_MARK + ". Please try again: ");
			}
		} while (mark == Double.NaN);

		System.out.println("Mark updated successfully!");
	}

	public void changePassword () {
		final String EXIT = "-1";
		String password, newPassword, confirmNewPassword;

		System.out.print("Please re-enter your current password to confirm your identity: ");
		password = sc.nextLine();
		while (!password.equals(this.password)) {
			System.out.println("Incorrect password! Please try again (or enter " + EXIT + " to cancel):");
			System.out.print("Current password: ");
			password = sc.nextLine();
			if (password.equals(EXIT)) {
				return;
			}
		}

		System.out.print("Enter new password: ");
		newPassword = sc.nextLine();
		System.out.print("Confirm new password: ");
		confirmNewPassword = sc.nextLine();
		while (!newPassword.equals(confirmNewPassword)) {
			System.out.println("Passwords don't match! Enter anything to try again (enter " + EXIT + " to cancel).");
			String choice = sc.nextLine();
			if (choice.equals(EXIT)) {
				return;
			}
			System.out.print("Enter new password: ");
			newPassword = sc.nextLine();
			System.out.print("Confirm new password: ");
			confirmNewPassword = sc.nextLine();
		}

		this.password = newPassword;
		System.out.println("Password changed successfully.");
	}

	public void changeUsername () {
		final String EXIT = "-1";
		String password, newUsername, confirmNewUsername;

		System.out.print("Please re-enter your current password to confirm your identity: ");
		password = sc.nextLine();
		while (!password.equals(this.password)) {
			System.out.println("Incorrect password! Please try again (or enter " + EXIT + " to cancel):");
			System.out.print("Current password: ");
			password = sc.nextLine();
			if (password.equals(EXIT)) {
				return;
			}
		}

		System.out.print("Enter new username: ");
		newUsername = sc.nextLine();
		System.out.print("Confirm new username: ");
		confirmNewUsername = sc.nextLine();
		while (!newUsername.equals(confirmNewUsername)) {
			System.out.println("Usernames don't match! Enter anything to try again (enter " + EXIT + " to cancel).");
			String choice = sc.nextLine();
			if (choice.equals(EXIT)) {
				return;
			}
			System.out.print("Enter new username: ");
			newUsername = sc.nextLine();
			System.out.print("Confirm new username: ");
			confirmNewUsername = sc.nextLine();
		}

		this.username = newUsername;
		System.out.println("Username changed successfully.");
	}
}
