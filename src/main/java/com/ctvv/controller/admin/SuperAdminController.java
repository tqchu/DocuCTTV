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

@WebServlet(name = "SuperAdminController", value = "/admin/manage-admin")
public class SuperAdminController
		extends HttpServlet {

	HttpSession session;
	private AdminDAO adminDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("id", id);
		request.setAttribute("action", action);
		RequestDispatcher dispatcher;
		dispatcher = request.getRequestDispatcher("/admin/super/addUpdateAdminForm.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				createAdmin(request, response);
				break;
			case "update":
				updateAdmin(request, response);
				break;
		}
	}

	private void createAdmin(HttpServletRequest request, HttpServletResponse response) {

	}

	private void updateAdmin(HttpServletRequest request, HttpServletResponse response) {

	}

	@Override
	public void init() throws ServletException {
		super.init();
		Context context = null;
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			// Tạo và gán dataSource cho adminDAO
			adminDAO = new AdminDAO(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
