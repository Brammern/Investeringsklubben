package Main;

import User.*;
import Utilities.ValidateUserID;

import java.util.Scanner;

/**
 * Klassen håndterer programmets hovedmenu, herunder login process for både admin og kunder.
 * Klassen håndterer brugerinput og opretter passende {@link User} objekter baseret på brugerens valg.
 * Menuen fungerer som et entry point til systemets flow.
 */
public class Menu {
/**Scanner til brugerinput fra konsol */
    private Scanner scanner;
    /** Det aktive brugerobjet admin eller kunde, der er logget ind. */
    private User user;

    /** Konstruktør der initaliserer input scanneren */
    public Menu() {
        scanner = new Scanner(System.in);
    }

    /**
     * videresender visningen til det aktive brugerobjekt.
     *
     * Metoden kalder {@link User#display()}, som viser enten admin eller member menu
     * Afhængig af hvem der er logget ind.
     */
    public void displayMenu(){user.display();}

    /**
     * Håndterer login process for admin og member.
     * Brugeren bliver spurgt om de vil logge ind som admin.
     * Ved svar ja oprettes et {@link Admin} objekt, og password validering sker automatisk
     * via Admin konstruktøren.
     * Ved svar nej bruger {@link ValidateUserID} til at validere navnet,
     * hvorefter der oprettes et {@link Member} objekt.
     *
     * Hvis brugeren indtaster noget ugyldigt, bliver de bedt om at prøve igen.
     */


    public void askAdminLogin() {
        while (true) {
            System.out.print("Vil du logge ind som Admin [Y/N]: ? ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                user = new Admin(this);
                user.display();
            }
            if (input.equals("n") || input.equals("no")) {
                ValidateUserID validateUserID = new ValidateUserID();
                String name = validateUserID.login();
                user = new Member(name);
                user.display();
            }
            System.out.println("Ugyldigt valg. Skriv enten yes/y eller no/n.");
        }
    }
 }








