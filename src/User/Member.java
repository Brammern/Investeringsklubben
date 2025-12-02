package User;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import CustomExceptions.InvalidUserIDException;
import FileHandler.CSVReader;
import FileHandler.TransactionWriter;
import Main.*;

import static FileHandler.TransactionHelper.getNextTransactionId;

public class Member implements User {
    private final Scanner scan = new Scanner(System.in);
    private Menu menu;
    private ArrayList<Holding> portfolio = new ArrayList<>();


    private int userId;
    private String fullName;
    private String email;
    private String birthDate;
    private int initialCash;
    private double totalValue;

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
        this.totalValue = calculateTotalValue();
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
    public String getBirthDate(){return birthDate;}


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
        System.out.println("\n--- Aktiemarked ---");

        CSVReader stockReader = new CSVReader("Stockmarket");
        ArrayList<String[]> rows = stockReader.read();

        if (rows.isEmpty()) {
            System.out.println("Ingen aktier fundet");
            return;
        }
        System.out.println("Ticker | Navn | Pris");

        for (String[] r : rows) {
            String ticker = r[0];
            String name = r[1];
            String price = r[3];
            System.out.println(ticker + " | " + name + " | " + price);
        }
    }

    public void showCurrency() {
        System.out.println("\n--- Valutakurser ---");

        CSVReader currencyReader = new CSVReader("Currency");
        ArrayList<String[]> rows = currencyReader.read();

        if (rows.isEmpty()) {
            System.out.println("Ingen data fundet");
            return;
        }
        System.out.println("Base | Quote | Rate");

        for (String[] r : rows) {
            String base = r[0];
            String quote = r[1];
            String rate = r[2];
            System.out.println(base + " | " + quote + " | " + rate);
        }
    }

    public void registerSale() {
        System.out.println("--- Registrer salg ---");
        System.out.print("Indtast hvilken aktie du har solgt: ");
        String stocks = scan.nextLine().toUpperCase();

        System.out.print("Hvor mange aktier har du solgt?: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Mængden skal være et tal. Salg afbrudt.");
            return;
        }
        //Tjekker om brugeren ejer aktien
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
        if (quantity > found.getQuantity()) {
            System.out.println("Du kan ikke sælge flere aktier end du ejer");
            return;
        }
        //Henter prisen fra aktiefilen
        CSVReader stockReader = new CSVReader("Stockmarket");
        ArrayList<String[]> rows = stockReader.read();

        Double price = null;
        String currency = null;

        for (String[] r : rows) {
            if (r[0].equalsIgnoreCase(stocks)) {
                price = Double.parseDouble(r[3]);
                currency = r[4];
                break;
            }
        }
        if (price == null) {
            System.out.println("Kunne ikke finde aktien");
            return;
        }
        //Opdatere portoføljen
        if (quantity == found.getQuantity()) {
            portfolio.remove(found);
            System.out.println("Alle" + stocks + " aktier er solgt til kurs " + price + " " + currency);
        } else {
            found.setQuantity(found.getQuantity() - quantity);
            System.out.println(quantity + " stk " + stocks + " akter solgt til kurs " + price + " " + currency);
        }
        //Skriver transaktionen til CSV
        int nextId = getNextTransactionId(); //Metode til at finde næste ledige ID
        TransactionWriter tw = new TransactionWriter("Transactions");
        tw.writeTransaction(nextId, this.userId, java.time.LocalDateTime.now().toString(), stocks, price, currency, "Sell", quantity);

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

    private void createMember(String fullName) {
        CSVReader userReader = new CSVReader("users");
        CSVReader transactionReader = new CSVReader("transactions");

        ArrayList<String[]> users = userReader.read();
        ArrayList<String[]> transactions = transactionReader.read();

        String[] userString = null;

        for (String[] user: users){
            if (user[1].equals(fullName)){
                userString = user;
            }
        }

        unwrapMember(userString);
        findTransactions(transactions);

    }

    private void unwrapMember(String[] memberInfo){
        if (memberInfo == null){throw new InvalidUserIDException("Bruger findes ikke");
        }
        this.userId = Integer.parseInt(memberInfo[0]);
        this.email = memberInfo[2];
        this.birthDate = memberInfo[3];
        this.initialCash = Integer.parseInt(memberInfo[4]);
    }
    
    private void findTransactions(ArrayList<String[]> transactions){
        for (String[] transaction: transactions){
            if (Integer.parseInt(transaction[1]) == this.userId){
                this.addHolding(new Holding(transaction));
            }
        }
    }
    private double calculateTotalValue(){
        double totalValue = 0;
        for(Holding holding: this.portfolio){
            totalValue += holding.getCurrentValue();
        }
        return totalValue;
    }

    @Override
    public String toString(){
        return userId + " - " + fullName + " - " + email + " - " + birthDate + " - " + initialCash + " DDK.";
    }
}
