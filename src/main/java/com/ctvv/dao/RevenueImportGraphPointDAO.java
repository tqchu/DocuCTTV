package com.ctvv.dao;

import com.ctvv.model.RevenueImportGraphPoint;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RevenueImportGraphPointDAO
		implements GenericDAO<RevenueImportGraphPoint> {

	@Override
	public RevenueImportGraphPoint get(int id) {
		return null;
	}

	@Override
	public List<RevenueImportGraphPoint> getAll() {
		return null;
	}
	public List<RevenueImportGraphPoint> getAllByDate(LocalDateTime start, LocalDateTime end) {
		List<RevenueImportGraphPoint> pointList = new ArrayList<>();
		String sql ="CALL stats_by_date(?,?)";
		try(
				Connection connection = DataSourceHelper.getDataSource().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
			preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				String label = resultSet.getTimestamp("date").toLocalDateTime().getDayOfMonth()+"";
				long revenue = resultSet.getLong("revenue");
				long importAmount  = resultSet.getLong("import_amount");
				pointList.add(new RevenueImportGraphPoint(label, revenue, importAmount));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return  pointList;

	}
	public List<RevenueImportGraphPoint> getAllByMonth(LocalDateTime start, LocalDateTime end) {
		List<RevenueImportGraphPoint> pointList = new ArrayList<>();
		String sql = "CALL stats_by_month(?,?)";
		try(
				Connection connection = DataSourceHelper.getDataSource().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
			preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				String label = resultSet.getInt("month")+"";
				long revenue = resultSet.getLong("revenue");
				long importAmount  = resultSet.getLong("import_amount");
				pointList.add(new RevenueImportGraphPoint(label, revenue, importAmount));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return  pointList;

	}

	@Override
	public RevenueImportGraphPoint create(RevenueImportGraphPoint revenueImportGraphPoint) {
		return null;
	}

	@Override
	public RevenueImportGraphPoint update(RevenueImportGraphPoint revenueImportGraphPoint) {
		return null;
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public RevenueImportGraphPoint map(ResultSet resultSet) {
		return null;
	}
}
