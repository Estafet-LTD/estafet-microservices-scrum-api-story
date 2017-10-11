package com.estafet.microservices.api.story.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "TASK")
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6037295262091167012L;

	@Id
	@Column(name = "TASK_ID")
	private Integer id;

	@Column(name = "STATUS", nullable = false)
	private String status = "Not Started";

	@Transient
	private Integer storyId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STORY_ID", nullable = false, referencedColumnName = "STORY_ID")
	private Story taskStory;

	public Task update(Task newTask) {
		status = newTask.getStatus() != null ? newTask.getStatus() : status;
		if (status.equals("Completed")) {
			taskStory.updateStatus();
		}
		return this;
	}

	public Integer getId() {
		return id;
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

	public Integer getStoryId() {
		return storyId;
	}

	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
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
