package com.estafet.microservices.api.story.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "TASK")
public class Task {

	@Id
	@SequenceGenerator(name = "TASK_ID_SEQ", sequenceName = "TASK_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASK_ID_SEQ")
	@Column(name = "TASK_ID")
	private Integer id;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "INITIAL_HOURS", nullable = false)
	private Integer initialHours;

	@JsonInclude(Include.NON_NULL)
	@Column(name = "REMAINING_HOURS", nullable = false)
	private Integer remainingHours;

	@Column(name = "STATUS", nullable = false)
	private String status = "Not Started";

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STORY_ID", nullable = false, referencedColumnName = "STORY_ID")
	private Story taskStory;

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
		status = newTask.getStatus() != null ? newTask.getStatus() : status;
		if (status.equals("Completed")) {
			taskStory.updateStatus();
		}
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

	public Task setTitle(String title) {
		this.title = title;
		return this;
	}

	public Task setInitialHours(Integer initialHours) {
		this.initialHours = initialHours;
		return this;
	}

	public Task setDescription(String description) {
		this.description = description;
		return this;
	}

	public Task setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public Story getTaskStory() {
		return taskStory;
	}

	Task setTaskStory(Story taskStory) {
		this.taskStory = taskStory;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
