package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> test(){
        System.out.println("was called too");
        return new ResponseEntity<>("{\"message:\"\"is dying\"}", HttpStatus.OK);
    }

    @GetMapping("/anaata")
    public void getAnaaata(){
        log.info(anaataRepository.findAll());
    }
}
