package com.estafet.microservices.api.story.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.story.model.Task;
import com.estafet.microservices.api.story.service.StoryService;

@Component
public class UpdateTaskConsumer {

	@Autowired
	private StoryService storyService;

	@Transactional
	@JmsListener(destination = "update.task.topic", containerFactory= "myFactory")
	public void onMessage(String message) {
		Task task = Task.fromJSON(message);
		storyService.updateTask(task);
	}

}
