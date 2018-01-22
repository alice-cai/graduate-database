//User abstract class
public abstract class User {

	//Fields of the User class.
	protected String username;
	protected String password;
	
	//Constructors: 1 default 1 that takes in two strings
	public User () {
		username = " ";
		password = " ";
	}
	
	public User (String u, String p) {
		username = u;
		password = p;
	}
	
	//methods
	//accessors mutators
	public String getUsername () {
		return username;
	}
	
	public String getPassword () {
		return password;
	}
	
	//change username. First parameter is check password, second parameter is 
	// the updated username
	public boolean changeUsername (String p, String n) {
		if(password == p){
			username = n;
			return true;
		}
		
		return false;
	}
	
	//change password. First parameter is check password, second parameter is 
	// the updated password
	public boolean changePassword (String p, String n) {
		if(password == p){
			password = n;
			return true;
		}
		
		return false;
	}
	
	public abstract void displayMainMenu();
	
}