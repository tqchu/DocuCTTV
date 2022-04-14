package com.ctvv.dao;
import com.ctvv.model.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private final DataSource dataSource;
    public CategoryDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public List<Category> getListCategory() throws SQLException {
        List<Category> data = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try(Connection connection =dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql) ){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int categoryID = resultSet.getInt("category_id");
                String categoryName = resultSet.getString("category_name");
                Category category = new Category(categoryID,categoryName);
                data.add(category);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }
    public void createCategory(String categoryName) throws SQLException {
        String sql = "INSERT INTO category(category_name) VALUES (?)";
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categoryName);
            statement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}