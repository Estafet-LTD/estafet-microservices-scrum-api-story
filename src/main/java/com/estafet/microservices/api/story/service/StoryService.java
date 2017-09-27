package com.estafet.microservices.api.story.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.story.dao.StoryDAO;
import com.estafet.microservices.api.story.message.AcceptanceCriteriaDetails;
import com.estafet.microservices.api.story.message.AddSprintStory;
import com.estafet.microservices.api.story.message.StoryDetails;
import com.estafet.microservices.api.story.model.AcceptanceCriterion;
import com.estafet.microservices.api.story.model.Sprint;
import com.estafet.microservices.api.story.model.Story;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StoryService {

	@Autowired
	private StoryDAO storyDAO;

	@Transactional(readOnly = true)
	public Story getStory(int storyId) {
		return storyDAO.getStory(storyId);
	}

	@Transactional(readOnly = true)
	public List<Story> getStories(int projectId) {
		return storyDAO.getStories(projectId);
	}

	@Transactional(readOnly = true)
	public List<Story> getStories(Integer projectId, Integer sprintId) {
		return storyDAO.getStories(projectId, sprintId);
	}
	
	@Transactional
	public Story createStory(int projectId, StoryDetails message) {
		Story story = new Story().setDescription(message.getDescription()).setStorypoints(message.getStorypoints())
				.setTitle(message.getTitle()).setProjectId(projectId);
		for (String criteria : message.getCriteria()) {
			story.addCriteria(criteria);
		}
		return storyDAO.createStory(story);
	}

	@Transactional
	public void deleteStory(int storyId) {
		storyDAO.deleteStory(storyDAO.getStory(storyId));
	}

	@Transactional
	public Story addAcceptanceCriteria(Integer storyId, AcceptanceCriteriaDetails message) {
		Story story = storyDAO.getStory(storyId);
		storyDAO.updateStory(
				story.addAcceptanceCriterion(new AcceptanceCriterion().setDescription(message.getCriteria())));
		return story;
	}

	@Transactional
	public Story changeStoryDetails(StoryDetails message) {
		Story story = getStory(message.getStoryId()).setDescription(message.getDescription())
				.setStorypoints(message.getStorypoints()).setTitle(message.getTitle());
		return storyDAO.updateStory(story);
	}

	@SuppressWarnings({ "rawtypes" })
	private List<Sprint> getProjectSprints(int projectId) {
		List objects = new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/project/{id}/sprints",
				List.class, projectId);
		List<Sprint> sprints = new ArrayList<Sprint>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			Sprint sprint = mapper.convertValue(object, new TypeReference<Sprint>() {
			});
			sprints.add(sprint);
		}
		return sprints;
	}
	
	private List<Integer> getProjectSprintIds(int projectId) {
		List<Integer> ids = new ArrayList<Integer>();
		for (Sprint sprint : getProjectSprints(projectId)) {
			ids.add(sprint.getId());
		}
		return ids;
	}

	@Transactional
	public Story addSprintStory(AddSprintStory message) {
		Story story = getStory(message.getStoryId());
		List<Integer> ids = getProjectSprintIds(story.getProjectId());
		if (!ids.contains(message.getSprintId())) {
			throw new RuntimeException("Cannot add story " + story.getId() + " to sprint " + message.getSprintId());
		}
		return storyDAO.updateStory(story.start(message.getSprintId()));
	}

	@Transactional
	public Story removeSprintStory(int storyId) {
		Story story = getStory(storyId);
		story.setSprintId(null);
		return storyDAO.updateStory(story);
	}

}
