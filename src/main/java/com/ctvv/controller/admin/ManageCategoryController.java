package com.ctvv.controller.admin;

import com.ctvv.dao.AdminDAO;
import com.ctvv.dao.CategoryDAO;
import com.ctvv.model.Category;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ManageCategoryController", value = "/admin/categories")
public class ManageCategoryController
		extends HttpServlet {
	private CategoryDAO categoryDAO;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher= request.getRequestDispatcher("/admin/admin/home.jsp");
		request.setAttribute("tab", "categories");
		dispatcher.forward(request, response);
		listCategory(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String action = request.getParameter("action");
		switch(action){
			case "create":
				create(request, response);
				break;
				//more service here such as delete or update
		}
	}
	private void listCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categoryList = new ArrayList<>();
		categoryList = categoryDAO.getAll();
		request.setAttribute("categoryList",categoryList);
		response.sendRedirect(request.getContextPath() + "/admin/categories");
	}
	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String categoryName = request.getParameter("categoryName");
		if (categoryDAO.find(categoryName)==null){
			Category category = new Category(categoryName);
			categoryDAO.create(category);
			request.setAttribute("successMessage","Thêm thành công");
			try {
				response.sendRedirect(request.getContextPath() + "/admin/categories");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			request.setAttribute("errorMessage", "Tên doanh mục đã tồn tại");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin/home.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (IOException | ServletException ex) {
				ex.printStackTrace();
			}
		}
	}
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			// Tạo và gán dataSource cho adminDAO
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			categoryDAO = new CategoryDAO(dataSource);
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}

}
