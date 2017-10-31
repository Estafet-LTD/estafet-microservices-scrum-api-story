package com.estafet.microservices.api.story.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.story.model.Task;
import com.estafet.microservices.api.story.service.StoryService;

import io.opentracing.Tracer;

@Component
public class NewTaskConsumer {

	@Autowired
	private Tracer tracer;
	
	@Autowired
	private StoryService storyService;

	@JmsListener(destination = "new.task.topic", containerFactory = "myFactory")
	public void onMessage(String message) {
		try {
			storyService.newTask(Task.fromJSON(message));
		} finally {
			tracer.activeSpan().close();
		}
	}

}
