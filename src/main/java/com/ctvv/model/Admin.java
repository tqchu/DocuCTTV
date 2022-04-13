package com.ctvv.model;

import java.util.Objects;

public class Admin {
	private int userId;
	private String username;
	private String email;
	private String password;
	private String fullName;
	private String role;

	public Admin() {
	}
	public Admin(Admin admin){
		this.userId=admin.getUserId();
		this.username =admin.getUsername();
		this.email=admin.getEmail();
		this.password=admin.getPassword();
		this.role=admin.getRole();
	}
	public Admin(int userId, String username, String email, String password, String fullName, String role) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.role = role;
	}

	public Admin(String usernameOrEmail, String password) {
		this.username = usernameOrEmail;
		this.email = usernameOrEmail;
		this.password = password;
	}

	public Admin(int id, String username, String email, String fullName, String role) {
		this.userId=id;
		this.username=username;
		this.email=email;
		this.fullName=fullName;
		this.role=role;
	}

	public Admin(String username, String email, String fullName, String password, String role) {
		this.username=username;
		this.email=email;
		this.fullName=fullName;
		this.password=password;
		this.role=role;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Admin admin = (Admin) o;
		return userId == admin.userId;
	}
}
