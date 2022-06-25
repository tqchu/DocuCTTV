package com.ctvv.controller.admin;

import com.ctvv.dao.CategoryDAO;
import com.ctvv.model.Category;
import com.ctvv.service.CategoryServices;

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
import java.util.List;

@WebServlet(name = "ManageCategoriesController", value = "/admin/categories/*")
public class ManageCategoriesController
		extends HttpServlet {
	private final CategoryServices categoryServices = new CategoryServices();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		categoryServices.listCategory(request, response);
	}


	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				categoryServices.create(request, response);
				break;
			case "update":
				categoryServices.update(request, response);
				break;
			case "delete":
				categoryServices.delete(request, response);
		}
	}

}
