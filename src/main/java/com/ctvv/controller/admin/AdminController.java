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
import java.util.Objects;

@WebServlet(name = "AdminController", value = "/admin")
public class AdminController
		extends HttpServlet {

	private AdminDAO adminDAO;
	HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session= request.getSession();
		String role= (String) session.getAttribute("role");
		// Chuyển sang trang đăng nhập
		if (role==null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login/login.jsp");
			dispatcher.forward(request, response);
		}
		//
		else{
			System.out.print(role);
		}

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session= request.getSession();
		authenticate(request, response);

	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Admin admin = new Admin(password, username);
		Admin authenticatedAdmin;
		// TH1: validate thành công
		try {
			authenticatedAdmin= adminDAO.validate(admin);
		} catch (SQLException e) {
			throw new ServletException();
		}
		if (authenticatedAdmin != null) {
			// TH1: la admin
			if (Objects.equals(authenticatedAdmin.getRole(), "admin")) {
				session.setAttribute("role", "admin");
			}
			// TH2: super admin
			else {
				session.setAttribute("role", "super admin");
			}
			// Chuyển về lại trang home (/admin)
			try {
				response.sendRedirect(request.getContextPath()+ "/admin"); // contextPath: link web
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		else {

			// Đưa ra thông báo lỗi


		}
		// TH2: validate fail
	}

	@Override
	public void init() throws ServletException {
		super.init();
		// Khởi tạo dataSource cho adminDao
		try {
			// Dòng bắt buộc để tạo dataSource
			Context context = new InitialContext();
			// Tạo và gán dataSource cho adminDAO
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			adminDAO = new AdminDAO(dataSource);

		} catch (NamingException e) {
			// Chưa tìm ra cách xử lý hợp lý
			e.printStackTrace();
		}


	}
}
