package com.example.demo;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
public class GenerateKeys {
    public static void main(String[] args) throws Exception {

// Step 1: Come up with a message we want to encrypt
        byte[] message = "NEE ANATA WA ITSUMO YUME WO MITE MASU KA?".getBytes();
// Step 2: Create a KeyGenerator object
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
// Step 3: Initialize the KeyGenerator with a certain keysize
        keyPairGenerator.initialize(512);
// Step 4: Generate the key pairs
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
// Step 5: Extract the keys
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
// Step 6: Create a Cipher object

        System.out.println("Private key is : " + privateKey);
        System.out.println("public key is : " + publicKey);

        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
// Step 7: Initialize the Cipher object
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
// Step 8: Give the Cipher our message
        cipher.update(message);
// Step 9: Encrypt the message
        byte[] ciphertext = cipher.doFinal();
// Step 10: Print the ciphertext
        System.out.println("message: " + new String(message, "UTF8"));
        System.out.println("ciphertext: " + new String(ciphertext, "UTF8"));
// Step 11: Change the Cipher object's mode
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
// Step 12: Give the Cipher objectour ciphertext
        cipher.update(ciphertext);
// Step 13: Decrypt the ciphertext
        byte[] decrypted = cipher.doFinal();
        System.out.println("decrypted: " + new String(decrypted, "UTF8"));
    }
}