package com.example.subscribetopic;

import com.example.subscribetopic.controller.ActiveMqController;
import com.example.subscribetopic.service.SubscriptionProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ActiveMqControllerTests {

    @Mock
    private SubscriptionProducer producer;

    private ActiveMqController activeMqController;

    @Before
    public void before() {
        initMocks(this);
        activeMqController = new ActiveMqController(producer);
    }

    @Test
    public void subscribeTest() throws Exception {
        String subscription = "{ \n" +
                "  \"email\": \"mahesh.test@gmail.com\",\n" +
                "  \"firstname\": \"mahesh\",\n" +
                "  \"gender\": \"male\",\n" +
                "  \"date_of_birth\": \"1986-12-23T22:14:25.643+0000\",\n" +
                "  \"has_consent\": true,\n" +
                "  \"newsletter_id\": \"1235\"\n" +
                "}";

        ResponseEntity<String> responseEntity = activeMqController.publish(subscription);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
