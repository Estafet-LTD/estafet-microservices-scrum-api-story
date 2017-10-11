package com.estafet.microservices.api.story.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.story.model.Story;

@Component
public class NewStoryProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(Story story) {
		jmsTemplate.convertAndSend("new.story.topic", story.toJSON());
	}
}
