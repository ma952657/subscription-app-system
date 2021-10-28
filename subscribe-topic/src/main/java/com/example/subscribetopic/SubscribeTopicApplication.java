package com.example.subscribetopic;

import com.example.subscribetopic.service.SubscriptionProducer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class SubscribeTopicApplication {

	//private String brokerUrl = "tcp://active-mq-broker:61616";
	@Value("${spring.activemq.url}")
	private String brokerUrl;

	@Value("${spring.activemq.user}")
	private String userName;

	@Value("${spring.activemq.password}")
	private String password;

	@Bean
	public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory =
				new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(brokerUrl);
		activeMQConnectionFactory.setUserName(userName);
		activeMQConnectionFactory.setPassword(password);

		return activeMQConnectionFactory;
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		return new CachingConnectionFactory(
				senderActiveMQConnectionFactory());
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(cachingConnectionFactory());
	}

	@Bean
	public SubscriptionProducer producer() {
		return new SubscriptionProducer(jmsTemplate());
	}

	public static void main(String[] args) {
		SpringApplication.run(SubscribeTopicApplication.class, args);
	}

}
