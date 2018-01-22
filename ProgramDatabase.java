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
         System.out.println("Cannot load file...");
      }
      
	}
	
   public static ArrayList<Program> getProgram()
   {
      return programs;
   } // for debugging
   
   public static int getProgramNumber()
   {
      return programs.size();
   }
	public static void displayMenu()
	{
		boolean exit = false;
		int option = menu.size();

		String input;
		ArrayList<Program> programList = new ArrayList<Program>();
		double mark;

		do
		{
         Method.displayMenu(menu);
			switch(Method.getOption(option))
			{
				case 1:
					System.out.print("\nEnter the OUAC code: ");
					input = Method.sc.nextLine();
					programList = searchByCode(input);
					Method.outputPrograms(programList);
					break;
				case 2:
					System.out.print("\nEnter the university: ");
					input = Method.sc.nextLine();
					programList = searchByUni(input);
					Method.outputPrograms(programList);
					break;
				case 3:
					System.out.print("\nEnter the major: ");
					input = Method.sc.nextLine();
					programList = searchByMajor(input);
					Method.outputPrograms(programList);
					break;
				case 4:
					System.out.print("\nEnter the program name: ");
					input = Method.sc.nextLine();
					programList = searchByProgram(input);
					Method.outputPrograms(programList);
					break;
				case 5:
					mark=Method.getMark(HIGHESTMARK);
					Method.displayMenu(order);
					programList = searchByAdmissionAverage(mark);
					switch(Method.getOption(order.size()))
					{
						case 1:
							//Collections.reverse(programList);
							Method.outputPrograms(sortReverse(programList));
							break;
						case 2:
							//Collections.sort(programList);
							Method.outputPrograms(sort(programList));
							break;
               }
               break;
            case 6:
					exit = true;
               break;
			}
		} while(!exit);
	}
	
	public static ArrayList<Program> sort(ArrayList<Program> programList) {
		boolean sorted = true;
		Program[] temp = convertArrayListToArray(programList);
		for(int i = temp.length-1; i > 0; i--){
			sorted = true;
			for(int j = 0; j < i; j++){
				if(temp[j].compareTo(temp[j+1]) > 0){
					sorted = false;
					Program temporary = temp[j];
					temp[j] = temp[j+1];
					temp[j+1] = temporary;
				}
			}
		}
		return convertArrayToArrayListAscending(temp);
	}
	
	public static ArrayList<Program> sortReverse(ArrayList<Program> programList) {
		boolean sorted = true;
		Program[] temp = convertArrayListToArray(programList);
		for(int i = temp.length-1; i > 0; i--){
			sorted = true;
			for(int j = 0; j < i; j++){
				if(temp[j].compareTo(temp[j+1]) > 0){
					sorted = false;
					Program temporary = temp[j];
					temp[j] = temp[j+1];
					temp[j+1] = temporary;
				}
			}
		}
		return convertArrayToArrayListDescending(temp);
	}

	private static Program[] convertArrayListToArray (ArrayList<Program> programList) {
		Program[] newList = new Program[programList.size()];

		for (int i = 0; i < programList.size(); i++) {
			newList[i] = programList.get(i);
		}

		return newList;
	}

	private static ArrayList<Program> convertArrayToArrayListAscending(Program[] programList) {
		ArrayList<Program> temp = new ArrayList<>();
		for(int i=0; i<programList.length; i++){
			temp.add(programList[i]);
		}

		return temp;
	}
	private static ArrayList<Program> convertArrayToArrayListDescending(Program[] programList) {
		ArrayList<Program> temp = new ArrayList<>();
		for(int i=programList.length-1; i>=0; i--){
			temp.add(programList[i]);
		}

		return temp;
	}
	
	
	public static ArrayList<Program> searchByUni(String s)
	{
		ArrayList<Program> programList = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).searchUni(s))
				programList.add(programs.get(i)); //add the element from the list programs
		}
		return programList;
	}
   
	public static ArrayList<Program> searchByCode(String s)
	{
		ArrayList<Program> programList = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).searchOuacCode(s))
				programList.add(programs.get(i)); //add the element from the list programs
		}
		return programList;
	}
	
	public static ArrayList<Program> searchByMajor(String s)
	{
		ArrayList<Program> programList = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).searchMajor(s))
				programList.add(programs.get(i)); //add the element from the list programs
		}
		return programList;
	}

	public static ArrayList<Program> searchByProgram(String s)
	{
		ArrayList<Program> programList = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).searchProgram(s))
				programList.add(programs.get(i)); //add the element from the list programs
		}
		return programList;
	}
	
	public static ArrayList<Program> searchByAdmissionAverage(double k)
	{
		ArrayList<Program> programList = new ArrayList<Program>();
		int size = programs.size();
		for (int i = 0; i < size; i++)
		{
			if (programs.get(i).lowerThan(k))
				programList.add(programs.get(i)); //add the element from the list programs
		}
		return programList;
	}

	public static void addProgram()// by prompt
	{
		Program programList = new Program(programs.size());
		programs.add(programList);
		saveData();
		//intitate all variables with user input.
		
	}
   
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
       try{
         BufferedWriter out = new BufferedWriter(new FileWriter(PROGRAM));
         for (int i = 0; i < programs.size(); i ++)
         {
            programs.get(i).save(PROGRAM);
         }
         out.close();
     }
     catch(IOException iox)
     {
			
     }

   }
}
