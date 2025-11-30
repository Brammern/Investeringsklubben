package User;

import java.util.ArrayList;
import java.util.Scanner;

import CustomExceptions.InvalidPasswordException;
import FileHandler.CSVReader;
import Main.*;
import Utilities.ValidatePassword;

public class Admin implements User {
    private ArrayList<Member> members = new ArrayList<>();
    private Menu menu;


    public Admin(Menu menu) {
        ValidatePassword validatePassword = new ValidatePassword();
        System.out.println("Indtast password");
        if (!validatePassword.enterPassword()) {
            throw new InvalidPasswordException("Invalid Password");
        }
        this.menu = menu;
        memberFactory();
    }

    private final Scanner scan = new Scanner(System.in);


    @Override
    public void display() {

        boolean running = true;
        while (running) {
            System.out.println("---ADMIN MENU---");
            System.out.println("1. Portefølge");
            System.out.println("2. Rangliste (top 5)");
            System.out.println("3. Aktier");
            System.out.println("4. Tilføj medlem");
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
                case "4":
                    addUser();
                    break;
                case "9":
                    running = false;
                    if (menu != null) {
                        menu.askAdminLogin();
                    }
                    break;

                default:
                    System.out.println("Ugyldigt valg, prøv igen!");
                    break;


            }
        }


    }


    public void portfolioOverview() {
       /*
       Mangler portefølge, skal trækkes fra filereader.
        */

        System.out.println("Alle medlemmer:");
        for (Member m : members) {
            System.out.println(m.getUserId() + " - " + m.getFullName() + " - " + m.getInitialCash() + " DKK");
        }
    }

    public void showRankings() {
        /*
        Burde umiddelbart være færdig - Sammenligner initialcash så man kan se hvem der har flest penge
        Muligvis skal den også læse fra aktier.
         */
        System.out.println("Top 5 medlemmer:");
        members.sort((m1, m2) -> m2.getInitialCash() - m1.getInitialCash());
        for (int i = 0; i < Math.min(5, members.size()); i++) {
            Member m = members.get(i);
            System.out.println((m.getUserId() + " - " + m.getFullName() + " - " + m.getInitialCash()));
        }
    }

    public void showStocks() {
//Mangler filehandler
    }

    public void addUser(){
        members.add(new Member (""));
    }

    private void memberFactory(){
        CSVReader userReader = new CSVReader("users");
        ArrayList<String[]> userStrings = userReader.read();
        ArrayList<String> names = new ArrayList<>();
        for (String[] user: userStrings){
            names.add(user[1]);
        }
        for(String name: names){
            members.add(new Member(name));
        }
    }
}