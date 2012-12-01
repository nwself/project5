package edu.cs5774.project5;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;

public class TaskBug {
	
	public TaskBug()
	{
		
	}
	
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
	
	private LinkedList<User> users = new LinkedList<User>();
	
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
	
	public void addUser(User user) {
		users.add(user);
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
	@XmlElement(name="due_date",type=Calendar.class)
	public Calendar getDueDate() {
		return dueDate;
	}
	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}
	
	@XmlElement(name="estimated_date",type=Calendar.class)
	public Calendar getEstimatedDate() {
		return estimatedDate;
	}
	public void setEstimatedDate(Calendar estimatedDate) {
		this.estimatedDate = estimatedDate;
	}
	
	@XmlElement(name="percentage_completed")
	public double getPercentageCompleted() {
		return percentageCompleted;
	}
	public void setPercentageCompleted(double percentageCompleted) {
		this.percentageCompleted = percentageCompleted;
	}
	
	@XmlElement(name="created_at",type=Calendar.class)
	public Calendar getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
	
	@XmlElement(name="pubDate",type=Calendar.class)
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
