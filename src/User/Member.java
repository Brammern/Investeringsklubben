package User;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import CustomExceptions.*;
import FileHandler.CSVReader;
import FileHandler.TransactionWriter;
import Main.*;

import static FileHandler.TransactionHelper.getNextTransactionId;

public class Member implements User, Comparable<Member> {
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
    public double getTotalValue(){return this.totalValue;}


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

        if (ticker.isEmpty()) {
            System.out.println("Aktienavn kan ikke være tomt. Registrering afbrudt.");
            return;
        }

        CSVReader stockReader = new CSVReader("stockMarket");
        ArrayList<String[]> stocks = stockReader.read();
        String stockName = null;
        String priceStr = null;
        String currency = "DKK";
        for (String[] stock : stocks) {
            if (stock == null || stock.length < 5) continue;
            if (stock[0].equalsIgnoreCase(ticker)) {
                stockName = stock[1];
                priceStr = stock[3];
                currency = stock[4];
                break;
            }
        }

        if (priceStr == null) {throw new StockNotFoundException("Aktien " + ticker + " blev ikke fundet på markedet.");}

        System.out.print("Hvor mange aktier har du købt?: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scan.nextLine());
            if (quantity <= 0) {
                System.out.println("Antallet skal være et positivt tal. Registrering afbrudt.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Antallet skal være et tal. Registrering afbrudt.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            System.out.println("Ugyldig prisformat for aktien " + ticker + ". Registrering afbrudt.");
            return;
        }

        // Ensure userId is set
        userIdFromName();

        Holding existing = null;
        for (Holding h : portfolio) {
            if (h.getTicker().equalsIgnoreCase(ticker)) {
                existing = h;
                break;
            }
        }

        String today = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (existing == null) {
            Holding h = new Holding(userId, today, ticker, price, currency, "BUY", quantity);
            addHolding(h);
        } else {
            int oldQty = existing.getQuantity();
            double oldPrice = existing.getPrice();
            int newQty = oldQty + quantity;
            double newPrice = ((oldPrice * oldQty) + (price * quantity)) / newQty;
            portfolio.remove(existing);
            Holding h = new Holding(userId, today, ticker, newPrice, currency, "BUY", newQty);
            addHolding(h);
        }

        System.out.println("Registreret køb: " + ticker + " (" + stockName + ") x" + quantity + " til " + price +
                " " + currency + " pr. stk.\n");

        //Writes the transaction to CSV
        int nextId = getNextTransactionId();
        TransactionWriter tw = new TransactionWriter("Transactions");
        tw.writeTransaction(nextId, userId, today, ticker, price, currency, "buy", quantity);
    }

    public void userIdFromName() {
        if (userId == 0 && fullName != null && !fullName.isEmpty()) {
            // Finder userID baseret på fulde navn hvis ID ikke er sat
            CSVReader userReader = new CSVReader("users");
            ArrayList<String[]> users = userReader.read();
            for (String[] user : users) {
                if (user[1].equalsIgnoreCase(fullName)) {
                    userId = Integer.parseInt(user[0]);
                    break;
                }
            }
        } else if (userId == 0) {
            System.out.println("Bruger ikke fundet. Kan ikke registrere køb.");
            return;
        }
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
        CSVReader transactionReader = new CSVReader("transactions");
        ArrayList<String[]> transaction = transactionReader.read();

        // Ensure userId is set
        userIdFromName();

        System.out.println("--- Din købshistorik ---");

        boolean found = false;
        String myIdStr = String.valueOf(userId);
        for (String[] record : transaction) {
            if (record == null || record.length == 0) continue;
            if (record[0].equalsIgnoreCase("id") || record[1].equalsIgnoreCase("user_id")) {
                continue;
            }
            if (record[1].equals(myIdStr)) {
                found = true;
                System.out.println("Transaktion ID: " + record[0] +
                        ", Dato: " + record[2] +
                        ", Aktie: " + record[3] +
                        ", Pris: " + record[4] +
                        ", Valuta: " + record[5] +
                        ", Type: " + record[6] +
                        ", Antal: " + record[7]);
            }
        }
        System.out.println("---------------------------\n");
        if (!found) {throw new InvalidUserIDException("Ingen transaktioner fundet for bruger ID: " + userId);}
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
    public double calculateGrowth(){
        return (this.totalValue/this.initialCash)*100;
    }

    @Override
    public String toString() {
        return userId + " - " + fullName + " - " + email + " - " + birthDate + " - " + initialCash + " DKK";
    }
    @Override
    public int compareTo(Member m){
        return (int)((this.calculateGrowth() - m.calculateGrowth())*1000);
    }
}
