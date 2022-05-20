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
		String sql = "INSERT INTO customer_order VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, order.getOrderId());
			statement.setInt(2, order.getCustomerId());
			statement.setString(3, order.getCustomerName());
			statement.setString(4, order.getRecipientName());
			statement.setString(5, order.getPhoneNumber());
			statement.setString(6, order.getAddress());
			statement.setTimestamp(7, Timestamp.valueOf(order.getOrderTime()));
			statement.setTimestamp(8, null);
			statement.setInt(9, order.getShippingFee());
			statement.setString(10, Order.OrderStatus.PENDING.name());
			statement.execute();
			statement.close();
			connection.close();
			orderDetailDAO.create(order.getOrderDetailList());
			return order;
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			statement.setString(5, order.getOrderId());
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
			String orderId = resultSet.getString("order_id");
			int customerId = resultSet.getInt("customer_id");
			String customerName = resultSet.getString("customer_name");
			String recipientName = resultSet.getString("recipient_name");
			String phoneNumber = resultSet.getString("phone_number");
			String address = resultSet.getString("address");
			LocalDateTime orderTime = resultSet.getTimestamp("order_time").toLocalDateTime();
			LocalDateTime completedTime = resultSet.getTimestamp("completed_time") != null ? resultSet.getTimestamp(
					"completed_time").toLocalDateTime() : null;
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

	public List<Order> getAll(
			int begin, int num, Order.OrderStatus status, String keyword, String sortBy,
			String order) {
		List<Order> orderList = new ArrayList<>();
		String sql = "SELECT * FROM customer_order WHERE order_status=?" +
				(keyword != null ? " AND (order_id LIKE '%" + keyword + "%' " +
						"OR customer_name LIKE '%" + keyword + "%')" : "") +
				(sortBy != null ? " ORDER BY " + sortBy + " " + order : "") + " LIMIT " + begin + ", " + num;
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, status.name());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				orderList.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	public List<Order> getAllForCustomers(
			int begin, int num, Order.OrderStatus status, String keyword, String sortBy,
			String order, int id) {
		List<Order> orderList = new ArrayList<>();
		String sql;
		if (keyword != null) {
			sql = "SELECT customer_order.* FROM customer_order JOIN order_detail od on customer_order.order_id = od" +
					".order_id WHERE order_status=? AND customer_id = ?" +
					" AND (customer_order.order_id LIKE '%" + keyword + "%' " +
					" OR product_name LIKE '%" + keyword + "%')" +
					(sortBy != null ? " ORDER BY " + sortBy + " " + order : "") + " LIMIT " + begin + ", " + num;
		} else {
			sql = "SELECT * FROM customer_order WHERE order_status=? AND customer_id = ?" +
					(sortBy != null ? " ORDER BY " + sortBy + " " + order : "") + " LIMIT " + begin + ", " + num;
		}

		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, status.name());
			statement.setInt(2, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				orderList.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	public int count(Order.OrderStatus status, String keyword) {
		int count = 0;
		String sql = "SELECT COUNT(*) AS count FROM customer_order WHERE order_status=?" +
				(keyword != null ? " AND (order_id LIKE '% " + keyword + "%' " +
						"OR customer_name LIKE '%" + keyword + "%')" : "");
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, status.name());
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int countForCustomer(Order.OrderStatus status, String keyword, int id) {
		String sql;
		if (keyword != null) {
			sql = "SELECT COUNT(*) AS count FROM customer_order JOIN order_detail od on customer_order.order_id = od" +
					".order_id WHERE order_status=? AND customer_id =?" +
					" AND (customer_order.order_id LIKE '%" + keyword + "%' " +
					" OR product_name LIKE '%" + keyword + "%')"
			;
		} else {
			sql = "SELECT COUNT(*) AS count FROM customer_order WHERE order_status=? AND customer_id =?";
		}
		int count = 0;
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, status.name());
			statement.setInt(2, id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Order get(String id) {
		String sql = "SELECT * FROM customer_order WHERE order_id = ?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
