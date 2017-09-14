package com.estafet.microservices.api.story.message;

public class TaskDetails {

	private int taskId;

	private int storyId;

	private String title;

	private String description;

	private Integer initialHours;

	public int getTaskId() {
		return taskId;
	}

	public int getStoryId() {
		return storyId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Integer getInitialHours() {
		return initialHours;
	}

}