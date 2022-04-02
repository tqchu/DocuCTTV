package com.ctvv.controller.customer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerAccountController", value = "/user/account")
public class CustomerAccountController
		extends HttpServlet {
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
			case "profile":
				updateProfile(request,response);
			case "changePassword":
				changePassword(request,response);
			case "addAddress":
				addAddress(request,response);
			case "updateAddress":
				updateAddress(request,response);
		}
	}

	private void updateAddress(HttpServletRequest request, HttpServletResponse response) {

	}

	private void addAddress(HttpServletRequest request, HttpServletResponse response) {
	}

	private void changePassword(HttpServletRequest request, HttpServletResponse response) {
	}

	private void updateProfile(HttpServletRequest request, HttpServletResponse response) {
	}
}
