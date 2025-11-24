package Utilities;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

/**
 * <a href ="https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/">Hashing guide</a>
 */
public class ValidatePassword {
    private final String correctHashedPassword = "1000:2ed1bb61fc9a0a3c6882498539603613:d6cce0b043d20a62670ab9d9dc333b1e1521705c57c864686eea1f4ce0c4a2e1d72acb579103e33dcacfade6b51fe3401caf53d72c0936b76bc22a19435829c6";

    public boolean enterPassword(){
        String input = getUserInput();
        try {
            return validatePassword(input);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUserInput(){
        Scanner sc = new Scanner(System.in);

        return sc.nextLine();
    }

    private boolean validatePassword(String originalPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = correctHashedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
//password is admin
//1000:2ed1bb61fc9a0a3c6882498539603613:d6cce0b043d20a62670ab9d9dc333b1e1521705c57c864686eea1f4ce0c4a2e1d72acb579103e33dcacfade6b51fe3401caf53d72c0936b76bc22a19435829c6
