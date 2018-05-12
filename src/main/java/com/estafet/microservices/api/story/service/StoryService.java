package com.estafet.microservices.api.story.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.story.dao.SprintDAO;
import com.estafet.microservices.api.story.dao.StoryDAO;
import com.estafet.microservices.api.story.message.AcceptanceCriteriaDetails;
import com.estafet.microservices.api.story.message.AddSprintStory;
import com.estafet.microservices.api.story.message.StoryDetails;
import com.estafet.microservices.api.story.model.AcceptanceCriterion;
import com.estafet.microservices.api.story.model.SimpleStory;
import com.estafet.microservices.api.story.model.Sprint;
import com.estafet.microservices.api.story.model.Story;
import com.estafet.microservices.api.story.model.Task;

@Service
public class StoryService {

	@Autowired
	private StoryDAO storyDAO;
	
	@Autowired
	private SprintDAO sprintDAO;

	@Transactional(readOnly = true)
	public Story getStory(int storyId) {
		return storyDAO.getStory(storyId);
	}

	@Transactional(readOnly = true)
	public List<SimpleStory> getStories(int projectId) {
		return SimpleStory.toList(storyDAO.getStories(projectId));
	}

	@Transactional(readOnly = true)
	public List<SimpleStory> getStories(Integer projectId, Integer sprintId) {
		return SimpleStory.toList(storyDAO.getStories(projectId, sprintId));
	}

	@Transactional
	public void newTask(Task task) {
		Story story = storyDAO.getStory(task.getStoryId());
		story.addTask(task);
		storyDAO.updateStory(story);
	}

	@Transactional
	public void updateTask(Task updated) {
		Task task = storyDAO.getTask(updated.getId());
		task.update(updated);
		storyDAO.updateStory(task.getTaskStory());
	}

	@Transactional
	public Story createStory(int projectId, StoryDetails message) {
		Story story = new Story().setDescription(message.getDescription()).setStorypoints(message.getStorypoints())
				.setTitle(message.getTitle()).setProjectId(projectId);
		for (String criteria : message.getCriteria()) {
			story.addAcceptanceCriterion(new AcceptanceCriterion().setDescription(criteria));
		}
		return storyDAO.createStory(story);
	}

	@Transactional
	public Story addAcceptanceCriteria(Integer storyId, AcceptanceCriteriaDetails message) {
		Story story = storyDAO.getStory(storyId);
		storyDAO.updateStory(
				story.addAcceptanceCriterion(new AcceptanceCriterion().setDescription(message.getCriteria())));
		return story;
	}

	private List<Integer> getProjectSprintIds(int projectId) {
		List<Integer> ids = new ArrayList<Integer>();
		for (Sprint sprint : sprintDAO.getProjectSprints(projectId)) {
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
	public void updateSprint(Sprint sprint) {
		sprintDAO.updateSprint(sprint);
	}

	@Transactional
	public void newSprint(Sprint sprint) {
		sprintDAO.createSprint(sprint);
	}

}
