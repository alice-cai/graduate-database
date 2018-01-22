import java.util.*;

public class ContactInfo
{
	private String phone;
	private String email;
	private String address;
	private String person;
   Scanner sc = new Scanner (System.in);
   
	public ContactInfo(String ph, String e, String a, String p)
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
		return("Phone: " + phone + "\nEmail: " + email + "\naddress: "+ address + "\nperson: " + person + "\n");
	}
	//ArrayList <Integer> hehe = new ArrayList <>();
	
}