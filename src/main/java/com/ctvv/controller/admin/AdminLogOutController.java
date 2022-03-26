package com.ctvv.controller.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminLogOutController", value = "/admin/logout")
public class AdminLogOutController
		extends HttpServlet {
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Reset dữ liệu trong session
		HttpSession session= request.getSession();
		session.invalidate();
		// Redirect về trang đăng nhập
		response.sendRedirect(request.getContextPath() + "/admin");
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
