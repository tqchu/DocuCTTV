package com.ctvv.controller.customer;

import com.ctvv.service.CustomerServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CustomerController", value = "/login")

public class CustomerLoginController
		extends HttpServlet {
	private final CustomerServices customerServices = new CustomerServices();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.removeAttribute("postData");
		customerServices.showLoginForm(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		if (account != null) {
			customerServices.authenticate(request, response);
		} else {
			customerServices.showLoginForm(request, response);
		}
	}

}
