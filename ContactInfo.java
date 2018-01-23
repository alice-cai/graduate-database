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
	}
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
   }
   
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
	}
	
	public String toString ()
	{
		return("Phone: " + phone + "\nEmail: " + email + "\naddress: "+ address + "\nPerson: " + person + "\n");
	}
   
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
   }
	
}