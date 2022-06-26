package com.ctvv.controller.admin;

import com.ctvv.dao.OrderDAO;
import com.ctvv.dao.RevenueImportGraphPointDAO;
import com.ctvv.dao.StatsProductDAO;
import com.ctvv.model.Order;
import com.ctvv.model.RevenueImportGraphPoint;
import com.ctvv.service.StatisticsServices;
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

	private StatisticsServices statisticsServices = new StatisticsServices();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		statisticsServices.viewStatistics(request, response);
	}

}
