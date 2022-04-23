package com.ctvv.dao;

import com.ctvv.model.Import;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ImportDAO
		extends GenericDAO<Import> {

	public ImportDAO(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Import get(int id) {
		return null;
	}

	@Override
	public List<Import> getAll() {
		return null;
	}
	public List<Import> getGroup(int id){
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
	public Import create(Import pImport) {
		String sql = "INSERT INTO import VALUES(?,?,?,?)";
		try(Connection connection = dataSource.getConnection();
		    PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1,pImport.getProductId());
			statement.setInt(2,pImport.getPrice());
			statement.setDate(3,Date.valueOf(pImport.getImportDay()));
			statement.setInt(4,pImport.getQuantity());
			statement.execute();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return pImport ;
	}

	@Override
	public Import update(Import anImport) {
		return null;
	}

	@Override
	public void delete(int id) {
		/*String sql = "DELETE  FROM import WHERE import_id=?";
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public Import map(ResultSet resultSet) {
		try {
			int productId = resultSet.getInt("product_id");
			int price = resultSet.getInt("import_price");
			int quantity = resultSet.getInt("quantity");
			LocalDate importDay = resultSet.getDate("import_day").toLocalDate();
			return new Import(productId, price, importDay, quantity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
}
