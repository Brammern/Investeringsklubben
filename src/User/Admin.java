package User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import Main.*;

public class Admin implements User {
    private ArrayList<Member> user;
    private Menu menu;

private Scanner scan = new Scanner(System.in);


    public Admin() {
        passwordValidate();
    }

    @Override
    public void display() {
        //shows all Admin methods

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

    public void passwordValidate(){
        String type = scan.nextLine();
        if(type.equals("1234")) {
            display();
        }else if(type.equalsIgnoreCase("Tilbage")){
            menu.displayMenu();
        }else{
            System.out.println("Prøv igen, eller skriv 'Tilbage' for at gå tilbage");
            passwordValidate();
        }

    }
}
