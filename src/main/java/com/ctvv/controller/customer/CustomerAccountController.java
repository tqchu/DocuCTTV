package com.ctvv.controller.customer;

import com.ctvv.dao.CustomerDAO;
import com.ctvv.model.Customer;

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
import java.time.LocalDate;

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

	private void changePassword(HttpServletRequest request, HttpServletResponse response) {
	}

	private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");

		String fullName = request.getParameter("fullname");
		String phoneNumber = request.getParameter("phonenumber");


		Customer updateProfile = new Customer(customer);
		updateProfile.setFullName(fullName);
		updateProfile.setPhoneNumber(phoneNumber);

		try {
			updateProfile = customerDAO.update_Profile(updateProfile);
			session.setAttribute("customer", updateProfile);
			request.setAttribute("successMessage", "Cap nhat thanh cong");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw new ServletException();

		}

	}
	public void init() throws ServletException {
		super.init();
		try {

			Context context = new InitialContext();

			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			customerDAO = new CustomerDAO(dataSource);

		} catch (NamingException e) {
			// Chưa tìm ra cách xử lý hợp lý
			e.printStackTrace();
		}
	}



}
