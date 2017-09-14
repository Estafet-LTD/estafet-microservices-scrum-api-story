package com.estafet.microservices.api.story.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.story.message.TaskDetails;
import com.estafet.microservices.api.story.model.Story;
import com.estafet.microservices.api.story.model.Task;

@Service
public class TaskService {

	public Task getTask(int taskId) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", taskId);
		return template.getForObject("http://localhost:8080/story-repository/task/{id}", Task.class, params);
	}

	public Story createTask(Integer storyId, TaskDetails message) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", storyId);
		Task task = new Task().setDescription(message.getDescription()).setInitialHours(message.getInitialHours())
				.setTitle(message.getTitle());
		return template.postForObject("http://localhost:8080/story-repository/story/{id}/task", task, Story.class,
				params);
	}

	public void deleteTask(int taskId) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", taskId);
		template.delete("http://localhost:8080/story-repository/task/{id}", params);
	}

	public Task changeTaskDetails(TaskDetails message) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", message.getTaskId());
		Task task = getTask(message.getTaskId()).setDescription(message.getDescription())
				.setInitialHours(message.getInitialHours()).setTitle(message.getTitle());
		template.put("http://localhost:8080/story-repository/task/{id}", task, params);
		return getTask(message.getTaskId());
	}

}
