package com.ctvv.dao;

import com.ctvv.model.ImportDetail;
import com.ctvv.model.ImportDetail;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImportDetailDAO
		extends GenericDAO<ImportDetail> {
	public ImportDetailDAO(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public ImportDetail get(int id) {
		return null;
	}

	@Override
	public List<ImportDetail> getAll() {
		return null;
	}

	@Override
	public ImportDetail create(ImportDetail importDetail) {
		return null;
	}

	@Override
	public ImportDetail update(ImportDetail importDetail) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public ImportDetail map(ResultSet resultSet) {
		try {
			int importId = resultSet.getInt("import_id");
			int productId = resultSet.getInt("product_id");
			String productName = resultSet.getString("product_name");
			int quantity = resultSet.getInt("quantity");
			int price = resultSet.getInt("price");
			double tax = resultSet.getDouble("tax");
			return new ImportDetail(importId, productId, productName, quantity, price, tax);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<ImportDetail> getGroup(int importId) {
		List<ImportDetail> importDetailList = new ArrayList<>();
		String sql = "SELECT * FROM import_detail WHERE import_id=? ";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, importId);
			ResultSet resultSet = statement.executeQuery();
			// loop the result set
			while (resultSet.next()) {
				importDetailList.add(map(resultSet));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return importDetailList;
	}
}
