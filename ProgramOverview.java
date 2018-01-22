import java.util.*;

public class ProgramOverview
{
	private String name;
	private String university;
	private String ouacCode;
	private String major;
	private boolean coop;
	private int enrollment;
	private double admissionAverage;
   Scanner sc = new Scanner (System.in);
	
	public ProgramOverview(String n, String u, String o, String m, boolean c, int e, double a)
	{
		name = n;
		university = u;
		ouacCode = o;
		major = m;
		coop = c;
		enrollment = e;
		admissionAverage = a;
	}

	public ProgramOverview()
	{
     		System.out.print ("Enter program name: ");
     		name=sc.nextLine();
      		System.out.print ("Enter university name: ");
      		university=sc.nextLine();
      		System.out.print ("Enter OUAC Code name: ");
      		ouacCode=sc.nextLine();
      		System.out.print ("Enter major name: ");
      		major=sc.nextLine();
      		System.out.print ("Enter enrollment number: ");
      		enrollment=sc.nextInt();
      		System.out.print ("Enter coop option (true or false): ");
      		coop=Boolean.parseBoolean(sc.nextLine().toLowerCase());
            System.out.print ("Enter admission average: ");
      		admissionAverage=Double.parseDouble(sc.nextLine());
	}

	public void display()
	{
		String input;
		System.out.println(this);
		System.out.print("Press any key to return to previous menu.");
		input = sc.nextLine(); 
	}
	
	public String getUniversity()
	{
		return university;
	}
	
	public String getName()
	{
		return name; 
	}

	public String getMajor()
	{
		return major; 
	}

	public String getOuacCode()
	{
		return ouacCode; 
	}
	
   public double getAverage()
   {
      return admissionAverage;
   }
   
	public String overview()
   {
      return ("Program name: " + name + "\nUniversity: " + university + "\nOUAC code : "+ ouacCode + "\nMajor: " + major);
   }
	
	public String toString ()
	{
		String s = ("Program name: " + name + "\nUniversity: " + university + "\nOUAC code : "+ ouacCode + "\nMajor: " + major + "\nCoop option: ");
		s+= (coop?"Yes":"No");
		s+= "\nEnrollment: " + enrollment + "\n";
		s+= "Admission average: " + admissionAverage;
		return s;
	}
	
}