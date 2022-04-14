package com.ctvv.dao;

import com.ctvv.model.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO
		extends GenericDAO<Category> {

	public CategoryDAO(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Category get(int id) {
		String sql = "SELECT * FROM category WHERE category_id=? ";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String categoryName = resultSet.getString("category_name");
				return new Category(id, categoryName);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Category> getAll() {
		List<Category> categoryList = new ArrayList<>();

		String sql = "SELECT * FROM category";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("category_id");
				String categoryName = resultSet.getString("category_name");
				categoryList.add(new Category(id, categoryName));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return categoryList;
	}

	@Override
	public void create(Category category) {
		Connection connection = null;
		String sql = "INSERT INTO category(category_name) VALUES (?)";
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, category.getCategoryName());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try{
				if(statement != null) statement.close();
				if(connection != null)	statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Category update(Category category) {
		return null;
	}

	@Override
	public void delete(int id) {

	}
}
