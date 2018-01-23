/**
* Student.java
* This class represents a single Student user. It stores the student's username,
* password, name, OEN, and courses.
*/

import java.util.*;
import java.io.*;

public class Student extends User {
	private static final String MENU = "user_menus/Student_Menu.txt";
	private static final String EXIT = "-1";
	public static final int MIN_USERNAME_LENGTH = 4;
	public static final int MIN_PASSWORD_LENGTH = 6;
	public static final int OEN_LENGTH = 9;
	public static final int PASSING_MARK = 50;

	private String firstName;
	private String lastName;
	private String oen;
	private CourseTracker courseTracker;

	private int numMenuOptions;
	private String[] menuOptions;
	private Scanner sc;

	public Student (String username, String password, String firstName, String lastName, String oen, CourseTracker courseTracker) {
		super(username, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.oen = oen;
		this.courseTracker = courseTracker;
		sc = new Scanner(System.in);
		loadMainMenu();
	}

	public String getFirstName () {
		return firstName;
	}

	public String getLastName () {
		return lastName;
	}

	public String getOEN () {
		return oen;
	}

	public CourseTracker getCourseTracker () {
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

	public void setFirstName (String firstName) {
		this.firstName = firstName;
	}

	public void setLastName (String lastName) {
		this.lastName = lastName;
	}

	public void setOEN (String oen) {
		this.oen = oen;
	}

	public boolean setPassword (String curPass, String newPass) {
		if (curPass.equals(password)) {
			password = newPass;
			return true;
		}
		return false;
	}

	public boolean setUsername (String curPass, String newUsername) {
		if (curPass.equals(password)) {
			username = newUsername;
			return true;
		}
		return false;
	}

	/**
	* Reads in the main menu options from the MENU text file and stores them in
	* the menuOptions array.
	*/
	public void loadMainMenu () {
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

	/**
	* Displays the Student main menu, prompts the user to choose a menu option, and
	* calls the method corresponding to the user's choice.
	*/
	public void displayMainMenu () {
		Scanner sc = new Scanner(System.in);
		int choice = 0;

		while (true) {
			// display menu options
			System.out.println("\n--- Student Menu (Logged In As " + username + ") ---");
			for (int i = 0; i < numMenuOptions; i++) {
				System.out.println(menuOptions[i]);
			}

			// prompts user to enter an option until they enter a valid option
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

			// calls the method that corresponds to the option selected by the user
			switch (choice){
				case 1:
				   	QNAStudent.initQNA();
					QNAStudent.displayMenu();
				 	break;
				case 2:
					InformationSystem.getProgramDatabase().displayMenu();
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
					getTopSixAverage();
					break;
				case 8:
					changeUsername();
					break;
				case 9:
					changePassword();
					break;
				case 10:
					return;
			}
			choice = 0;
		}
	}

	/**
	* Displays a numbered list of this Student's courses.
	*/
	public void displayCourseList () {
		ArrayList<ActiveCourse> courseList = courseTracker.getCourseList();

		System.out.printf("%n--- %s %s's Courses ---%n", firstName, lastName);
		for (int i = 0; i < courseList.size(); i++) {
			ActiveCourse course = courseList.get(i);
			System.out.printf("%d - %s (%.1f)%n", i+1, course.getCourseCode(), course.getMark());
		}
	}

	/**
	* Calls the displayCourseList() method. Prompts the user to enter any key when
	* they are done viewing the list.
	*/
	public void viewCourses () {
		displayCourseList();
		System.out.println("Enter anything to return to the previous menu: ");
		sc.nextLine();
	}

	/**
	* Checks if this Student is already taking the maximum number of courses. If so, this
	* method outputs an error message and takes the student back to the main menu. If not,
	* this method prompts the student for information about the course they are adding.
	*/
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
			while (courseTracker.courseFound(courseCode)) {
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

	/**
	* Displays the list of courses taken by this Student and prompts the student to select
	* the course that they would like to delete. Once the user has selected a course, this
	* method removes it from the CourseTracker and outputs a confirmation message.
	*/
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
		System.out.println("Course successfully dropped.");
	}

	/**
	* Displays the list of courses taken by this Student and prompts the user to select
	* the course that they would like to update. Once the user has selected a course,
	* this method prompts the user for the new course mark. It then replaces the old
	* mark with the new one and outputs a confirmation message.
	*/
	public void updateCourseMark () {
		final int EXIT = -1;
		int choice = 0;
		double mark = Integer.MAX_VALUE;
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
				mark = Integer.MAX_VALUE;
				System.out.println("Mark must be between " + ActiveCourse.MIN_MARK + " and " + ActiveCourse.MAX_MARK + ". Please try again: ");
			}
		} while ((int)mark == Integer.MAX_VALUE);

		System.out.println("Mark updated successfully!");
	}

	/**
	* Displays the list of courses taken by this Student and prompts the user to select
	* the six courses that they would like to use for their top six. It then outputs the
	* Student's top six average using the selected courses.
	*/
	public void getTopSixAverage () {
		System.out.println("\n--- Top Six Average ---");
		if (courseTracker.getNumCourses() < GraduateDatabase.NUM_COURSES) {
			System.out.println("Not enough courses for top six! Enter anything to return to previous menu.");
			sc.nextLine();
			return;
		}

		displayCourseList();

		ArrayList<ActiveCourse> currentCourses = courseTracker.getCourseList();
		ArrayList<Integer> selectedCourses = new ArrayList<>();
		int choice = 0;

		System.out.println("\nEnter the indicies of the courses that you are using for your top six:");
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

		Course[] topSixCourses = new Course[GraduateDatabase.NUM_COURSES];
		int markSum = 0;
		for (int i = 0; i < selectedCourses.size(); i++) {
			int courseIndex = selectedCourses.get(i);
			ActiveCourse selectedCourse = currentCourses.get(courseIndex);
			topSixCourses[i] = new Course(selectedCourse.getCourseCode(), selectedCourse.getMark());
			markSum += selectedCourse.getMark();
		}

		double topSixAverage = courseTracker.checkAverageTopSixRecursion(topSixCourses);

		System.out.printf("Average: %.2f%%%n", topSixAverage);
		System.out.println("Enter anything to return to the previous menu: ");
		sc.nextLine();
	}

	/**
	* Allows the user to change their password, provided that they successfully verify
	* their identity by entering their current password.
	*/
	public void changePassword () {
		final String EXIT = "-1";
		String password, newPassword, confirmNewPassword;

		System.out.println("\n--- Change Password ---");
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
		while (newPassword.length() < MIN_PASSWORD_LENGTH) {
			System.out.println("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.");
			System.out.print("Enter new password: ");
			newPassword = sc.nextLine();
		}
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

	/**
	* Allows the user to change their username, provided that they successfully verify
	* their identity by entering their current password.
	*/
	public void changeUsername () {
		final String EXIT = "-1";
		String password, newUsername, confirmNewUsername;

		System.out.println("\n--- Change Username ---");
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
		while (username.length() < MIN_USERNAME_LENGTH) {
			System.out.println("Username must be at least " + MIN_USERNAME_LENGTH + " characters long.");
			System.out.print("Enter new username: ");
			username = sc.nextLine();
		}
		System.out.print("Confirm new username: ");
		confirmNewUsername = sc.nextLine();
		while (!newUsername.equalsIgnoreCase(confirmNewUsername)) {
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

		this.username = newUsername.toLowerCase();
		System.out.println("Username changed successfully.");
	}
}
