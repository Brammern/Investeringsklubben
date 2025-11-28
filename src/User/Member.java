package User;

import java.util.ArrayList;
import java.util.Scanner;

import FileHandler.CSVReader;
import Main.*;

public class Member implements User {
    private final Scanner scan = new Scanner(System.in);
    private Menu menu;
    private ArrayList<Holding> portfolio = new ArrayList<>();


    private int userId;
    private String fullName;
    private String email;
    private String birthDate;
    private int initialCash;

    public Member(int userId, String fullName, String email, String birthDate, int initialCash) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.initialCash = initialCash;
        //ask for username and initialize fields
    }

    public Member(String fullName) {
        this.fullName = fullName;
        createMember(fullName);
    }

    public int getInitialCash() {
        return initialCash;
    }

    public String getFullName() {
        return fullName;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public void display() {
        //Mangler medlemmer ID + navn verificering - Grov skitse pt.

        boolean running = true;

        while (running) {
            System.out.println("---MEDLEM MENU---");
            System.out.println("1. Vis aktie Market");
            System.out.println("2. Vis currency");
            System.out.println("3. Registre salg");
            System.out.println("4. Registre køb");
            System.out.println("5. Vis portifølge");
            System.out.println("6. Vis transaktions historik");
            System.out.println("9. Tilbage til menu");
            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    showStockMarket();
                    break;
                case "2":
                    showCurrency();
                    break;
                case "3":
                    registerSale();
                    break;
                case "4":
                    registerPurchase();
                    break;
                case "5":
                    showPortfolio();
                    break;
                case "6":
                    showTransactionHistory();
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

    public void addHolding(Holding h) {
        portfolio.add(h);
    }

    public void showStockMarket() {
//Mangler filehandler
    }

    public void showCurrency() {
//Mangler filehandler

    }

    public void registerSale() {
       /*
       Skal kunne læse fra aktiefilen
       scan.nextline skal ændre med filereader
        */

        System.out.println("--- Registrer salg ---");
        System.out.print("Indtast hvilken aktie du har solgt: ");
        String stocks = scan.nextLine();

        System.out.print("Hvor mange aktier har du solgt?: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Mængden skal være et tal. Køb afbrudt.");
            return;
        }

        Holding found = null;
        for (Holding h : portfolio) {
            if (h.getTicker().equalsIgnoreCase(stocks)) {
                found = h;
                break;
            }
        }

        if (found == null) {
            System.out.println("Du har ingen aktier med navnet: " + stocks + " i din portefølge");
            return;
        }

        int currentQty = found.getQuantity();
        if (quantity >= currentQty) {
            portfolio.remove(found);
            System.out.println("Du har solgt alle dine af:" + stocks + " (" + currentQty + " stk.)");
        } else {
            int newQuantity = currentQty - quantity;
            found.setQuantity(newQuantity);
            System.out.println("Du har nu: " + newQuantity + " aktier af: " + stocks + " tilbage");
        }

    }

    public void registerPurchase() {
        /*MANGLER følgende:
        Skal læse fra fil
        Skal kunne fjerne fra portfølge array under det gældende medlem
         */

        System.out.println("--- Registrer køb ---");
        System.out.print("Indtast hvilken aktie du har købt: ");
        String ticker = scan.nextLine();


        System.out.print("Hvor mange aktier har du købt?: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Antallet skal være et tal. Registrering afbrudt.");
            return;
        }

        //to be fixed
        //Holding h = new Holding(ticker, quantity);
        //addHolding(h);
        //System.out.println("Tilføjet til din portefølje: " + h);
    }

    public void showPortfolio() {
        System.out.println("--- Din portefølje ---");
        if (portfolio.isEmpty()) {
            System.out.println("Du har ingen investeringer endnu.");
        } else {
            for (Holding h : portfolio) {
                System.out.println(h);
            }
        }
    }


    public void showTransactionHistory() {

    }

    public void createMember(String fullName) {
        CSVReader userReader = new CSVReader("users");
        CSVReader transactionReader = new CSVReader("transactions");

        ArrayList<String[]> user = userReader.read();
        ArrayList<String[]> transaction = transactionReader.read();

    }

    @Override
    public String toString(){
        return userId + " - " + fullName + " - " + email + " - " + birthDate + " - " + initialCash + " DDK.";
    }
}
