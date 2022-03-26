package com.ctvv.dao;

import com.ctvv.model.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.*;

public class AdminDAO {

	private final DataSource dataSource;
	public AdminDAO(DataSource dataSource) {
		this.dataSource= dataSource;
	}

	public Admin validate(Admin admin) throws SQLException {
		// Tạo connection
		Admin authenticatedAdmin = null;
		String username=admin.getUsername();
		String password= admin.getPassword();
		String sql="SELECT * FROM admin WHERE (username=?) and (password=?)";
		try{
			Connection connection=dataSource.getConnection();
			PreparedStatement statement=connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet resultSet= statement.executeQuery();
			while (resultSet.next()){
				int userId= resultSet.getInt("user_id");
				String fullName= resultSet.getString("fullname");
				String userName= resultSet.getString("username");
				String passWord= resultSet.getString("password");
				String role= resultSet.getString("role");
				authenticatedAdmin=new Admin(userId,username,passWord,fullName,role);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return authenticatedAdmin;
	}

	public Admin update(Admin admin) throws SQLException{
		String fullName= admin.getFullName();
		String username= admin.getUsername();
		String password= admin.getPassword();
		int id= admin.getUser_id();
		Connection connection = dataSource.getConnection();
		String sql = "UPDATE admin SET fullname=?, username=?, password=? WHERE user_id=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, fullName);
		statement.setString(2, username);
		statement.setString(3, password);
		statement.setInt(4, id);
		statement.execute();
		return admin;
	}
}
