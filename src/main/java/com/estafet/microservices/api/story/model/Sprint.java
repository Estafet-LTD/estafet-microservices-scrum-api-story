package com.estafet.microservices.api.story.model;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "SPRINT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprint {

	@Id
	@Column(name = "SPRINT_ID")
	private Integer id;

	@Column(name = "PROJECT_ID", nullable = false)
	private Integer projectId;

	public Integer getId() {
		return id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public static Sprint fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, Sprint.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
