package com.ctvv.model;

public class Admin {
	private int userId;
	private String username;
	private String password;
	private String fullName;
	private String role;

	public Admin(int user_id, String username, String password, String fullName, String role) {
		this.userId = user_id;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.role = role;
	}

	public Admin(Admin admin) {
		this.userId = admin.userId;
		this.username = admin.username;
		this.password = admin.password;
		this.fullName = admin.fullName;
		this.role = admin.role;
	}

	public Admin(String password, String username) {
		this.password = password;
		this.username = username;
	}

	public Admin(String fullName, String username, String password) {
		this.password = password;
		this.username = username;
		this.fullName = fullName;
	}
	public Admin(String fullName, String username, String password, String role){
		this.fullName = fullName;
		this.username=username;
		this.password=password;
		this.role=role;
	}

	public Admin(int id, String username, String fullName, String role) {
		this.userId = id;
		this.role=role;
		this.username = username;
		this.fullName = fullName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
