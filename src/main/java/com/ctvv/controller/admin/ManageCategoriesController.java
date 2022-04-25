package com.ctvv.controller.admin;

import com.ctvv.dao.CategoryDAO;
import com.ctvv.model.Category;

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

@WebServlet(name = "ManageCategoriesController", value = "/admin/categories")
public class ManageCategoriesController
		extends HttpServlet {
	private CategoryDAO categoryDAO;
	private HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		listCategory(request,response);
	}

	private void listCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                           IOException {
		List<Category> categoryList = categoryDAO.getAll();
		request.setAttribute("list", categoryList);
		goHome(request,response);

	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tab", "categories");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				create(request, response);
				break;
			case "update":
				update(request,response);
				break;
			case "delete":
				delete(request,response);
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		int categoryId= Integer.parseInt(request.getParameter("categoryId"));

		categoryDAO.delete(categoryId);
		session=request.getSession();
		session.setAttribute("successMessage", "Xóa doanh mục thành công");
		try {
			response.sendRedirect(request.getContextPath() + "/admin/categories");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		String categoryName = request.getParameter("categoryName");
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		session= request.getSession();

		if (categoryDAO.find(categoryName) == null) {
			Category category = new Category(categoryId, categoryName);
			categoryDAO.update(category);
			session.setAttribute("successMessage", "Sửa doanh mục thành công");

		} else {
			session.setAttribute("errorMessage", "Tên doanh mục đã tồn tại");
		}
		try {
			response.sendRedirect(request.getContextPath() + "/admin/categories");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryName = request.getParameter("categoryName");
		session= request.getSession();

		if (categoryDAO.find(categoryName) == null) {
			Category category = new Category(categoryName);
			categoryDAO.create(category);
			session.setAttribute("successMessage", "Thêm doanh mục thành công");

		} else {
			session.setAttribute("errorMessage", "Tên doanh mục đã tồn tại");
		}
		try {
			response.sendRedirect(request.getContextPath() + "/admin/categories");
		} catch (IOException e) {
			e.printStackTrace();
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
