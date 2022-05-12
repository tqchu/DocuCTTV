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
		String sql = "SELECT * FROM customer_order WHERE order_id = ?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		String sql = "UPDATE customer_order SET recipient_name=?, phone_number=?, address=?, order_status=? WHERE " +
				"order_id=?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, order.getRecipientName());
			statement.setString(2, order.getPhoneNumber());
			statement.setString(3, order.getAddress());
			statement.setString(4, order.getStatus().name());
			statement.setInt(5, order.getOrderId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
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
	public List<Order> getAll(Order.OrderStatus status, String keyword, String sortBy, String order) {
		List<Order> orderList = new ArrayList<>();
		String sql = "SELECT * FROM customer_order WHERE order_status=?"+
				(keyword!=null? " AND (order_id LIKE '% "+keyword+ "%' " +
										"OR customer_name LIKE '%"+keyword+"%')":"") +
				(sortBy!=null? " ORDER BY " +sortBy + " " + order :"");
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1,status.name());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				orderList.add(map(resultSet));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return orderList;
	}

	public int count(Order.OrderStatus status, String keyword) {
		int count = 0;
		String sql = "SELECT COUNT(*) AS count FROM customer_order WHERE order_status=?"+
					(keyword!=null? " AND (order_id LIKE '% "+keyword+ "%' " +
										"OR customer_name LIKE '%"+keyword+"%')":"");
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1,status.name());
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("count");
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return count;
	}
}
