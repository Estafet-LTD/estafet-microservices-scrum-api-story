package com.estafet.microservices.api.story.model;

import java.util.ArrayList;
import java.util.List;

public class Story {

	private int id;

	private String title;

	private String description;

	private Integer storypoints;

	private Integer sprintId;

	private Integer projectId;

	private List<AcceptanceCriterion> criteria = new ArrayList<AcceptanceCriterion>();

	private List<Task> tasks = new ArrayList<Task>();

	private String status = "Not Started";

	public Story addTask(Task task) {
  		tasks.add(task);
 		if ("Not Started".equals(status)) {
 			status = "Planning";
 		} else if ("Completed".equals(status)) {
 			throw new RuntimeException("Story has already been completed.");
 		}
  		return this;
  	}

	public Story start(int sprintId) {
		if ("Not Started".equals(status) || "Planning".equals(status)) {
			status = "In Progress";
			this.sprintId = sprintId;
			return this;
		}
		throw new RuntimeException("StoryDetails has already been started.");
	}

	public Story reopen() {
		if ("Completed".equals(status)) {
			status = "Planning";
			return this;
		}
		throw new RuntimeException("StoryDetails has not been completed.");
	}

	public Story complete() {
		if (!"Completed".equals(status)) {
			if (tasks != null) {
				for (Task task : tasks) {
					task.complete();
				}
			}
			status = "Completed";
			return this;
		}
		throw new RuntimeException("StoryDetails has already been completed.");
	}

	public Integer getSprintId() {
		return sprintId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Integer getStorypoints() {
		return storypoints;
	}

	public int getId() {
		return id;
	}

	public List<AcceptanceCriterion> getCriteria() {
		return criteria;
	}

	public String getStatus() {
		return status;
	}

	public Story setId(int id) {
		this.id = id;
		return this;
	}

	public Story setTitle(String title) {
		this.title = title;
		return this;
	}

	public Story setDescription(String description) {
		this.description = description;
		return this;
	}

	public Story setStorypoints(Integer storypoints) {
		this.storypoints = storypoints;
		return this;
	}
	
	

}
