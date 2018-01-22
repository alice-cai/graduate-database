import java.util.*;
import java.io.*;

public class Program implements Comparable<Program>
{
	private final String MENU = "programs/program_menu.txt";
	private ProgramOverview overview;
	private ArrayList<CourseRequirement> course;
	private AdditionalInfo additionalInfo;
	private ContactInfo contact;
	private int id;
	private ArrayList<PastData> pastData;
	private ArrayList<String> menu;
	
	public Program(ProgramOverview po, ArrayList<CourseRequirement> c, AdditionalInfo info, ContactInfo ci, int programID, ArrayList<PastData> pd)
	{
		overview = po;
		course = c;
		additionalInfo = info;
		contact = ci;
		id = programID;
		pastData = pd;
		menu = Method.readMenu(MENU);
	}//file is read from the ProgramDatabase class to initialize Program class

   public Program (int programID)
   {
		course = new ArrayList<CourseRequirement>();
      pastData = new ArrayList<PastData>();
      overview = new ProgramOverview();
      additionalInfo = new AdditionalInfo();
      contact = new ContactInfo();
      CourseRequirement c;
      boolean exit;
      String input;
      System.out.print ("Enter any negative integer to exit from entering courses, enter anything else to stay: ");
      input = Method.sc.nextLine();
      exit = !Method.inputCheck(input);
      for (int i = 0;  i <6 && !exit; i++)
      {
         c = new CourseRequirement();
         course.add(c);
         System.out.print ("Enter any negative integer to exit from entering courses, enter anythign else to stay: ");
         input =Method.sc.nextLine();
         exit = !Method.inputCheck(input);
      }
     	id = programID+1;
      menu = Method.readMenu(MENU);
      
   }
	
   public void setID(int Id)
   {
      id = Id;
   }
   public int getId()
   {
      return id;
   }
	public void displayMenu()
	{
		boolean exit = false;
		int option = menu.size();
      String input;
      ArrayList<Program> p;
		do
		{
         Method.displayMenu(menu);
			switch(Method.getOption(option))
			{
				case 1:
					overview.display();
				   break;
				case 2:
					displayCourse();
					break;
				case 3:
					displayPastData();
					break;
				case 4:
					additionalInfo.display();
					break;
				case 5:
					contact.display();
					break;
				case 6:
					exit = true;
			}
		}while(!exit);
	}
	
	
	public void displayPastData()
	{
		int size = pastData.size();
		String input;
		boolean exit;
		if (size > 0 )
		{
			for (int i = 1; i <= size; i++)
			{
				System.out.printf("%d: Year %d%n", i, pastData.get(i-1).getYear());
			}
			pastData.get(Method.getOption(size)-1).display();
		}
		else
		{
			System.out.println("The past data is empty, enter \"1\" to return to previous list");
			Method.getOption(1);
		}
	}

	public void displayCourse()
	{
		int size = course.size();
		for (int i = 0; i < size; i++)
      {
			course.get(i).display();
      }
		String input;
		System.out.print("Press any key to return to previous menu.");
		input = Method.sc.nextLine(); 
	}
	
	
	public boolean searchProgram(String s)
	{
			return (overview.getName().toLowerCase().contains(s.toLowerCase()));
	}
	public boolean searchOuacCode(String s)
	{
			return (overview.getOuacCode().toLowerCase().contains(s.toLowerCase()));
	}
	public boolean searchUni(String s)
	{
			return (overview.getUniversity().toLowerCase().contains(s.toLowerCase()));
	}
	public boolean searchMajor(String s)
	{
			return ((overview.getMajor().toLowerCase()).contains(s.toLowerCase()));
	}
      
	public boolean lowerThan(double n)
	{
		return (overview.getAverage() <= n);
	}	
	
	public String toString()
	{
		return overview.overview();
	}
	
	public int compareTo(Program p)
	{
		double difference = overview.getAverage() - p.overview.getAverage();
		if (difference>0)
			return 100;
		else if(difference == 0)
			return 0;
		else
			return -999;
	}
   
   public void save (String file)
   {
      try
      {
         BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
         
         out.write(id+ "");out.newLine();
         out.close();
         overview.save(file);
         
         int size = course.size();
         for (int i = 0; i < size; i ++)
         {
            course.get(i).save(file);
         }
         out = new BufferedWriter(new FileWriter(file,true));

         out.write("End of course requirement"); out.newLine();
         out.close();
         
         additionalInfo.save(file);
         
         contact.save(file);
         
         out.close();
      }
      catch(IOException iox)
      {
         System.out.println("Problem saving program.");
      }
   }
}
