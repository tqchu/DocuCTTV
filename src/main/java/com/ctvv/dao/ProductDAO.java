package com.ctvv.dao;

import com.ctvv.model.Product;
import com.ctvv.model.Dimension;
import
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDAO extends GenericDAO<Product>{
    private CategoryDAO categoryDAO;
    DimensionDAO dimensionDAO;
    MaterialDAO materialDAO;
    ImagePathDAO imagePathDAO;

    public ProductDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Product get(int id) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public void create(Product product) throws SQLException {
        String sql = "INSERT INTO product(product_name, warranty_period, quantity, description, category_id) VALUES(?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
                connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getWarrantyPeriod());
            statement.setInt(3, product.getQuantity());
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getCategory().getCategoryId());
            statement.execute();
            List<Dimension> dimensionList = product.getDimensionList();
            for (int i = 0;i< dimensionList.size();i++){
                dimensionDAO.create(dimensionList.get(i));

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Product update(Product product) {

    }

    @Override
    public void delete(int id) {

    }
}
