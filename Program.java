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
	}//file is read from the ProgramDatabase class to initialize instances of the Program class

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
	}//Initialzing the Program instance from user's input
	
	public void displayMenu()
	{
		boolean exit = false;
		int option = menu.size();
		String input;
		ArrayList<Program> p;
		do
		{
			System.out.println("\n--- Program Menu ---");
			Method.displayMenu(menu);
			switch(Method.getOption(option))
			{
				case 1:
					System.out.println("\n--- Program Overview ---");
					overview.display();
					break;
				case 2:
					System.out.println("\n--- Course Requirement ---");
					displayCourse();
					break;
				case 3:
					System.out.println("\n--- Past Data ---");
					displayPastData();
					break;
				case 4:
					System.out.println("\n--- Additional Info ---");
					additionalInfo.display();
					break;
				case 5:
					System.out.println("\n--- Contact Information ---");
					contact.display();
					break;
				case 6:
					exit = true;
			}
		}while(!exit);
	}// Displays and gets options and displays the corresponding information
	
	
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
	}//User chooses the year of past data they want to see and the data are displayed.

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
	}// Displays the courses required
	
	public int getId()
	{
		return id;
	}

	public void setID(int id)
	{
      	this.id = id;
	}

	public boolean searchProgram(String s)
	{
		return (overview.getName().toLowerCase().contains(s.toLowerCase()));
	}//Search program based on the name of the program

	public boolean searchOuacCode(String s)
	{
		return (overview.getOuacCode().toLowerCase().contains(s.toLowerCase()));
	}//Search program based on the OUAC code of the program

	public boolean searchUni(String s)
	{
		return (overview.getUniversity().toLowerCase().contains(s.toLowerCase()));
	}//Search program based on the university of the program

	public boolean searchMajor(String s)
	{
		return ((overview.getMajor().toLowerCase()).contains(s.toLowerCase()));
	}//Search program based on the major of the program
      
	public boolean lowerThan(double n)
	{
		return (overview.getAverage() <= n);
	}//Checks if the program has a lower average than the given value
	
	public String toString()
	{
		return overview.overview();
	}//Converts to String for output.
	
	public int compareTo(Program p)
	{
		double difference = overview.getAverage() - p.overview.getAverage();
		if (difference>0)
			return 100;
		else if(difference == 0)
			return 0;
		else
			return -999;
	}//Compares between programs based on the admission average.
   
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
	}//saves the current Program instance to a given file
}