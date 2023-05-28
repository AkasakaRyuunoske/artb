package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class CamelliaController {
//    @Autowired
//    KafkaTemplate<String, String> template;
//
//    @GetMapping("/message/{message}")
//    public String testMessage(@PathVariable("message") String message) {
//        template.send("utsup", message);
//        return message + " was send";
//    }

    @GetMapping("/testmessage")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("{\"message:\"\"is dying\"}", HttpStatus.OK);
    }

    @GetMapping("/redirect_test")
    public ModelAndView redirect(ModelAndView modelAndView){

        modelAndView.setViewName("redirect:http://localhost:8090/testmessage");

        return modelAndView;
    }

    @GetMapping(value = "/request_test/{content}")
    private String getStudentString(@PathVariable String content) {

        String uri = "http://localhost:8090/testmessage/" + content;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        log.info("Result is: " + result);

        return result;
    }

    @PostMapping(value = "/request_test")
    private String postStudentString(@RequestBody Map<String, String> payload) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        log.info("IV: " + payload.get("IV"));
        Security.addProvider(new BouncyCastleProvider());

        String key = "ThisIsASecretKey";

        // Generate a random IV
//        byte[] ivBytes = payload.get("IV").getBytes();
        byte[] ivBytes = Base64.getDecoder().decode(payload.get("IV"));
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        log.info("ivParameterSpec: " + Arrays.toString(ivParameterSpec.getIV()));
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Camellia");

        // Create a new cipher instance for decryption and initialize it with the key and IV
        Cipher decryptionCipher = Cipher.getInstance("Camellia/CBC/PKCS7Padding", "BC");
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        // Decode the Base64 encoded message and IV
        byte[] decodedBytes = Base64.getDecoder().decode(payload.get("encryptedMessage"));

        // Decrypt the message
        byte[] decryptedBytes = decryptionCipher.doFinal(decodedBytes);

        // Convert the decrypted bytes back to a string
        String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);
        System.out.println("Decrypted message: " + decryptedMessage);

        log.info("Message: " + decryptedMessage);
        log.info("Encrypted message: " + payload.get("encryptedMessage"));
        log.info("encoded IV: " + payload.get("IV"));
        log.info("ivParameterSpec: " + Arrays.toString(ivParameterSpec.getIV()));

        log.info("\n" + decryptedMessage + "\nOkay, group climbing starts at 5am.");

        return "The opposition to justice is justice it self";
    }

    @PostMapping(value = "/asymmetric_key_request_test")
    private String post(@RequestBody Map<String, String> payload) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        log.info("Public key: " + payload.get("PK"));
        log.info("encryptedMessage : " + payload.get("encryptedMessage"));

        return "The opposition to justice is justice it self";
    }

    // HANDSHAKE PROCESS ---> RECEIVING


    @PostMapping(value = "/start_handshake")
    private ResponseEntity<String> startHandshake(@RequestBody String message, HttpServletRequest httpServletRequest){
        log.info("Handshake requested.");
        log.info("This guy is trying to handshake me: " + httpServletRequest.getRemoteUser());
        log.info("He's from: " + httpServletRequest.getContextPath());
        log.info("Reading his message...");

        if (!message.equals("Nee anaata")){
            log.info("Another cringy guy, nothing to worry about");
            return new ResponseEntity<>("Handshake refused.", HttpStatus.BAD_REQUEST);
        }

        log.info("He's not too bad... Sending my answer...");

        return new ResponseEntity<>("Nee Nee", HttpStatus.TEMPORARY_REDIRECT);
    }

    @PostMapping(value = "/start_handshake/getEncryptedMessage")
    private ResponseEntity<Map<String, String>> getEncryptedMessage(@RequestBody byte[] publicKeyBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException {
        log.info("Handshake continued.");

        // Assuming publicKeyBytes contains the byte array of the public key
        PublicKey publicKey = null;

        try {
            // Create a KeyFactory instance for RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // Create an X509EncodedKeySpec with the public key bytes
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

            // Generate the PublicKey object from the key specification
            publicKey = keyFactory.generatePublic(publicKeySpec);


        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
        }


        log.info("The guy send me: " + publicKey);

        Security.addProvider(new BouncyCastleProvider());

        String key = "ThisIsASecretKey";

        // Generate a random IV
        byte[] ivBytes = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(ivBytes);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        // Create a Camellia cipher instance and initialize it with the key and IV
        Cipher cipher_Camellia = Cipher.getInstance("Camellia/CBC/PKCS7Padding", "BC");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Camellia");
        cipher_Camellia.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        Cipher cipher_RSA = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
// Step 7: Initialize the Cipher object
        cipher_RSA.init(Cipher.ENCRYPT_MODE, publicKey);
// Step 8: Give the Cipher our message
        cipher_RSA.update(key.getBytes());
// Step 9: Encrypt the message
        byte[] ciphertext = cipher_RSA.doFinal();

        cipher_RSA.update(ivBytes);
        byte[] encryptedIVBytes = cipher_RSA.doFinal();

        Map<String, String> payload_response = new HashMap<>();

        payload_response.put("Message", "Alright, i will share my secret with u...");
        payload_response.put("Key",         key);
        payload_response.put("IV",          Base64.getEncoder().encodeToString(ivBytes));
        payload_response.put("CipheredKey", Base64.getEncoder().encodeToString(ciphertext));
        payload_response.put("CipheredIV",  Base64.getEncoder().encodeToString(encryptedIVBytes));

        log.info("payload_response Key: "         + payload_response.get("Key"));
        log.info("payload_response IV: "          + Arrays.toString(Base64.getDecoder().decode(payload_response.get("IV"))));
        log.info("payload_response CipheredKey: " + payload_response.get("CipheredKey"));
        log.info("payload_response CipheredIV: "  + payload_response.get("CipheredIV"));
        log.info("payload_response CipheredIV decoded: " + Arrays.toString(Base64.getDecoder().decode(payload_response.get("CipheredIV").getBytes())));
        return new ResponseEntity<>(payload_response, HttpStatus.OK);
    }

    @PostMapping("/start_handshake/last_step")
    public ResponseEntity<String> postLastStep(@RequestBody String encryptedMessage){

        return new ResponseEntity<>("Yey we can finally talk", HttpStatus.CONTINUE);
    }
}
