package com.freddiemac.loanacquisition.security;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncryptionUtil {

	public static String generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        
        SecretKey secretKey = keyGen.generateKey();
        
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
	
	 public static String encryptPassword(String password, String secretKey) throws Exception {

		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, originalKey);
        
        byte[] encryptedPasswordBytes = cipher.doFinal(password.getBytes());
        
        return Base64.getEncoder().encodeToString(encryptedPasswordBytes);
    }

    public static String decryptPassword(String encryptedPassword, String secretKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, originalKey);
        
        byte[] decryptedPasswordBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        
        return new String(decryptedPasswordBytes);
    }
}
