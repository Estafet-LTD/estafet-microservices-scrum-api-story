package com.estafet.microservices.api.story.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.estafet.microservices.api.story.model.Task;
import com.estafet.microservices.api.story.service.StoryService;

import io.opentracing.ActiveSpan;
import io.opentracing.Tracer;

@Component
public class UpdateTaskConsumer {

	@Autowired
	private Tracer tracer;
	
	@Autowired
	private StoryService storyService;

	@JmsListener(destination = "update.task.topic", containerFactory= "myFactory")
	public void onMessage(String message) {
		ActiveSpan span = tracer.activeSpan().log(message);
		try {
			storyService.updateTask(Task.fromJSON(message));
		} finally {
			span.close();
		}
	}

}
