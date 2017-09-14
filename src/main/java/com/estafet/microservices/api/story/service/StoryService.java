package com.estafet.microservices.api.story.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.story.message.AcceptanceCriteriaDetails;
import com.estafet.microservices.api.story.message.AddSprintStory;
import com.estafet.microservices.api.story.message.StoryDetails;
import com.estafet.microservices.api.story.model.AcceptanceCriterion;
import com.estafet.microservices.api.story.model.Story;

@Service
public class StoryService {

	public Story getStory(int storyId) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", storyId);
		return template.getForObject("http://localhost:8080/story-repository/story/{id}", Story.class, params);
	}

	public Story createStory(int projectId, StoryDetails message) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", projectId);
		Story story = new Story().setDescription(message.getDescription()).setStorypoints(message.getStorypoints())
				.setTitle(message.getTitle());
		return template.postForObject("http://localhost:8080/story-repository/project/{id}/sprint", story, Story.class,
				params);
	}

	public void deleteStory(int storyId) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", storyId);
		template.delete("http://localhost:8080/story-repository/story/{id}", params);
	}

	public Story addAcceptanceCriteria(AcceptanceCriteriaDetails message) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", message.getStoryId());
		return template.postForObject("http://localhost:8080/story-repository/story/{id}/criteria",
				new AcceptanceCriterion().setDescription(message.getCriteria()), Story.class, params);
	}

	public Story changeStoryDetails(StoryDetails message) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", message.getStoryId());
		Story story = getStory(message.getStoryId()).setDescription(message.getDescription())
				.setStorypoints(message.getStorypoints()).setTitle(message.getTitle());
		template.put("http://localhost:8080/story-repository/story/{id}", story, params);
		return getStory(message.getStoryId());
	}

	public Story addSprintStory(AddSprintStory message) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", message.getStoryId());
		Story story = getStory(message.getStoryId()).start(message.getSprintId());
		template.put("http://localhost:8080/story-repository/story/{id}", story, params);
		return getStory(message.getStoryId());
	}

	public Story removeSprintStory(int storyId) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", storyId);
		template.put("http://localhost:8080/story-repository/remove-from-sprint/story/{id}", new Story().setId(storyId),
				params);
		return getStory(storyId);
	}

}
