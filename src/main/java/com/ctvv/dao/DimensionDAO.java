package com.ctvv.dao;

import com.ctvv.model.Dimension;
import com.ctvv.model.Dimension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DimensionDAO
		extends GenericDAO<Dimension> {

	public DimensionDAO(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Dimension get(int id) {
		return null;
	}

	public List<Dimension> getGroup(int productId) {
		List<Dimension> dimensionList = new ArrayList<>();
		String sql = "SELECT * FROM dimension " +
				"JOIN product_dimension ON dimension.dimension_id = product_dimension.dimension_id " +
				"JOIN product p on product_dimension.product_id = p.product_id  WHERE p.product_id=? ";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, productId);
			ResultSet resultSet = statement.executeQuery();
			// loop the result set
			while (resultSet.next()) {
				int length = resultSet.getInt("length");
				int width = resultSet.getInt("width");
				int height = resultSet.getInt("height");
				dimensionList.add(new Dimension(length, width, height));

			}

		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return dimensionList;
	}

	@Override
	public List<Dimension> getAll() {
		return null;
	}
	public int getLastId(){
		String sql = "SELECT LAST_INSERT_ID() AS lastId FROM dimension";
		int lastId = 0;
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)){
			ResultSet resultSet = statement.executeQuery();
			lastId = resultSet.getInt("lastId");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return lastId;
	}
	@Override
	public void create(Dimension dimension) {
		String sql = "INSERT INTO dimension(length, width, height) VALUES(?,?,?)";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setDouble(1,dimension.getLength());
			statement.setDouble(2,dimension.getWidth());
			statement.setDouble(3,dimension.getHeight());
			statement.execute();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public Dimension update(Dimension dimension) {
		return null;
	}

	@Override
	public void delete(int id) {

	}
}
