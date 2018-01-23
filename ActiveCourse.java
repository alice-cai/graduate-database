/**
* ActiveCourse.java
* Stores information about a course in which a student is currently enrolled. Allows
* modification of the student's mark.
*/

public class ActiveCourse extends Course {
	public static final int MAX_MARK = 100;
	public static final int MIN_MARK = 0;
	public ActiveCourse (String courseCode, double mark) {
		super(courseCode, mark);
	}

	public boolean updateMark (double newMark) {
		if (newMark >= MIN_MARK && newMark <= MAX_MARK) {
			mark = newMark;
			return true;
		}
		return false;
	}
}