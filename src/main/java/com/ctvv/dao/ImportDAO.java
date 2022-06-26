package com.ctvv.dao;

import com.ctvv.model.Category;
import com.ctvv.model.Import;
import com.ctvv.model.ImportDetail;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportDAO
		implements GenericDAO<Import> {
	private ImportDetailDAO importDetailDAO;

	public ImportDAO() {
		importDetailDAO = new ImportDetailDAO();
	}

	@Override
	public Import get(int id) {
		String sql = "SELECT * FROM import WHERE import_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Import> getAll() {
		return null;
	}

	@Override
	public Import create(Import pImport) {
		String sql = "INSERT INTO import(importer_name, provider_id, provider_tax_id,provider_name, import_date) " +
				"VALUES (?,?,?,?,?)";

		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){

			connection.setAutoCommit(false);
			statement.setString(1, pImport.getImporterName());
			statement.setInt(2, pImport.getProviderId());
			statement.setString(3, pImport.getProviderTaxId());
			statement.setString(4, pImport.getProviderName());
			statement.setTimestamp(5, Timestamp.valueOf(pImport.getImportDate()));
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			while (resultSet.next()) {
				int importId = resultSet.getInt(1);
				pImport.setImportId(importId);
			}
			connection.commit();
			resultSet.close();
			// IMPORT DETAIL
			for (ImportDetail importDetail : pImport.getImportDetailList()) {
				importDetail.setImportId(pImport.getImportId());
				importDetailDAO.create(importDetail);
			}
			return pImport;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Import update(Import anImport) {
		return null;
	}

	@Override
	public void delete(int id) {
		/*String sql = "DELETE  FROM import WHERE import_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
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
			int importId = resultSet.getInt("import_id");
			String importerName = resultSet.getString("importer_name");
			int providerId = resultSet.getInt("provider_id");
			String providerTaxId = resultSet.getString("provider_tax_id");
			String providerName = resultSet.getString("provider_name");
			LocalDateTime importDate = resultSet.getTimestamp("import_date").toLocalDateTime();
			List<ImportDetail> importDetailList = importDetailDAO.getGroup(importId);
			long totalPrice = totalPrice(importDetailList);

			return new Import(importId, importerName, providerId,providerTaxId, providerName, importDate, totalPrice,
					importDetailList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public long totalPrice(List<ImportDetail> importDetailList) {
		long totalPrice = 0;
		for (ImportDetail importDetail : importDetailList) {
			totalPrice += (long) importDetail.getPrice() * importDetail.getQuantity() * (1 - importDetail.getTax());
		}
		return totalPrice;
	}

	public List<Import> get(int begin, int numberOfRec, String keyword, String sortBy, String order) {
		if (order == null) order = "ASC";
		List<Import> importList = new ArrayList<>();
		String sql =
				"SELECT * FROM import " +
						(keyword != null ? " WHERE provider_name LIKE '%" + keyword + "%' " : "") +
						(sortBy != null ? "ORDER BY " + sortBy + " " + order : "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				importList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return importList;
	}

	public List<Import> get(
			int begin, int numberOfRec, String keyword, LocalDateTime from, LocalDateTime to, String sortBy,
			String order) {
		boolean isSearch = (keyword != null) || (from != null) || (to != null);
		if (from == null) from = LocalDateTime.of(1000, 1, 1, 0, 0, 0);
		if (to == null) to = LocalDateTime.of(3000, 12, 31, 0, 0, 0);
		if (order == null) order = "ASC";

		List<Import> importList = new ArrayList<>();
		String sql =
				"SELECT * FROM import " +
						(isSearch ?
								" WHERE " + (keyword != null ? "( provider_name LIKE '%" + keyword + "%' OR " +
										"importer_name  LIKE '%" + keyword + "%' OR " + "import_id  LIKE '%" + keyword +
										"%')" : "") +
										((keyword != null ? " AND" : "") + " import_date BETWEEN '" + from + "' AND '" + to + "'")
								: "") +
						(sortBy != null ? "ORDER BY " + sortBy + " " + order : "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				importList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return importList;
	}

	public int count(String keyword, LocalDateTime from, LocalDateTime to) {
		int count = 0;
		boolean isSearch = (keyword != null) || (from != null) || (to != null);
		if (from == null) from = LocalDateTime.of(1000, 1, 1, 0, 0, 0);
		if (to == null) to = LocalDateTime.of(3000, 12, 31, 0, 0, 0);
		String sql =
				"SELECT COUNT(import_id) AS no FROM import " +
						(isSearch ?
								" WHERE " + (keyword != null ? "( provider_name LIKE '%" + keyword + "%' OR " +
										"importer_name  LIKE '%" + keyword + "%' OR " + "import_id  LIKE '%" + keyword +
										"%')" : "") +
										((keyword != null ? " AND" : "") + " import_date BETWEEN '" + from + "' AND '" + to + "'")
								: "");
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("no");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
