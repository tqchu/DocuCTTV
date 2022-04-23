package com.ctvv.dao;

import com.ctvv.model.ProductDimension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDimensionDAO {
	private DataSource dataSource;
	public ProductDimensionDAO(DataSource dataSource){
		this.dataSource = dataSource;
	}
	public  void create(ProductDimension productDimension){
		String sql = "INSERT INTO product_dimension VALUES(?,?)";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, productDimension.getProductId());
			statement.setInt(2, productDimension.getDimensionId());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ProductDimension find(int productId, int dimensionId) {
		String sql = "SELECT * FROM product_dimension where (product_id=?) AND (dimension_id=?)";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, productId);
			statement.setInt(2, dimensionId);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()){
				return new ProductDimension(productId, dimensionId);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  null;
	}

	public void delete(int productId) {
		String sql = "DELETE FROM product_dimension WHERE product_id=?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, productId);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
