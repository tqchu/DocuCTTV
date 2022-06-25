package com.ctvv.controller.admin;

import com.ctvv.dao.AdminDAO;
import com.ctvv.model.Admin;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "AdminController", value = "/admin")
public class AdminController
		extends HttpServlet {

	private final String HOME_SERVLET = "/admin";
	HttpSession session;
	private AdminDAO adminDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		// Chuyển sang trang đăng nhập
		if (admin == null) {
			// Đặt headerAction là đăng nhập
			request.setAttribute("headerAction", "Đăng nhập");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login/login.jsp");
			dispatcher.forward(request, response);
		}
		//
		else {
			response.sendRedirect(request.getContextPath() + "/admin/products");
		}

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		authenticate(request, response);
	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String from = request.getParameter("from");
		if (Objects.equals(from, "")) {
			from = request.getContextPath() + HOME_SERVLET;
		}
		String usernameOrEmail = request.getParameter("usernameOrEmail");
		String password = request.getParameter("password");
		Admin admin = new Admin(usernameOrEmail, password);
		Admin authenticatedAdmin;
		// TH1: validate thành công
		authenticatedAdmin = adminDAO.validate(admin);
		if (authenticatedAdmin != null) {

			session.setAttribute("admin", authenticatedAdmin);
			// Chuyển về lại trang home (/admin)
			try {
				response.sendRedirect(from); // contextPath: link web
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {

			request.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login/login.jsp");
				dispatcher.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		adminDAO=new AdminDAO();
	}
}
