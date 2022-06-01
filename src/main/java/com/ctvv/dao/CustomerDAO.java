package com.ctvv.dao;

import com.ctvv.model.Customer;
import com.ctvv.model.Customer;
import com.ctvv.model.ShippingAddress;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class CustomerDAO
		extends GenericDAO<Customer> {

	public CustomerDAO(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Customer get(int id) {
		Customer customer = new Customer();
		String sql = "SELECT * FROM customer WHERE user_id=? ";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			// loop the result set
			while (resultSet.next()) {
				return  map(resultSet);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public List<Customer> getAll() {
		return null;
	}

	@Override
	public Customer create(Customer customer) {
		return null;
	}

	@Override
	public Customer update(Customer customer) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public Customer map(ResultSet resultSet) {
		try {
			int userId = resultSet.getInt("user_id");
			String fullName = resultSet.getString("fullname");
			String password = resultSet.getString("password");
			String pNumber = resultSet.getString("phoneNumber");
			String email = resultSet.getString("email");
			Customer.Gender gender = Customer.Gender.values()[resultSet.getByte("gender")];
			LocalDate dateOfBirth = resultSet.getDate("date_of_birth").toLocalDate();
			ShippingAddressDAO addressDAO = new ShippingAddressDAO(dataSource);
			ShippingAddress address = addressDAO.getAddress(userId);
			return new Customer(userId, password, email, fullName, pNumber, gender, dateOfBirth,
					address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Customer validate(Customer customer) {
		boolean isPhoneNumber = (customer.getPhoneNumber() != null);
		String account = isPhoneNumber ? customer.getPhoneNumber() : customer.getEmail();
		String password = customer.getPassword();
		String sql = "SELECT * FROM customer WHERE " +
				(isPhoneNumber ? "phonenumber=?" : "email=?") +
				" and (password=?) LIMIT 1";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, account);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Customer updateProfile(Customer customer) throws SQLException {
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

	public Customer updatePassword(Customer customer) throws SQLException {
		int userId = customer.getUserId();
		String newPassword = customer.getPassword();
		Connection connection = null;
		String sql = "UPDATE customer SET password = ? WHERE user_id = ?";
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, newPassword);
			statement.setInt(2, userId);
			statement.execute();
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return customer;
	}
}
