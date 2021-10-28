package com.example.publicservice.controller;

import com.example.publicservice.model.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/subscribe")
@Slf4j
public class PublicServiceController {

    @Value("${spring.subscribe-service.url}")
    private String SUBSCRIBE_RESOURCE_URL ;
//    @Value("${spring.activemq.url}")
//    private String ACTIVEMQ_RESOURCE_URL ;
    @Autowired
    private RestTemplate restTemplate;
    public PublicServiceController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/")
    public ResponseEntity<String> subscribe(@Validated @RequestBody Subscription subscription) throws Exception {
        try {
            HttpEntity<Subscription> request = new HttpEntity<>(subscription);
            String url = SUBSCRIBE_RESOURCE_URL+"subscribe/";
            return new ResponseEntity(restTemplate.postForObject(url, request, String.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in ceration"+e);
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() throws Exception {
        try {
            String url = SUBSCRIBE_RESOURCE_URL+"subscribe/getAll";
            return  ResponseEntity.ok(Arrays.asList(restTemplate.getForObject(url, Subscription[].class)));
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{subs_id}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable("subs_id") String subsId) throws Exception {
        try {
            String url = SUBSCRIBE_RESOURCE_URL+"subscribe/"+subsId;
            return  ResponseEntity.ok(restTemplate.getForObject(url, Subscription.class));
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{subs_id}")
    public ResponseEntity<HttpStatus> deleteSubscription(@PathVariable("subs_id") String subsId) throws Exception {
        try {
            String url = SUBSCRIBE_RESOURCE_URL+"subscribe/"+subsId;
             restTemplate.delete(url);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}