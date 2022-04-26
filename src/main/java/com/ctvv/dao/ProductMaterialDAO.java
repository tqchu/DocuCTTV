package com.ctvv.dao;

import com.ctvv.model.ProductMaterial;
import com.ctvv.model.ProductMaterial;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMaterialDAO{
	private DataSource dataSource;
	public ProductMaterialDAO(DataSource dataSource){
		this.dataSource = dataSource;
	}
	public void create(ProductMaterial productMaterial) {
		String sql = "INSERT INTO product_material VALUES(?,?)";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, productMaterial.getProductId());
			statement.setInt(2, productMaterial.getMaterialId());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ProductMaterial find(int productId, int materialId) {
		String sql = "SELECT * FROM product_material where (product_id=?) AND (material_id=?)";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, productId);
			statement.setInt(2, materialId);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()){
				return new ProductMaterial(productId, materialId);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  null;
	}

	public void delete(ProductMaterial productMaterial) {
		String sql = "DELETE FROM product_material WHERE product_id=? AND material_id=?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, productMaterial.getProductId());
			statement.setInt(2, productMaterial.getMaterialId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeUnnecessaryMaterial() {
		String sql = "DELETE FROM material WHERE material_id NOT IN (SELECT DISTINCT material_id FROM product_material)";
		try(Connection connection = dataSource.getConnection();
		    PreparedStatement statement = connection.prepareStatement(sql)){
			statement.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}
