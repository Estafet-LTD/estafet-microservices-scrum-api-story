package com.estafet.microservices.api.story.jms;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.story.model.Story;

@Component
public class UpdateStoryProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(Story story) {
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.convertAndSend("update.story.topic", story.toJSON(), new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty("message.event.interaction.reference", UUID.randomUUID().toString());
				return message;
			}
		});
	}
}
