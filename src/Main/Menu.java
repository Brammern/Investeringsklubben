package Main;

import User.*;

import java.util.Scanner;


public class Menu {

    private Scanner scanner;
    private User user;

    // Konstruktør
    public Menu() {
        scanner = new Scanner(System.in);
    }
    public void displayMenu(){user.display();}


    private void askAdminLogin() {
        while (true) {
            System.out.print("Vil du logge ind som Admin [Y/N]: ? ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                user = new Admin();
                user.display();
            }
            if (input.equals("n") || input.equals("no")) {
                user = new Member();
                user.display();
            }
            System.out.println("Ugyldigt valg. Skriv enten yes/y eller no/n.");
        }
    }

}








