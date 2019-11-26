package com.estafet.microservices.api.story.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estafet.microservices.api.story.message.AcceptanceCriteriaDetails;
import com.estafet.microservices.api.story.message.AddSprintStory;
import com.estafet.microservices.api.story.message.StoryDetails;
import com.estafet.microservices.api.story.model.Story;
import com.estafet.microservices.api.story.service.StoryService;

import io.opentracing.Tracer;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
public class StoryController {

	@Value("${app.version}")
	private String appVersion;
	
	@Autowired
	private StoryService storyService;
	
	@Autowired
	private Tracer tracer;	

	@GetMapping("/api")
	public Story getAPI() {
		tracer.activeSpan().deactivate();
		return Story.getAPI(appVersion);
	}
	
	@GetMapping("/story/{id}")
	public Story getStory(@PathVariable int id) {
		return storyService.getStory(id);
	}
	
	@PostMapping("/project/{id}/story")
	public ResponseEntity createStory(@PathVariable int id, @RequestBody StoryDetails message) {
		return new ResponseEntity(storyService.createStory(id, message), HttpStatus.OK);
	}

	@GetMapping("/project/{id}/stories")
	public ResponseEntity getStories(@PathVariable int id,
			@RequestParam(value = "sprintId", required = false) Integer sprintId) {
		return new ResponseEntity(storyService.getStories(id, sprintId), HttpStatus.OK);
	}

	@PostMapping("/story/{id}/criteria")
	public ResponseEntity addAcceptanceCriteria(@PathVariable int id, @RequestBody AcceptanceCriteriaDetails message) {
		return new ResponseEntity(storyService.addAcceptanceCriteria(id, message), HttpStatus.OK);
	}

	@PostMapping("/add-story-to-sprint")
	public ResponseEntity addSprintStory(@RequestBody AddSprintStory message) {
		return new ResponseEntity(storyService.addSprintStory(message), HttpStatus.OK);
	}

}
