package com.estafet.microservices.api.story.dao;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estafet.microservices.api.story.jms.NewTaskProducer;
import com.estafet.microservices.api.story.jms.UpdateTaskProducer;
import com.estafet.microservices.api.story.model.Story;
import com.estafet.microservices.api.story.model.Task;

@Repository
public class TaskDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private StoryDAO storyDAO;
	
	@Autowired
	private UpdateTaskProducer updateTaskProducer;
	
	@Autowired
	private NewTaskProducer newTaskProducer;
	
	public Task getTask(int taskId) {
		return entityManager.find(Task.class, new Integer(taskId));
	}
	
	public Story addTask(int storyId, Task task) {
		Story story = storyDAO.getStory(storyId);
		story.addTask(task);
		entityManager.merge(story);
		newTaskProducer.sendMessage(task);
		return story;
	}
	
	public void deleteTask(Task task) {
		Story story = storyDAO.getStory(task.getTaskStory().getId());
		Iterator<Task> iterator = story.getTasks().iterator();
		while (iterator.hasNext()) {
			Task findTask = iterator.next();
			if (task.equals(findTask)) {
				iterator.remove();
				entityManager.merge(story);
				entityManager.remove(findTask);
				return;
			}
		}
	}
	
	public Task updateTask(Task task) {
		entityManager.merge(task);
		updateTaskProducer.sendMessage(task);
		return task;
	}
	
}
