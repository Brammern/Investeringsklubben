package Utilities;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

/**
 * Validate Password is a password validation Class using PBEKey and PBKDF2WithHmacSHA1 algorithm for encoding.
 * <a href ="https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/">Hashing guide</a> <br>
 * Has Attributes: <br>
 * {@link #correctHashedPassword} the correctHashedPassword <br>
 * Has Methods: <br>
 * {@link #enterPassword()} is the main function for password validation
 */
public class ValidatePassword {
    /**
     * correctHashedPassword is as it says
     */
    private final String correctHashedPassword = "1000:2ed1bb61fc9a0a3c6882498539603613:d6cce0b043d20a62670ab9d9dc333b1e1521705c57c864686eea1f4ce0c4a2e1d72acb579103e33dcacfade6b51fe3401caf53d72c0936b76bc22a19435829c6";

    /**
     * enterPassword gets a user input with {@link #getUserInput()}, the validates it using {@link #validatePassword(String)}
     *
     * @return boolean that is true if {@link #validatePassword(String)} passes, otherwise returns false
     */
    public boolean enterPassword() {
        String input = getUserInput();
        try {
            return validatePassword(input);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getUserInput is a simple function for getting user input using {@link Scanner}
     *
     * @return String containing the user input
     */
    private String getUserInput() {
        Scanner sc = new Scanner(System.in);

        return sc.nextLine();
    }

    /**
     * validatePassword validates if the user input matches {@link #correctHashedPassword}.
     * To do this the input is encoded with the same seed and salt as {@link #correctHashedPassword}.
     * All encoding uses the PBKDF2WithHmacSHA1 algorithm, and is converted to hexidecimal using {@link #fromHex(String)}
     *
     * @param originalPassword Password input by the user
     * @return boolean if input password matches stored password
     * @throws NoSuchAlgorithmException exception if PBKDF2WithHmacSHA1 does not exist
     * @throws InvalidKeySpecException exception if {@link SecretKeyFactory} fails to generate a valid key
     */
    private boolean validatePassword(String originalPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = correctHashedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
//password is admin
//1000:2ed1bb61fc9a0a3c6882498539603613:d6cce0b043d20a62670ab9d9dc333b1e1521705c57c864686eea1f4ce0c4a2e1d72acb579103e33dcacfade6b51fe3401caf53d72c0936b76bc22a19435829c6
