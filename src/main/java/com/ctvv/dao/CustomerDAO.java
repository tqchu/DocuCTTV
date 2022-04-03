package com.ctvv.dao;

import com.ctvv.model.Customer;
import com.ctvv.model.ShippingAddress;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public Customer update_Profile(Customer customer) throws SQLException{
		String fullName = customer.getFullName();
		String phoneNumber = customer.getPhoneNumber();
		Customer.Gender gender = customer.getGender();
		LocalDate date_of_birth = customer.getDateOfBirth();
		int id = customer.getUserId();

		String sql = "UPDATE customer SET fullname=?, phonenumber=?, gender=?, date_of_birth=? WHERE user_id=? ";
		Connection connection = null;
		PreparedStatement statement = null;

		try{
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, fullName);
			statement.setString(2,phoneNumber);
			statement.setString(3, String.valueOf(gender));
			statement.setString(4, String.valueOf(date_of_birth));
			statement.setInt(5, id);
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
