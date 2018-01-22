import java.util.*;
import java.io.*;

public class ProgramDatabase
{	
	private final String PROGRAM = "programs/program_overview.txt";
	private final String MENU = "programs/program_database_menu.txt";
	private final String ORDER = "programs/sort.txt";
	private final String END = "end of course requirement";
	private final double HIGHESTMARK = 100.0;
	private ArrayList<Program> programs;
	private PastData_Manager pastdata;
	private ArrayList<String> menu;
	private ArrayList<String> order;
   
	public ProgramDatabase()
	{
		menu = Method.readMenu(MENU);
		order = Method.readMenu(ORDER);
      pastdata = new PastData_Manager();
      programs = new ArrayList<Program>();
      
      int totalProgram = getProgramNumber();
      int graduateSize;
      
      for(int i = 1; i<= totalProgram; i++)
      {
         for (int j = Method.CURYEAR-Method.PASTDATA; j<Method.CURYEAR; j++)
         {
            if (!((graduateSize=pastdata.getGrad().findGradList(i,j).size())<=0))
            {
               pastdata.addPastData(new PastData(j, i, pastdata.getGrad().findLowestAverage(i,j),pastdata.getGrad().calculateMean(i,j), pastdata.getGrad().calculateMedian(i,j), pastdata.getGrad().findGradList(i,j).size()));
            }
         }
      }
      
      String [] placeHolder = new String [4];
      int enrollment;
      int courseMark;
      double mark;
      boolean coop;
      
      String input;
      ProgramOverview overview;
      ArrayList<CourseRequirement> courses = new ArrayList<CourseRequirement>();
      AdditionalInfo additionalInfo;
      ContactInfo contact;
      int ID;
      
      try
      {
         BufferedReader in = new BufferedReader(new FileReader(PROGRAM));
         while ((input = in.readLine())!=null)
         {
            ID = Integer.parseInt(input);
            for (int i= 0; i < 4; i++)
               placeHolder[i] = in.readLine();
            coop = Method.trueOrFalse(in.readLine());
            enrollment = Integer.parseInt(in.readLine());
            mark = pastdata.admissionAverage(ID);
            overview = new ProgramOverview(placeHolder[0],placeHolder[1],placeHolder[2],placeHolder[3],coop, enrollment, mark);
            
            while (!(input = in.readLine()).toLowerCase().equals(END))
            {
               courseMark = Integer.parseInt(in.readLine());
               courses.add(new CourseRequirement(input,courseMark));
            }
            
            for (int i= 0; i < 3; i++)
               placeHolder[i] = in.readLine();
            additionalInfo = new AdditionalInfo(placeHolder[0],placeHolder[1],placeHolder[2]);
            
            for (int i= 0; i < 4; i++)
               placeHolder[i] = in.readLine();
            contact = new ContactInfo(placeHolder[0],placeHolder[1],placeHolder[2], placeHolder[3]);
            
            programs.add(new Program(overview, courses, additionalInfo, contact, ID, pastdata.findPastData(ID)));
         }
			in.close();
      }
      catch (IOException iox)
      {
         System.out.println("Cannot load file...");
      }
      
	}
	
   
   public int getProgramNumber()
   {
      return programs.size();
   }
	//////////////////
	public void displayMenu()
	{
		boolean exit = false;
		int option = menu.size();
      String input;
      ArrayList<Program> p;
      double mark;
		do
		{
         Method.displayMenu(menu);
			switch(Method.getOption(option))
			{
				case 1:
               System.out.print("Enter the OUAC code: ");
               input = Method.sc.nextLine();
               p = searchByCode(input);
					Method.outputPrograms(p);
				break;
				case 2:
               System.out.print("Enter the university: ");
               input = Method.sc.nextLine();
               p = searchByUni(input);
					Method.outputPrograms(p);
					break;
				case 3:
               System.out.print("Enter the major: ");
               input = Method.sc.nextLine();
               p = searchByMajor(input);
					Method.outputPrograms(p);
					break;
				case 4:
					System.out.print("Enter the program name: ");
               input = Method.sc.nextLine();
               p = searchByProgram(input);
					Method.outputPrograms(p);
				   break;
				case 5:
               mark=Method.getMark(HIGHESTMARK);
               Method.displayMenu(order);
               p = searchByAdmissionAverage(mark);
               switch(Method.getOption(order.size()))
               {
                  case 1:
                     Collections.sort(p);
							Method.outputPrograms(p);
							break;
                  case 2:
							Collections.reverse(p);
							Method.outputPrograms(p);
							break;
               }
               break;
            case 6:
					exit = true;
               break;
			}
		}while(!exit);
	}
	////////////
	
	
	public ArrayList<Program> searchByUni(String s)
	{
		ArrayList<Program> p = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).searchUni(s))
				p.add(programs.get(i)); //add the element from the list programs
		}
		return p;
	}
   
	public ArrayList<Program> searchByCode(String s)
	{
		ArrayList<Program> p = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).searchOuacCode(s))
				p.add(programs.get(i)); //add the element from the list programs
		}
		return p;
	}
	
	public ArrayList<Program> searchByMajor(String s)
	{
		ArrayList<Program> p = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).searchMajor(s))
				p.add(programs.get(i)); //add the element from the list programs
		}
		return p;
	}

	public ArrayList<Program> searchByProgram(String s)
	{
		ArrayList<Program> p = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).searchProgram(s))
				p.add(programs.get(i)); //add the element from the list programs
		}
		return p;
	}
	
	public ArrayList<Program> searchByAdmissionAverage(double k)
	{
		ArrayList<Program> p = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).lowerThan(k))
				p.add(programs.get(i)); //add the element from the list programs
		}
		return p;
	}

	public void addProgram()// by prompt
	{
		Program p = new Program(programs.size());
		programs.add(p);
		
		//intitate all variables with user input.
		
	}
	
   public void addPastData()
	{	
		int year;
		String input;
	
		PastData p;
		System.out.print("Enter the year: ");
		do{
			input = Method.sc.nextLine();
		}
		while(!Method.inputCheck (input, Method.CURYEAR));
		year = Integer.parseInt(input);
		System.out.print("Search program: ");
      
		// input info about the program here
	}
   
	public void deleteProgram()
	{
		  
	}// delete from a list
	
	public void deleteProgram(int index)
	{
		
	}
   private void saveData()
   {
		
   }
}