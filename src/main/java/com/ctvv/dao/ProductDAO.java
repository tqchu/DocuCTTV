package com.ctvv.dao;

import com.ctvv.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO
		extends GenericDAO<Product> {
	private ProductPriceDAO productPriceDAO;
	private ImagePathDAO imagePathDAO;
	private CategoryDAO categoryDAO;

	public ProductDAO(DataSource dataSource) {
		super(dataSource);
		categoryDAO = new CategoryDAO(dataSource);
		productPriceDAO = new ProductPriceDAO(dataSource);
		imagePathDAO = new ImagePathDAO(dataSource);
	}

	@Override
	public Product get(int id) {
		String sql = "SELECT * FROM product WHERE product_id=?";
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				return map(resultSet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getAll() {
		List<Product> productList = new ArrayList<>();
		String sql = "SELECT * FROM product";
		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement =
				connection.prepareStatement(sql);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				productList.add(map(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public Product create(Product product) {
		String sql = "INSERT INTO product(product_name, warranty_period, description, category_id) VALUES (?,?,?,?)";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			connection.setAutoCommit(false);
			statement.setString(1, product.getName());
			statement.setInt(2, product.getWarrantyPeriod());
			statement.setString(3, product.getDescription());
			if (product.getCategory() == null) {
				statement.setNull(4, Types.INTEGER);
			} else
				statement.setInt(4, product.getCategory().getCategoryId());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			while (resultSet.next()) {
				int productId = resultSet.getInt(1);
				product.setProductId(productId);
			}
			connection.commit();
			resultSet.close();
			return product;

		} catch (SQLException e) {
			try {
				if (connection != null) connection.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	@Override
	public Product update(Product product) {
		String sql = "UPDATE product SET product_name=?, warranty_period=?,  description=?, "+
				"category_id=? WHERE product_id=?";
		try (Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, product.getName());
			statement.setInt(2, product.getWarrantyPeriod());
			statement.setString(3, product.getDescription());
			if (product.getCategory() == null) {
				statement.setNull(4, Types.INTEGER);
			} else
				statement.setInt(4, product.getCategory().getCategoryId());
			statement.setInt(5, product.getProductId());
			statement.executeUpdate();
			return product;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public Product map(ResultSet resultSet) {
		try {
			int productId = resultSet.getInt("product_id");
			String productName = resultSet.getString("product_name");
			int warrantyPeriod = resultSet.getInt("warranty_period");
			String description = resultSet.getString("description");
			int categoryId = resultSet.getInt("category_id");
			Category category = categoryDAO.get(categoryId);
			List<ImagePath> imagePathList = imagePathDAO.getGroup(productId);
			List<ProductPrice> productPriceList = productPriceDAO.getGroup(productId);

			return new Product(productId, productName, warrantyPeriod, description,
					category, imagePathList, productPriceList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}


	public List<Product> getAllByCategory(int categoryId) {
		List<Product> productList = new ArrayList<>();
		String sql = "SELECT * FROM product WHERE category_id=?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, categoryId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				productList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public List<Product> search(String keyword) {
		List<Product> productList = new ArrayList<>();
		String sql = "SELECT * FROM product WHERE product_name LIKE ?";
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "%"+keyword + "%");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				productList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;

	}
}
