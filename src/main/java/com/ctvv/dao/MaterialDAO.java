package com.ctvv.dao;

import com.ctvv.model.Material;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MaterialDAO extends GenericDAO<Material> {
    public MaterialDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Material get(int id) {
        return null;
    }

    @Override
    public List<Material> getAll() {
        return null;
    }

    @Override
    public void create(Material material) throws SQLException {
        String sql = "INSERT INTO material(material_name) VALUES(?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,material.getMaterialName());
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Material update(Material material) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
