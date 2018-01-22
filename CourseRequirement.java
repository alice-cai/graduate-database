import java.util.*;

public class CourseRequirement
{

	private String course;
	private int minMark;
   Scanner sc = new Scanner (System.in);
   
	public CourseRequirement(String c, int m)
	{
		course = c;
		minMark = m;
	}
	public CourseRequirement()
   {
      System.out.print ("Enter the required course: ");
      course=sc.nextLine();
      System.out.print ("Enter the minimum average required in this course (Enter -1 if there is no minimum average): ");
      minMark=sc.nextInt();
   }
	public void display()
	{
		Scanner sc  = new Scanner (System.in);
		String input;
		System.out.println(this);
		System.out.print("Press any key to return to previous menu.");
		input = sc.nextLine(); 
	}
	
	public String toString ()
	{
		String s= "Course code: " + course;
      s+=(minMark>0?("\nMinimum average: " + minMark + "\n"):("\nNo mininum requirement."));
      
      return s;
	}
}