package com.ctvv.dao;

import com.ctvv.model.RevenueImportGraphPoint;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RevenueImportGraphPointDAO extends GenericDAO<RevenueImportGraphPoint>{

	public RevenueImportGraphPointDAO(DataSource dataSource) {
		super(dataSource);
	}

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
		String sql ="WITH result1 AS ("+
				"SELECT CAST(completed_time AS DATE) as completed_date,SUM(quantity * price) AS revenue"+
				" FROM order_detail"+
				" JOIN customer_order co ON" +
				" order_detail.order_id  = co.order_id"+
				" WHERE (completed_time BETWEEN ? AND ?) AND order_status ='completed'" +
				" GROUP BY completed_date) "+
				", result2 AS (" +
				"  SELECT CAST(i.import_date AS DATE) as import_date,SUM(quantity * price) AS " +
				"import_amount" +
				"             FROM import i JOIN import_detail ON import_detail.import_id = i.import_id" +
				"             WHERE (i.import_date BETWEEN ? AND ?)" +
				"             GROUP BY CAST(i.import_date AS DATE)" +
				"         )"
				+"SELECT completed_date AS date, revenue, IFNULL(import_amount, 0) AS import_amount FROM result1 LEFT " +
				"JOIN result2" +
				"    ON result1.completed_date = result2.import_date" +
				" UNION" +
				" SELECT import_date AS date, IFNULL(revenue, 0) AS revenue, import_amount FROM result1 RIGHT JOIN " +
				"result2 ON result1" +
				"    .completed_date =result2.import_date" +
				" ORDER BY  date"
				;
		try(
				Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
			preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
			preparedStatement.setTimestamp(3, Timestamp.valueOf(start));
			preparedStatement.setTimestamp(4, Timestamp.valueOf(end));
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
		String sql ="WITH result1 AS ("+
				"SELECT MONTH(completed_time) AS month,SUM(quantity * price) AS revenue"+
				" FROM order_detail"+
				" JOIN customer_order co ON" +
				" order_detail.order_id  = co.order_id"+
				" WHERE (completed_time BETWEEN ? AND ?) AND order_status ='completed'" +
				" GROUP BY month) "+
				", result2 AS (" +
				" SELECT MONTH(import_date) AS month,SUM(quantity * price) AS " +
				"import_amount" +
				"             FROM import i JOIN import_detail ON import_detail.import_id = i.import_id" +
				"             WHERE (i.import_date BETWEEN ? AND ?)" +
				"             GROUP BY month" +
				"         )"
				+"SELECT result1.month AS month, revenue, IFNULL(import_amount,0) AS import_amount FROM result1 LEFT " +
				"JOIN result2" +
				"    ON result1.month = result2.month" +
				" UNION" +
				" SELECT result2.month AS month, IFNULL(revenue, 0) AS revenue, import_amount FROM result1 RIGHT JOIN" +
				" " +
				"result2 ON result1" +
				"    .month =result2.month" +
				" ORDER BY month"
				;
		try(
				Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
			preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
			preparedStatement.setTimestamp(3, Timestamp.valueOf(start));
			preparedStatement.setTimestamp(4, Timestamp.valueOf(end));
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
