package com.estafet.microservices.api.story.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.estafet.microservices.api.story.message.AcceptanceCriteriaDetails;
import com.estafet.microservices.api.story.message.AddSprintStory;
import com.estafet.microservices.api.story.message.StoryDetails;
import com.estafet.microservices.api.story.model.Story;
import com.estafet.microservices.api.story.service.StoryService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
public class StoryController {
	
	@Autowired
	private StoryService storyService;
	
	@GetMapping("/story/{id}")
	public Story getStory(@PathVariable int id) {
		return storyService.getStory(id);
	}
	
	@PostMapping("/project/{id}/story")
	public ResponseEntity createStory(@PathVariable int id, @RequestBody StoryDetails message) {
		return new ResponseEntity(storyService.createStory(id, message), HttpStatus.OK);
	}
	
	@DeleteMapping("/story/{id}")
	public ResponseEntity deleteStory(@PathVariable int id) {
		storyService.deleteStory(id);
		return new ResponseEntity(id, HttpStatus.OK);
	}
	
	@PostMapping("/add-acceptance-criteria")
	public ResponseEntity addAcceptanceCriteria(@RequestBody AcceptanceCriteriaDetails message) {
		return new ResponseEntity(storyService.addAcceptanceCriteria(message), HttpStatus.OK);
	}
	
	@PostMapping("/change-story-details")
	public ResponseEntity changeStoryDetails(StoryDetails message) {
		return new ResponseEntity(storyService.changeStoryDetails(message), HttpStatus.OK);
	}
	
	@PostMapping("/add-sprint-story")
	public ResponseEntity addSprintStory(AddSprintStory message) {
		return new ResponseEntity(storyService.addSprintStory(message), HttpStatus.OK);
	}
	
	@PostMapping("/story/{id}")
	public ResponseEntity removeSprintStory(int id) {
		return new ResponseEntity(storyService.removeSprintStory(id), HttpStatus.OK);
	}
	
}
