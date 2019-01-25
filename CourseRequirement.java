import java.util.*;
import java.io.*;

public class CourseRequirement
{
	private String course;
	private int minMark;
	Scanner sc = new Scanner (System.in);

	public CourseRequirement(String c, int m)
	{
		course = c;
		minMark = m;
	}//Initializes from the Program Database class, with readings from the file

	public CourseRequirement()
	{
		System.out.print ("Enter the required course code: ");
		course=sc.nextLine();
		System.out.print ("Enter the minimum average required in this course (Enter -1 if there is no minimum average): ");
		minMark=sc.nextInt();
	}//Intialzation by admin's input

	public void display()
	{
		System.out.println(this);
	}//prints the course's information

	public String toString ()
	{
		String s= "Course code: " + course;
		s+=(minMark>0?("\nMinimum average: " + minMark + "\n"):("\nNo mininum requirement."));

		return s;
	}//For display purposes

	public void save (String file)
	{
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
			out.write(course);out.newLine();
			out.write(minMark+"");out.newLine();

			out.close();
		}
		catch(IOException iox)
		{
			System.out.println("Problem saving course requirements.");
		}
	}//Saves the course requirements
}