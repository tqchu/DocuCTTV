package com.ctvv.dao;

import com.ctvv.model.Category;
import com.ctvv.model.ImagePath;
import com.ctvv.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO
		extends GenericDAO<Product> {
	private final ImagePathDAO imagePathDAO;
	private final CategoryDAO categoryDAO;

	public ProductDAO(DataSource dataSource) {
		super(dataSource);
		categoryDAO = new CategoryDAO(dataSource);
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
		String sql = "SELECT * FROM product ";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
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
		String sql = "INSERT INTO product(product_name, warranty_period, description,dimension,material,price," +
				"category_id) VALUES (?,?,?,?,?,?,?)";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			connection.setAutoCommit(false);
			statement.setString(1, product.getName());
			statement.setInt(2, product.getWarrantyPeriod());
			statement.setString(3, product.getDescription());
			statement.setString(4, product.getDimension());
			statement.setString(5, product.getMaterial());
			statement.setInt(6, product.getPrice());
			if (product.getCategory() == null) {
				statement.setNull(7, Types.INTEGER);
			} else
				statement.setInt(7, product.getCategory().getCategoryId());
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
		String sql = "UPDATE product SET product_name=?, warranty_period=?,  description=?, " +
				"dimension = ?, material = ?, price = ?, category_id=? WHERE product_id=?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, product.getName());
			statement.setInt(2, product.getWarrantyPeriod());
			statement.setString(3, product.getDescription());
			statement.setString(4, product.getDimension());
			statement.setString(5, product.getMaterial());
			statement.setInt(6, product.getPrice());
			if (product.getCategory() == null) {
				statement.setNull(7, Types.INTEGER);
			} else
				statement.setInt(7, product.getCategory().getCategoryId());
			statement.setInt(8, product.getProductId());
			statement.executeUpdate();
			return product;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(int productId) {
		String sql = "DELETE FROM product WHERE product_id = ?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, productId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Product map(ResultSet resultSet) {
		try {
			int productId = resultSet.getInt("product_id");
			String productName = resultSet.getString("product_name");
			int warrantyPeriod = resultSet.getInt("warranty_period");
			String material = resultSet.getString("material");
			String dimension = resultSet.getString("dimension");
			String description = resultSet.getString("description");
			int price = resultSet.getInt("price");
			int categoryId = resultSet.getInt("category_id");
			Category category = categoryDAO.get(categoryId);
			List<ImagePath> imagePathList = imagePathDAO.getGroup(productId);

			return new Product(productId, productName, warrantyPeriod, material, dimension, description,
					price, category, imagePathList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Product> get(int begin, int numberOfRec, String keyword, String field, String sortBy, String order) {
		if (order == null) order = "ASC";
		if (field == null) field = "product_name";
		List<Product> productList = new ArrayList<>();
		String sql =
				"SELECT * FROM product " +
						(keyword != null ? " WHERE " + field + " LIKE '%" + keyword + "%' " : "") +
						(sortBy != null ? "ORDER BY " + sortBy + " " + order : "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
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
	public List<Product> getAllByCategory(int categoryId, String sortBy, String order, int begin, int numberOfRec) {
		List<Product> productList = new ArrayList<>();
		String sql = "SELECT * FROM product WHERE category_id=? "+(sortBy!=null? "ORDER BY "+sortBy+" "+order : "")
				+ " LIMIT "+begin+","+numberOfRec;
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

	public int count(String keyword, String field) {
		int count = 0;
		if (field == null) field = "product_name";
		List<Product> productList = new ArrayList<>();
		String sql =
				"SELECT COUNT(product_id) AS no FROM product " +
						(keyword != null ? " WHERE " + field + " LIKE '%" + keyword + "%' " : "");
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("no");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Product> get(
			int begin, int numberOfRec, String keyword, int minPrice, int maxPrice, String sortBy,
			String order) {
		if (order == null) order = "ASC";
		List<Product> productList = new ArrayList<>();
		String sql =
				"SELECT * FROM product " +
						(keyword != null ?
								" WHERE (product_name  LIKE '%" + keyword +
										"%' OR description  LIKE '%" + keyword +
										"%' OR material  LIKE '%" + keyword + "' ) " +
										" AND (price BETWEEN " + minPrice + " AND " + maxPrice + ")"
								: "") +
						(sortBy != null ? " ORDER BY " + sortBy + " " + order : "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
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

	public int count(String keyword, int minPrice, int maxPrice) {
		int count = 0;
		String sql =
				"SELECT COUNT(product_id) AS no FROM product " +
						(keyword != null ?
								" WHERE (product_name  LIKE '%" + keyword +
										"%' OR description  LIKE '%" + keyword +
										"%' OR material  LIKE '%" + keyword + "' ) " +
										" AND (price BETWEEN " + minPrice + " AND " + maxPrice + ")"
								: "");
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("no");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
  }

	public Product findByName (String name)
	{
		String sql = "SELECT * FROM product WHERE product_name = ?";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);)
		{
			ResultSet resultSet;
			statement.setString(1,name);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				return map(resultSet);
			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
