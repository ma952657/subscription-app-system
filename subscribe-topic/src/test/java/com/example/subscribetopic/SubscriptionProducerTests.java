package com.example.subscribetopic;

import com.example.subscribetopic.service.SubscriptionProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionProducerTests {

    @Mock
    private JmsTemplate jmsTemplate;

    SubscriptionProducer subscriptionProducer;

    @Before
    public void before() {
        initMocks(this);
        subscriptionProducer = new SubscriptionProducer(jmsTemplate);
    }

    @Test
    public void sendMessageTest() {
        String message = "{ \n" +
                "  \"email\": \"louise.testy@gmail.com\",\n" +
                "  \"firstname\": \"louise\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"date_of_birth\": \"1988-07-23T22:14:25.643+0000\",\n" +
                "  \"has_consent\": true,\n" +
                "  \"newsletter_id\": \"1234\"\n" +
                "}";

        subscriptionProducer.sendMessage(message);

        verify(jmsTemplate, times(1)).convertAndSend("subscriptions", message);
    }
}
