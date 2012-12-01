package edu.cs5774.project5;

import java.util.Calendar;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class TaskBug {
	
	public TaskBug() {}

	@XmlType(name = "status")
	@XmlEnum
	@XmlJavaTypeAdapter(StatusAdapter.class)
	public enum Status {
		OPEN("Open"),
		IN_PROGRESS("In Progress"),
		COMPLETE("Complete");
		
		private final String value;
		
		Status(String v) {
			value = v;
		}
		
	    public String toString() {
	        return value;
	    }

	    public static Status fromValue(String v) {
	    	for (Status c: Status.values()) {
	    		if (c.value.equals(v)) {
	    			return c;
	    		}
	    	}
	    	return OPEN;
	    }
	}
	
	enum Priority {
		Low,
		Medium,
		High
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
	private User user;
	
	public TaskBug(String title, Status status, Priority priority,
			Calendar dueDate, Calendar estimatedDate,
			double percentageCompleted, Calendar createdAt, Calendar updatedAt,
			boolean isTask, User user) {
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
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	@XmlElement(name="due_date")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Calendar getDueDate() {
		return dueDate;
	}
	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}
	
	@XmlElement(name="estimated_date")
	@XmlJavaTypeAdapter(DateAdapter.class)
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
	
	@XmlElement(name="created_at")
	@XmlJavaTypeAdapter(CalendarAdapter.class)
	public Calendar getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
	
	@XmlElement(name="pubDate")
	@XmlJavaTypeAdapter(CalendarAdapter.class)
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
