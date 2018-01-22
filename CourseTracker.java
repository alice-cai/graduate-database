/**
* CourseTracker.java
* Responsible for managing and updating a single student's courses.
*/

import java.util.ArrayList;
import java.io.*;

public class CourseTracker {
	public static final int MAX_COURSES = 8;
	private static final String[] AVAILABLE_COURSES = {"ENG4U", "SPH4U", "SCH4U", "SBI4U", "MHF4U", "MCV4U", "ICS4U", "MDM4U"};
	private ArrayList<ActiveCourse> courseList;

	public CourseTracker (ArrayList<ActiveCourse> courseList) {
		this.courseList = courseList;
	}

	public ArrayList<ActiveCourse> getCourseList () {
		return courseList;
	}

	public int getNumCourses () {
		return courseList.size();
	}

	public static boolean isValidCourse (String courseCode) {
		courseCode = courseCode.toUpperCase();
		for (String availableCourse : AVAILABLE_COURSES) {
			if (availableCourse.equals(courseCode)) {
				return true;
			}
		}
		return false;
	}

	public void addCourse (ActiveCourse course) {
		if (courseList.size() < MAX_COURSES && findByCourseCode(course.getCourseCode()) == null) {
			courseList.add(course);
		}
	}

	public boolean updateMark (String courseCode, double newMark) {
		ActiveCourse course = findByCourseCode(courseCode);
		if (course != null && course.updateMark(newMark)) {
			return true;
		}
		return false;
	}

	public boolean courseExists (String courseCode) {
		if (findByCourseCode(courseCode) != null) {
			return true;
		}
		return false;
	}

	private ActiveCourse findByCourseCode (String courseCode) {
		for (ActiveCourse course : courseList) {
			if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
				return course;
			}
		}
		return null;
	}

	/**
	* checkAverageTopSix
	* Takes in an array of 6 course codes and calculates the average mark.
	*/
	public double checkAverageTopSix (String[] topSixCourseList) {
		final int NUM_COURSES = 6;
		double markSum = 0;
		int count = 0;

		for (int i = 0; i < topSixCourseList.length; i++) {
			for (ActiveCourse course : courseList) {
				if (topSixCourseList[i].equals(course.getCourseCode())) {
					markSum += course.getMark();
					count++;
				}
			}
		}
		if (count == NUM_COURSES) {
			return markSum / NUM_COURSES;
		}
		return -1;
	}

	public boolean dropCourse (String courseCode) {
		ActiveCourse course = findByCourseCode(courseCode);
		if (course != null) {
			courseList.remove(course);
			return true;
		}
		return false;
	}

	public void dropCourse (ActiveCourse course) {
		courseList.remove(course);
	}
}
