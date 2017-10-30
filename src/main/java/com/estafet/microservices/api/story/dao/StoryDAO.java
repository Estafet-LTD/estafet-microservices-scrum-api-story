package com.estafet.microservices.api.story.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estafet.microservices.api.story.jms.NewStoryProducer;
import com.estafet.microservices.api.story.jms.UpdateStoryProducer;
import com.estafet.microservices.api.story.model.Story;
import com.estafet.microservices.api.story.model.Task;

@Repository
public class StoryDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private NewStoryProducer newStoryProducer;
	
	@Autowired
	private UpdateStoryProducer updateStoryProducer;

	public List<Story> getStories(Integer projectId) {
		return getStories(projectId, null);
	}
	
	public List<Story> getStories(Integer projectId, Integer sprintId) {
		if (sprintId != null) {
			TypedQuery<Story> query = entityManager.createQuery("Select s from Story s where s.projectId = :projectId and s.sprintId = :sprintId", Story.class);
			query.setParameter("projectId", projectId);
			query.setParameter("sprintId", sprintId); 
			return query.getResultList();
		} else {
			TypedQuery<Story> query = entityManager.createQuery("Select s from Story s where s.projectId = :projectId", Story.class);
			return query.setParameter("projectId", projectId).getResultList(); 
		}
	}
	
	public Task getTask(int taskId) {
		return entityManager.find(Task.class, new Integer(taskId));
	}

	public Story getStory(int storyId) {
		return entityManager.find(Story.class, new Integer(storyId));
	}

	public Story createStory(Story story) {
		entityManager.persist(story);
		newStoryProducer.sendMessage(story);
		return story;
	}

	public Story updateStory(Story story) {
		entityManager.merge(story);
		updateStoryProducer.sendMessage(story);
		return story;
	}

}
