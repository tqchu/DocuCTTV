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
	private OrderDAO orderDAO;
	private HttpSession session;
	private final String PENDING = "/pending";
	private final String TO_SHIP = "/to-ship";
	private final String TO_RECEIVE = "/to-receive";
	private final String COMPLETED = "/completed";
	private final String CANCELED = "/canceled";
	private  final String HOME_PAGE = "/";

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		Order.OrderStatus  status = Order.OrderStatus.PENDING;
		switch (path) {
			case TO_SHIP:
				status = Order.OrderStatus.TO_SHIP;
				break;
			case TO_RECEIVE:
				status = Order.OrderStatus.TO_RECEIVE;
				break;
			case COMPLETED:
				status = Order.OrderStatus.COMPLETED;
				break;
			default:
				response.sendRedirect(request.getContextPath() + request.getServletPath() + PENDING);
				break;
		}
		List<Order> orderList = orderDAO.getAll(status);
		request.setAttribute("orderList", orderList);
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			orderDAO = new OrderDAO(dataSource);
		}
		catch (NamingException e){
			e.printStackTrace();
		}
	}
}
