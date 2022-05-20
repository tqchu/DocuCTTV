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
		return null;
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
		String sql = "SELECT res1.product_id, res1.product_name, res1.sum1 - IFNULL(res2.sum2,0) as quantity" +
				" FROM " +
				" (SELECT product.*, IFNULL(SUM(quantity),0) as sum1" +
				" FROM product LEFT JOIN import_detail ON product.product_id = import_detail.product_id" +
				" GROUP BY product.product_id) res1" +
				" LEFT JOIN " +
				" (SELECT order_detail.product_id, SUM(quantity) as sum2" +
				" FROM order_detail JOIN customer_order ON order_detail.order_id = customer_order.order_id" +
				" WHERE customer_order.order_status <> 'canceled'" +
				" GROUP BY order_detail.product_id) res2" +
				" ON res1.product_id = res2.product_id"
				+ (keyword!=null? " WHERE product_name LIKE '%"+keyword+"%'":"")
				+ (sortBy != null ? " ORDER BY " + sortBy + " " + order : "")
				+ " LIMIT "+begin+", "+numberOfRecords;
		try(Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql)){
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				int productId = resultSet.getInt("product_id");
				String productName = resultSet.getString("product_name");
				int quantity = resultSet.getInt("quantity");
				stockItemList.add(new StockItem(productId,productName,quantity));
			}
			return stockItemList;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
