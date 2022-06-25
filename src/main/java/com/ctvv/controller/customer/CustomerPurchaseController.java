package com.ctvv.controller.customer;

import com.ctvv.dao.OrderDAO;
import com.ctvv.dao.StockItemDAO;
import com.ctvv.model.*;
import com.ctvv.service.OrderServices;
import com.ctvv.util.EmailUtils;
import com.ctvv.util.UniqueStringUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CustomerPurchaseController", value = "/user/purchase/*")
public class CustomerPurchaseController
		extends HttpServlet {
	private final OrderServices orderServices = new OrderServices();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		orderServices.viewOrdersForCustomer(request, response);
	}


	private void viewOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                              IOException {

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		// to-ship, to-receive, cancel
		if (action.equals("create")) {
			orderServices.create(request, response);
		} else {
			String id = (request.getParameter("id"));
			Order order = orderDAO.get(id);
			switch (action) {
				case "cancel":
					order.setStatus(Order.OrderStatus.CANCELED);
					break;

			}
			orderDAO.update(order);
			response.sendRedirect(request.getParameter("from"));
		}
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws IOException {

	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
}
