package com.example.demo;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class KafkaController {
//    @Autowired
//    KafkaTemplate<String, String> template;
//
//    @GetMapping("/message/{message}")
//    public String testMessage(@PathVariable("message") String message) {
//        template.send("utsup", message);
//        return message + " was send";
//    }

    @Autowired
    AnaataRepository anaataRepository;


    @GetMapping("/asymmetric_key_request_test")
    ResponseEntity<String> RSAController() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
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


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        String message = "Climb Niitaka Mound";
        Map<String, String> payload = new HashMap<>();

        payload.put("encryptedMessage", Base64.getEncoder().encodeToString(ciphertext));
//        payload.put("IV", Arrays.toString(ivBytes));
        payload.put("PK", publicKey.toString());

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(payload, headers);

        String uri = "http://localhost:8080/asymmetric_key_request_test";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, httpEntity, String.class);

        log.info("Result is: " + result);


        return new ResponseEntity<>("{\"The message\":\"is dying\"}", HttpStatus.OK);
    }

    @GetMapping("/testmessage/{anaata}")
    public ResponseEntity<String> CamelliaController(@PathVariable String anaata) throws NoSuchPaddingException,
                                                NoSuchAlgorithmException,
                                                NoSuchProviderException,
                                                InvalidAlgorithmParameterException,
                                                InvalidKeyException,
                                                IllegalBlockSizeException,
                                                BadPaddingException {
        // -------------- Symmetric Key Func --------------------------- //

        Security.addProvider(new BouncyCastleProvider());

        Anaata yume = anaataRepository.findByYume(anaata);

        String message = yume.toString();
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

        log.info("Message: " + message);
        log.info("Encrypted message: " + encryptedMessage);
        log.info("encoded IV: " + encodedIV);
        log.info("IV: " + Arrays.toString(ivBytes));
        log.info("ivParameterSpec: " + Arrays.toString(ivParameterSpec.getIV()));

        // -------------- Symmetric Key Func --------------------------- //

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        String message = "Climb Niitaka Mound";
        Map<String, String> payload = new HashMap<>();

        payload.put("encryptedMessage", encryptedMessage);
//        payload.put("IV", Arrays.toString(ivBytes));
        payload.put("IV", Base64.getEncoder().encodeToString(ivBytes));

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(payload, headers);

        String uri = "http://localhost:8080/request_test";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, httpEntity, String.class);

        log.info("Result is: " + result);

        return new ResponseEntity<>("{\"The message\":\"isn't dying\"}", HttpStatus.OK);
    }

    @GetMapping("/anaata")
    public void getAnaaata(){
        log.info("anaataRepository: " + anaataRepository.findByYume("Chloe"));
    }

    // HANDSHAKE PROCESS ---> INITIATOR

    @GetMapping("/start_handshake_with_Chloe")
    public void startHandshakeWithChloe() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        log.info("Handshake attempted with: Chloe");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> sendNee_httpEntity = new HttpEntity<>("Nee anaata", headers);

        String sendNee_uri = "http://localhost:8080/start_handshake";

        RestTemplate sendNee_restTemplate = new RestTemplate();
        String sendNee_result = sendNee_restTemplate.postForObject(sendNee_uri, sendNee_httpEntity, String.class);

        log.info("Result is: " + sendNee_result);

        if (sendNee_result != null) {
            if (sendNee_result.equals("Nee Nee")) {

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

//                Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
//// Step 7: Initialize the Cipher object
//                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//// Step 8: Give the Cipher our message
//                cipher.update(message);
//// Step 9: Encrypt the message
//                byte[] ciphertext = cipher.doFinal();
//// Step 10: Print the ciphertext
//                System.out.println("message: " + new String(message, "UTF8"));
//                System.out.println("ciphertext: " + new String(ciphertext, "UTF8"));
//// Step 11: Change the Cipher object's mode
//                cipher.init(Cipher.DECRYPT_MODE, privateKey);
//// Step 12: Give the Cipher objectour ciphertext
//                cipher.update(ciphertext);
//// Step 13: Decrypt the ciphertext
//                byte[] decrypted = cipher.doFinal();
//                System.out.println("decrypted: " + new String(decrypted, "UTF8"));
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                byte[] publicKeyBytes = publicKey.getEncoded();

                HttpEntity<byte[]> sendEncryptedMessage_httpEntity = new HttpEntity<>(publicKeyBytes, headers);

                String sendEncryptedMessage_uri = "http://localhost:8080/start_handshake/getEncryptedMessage";

                RestTemplate restTemplate = new RestTemplate();
                Map<String, String> sendEncryptedMessage_result = restTemplate.postForObject(sendEncryptedMessage_uri, sendEncryptedMessage_httpEntity, Map.class);

                log.info("sendEncryptedMessage_result Key: " + sendEncryptedMessage_result.get("Key"));
                log.info("sendEncryptedMessage_result IV: " + sendEncryptedMessage_result.get("IV"));
            }
        }
    }
}
