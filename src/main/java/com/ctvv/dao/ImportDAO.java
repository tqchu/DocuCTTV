package com.ctvv.dao;
import com.ctvv.model.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ImportDAO extends GenericDAO<Import>{
    public ImportDAO(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Import get(int id) {

        return null;
    }
    public  List<Import> getGroup(int id){
        List<Import> importList= new ArrayList<>();
        String sql = "SELECT * FROM import WHERE product_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int price = resultSet.getInt("import_price");
                LocalDate date = resultSet.getDate("import_day").toLocalDate();
                int quantity = resultSet.getInt("quantity");
                importList.add(new Import(id,price,date,quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return importList;
    }

    @Override
    public List<Import> getAll() {
        return null;
    }

    @Override
    public void create(Import anImport) throws SQLException {
        String sql = "INSERT INTO import VALUES (?, ?, ? )";
    }

    @Override
    public Import update(Import anImport) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
