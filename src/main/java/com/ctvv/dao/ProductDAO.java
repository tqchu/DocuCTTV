package com.ctvv.dao;

import com.ctvv.model.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO
		extends GenericDAO<Product> {
	DimensionDAO dimensionDAO;
	MaterialDAO materialDAO;
	ImagePathDAO imagePathDAO;
	private CategoryDAO categoryDAO;

	public ProductDAO(DataSource dataSource) {
		super(dataSource);
		categoryDAO = new CategoryDAO(dataSource);
		dimensionDAO = new DimensionDAO(dataSource);
		materialDAO = new MaterialDAO(dataSource);
		imagePathDAO = new ImagePathDAO(dataSource);
	}

	@Override
	public Product get(int id) {
		String sql = "SELECT * FROM product LIMIT 1";
		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement =
				connection.prepareStatement(sql);) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				boolean status = resultSet.getBoolean("status");
				// Nếu sản phẩm còn sử dụng
				if (status) {
					int productId = resultSet.getInt("product_id");
					String productName = resultSet.getString("product_name");
					int warrantyPeriod = resultSet.getInt("warranty_period");
					int quantity = resultSet.getInt("quantity");
					String description = resultSet.getString("description");
					int price = resultSet.getInt("price");

					int categoryId = resultSet.getInt("category_id");

					Category category = categoryDAO.get(categoryId);
					List<Dimension> dimensionList = dimensionDAO.getGroup(productId);
					List<Material> materialList = materialDAO.getGroup(productId);
					List<ImagePath> imagePathList = imagePathDAO.getGroup(productId);
					return new Product(id, productName, warrantyPeriod, quantity, description, price, true, category,
							dimensionList,
							materialList, imagePathList);
				}

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
				boolean status = resultSet.getBoolean("status");
				// Sản phẩm còn kinh doanh
				if (status) {
					int productId = resultSet.getInt("product_id");
					String productName = resultSet.getString("product_name");
					int warrantyPeriod = resultSet.getInt("warranty_period");
					int quantity = resultSet.getInt("quantity");
					int price = resultSet.getInt("price");
					String description = resultSet.getString("description");
					int categoryId = resultSet.getInt("category_id");

					Category category = categoryDAO.get(categoryId);
					List<Dimension> dimensionList = dimensionDAO.getGroup(productId);
					List<Material> materialList = materialDAO.getGroup(productId);
					List<ImagePath> imagePathList = imagePathDAO.getGroup(productId);
					productList.add(new Product(productId, productName, warrantyPeriod, quantity, description, price,
							true,
							category,
							dimensionList,
							materialList, imagePathList));
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public int getLastId(){
		int lastId = 0;
		String sql ="SELECT LAST_INSERT_ID() AS lastId FROM product";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
		){
			ResultSet resultSet = statement.executeQuery();
			lastId = resultSet.getInt("lastId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastId;
	}
	@Override
	public void create(Product product) throws SQLException {
		String sql = "INSERT INTO product(product_name, warranty_period, quantity, description, category_id,price,product_status) VALUES(?,?,?,?,?,?,?)";
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = null;
		try
		{
			statement = connection.prepareStatement(sql);
			statement.setString(1, product.getName());
			statement.setInt(2, product.getWarrantyPeriod());
			statement.setInt(3, product.getQuantity());
			statement.setString(4, product.getDescription());
			statement.setInt(5, product.getCategory().getCategoryId());
			statement.setDouble(6,product.getPrice());
			statement.setBoolean(7,product.isStatus());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int lastProductId = this.getLastId();
		try{
			List<Dimension> dimensionList = product.getDimensionList();
			List<Material> materialList = product.getMaterialList();
			List<ImagePath> imagePathList = product.getImagePathList();
			int lastDimensionId = dimensionDAO.getLastId();
			int lastMaterialId = materialDAO.getLastId();
			for (int i = 0; i < dimensionList.size(); i++) {
				dimensionDAO.create(dimensionList.get(i));
				statement = connection.prepareStatement("INSERT INTO product_dimension VALUES(?,?)");
				statement.setInt(1, lastProductId);
				statement.setInt(2, lastDimensionId+1);
				statement.execute();
			}
			for (int i = 0; i< materialList.size();i++){
				materialDAO.create(materialList.get(i));
				statement = connection.prepareStatement("INSERT INTO product_material VALUES(?,?)");
				statement.setInt(1,lastProductId);
				statement.setInt(2,lastMaterialId+1);
				statement.execute();
			}
			for (int i = 0; i<imagePathList.size();i++){
				String imagePath = imagePathList.get(i).getPath();
				imagePathList.set(i,new ImagePath(lastProductId,imagePath));
				imagePathDAO.create(imagePathList.get(i));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public Product update(Product product) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

}
