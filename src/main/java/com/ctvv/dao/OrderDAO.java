package com.ctvv.dao;

import com.ctvv.model.Order;
import com.ctvv.model.OrderDetail;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

	public String createOrderId(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuMMdd");
		LocalDate localDate = LocalDate.now();
		String strDate = dtf.format(localDate);
		char[] random = new char[8];
		char[] chars = "ABSDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		Random r = new Random();
		for (int i = 0;  i < 8;  i++) {
			random[i] = chars[r.nextInt(chars.length)];
		}
		String strRandom = new String(random);
		String orderId = new String(strDate + strRandom);
		return orderId;
	}

	@Override
	public Order create(Order order) {
		String sql = "INSERT INTO customer_order VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			order.setOrderId(createOrderId());
			statement.setString(1, order.getOrderId());
			statement.setInt(2, order.getCustomerId());
			statement.setString(3, order.getCustomerName());
			statement.setString(4, order.getRecipientName());
			statement.setString(5, order.getPhoneNumber());
			statement.setString(6, order.getAddress());
			statement.setTimestamp(7, Timestamp.valueOf(order.getOrderTime()));
			statement.setTimestamp(8, Timestamp.valueOf(order.getCompletedTime()));
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			while(resultSet.next()){
				String orderId = resultSet.getString(1);
				order.setOrderId(orderId);
			}
			connection.commit();
			resultSet.close();
			for(OrderDetail orderDetail: order.getOrderDetailList()){
				orderDetail.setOrderId(order.getOrderId());
				orderDetailDAO.create(orderDetail);
			}
			return order;
		} catch (SQLException e) {
			try {
				if (connection != null) connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
			String orderId = resultSet.getString("order_id");
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
		String sql = "SELECT * FROM customer_order WHERE order_status=?";
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
