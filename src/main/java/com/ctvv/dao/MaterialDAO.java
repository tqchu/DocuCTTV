package com.ctvv.dao;

import com.ctvv.model.Material;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO extends GenericDAO<Material>{
	public MaterialDAO(DataSource dataSource) {
		super(dataSource);
	}

	public List<Material> getGroup(int productId) {
		List<Material> materialList = new ArrayList<>();
		String sql = "SELECT * FROM material " +
				"JOIN product_material ON material.material_id = product_material.material_id " +
				"JOIN product p on product_material.product_id = p.product_id  WHERE p.product_id=? ";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, productId);
			ResultSet resultSet = statement.executeQuery();
			// loop the result set
			while (resultSet.next()) {
				int id = resultSet.getInt("material_id");
				String name = resultSet.getString("material_name");
				materialList.add(new Material(id, name));

			}

		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return materialList;
	}
	@Override
	public Material get(int id) {
		return null;
	}

	@Override
	public List<Material> getAll() {
		return null;
	}

	@Override
	public void create(Material material) {

	}

	@Override
	public Material update(Material material) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public Material map(ResultSet resultSet) {
		return null;
	}
}
