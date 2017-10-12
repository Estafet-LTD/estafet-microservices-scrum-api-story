package com.estafet.microservices.api.story.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.story.model.Story;

@Component
public class UpdateStoryProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(Story story) {
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.convertAndSend("update.story.topic", story.toJSON());
	}
}
