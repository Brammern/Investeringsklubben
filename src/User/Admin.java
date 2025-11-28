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
        this.members = loadMemberFromCsv();
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
            System.out.println("5. Vis alle medlemmer");
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
                case "5":
                    showMembers();
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
        CSVReader transactionReader = new CSVReader("transactions");

        ArrayList<String[]> user = transactionReader.read();

        System.out.println("Alle medlemmer:");
        for (String[] row : user) {
            System.out.println(String.join(",", row));
        }
    }

    public void showRankings() {
        /*
        Mangler omregning fra valuta til DDK og comparater fra Medlem.
         */
        System.out.println("Top 5 medlemmer:");
        members.sort((m1, m2) -> m2.getInitialCash() - m1.getInitialCash());
        for (int i = 0; i < Math.min(5, members.size()); i++) {
            Member m = members.get(i);
            System.out.println((m.getUserId() + " - " + m.getFullName() + " - " + m.getInitialCash()));
        }
    }

    public void showStocks(){
        CSVReader stockReader = new CSVReader("stockMarket");

        ArrayList<String[]> user = stockReader.read();

        System.out.println("Alle aktier:");
        for (String[] rows : user) {
            System.out.println(String.join(",", rows));
        }
    }


    private ArrayList<Member> loadMemberFromCsv(){
        CSVReader readUser = new CSVReader("users");
        ArrayList<String[]> rows = readUser.read();
        ArrayList <Member> loadedMemberss = new ArrayList<>();

        for (String[] row : rows) {
            int userId = Integer.parseInt(row[0]);
            String fullName = row[1];
            String email = row[2];
            String birthDate = row[3];
            int initialCash = Integer.parseInt(row[4]);

            Member m = new Member (userId, fullName, email, birthDate, initialCash);
            loadedMemberss.add(m);
        }
        return loadedMemberss;
    }

    public void addUser() {

        int userId;

        while (true) {
            System.out.println("Indtast userId: (Helt tal)");
            try {
                userId = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Fejl, det skal være et helt tal!");
                continue;
            }
            if (!userIdExists(userId)) {
                break;
            }
        }

            System.out.println("Indtast fulde navn:");
            String fullName = scan.nextLine();

            System.out.println("Indtast email:");
            String email = scan.nextLine();

            System.out.println("Indtast fødselsdato (fx 17-05-1990):");
            String birthDate = scan.nextLine();

            int initialCash;
            while(true){
                System.out.println("Indtast startbeløb (Minimum 10.000 DKK.)");
                try{
                    initialCash=Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Fejl, skal være et helt tal!");
                    continue;
                }

                if (initialCash>=10000){
                    break;
                }
                System.out.println("Fejl, Startbeløb skal være mindst 10.000 DDK.");
            }

            Member m = new Member(userId, fullName, email, birthDate, initialCash);
            members.add(m);

            System.out.println("Medlem tilføjet: " + m);
        }

        public void showMembers () {
            System.out.println("---Alle Medlemmer---");
        for (Member m : members) {
                System.out.println(m);
            }
        }

        private boolean userIdExists ( int userId){
            for (Member m : members) {
                if (m.getUserId() == userId) {
                    return true;
                }
            }
            return false;
        }
    }