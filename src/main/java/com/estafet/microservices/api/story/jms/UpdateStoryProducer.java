package com.estafet.microservices.api.story.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.support.JmsGatewaySupport;

import com.estafet.microservices.api.story.model.Story;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class UpdateStoryProducer extends JmsGatewaySupport {

	public void sendMessage(final Story story) {
		getJmsTemplate().send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				try {
					ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
					String json = ow.writeValueAsString(story);
					TextMessage message = session.createTextMessage(json);
					return message;
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
