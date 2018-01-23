/**
* Graduate.java
* Stores all information about a single Graduate.
*/

public class Graduate {
	public static final int NUM_COURSES = 6;
	private String oen;
	private String firstName;
	private String lastName;
	private Course[] topSixCourses;
	private double topSixAverage;
	private String university;
	private int programID;
	private int yearGraduated;

	public Graduate (String oen, String firstName, String lastName, Course[] topSixCourses, double topSixAverage, String university, int programID, int yearGraduated) {
		this.oen = oen;
		this.firstName = firstName;
		this.lastName = lastName;
		this.topSixCourses = topSixCourses;
		this.topSixAverage = topSixAverage;
		this.university = university;
		this.programID = programID;
		this.yearGraduated = yearGraduated;
	}

	public String getOEN () {
		return oen;
	}

	public String getFirstName () {
		return firstName;
	}

	public String getLastName () {
		return lastName;
	}

	public Course[] getTopSixCourses () {
		return topSixCourses;
	}

	public double getTopSixAverage () {
		return topSixAverage;
	}

	public String getUniversity () {
		return university;
	}

	public int getProgramID () {
		return programID;
	}

	public int getYearGraduated () {
		return yearGraduated;
	}

	public double compareToGraduate (Graduate other) {
		return topSixAverage - other.topSixAverage;
	}

	public String toString () {
		String output = "";

		output += String.format("%n--- %s %s (%s) ---", firstName, lastName, oen);
		for (int i = 0; i < NUM_COURSES; i++) {
			Course course = topSixCourses[i];
			output += String.format("%n%s (%.1f)", course.getCourseCode(), course.getMark());
		}
		output += "\nAverage: " + Math.round(topSixAverage) + "%";
		output += "\nAccepted to: " + university;
		output += "\nYear Graduated: " + yearGraduated;

		return output;
	}
}