package com.ctvv.controller.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ManageInventoryController", value = "/admin/inventory/*")
public class ManageInventoryController
		extends HttpServlet {
	final String HOME_PAGE = "/admin/manage/home.jsp";

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		if (request.getRequestURI().equals(request.getContextPath()+"/admin/inventory/history"))
			request.setAttribute("tab", "inventoryHistory");
		else
			request.setAttribute("tab", "inventory");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(HOME_PAGE);
		requestDispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
