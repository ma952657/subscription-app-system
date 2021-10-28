package com.example.publicservice;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.example.publicservice.controller.PublicServiceController;
import com.example.publicservice.model.Subscription;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PublicServiceControllerTests {

    private static final String EXPECTED_SUBSCRIPTION_ID = "SR1";
    @Mock
    private RestTemplate restTemplate;

    private PublicServiceController publicServiceController;

    @Before
    public void before() {
        initMocks(this);
        publicServiceController = new PublicServiceController(restTemplate);
    }

    @Test
    public void subscribeTest() throws Exception {

        Subscription subscription = new Subscription();
        //when()
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), anyObject())).thenReturn(EXPECTED_SUBSCRIPTION_ID);
        ResponseEntity<String> response = publicServiceController.subscribe(subscription);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EXPECTED_SUBSCRIPTION_ID, response.getBody());
    }

    @Test
    public void getAllSupscriptionsTest() throws Exception {
        Subscription[] subList = new Subscription[10];
        subList[0] = new Subscription();
        subList[1] = new Subscription();
        when(restTemplate.getForObject(anyString(),anyObject())).thenReturn(subList);
        ResponseEntity<List<Subscription>> response = publicServiceController.getAllSubscriptions();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getSupscriptionsTest() throws Exception {
        Subscription subscription = new Subscription();
        ResponseEntity<Subscription> response = publicServiceController.getSubscription("SR1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteSubscriptionTest() throws Exception {
        Subscription subscription = new Subscription();
        when(restTemplate.getForObject(anyString(),anyObject())).thenReturn(subscription);
        ResponseEntity response = publicServiceController.deleteSubscription("SR1");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}