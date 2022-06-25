package com.ctvv.dao;

import com.ctvv.model.OrderDetail;
import com.ctvv.model.OrderDetail;
import com.ctvv.model.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO
		implements GenericDAO<OrderDetail> {
	private ProductDAO productDAO;
	public OrderDetailDAO() {
		productDAO = new ProductDAO();
	}

	@Override
	public OrderDetail get(int id) {
		return null;
	}
	public List<OrderDetail> getGroup(String orderId){
			List<OrderDetail> orderDetailList = new ArrayList<>();
			String sql = "SELECT * FROM order_detail WHERE order_id=? ";
			try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
					connection.prepareStatement(sql);) {
				statement.setString(1, orderId);
				ResultSet resultSet = statement.executeQuery();
				// loop the result set
				while (resultSet.next()) {
					orderDetailList.add(map(resultSet));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return orderDetailList;
	}
	@Override
	public List<OrderDetail> getAll() {
		return null;
	}

	@Override
	public OrderDetail create(OrderDetail orderDetail) {
		return null;
	}

	@Override
	public OrderDetail update(OrderDetail orderDetail) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public OrderDetail map(ResultSet resultSet) {
		try {
			String orderId = resultSet.getString("order_id");
			int productId = resultSet.getInt("product_id");
			Product product = productDAO.get(productId);
			String productName = resultSet.getString("product_name");
			int quantity = resultSet.getInt("quantity");
			int price = resultSet.getInt("price");
			return new OrderDetail(orderId, product, productName, quantity, price);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Insert 1 danh sách orderDetail của 1 order <br> (Sử dụng batch)
	 */
	public void create(List<OrderDetail> orderDetailList) {
		String sql = "INSERT INTO order_detail VALUES (?, ?, ?,?,?)";
		Connection connection;
		PreparedStatement statement;
		try {
			connection = DataSourceHelper.getDataSource().getConnection();
			statement = connection.prepareStatement(sql);
			// Loop qua từng orderDetail
			for (OrderDetail orderDetail : orderDetailList) {
				statement.setString(1, orderDetail.getOrderId());
				statement.setInt(2, orderDetail.getProduct().getProductId());
				statement.setString(3, orderDetail.getProductName());
				statement.setInt(4, orderDetail.getQuantity());
				statement.setInt(5, orderDetail.getPrice());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
