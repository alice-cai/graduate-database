import java.util.*;
import java.io.*;

//Guest user class. Extends User
public class Admin extends User{

	private final String MAIN_MENU = "user_menus/Admin_Menu.txt";
	private final String UPDATE_STUDENT_INFO_MENU = "user_menus/Admin_Menu_UpdateStudentInfo.txt";
	private String adminNumber;
	private Scanner sc;
	private ArrayList<Student> studentList;
	private GraduateDatabase graduateDatabase;
	private ProgramDatabase programDatabase;
	private int numMenuOptions;
	private String[] menuOptions;
	private int numUpdateStudentMenuOptions;
	private String[] updateStudentMenuOptions;

	public Admin(String username, String password, String adminNumber, ArrayList<Student> studentList, GraduateDatabase graduateDatabase, ProgramDatabase programDatabase){
		super(username, password);
		this.adminNumber = adminNumber;
		this.studentList = studentList;
		this.graduateDatabase = graduateDatabase;
		this.programDatabase = programDatabase;
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

	public void setUsername(String user){
		username = user;
	}

	public void setPassword(String pass){
		password = pass;
	}

	public void setAdminNumber(String num){
		adminNumber = num;
	}

	/**
	* loadMainMenu()
	* Loads all main menu options from a MAIN_MENU text file. Stores number of menu options 
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
	* loadUpdateCourseMenu()
	* Loads all main menu options from a UPDATE_STUDENT_INFO_MENU text file. Stores number
	* of menu options in numUpdateStudentMenuOptions and stores options in updateStudentMenuOptions array.
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

	//displays the menu for admin users
	public void displayMainMenu(){
		int choice = 0;
		boolean keepOnGoing = true;

		while(keepOnGoing){	   
			System.out.println("\n--- Admin Menu (Logged In As " + username + ") ---");
			for (int i = 0; i < numMenuOptions; i++) {
				System.out.println(menuOptions[i]);
			}

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

			//executes the choice number				
			switch (choice){
				case 1:
					QNAAdmin.initQNA();
					QNAAdmin.displayMenu();
					break;
				case 2:
					//search programs
					break;
				case 3:
					// add program
					break;
				case 4: 
					//delete program
					break;
				case 5:
					displayStudentList();
					break;
				case 6:
					displayGraduateList();
					break;
				case 7:
					keepOnGoing = false;
					break;
			}
			choice = 0;
		}
	}

	private void displayStudentList () {
		final int EXIT = -1;
		int choice = 0;

		while (true) {
			System.out.println("\n--- Student Database ---");
			for (int i = 0; i < studentList.size(); i++) {
				Student student = studentList.get(i);
				System.out.printf("%2d - %s %s (%s)%n", i+1, student.getFirstName(), student.getLastName(), student.getOEN());
			}
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

			Student student = studentList.get(choice-1);
			displayUpdateStudentInfoMenu(student);
		}
	}

	private void displayGraduateList () {
		final int EXIT = -1;
		ArrayList<Graduate> graduateList = graduateDatabase.getGraduateList();
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
				} else if (!(choice > 0 && choice <= studentList.size() + 1)) {
					System.out.println("Invalid input!");
					choice = 0;
				}
			} while (choice == 0);

			System.out.println(graduateList.get(choice-1));
			System.out.println("Enter any key to return to the previous menu: ");
			sc.nextLine();
		}
	}

	private void displayUpdateStudentInfoMenu (Student student) {
		final int EXIT = -1;
		int choice = 0;
		boolean keepOnGoing = true;

		do {
			System.out.printf("%n--- Update %s %s's Information ---%n", student.getFirstName(), student.getLastName());
			for (int i = 0; i < updateStudentMenuOptions.length; i++) {
				System.out.println(updateStudentMenuOptions[i]);
			}

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
						keepOnGoing = false;
					}
					break;
				case 6:
					if (confirmStudentDeletion()) {
						studentList.remove(student);
						System.out.println("Student removed. Enter any key to go back to the main menu.");
						sc.nextLine();
						keepOnGoing = false;
					}
					break;
			}
			choice = 0;
		} while (keepOnGoing);
	}

	private boolean moveToGraduateDatabase (Student student) {
		final String CONFIRMATION = "CONFIRM";

		System.out.println("\n--- Move Student to Graudate Database ---");

		if (student.getNumPassingCourses() < GraduateDatabase.NUM_COURSES) {
			System.out.println("This student does not have enough credits to graduate! Enter any");
			System.out.println("key to return to the previous menu.");
			sc.nextLine();
			return false;
		}

		System.out.println("Are you sure you want to move this student to the graduate database? You will no");
		System.out.println("longer be able to modify this student's information and their account will be closed.");
		System.out.println("Enter \"" + CONFIRMATION + "\" to confirm. Enter anything else to cancel.");
		String input = sc.nextLine();

		if (!input.equalsIgnoreCase(CONFIRMATION)) {
			return false;
		}

		student.displayCourseList();
		ArrayList<ActiveCourse> currentCourses = student.getCourseTracker().getCourseList();
		ArrayList<Integer> selectedCourses = new ArrayList<>();
		int choice = 0;

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

		Course[] topSixCourses = new Course[GraduateDatabase.NUM_COURSES];
		int markSum = 0;
		for (int i = 0; i < selectedCourses.size(); i++) {
			int courseIndex = selectedCourses.get(i);
			ActiveCourse selectedCourse = currentCourses.get(courseIndex);
			topSixCourses[i] = new Course(selectedCourse.getCourseCode(), selectedCourse.getMark());
			markSum += selectedCourse.getMark();
		}

		double topSixAverage = (double) markSum / GraduateDatabase.NUM_COURSES;

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

			if (!(choice > 0 && choice <= GraduateDatabase.NUM_COURSES)) {
				System.out.println("Invalid input!");
				choice = 0;
			}
		}

		int programID = choice - 1;
		String university = programList[programID];

		graduateDatabase.addGraduate(student.getOEN(), student.getFirstName(), student.getLastName(), topSixCourses, topSixAverage, university, programID);
		studentList.remove(student);
		System.out.println("Student moved to graduate file.");
		return true;
	}

	private boolean confirmStudentDeletion () {
		final String CONFIRMATION = "CONFIRM DELETION";

		System.out.println("\n--- Remove Student Account ---");
		System.out.println("Are you sure you want to remove this student from the database? This action");
		System.out.println("cannot be undone. Enter \"CONFRIM DELETION\" to delete the student. Enter any");
		System.out.println("other key to return to the previous menu.");
		String input = sc.nextLine();
		if (input.equalsIgnoreCase(CONFIRMATION)) {
			return true;
		}
		return false;
	}

	private Student searchStudentByOEN (String OEN) {
		for(Student student: studentList){
			if(student.getOEN().equals(OEN)){
				return student;
			}
		}
		return null;
	}
}
