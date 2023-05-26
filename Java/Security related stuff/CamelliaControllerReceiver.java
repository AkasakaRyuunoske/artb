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
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

//This is receiver example

@RestController
@Log4j2
public class CamelliaControllerReceiver {
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
    public ModelAndView redirect(ModelAndView modelAndView, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        httpServletResponse.setHeader("Yume", "Anaata");
        httpServletResponse.setStatus(416);

        log.info("Response by main is(Header): " + httpServletResponse.getHeader("Yume"));
        log.info("Response by main is(Status): " + httpServletResponse.getStatus());

        httpServletRequest.setAttribute("Yume", "Anaata");

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
}
