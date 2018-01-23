/**
* User.java
* This abstract class represents a skeleton for a User object.
*/

public abstract class User {
	protected String username;
	protected String password;
	
	public User () {
		username = "";
		password = "";
	}
	
	public User (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername () {
		return username;
	}
	
	public String getPassword () {
		return password;
	}

	public abstract void loadMainMenu();
	
	public abstract void displayMainMenu();
	
}