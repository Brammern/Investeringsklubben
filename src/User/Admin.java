package User;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Main.*;

public class Admin implements User {
    private ArrayList<Member> user;
    private Menu menu;

    private final Scanner scan = new Scanner(System.in);


    public Admin() {
        passwordValidate();
    }


    @Override
    public void display() {
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
                    menu.displayMenu();
                    break;

                default:
                    System.out.println("Ugyldigt valg, prøv igen!");
                    break;


            }
        }

    }


    public void portfolioOverview() {
        System.out.println("Portfolio" + user);
    }

    public void showRankings() {
        System.out.println("Top 5 inversteringer:");
        Collections.sort(user, (i1, i2) -> Integer.compare(i1.getInitialCash(), (i2.getInitialCash())));
        for (int i = 0; i < Math.min(5, user.size()); i++) {
            System.out.println(user.get(i));
        }

    }

    public void showStocks() {

    }

    public void passwordValidate() {
        String type = scan.nextLine();
        if (type.equals("1234")) {
            display();
        } else if (type.equalsIgnoreCase("Tilbage")) {
            menu.displayMenu();
        } else {
            System.out.println("Prøv igen, eller skriv 'Tilbage' for at gå tilbage");
            passwordValidate();
        }

    }
}