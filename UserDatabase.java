/**
* UserDatabase.java
* Responsible for loading, saving, and managing all users in the Information
* System.
*/

import java.util.*;
import java.io.*;

public class UserDatabase {
	private static final String ADMIN_FILE = "user_data/admin_list.txt";
	private static final String STUDENT_FILE = "user_data/student_list.txt"; 
	public static final Guest GUEST = new Guest();

	private ArrayList<Admin> adminList;
	private ArrayList<Student> studentList;

	public UserDatabase () {
		adminList = new ArrayList<>();
		studentList = new ArrayList<>();
		loadAdminList();
		loadStudentList();
	}

	/**
	* Reads the list of admins from the ADMIN_FILE and stores it in the adminList.
	*/
	public void loadAdminList () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(ADMIN_FILE));

			int numAdmins = Integer.parseInt(in.readLine());
			for(int i = 0; i < numAdmins; i++){
				in.readLine();
				Admin admin = new Admin(in.readLine(), in.readLine(), in.readLine(), studentList);
				adminList.add(admin);
			}
			in.close();
		}
		catch(IOException iox){
			System.out.println("Error reading admin list from file.");
		}
		catch(NumberFormatException nfe){
			System.out.println("Error converting from string to integer (while loading admin list).");
		}
	}

	/**
	* Reads the list of students from the STUDENT_FILE and stores it in the studentList.
	*/
	public void loadStudentList () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(STUDENT_FILE));

			int num = Integer.parseInt(in.readLine());

			for(int i=0; i<num; i++){
				in.readLine();

				String username = in.readLine();
				String password = in.readLine();
				String firstName = in.readLine();
				String lastName = in.readLine();
				String oen = in.readLine();

				int counter = Integer.parseInt(in.readLine());
				ArrayList<ActiveCourse> courseList = new ArrayList<>();
				for(int j=0; j<counter; j++){
					ActiveCourse active = new ActiveCourse(in.readLine(), Double.parseDouble(in.readLine()));
					courseList.add(active);
				}
				CourseTracker courseTracker = new CourseTracker(courseList);

				Student stu = new Student(username, password, firstName, lastName, oen, courseTracker);
				studentList.add(stu);
			}

			in.close();
		}
		catch(IOException iox){
			System.out.println("Error reading from student file");
		}
		catch(NumberFormatException nfe){
			System.out.println("Error converting from string to integer (user database - load student list)");
		}

	}

	/**
	* Saves all the information about the Admin objects in adminList to ADMIN_FILE.
	*/
	public void saveAdminList () {
		try {
			BufferedWriter out = new BufferedWriter (new FileWriter(ADMIN_FILE));

			out.write(adminList.size() + "");
			out.newLine();

			for (Admin admin : adminList) {
				out.newLine();

				out.write(admin.getUsername());
				out.newLine();
				out.write(admin.getPassword());
				out.newLine();
				out.write(admin.getAdminNumber());
				out.newLine();
			}

			out.close();
		} catch (IOException iox) {
			System.out.println("Error writing to admin file.");
		}
	}

	/**
	* Saves all the information about the Student objects in studentList to STUDENT_FILE.
	*/
	public void saveStudentList(){
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(STUDENT_FILE));

			out.write(studentList.size() + "");
			out.newLine();

			for(Student stu : studentList) {
				out.newLine();

				out.write(stu.getUsername());
				out.newLine();
				out.write(stu.getPassword());
				out.newLine();
				out.write(stu.getFirstName());
				out.newLine();
				out.write(stu.getLastName());
				out.newLine();
				out.write(stu.getOEN());
				out.newLine();

				out.write(stu.getCourseTracker().getCourseList().size() + "");
				out.newLine();

				for(ActiveCourse course : stu.getCourseTracker().getCourseList()){
					out.write(course.getCourseCode() + "");
					out.newLine();
					out.write(course.getMark() + "");
					out.newLine();
				}
			}
			out.close();
		}
		catch(IOException iox){
			System.out.println("Error writing to file");
		}
	}

	/**
	* Takes in a Student object and adds it to the studentList.
	*/
	public void addStudent (Student student) {
		studentList.add(student);
		saveStudentList();
	}

	/**
	* Takes in a username and checks if a Student already has that username. If so,
	* return false. Otherwise, return true.
	*/
	public boolean studentUsernameAvailable (String username) {
		for (Student curStudent : studentList) {
			if (username.equals(curStudent.getUsername())) {
				return false;
			}
		}
		return true;
	}

	/**
	* Takes in an OEN and returns the Student to which it belongs.
	*/
	public Student searchStudentByOEN (String OEN) {
		for(Student student: studentList){
			if(student.getOEN().equals(OEN)){
				return student;
			}
		}
		return null;
	}

	/**
	* Takes in Student login information and returns the Student with the matching
	* username and password. If the login information does not match that of any
	* existing Student, this method returns null.
	*/
	public Student searchStudentByLoginInfo (String user, String password) {
		for(Student student: studentList){
			if(student.getUsername().equalsIgnoreCase(user) && student.getPassword().equals(password)){
				return student;
			}
		}
		return null;
	}

	/**
	* Takes in Admin login information and returns the Admin with the matching
	* username and password. If the login information does not match that of any
	* existing Admin, this method returns null.
	*/
	public Admin searchAdminByLoginInfo (String user, String password) {
		for(Admin admin: adminList){
			if(admin.getUsername().equalsIgnoreCase(user) && admin.getPassword().equals(password)){
				return admin;
			}
		}
		return null;
	}
}