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
import java.util.List;

@WebServlet(name = "ManageAdminsController", value = "/admin/admins/*")
public class ManageAdminsController
		extends HttpServlet {
	HttpSession session;
	private AdminDAO adminDAO;
	private  final String HOME_SERVLET = "/admin/admins";
	final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session= request.getSession();
		String action = request.getParameter("action");
		RequestDispatcher dispatcher;
		if (action == null) listAdmins(request, response);
		else if ("create".equals(action)) {
			dispatcher = request.getRequestDispatcher("/admin/manage/admins/addForm.jsp");
			dispatcher.forward(request, response);
		} else {
			listAdmins(request, response);
		}
	}

	private void listAdmins(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                         IOException {
		String keyword = request.getParameter("keyword");
		String sortBy = getOrder(request);
		List<Admin> adminList = adminDAO.getAdminList();
		int begin = getBegin(request);
		adminList = adminDAO.get(begin, NUMBER_OF_RECORDS_PER_PAGE, keyword, sortBy, null);
		int numberOfPages = (adminDAO.count(keyword) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
		Admin i = (Admin) session.getAttribute("admin");
		// Xóa bản thân ra khỏi danh sách
		adminList.remove(i);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("adminList", adminList);
		goHome(request, response);
	}

	public String getOrder(HttpServletRequest request) {
		String sortBy = request.getParameter("sortBy");
		if (sortBy != null) {
			switch (sortBy) {
				case "default":
					sortBy = null;
					break;
				case "name":
					sortBy = "username";
					break;
				case "fullname":
					sortBy = "fullname";
					break;
				case "email":
					sortBy = "email";
					break;
			}
		}
		return sortBy;
	}

	public int getBegin(HttpServletRequest request) {
		String pageParam = request.getParameter("page");
		int page;
		if (pageParam == null) {
			page = 1;
		} else {
			page = Integer.parseInt(pageParam);
		}
		return NUMBER_OF_RECORDS_PER_PAGE * (page - 1);
	}


	private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		adminDAO.delete(Integer.parseInt(request.getParameter("id")));
		try {
			response.sendRedirect( request.getContextPath()+HOME_SERVLET);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		request.setAttribute("tab", "admins");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);

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
				updateAdmin(request, response);
				break;
			case "delete":
				deleteAdmin(request, response);

		}
	}

	private void createAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullName");
		String role = request.getParameter("role");

		if ((adminDAO.findByUsername(username) == null) && (adminDAO.findByEmail(email) == null)) {
			Admin admin = new Admin(username, email, fullName, password, role);
			request.setAttribute("successMessage", "Thêm thành công");
			adminDAO.createAdmin(admin);
			try {
				response.sendRedirect(request.getContextPath() + "/admin");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (adminDAO.findByUsername(username) != null) {
				request.setAttribute("usernameErrorMessage", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác");
			}
			if (adminDAO.findByEmail(email) != null) {
				request.setAttribute("emailErrorMessage", "Email đã tồn tại, vui lòng chọn tên khác");
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admins/addForm.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (IOException | ServletException ex) {
				ex.printStackTrace();
			}
		}


	}

	private void updateAdmin(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String role = request.getParameter("role");
		adminDAO.updateRole(role, id);
		request.setAttribute("successMessage", "Cập nhật thành công");
		try {
			response.sendRedirect( request.getContextPath()+HOME_SERVLET);

		} catch (IOException e) {
			e.printStackTrace();
		}
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
