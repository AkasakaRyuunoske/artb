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

    @GetMapping("/testmessage")
    public ResponseEntity<String> test(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // -------------- Symmetric Key Func --------------------------- //

        Security.addProvider(new BouncyCastleProvider());

        String message = "Climb Niitaka mound";
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

        log.info("Request by main is:(Header): " + httpServletRequest.getAttribute("Yume"));
        log.info("Request by main is:(Context Path): " + httpServletRequest.getContextPath());

        log.info("Response by main is(Header): " + httpServletResponse.getHeader("Yume"));
        log.info("Response by main is(Status): " + httpServletResponse.getStatus());

        return new ResponseEntity<>("{\"The message\":\"isn't dying\"}", HttpStatus.OK);
    }

    @GetMapping("/anaata")
    public void getAnaaata(){
        log.info("anaataRepository: " + anaataRepository.findByYume("Chloe"));
    }
}
