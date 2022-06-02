package com.ctvv.dao;

import com.ctvv.model.ShippingAddress;

import javax.sql.DataSource;
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

		int id = shippingAddress.getCustomerId();
		String sql = "UPDATE shipping_address SET recipient_name=?,  address=? WHERE user_id=?";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, recipient_name);
			statement.setString(2, address);
			statement.setInt(3, id);
			statement.execute();
		}
		return shippingAddress;
	}

	public ShippingAddress create(ShippingAddress address) {
		String sql = "INSERT INTO shipping_address VALUES (?,?,?,?)";
		try(Connection connection = dataSource.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, address.getCustomerId());
			preparedStatement.setString(2, address.getRecipientName());
			preparedStatement.setString(3, address.getPhoneNumber());
			preparedStatement.setString(4, address.getAddress());
			preparedStatement.executeUpdate();
			return address;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
