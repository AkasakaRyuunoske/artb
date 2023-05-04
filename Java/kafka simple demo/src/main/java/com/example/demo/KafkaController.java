package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    KafkaTemplate<String, String> template;

    @GetMapping("/message/{message}")
    public String testMessage(@PathVariable("message") String message) {
        template.send("utsup", message);
        return message + " was send";
    }
}
