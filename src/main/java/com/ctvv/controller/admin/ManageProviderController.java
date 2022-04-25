package com.ctvv.controller.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ManageProviderController", value = "/admin/providers/*")
public class ManageProviderController
		extends HttpServlet {
    final String HOME_PAGE = "/admin/manage/home.jsp";
	final String SEARCH_SERVLET ="/admin/providers/search";
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.equals(request.getContextPath()+ SEARCH_SERVLET)){
			search(request,response);
		}
		else
			listProviders(request,response);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {

	}

	private void listProviders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		goHome(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tab", "providers");
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_PAGE);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action){
			case "create":
				create(request,response);
				break;
			case "update":
				update(request, response);
				break;
			case "delete":
				delete(request, response);
				break;
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
	}

	private void create(HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
}
