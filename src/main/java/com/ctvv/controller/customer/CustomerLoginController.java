package com.ctvv.controller.customer;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.model.Customer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "CustomerController", value = "/login")

public class CustomerLoginController
		extends HttpServlet {
	private final String HOME_SERVLET = "/login";
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
		session = request.getSession();
		session.removeAttribute("postData");
		showLoginForm(request, response);
	}

	private void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                            IOException {
		request.setAttribute("headerAction", "Đăng nhập");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}


	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		if (account != null) {
			authenticate(request, response);
		} else {
			showLoginForm(request, response);
		}
	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                           IOException {
		session = request.getSession();
		String from = request.getParameter("from");
		if (from.equals("")) {
			from = request.getContextPath();
		}
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		Customer customer = new Customer();
		boolean isPhoneNumber = (account.indexOf('@') == -1);
		if (isPhoneNumber) {
			customer.setPhoneNumber(account);
		} else {
			customer.setEmail(account);
		}
		customer.setPassword(password);

		Customer authenticatedCustomer;
		// TH1: tài khoản tồn tại
		authenticatedCustomer = customerDAO.validate(customer);
		if (authenticatedCustomer != null) {
			// Tài khoản bình thường (active)
			if (authenticatedCustomer.isActive()) {
				session.setAttribute("customer", authenticatedCustomer);
				String postData = (String) session.getAttribute("postData");
				if (postData != null) {
					response.setStatus(307);
					response.setHeader("Location", from);
				} else {
					response.sendRedirect(from);
				}
			}
			// Tài khoản bị khóa
			else {
				session.setAttribute("loginMessage", "Tài khoản của bạn đã bị khóa vì vi phạm chính sách của chúng " +
						"tôi");
				response.sendRedirect(request.getContextPath() + HOME_SERVLET);
			}

		} else {

			session.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
			response.sendRedirect(request.getContextPath() + HOME_SERVLET);
		}
	}
}
