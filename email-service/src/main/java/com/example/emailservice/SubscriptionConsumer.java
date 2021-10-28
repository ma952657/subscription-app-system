package com.example.emailservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SubscriptionConsumer {

    @Autowired
    private JavaMailSender javaMailSender;

    public SubscriptionConsumer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @JmsListener(destination = "subscriptions", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Subscription subscription = new Subscription();
        try {
            subscription = mapper.readValue(message, Subscription.class);
           } catch(IOException ex) {
            ex.printStackTrace();
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(subscription.getEmail());
        msg.setSubject("Subscription to newsletter " + subscription.getNewsletterId());
        msg.setText("Hello " + subscription.getFirstname() + " thank you for subscribing to " + subscription.getNewsletterId());
        javaMailSender.send(msg);
    }
}
