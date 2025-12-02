package Main;

import User.*;
import Utilities.ValidateUserID;

import java.util.Scanner;


public class Menu {

    private Scanner scanner;
    private User user;

    // Konstruktør
    public Menu() {
        scanner = new Scanner(System.in);
    }
    public void displayMenu(){user.display();}


    public void askAdminLogin() {
        user = null;
        while (true) {
            System.out.print("Vil du logge ind som Admin [Y/N]: ? ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                user = new Admin(this);
                user.display();
                continue;
            }
            if (input.equals("n") || input.equals("no")) {
                ValidateUserID validateUserID = new ValidateUserID();
                String name = validateUserID.login();
                user = new Member(name);
                user.display();
                continue;
            }
            System.out.println("Ugyldigt valg. Skriv enten yes/y eller no/n.");
        }
    }
 }





