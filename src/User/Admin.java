    package User;

    import java.text.DateFormat;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Scanner;

    import CustomExceptions.InvalidPasswordException;
    import FileHandler.CSVReader;
    import FileHandler.WriteUser;
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
            System.out.println("Alle medlemmer:");
            for (Member m : members) {
                System.out.println(m);
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

        public void showStocks() {
            CSVReader stockReader = new CSVReader("stockMarket");

            ArrayList<String[]> stocks = stockReader.read();

        System.out.println("Alle aktier:");
        System.out.println("Ticker, Navn, Sektor, Pris, Valuta, Rating, Udbytte, Marked, Seneste Opdatering");
        for (String[] rows : stocks) {
            System.out.println(String.join(", ", rows));
        }
    }

        private void memberFactory() {
            CSVReader userReader = new CSVReader("users");
            ArrayList<String[]> userStrings = userReader.read();
            ArrayList<String> names = new ArrayList<>();
            for (String[] user : userStrings) {
                names.add(user[1]);
            }
            for (String name : names) {
                members.add(new Member(name));
            }
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

            String birthDate = null;
            while (true) {

                System.out.println("Indtast fødselsdato (dd-MM-yyyy):");
                birthDate = scan.nextLine();
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                formatter.setLenient(false);
                try {
                    formatter.parse(birthDate);
                    break;
                } catch (ParseException e) {
                    System.out.println("Ugyldig datoformat. Prøv igen (dd-mm-yyyy)");
                }


                int initialCash;
                while (true) {
                    System.out.println("Indtast startbeløb (Minimum 10.000 DKK.)");
                    try {
                        initialCash = Integer.parseInt(scan.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Fejl, skal være et helt tal!");
                        continue;
                    }

                    if (initialCash >= 10000) {
                        break;
                    }
                    System.out.println("Fejl, Startbeløb skal være mindst 10.000 DDK.");
                }

                Member m = new Member(userId, fullName, email, birthDate, initialCash);
                members.add(m);
                WriteUser w = new WriteUser(m);
                w.writer();

                System.out.println("Medlem tilføjet: " + m);
            }
        }

        public void showMembers() {
            System.out.println("---Alle Medlemmer---");
            for (Member m : members) {
                System.out.println(m);
            }
        }

        private boolean userIdExists(int userId) {
            for (Member m : members) {
                if (m.getUserId() == userId) {
                    System.out.println("UserID er allerede i brug!");
                    return true;
                }
            }
            return false;
        }
    }
