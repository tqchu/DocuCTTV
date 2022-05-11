package com.ctvv.controller.admin;

import com.ctvv.dao.OrderDAO;
import com.ctvv.model.Order;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageOrdersController", value = "/admin/orders/*")
public class ManageOrdersController
		extends HttpServlet {
	private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	private final String PENDING = "/pending";
	private final String TO_SHIP = "/to-ship";
	private final String TO_RECEIVE = "/to-receive";
	private final String COMPLETED = "/completed";
	private final String CANCELED = "/canceled";
	private final String HOME_PAGE = "/admin/manage/home.jsp";
	private OrderDAO orderDAO;
	private HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		Order.OrderStatus status = Order.OrderStatus.PENDING;
		String statusTab = "pending";
		if (path == null) response.sendRedirect(request.getContextPath() + request.getServletPath() + PENDING);
		else {
			if (path.startsWith(TO_SHIP)) {
				status = Order.OrderStatus.TO_SHIP;
				statusTab = "to-ship";
			} else if (path.startsWith(TO_RECEIVE)) {
				status = Order.OrderStatus.TO_RECEIVE;
				statusTab = "to-receive";

			} else if (path.startsWith(COMPLETED)) {
				status = Order.OrderStatus.COMPLETED;
				statusTab = "completed";

			}
			String keyword = request.getParameter("keyword");
			int begin = getBegin(request);
			// sortBy, order
			// Xử lý numberOfPage
			//			int numberOfPages = (importDAO.count(keyword, from, to) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
			//			request.setAttribute("numberOfPages", numberOfPages);
			List<Order> orderList = orderDAO.getAll(status);

			request.setAttribute("tab", "orders");
			request.setAttribute("statusTab", statusTab);
			request.setAttribute("orderList", orderList);
			RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
			dispatcher.forward(request, response);
		}

	}


	private int getBegin(HttpServletRequest request) {
		String pageParam = request.getParameter("page");
		int page;
		if (pageParam == null) {
			page = 1;
		} else {
			page = Integer.parseInt(pageParam);
		}
		return NUMBER_OF_RECORDS_PER_PAGE * (page - 1);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		int id = Integer.parseInt(request.getParameter("id"));


	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {

	}

	private void viewPendingOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                                IOException {

	}

	private void viewToShipOrders(HttpServletRequest request, HttpServletResponse response) {

	}

	private void viewToReceiveOrders(HttpServletRequest request, HttpServletResponse response) {
	}

	private void viewCompletedOrders(HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			orderDAO = new OrderDAO(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
