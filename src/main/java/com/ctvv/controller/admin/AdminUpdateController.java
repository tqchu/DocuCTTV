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
		request.setAttribute("headerAction", "Thay đổi thông tin");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/update/updateForm.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Đặt charaterEncoding của request param thành UTF-8
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		update(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException {


		session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		String fullName = request.getParameter("fullName");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		boolean isUsernameValid =
				(adminDAO.findByUsername(username).equals(admin) || adminDAO.findByUsername(username) == null);
		boolean isEmailValid = (adminDAO.findByEmail(email).equals(admin) || adminDAO.findByEmail(email) == null);
		boolean isPhoneNumberValid =
				(adminDAO.findByPhoneNumber(phoneNumber).equals(admin) || adminDAO.findByPhoneNumber(phoneNumber) == null);
		// Tạo 1 bản sao của admin (session)
		if (isPhoneNumberValid && isEmailValid && isUsernameValid) {
			Admin updatedAdmin = new Admin(admin);
			updatedAdmin.setFullName(fullName);
			updatedAdmin.setEmail(email);
			updatedAdmin.setPhoneNumber(phoneNumber);
			updatedAdmin.setAddress(address);
			updatedAdmin.setUsername(username);
			updatedAdmin.setPassword(password);
			updatedAdmin.setEmail(email);
			updatedAdmin = adminDAO.update(updatedAdmin);
			// Thành côngs
			session.setAttribute("admin", updatedAdmin);
			// Đặt tin nhắn thành công
			request.setAttribute("successMessage", "Cập nhật thành công!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/update/updateForm.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (!isUsernameValid) {
				request.setAttribute("usernameErrorMessage", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác");
			}
			if (!isEmailValid) {
				request.setAttribute("emailErrorMessage", "Email đã tồn tại, vui lòng chọn tên khác");
			}
			if (!isPhoneNumberValid) {
				request.setAttribute("phoneNumberErrorMessage", "Số điện thoại đã tồn tại, vui lòng chọn tên khác");
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/update/updateForm.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (IOException | ServletException ex) {
				ex.printStackTrace();
			}
		}


	}

	@Override
	public void init() throws ServletException {
		super.init();
		adminDAO = new AdminDAO();
	}
}
