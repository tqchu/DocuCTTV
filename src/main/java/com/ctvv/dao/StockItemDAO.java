package com.ctvv.dao;

import com.ctvv.model.Product;
import com.ctvv.model.StockItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
		String soldQuantitySql = "SELECT SUM(quantity) AS sum FROM order_detail JOIN customer_order " +
				"WHERE product_id ='"+id +"' AND order_status != 'canceled'";
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

	public List<StockItem> get(int begin, int numberOfRecords, String keyword, String sortBy, String order){
		List<StockItem> stockItemList = new ArrayList<>();
		String sql = "SELECT product_id FROM product "+(keyword!=null? " WHERE product_name LIKE '%"+keyword+"%'":"")
				+(sortBy != null ? " ORDER BY " + sortBy + " " + order : "")
				+ " LIMIT "+begin+", "+numberOfRecords;
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)){
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				int productId = resultSet.getInt("product_id");
				stockItemList.add(get(productId));
			}
			return stockItemList;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
