package edu.cs5774.project5;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Project {
	private String name;
	
	private String description;

	private Calendar createdAt;

	private Calendar updatedAt;
	
	private LinkedList<User> users = new LinkedList<User>();
	
	private LinkedList<TaskBug> task = new LinkedList<TaskBug>();
	private LinkedList<TaskBug> bug = new LinkedList<TaskBug>();
	
	private String savedPath;
	
	public Project() {}

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
		this.savedPath = other.savedPath;
		
		this.users.addAll(other.users);
		this.task.addAll(other.task);
		this.bug.addAll(other.bug);
	}

	
	public String getSavedPath() {
		return savedPath;
	}

	public void setSavedPath(String savedPath) {
		this.savedPath = savedPath;
	}

	public void addUser(User user) {
		users.add(user);
	}
	
	public void addTask(TaskBug taskBug) {
		task.add(taskBug);
	}

	public void removeTask(TaskBug taskBug) {
		if (task.contains(taskBug)) {
			task.remove(taskBug);
		} else {
			bug.remove(taskBug);
		}
	}
	
	public void addBug(TaskBug taskBug) {
		bug.add(taskBug);
	}

	public void removeBug(TaskBug taskBug) {
		if (task.contains(taskBug)) {
			task.remove(taskBug);
		} else {
			bug.remove(taskBug);
		}
	}
	
	@XmlElement(name="title")
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
	
	@XmlElement(name="user")
	public LinkedList<User> getUsers() {
		return users;
	}
	public void setUsers(LinkedList<User> users) {
		this.users = users;
	}
	
	@XmlElement(name="task")
	public List<TaskBug> getTask() {
		return task;
	}
	public void setTask(LinkedList<TaskBug> task) {
		this.task = task;
	}
	
	@XmlElement(name="bug")
	public List<TaskBug> getBug() {
		return bug;
	}
	public void setBug(LinkedList<TaskBug> bug) {
		this.bug = bug;
	}
	
}
