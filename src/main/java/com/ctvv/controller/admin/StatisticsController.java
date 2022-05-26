package com.ctvv.controller.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "StatisticsController", value = "/admin/statistics/*")
public class StatisticsController
		extends HttpServlet {
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo(); // ex: '/this-year'
		if (path!=null){
			String tab= path.substring(1);
			switch (tab){
				case "this-month":
					break;
				case "this-quarter":
					break;
				case "this-year":
					break;
			}
			goHome(request, response);
		}
		else{
			response.sendRedirect(request.getContextPath()+"/admin/statistics/this-month");
		}

	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tab", "statistics");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
