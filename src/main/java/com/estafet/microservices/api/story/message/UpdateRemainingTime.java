package com.estafet.microservices.api.story.message;

public class UpdateRemainingTime {

	private int taskId;

	private int remainingTime;
	
	private String updateTime;
	
	public String getUpdateTime() {
		return updateTime;
	}

	public UpdateRemainingTime setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	public int getTaskId() {
		return taskId;
	}

	public UpdateRemainingTime setTaskId(int taskId) {
		this.taskId = taskId;
		return this;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public UpdateRemainingTime setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
		return this;
	}

}
