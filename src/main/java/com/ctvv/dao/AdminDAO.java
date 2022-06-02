package com.ctvv.dao;

import com.ctvv.model.Admin;
import com.ctvv.util.PasswordHashingUtil;

import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class 	AdminDAO {

	private final DataSource dataSource;

	public AdminDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Admin validate(Admin admin)  {
		// Tạo connection
		Admin authenticatedAdmin = null;
		String usernameToAuthenticate = admin.getUsername();
		String emailToAuthenticate = admin.getEmail();
		String password = admin.getPassword();
		String sql = "SELECT * FROM admin WHERE ((username=?) or email=? ) LIMIT 1";

		try (Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, usernameToAuthenticate);
			statement.setString(2, emailToAuthenticate);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String validPassword = resultSet.getString("password");
				if (PasswordHashingUtil.validatePassword(password,validPassword))
					return map(resultSet);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	private Admin map(ResultSet resultSet){
		try {
			int userId = resultSet.getInt("user_id");
			String fullName = resultSet.getString("fullname");
			String username = resultSet.getString("username");
			String email = resultSet.getString("email");
			String passWord = resultSet.getString("password");
			String role = resultSet.getString("role");
			return new Admin(userId, username,email,passWord,fullName,role);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	public Admin get(int id) throws SQLException {
		Admin admin = new Admin();
		String sql = "SELECT * FROM admin WHERE user_id=? ";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			// loop the result set
			while (resultSet.next()) {
				String username = resultSet.getString("username");
				String fullName = resultSet.getString("fullname");
				String email = resultSet.getString("email");
				String role = resultSet.getString("role");
				admin = new Admin(id, username, email, fullName, role);
			}
		}
		return admin;
	}

	public Admin update(Admin admin) throws SQLException {
		String fullName = admin.getFullName();
		String username = admin.getUsername();
		String email = admin.getEmail();
		String password = admin.getPassword();
		int id = admin.getUserId();

		String sql = "UPDATE admin SET fullname=?, username=?,email=?, password=? WHERE user_id=?";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, fullName);
			statement.setString(2, username);
			statement.setString(3, email);
			statement.setString(4, PasswordHashingUtil.createHash(password));
			statement.setInt(5, id);
			statement.execute();
		}
		return admin;
	}
	public void updateRole(String role, int id){

		String sql = "UPDATE admin SET role=? WHERE user_id=?";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, role);
			statement.setInt(2, id);

			statement.execute();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	public List<Admin> getAdminList() {
		List<Admin> adminList = new ArrayList<>();
		String sql = "SELECT * FROM admin";
		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
			ResultSet resultSet = statement.executeQuery(sql);
			// loop the result set
			while (resultSet.next()) {
				int id = resultSet.getInt("user_id");
				String username = resultSet.getString("username");
				String email = resultSet.getString("email");
				String fullName = resultSet.getString("fullname");
				String role = resultSet.getString("role");
				Admin admin = new Admin(id, username, email, fullName, role);
				adminList.add(admin);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return adminList;
	}

	public void createAdmin(Admin admin) {
		Connection connection = null;
		String sql = "INSERT INTO admin(username,email, password, fullname, role)  VALUES(?,?, ?, ?, ?)";
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, admin.getUsername());
			statement.setString(2, admin.getEmail());
			statement.setString(3, PasswordHashingUtil.createHash(admin.getPassword()));
			statement.setString(4, admin.getFullName());
			statement.setString(5, admin.getRole());
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			try{
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
	}



	public void delete(int id) {
		String sql = "DELETE FROM admin WHERE user_id=?";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public Admin findByUsername(String username) {
		String sql = "SELECT * FROM admin WHERE username=?";
		Admin admin = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql); ) {
			ResultSet resultSet;
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			while (resultSet.next()){
				int id = resultSet.getInt("user_id");
				String fullName = resultSet.getString("fullname");
				String email = resultSet.getString("email");
				String role = resultSet.getString("role");
				admin = new Admin(id, username, email, fullName, role);
			}
			resultSet.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return admin;
	}

	public Admin findByEmail(String email) {
		String sql = "SELECT * FROM admin WHERE email=?";
		Admin admin = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql); ) {
			ResultSet resultSet;
			statement.setString(1, email);
			resultSet = statement.executeQuery();
			while (resultSet.next()){
				int id = resultSet.getInt("user_id");
				String username = resultSet.getString("username");
				String fullName = resultSet.getString("fullname");
				String role = resultSet.getString("role");
				admin = new Admin(id, username, email, fullName, role);
			}
			resultSet.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return admin;
	}
}
