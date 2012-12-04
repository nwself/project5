package edu.cs5774.project5;

import java.util.Calendar;

/**
 * Model class that describes a User which can be assigned to a Task, Bug or Project.
 *
 */
public class User {
	private String username;
	private String email;
	private Calendar createdAt;
	
	public User() {
		
	}
	
	public User(String username, String email, Calendar createdAt) {
		super();
		this.username = username;
		this.email = email;
		this.createdAt = createdAt;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Calendar getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
}
