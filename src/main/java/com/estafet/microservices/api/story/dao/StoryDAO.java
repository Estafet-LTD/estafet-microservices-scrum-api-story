package com.estafet.microservices.api.story.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estafet.microservices.api.story.jms.NewStoryProducer;
import com.estafet.microservices.api.story.model.Story;

@Repository
public class StoryDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private NewStoryProducer newStoryProducer;

	public List<Story> getStories(Integer projectId) {
		return getStories(projectId, null);
	}
	
	public List<Story> getStories(Integer projectId, Integer sprintId) {
		TypedQuery<Story> query = entityManager.createQuery("Select s from Story s where s.projectId = :projectId", Story.class);
		List<Story> stories = query.setParameter("projectId", projectId).getResultList(); 
		if (sprintId != null) {
			List<Story> sprintStories = new ArrayList<Story>();
			for (Story story : stories) {
				if (story.getSprintId() != null && story.getSprintId().equals(sprintId)) {
					sprintStories.add(story);
				}
			}
			return sprintStories;
		} else {
			return stories;	
		}
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
		return story;
	}

	public void deleteStory(Story story) {
		entityManager.remove(story);
	}

}
