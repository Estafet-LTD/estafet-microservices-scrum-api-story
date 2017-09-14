package com.estafet.microservices.api.story.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estafet.microservices.api.story.message.TaskDetails;
import com.estafet.microservices.api.story.model.Task;
import com.estafet.microservices.api.story.service.TaskService;

@RestController
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@GetMapping("/task/{id}")
	public Task getTask(int taskId) {
		return taskService.getTask(taskId);
	}
	
	@PostMapping("/add-sub-task")
	public Task createTask(TaskDetails message) {
		return taskService.createTask(message);
	}
	
	@DeleteMapping("/task/{id}")
	public void deleteTask(int taskId) {
		taskService.deleteTask(taskId);
	}
	
	@PostMapping("/change-task-details")
	public Task changeTaskDetails(TaskDetails message) {
		return taskService.changeTaskDetails(message);
	}
	
}
