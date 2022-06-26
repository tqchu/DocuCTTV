package com.ctvv.dao;

import com.ctvv.model.Product;
import com.ctvv.model.StatsProduct;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StatsProductDAO
        implements GenericDAO<StatsProduct> {
    private ProductDAO productDAO;
    public StatsProductDAO() {
        productDAO = new ProductDAO();
    }

    @Override
    public StatsProduct get(int id) {
        return null;
    }

    @Override
    public List<StatsProduct> getAll() {
        return null;
    }

    @Override
    public StatsProduct create(StatsProduct statsProduct) {
        return null;
    }

    @Override
    public StatsProduct update(StatsProduct statsProduct) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
    public List<StatsProduct> getAll(LocalDateTime start, LocalDateTime end){
        List<StatsProduct> statsProductList = new ArrayList<>();
        String sql = "SELECT result2.product_id, import_quantity, " +
                "IFNULL(sold_quantity,0) AS sold_quantity, IFNULL(revenue,0) AS revenue " +
                "FROM " +
                "(SELECT product.product_id, IFNULL(import_quantity,0) AS import_quantity " +
                "FROM product LEFT JOIN " +
                "(SELECT product_id, SUM(quantity) AS import_quantity " +
                "FROM import JOIN import_detail ON import.import_id = import_detail.import_id " +
                "WHERE import_date BETWEEN ? AND ? " +
                "GROUP BY product_id) result1 " +
                "ON product.product_id = result1.product_id) as result2 " +
                "LEFT JOIN " +
                "(SELECT product_id, SUM(quantity) AS sold_quantity, SUM(quantity*price) AS revenue " +
                "FROM customer_order JOIN order_detail ON customer_order.order_id = order_detail.order_id " +
                "WHERE order_status = 'completed' AND completed_time BETWEEN ? AND ? " +
                "GROUP BY product_id) result3 " +
                "ON result2.product_id = result3.product_id";
        try(Connection connection = DataSourceHelper.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            statement.setTimestamp(3, Timestamp.valueOf(start));
            statement.setTimestamp(4, Timestamp.valueOf(end));
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                statsProductList.add(map(resultSet));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return statsProductList;
    }
    @Override
    public StatsProduct map(ResultSet resultSet) {
        try{
            int productId = resultSet.getInt("product_id");
            int importQuantity = resultSet.getInt("import_quantity");
            int soldQuantity = resultSet.getInt("sold_quantity");
            long revenue = resultSet.getLong("revenue");
            Product product = productDAO.get(productId);
            return new StatsProduct(product, importQuantity, soldQuantity, revenue);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
