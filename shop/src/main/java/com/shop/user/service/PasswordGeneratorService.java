package com.shop.user.service;

import com.shop.user.model.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * <a href="https://www.quickprogrammingtips.com/java/how-to-securely-store-passwords-in-java.html">...</a>
 */
@Service
public class PasswordGeneratorService {
    public String passwordGenerator(String password) throws Exception {
        String salt = generateSalt();
        String encryptedPassword = generateEncryptedPassword(password,salt);

        return encryptedPassword;
    }
    public String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    public String generateEncryptedPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String algorithm = "PBKDF2WithHmacSHA256";
        int keyLength = 256;
        int iterations = 10000;

        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(),saltBytes,iterations,keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);

        byte[] encBytes = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(encBytes);
    }

    public boolean authenticateUser(User user, String password) throws Exception {
        String salt = user.getSalt();
        String hash = generateEncryptedPassword(password,salt);

        if (hash.equals(user.getPassword())){
            return true;
        }else {
            return false;
        }
    }
}
