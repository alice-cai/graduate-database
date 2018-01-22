/**
* GraduateDatabase.java
* Keeps track of all Graduate objects. Responsible for reading from file,
* saving to file, adding new Graduates, and removing outdated data.
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.io.*;

public class GraduateDatabase {
	public static final String[] PROGRAMS = { "University of Waterloo (Software Engineering)", "University of Toronto (Rotman Commerce)",
	"University of Toronto (Engineering Science)", "McMaster University (Life Science)", "Trent University (Anthropology)", "University of Guelph (Agriculture)",
	"Western University (Science)", "Queen's University (Commerce)", "York University (Mathematics)", "Ryerson University (Arts and Contemporary Studies)"};
	private final String GRADUATE_FILE = "user_data/graduate_list.txt";
	public static final int NUM_COURSES = 6;
	public static final int MAX_YEARS_FROM_GRAD = 5;
	private ArrayList<Graduate> graduateList;
	private int currentYear;

	public GraduateDatabase () {
		graduateList = new ArrayList<>();
		Date date = new Date();
		currentYear = 1900 + date.getYear();

		createByFileInput();
		removeOldGraduates();
	}

	public ArrayList<Graduate> getGraduateList () {
		return graduateList;
	}

	/**
	* createByFileInput
	* Loads all Graduate data from GRADUATE_FILE.
	*/
	private void createByFileInput () {
		try {
			BufferedReader in = new BufferedReader (new FileReader(GRADUATE_FILE));

			int numGraduates = Integer.parseInt(in.readLine());
			for (int i = 0; i < numGraduates; i++) {
				in.readLine(); // flush blank line
				String graduateID = in.readLine();
				String firstName = in.readLine();
				String lastName = in.readLine();
				Course[] courseList = new Course[NUM_COURSES];
				for (int j = 0; j < NUM_COURSES; j++) {
					String courseCode = in.readLine();
					double mark = Double.parseDouble(in.readLine());
					courseList[j] = new Course(courseCode, mark);
				}
				double average = Double.parseDouble(in.readLine());
				String university = in.readLine();
				int programID = Integer.parseInt(in.readLine());
				int yearGraduated = Integer.parseInt(in.readLine());
				Graduate graduate = new Graduate(graduateID, firstName, lastName, courseList, average, university, programID, yearGraduated);
				graduateList.add(graduate);
			}
			in.close();
		} catch (IOException iox) {
			System.out.println("Error reading from graduate file.");
		}
	}

	/**
	* saveToFile
	* Saves all Graduate data to GRADUATE_FILE.
	*/
	public void saveToFile () {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));

			out.write(graduateList.size() + "");
			out.newLine();

			for(Graduate graduate : graduateList) {
				out.newLine(); // blank line

				out.write(graduate.getOEN());
				out.newLine();

				out.write(graduate.getFirstName());
				out.newLine();

				out.write(graduate.getLastName());
				out.newLine();

				Course[] courses = graduate.getTopSixCourses();
				for(int i = 0; i < NUM_COURSES; i++) {
					out.write(courses[i].getCourseCode());
					out.newLine();
					out.write(courses[i].getMark() + "");
					out.newLine();
				}

				out.write(graduate.getTopSixAverage() + "");
				out.newLine();

				out.write(graduate.getUniversity());
				out.newLine();

				out.write(graduate.getProgramID());
				out.newLine();

				out.write(graduate.getYearGraduated() + "");
				out.newLine();
			}
			out.close();
		} catch (IOException iox) {
			System.out.println("Error writing to graduate file.");
		}
	}

	/**
	* removeOldGraduates
	* Removes all graduates that graduated over MAX_YEARS_FROM_GRAD ago.
	*/
	private void removeOldGraduates () {
		Iterator<Graduate> graduateListIterator = graduateList.iterator();

		while (graduateListIterator.hasNext()) {
			Graduate graduate = graduateListIterator.next();
			if (graduate.getYearGraduated() < (currentYear - MAX_YEARS_FROM_GRAD)) {
				graduateListIterator.remove();
			}
		}
	}

	public void addGraduate (String oen, String firstName, String lastName, Course[] topSixCourses, double topSixAverage, String university, int programID) {
		Graduate graduate = new Graduate(oen, firstName, lastName, topSixCourses, topSixAverage, university, programID, currentYear);
		graduateList.add(graduate);
	}

	/**
	* calculateMean
	* Takes in a programID. Returns the average admission average for the
	* specified program.
	*/
	public double calculateMean (int programID) {
		double sum = 0;
		int count = 0;
		for (Graduate graduate : graduateList) {
			if (graduate.getProgramID() == programID) {
				sum += graduate.getTopSixAverage();
				count++;
			}
		}
		return sum / count;
	}

	/**
	* calculateMean
	* Takes in a programID and a year. Returns the average admission average
	* for the specified program in the specified year.
	*/
	public double calculateMean (int programID, int year) {
		double sum = 0;
		int count = 0;
		for (Graduate graduate : graduateList) {
			if (graduate.getProgramID() == programID && graduate.getYearGraduated() == year) {
				sum += graduate.getTopSixAverage();
				count++;
			}
		}
		return sum / count;
	}

	/**
	* calculateMedian
	* Takes in a programID. Returns the median admission average for the
	* specified program in the specified year.
	*/
	public double calculateMedian (int programID) {
		ArrayList<Double> graduateMarks = new ArrayList<>();

		for (Graduate graduate : graduateList) {
			if (graduate.getProgramID() == programID) {
				graduateMarks.add(graduate.getTopSixAverage());
			}
		}

		Double[] graduateMarksArray = (Double[])(graduateMarks.toArray());
		Arrays.sort(graduateMarksArray);

		return graduateMarksArray[graduateMarksArray.length/2];
	}

	/**
	* calculateMedian
	* Takes in a programID and a year. Returns the median admission average
	* for the specified program in the specified year.
	*/
	public double calculateMedian (int programID, int year) {
		ArrayList<Double> graduateMarks = new ArrayList<>();

		for (Graduate graduate : graduateList) {
			if (graduate.getProgramID() == programID && graduate.getYearGraduated() == year) {
				graduateMarks.add(graduate.getTopSixAverage());
			}
		}

		Object[] graduateMarksArray = (graduateMarks.toArray());
		Arrays.sort(graduateMarksArray);

		return (double)graduateMarksArray[graduateMarksArray.length/2];
	}

	/**
	* findLowestAverage
	* Takes in a programID. Returns the average of the student with the lowest
	* grades that was admitted into the specified program over the past
	* MAX_YEARS_FROM_GRAD years.
	*/
	public double findLowestAverage (int programID) {
		double lowestAverage = -1;
		for (Graduate graduate : graduateList) {
			if (graduate.getProgramID() == programID && graduate.getTopSixAverage() < lowestAverage) {
				lowestAverage = graduate.getTopSixAverage();
			}
		}
		return lowestAverage;
	}

	/**
	* findLowestAverage
	* Takes in a programID and a year. Returns the average of the student
	* with the lowest grades that was admitted into the specified program
	* in the specified year.
	*/
	public double findLowestAverage (int programID, int year) {
		double lowestAverage = -1;
		for (Graduate graduate : graduateList) {
			if (graduate.getProgramID() == programID && graduate.getYearGraduated() == year && graduate.getTopSixAverage() < lowestAverage) {
				lowestAverage = graduate.getTopSixAverage();
			}
		}
		return lowestAverage;
	}

	/**
	* findGradList
	* Takes in a programID and returns an ArrayList of all Graduates who were
	* admitted to that program in the past MAX_YEARS_FROM_GRAD years.
	*/
	public ArrayList<Graduate> findGradList (int programID) {
		ArrayList<Graduate> list = new ArrayList<>();

		for (Graduate graduate : graduateList) {
			if (graduate.getProgramID() == programID) {
				list.add(graduate);
			}
		}

		return list;
	}

	/**
	* findGradList
	* Takes in a programID and a year. Returns an ArrayList of all Graduates who were
	* admitted to the specified program in the specified year.
	*/
	public ArrayList<Graduate> findGradList (int programID, int year) {
		ArrayList<Graduate> list = new ArrayList<>();

		for (Graduate graduate : graduateList) {
			if (graduate.getProgramID() == programID && graduate.getYearGraduated() == year) {
				list.add(graduate);
			}
		}

		return list;
	}
}
