package com.ctvv.dao;

import com.ctvv.model.Category;
import com.ctvv.model.Dimension;
import com.ctvv.model.Import;

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
	public Dimension create(Category category) {
		String sql = "INSERT INTO category(category_name) VALUES(?)";
		try (Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, category.getCategoryName());
			statement.execute();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
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
		String categoryName = category.getCategoryName();
		int categoryId = category.getCategoryId();

		String sql = "UPDATE category SET category_name=? WHERE category_id=?";
		try( Connection connection = dataSource.getConnection();
		     PreparedStatement statement =connection.prepareStatement(sql)) {
			statement.setString(1, categoryName);
			statement.setInt(2,categoryId);
			statement.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return category;
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE  FROM category WHERE category_id=?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.execute();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public Import map(ResultSet resultSet) {
		return null;
	}
}
