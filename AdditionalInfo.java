import java.util.*;

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
	}
   public AdditionalInfo()
	{
      System.out.print ("Enter scholarship options: ");
      scholarship=Method.sc.nextLine();
      System.out.print ("Enter supplementary requirement: ");
      supplementary=Method.sc.nextLine();
      System.out.print ("Enter early admission information: ");
      earlyAdm=Method.sc.nextLine();

	}
	public void display()
	{
		String input;
		System.out.print(this);
		System.out.print("Press any key to return to previous menu.");
		input = Method.sc.nextLine(); 
	}
	
	public String toString ()
	{
		return("Scholarship options: " + scholarship + "\nSupplementary requirement: " + supplementary + "\nEarly admission date: "+ earlyAdm + "\n\n");
	}
}