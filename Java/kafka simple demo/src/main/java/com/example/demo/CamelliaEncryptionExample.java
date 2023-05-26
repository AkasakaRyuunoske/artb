package com.example.demo;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

public class CamelliaEncryptionExample {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        String message = "Hello, World!";
        String key = "ThisIsASecretKey";

        // Generate a random IV
        byte[] ivBytes = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(ivBytes);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        // Create a Camellia cipher instance and initialize it with the key and IV
        Cipher cipher = Cipher.getInstance("Camellia/CBC/PKCS7Padding", "BC");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Camellia");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        // Encrypt the message
        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

        // Encode the encrypted bytes and IV using Base64 for convenient display or transmission
        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedBytes);
        String encodedIV = Base64.getEncoder().encodeToString(ivBytes);
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("IV: " + encodedIV);

        // Create a new cipher instance for decryption and initialize it with the key and IV
        Cipher decryptionCipher = Cipher.getInstance("Camellia/CBC/PKCS7Padding", "BC");
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        // Decode the Base64 encoded message and IV
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decodedIV = Base64.getDecoder().decode(encodedIV);

        // Decrypt the message
        byte[] decryptedBytes = decryptionCipher.doFinal(decodedBytes);

        // Convert the decrypted bytes back to a string
        String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);
        System.out.println("Decrypted message: " + decryptedMessage);
    }
}

