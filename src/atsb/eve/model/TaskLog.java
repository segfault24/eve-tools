package atsb.eve.model;

import java.sql.Timestamp;

public class TaskLog {

	private int taskLogId;
	private String taskName;
	private Timestamp startTime;
	private Timestamp finishTime;
	private int duration;
	private boolean success;
	private String error;

	public int getTaskLogId() {
		return taskLogId;
	}

	public void setTaskLogId(int taskLogId) {
		this.taskLogId = taskLogId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
