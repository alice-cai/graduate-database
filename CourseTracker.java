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

	/**
	* Checks if the inputted course code is an available course at AY Jackson. All available
	* course codes are stored in the AVAILABLE_COURSES array.
	*
	* @param courseCode - the course code that is being checked
	* @return boolean indicating whether the specified course code belongs to an AYJ course
	*/
	public static boolean isValidCourse (String courseCode) {
		courseCode = courseCode.toUpperCase();
		for (String availableCourse : AVAILABLE_COURSES) {
			if (availableCourse.equals(courseCode)) {
				return true;
			}
		}
		return false;
	}

	/**
	* Checks if the student is already taking the maximum number of courses or if the student
	* is already taking the specified course. If not, this method adds the specified course to
	* the student's list of courses.
	*
	* @param course - course that is to be added
	* @return boolean indicating whether the course has been added
	*/
	public boolean addCourse (ActiveCourse course) {
		if (courseList.size() < MAX_COURSES && findByCourseCode(course.getCourseCode()) == null) {
			courseList.add(course);
			return true;
		}
		return false;
	}

	/**
	* Takes in a course code. If the student is currently taking the course
	* specified by the course code, it removes it from the student's course
	* list.
	*
	* @param courseCode - course code of the course that is to be dropped
	* @return boolean indicating whether the course was dropped
	*/
	public boolean dropCourse (String courseCode) {
		ActiveCourse course = findByCourseCode(courseCode);
		if (course != null) {
			courseList.remove(course);
			return true;
		}
		return false;
	}

	/**
	* Takes in an ActiveCourse object and removes it from the student's list
	* of courses.
	*
	* @param courseCode - course code of the course that is to be dropped
	* @return boolean indicating whether the course was dropped
	*/
	public boolean dropCourse (ActiveCourse course) {
		return courseList.remove(course);
	}

	/**
	* Checks if the student is already taking the maximum number of courses or if the student
	* is already taking the specified course. If not, this method adds the specified course to
	* the student's list of courses.
	*/
	public boolean updateMark (String courseCode, double newMark) {
		ActiveCourse course = findByCourseCode(courseCode);
		if (course != null && course.updateMark(newMark)) {
			return true;
		}
		return false;
	}

	/**
	* Checks if the student is already taking the specified course. If so, this method returns
	* true. Otherwise, it returns false.
	*/
	public boolean courseFound (String courseCode) {
		if (findByCourseCode(courseCode) != null) {
			return true;
		}
		return false;
	}

	/**
	* findByCourseCode(String courseCode)
	* Looks through the student's courses and return the course with specified course code, or
	* null if the student is not taking that course.
	*/
	private ActiveCourse findByCourseCode (String courseCode) {
		for (ActiveCourse course : courseList) {
			if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
				return course;
			}
		}
		return null;
	}

	/**
	* Takes in an array of six Course objects and calculates the average mark.
	*
	* @param topSixCourseList - array of top six courses
	* @return average mark
	*/
	public double checkAverageTopSix (Course[] topSixCourseList) {
		final int NUM_COURSES = 6;
		double markSum = 0;
		int count = 0;

		for (Course course : topSixCourseList) {
			markSum += course.getMark();
			count++;
		}

		if (count == NUM_COURSES) {
			return markSum / NUM_COURSES;
		}
		return -1;
	}

	/**
	* Takes in an array of six Course objects and calculates the average mark
	* using recursion.
	*
	* @param topSixCourseList - array of top six courses
	* @return average mark
	*/
	public double checkAverageTopSixRecursion(Course[] topSixCourseList){
		if(topSixCourseList.length == 1){
			double mark = topSixCourseList[0].getMark();
			return mark;
		}
		else if(topSixCourseList.length == 6){
			Course[] temp = new Course[topSixCourseList.length-1]; 
			for(int i=0; i<topSixCourseList.length-1; i++){
				temp[i] = topSixCourseList[i+1];
			}
			double mark = topSixCourseList[0].getMark();
			double total = mark + checkAverageTopSixRecursion(temp);
			return total/6;
		}
		else{
			Course[] temp = new Course[topSixCourseList.length-1]; 
			for(int i=0; i<topSixCourseList.length-1; i++){
				temp[i] = topSixCourseList[i+1];
			}
			double mark = topSixCourseList[0].getMark();
			return mark + checkAverageTopSixRecursion(temp);	
		}
	}
}
