package com.ctvv.dao;

import com.ctvv.model.Product;
import com.ctvv.model.StockItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class StockItemDAO
		extends GenericDAO<StockItem> {
	private ProductDAO productDAO;

	public StockItemDAO(DataSource dataSource) {
		super(dataSource);
		productDAO = new ProductDAO(dataSource);
	}

	@Override
	public StockItem get(int id) {

		StockItem stockItem = new StockItem();
		stockItem.setProductId(id);
		Product product = productDAO.get(id);

		String importedQuantitySql = "SELECT SUM(quantity) AS sum FROM import_detail WHERE product_id = " + id;
		String soldQuantitySql = "SELECT SUM(quantity) AS sum FROM order_detail WHERE product_id =  " + id;
		try (Connection connection = dataSource.getConnection();
		     Statement statement = connection.createStatement();
		) {
			ResultSet resultSet = statement.executeQuery(importedQuantitySql);
			resultSet.next();
			int importedQuantity = resultSet.getInt("sum");
			resultSet = statement.executeQuery(soldQuantitySql);
			resultSet.next();
			int soldQuantity = resultSet.getInt("sum");
			stockItem.setQuantity(importedQuantity - soldQuantity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stockItem.setProductName(product.getName());
		return stockItem;
	}

	@Override
	public List<StockItem> getAll() {
		return null;
	}

	@Override
	public StockItem create(StockItem stockItem) {
		return null;
	}

	@Override
	public StockItem update(StockItem stockItem) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public StockItem map(ResultSet resultSet) {
		return null;
	}
}
