/**
* Guest.java
* This class represents a Guest user. It is responsible for loading and displaying
* menu options for Guest.
*/

import java.util.*;
import java.io.*;

public class Guest extends User {
	private final String MENU = "user_menus/Guest_Menu.txt";
	private int numMenuOptions;
	private String[] menuOptions;

	public Guest () {
		loadMainMenu();
	}

	public void loadMainMenu () {
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

	/**
	* Displays the menu options for a Guest user and prompts the user to choose
	* one of the menu options. It calls different methods based on which option
	* is chosen.
	*/
	public void displayMainMenu(){
		Scanner sc = new Scanner(System.in);
		int choice = 0;

		while (true) {
			// displays main menu of options
			System.out.println("\nGuest Menu");
			for (int i = 0; i < numMenuOptions; i++) {
				System.out.println(menuOptions[i]);
			}

			// prompts user for input until they enter a valid choice number
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

			// calls method corresponding to the option chosen by user
			switch (choice){
				case 1:
					QNAGuest.initQNA();
					QNAGuest.displayMenu();
					break;
				case 2:
					InformationSystem.getProgramDatabase().displayMenu();
					break;
				case 3:
					return;
			}
			choice = 0;
		}
	}
}
