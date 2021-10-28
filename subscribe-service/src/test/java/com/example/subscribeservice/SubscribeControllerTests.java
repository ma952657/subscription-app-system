package com.example.subscribeservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.example.subscribeservice.controller.SubscribeController;
import com.example.subscribeservice.model.Subscription;
import com.example.subscribeservice.repository.SubscriptionRepository;
import com.example.subscribeservice.service.NextSequenceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class SubscribeControllerTests {

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private NextSequenceService nextSequenceService;
    @MockBean
    private SubscriptionRepository subscriptionRepository;

    private SubscribeController subscribeController;

    @Before
    public void before() {
        initMocks(this);
        subscribeController = new SubscribeController(restTemplate, subscriptionRepository,nextSequenceService);
    }

    @Test
    public void subscribeTest() throws Exception {
        Subscription subscription = new Subscription();
        when(nextSequenceService.getNextSequence("SR")).thenReturn(1);
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), anyObject())).thenReturn(new ResponseEntity<>("",
                HttpStatus.OK));
        ResponseEntity<String> responseEntity = subscribeController.subscribe(subscription);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void getAllSubscriptionTest() throws Exception {
        Subscription subscription = new Subscription();
        List<Subscription> subsList = new ArrayList<>();
        when(subscriptionRepository.findAll()).thenReturn(subsList);
        ResponseEntity<List<Subscription>> responseEntity = subscribeController.getAllSupscriptions();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void getSubscriptionTest() throws Exception {
        Subscription subscription = new Subscription();
        List<Subscription> subsList = new ArrayList<>();
        when(subscriptionRepository.findById(anyString())).thenReturn(Optional.of(subscription));
        ResponseEntity<Subscription> responseEntity = subscribeController.getSubscription("SR");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
    @Test
    public void getDeleteSubscriptionTest() throws Exception {
        ResponseEntity responseEntity = subscribeController.deleteSubscription("SR");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}