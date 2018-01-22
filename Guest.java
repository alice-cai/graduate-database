import java.util.*;
import java.io.*;

//Guest user class. Extends User
public class Guest extends User{
	private final String MENU = "user_menus/Guest_Menu.txt";
	private int numMenuOptions;
	private String[] menuOptions;

	public Guest () {
		loadMenu();
	}

	public void loadMenu () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(MENU));
			numMenuOptions = Integer.parseInt(in.readLine());
			in.readLine();

			menuOptions = new String[numMenuOptions];
			for (int i = 0; i < numMenuOptions; i++) {
				menuOptions[i] = in.readLine();
			}

			in.close();
		} catch(IOException iox){
			System.out.println("Error reading guest menu from file.");
		}
	}

	public void displayMainMenu(){
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		boolean keepOnGoing = true;

		while(keepOnGoing){
			System.out.println("\nGuest Menu");
			for (int i = 0; i < numMenuOptions; i++) {
				System.out.println(menuOptions[i]);
			}

			while (!(choice > 0 && choice <= numMenuOptions)) {
				try {
					System.out.print("Enter an option: ");
					choice = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException ime) {
					sc.nextLine();
					System.out.print("Invalid input. ");
				}
			}

			//executes the choice number				
			switch (choice){
				case 1:
					QNAGuest.initQNA();
					QNAGuest.displayMenu();
					break;
				case 2:
					// I think you need to somehow get an instance of Program Database available for the user object
					break;
				case 3:
					keepOnGoing = false;
					break;
			}
			choice = 0;
		}
	}
}
