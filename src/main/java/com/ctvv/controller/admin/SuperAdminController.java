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
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(name = "SuperAdminController", value = "/admin/manage-admin")
public class SuperAdminController
		extends HttpServlet {
	HttpSession session;
	private AdminDAO adminDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		RequestDispatcher dispatcher;
		switch (action) {
			case "create":
				request.setAttribute("action", "create");
				dispatcher = request.getRequestDispatcher("/admin/super/addForm.jsp");
				dispatcher.forward(request, response);
				break;

			case "update":
				int id = Integer.parseInt(request.getParameter("id"));
				try {
					Admin admin = adminDAO.get(id);
					request.setAttribute("changeAdmin", admin);
				} catch (SQLException e) {
					throw new ServletException();
				}
				request.setAttribute("action", "update");
				dispatcher = request.getRequestDispatcher("/admin/super/addForm.jsp");
				dispatcher.forward(request, response);
				break;
			case "delete":
				deleteAdmin(request, response);
				break;

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                       UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				createAdmin(request, response);
				break;
			case "update":
//				updateAdmin(request, response);
				break;

		}
	}

	private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			adminDAO.delete(Integer.parseInt(request.getParameter("id")));
			try {
				response.sendRedirect(request.getContextPath()+"/admin");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw  new ServletException();
		}
	}

	private void createAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullName");
		String role = request.getParameter("role");
		Admin admin = new Admin(username, email, fullName, password, role);
		try {
			adminDAO.createAdmin(admin);
			request.setAttribute("successMessage", "Thêm thành công");
			try {
				response.sendRedirect(request.getContextPath() + "/admin");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				request.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác");
				request.setAttribute("action", "create");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/super/addForm.jsp");
				try {
					dispatcher.forward(request, response);
				} catch (IOException | ServletException ex) {
					ex.printStackTrace();
				}
			} else throw new ServletException();
		}
	}
/*

	private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		session = request.getSession();
		int id= Integer.parseInt(request.getParameter("id"));
		Admin admin;
		try {
			admin = adminDAO.get(id);
		} catch (SQLException e) {
			throw new ServletException();
		}
		String fullName = request.getParameter("fullName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");

		Admin updatedAdmin = new Admin(admin);
		updatedAdmin.setFullName(fullName);
		updatedAdmin.setUsername(username);
		updatedAdmin.setPassword(password);
		updatedAdmin.setRole(role);

		try {
			updatedAdmin = (Admin) adminDAO.updateSuper(admin);
			request.setAttribute("changeAdmin", updatedAdmin);
			request.setAttribute("successMessage", "Cập nhật thành công!");
			request.setAttribute("action", "update");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/super/addForm.jsp");
			try {
				dispatcher.forward(request,response);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SQLException | ServletException e) {

			if (e instanceof SQLIntegrityConstraintViolationException) {
				request.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác");

				RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/super/addForm.jsp");
				try {
					dispatcher.forward(request, response);
				} catch (IOException | ServletException ex) {
					ex.printStackTrace();
				}
			}
			else throw new ServletException();

		}
	}
*/

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
