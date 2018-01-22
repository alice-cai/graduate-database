import java.util.*;
import java.io.*;

public class ProgramDatabase
{	
	private static final String PROGRAM = "programs/program_overview.txt";
	private static final String MENU = "programs/program_database_menu.txt";
	private static final String ORDER = "programs/sort.txt";
	private static final String END = "end of course requirement";
	private static final double HIGHESTMARK = 100.0;
	private static ArrayList<Program> programs;
	private static PastData_Manager pastdata;
	private static ArrayList<String> menu;
	private static ArrayList<String> order;
   
	public static void initialize ()
	{
		menu = Method.readMenu(MENU);
		order = Method.readMenu(ORDER);
		programs = new ArrayList<Program>();
		pastdata = new PastData_Manager();

		int totalProgram = getProgramNumber();
		int graduateSize;

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
            
            for (int j = Method.CURYEAR-Method.PASTDATA+1; j<=Method.CURYEAR; j++)
            {
               if (!((graduateSize=(pastdata.getGrad().findGradList(ID,j).size()))<=0))
               {
                  pastdata.addPastData(new PastData(j, ID, pastdata.getGrad().findLowestAverage(ID,j),pastdata.getGrad().calculateMean(ID,j), pastdata.getGrad().calculateMedian(ID,j), pastdata.getGrad().findGradList(ID,j).size()));
               }
            }
            
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
            courses=new ArrayList<CourseRequirement>();
            pastdata = new PastData_Manager();
         }
			in.close();
      }
      catch (IOException iox)
      {
         System.out.println("Cannot load ProgramDatabase file...");
      }
	}
	
	public static ArrayList<Program> getProgram()
	{
		return programs;
	}

	public static int getProgramNumber()
	{
		return programs.size();
	}

	public static void displayMenu()
	{
		boolean exit = false;
		int option = menu.size();

		String input;
		ArrayList<Program> p = new ArrayList<Program>();
		double mark;
		do
		{
			System.out.println("\n--- Program Database ---");
         	Method.displayMenu(menu);
			switch(Method.getOption(option))
			{
				case 1:
					System.out.print("\nEnter the OUAC code: ");
					input = Method.sc.nextLine();
					p = searchByCode(input);
					Method.outputPrograms(p);
					break;
				case 2:
					System.out.print("\nEnter the university: ");
					input = Method.sc.nextLine();
					p = searchByUni(input);
					Method.outputPrograms(p);
					break;
				case 3:
					System.out.print("\nEnter the major: ");
					input = Method.sc.nextLine();
					p = searchByMajor(input);
					Method.outputPrograms(p);
					break;
				case 4:
					System.out.print("\nEnter the program name: ");
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
							Collections.reverse(p);
							boolean sorted = true;
							Object[]ph = new Object[p.size()];
							for(int i=ph.length-1; i>0; i--){
								sorted = true;
								for(int j=0; j<i-1; j++){
									if(ph[j] < ph[j+1]){
										sorted = false;
										Object temp = ph[j];
										ph[j] = ph[j+1];
										ph[j+1] = temp;
									}
								}
							}
							Method.outputPrograms(p);
							break;
						case 2:
							Collections.sort(p);
							boolean sorted = true;
							Object[]ph = new Object[p.size()];
							for(int i=ph.length-1; i>0; i--){
								sorted = true;
								for(int j=0; j<i-1; j++){
									if(ph[j] > ph[j+1]){
										sorted = false;
										Object temp = ph[j];
										ph[j] = ph[j+1];
										ph[j+1] = temp;
									}
								}
							}
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
	
	public static ArrayList<Program> searchByUni(String s)
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
   
	public static ArrayList<Program> searchByCode(String s)
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
	
	public static ArrayList<Program> searchByMajor(String s)
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

	public static ArrayList<Program> searchByProgram(String s)
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
	
	public static ArrayList<Program> searchByAdmissionAverage(double k)
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

	public static void addProgram()// by prompt
	{
		Program p = new Program(programs.size());
		programs.add(p);
		saveData();
		//intitate all variables with user input.
		
	}
	
   /*public void addPastData()
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
	}*/
   
	public static void deleteProgram()
	{
		Method.displayProgramList(programs);
		System.out.print("Enter the program number to be deleted: ");
		deleteProgram(Method.getOption(programs.size()+1)-2);
		saveData();
	}// delete from a list
	
	public static void deleteProgram(int index)
	{
		int size = programs.size();
		String input;

		if (index >=0 && index < size)
		{
			programs.remove(index);
			for (int i = index; i < size-1; i++)
			{
				programs.get(i).setID(programs.get(i).getId()-1);
			}
			System.out.println("Program deleted.");
			System.out.print("Press any key to return to previous menu.");
			input = Method.sc.nextLine(); 
		}
		else if (index!=-1)
		{
			System.out.println("Deletion unsuccessful.");
			System.out.print("Press any key to return to previous menu.");
			input = Method.sc.nextLine(); 
		}
	}
   

	private static void saveData()
	{
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter("hehe.txt"));
		}
		catch(IOException iox)
		{
			System.out.println("Problem accessing the file, no progress is saved.");
		}
	}
}
