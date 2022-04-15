package com.ctvv.dao;

import com.ctvv.model.Dimension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DimensionDAO extends GenericDAO<Dimension> {
    public DimensionDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Dimension get(int id) {
        return null;
    }

    @Override
    public List<Dimension> getAll() {
        return null;
    }

    @Override
    public void create(Dimension dimension) throws SQLException {
        String sql = "INSERT INTO dimension(length, width, height) VALUES(?,?,?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDouble(1,dimension.getLength());
            statement.setDouble(2,dimension.getWidth());
            statement.setDouble(3,dimension.getHeight());
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Dimension update(Dimension dimension) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
