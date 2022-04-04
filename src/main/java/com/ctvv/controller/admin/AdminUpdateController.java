package com.ctvv.controller.admin;

import com.ctvv.dao.AdminDAO;
import com.ctvv.model.Admin;

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
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet(name = "AdminUpdateController", value = "/admin/update")
public class AdminUpdateController
		extends HttpServlet {

	HttpSession session;
	private AdminDAO adminDAO;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("headerAction","Thay đổi thông tin");
		RequestDispatcher dispatcher= request.getRequestDispatcher("/admin/update/updateInformationForm.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		update(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws  ServletException {
		// Đặt charaterEncoding của request param thành UTF-8
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		session= request.getSession();
		Admin admin= (Admin) session.getAttribute("admin");

		// Lấy dữ liệu từ form
		String fullName = request.getParameter("fullName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// Tạo 1 bản sao của admin (session)
		Admin updatedAdmin= new Admin(admin);
		updatedAdmin.setFullName(fullName);
		updatedAdmin.setUsername(username);
		updatedAdmin.setPassword(password);
		try {
			updatedAdmin = adminDAO.update(updatedAdmin);
			// Thành côngs
			// Gán admin trong session bằng admin vừa được câp nhật nếu không có exception xảy ra.
			session.setAttribute("admin", updatedAdmin);
			// Đặt tin nhắn thành công
			request.setAttribute("successMessage", "Cập nhật thành công!");
			RequestDispatcher dispatcher=request.getRequestDispatcher("/admin/update/updateInformationForm.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		// Catch tất cả exception của SQLException
		catch (SQLException e){
			// Trường hợp username đã tồn tại
			if (e instanceof SQLIntegrityConstraintViolationException){
				request.setAttribute("errorMessage","Tên đăng nhập đã tồn tại, vui lòng chọn tên khác");
				// Dispatch về form cập nhật
				RequestDispatcher dispatcher=request.getRequestDispatcher("/admin/update/updateInformationForm.jsp");
				try {
					dispatcher.forward(request, response);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			// Trường là exception vì lỗi nào đó
			else throw new ServletException();
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
