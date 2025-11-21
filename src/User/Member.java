package User;

import java.util.Scanner;
import Main.*;

public class Member implements User{
    private final Scanner scan = new Scanner(System.in);
    private Menu menu;

    int userId;
    String fullName;
    String email;
    String birthDate;
    int initialCash;


    public Member(int userId, String fullName, String email, String birthDate, int initialCash){
        this.userId=userId;
        this.fullName=fullName;
        this.email=email;
        this.birthDate=birthDate;
        this.initialCash=initialCash;
        //ask for username and initialize fields
    }

    public int getInitialCash() {
        return initialCash;
    }

    @Override
    public void display(){
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
                case "5":
                    showPortfolio();
                case "6":
                    showTransactionHistory();
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

    public void showStockMarket(){

    }
    public void showCurrency(){

    }
    public void registerSale(){

    }
    public void registerPurchase(){

    }
    public void showPortfolio(){

    }
    public void showTransactionHistory(){

    }
}
