package Utilities;

import FileHandler.CSVReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ValidateUserID {

    private List<String> validUsers = new ArrayList<>();

    public ValidateUserID() {
        CSVReader reader = new CSVReader("users");
        ArrayList<String[]> rows = reader.read();

        for (String[] row : rows) {
            validUsers.add(row[1]);
        }
    }

    private boolean isValid(String username) {
        return validUsers.contains(username);
    }
    public String login() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Indtast dit fulde navn: ");
            String username = scanner.nextLine();

            if (isValid(username)) {
                System.out.println("Velkommen, " + username);
                return username;
            } else {
                System.out.println("Forkert brugernavn, prøv igen.\n");
            }
        }

    }
}