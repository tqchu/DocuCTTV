package com.ctvv.model;

public class Admin {
	int user_id;
	String fullName;
	String username;
	String password;
	String role;

	public Admin(int user_id, String fullName, String username, String password, String role) {
		this.user_id = user_id;
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Admin(String username, String password) {
		this.username=username;
		this.password=password;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
