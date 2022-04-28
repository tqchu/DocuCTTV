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

	@Override
	public Material get(int id) {
		String sql = "SELECT * FROM material WHERE material_id=?";
		Material returnMaterial = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				returnMaterial = map(resultSet);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnMaterial;

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

	public Material find(Material material) {
		String sql = "SELECT * FROM material WHERE material_name=?";
		Material returnMaterial = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setString(1, material.getMaterialName());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				returnMaterial = map(resultSet);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnMaterial;
	}
}
