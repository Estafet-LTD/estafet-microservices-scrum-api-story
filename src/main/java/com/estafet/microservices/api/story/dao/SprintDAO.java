package com.estafet.microservices.api.story.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estafet.microservices.api.story.model.Sprint;

@Repository
public class SprintDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Sprint> getProjectSprints(Integer projectId) {
		return entityManager.createQuery("select s from Sprint s where s.projectId = " + projectId, Sprint.class)
				.getResultList();
	}
	
	public Sprint createSprint(Sprint sprint) {
		entityManager.persist(sprint);
		return sprint;
	}

	public Sprint updateSprint(Sprint sprint) {
		entityManager.merge(sprint);
		return sprint;
	}

}
