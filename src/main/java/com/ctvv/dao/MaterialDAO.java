package com.ctvv.dao;

import com.ctvv.model.Dimension;
import com.ctvv.model.Material;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO
		extends GenericDAO<Material> {
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

		} catch (SQLException e) {
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
	public Material create(Material material) {
		String sql = "INSERT INTO material(material_name) VALUES(?)";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, material.getMaterialName());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			while (resultSet.next()) {
				int materialId = resultSet.getInt(1);
				material.setMaterialId(materialId);
			}
			return material;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
		try {
			int materialId = resultSet.getInt("material_id");
			String materialName = resultSet.getString("material_name");
			return new Material(materialId, materialName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Material find(String materialName) {
		String sql = "SELECT * FROM material WHERE material_name=?";
		Material material = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setString(1, materialName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				material = map(resultSet);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return material;
	}
}
