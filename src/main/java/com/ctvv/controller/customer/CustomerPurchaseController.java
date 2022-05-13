package com.ctvv.controller.customer;

import com.ctvv.dao.OrderDAO;
import com.ctvv.model.Customer;
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

@WebServlet(name = "CustomerPurchaseController", value = "/user/purchase/*")
public class CustomerPurchaseController
		extends HttpServlet {
	private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	private final String PENDING = "/pending";
	private final String TO_SHIP = "/to-ship";
	private final String TO_RECEIVE = "/to-receive";
	private final String COMPLETED = "/completed";
	private final String CANCELED = "/canceled";
	private final String HOME_PAGE = "/customer/account/manage-account.jsp";
	private OrderDAO orderDAO;
	private HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// orders/
		String path = request.getPathInfo(); //
		Order.OrderStatus status = Order.OrderStatus.PENDING;
		String statusTab = "pending";
		if (path == null) response.sendRedirect(request.getContextPath() + request.getServletPath() + PENDING);
		else {
			if (path.startsWith(PENDING) || path.startsWith(TO_SHIP) || path.startsWith(TO_RECEIVE) || path.startsWith(COMPLETED) || path.startsWith(CANCELED)) {
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
				else if (path.startsWith(CANCELED)){
					status = Order.OrderStatus.CANCELED;
					statusTab = "canceled";
				}
				session = request.getSession();
				Customer customer = (Customer) session.getAttribute("customer");
				String keyword = request.getParameter("keyword");
				int begin = getBegin(request);
				String sortBy;
				if (status.name().equals("COMPLETED"))
					sortBy = "completed_time";
				else
					sortBy = "order_time";
				String order = request.getParameter("order");
				if (order == null) order = "DESC";
				//Xử lý numberOfPages
				int numberOfPages = (orderDAO.countForCustomer(status, keyword, customer.getUserId()) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
				request.setAttribute("numberOfPages", numberOfPages);
				List<Order> orderList = orderDAO.getAllForCustomers(begin, NUMBER_OF_RECORDS_PER_PAGE, status, keyword
						, sortBy,
						order,
						customer.getUserId());
				request.setAttribute("statusTab", statusTab);
				request.setAttribute("orderList", orderList);
				goHome(request, response);
			} else {
				viewOrderDetail(request, response);
			}
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

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		request.setAttribute("tab", "purchase");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	private void viewOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                              IOException {
		int orderId = Integer.parseInt(request.getPathInfo().substring(1));
		Order order = orderDAO.get(orderId);
		request.setAttribute("tab", "orderDetail");
		request.setAttribute("order", order);
		goHome(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		// to-ship, to-receive, cancel
		String id = (request.getParameter("id"));
		Order order = orderDAO.get(id);
		switch (action) {
			case "completed":
				order.setStatus(Order.OrderStatus.COMPLETED);
				break;
			case "cancel":
				order.setStatus(Order.OrderStatus.CANCELED);
				break;

		}
		orderDAO.update(order);
		response.sendRedirect(request.getParameter("from"));
	}

	@Override
	public void init() throws ServletException {
		super.init();
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
