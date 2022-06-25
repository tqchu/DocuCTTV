package com.ctvv.controller.admin;

import com.ctvv.dao.OrderDAO;
import com.ctvv.dao.RevenueImportGraphPointDAO;
import com.ctvv.dao.StatsProductDAO;
import com.ctvv.model.Order;
import com.ctvv.model.RevenueImportGraphPoint;
import com.ctvv.util.GsonUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

@WebServlet(name = "StatisticsController", value = "/admin/statistics/*")
public class StatisticsController
		extends HttpServlet {
	private RevenueImportGraphPointDAO revenueImportGraphPointDAO;
	private OrderDAO orderDAO;
	private StatsProductDAO statsProductDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo(); // ex: '/this-year'
		if (path != null) {
			String tab = path.substring(1);
			List<RevenueImportGraphPoint> pointList = null;
			LocalDate thisDay = LocalDate.now();
			LocalDateTime start = null;
			LocalDateTime end = LocalDateTime.now();
			int endPoint = 0;
			int startPoint = 0;
			String title = null;
			switch (tab) {
				case "this-month":
					start = thisDay.withDayOfMonth(1).atStartOfDay();
					pointList = revenueImportGraphPointDAO.getAllByDate(start, end);
					startPoint = 1;
					title = "tháng " + thisDay.getMonthValue();
					endPoint = thisDay.getDayOfMonth();
					break;
				case "this-quarter":
					start =
							thisDay.with(thisDay.getMonth().firstMonthOfQuarter()).with(firstDayOfMonth()).atStartOfDay();
					pointList = revenueImportGraphPointDAO.getAllByMonth(start, end);
					startPoint = start.getMonthValue();
					endPoint = thisDay.getMonthValue();
					title = "quý " + ((thisDay.getMonthValue() - 1) / 3 + 1);
					break;
				case "this-year":
					start = thisDay.with(firstDayOfYear()).atStartOfDay();
					pointList = revenueImportGraphPointDAO.getAllByMonth(start, end);
					startPoint = 1;
					title = "năm " + thisDay.getYear();
					endPoint = thisDay.getMonthValue();
					break;
			}
			// Tạo 1 map lưu trữ các cặp (Trạng thái - số lượng) đơn hàng
			Map<String, Integer> orderChartElementList = new HashMap<>();
			int numberOfPendingOrder = orderDAO.count(Order.OrderStatus.PENDING, start, end);
			int numberOfConfirmedOrder = orderDAO.count(Order.OrderStatus.TO_SHIP, start, end);
			int numberOfShippedOrder = orderDAO.count(Order.OrderStatus.TO_RECEIVE, start, end);
			int numberOfCompletedOrder = orderDAO.count(Order.OrderStatus.COMPLETED, start, end);
			orderChartElementList.put("Đang chờ", numberOfPendingOrder);
			orderChartElementList.put("Đang chờ vận chuyển", numberOfConfirmedOrder);
			orderChartElementList.put("Đang giao", numberOfShippedOrder);
			orderChartElementList.put("Đã giao", numberOfCompletedOrder);
			request.setAttribute("numberOfTotalOrder",
					numberOfPendingOrder + numberOfConfirmedOrder + numberOfShippedOrder + numberOfCompletedOrder);

			// Lấy tổng doanh thu và import amount sử dụng Java 8 Stream dựa trên dữ liệu có sẵn từ các pointList
			// (Đỡ viết thêm hàm)
			request.setAttribute("totalRevenue",
					pointList.stream().map(RevenueImportGraphPoint::getRevenue).reduce((long) 0, Long::sum));
			request.setAttribute("totalImportAmount",
					pointList.stream().map(RevenueImportGraphPoint::getImportAmount).reduce((long) 0, Long::sum));


			request.setAttribute("title", title);
			request.setAttribute("orderChartElementList", GsonUtil.getOrderElementList(orderChartElementList));
			request.setAttribute("statisticsTab", tab);
			// Method reference (RevenueImportGraphPoint::getRevenue)
			request.setAttribute("revenuePointList", GsonUtil.getAmount(pointList, startPoint, endPoint,
					RevenueImportGraphPoint::getRevenue));
			request.setAttribute("importPointList", GsonUtil.getAmount(pointList, startPoint, endPoint,
					RevenueImportGraphPoint::getImportAmount));
			request.setAttribute("statsProductList", statsProductDAO.getAll(start, end));
			goHome(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/statistics/this-month");
		}

	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		request.setAttribute("tab", "statistics");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	public void init() throws ServletException {
		super.init();
		revenueImportGraphPointDAO = new RevenueImportGraphPointDAO();
		orderDAO = new OrderDAO();
		statsProductDAO = new StatsProductDAO();

	}
}
