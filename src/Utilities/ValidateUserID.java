package Utilities;

import FileHandler.CSVReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * ValidateUserID is a class used to check if a name exists in database
 * Has Attributes: <br>
 * {@link #validUsers} <br>
 * Has Methods: <br>
 * {@link #login()}
 */
public class ValidateUserID {
    /**
     * validUsers is a List&ltString&gt containing all Full Names stored in database
     */
    private List<String> validUsers = new ArrayList<>();

    public ValidateUserID() {
        CSVReader reader = new CSVReader("users");
        ArrayList<String[]> rows = reader.read();

        for (String[] row : rows) {
            validUsers.add(row[1]);
        }
    }

    /**
     * isValid checks if a username exists in {@link #validUsers}
     *
     * @param username the username to test
     * @return boolean if input username exists
     */
    private boolean isValid(String username) {
        return validUsers.contains(username);
    }

    /**
     * login gets a String input from user, then checks if it exists using {@link #isValid(String)}
     * @return the validated username as String
     */
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