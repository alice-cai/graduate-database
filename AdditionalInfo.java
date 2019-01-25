import java.util.*;
import java.io.*;

public class AdditionalInfo
{
	private String scholarship;
	private String supplementary;
	private String earlyAdm;

	public AdditionalInfo(String sc, String su, String e)
	{
		scholarship = sc;
		supplementary = su;
		earlyAdm = e;
	}//Initializes from the Program Database class, with readings from the file

	public AdditionalInfo()
	{
		System.out.print ("Enter scholarship options: ");
		scholarship=Method.sc.nextLine();
		System.out.print ("Enter supplementary requirement: ");
		supplementary=Method.sc.nextLine();
		System.out.print ("Enter early admission information: ");
		earlyAdm=Method.sc.nextLine();
	}//Initialization from admin's input

	public String getScholarship()
	{
		return scholarship;
	}  

	public String getSupplementary()
	{
		return supplementary;
	}

	public String getEarlyAdm()
	{
		return earlyAdm;
	}//for accessing the information (in case if we need to)

	public void display()
	{
		String input;
		System.out.println(this);
		System.out.print("Press any key to return to previous menu.");
		input = Method.sc.nextLine(); 
	}//displays the information

	public String toString ()
	{
		return("Scholarship options: " + scholarship + "\nSupplementary requirement: " + supplementary + "\nEarly admission date: "+ earlyAdm +"\n");
	}//For displaying purposes


	public void save (String file)
	{
		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
			out.write(scholarship);out.newLine();
			out.write(supplementary);out.newLine();
			out.write(earlyAdm);out.newLine();
			out.close();
		}
		catch(IOException iox)
		{
			System.out.println("Problem saving additional information.");
		}
	}//Saving the changes to text file
}
