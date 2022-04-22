package com.ctvv.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductMaterialDAO{
	private DataSource dataSource;
	public ProductMaterialDAO(DataSource dataSource){
		this.dataSource = dataSource;
	}
	public void create(int productId, int materialId) {
		String sql = "INSERT INTO product_material VALUES(?,?)";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, productId);
			statement.setInt(2, materialId);

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
