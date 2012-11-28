package edu.cs5774.project5;

import java.util.Calendar;

public class TaskBug {
	enum Status {
		OPEN,
		IN_PROGRESS,
		COMPLETE
	}
	
	enum Priority {
		LOW,
		MEDIUM,
		HIGH
	}
	
	private String title;
	private Status status;
	private Priority priority;
	private Calendar dueDate;
	private Calendar estimatedDate;
	private double percentageCompleted;
	private Calendar createdAt;
	private Calendar updatedAt;
	private boolean isTask;
	
	public TaskBug(String title, Status status, Priority priority,
			Calendar dueDate, Calendar estimatedDate,
			double percentageCompleted, Calendar createdAt, Calendar updatedAt,
			boolean isTask) {
		super();
		this.title = title;
		this.status = status;
		this.priority = priority;
		this.dueDate = dueDate;
		this.estimatedDate = estimatedDate;
		this.percentageCompleted = percentageCompleted;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isTask = isTask;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public Calendar getDueDate() {
		return dueDate;
	}
	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}
	public Calendar getEstimatedDate() {
		return estimatedDate;
	}
	public void setEstimatedDate(Calendar estimatedDate) {
		this.estimatedDate = estimatedDate;
	}
	public double getPercentageCompleted() {
		return percentageCompleted;
	}
	public void setPercentageCompleted(double percentageCompleted) {
		this.percentageCompleted = percentageCompleted;
	}
	public Calendar getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
	public Calendar getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}
	public boolean isTask() {
		return isTask;
	}
	public void setTask(boolean isTask) {
		this.isTask = isTask;
	}
}
