package User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import CustomExceptions.InvalidPasswordException;
import Main.*;

public class Admin implements User {
    private ArrayList<Member> members = new ArrayList<>();
    private Menu menu;


    public Admin(ArrayList<Member> members, Menu menu) {
        this.members = members;
        this.menu = menu;
    }

    private final Scanner scan = new Scanner(System.in);


    public void admin() {
        display();
    }


    @Override
    public void display() {

        if (!login()) {
            if (menu != null) {
                menu.displayMenu();
            }
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("---ADMIN MENU---");
            System.out.println("1. Portefølge");
            System.out.println("2. Rangliste (top 5)");
            System.out.println("3. Aktier");
            System.out.println("9. Tilbage til menu");
            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    portfolioOverview();
                    break;
                case "2":
                    showRankings();
                    break;
                case "3":
                    showStocks();
                    break;
                case "9":
                    running = false;
                    if (menu != null) {
                        menu.displayMenu();
                    }
                    break;

                default:
                    System.out.println("Ugyldigt valg, prøv igen!");
                    break;


            }
        }

    }


    public void portfolioOverview() {
        System.out.println("Alle medlemmer:");
        for (Member m : members) {
            System.out.println(m.getUserId() + " - " + m.getFullName() + " - " + m.getInitialCash() + " DKK");
        }
    }

    public void showRankings() {
        System.out.println("Top 5 inversteringer:");
        members.sort((m1,m2) -> m2.getInitialCash() - m1.getInitialCash());
        for (int i = 0; i < Math.min(5, members.size()); i++) {
            Member m = members.get(i);
            System.out.println((m.getUserId() + " - " + m.getFullName() + " - " + m.getInitialCash()));
        }

    }

    public void showStocks() {
//Mangler filehandler
    }

    public boolean login() {
        while (true) {
            System.out.println("---ADMIN LOGIN---");
            System.out.println("Skriv adgangskoden:");
            String input = scan.nextLine();

            if (input.equalsIgnoreCase("Tilbage")){
                System.out.println("Returnere til hovedmenuen... \n");
                return false;
            }

            try {
                passwordValidator.validate(input);
                System.out.println("Login lykkedes! \n");
                return true;
            } catch (InvalidPasswordException e) {
                System.out.println(e.getMessage());
            }

//            if (input.equals("1234")) {
//                return true;
//            } else if (input.equalsIgnoreCase("Tilbage")) {
//                System.out.println("Går tilbage til menuen! \n");
//                return false;
//            } else {
//                System.out.println("Prøv igen, eller skriv 'Tilbage' for at gå tilbage");
//            }

        }
    }
}