import java.util.*;
import java.io.*;

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
	}//This is initilized from the Program Database class

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
      		System.out.print ("Enter coop option (enter \"true\" for true, and anything else for false): ");
            	sc.next();
      		coop=Boolean.parseBoolean(sc.nextLine().toLowerCase());
            	System.out.print ("Enter anticipated admission average: ");
      		admissionAverage=Double.parseDouble(sc.nextLine());
	}//This is initilized from admin's input

	public void display()
	{
		String input;
		System.out.println(this);
		System.out.print("Press any key to return to previous menu.");
		input = sc.nextLine(); 
	}//This is displayed once the user chooses to view the program
	
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
   }//Accessors for searching purposes in the Program class
   
	public String overview()
   {
      return ("Program name: " + name + "\nUniversity: " + university + "\nOUAC code : "+ ouacCode + "\nMajor: " + major + "\nAdmisision average: " + admissionAverage+"\n");
   }//Prints the basic information of a program
	
	public String toString ()
	{
		String s = ("Program name: " + name + "\nUniversity: " + university + "\nOUAC code : "+ ouacCode + "\nMajor: " + major + "\nCoop option: ");
		s+= (coop?"Yes":"No");
		s+= "\nEnrollment: " + ((enrollment>0)?enrollment:"Unknown") + "\n";
		s+= "Admission average: " + (admissionAverage>0? admissionAverage : "unknown") + "\n";
		return s;
	}// Converts the program's information to String
   
   public void save (String file)
   {
      try
      {
         BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
         out.write(name);out.newLine();
         out.write(university);out.newLine();
         out.write(ouacCode);out.newLine();
         out.write(major);out.newLine();
         out.write(coop?"Yes":"No");out.newLine();
         out.write(enrollment + "");out.newLine();
         out.close();
      }
      catch(IOException iox)
      {
         System.out.println("Problem saving program overview.");
      }
   }//Saves ProgramOverview information of a program.
	
}
