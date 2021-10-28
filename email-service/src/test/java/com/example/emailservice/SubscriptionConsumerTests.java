package com.example.emailservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionConsumerTests {

    @Mock
    private JavaMailSender javaMailSender;

    SubscriptionConsumer subscriptionConsumer;

    @Before
    public void before() {
        initMocks(this);
        subscriptionConsumer = new SubscriptionConsumer(javaMailSender);
    }

    @Test
    public void sendMessageTest() throws IOException {
        Subscription subscription = new Subscription();
        subscription.setFirstname("Mahesh");
        subscription.setEmail("mahesh@gmail.com");
        subscription.setNewsletterId("programmer");
        String message = "hello";
        subscriptionConsumer.receiveMessage(message);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
