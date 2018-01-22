import java.util.*;
import java.io.*;

public class ContactInfo
{
	private String phone;
	private String email;
	private String address;
	private String person;
	Scanner sc = new Scanner (System.in);
   
	public ContactInfo(String ph, String e, String p, String a)
	{
		phone=ph;
		email = e;
		address = a;
		person = p;
	}//This is initilized from the Program Database class
	public ContactInfo()
	   {
	      System.out.print ("Enter program phone number: ");
	      phone=sc.nextLine();
	      System.out.print ("Enter program Email: ");
	      email=sc.nextLine();
	      System.out.print ("Enter program address: ");
	      address=sc.nextLine();
	      System.out.print ("Enter contact person: ");
	      person=sc.nextLine();
	   }//This is initilized from admin's input

   public String getPhone()
   {
      return phone;
   }

   public String getEmail()
   {
      return email;
   }

   public String getPerson()
   {
      return person;
   }

   public String getAddress()
   {
      return address;
   }
   
	public void display()
	{
		Scanner sc  = new Scanner (System.in);
		String input;
		System.out.println(this);
		System.out.print("Press any key to return to previous menu.");
		input = sc.nextLine(); 
	}//This is displayed once the user chooses to view the program
	
	public String toString ()
	{
		return("Phone: " + phone + "\nEmail: " + email + "\naddress: "+ address + "\nperson: " + person + "\n");
	}//This is for displaying purposes
   
	public void save (String file)
   {
      try
      {
         BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
         out.write(phone);out.newLine();
         out.write(email);out.newLine();
         out.write(person);out.newLine();
         out.write(address);out.newLine();
         out.close();
      }
      catch(IOException iox)
      {
         System.out.println("Problem saving contact information.");
      }
   }//Saves contact information of a program
	
}
