package com.estafet.microservices.api.story.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.estafet.microservices.api.story.message.TaskDetails;
import com.estafet.microservices.api.story.message.UpdateRemainingTime;
import com.estafet.microservices.api.story.model.Task;
import com.estafet.microservices.api.story.service.StoryService;
import com.estafet.microservices.api.story.service.TaskService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
public class TaskController {

	@Autowired
	private StoryService storyService;
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping("/task/{id}")
	public Task getTask(@PathVariable int id) {
		return taskService.getTask(id);
	}
	
	@GetMapping("/story/{id}/tasks")
	public List<Task> getStoryTasks(@PathVariable int id) {
		return new ArrayList<Task>(storyService.getStory(id).getTasks());
	}
	
	@PostMapping("/story/{id}/task")
	public ResponseEntity createTask(@PathVariable int id, @RequestBody TaskDetails message) {
		return new ResponseEntity(taskService.createTask(id, message), HttpStatus.OK);
	}
	
	@PutMapping("/task/{id}")
	public ResponseEntity updateTask(@PathVariable int id, @RequestBody Task task) {
		return new ResponseEntity(taskService.updateTask(task), HttpStatus.OK);
	}
	
	@DeleteMapping("/task/{id}")
	public ResponseEntity deleteTask(@PathVariable int id) {
		taskService.deleteTask(id);
		return new ResponseEntity(id, HttpStatus.OK);
	}
	
	@PostMapping("/change-task-details")
	public ResponseEntity changeTaskDetails(@RequestBody TaskDetails message) {
		return new ResponseEntity(taskService.changeTaskDetails(message), HttpStatus.OK);
	}
	
	@PostMapping("/update-remaining-time")
	public ResponseEntity updateRemainingTime(@RequestBody UpdateRemainingTime message) {
		return new ResponseEntity(taskService.updateRemainingTime(message), HttpStatus.OK);
	}
	
}
