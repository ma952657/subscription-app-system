package com.example.subscribetopic.controller;

import com.example.subscribetopic.service.SubscriptionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/activeMQ")
public class ActiveMqController {

    @Autowired
    private final SubscriptionProducer producer;

    public ActiveMqController(SubscriptionProducer producer) {
        this.producer = producer;
    }

    @RequestMapping(value = "/publish", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public ResponseEntity<String> publish(@Validated @RequestBody String subscription) {
        this.producer.sendMessage(subscription);

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
