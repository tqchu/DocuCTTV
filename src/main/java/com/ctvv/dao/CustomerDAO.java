package com.ctvv.dao;

import com.ctvv.model.*;
import com.ctvv.model.Customer;
import com.ctvv.util.PasswordHashingUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

	public List<Customer> getCustomerList() {
		List<Customer> customerList = new ArrayList<>();
		String sql = "SELECT * FROM customer";
		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
			ResultSet resultSet = statement.executeQuery(sql);
			// loop the result set
			while (resultSet.next()) {
				int user_id = resultSet.getInt("user_id");
				String phonenumber = resultSet.getString("phonenumber");
				String email = resultSet.getString("email");
				String fullname = resultSet.getString("fullname");
				LocalDate dateOfBirth = resultSet.getDate("date_of_birth").toLocalDate();
				Customer.Gender gender = Customer.Gender.values()[resultSet.getByte("gender")];
				Customer customer = new Customer(user_id, email, fullname, phonenumber, gender, dateOfBirth);
				customerList.add(customer);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return customerList;
	}

	public List<Customer> get(int begin, int numberOfRec, String keyword) {
		List<Customer> customerList = new ArrayList<>();
		String sql =
				"SELECT * FROM customer " +
						(keyword != null ? " WHERE fullname LIKE '%" + keyword + "%' " : "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				customerList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	public int count(String keyword) {
		int count = 0;
		String sql =
				"SELECT COUNT(user_id) AS no FROM customer " +
						(keyword != null ? " WHERE fullname" +  " LIKE '%" + keyword + "%' " : "");
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("no");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
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
		String sql = "DELETE FROM customer WHERE user_id = ?";
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				" LIMIT 1";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, account);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String validPassword = resultSet.getString("password");
				if (PasswordHashingUtil.validatePassword(password,validPassword))
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
			statement.setString(1, PasswordHashingUtil.createHash(newPassword));
			statement.setInt(2, userId);
			statement.execute();
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return customer;
	}

	public Customer findCustomerByPhoneNumber(String phoneNumber) {
		String sql = "SELECT * FROM customer WHERE phonenumber=? ";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setString(1, phoneNumber);
			ResultSet resultSet = statement.executeQuery();
			// loop the result set
			while (resultSet.next()) {
				return  map(resultSet);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
