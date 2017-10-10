package com.estafet.microservices.api.story.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.story.model.Task;
import com.estafet.microservices.api.story.service.StoryService;

@Component
public class NewTaskConsumer {

	@Autowired
	private StoryService storyService;

	@Transactional
	@JmsListener(destination = "new.task.topic", containerFactory = "myFactory")
	public void onMessage(Task task) {
		storyService.addTask(task);
	}

}
