package com.ctvv.dao;

import com.ctvv.model.Category;
import com.ctvv.model.Import;
import com.ctvv.model.Provider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProviderDAO

		implements GenericDAO<Provider> {

	@Override
	public Provider get(int id) {
		String sql = "SELECT * FROM provider WHERE provider_id = ?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Provider> getAll() {
		List<Provider> providerList = new ArrayList<>();
		String sql = "SELECT * FROM provider";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				providerList.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return providerList;
	}

	@Override
	public Provider create(Provider provider) {
		String sql = "INSERT INTO provider(provider_name, provider_address,phone_number,email,tax_id_number) VALUES " +
				"(?,?,?,?,?)";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, provider.getProviderName());
			statement.setString(2, provider.getAddress());
			statement.setString(3, provider.getPhoneNumber());
			statement.setString(4, provider.getEmail());
			statement.setString(5, provider.getTaxId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return provider;
	}

	@Override
	public Provider update(Provider provider) {
		String sql = "UPDATE provider SET provider_name = ?, provider_address = ?, phone_number = ?, email = ?, " +
				"tax_id_number = ? WHERE provider_id = ?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, provider.getProviderName());
			statement.setString(2, provider.getAddress());
			statement.setString(3, provider.getPhoneNumber());
			statement.setString(4, provider.getEmail());
			statement.setString(5, provider.getTaxId());
			statement.setInt(6, provider.getProviderId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return provider;
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM provider WHERE provider_id = ?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Provider map(ResultSet resultSet) {
		int providerId;
		try {
			providerId = resultSet.getInt("provider_id");
			String providerName = resultSet.getString("provider_name");
			String providerAddress = resultSet.getString("provider_address");
			String phoneNumber = resultSet.getString("phone_number");
			String email = resultSet.getString("email");
			String taxIdNumber = resultSet.getString("tax_id_number");
			return new Provider(providerId, providerName, providerAddress, phoneNumber, email, taxIdNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Provider> get(int begin, int numberOfRec, String keyword, String sortBy, String order) {
		if (order == null) order = "ASC";
		List<Provider> providerList = new ArrayList<>();
		String sql =
				"SELECT * FROM provider " +
						(keyword != null ? " WHERE provider_name LIKE '%" + keyword + "%' " : "") +
						(sortBy != null ? "ORDER BY " + sortBy + " " + order : "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				providerList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return providerList;
	}

	public int count(String keyword) {
		int count = 0;
		List<Provider> providerList = new ArrayList<>();
		String sql =
				"SELECT COUNT(provider_id) AS no FROM provider " +
						(keyword != null ? " WHERE provider_name" +  " LIKE '%" + keyword + "%' " : "");
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

	public Provider findByName(String name) {
		String sql = "SELECT * FROM provider WHERE provider_name = ?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql);) {
			ResultSet resultSet;
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Provider findByEmail(String email) {
		String sql = "SELECT * FROM provider where email=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Provider findByPhoneNumber(String phoneNumber) {
		String sql = "SELECT * FROM provider WHERE phone_number = ?";
		Provider provider = null;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql);) {
			ResultSet resultSet;
			statement.setString(1, phoneNumber);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Provider findByTaxId(String taxId) {
		String sql = "SELECT * FROM provider where tax_id_number=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, taxId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<Provider> getAll(String sortBy, String order) {
		List<Provider> providerList = new ArrayList<>();
		String sql = "SELECT * FROM provider" +
				(sortBy != null ? " ORDER BY " + sortBy + " " + order : "");
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				providerList.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return providerList;
	}

}
