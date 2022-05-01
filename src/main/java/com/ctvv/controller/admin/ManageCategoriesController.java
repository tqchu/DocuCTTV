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

@WebServlet(name = "ManageCategoriesController", value = "/admin/categories/*")
public class ManageCategoriesController
		extends HttpServlet {
	final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	private CategoryDAO categoryDAO;
	private HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		listCategory(request, response);
	}

	private void listCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                           IOException {
		String keyword = request.getParameter("keyword");
		String orderBy = getOrder(request);
		List<Category> categoryList;
		int begin = getBegin(request);
		categoryList = categoryDAO.get(begin, NUMBER_OF_RECORDS_PER_PAGE, keyword, null, orderBy, null);
		int numberOfPages = categoryDAO.count(keyword, null) / NUMBER_OF_RECORDS_PER_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("list", categoryList);
		goHome(request, response);
	}

	public String getOrder(HttpServletRequest request) {
		String orderBy = request.getParameter("orderBy");
		if (orderBy != null) {
			switch (orderBy) {
				case "default":
					orderBy = null;
					break;
				case "name":
					orderBy = "category_name";
					break;
			}
		}
		return orderBy;
	}

	public int getBegin(HttpServletRequest request) {
		String pageParam = request.getParameter("page");
		int page;
		if (pageParam == null) {
			page = 1;
		} else {
			page = Integer.parseInt(pageParam);
		}
		return NUMBER_OF_RECORDS_PER_PAGE * (page - 1);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		request.setAttribute("requestURI", request.getRequestURI());
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
				update(request, response);
				break;
			case "delete":
				delete(request, response);
		}
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                     IOException {
		String categoryName = request.getParameter("categoryName");
		session = request.getSession();

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

	private void update(HttpServletRequest request, HttpServletResponse response) {
		String categoryName = request.getParameter("categoryName");
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		session = request.getSession();

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

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));

		categoryDAO.delete(categoryId);
		session = request.getSession();
		session.setAttribute("successMessage", "Xóa doanh mục thành công");
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
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			categoryDAO = new CategoryDAO(dataSource);
		} catch (NamingException e) {
			throw new ServletException();
		}
	}

}
