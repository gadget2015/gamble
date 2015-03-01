package org.robert.taanalys.scheduler;

import java.util.Date;

public class TaskExecutionInfo {
	public String message;
	public Date executionTime;

	public TaskExecutionInfo() {
	}

	public TaskExecutionInfo(String message, Date executionTime) {
		this.setMessage(message);
		this.setExecutionTime(executionTime);
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
