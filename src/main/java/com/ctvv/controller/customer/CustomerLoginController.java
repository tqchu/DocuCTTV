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
import java.sql.*;
import java.util.List;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebServlet(name = "CustomerController", value = "")

public class CustomerLoginController
		extends HttpServlet {
	HttpSession session;
	private CustomerDAO customerDAO;
	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		Context context = null;
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			customerDAO = new CustomerDAO(dataSource);
			productDAO = new ProductDAO(dataSource);
			categoryDAO = new CategoryDAO(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}


	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			request.setAttribute("headerAction", "Đăng nhập");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/login/login.jsp");
			dispatcher.forward(request, response);
		} else {
			listProductAndCategory(request, response);

		}
	}

	private void listProductAndCategory(
			HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                         IOException {
		List<Product> productList = productDAO.getAll();
		List<Category> categoryList = categoryDAO.getAll();
		request.setAttribute("productList", productList);
		request.setAttribute("categoryList", categoryList);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
		requestDispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException {
		session = request.getSession();
		authenticate(request, response);

	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");
		Customer customer = new Customer(phoneNumber, password);
		Customer authenticatedCustomer;
		// TH1: validate thành công
		try {
			authenticatedCustomer = customerDAO.validate(customer);
		} catch (SQLException e) {
			throw new ServletException();
		}
		if (authenticatedCustomer != null) {

			session.setAttribute("customer", authenticatedCustomer);

			try {
				response.sendRedirect(request.getContextPath());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {

			request.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/login/login.jsp");
				dispatcher.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
