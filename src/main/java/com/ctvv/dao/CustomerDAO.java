package com.ctvv.dao;

import com.ctvv.model.Customer;
import com.ctvv.model.ShippingAddress;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;

public class CustomerDAO {

	private final DataSource dataSource;

	public CustomerDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Customer validate(Customer customer) throws SQLException {
		// Tạo connection
		Customer authenticatedCustomer = null;
		String phoneNumber = customer.getPhoneNumber();
		String password = customer.getPassword();
		String sql = "SELECT * FROM customer WHERE (phonenumber=?) and (password=?) LIMIT 1";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, phoneNumber);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int userId = resultSet.getInt("user_id");
				String fullName = resultSet.getString("fullname");
				String passWord = resultSet.getString("password");
				String pNumber = resultSet.getString("phoneNumber");
				Customer.Gender gender = Customer.Gender.values()[resultSet.getByte("gender")];
				LocalDate dateOfBirth = resultSet.getDate("date_of_birth").toLocalDate();
				ShippingAddressDAO addressDAO = new ShippingAddressDAO(dataSource);
				ShippingAddress address = addressDAO.getAddress(userId);
				authenticatedCustomer = new Customer(userId, password, fullName, pNumber, gender, dateOfBirth, address);
			}
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}


		return authenticatedCustomer;
	}
	public Customer updateProfile(Customer customer) throws SQLException{
		String fullName = customer.getFullName();
		Customer.Gender gender = customer.getGender();
		LocalDate dateOfBirth = customer.getDateOfBirth();
		int id = customer.getUserId();

		String sql = "UPDATE customer SET fullname=?, gender=?, date_of_birth=? WHERE user_id=? ";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, fullName);
			statement.setInt(2, gender.getValue());
			statement.setDate(3, Date.valueOf(dateOfBirth));
			statement.setInt(4, id);
			statement.execute();
		}
		return customer;

	}
	public Customer updatePassword(Customer customer) throws SQLException{
		int userId = customer.getUserId();
		String newPassword = customer.getPassword();
		Connection connection = null;
		String sql = "UPDATE customer SET password = ? WHERE user_id = ?";
		PreparedStatement statement = null;
		try{
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1,newPassword);
			statement.setInt(2,userId);
			statement.execute();
		}
		finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return customer;
	}
/*
	public Customer update(Customer admin) throws SQLException {
		String fullName = admin.getFullName();
		String username = admin.getUsername();
		String password = admin.getPassword();
		int id = admin.getUser_id();

		String sql = "UPDATE admin SET fullname=?, username=?, password=? WHERE user_id=?";
		Connection connection = null;
		PreparedStatement statement = null;
		try{
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, fullName);
			statement.setString(2, username);
			statement.setString(3, password);
			statement.setInt(4, id);
			statement.execute();
		}
		finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return admin;
	}*/


}
