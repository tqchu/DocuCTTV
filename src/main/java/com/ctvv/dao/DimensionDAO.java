package com.ctvv.dao;

import com.ctvv.model.Category;
import com.ctvv.model.Dimension;
import com.ctvv.model.Dimension;
import com.ctvv.model.Dimension;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DimensionDAO
		extends GenericDAO<Dimension> {

	public DimensionDAO(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Dimension get(int id) {

		String sql = "SELECT * FROM dimension WHERE dimension_id=?";
		Dimension returnDimension = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				returnDimension = map(resultSet);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnDimension;
	}

	@Override
	public List<Dimension> getAll() {
		return null;
	}

	@Override
	public Dimension create(Dimension dimension) {
		String sql = "INSERT INTO dimension(length, width, height) VALUES(?,?,?)";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setDouble(1, dimension.getLength());
			statement.setDouble(2, dimension.getWidth());
			statement.setDouble(3, dimension.getHeight());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			while (resultSet.next()) {
				int dimensionId = resultSet.getInt(1);
				dimension.setDimensionId(dimensionId);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dimension;
	}

	@Override
	public Dimension update(Dimension dimension) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public Dimension map(ResultSet resultSet) {
		try {
			int dimensionId = resultSet.getInt("dimension_id");
			int length = resultSet.getInt("length");
			int width = resultSet.getInt("width");
			int height = resultSet.getInt("height");
			return new Dimension(dimensionId, length, width, height);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Dimension find(Dimension dimension) {
		String sql = "SELECT * FROM dimension WHERE length=? AND width=? AND height=? LIMIT 1";
		Dimension returnDimension=null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setDouble(1, dimension.getLength());
			statement.setDouble(2, dimension.getWidth());
			statement.setDouble(3, dimension.getHeight());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				returnDimension = map(resultSet);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnDimension;
	}
}
