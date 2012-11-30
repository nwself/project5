package edu.cs5774.project5;

import java.util.Calendar;
import java.util.LinkedList;

public class Project {
	private String name;
	private String description;
	private Calendar createdAt;
	private Calendar updatedAt;
	
	private LinkedList<User> users = new LinkedList<User>();
	private LinkedList<TaskBug> taskBugs = new LinkedList<TaskBug>();
	
	public Project(String name, String description, Calendar createdAt,
			Calendar updatedAt) {
		super();
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Project(Project other) {
		this.name = other.name;
		this.description = other.description;
		this.createdAt = other.createdAt;
		this.updatedAt = other.updatedAt;
		
		this.users.addAll(other.users);
		this.taskBugs.addAll(other.taskBugs);
	}

	public void addUser(User user) {
		users.add(user);
	}
	
	public void addTaskBug(TaskBug taskBug) {
		taskBugs.add(taskBug);
	}

	public void removeTaskBug(TaskBug taskBug) {
		taskBugs.remove(taskBug);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public LinkedList<User> getUsers() {
		return users;
	}
	public void setUsers(LinkedList<User> users) {
		this.users = users;
	}
	public LinkedList<TaskBug> getTaskBugs() {
		return taskBugs;
	}
	public void setTaskBugs(LinkedList<TaskBug> taskBugs) {
		this.taskBugs = taskBugs;
	}
}
