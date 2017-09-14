package com.estafet.microservices.api.story.message;

public class TaskDetails {

	private int taskId;

	private String title;

	private String description;

	private Integer initialHours;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getInitialHours() {
		return initialHours;
	}

	public void setInitialHours(Integer initialHours) {
		this.initialHours = initialHours;
	}

}