package com.estafet.microservices.api.story.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.microservices.api.story.dao.StoryDAO;
import com.estafet.microservices.api.story.dao.TaskDAO;
import com.estafet.microservices.api.story.message.TaskDetails;
import com.estafet.microservices.api.story.message.UpdateRemainingTime;
import com.estafet.microservices.api.story.model.Story;
import com.estafet.microservices.api.story.model.Task;

@Service
public class TaskService {

	@Autowired
	private TaskDAO taskDAO;

	@Autowired
	private StoryDAO storyDAO;

	@Transactional(readOnly = true)
	public Task getTask(int taskId) {
		return taskDAO.getTask(taskId);
	}

	@Transactional
	public Story createTask(Integer storyId, TaskDetails message) {
		Task task = new Task().setDescription(message.getDescription()).setInitialHours(message.getInitialHours())
				.setTitle(message.getTitle()).setRemainingHours(message.getInitialHours());
		Story story = storyDAO.getStory(storyId);
		story.addTask(task);
		storyDAO.updateStory(story);
		return story;
	}

	@Transactional
	public Task updateTask(Task updated) {
		Task task = taskDAO.getTask(updated.getId());
		taskDAO.updateTask(task.update(updated));
		return task;
	}

	@Transactional
	public void deleteTask(int taskId) {
		taskDAO.deleteTask(getTask(taskId));
	}

	@Transactional
	public Task changeTaskDetails(TaskDetails message) {
		Task task = getTask(message.getTaskId()).setDescription(message.getDescription())
				.setInitialHours(message.getInitialHours()).setTitle(message.getTitle());
		return taskDAO.updateTask(task);
	}

	@Transactional
	public Task updateRemainingTime(UpdateRemainingTime message) {
		Task task = getTask(message.getTaskId()).setRemainingHours(message.getRemainingTime())
				.setRemainingUpdated(message.getUpdateTime());
		return taskDAO.updateTask(task);
	}

}
