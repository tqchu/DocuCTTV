package com.ctvv.dao;

import com.ctvv.model.Order;
import com.ctvv.model.OrderDetail;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO
		extends GenericDAO<Order> {
	private OrderDetailDAO orderDetailDAO;

	public OrderDAO(DataSource dataSource) {
		super(dataSource);
		orderDetailDAO = new OrderDetailDAO(dataSource);
	}

	@Override
	public Order get(int id) {
		return null;
	}

	@Override
	public List<Order> getAll() {
		return null;
	}

	@Override
	public Order create(Order order) {
		return null;
	}

	@Override
	public Order update(Order order) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public Order map(ResultSet resultSet) {
		try {
			int orderId = resultSet.getInt("order_id");
			int customerId = resultSet.getInt("customer_id");
			String customerName = resultSet.getString("customer_name");
			String recipientName = resultSet.getString("recipient_name");
			String phoneNumber = resultSet.getString("phone_number");
			String address = resultSet.getString("address");
			LocalDateTime orderTime = resultSet.getTimestamp("order_time").toLocalDateTime();
			LocalDateTime completedTime = resultSet.getTimestamp("completed_time").toLocalDateTime();
			int shippingFee = resultSet.getInt("shipping_fee");
			Order.OrderStatus status = Order.OrderStatus.valueOf(resultSet.getString("order_status").toUpperCase());
			List<OrderDetail> orderDetailList = orderDetailDAO.getGroup(orderId);
			return new Order(orderId, customerId, customerName, recipientName, phoneNumber, address, orderTime,
					completedTime,
					status, orderDetailList, shippingFee);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}



	public List<Order> getAll(String sortBy, String order) {
		List<Order> orderList = new ArrayList<>();
		String sql = "SELECT * FROM customer_order " +
				(sortBy != null ? " ORDER BY " + sortBy + " " + order : "");
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				orderList.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	public List<Order> getAll(Order.OrderStatus status) {
		List<Order> orderList = new ArrayList<>();
		String sql = "SELECT * FROM customer_order WHERE order_status=?"  +
				"  ORDER BY order_status";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, status.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				orderList.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
}
