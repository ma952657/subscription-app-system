package com.example.subscribetopic.service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionProducer {

    private String topic = "subscriptions";

    @Autowired
    private JmsTemplate jmsTemplate;

    public SubscriptionProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(topic, message);
    }
}
