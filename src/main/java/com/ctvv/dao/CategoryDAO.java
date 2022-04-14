package com.ctvv.dao;

import com.ctvv.model.Category;
import com.ctvv.model.Category;

import javax.sql.DataSource;
import java.sql.*;
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
		String sql = "INSERT INTO category(category_name) VALUES(?)";
		try (Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, category.getCategoryName());
			statement.execute();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	public Category find(String categoryName) {
		String sql = "SELECT * FROM category WHERE category_name = ?";
		Category category = null;
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql); ) {
			ResultSet resultSet;
			statement.setString(1,categoryName);
			resultSet=statement.executeQuery();
			while(resultSet.next()){
				String category_name = resultSet.getString("category_name");
				category = new Category(category_name);
			}
			resultSet.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return category;
	}
	@Override
	public Category update(Category category) {
		return null;
	}

	@Override
	public void delete(int id) {

	}
}
