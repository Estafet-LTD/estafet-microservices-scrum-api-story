package com.estafet.microservices.api.story.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.api.story.model.Sprint;
import com.estafet.microservices.api.story.model.Story;

@Service
public class SprintService {

	@Autowired
	private RestTemplate restTemplate;
	
	public Sprint getSprint(Story story) {
		return restTemplate.getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}",
				Sprint.class, story.getSprintId());
	}
	
}
