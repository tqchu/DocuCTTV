package com.ctvv.dao;

import com.ctvv.model.Dimension;
import com.ctvv.model.Material;
import com.ctvv.model.ProductDetail;
import com.ctvv.model.ProductPrice;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductPriceDAO
		extends GenericDAO<ProductPrice> {

	private final ProductDetailDAO productDetailDAO;

	public ProductPriceDAO(DataSource dataSource) {
		super(dataSource);
		productDetailDAO = new ProductDetailDAO(dataSource);
	}

	@Override
	public ProductPrice get(int id) {
		return null;
	}

	@Override
	public List<ProductPrice> getAll() {
		return null;
	}

	@Override
	public ProductPrice create(ProductPrice productPrice) {
		String sql = "INSERT INTO product_price VALUES (?,?)";
		try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement =
				connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, productPrice.getProductDetail().getProductDetailId());
			preparedStatement.setInt(2, productPrice.getPrice());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ProductPrice update(ProductPrice productPrice) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public ProductPrice map(ResultSet resultSet) {
		try {
			int productDetailId = resultSet.getInt("product_detail_id");
			ProductDetail productDetail = productDetailDAO.get(productDetailId);
			int price = resultSet.getInt("price");
			return new ProductPrice(productDetail, price);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ProductPrice> getGroup(int productId) {
		List<ProductPrice> productPriceList = new ArrayList<>();
		String sql = "SELECT product_price.* FROM product_price JOIN product_detail ON product_price.product_detail_id" +
				" = " +
				"product_detail.product_detail_id WHERE product_detail.product_id=?";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, productId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				productPriceList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productPriceList;
	}
}
