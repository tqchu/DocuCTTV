package com.ctvv.dao;

import com.ctvv.model.Dimension;
import com.ctvv.model.Material;
import com.ctvv.model.ProductDetail;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class ProductDetailDAO
		extends GenericDAO<ProductDetail> {
	private MaterialDAO materialDAO;
	private DimensionDAO dimensionDAO;

	public ProductDetailDAO(DataSource dataSource) {
		super(dataSource);
		materialDAO = new MaterialDAO(dataSource);
		dimensionDAO = new DimensionDAO(dataSource);
	}

	@Override
	public ProductDetail get(int productDetailId) {
		String sql = "SELECT * FROM product_detail WHERE product_detail_id=?";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, productDetailId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductDetail> getAll() {
		return null;
	}

	@Override
	public ProductDetail create(ProductDetail productDetail) {
		String sql = "INSERT INTO  product_detail(product_id, dimension_id, material_id) VALUES (?,?,?)";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			statement.setInt(1, productDetail.getProductId());
			statement.setInt(2, productDetail.getDimension().getDimensionId());
			statement.setInt(3, productDetail.getMaterial().getMaterialId());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			while (resultSet.next())
				productDetail.setProductDetailId(resultSet.getInt(1));
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productDetail;
	}

	@Override
	public ProductDetail update(ProductDetail productDetail) {
		return null;
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM product_detail WHERE product_id = ?";
		try(Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1,id);
			statement.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public ProductDetail map(ResultSet resultSet) {
		try {
			int productDetailId = resultSet.getInt(1);
			int productId = resultSet.getInt("product_id");
			Material material = materialDAO.get(resultSet.getInt("material_id"));
			Dimension dimension = dimensionDAO.get(resultSet.getInt("dimension_id"));
			return new ProductDetail(productDetailId, productId, material, dimension);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
