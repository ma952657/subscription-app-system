package com.example.subscribeservice.controller;

import com.example.subscribeservice.service.NextSequenceService;
import com.example.subscribeservice.model.Subscription;
import com.example.subscribeservice.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subscribe")
@Slf4j
public class SubscribeController {

    @Value("${spring.activemq.url}")
    private String ACTIVE_MQ_TOPIC_URL;

    private RestTemplate activeMQTemplate;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private NextSequenceService nextSequenceService;

    public SubscribeController(RestTemplate activeMQTemplate, SubscriptionRepository subscriptionRepository, NextSequenceService nextSequenceService) {
        this.activeMQTemplate = activeMQTemplate;
        this.subscriptionRepository = subscriptionRepository;
        this.nextSequenceService = nextSequenceService;
    }

    @PostMapping("/")
    public ResponseEntity<String> subscribe(@Validated @RequestBody Subscription subscription) throws Exception {
        try {
            subscription.setSubsId("SR"+nextSequenceService.getNextSequence(Subscription.SEQUENCE_NAME));
            Subscription savedSubscription = subscriptionRepository.save(subscription);
            HttpEntity<Subscription> request = new HttpEntity<>(subscription);
            activeMQTemplate.postForObject(ACTIVE_MQ_TOPIC_URL, request, Subscription.class);
            return new ResponseEntity<>(savedSubscription.getSubsId(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in save()"+e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Subscription>> getAllSupscriptions() throws Exception {
        try {
            List<Subscription> subsList = new ArrayList<>();
            subscriptionRepository.findAll().forEach(subsList::add);
            return ResponseEntity.ok(subsList);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{subs_id}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable("subs_id") String subsId) throws Exception {

        try {
            Optional<Subscription> subsData = subscriptionRepository.findById(subsId);
            if (subsData.isPresent()) {
                return new ResponseEntity<>(subsData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{subs_id}")
    public ResponseEntity<HttpStatus> deleteSubscription(@PathVariable("subs_id") String subsId) throws Exception {

        try {
            subscriptionRepository.deleteById(subsId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}