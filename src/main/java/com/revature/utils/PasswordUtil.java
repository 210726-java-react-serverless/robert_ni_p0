package com.revature.utils;

import com.revature.utils.exceptions.DataSourceException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

public class PasswordUtil {

    private String salt;
    private final Random random = new SecureRandom();

    public PasswordUtil() {
        Properties props = new Properties();

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            props.load(loader.getResourceAsStream("application.properties"));
            this.salt = props.getProperty("salt");
            if (salt == null) {
                throw new IllegalStateException("No salt found for password encryption");
            }
        } catch (IOException e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

    public String generateSecurePassword(String password) {
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    private byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, 10000, 256);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new AssertionError("Error while hashing password", e);

        } finally {
            spec.clearPassword();
        }
    }
}
