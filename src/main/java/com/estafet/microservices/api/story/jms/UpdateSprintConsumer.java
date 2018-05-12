package com.estafet.microservices.api.story.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.story.model.Sprint;
import com.estafet.microservices.api.story.service.StoryService;

import io.opentracing.Tracer;

@Component
public class UpdateSprintConsumer {

	@Autowired
	private Tracer tracer;
	
	@Autowired
	private StoryService storyService;

	@JmsListener(destination = "update.sprint.topic", containerFactory= "myFactory")
	public void onMessage(String message) {
		try {
			storyService.updateSprint(Sprint.fromJSON(message));
		} finally {
			tracer.activeSpan().close();
		}
	}

}
