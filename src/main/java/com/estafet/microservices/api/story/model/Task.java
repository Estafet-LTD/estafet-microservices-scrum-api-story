package com.estafet.microservices.api.story.model;

public class Task {

	private Integer id;

	private String title;

	private String description;

	private Integer initialHours;

	private Integer remainingHours;

	private String status = "Not Started";

	public Task complete() {
		if (!"Completed".equals(status)) {
			remainingHours = 0;
			status = "Completed";
			return this;
		}
		throw new RuntimeException("Task is already complete.");
	}

	public Task reopen() {
		if ("Completed".equals(status)) {
			status = "Not Started";
			return this;
		}
		throw new RuntimeException("Task must be completed to reopen.");
	}

	public Task claim() {
		if ("Not Started".equals(status)) {
			status = "In Progress";
			return this;
		}
		throw new RuntimeException("Task needs to be not started to claim it.");
	}

	public Task setRemainingHours(Integer remainingHours) {
		if (remainingHours != null) {
			this.remainingHours = remainingHours;
			if (remainingHours == 0) {
				status = "Completed";
			}
		}
		return this;
	}

	public Task update(Task newTask) {
		title = newTask.getTitle() != null ? newTask.getTitle() : title;
		description = newTask.getDescription() != null ? newTask.getDescription() : description;
		initialHours = newTask.getInitialHours() != null ? newTask.getInitialHours() : initialHours;
		setRemainingHours(newTask.getRemainingHours());
		return this;
	}

	public Integer getId() {
		return id;
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

	public Integer getRemainingHours() {
		return remainingHours;
	}

	public String getStatus() {
		return status;
	}

	public Task setTitle(String title) {
		this.title = title;
		return this;
	}

	public Task setDescription(String description) {
		this.description = description;
		return this;
	}

	public Task setInitialHours(Integer initialHours) {
		this.initialHours = initialHours;
		return this;
	}
	
	

}
