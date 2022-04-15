package com.ctvv.dao;

import com.ctvv.model.ImagePath;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ImagePathDAO extends GenericDAO<ImagePath> {
    public ImagePathDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ImagePath get(int id) {
        return null;
    }

    @Override
    public List<ImagePath> getAll() {
        return null;
    }

    @Override
    public void create(ImagePath imagePath) throws SQLException {
        String sql = "INSERT INTO image VALUES(?,?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,imagePath.getProductId());
            statement.setString(2,imagePath.getPath());
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ImagePath update(ImagePath imagePath) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
