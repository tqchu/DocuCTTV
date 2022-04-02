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
		String sql = "SELECT * FROM shipping_address WHERE user_id=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, userId);
		ResultSet resultSet;
		resultSet = statement.executeQuery();
		while (resultSet.next()) {
			String recipientName = resultSet.getString("recipient_name");
			String phoneNumber = resultSet.getString("phone_number");
			String address = resultSet.getString("address");
			shippingAddress = new ShippingAddress(recipientName, phoneNumber, address);
		}
		resultSet.close();
		statement.close();
		connection.close();

		return shippingAddress;
	}
}
