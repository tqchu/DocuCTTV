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

    public ShippingAddress updateAddress(int userId, ShippingAddress shippingAddress) throws SQLException {
        Connection connection = null;
        String sql = "UPDATE shipping_address SET recipient_name=?, phone_number=?, address=? WHERE user_id=?";
        PreparedStatement statement = null;
        String recipientName = shippingAddress.getRecipientName();
        String phoneNumber = shippingAddress.getPhoneNumber();
        String address = shippingAddress.getAddress();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, recipientName);
            statement.setString(2, phoneNumber);
            statement.setString(3, address);
            statement.setInt(4, userId);
            statement.execute();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return shippingAddress;

    }
}
