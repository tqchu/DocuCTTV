package com.ctvv.controller.admin;

import com.ctvv.model.Admin;
import com.ctvv.service.AdminServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminController", value = "/admin")
public class AdminController
		extends HttpServlet {
	private final AdminServices adminServices = new AdminServices();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin == null) {
			adminServices.showLoginForm(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/admin/products");
		}

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		adminServices.authenticate(request, response);
	}
}
