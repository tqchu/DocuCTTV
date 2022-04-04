package com.ctvv.controller.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
@WebServlet(name = "AdminForgotPasswordController", value = "/admin/forgot-password")
public class AdminForgotPasswordController
		extends HttpServlet {
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.setAttribute("headerAction","Khôi phục mật khẩu");
		RequestDispatcher dispatcher=request.getRequestDispatcher("/admin/login/forgot-password.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
