/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.utilities;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author miki
 */
public class PasswordUtils {
    
    private PasswordUtils() { }
    
    private static final int SALT_LENGTH = 16; // 128 bits
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256; // 256 bits
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
        
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    public static String hashPassword(String password, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), 
                saltBytes, 
                ITERATIONS, 
                KEY_LENGTH
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    public static boolean verifyPassword(String password, String storedHash, String storedSalt) {
        String calculatedHash = hashPassword(password, storedSalt);
        return calculatedHash.equals(storedHash);
    }
    
    /**
     * Generates a new salt and hashes the password
     * @param password The password to hash
     * @return String array where [0] = hash, [1] = salt
     */
    public static String[] createHashedPassword(String password) {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        return new String[]{hash, salt};
    }
}
