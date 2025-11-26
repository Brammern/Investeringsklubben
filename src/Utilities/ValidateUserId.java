package Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    public class ValidateUserID {

        private static final int MIN_LENGTH = 6;
        private static final int MAX_LENGTH = 20;
        private static final String VALID_REGEX = "^[A-Za-z][A-Za-z0-9_-]*$";


        public static ValidateUserId() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("=== Investment Club User ID Validator ===");

            String userId;
            ValidationResult result;


            while (true) {
                System.out.print("\nEnter User ID to validate: ");
                userId = scanner.nextLine();

                result = validateUserId(userId);

                if (result.isValid()) {
                    System.out.println("User ID '" + userId + "' is VALID");
                    break;
                }

                System.out.println("User ID '" + userId + "' is INVALID");
                System.out.println("Reasons:");
                for (String error : result.getErrors()) {
                    System.out.println("  - " + error);
                }

                System.out.println("\nPlease try again.");
            }

            scanner.close();
        }

        public static ValidationResult validateUserId(String id) {
            ValidationResult result = new ValidationResult();

            if (id == null || id.isEmpty()) {
                result.addError("User ID cannot be empty");
                return result;
            }

            if (id.contains(" ")) result.addError("User ID cannot contain spaces");
            if (id.length() < MIN_LENGTH) result.addError("Too short (min 6 characters)");
            if (id.length() > MAX_LENGTH) result.addError("Too long (max 20 characters)");

            if (!id.matches(VALID_REGEX))
                result.addError("Must start with a letter and contain only letters, numbers, '_' or '-'");

            return result;
        }
    }

    class ValidationResult {
        private final List<String> errors = new ArrayList<>();

        public void addError(String error) { errors.add(error); }
        public boolean isValid() { return errors.isEmpty(); }
        public List<String> getErrors() { return errors; }
    }

}
