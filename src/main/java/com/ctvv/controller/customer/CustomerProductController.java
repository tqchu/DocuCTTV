package com.ctvv.controller.customer;

import com.ctvv.dao.CategoryDAO;
import com.ctvv.dao.ProductDAO;
import com.ctvv.model.Category;
import com.ctvv.model.Product;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerProductController", value = "/products")
public class CustomerProductController
		extends HttpServlet {
	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;
	final int NUMBER_OF_RECORDS_PER_PAGE = 20;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getRequestURI().equals(request.getContextPath()+ "products/search")) search(request,response);
		else {
			String categoryName = request.getParameter("category");
			String idParam = request.getParameter("id");
			if (categoryName != null) {
				Category category = categoryDAO.find(categoryName);
				List<Product> productList;
				if (request.getRequestURI().equals(request.getContextPath()+"&sortBy=price&order=ASC"))
					productList = productDAO.getAllByCategory(category.getCategoryId(),"price","ASC",0,NUMBER_OF_RECORDS_PER_PAGE);
				else if (request.getRequestURI().equals(request.getContextPath()+"&sortBy=price&order=DESC"))
					productList = productDAO.getAllByCategory(category.getCategoryId(),"price","DESC",0,NUMBER_OF_RECORDS_PER_PAGE);
				else
					productList = productDAO.getAllByCategory(category.getCategoryId(),null,null,0,NUMBER_OF_RECORDS_PER_PAGE);
				request.setAttribute("productList", productList);
				List<Category> categoryList = categoryDAO.getAll();
				request.setAttribute("categoryList", categoryList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
				dispatcher.forward(request, response);
			} else if (idParam != null) {
				int id = Integer.parseInt(idParam);
				Product product = productDAO.get(id);
				request.setAttribute("product", product);
				if (product.getCategory() != null) {
					List<Product> similarProducts = productDAO.getAllByCategory(product.getCategory().getCategoryId(),null,null,0,NUMBER_OF_RECORDS_PER_PAGE);
					similarProducts.remove(product);
					request.setAttribute("similarProducts", similarProducts);
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/product.jsp");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath());
			}
		}

	}
	public String getsortBy(HttpServletRequest request) {
		String sortBy = request.getParameter("sortBy");
		if (sortBy != null) {
			switch (sortBy) {
				case "default":
					sortBy = null;
					break;
				case "name":
					sortBy = "product_name";
					break;
			}
		}
		return sortBy;
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
	private void search(HttpServletRequest request, HttpServletResponse response) {
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	public void init() throws ServletException {
		super.init();
		Context context = null;
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctvv");
			productDAO = new ProductDAO(dataSource);
			categoryDAO = new CategoryDAO(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
