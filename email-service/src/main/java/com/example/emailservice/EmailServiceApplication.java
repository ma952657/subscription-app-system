package com.example.emailservice;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@EnableJms
public class EmailServiceApplication {

	@Value("${spring.activemq.url}")
	private String brokerUrl;

	@Value("${spring.activemq.user}")
	private String userName;

	@Value("${spring.activemq.password}")
	private String password;


	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.BYTES);
		converter.setTypeIdPropertyName("DocumentType");
		return converter;
	}

	@Bean
	public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory =
				new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(brokerUrl);
		activeMQConnectionFactory.setUserName(userName);
		activeMQConnectionFactory.setPassword(password);
		return activeMQConnectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(receiverActiveMQConnectionFactory());
		return factory;
	}


	@Bean
	public SubscriptionConsumer receiver() {
		return new SubscriptionConsumer(javaMailSender);
	}

	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}

}
