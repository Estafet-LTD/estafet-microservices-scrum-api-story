package com.estafet.microservices.api.story.jms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.story.model.Task;
import com.estafet.microservices.api.story.service.StoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(value = "updateTaskConsumer")
public class UpdateTaskConsumer {

	@Autowired
	private StoryService storyService;

	@Transactional
	public void onMessage(String message) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Task task = mapper.readValue(message, Task.class);
			storyService.updateTask(task);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
