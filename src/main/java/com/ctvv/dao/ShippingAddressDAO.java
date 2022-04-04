package com.ctvv.dao;

import com.ctvv.model.Customer;
import com.ctvv.model.ShippingAddress;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShippingAddressDAO {

	private final DataSource dataSource;

	public ShippingAddressDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public ShippingAddress getAddress(int userId) throws SQLException {
		Connection connection = dataSource.getConnection();
		ShippingAddress shippingAddress = null;
		String sql = "SELECT * FROM shipping_address WHERE user_id=? LIMIT 1";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, userId);
		ResultSet resultSet;
		resultSet = statement.executeQuery();
		while (resultSet.next()) {
			int customerId = resultSet.getInt("user_id");
			String recipientName = resultSet.getString("recipient_name");
			String phoneNumber = resultSet.getString("phone_number");
			String address = resultSet.getString("address");
			shippingAddress = new ShippingAddress(customerId, recipientName, phoneNumber, address);
		}
		resultSet.close();
		statement.close();
		connection.close();
		return shippingAddress;
	}

	public ShippingAddress update(ShippingAddress shippingAddress) throws SQLException{

		String recipient_name = shippingAddress.getRecipientName();
		String address = shippingAddress.getAddress();
		String phone_number = shippingAddress.getPhoneNumber();

		int id = shippingAddress.getCustomerId();
		String sql = "UPDATE shipping_address SET recipient_name=?, phone_number=?, address=? WHERE user_id=?";
		Connection connection = null;
		PreparedStatement statement = null;
		try{
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, recipient_name);
			statement.setString(2, phone_number);
			statement.setString(3, address);
			statement.setInt(4, id);
			statement.execute();
		} finally {
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
		return shippingAddress;
	}
}
