package com.ctvv.service;

import com.ctvv.dao.AdminDAO;
import com.ctvv.model.Admin;
import com.ctvv.util.EmailUtils;
import com.ctvv.util.RequestUtils;
import com.ctvv.util.UniqueStringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AdminServices {
	private final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	private final String ADMIN_HOME_SERVLET = "/admin";
	private final String MANAGE_ADMINS_SERVLET = "/admin/admins";
	HttpSession session;
	private AdminDAO adminDAO = new AdminDAO();

	public void showLoginForm(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Đặt headerAction là đăng nhập
		request.setAttribute("headerAction", "Đăng nhập");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login/login.jsp");
		dispatcher.forward(request, response);
	}

	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		session = request.getSession();
		String from = request.getParameter("from");
		if (Objects.equals(from, "")) {
			from = request.getContextPath() + ADMIN_HOME_SERVLET;
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

	public void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                            IOException {
		request.setAttribute("headerAction", "Thay đổi thông tin");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/update/updateForm.jsp");
		dispatcher.forward(request, response);
	}

	public void updateAccountInformation(
			HttpServletRequest request, HttpServletResponse response) throws ServletException {
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

	public void listAdmins(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                        IOException {
		session = request.getSession();
		String keyword = request.getParameter("keyword");
		String sortBy = getOrder(request);
		List<Admin> adminList = adminDAO.getAdminList();
		int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_PAGE);
		adminList = adminDAO.get(begin, NUMBER_OF_RECORDS_PER_PAGE, keyword, sortBy, null);
		int numberOfPages = (adminDAO.count(keyword) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
		Admin i = (Admin) session.getAttribute("admin");
		// Xóa bản thân ra khỏi danh sách
		adminList.remove(i);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("adminList", adminList);
		request.setAttribute("tab", "admins");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}

	private String getOrder(HttpServletRequest request) {
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

	public void showAddAdminForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                              IOException {
		RequestDispatcher dispatcher;
		dispatcher = request.getRequestDispatcher("/admin/manage/admins/addForm.jsp");
		dispatcher.forward(request, response);
	}

	public void createAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		session = request.getSession();
		String username = request.getParameter("username");
		String password = UniqueStringUtils.randomUUID(8);
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		String fullName = request.getParameter("fullName");
		String role = request.getParameter("role");

		if ((adminDAO.findByUsername(username) == null) && (adminDAO.findByEmail(email) == null) && (adminDAO.findByPhoneNumber(phoneNumber) == null)) {
			Admin admin = new Admin(username, email, fullName, password, phoneNumber, address, role);
			// Gửi email  thông báo về cho nhân viên
			EmailUtils.sendPasswordForNewAdmin(email, password);
			adminDAO.create(admin);
			session.setAttribute("successMessage", "Thêm quản trị viên thành công");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/admins");
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
			if (adminDAO.findByPhoneNumber(phoneNumber) != null) {
				request.setAttribute("phoneNumberErrorMessage", "Số điện thoại đã tồn tại, vui lòng chọn tên khác");
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/admins/addForm.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void updateAdmin(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String role = request.getParameter("role");
		adminDAO.updateRole(role, id);
		request.setAttribute("successMessage", "Cập nhật thành công");
		try {
			response.sendRedirect(request.getContextPath() + MANAGE_ADMINS_SERVLET);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteAdmin(HttpServletRequest request, HttpServletResponse response) {
		adminDAO.delete(Integer.parseInt(request.getParameter("id")));
		try {
			response.sendRedirect(request.getContextPath() + MANAGE_ADMINS_SERVLET);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
