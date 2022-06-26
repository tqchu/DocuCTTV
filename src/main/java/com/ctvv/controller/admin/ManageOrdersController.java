package com.ctvv.controller.admin;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.dao.OrderDAO;
import com.ctvv.model.Customer;
import com.ctvv.model.Order;
import com.ctvv.service.OrderServices;
import com.ctvv.util.EmailUtils;
import com.ctvv.util.JasperReportUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ManageOrdersController", value = "/admin/orders/*")
public class ManageOrdersController
		extends HttpServlet {
	private final OrderServices orderServices = new OrderServices();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path == null) response.sendRedirect(request.getContextPath() + request.getServletPath() + "/pending");
		else {
			orderServices.viewOrders(request, response);
		}

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if ("/download".equals(path)) {
			orderServices.downloadBillFile(request, response);
		} else {
			orderServices.updateOrderStatus(request, response);
		}

	}

}