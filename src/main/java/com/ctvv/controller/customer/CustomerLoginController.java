package com.ctvv.controller.customer;

import com.ctvv.dao.CategoryDAO;
import com.ctvv.dao.CustomerDAO;
import com.ctvv.dao.ProductDAO;
import com.ctvv.model.Category;
import com.ctvv.model.Customer;
import com.ctvv.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebServlet(name = "CustomerController", value = "/login")

public class CustomerLoginController
		extends HttpServlet {
	private final String HOME_SERVLET = "";
	private final String HOME_PAGE = "/customer/login/login.jsp";
	HttpSession session;
	private CustomerDAO customerDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		Context context;
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			customerDAO = new CustomerDAO(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}


	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("headerAction", "Đăng nhập");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}


	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		authenticate(request, response);
	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                           IOException {
		session = request.getSession();
		String from = request.getParameter("from");
		if (Objects.equals(from, "")) {
			from = request.getContextPath() + HOME_SERVLET;
		}
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		Customer customer = new Customer();
		boolean isPhoneNumber = (account.indexOf('@') == -1);
		if (isPhoneNumber){
			customer.setPhoneNumber(account);
		}
		else{
			customer.setEmail(account);
		}
		customer.setPassword(password);

		Customer authenticatedCustomer;
		// TH1: validate thành công
		authenticatedCustomer = customerDAO.validate(customer);
		if (authenticatedCustomer != null) {
			session.setAttribute("customer", authenticatedCustomer);
			String postData = (String) session.getAttribute("postData");
			if (postData != null) {
				//				response.set
				//				response.se
				response.setStatus(307);
				response.setHeader("Location", from);
			} else {
				response.sendRedirect(from);
			}
		} else {

			request.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
				dispatcher.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
