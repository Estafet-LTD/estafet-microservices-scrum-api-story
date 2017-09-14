package com.estafet.microservices.api.story.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.story.message.AcceptanceCriteriaDetails;
import com.estafet.microservices.api.story.message.AddSprintStory;
import com.estafet.microservices.api.story.message.StoryDetails;
import com.estafet.microservices.api.story.model.AcceptanceCriterion;
import com.estafet.microservices.api.story.model.Project;
import com.estafet.microservices.api.story.model.Story;

@Service
public class StoryService {

	public Story getStory(int storyId) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", storyId);
		return template.getForObject("http://localhost:8080/story-repository/story/{id}", Story.class, params);
	}

	@SuppressWarnings("unchecked")
	public List<Story> getStories(int projectId) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", projectId);
		return template.getForObject("http://localhost:8080/story-repository/project/{id}/stories",
				new ArrayList<Story>().getClass(), params);
	}

	public Story createStory(int projectId, StoryDetails message) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", projectId);
		Story story = new Story().setDescription(message.getDescription()).setStorypoints(message.getStorypoints())
				.setTitle(message.getTitle());
		for (String criteria : message.getCriteria()) {
			story.addCriteria(criteria);
		}
		return template
				.postForObject("http://localhost:8080/story-repository/project/{id}/story", story, Story.class, params)
				.setProjectId(projectId);
	}

	public void deleteStory(int storyId) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", storyId);
		template.delete("http://localhost:8080/story-repository/story/{id}", params);
	}

	public Story addAcceptanceCriteria(Integer storyId, AcceptanceCriteriaDetails message) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", storyId);
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

	public Project getProject(int projectId) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", projectId);
		return template.getForObject("http://localhost:8080/sprint-repository/project/{id}", Project.class, params);
	}

	public Story addSprintStory(AddSprintStory message) {
		RestTemplate template = new RestTemplate();
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", message.getStoryId());
		Story story = getStory(message.getStoryId());
		if (!getProject(story.getProjectId()).containsSprint(message.getSprintId())) {
			throw new RuntimeException("Cannot add story " + story.getId() + " to sprint " + message.getSprintId());
		}
		template.put("http://localhost:8080/story-repository/story/{id}", story.start(message.getSprintId()), params);
		return getStory(message.getStoryId());
	}

	public Story removeSprintStory(int storyId) {
		RestTemplate template = new RestTemplate();
		return template.postForObject("http://localhost:8080/story-repository/remove-story-from-sprint", new Story().setId(storyId), Story.class);
	}

}
