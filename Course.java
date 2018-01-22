/**
* Course.java
* Stores information about a completed course.
*/

public class Course {
	protected String courseCode;
	protected double mark;

	public Course (String courseCode, double mark) {
		this.courseCode = courseCode.toUpperCase();
		this.mark = mark;
	}

	public String getCourseCode () {
		return courseCode;
	}

	public double getMark () {
		return mark;
	}
}