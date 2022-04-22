package com.ctvv.dao;

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
	public  void create(int productId, int dimensionId){
		String sql = "INSERT INTO product_dimension VALUES(?,?)";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, productId);
			statement.setInt(2, dimensionId);

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
