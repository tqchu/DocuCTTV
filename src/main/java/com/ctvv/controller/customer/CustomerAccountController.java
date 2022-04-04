package com.ctvv.controller.customer;

import com.ctvv.dao.AdminDAO;
import com.ctvv.dao.CustomerDAO;
import com.ctvv.model.Customer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

@WebServlet(name = "CustomerAccountController", value = "/user/account")
public class CustomerAccountController
		extends HttpServlet {
	HttpSession session;
	private CustomerDAO customerDAO;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tab= request.getParameter("tab");
		if (tab==null){
			response.sendRedirect(request.getContextPath()+"/user/account?tab=profile");
		}
		else{
			request.setAttribute("tab", tab);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
			dispatcher.forward(request, response);
		}

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String action= request.getParameter("action");
			switch (action){
				case "updateProfile":
					updateProfile(request,response);
				case "changePassword":
						changePassword(request,response);
				case "updateAddress":
					updateAddress(request,response);
			}
	}

	private void updateAddress(HttpServletRequest request, HttpServletResponse response) {

	}


	private void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("tab", "password");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("password");
		String confirmedPassword = request.getParameter("confirmedPassword");

		session  = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");

		try {
			if (oldPassword.equals(customer.getPassword())){
				if (newPassword.equals(confirmedPassword)){
					//đổi mật khẩu trong database
					customer.setPassword(newPassword);
					customer = customerDAO.updatePassword(customer);
					//đổi mật khẩu cho session hien tai
					session.setAttribute("customer",customer);
					request.setAttribute("successMessage","Đổi mật khẩu thành công");
				}
			}
			else {
				request.setAttribute("wrongOldPassword","Sai mật khẩu cũ");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
		dispatcher.forward(request, response);
	}

	private void updateProfile(HttpServletRequest request, HttpServletResponse response){
		session = request.getSession();
		Customer customer = (Customer)session.getAttribute("customer");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
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
			customerDAO = new CustomerDAO(dataSource);

		} catch (NamingException e) {
			// Chưa tìm ra cách xử lý hợp lý
			e.printStackTrace();
		}
	}
}
