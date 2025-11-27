package Utilities;

import FileHandler.CSVReader;
import java.util.ArrayList;
import java.util.List;
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

    public boolean isValid(String username) {
        return validUsers.contains(username);
    }
    public void login() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();

            if (isValid(username)) {
                System.out.println("Welcome, " + username);
                break;
            } else {
                System.out.println("Invalid username, try again.\n");
            }
        }

        scanner.close();
    }
}