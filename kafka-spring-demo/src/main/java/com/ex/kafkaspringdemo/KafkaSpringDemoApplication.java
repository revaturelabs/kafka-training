package com.ex.kafkaspringdemo;

import com.ex.kafkaspringdemo.domain.ChatExchange;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Scanner;

@SpringBootApplication
public class KafkaSpringDemoApplication implements ApplicationContextAware{

	KafkaTemplate<String, ChatExchange> kafkaTemplate;
	static ApplicationContext context;
	Scanner scanner = new Scanner(System.in);

	@Autowired
	public void setKafkaTemplate(KafkaTemplate<String, ChatExchange> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringDemoApplication.class, args);
		KafkaSpringDemoApplication app = context.getBean(KafkaSpringDemoApplication.class);

		String name = "";
		System.out.println("Please enter your username");

		while(name.isEmpty()) {
			System.out.println(String.format("[%s]:", "Name"));
			name = app.scanner.next();
		}
		app.addUser(name);
	}

	private void addUser(String name) {
		ChatExchange exchange = new ChatExchange();
		exchange.setContext("user-join");
		exchange.setMessage(name);
		exchange.setSender(name);
		kafkaTemplate.send("demo-chat", exchange);
	}

	@KafkaListener(topics="demo-chat",
					containerFactory="kafkaListenerContainerFactory")
	public void chatExchangeReceiver(@Payload ChatExchange exchange) {

		switch (exchange.getContext()) {
			case "user-join":
				System.out.println(String.format("User joined [%s]", exchange.getMessage()));
				//userJoin(exchange.getMessage());
				break;
//			case "user-message":
//				receiveMessage(exchange);
//				break;
		}

	}



	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
